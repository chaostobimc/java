/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Comparator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.seventv;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteApi;
import net.friendswithmoney.fwm.seventv.EmoteTexture;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.minecraft.client.Minecraft;

public final class EmoteRegistry {
    private static final Map<String, Emote> BY_KEY = new ConcurrentHashMap();
    private static final Map<String, Emote> BY_NAME = new ConcurrentHashMap();
    private static final Set<String> NOT_FOUND = ConcurrentHashMap.newKeySet();
    private static final Set<String> PENDING = ConcurrentHashMap.newKeySet();

    private EmoteRegistry() {
    }

    public static void register(Emote e) {
        BY_KEY.putIfAbsent((Object)e.key(), (Object)e);
    }

    public static void bindName(Emote e) {
        EmoteRegistry.register(e);
        BY_NAME.put((Object)e.name(), (Object)e);
        NOT_FOUND.remove((Object)e.name());
    }

    public static Emote byKey(String key) {
        return (Emote)((Object)BY_KEY.get((Object)key));
    }

    public static Emote byName(String name) {
        return (Emote)((Object)BY_NAME.get((Object)name));
    }

    public static List<Emote> startingWith(String prefix, int limit) {
        String p = prefix.toLowerCase(Locale.ROOT);
        LinkedHashMap byName = new LinkedHashMap();
        for (Emote e2 : BY_KEY.values()) {
            Emote had;
            if (!e2.name().toLowerCase(Locale.ROOT).startsWith(p) || (had = (Emote)((Object)byName.get((Object)e2.name()))) != null && (had.provider() == Emote.Provider.SEVENTV || e2.provider() != Emote.Provider.SEVENTV)) continue;
            byName.put((Object)e2.name(), (Object)e2);
        }
        ArrayList out = new ArrayList(byName.values());
        out.sort(Comparator.comparingInt(e -> e.name().length()).thenComparing(Emote::name));
        return out.size() > limit ? out.subList(0, limit) : out;
    }

    public static boolean isKnownMissing(String name) {
        return NOT_FOUND.contains((Object)name);
    }

    public static void resolveAsync(String name) {
        if (BY_NAME.containsKey((Object)name) || NOT_FOUND.contains((Object)name) || !PENDING.add((Object)name)) {
            return;
        }
        EmoteApi.lookupByName(name).whenComplete((emote, err) -> {
            PENDING.remove((Object)name);
            if (err != null || emote == null) {
                NOT_FOUND.add((Object)name);
                return;
            }
            EmoteRegistry.register(emote);
            BY_NAME.putIfAbsent((Object)name, (Object)emote);
            EmoteTexture.get(emote);
            Minecraft.getInstance().execute(SevenTv::requestChatRefresh);
        });
    }
}
