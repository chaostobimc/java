/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.HashSet
 *  java.util.Iterator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Set
 *  java.util.function.Consumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.client.input.MouseButtonInfo
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.seventv;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteApi;
import net.friendswithmoney.fwm.seventv.EmoteConfig;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.EmoteTexture;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.input.MouseButtonInfo;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;

public final class EmotePicker {
    private static final int C_BG = -265605839;
    private static final int C_BG_DARK = -266461406;
    private static final int C_BORDER = -14803166;
    private static final int C_HOVER = 0x40FFFFFF;
    private static final int C_TEXT = -2367775;
    private static final int C_MUTED = -7038044;
    private static final int C_ACCENT = -10983950;
    private static final int C_7TV = -14043402;
    private static final int C_BTTV = -2818028;
    private static final int CELL = 24;
    private static final int ICON = 20;
    private static final int HEADER_H = 14;
    private static final int KEY_ENTER = 40;
    private static final int KEY_KP_ENTER = 88;
    private static final int KEY_ESCAPE = 41;
    private static final int KEY_TAB = 43;
    private final Minecraft mc = Minecraft.getInstance();
    private Consumer<Emote> onPick;
    private static EmotePicker INSTANCE;
    private static volatile List<Emote> preSeven;
    private static volatile List<Emote> preBttv;
    private static volatile long preAt;
    private static volatile boolean preloadingSeven;
    private static volatile boolean preloadingBttv;
    private static final long PRELOAD_TTL = 1800000L;
    private static final int MIN_W = 190;
    private static final int MIN_H = 150;
    private boolean resizing;
    private boolean selectingText;
    private MouseButtonInfo selectButton;
    private static long lastToggle;
    private EditBox search;
    private boolean open;
    private Tab tab = Tab.ALL;
    private int x;
    private int y;
    private int w;
    private int h;
    private int gridX;
    private int gridY;
    private int gridW;
    private int gridH;
    private int cols;
    private double scroll;
    private String jumpTarget;
    private String activeQuery = "";
    private String pendingQuery = "";
    private long queryChangedAt;
    private int requestId;
    private final List<Emote> seven = new ArrayList();
    private final List<Emote> bttv = new ArrayList();
    private int sevenPage = 1;
    private int bttvOffset = 0;
    private boolean sevenMore = true;
    private boolean bttvMore = true;
    private boolean loadingSeven;
    private boolean loadingBttv;
    private boolean errorSeven;
    private boolean errorBttv;
    private final List<Row> rows = new ArrayList();
    private final Map<String, Integer> sectionOffsets = new LinkedHashMap();
    private int contentHeight;
    private Emote hovered;

    public EmotePicker(Consumer<Emote> onPick) {
        this.onPick = onPick;
    }

    public static EmotePicker get() {
        if (INSTANCE == null) {
            INSTANCE = new EmotePicker((Consumer<Emote>)((Consumer)e -> {}));
        }
        return INSTANCE;
    }

    public static void preload() {
        Minecraft mc = Minecraft.getInstance();
        boolean fresh = System.currentTimeMillis() - preAt < 1800000L;
        for (Emote e : EmoteConfig.frequent(36)) {
            EmoteTexture.get(e);
        }
        if (!(preloadingSeven || fresh && preSeven != null)) {
            preloadingSeven = true;
            EmoteApi.searchSevenTv("", 1, 60).whenComplete((list, err) -> mc.execute(() -> {
                preloadingSeven = false;
                if (err != null || list == null || list.isEmpty()) {
                    return;
                }
                for (Emote e : list) {
                    EmoteRegistry.register(e);
                    EmoteTexture.get(e);
                }
                preSeven = List.copyOf((Collection)list);
                preAt = System.currentTimeMillis();
                SevenTv.LOG.info("Emote Chat: {} 7TV-Emotes vorgeladen", (Object)list.size());
            }));
        }
        if (!(preloadingBttv || fresh && preBttv != null)) {
            preloadingBttv = true;
            EmoteApi.searchBttv("", 0, 100).whenComplete((list, err) -> mc.execute(() -> {
                preloadingBttv = false;
                if (err != null || list == null || list.isEmpty()) {
                    return;
                }
                for (Emote e : list) {
                    EmoteRegistry.register(e);
                    EmoteTexture.get(e);
                }
                preBttv = List.copyOf((Collection)list);
                preAt = System.currentTimeMillis();
                SevenTv.LOG.info("Emote Chat: {} BTTV-Emotes vorgeladen", (Object)list.size());
            }));
        }
    }

    public void setOnPick(Consumer<Emote> onPick) {
        this.onPick = onPick;
    }

    public void toggleDebounced() {
        long now = System.currentTimeMillis();
        if (now - lastToggle < 250L) {
            return;
        }
        lastToggle = now;
        this.toggle();
    }

    public static boolean toggledRecently() {
        return System.currentTimeMillis() - lastToggle < 1000L;
    }

    public boolean isOpen() {
        return this.open;
    }

    public void toggle() {
        if (this.open) {
            this.close();
        } else {
            this.open();
        }
    }

    public void open() {
        EmotePicker.preload();
        this.open = true;
        this.scroll = 0.0;
        this.jumpTarget = null;
        if (this.search == null) {
            this.search = new EditBox(this.mc.font, 0, 0, 100, 14, (Component)Component.literal((String)"Emotes suchen"));
            this.search.setMaxLength(40);
            this.search.setBordered(false);
            this.search.setHint((Component)Component.literal((String)"Finde das perfekte Emote"));
            this.search.setTextColor(-2367775);
        }
        this.search.setValue("");
        this.search.setFocused(true);
        this.activeQuery = "";
        this.pendingQuery = "";
        this.startQuery("");
    }

    public void close() {
        this.open = false;
        if (this.resizing) {
            this.resizing = false;
            EmoteConfig.save();
        }
        this.selectingText = false;
        if (this.search != null) {
            this.search.setFocused(false);
        }
    }

    private void startQuery(String q) {
        this.activeQuery = q;
        int rid = ++this.requestId;
        this.seven.clear();
        this.bttv.clear();
        this.sevenPage = 1;
        this.bttvOffset = 0;
        this.bttvMore = true;
        this.sevenMore = true;
        this.errorBttv = false;
        this.errorSeven = false;
        this.scroll = 0.0;
        this.jumpTarget = null;
        if (q.isBlank()) {
            List<Emote> ps = preSeven;
            List<Emote> pb = preBttv;
            if (ps != null) {
                this.seven.addAll(ps);
                this.sevenPage = 2;
            }
            if (pb != null) {
                this.bttv.addAll(pb);
                this.bttvMore = false;
            }
        }
        this.loadMore(rid);
    }

    private void loadMore(int rid) {
        boolean sevenPaging;
        if (rid != this.requestId) {
            return;
        }
        boolean bl = sevenPaging = this.tab == Tab.SEVENTV || !this.activeQuery.isBlank() || this.sevenPage == 1;
        if (this.tab != Tab.BTTV && this.sevenMore && sevenPaging && !this.loadingSeven && !this.errorSeven) {
            this.loadingSeven = true;
            int page = this.sevenPage++;
            EmoteApi.searchSevenTv(this.activeQuery, page, 60).whenComplete((list, err) -> this.mc.execute(() -> {
                if (rid != this.requestId) {
                    return;
                }
                this.loadingSeven = false;
                if (err != null) {
                    this.errorSeven = true;
                    SevenTv.LOG.warn("7TV konnte nicht geladen werden: {}", (Object)String.valueOf((Object)err));
                    return;
                }
                if (list.isEmpty()) {
                    this.sevenMore = false;
                }
                for (Emote e : list) {
                    EmoteRegistry.register(e);
                }
                this.seven.addAll((Collection)list);
            }));
        }
        if (this.tab != Tab.SEVENTV && this.bttvMore && !this.loadingBttv && !this.errorBttv) {
            this.loadingBttv = true;
            int off = this.bttvOffset;
            this.bttvOffset += 50;
            EmoteApi.searchBttv(this.activeQuery, off, this.activeQuery.isBlank() ? 100 : 50).whenComplete((list, err) -> this.mc.execute(() -> {
                if (rid != this.requestId) {
                    return;
                }
                this.loadingBttv = false;
                if (err != null) {
                    this.errorBttv = true;
                    SevenTv.LOG.warn("BetterTTV konnte nicht geladen werden: {}", (Object)String.valueOf((Object)err));
                    return;
                }
                if (list.isEmpty() || this.activeQuery.isBlank()) {
                    this.bttvMore = false;
                }
                for (Emote e : list) {
                    EmoteRegistry.register(e);
                }
                this.bttv.addAll((Collection)list);
            }));
        }
    }

    private void rebuildRows() {
        this.rows.clear();
        this.sectionOffsets.clear();
        HashSet seenNames = new HashSet();
        HashSet seenHashes = new HashSet();
        if (this.activeQuery.isBlank()) {
            List<Emote> freq = this.filterTab(EmoteConfig.frequent(36));
            if (!freq.isEmpty()) {
                this.addSection("H\u00e4ufig verwendet", -2367775, EmotePicker.dedupe(freq, (Set<String>)new HashSet(), (Set<Long>)new HashSet()), "freq");
            }
            if (this.tab != Tab.BTTV) {
                this.addSection("7TV \u2013 Beliebt", -14043402, EmotePicker.dedupe(this.seven, (Set<String>)seenNames, (Set<Long>)seenHashes), "7tv");
            }
            if (this.tab != Tab.SEVENTV) {
                this.addSection("BetterTTV \u2013 Beliebt", -2818028, EmotePicker.dedupe(this.bttv, (Set<String>)seenNames, (Set<Long>)seenHashes), "bttv");
            }
        } else {
            ArrayList merged = new ArrayList();
            if (this.tab != Tab.BTTV) {
                merged.addAll(this.seven);
            }
            if (this.tab != Tab.SEVENTV) {
                merged.addAll(this.bttv);
            }
            String q = this.activeQuery.trim().toLowerCase(Locale.ROOT);
            ArrayList exact = new ArrayList();
            ArrayList starts = new ArrayList();
            ArrayList rest = new ArrayList();
            ArrayList inter = new ArrayList();
            List<Emote> a = this.tab == Tab.BTTV ? List.of() : this.seven;
            List<Emote> b = this.tab == Tab.SEVENTV ? List.of() : this.bttv;
            for (int i = 0; i < Math.max((int)a.size(), (int)b.size()); ++i) {
                if (i < a.size()) {
                    inter.add((Object)((Emote)((Object)a.get(i))));
                }
                if (i >= b.size()) continue;
                inter.add((Object)((Emote)((Object)b.get(i))));
            }
            for (Emote e : inter) {
                String n = e.name().toLowerCase(Locale.ROOT);
                if (n.equals((Object)q)) {
                    exact.add((Object)e);
                    continue;
                }
                if (n.startsWith(q)) {
                    starts.add((Object)e);
                    continue;
                }
                rest.add((Object)e);
            }
            ArrayList ordered = new ArrayList();
            ordered.addAll((Collection)exact);
            ordered.addAll((Collection)starts);
            ordered.addAll((Collection)rest);
            HashSet sevenNames = new HashSet();
            for (Emote e : this.seven) {
                sevenNames.add((Object)e.name().toLowerCase(Locale.ROOT));
            }
            ArrayList filtered = new ArrayList();
            for (Emote e : ordered) {
                if (e.provider() == Emote.Provider.BTTV && this.tab == Tab.ALL && sevenNames.contains((Object)e.name().toLowerCase(Locale.ROOT))) continue;
                filtered.add((Object)e);
            }
            this.addSection("Suchergebnisse f\u00fcr \u201e" + this.activeQuery.trim() + "\u201c", -2367775, EmotePicker.dedupe((List<Emote>)filtered, (Set<String>)seenNames, (Set<Long>)seenHashes), "results");
        }
        this.contentHeight = 0;
        for (Row r : this.rows) {
            this.contentHeight += r instanceof Header ? 14 : 24;
        }
    }

    private List<Emote> filterTab(List<Emote> list) {
        if (this.tab == Tab.ALL) {
            return list;
        }
        ArrayList out = new ArrayList();
        for (Emote e : list) {
            if (this.tab == Tab.SEVENTV && e.provider() == Emote.Provider.SEVENTV) {
                out.add((Object)e);
            }
            if (this.tab != Tab.BTTV || e.provider() != Emote.Provider.BTTV) continue;
            out.add((Object)e);
        }
        return out;
    }

    private static List<Emote> dedupe(List<Emote> in, Set<String> names, Set<Long> hashes) {
        ArrayList out = new ArrayList();
        for (Emote e : in) {
            if (!names.add((Object)e.name().toLowerCase(Locale.ROOT))) continue;
            EmoteTexture t = EmoteTexture.peek(e);
            if (t != null && t.state == EmoteTexture.State.READY) {
                boolean dup = false;
                Iterator iterator = hashes.iterator();
                while (iterator.hasNext()) {
                    long hh = (Long)iterator.next();
                    if (Long.bitCount((long)(hh ^ t.hash)) > 2) continue;
                    dup = true;
                    break;
                }
                if (dup) continue;
                hashes.add((Object)t.hash);
            }
            out.add((Object)e);
        }
        return out;
    }

    private void addSection(String title, int color, List<Emote> list, String id) {
        int offset = 0;
        for (Row r : this.rows) {
            offset += r instanceof Header ? 14 : 24;
        }
        this.sectionOffsets.put((Object)id, (Object)offset);
        this.rows.add((Object)new Header(title, color));
        if (list.isEmpty()) {
            this.rows.add((Object)new Line((List<Emote>)List.of(), id));
            return;
        }
        for (int i = 0; i < list.size(); i += this.cols) {
            this.rows.add((Object)new Line((List<Emote>)list.subList(i, Math.min((int)list.size(), (int)(i + this.cols))), id));
        }
    }

    private void layout(int screenW, int screenH) {
        int maxW = Math.max((int)190, (int)(screenW - 8));
        int maxH = Math.max((int)150, (int)(screenH - 40));
        this.w = Math.max((int)190, (int)Math.min((int)maxW, (int)EmoteConfig.pickerWidth()));
        this.h = Math.max((int)150, (int)Math.min((int)maxH, (int)EmoteConfig.pickerHeight()));
        this.x = screenW - this.w - 2;
        this.y = screenH - 16 - this.h;
        this.gridX = this.x + 26;
        this.gridY = this.y + 40;
        this.gridW = this.w - 26 - 6;
        this.gridH = this.h - 40 - 24;
        this.cols = Math.max((int)1, (int)(this.gridW / 24));
        this.search.setX(this.x + 10);
        this.search.setY(this.y + 24);
        this.search.setWidth(this.w - 30);
        this.search.setHeight(12);
    }

    public void render(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta, int screenW, int screenH) {
        Emote show;
        double maxScroll;
        if (!this.open) {
            return;
        }
        this.layout(screenW, screenH);
        String now = this.search.getValue();
        if (!now.equals((Object)this.pendingQuery)) {
            this.pendingQuery = now;
            this.queryChangedAt = System.currentTimeMillis();
        }
        if (!this.pendingQuery.equals((Object)this.activeQuery) && System.currentTimeMillis() - this.queryChangedAt > 300L) {
            this.startQuery(this.pendingQuery);
        }
        if (this.selectingText && this.selectButton != null) {
            this.search.mouseDragged(this.searchEvent(mouseX, this.selectButton), 0.0, 0.0);
        }
        if (this.resizing) {
            int right = screenW - 2;
            int bottom = screenH - 16;
            EmoteConfig.setPickerSize(right - mouseX, bottom - mouseY);
            this.layout(screenW, screenH);
        }
        this.rebuildRows();
        if (this.jumpTarget != null && this.sectionOffsets.containsKey((Object)this.jumpTarget)) {
            this.scroll = ((Integer)this.sectionOffsets.get((Object)this.jumpTarget)).intValue();
        }
        if (this.scroll > (maxScroll = (double)Math.max((int)0, (int)(this.contentHeight - this.gridH)))) {
            this.scroll = maxScroll;
        }
        if (this.scroll < 0.0) {
            this.scroll = 0.0;
        }
        if (maxScroll - this.scroll < 72.0) {
            this.loadMore(this.requestId);
        }
        Font font = this.mc.font;
        g.nextStratum();
        g.fill(this.x - 1, this.y - 1, this.x + this.w + 1, this.y + this.h + 1, -14803166);
        g.fill(this.x, this.y, this.x + this.w, this.y + this.h, -265605839);
        this.drawGrip(g, mouseX, mouseY);
        int tx = this.x + 14;
        for (Tab t : Tab.values()) {
            boolean hov;
            int tw = font.width(t.label) + 10;
            boolean sel = t == this.tab;
            boolean bl = hov = mouseX >= tx && mouseX < tx + tw && mouseY >= this.y + 4 && mouseY < this.y + 17;
            if (sel) {
                g.fill(tx, this.y + 4, tx + tw, this.y + 17, -12565943);
            } else if (hov) {
                g.fill(tx, this.y + 4, tx + tw, this.y + 17, 0x30FFFFFF);
            }
            g.text(font, t.label, tx + 5, this.y + 7, sel ? -1 : -7038044, false);
            tx += tw + 4;
        }
        g.fill(this.x + 6, this.y + 21, this.x + this.w - 6, this.y + 36, -266461406);
        g.outline(this.x + 6, this.y + 21, this.w - 12, 15, -10983950);
        this.search.extractWidgetRenderState(g, mouseX, mouseY, delta);
        this.drawMagnifier(g, this.x + this.w - 18, this.y + 24);
        g.fill(this.x, this.gridY - 2, this.x + 22, this.y + this.h - 22, -266461406);
        int sy = this.gridY + 2;
        sy = this.sidebarButton(g, font, "freq", "", -2367775, sy, mouseX, mouseY);
        sy = this.sidebarButton(g, font, "7tv", "7", -14043402, sy, mouseX, mouseY);
        this.sidebarButton(g, font, "bttv", "B", -2818028, sy, mouseX, mouseY);
        this.hovered = null;
        g.enableScissor(this.gridX, this.gridY, this.gridX + this.gridW, this.gridY + this.gridH);
        int cy = this.gridY - (int)this.scroll;
        for (Row r : this.rows) {
            int rh;
            int n = rh = r instanceof Header ? 14 : 24;
            if (cy + rh >= this.gridY && cy <= this.gridY + this.gridH) {
                if (r instanceof Header) {
                    Header hd = (Header)r;
                    g.text(font, hd.title(), this.gridX + 2, cy + 3, hd.color(), false);
                } else if (r instanceof Line) {
                    Line ln = (Line)r;
                    if (ln.emotes().isEmpty()) {
                        g.text(font, this.statusText(ln.section()), this.gridX + 4, cy + 8, -7038044, false);
                    }
                    for (int i = 0; i < ln.emotes().size(); ++i) {
                        boolean hov;
                        Emote e = (Emote)((Object)ln.emotes().get(i));
                        int ex = this.gridX + i * 24;
                        boolean bl = hov = mouseX >= ex && mouseX < ex + 24 && mouseY >= Math.max((int)cy, (int)this.gridY) && mouseY < Math.min((int)(cy + 24), (int)(this.gridY + this.gridH));
                        if (hov) {
                            g.fill(ex, cy, ex + 24, cy + 24, 0x40FFFFFF);
                            this.hovered = e;
                        }
                        EmotePicker.drawEmote(g, e, ex + 2, cy + 2, 20, 20);
                    }
                }
            }
            cy += rh;
        }
        g.disableScissor();
        if (this.contentHeight > this.gridH) {
            int barH = Math.max((int)12, (int)((int)((double)this.gridH * ((double)this.gridH / (double)this.contentHeight))));
            int barY = this.gridY + (int)((double)(this.gridH - barH) * (this.scroll / (double)Math.max((int)1, (int)(this.contentHeight - this.gridH))));
            g.fill(this.x + this.w - 5, this.gridY, this.x + this.w - 2, this.gridY + this.gridH, 0x40000000);
            g.fill(this.x + this.w - 5, barY, this.x + this.w - 2, barY + barH, -15066338);
        }
        int fy = this.y + this.h - 22;
        g.fill(this.x, fy, this.x + this.w, this.y + this.h, -266461406);
        Emote emote = show = this.hovered != null ? this.hovered : this.firstResult();
        if (show != null) {
            EmotePicker.drawEmote(g, show, this.x + 8, fy + 3, 16, 16);
            g.text(font, show.chatCode(), this.x + 30, fy + 4, -2367775, false);
            g.text(font, show.provider().label, this.x + 30, fy + 13, show.provider() == Emote.Provider.SEVENTV ? -14043402 : -2818028, false);
        } else {
            g.text(font, "Emote ausw\u00e4hlen", this.x + 8, fy + 8, -7038044, false);
        }
    }

    private int sidebarButton(GuiGraphicsExtractor g, Font font, String id, String label, int color, int sy, int mx, int my) {
        if (!this.sectionOffsets.containsKey((Object)id)) {
            return sy;
        }
        boolean hov = mx >= this.x + 2 && mx < this.x + 20 && my >= sy && my < sy + 18;
        g.fill(this.x + 2, sy, this.x + 20, sy + 18, hov ? -12565943 : -13552840);
        if (label.isEmpty()) {
            this.drawClock(g, this.x + 6, sy + 4, color);
        } else {
            g.centeredText(font, label, this.x + 11, sy + 5, color);
        }
        return sy + 22;
    }

    private boolean overSearch(double mx, double my) {
        return mx >= (double)(this.x + 6) && mx < (double)(this.x + this.w - 6) && my >= (double)(this.y + 21) && my < (double)(this.y + 36);
    }

    private MouseButtonEvent searchEvent(double mx, MouseButtonInfo info) {
        double cx = Math.max((double)this.search.getX(), (double)Math.min((double)(this.search.getX() + this.search.getWidth() - 1), (double)mx));
        double cy = (double)this.search.getY() + (double)this.search.getHeight() / 2.0;
        return new MouseButtonEvent(cx, cy, info);
    }

    private boolean overGrip(double mx, double my) {
        return mx >= (double)(this.x - 1) && mx < (double)(this.x + 12) && my >= (double)(this.y - 1) && my < (double)(this.y + 12);
    }

    private void drawGrip(GuiGraphicsExtractor g, int mx, int my) {
        boolean hov = this.resizing || this.overGrip(mx, my);
        int c = hov ? -10983950 : -7038044;
        int gx = this.x + 3;
        int gy = this.y + 3;
        g.fill(gx + 1, gy, gx + 5, gy + 6, c);
        g.fill(gx, gy + 1, gx + 6, gy + 5, c);
        if (hov && !this.resizing) {
            g.setTooltipForNextFrame(this.mc.font, (Component)Component.literal((String)"Ziehen, um die Gr\u00f6\u00dfe zu \u00e4ndern"), mx, my);
        }
    }

    private void drawClock(GuiGraphicsExtractor g, int cx, int cy, int c) {
        g.fill(cx + 3, cy, cx + 7, cy + 1, c);
        g.fill(cx + 3, cy + 9, cx + 7, cy + 10, c);
        g.fill(cx, cy + 3, cx + 1, cy + 7, c);
        g.fill(cx + 9, cy + 3, cx + 10, cy + 7, c);
        g.fill(cx + 1, cy + 1, cx + 3, cy + 3, c);
        g.fill(cx + 7, cy + 1, cx + 9, cy + 3, c);
        g.fill(cx + 1, cy + 7, cx + 3, cy + 9, c);
        g.fill(cx + 7, cy + 7, cx + 9, cy + 9, c);
        g.fill(cx + 4, cy + 2, cx + 5, cy + 5, c);
        g.fill(cx + 5, cy + 5, cx + 7, cy + 6, c);
    }

    private void drawMagnifier(GuiGraphicsExtractor g, int mx, int my) {
        int c = -7038044;
        g.fill(mx + 1, my, mx + 5, my + 1, c);
        g.fill(mx + 1, my + 5, mx + 5, my + 6, c);
        g.fill(mx, my + 1, mx + 1, my + 5, c);
        g.fill(mx + 5, my + 1, mx + 6, my + 5, c);
        g.fill(mx + 5, my + 5, mx + 7, my + 7, c);
        g.fill(mx + 7, my + 7, mx + 9, my + 9, c);
    }

    public static void drawEmote(GuiGraphicsExtractor g, Emote e, int bx, int by, int bw, int bh) {
        EmoteTexture t = EmoteTexture.get(e);
        if (t.state != EmoteTexture.State.READY) {
            int c = t.state == EmoteTexture.State.FAILED ? 0x40FF4444 : 0x20FFFFFF;
            g.fill(bx + 3, by + 3, bx + bw - 3, by + bh - 3, c);
            return;
        }
        float aspect = t.aspect();
        int dw = bw;
        int dh = bh;
        if (aspect >= 1.0f) {
            dh = Math.max((int)1, (int)Math.round((float)((float)bw / aspect)));
        } else {
            dw = Math.max((int)1, (int)Math.round((float)((float)bh * aspect)));
        }
        int dx = bx + (bw - dw) / 2;
        int dy = by + (bh - dh) / 2;
        int frame = t.currentFrame();
        g.blit(RenderPipelines.GUI_TEXTURED, t.id, dx, dy, 0.0f, (float)(frame * t.frameH), dw, dh, t.frameW, t.frameH, t.frameW, t.frameH * t.frames);
    }

    /*
     * Exception decompiling
     */
    private String statusText(String section) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1802596)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4489513)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4491475)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820062)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820183)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1024810)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4466968)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:604767)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4234497)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private Emote firstResult() {
        for (Row r : this.rows) {
            Line ln;
            if (!(r instanceof Line) || (ln = (Line)r).emotes().isEmpty()) continue;
            return (Emote)((Object)ln.emotes().get(0));
        }
        return null;
    }

    public boolean isInside(double mx, double my) {
        return this.open && (this.resizing || mx >= (double)(this.x - 1) && mx < (double)(this.x + this.w + 1) && my >= (double)(this.y - 1) && my < (double)(this.y + this.h + 1));
    }

    public boolean mouseClicked(MouseButtonEvent event, boolean doubleClick) {
        boolean shift;
        double mx = event.x();
        double my = event.y();
        int button = event.button();
        boolean bl = shift = (event.modifiers() & 3) != 0;
        if (!this.open) {
            return false;
        }
        if (!this.isInside(mx, my)) {
            this.close();
            return false;
        }
        if (button == 1 && this.overGrip(mx, my)) {
            this.resizing = true;
            return true;
        }
        if (this.overSearch(mx, my)) {
            this.search.setFocused(true);
            this.search.mouseClicked(this.searchEvent(mx, event.buttonInfo()), doubleClick);
            if (button == 1) {
                this.selectingText = true;
                this.selectButton = event.buttonInfo();
            }
            return true;
        }
        int tx = this.x + 14;
        for (Tab t : Tab.values()) {
            int tw = this.mc.font.width(t.label) + 10;
            if (mx >= (double)tx && mx < (double)(tx + tw) && my >= (double)(this.y + 4) && my < (double)(this.y + 17)) {
                if (this.tab != t) {
                    this.tab = t;
                    this.scroll = 0.0;
                    this.jumpTarget = null;
                    this.loadMore(this.requestId);
                }
                return true;
            }
            tx += tw + 4;
        }
        int sy = this.gridY + 2;
        for (String id : new String[]{"freq", "7tv", "bttv"}) {
            if (!this.sectionOffsets.containsKey((Object)id)) continue;
            if (mx >= (double)(this.x + 2) && mx < (double)(this.x + 20) && my >= (double)sy && my < (double)(sy + 18)) {
                this.scroll = ((Integer)this.sectionOffsets.get((Object)id)).intValue();
                this.jumpTarget = id;
                return true;
            }
            sy += 22;
        }
        if (mx >= (double)this.gridX && mx < (double)(this.gridX + this.gridW) && my >= (double)this.gridY && my < (double)(this.gridY + this.gridH)) {
            int cy = this.gridY - (int)this.scroll;
            for (Row r : this.rows) {
                int rh;
                int n = rh = r instanceof Header ? 14 : 24;
                if (r instanceof Line) {
                    int idx;
                    Line ln = (Line)r;
                    if (my >= (double)cy && my < (double)(cy + rh) && (idx = (int)((mx - (double)this.gridX) / 24.0)) >= 0 && idx < ln.emotes().size()) {
                        this.pick((Emote)((Object)ln.emotes().get(idx)), shift);
                        return true;
                    }
                }
                cy += rh;
            }
        }
        this.search.setFocused(true);
        return true;
    }

    public boolean mouseReleased(int button) {
        if (this.selectingText) {
            this.selectingText = false;
            return true;
        }
        if (!this.resizing) {
            return false;
        }
        this.resizing = false;
        EmoteConfig.save();
        return true;
    }

    public boolean isResizing() {
        return this.resizing;
    }

    public boolean mouseScrolled(double mx, double my, double amount) {
        if (!this.open || !this.isInside(mx, my)) {
            return false;
        }
        this.jumpTarget = null;
        this.scroll -= amount * 24.0 * 1.5;
        if (this.scroll < 0.0) {
            this.scroll = 0.0;
        }
        return true;
    }

    public boolean keyPressed(KeyEvent e) {
        if (!this.open) {
            return false;
        }
        int key = e.key();
        if (key == 41) {
            this.close();
            return true;
        }
        if (key == 40 || key == 88) {
            Emote first = this.firstResult();
            if (first != null) {
                this.pick(first, false);
            }
            return true;
        }
        if (key == 43) {
            this.tab = Tab.values()[(this.tab.ordinal() + 1) % Tab.values().length];
            this.scroll = 0.0;
            this.jumpTarget = null;
            this.loadMore(this.requestId);
            return true;
        }
        this.search.keyPressed(e);
        return true;
    }

    public boolean charTyped(CharacterEvent e) {
        if (!this.open) {
            return false;
        }
        this.search.charTyped(e);
        return true;
    }

    private void pick(Emote e, boolean keepOpen) {
        EmoteRegistry.bindName(e);
        EmoteConfig.recordUse(e);
        this.onPick.accept((Object)e);
        if (!keepOpen) {
            this.close();
        }
    }

    public static enum Tab {
        ALL("Alle"),
        SEVENTV("7TV"),
        BTTV("BTTV");

        final String label;

        private Tab(String l) {
            this.label = l;
        }
    }

    private static sealed interface Row
    permits Header, Line {
    }

    private record Header(String title, int color) implements Row
    {
    }

    private record Line(List<Emote> emotes, String section) implements Row
    {
    }
}
