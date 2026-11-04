/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TabStrip {
    public static final int HEIGHT = 13;
    private final List<String> labelKeys;
    private int active;
    private int x;
    private int y;
    private int width;

    public TabStrip(List<String> labelKeys) {
        this.labelKeys = labelKeys;
    }

    public void setBounds(int x, int y, int width) {
        this.x = x;
        this.y = y;
        this.width = width;
    }

    public int active() {
        return this.active;
    }

    public void setActive(int index) {
        if (index >= 0 && index < this.labelKeys.size()) {
            this.active = index;
        }
    }

    public int size() {
        return this.labelKeys.size();
    }

    private int slotW() {
        return Math.max((int)1, (int)(this.width / this.labelKeys.size()));
    }

    private int slotX(int slot) {
        return this.x + slot * this.slotW();
    }

    public int indexAt(double mouseX, double mouseY) {
        if (mouseY < (double)this.y || mouseY >= (double)(this.y + 13)) {
            return -1;
        }
        for (int i = 0; i < this.labelKeys.size(); ++i) {
            if (!(mouseX >= (double)this.slotX(i)) || !(mouseX < (double)(this.slotX(i) + this.slotW()))) continue;
            return i;
        }
        return -1;
    }

    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int hovered = this.indexAt(mouseX, mouseY);
        for (int i = 0; i < this.labelKeys.size(); ++i) {
            boolean selected = i == this.active;
            int sx = this.slotX(i);
            int sw = this.slotW() - 2;
            FwmTheme.box(ctx, sx, this.y, sw, 13, selected ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, selected || i == hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
            String label = font.plainSubstrByWidth(Lang.str((String)this.labelKeys.get(i), new Object[0]), sw - 4);
            MutableComponent mutableComponent = Component.literal((String)label);
            int n = sx + (sw - font.width(label)) / 2;
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)mutableComponent, n, this.y + (13 - 9) / 2 + 1, selected ? FwmTheme.TEXT : (i == hovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM), false);
        }
    }
}
