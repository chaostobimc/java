/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.Objects
 *  java.util.function.IntConsumer
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.function.IntConsumer;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.ui.FwmIcons;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class OverviewPanel
extends Panel {
    private static final int TILE_H = 34;
    private static final int GAP = 6;
    private static final int FILTER_H = 17;
    private static final int SECTION_H = 15;
    private static final int SCROLLBAR_W = 3;
    private final List<Tile> tiles;
    private final IntConsumer open;
    private String typed = "";
    private boolean searching;
    private int scroll;
    private boolean scrollbarShown;

    public OverviewPanel(List<Tile> tiles, IntConsumer open) {
        this.tiles = tiles;
        this.open = open;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.overview", new Object[0]);
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    private List<Tile> matchingOpen() {
        ArrayList out = new ArrayList();
        String needle = this.typed.toLowerCase(Locale.ROOT);
        for (Tile tile : this.tiles) {
            if (tile.locked() || !this.typed.isEmpty() && !Lang.str(tile.labelKey(), new Object[0]).toLowerCase(Locale.ROOT).contains((CharSequence)needle)) continue;
            out.add((Object)tile);
        }
        return out;
    }

    private List<Tile> matchingLocked() {
        ArrayList out = new ArrayList();
        String needle = this.typed.toLowerCase(Locale.ROOT);
        for (Tile tile : this.tiles) {
            if (!tile.locked() || !this.typed.isEmpty() && !Lang.str(tile.labelKey(), new Object[0]).toLowerCase(Locale.ROOT).contains((CharSequence)needle)) continue;
            out.add((Object)tile);
        }
        return out;
    }

    private Layout layout() {
        int top;
        List<Tile> switchable = this.matchingOpen();
        List alwaysOn = List.of();
        List<Tile> locked = this.matchingLocked();
        int switchableTop = top = this.listTop();
        int alwaysTop = top += this.rowsFor(switchable.size()) * 40;
        if (!alwaysOn.isEmpty()) {
            alwaysTop = top += 15;
            top += this.rowsFor(alwaysOn.size()) * 40;
        }
        int lockedTop = top;
        if (!locked.isEmpty()) {
            lockedTop = top += 15;
            top += this.rowsFor(locked.size()) * 40;
        }
        return new Layout(switchable, (List<Tile>)alwaysOn, locked, switchableTop, alwaysTop, lockedTop, top);
    }

    private int maxScroll(Layout layout) {
        return Math.max((int)0, (int)(layout.contentBottom() - (this.y + this.height)));
    }

    private Tile firstHit() {
        List<Tile> open = this.matchingOpen();
        return open.isEmpty() ? null : (Tile)((Object)open.get(0));
    }

    private boolean overSearch(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.y && mouseY < (double)(this.y + 17);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.searching || !this.typed.isEmpty();
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        char chr;
        if (!this.searching) {
            return false;
        }
        char c = chr = input.codepoint() >= 32 ? (char)input.codepoint() : (char)'\u0000';
        if (chr == '\u0000' || this.typed.length() >= 20) {
            return false;
        }
        this.typed = this.typed + chr;
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        Tile hit;
        if (!this.searching && this.typed.isEmpty()) {
            return false;
        }
        if (keyCode == 42 && !this.typed.isEmpty()) {
            this.typed = this.typed.substring(0, this.typed.length() - 1);
            return true;
        }
        if (keyCode == 41 && (this.searching || !this.typed.isEmpty())) {
            this.typed = "";
            this.searching = false;
            return true;
        }
        if ((keyCode == 40 || keyCode == 88) && (hit = this.firstHit()) != null) {
            this.typed = "";
            this.searching = false;
            this.open.accept(hit.category());
            return true;
        }
        return false;
    }

    private int columns() {
        return this.width >= 190 ? 2 : 1;
    }

    private int tileW() {
        int usable = this.width - (this.scrollbarShown ? 6 : 0);
        return (usable - (this.columns() - 1) * 6) / this.columns();
    }

    private int listTop() {
        return this.y + 17 + 6;
    }

    private int rowsFor(int count) {
        return (count + this.columns() - 1) / this.columns();
    }

    private int tileX(int index) {
        return this.x + index % this.columns() * (this.tileW() + 6);
    }

    private int rowY(int top, int index) {
        return top + index / this.columns() * 40;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int top;
        Layout layout = this.layout();
        int maxScroll = this.maxScroll(layout);
        this.scroll = Math.max((int)0, (int)Math.min((int)maxScroll, (int)this.scroll));
        this.scrollbarShown = maxScroll > 0;
        this.renderSearch(ctx, font, layout.hits(), mouseX, mouseY);
        if (layout.hits() == 0) {
            MutableComponent note = Lang.of("fwm.overview.noHit", new Object[0]);
            ctx.text(font, (Component)note, this.x + (this.width - font.width((FormattedText)note)) / 2, this.listTop() + 12, FwmTheme.TEXT_DIM, false);
            return;
        }
        ctx.enableScissor(this.x, this.listTop(), this.x + this.width, this.y + this.height);
        this.renderTiles(ctx, font, layout.switchable(), layout.switchableTop() - this.scroll, mouseX, mouseY);
        if (!layout.alwaysOn().isEmpty()) {
            top = layout.alwaysTop() - this.scroll;
            this.renderHeading(ctx, font, "fwm.overview.otherFeatures", top);
            this.renderTiles(ctx, font, layout.alwaysOn(), top, mouseX, mouseY);
        }
        if (!layout.locked().isEmpty()) {
            top = layout.lockedTop() - this.scroll;
            this.renderHeading(ctx, font, "fwm.tab.comingSoon", top);
            this.renderTiles(ctx, font, layout.locked(), top, mouseX, mouseY);
        }
        ctx.disableScissor();
        if (this.scrollbarShown) {
            this.renderScrollbar(ctx, maxScroll);
        }
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx, int maxScroll) {
        int top = this.listTop();
        int trackH = this.y + this.height - top;
        int trackX = this.x + this.width - 3;
        int thumbH = Math.max((int)10, (int)(trackH * trackH / (trackH + maxScroll)));
        int thumbY = top + (trackH - thumbH) * this.scroll / maxScroll;
        ctx.fill(trackX, top, trackX + 3, top + trackH, FwmTheme.OFF);
        ctx.fill(trackX, thumbY, trackX + 3, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private void renderHeading(GuiGraphicsExtractor ctx, Font font, String key, int top) {
        int headingY = top - 15 + 2;
        MutableComponent heading = Lang.of(key, new Object[0]);
        ctx.text(font, (Component)heading, this.x + 2, headingY, FwmTheme.TEXT_DIM, false);
        FwmTheme.divider(ctx, this.x + font.width((FormattedText)heading) + 6, headingY + 3, Math.max((int)0, (int)(this.width - font.width((FormattedText)heading) - 8)));
    }

    private void renderTiles(GuiGraphicsExtractor ctx, Font font, List<Tile> tiles, int top, int mouseX, int mouseY) {
        for (int i = 0; i < tiles.size(); ++i) {
            this.renderTile(ctx, font, (Tile)((Object)tiles.get(i)), this.tileX(i), this.rowY(top, i), mouseX, mouseY);
        }
    }

    private void renderSearch(GuiGraphicsExtractor ctx, Font font, int hits, int mouseX, int mouseY) {
        boolean active = this.searching || !this.typed.isEmpty();
        FwmTheme.box(ctx, this.x, this.y, this.width, 17, FwmTheme.RAISED, active ? FwmTheme.ACCENT : FwmTheme.OFF);
        Objects.requireNonNull((Object)font);
        int textY = this.y + (17 - 9) / 2 + 1;
        this.pencil(ctx, this.x + 5, this.y + 5, active ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM);
        MutableComponent label = active ? Component.literal((String)(this.typed + (System.currentTimeMillis() / 500L % 2L == 0L ? "_" : ""))) : Lang.of("fwm.overview.search", new Object[0]);
        ctx.text(font, (Component)label, this.x + 16, textY, active ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
        if (this.typed.isEmpty()) {
            return;
        }
        MutableComponent count = Lang.of("fwm.overview.hits", hits);
        ctx.text(font, (Component)count, this.x + this.width - font.width((FormattedText)count) - 5, textY, FwmTheme.TEXT_DIM, false);
    }

    private void pencil(GuiGraphicsExtractor ctx, int px, int py, int color) {
        for (int i = 0; i < 5; ++i) {
            ctx.fill(px + 5 - i, py + i, px + 7 - i, py + i + 1, color);
        }
        ctx.fill(px, py + 5, px + 2, py + 7, color);
        ctx.fill(px + 2, py + 4, px + 4, py + 6, color);
    }

    private void renderTile(GuiGraphicsExtractor ctx, Font font, Tile tile, int tx, int ty, int mouseX, int mouseY) {
        String key;
        String small;
        int n;
        String sub;
        if (ty + 34 <= this.listTop() || ty >= this.y + this.height) {
            return;
        }
        boolean hovered = !tile.locked() && mouseX >= tx && mouseX < tx + this.tileW() && mouseY >= ty && mouseY < ty + 34;
        boolean on = tile.locked() || Features.isOn(tile.labelKey());
        FwmTheme.tile(ctx, tx, ty, this.tileW(), 34, tile.locked(), hovered, on);
        int color = tile.locked() ? FwmTheme.OFF : (on ? FwmTheme.TEXT : FwmTheme.DISABLED_TEXT);
        FwmIcons.draw(ctx, tile.labelKey(), tx + 7, ty + 9, color);
        int textX = tx + 7 + 16 + 7;
        int room = tx + this.tileW() - 6 - textX;
        String string = sub = tile.locked() ? "" : this.subtitle(tile);
        if (sub.isEmpty()) {
            Objects.requireNonNull((Object)font);
            n = ty + (34 - 9) / 2 + 1;
        } else {
            n = ty + 8;
        }
        int nameY = n;
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(this.title(tile), room)), textX, nameY, color, false);
        if (sub.isEmpty()) {
            return;
        }
        if (font.width(sub) > room && !(small = Lang.str(key = tile.labelKey() + ".sub.short", new Object[0])).equals((Object)key)) {
            sub = small;
        }
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(sub, room)), textX, ty + 20, on ? FwmTheme.TEXT_DIM : FwmTheme.DISABLED_TEXT, false);
    }

    private String title(Tile tile) {
        String key = tile.labelKey() + ".tile";
        String value = Lang.str(key, new Object[0]);
        return value.equals((Object)key) ? Lang.str(tile.labelKey(), new Object[0]) : value;
    }

    private String subtitle(Tile tile) {
        if (!Features.isOn(tile.labelKey())) {
            return Lang.str("fwm.overview.off", new Object[0]);
        }
        String key = tile.labelKey() + ".sub";
        String value = Lang.str(key, new Object[0]);
        return value.equals((Object)key) ? "" : value;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        Layout layout;
        if (button != 0 && button != 1) {
            return false;
        }
        if (button == 0 && this.overSearch(mouseX, mouseY)) {
            this.searching = true;
            return true;
        }
        if (button == 0) {
            this.searching = false;
        }
        if (this.clickTiles((layout = this.layout()).switchable(), layout.switchableTop() - this.scroll, mouseX, mouseY, button)) {
            return true;
        }
        return this.clickTiles(layout.alwaysOn(), layout.alwaysTop() - this.scroll, mouseX, mouseY, button);
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        int maxScroll = this.maxScroll(this.layout());
        if (maxScroll <= 0) {
            return false;
        }
        this.scroll = Math.max((int)0, (int)Math.min((int)maxScroll, (int)(this.scroll - (int)Math.signum((double)vertical) * 40)));
        return true;
    }

    private boolean clickTiles(List<Tile> tiles, int top, double mouseX, double mouseY, int button) {
        for (int i = 0; i < tiles.size(); ++i) {
            if (!this.hits((Tile)((Object)tiles.get(i)), this.tileX(i), this.rowY(top, i), mouseX, mouseY, button)) continue;
            return true;
        }
        return false;
    }

    private boolean hits(Tile tile, int tx, int ty, double mouseX, double mouseY, int button) {
        if (mouseX < (double)tx || mouseX >= (double)(tx + this.tileW()) || mouseY < (double)ty || mouseY >= (double)(ty + 34)) {
            return false;
        }
        if (button == 1) {
            if (!Features.canSwitch(tile.labelKey())) {
                return false;
            }
            Features.toggle(tile.labelKey());
            return true;
        }
        this.typed = "";
        this.searching = false;
        this.open.accept(tile.category());
        return true;
    }

    public record Tile(String labelKey, boolean locked, int category) {
    }

    private record Layout(List<Tile> switchable, List<Tile> alwaysOn, List<Tile> locked, int switchableTop, int alwaysTop, int lockedTop, int contentBottom) {
        int hits() {
            return this.switchable.size() + this.alwaysOn.size() + this.locked.size();
        }
    }
}
