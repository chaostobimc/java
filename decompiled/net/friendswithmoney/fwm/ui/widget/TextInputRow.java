/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class TextInputRow
extends Row {
    private static final int MAX_BOX_W = 120;
    private static final int BOX_H = 14;
    private final Supplier<String> getter;
    private final Consumer<String> setter;
    private final String empty;
    private final int max;
    private StringBuilder typed;

    public TextInputRow(Component label, Supplier<String> getter, Consumer<String> setter, String empty, int max) {
        super(label);
        this.getter = getter;
        this.setter = setter;
        this.empty = empty;
        this.max = max;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean blank;
        int boxW = Math.min((int)120, (int)this.controlWidth());
        int bx = this.controlRight() - boxW;
        int by = this.y + 6;
        boolean hovered = mouseX >= bx && mouseX < bx + boxW && mouseY >= by && mouseY < by + 14;
        FwmTheme.box(ctx, bx, by, boxW, 14, FwmTheme.RAISED, this.typed != null || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        String value = (String)this.getter.get();
        boolean bl = blank = this.typed == null && (value == null || value.isBlank());
        String shown = this.typed != null ? String.valueOf((Object)this.typed) + "_" : (blank ? this.empty : value);
        MutableComponent text = Component.literal((String)font.plainSubstrByWidth(shown, boxW - 6));
        ctx.text(font, (Component)text, bx + 4, by + 4, this.typed != null ? FwmTheme.ACCENT : (blank ? FwmTheme.TEXT_DIM : FwmTheme.TEXT), false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int boxW = Math.min((int)120, (int)this.controlWidth());
        int bx = this.controlRight() - boxW;
        int by = this.y + 6;
        if (button == 0 && mouseX >= (double)bx && mouseX < (double)(bx + boxW) && mouseY >= (double)by && mouseY < (double)(by + 14)) {
            String value = (String)this.getter.get();
            this.typed = new StringBuilder(value == null ? "" : value);
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
        } else if (keyCode == 40 || keyCode == 88) {
            this.commit();
        } else if (keyCode == 42 && !this.typed.isEmpty()) {
            this.typed.setLength(this.typed.length() - 1);
        }
        return true;
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        if (this.typed == null) {
            return false;
        }
        if (this.typed.length() < this.max && input.isAllowedChatCharacter()) {
            this.typed.append(input.codepointAsString());
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
        String text = this.typed.toString().trim();
        this.typed = null;
        this.setter.accept((Object)text);
    }
}
