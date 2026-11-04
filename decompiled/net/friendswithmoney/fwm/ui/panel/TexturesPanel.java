/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
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

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.textures.TextureMixer;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
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

public class TexturesPanel
extends Panel {
    private static final int LEFT_W = 122;
    private static final int ROW_H = 11;
    private static final int ITEM_H = 20;
    private static final int BTN_H = 14;
    private static final int COLS = 4;
    private static final int GAP = 4;
    private final PanelHost host;
    private final List<Hit> hits = new ArrayList();
    private List<String> packs = List.of();
    private EditBox search;
    private EditBox themeName;
    private boolean themesTab;
    private boolean onlyFavourites;
    private String category = "crystal";
    private int listScroll;
    private int gridScroll;
    private int themeScroll;
    private FwmConfig.TextureTheme renaming;

    public TexturesPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.textures", new Object[0]);
    }

    @Override
    protected void onResize() {
        String beforeName;
        this.packs = TextureMixer.packs();
        for (String pack : this.packs) {
            TextureMixer.content(pack);
        }
        Font font = Minecraft.getInstance().font;
        String before = this.search == null ? "" : this.search.getValue();
        String string = beforeName = this.themeName == null ? "" : this.themeName.getValue();
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        if (this.themeName != null) {
            this.host.detach((AbstractWidget)this.themeName);
        }
        this.search = this.field(font, this.midX() + 4, this.y + 26, this.midW() - 110, 40, Lang.t("Suchen \u2026", "Search \u2026"));
        this.search.setValue(before);
        this.themeName = this.field(font, this.midX() + 4, this.y + 26, this.midW() - 70, 40, Lang.t("Name des Themes", "Theme name"));
        this.themeName.setValue(beforeName);
        this.showField();
    }

    private EditBox field(Font font, int fx, int fy, int w, int max, String hint) {
        EditBox f = new EditBox(font, fx, fy, Math.max((int)20, (int)w), 10, (Component)Component.literal((String)hint));
        f.setBordered(false);
        f.setMaxLength(max);
        f.setTextColor(FwmTheme.TEXT);
        f.setHint((Component)Component.literal((String)hint));
        return f;
    }

    private void showField() {
        this.host.detach((AbstractWidget)this.search);
        this.host.detach((AbstractWidget)this.themeName);
        this.host.attach((AbstractWidget)(this.themesTab ? this.themeName : this.search));
    }

    private int midX() {
        return this.x + 122 + 6;
    }

    private int midW() {
        return this.width - 122 - 6;
    }

    private int footY() {
        return this.y + this.height - 14;
    }

    private int gridY() {
        return this.y + 42;
    }

    private int tileW() {
        return Math.max((int)20, (int)((this.midW() - 12) / 4));
    }

    private int tileH() {
        return Math.round((float)((float)this.tileW() * 0.78f)) + 12;
    }

    private List<TextureMixer.Category> categories() {
        boolean reading = false;
        for (String pack : this.packs) {
            if (TextureMixer.content(pack) != null) continue;
            reading = true;
        }
        ArrayList out = new ArrayList();
        for (TextureMixer.Category c : TextureMixer.CATEGORIES) {
            if (!TextureMixer.offered(c.id()) || !reading && TextureMixer.packsWith(this.packs, c.id()) <= 0) continue;
            out.add((Object)c);
        }
        return out;
    }

    private List<String> styles() {
        String query = this.search == null ? "" : this.search.getValue().trim().toLowerCase(Locale.ROOT);
        ArrayList out = new ArrayList();
        if (!this.onlyFavourites && query.isEmpty()) {
            out.add((Object)"");
        }
        for (String pack : this.packs) {
            Map<String, String> c = TextureMixer.content(pack);
            if (c == null || !c.containsKey((Object)this.category) || this.onlyFavourites && !TextureMixer.favourite(pack) || !query.isEmpty() && !TexturesPanel.strip(pack).toLowerCase(Locale.ROOT).contains((CharSequence)query)) continue;
            out.add((Object)pack);
        }
        ArrayList sorted = new ArrayList();
        if (!out.isEmpty() && ((String)out.get(0)).isEmpty()) {
            sorted.add((Object)"");
        }
        for (String p : out) {
            if (p.isEmpty() || !TextureMixer.favourite(p)) continue;
            sorted.add((Object)p);
        }
        for (String p : out) {
            if (p.isEmpty() || TextureMixer.favourite(p)) continue;
            sorted.add((Object)p);
        }
        return sorted;
    }

    private static String strip(String pack) {
        String p = pack.toLowerCase(Locale.ROOT).endsWith(".zip") ? pack.substring(0, pack.length() - 4) : pack;
        return p.replaceAll("\u00a7.", "");
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.hits.clear();
        TextureMixer.previewBudget(3);
        int half = 60;
        this.tab(ctx, font, this.x, this.y, half, Lang.t("Kategorien", "Categories"), !this.themesTab, mouseX, mouseY, () -> this.setTab(false));
        this.tab(ctx, font, this.x + half + 2, this.y, 122 - half - 2, "Themes", this.themesTab, mouseX, mouseY, () -> this.setTab(true));
        ctx.fill(this.x + 122 + 2, this.y, this.x + 122 + 3, this.footY() - 4, FwmTheme.OFF);
        if (this.themesTab) {
            this.themes(ctx, font, mouseX, mouseY);
        } else {
            this.categoryList(ctx, font, mouseX, mouseY);
            this.grid(ctx, font, mouseX, mouseY);
        }
        this.footer(ctx, font, mouseX, mouseY);
    }

    private void setTab(boolean themes) {
        this.themesTab = themes;
        this.showField();
    }

    private void tab(GuiGraphicsExtractor ctx, Font font, int bx, int by, int bw, String label, boolean on, int mouseX, int mouseY, Runnable action) {
        boolean over = TexturesPanel.in(mouseX, mouseY, bx, by, bw, 14);
        FwmTheme.box(ctx, bx, by, bw, 14, on ? FwmTheme.RAISED : 0, on || over ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent t = Component.literal((String)font.plainSubstrByWidth(label, bw - 4));
        ctx.text(font, (Component)t, bx + (bw - font.width((FormattedText)t)) / 2, by + 4, on ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        this.hits.add((Object)new Hit(bx, by, bw, 14, 0, action));
    }

    private void categoryList(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int i;
        List<TextureMixer.Category> cats = this.categories();
        if (!cats.isEmpty() && cats.stream().noneMatch(c -> c.id().equals((Object)this.category))) {
            this.category = ((TextureMixer.Category)((Object)cats.get(0))).id();
            this.gridScroll = 0;
        }
        ArrayList rows = new ArrayList();
        String group = null;
        for (TextureMixer.Category c2 : cats) {
            if (!c2.group().equals(group)) {
                group = c2.group();
                for (String[] g : TextureMixer.GROUPS) {
                    if (!g[0].equals((Object)group)) continue;
                    rows.add((Object)Lang.t(g[1], g[2]));
                }
            }
            rows.add((Object)c2);
        }
        int top = this.y + 14 + 4;
        int bottom = this.footY() - 6;
        int fit = 0;
        int h = 0;
        for (i = rows.size() - 1; i >= 0 && (h += rows.get(i) instanceof String ? 11 : 20) <= bottom - top; --i) {
            ++fit;
        }
        this.listScroll = Math.max((int)0, (int)Math.min((int)this.listScroll, (int)(rows.size() - fit)));
        int ry = top;
        for (i = this.listScroll; i < rows.size(); ++i) {
            TextureMixer.Preview icon;
            Object row = rows.get(i);
            if (row instanceof String) {
                String heading = (String)row;
                if (ry + 11 > bottom) break;
                ctx.text(font, (Component)Component.literal((String)heading.toUpperCase(Locale.ROOT)), this.x + 2, ry + 2, FwmTheme.TEXT_DIM, false);
                ry += 11;
                continue;
            }
            if (ry + 20 > bottom) break;
            TextureMixer.Category c3 = (TextureMixer.Category)((Object)row);
            boolean on = c3.id().equals((Object)this.category);
            boolean over = TexturesPanel.in(mouseX, mouseY, this.x, ry, 122, 20);
            if (on) {
                ctx.fill(this.x, ry, this.x + 122, ry + 20, 0x33FFFFFF);
                ctx.fill(this.x, ry, this.x + 2, ry + 20, FwmTheme.ACCENT);
            } else if (over) {
                FwmTheme.hover(ctx, this.x, ry, 122, 20);
            }
            String pack = TextureMixer.chosen(c3.id());
            FwmTheme.box(ctx, this.x + 4, ry + 2, 16, 16, FwmTheme.INSET, FwmTheme.EDGE);
            TextureMixer.Preview preview = icon = pack.isEmpty() ? TextureMixer.vanillaPreview(c3) : TextureMixer.preview(pack, c3);
            if (icon != null) {
                TexturesPanel.picture(ctx, icon, c3.tiled(), this.x + 4, ry + 2, 16);
            }
            String count = String.valueOf((int)TextureMixer.packsWith(this.packs, c3.id()));
            int cw = font.width(count);
            ctx.text(font, (Component)Component.literal((String)count), this.x + 122 - 3 - cw, ry + 6, on ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
            int room = 98 - cw - 6;
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(c3.label(), room)), this.x + 24, ry + 1, on ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
            String now = pack.isEmpty() ? "Vanilla" : TexturesPanel.strip(pack);
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(now, room)), this.x + 24, ry + 11, pack.isEmpty() ? FwmTheme.TEXT_DIM : -8398438, false);
            String id = c3.id();
            this.hits.add((Object)new Hit(this.x, ry, 122, 20, 0, () -> {
                this.category = id;
                this.gridScroll = 0;
            }));
            ry += 20;
        }
    }

    private void grid(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        TextureMixer.Category cat = TextureMixer.category(this.category);
        List<String> styles = this.styles();
        String chosen = TextureMixer.chosen(this.category);
        ctx.text(font, (Component)Component.literal((String)cat.label()), this.midX(), this.y + 2, FwmTheme.TEXT, false);
        String sub = TextureMixer.packsWith(this.packs, this.category) + " Styles \u00b7 " + (chosen.isEmpty() ? "Vanilla" : TexturesPanel.strip(chosen));
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(sub, this.midW())), this.midX(), this.y + 13, FwmTheme.TEXT_DIM, false);
        int sw = this.midW() - 106;
        FwmTheme.box(ctx, this.midX(), this.y + 23, sw, 15, FwmTheme.INSET, this.search.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
        this.search.extractWidgetRenderState(ctx, mouseX, mouseY, 0.0f);
        int favs = 0;
        for (String p : this.packs) {
            if (!TextureMixer.favourite(p)) continue;
            ++favs;
        }
        int cx = this.midX() + sw + 4;
        this.tab(ctx, font, cx, this.y + 23, 34, Lang.t("Alle", "All"), !this.onlyFavourites, mouseX, mouseY, () -> {
            this.onlyFavourites = false;
            this.gridScroll = 0;
        });
        this.tab(ctx, font, cx + 36, this.y + 23, 66, "\u2605 " + Lang.t("Favoriten", "Favs") + " (" + favs + ")", this.onlyFavourites, mouseX, mouseY, () -> {
            this.onlyFavourites = true;
            this.gridScroll = 0;
        });
        int tw = this.tileW();
        int th = this.tileH();
        int rowsVisible = Math.max((int)1, (int)((this.footY() - 18 - this.gridY()) / (th + 4)));
        int totalRows = (styles.size() + 4 - 1) / 4;
        this.gridScroll = Math.max((int)0, (int)Math.min((int)this.gridScroll, (int)(totalRows - rowsVisible)));
        for (int i = this.gridScroll * 4; i < styles.size() && i < (this.gridScroll + rowsVisible) * 4; ++i) {
            TextureMixer.Preview preview;
            String pack = (String)styles.get(i);
            int slot = i - this.gridScroll * 4;
            int tx = this.midX() + slot % 4 * (tw + 4);
            int ty = this.gridY() + slot / 4 * (th + 4);
            boolean on = pack.equals((Object)chosen);
            boolean over = TexturesPanel.in(mouseX, mouseY, tx, ty, tw, th);
            int sceneH = th - 12;
            LauncherGfx.roundGradient(ctx, tx, ty, tw, sceneH, 4, -14208438, -15723749);
            ctx.fill(tx + 2, ty + sceneH * 2 / 3, tx + tw - 2, ty + sceneH * 2 / 3 + 1, 0x14FFFFFF);
            if (on) {
                LauncherGfx.ring(ctx, tx - 1, ty - 1, tw + 2, sceneH + 2, 5, FwmTheme.ACCENT);
            } else if (over) {
                LauncherGfx.ring(ctx, tx - 1, ty - 1, tw + 2, sceneH + 2, 5, 0x66FFFFFF);
            }
            TextureMixer.Preview preview2 = preview = pack.isEmpty() ? TextureMixer.vanillaPreview(cat) : TextureMixer.preview(pack, cat);
            if (preview != null) {
                TexturesPanel.fit(ctx, preview, tx + 3, ty + 3, tw - 6, sceneH - 6);
            } else {
                String none = cat.id().equals((Object)"sounds") ? "\u266a" : "?";
                ctx.text(font, (Component)Component.literal((String)none), tx + (tw - font.width(none)) / 2, ty + sceneH / 2 - 4, FwmTheme.TEXT_DIM, false);
            }
            String name = pack.isEmpty() ? "Vanilla" : TexturesPanel.strip(pack);
            MutableComponent label = Component.literal((String)font.plainSubstrByWidth(name, tw - 4));
            ctx.text(font, (Component)label, tx + (tw - font.width((FormattedText)label)) / 2, ty + th - 9, on ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
            if (on) {
                ctx.fill(tx + 3, ty + 3, tx + 6, ty + 6, FwmTheme.ACCENT);
            }
            if (!pack.isEmpty()) {
                boolean fav = TextureMixer.favourite(pack);
                int starX = tx + tw - 10;
                boolean overStar = TexturesPanel.in(mouseX, mouseY, starX - 1, ty, 11, 11);
                if (fav || over) {
                    ctx.text(font, (Component)Component.literal((String)(fav ? "\u2605" : "\u2606")), starX, ty + 2, fav ? -865972 : (overStar ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM), false);
                }
                this.hits.add((Object)new Hit(starX - 1, ty, 11, 11, 0, () -> TextureMixer.toggleFavourite(pack)));
            }
            String id = this.category;
            this.hits.add((Object)new Hit(tx, ty, tw, th, 0, () -> TextureMixer.choose(id, pack)));
        }
        if (styles.isEmpty()) {
            String none = this.onlyFavourites ? Lang.t("Noch keine Favoriten \u2013 Stern auf einer Kachel.", "No favourites yet \u2013 star a tile.") : Lang.t("Kein Pack passt.", "No pack fits.");
            ctx.text(font, (Component)Component.literal((String)none), this.midX() + (this.midW() - font.width(none)) / 2, this.gridY() + 30, FwmTheme.TEXT_DIM, false);
        }
    }

    private static void fit(GuiGraphicsExtractor ctx, TextureMixer.Preview p, int px, int py, int bw, int bh) {
        int h;
        int w = p.width();
        float scale = Math.min((float)((float)bw / (float)w), (float)((float)bh / (float)(h = p.frameHeight())));
        if (scale >= 2.0f) {
            scale = (float)Math.floor((double)scale);
        }
        int dw = Math.max((int)1, (int)Math.round((float)((float)w * scale)));
        int dh = Math.max((int)1, (int)Math.round((float)((float)h * scale)));
        FwmGfx.blit(ctx, p.id(), px + (bw - dw) / 2, py + (bh - dh) / 2, dw, dh, w, h, w, p.height());
    }

    private static void picture(GuiGraphicsExtractor ctx, TextureMixer.Preview p, boolean tiled, int px, int py, int size) {
        int h;
        if (tiled) {
            int s = size / 3;
            for (int r = 0; r < 3; ++r) {
                for (int c = 0; c < 3; ++c) {
                    FwmGfx.blit(ctx, p.id(), px + c * s + (size - 3 * s) / 2, py + r * s + (size - 3 * s) / 2, s, s, p.width(), p.frameHeight(), p.width(), p.height());
                }
            }
            return;
        }
        int w = p.width();
        float scale = Math.min((float)((float)size / (float)w), (float)((float)size / (float)(h = p.frameHeight())));
        if (scale >= 1.0f) {
            scale = (float)Math.floor((double)scale);
        }
        int dw = Math.max((int)1, (int)Math.round((float)((float)w * scale)));
        int dh = Math.max((int)1, (int)Math.round((float)((float)h * scale)));
        FwmGfx.blit(ctx, p.id(), px + (size - dw) / 2, py + (size - dh) / 2, dw, dh, w, h, w, p.height());
    }

    private void themes(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int ty = this.y + 14 + 6;
        for (String line : font.split((FormattedText)Component.literal((String)Lang.t("Ein Theme merkt sich die ganze Auswahl. Anwenden setzt sie, \u201eMix bauen\u201c macht sie aktiv.", "A theme keeps the whole choice. Apply sets it, \"Build\" makes it real.")), 118).stream().map(o -> {
            StringBuilder b = new StringBuilder();
            o.accept((index, style, cp) -> {
                b.appendCodePoint(cp);
                return true;
            });
            return b.toString();
        }).toList()) {
            ctx.text(font, (Component)Component.literal((String)line), this.x + 2, ty, FwmTheme.TEXT_DIM, false);
            ty += 10;
        }
        ctx.text(font, (Component)Component.literal((String)(this.renaming == null ? Lang.t("Aktuelle Auswahl speichern", "Save current choice") : Lang.t("Umbenennen: ", "Rename: ") + this.renaming.name)), this.midX(), this.y + 12, FwmTheme.TEXT_DIM, false);
        int fw = this.midW() - 66;
        FwmTheme.box(ctx, this.midX(), this.y + 23, fw, 15, FwmTheme.INSET, this.themeName.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
        this.themeName.extractWidgetRenderState(ctx, mouseX, mouseY, 0.0f);
        this.button(ctx, font, this.midX() + fw + 4, this.y + 23, 62, this.renaming == null ? Lang.t("Speichern", "Save") : "OK", mouseX, mouseY, this::saveTheme);
        List<FwmConfig.TextureTheme> themes = FwmConfig.INSTANCE.textureThemes;
        int top = this.gridY();
        int rowH = 16;
        int visible = Math.max((int)1, (int)((this.footY() - 6 - top) / rowH));
        int total = themes.size() + 1;
        this.themeScroll = Math.max((int)0, (int)Math.min((int)this.themeScroll, (int)(total - visible)));
        for (int i = 0; i < visible && this.themeScroll + i < total; ++i) {
            FwmConfig.TextureTheme theme;
            int idx = this.themeScroll + i;
            int ry = top + i * rowH;
            FwmConfig.TextureTheme textureTheme = theme = idx == 0 ? null : (FwmConfig.TextureTheme)themes.get(idx - 1);
            if (TexturesPanel.in(mouseX, mouseY, this.midX(), ry, this.midW(), rowH)) {
                FwmTheme.hover(ctx, this.midX(), ry, this.midW(), rowH);
            }
            String name = theme == null ? Lang.t("Alles Vanilla", "All vanilla") : theme.name;
            String info = theme == null ? Lang.t("eingebaut", "built in") : theme.mix.size() + Lang.t(" Kategorien", " categories");
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(name, this.midW() - 200)), this.midX() + 3, ry + 4, FwmTheme.TEXT, false);
            ctx.text(font, (Component)Component.literal((String)info), this.midX() + this.midW() - 190, ry + 4, FwmTheme.TEXT_DIM, false);
            int bx = this.midX() + this.midW() - 120;
            this.button(ctx, font, bx, ry + 1, 58, Lang.t("Anwenden", "Apply"), mouseX, mouseY, () -> TextureMixer.applyTheme(theme));
            if (theme == null) continue;
            this.button(ctx, font, bx + 60, ry + 1, 44, Lang.t("Name", "Name"), mouseX, mouseY, () -> {
                this.renaming = theme;
                this.themeName.setValue(theme.name);
                this.themeName.setFocused(true);
            });
            this.button(ctx, font, bx + 106, ry + 1, 14, "\u00d7", mouseX, mouseY, () -> this.confirm(theme));
        }
    }

    private void confirm(FwmConfig.TextureTheme theme) {
        FwmConfig.INSTANCE.textureThemes.remove((Object)theme);
        if (this.renaming == theme) {
            this.renaming = null;
        }
        ConfigManager.save();
    }

    private void saveTheme() {
        String name = this.themeName.getValue().trim();
        if (name.isEmpty()) {
            return;
        }
        if (this.renaming != null) {
            FwmConfig.INSTANCE.textureThemes.removeIf(t -> t != this.renaming && name.equalsIgnoreCase(t.name));
            this.renaming.name = name;
            this.renaming = null;
            ConfigManager.save();
        } else {
            TextureMixer.saveTheme(name);
        }
        this.themeName.setValue("");
        this.themeName.setFocused(false);
    }

    private void footer(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int sy = this.footY() - 11;
        if (TextureMixer.building()) {
            int barW = this.width - 6;
            ctx.fill(this.x + 3, sy + 2, this.x + 3 + barW, sy + 7, 0x55FFFFFF);
            ctx.fill(this.x + 3, sy + 2, this.x + 3 + Math.round((float)((float)barW * TextureMixer.progress())), sy + 7, FwmTheme.ACCENT);
        } else {
            String msg = TextureMixer.message();
            if (msg.isEmpty() && this.packs.isEmpty()) {
                msg = Lang.t("Keine Resource-Packs im Ordner gefunden.", "No resource packs found in the folder.");
            }
            if (msg.isEmpty() && TextureMixer.active(Minecraft.getInstance())) {
                msg = Lang.t("FWM Mix ist aktiv.", "FWM Mix is on.");
            }
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(msg, this.width - 6)), this.x + 3, sy, FwmTheme.TEXT_DIM, false);
        }
        Minecraft client = Minecraft.getInstance();
        int w = (this.width - 12) / 4;
        int by = this.footY();
        this.button(ctx, font, this.x, by, w, Lang.t("Mix bauen", "Build mix"), mouseX, mouseY, () -> TextureMixer.build(client));
        this.button(ctx, font, this.x + (w + 4), by, w, Lang.t("Mix entfernen", "Remove mix"), mouseX, mouseY, () -> TextureMixer.remove(client));
        this.button(ctx, font, this.x + 2 * (w + 4), by, w, Lang.t("Quell-Packs aus: ", "Sources off: ") + (FwmConfig.INSTANCE.textureMixDisableSources ? Lang.t("An", "On") : Lang.t("Aus", "Off")), mouseX, mouseY, () -> {
            FwmConfig.INSTANCE.textureMixDisableSources = !FwmConfig.INSTANCE.textureMixDisableSources;
            ConfigManager.save();
        });
        this.button(ctx, font, this.x + 3 * (w + 4), by, this.width - 3 * (w + 4), Lang.t("Neu einlesen", "Rescan"), mouseX, mouseY, () -> {
            TextureMixer.clearPreviews();
            this.onResize();
        });
    }

    private void button(GuiGraphicsExtractor ctx, Font font, int bx, int by, int bw, String label, int mouseX, int mouseY, Runnable action) {
        boolean over = TexturesPanel.in(mouseX, mouseY, bx, by, bw, 14);
        FwmTheme.box(ctx, bx, by, bw, 14, FwmTheme.RAISED, over ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent text = Component.literal((String)font.plainSubstrByWidth(label, bw - 4));
        int n = bx + (bw - font.width((FormattedText)text)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)text, n, by + (14 - 9) / 2 + 1, over ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        this.hits.add((Object)new Hit(bx, by, bw, 14, 0, action));
    }

    private static boolean in(double mx, double my, int bx, int by, int bw, int bh) {
        return mx >= (double)bx && mx < (double)(bx + bw) && my >= (double)by && my < (double)(by + bh);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        EditBox field = this.themesTab ? this.themeName : this.search;
        boolean inField = TexturesPanel.in(mouseX, mouseY, this.midX(), this.y + 23, this.themesTab ? this.midW() - 66 : this.midW() - 106, 15);
        field.setFocused(inField);
        if (inField) {
            return true;
        }
        if (button != 0) {
            return true;
        }
        for (Hit hit : this.hits) {
            if (!TexturesPanel.in(mouseX, mouseY, hit.x(), hit.y(), hit.w(), hit.h())) continue;
            hit.action().run();
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        int step = -((int)Math.signum((double)vertical));
        if (mouseX < (double)(this.x + 122)) {
            this.listScroll = Math.max((int)0, (int)(this.listScroll + step));
        } else if (this.themesTab) {
            this.themeScroll = Math.max((int)0, (int)(this.themeScroll + step));
        } else {
            this.gridScroll = Math.max((int)0, (int)(this.gridScroll + step));
        }
        return true;
    }

    private EditBox focused() {
        if (this.themesTab) {
            return this.themeName != null && this.themeName.isFocused() ? this.themeName : null;
        }
        return this.search != null && this.search.isFocused() ? this.search : null;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.focused() != null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EditBox f = this.focused();
        if (f == null) {
            return false;
        }
        if (keyCode == 41) {
            f.setFocused(false);
            if (f == this.themeName) {
                this.renaming = null;
            }
            return true;
        }
        if (keyCode == 40 && f == this.themeName) {
            this.saveTheme();
            return true;
        }
        boolean used = f.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
        if (f == this.search) {
            this.gridScroll = 0;
        }
        return used;
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        EditBox f = this.focused();
        if (f == null) {
            return false;
        }
        if (f == this.search) {
            this.gridScroll = 0;
        }
        return f.charTyped(input);
    }

    @Override
    public void onHidden() {
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        if (this.themeName != null) {
            this.host.detach((AbstractWidget)this.themeName);
        }
        TextureMixer.releasePreviews();
    }

    private record Hit(int x, int y, int w, int h, int button, Runnable action) {
    }
}
