/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.List
 *  net.minecraft.client.gui.components.PlayerTabOverlay
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.network.chat.Component
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import java.util.List;
import net.friendswithmoney.fwm.net.Presence;
import net.minecraft.client.gui.components.PlayerTabOverlay;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={PlayerTabOverlay.class})
public abstract class PlayerListHudMixin {
    @Inject(method={"getPlayerInfos"}, at={@At(value="RETURN")})
    private void fwm$rows(CallbackInfoReturnable<List<PlayerInfo>> cir) {
        Presence.tabRows((List<PlayerInfo>)((List)cir.getReturnValue()));
    }

    @Inject(method={"getNameForDisplay"}, at={@At(value="RETURN")}, cancellable=true)
    private void fwm$logo(PlayerInfo entry, CallbackInfoReturnable<Component> cir) {
        if (entry == null || entry.getProfile() == null) {
            return;
        }
        Component named = (Component)cir.getReturnValue();
        cir.setReturnValue((Object)Presence.decorateTab(entry.getProfile().id(), named));
    }
}
