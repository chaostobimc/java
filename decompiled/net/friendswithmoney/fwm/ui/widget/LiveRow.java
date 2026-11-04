/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.function.IntSupplier
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.IntSupplier;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class LiveRow
extends Row {
    private final Supplier<Component> value;
    private final IntSupplier colour;

    public LiveRow(Component label, Supplier<Component> value) {
        this(label, value, () -> FwmTheme.ACCENT);
    }

    public LiveRow(Component label, Supplier<Component> value, IntSupplier colour) {
        super(label);
        this.value = value;
        this.colour = colour;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        Component text = (Component)this.value.get();
        int room = this.controlRight() - (this.x + this.padding() + font.width((FormattedText)this.trimmedLabel(font)) + 8);
        String shown = font.plainSubstrByWidth(text.getString(), Math.max((int)10, (int)room));
        ctx.text(font, (Component)Component.literal((String)shown), this.controlRight() - font.width(shown), this.textY(font), this.colour.getAsInt(), false);
    }
}
