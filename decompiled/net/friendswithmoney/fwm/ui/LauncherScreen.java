/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.resources.Identifier
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.auction.Times;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.net.MemberData;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.ui.DesignHost;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.StreamerScreen;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

public class LauncherScreen
extends DesignHost {
    private static final int VW = 426;
    private static final int VH = 256;
    static final String[][] GROUPS = new String[][]{{"Handel", "fwm.tab.sellmacro", "fwm.tab.market", "fwm.tab.kommt", "fwm.tab.spawner", "fwm.tab.spawnermacro", "fwm.tab.money", "fwm.tab.waypoints"}, {"Chat", "fwm.tab.messages", "fwm.tab.secondchat", "fwm.tab.colorcodes", "fwm.tab.members", "fwm.tab.autotpa", "fwm.tab.emotes"}, {"Werkzeuge", "fwm.tab.toolguard", "fwm.tab.rtp", "fwm.tab.antileak", "fwm.tab.fightkit", "fwm.tab.textures"}};
    static final String[] MORE = new String[]{"fwm.tab.extras", "fwm.tab.settings"};
    static final String[] STREAMER = new String[]{"Streamer Tools", "fwm.tab.auction", "fwm.tab.giveaway"};
    private static final int GROUPS_TOP = 32;
    private static final int ROWS = 5;
    private static final int STREAMER_TOP = 186;
    private static final int BELOW_TILES = 228;
    private String query = "";
    private static final int TILE_STEP = 28;
    private static final int VIEW_H = 137;
    private final float[] colScroll = new float[GROUPS.length];
    private final float[] colTarget = new float[GROUPS.length];
    private final float[] colBounce = new float[GROUPS.length];
    private final long[] colTouched = new long[GROUPS.length];
    private final int[][] colViews = new int[GROUPS.length][];
    private long lastFrame;
    private String scrolledFor = "";
    private static final boolean SEARCH = true;
    private final List<int[]> tileBoxes = new ArrayList();
    private final List<String> tileKeys = new ArrayList();
    private static final long SPAWNER_CYCLE_MS = 2500L;
    private static final String TUTORIAL = "fwm.tutorial.replayShort";

    public static int maxScalePercent() {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) {
            return 150;
        }
        int w = client.getWindow().getGuiScaledWidth();
        int h = client.getWindow().getGuiScaledHeight();
        float crisp = LauncherScreen.crispScale(w, h, 426, 256, client.getWindow().getGuiScale());
        float fits = Math.min((float)((float)w / 426.0f), (float)((float)h / 256.0f));
        int most = (int)Math.floor((double)(fits / crisp * 100.0f));
        return Math.max((int)70, (int)Math.min((int)150, (int)most));
    }

    public LauncherScreen(Screen parent) {
        super(parent);
    }

    public LauncherScreen(Screen parent, String page, int tab) {
        super(parent);
        if (page != null) {
            this.openPage(page, tab);
        }
    }

    @Override
    protected int vw() {
        return 426;
    }

    @Override
    protected int vh() {
        return 256;
    }

    /*
     * Exception decompiling
     */
    static String status(String key) {
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

    /*
     * Exception decompiling
     */
    private static String groupName(String german) {
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

    private static String auctionLine() {
        if (Auction.running()) {
            return Lang.t("L\u00e4uft, noch ", "Running, ") + Times.clock((int)((Auction.remainingMs() + 999L) / 1000L));
        }
        if (Auction.hasResult()) {
            return Lang.t("Ergebnis offen", "Result open");
        }
        return Lang.t("Versteigern per /pay", "Auction via /pay");
    }

    private static String giveawayLine() {
        Giveaway.Status g = Giveaway.status();
        int n = Giveaway.participants().size();
        return switch (g) {
            case Giveaway.Status.RUNNING -> "Live, " + n + Lang.t(" dabei", " joined");
            case Giveaway.Status.PAUSED -> Lang.t("Pausiert, ", "Paused, ") + n + Lang.t(" dabei", " joined");
            case Giveaway.Status.ENDED -> Lang.t("Gewinner gezogen", "Winner drawn");
            default -> Lang.t("Verlosen per /msg", "Raffle via /msg");
        };
    }

    private static String spawnerLine() {
        SpawnerData data = Spawners.data();
        if (data == null || data.items == null) {
            return Lang.t("Preise ansehen", "View prices");
        }
        SpawnerData.Item item = LauncherScreen.shownSpawner();
        if (item != null) {
            return item.name + " " + item.buyPrice;
        }
        return Lang.t("Preise ansehen", "View prices");
    }

    private static SpawnerData.Item shownSpawner() {
        SpawnerData data = Spawners.data();
        if (data == null || data.items == null) {
            return null;
        }
        ArrayList priced = new ArrayList();
        for (SpawnerData.Item item : data.items) {
            if (item.name == null || item.buyPrice == null || item.buyPrice.isBlank()) continue;
            priced.add((Object)item);
        }
        if (priced.isEmpty()) {
            return null;
        }
        return (SpawnerData.Item)priced.get((int)(System.currentTimeMillis() / 2500L % (long)priced.size()));
    }

    private static String clanLine() {
        HashSet clans = new HashSet();
        for (MemberData.Member m : Members.list()) {
            if (m.clan == null) continue;
            clans.add((Object)m.clan);
        }
        return clans.isEmpty() ? Lang.t("Mitgliederliste", "Member list") : clans.size() + " Clans";
    }

    private boolean matches(String key) {
        if (this.query.isBlank()) {
            return true;
        }
        return this.title(key).toLowerCase(Locale.ROOT).contains((CharSequence)this.query.toLowerCase(Locale.ROOT).trim());
    }

    private String firstMatch() {
        for (String[] group : GROUPS) {
            for (int i = 1; i < group.length; ++i) {
                if (!this.matches(group[i])) continue;
                return group[i];
            }
        }
        for (int i = 1; i < STREAMER.length; ++i) {
            if (!this.matches(STREAMER[i])) continue;
            return STREAMER[i];
        }
        for (String key : MORE) {
            if (!this.matches(key)) continue;
            return key;
        }
        return null;
    }

    @Override
    protected void drawCanvas(GuiGraphicsExtractor ctx, int mx, int my, float delta) {
        this.tileBoxes.clear();
        this.tileKeys.clear();
        this.brand(ctx, 12, 9, 414);
        if (this.pageKey != null) {
            this.setFrame(14, 34, 398, 200);
            this.drawPage(ctx, mx, my, delta, "Start");
            this.footer(ctx, mx, my, false);
            return;
        }
        this.searchField(ctx);
        this.groups(ctx, mx, my);
        this.footer(ctx, mx, my, true);
    }

    private void searchField(GuiGraphicsExtractor ctx) {
        int brandEnd = 37 + this.font.width("Friends With Money") + 8;
        int sx = Math.max((int)113, (int)brandEnd);
        int sw = Math.max((int)80, (int)Math.min((int)200, (int)(this.infoLeft() - 8 - sx)));
        int sy = 10;
        if (!this.query.isEmpty()) {
            LauncherGfx.ring(ctx, sx - 1, sy - 1, sw + 2, 18, 9, -1436751724);
        }
        LauncherGfx.round(ctx, sx, sy, sw, 16, 8, -219220752);
        LauncherGfx.ring(ctx, sx + 7, sy + 4, 7, 7, 3, -13743558);
        ctx.fill(sx + 13, sy + 10, sx + 15, sy + 12, -13743558);
        if (this.query.isEmpty()) {
            String hint = Lang.t("Lostippen zum Suchen", "Type to search");
            int room = sw - 20 - this.font.width(Keybinds.menuKeyName()) - 14;
            this.text(ctx, LauncherGfx.trim(this.font, hint, room), sx + 20, sy + 4, -9796234);
        } else {
            this.text(ctx, this.query, sx + 20, sy + 4, -15716322);
            if (System.currentTimeMillis() / 500L % 2L == 0L) {
                int cx = sx + 20 + this.font.width(this.query) + 1;
                ctx.fill(cx, sy + 3, cx + 1, sy + 13, -12136081);
            }
        }
        String key = Keybinds.menuKeyName();
        int kw = this.font.width(key) + 6;
        LauncherGfx.ring(ctx, sx + sw - kw - 5, sy + 3, kw, 10, 3, -6442074);
        this.text(ctx, key, sx + sw - kw - 2, sy + 4, -9796234);
    }

    private void groups(GuiGraphicsExtractor ctx, int mx, int my) {
        int colW = 126;
        int gap = 9;
        int x0 = (426 - (3 * colW + 2 * gap)) / 2;
        int y0 = 32;
        String first = this.query.isBlank() ? null : this.firstMatch();
        this.animateColumns(first);
        for (int g = 0; g < GROUPS.length; ++g) {
            int x = x0 + g * (colW + gap);
            this.text(ctx, LauncherScreen.groupName(GROUPS[g][0]).toUpperCase(Locale.ROOT), x + 2, y0, this.query.isBlank() ? -9464447 : 1433376129);
            int top = y0 + 11;
            float offset = this.colScroll[g] + this.colBounce[g];
            boolean overColumn = mx >= x && mx < x + colW && my >= top && my < top + 137;
            this.colViews[g] = new int[]{x, top, colW, 137};
            ctx.enableScissor(x - 1, top, x + colW + 1, top + 137);
            Matrix3x2fStack m = ctx.pose();
            m.pushMatrix();
            m.translate(0.0f, -offset);
            for (int i = 1; i < GROUPS[g].length; ++i) {
                String key = GROUPS[g][i];
                int y = top + (i - 1) * 28;
                int h = 25;
                int realTop = Math.round((float)((float)y - offset));
                int visTop = Math.max((int)realTop, (int)top);
                int visBottom = Math.min((int)(realTop + h), (int)(top + 137));
                if (visBottom <= visTop) continue;
                boolean over = overColumn && mx >= x && mx < x + colW && my >= visTop && my < visBottom;
                boolean match = this.matches(key);
                boolean selected = match && (over || key.equals((Object)first));
                this.tileBoxes.add((Object)new int[]{x, visTop, colW, visBottom - visTop});
                this.tileKeys.add((Object)key);
                this.tile(ctx, x, y, colW, h, key, selected, over, !match);
            }
            m.popMatrix();
            ctx.disableScissor();
            this.scrollHints(ctx, g, x, top, colW, overColumn);
        }
        int width = 3 * colW + 2 * gap;
        int count = STREAMER.length - 1;
        int half = (width - (count - 1) * gap) / count;
        this.text(ctx, STREAMER[0].toUpperCase(Locale.ROOT), x0 + 2, 186, this.query.isBlank() ? -9464447 : 1433376129);
        for (int i = 1; i < STREAMER.length; ++i) {
            String key = STREAMER[i];
            int x = x0 + (i - 1) * (half + gap);
            int y = 197;
            int h = 25;
            boolean over = mx >= x && mx < x + half && my >= y && my < y + h;
            boolean match = this.matches(key);
            boolean selected = match && (over || key.equals((Object)first));
            this.tileBoxes.add((Object)new int[]{x, y, half, h});
            this.tileKeys.add((Object)key);
            this.tile(ctx, x, y, half, h, key, selected, over, !match);
        }
        if (!this.query.isBlank()) {
            this.moreHits(ctx, mx, my, x0, 228);
        }
    }

    private static int maxScroll(int group) {
        int content = (GROUPS[group].length - 1) * 28 - 3;
        return Math.max((int)0, (int)(content - 137));
    }

    private void animateColumns(String first) {
        long now = System.currentTimeMillis();
        float dt = this.lastFrame == 0L ? 0.0f : Math.min((float)0.1f, (float)((float)(now - this.lastFrame) / 1000.0f));
        this.lastFrame = now;
        if (!this.query.equals((Object)this.scrolledFor)) {
            this.scrolledFor = this.query;
            if (!this.query.isBlank()) {
                block0: for (int g = 0; g < GROUPS.length; ++g) {
                    for (int i = 1; i < GROUPS[g].length; ++i) {
                        if (!this.matches(GROUPS[g][i])) continue;
                        int tileTop = (i - 1) * 28;
                        if ((float)tileTop < this.colTarget[g]) {
                            this.colTarget[g] = tileTop;
                        }
                        if (!((float)(tileTop + 25) > this.colTarget[g] + 137.0f)) continue block0;
                        this.colTarget[g] = tileTop + 25 - 137;
                        continue block0;
                    }
                }
            }
        }
        float ease = 1.0f - (float)Math.exp((double)(-dt * 14.0f));
        float spring = (float)Math.exp((double)(-dt * 12.0f));
        for (int g = 0; g < GROUPS.length; ++g) {
            this.colTarget[g] = Math.max((float)0.0f, (float)Math.min((float)LauncherScreen.maxScroll(g), (float)this.colTarget[g]));
            int n = g;
            this.colScroll[n] = this.colScroll[n] + (this.colTarget[g] - this.colScroll[g]) * ease;
            if (Math.abs((float)(this.colTarget[g] - this.colScroll[g])) < 0.25f) {
                this.colScroll[g] = this.colTarget[g];
            }
            int n2 = g;
            this.colBounce[n2] = this.colBounce[n2] * spring;
            if (!(Math.abs((float)this.colBounce[g]) < 0.1f)) continue;
            this.colBounce[g] = 0.0f;
        }
    }

    private void scrollHints(GuiGraphicsExtractor ctx, int g, int x, int top, int w, boolean hovered) {
        int max = LauncherScreen.maxScroll(g);
        if (max <= 0) {
            return;
        }
        long since = System.currentTimeMillis() - this.colTouched[g];
        float shown = hovered ? 1.0f : (since < 900L ? 1.0f - (float)since / 900.0f : 0.35f);
        int alpha = Math.round((float)(170.0f * shown));
        int trackX = x + w + 3;
        int thumbH = Math.max((int)14, (int)(18769 / (137 + max)));
        int thumbY = top + Math.round((float)((float)(137 - thumbH) * (this.colScroll[g] / (float)max)));
        ctx.fill(trackX, top, trackX + 2, top + 137, Math.round((float)(34.0f * shown)) << 24 | 0xFFFFFF);
        ctx.fill(trackX, thumbY, trackX + 2, thumbY + thumbH, alpha << 24 | 0x46D16F);
        int cx = x + w / 2;
        int arrow = Math.round((float)(153.0f * Math.max((float)0.5f, (float)shown))) << 24 | 0xFFFFFF;
        if (this.colScroll[g] < (float)(max - 1)) {
            int by = top + 137 + 1;
            ctx.fill(cx - 3, by, cx + 4, by + 1, arrow);
            ctx.fill(cx - 2, by + 1, cx + 3, by + 2, arrow);
            ctx.fill(cx - 1, by + 2, cx + 2, by + 3, arrow);
        }
        if (this.colScroll[g] > 1.0f) {
            int ty = top - 4;
            ctx.fill(cx - 1, ty, cx + 2, ty + 1, arrow);
            ctx.fill(cx - 2, ty + 1, cx + 3, ty + 2, arrow);
            ctx.fill(cx - 3, ty + 2, cx + 4, ty + 3, arrow);
        }
    }

    @Override
    protected boolean designScrolled(double mx, double my, double vertical) {
        if (this.pageKey != null || vertical == 0.0) {
            return false;
        }
        for (int g = 0; g < GROUPS.length; ++g) {
            int[] view = this.colViews[g];
            if (view == null || !LauncherScreen.inside(view, mx, my)) continue;
            this.colTouched[g] = System.currentTimeMillis();
            int max = LauncherScreen.maxScroll(g);
            float want = this.colTarget[g] - (float)Math.signum((double)vertical) * 28.0f;
            if (want < 0.0f || want > (float)max) {
                this.colBounce[g] = (float)(want < 0.0f ? -1 : 1) * 7.0f;
            }
            this.colTarget[g] = Math.max((float)0.0f, (float)Math.min((float)max, (float)want));
            return true;
        }
        return false;
    }

    private void tile(GuiGraphicsExtractor ctx, int x, int y, int w, int h, String key, boolean selected, boolean over, boolean faded) {
        Identifier spawnerPicture;
        boolean off;
        boolean bl = off = LauncherScreen.switchable(key) && !LauncherScreen.on(key);
        if (faded) {
            LauncherGfx.ring(ctx, x, y, w, h, 6, 0x14FFFFFF);
            LauncherGfx.icon(ctx, key, x + 7, y + 6, 0.75f, 0x33FFFFFF);
            this.text(ctx, this.title(key), x + 25, y + 9, 0x33FFFFFF);
            return;
        }
        if (selected && !over) {
            LauncherGfx.round(ctx, x, y, w, h, 6, -218103809);
        } else {
            LauncherGfx.round(ctx, x, y, w, h, 6, over ? 2048870964 : 1544960042);
            LauncherGfx.ring(ctx, x, y, w, h, 6, over ? 1717364884 : 0x1FFFFFFF);
        }
        boolean light = selected && !over;
        SpawnerData.Item spawner = key.equals((Object)"fwm.tab.spawner") && !off ? LauncherScreen.shownSpawner() : null;
        Identifier identifier = spawnerPicture = spawner == null ? null : SpawnerPanel.picture(spawner.name);
        if (spawnerPicture != null) {
            FwmGfx.blit(ctx, spawnerPicture, x + 5, y + 4, 16, 16, 64, 64, 64);
        } else {
            LauncherGfx.icon(ctx, key, x + 7, y + 6, 0.75f, light ? -16045545 : (off ? -1017230 : -5846860));
        }
        this.text(ctx, LauncherGfx.trim(this.font, this.title(key), w - 30), x + 25, y + 4, light ? -16045545 : (off ? -1017230 : -1));
        this.text(ctx, LauncherGfx.trim(this.font, LauncherScreen.status(key), w - 30), x + 25, y + 14, light ? -11768998 : (off ? -1427080590 : -9464447));
        if (key.equals((Object)"fwm.tab.sellmacro")) {
            int kw;
            String line = LauncherGfx.trim(this.font, LauncherScreen.status(key), w - 30);
            String cap = Keybinds.toggleKeyName();
            int kx = x + 25 + this.font.width(line) + 4;
            if (kx + (kw = this.font.width(cap) + 6) <= x + w - 4) {
                LauncherGfx.ring(ctx, kx, y + 13, kw, 10, 3, light ? -16045545 : -5846860);
                this.text(ctx, cap, kx + 3, y + 14, light ? -16045545 : -1);
            }
        }
        if (key.equals((Object)"fwm.tab.messages") && Messages.unreadTotal() > 0) {
            String n = String.valueOf((int)Math.min((int)99, (int)Messages.unreadTotal()));
            int bw = Math.max((int)9, (int)(this.font.width(n) + 5));
            LauncherGfx.round(ctx, x + w - bw - 5, y + 8, bw, 9, 4, -1017230);
            this.text(ctx, n, x + w - bw - 5 + (bw - this.font.width(n) + 1) / 2, y + 9, -1);
        }
    }

    private void moreHits(GuiGraphicsExtractor ctx, int mx, int my, int x, int y) {
        for (String key : MORE) {
            if (!this.matches(key)) continue;
            x += this.chip(ctx, key, x, y, mx, my) + 5;
        }
    }

    private int chip(GuiGraphicsExtractor ctx, String key, int x, int y, int mx, int my) {
        String label = this.title(key);
        int w = this.font.width(label) + 20;
        boolean over = mx >= x && mx < x + w && my >= y && my < y + 13;
        LauncherGfx.round(ctx, x, y, w, 13, 6, over ? 0x33FFFFFF : 0x14FFFFFF);
        LauncherGfx.icon(ctx, key, x + 4, y + 2, 0.5625f, over ? -1 : -5846860);
        this.text(ctx, label, x + 15, y + 3, over ? -1 : -5846860);
        this.tileBoxes.add((Object)new int[]{x, y, w, 13});
        this.tileKeys.add((Object)key);
        return w;
    }

    private void tutorialChip(GuiGraphicsExtractor ctx, int x, int y, int mx, int my) {
        String label = Lang.str(TUTORIAL, new Object[0]);
        int w = this.font.width(label) + 20;
        boolean over = mx >= x && mx < x + w && my >= y && my < y + 13;
        LauncherGfx.round(ctx, x, y, w, 13, 6, over ? 0x33FFFFFF : 0x14FFFFFF);
        LauncherGfx.ring(ctx, x + 4, y + 2, 9, 9, 4, over ? -1 : -5846860);
        this.text(ctx, "?", x + 7, y + 3, over ? -1 : -5846860);
        this.text(ctx, label, x + 15, y + 3, over ? -1 : -5846860);
        this.tileBoxes.add((Object)new int[]{x, y, w, 13});
        this.tileKeys.add((Object)TUTORIAL);
    }

    private void footer(GuiGraphicsExtractor ctx, int mx, int my, boolean chips) {
        int fy = 241;
        if (chips && this.query.isBlank()) {
            int x = 14;
            for (String key : MORE) {
                x += this.chip(ctx, key, x, fy - 3, mx, my) + 5;
            }
            this.tutorialChip(ctx, x, fy - 3, mx, my);
            this.flags(ctx, fy, 412);
            return;
        }
        int flags = this.footer(ctx, 14, fy, 412);
        if (this.pageKey == null) {
            this.right(ctx, Lang.t("Enter \u00f6ffnet, Esc leert die Suche", "Enter opens, Esc clears the search"), flags - 10, fy, -9464447);
        }
        if (this.pageKey != null) {
            String hint = Lang.t("Esc zur\u00fcck zum Start", "Esc back to start");
            this.right(ctx, hint, flags - 10, fy, -9464447);
        }
    }

    @Override
    protected boolean designClicked(double mx, double my, int button) {
        if (this.pageKey != null) {
            return false;
        }
        for (int i = 0; i < this.tileBoxes.size(); ++i) {
            if (!LauncherScreen.inside((int[])this.tileBoxes.get(i), mx, my)) continue;
            String key = (String)this.tileKeys.get(i);
            if (key.equals((Object)TUTORIAL)) {
                if (button == 0) {
                    FwmTutorial.start();
                }
                return button == 0;
            }
            if (button == 1) {
                if (!LauncherScreen.switchable(key)) {
                    return false;
                }
                Features.toggle(LauncherScreen.feature(key));
                ConfigManager.save();
                return true;
            }
            if (button == 0 && this.matches(key)) {
                this.query = "";
                Screen live = StreamerScreen.ifLive(key);
                if (live != null) {
                    this.minecraft.gui.setScreen(live);
                } else {
                    this.openPage(key);
                }
                return true;
            }
            return false;
        }
        return false;
    }

    @Override
    protected void leavePage() {
        this.closePage();
    }

    @Override
    protected boolean escape() {
        if (super.escape()) {
            return true;
        }
        if (this.pageKey != null) {
            this.closePage();
            return true;
        }
        if (!this.query.isEmpty()) {
            this.query = "";
            return true;
        }
        return false;
    }

    @Override
    protected boolean designTyping() {
        return !this.query.isEmpty();
    }

    @Override
    protected boolean designKey(KeyEvent input) {
        if (this.pageKey != null) {
            return false;
        }
        int key = input.key();
        if (key == 42) {
            if (!this.query.isEmpty()) {
                this.query = this.query.substring(0, this.query.length() - 1);
            }
            return true;
        }
        if (key == 40 || key == 88) {
            String first = this.firstMatch();
            if (first != null) {
                this.click();
                this.query = "";
                this.openPage(first);
            }
            return true;
        }
        return false;
    }

    @Override
    protected boolean designChar(CharacterEvent input) {
        if (this.pageKey != null) {
            return false;
        }
        int cp = input.codepoint();
        if (cp < 32 || this.query.length() >= 30) {
            return false;
        }
        if (this.query.isEmpty() && cp == 32) {
            return true;
        }
        this.query = this.query + new String(Character.toChars((int)cp));
        return true;
    }
}
