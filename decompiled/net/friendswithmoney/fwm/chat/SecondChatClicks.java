/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Optional
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.network.chat.ClickEvent
 *  net.minecraft.network.chat.ClickEvent$RunCommand
 *  net.minecraft.network.chat.ClickEvent$SuggestCommand
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.chat;

import java.util.Optional;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.Style;

public final class SecondChatClicks {
    private SecondChatClicks() {
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof ChatScreen)) {
                return;
            }
            ChatScreen chat = (ChatScreen)screen;
            ScreenMouseEvents.allowMouseClick((Screen)screen).register((ignored, click) -> !SecondChatClicks.handle(client, chat, click));
            ScreenMouseEvents.allowMouseScroll((Screen)screen).register((ignored, mouseX, mouseY, horizontal, vertical) -> !FwmHud.scrollSecondChat(mouseX, mouseY, vertical));
        });
    }

    private static boolean handle(Minecraft client, ChatScreen screen, MouseButtonEvent click) {
        if (GLFW.button(click.button()) != 0) {
            return false;
        }
        if (!Features.isOn("fwm.tab.secondchat")) {
            return false;
        }
        if (client.player == null) {
            return false;
        }
        for (FwmHud.ChatHit hit : FwmHud.chatHits()) {
            if (click.x() < (double)hit.x() || click.x() >= (double)(hit.x() + hit.width()) || click.y() < (double)hit.y() || click.y() >= (double)(hit.y() + hit.height())) continue;
            double offset = click.x() - (double)hit.x();
            Style style = SecondChatClicks.styleAt(client.font, hit.text(), offset);
            if (SecondChatClicks.run(client, screen, style)) {
                return true;
            }
            return SecondChatClicks.offerPlainCommand(client, screen, hit.text().getString(), offset);
        }
        return false;
    }

    private static boolean offerPlainCommand(Minecraft client, ChatScreen screen, String line, double offset) {
        int index;
        int first = SecondChatClicks.commandAt(line, 0);
        if (first < 0) {
            return false;
        }
        int start = first;
        if (SecondChatClicks.commandAt(line, first + 1) >= 0 && (start = line.lastIndexOf(47, Math.max((int)0, (int)Math.min((int)(index = client.font.getSplitter().plainIndexAtWidth(line, (int)offset, Style.EMPTY)), (int)(line.length() - 1))))) < 0) {
            return false;
        }
        String command = line.substring(start).trim();
        if (command.length() < 2) {
            return false;
        }
        screen.insertText(command, true);
        return true;
    }

    private static int commandAt(String line, int from) {
        for (int i = Math.max((int)0, (int)from); i < line.length() - 1; ++i) {
            if (line.charAt(i) != '/' || i > 0 && !Character.isWhitespace((char)line.charAt(i - 1)) || !Character.isLetter((char)line.charAt(i + 1))) continue;
            return i;
        }
        return -1;
    }

    private static boolean run(Minecraft client, ChatScreen screen, Style style) {
        if (style == null) {
            return false;
        }
        ClickEvent event = style.getClickEvent();
        if (event == null) {
            return false;
        }
        if (event instanceof ClickEvent.RunCommand) {
            ClickEvent.RunCommand command = (ClickEvent.RunCommand)event;
            String bare = command.command();
            if (bare.startsWith("/")) {
                bare = bare.substring(1);
            }
            if (bare.isBlank()) {
                return false;
            }
            client.player.connection.sendCommand(bare);
            screen.onClose();
            return true;
        }
        if (event instanceof ClickEvent.SuggestCommand) {
            ClickEvent.SuggestCommand suggest = (ClickEvent.SuggestCommand)event;
            screen.insertText(suggest.command(), true);
            return true;
        }
        return false;
    }

    private static Style styleAt(Font font, FormattedText line, double offset) {
        float[] cursor = new float[]{0.0f};
        Optional found = line.visit((style, part) -> {
            cursor[0] = cursor[0] + (float)font.width((FormattedText)Component.literal((String)part).setStyle(style));
            return (double)cursor[0] > offset ? Optional.of((Object)style) : Optional.empty();
        }, Style.EMPTY);
        return (Style)found.orElse(null);
    }
}
