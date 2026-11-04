/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.llamalad7.mixinextras.injector.wrapoperation.Operation
 *  com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation
 *  com.mojang.brigadier.suggestion.Suggestion
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Void
 *  java.util.List
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.CommandSuggestions$SuggestionsList
 *  net.minecraft.client.renderer.Rect2i
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  org.spongepowered.asm.mixin.Final
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 */
package net.friendswithmoney.fwm.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.mojang.brigadier.suggestion.Suggestion;
import java.util.List;
import net.friendswithmoney.fwm.net.FakeList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.CommandSuggestions;
import net.minecraft.client.renderer.Rect2i;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value={CommandSuggestions.SuggestionsList.class})
public abstract class SuggestionWindowMixin {
    @Shadow
    @Final
    private Rect2i rect;
    @Shadow
    @Final
    private List<Suggestion> suggestionList;

    @Inject(method={"<init>"}, at={@At(value="RETURN")})
    private void fwm$widen(CallbackInfo ci) {
        Font font = Minecraft.getInstance().font;
        int widest = this.rect.getWidth();
        for (Suggestion suggestion : this.suggestionList) {
            Component badge = FakeList.realBadge(suggestion.getText());
            if (badge == null) continue;
            widest = Math.max((int)widest, (int)(font.width(suggestion.getText()) + font.width((FormattedText)badge) + 2));
        }
        this.rect.setWidth(widest);
    }

    @WrapOperation(method={"extractRenderState"}, at={@At(value="INVOKE", target="Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Ljava/lang/String;III)V")})
    private void fwm$badge(GuiGraphicsExtractor ctx, Font font, String text, int x, int y, int color, Operation<Void> original) {
        original.call(new Object[]{ctx, font, text, x, y, color});
        Component badge = FakeList.realBadge(text);
        if (badge != null) {
            ctx.text(font, badge, x + font.width(text), y, -11141291);
        }
    }
}
