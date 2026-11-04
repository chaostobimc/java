/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Locale
 *  java.util.Map
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.net;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.minecraft.client.Minecraft;

public final class MarketPrices {
    private static final long REFRESH_MS = 3600000L;
    private static final long RETRY_MS = 600000L;
    private static volatile Map<String, Double[]> items = Map.of();
    private static volatile Map<String, String> byName = Map.of();
    private static boolean busy;
    private static long nextLoad;
    private static int requests;

    private MarketPrices() {
    }

    public static void ensure() {
        if (!Features.isOn("fwm.feature.ahprice")) {
            return;
        }
        if (busy || System.currentTimeMillis() < nextLoad) {
            return;
        }
        busy = true;
        ++requests;
        Minecraft client = Minecraft.getInstance();
        FwmApi.getUrl(MarketPrices.base() + "/prices", Data.class).whenCompleteAsync((result, error) -> client.execute(() -> {
            busy = false;
            if (error != null || result == null || result.items == null) {
                System.err.println("[FWM] Market prices failed: " + (error == null ? "empty response" : error.getMessage()));
                nextLoad = System.currentTimeMillis() + 600000L;
                return;
            }
            items = result.items;
            HashMap names = new HashMap();
            if (result.names != null) {
                result.names.forEach((arg_0, arg_1) -> MarketPrices.lambda$ensure$2((Map)names, arg_0, arg_1));
            }
            byName = names;
            nextLoad = System.currentTimeMillis() + 3600000L;
        }), FwmApi.executor());
    }

    public static Double[] get(String key) {
        return key == null ? null : (Double[])items.get((Object)key);
    }

    public static String keyForName(String displayName) {
        return displayName == null ? null : (String)byName.get((Object)MarketPrices.normalize(displayName));
    }

    public static int requests() {
        return requests;
    }

    private static String normalize(String name) {
        return name.replaceAll("\u00a7.", "").trim().toLowerCase(Locale.ROOT);
    }

    private static String base() {
        return FwmConfig.INSTANCE.marketHistoryUrl.replaceAll("/+$", "");
    }

    private static /* synthetic */ void lambda$ensure$2(Map names, String key, String name) {
        names.put((Object)MarketPrices.normalize(name), (Object)key);
    }

    public static class Data {
        public boolean ok;
        public String day;
        public Map<String, Double[]> items;
        public Map<String, String> names;
    }
}
