/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.entity.LivingEntityRenderer
 *  net.minecraft.world.entity.LivingEntity
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.net.Presence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={LivingEntityRenderer.class})
public abstract class LivingEntityRendererMixin {
    @Inject(method={"shouldShowName(Lnet/minecraft/world/entity/LivingEntity;D)Z"}, at={@At(value="RETURN")}, cancellable=true)
    private void fwm$ownLabel(LivingEntity entity, double squaredDistance, CallbackInfoReturnable<Boolean> cir) {
        if (cir.getReturnValueZ()) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (entity != client.player || entity != client.getCameraEntity()) {
            return;
        }
        if (client.options.getCameraType().isFirstPerson()) {
            return;
        }
        if (entity.isInvisible() || entity.isDiscrete() || !Presence.showOwn()) {
            return;
        }
        cir.setReturnValue((Object)true);
    }
}
