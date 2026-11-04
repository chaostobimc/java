/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.shop;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.friendswithmoney.fwm.auction.Money;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;

public final class UnitPrice {
    private static final Pattern AMOUNT = Pattern.compile((String)"^([^\\d]*)([\\d.,]+)([^\\d]*)$");
    private static final Pattern GERMAN = Pattern.compile((String)"\\.\\d{3}(?!\\d)|,\\d{1,2}$");
    private static final Pattern ENGLISH = Pattern.compile((String)",\\d{3}(?!\\d)|\\.\\d{1,2}$");
    private static boolean lastGerman = true;
    private static Pattern pattern;
    private static String patternSource;

    private UnitPrice() {
    }

    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> UnitPrice.append(stack, (List<Component>)lines));
    }

    private static void append(ItemStack stack, List<Component> lines) {
        if (!Features.isOn("fwm.feature.unitprice")) {
            return;
        }
        if (stack == null || lines == null) {
            return;
        }
        int count = stack.getCount();
        if (count <= 1) {
            return;
        }
        String label = Lang.str("fwm.unitprice.label", new Object[0]);
        for (int i = 0; i < lines.size(); ++i) {
            String line = ((Component)lines.get(i)).getString();
            if (line == null || line.isEmpty()) continue;
            if (line.startsWith(label)) {
                return;
            }
            Matcher matcher = UnitPrice.pattern().matcher((CharSequence)line);
            if (!matcher.find()) continue;
            Component unit = UnitPrice.unitLine(matcher.group(1), count, label);
            if (unit == null) {
                return;
            }
            lines.add(i + 1, (Object)unit);
            return;
        }
    }

    private static Component unitLine(String price, int count, String label) {
        if (price == null) {
            return null;
        }
        Matcher parts = AMOUNT.matcher((CharSequence)price.trim());
        if (!parts.matches()) {
            return null;
        }
        String amount = parts.group(2);
        long total = Money.cents(amount);
        if (total <= 0L) {
            return null;
        }
        String prefix = parts.group(1);
        String suffix = parts.group(3);
        MutableComponent text = Component.literal((String)label).withStyle(ChatFormatting.WHITE);
        return text.append((Component)Component.literal((String)UnitPrice.money(Math.round((double)((double)total / (double)count)), prefix, suffix, UnitPrice.germanStyle(amount))).withStyle(ChatFormatting.GOLD));
    }

    private static boolean germanStyle(String amount) {
        if (GERMAN.matcher((CharSequence)amount).find()) {
            lastGerman = true;
        } else if (ENGLISH.matcher((CharSequence)amount).find()) {
            lastGerman = false;
        }
        return lastGerman;
    }

    private static String money(long cents, String prefix, String suffix, boolean german) {
        String withStyle = (german ? Fmt.moneyDe(cents) : Fmt.money(cents)).substring(1);
        return prefix + withStyle + suffix;
    }

    private static Pattern pattern() {
        String source = FwmConfig.INSTANCE.unitPricePattern;
        if (pattern == null || !source.equals((Object)patternSource)) {
            try {
                pattern = Pattern.compile((String)source);
            }
            catch (Exception e) {
                pattern = Pattern.compile((String)"(?:Preis|Price)\\s*:\\s*(\\S+)");
            }
            patternSource = source;
        }
        return pattern;
    }
}
