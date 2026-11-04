/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.function.BooleanSupplier
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.function.BooleanSupplier;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;

public class SectionRow
extends Row {
    private final BooleanSupplier open;
    private final Runnable toggle;
    private final String hint;

    public SectionRow(Component label, String hint, BooleanSupplier open, Runnable toggle) {
        super(label);
        this.hint = hint;
        this.open = open;
        this.toggle = toggle;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean on = this.open.getAsBoolean();
        boolean hovered = this.isHovered(mouseX, mouseY);
        FwmTheme.box(ctx, this.x, this.y + 2, this.width, 22, on ? FwmTheme.RAISED : (hovered ? FwmTheme.HOVER : FwmTheme.INSET), on || hovered ? FwmTheme.ACCENT : FwmTheme.EDGE);
        String arrow = on ? "\u25bc" : "\u25b6";
        int ty = this.y + 9;
        ctx.text(font, (Component)Component.literal((String)arrow), this.x + 7, ty, FwmTheme.ACCENT, false);
        ctx.text(font, (Component)this.label.copy().withStyle(ChatFormatting.BOLD), this.x + 19, ty, FwmTheme.TEXT, false);
        if (this.hint != null && !this.hint.isEmpty()) {
            int room = this.width - 19 - font.width((FormattedText)this.label.copy().withStyle(ChatFormatting.BOLD)) - 20;
            String shown = font.plainSubstrByWidth(this.hint, Math.max((int)0, (int)room));
            ctx.text(font, (Component)Component.literal((String)shown), this.x + this.width - 8 - font.width(shown), ty, FwmTheme.TEXT_DIM, false);
        }
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            return false;
        }
        this.toggle.run();
        return true;
    }
}
