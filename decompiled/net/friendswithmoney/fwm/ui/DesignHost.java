/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayDeque
 *  java.util.Deque
 *  java.util.List
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.components.events.GuiEventListener
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.client.resources.sounds.SimpleSoundInstance
 *  net.minecraft.client.resources.sounds.SoundInstance
 *  net.minecraft.core.Holder
 *  net.minecraft.network.chat.Component
 *  net.minecraft.sounds.SoundEvents
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.FwmClient;
import net.friendswithmoney.fwm.antileak.AntiLeak;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.net.Giveaways;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.net.ModNews;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.LauncherScreen;
import net.friendswithmoney.fwm.ui.SharpIcons;
import net.friendswithmoney.fwm.ui.panel.AuctionPanel;
import net.friendswithmoney.fwm.ui.panel.GiveawayPanel;
import net.friendswithmoney.fwm.ui.panel.MoneyPanels;
import net.friendswithmoney.fwm.ui.panel.NameListPanel;
import net.friendswithmoney.fwm.ui.panel.NewsPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.PingListPanel;
import net.friendswithmoney.fwm.ui.panel.SellStatsPanel;
import net.friendswithmoney.fwm.ui.panel.SellmacroPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerAdPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.friendswithmoney.fwm.ui.panel.TabbedPanel;
import net.friendswithmoney.fwm.ui.panel.TestsPanel;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import org.joml.Matrix3x2fStack;

public abstract class DesignHost
extends Screen
implements PanelHost {
    protected static final int CARD = 1544960042;
    protected static final int CARD_EDGE = 0x1FFFFFFF;
    protected static final int CARD_HI = -14923216;
    protected static final int LINE = 0x1AFFFFFF;
    protected static final int WHITE = -1;
    protected static final int SOFT = -5846860;
    protected static final int DIMTXT = -9464447;
    protected static final int GREEN = -12136081;
    protected static final int BRIGHT = -10688364;
    protected static final int GREEN_DARK = -16045545;
    protected static final int RED = -1017230;
    protected static final int OFF_TRACK = -12957119;
    private static final int SETTINGS_KEYS = 3;
    protected final Screen parent;
    protected String pageKey;
    protected Panel base;
    protected final Deque<Panel> subs = new ArrayDeque();
    private int frameX;
    private int frameY;
    private int frameW;
    private int frameH;
    private int[] backBox = new int[4];
    private int[] switchBox = new int[4];
    private int[] miniBox = new int[4];
    private int[] cogBox = new int[4];
    private int[] nameBox = new int[4];
    private int[] infoBox = new int[4];
    private int lastMx = -1;
    private int lastMy = -1;
    private int[] flagBoxDe = new int[4];
    private int[] flagBoxEn = new int[4];
    private boolean menuKeyArmed = !DesignHost.menuKeyHeld();
    private int appliedScalePercent = -1;
    private boolean mouseDown;
    private static final int TITLE_H = 22;

    protected DesignHost(Screen parent) {
        super((Component)Component.literal((String)"FWM"));
        this.parent = parent;
    }

    protected abstract int vw();

    protected abstract int vh();

    protected float scale() {
        int width = this.width - FwmTutorial.reservedSide(this.width);
        double gui = Minecraft.getInstance().getWindow().getGuiScale();
        float crisp = DesignHost.crispScale(width, this.height, this.vw(), this.vh(), gui);
        int percent = this.appliedPercent();
        if (percent == 100) {
            return crisp;
        }
        float fits = Math.min((float)((float)width / (float)this.vw()), (float)((float)this.height / (float)this.vh()));
        return Math.min((float)fits, (float)(crisp * (float)percent / 100.0f));
    }

    protected static float crispScale(int width, int height, int vw, int vh, double gui) {
        float refW = Math.max((int)460, (int)vw);
        float refH = Math.max((int)290, (int)vh);
        float fit = Math.min((float)((float)width / refW), (float)((float)height / refH));
        int most = Math.max((int)1, (int)((int)Math.floor((double)(gui * (double)Math.min((float)((float)width / (float)vw), (float)((float)height / (float)vh))))));
        int px = Math.max((int)1, (int)((int)Math.floor((double)(gui * (double)fit * 0.86))));
        if (px < 2 && most >= 2) {
            px = 2;
        }
        return (float)((double)Math.min((int)px, (int)most) / gui);
    }

    private int appliedPercent() {
        if (this.appliedScalePercent < 0 || !this.mouseDown) {
            this.appliedScalePercent = DesignHost.configuredPercent();
        }
        return this.appliedScalePercent;
    }

    private static int configuredPercent() {
        return FwmConfig.INSTANCE.menuScale <= 0 ? 100 : FwmConfig.INSTANCE.menuScale;
    }

    protected float offX() {
        int reserved = FwmTutorial.reservedSide(this.width);
        return (float)(FwmTutorial.onLeft() ? reserved : 0) + (float)Math.floor((double)(((float)(this.width - reserved) - (float)this.vw() * this.scale()) / 2.0f));
    }

    protected float offY() {
        return (float)Math.floor((double)(((float)this.height - (float)this.vh() * this.scale()) / 2.0f));
    }

    protected double vx(double x) {
        return (x - (double)this.offX()) / (double)this.scale();
    }

    protected double vy(double y) {
        return (y - (double)this.offY()) / (double)this.scale();
    }

    protected static String feature(String key) {
        return key.equals((Object)"fwm.tab.antileak") ? "fwm.feature.antileak" : key;
    }

    protected static boolean switchable(String key) {
        return Features.canSwitch(DesignHost.feature(key));
    }

    protected static boolean on(String key) {
        return Features.isOn(DesignHost.feature(key));
    }

    /*
     * Exception decompiling
     */
    protected Panel create(String key) {
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

    protected void openPage(String key) {
        this.openPage(key, -1);
    }

    protected void openPage(String key, int tab) {
        Panel panel;
        if (key.equals((Object)"fwm.tab.giveaway")) {
            key = "fwm.tab.auction";
            tab = 1;
        }
        if (key.equals((Object)this.pageKey) && tab < 0) {
            while (!this.subs.isEmpty()) {
                this.closeSub();
            }
            return;
        }
        this.closePage();
        this.pageKey = key;
        this.base = this.create(key);
        if (tab >= 0 && (panel = this.base) instanceof TabbedPanel) {
            TabbedPanel tabbed = (TabbedPanel)panel;
            tabbed.setTab(tab);
        }
        this.applyBounds();
    }

    protected void closePage() {
        while (!this.subs.isEmpty()) {
            this.closeSub();
        }
        if (this.base != null) {
            this.base.onHidden();
        }
        this.base = null;
        this.pageKey = null;
    }

    protected Panel active() {
        return this.subs.isEmpty() ? this.base : (Panel)this.subs.peek();
    }

    protected void setFrame(int x, int y, int w, int h) {
        if (x == this.frameX && y == this.frameY && w == this.frameW && h == this.frameH) {
            return;
        }
        this.frameX = x;
        this.frameY = y;
        this.frameW = w;
        this.frameH = h;
        this.applyBounds();
    }

    private void applyBounds() {
        if (this.base == null || this.frameW <= 0) {
            return;
        }
        int px = this.frameX;
        int py = this.frameY + 22 + 3;
        int pw = this.frameW;
        int ph = this.frameH - 22 - 3;
        this.base.setBounds(px, py, pw, ph);
        for (Panel panel : this.subs) {
            panel.setBounds(px, py, pw, ph);
        }
    }

    private boolean pageLive() {
        if (!this.subs.isEmpty() || this.pageKey == null) {
            return true;
        }
        if (this.pageKey.equals((Object)"fwm.tab.antileak")) {
            return true;
        }
        return !DesignHost.switchable(this.pageKey) || DesignHost.on(this.pageKey);
    }

    protected void init() {
        super.init();
        if (this instanceof LauncherScreen && FwmTutorial.pending() && !FwmTutorial.active()) {
            FwmTutorial.start();
        }
    }

    void tourShow(String key, int tab) {
        Panel panel;
        if ("fwm.tab.overview".equals((Object)key)) {
            this.closePage();
            return;
        }
        if (key.equals((Object)this.pageKey) && tab >= 0 && (panel = this.base) instanceof TabbedPanel) {
            TabbedPanel tabbed = (TabbedPanel)panel;
            while (!this.subs.isEmpty()) {
                this.closeSub();
            }
            tabbed.setTab(tab);
            this.applyBounds();
            return;
        }
        this.openPage(key, tab);
    }

    void tourRelayout() {
    }

    @Override
    public void openSub(Panel panel) {
        this.subs.push((Object)panel);
        this.applyBounds();
    }

    @Override
    public void closeSub() {
        if (this.subs.isEmpty()) {
            return;
        }
        ((Panel)this.subs.pop()).onHidden();
        this.applyBounds();
    }

    @Override
    public void attach(AbstractWidget widget) {
        this.addWidget((GuiEventListener)widget);
    }

    @Override
    public void detach(AbstractWidget widget) {
        this.removeWidget((GuiEventListener)widget);
    }

    @Override
    public List<String> sidebarKeys() {
        return List.of();
    }

    public void extractRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, this.width, this.height, FwmTheme.OVERLAY);
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate(this.offX(), this.offY());
        m.scale(this.scale(), this.scale());
        LauncherGfx.card(ctx, 0, 0, this.vw(), this.vh(), 8);
        this.lastMx = (int)Math.floor((double)this.vx(mouseX));
        this.lastMy = (int)Math.floor((double)this.vy(mouseY));
        this.drawCanvas(ctx, this.lastMx, this.lastMy, delta);
        m.popMatrix();
        super.extractRenderState(ctx, mouseX, mouseY, delta);
        if (FwmTutorial.active()) {
            FwmTutorial.dock(Math.round((float)this.offX()), Math.round((float)(this.offX() + (float)this.vw() * this.scale())), Math.round((float)this.offY()), Math.round((float)(this.offY() + (float)this.vh() * this.scale())));
            FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
        }
    }

    protected abstract void drawCanvas(GuiGraphicsExtractor var1, int var2, int var3, float var4);

    protected void text(GuiGraphicsExtractor ctx, String s, int x, int y, int color) {
        LauncherGfx.text(ctx, this.font, s, x, y, color);
    }

    protected void right(GuiGraphicsExtractor ctx, String s, int rightX, int y, int color) {
        this.text(ctx, s, rightX - this.font.width(s), y, color);
    }

    protected static boolean inside(int[] box, double x, double y) {
        return x >= (double)box[0] && x < (double)(box[0] + box[2]) && y >= (double)box[1] && y < (double)(box[1] + box[3]);
    }

    protected void brand(GuiGraphicsExtractor ctx, int x, int y, int rightX) {
        FwmTheme.logo(ctx, x, y, 20);
        this.text(ctx, "Friends With Money", x + 25, y + 5, -1);
        String name = FwmGfx.playerName();
        int cw = this.font.width(name) + 22;
        this.nameBox = new int[]{rightX - cw, y + 2, cw, 14};
        LauncherGfx.round(ctx, rightX - cw, y + 2, cw, 14, 7, 0x24FFFFFF);
        FwmGfx.playerHead(ctx, rightX - cw + 4, y + 5, 8);
        this.text(ctx, name, rightX - cw + 16, y + 5, -1);
        int ix = rightX - cw - 18;
        this.infoBox = new int[]{ix, y + 2, 14, 14};
        boolean hover = DesignHost.inside(this.infoBox, this.lastMx, this.lastMy);
        boolean open = "fwm.tab.news".equals((Object)this.pageKey);
        if (hover) {
            LauncherGfx.ring(ctx, ix - 1, y + 1, 16, 16, 5, 1432152212);
        }
        LauncherGfx.round(ctx, ix, y + 2, 14, 14, 4, hover ? 1079830676 : (open ? 0x44FFFFFF : 0x24FFFFFF));
        LauncherGfx.ring(ctx, ix, y + 2, 14, 14, 4, hover ? -10688364 : (open ? -1 : 0x66FFFFFF));
        LauncherGfx.infoGlyph(ctx, ix, y + 2, hover || open ? -1 : -5846860);
        int giveaways = Giveaways.open();
        if (giveaways > 0) {
            String n = String.valueOf((int)Math.min((int)9, (int)giveaways));
            LauncherGfx.round(ctx, ix + 9, y - 1, 8, 8, 4, -865972);
            ctx.text(this.font, (Component)Component.literal((String)n), ix + 13 - this.font.width(n) / 2, y, -15066598, false);
        }
    }

    protected int infoLeft() {
        return this.infoBox[0];
    }

    protected int footer(GuiGraphicsExtractor ctx, int x, int y, int rightX) {
        this.text(ctx, "FWM Mod v" + FwmClient.MOD_VERSION, x, y, -9464447);
        return this.flags(ctx, y, rightX);
    }

    protected int flags(GuiGraphicsExtractor ctx, int y, int rightX) {
        int fx = rightX - 12;
        this.flagBoxEn = new int[]{fx - 1, y - 1, 14, 10};
        FwmTheme.usFlag(ctx, fx, y, 12, 8, !Lang.isGerman());
        this.flagBoxDe = new int[]{(fx -= 16) - 1, y - 1, 14, 10};
        FwmTheme.germanFlag(ctx, fx, y, 12, 8, Lang.isGerman());
        return fx;
    }

    protected String title(String key) {
        return Lang.str(key, new Object[0]);
    }

    protected void drawPage(GuiGraphicsExtractor ctx, int mx, int my, float delta, String back) {
        int lw;
        String backLabel;
        Panel page = this.active();
        if (page == null) {
            return;
        }
        int x = this.frameX;
        int y = this.frameY;
        int right = this.frameX + this.frameW;
        int tx = x;
        this.backBox = new int[4];
        String string = backLabel = !this.subs.isEmpty() ? Lang.str("fwm.items.back", new Object[0]) : back;
        if (backLabel != null) {
            int bw = this.font.width(backLabel) + 16;
            this.backBox = new int[]{x, y + 4, bw, 14};
            boolean over = DesignHost.inside(this.backBox, mx, my);
            LauncherGfx.round(ctx, x, y + 4, bw, 14, 7, over ? 0x33FFFFFF : 0x1AFFFFFF);
            DesignHost.chevron(ctx, x + 5, y + 8, over ? -1 : -5846860);
            this.text(ctx, backLabel, x + 11, y + 7, over ? -1 : -5846860);
            tx = x + bw + 8;
        }
        if (this.subs.isEmpty() && this.pageKey != null) {
            LauncherGfx.icon(ctx, this.pageKey, tx, y + 3, 1.0f, -1);
            tx += 20;
        }
        String heading = page.title().getString();
        int rx = right;
        this.switchBox = new int[4];
        this.miniBox = new int[4];
        this.cogBox = new int[4];
        if (this.subs.isEmpty() && this.pageKey != null && DesignHost.switchable(this.pageKey)) {
            boolean isOn = DesignHost.on(this.pageKey);
            String label = isOn ? Lang.t("An", "On") : Lang.t("Aus", "Off");
            lw = this.font.width(label);
            this.switchBox = new int[]{rx - 26 - lw - 4, y + 4, 26 + lw + 4, 14};
            this.text(ctx, label, rx - 26 - lw - 2, y + 7, isOn ? -5846860 : -1017230);
            LauncherGfx.toggle(ctx, rx - 22, y + 5, isOn, -12136081, -12957119);
            rx = this.switchBox[0] - 8;
        }
        if (page.settingsCog()) {
            this.cogBox = new int[]{rx - 14, y + 4, 14, 14};
            boolean over = DesignHost.inside(this.cogBox, mx, my);
            LauncherGfx.round(ctx, rx - 14, y + 4, 14, 14, 7, over ? 0x33FFFFFF : 0x1AFFFFFF);
            SharpIcons.draw(ctx, "icon_cog", rx - 12, y + 6, 10, over ? -1 : -5846860);
            rx -= 20;
        }
        if (page.headerSwitch() != null) {
            String label = Lang.str("fwm.header.minimap", new Object[0]);
            boolean isOn = Features.isOn(page.headerSwitch());
            lw = this.font.width(label);
            this.miniBox = new int[]{rx - 22 - lw - 4, y + 4, 22 + lw + 4, 14};
            this.text(ctx, label, rx - 22 - lw - 3, y + 7, isOn ? -5846860 : -9464447);
            LauncherGfx.toggle(ctx, rx - 22, y + 5, isOn, -12136081, -12957119);
            rx = this.miniBox[0] - 8;
        }
        this.text(ctx, LauncherGfx.trim(this.font, heading, Math.max((int)10, (int)(rx - tx - 4))), tx, y + 7, -1);
        ctx.fill(x, y + 22, right, y + 22 + 1, 0x1AFFFFFF);
        if (!this.pageLive()) {
            String note = Lang.t("Dieses Feature ist ausgeschaltet.", "This feature is switched off.");
            String hint = Lang.t("Mit dem Schalter oben rechts wieder anschalten", "Switch it back on at the top right");
            int cy = this.frameY + this.frameH / 2;
            this.text(ctx, note, x + (this.frameW - this.font.width(note)) / 2, cy - 8, -1017230);
            this.text(ctx, hint, x + (this.frameW - this.font.width(hint)) / 2, cy + 4, -9464447);
            return;
        }
        page.render(ctx, this.font, mx, my, delta);
    }

    protected static void chevron(GuiGraphicsExtractor ctx, int x, int y, int color) {
        ctx.fill(x + 2, y, x + 3, y + 1, color);
        ctx.fill(x + 1, y + 1, x + 2, y + 2, color);
        ctx.fill(x, y + 2, x + 1, y + 3, color);
        ctx.fill(x + 1, y + 3, x + 2, y + 4, color);
        ctx.fill(x + 2, y + 4, x + 3, y + 5, color);
    }

    protected void click() {
        if (FwmConfig.INSTANCE.menuSounds) {
            Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
        }
    }

    protected abstract boolean designClicked(double var1, double var3, int var5);

    protected abstract void leavePage();

    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        double mx = this.vx(click.x());
        double my = this.vy(click.y());
        int button = GLFW.button(click.button());
        this.mouseDown = true;
        if (FwmTutorial.mouseClicked(click.x(), click.y(), button)) {
            return true;
        }
        if (FwmTutorial.consumes(click.x(), this.width)) {
            return true;
        }
        if (button == 0) {
            TabbedPanel t;
            Panel panel;
            if (DesignHost.inside(this.flagBoxDe, mx, my) || DesignHost.inside(this.flagBoxEn, mx, my)) {
                Lang.set(DesignHost.inside(this.flagBoxDe, mx, my) ? "de" : "en");
                ConfigManager.save();
                this.applyBounds();
                this.click();
                return true;
            }
            if (DesignHost.inside(this.infoBox, mx, my)) {
                this.openPage("fwm.tab.news");
                this.click();
                return true;
            }
            if (this.subs.isEmpty() && (this.base instanceof SpawnerPanel || (panel = this.base) instanceof TabbedPanel && (t = (TabbedPanel)panel).shows(SpawnerPanel.class)) && DesignHost.inside(this.nameBox, mx, my) && Members.isMember(FwmGfx.playerName())) {
                this.openSub(new SpawnerAdPanel(this));
                this.click();
                return true;
            }
            if (this.active() != null) {
                if (DesignHost.inside(this.backBox, mx, my)) {
                    if (!this.subs.isEmpty()) {
                        this.closeSub();
                    } else {
                        this.leavePage();
                    }
                    this.click();
                    return true;
                }
                if (DesignHost.inside(this.switchBox, mx, my)) {
                    Features.toggle(DesignHost.feature(this.pageKey));
                    ConfigManager.save();
                    this.click();
                    return true;
                }
                if (DesignHost.inside(this.miniBox, mx, my)) {
                    Features.toggle(this.active().headerSwitch());
                    this.click();
                    return true;
                }
                if (DesignHost.inside(this.cogBox, mx, my)) {
                    this.openPage("fwm.tab.settings", 3);
                    this.click();
                    return true;
                }
            }
        }
        if (this.designClicked(mx, my, button)) {
            this.click();
            return true;
        }
        if (this.active() != null && this.pageLive() && this.active().mouseClicked(mx, my, button)) {
            this.click();
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    public boolean mouseDragged(MouseButtonEvent click, double deltaX, double deltaY) {
        if (this.active() != null && this.active().mouseDragged(this.vx(click.x()), this.vy(click.y()), GLFW.button(click.button()))) {
            return true;
        }
        return super.mouseDragged(click, deltaX, deltaY);
    }

    public boolean mouseReleased(MouseButtonEvent click) {
        if (this.active() != null) {
            this.active().mouseReleased();
        }
        this.mouseDown = false;
        return super.mouseReleased(click);
    }

    protected boolean designScrolled(double mx, double my, double vertical) {
        return false;
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        double my;
        double mx = this.vx(mouseX);
        if (this.designScrolled(mx, my = this.vy(mouseY), vertical)) {
            return true;
        }
        if (this.active() != null && this.active().mouseScrolled(mx, my, vertical)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontal, vertical);
    }

    protected boolean designKey(KeyEvent input) {
        return false;
    }

    protected boolean designChar(CharacterEvent input) {
        return false;
    }

    protected boolean typingInField() {
        for (Object child : this.children()) {
            EditBox field;
            if (!(child instanceof EditBox) || !(field = (EditBox)child).isFocused()) continue;
            return true;
        }
        return false;
    }

    public boolean keyPressed(KeyEvent input) {
        boolean typing;
        Panel page = this.active();
        if (FwmTutorial.keyPressed(input.key())) {
            return true;
        }
        if (page != null && page.isCapturingKeys() && page.keyPressed(input.key(), input.keycode(), input.modifiers())) {
            return true;
        }
        boolean bl = typing = this.typingInField() || page != null && page.isCapturingKeys();
        if (this.menuKeyArmed && !typing && Keybinds.OPEN_MENU != null && Keybinds.OPEN_MENU.matches(input) && !this.designTyping()) {
            this.onClose();
            return true;
        }
        if (input.key() == 41) {
            if (this.escape()) {
                return true;
            }
            this.onClose();
            return true;
        }
        if (page != null && this.pageLive() && page.keyPressed(input.key(), input.keycode(), input.modifiers())) {
            return true;
        }
        if (!typing && this.designKey(input)) {
            return true;
        }
        return super.keyPressed(input);
    }

    protected boolean designTyping() {
        return false;
    }

    protected boolean escape() {
        if (!this.subs.isEmpty()) {
            this.closeSub();
            return true;
        }
        return false;
    }

    public boolean keyReleased(KeyEvent input) {
        if (Keybinds.OPEN_MENU != null && Keybinds.OPEN_MENU.matches(input)) {
            this.menuKeyArmed = true;
        }
        return super.keyReleased(input);
    }

    public boolean charTyped(CharacterEvent input) {
        Panel page = this.active();
        if (page != null && this.pageLive() && page.charTyped(input)) {
            return true;
        }
        if (!this.typingInField() && this.designChar(input)) {
            return true;
        }
        return super.charTyped(input);
    }

    private static boolean menuKeyHeld() {
        if (Keybinds.OPEN_MENU == null) {
            return false;
        }
        InputConstants.Key key = InputConstants.getKey((String)Keybinds.OPEN_MENU.saveString());
        if (key == null || key.getType() != InputConstants.Type.KEYBOARD) {
            return false;
        }
        return InputConstants.isKeyDown((int)key.getValue());
    }

    public void onClose() {
        this.closePage();
        FwmConfig.INSTANCE.clamp();
        ConfigManager.save();
        AntiLeak.sync(Minecraft.getInstance());
        if (this.minecraft != null) {
            this.minecraft.gui.setScreen(this.parent);
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    private static /* synthetic */ Panel lambda$create$12() {
        return new NewsPanel(ModNews.Kind.FIXES);
    }

    private static /* synthetic */ Panel lambda$create$11() {
        return new NewsPanel(ModNews.Kind.BUGS);
    }

    private static /* synthetic */ Panel lambda$create$10() {
        return new NewsPanel(ModNews.Kind.INFOS);
    }

    private /* synthetic */ Panel lambda$create$9() {
        return new MoneyPanels.HistoryList(this);
    }

    private /* synthetic */ Panel lambda$create$7() {
        return new NameListPanel(this, "fwm.autotpa.tabNames", "fwm.autotpa.none", "fwm.autotpa.add", (Supplier<List<String>>)((Supplier)() -> FwmConfig.INSTANCE.autoTpaNames));
    }

    private /* synthetic */ Panel lambda$create$6() {
        return new PingListPanel(this);
    }

    private /* synthetic */ Panel lambda$create$5() {
        return new SpawnerPanel(this);
    }

    private /* synthetic */ Panel lambda$create$4() {
        return new GiveawayPanel(this);
    }

    private /* synthetic */ Panel lambda$create$3() {
        return new AuctionPanel(this);
    }

    private /* synthetic */ Panel lambda$create$2() {
        return new TestsPanel(this);
    }

    private /* synthetic */ Panel lambda$create$1() {
        return new SellStatsPanel(this);
    }

    private /* synthetic */ Panel lambda$create$0() {
        return new SellmacroPanel(this);
    }
}
