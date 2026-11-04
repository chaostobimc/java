/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.function.IntConsumer
 *  java.util.function.IntSupplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.IntConsumer;
import java.util.function.IntSupplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class HexRow
extends Row {
    private static final int BOX_W = 64;
    private static final int BOX_H = 14;
    private final IntSupplier getter;
    private final IntConsumer setter;
    private StringBuilder typed;

    public HexRow(Component label, IntSupplier getter, IntConsumer setter) {
        super(label);
        this.getter = getter;
        this.setter = setter;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int bx = this.controlRight() - 64;
        int by = this.y + 6;
        boolean hovered = mouseX >= bx - 16 && mouseX < bx + 64 && mouseY >= by && mouseY < by + 14;
        int rgb = this.getter.getAsInt() & 0xFFFFFF;
        ctx.fill(bx - 16, by + 1, bx - 4, by + 14 - 1, 0xFF000000 | rgb);
        FwmTheme.box(ctx, bx, by, 64, 14, FwmTheme.RAISED, this.typed != null || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        String shown = this.typed != null ? "#" + String.valueOf((Object)this.typed) + "_" : String.format((String)"#%06X", (Object[])new Object[]{rgb});
        ctx.text(font, (Component)Component.literal((String)shown), bx + (64 - font.width(shown)) / 2, by + 4, this.typed != null ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int bx = this.controlRight() - 64;
        int by = this.y + 6;
        if (button == 0 && mouseX >= (double)(bx - 16) && mouseX < (double)(bx + 64) && mouseY >= (double)by && mouseY < (double)(by + 14)) {
            this.typed = new StringBuilder();
            return true;
        }
        this.commit();
        return false;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.typed != null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.typed == null) {
            return false;
        }
        if (keyCode == 41) {
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
        if (this.typed.length() >= 6) {
            return true;
        }
        char c = '\u0000';
        if (GLFW.digit(keyCode) >= 0) {
            c = (char)(48 + GLFW.digit(keyCode));
        } else if (GLFW.letter(keyCode) >= 'A' && GLFW.letter(keyCode) <= 'F') {
            c = GLFW.letter(keyCode);
        }
        if (c != '\u0000') {
            this.typed.append(c);
        }
        return true;
    }

    @Override
    public void commitTyped() {
        this.commit();
    }

    private void commit() {
        if (this.typed == null) {
            return;
        }
        String hex = this.typed.toString();
        this.typed = null;
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0) + hex.charAt(1) + hex.charAt(1) + hex.charAt(2) + hex.charAt(2);
        }
        if (hex.length() == 6) {
            this.setter.accept(Integer.parseInt((String)hex, (int)16));
        }
    }
}
