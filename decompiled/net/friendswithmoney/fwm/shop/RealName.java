/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Optional
 *  net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback
 *  net.minecraft.ChatFormatting
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.shop;

import java.util.List;
import java.util.Optional;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.ItemStack;

public final class RealName {
    private RealName() {
    }

    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, type, lines) -> RealName.append(stack, (List<Component>)lines));
    }

    private static void append(ItemStack stack, List<Component> lines) {
        if (!Features.isOn("fwm.feature.realname")) {
            return;
        }
        if (stack == null || stack.isEmpty() || lines == null || lines.isEmpty()) {
            return;
        }
        boolean listing = RealName.isListing(lines);
        Component custom = (Component)stack.get(DataComponents.CUSTOM_NAME);
        if (custom == null || !listing && !RealName.anvilName(custom)) {
            return;
        }
        MutableComponent original = Component.translatable((String)stack.getItem().getDescriptionId());
        if (custom.getString().trim().equalsIgnoreCase(original.getString().trim())) {
            return;
        }
        String label = Lang.str("fwm.realname.label", new Object[0]);
        for (Component line : lines) {
            if (!line.getString().startsWith(label)) continue;
            return;
        }
        lines.set(0, (Object)RealName.italic((Component)lines.get(0)));
        lines.add(1, (Object)Component.literal((String)label).withStyle(ChatFormatting.GRAY).append((Component)original.copy().withStyle(ChatFormatting.YELLOW)));
    }

    private static Component italic(Component line) {
        MutableComponent out = Component.empty();
        line.visit((style, text) -> {
            out.append((Component)Component.literal((String)text).setStyle(style.withItalic(Boolean.valueOf((boolean)true))));
            return Optional.empty();
        }, Style.EMPTY);
        return out;
    }

    static boolean isListing(List<Component> lines) {
        for (Component line : lines) {
            String plain = line.getString().trim();
            if (!plain.startsWith("Verk\u00e4ufer:") && !plain.startsWith("Seller:")) continue;
            return true;
        }
        return false;
    }

    private static boolean anvilName(Component name) {
        Style italic = Style.EMPTY.withItalic(Boolean.valueOf((boolean)true));
        Optional upright = name.visit((style, part) -> !part.isBlank() && !style.applyTo(italic).isItalic() ? Optional.of((Object)Boolean.TRUE) : Optional.empty(), Style.EMPTY);
        return upright.isEmpty();
    }
}
