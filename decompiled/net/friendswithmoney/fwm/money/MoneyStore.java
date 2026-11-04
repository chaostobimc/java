/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.mojang.authlib.GameProfile
 *  java.lang.CharSequence
 *  java.lang.Exception
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
 *  java.time.LocalDate
 *  java.util.ArrayList
 *  java.util.Map
 *  java.util.TreeMap
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.money;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.authlib.GameProfile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;

public final class MoneyStore {
    private static final int KEEP_DAYS = 60;
    private static final long SESSION_GAP_MS = 300000L;
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static Data data = new Data();
    private static String loadedFor;
    private static boolean dirty;
    private static long lastSave;
    private static long sessionCents;
    private static long sessionLeftAt;
    private static boolean inSession;

    private MoneyStore() {
    }

    public static void book(Kind kind, long cents) {
        if (cents <= 0L) {
            return;
        }
        MoneyStore.ensureLoaded();
        long[] day = (long[])MoneyStore.data.days.computeIfAbsent((Object)MoneyStore.today(), k -> new long[Kind.values().length]);
        if (day.length < Kind.values().length) {
            long[] grown = new long[Kind.values().length];
            System.arraycopy((Object)day, (int)0, (Object)grown, (int)0, (int)day.length);
            day = grown;
            MoneyStore.data.days.put((Object)MoneyStore.today(), (Object)day);
        }
        int n = kind.ordinal();
        day[n] = day[n] + cents;
        sessionCents += kind.income ? cents : -cents;
        dirty = true;
    }

    public static long user() {
        return sessionCents;
    }

    public static long[] day(LocalDate date) {
        MoneyStore.ensureLoaded();
        long[] day = (long[])MoneyStore.data.days.get((Object)date.toString());
        long[] out = new long[Kind.values().length];
        if (day != null) {
            System.arraycopy((Object)day, (int)0, (Object)out, (int)0, (int)Math.min((int)day.length, (int)out.length));
        }
        return out;
    }

    public static long net(long[] day) {
        long sum = 0L;
        for (Kind kind : Kind.values()) {
            sum += kind.income ? day[kind.ordinal()] : -day[kind.ordinal()];
        }
        return sum;
    }

    public static long income(long[] day) {
        long sum = 0L;
        for (Kind kind : Kind.values()) {
            if (!kind.income) continue;
            sum += day[kind.ordinal()];
        }
        return sum;
    }

    public static long expenses(long[] day) {
        long sum = 0L;
        for (Kind kind : Kind.values()) {
            if (kind.income) continue;
            sum += day[kind.ordinal()];
        }
        return sum;
    }

    public static long todayNet() {
        return MoneyStore.net(MoneyStore.day(LocalDate.now()));
    }

    public static boolean claimGoal() {
        MoneyStore.ensureLoaded();
        if (MoneyStore.today().equals((Object)MoneyStore.data.goalNotified)) {
            return false;
        }
        MoneyStore.data.goalNotified = MoneyStore.today();
        dirty = true;
        return true;
    }

    public static void joined() {
        if (!inSession && System.currentTimeMillis() - sessionLeftAt > 300000L) {
            sessionCents = 0L;
        }
        inSession = true;
    }

    public static void left() {
        if (!inSession) {
            return;
        }
        inSession = false;
        sessionLeftAt = System.currentTimeMillis();
        MoneyStore.save();
    }

    public static void tick() {
        if (dirty && System.currentTimeMillis() - lastSave > 60000L) {
            MoneyStore.save();
        }
    }

    public static void save() {
        if (!dirty || loadedFor == null) {
            return;
        }
        String cut = LocalDate.now().minusDays(60L).toString();
        ArrayList old = new ArrayList();
        for (String key : MoneyStore.data.days.keySet()) {
            if (key.compareTo(cut) >= 0) continue;
            old.add((Object)key);
        }
        old.forEach(arg_0 -> MoneyStore.data.days.remove(arg_0));
        try {
            Path file = MoneyStore.file(loadedFor);
            Files.createDirectories((Path)file.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)file, (CharSequence)GSON.toJson((Object)data), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
            dirty = false;
            lastSave = System.currentTimeMillis();
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write money file: " + e.getMessage());
        }
    }

    private static void ensureLoaded() {
        String id = MoneyStore.account();
        if (id.equals((Object)loadedFor)) {
            return;
        }
        if (loadedFor != null) {
            MoneyStore.save();
        }
        loadedFor = id;
        data = new Data();
        try {
            Data read;
            Path file = MoneyStore.file(id);
            if (Files.exists((Path)file, (LinkOption[])new LinkOption[0]) && (read = (Data)GSON.fromJson(Files.readString((Path)file, (Charset)StandardCharsets.UTF_8), Data.class)) != null) {
                if (read.days == null) {
                    read.days = new TreeMap();
                }
                if (read.goalNotified == null) {
                    read.goalNotified = "";
                }
                data = read;
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read money file: " + e.getMessage());
        }
    }

    static String account() {
        GameProfile profile = Minecraft.getInstance().getGameProfile();
        return profile == null || profile.id() == null ? "default" : profile.id().toString();
    }

    private static Path file(String id) {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-money-" + id + ".json");
    }

    static String today() {
        return LocalDate.now().toString();
    }

    static final class Data {
        Map<String, long[]> days = new TreeMap();
        String goalNotified = "";

        Data() {
        }
    }

    public static enum Kind {
        SELL(true),
        PAY_IN(true),
        PAY_OUT(false),
        AH_SELL(true),
        AH_BUY(false),
        OTHER_IN(true),
        OTHER_OUT(false),
        ORDER(true),
        ORDER_BUY(false),
        FEE(false);

        public final boolean income;

        private Kind(boolean income) {
            this.income = income;
        }
    }
}
