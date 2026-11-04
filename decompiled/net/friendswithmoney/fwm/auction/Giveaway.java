/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonElement
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.concurrent.ThreadLocalRandom
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.auction;

import com.google.gson.JsonElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.friendswithmoney.fwm.auction.StreamerStore;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public final class Giveaway {
    private static final String FILE = "fwm-giveaway-state.json";
    private static final long AFTERGLOW_MS = 60000L;
    public static final long DRAW_MS = 4500L;
    private static ItemStack item = ItemStack.EMPTY;
    private static String word = "";
    private static int limit;
    private static long durationMs;
    private static long startedAt;
    private static long pausedAt;
    private static long pausedTotal;
    private static Status status;
    private static final List<Participant> participants;
    private static String winner;
    private static long drawnAt;
    private static boolean restored;

    private Giveaway() {
    }

    public static void hold(ItemStack stack) {
        if (status == Status.RUNNING || status == Status.PAUSED) {
            return;
        }
        item = stack == null ? ItemStack.EMPTY : stack.copy();
    }

    public static void start(ItemStack stack, String joinWord, int maxParticipants, int seconds) {
        item = stack == null ? ItemStack.EMPTY : stack.copy();
        word = joinWord == null ? "" : joinWord.trim();
        limit = Math.max((int)1, (int)maxParticipants);
        durationMs = (long)Math.max((int)1, (int)seconds) * 1000L;
        startedAt = System.currentTimeMillis();
        pausedAt = 0L;
        pausedTotal = 0L;
        participants.clear();
        winner = null;
        drawnAt = 0L;
        status = Status.RUNNING;
        Giveaway.save();
    }

    public static void pause() {
        if (status != Status.RUNNING) {
            return;
        }
        status = Status.PAUSED;
        pausedAt = System.currentTimeMillis();
        Giveaway.save();
    }

    public static void resume() {
        if (status != Status.PAUSED) {
            return;
        }
        pausedTotal += System.currentTimeMillis() - pausedAt;
        pausedAt = 0L;
        status = Status.RUNNING;
        Giveaway.save();
    }

    public static void draw() {
        if (status != Status.RUNNING && status != Status.PAUSED) {
            return;
        }
        if (status == Status.PAUSED) {
            Giveaway.resume();
        }
        status = Status.ENDED;
        drawnAt = System.currentTimeMillis();
        winner = participants.isEmpty() ? null : ((Participant)((Object)participants.get(ThreadLocalRandom.current().nextInt(participants.size())))).name();
        Giveaway.notify(winner == null ? "Giveaway beendet \u2013 es gab keine Teilnehmer." : "Gewinner: " + winner + " (" + participants.size() + " Teilnehmer). \u00dcbergib das Item manuell.");
        Giveaway.save();
    }

    public static void cancel() {
        status = Status.NONE;
        participants.clear();
        winner = null;
        drawnAt = 0L;
        Giveaway.save();
    }

    public static void reset() {
        Giveaway.cancel();
    }

    public static Status status() {
        return status;
    }

    public static boolean active() {
        return status == Status.RUNNING || status == Status.PAUSED;
    }

    public static ItemStack item() {
        return item;
    }

    public static String word() {
        return word;
    }

    public static int limit() {
        return limit;
    }

    public static List<Participant> participants() {
        return new ArrayList(participants);
    }

    public static String winner() {
        return winner;
    }

    public static long drawnAt() {
        return drawnAt;
    }

    public static boolean drawing() {
        return status == Status.ENDED && winner != null && System.currentTimeMillis() - drawnAt < 4500L;
    }

    public static long remainingMs() {
        long now = status == Status.PAUSED ? pausedAt : System.currentTimeMillis();
        return Math.max((long)0L, (long)(startedAt + pausedTotal + durationMs - now));
    }

    public static float progress() {
        if (durationMs <= 0L) {
            return 0.0f;
        }
        return Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)((float)Giveaway.remainingMs() / (float)durationMs)));
    }

    public static boolean visible() {
        return Giveaway.active() || status == Status.ENDED && System.currentTimeMillis() < drawnAt + 60000L;
    }

    public static String joinCommand() {
        return "/msg " + FwmGfx.playerName() + " " + word;
    }

    public static void accept(Component message) {
        if (status != Status.RUNNING || message == null || word.isEmpty()) {
            return;
        }
        Messages.Parsed dm = Messages.parse(message);
        if (dm == null || dm.sent()) {
            return;
        }
        String from = dm.partner();
        String text = dm.text();
        if (!text.trim().toLowerCase(Locale.ROOT).equals((Object)word.toLowerCase(Locale.ROOT))) {
            return;
        }
        for (Participant p : participants) {
            if (!p.name().equalsIgnoreCase(from)) continue;
            return;
        }
        participants.add((Object)new Participant(from, System.currentTimeMillis()));
        Giveaway.save();
    }

    private static void notify(String text) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        client.player.sendSystemMessage((Component)Component.literal((String)"[Giveaway] ").withStyle(ChatFormatting.LIGHT_PURPLE).append((Component)Component.literal((String)text).withStyle(ChatFormatting.WHITE)));
    }

    public static void tick(Minecraft client) {
        if (client.level == null || client.player == null) {
            return;
        }
        if (!restored) {
            restored = true;
            Giveaway.load();
        }
        if (status == Status.RUNNING && Giveaway.remainingMs() <= 0L) {
            Giveaway.draw();
        }
    }

    private static void save() {
        if (status == Status.NONE || item.isEmpty()) {
            StreamerStore.save(FILE, null);
            return;
        }
        State state = new State();
        state.item = StreamerStore.encode(item);
        if (state.item == null) {
            return;
        }
        state.word = word;
        state.limit = limit;
        state.durationMs = durationMs;
        state.startedAt = startedAt;
        state.pausedAt = pausedAt;
        state.pausedTotal = pausedTotal;
        state.status = status;
        state.participants = new ArrayList(participants);
        state.winner = winner;
        state.drawnAt = drawnAt;
        StreamerStore.save(FILE, state);
    }

    private static void load() {
        State state = StreamerStore.load(FILE, State.class);
        if (state == null || state.item == null || state.status == null || state.status == Status.NONE) {
            return;
        }
        if (state.status == Status.ENDED && System.currentTimeMillis() > state.drawnAt + 86400000L) {
            StreamerStore.save(FILE, null);
            return;
        }
        ItemStack stack = StreamerStore.decode(state.item);
        if (stack.isEmpty()) {
            return;
        }
        item = stack;
        word = state.word == null ? "" : state.word;
        limit = state.limit;
        durationMs = state.durationMs;
        startedAt = state.startedAt;
        pausedAt = state.pausedAt;
        pausedTotal = state.pausedTotal;
        status = state.status;
        participants.clear();
        if (state.participants != null) {
            participants.addAll(state.participants);
        }
        winner = state.winner;
        drawnAt = state.drawnAt;
    }

    static {
        status = Status.NONE;
        participants = new ArrayList();
    }

    public static enum Status {
        NONE,
        RUNNING,
        PAUSED,
        ENDED;

    }

    public record Participant(String name, long joinedAt) {
    }

    private static class State {
        JsonElement item;
        String word;
        int limit;
        long durationMs;
        long startedAt;
        long pausedAt;
        long pausedTotal;
        Status status;
        List<Participant> participants;
        String winner;
        long drawnAt;

        private State() {
        }
    }
}
