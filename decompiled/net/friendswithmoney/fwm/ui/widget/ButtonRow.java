/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.Objects
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.Objects;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class ButtonRow
extends Row {
    private static final int MAX_BTN_W = 96;
    private static final int BTN_H = 14;
    private final Supplier<Component> caption;
    private final Runnable action;

    public ButtonRow(Component label, Supplier<Component> caption, Runnable action) {
        super(label);
        this.caption = caption;
        this.action = action;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int btnW = Math.min((int)96, (int)this.controlWidth());
        int bx = this.controlRight() - btnW;
        int by = this.y + 6;
        boolean hovered = mouseX >= bx && mouseX < bx + btnW && mouseY >= by && mouseY < by + 14;
        FwmTheme.box(ctx, bx, by, btnW, 14, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent text = Component.literal((String)font.plainSubstrByWidth(((Component)this.caption.get()).getString(), btnW - 6));
        int n = bx + (btnW - font.width((FormattedText)text)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)text, n, by + (14 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            return false;
        }
        this.action.run();
        return true;
    }
}
