/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Iterator
 *  net.minecraft.core.Holder
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.alchemy.PotionContents
 *  net.minecraft.world.item.enchantment.ItemEnchantments
 */
package net.friendswithmoney.fwm.shop;

import java.util.Iterator;
import net.friendswithmoney.fwm.net.MarketPrices;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.enchantment.ItemEnchantments;

public final class MarketKey {
    private MarketKey() {
    }

    public static String of(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return null;
        }
        String custom = MarketPrices.keyForName(stack.getHoverName().getString());
        if (custom != null) {
            return custom;
        }
        Identifier id = BuiltInRegistries.ITEM.getKey((Object)stack.getItem());
        String path = id.getPath();
        if (path.equals((Object)"enchanted_book")) {
            return MarketKey.book(stack);
        }
        if (path.equals((Object)"potion") || path.equals((Object)"splash_potion") || path.equals((Object)"tipped_arrow")) {
            String potion = MarketKey.potion(stack);
            return potion == null ? null : path + "#" + potion;
        }
        return path;
    }

    private static String book(ItemStack stack) {
        ItemEnchantments stored = (ItemEnchantments)stack.get(DataComponents.STORED_ENCHANTMENTS);
        if (stored == null || stored.size() != 1) {
            return null;
        }
        Iterator iterator = stored.keySet().iterator();
        if (iterator.hasNext()) {
            Holder entry = (Holder)iterator.next();
            String name = (String)entry.unwrapKey().map(key -> key.identifier().getPath()).orElse(null);
            if (name == null) {
                return null;
            }
            return "enchanted_book#" + name + "_" + stored.getLevel(entry);
        }
        return null;
    }

    private static String potion(ItemStack stack) {
        PotionContents contents = (PotionContents)stack.get(DataComponents.POTION_CONTENTS);
        if (contents == null || contents.potion().isEmpty()) {
            return null;
        }
        Holder potion = (Holder)contents.potion().get();
        String name = (String)potion.unwrapKey().map(key -> key.identifier().getPath()).orElse(null);
        if (name == null) {
            return null;
        }
        return name.replaceFirst("^(long|strong)_", "");
    }
}
