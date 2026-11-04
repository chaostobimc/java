/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Objects
 *  java.util.function.IntConsumer
 *  java.util.function.IntFunction
 *  java.util.function.IntSupplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.Objects;
import java.util.function.IntConsumer;
import java.util.function.IntFunction;
import java.util.function.IntSupplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class SliderRow
extends Row {
    private static final int VALUE_W = 52;
    private static final int MIN_TRACK_W = 24;
    private static final int TRACK_H = 2;
    private static final int KNOB_W = 5;
    private static final int KNOB_H = 11;
    private static final int BOX_H = 12;
    private static final int MAX_TYPED = 8;
    private final int min;
    private final int max;
    private final IntSupplier getter;
    private final IntConsumer setter;
    private final IntFunction<Component> display;
    private final Parser parser;
    private boolean dragging;
    private StringBuilder typed;

    public SliderRow(Component label, int min, int max, IntSupplier getter, IntConsumer setter, IntFunction<Component> display) {
        this(label, min, max, getter, setter, display, null);
    }

    public SliderRow(Component label, int min, int max, IntSupplier getter, IntConsumer setter, IntFunction<Component> display, Parser parser) {
        super(label);
        this.min = min;
        this.max = max;
        this.getter = getter;
        this.setter = setter;
        this.display = display;
        this.parser = parser;
    }

    private int trackX() {
        return this.controlX();
    }

    private int trackWidth() {
        return Math.max((int)24, (int)(this.controlWidth() - 52));
    }

    private int valueX() {
        return this.trackX() + this.trackWidth() + 6;
    }

    private int valueWidth() {
        return Math.max((int)20, (int)(this.controlRight() - this.valueX()));
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int tx = this.trackX();
        int tw = this.trackWidth();
        int ty = this.y + 12;
        float progress = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)((float)(this.getter.getAsInt() - this.min) / (float)(this.max - this.min))));
        int filled = Math.round((float)((float)tw * progress));
        ctx.fill(tx, ty, tx + tw, ty + 2, FwmTheme.OFF);
        ctx.fill(tx, ty, tx + filled, ty + 2, FwmTheme.ACCENT);
        FwmTheme.pill(ctx, tx + filled - 2, this.y + 7, 5, 11, FwmTheme.ACCENT);
        if (this.parser == null) {
            Component value = (Component)this.display.apply(this.getter.getAsInt());
            ctx.text(font, value, this.controlRight() - font.width((FormattedText)value), this.textY(font), FwmTheme.TEXT_DIM, false);
            return;
        }
        int bx = this.valueX();
        int bw = this.valueWidth();
        int by = this.y + 7;
        boolean editing = this.typed != null;
        boolean hovered = mouseX >= bx && mouseX < bx + bw && mouseY >= by && mouseY < by + 12;
        FwmTheme.box(ctx, bx, by, bw, 12, FwmTheme.RAISED, editing || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        String shown = editing ? String.valueOf((Object)this.typed) + "_" : ((Component)this.display.apply(this.getter.getAsInt())).getString();
        MutableComponent text = Component.literal((String)font.plainSubstrByWidth(shown, bw - 4));
        int n = bx + (bw - font.width((FormattedText)text)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)text, n, by + (12 - 9) / 2 + 1, editing || hovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            this.commit();
            return false;
        }
        if (this.parser != null && mouseX >= (double)this.valueX()) {
            this.typed = new StringBuilder();
            return true;
        }
        if (mouseX < (double)(this.trackX() - 5) || mouseX > (double)(this.trackX() + this.trackWidth() + 5)) {
            return false;
        }
        this.commit();
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
        this.dragging = false;
    }

    @Override
    public void commitTyped() {
        this.commit();
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
        if (keyCode == 40 || keyCode == 88) {
            this.commit();
            return true;
        }
        if (keyCode == 42) {
            if (!this.typed.isEmpty()) {
                this.typed.setLength(this.typed.length() - 1);
            }
            return true;
        }
        if (this.typed.length() >= 8) {
            return true;
        }
        int digit = SliderRow.digit(keyCode);
        if (digit >= 0) {
            if (digit != 0 || !this.typed.isEmpty()) {
                this.typed.append((char)(48 + digit));
            }
            return true;
        }
        char unit = SliderRow.unit(keyCode);
        if (unit != '\u0000' && !this.typed.isEmpty() && Character.isDigit((char)this.typed.charAt(this.typed.length() - 1))) {
            this.typed.append(unit);
        }
        return true;
    }

    private static int digit(int keyCode) {
        return GLFW.digit(keyCode);
    }

    private static char unit(int keyCode) {
        return switch (keyCode) {
            case 16 -> 'm';
            case 11 -> 'h';
            case 22 -> 's';
            default -> '\u0000';
        };
    }

    private void commit() {
        if (this.typed == null) {
            return;
        }
        String text = this.typed.toString();
        this.typed = null;
        if (text.isEmpty() || this.parser == null) {
            return;
        }
        long value = this.parser.parse(text);
        this.setter.accept((int)Math.max((long)this.min, (long)Math.min((long)this.max, (long)value)));
    }

    private void applyFromMouse(double mouseX) {
        double progress = (mouseX - (double)this.trackX()) / (double)this.trackWidth();
        progress = Math.max((double)0.0, (double)Math.min((double)1.0, (double)progress));
        this.setter.accept(this.min + (int)Math.round((double)(progress * (double)(this.max - this.min))));
    }

    public static interface Parser {
        public long parse(String var1);
    }
}
