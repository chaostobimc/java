/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.reflect.TypeToken
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
 *  java.util.Collections
 *  java.util.List
 *  net.fabricmc.loader.api.FabricLoader
 */
package net.friendswithmoney.fwm.money;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.money.MoneyStore;

public final class History {
    private static final int MAX = 5000;
    private static final long MERGE_MS = 60000L;
    private static final Gson GSON = new GsonBuilder().create();
    private static List<Entry> entries = new ArrayList();
    private static String loadedFor;
    private static boolean dirty;
    private static long lastSave;

    private History() {
    }

    public static void add(MoneyStore.Kind kind, long cents, String player, String item, String line) {
        Entry last;
        long signed;
        if (!FwmConfig.INSTANCE.historyEnabled) {
            return;
        }
        History.ensureLoaded();
        long now = System.currentTimeMillis();
        long l = signed = kind.income ? cents : -cents;
        if (kind == MoneyStore.Kind.SELL && FwmConfig.INSTANCE.historyMergeSell && !entries.isEmpty() && (last = (Entry)entries.get(entries.size() - 1)).kind() == MoneyStore.Kind.SELL && now - last.t < 60000L) {
            last.c += signed;
            ++last.n;
            last.t = now;
            dirty = true;
            return;
        }
        Entry entry = new Entry();
        entry.t = now;
        entry.k = kind.ordinal();
        entry.c = signed;
        entry.p = player;
        entry.i = item;
        entry.l = line == null ? null : (line.length() > 200 ? line.substring(0, 200) : line);
        entries.add((Object)entry);
        while (entries.size() > 5000) {
            entries.remove(0);
        }
        dirty = true;
    }

    public static List<Entry> newestFirst() {
        History.ensureLoaded();
        ArrayList out = new ArrayList(entries);
        Collections.reverse((List)out);
        return out;
    }

    public static void tick() {
        if (dirty && System.currentTimeMillis() - lastSave > 60000L) {
            History.save();
        }
    }

    public static void save() {
        if (!dirty || loadedFor == null) {
            return;
        }
        long cut = System.currentTimeMillis() - (long)FwmConfig.INSTANCE.historyDays * 86400000L;
        entries.removeIf(e -> e.t < cut);
        try {
            Path file = History.file(loadedFor);
            Files.createDirectories((Path)file.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)file, (CharSequence)GSON.toJson(entries), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
            dirty = false;
            lastSave = System.currentTimeMillis();
        }
        catch (Exception e2) {
            System.err.println("[FWM] Could not write history: " + e2.getMessage());
        }
    }

    private static void ensureLoaded() {
        String id = MoneyStore.account();
        if (id.equals((Object)loadedFor)) {
            return;
        }
        if (loadedFor != null) {
            History.save();
        }
        loadedFor = id;
        entries = new ArrayList();
        try {
            List read;
            Path file = History.file(id);
            if (Files.exists((Path)file, (LinkOption[])new LinkOption[0]) && (read = (List)GSON.fromJson(Files.readString((Path)file, (Charset)StandardCharsets.UTF_8), new TypeToken<List<Entry>>(){}.getType())) != null) {
                entries = new ArrayList((Collection)read);
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read history: " + e.getMessage());
        }
    }

    private static Path file(String id) {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-history-" + id + ".json");
    }

    public static final class Entry {
        public long t;
        public int k;
        public long c;
        public String p;
        public String i;
        public int n = 1;
        public String l;

        public MoneyStore.Kind kind() {
            MoneyStore.Kind[] all = MoneyStore.Kind.values();
            return this.k >= 0 && this.k < all.length ? all[this.k] : MoneyStore.Kind.OTHER_IN;
        }
    }
}
