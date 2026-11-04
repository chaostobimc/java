/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashMap
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.auction;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import net.friendswithmoney.fwm.auction.Payments;
import net.friendswithmoney.fwm.auction.StreamerStore;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class Auction {
    private static final int HISTORY = 6;
    private static final long AFTERGLOW_MS = 60000L;
    private static final long LATE_MS = 600000L;
    private static final String FILE = "fwm-auction-state.json";
    private static ItemStack item = ItemStack.EMPTY;
    private static long startBid;
    private static long minStep;
    private static long endsAt;
    private static long durationMs;
    private static final List<Bid> bids;
    private static final List<Bid> late;
    private static final Map<String, Long> paidBy;
    private static boolean missing;
    private static long extendedAt;
    private static int extendedBy;
    private static boolean restored;
    private static int ticks;

    private Auction() {
    }

    public static void start(ItemStack stack, int seconds, long startBidCents, long minStepCents) {
        item = stack == null ? ItemStack.EMPTY : stack.copy();
        startBid = Math.max((long)0L, (long)startBidCents);
        minStep = Math.max((long)0L, (long)minStepCents);
        durationMs = (long)Math.max((int)1, (int)seconds) * 1000L;
        endsAt = System.currentTimeMillis() + durationMs;
        bids.clear();
        late.clear();
        paidBy.clear();
        missing = false;
        Auction.save();
    }

    public static void hold(ItemStack stack) {
        if (Auction.running()) {
            return;
        }
        Auction.clearResult();
        item = stack == null ? ItemStack.EMPTY : stack.copy();
    }

    public static void stop() {
        if (endsAt > System.currentTimeMillis()) {
            endsAt = System.currentTimeMillis();
        }
        Auction.save();
    }

    public static void clearResult() {
        endsAt = 0L;
        bids.clear();
        late.clear();
        paidBy.clear();
        missing = false;
        Auction.save();
    }

    public static void clear() {
        Auction.clearResult();
        item = ItemStack.EMPTY;
    }

    public static boolean running() {
        return !item.isEmpty() && System.currentTimeMillis() < endsAt;
    }

    public static boolean hasResult() {
        return !item.isEmpty() && endsAt > 0L && System.currentTimeMillis() >= endsAt;
    }

    public static boolean visible() {
        return !item.isEmpty() && endsAt > 0L && System.currentTimeMillis() < endsAt + 60000L;
    }

    public static ItemStack item() {
        return item;
    }

    public static long remainingMs() {
        return Math.max((long)0L, (long)(endsAt - System.currentTimeMillis()));
    }

    public static float progress() {
        if (durationMs <= 0L) {
            return 0.0f;
        }
        return Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)((float)Auction.remainingMs() / (float)durationMs)));
    }

    public static String leader() {
        Bid top = Auction.top();
        return top == null ? null : top.name();
    }

    public static long price() {
        Bid top = Auction.top();
        return top == null ? startBid : top.amount();
    }

    private static Bid top() {
        List<Bid> ranking = Auction.ranking();
        if (ranking.isEmpty() || ((Bid)((Object)ranking.get(0))).amount() < startBid) {
            return null;
        }
        return (Bid)((Object)ranking.get(0));
    }

    public static List<Bid> ranking() {
        ArrayList out = new ArrayList();
        if (FwmConfig.INSTANCE.auctionStackPayments) {
            HashMap at = new HashMap();
            for (Bid paid : bids) {
                String key = paid.name().toLowerCase(Locale.ROOT);
                Integer index = (Integer)at.get((Object)key);
                if (index == null) {
                    at.put((Object)key, (Object)out.size());
                    out.add((Object)paid);
                    continue;
                }
                Bid sum = (Bid)((Object)out.get(index.intValue()));
                out.set(index.intValue(), (Object)new Bid(sum.name(), sum.amount() + paid.amount(), paid.time()));
            }
        } else {
            out.addAll(bids);
        }
        out.sort((a, b) -> a.amount() != b.amount() ? Long.compare((long)b.amount(), (long)a.amount()) : Long.compare((long)a.time(), (long)b.time()));
        return out;
    }

    public static List<Bid> late() {
        return new ArrayList(late);
    }

    public static long startBid() {
        return startBid;
    }

    public static long minStep() {
        return minStep;
    }

    public static long nextMinimum() {
        if (Auction.top() == null) {
            return startBid;
        }
        return Auction.price() + Math.max((long)1L, (long)minStep);
    }

    public static List<Bid> bids() {
        List<Bid> ranking = Auction.ranking();
        return new ArrayList((Collection)ranking.subList(0, Math.min((int)6, (int)ranking.size())));
    }

    public static List<Bid> allBids() {
        return Auction.ranking();
    }

    public static int bidCount() {
        return bids.size();
    }

    public static boolean itemMissing() {
        return missing;
    }

    public static long earnings() {
        long sum = 0L;
        Iterator iterator = paidBy.values().iterator();
        while (iterator.hasNext()) {
            long paid = (Long)iterator.next();
            sum += paid;
        }
        for (Bid paid : late) {
            sum += paid.amount();
        }
        return sum;
    }

    public static String plain(long cents) {
        return cents % 100L == 0L ? String.valueOf((long)(cents / 100L)) : String.format((Locale)Locale.ROOT, (String)"%.2f", (Object[])new Object[]{(double)cents / 100.0});
    }

    public static void accept(Component message) {
        boolean newHigh;
        boolean live;
        if (message == null || item.isEmpty() || endsAt <= 0L) {
            return;
        }
        long now = System.currentTimeMillis();
        boolean bl = live = now < endsAt;
        if (!live && now > endsAt + 600000L) {
            return;
        }
        if (!ServerLine.fromServer(message)) {
            return;
        }
        Payments.Payment paid = Payments.read(ServerLine.plain(message), Auction.self());
        if (paid == null || paid.amount() <= 0L) {
            return;
        }
        if (!live) {
            late.add((Object)new Bid(paid.name(), paid.amount(), now));
            Auction.save();
            return;
        }
        String leaderBefore = Auction.leader();
        long priceBefore = Auction.price();
        paidBy.merge((Object)paid.name().toLowerCase(Locale.ROOT), (Object)paid.amount(), Long::sum);
        bids.add((Object)new Bid(paid.name(), paid.amount(), now));
        boolean bl2 = newHigh = Auction.leader() != null && (leaderBefore == null || Auction.price() > priceBefore);
        if (newHigh) {
            Auction.extendIfLate(now);
        }
        Auction.save();
    }

    static void extendIfLate(long now) {
        FwmConfig config = FwmConfig.INSTANCE;
        if (!config.auctionAntiSnipe || endsAt - now > 3000L) {
            return;
        }
        int seconds = Math.max((int)1, (int)config.auctionAntiSnipeSeconds);
        endsAt += (long)seconds * 1000L;
        durationMs += (long)seconds * 1000L;
        extendedAt = now;
        extendedBy = seconds;
    }

    public static int recentExtension() {
        return System.currentTimeMillis() - extendedAt < 2000L ? extendedBy : 0;
    }

    private static String self() {
        String name = FwmGfx.playerName();
        return name.isEmpty() ? null : name;
    }

    private static void notify(String text) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        client.player.sendSystemMessage((Component)Component.literal((String)"[Auktion] ").withStyle(ChatFormatting.GOLD).append((Component)Component.literal((String)text).withStyle(ChatFormatting.WHITE)));
    }

    public static void tick(Minecraft client) {
        boolean now;
        if (client.level == null || client.player == null) {
            return;
        }
        if (!restored) {
            restored = true;
            Auction.load();
        }
        if (!Auction.running() || ++ticks % 20 != 0) {
            return;
        }
        boolean bl = now = StreamerStore.carried(item) < item.getCount();
        if (now && !missing) {
            Auction.notify("Achtung: Das versteigerte Item fehlt im Inventar!");
        }
        missing = now;
    }

    private static void save() {
        if (item.isEmpty() || endsAt <= 0L) {
            StreamerStore.save(FILE, null);
            return;
        }
        State state = new State();
        state.item = StreamerStore.encode(item);
        if (state.item == null) {
            return;
        }
        state.startBid = startBid;
        state.minStep = minStep;
        state.endsAt = endsAt;
        state.durationMs = durationMs;
        state.payments = new ArrayList(bids);
        state.late = new ArrayList(late);
        state.paidBy = new HashMap(paidBy);
        StreamerStore.save(FILE, state);
    }

    private static void load() {
        State state = StreamerStore.load(FILE, State.class);
        if (state == null || state.item == null) {
            return;
        }
        if (System.currentTimeMillis() > state.endsAt + 86400000L) {
            StreamerStore.save(FILE, null);
            return;
        }
        ItemStack stack = StreamerStore.decode(state.item);
        if (stack.isEmpty()) {
            return;
        }
        item = stack;
        startBid = state.startBid;
        minStep = state.minStep;
        endsAt = state.endsAt;
        durationMs = state.durationMs;
        bids.clear();
        late.clear();
        paidBy.clear();
        if (state.payments != null) {
            bids.addAll(state.payments);
        } else if (state.bids != null) {
            bids.addAll(state.bids);
        }
        if (state.late != null) {
            late.addAll(state.late);
        }
        if (state.paidBy != null) {
            paidBy.putAll(state.paidBy);
        }
    }

    public static void start(ItemStack stack, int seconds, long startBidCents) {
        Auction.start(stack, seconds, startBidCents, 0L);
    }

    static {
        bids = new ArrayList();
        late = new ArrayList();
        paidBy = new HashMap();
    }

    public record Bid(String name, long amount, long time) {
    }

    private static class State {
        JsonElement item;
        long startBid;
        long minStep;
        long endsAt;
        long durationMs;
        List<Bid> bids;
        List<Bid> payments;
        List<Bid> late;
        Map<String, Long> paidBy;

        private State() {
        }
    }
}
