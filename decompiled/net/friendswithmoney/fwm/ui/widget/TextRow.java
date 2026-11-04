/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.widget;

import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class TextRow
extends Row {
    private final Component detail;
    private final boolean heading;

    public TextRow(Component label) {
        this(label, null, true);
    }

    public TextRow(Component label, Component detail) {
        this(label, detail, false);
    }

    private TextRow(Component label, Component detail, boolean heading) {
        super(label);
        this.detail = detail;
        this.heading = heading;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int textY = this.textY(font);
        int left = this.x + this.padding();
        int room = this.width - 2 * this.padding();
        if (this.heading) {
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(this.label.getString(), room)), left, textY, FwmTheme.ACCENT, false);
            return;
        }
        String lead = this.label.getString();
        ctx.text(font, lead, left, textY, FwmTheme.TEXT_DIM, false);
        if (this.detail == null) {
            return;
        }
        int detailX = left + font.width(lead) + 5;
        int detailRoom = Math.max((int)8, (int)(this.x + this.width - this.padding() - detailX));
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(this.detail.getString(), detailRoom)), detailX, textY, FwmTheme.TEXT, false);
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
    }
}
