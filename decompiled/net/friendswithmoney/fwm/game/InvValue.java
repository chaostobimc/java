/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemContainerContents
 */
package net.friendswithmoney.fwm.game;

import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.mixin.HandledScreenAccessor;
import net.friendswithmoney.fwm.net.MarketPrices;
import net.friendswithmoney.fwm.shop.MarketKey;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;

public final class InvValue {
    private static int timer;
    private static int lastHash;
    private static long value;
    private static int unpriced;

    private InvValue() {
    }

    public static boolean active() {
        return Features.isOn("fwm.feature.invvalue") && Features.isOn("fwm.feature.ahprice");
    }

    public static long value() {
        return value;
    }

    public static int unpriced() {
        return unpriced;
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof InventoryScreen)) {
                return;
            }
            InventoryScreen inventory = (InventoryScreen)screen;
            ScreenEvents.afterExtract((Screen)screen).register((s, ctx, mouseX, mouseY, delta) -> InvValue.render(client, inventory, ctx));
        });
    }

    public static void tick(Minecraft client) {
        if (!InvValue.active() || client.player == null) {
            return;
        }
        if (++timer < 20) {
            return;
        }
        timer = 0;
        MarketPrices.ensure();
        Inventory inventory = client.player.getInventory();
        int hash = 1;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            hash = 31 * hash + (stack.isEmpty() ? 0 : stack.getItem().hashCode() * 97 + stack.getCount());
        }
        if ((hash = 31 * hash + (FwmConfig.INSTANCE.invValueShulkers ? 1 : 0) + FwmConfig.INSTANCE.invValueSource * 7) == lastHash && value >= 0L) {
            return;
        }
        long sum = 0L;
        int missing = 0;
        boolean anyPrice = false;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemContainerContents box;
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty()) continue;
            long unit = InvValue.price(stack);
            if (unit >= 0L) {
                anyPrice = true;
                sum += unit * (long)stack.getCount();
            } else if (stack.get(DataComponents.CONTAINER) == null) {
                missing += stack.getCount();
            }
            if (!FwmConfig.INSTANCE.invValueShulkers || (box = (ItemContainerContents)stack.get(DataComponents.CONTAINER)) == null) continue;
            for (ItemStack inner : box.nonEmptyItemCopyStream().toList()) {
                long p = InvValue.price(inner);
                if (p >= 0L) {
                    anyPrice = true;
                    sum += p * (long)inner.getCount();
                    continue;
                }
                missing += inner.getCount();
            }
        }
        lastHash = anyPrice ? hash : 0;
        value = anyPrice ? sum : -1L;
        unpriced = missing;
    }

    static long price(ItemStack stack) {
        Double[] p = MarketPrices.get(MarketKey.of(stack));
        if (p == null || p.length < 2) {
            return -1L;
        }
        double ah = p[0] == null ? 0.0 : p[0];
        double orders = p[1] == null ? 0.0 : p[1];
        double pick = switch (FwmConfig.INSTANCE.invValueSource) {
            case 1 -> {
                if (orders > 0.0) {
                    yield orders;
                }
                yield ah;
            }
            case 2 -> Math.max((double)ah, (double)orders);
            case 3 -> {
                if (ah > 0.0 && orders > 0.0) {
                    yield Math.min((double)ah, (double)orders);
                }
                yield Math.max((double)ah, (double)orders);
            }
            default -> ah > 0.0 ? ah : orders;
        };
        return pick > 0.0 ? Math.round((double)(pick * 100.0)) : -1L;
    }

    public static String line() {
        String amount = value < 0L ? "\u2013" : (FwmConfig.INSTANCE.moneyHideAmounts ? "\u2022\u2022\u2022" : Fmt.compactMoney(value));
        return Lang.t("Inventar: ", "Inventory: ") + amount;
    }

    private static void render(Minecraft client, InventoryScreen screen, GuiGraphicsExtractor ctx) {
        if (!InvValue.active() || !FwmConfig.INSTANCE.invValueInInventory || client.player == null) {
            return;
        }
        HandledScreenAccessor at = (HandledScreenAccessor)screen;
        String text = InvValue.line();
        int x = at.fwm$x() + 176 - client.font.width(text) - 2;
        int y = at.fwm$y() - 11;
        if (y < 2) {
            return;
        }
        ctx.text(client.font, text, x, y, -1509908);
    }

    static {
        value = -1L;
    }
}
