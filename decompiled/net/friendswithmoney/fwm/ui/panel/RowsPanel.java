/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.input.CharacterEvent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.ui.ConfirmBox;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.CharacterEvent;

public abstract class RowsPanel
extends Panel {
    private static final int SCROLLBAR_W = 3;
    protected final List<Row> rows = new ArrayList();
    protected final ConfirmBox confirm = new ConfirmBox();
    private int scroll;
    private int maxScroll;
    private int rowsShown;

    protected abstract void buildRows();

    @Override
    protected void onResize() {
        this.rows.clear();
        this.buildRows();
        this.rowsShown = Math.max((int)1, (int)Math.min((int)this.rows.size(), (int)(this.height / 26)));
        this.maxScroll = Math.max((int)0, (int)(this.rows.size() - this.rowsShown));
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll, (int)this.scroll));
        this.positionRows();
    }

    private void positionRows() {
        int rowWidth = this.width - (this.maxScroll > 0 ? 5 : 0);
        for (int i = 0; i < this.rows.size(); ++i) {
            int visibleIndex = i - this.scroll;
            if (visibleIndex < 0 || visibleIndex >= this.rowsShown) {
                ((Row)this.rows.get(i)).setBounds(this.x, -1073741824, rowWidth);
                continue;
            }
            ((Row)this.rows.get(i)).setBounds(this.x, this.y + visibleIndex * 26, rowWidth);
        }
    }

    private int lastVisible() {
        return Math.min((int)this.rows.size(), (int)(this.scroll + this.rowsShown));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        for (int i = this.scroll; i < this.lastVisible(); ++i) {
            ((Row)this.rows.get(i)).render(ctx, font, mouseX, mouseY);
        }
        if (this.maxScroll > 0) {
            int listH = this.rowsShown * 26;
            int trackX = this.x + this.width - 3;
            int thumbH = Math.max((int)10, (int)(listH * this.rowsShown / this.rows.size()));
            int thumbY = this.y + (listH - thumbH) * this.scroll / this.maxScroll;
            ctx.fill(trackX, this.y, trackX + 3, this.y + listH, FwmTheme.OFF);
            ctx.fill(trackX, thumbY, trackX + 3, thumbY + thumbH, FwmTheme.ACCENT);
        }
        this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.confirm.open()) {
            return this.confirm.mouseClicked(mouseX, mouseY);
        }
        for (Row row : this.rows) {
            if (!row.isCapturingKeys() || row.isHovered(mouseX, mouseY)) continue;
            row.commitTyped();
        }
        for (int i = this.scroll; i < this.lastVisible(); ++i) {
            if (!((Row)this.rows.get(i)).mouseClicked(mouseX, mouseY, button)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        for (int i = this.scroll; i < this.lastVisible(); ++i) {
            if (!((Row)this.rows.get(i)).mouseDragged(mouseX, mouseY, button)) continue;
            return true;
        }
        return false;
    }

    @Override
    public void mouseReleased() {
        for (Row row : this.rows) {
            row.mouseReleased();
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (this.maxScroll <= 0) {
            return false;
        }
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll, (int)(this.scroll - (int)Math.signum((double)vertical))));
        this.positionRows();
        return true;
    }

    @Override
    public boolean isCapturingKeys() {
        if (this.confirm.open()) {
            return true;
        }
        for (Row row : this.rows) {
            if (!row.isCapturingKeys()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        for (Row row : this.rows) {
            if (!row.isCapturingKeys() || !row.charTyped(input)) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.confirm.open()) {
            return this.confirm.keyPressed(keyCode);
        }
        for (Row row : this.rows) {
            if (!row.isCapturingKeys() || !row.keyPressed(keyCode, scanCode, modifiers)) continue;
            return true;
        }
        return false;
    }
}
