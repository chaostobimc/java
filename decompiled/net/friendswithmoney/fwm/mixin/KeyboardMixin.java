/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  net.minecraft.client.KeyboardHandler
 *  net.minecraft.client.input.KeyEvent
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.antileak.FakeCoords;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={KeyboardHandler.class})
public class KeyboardMixin {
    @Inject(method={"handleDebugKeys"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$noCopyPosition(KeyEvent input, CallbackInfoReturnable<Boolean> cir) {
        if (input.key() == 6 && FakeCoords.active()) {
            cir.setReturnValue((Object)true);
        }
    }

    @Inject(method={"copyRecreateCommand"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$noCopyLookAt(boolean hasQueryPermission, boolean queryServer, CallbackInfo ci) {
        if (FakeCoords.active()) {
            ci.cancel();
        }
    }
}
