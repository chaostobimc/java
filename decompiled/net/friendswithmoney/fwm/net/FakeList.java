/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  java.lang.CharSequence
 *  java.lang.Exception
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
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.net.FwmApi;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class FakeList {
    private static final long REFRESH_MS = 600000L;
    private static final long RETRY_MS = 300000L;
    private static final Gson GSON = new Gson();
    private static volatile Set<String> tags = Set.of();
    private static volatile Set<String> players = Set.of();
    private static volatile List<String> similar = List.of();
    private static volatile boolean managed;
    private static volatile Map<String, String> real;
    private static boolean busy;
    private static boolean fromDisk;
    private static long nextLoad;

    private FakeList() {
    }

    public static boolean managed() {
        return managed;
    }

    public static List<String> similarTags() {
        return similar;
    }

    public static boolean isFakeTag(String tag) {
        String key = FakeList.tag(tag);
        return !key.isEmpty() && tags.contains((Object)key);
    }

    public static boolean isFakePlayer(String name) {
        return name != null && players.contains((Object)name.trim().toLowerCase(Locale.ROOT));
    }

    public static String realLabel(String name) {
        if (name == null) {
            return null;
        }
        return (String)real.get((Object)ServerLine.strip(name).trim().toLowerCase(Locale.ROOT));
    }

    public static Component realBadge(String name) {
        String label = FakeList.realLabel(name);
        return label == null ? null : Component.literal((String)(" (" + label + ")")).withStyle(ChatFormatting.GREEN);
    }

    public static Component decorateReal(String name, Component text) {
        Component badge = FakeList.realBadge(name);
        if (badge == null || text == null) {
            return text;
        }
        return Component.empty().append(text).append(badge);
    }

    public static void tick(Minecraft client) {
        if (!fromDisk) {
            fromDisk = true;
            Data cached = FakeList.load();
            if (cached != null) {
                FakeList.apply(cached);
            }
        }
        if (busy || System.currentTimeMillis() < nextLoad) {
            return;
        }
        busy = true;
        FwmApi.get("/api/mod/fakes", Data.class).whenCompleteAsync((result, error) -> client.execute(() -> {
            busy = false;
            if (error != null || result == null) {
                System.err.println("[FWM] Fake list failed: " + (error == null ? "empty response" : error.getMessage()));
                nextLoad = System.currentTimeMillis() + 300000L;
                return;
            }
            FakeList.apply(result);
            nextLoad = System.currentTimeMillis() + 600000L;
            FakeList.save(result);
        }), FwmApi.executor());
    }

    private static void apply(Data in) {
        HashSet t = new HashSet();
        if (in.tags != null) {
            for (String s : in.tags) {
                if (FakeList.tag(s).isEmpty()) continue;
                t.add((Object)FakeList.tag(s));
            }
        }
        HashSet p = new HashSet();
        if (in.players != null) {
            for (String s : in.players) {
                if (s == null || s.isBlank()) continue;
                p.add((Object)s.trim().toLowerCase(Locale.ROOT));
            }
        }
        HashMap r = new HashMap();
        if (in.real != null) {
            for (Real entry : in.real) {
                if (entry == null || entry.name == null || entry.name.isBlank()) continue;
                String label = entry.label == null || entry.label.isBlank() ? "echt" : entry.label.trim();
                r.put((Object)entry.name.trim().toLowerCase(Locale.ROOT), (Object)label);
            }
        }
        tags = Set.copyOf((Collection)t);
        players = Set.copyOf((Collection)p);
        ArrayList sim = new ArrayList();
        if (in.similar != null) {
            for (String x : in.similar) {
                if (FakeList.tag(x).isEmpty()) continue;
                sim.add((Object)FakeList.tag(x));
            }
        }
        similar = List.copyOf((Collection)sim);
        managed = in.managed;
        real = Map.copyOf((Map)r);
    }

    private static String tag(String raw) {
        if (raw == null) {
            return "";
        }
        String s = raw.trim();
        if (s.startsWith("[") && s.endsWith("]") && s.length() > 1) {
            s = s.substring(1, s.length() - 1);
        }
        return s.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-fakes.json");
    }

    private static Data load() {
        try {
            Path path = FakeList.file();
            if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
                return null;
            }
            return (Data)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Data.class);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read fake list cache: " + e.getMessage());
            return null;
        }
    }

    private static void save(Data value) {
        try {
            Path path = FakeList.file();
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)value), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write fake list cache: " + e.getMessage());
        }
    }

    static {
        real = Map.of();
    }

    public static final class Data {
        public List<String> tags;
        public List<String> players;
        public List<String> similar;
        public boolean managed;
        public List<Real> real;
    }

    public static final class Real {
        public String name;
        public String label;
    }
}
