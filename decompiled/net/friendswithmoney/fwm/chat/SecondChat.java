/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayDeque
 *  java.util.ArrayList
 *  java.util.Deque
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Optional
 *  java.util.regex.Pattern
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$RunCommand
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.chat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.ChatFilter;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.money.MoneyEvents;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class SecondChat {
    private static final int MAX = 50;
    public static final long FADE_MS = 10000L;
    private static final Deque<Line> lines = new ArrayDeque();
    private static final Map<String, Pattern> PATTERNS = new HashMap();

    private SecondChat() {
    }

    public static boolean accept(Component message) {
        if (message == null || !Features.isOn("fwm.tab.secondchat")) {
            return true;
        }
        String line = message.getString();
        if (line == null || line.isEmpty()) {
            return true;
        }
        if (!ServerLine.fromServer(message) && !ServerLine.tagged(message, "[Nachricht]")) {
            return true;
        }
        if (!SecondChat.matches(message, line) && !SecondChat.offersTeleport(message)) {
            return true;
        }
        lines.addLast((Object)new Line(message, System.currentTimeMillis()));
        while (lines.size() > 50) {
            lines.removeFirst();
        }
        return !FwmConfig.INSTANCE.secondChatRemove;
    }

    public static void acceptActionBar(Component message) {
        if (message == null || !Features.isOn("fwm.tab.secondchat") || !FwmConfig.INSTANCE.secondChatMarket) {
            return;
        }
        String plain = ServerLine.plain(message).trim();
        if (!MoneyEvents.AH_SOLD.matcher((CharSequence)plain).find() && !MoneyEvents.ORDER_FILLED.matcher((CharSequence)plain).find()) {
            return;
        }
        lines.addLast((Object)new Line(message, System.currentTimeMillis()));
        while (lines.size() > 50) {
            lines.removeFirst();
        }
    }

    private static boolean matches(Component message, String line) {
        FwmConfig config = FwmConfig.INSTANCE;
        String plain = ServerLine.plain(message).stripLeading();
        if (config.secondChatTpa && SecondChat.isTpa(message, plain)) {
            return true;
        }
        if (config.secondChatClan && SecondChat.isClan(plain)) {
            return true;
        }
        if (config.secondChatMsg && SecondChat.isMessage(message, plain)) {
            return true;
        }
        if (SecondChat.fromServer(message, plain)) {
            if (MoneyEvents.isMarketLine(plain)) {
                return config.secondChatMarket;
            }
            boolean payment = SecondChat.isPayment(plain);
            if (config.secondChatPay && payment) {
                return true;
            }
            if (config.secondChatHugoOther && !payment) {
                return true;
            }
        }
        return SecondChat.isCustom(line);
    }

    private static boolean isClan(String plain) {
        return plain.startsWith("[Clan]");
    }

    private static boolean isCustom(String line) {
        List<ChatFilter> filters = FwmConfig.INSTANCE.secondChatFilters;
        if (filters == null) {
            return false;
        }
        for (ChatFilter filter : filters) {
            if (filter == null || !filter.matches(line)) continue;
            return true;
        }
        return false;
    }

    private static boolean isTpa(Component message, String plain) {
        if (!SecondChat.fromServer(message, plain)) {
            return false;
        }
        return plain.contains((CharSequence)"Teleportations-Anfrage") || plain.contains((CharSequence)"Teleport-Anfrage");
    }

    private static boolean offersTeleport(Component message) {
        if (!FwmConfig.INSTANCE.secondChatTpa || !ServerLine.fromServer(message)) {
            return false;
        }
        return message.visit((style, part) -> {
            ClickEvent patt0$temp = style.getClickEvent();
            if (!(patt0$temp instanceof ClickEvent.RunCommand)) {
                return Optional.empty();
            }
            ClickEvent.RunCommand run = (ClickEvent.RunCommand)patt0$temp;
            String command = run.command();
            return command.contains((CharSequence)"tpaccept") || command.contains((CharSequence)"tpdeny") ? Optional.of((Object)Boolean.TRUE) : Optional.empty();
        }, Style.EMPTY).isPresent();
    }

    private static String prefix() {
        String prefix = FwmConfig.INSTANCE.serverPrefix;
        return prefix == null || prefix.isBlank() ? "[HugoSMP]" : prefix.trim();
    }

    private static boolean fromServer(Component message, String plain) {
        return ServerLine.fromServer(message);
    }

    private static boolean isPayment(String plain) {
        FwmConfig config = FwmConfig.INSTANCE;
        for (String source : config.payPatterns) {
            if (!SecondChat.find(source, plain)) continue;
            return true;
        }
        for (String source : config.moneyPayOutPatterns) {
            if (!SecondChat.find(source, plain)) continue;
            return true;
        }
        return SecondChat.find(config.secondChatPayPattern, plain);
    }

    private static boolean find(String source, String line) {
        if (source == null || source.isBlank()) {
            return false;
        }
        Pattern pattern = (Pattern)PATTERNS.computeIfAbsent((Object)source, s -> {
            try {
                return Pattern.compile((String)s, (int)2);
            }
            catch (Exception e) {
                return Pattern.compile((String)"(?!)");
            }
        });
        return pattern.matcher((CharSequence)line).find();
    }

    private static boolean isMessage(Component message, String plain) {
        return Messages.parse(message) != null;
    }

    public static List<Line> lines() {
        return new ArrayList(lines);
    }

    public static void clear() {
        lines.clear();
    }

    public record Line(Component text, long at) {
    }
}
