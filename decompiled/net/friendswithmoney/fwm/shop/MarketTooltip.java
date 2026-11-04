/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.Locale
 *  java.util.stream.Stream
 *  net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.ComponentContents
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.contents.TranslatableContents
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemContainerContents
 */
package net.friendswithmoney.fwm.shop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.net.MarketPrices;
import net.friendswithmoney.fwm.shop.MarketKey;
import net.friendswithmoney.fwm.shop.MarketScreens;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.ComponentContents;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class MarketTooltip {
    private static final String[] UNITS = new String[]{"K", "M", "B", "T"};

    private MarketTooltip() {
    }

    public static void register() {
        MarketScreens.register();
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> MarketTooltip.append(stack, (List<Component>)lines));
    }

    private static void append(ItemStack stack, List<Component> lines) {
        if (!Features.isOn("fwm.feature.ahprice")) {
            return;
        }
        if (stack == null || stack.isEmpty() || lines == null) {
            return;
        }
        if (!MarketScreens.allowed()) {
            return;
        }
        MarketPrices.ensure();
        Double[] prices = MarketPrices.get(MarketKey.of(stack));
        boolean own = prices != null && prices.length >= 2;
        List<Component> content = MarketTooltip.content(stack);
        if (!own && content.isEmpty()) {
            return;
        }
        String ahLabel = Lang.str("fwm.ahprice.ah", new Object[0]);
        String contentLabel = Lang.str("fwm.ahprice.content.ah", new Object[0]);
        int wert = -1;
        for (int i = 0; i < lines.size(); ++i) {
            String line = ((Component)lines.get(i)).getString();
            if (line.startsWith(ahLabel) || line.startsWith(contentLabel)) {
                return;
            }
            if (wert >= 0 || !line.trim().startsWith("Wert:")) continue;
            wert = i;
        }
        ArrayList add = new ArrayList();
        if (own) {
            int count = stack.getCount();
            add.add((Object)MarketTooltip.line(ahLabel, prices[0], count));
            add.add((Object)MarketTooltip.line(Lang.str("fwm.ahprice.orders", new Object[0]), prices[1], count));
        }
        add.addAll(content);
        if (wert >= 0) {
            lines.addAll(wert + 1, (Collection)add);
            return;
        }
        int at = MarketTooltip.debugStart(stack, lines);
        lines.add(at, (Object)Component.empty());
        lines.addAll(at + 1, (Collection)add);
    }

    private static List<Component> content(ItemStack stack) {
        ItemContainerContents box = (ItemContainerContents)stack.get(DataComponents.CONTAINER);
        if (box == null) {
            return List.of();
        }
        long ah = 0L;
        long orders = 0L;
        int unpriced = 0;
        boolean any = false;
        for (ItemStack inner : () -> ((Stream)box.nonEmptyItemCopyStream()).iterator()) {
            double o;
            any = true;
            Double[] p = MarketPrices.get(MarketKey.of(inner));
            double a = p != null && p.length >= 2 && p[0] != null ? p[0] : 0.0;
            double d = o = p != null && p.length >= 2 && p[1] != null ? p[1] : 0.0;
            if (a <= 0.0 && o <= 0.0) {
                unpriced += inner.getCount();
                continue;
            }
            ah += Math.round((double)(a * 100.0)) * (long)inner.getCount();
            orders += Math.round((double)(o * 100.0)) * (long)inner.getCount();
        }
        if (!any) {
            return List.of();
        }
        MutableComponent ahLine = Component.literal((String)Lang.str("fwm.ahprice.content.ah", new Object[0])).withStyle(ChatFormatting.WHITE).append((Component)Component.literal((String)(ah > 0L ? MarketTooltip.money(ah) : "\u2013")).withStyle(ah > 0L ? ChatFormatting.AQUA : ChatFormatting.GRAY));
        MutableComponent orderLine = Component.literal((String)Lang.str("fwm.ahprice.content.orders", new Object[0])).withStyle(ChatFormatting.WHITE).append((Component)Component.literal((String)(orders > 0L ? MarketTooltip.money(orders) : "\u2013")).withStyle(orders > 0L ? ChatFormatting.AQUA : ChatFormatting.GRAY));
        if (unpriced > 0) {
            orderLine.append((Component)Component.literal((String)String.format((String)Lang.str("fwm.ahprice.content.unpriced", new Object[0]), (Object[])new Object[]{unpriced})).withStyle(ChatFormatting.GRAY));
        }
        return List.of((Object)ahLine, (Object)orderLine);
    }

    private static int debugStart(ItemStack stack, List<Component> lines) {
        String id = BuiltInRegistries.ITEM.getKey((Object)stack.getItem()).toString();
        for (int i = 0; i < lines.size(); ++i) {
            TranslatableContents t;
            Component line = (Component)lines.get(i);
            ComponentContents componentContents = line.getContents();
            if (componentContents instanceof TranslatableContents && (t = (TranslatableContents)componentContents).getKey().equals((Object)"item.durability")) {
                return i;
            }
            if (!line.getString().equals((Object)id)) continue;
            return i;
        }
        return lines.size();
    }

    private static Component line(String label, Double dollars, int count) {
        MutableComponent text = Component.literal((String)label).withStyle(ChatFormatting.WHITE);
        if (dollars == null || dollars <= 0.0) {
            return text.append((Component)Component.literal((String)"\u2013").withStyle(ChatFormatting.GRAY));
        }
        long cents = Math.round((double)(dollars * 100.0));
        text.append((Component)Component.literal((String)MarketTooltip.money(cents * (long)count)).withStyle(ChatFormatting.AQUA));
        if (count > 1) {
            text.append((Component)Component.literal((String)(" (" + count + "x " + MarketTooltip.money(cents) + ")")).withStyle(ChatFormatting.GRAY));
        }
        return text;
    }

    static String money(long cents) {
        int unit;
        double dollars = (double)cents / 100.0;
        if (dollars < 10.0) {
            return Fmt.moneyDe(cents);
        }
        if (dollars < 1000.0) {
            return "$" + Math.round((double)dollars);
        }
        double value = dollars;
        for (unit = -1; value >= 999.95 && unit < UNITS.length - 1; value /= 1000.0, ++unit) {
        }
        return "$" + String.format((Locale)Locale.GERMANY, (String)"%.1f", (Object[])new Object[]{value}) + UNITS[unit];
    }
}
