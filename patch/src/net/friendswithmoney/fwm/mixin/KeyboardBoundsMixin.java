package net.friendswithmoney.fwm.mixin;

import java.nio.ByteBuffer;
import org.lwjgl.sdl.SDLKeyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Minecraft 26.3 indexes SDL's keyboard state with saved key codes. */
@Mixin(targets = "com.mojang.blaze3d.platform.InputConstants", remap = false)
public abstract class KeyboardBoundsMixin {
    @Inject(method = "isKeyDown(I)Z", at = @At("HEAD"), cancellable = true, remap = false)
    private static void fwm$guardKeyboardIndex(int key, CallbackInfoReturnable<Boolean> result) {
        ByteBuffer state = SDLKeyboard.SDL_GetKeyboardState();
        if (state == null || key < 0 || key >= state.limit()) {
            result.setReturnValue(false);
        }
    }
}
