/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.blaze3d.vertex.PoseStack
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.attribute.FileAttribute
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Set
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.phys.Vec3
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.blaze3d.vertex.PoseStack;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.RtpPoints;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.phys.Vec3;

public final class Waypoints {
    public static final double FOUND_RADIUS = 2.0;
    public static final double SHOW_RADIUS = 500.0;
    private static final double MAX_DRAW = 32.0;
    private static final long REFRESH_MS = 600000L;
    private static final long RETRY_MS = 300000L;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static volatile Data data = new Data();
    private static boolean busy;
    private static boolean fromDisk;
    private static long nextLoad;
    private static int ticks;
    private static Set<String> doneSet;
    private static List<String> doneFrom;
    private static int doneSize;

    private Waypoints() {
    }

    public static List<Group> groups() {
        List<Group> g = Waypoints.data.groups;
        return g == null ? List.of() : g;
    }

    public static String key(Group group, Point point) {
        return group.id + "/" + point.id;
    }

    public static boolean groupOn(Group group) {
        return !FwmConfig.INSTANCE.waypointsGroupOff.contains((Object)group.id);
    }

    public static void setGroupOn(Group group, boolean on) {
        List<String> off = FwmConfig.INSTANCE.waypointsGroupOff;
        off.remove((Object)group.id);
        if (!on) {
            off.add((Object)group.id);
        }
        ConfigManager.save();
    }

    public static boolean done(Group group, Point point) {
        List<String> list = FwmConfig.INSTANCE.waypointsDone;
        if (list != doneFrom || list.size() != doneSize) {
            doneSet = new HashSet(list);
            doneFrom = list;
            doneSize = list.size();
        }
        return doneSet.contains((Object)Waypoints.key(group, point));
    }

    public static void setDone(Group group, Point point, boolean done) {
        List<String> list = FwmConfig.INSTANCE.waypointsDone;
        list.remove((Object)Waypoints.key(group, point));
        if (done) {
            list.add((Object)Waypoints.key(group, point));
        }
        ConfigManager.save();
    }

    public static int found(Group group) {
        int n = 0;
        for (Point p : group.points) {
            if (!Waypoints.done(group, p)) continue;
            ++n;
        }
        return n;
    }

    public static void register() {
        LevelRenderEvents.COLLECT_SUBMITS.register(Waypoints::render);
    }

    public static void tick(Minecraft client) {
        if (!Features.isOn("fwm.tab.waypoints")) {
            return;
        }
        if (!fromDisk) {
            fromDisk = true;
            Data cached = Waypoints.load();
            if (cached != null) {
                data = Waypoints.clean(cached);
            }
        }
        if (!busy && System.currentTimeMillis() >= nextLoad) {
            busy = true;
            FwmApi.get("/api/mod/waypoints", Data.class).whenCompleteAsync((result, error) -> client.execute(() -> {
                busy = false;
                if (error != null || result == null) {
                    System.err.println("[FWM] Waypoints failed: " + (error == null ? "empty response" : error.getMessage()));
                    nextLoad = System.currentTimeMillis() + 300000L;
                    return;
                }
                data = Waypoints.clean(result);
                nextLoad = System.currentTimeMillis() + 600000L;
                Waypoints.save(data);
            }), FwmApi.executor());
        }
        if (++ticks % 10 == 0) {
            Waypoints.checkFound(client);
        }
    }

    private static Data clean(Data in) {
        if (in.groups == null) {
            in.groups = new ArrayList();
        }
        in.groups.removeIf(g -> g == null || g.id == null);
        for (Group g2 : in.groups) {
            if (g2.points == null) {
                g2.points = new ArrayList();
            }
            g2.points.removeIf(p -> p == null || p.id == null);
            g2.points.sort((a, b) -> Integer.compare((int)a.n, (int)b.n));
        }
        return in;
    }

    public static boolean reached(Point point, double px, double py, double pz) {
        double dx = px - ((double)point.x + 0.5);
        double dy = py - (double)point.y;
        double dz = pz - ((double)point.z + 0.5);
        return dx * dx + dz * dz <= 4.0 && Math.abs((double)dy) <= 3.0;
    }

    private static void checkFound(Minecraft client) {
        if (client.player == null || client.level == null) {
            return;
        }
        String dim = RtpPoints.Dim.of(client.level).key();
        for (Group group : Waypoints.groups()) {
            if (!Waypoints.groupOn(group)) continue;
            for (Point point : group.points) {
                if (!dim.equals((Object)point.dim) || Waypoints.done(group, point) || !Waypoints.reached(point, client.player.getX(), client.player.getY(), client.player.getZ())) continue;
                Waypoints.setDone(group, point, true);
                client.player.sendOverlayMessage((Component)Component.literal((String)("\u2714 " + group.name + " " + point.n + " ")).withStyle(ChatFormatting.GREEN).append((Component)Component.literal((String)("(" + Waypoints.found(group) + "/" + group.points.size() + ")")).withStyle(ChatFormatting.GRAY)));
            }
        }
    }

    private static void render(LevelRenderContext ctx) {
        if (!Features.isOn("fwm.tab.waypoints")) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.player == null) {
            return;
        }
        PoseStack matrices = ctx.poseStack();
        SubmitNodeCollector queue = ctx.submitNodeCollector();
        if (matrices == null || queue == null || ctx.levelState() == null) {
            return;
        }
        CameraRenderState cameraState = ctx.levelState().cameraRenderState;
        Camera camera = client.gameRenderer.mainCamera();
        if (cameraState == null || camera == null) {
            return;
        }
        Vec3 cam = camera.position();
        String dim = RtpPoints.Dim.of(client.level).key();
        for (Group group : Waypoints.groups()) {
            if (!Waypoints.groupOn(group)) continue;
            for (Point point : group.points) {
                double dz;
                double dy;
                double dx;
                double dist;
                if (!dim.equals((Object)point.dim) || Waypoints.done(group, point) || (dist = Math.sqrt((double)((dx = (double)point.x + 0.5 - cam.x) * dx + (dy = (double)point.y + 1.2 - cam.y) * dy + (dz = (double)point.z + 0.5 - cam.z) * dz))) > 500.0) continue;
                double f = dist > 32.0 ? 32.0 / dist : 1.0;
                MutableComponent label = Component.literal((String)"\u25c6 ").withStyle(ChatFormatting.GOLD).append((Component)Component.literal((String)(group.name + " " + point.n)).withStyle(ChatFormatting.WHITE)).append((Component)Component.literal((String)("  " + Math.round((double)dist) + " m")).withStyle(ChatFormatting.GRAY));
                float scale = 1.3f + (float)(Math.min((double)dist, (double)32.0) / 32.0) * 1.7f;
                matrices.pushPose();
                matrices.translate(dx * f, dy * f, dz * f);
                matrices.scale(scale, scale, scale);
                queue.submitNameTag(matrices, Vec3.ZERO, 0, (Component)label, true, 0xF000F0, cameraState);
                matrices.popPose();
            }
        }
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-waypoints.json");
    }

    private static Data load() {
        try {
            Path path = Waypoints.file();
            if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
                return null;
            }
            return (Data)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Data.class);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read waypoint cache: " + e.getMessage());
            return null;
        }
    }

    private static void save(Data value) {
        try {
            Path path = Waypoints.file();
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)value), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write waypoint cache: " + e.getMessage());
        }
    }

    static {
        doneSet = Set.of();
        doneSize = -1;
    }

    public static final class Data {
        public List<Group> groups = new ArrayList();
    }

    public static final class Group {
        public String id;
        public String name;
        public String credit;
        public List<Point> points = new ArrayList();
    }

    public static final class Point {
        public String id;
        public int n;
        public int x;
        public int y;
        public int z;
        public String dim = "overworld";
    }
}
