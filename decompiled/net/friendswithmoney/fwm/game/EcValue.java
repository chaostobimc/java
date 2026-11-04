/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.Locale
 *  java.util.regex.Pattern
 *  net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.ContainerScreen
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.ChestMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemContainerContents
 *  net.minecraft.world.level.block.EnderChestBlock
 */
package net.friendswithmoney.fwm.game;

import java.util.Locale;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.InvValue;
import net.friendswithmoney.fwm.mixin.HandledScreenAccessor;
import net.friendswithmoney.fwm.mixin.ScreenTitleAccessor;
import net.friendswithmoney.fwm.net.MarketPrices;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.block.EnderChestBlock;

public final class EcValue {
    private static final Pattern COMMAND = Pattern.compile((String)"^(ec|ecsee)(\\s.*)?$", (int)2);
    private static final long WINDOW_MS = 3000L;
    private static final int TITLE_COLOR = -12566464;
    private static long pendingUntil;
    private static ContainerScreen shown;
    private static int lastHash;
    private static long value;
    private static int unpriced;

    private EcValue() {
    }

    public static void register() {
        ClientSendMessageEvents.COMMAND.register(command -> {
            if (command == null || !COMMAND.matcher((CharSequence)command.trim()).matches()) {
                return;
            }
            pendingUntil = System.currentTimeMillis() + 3000L;
            MarketPrices.ensure();
        });
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (world.isClientSide() && world.getBlockState(hit.getBlockPos()).getBlock() instanceof EnderChestBlock) {
                pendingUntil = System.currentTimeMillis() + 3000L;
                MarketPrices.ensure();
            }
            return InteractionResult.PASS;
        });
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof ContainerScreen)) {
                return;
            }
            ContainerScreen chest = (ContainerScreen)screen;
            if (screen != shown) {
                if (System.currentTimeMillis() >= pendingUntil) {
                    return;
                }
                if (!screen.getTitle().getString().toLowerCase(Locale.ROOT).contains((CharSequence)"ender")) {
                    return;
                }
                pendingUntil = 0L;
                shown = chest;
                lastHash = 0;
                value = -1L;
            }
            EcValue.fitTitle(client, chest);
            ScreenEvents.afterExtract((Screen)screen).register((s, ctx, mouseX, mouseY, delta) -> EcValue.render(client, chest, ctx, mouseX, mouseY));
            ScreenEvents.remove((Screen)screen).register(s -> {
                if (s == shown) {
                    shown = null;
                }
            });
        });
    }

    private static boolean on() {
        return FwmConfig.INSTANCE.ecValue;
    }

    private static String widest() {
        return Lang.t("Wert: ", "Value: ") + "999,99M";
    }

    private static void fitTitle(Minecraft client, ContainerScreen chest) {
        if (!EcValue.on()) {
            return;
        }
        Font font = client.font;
        HandledScreenAccessor at = (HandledScreenAccessor)chest;
        int room = at.fwm$backgroundWidth() - 8 - 8 - font.width(EcValue.widest()) - 6;
        String title = chest.getTitle().getString();
        if (font.width(title) <= room) {
            return;
        }
        String cut = font.plainSubstrByWidth(title, Math.max((int)10, (int)(room - font.width("\u2026")))) + "\u2026";
        ((ScreenTitleAccessor)chest).fwm$setTitle((Component)Component.literal((String)cut).setStyle(chest.getTitle().getStyle()));
    }

    private static void recompute(ContainerScreen chest, Inventory own) {
        int hash = 1;
        for (Slot slot : ((ChestMenu)chest.getMenu()).slots) {
            if (slot.container == own) continue;
            ItemStack stack = slot.getItem();
            hash = 31 * hash + (stack.isEmpty() ? 0 : stack.getItem().hashCode() * 97 + stack.getCount() + (stack.get(DataComponents.CONTAINER) == null ? 0 : ((ItemContainerContents)stack.get(DataComponents.CONTAINER)).hashCode()));
        }
        if ((hash = 31 * hash + FwmConfig.INSTANCE.invValueSource) == lastHash && value >= 0L) {
            return;
        }
        long sum = 0L;
        int missing = 0;
        boolean anyPrice = false;
        for (Slot slot : ((ChestMenu)chest.getMenu()).slots) {
            ItemContainerContents box;
            ItemStack stack;
            if (slot.container == own || (stack = slot.getItem()).isEmpty()) continue;
            long unit = InvValue.price(stack);
            if (unit >= 0L) {
                anyPrice = true;
                sum += unit * (long)stack.getCount();
            } else if (stack.get(DataComponents.CONTAINER) == null) {
                missing += stack.getCount();
            }
            if ((box = (ItemContainerContents)stack.get(DataComponents.CONTAINER)) == null) continue;
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
        boolean empty = sum == 0L && missing == 0;
        lastHash = anyPrice || empty ? hash : 0;
        value = anyPrice || empty ? sum : -1L;
        unpriced = missing;
    }

    private static void render(Minecraft client, ContainerScreen chest, GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        String amount;
        if (chest != shown || !EcValue.on() || client.player == null) {
            return;
        }
        EcValue.recompute(chest, client.player.getInventory());
        if (value < 0L) {
            amount = "\u2026";
        } else if (FwmConfig.INSTANCE.moneyHideAmounts) {
            amount = "***";
        } else {
            amount = Fmt.shortMoney(value);
            if (!Lang.isGerman()) {
                amount = amount.replace(',', '.');
            }
        }
        Font font = client.font;
        HandledScreenAccessor at = (HandledScreenAccessor)chest;
        int right = at.fwm$x() + at.fwm$backgroundWidth() - 8;
        String text = Lang.t("Wert: ", "Value: ") + amount;
        int titleEnd = at.fwm$x() + 8 + font.width((FormattedText)chest.getTitle()) + 6;
        if (right - font.width(text) < titleEnd) {
            text = amount;
        }
        int tx = right - font.width(text);
        int ty = at.fwm$y() + 6;
        ctx.text(font, (Component)Component.literal((String)text), tx, ty, -12566464, false);
        if (unpriced > 0 && mouseX >= tx && mouseX < right && mouseY >= ty - 1 && mouseY < ty + 9) {
            ctx.setTooltipForNextFrame(font, (Component)Component.literal((String)(unpriced + Lang.t(" Items ohne Preis", " items without a price"))), mouseX, mouseY);
        }
    }

    static {
        value = -1L;
    }
}
