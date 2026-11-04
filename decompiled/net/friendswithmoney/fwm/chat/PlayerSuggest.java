/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.brigadier.CommandDispatcher
 *  com.mojang.brigadier.ParseResults
 *  com.mojang.brigadier.StringReader
 *  com.mojang.brigadier.suggestion.Suggestion
 *  com.mojang.brigadier.suggestion.Suggestions
 *  com.mojang.brigadier.tree.CommandNode
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.Locale
 *  java.util.concurrent.CompletableFuture
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.PlayerInfo
 */
package net.friendswithmoney.fwm.chat;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.suggestion.Suggestion;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.tree.CommandNode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;

public final class PlayerSuggest {
    private static final int NAME_MAX = 16;
    private static volatile List<String> results = List.of();
    private static volatile boolean waiting;
    private static String lastQuery;
    private static volatile CompletableFuture<Suggestions> pending;

    private PlayerSuggest() {
    }

    public static List<String> forQuery(String query) {
        String trimmed;
        String string = trimmed = query == null ? "" : query.trim();
        if (!trimmed.equals((Object)lastQuery)) {
            lastQuery = trimmed;
            PlayerSuggest.request(trimmed);
        }
        ArrayList out = new ArrayList(results);
        for (String name : PlayerSuggest.fromPlayerList(trimmed)) {
            if (PlayerSuggest.containsIgnoreCase((List<String>)out, name)) continue;
            out.add((Object)name);
        }
        return out;
    }

    public static boolean waiting() {
        return waiting && results.isEmpty();
    }

    public static void reset() {
        lastQuery = null;
        pending = null;
        waiting = false;
        results = List.of();
    }

    private static void request(String query) {
        results = List.of();
        waiting = false;
        Minecraft client = Minecraft.getInstance();
        ClientPacketListener handler = client.getConnection();
        if (handler == null || query.isEmpty()) {
            return;
        }
        String command = FwmConfig.INSTANCE.msgCommand.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        String line = "/" + command + " " + query;
        try {
            CompletableFuture future;
            CommandDispatcher dispatcher = handler.getCommands();
            StringReader reader = new StringReader(line);
            reader.setCursor(1);
            ParseResults parse = dispatcher.parse(reader, (Object)handler.getSuggestionsProvider());
            pending = future = dispatcher.getCompletionSuggestions(parse, line.length());
            waiting = true;
            if (FwmConfig.INSTANCE.msgDebug) {
                CommandNode node = handler.getCommands().getRoot().getChild(command);
                System.out.println("[FWM] asking \"" + line + "\" | node=" + String.valueOf((Object)node) + " | children=" + String.valueOf((Object)(node == null ? "-" : node.getChildren())));
            }
            future.thenAccept(suggestions -> PlayerSuggest.accept((CompletableFuture<Suggestions>)future, suggestions));
        }
        catch (Exception e) {
            results = List.of();
            waiting = false;
        }
    }

    private static boolean containsIgnoreCase(List<String> names, String name) {
        for (String known : names) {
            if (!known.equalsIgnoreCase(name)) continue;
            return true;
        }
        return false;
    }

    private static void accept(CompletableFuture<Suggestions> future, Suggestions suggestions) {
        if (future != pending) {
            return;
        }
        waiting = false;
        if (suggestions == null) {
            return;
        }
        String self = FwmGfx.playerName();
        ArrayList names = new ArrayList();
        for (Suggestion suggestion : suggestions.getList()) {
            String text = suggestion.getText();
            if (!PlayerSuggest.looksLikeName(text) || text.equalsIgnoreCase(self) || names.contains((Object)text)) continue;
            names.add((Object)text);
        }
        results = List.copyOf((Collection)names);
        if (FwmConfig.INSTANCE.msgDebug) {
            StringBuilder raw = new StringBuilder();
            for (Suggestion suggestion : suggestions.getList()) {
                raw.append(" [").append(suggestion.getText()).append(']');
            }
            System.out.println("[FWM] answer:" + String.valueOf((Object)(raw.length() == 0 ? " (none)" : raw)) + " | kept=" + String.valueOf(results));
        }
    }

    private static boolean looksLikeName(String text) {
        if (text == null || text.isEmpty() || text.length() > 16) {
            return false;
        }
        for (int i = 0; i < text.length(); ++i) {
            boolean allowed;
            char c = text.charAt(i);
            boolean bl = allowed = c == '_' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
            if (allowed) continue;
            return false;
        }
        return true;
    }

    private static List<String> fromPlayerList(String query) {
        Minecraft client = Minecraft.getInstance();
        ClientPacketListener handler = client.getConnection();
        if (handler == null || query.isEmpty()) {
            return List.of();
        }
        String self = FwmGfx.playerName();
        String lower = query.toLowerCase(Locale.ROOT);
        ArrayList names = new ArrayList();
        for (PlayerInfo entry : handler.getOnlinePlayers()) {
            String name;
            if (entry.getProfile() == null || (name = entry.getProfile().name()) == null || name.isBlank() || name.equalsIgnoreCase(self) || !name.toLowerCase(Locale.ROOT).contains((CharSequence)lower)) continue;
            names.add((Object)name);
        }
        names.sort(String.CASE_INSENSITIVE_ORDER);
        return names;
    }
}
