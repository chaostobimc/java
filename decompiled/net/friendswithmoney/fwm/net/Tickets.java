/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Comparator
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  java.util.concurrent.CompletableFuture
 *  java.util.concurrent.ConcurrentHashMap
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.net;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.ModAccount;
import net.friendswithmoney.fwm.ui.FwmNotice;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class Tickets {
    private static final long POLL_MS = 60000L;
    private static volatile List<Spawner> spawners = List.of();
    private static volatile String spawnerError = "";
    private static volatile List<Ticket> tickets = List.of();
    private static volatile long nextPoll;
    private static volatile long pausedUntil;
    private static final Map<String, String> SEEN;
    private static final Map<String, List<Message>> MESSAGES;
    private static final Map<String, String> AFTER;
    private static final Set<String> LOADING;
    private static volatile String watched;
    private static long nextWatch;
    private static final long WATCH_MS = 15000L;

    private Tickets() {
    }

    public static List<Spawner> spawners() {
        return spawners;
    }

    public static String spawnerError() {
        return spawnerError;
    }

    public static List<Ticket> tickets() {
        return tickets;
    }

    public static List<Message> messages(String ticketId) {
        return (List)MESSAGES.getOrDefault((Object)ticketId, (Object)List.of());
    }

    public static boolean loading(String ticketId) {
        return LOADING.contains((Object)ticketId);
    }

    public static void watch(String ticketId) {
        watched = ticketId;
        nextWatch = 0L;
    }

    public static void loadMessages(String ticketId, boolean quiet) {
        if (ticketId == null || !ModAccount.hasKey() || System.currentTimeMillis() < pausedUntil || !LOADING.add((Object)ticketId)) {
            return;
        }
        String after = (String)AFTER.get((Object)ticketId);
        String path = "/mod/v1/tickets/" + ticketId + "/messages?limit=50" + (after == null ? "" : "&after=" + after);
        ModAccount.call("GET", path, null).whenComplete((got, error) -> {
            LOADING.remove((Object)ticketId);
            if (error != null || got == null) {
                return;
            }
            Tickets.pause(got);
            MessagePage page = ModAccount.parse(got, MessagePage.class);
            if (got.status() != 200 || page == null || page.messages == null) {
                return;
            }
            boolean first = !AFTER.containsKey((Object)ticketId);
            ArrayList all = new ArrayList((Collection)MESSAGES.getOrDefault((Object)ticketId, (Object)List.of()));
            HashSet have = new HashSet();
            for (Message m2 : all) {
                have.add((Object)m2.id);
            }
            ArrayList fresh = new ArrayList();
            for (Message m3 : page.messages) {
                if (m3.id == null || !have.add((Object)m3.id)) continue;
                fresh.add((Object)m3);
            }
            all.addAll((Collection)fresh);
            all.sort(Comparator.comparing(m -> m.createdAt == null ? "" : m.createdAt));
            MESSAGES.put((Object)ticketId, (Object)List.copyOf((Collection)all));
            if (page.pagination != null && page.pagination.nextAfter != null) {
                AFTER.put((Object)ticketId, (Object)page.pagination.nextAfter);
            } else if (first) {
                AFTER.put((Object)ticketId, (Object)(all.isEmpty() ? "0" : ((Message)all.get((int)(all.size() - 1))).id));
            }
            if (!(quiet || first || fresh.isEmpty() || ticketId.equals((Object)watched))) {
                Message last = (Message)fresh.get(fresh.size() - 1);
                String who = last.author == null ? "FWM" : (last.author.displayName != null ? last.author.displayName : last.author.username);
                Minecraft client = Minecraft.getInstance();
                client.execute(() -> FwmNotice.show(Lang.t("NEUE NACHRICHT IM TICKET", "NEW TICKET MESSAGE"), who, last.content == null ? "" : last.content, 6088852, 10000L));
            }
        });
    }

    public static boolean hasLive(String type) {
        for (Ticket t : tickets) {
            if (!type.equals((Object)t.type) || !t.live()) continue;
            return true;
        }
        return false;
    }

    public static void loadSpawners() {
        if (!ModAccount.hasKey() || System.currentTimeMillis() < pausedUntil) {
            return;
        }
        ModAccount.call("GET", "/mod/v1/spawners", null).whenComplete((got, error) -> {
            if (error != null || got == null) {
                spawnerError = "Keine Verbindung.";
                return;
            }
            Tickets.pause(got);
            SpawnerList list = ModAccount.parse(got, SpawnerList.class);
            if (got.status() == 200 && list != null && list.spawners != null) {
                spawners = List.copyOf(list.spawners);
                spawnerError = "";
            } else {
                spawnerError = ModAccount.errorText(got);
            }
        });
    }

    public static void refresh() {
        if (!ModAccount.hasKey() || System.currentTimeMillis() < pausedUntil) {
            return;
        }
        nextPoll = System.currentTimeMillis() + 60000L;
        ModAccount.call("GET", "/mod/v1/tickets", null).whenComplete((got, error) -> {
            if (error != null || got == null) {
                return;
            }
            Tickets.pause(got);
            TicketList list = ModAccount.parse(got, TicketList.class);
            if (got.status() != 200 || list == null || list.tickets == null) {
                return;
            }
            Tickets.announce(list.tickets);
            tickets = List.copyOf(list.tickets);
            for (Ticket t : list.tickets) {
                if (!t.live() || t.id == null) continue;
                Tickets.loadMessages(t.id, !AFTER.containsKey((Object)t.id));
            }
        });
    }

    private static void pause(FwmApi.Fetched got) {
        if (got.status() == 429) {
            pausedUntil = System.currentTimeMillis() + Math.max((long)60L, (long)got.retryAfterSeconds()) * 1000L;
        }
    }

    public static CompletableFuture<String> openSpawner(boolean buy, Spawner spawner, int amount, String notes) {
        HashMap body = new HashMap();
        body.put((Object)"type", (Object)"spawner");
        body.put((Object)"action", (Object)(buy ? "ankauf" : "verkauf"));
        body.put((Object)"spawner", (Object)(spawner.id != null ? spawner.id : spawner.name));
        body.put((Object)"amount", (Object)amount);
        if (notes != null && !notes.isBlank()) {
            body.put((Object)"notes", (Object)notes.trim());
        }
        return Tickets.open((Map<String, Object>)body);
    }

    public static CompletableFuture<String> openMiddleman(String description) {
        HashMap body = new HashMap();
        body.put((Object)"type", (Object)"middleman");
        body.put((Object)"description", (Object)description.trim());
        return Tickets.open((Map<String, Object>)body);
    }

    private static CompletableFuture<String> open(Map<String, Object> body) {
        return ModAccount.call("POST", "/mod/v1/tickets", body).handle((got, error) -> {
            if (error != null || got == null) {
                return "Keine Verbindung zur FWM-API.";
            }
            Tickets.pause(got);
            if (got.status() == 201) {
                Tickets.refresh();
                return null;
            }
            return ModAccount.errorText(got);
        });
    }

    public static void tick(Minecraft client) {
        if (!ModAccount.hasKey()) {
            return;
        }
        long now = System.currentTimeMillis();
        if (watched != null && now >= nextWatch && now >= pausedUntil) {
            nextWatch = now + 15000L;
            Tickets.loadMessages(watched, true);
        }
        if (now < nextPoll || now < pausedUntil) {
            return;
        }
        boolean anyLive = false;
        for (Ticket t : tickets) {
            if (!t.live()) continue;
            anyLive = true;
        }
        if (!anyLive && nextPoll != 0L) {
            return;
        }
        Tickets.refresh();
    }

    private static void announce(List<Ticket> fresh) {
        boolean first = SEEN.isEmpty();
        for (Ticket t : fresh) {
            if (t.id == null) continue;
            String now = t.status + "/" + t.outcome;
            String before = (String)SEEN.put((Object)t.id, (Object)now);
            if (first || before == null || before.equals((Object)now)) continue;
            String what = "spawner".equals((Object)t.type) ? "Spawner-Ticket" : "Middleman-Ticket";
            String line = what + ": " + Tickets.statusText(t);
            Minecraft client = Minecraft.getInstance();
            client.execute(() -> {
                boolean good = "success".equals((Object)t.status) || "success".equals((Object)t.outcome);
                boolean bad = "failure".equals((Object)t.status) || "failure".equals((Object)t.outcome);
                FwmNotice.show(what.toUpperCase(Locale.ROOT), Tickets.statusText(t), "", good ? 6088852 : (bad ? 14709876 : 15911244), 10000L);
                if (client.gui != null) {
                    client.gui.hud.getChat().addClientSystemMessage((Component)Component.literal((String)"[FWM] ").withStyle(ChatFormatting.GREEN).append((Component)Component.literal((String)line).withStyle(ChatFormatting.GRAY)));
                }
            });
        }
    }

    /*
     * Exception decompiling
     */
    public static String statusText(Ticket t) {
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
        SEEN = new HashMap();
        MESSAGES = new ConcurrentHashMap();
        AFTER = new ConcurrentHashMap();
        LOADING = ConcurrentHashMap.newKeySet();
    }

    public static final class Ticket {
        public String id;
        public String type;
        public String status;
        public String outcome;
        public String claimedByMinecraftName;
        public String createdAt;
        public String updatedAt;
        public String closedAt;

        public boolean live() {
            return "open".equals((Object)this.status) || "claimed".equals((Object)this.status);
        }
    }

    public static final class Spawner {
        public String id;
        public String name;
        public boolean ankauf;
        public boolean verkauf;
        public String ankaufPreis;
        public String verkaufPreis;

        public boolean can(boolean buy) {
            String price;
            String string = price = buy ? this.ankaufPreis : this.verkaufPreis;
            return (buy ? this.ankauf : this.verkauf) && price != null && !price.isBlank() && !"0".equals((Object)price.trim());
        }
    }

    static final class TicketList {
        boolean ok;
        List<Ticket> tickets = new ArrayList();

        TicketList() {
        }
    }

    static final class SpawnerList {
        boolean ok;
        List<Spawner> spawners = new ArrayList();

        SpawnerList() {
        }
    }

    static final class MessagePage {
        boolean ok;
        List<Message> messages = new ArrayList();
        Pagination pagination;

        MessagePage() {
        }
    }

    public static final class Message {
        public String id;
        public Author author;
        public String content;
        public String createdAt;
        public String editedAt;
    }

    static final class Pagination {
        String nextAfter;
        boolean hasMore;

        Pagination() {
        }
    }

    public static final class Author {
        public String discordId;
        public String username;
        public String displayName;
    }

    static final class Created {
        boolean ok;
        String ticketId;
        String status;
        String type;

        Created() {
        }
    }
}
