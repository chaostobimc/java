/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
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
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.LinkedHashSet
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 *  java.util.UUID
 *  java.util.concurrent.ConcurrentHashMap
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.ModId;
import net.friendswithmoney.fwm.net.Presence;
import net.friendswithmoney.fwm.ui.FwmNotice;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.Minecraft;

public final class Giveaways {
    private static final Gson GSON = new Gson();
    private static final long POLL_MS = 60000L;
    private static final long[] BACKOFF_MS = new long[]{120000L, 300000L, 600000L};
    private static volatile List<Active> active = List.of();
    private static volatile List<Recent> recent = List.of();
    private static volatile long clockOffset;
    private static volatile boolean loaded;
    private static long nextPoll;
    private static int failures;
    private static boolean running;
    private static Seen seen;
    private static final Map<String, String> JOIN_RESULT;
    private static final Set<String> JOINING;
    public static final long CARD_MS = 30000L;

    private Giveaways() {
    }

    public static List<Active> active() {
        return active;
    }

    public static List<Recent> recent() {
        return recent;
    }

    public static boolean loaded() {
        return loaded;
    }

    public static long now() {
        return System.currentTimeMillis() + clockOffset;
    }

    public static int open() {
        long now = Giveaways.now();
        int n = 0;
        for (Active a : active) {
            if (a.joined || a.endsAt <= now) continue;
            ++n;
        }
        return n;
    }

    public static String joinResult(String id) {
        return (String)JOIN_RESULT.get((Object)id);
    }

    public static boolean joining(String id) {
        return JOINING.contains((Object)id);
    }

    public static void tick(Minecraft client) {
        if (client.player == null || !Presence.onHugo(client)) {
            return;
        }
        long now = System.currentTimeMillis();
        if (running || now < nextPoll) {
            return;
        }
        Giveaways.poll(client.player.getUUID());
    }

    public static void refresh() {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || running) {
            return;
        }
        Giveaways.poll(client.player.getUUID());
    }

    private static void poll(UUID uuid) {
        running = true;
        long sent = System.currentTimeMillis();
        FwmApi.get("/api/mod/giveaways?uuid=" + uuid.toString().replace((CharSequence)"-", (CharSequence)""), Answer.class).whenComplete((answer, error) -> {
            running = false;
            long back = System.currentTimeMillis();
            if (error != null || answer == null) {
                nextPoll = back + BACKOFF_MS[Math.min((int)failures, (int)(BACKOFF_MS.length - 1))];
                ++failures;
                return;
            }
            failures = 0;
            nextPoll = back + 60000L;
            if (answer.now > 0L) {
                clockOffset = answer.now - (sent + back) / 2L;
            }
            List a = answer.active == null ? List.of() : List.copyOf(answer.active);
            List r = answer.recent == null ? List.of() : List.copyOf(answer.recent);
            active = a;
            recent = r;
            loaded = true;
            Giveaways.announce((List<Active>)a, (List<Recent>)r);
        });
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-giveaways.json");
    }

    private static Seen seen() {
        if (seen != null) {
            return seen;
        }
        try {
            if (Files.exists((Path)Giveaways.file(), (LinkOption[])new LinkOption[0])) {
                seen = (Seen)GSON.fromJson(Files.readString((Path)Giveaways.file(), (Charset)StandardCharsets.UTF_8), Seen.class);
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        if (seen == null) {
            seen = new Seen();
        }
        if (Giveaways.seen.announced == null) {
            Giveaways.seen.announced = new LinkedHashSet();
        }
        if (Giveaways.seen.results == null) {
            Giveaways.seen.results = new LinkedHashSet();
        }
        return seen;
    }

    private static void announce(List<Active> a, List<Recent> r) {
        Seen s = Giveaways.seen();
        ArrayList toasts = new ArrayList();
        for (Object g : a) {
            if (!s.announced.add((Object)((Active)g).id)) continue;
            toasts.add((Object)new Card(Lang.t("NEUES GIVEAWAY", "NEW GIVEAWAY"), ((Active)g).title, Lang.t("Preis: ", "Prize: ") + ((Active)g).prize + " \u00b7 " + Lang.t("Info (i) \u00f6ffnen zum Teilnehmen", "open Info (i) to join"), false));
        }
        for (Object g : r) {
            s.announced.add((Object)((Recent)g).id);
            if (!((Recent)g).joined || !s.results.add((Object)((Recent)g).id) || !"drawn".equals((Object)((Recent)g).status)) continue;
            toasts.add((Object)(((Recent)g).won ? new Card(Lang.t("DU HAST GEWONNEN!", "YOU WON!"), ((Recent)g).title, Lang.t("Melde dich bei FWM.", "Contact FWM."), true) : new Card(Lang.t("AUSGELOST", "DRAWN"), ((Recent)g).title, Lang.t("Gewinner: ", "Winner: ") + ((Recent)g).winnerName, false)));
        }
        Giveaways.trim(s.announced);
        Giveaways.trim(s.results);
        if (toasts.isEmpty()) {
            return;
        }
        try {
            Files.writeString((Path)Giveaways.file(), (CharSequence)GSON.toJson((Object)s), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
        for (Card c : toasts) {
            FwmNotice.show(c.head(), c.title(), c.line(), 15911244, 30000L);
        }
    }

    private static void trim(Set<String> set) {
        while (set.size() > 200) {
            set.remove(set.iterator().next());
        }
    }

    public static void join(String id) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || !JOINING.add((Object)id)) {
            return;
        }
        JoinBody body = new JoinBody();
        body.uuid = client.player.getUUID().toString().replace((CharSequence)"-", (CharSequence)"");
        body.name = client.player.getGameProfile().name();
        body.modId = ModId.get();
        FwmApi.post("/api/mod/giveaways/" + id + "/join", body, null, JoinAnswer.class).whenComplete((answer, error) -> {
            JOINING.remove((Object)id);
            String result = error != null || answer == null || answer.result == null ? "error" : answer.result;
            JOIN_RESULT.put((Object)id, (Object)result);
            if ("joined".equals((Object)result) || "already_joined".equals((Object)result)) {
                ArrayList now = new ArrayList(active);
                for (Active a : now) {
                    if (!a.id.equals((Object)id)) continue;
                    a.joined = true;
                    if (answer.participants <= 0) continue;
                    a.participants = answer.participants;
                }
                active = List.copyOf((Collection)now);
            }
        });
    }

    /*
     * Exception decompiling
     */
    public static String explain(String result) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1802596)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4489513)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4491475)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820062)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820183)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1024810)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4466968)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:604767)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4234497)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    static {
        JOIN_RESULT = new ConcurrentHashMap();
        JOINING = ConcurrentHashMap.newKeySet();
    }

    public static final class Active {
        public String id;
        public String title;
        public String prize;
        public String description;
        public long startsAt;
        public long endsAt;
        public int participants;
        public boolean joined;
    }

    static final class Answer {
        long now;
        List<Active> active;
        List<Recent> recent;

        Answer() {
        }
    }

    static final class Seen {
        Set<String> announced = new LinkedHashSet();
        Set<String> results = new LinkedHashSet();

        Seen() {
        }
    }

    public record Card(String head, String title, String line, boolean won) {
    }

    public static final class Recent {
        public String id;
        public String title;
        public String prize;
        public long endedAt;
        public String status;
        public String winnerName;
        public boolean won;
        public boolean joined;
    }

    static final class JoinBody {
        String uuid;
        String name;
        String modId;

        JoinBody() {
        }
    }

    static final class JoinAnswer {
        String result;
        int participants;

        JoinAnswer() {
        }
    }
}
