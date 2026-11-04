/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.Objects
 *  java.util.function.BooleanSupplier
 *  java.util.function.Consumer
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class ToggleRow
extends Row {
    private static final int MAX_PILL_W = 46;
    private static final int PILL_H = 14;
    private static final int KNOB = 10;
    private final BooleanSupplier getter;
    private final Consumer<Boolean> setter;

    public ToggleRow(Component label, BooleanSupplier getter, Consumer<Boolean> setter) {
        super(label);
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean on = this.getter.getAsBoolean();
        int pillW = Math.min((int)46, (int)this.controlWidth());
        int px = this.controlRight() - pillW;
        int py = this.y + 6;
        FwmTheme.pill(ctx, px, py, pillW, 14, on ? FwmTheme.ON : FwmTheme.OFF);
        int knobX = on ? px + pillW - 2 - 10 : px + 2;
        FwmTheme.pill(ctx, knobX, py + 2, 10, 10, on ? -16375537 : FwmTheme.TEXT);
        MutableComponent state = Lang.of(on ? "fwm.state.on" : "fwm.state.off", new Object[0]);
        int room = pillW - 10 - 4;
        int textX = (on ? px + 2 : knobX + 10) + (room - font.width((FormattedText)state)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)state, textX, py + (14 - 9) / 2 + 1, on ? -16375537 : FwmTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            return false;
        }
        this.setter.accept((Object)(!this.getter.getAsBoolean() ? 1 : 0));
        return true;
    }
}
