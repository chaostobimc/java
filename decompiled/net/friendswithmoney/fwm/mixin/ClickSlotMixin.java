/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Throwable
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerInput
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.money.MoneyEvents;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={MultiPlayerGameMode.class})
public class ClickSlotMixin {
    @Inject(method={"handleContainerInput"}, at={@At(value="HEAD")})
    private void fwm$handleContainerInput(int containerId, int slotId, int button, ContainerInput action, Player player, CallbackInfo ci) {
        try {
            AbstractContainerMenu handler;
            AbstractContainerMenu abstractContainerMenu = handler = player == null ? null : player.containerMenu;
            if (handler == null || slotId < 0 || slotId >= handler.slots.size()) {
                return;
            }
            MoneyEvents.offerClicked(handler.getSlot(slotId).getItem());
            MoneyEvents.menuClicked(handler);
        }
        catch (Throwable throwable) {
            // empty catch block
        }
    }
}
