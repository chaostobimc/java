/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Objects
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.Objects;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class SoonPanel
extends Panel {
    @Override
    public Component title() {
        return Lang.of("fwm.panel.soon", new Object[0]);
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        MutableComponent hint = Lang.of("fwm.panel.soonHint", new Object[0]);
        int n = this.x + (this.width - font.width((FormattedText)hint)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)hint, n, this.y + (this.height - 9) / 2, FwmTheme.TEXT_DIM, false);
    }
}
