/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class PriceRow
extends Row {
    private static final int BOX_W = 52;
    private static final int BOX_H = 14;
    private static final int MAX_TYPED = 10;
    private final Supplier<String> siteBuy;
    private final Supplier<String> siteSell;
    private final Supplier<String[]> own;
    private final Consumer<String[]> setter;
    private int editing = -1;
    private StringBuilder typed;

    public PriceRow(Component label, Supplier<String> siteBuy, Supplier<String> siteSell, Supplier<String[]> own, Consumer<String[]> setter) {
        super(label);
        this.siteBuy = siteBuy;
        this.siteSell = siteSell;
        this.own = own;
        this.setter = setter;
    }

    private int boxX(int i) {
        return this.controlRight() - (2 - i) * 52 - (1 - i) * 4;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        String[] mine = (String[])this.own.get();
        int by = this.y + 6;
        for (int i = 0; i < 2; ++i) {
            String shown;
            String set;
            int bx = this.boxX(i);
            boolean hovered = mouseX >= bx && mouseX < bx + 52 && mouseY >= by && mouseY < by + 14;
            boolean typing = this.editing == i;
            FwmTheme.box(ctx, bx, by, 52, 14, FwmTheme.RAISED, typing || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
            String string = set = mine == null || mine[i] == null || mine[i].isBlank() ? null : mine[i];
            String string2 = typing ? String.valueOf((Object)this.typed) + "_" : (set != null ? set : (shown = i == 0 ? (String)this.siteBuy.get() : (String)this.siteSell.get()));
            int color = typing ? FwmTheme.ACCENT : (set != null ? (i == 0 ? -16711856 : -43691) : FwmTheme.TEXT_DIM);
            MutableComponent text = Component.literal((String)font.plainSubstrByWidth(shown == null ? "-" : shown, 48));
            ctx.text(font, (Component)text, bx + (52 - font.width((FormattedText)text)) / 2, by + 4, color, false);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int by = this.y + 6;
        for (int i = 0; i < 2; ++i) {
            int bx = this.boxX(i);
            if (button != 0 || !(mouseX >= (double)bx) || !(mouseX < (double)(bx + 52)) || !(mouseY >= (double)by) || !(mouseY < (double)(by + 14))) continue;
            this.commit();
            this.editing = i;
            this.typed = new StringBuilder();
            return true;
        }
        this.commit();
        return false;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.editing >= 0;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.editing < 0) {
            return false;
        }
        if (keyCode == 41) {
            this.editing = -1;
            this.typed = null;
            return true;
        }
        if (keyCode == 40 || keyCode == 88 || keyCode == 43) {
            this.commit();
            return true;
        }
        if (keyCode == 42) {
            if (!this.typed.isEmpty()) {
                this.typed.setLength(this.typed.length() - 1);
            }
            return true;
        }
        if (this.typed.length() >= 10) {
            return true;
        }
        int c = 0;
        if (GLFW.digit(keyCode) >= 0) {
            c = (char)(48 + GLFW.digit(keyCode));
        } else if (keyCode == 44) {
            c = 44;
        } else if (keyCode == 46 || keyCode == 330) {
            c = 46;
        } else if (keyCode == 14) {
            c = 75;
        } else if (keyCode == 16) {
            c = 77;
        } else if (keyCode == 5) {
            c = 66;
        }
        if (c != 0) {
            this.typed.append((char)c);
        }
        return true;
    }

    @Override
    public void commitTyped() {
        this.commit();
    }

    private void commit() {
        if (this.editing < 0) {
            return;
        }
        String[] mine = (String[])this.own.get();
        String[] next = mine == null ? new String[2] : (String[])mine.clone();
        next[this.editing] = this.typed.toString().trim();
        this.editing = -1;
        this.typed = null;
        this.setter.accept((Object)next);
    }
}
