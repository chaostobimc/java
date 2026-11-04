/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  net.minecraft.client.gui.components.DebugScreenOverlay
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.ModifyVariable
 */
package net.friendswithmoney.fwm.mixin;

import java.util.List;
import net.friendswithmoney.fwm.antileak.FakeCoords;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(value={DebugScreenOverlay.class})
public class DebugHudMixin {
    @ModifyVariable(method={"extractLines"}, at=@At(value="HEAD"), argsOnly=true)
    private List<String> fwm$fakeCoords(List<String> lines) {
        return FakeCoords.rewrite(lines);
    }
}
