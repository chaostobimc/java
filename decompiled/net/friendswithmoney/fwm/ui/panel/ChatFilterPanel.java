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
import net.friendswithmoney.fwm.config.ChatFilter;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
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

public class ChatFilterPanel
extends Panel {
    private static final int ROW_H = 16;
    private static final int PILL_W = 34;
    private static final int PILL_H = 12;
    private static final int KNOB = 8;
    private static final int DEL_W = 12;
    private static final int BTN_H = 14;
    private static final int SCROLLBAR_W = 4;
    private final PanelHost host;
    private EditBox editor;
    private int editing = -1;
    private int scroll;

    public ChatFilterPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.secondchat.filters", new Object[0]);
    }

    private List<ChatFilter> filters() {
        return FwmConfig.INSTANCE.secondChatFilters;
    }

    private int listH() {
        return this.height - 14 - 3;
    }

    private int rowsVisible() {
        return Math.max((int)1, (int)(this.listH() / 16));
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.filters().size() - this.rowsVisible()));
    }

    private int rowY(int slot) {
        return this.y + slot * 16;
    }

    private int rowW() {
        return this.width - (this.maxScroll() > 0 ? 6 : 0);
    }

    private int deleteX() {
        return this.x + this.rowW() - 12;
    }

    private int pillX() {
        return this.deleteX() - 4 - 34;
    }

    private int addY() {
        return this.y + this.height - 14;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int index;
        List<ChatFilter> filters = this.filters();
        for (int slot = 0; slot < this.rowsVisible() && (index = this.scroll + slot) < filters.size(); ++slot) {
            this.renderRow(ctx, font, (ChatFilter)filters.get(index), index, this.rowY(slot), mouseX, mouseY, delta);
        }
        if (filters.isEmpty()) {
            MutableComponent empty = Lang.of("fwm.secondchat.filtersNone", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.y + this.listH() / 2 - 4, FwmTheme.TEXT_DIM, false);
        }
        this.renderScrollbar(ctx);
        this.renderAddButton(ctx, font, mouseX, mouseY);
    }

    private void renderRow(GuiGraphicsExtractor ctx, Font font, ChatFilter filter, int index, int ry, int mouseX, int mouseY, float delta) {
        boolean hovered;
        boolean bl = hovered = mouseX >= this.x && mouseX < this.x + this.rowW() && mouseY >= ry && mouseY < ry + 16;
        if (hovered) {
            FwmTheme.hover(ctx, this.x, ry, this.rowW(), 16);
        }
        Objects.requireNonNull((Object)font);
        int textY = ry + (16 - 9) / 2 + 1;
        if (this.editing == index && this.editor != null) {
            FwmTheme.box(ctx, this.x, ry + 1, this.pillX() - this.x - 4, 14, FwmTheme.RAISED, FwmTheme.ACCENT);
            this.editor.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        } else {
            String value = filter.text == null || filter.text.isBlank() ? Lang.str("fwm.secondchat.filterHint", new Object[0]) : filter.text;
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(value, this.pillX() - this.x - 8)), this.x + 3, textY, filter.on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
        }
        int py = ry + 2;
        FwmTheme.pill(ctx, this.pillX(), py, 34, 12, filter.on ? FwmTheme.ON : FwmTheme.OFF);
        FwmTheme.pill(ctx, filter.on ? this.pillX() + 34 - 2 - 8 : this.pillX() + 2, py + 2, 8, 8, filter.on ? -16375537 : FwmTheme.TEXT);
        boolean overDelete = mouseX >= this.deleteX() && mouseX < this.deleteX() + 12 && mouseY >= ry && mouseY < ry + 16;
        MutableComponent cross = Component.literal((String)"x");
        ctx.text(font, (Component)cross, this.deleteX() + (12 - font.width((FormattedText)cross)) / 2, textY, overDelete ? -2067340 : FwmTheme.TEXT_DIM, false);
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx) {
        if (this.maxScroll() <= 0) {
            return;
        }
        int trackX = this.x + this.width - 4;
        ctx.fill(trackX, this.y, trackX + 4, this.y + this.listH(), FwmTheme.OFF);
        int thumbH = Math.max((int)12, (int)(this.listH() * this.rowsVisible() / Math.max((int)1, (int)this.filters().size())));
        int thumbY = this.y + (this.listH() - thumbH) * this.scroll / this.maxScroll();
        ctx.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private void renderAddButton(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean hovered = this.overAdd(mouseX, mouseY);
        FwmTheme.box(ctx, this.x, this.addY(), this.width, 14, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = Lang.of("fwm.secondchat.filterAdd", new Object[0]);
        int n = this.x + (this.width - font.width((FormattedText)label)) / 2;
        int n2 = this.addY();
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)label, n, n2 + (14 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    private boolean overAdd(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.addY() && mouseY < (double)(this.addY() + 14);
    }

    private void startEditing(int index, int ry) {
        this.stopEditing(true);
        ChatFilter filter = (ChatFilter)this.filters().get(index);
        Font font = Minecraft.getInstance().font;
        this.editor = new EditBox(font, this.x + 4, ry + 4, Math.max((int)20, (int)(this.pillX() - this.x - 10)), 10, (Component)Lang.of("fwm.secondchat.filterHint", new Object[0]));
        this.editor.setBordered(false);
        this.editor.setMaxLength(64);
        this.editor.setTextColor(FwmTheme.TEXT);
        this.editor.setHint((Component)Lang.of("fwm.secondchat.filterHint", new Object[0]));
        this.editor.setValue(filter.text == null ? "" : filter.text);
        this.editor.setFocused(true);
        this.host.attach((AbstractWidget)this.editor);
        this.editing = index;
    }

    private void stopEditing(boolean keep) {
        if (this.editor == null) {
            return;
        }
        if (keep && this.editing >= 0 && this.editing < this.filters().size()) {
            String value = this.editor.getValue().trim();
            if (value.isEmpty()) {
                this.filters().remove(this.editing);
            } else {
                ((ChatFilter)this.filters().get((int)this.editing)).text = value;
            }
            ConfigManager.save();
        }
        this.host.detach((AbstractWidget)this.editor);
        this.editor = null;
        this.editing = -1;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int index;
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (this.overAdd(mouseX, mouseY)) {
            this.stopEditing(true);
            this.filters().add((Object)new ChatFilter(""));
            this.scroll = this.maxScroll();
            int slot = this.filters().size() - 1 - this.scroll;
            this.startEditing(this.filters().size() - 1, this.rowY(slot));
            return true;
        }
        for (int slot = 0; slot < this.rowsVisible() && (index = this.scroll + slot) < this.filters().size(); ++slot) {
            int ry = this.rowY(slot);
            if (mouseY < (double)ry || mouseY >= (double)(ry + 16)) continue;
            if (mouseX >= (double)this.deleteX()) {
                this.stopEditing(false);
                this.filters().remove(index);
                ConfigManager.save();
            } else if (mouseX >= (double)this.pillX()) {
                this.stopEditing(true);
                ChatFilter filter = (ChatFilter)this.filters().get(index);
                filter.on = !filter.on;
                ConfigManager.save();
            } else {
                this.startEditing(index, ry);
            }
            return true;
        }
        this.stopEditing(true);
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical))));
        return true;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.editor != null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.editor == null) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            this.stopEditing(true);
            return true;
        }
        if (keyCode == 41) {
            this.stopEditing(false);
            return true;
        }
        return this.editor.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.editor != null && this.editor.charTyped(input);
    }

    @Override
    public void onHidden() {
        this.stopEditing(true);
    }
}
