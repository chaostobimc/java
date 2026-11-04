/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.List
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.components.ChatComponent
 *  net.minecraft.client.multiplayer.chat.GuiMessage
 *  net.minecraft.network.chat.Component
 *  org.slf4j.Logger
 *  org.slf4j.LoggerFactory
 */
package net.friendswithmoney.fwm.seventv;

import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.mixin.ChatHudAccessor;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteConfig;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.EmoteText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import net.minecraft.network.chat.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SevenTv {
    public static final String NAMESPACE = "fwm";
    public static final Logger LOG = LoggerFactory.getLogger((String)"FWM 7TV");
    private static volatile boolean chatDirty;
    private static volatile long lastLoadedAt;
    private static final Set<String> IN_CHAT;
    private static long lastRefresh;
    private static final int REFRESH_LINES = 120;

    private SevenTv() {
    }

    public static boolean on() {
        return Features.isOn("fwm.tab.emotes");
    }

    public static void register() {
        EmoteConfig.load();
        for (Emote e : EmoteConfig.frequent(Integer.MAX_VALUE)) {
            EmoteRegistry.bindName(e);
        }
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.gui != null) {
                SevenTv.tick(client.gui.hud.getChat());
            }
        });
    }

    public static Component transform(Component message) {
        if (!SevenTv.on()) {
            return message;
        }
        try {
            return EmoteText.transform(message);
        }
        catch (Throwable t) {
            LOG.warn("Emote-Ersetzung fehlgeschlagen", t);
            return message;
        }
    }

    static void inChat(String key) {
        if (IN_CHAT.size() > 2000) {
            IN_CHAT.clear();
        }
        IN_CHAT.add((Object)key);
    }

    public static void onEmoteLoaded(String key) {
        if (!IN_CHAT.contains((Object)key)) {
            return;
        }
        lastLoadedAt = System.currentTimeMillis();
        chatDirty = true;
    }

    public static void requestChatRefresh() {
        chatDirty = true;
    }

    private static void tick(ChatComponent chat) {
        if (!chatDirty) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now - lastLoadedAt < 150L || now - lastRefresh < 1000L) {
            return;
        }
        chatDirty = false;
        lastRefresh = now;
        try {
            ChatHudAccessor acc = (ChatHudAccessor)chat;
            List<GuiMessage> all = acc.fwm$messages();
            for (int i = 0; i < Math.min((int)all.size(), (int)120); ++i) {
                GuiMessage m = (GuiMessage)all.get(i);
                Component c = EmoteText.transform(m.content());
                if (c == m.content()) continue;
                all.set(i, (Object)new GuiMessage(m.addedTime(), c, m.signature(), m.source(), m.tag()));
            }
            acc.fwm$refresh();
        }
        catch (Throwable t) {
            LOG.warn("Chat-Aktualisierung fehlgeschlagen", t);
        }
    }

    public static Minecraft mc() {
        return Minecraft.getInstance();
    }

    static {
        IN_CHAT = ConcurrentHashMap.newKeySet();
    }
}
