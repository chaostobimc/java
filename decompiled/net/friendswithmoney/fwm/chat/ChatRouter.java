/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.chat;

import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.chat.FakeTags;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.chat.SecondChat;
import net.friendswithmoney.fwm.game.AutoTpa;
import net.friendswithmoney.fwm.game.TpaWatch;
import net.friendswithmoney.fwm.money.MoneyEvents;
import net.friendswithmoney.fwm.stats.SellStats;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public final class ChatRouter {
    private ChatRouter() {
    }

    public static void register() {
        ClientReceiveMessageEvents.ALLOW_GAME.register((message, overlay) -> {
            if (overlay) {
                return true;
            }
            return ChatRouter.accept(message, false);
        });
        ClientReceiveMessageEvents.MODIFY_GAME.register((message, overlay) -> overlay ? message : FakeTags.mark(message));
        ClientReceiveMessageEvents.ALLOW_CHAT.register((message, signedMessage, sender, params, receptionTimestamp) -> {
            if (!ChatRouter.accept(message, true)) {
                return false;
            }
            Component shown = params.decorate(message);
            Component marked = FakeTags.mark(shown);
            if (marked == shown) {
                return true;
            }
            Minecraft.getInstance().gui.hud.getChat().addServerSystemMessage(marked);
            return false;
        });
    }

    public static boolean actionBar(Component message) {
        if (message == null) {
            return true;
        }
        SellStats.acceptActionBar(message);
        MoneyEvents.accept(message, true);
        if (!MoneyEvents.shown(message, true)) {
            return false;
        }
        SecondChat.acceptActionBar(message);
        return true;
    }

    private static boolean accept(Component message, boolean signed) {
        if (!signed) {
            SellStats.accept(message);
            Auction.accept(message);
            TpaWatch.accept(message);
            MoneyEvents.accept(message, false);
            AutoTpa.accept(message);
            Giveaway.accept(message);
            Messages.accept(message);
        }
        if (signed) {
            return true;
        }
        if (!MoneyEvents.shown(message, false)) {
            return false;
        }
        return SecondChat.accept(FakeTags.mark(message));
    }
}
