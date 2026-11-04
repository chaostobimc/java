/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.net.URLEncoder
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 */
package net.friendswithmoney.fwm.net;

import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.LiveAuctionData;
import net.friendswithmoney.fwm.net.Market;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;

public final class PricePings {
    public static final int MAX_PINGS = 10;
    private static final long ROUND_MS = 20000L;
    private static final long MIN_GAP_MS = 2000L;
    private static final long SOUND_GAP_MS = 3000L;
    private static final Map<String, Long> LAST_ID = new HashMap();
    private static final Set<String> BUSY = new HashSet();
    private static long nextAt;
    private static int cursor;
    private static long lastSound;

    private PricePings() {
    }

    public static List<FwmConfig.PricePing> list() {
        return FwmConfig.INSTANCE.pricePings;
    }

    public static FwmConfig.PricePing find(String itemKey, boolean order) {
        for (FwmConfig.PricePing ping : PricePings.list()) {
            if (ping.order != order || ping.itemKey == null || !ping.itemKey.equals((Object)itemKey)) continue;
            return ping;
        }
        return null;
    }

    public static boolean set(MarketData.Item item, String name, double maxPrice, boolean order) {
        return PricePings.set(item, name, maxPrice, order, !order, false, true, false);
    }

    public static boolean set(MarketData.Item item, String name, double maxPrice, boolean order, boolean below, boolean repeat, boolean toast, boolean chat) {
        FwmConfig.PricePing ping = PricePings.find(item.itemKey, order);
        if (ping == null) {
            if (PricePings.list().size() >= 10) {
                return false;
            }
            ping = new FwmConfig.PricePing();
            ping.itemKey = item.itemKey;
            ping.order = order;
            PricePings.list().add((Object)ping);
        }
        ping.baseItemKey = item.baseItemKey;
        ping.icon = item.icon;
        ping.name = name;
        ping.maxPrice = maxPrice;
        ping.direction = below ? "below" : "above";
        ping.repeat = repeat;
        ping.toast = toast;
        ping.chat = chat;
        ping.fired = false;
        if (ping.seen == null) {
            ping.seen = new ArrayList();
        }
        ping.seen.clear();
        LAST_ID.remove((Object)PricePings.slot(item.itemKey, order));
        ConfigManager.save();
        return true;
    }

    public static void remove(String itemKey, boolean order) {
        PricePings.list().removeIf(ping -> ping.order == order && itemKey != null && itemKey.equals((Object)ping.itemKey));
        LAST_ID.remove((Object)PricePings.slot(itemKey, order));
        ConfigManager.save();
    }

    private static String slot(String itemKey, boolean order) {
        return (order ? "o:" : "a:") + itemKey;
    }

    public static double parsePrice(String raw) {
        if (raw == null) {
            return Double.NaN;
        }
        String text = raw.trim().toLowerCase(Locale.ROOT).replace((CharSequence)"$", (CharSequence)"").replace((CharSequence)" ", (CharSequence)"");
        if (text.isEmpty()) {
            return Double.NaN;
        }
        double factor = 1.0;
        char last = text.charAt(text.length() - 1);
        if (last == 'k' || last == 'm' || last == 'b') {
            factor = last == 'k' ? 1000.0 : (last == 'm' ? 1000000.0 : 1.0E9);
            text = text.substring(0, text.length() - 1).replace(',', '.');
        } else {
            text = text.replace((CharSequence)".", (CharSequence)"").replace((CharSequence)",", (CharSequence)"");
        }
        try {
            double value = Double.parseDouble((String)text) * factor;
            return value > 0.0 ? value : Double.NaN;
        }
        catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static void tick(Minecraft client) {
        if (client.player == null || !Features.isOn("fwm.feature.priceping")) {
            return;
        }
        ArrayList pings = new ArrayList();
        for (FwmConfig.PricePing p : PricePings.list()) {
            if (p.fired) continue;
            pings.add((Object)p);
        }
        if (pings.isEmpty()) {
            return;
        }
        long now = System.currentTimeMillis();
        if (now < nextAt) {
            return;
        }
        nextAt = now + Math.max((long)2000L, (long)(20000L / (long)pings.size()));
        cursor %= pings.size();
        PricePings.poll(client, (FwmConfig.PricePing)pings.get(cursor++));
    }

    private static void poll(Minecraft client, FwmConfig.PricePing ping) {
        String key = ping.itemKey;
        boolean order = ping.order;
        String slot = PricePings.slot(key, order);
        if (key == null || BUSY.contains((Object)slot)) {
            return;
        }
        Long after = (Long)LAST_ID.get((Object)slot);
        String feed = PricePings.base() + (order ? "/live-orders" : "/live-auctions");
        String url = after == null ? feed + "?limit=1" : feed + "?itemKey=" + PricePings.encode(key) + "&afterId=" + after + "&limit=100";
        BUSY.add((Object)slot);
        FwmApi.getUrl(url, LiveAuctionData.class).whenCompleteAsync((result, error) -> client.execute(() -> {
            BUSY.remove((Object)slot);
            if (error != null || result == null) {
                System.err.println("[FWM] Price ping failed for " + key + ": " + (error == null ? "empty response" : error.getMessage()));
                return;
            }
            FwmConfig.PricePing current = PricePings.find(key, order);
            if (current == null) {
                return;
            }
            long newest = after == null ? 0L : after;
            for (LiveAuctionData.Entry entry : result.list()) {
                String seenKey;
                double unit;
                long id = PricePings.parseLong(entry.id);
                newest = Math.max((long)newest, (long)id);
                if (after == null || id <= after || !key.equals((Object)entry.itemKey) || Double.isNaN((double)(unit = Market.number(order ? entry.price : entry.unitPrice))) || current.fired || !(current.below() ? unit <= current.maxPrice : unit >= current.maxPrice)) continue;
                String string = seenKey = entry.id != null ? entry.id : entry.sellerName + "|" + unit + "|" + entry.amount;
                if (current.seen == null) {
                    current.seen = new ArrayList();
                }
                if (current.seen.contains((Object)seenKey)) continue;
                current.seen.add((Object)seenKey);
                while (current.seen.size() > 50) {
                    current.seen.remove(0);
                }
                PricePings.announce(client, current, entry, unit);
                if (current.repeat) continue;
                current.fired = true;
                ConfigManager.save();
                break;
            }
            if (result.meta != null) {
                newest = Math.max((long)newest, (long)PricePings.parseLong(result.meta.nextAfterId));
            }
            LAST_ID.put((Object)slot, (Object)newest);
        }), FwmApi.executor());
    }

    private static void announce(Minecraft client, FwmConfig.PricePing ping, LiveAuctionData.Entry entry, double unitPrice) {
        String name = ping.name == null || ping.name.isBlank() ? ping.itemKey : ping.name;
        String price = "$" + Fmt.compactUp(Math.round((double)unitPrice));
        String seller = entry.sellerName == null ? "?" : entry.sellerName;
        long amount = Market.rounded(entry.amount);
        if (ping.toastOn()) {
            long now = System.currentTimeMillis();
            if (now - lastSound >= 3000L) {
                lastSound = now;
                client.getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.NOTE_BLOCK_PLING, (float)1.6f));
            }
            FwmHud.showPricePing(ping, name, price, seller, amount);
        }
        if (ping.chat && client.gui != null) {
            client.gui.hud.getChat().addClientSystemMessage((Component)Component.literal((String)"[FWM] ").withStyle(ChatFormatting.GREEN).append((Component)Component.literal((String)((ping.order ? "Order: " : "AH: ") + amount + "x " + name + " f\u00fcr " + price + " \u00b7 " + seller)).withStyle(ChatFormatting.GRAY)));
        }
    }

    private static String base() {
        return FwmConfig.INSTANCE.marketApiUrl.replaceAll("/+$", "");
    }

    private static String encode(String raw) {
        return URLEncoder.encode((String)raw, (Charset)StandardCharsets.UTF_8).replace((CharSequence)"+", (CharSequence)"%20");
    }

    private static long parseLong(String raw) {
        try {
            return raw == null ? 0L : Long.parseLong((String)raw.trim());
        }
        catch (NumberFormatException e) {
            return 0L;
        }
    }
}
