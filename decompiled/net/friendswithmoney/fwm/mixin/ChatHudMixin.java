/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.minecraft.client.gui.components.ChatComponent
 *  net.minecraft.network.chat.Component
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.chat.FakeTags;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={ChatComponent.class})
public class ChatHudMixin {
    @ModifyVariable(method={"addPlayerMessage(Lnet/minecraft/network/chat/Component;Lnet/minecraft/network/chat/MessageSignature;Lnet/minecraft/client/multiplayer/chat/GuiMessageTag;)V"}, at=@At(value="HEAD"), argsOnly=true)
    private Component fwm$markPlayerMessage(Component message) {
        return SevenTv.transform(FakeTags.mark(message));
    }
}
