/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.util.List
 *  net.minecraft.client.gui.components.ChatComponent
 *  net.minecraft.client.multiplayer.chat.GuiMessage
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 *  org.spongepowered.asm.mixin.gen.Invoker
 */
package net.friendswithmoney.fwm.mixin;

import java.util.List;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.client.multiplayer.chat.GuiMessage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(value={ChatComponent.class})
public interface ChatHudAccessor {
    @Accessor(value="allMessages")
    public List<GuiMessage> fwm$messages();

    @Invoker(value="refreshTrimmedMessages")
    public void fwm$refresh();
}
