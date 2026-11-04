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
 *  java.util.Comparator
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.chat;

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
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.chat.Conversation;
import net.friendswithmoney.fwm.chat.PrivateMessage;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class Messages {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final long SAVE_INTERVAL_MS = 15000L;
    private static final List<Conversation> conversations = new ArrayList();
    private static final long DUPLICATE_MS = 250L;
    private static Pattern pattern;
    private static String patternSource;
    private static boolean dirty;
    private static long lastSave;
    private static String lastKey;
    private static long lastKeyAt;
    private static final Pattern STRICT;

    private Messages() {
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-messages.json");
    }

    public static void load() {
        Path path = Messages.file();
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            return;
        }
        try {
            String json = Files.readString((Path)path, (Charset)StandardCharsets.UTF_8);
            List loaded = (List)GSON.fromJson(json, new TypeToken<List<Conversation>>(){}.getType());
            conversations.clear();
            if (loaded == null) {
                return;
            }
            for (Conversation conversation : loaded) {
                if (conversation == null || conversation.partner == null) continue;
                if (conversation.messages == null) {
                    conversation.messages = new ArrayList();
                }
                conversations.add((Object)conversation);
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    public static void tick(Minecraft client) {
        if (!dirty) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastSave < 15000L) {
            return;
        }
        Messages.save();
    }

    public static void save() {
        dirty = false;
        lastSave = System.currentTimeMillis();
        Path path = Messages.file();
        try {
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson(conversations), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    public static boolean isHidden(String partner) {
        if (partner == null) {
            return false;
        }
        for (String name : FwmConfig.INSTANCE.msgHidden) {
            if (!name.equalsIgnoreCase(partner)) continue;
            return true;
        }
        return false;
    }

    public static void hide(String partner) {
        if (partner == null || partner.isBlank() || Messages.isHidden(partner)) {
            return;
        }
        FwmConfig.INSTANCE.msgHidden.add((Object)partner);
        ConfigManager.save();
    }

    public static void unhide(String partner) {
        if (FwmConfig.INSTANCE.msgHidden.removeIf(n -> n.equalsIgnoreCase(partner))) {
            ConfigManager.save();
        }
    }

    public static List<Conversation> conversations() {
        ArrayList out = new ArrayList(conversations);
        out.removeIf(c -> Messages.isHidden(c.partner));
        out.sort(Comparator.comparingLong(c -> c.lastActivity).reversed());
        return out;
    }

    public static Conversation find(String partner) {
        for (Conversation conversation : conversations) {
            if (!conversation.partner.equalsIgnoreCase(partner)) continue;
            return conversation;
        }
        return null;
    }

    public static int unreadTotal() {
        int total = 0;
        for (Conversation conversation : conversations) {
            if (Messages.isHidden(conversation.partner)) continue;
            total += conversation.unread;
        }
        return total;
    }

    public static void markRead(Conversation conversation) {
        if (conversation == null || conversation.unread == 0) {
            return;
        }
        conversation.unread = 0;
        dirty = true;
    }

    public static void forget(String partner) {
        if (partner == null) {
            return;
        }
        boolean removed = conversations.removeIf(conversation -> partner.equalsIgnoreCase(conversation.partner));
        if (removed) {
            dirty = true;
            Messages.save();
        }
    }

    public static void markAllRead() {
        boolean changed = false;
        for (Conversation conversation : conversations) {
            if (conversation.unread == 0) continue;
            conversation.unread = 0;
            changed = true;
        }
        if (changed) {
            dirty = true;
        }
    }

    public static boolean isIgnored(String partner) {
        if (partner == null) {
            return false;
        }
        for (String name : FwmConfig.INSTANCE.msgIgnored) {
            if (!partner.equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }

    public static void ignore(String partner) {
        if (partner == null || partner.isBlank() || Messages.isIgnored(partner)) {
            return;
        }
        FwmConfig.INSTANCE.msgIgnored.add((Object)partner);
        ConfigManager.save();
        Messages.send(FwmConfig.INSTANCE.msgIgnoreCommand, partner, "");
        Conversation conversation = Messages.find(partner);
        if (conversation != null) {
            Messages.markRead(conversation);
        }
    }

    public static void unignore(String partner) {
        if (partner == null) {
            return;
        }
        boolean removed = FwmConfig.INSTANCE.msgIgnored.removeIf(arg_0 -> ((String)partner).equalsIgnoreCase(arg_0));
        if (!removed) {
            return;
        }
        ConfigManager.save();
        Messages.send(FwmConfig.INSTANCE.msgIgnoreCommand, partner, "");
    }

    public static void send(String partner, String text) {
        if (text == null || text.isBlank()) {
            return;
        }
        Messages.send(FwmConfig.INSTANCE.msgCommand, partner, text);
    }

    private static void send(String rawCommand, String partner, String text) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        if (partner == null || partner.isBlank() || rawCommand == null) {
            return;
        }
        String command = rawCommand.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        if (command.isEmpty()) {
            return;
        }
        String line = command + " " + partner;
        if (!text.isBlank()) {
            line = line + " " + text.trim();
        }
        client.player.connection.sendCommand(line);
    }

    public static void accept(Component message) {
        if (message == null || !Features.isOn("fwm.tab.messages")) {
            return;
        }
        Parsed dm = Messages.parse(message);
        if (dm == null) {
            return;
        }
        Messages.record(dm.partner(), dm.text(), dm.sent());
    }

    public static Parsed parse(Component message) {
        if (message == null || !ServerLine.tagged(message, "[Nachricht]")) {
            return null;
        }
        Matcher matcher = STRICT.matcher((CharSequence)ServerLine.plain(message));
        if (!matcher.matches()) {
            return null;
        }
        if (matcher.group(1) != null) {
            return new Parsed(matcher.group(1), matcher.group(3), true);
        }
        return new Parsed(matcher.group(2), matcher.group(3), false);
    }

    public static boolean involvesSelf(String from, String to) {
        return Messages.isSelf(from) != Messages.isSelf(to);
    }

    public static boolean isSelf(String name) {
        if (name == null || name.isBlank()) {
            return false;
        }
        for (String self : FwmConfig.INSTANCE.msgSelfNames) {
            if (!name.equalsIgnoreCase(self)) continue;
            return true;
        }
        return name.equalsIgnoreCase(FwmGfx.playerName());
    }

    private static void record(String partner, String text, boolean sent) {
        if (partner == null || partner.isBlank() || text == null || text.isBlank()) {
            return;
        }
        String key = (sent ? ">" : "<") + partner + " " + text;
        long now = System.currentTimeMillis();
        if (key.equals((Object)lastKey) && now - lastKeyAt < 250L) {
            return;
        }
        lastKey = key;
        lastKeyAt = now;
        Conversation conversation = Messages.find(partner);
        if (conversation == null) {
            conversation = new Conversation(partner);
            conversations.add((Object)conversation);
        }
        boolean muted = Messages.isIgnored(partner) || Messages.isHidden(partner);
        conversation.add(new PrivateMessage(sent, text, System.currentTimeMillis()), FwmConfig.INSTANCE.msgHistoryPerContact, !muted);
        Messages.prune();
        dirty = true;
        if (!sent && !muted) {
            FwmHud.showMessage(partner, text);
        }
    }

    private static void prune() {
        int limit = FwmConfig.INSTANCE.msgContactLimit;
        while (conversations.size() > limit) {
            Conversation oldest = (Conversation)conversations.get(0);
            for (Conversation conversation : conversations) {
                if (conversation.lastActivity >= oldest.lastActivity) continue;
                oldest = conversation;
            }
            conversations.remove((Object)oldest);
        }
    }

    public static Pattern pattern() {
        String source = FwmConfig.INSTANCE.msgPattern;
        if (pattern == null || !source.equals((Object)patternSource)) {
            try {
                pattern = Pattern.compile((String)source);
            }
            catch (Exception e) {
                pattern = Pattern.compile((String)"([.*]?[A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$");
            }
            patternSource = source;
        }
        return pattern;
    }

    static {
        STRICT = Pattern.compile((String)"^\\[Nachricht\\] (?:Du -> ([.*]?[A-Za-z0-9_]{2,16})|([.*]?[A-Za-z0-9_]{2,16}) -> Du): (.+)$");
    }

    public record Parsed(String partner, String text, boolean sent) {
    }
}
