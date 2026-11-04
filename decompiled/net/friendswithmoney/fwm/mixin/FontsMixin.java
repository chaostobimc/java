/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  net.minecraft.client.gui.GlyphSource
 *  net.minecraft.network.chat.FontDescription
 *  net.minecraft.network.chat.FontDescription$Resource
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import net.friendswithmoney.fwm.seventv.EmoteGlyphs;
import net.minecraft.client.gui.GlyphSource;
import net.minecraft.network.chat.FontDescription;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets={"net.minecraft.client.gui.font.FontManager$CachedFontProvider"})
public abstract class FontsMixin {
    @Inject(method={"getGlyphSource"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$emoteGlyphs(FontDescription source, CallbackInfoReturnable<GlyphSource> cir) {
        FontDescription.Resource font;
        GlyphSource emote;
        if (source instanceof FontDescription.Resource && (emote = EmoteGlyphs.sourceFor((font = (FontDescription.Resource)source).id())) != null) {
            cir.setReturnValue((Object)emote);
        }
    }
}
