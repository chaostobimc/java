/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui;

import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public final class ConfirmBox {
    private static final int WIDTH = 150;
    private static final int PAD = 8;
    private static final int LINE_H = 10;
    private static final int BTN_H = 14;
    private static final int BTN_GAP = 6;
    private Component question;
    private Runnable onYes;
    private String yesKey = "fwm.confirm.delete";
    private int yesX;
    private int noX;
    private int btnY;
    private int btnW;

    public boolean open() {
        return this.question != null;
    }

    public void ask(Component question, Runnable onYes) {
        this.ask(question, "fwm.confirm.delete", onYes);
    }

    public void ask(Component question, String yesKey, Runnable onYes) {
        this.question = question;
        this.yesKey = yesKey;
        this.onYes = onYes;
    }

    public void close() {
        this.question = null;
        this.onYes = null;
    }

    public void render(GuiGraphicsExtractor ctx, Font font, int x, int y, int width, int height, int mouseX, int mouseY) {
        if (!this.open()) {
            return;
        }
        int boxW = Math.min((int)150, (int)(width - 8));
        List lines = font.split((FormattedText)this.question, boxW - 16);
        int boxH = 8 + lines.size() * 10 + 6 + 14 + 8;
        int boxX = x + (width - boxW) / 2;
        int boxY = y + (height - boxH) / 2;
        ctx.fill(x, y, x + width, y + height, -1342177280);
        FwmTheme.panel(ctx, boxX, boxY, boxW, boxH);
        int lineY = boxY + 8;
        for (FormattedCharSequence line : lines) {
            ctx.text(font, line, boxX + 8, lineY, FwmTheme.TEXT, false);
            lineY += 10;
        }
        this.btnW = (boxW - 16 - 6) / 2;
        this.btnY = boxY + boxH - 8 - 14;
        this.noX = boxX + 8;
        this.yesX = this.noX + this.btnW + 6;
        this.button(ctx, font, this.noX, this.btnY, this.btnW, (Component)Lang.of("fwm.confirm.cancel", new Object[0]), this.hovered(this.noX, mouseX, mouseY), false);
        this.button(ctx, font, this.yesX, this.btnY, this.btnW, (Component)Lang.of(this.yesKey, new Object[0]), this.hovered(this.yesX, mouseX, mouseY), true);
    }

    private boolean hovered(int bx, int mouseX, int mouseY) {
        return mouseX >= bx && mouseX < bx + this.btnW && mouseY >= this.btnY && mouseY < this.btnY + 14;
    }

    private void button(GuiGraphicsExtractor ctx, Font font, int bx, int by, int bw, Component label, boolean hovered, boolean danger) {
        int edge = danger ? (hovered ? FwmTheme.DISABLED_TEXT : FwmTheme.DISABLED_EDGE) : (hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        FwmTheme.box(ctx, bx, by, bw, 14, danger ? FwmTheme.DISABLED_FILL : FwmTheme.RAISED, edge);
        int color = danger ? FwmTheme.DISABLED_TEXT : (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT);
        int n = bx + (bw - font.width((FormattedText)label)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, label, n, by + (14 - 9) / 2 + 1, color, false);
    }

    public boolean mouseClicked(double mouseX, double mouseY) {
        boolean yes;
        if (!this.open()) {
            return false;
        }
        boolean bl = yes = mouseX >= (double)this.yesX && mouseX < (double)(this.yesX + this.btnW) && mouseY >= (double)this.btnY && mouseY < (double)(this.btnY + 14);
        if (yes) {
            Runnable action = this.onYes;
            this.close();
            if (action != null) {
                action.run();
            }
            return true;
        }
        if (mouseX >= (double)this.noX && mouseX < (double)(this.noX + this.btnW) && mouseY >= (double)this.btnY && mouseY < (double)(this.btnY + 14)) {
            this.close();
        }
        return true;
    }

    public boolean keyPressed(int keyCode) {
        if (!this.open()) {
            return false;
        }
        if (keyCode == 41) {
            this.close();
            return true;
        }
        if (keyCode == 40 || keyCode == 88) {
            Runnable action = this.onYes;
            this.close();
            if (action != null) {
                action.run();
            }
        }
        return true;
    }
}
