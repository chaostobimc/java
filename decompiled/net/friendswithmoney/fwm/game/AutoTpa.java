/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Locale
 *  java.util.Optional
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$RunCommand
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.game;

import java.util.Locale;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class AutoTpa {
    private static final Pattern REQUEST = Pattern.compile((String)"(?<name>[.*]?[A-Za-z0-9_]{2,16})\\s+hat\\s+(?:dir\\s+eine\\s+Teleportations-Anfrage|gefragt,\\s+ob\\s+du)", (int)2);
    private static final Pattern ACCEPT = Pattern.compile((String)"^/?tpaccept(?:\\s+[.*]?[A-Za-z0-9_]{2,16}(?:\\s+(?:tpa|tpahere))?)?$", (int)2);
    private static String pendingCommand;
    private static String pendingName;
    private static int pendingTicks;

    private AutoTpa() {
    }

    public static void accept(Component message) {
        String command;
        if (message == null || !Features.isOn("fwm.tab.autotpa")) {
            return;
        }
        if (FwmConfig.INSTANCE.autoTpaNames.isEmpty()) {
            return;
        }
        String button = AutoTpa.acceptCommand(message);
        if (!ServerLine.fromServer(message)) {
            return;
        }
        String line = ServerLine.plain(message);
        Matcher matcher = REQUEST.matcher((CharSequence)line);
        if (!matcher.find()) {
            return;
        }
        String name = matcher.group("name");
        boolean here = line.toLowerCase(Locale.ROOT).contains((CharSequence)"gefragt");
        if (here && !FwmConfig.INSTANCE.autoTpaHere) {
            return;
        }
        if (!AutoTpa.listed(name)) {
            return;
        }
        String string = button != null ? ServerLine.strip(button).trim() : (command = "tpaccept " + name + (here ? " tpahere" : " tpa"));
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        if (!ACCEPT.matcher((CharSequence)command).matches()) {
            return;
        }
        pendingCommand = command;
        pendingName = name;
        pendingTicks = Math.max((int)1, (int)FwmConfig.INSTANCE.autoTpaDelayTicks);
    }

    public static void tick(Minecraft client) {
        if (pendingCommand == null) {
            return;
        }
        if (client.player == null || client.getConnection() == null || !Features.isOn("fwm.tab.autotpa")) {
            pendingCommand = null;
            return;
        }
        if (--pendingTicks > 0) {
            return;
        }
        client.getConnection().sendCommand(pendingCommand);
        client.player.sendSystemMessage((Component)Component.literal((String)"[FWM] ").withStyle(ChatFormatting.GREEN).append((Component)Component.literal((String)Lang.t("TPA von " + pendingName + " automatisch angenommen.", "Accepted " + pendingName + "'s TPA automatically.")).withStyle(ChatFormatting.GRAY)));
        pendingCommand = null;
    }

    private static boolean listed(String name) {
        if (name == null) {
            return false;
        }
        String bare = name.replaceFirst("^[.*]", "");
        for (String entry : FwmConfig.INSTANCE.autoTpaNames) {
            String want;
            if (entry == null || (want = entry.trim().replaceFirst("^[.*]", "")).isEmpty() || !want.equalsIgnoreCase(bare)) continue;
            return true;
        }
        return false;
    }

    private static String acceptCommand(Component message) {
        Optional found = message.visit((style, part) -> {
            ClickEvent.RunCommand run;
            ClickEvent patt0$temp = style.getClickEvent();
            if (patt0$temp instanceof ClickEvent.RunCommand && (run = (ClickEvent.RunCommand)patt0$temp).command().toLowerCase(Locale.ROOT).contains((CharSequence)"tpaccept")) {
                return Optional.of((Object)run.command().trim());
            }
            return Optional.empty();
        }, Style.EMPTY);
        return (String)found.orElse(null);
    }
}
