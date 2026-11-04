/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.config.TestRun;
import net.friendswithmoney.fwm.ui.ConfirmBox;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.TestDetailPanel;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class TestsPanel
extends Panel {
    private static final int ROW_H = 16;
    private static final int SCROLLBAR_W = 4;
    private static final int PENCIL_W = 14;
    private static final int DELETE_W = 12;
    private final PanelHost host;
    private final ConfirmBox confirm = new ConfirmBox();
    private EditBox rename;
    private int renaming = -1;
    private int scroll;

    public TestsPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.test.listTitle", new Object[0]);
    }

    private List<TestRun> tests() {
        return FwmConfig.INSTANCE.tests;
    }

    private int rowsVisible() {
        return Math.max((int)1, (int)(this.height / 16));
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.tests().size() - this.rowsVisible()));
    }

    private TestRun at(int index) {
        List<TestRun> tests = this.tests();
        return (TestRun)tests.get(tests.size() - 1 - index);
    }

    @Override
    protected void onResize() {
        this.stopRenaming(false);
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)this.scroll));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int index;
        if (this.tests().isEmpty()) {
            MutableComponent empty = Lang.of("fwm.test.none", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.y + this.height / 2 - 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        int rowW = this.width - (this.maxScroll() > 0 ? 6 : 0);
        for (int i = 0; i < this.rowsVisible() && (index = this.scroll + i) < this.tests().size(); ++i) {
            this.renderRow(ctx, font, index, this.y + i * 16, rowW, mouseX, mouseY, delta);
        }
        this.renderScrollbar(ctx);
        this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    }

    private void renderRow(GuiGraphicsExtractor ctx, Font font, int index, int rowY, int rowW, int mouseX, int mouseY, float delta) {
        boolean hovered;
        TestRun run = this.at(index);
        boolean bl = hovered = mouseX >= this.x && mouseX < this.x + rowW && mouseY >= rowY && mouseY < rowY + 16;
        if (hovered) {
            FwmTheme.hover(ctx, this.x, rowY, rowW, 16);
        }
        Objects.requireNonNull((Object)font);
        int textY = rowY + (16 - 9) / 2 + 1;
        int deleteX = this.x + rowW - 12;
        boolean overDelete = mouseX >= deleteX && mouseX < deleteX + 12 && mouseY >= rowY && mouseY < rowY + 16;
        this.cross(ctx, deleteX + 3, rowY + 5, overDelete ? FwmTheme.DISABLED_TEXT : FwmTheme.TEXT_DIM);
        int pencilX = deleteX - 14;
        boolean overPencil = mouseX >= pencilX && mouseX < pencilX + 14 && mouseY >= rowY && mouseY < rowY + 16;
        this.pencil(ctx, pencilX + 3, rowY + 4, overPencil ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM);
        String value = Fmt.compactMoneyUp(run.cents);
        int valueX = pencilX - 4 - font.width(value);
        ctx.text(font, (Component)Component.literal((String)value), valueX, textY, FwmTheme.ACCENT, false);
        if (this.renaming == index && this.rename != null) {
            FwmTheme.box(ctx, this.x, rowY + 1, valueX - this.x - 4, 14, FwmTheme.RAISED, FwmTheme.ACCENT);
            this.rename.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
            return;
        }
        MutableComponent name = Component.literal((String)font.plainSubstrByWidth(run.name == null ? "?" : run.name, Math.max((int)8, (int)(valueX - this.x - 6))));
        ctx.text(font, (Component)name, this.x + 2, textY, FwmTheme.TEXT, false);
    }

    private void pencil(GuiGraphicsExtractor ctx, int px, int py, int color) {
        for (int i = 0; i < 5; ++i) {
            ctx.fill(px + i, py + 4 - i, px + i + 2, py + 6 - i, color);
        }
        ctx.fill(px, py + 5, px + 2, py + 7, color);
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
        int listH = this.rowsVisible() * 16;
        ctx.fill(trackX, this.y, trackX + 4, this.y + listH, FwmTheme.OFF);
        int thumbH = Math.max((int)12, (int)(listH * this.rowsVisible() / Math.max((int)1, (int)this.tests().size())));
        int thumbY = this.y + (listH - thumbH) * this.scroll / this.maxScroll();
        ctx.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private void startRenaming(int index, int rowY, int fieldW) {
        this.stopRenaming(true);
        TestRun run = this.at(index);
        Font font = Minecraft.getInstance().font;
        this.rename = new EditBox(font, this.x + 3, rowY + 4, Math.max((int)20, (int)fieldW), 10, (Component)Lang.of("fwm.test.rename", new Object[0]));
        this.rename.setBordered(false);
        this.rename.setMaxLength(24);
        this.rename.setTextColor(FwmTheme.TEXT);
        this.rename.setValue(run.name == null ? "" : run.name);
        this.rename.setFocused(true);
        this.host.attach((AbstractWidget)this.rename);
        this.renaming = index;
    }

    private void stopRenaming(boolean keep) {
        String value;
        if (this.rename == null) {
            return;
        }
        if (keep && this.renaming >= 0 && this.renaming < this.tests().size() && !(value = this.rename.getValue().trim()).isEmpty()) {
            this.at((int)this.renaming).name = value;
            ConfigManager.save();
        }
        this.host.detach((AbstractWidget)this.rename);
        this.rename = null;
        this.renaming = -1;
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
        int rowW = this.width - (this.maxScroll() > 0 ? 6 : 0);
        int deleteX = this.x + rowW - 12;
        int pencilX = deleteX - 14;
        for (int i = 0; i < this.rowsVisible() && (index = this.scroll + i) < this.tests().size(); ++i) {
            int rowY = this.y + i * 16;
            if (mouseY < (double)rowY || mouseY >= (double)(rowY + 16)) continue;
            if (mouseX >= (double)deleteX) {
                this.stopRenaming(true);
                this.delete(this.at(index));
            } else if (mouseX >= (double)pencilX) {
                this.startRenaming(index, rowY, pencilX - this.x - 8);
            } else if (this.renaming != index) {
                this.stopRenaming(true);
                this.host.openSub(new TestDetailPanel(this.at(index)));
            }
            return true;
        }
        this.stopRenaming(true);
        return true;
    }

    private void delete(TestRun run) {
        this.confirm.ask((Component)Lang.of("fwm.test.deleteAsk", run.name == null ? "?" : run.name), () -> {
            this.tests().remove((Object)run);
            this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)this.scroll));
            ConfigManager.save();
        });
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical))));
        return true;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.rename != null || this.confirm.open();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.confirm.open()) {
            return this.confirm.keyPressed(keyCode);
        }
        if (this.rename == null) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            this.stopRenaming(true);
            return true;
        }
        if (keyCode == 41) {
            this.stopRenaming(false);
            return true;
        }
        return this.rename.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.rename != null && this.rename.charTyped(input);
    }

    @Override
    public void onHidden() {
        this.stopRenaming(true);
    }
}
