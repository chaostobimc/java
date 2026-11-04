/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Iterable
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.Locale
 *  java.util.Objects
 *  java.util.function.Consumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.Consumer;
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
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public class ItemPanel
extends Panel {
    private static final int ROW_H = 18;
    private static final int SEARCH_H = 16;
    private static final int FOOTER_H = 20;
    private static final int SCROLLBAR_W = 4;
    private static final int BUTTONS = 3;
    private static final int BUTTON_GAP = 4;
    private static final int BUTTON_H = 14;
    private static List<Entry> catalogue;
    private final PanelHost host;
    private final List<Entry> visible = new ArrayList();
    private EditBox search;
    private int listY;
    private int listH;
    private int rowsVisible;
    private int scroll;
    private final Consumer<String> pick;

    public ItemPanel(PanelHost host) {
        this(host, null);
    }

    public ItemPanel(PanelHost host, Consumer<String> pick) {
        this.host = host;
        this.pick = pick;
    }

    private static List<Entry> catalogue() {
        if (catalogue != null) {
            return catalogue;
        }
        ArrayList built = new ArrayList();
        for (Item item : BuiltInRegistries.ITEM) {
            try {
                ItemStack stack = new ItemStack((ItemLike)item);
                if (stack.isEmpty()) continue;
                String id = BuiltInRegistries.ITEM.getKey((Object)item).toString();
                String name = stack.getHoverName().getString();
                built.add((Object)new Entry(id, name, name.toLowerCase(Locale.ROOT), stack));
            }
            catch (Exception exception) {}
        }
        built.sort((a, b) -> a.name.compareToIgnoreCase(b.name));
        if (!built.isEmpty()) {
            catalogue = List.copyOf((Collection)built);
            return catalogue;
        }
        return List.of();
    }

    @Override
    public Component title() {
        return Lang.of("fwm.items.title", new Object[0]);
    }

    @Override
    protected void onResize() {
        String previous;
        this.listY = this.y + 16 + 4;
        this.listH = Math.max((int)18, (int)(this.height - 16 - 4 - 20));
        this.rowsVisible = Math.max((int)1, (int)(this.listH / 18));
        String string = previous = this.search == null ? "" : this.search.getValue();
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        Font font = Minecraft.getInstance().font;
        this.search = new EditBox(font, this.x + 5, this.y + 3, this.width - 10, 12, (Component)Lang.of("fwm.items.search", new Object[0]));
        this.search.setBordered(false);
        this.search.setMaxLength(48);
        this.search.setTextColor(FwmTheme.TEXT);
        this.search.setHint((Component)Lang.of("fwm.items.search", new Object[0]));
        this.search.setValue(previous);
        this.search.setResponder(text -> {
            this.applyFilter((String)text);
            this.scroll = 0;
        });
        this.search.setFocused(true);
        this.host.attach((AbstractWidget)this.search);
        this.applyFilter(previous);
    }

    private void applyFilter(String query) {
        this.visible.clear();
        String needle = query == null ? "" : query.trim().toLowerCase(Locale.ROOT);
        for (Entry entry : ItemPanel.catalogue()) {
            if (!needle.isEmpty() && !entry.lowerName.contains((CharSequence)needle) && !entry.id.contains((CharSequence)needle)) continue;
            this.visible.add((Object)entry);
        }
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.visible.size() - this.rowsVisible));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        FwmTheme.box(ctx, this.x, this.y, this.width, 16, FwmTheme.RAISED, this.search.isFocused() ? FwmTheme.ACCENT : FwmTheme.OFF);
        this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        this.renderList(ctx, font, mouseX, mouseY);
        this.renderFooter(ctx, font, mouseX, mouseY);
    }

    private void renderList(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int index;
        ctx.enableScissor(this.x, this.listY, this.x + this.width, this.listY + this.listH);
        for (int i = 0; i < this.rowsVisible && (index = this.scroll + i) < this.visible.size(); ++i) {
            boolean hovered;
            Entry entry = (Entry)((Object)this.visible.get(index));
            int rowY = this.listY + i * 18;
            boolean bl = hovered = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= rowY && mouseY < rowY + 18;
            if (hovered) {
                ctx.fill(this.x, rowY, this.x + this.width, rowY + 18, FwmTheme.HOVER);
            }
            boolean on = this.pick != null || FwmConfig.INSTANCE.isWhitelisted(entry.id);
            int boxX = this.x + 3;
            int boxY = rowY + 4;
            if (this.pick == null) {
                FwmTheme.box(ctx, boxX, boxY, 10, 10, on ? FwmTheme.ON : 0, on ? FwmTheme.ON : FwmTheme.OFF);
            }
            ctx.item(entry.stack, boxX + 15, rowY + 1);
            MutableComponent mutableComponent = Component.literal((String)entry.name);
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)mutableComponent, boxX + 34, rowY + (18 - 9) / 2 + 1, on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
        }
        ctx.disableScissor();
        if (this.visible.isEmpty()) {
            MutableComponent empty = Lang.of(ItemPanel.catalogue().isEmpty() ? "fwm.items.notInGame" : "fwm.items.noMatch", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.listY + this.listH / 2 - 4, FwmTheme.TEXT_DIM, false);
        }
        this.renderScrollbar(ctx);
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx) {
        if (this.maxScroll() <= 0) {
            return;
        }
        int trackX = this.x + this.width - 4;
        ctx.fill(trackX, this.listY, trackX + 4, this.listY + this.listH, FwmTheme.OFF);
        int thumbH = Math.max((int)12, (int)(this.listH * this.rowsVisible / Math.max((int)1, (int)this.visible.size())));
        int thumbY = this.listY + (this.listH - thumbH) * this.scroll / this.maxScroll();
        ctx.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private void renderFooter(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        if (this.pick != null) {
            return;
        }
        int by = this.footerY();
        this.drawButton(ctx, font, this.buttonX(0), by, this.buttonW(), (Component)Lang.of("fwm.items.all", new Object[0]), mouseX, mouseY);
        this.drawButton(ctx, font, this.buttonX(1), by, this.buttonW(), (Component)Lang.of("fwm.items.presetFlower", new Object[0]), mouseX, mouseY);
        this.drawButton(ctx, font, this.buttonX(2), by, this.buttonW(), (Component)Lang.of("fwm.items.none", new Object[0]), mouseX, mouseY);
    }

    private int footerY() {
        return this.y + this.height - 14;
    }

    private int buttonW() {
        return (this.width - 8) / 3;
    }

    private int buttonX(int index) {
        return this.x + index * (this.buttonW() + 4);
    }

    private int buttonAt(double mx, double my) {
        if (this.pick != null) {
            return -1;
        }
        if (my < (double)this.footerY() || my >= (double)(this.footerY() + 14)) {
            return -1;
        }
        for (int i = 0; i < 3; ++i) {
            if (!(mx >= (double)this.buttonX(i)) || !(mx < (double)(this.buttonX(i) + this.buttonW()))) continue;
            return i;
        }
        return -1;
    }

    private void drawButton(GuiGraphicsExtractor ctx, Font font, int bx, int by, int w, Component label, int mouseX, int mouseY) {
        boolean hovered = mouseX >= bx && mouseX < bx + w && mouseY >= by && mouseY < by + 14;
        FwmTheme.box(ctx, bx, by, w, 14, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label.getString(), w - 6));
        int n = bx + (w - font.width((FormattedText)shown)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)shown, n, by + (14 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int index;
        if (button != 0) {
            return false;
        }
        switch (this.buttonAt(mouseX, mouseY)) {
            case 0: {
                ArrayList everything = new ArrayList();
                for (Entry entry : ItemPanel.catalogue()) {
                    everything.add((Object)entry.id);
                }
                FwmConfig.INSTANCE.setWhitelist((Iterable<String>)everything);
                return true;
            }
            case 1: {
                FwmConfig.INSTANCE.setWhitelist((Iterable<String>)FwmConfig.PRESET_FLOWER_FARM);
                return true;
            }
            case 2: {
                FwmConfig.INSTANCE.clearWhitelist();
                return true;
            }
        }
        if (mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.listY && mouseY < (double)(this.listY + this.listH) && (index = this.scroll + (int)((mouseY - (double)this.listY) / 18.0)) >= 0 && index < this.visible.size()) {
            if (this.pick != null) {
                this.pick.accept((Object)((Entry)((Object)this.visible.get((int)index))).id);
                this.host.closeSub();
                return true;
            }
            FwmConfig.INSTANCE.toggleWhitelisted(((Entry)((Object)this.visible.get((int)index))).id);
            return true;
        }
        this.search.setFocused(mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + 16));
        return this.contains(mouseX, mouseY);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical) * 3)));
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.search.isFocused() && this.search.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.search.isFocused() && this.search.charTyped(input);
    }

    @Override
    public void onHidden() {
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        ConfigManager.save();
    }

    private record Entry(String id, String name, String lowerName, ItemStack stack) {
    }
}
