/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.MatchException
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
 *  java.util.ArrayDeque
 *  java.util.ArrayList
 *  java.util.Deque
 *  java.util.HashSet
 *  java.util.List
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.minecraft.client.Minecraft;

public final class ModNews {
    private static final long REFRESH_MS = 600000L;
    private static final long RETRY_MS = 300000L;
    public static final long CARD_MS = 10000L;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static volatile Data data = new Data();
    private static boolean busy;
    private static boolean fromDisk;
    private static long nextLoad;
    private static final Deque<Entry> cards;
    private static Entry card;
    private static long cardAt;

    private ModNews() {
    }

    public static List<Entry> list(Kind kind) {
        Data d = data;
        List<Entry> list = switch (kind.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> d.infos;
            case 1 -> d.bugs;
            case 2 -> d.fixes;
        };
        return list == null ? List.of() : list;
    }

    public static void tick(Minecraft client) {
        if (!fromDisk) {
            fromDisk = true;
            Data cached = ModNews.load();
            if (cached != null) {
                data = ModNews.clean(cached);
            }
        }
        if (busy || System.currentTimeMillis() < nextLoad) {
            return;
        }
        busy = true;
        FwmApi.get("/api/mod/news", Data.class).whenCompleteAsync((result, error) -> client.execute(() -> {
            busy = false;
            if (error != null || result == null) {
                System.err.println("[FWM] News failed: " + (error == null ? "empty response" : error.getMessage()));
                nextLoad = System.currentTimeMillis() + 300000L;
                return;
            }
            data = ModNews.clean(result);
            nextLoad = System.currentTimeMillis() + 600000L;
            ModNews.save(data);
            ModNews.queueNew();
        }), FwmApi.executor());
    }

    private static Data clean(Data in) {
        if (in.infos == null) {
            in.infos = new ArrayList();
        }
        if (in.bugs == null) {
            in.bugs = new ArrayList();
        }
        if (in.fixes == null) {
            in.fixes = new ArrayList();
        }
        in.infos.removeIf(e -> e == null || e.id == null);
        in.bugs.removeIf(e -> e == null || e.id == null);
        in.fixes.removeIf(e -> e == null || e.id == null);
        return in;
    }

    private static void queueNew() {
        FwmConfig config = FwmConfig.INSTANCE;
        boolean first = config.newsShownIds == null;
        HashSet shown = new HashSet(first ? List.of() : config.newsShownIds);
        ArrayList fresh = new ArrayList();
        for (Entry e : ModNews.data.infos) {
            if (shown.contains((Object)e.id)) continue;
            fresh.add((Object)e);
        }
        if (fresh.isEmpty() && !first) {
            return;
        }
        if (first && fresh.size() > 1) {
            fresh = List.of((Object)((Entry)fresh.get(0)));
        }
        for (int i = fresh.size() - 1; i >= 0; --i) {
            cards.add((Object)((Entry)fresh.get(i)));
        }
        ArrayList ids = new ArrayList();
        for (Entry e : ModNews.data.infos) {
            ids.add((Object)e.id);
        }
        config.newsShownIds = ids;
        ConfigManager.save();
    }

    public static Entry card() {
        long now = System.currentTimeMillis();
        if (card != null && now - cardAt < 10000L) {
            return card;
        }
        card = (Entry)cards.poll();
        cardAt = now;
        return card;
    }

    public static float cardProgress() {
        return card == null ? 1.0f : Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - cardAt) / 10000.0f));
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-news.json");
    }

    private static Data load() {
        try {
            Path path = ModNews.file();
            if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
                return null;
            }
            return (Data)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Data.class);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read news cache: " + e.getMessage());
            return null;
        }
    }

    private static void save(Data value) {
        try {
            Path path = ModNews.file();
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)value), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write news cache: " + e.getMessage());
        }
    }

    static {
        cards = new ArrayDeque();
    }

    public static final class Data {
        public List<Entry> infos = new ArrayList();
        public List<Entry> bugs = new ArrayList();
        public List<Entry> fixes = new ArrayList();
    }

    public static enum Kind {
        INFOS,
        BUGS,
        FIXES;

    }

    public static final class Entry {
        public String id;
        public String title;
        public String text;
        public long createdAt;
    }
}
