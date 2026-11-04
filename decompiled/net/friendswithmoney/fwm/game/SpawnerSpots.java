/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.core.BlockPos
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.Vec3
 */
package net.friendswithmoney.fwm.game;

import com.mojang.blaze3d.vertex.PoseStack;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.SpawnerMacro;
import net.friendswithmoney.fwm.net.RtpPoints;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public final class SpawnerSpots {
    private static final double LABEL_RADIUS = 12.0;
    private static final Map<BlockPos, Info> INFO = new HashMap();
    private static BlockPos lastOpened;
    private static long lastOpenedAt;

    private SpawnerSpots() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (world.isClientSide() && hit != null && world.getBlockState(hit.getBlockPos()).is((Object)Blocks.SPAWNER)) {
                lastOpened = hit.getBlockPos().immutable();
                lastOpenedAt = System.currentTimeMillis();
            }
            return InteractionResult.PASS;
        });
        LevelRenderEvents.COLLECT_SUBMITS.register(SpawnerSpots::render);
    }

    public static boolean openedJustNow() {
        return lastOpened != null && System.currentTimeMillis() - lastOpenedAt < 3000L;
    }

    public static BlockPos opened() {
        return lastOpened != null && System.currentTimeMillis() - lastOpenedAt < 60000L ? lastOpened : null;
    }

    static String server(Minecraft client) {
        if (client.isLocalServer()) {
            return "singleplayer";
        }
        ServerData info = client.getCurrentServer();
        return info == null || info.ip == null ? "" : info.ip.toLowerCase(Locale.ROOT);
    }

    static String dim(Minecraft client) {
        return client.level == null ? "" : RtpPoints.Dim.of(client.level).key();
    }

    public static List<BlockPos> here(Minecraft client) {
        ArrayList out = new ArrayList();
        String server = SpawnerSpots.server(client);
        String dim = SpawnerSpots.dim(client);
        for (FwmConfig.SpawnerSpot spot : FwmConfig.INSTANCE.spawnerSpots) {
            if (spot == null || !server.equals((Object)spot.server) || !dim.equals((Object)spot.dim)) continue;
            out.add((Object)new BlockPos(spot.x, spot.y, spot.z));
        }
        return out;
    }

    public static FwmConfig.SpawnerSpot spot(Minecraft client, BlockPos pos) {
        if (pos == null) {
            return null;
        }
        String server = SpawnerSpots.server(client);
        String dim = SpawnerSpots.dim(client);
        for (FwmConfig.SpawnerSpot spot : FwmConfig.INSTANCE.spawnerSpots) {
            if (spot == null || spot.x != pos.getX() || spot.y != pos.getY() || spot.z != pos.getZ() || !server.equals((Object)spot.server) || !dim.equals((Object)spot.dim)) continue;
            return spot;
        }
        return null;
    }

    public static FwmConfig.SpawnerSpot rules(Minecraft client, BlockPos pos) {
        FwmConfig.SpawnerSpot spot = SpawnerSpots.spot(client, pos);
        if (spot != null) {
            return spot;
        }
        FwmConfig.SpawnerSpot defaults = new FwmConfig.SpawnerSpot();
        defaults.mode = FwmConfig.INSTANCE.spawnerMode;
        defaults.sellFill = FwmConfig.INSTANCE.spawnerSellFill;
        defaults.dropMin = FwmConfig.INSTANCE.spawnerDropMin;
        return defaults;
    }

    public static boolean selected(Minecraft client, BlockPos pos) {
        return pos != null && SpawnerSpots.here(client).contains((Object)pos);
    }

    public static void toggle(Minecraft client, BlockPos pos) {
        String dim;
        if (pos == null) {
            return;
        }
        String server = SpawnerSpots.server(client);
        boolean removed = FwmConfig.INSTANCE.spawnerSpots.removeIf(arg_0 -> SpawnerSpots.lambda$toggle$0(pos, server, dim = SpawnerSpots.dim(client), arg_0));
        if (!removed) {
            FwmConfig.SpawnerSpot spot = new FwmConfig.SpawnerSpot(pos.getX(), pos.getY(), pos.getZ(), dim, server);
            spot.mode = FwmConfig.INSTANCE.spawnerMode;
            spot.sellFill = FwmConfig.INSTANCE.spawnerSellFill;
            spot.dropMin = FwmConfig.INSTANCE.spawnerDropMin;
            FwmConfig.INSTANCE.spawnerSpots.add((Object)spot);
        }
        INFO.remove((Object)pos);
        ConfigManager.save();
    }

    public static void clearHere(Minecraft client) {
        String server = SpawnerSpots.server(client);
        String dim = SpawnerSpots.dim(client);
        FwmConfig.INSTANCE.spawnerSpots.removeIf(spot -> spot == null || server.equals((Object)spot.server) && dim.equals((Object)spot.dim));
        INFO.clear();
        ConfigManager.save();
    }

    public static int number(Minecraft client, BlockPos pos) {
        return SpawnerSpots.here(client).indexOf((Object)pos) + 1;
    }

    public static Info info(BlockPos pos) {
        return (Info)INFO.computeIfAbsent((Object)pos.immutable(), p -> new Info());
    }

    static void counts(BlockPos pos, long bones, long arrows, int fill) {
        Info info = SpawnerSpots.info(pos);
        info.bones = bones;
        info.arrows = arrows;
        info.fill = fill;
        info.note = "";
    }

    static void note(BlockPos pos, String note) {
        SpawnerSpots.info((BlockPos)pos).note = note;
    }

    public static String summary(BlockPos pos) {
        Info info = (Info)INFO.get((Object)pos);
        if (info == null) {
            return "\u2013";
        }
        if (!info.note.isEmpty()) {
            return info.note;
        }
        if (info.bones < 0L) {
            return info.kind == null ? "\u2013" : info.kind;
        }
        return (info.kind == null ? "" : info.kind + " \u00b7 ") + SpawnerSpots.group(info.bones) + (String)(info.arrows > 0L ? " \u00b7 " + SpawnerSpots.group(info.arrows) : "") + (String)(info.fill >= 0 ? " \u00b7 " + info.fill + " %" : "");
    }

    private static String group(long n) {
        return String.format((Locale)Locale.GERMANY, (String)"%,d", (Object[])new Object[]{Math.max((long)0L, (long)n)});
    }

    private static void render(LevelRenderContext ctx) {
        if (!FwmConfig.INSTANCE.spawnerLabels || !Features.isOn("fwm.tab.spawnermacro")) {
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
        List<BlockPos> spots = SpawnerSpots.here(client);
        BlockPos current = SpawnerMacro.current();
        for (int i = 0; i < spots.size(); ++i) {
            double dz;
            double dy;
            BlockPos pos = (BlockPos)spots.get(i);
            double dx = (double)pos.getX() + 0.5 - cam.x;
            double dist = Math.sqrt((double)(dx * dx + (dy = (double)pos.getY() + 1.35 - cam.y) * dy + (dz = (double)pos.getZ() + 0.5 - cam.z) * dz));
            if (dist > 12.0) continue;
            boolean now = pos.equals((Object)current);
            MutableComponent label = Component.literal((String)((now ? "\u25b6 #" : "\u2714 #") + (i + 1))).withStyle(now ? ChatFormatting.GOLD : ChatFormatting.GREEN);
            matrices.pushPose();
            matrices.translate(dx, dy, dz);
            matrices.scale(0.8f, 0.8f, 0.8f);
            queue.submitNameTag(matrices, Vec3.ZERO, 0, (Component)label, true, 0xF000F0, cameraState);
            matrices.popPose();
        }
    }

    private static /* synthetic */ boolean lambda$toggle$0(BlockPos pos, String server, String dim, FwmConfig.SpawnerSpot spot) {
        return spot != null && spot.x == pos.getX() && spot.y == pos.getY() && spot.z == pos.getZ() && server.equals((Object)spot.server) && dim.equals((Object)spot.dim);
    }

    public static final class Info {
        public long bones = -1L;
        public long arrows = -1L;
        public int fill = -1;
        public String note = "";
        public String kind;
    }
}
