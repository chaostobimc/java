/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.colorcode.ColorCode;
import net.friendswithmoney.fwm.colorcode.Templates;
import net.friendswithmoney.fwm.ui.ConfirmBox;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.ColorCodePanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class ColorCodeTemplatesPanel
extends Panel {
    private static final int BTN_H = 13;
    private static final int LIST_Y = 18;
    private static final int ROW_H = 16;
    private static final int SCROLLBAR_W = 4;
    private static final int DELETE_W = 12;
    private static final int TAG_W = 12;
    private static final long NOTE_MS = 2000L;
    private final ColorCodePanel.User user;
    private final ConfirmBox confirm = new ConfirmBox();
    private int scroll;
    private Templates.Added note;
    private long noteAt;

    public ColorCodeTemplatesPanel(ColorCodePanel.User user) {
        this.user = user;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.colorcodes.templates", new Object[0]);
    }

    private List<Templates.Template> templates() {
        return Templates.all();
    }

    private int rowsVisible() {
        return Math.max((int)1, (int)((this.height - 18) / 16));
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.templates().size() - this.rowsVisible()));
    }

    private Templates.Template at(int index) {
        return (Templates.Template)this.templates().get(this.templates().size() - 1 - index);
    }

    @Override
    protected void onResize() {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)this.scroll));
    }

    private int saveW(Font font) {
        return font.width((FormattedText)Lang.of("fwm.colorcodes.save", new Object[0])) + 12;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int sw = this.saveW(font);
        boolean overSave = !this.confirm.open() && ColorCodePanel.over(mouseX, mouseY, this.x, this.y, sw, 13);
        ColorCodePanel.button(ctx, font, this.x, this.y, sw, 13, (Component)Lang.of("fwm.colorcodes.save", new Object[0]), overSave, false, true);
        String count = Lang.str("fwm.colorcodes.count", this.templates().size(), 50);
        ctx.text(font, count, this.x + this.width - font.width(count), this.y + 3, FwmTheme.TEXT_DIM, false);
        if (this.note != null && System.currentTimeMillis() - this.noteAt < 2000L) {
            MutableComponent text = switch (this.note) {
                default -> throw new MatchException(null, null);
                case Templates.Added.OK -> Lang.of("fwm.colorcodes.saved", new Object[0]);
                case Templates.Added.EXISTS -> Lang.of("fwm.colorcodes.exists", new Object[0]);
                case Templates.Added.FULL -> Lang.of("fwm.colorcodes.full", 50);
                case Templates.Added.EMPTY -> Lang.of("fwm.colorcodes.empty", new Object[0]);
            };
            int room = this.width - sw - 12 - font.width(count);
            ctx.text(font, ColorCodePanel.trimmed(font, (Component)text, room), this.x + sw + 6, this.y + 3, this.note == Templates.Added.OK ? FwmTheme.ACCENT : FwmTheme.DISABLED_TEXT, false);
        }
        FwmTheme.divider(ctx, this.x, this.y + 18 - 3, this.width);
        if (this.templates().isEmpty()) {
            MutableComponent empty = Lang.of("fwm.colorcodes.none", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.y + 18 + (this.height - 18) / 2 - 4, FwmTheme.TEXT_DIM, false);
        } else {
            int index;
            int rowW = this.rowWidth();
            int hoverY = this.confirm.open() ? -1000 : mouseY;
            for (int i = 0; i < this.rowsVisible() && (index = this.scroll + i) < this.templates().size(); ++i) {
                this.renderRow(ctx, font, this.at(index), this.y + 18 + i * 16, rowW, mouseX, hoverY);
            }
            this.renderScrollbar(ctx);
        }
        this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    }

    private int rowWidth() {
        return this.width - (this.maxScroll() > 0 ? 6 : 0);
    }

    private void renderRow(GuiGraphicsExtractor ctx, Font font, Templates.Template template, int rowY, int rowW, int mouseX, int mouseY) {
        boolean hovered;
        boolean bl = hovered = mouseX >= this.x && mouseX < this.x + rowW && mouseY >= rowY && mouseY < rowY + 16;
        if (hovered) {
            FwmTheme.hover(ctx, this.x, rowY, rowW, 16);
        }
        Objects.requireNonNull((Object)font);
        int textY = rowY + (16 - 9) / 2 + 1;
        String tag = template.mode() == ColorCode.Mode.SIGN ? "S" : "R";
        ctx.text(font, tag, this.x + 2, textY, FwmTheme.TEXT_DIM, false);
        int deleteX = this.x + rowW - 12;
        boolean overDelete = hovered && mouseX >= deleteX;
        this.cross(ctx, deleteX + 3, rowY + 5, overDelete ? FwmTheme.DISABLED_TEXT : FwmTheme.TEXT_DIM);
        String length = String.valueOf((int)template.code.length());
        int lengthX = deleteX - 4 - font.width(length);
        boolean over = template.code.length() > template.mode().limit;
        ctx.text(font, length, lengthX, textY, over ? -43691 : FwmTheme.TEXT_DIM, false);
        MutableComponent shown = ColorCodePanel.text(ColorCode.parse(template.code));
        ctx.text(font, ColorCodePanel.trimmed(font, (Component)shown, lengthX - 6 - (this.x + 12)), this.x + 12, textY, -1, true);
    }

    private void cross(GuiGraphicsExtractor ctx, int cx, int cy, int color) {
        for (int i = 0; i < 5; ++i) {
            ctx.fill(cx + i, cy + i, cx + i + 1, cy + i + 1, color);
            ctx.fill(cx + 4 - i, cy + i, cx + 5 - i, cy + i + 1, color);
        }
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx) {
        if (this.maxScroll() <= 0) {
            return;
        }
        int trackX = this.x + this.width - 4;
        int top = this.y + 18;
        int listH = this.rowsVisible() * 16;
        ctx.fill(trackX, top, trackX + 4, top + listH, FwmTheme.OFF);
        int thumbH = Math.max((int)12, (int)(listH * this.rowsVisible() / Math.max((int)1, (int)this.templates().size())));
        int thumbY = top + (listH - thumbH) * this.scroll / this.maxScroll();
        ctx.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, FwmTheme.ACCENT);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int index;
        if (this.confirm.open()) {
            return this.confirm.mouseClicked(mouseX, mouseY);
        }
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        Font font = Minecraft.getInstance().font;
        if (ColorCodePanel.over(mouseX, mouseY, this.x, this.y, this.saveW(font), 13)) {
            this.note = Templates.add(this.user.code, this.user.mode);
            this.noteAt = System.currentTimeMillis();
            this.scroll = 0;
            return true;
        }
        int rowW = this.rowWidth();
        for (int i = 0; i < this.rowsVisible() && (index = this.scroll + i) < this.templates().size(); ++i) {
            int rowY = this.y + 18 + i * 16;
            if (mouseY < (double)rowY || mouseY >= (double)(rowY + 16) || mouseX >= (double)(this.x + rowW)) continue;
            Templates.Template template = this.at(index);
            if (mouseX >= (double)(this.x + rowW - 12)) {
                String label = template.name == null || template.name.isBlank() ? template.code : template.name;
                this.confirm.ask((Component)Lang.of("fwm.colorcodes.deleteAsk", label), () -> {
                    Templates.remove(template);
                    this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)this.scroll));
                });
            } else {
                this.user.load(ColorCode.parse(template.code), template.mode());
                if (this.user.owner != null) {
                    this.user.owner.setTab(0);
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical))));
        return true;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.confirm.open();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.confirm.open() && this.confirm.keyPressed(keyCode);
    }
}
