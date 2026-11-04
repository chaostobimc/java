/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.world.inventory.Slot
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.gen.Accessor
 */
package net.friendswithmoney.fwm.mixin;

import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(value={AbstractContainerScreen.class})
public interface HandledScreenAccessor {
    @Accessor(value="leftPos")
    public int fwm$x();

    @Accessor(value="topPos")
    public int fwm$y();

    @Accessor(value="imageWidth")
    public int fwm$backgroundWidth();

    @Accessor(value="imageHeight")
    public int fwm$backgroundHeight();

    @Accessor(value="hoveredSlot")
    public Slot fwm$focusedSlot();
}
