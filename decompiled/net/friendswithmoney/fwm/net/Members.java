/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.IllegalStateException
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
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
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
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.MemberData;
import net.minecraft.client.Minecraft;

public final class Members {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static MemberData data;
    private static State state;
    private static Map<String, MemberData.Member> byName;
    private static Set<String> clanTags;
    private static final List<String> RANKS;
    public static final int LEADER_RANK;
    private static long fetchedAt;
    private static final long MAX_AGE_MS = 172800000L;
    private static long lastAttempt;
    private static boolean inFlight;

    private Members() {
    }

    public static boolean isMember(String accountName) {
        return Members.role(accountName) != null;
    }

    public static MemberData.Member member(String accountName) {
        if (accountName == null || accountName.isEmpty()) {
            return null;
        }
        if (fetchedAt <= 0L || System.currentTimeMillis() - fetchedAt > 172800000L) {
            return null;
        }
        return (MemberData.Member)byName.get((Object)accountName.toLowerCase(Locale.ROOT));
    }

    public static String role(String accountName) {
        MemberData.Member member = Members.member(accountName);
        return member == null ? null : member.role;
    }

    public static int clanOrder(String clan) {
        String c;
        String string = c = clan == null ? "" : clan.trim().toUpperCase(Locale.ROOT);
        if (c.equals((Object)"FWM")) {
            return 0;
        }
        if (c.equals((Object)"FWM$")) {
            return 1;
        }
        if (c.matches("FWM[0-9]{1,3}")) {
            return 2 + Integer.parseInt((String)c.substring(3));
        }
        return 10000;
    }

    public static int rankIndex(String role) {
        if (role == null) {
            return RANKS.size();
        }
        String lower = Members.normalize(role);
        for (int i = 0; i < RANKS.size(); ++i) {
            if (!lower.startsWith((String)RANKS.get(i))) continue;
            return i;
        }
        return RANKS.size();
    }

    private static String normalize(String role) {
        return role.toLowerCase(Locale.ROOT).trim().replaceAll("[\\s_-]", "");
    }

    public static List<MemberData.Member> list() {
        return data == null || Members.data.members == null ? List.of() : Members.data.members;
    }

    public static State state() {
        return state;
    }

    public static long ageMinutes() {
        if (fetchedAt == 0L) {
            return -1L;
        }
        return (System.currentTimeMillis() - fetchedAt) / 60000L;
    }

    public static void load() {
        Path path = Members.cacheFile();
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            return;
        }
        try {
            Snapshot snapshot = (Snapshot)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Snapshot.class);
            if (snapshot != null && snapshot.data != null) {
                Members.apply(snapshot.data, snapshot.fetchedAt);
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    public static void tick(Minecraft client) {
        if (inFlight) {
            return;
        }
        long interval = (long)Math.max((int)1, (int)FwmConfig.INSTANCE.memberRefreshMinutes) * 60000L;
        if (System.currentTimeMillis() - Math.max((long)fetchedAt, (long)lastAttempt) < interval) {
            return;
        }
        inFlight = true;
        lastAttempt = System.currentTimeMillis();
        if (data == null) {
            state = State.LOADING;
        }
        FwmApi.getUrl(FwmConfig.INSTANCE.membersApiUrl, JsonObject.class).thenApply(Members::parse).whenCompleteAsync((result, error) -> {
            if (error != null || result == null || result.members.isEmpty()) {
                System.err.println("[FWM] Member fetch failed: " + (error == null ? "empty list" : error.getMessage()));
                client.execute(() -> {
                    inFlight = false;
                    state = State.ERROR;
                });
                return;
            }
            long now = System.currentTimeMillis();
            Members.save(result, now);
            client.execute(() -> {
                inFlight = false;
                Members.apply(result, now);
            });
        }, FwmApi.executor());
    }

    private static MemberData parse(JsonObject root) {
        if (root == null) {
            throw new IllegalStateException("empty response");
        }
        if (root.has("ok") && root.get("ok").isJsonPrimitive() && !root.get("ok").getAsBoolean()) {
            throw new IllegalStateException("api said ok:false");
        }
        MemberData out = new MemberData();
        for (Map.Entry group : root.entrySet()) {
            if (!((JsonElement)group.getValue()).isJsonArray()) continue;
            String clan = ((String)group.getKey()).toUpperCase(Locale.ROOT);
            out.clans.add((Object)clan);
            for (JsonElement element : ((JsonElement)group.getValue()).getAsJsonArray()) {
                JsonObject entry;
                String name;
                if (!element.isJsonObject() || (name = Members.string(entry = element.getAsJsonObject(), "mcName")) == null || name.isBlank()) continue;
                MemberData.Member member = new MemberData.Member();
                member.name = name.trim();
                member.role = Members.string(entry, "rolle");
                member.clan = clan;
                out.members.add((Object)member);
            }
        }
        return out;
    }

    private static String string(JsonObject object, String key) {
        JsonElement element = object.get(key);
        return element == null || !element.isJsonPrimitive() ? null : element.getAsString();
    }

    private static void apply(MemberData value, long at) {
        HashMap built = new HashMap();
        for (MemberData.Member member : value.members == null ? new ArrayList() : value.members) {
            if (member == null || member.name == null || member.name.isBlank()) continue;
            if (member.role == null || member.role.isBlank()) {
                member.role = "Mitglied";
            }
            if (member.role.trim().equalsIgnoreCase("Kapital")) {
                member.role = "Kapitalstratege";
            }
            if (Members.normalize(member.role).equals((Object)"headclanrat")) {
                member.role = "Head-Clanrat";
            }
            built.put((Object)member.name.toLowerCase(Locale.ROOT), (Object)member);
        }
        HashSet tags = new HashSet();
        if (value.clans != null) {
            for (String clan : value.clans) {
                if (clan == null || clan.isBlank()) continue;
                tags.add((Object)clan.trim().toUpperCase(Locale.ROOT));
            }
        }
        if (tags.isEmpty() && value.members != null) {
            for (MemberData.Member member : value.members) {
                if (member == null || member.clan == null) continue;
                tags.add((Object)member.clan.toUpperCase(Locale.ROOT));
            }
        }
        data = value;
        byName = Map.copyOf((Map)built);
        clanTags = Set.copyOf((Collection)tags);
        fetchedAt = at;
        state = State.OK;
    }

    public static Set<String> clanTags() {
        return clanTags;
    }

    private static void save(MemberData value, long at) {
        Path path = Members.cacheFile();
        try {
            Snapshot snapshot = new Snapshot();
            snapshot.fetchedAt = at;
            snapshot.data = value;
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)snapshot), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    private static Path cacheFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-members.json");
    }

    static {
        state = State.EMPTY;
        byName = Map.of();
        clanTags = Set.of();
        RANKS = List.of((Object[])new String[]{"management", "headclanrat", "clanrat", "leader", "titan", "marktlegende", "kapitalf\u00fcrst", "elite", "kapitalstratege", "investor", "anteilseigner", "kassenfreund", "mitglied"});
        LEADER_RANK = RANKS.indexOf((Object)"leader");
    }

    public static enum State {
        EMPTY,
        LOADING,
        OK,
        ERROR;

    }

    private static class Snapshot {
        long fetchedAt;
        MemberData data;

        private Snapshot() {
        }
    }
}
