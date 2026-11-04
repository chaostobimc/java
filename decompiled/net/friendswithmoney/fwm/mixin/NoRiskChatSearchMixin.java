/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Pseudo
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.seventv.SevenTv;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Pseudo
@Mixin(targets={"gg.norisk.client.v2.modules.chatutils.ChatSearch"}, remap=false)
public abstract class NoRiskChatSearchMixin {
    @Inject(method={"isAvailable"}, at={@At(value="HEAD")}, cancellable=true, require=0, remap=false)
    private void fwm$hideForEmotes(CallbackInfoReturnable<Boolean> cir) {
        if (SevenTv.on()) {
            cir.setReturnValue((Object)false);
        }
    }
}
