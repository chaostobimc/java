/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.util.function.DoubleConsumer
 *  java.util.function.DoubleSupplier
 *  java.util.function.IntSupplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.DoubleConsumer;
import java.util.function.DoubleSupplier;
import java.util.function.IntSupplier;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ColorRow
extends Row {
    private static final int SWATCH = 12;
    private static final int GAP = 4;
    private static final int TRACK_H = 8;
    private static final int KNOB_W = 5;
    private static final int KNOB_H = 12;
    private static final int MIN_TRACK_W = 30;
    private final DoubleSupplier getter;
    private final DoubleConsumer setter;
    private final Gradient gradient;
    private final DoubleSupplier fallback;
    private final IntSupplier swatch;
    private final Runnable reset;
    private boolean dragging;

    public ColorRow(Component label, DoubleSupplier getter, DoubleConsumer setter, Gradient gradient, DoubleSupplier fallback, IntSupplier swatch, Runnable reset) {
        super(label);
        this.getter = getter;
        this.setter = setter;
        this.gradient = gradient;
        this.fallback = fallback;
        this.swatch = swatch;
        this.reset = reset;
    }

    private boolean hasSwatch() {
        return this.reset != null && this.swatch != null;
    }

    private int swatchX() {
        return this.controlX();
    }

    private int trackX() {
        return this.controlX() + (this.hasSwatch() ? 16 : 0);
    }

    private int trackWidth() {
        return Math.max((int)30, (int)(this.controlRight() - this.trackX()));
    }

    private float shown() {
        double value = this.getter.getAsDouble();
        if (value < 0.0 && this.fallback != null) {
            value = this.fallback.getAsDouble();
        }
        return (float)Math.max((double)0.0, (double)Math.min((double)1.0, (double)value));
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int tx = this.trackX();
        int tw = this.trackWidth();
        int ty = this.y + 9;
        for (int i = 0; i < tw; ++i) {
            ctx.fill(tx + i, ty, tx + i + 1, ty + 8, 0xFF000000 | this.gradient.at((float)i / (float)tw));
        }
        int knobX = tx + Math.round((float)(this.shown() * (float)tw)) - 2;
        FwmTheme.pill(ctx, knobX, this.y + 7, 5, 12, FwmTheme.TEXT);
        if (!this.hasSwatch()) {
            return;
        }
        boolean hovered = mouseX >= this.swatchX() && mouseX < this.swatchX() + 12 && mouseY >= this.y + 7 && mouseY < this.y + 19;
        FwmTheme.box(ctx, this.swatchX(), this.y + 7, 12, 12, 0xFF000000 | this.swatch.getAsInt() & 0xFFFFFF, hovered ? FwmTheme.TEXT : FwmTheme.OFF);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (this.hasSwatch() && mouseX >= (double)this.swatchX() && mouseX < (double)(this.swatchX() + 12)) {
            this.reset.run();
            ConfigManager.save();
            return true;
        }
        if (mouseX < (double)(this.trackX() - 5)) {
            return false;
        }
        this.dragging = true;
        this.applyFromMouse(mouseX);
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (!this.dragging) {
            return false;
        }
        this.applyFromMouse(mouseX);
        return true;
    }

    @Override
    public void mouseReleased() {
        if (!this.dragging) {
            return;
        }
        this.dragging = false;
        ConfigManager.save();
    }

    private void applyFromMouse(double mouseX) {
        double progress = (mouseX - (double)this.trackX()) / (double)this.trackWidth();
        this.setter.accept(Math.max((double)0.0, (double)Math.min((double)1.0, (double)progress)));
    }

    public static interface Gradient {
        public int at(float var1);
    }
}
