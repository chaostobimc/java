/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashSet
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map$Entry
 *  java.util.UUID
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FontDescription
 *  net.minecraft.network.chat.FontDescription$Resource
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.net.ClanLogos;
import net.friendswithmoney.fwm.net.FwmApi;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;

public final class TrustedClans {
    private static final long REFRESH_MS = 600000L;
    private static final long RETRY_MS = 60000L;
    private static final Gson GSON = new Gson();
    private static final Clan FWM = TrustedClans.fwm();
    private static volatile List<Clan> dashClans = TrustedClans.builtIn();
    private static volatile List<Clan> botClans = List.of();
    private static volatile List<BotFakeTag> botFakes = List.of();
    private static volatile List<Clan> clans = dashClans;
    private static volatile List<Player> players = List.of();
    private static volatile long nextFetch;
    private static volatile long nextBotFetch;
    private static volatile BotCache botCache;
    private static int logoTicks;

    private static Clan fwm() {
        Clan c = new Clan();
        c.id = "fwm";
        c.name = "FWM";
        return c;
    }

    private TrustedClans() {
    }

    private static List<Clan> builtIn() {
        Clan market = new Clan();
        market.id = "mrket";
        market.name = "HugoSMP Market";
        market.display = "{tag}";
        market.gradient = "#AA0000,#00AA00";
        market.tags = List.of((Object)"MRKET", (Object)"MRK2T");
        return List.of((Object)market);
    }

    private static Path cacheFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-clans.json");
    }

    private static Path botCacheFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-trusted-clans.json");
    }

    public static void load() {
        Path file2;
        try {
            file2 = TrustedClans.cacheFile();
            if (!Files.exists((Path)file2, (LinkOption[])new LinkOption[0])) {
                return;
            }
            Data data = (Data)GSON.fromJson(Files.readString((Path)file2, (Charset)StandardCharsets.UTF_8), Data.class);
            if (data != null && data.clans != null && !data.clans.isEmpty()) {
                dashClans = TrustedClans.tidy(data.clans);
            }
            if (data != null && data.players != null) {
                players = List.copyOf(data.players);
            }
        }
        catch (Exception file2) {
            // empty catch block
        }
        try {
            BotCache cache;
            file2 = TrustedClans.botCacheFile();
            if (Files.exists((Path)file2, (LinkOption[])new LinkOption[0]) && (cache = (BotCache)GSON.fromJson(Files.readString((Path)file2, (Charset)StandardCharsets.UTF_8), BotCache.class)) != null) {
                botCache = cache;
                TrustedClans.applyBot();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        TrustedClans.merge();
    }

    public static void tick(Minecraft client) {
        long now = System.currentTimeMillis();
        if (now >= nextFetch) {
            nextFetch = now + 600000L;
            FwmApi.get("/api/mod/clans", Data.class).whenComplete((data, error) -> {
                if (error != null || data == null || data.clans == null) {
                    nextFetch = System.currentTimeMillis() + 60000L;
                    return;
                }
                dashClans = TrustedClans.tidy(data.clans);
                players = data.players == null ? List.of() : List.copyOf(data.players);
                TrustedClans.merge();
                try {
                    Files.writeString((Path)TrustedClans.cacheFile(), (CharSequence)GSON.toJson(data), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
                }
                catch (Exception exception) {
                    // empty catch block
                }
            });
        }
        if (now >= nextBotFetch) {
            nextBotFetch = now + 600000L;
            TrustedClans.fetchBot();
        }
        if (++logoTicks % 20 == 0) {
            ClanLogos.tick(client, clans, players);
        }
    }

    private static void fetchBot() {
        BotCache cache = botCache;
        FwmApi.request("GET", "https://api.friendswithmoney.net/trusted-clans", cache.listEtag, null, null, 2000000L).thenCompose(list -> {
            if (list.status() == 200) {
                BotList parsed = list.json(BotList.class);
                if (parsed != null && parsed.clans != null) {
                    cache.list = parsed;
                    cache.listEtag = list.etag();
                }
            } else if (list.status() == 429) {
                nextBotFetch = System.currentTimeMillis() + Math.max((long)60L, (long)list.retryAfterSeconds()) * 1000L;
            }
            return FwmApi.request("GET", "https://api.friendswithmoney.net/fake-clans", cache.fakesEtag, null, null, 2000000L);
        }).whenComplete((fakes, error) -> {
            if (error != null) {
                nextBotFetch = System.currentTimeMillis() + 60000L;
                return;
            }
            if (fakes.status() == 200) {
                BotFakes parsed = fakes.json(BotFakes.class);
                if (parsed != null && parsed.tags != null) {
                    cache.fakes = parsed;
                    cache.fakesEtag = fakes.etag();
                }
            } else if (fakes.status() == 429) {
                nextBotFetch = System.currentTimeMillis() + Math.max((long)60L, (long)fakes.retryAfterSeconds()) * 1000L;
            }
            botCache = cache;
            TrustedClans.applyBot();
            TrustedClans.merge();
            try {
                Files.writeString((Path)TrustedClans.botCacheFile(), (CharSequence)GSON.toJson((Object)cache), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
    }

    private static boolean isFwm(String text) {
        if (text == null) {
            return false;
        }
        String t = text.replaceAll("[^A-Za-z]", "").toLowerCase(Locale.ROOT);
        return t.equals((Object)"fwm") || t.equals((Object)"friendswithmoney");
    }

    private static void applyBot() {
        BotCache cache = botCache;
        LinkedHashMap groups = new LinkedHashMap();
        if (cache.list != null && cache.list.clans != null) {
            for (BotClan b : cache.list.clans) {
                String hex;
                if (b == null || b.groupId == null || b.tag == null || b.tag.isBlank()) continue;
                Object c = (Clan)groups.computeIfAbsent((Object)b.groupId, id -> {
                    Clan n = new Clan();
                    n.id = "g" + id.replaceAll("[^0-9A-Za-z_-]", "").toLowerCase(Locale.ROOT);
                    n.display = "&f&l{tag}";
                    n.exact = true;
                    return n;
                });
                String tag = TrustedClans.exact(b.tag);
                if ("main".equals((Object)b.type)) {
                    c.tags.add(0, (Object)tag);
                } else {
                    c.tags.add((Object)tag);
                }
                if (c.name == null || "main".equals((Object)b.type)) {
                    c.name = b.displayName != null ? b.displayName : b.name;
                }
                String string = hex = b.nameColor == null ? "" : b.nameColor.trim().replace((CharSequence)"#", (CharSequence)"");
                if (hex.matches("[0-9A-Fa-f]{6}") && ("main".equals((Object)b.type) || c.display.equals((Object)"&f&l{tag}"))) {
                    c.display = "&#" + hex.toUpperCase(Locale.ROOT) + "&l{tag}";
                }
                if (b.iconUrl != null) {
                    String string2 = c.iconUrl = b.iconUrl.startsWith("http") ? b.iconUrl : "https://api.friendswithmoney.net" + b.iconUrl;
                }
                if (b.iconEtag == null) continue;
                c.iconEtag = b.iconEtag;
            }
        }
        ArrayList out = new ArrayList();
        HashSet fwmGroups = new HashSet();
        for (Map.Entry e : groups.entrySet()) {
            Clan c = (Clan)e.getValue();
            boolean fwm = TrustedClans.isFwm(c.name);
            for (String t : c.tags) {
                if (!TrustedClans.isFwm(t)) continue;
                fwm = true;
            }
            if (fwm) {
                fwmGroups.add((Object)((String)e.getKey()));
                continue;
            }
            out.add((Object)c);
        }
        ArrayList fakes = new ArrayList();
        if (cache.fakes != null && cache.fakes.tags != null) {
            for (BotFake f : cache.fakes.tags) {
                Clan target;
                if (f == null || f.tag == null || f.tag.isBlank()) continue;
                Clan clan = target = fwmGroups.contains((Object)f.groupId) || TrustedClans.isFwm(f.trustedClan) ? FWM : (Clan)groups.get((Object)f.groupId);
                if (target == null) continue;
                fakes.add((Object)new BotFakeTag(TrustedClans.exact(f.tag), target));
            }
        }
        botClans = List.copyOf((Collection)out);
        botFakes = List.copyOf((Collection)fakes);
    }

    private static void merge() {
        ArrayList out = new ArrayList(botClans);
        HashSet botTags = new HashSet();
        for (Clan c : botClans) {
            for (String t : c.tags) {
                botTags.add((Object)t.toUpperCase(Locale.ROOT));
            }
        }
        for (Clan c : dashClans) {
            boolean clash = false;
            for (String t : c.tags) {
                if (!botTags.contains((Object)t)) continue;
                clash = true;
            }
            if (clash) continue;
            out.add((Object)c);
        }
        clans = List.copyOf((Collection)out);
    }

    static String exact(String tag) {
        return tag.trim().replaceAll("[^\\x00-\\x7F]", "").replace((CharSequence)"[", (CharSequence)"").replace((CharSequence)"]", (CharSequence)"");
    }

    private static List<Clan> tidy(List<Clan> raw) {
        ArrayList out = new ArrayList();
        for (Clan c : raw) {
            if (c == null || c.id == null || "fwm".equals((Object)c.id)) continue;
            ArrayList tags = new ArrayList();
            if (c.tags != null) {
                for (String t : c.tags) {
                    if (t == null) continue;
                    tags.add((Object)TrustedClans.plain(t));
                }
            }
            ArrayList fakes = new ArrayList();
            if (c.fakeTags != null) {
                for (String t : c.fakeTags) {
                    if (t == null) continue;
                    fakes.add((Object)TrustedClans.plain(t));
                }
            }
            c.tags = tags;
            c.fakeTags = fakes;
            out.add((Object)c);
        }
        return List.copyOf((Collection)out);
    }

    static String plain(String tag) {
        return tag.trim().replaceAll("[^\\x00-\\x7F]", "").replace((CharSequence)"[", (CharSequence)"").replace((CharSequence)"]", (CharSequence)"").toUpperCase(Locale.ROOT);
    }

    public static List<Clan> all() {
        return clans;
    }

    public static Clan match(String tag) {
        if (tag == null) {
            return null;
        }
        String p = TrustedClans.plain(tag);
        String e = TrustedClans.exact(tag);
        for (Clan c : clans) {
            if ("fwm".equals((Object)c.id) || !c.tags.contains((Object)(c.exact ? e : p))) continue;
            return c;
        }
        return null;
    }

    public static boolean isFwmTag(String tag) {
        if (tag == null) {
            return false;
        }
        String p = TrustedClans.plain(tag);
        for (Clan c : dashClans) {
            if (!"fwm".equals((Object)c.id) || !c.tags.contains((Object)p)) continue;
            return true;
        }
        return false;
    }

    public static Clan fakeOf(String tag) {
        if (tag == null) {
            return null;
        }
        String p = TrustedClans.plain(tag);
        if (p.isEmpty()) {
            return null;
        }
        String e = TrustedClans.exact(tag);
        for (BotFakeTag f : botFakes) {
            if (!f.tag().equals((Object)e)) continue;
            return f.clan();
        }
        for (Clan c : dashClans) {
            if (!c.fakeTags.contains((Object)p)) continue;
            return c;
        }
        return null;
    }

    public static MutableComponent label(Clan clan, String wornTag) {
        String display = clan.display == null || clan.display.isBlank() ? "{tag}" : clan.display;
        return TrustedClans.styled(display, wornTag == null ? "" : wornTag, TrustedClans.gradient(clan.gradient), clan.id);
    }

    public static MutableComponent playerLabel(UUID uuid) {
        if (uuid == null) {
            return null;
        }
        String id = uuid.toString().replace((CharSequence)"-", (CharSequence)"");
        for (Player p : players) {
            if (!id.equals((Object)p.uuid) || p.display == null || p.display.isBlank()) continue;
            return TrustedClans.styled(p.display, "", null, "p_" + id);
        }
        return null;
    }

    private static MutableComponent styled(String display, String tag, int[] gradient, String logoId) {
        MutableComponent out = Component.empty();
        Style style = Style.EMPTY.withColor(ChatFormatting.WHITE);
        StringBuilder run = new StringBuilder();
        for (int i = 0; i < display.length(); ++i) {
            ChatFormatting f;
            char ch = display.charAt(i);
            if (ch == '&' && i + 7 < display.length() && display.charAt(i + 1) == '#' && display.substring(i + 2, Math.min((int)display.length(), (int)(i + 8))).matches("[0-9a-fA-F]{6}")) {
                TrustedClans.flush(out, run, style);
                style = style.withColor(Integer.parseInt((String)display.substring(i + 2, i + 8), (int)16));
                i += 7;
                continue;
            }
            if (ch == '&' && i + 1 < display.length() && (f = ChatFormatting.getByCode((char)Character.toLowerCase((char)display.charAt(i + 1)))) != null) {
                TrustedClans.flush(out, run, style);
                style = f == ChatFormatting.RESET ? Style.EMPTY.withColor(ChatFormatting.WHITE) : ("0123456789abcdef".indexOf((int)Character.toLowerCase((char)display.charAt(i + 1))) >= 0 ? Style.EMPTY.withColor(f) : style.applyFormat(f));
                ++i;
                continue;
            }
            if (display.startsWith("{tag}", i)) {
                TrustedClans.flush(out, run, style);
                if (gradient != null) {
                    int n = tag.length();
                    for (int k = 0; k < n; ++k) {
                        float t = n == 1 ? 0.0f : (float)k / (float)(n - 1);
                        out.append((Component)Component.literal((String)String.valueOf((char)tag.charAt(k))).setStyle(Style.EMPTY.withColor(TrustedClans.mix(gradient[0], gradient[1], t)).withBold(Boolean.valueOf((boolean)true))));
                    }
                } else {
                    out.append((Component)Component.literal((String)tag).setStyle(style));
                }
                i += 4;
                continue;
            }
            run.append(ch);
        }
        TrustedClans.flush(out, run, style);
        Identifier font = ClanLogos.font(logoId);
        if (font != null) {
            out.append((Component)Component.literal((String)" "));
            out.append((Component)Component.literal((String)"\ue000").setStyle(Style.EMPTY.withFont((FontDescription)new FontDescription.Resource(font)).withColor(ChatFormatting.WHITE)));
        }
        return out;
    }

    private static void flush(MutableComponent out, StringBuilder run, Style style) {
        if (run.length() == 0) {
            return;
        }
        out.append((Component)Component.literal((String)run.toString()).setStyle(style));
        run.setLength(0);
    }

    private static int[] gradient(String spec) {
        if (spec == null || !spec.matches("#[0-9a-fA-F]{6},#[0-9a-fA-F]{6}")) {
            return null;
        }
        return new int[]{Integer.parseInt((String)spec.substring(1, 7), (int)16), Integer.parseInt((String)spec.substring(9, 15), (int)16)};
    }

    private static int mix(int a, int b, float t) {
        int r = Math.round((float)((float)(a >> 16 & 0xFF) * (1.0f - t) + (float)(b >> 16 & 0xFF) * t));
        int g = Math.round((float)((float)(a >> 8 & 0xFF) * (1.0f - t) + (float)(b >> 8 & 0xFF) * t));
        int bl = Math.round((float)((float)(a & 0xFF) * (1.0f - t) + (float)(b & 0xFF) * t));
        return r << 16 | g << 8 | bl;
    }

    public static String fakeWarning(Clan clan) {
        String what = !clan.tags.isEmpty() ? (String)clan.tags.get(0) : (clan.name == null ? clan.id : clan.name);
        return "ACHTUNG FAKE " + what.toUpperCase(Locale.ROOT);
    }

    static {
        botCache = new BotCache();
    }

    public static final class Clan {
        public String id;
        public String name;
        public String display;
        public String gradient;
        public List<String> tags = new ArrayList();
        public List<String> fakeTags = new ArrayList();
        public String logo;
        public long logoVersion;
        public transient boolean exact;
        public transient String iconUrl;
        public transient String iconEtag;
    }

    static final class Data {
        List<Clan> clans = new ArrayList();
        List<Player> players = new ArrayList();

        Data() {
        }
    }

    static final class BotCache {
        BotList list;
        String listEtag;
        BotFakes fakes;
        String fakesEtag;

        BotCache() {
        }
    }

    static final class BotList {
        List<BotClan> clans = new ArrayList();

        BotList() {
        }
    }

    static final class BotClan {
        String groupId;
        String displayName;
        String name;
        String tag;
        String type;
        String iconUrl;
        String iconEtag;
        String nameColor;

        BotClan() {
        }
    }

    static final class BotFakes {
        List<BotFake> tags = new ArrayList();

        BotFakes() {
        }
    }

    static final class BotFake {
        String groupId;
        String trustedClan;
        String tag;

        BotFake() {
        }
    }

    private record BotFakeTag(String tag, Clan clan) {
    }

    public static final class Player {
        public String uuid;
        public String name;
        public String display;
        public String logo;
        public long logoVersion;
    }
}
