/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayDeque
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.Deque
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Objects
 *  java.util.function.Supplier
 *  net.minecraft.client.KeyMapping
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
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.sounds.SoundEvents
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.FwmClient;
import net.friendswithmoney.fwm.antileak.AntiLeak;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.ui.Category;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmIcons;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.SharpIcons;
import net.friendswithmoney.fwm.ui.panel.AntiLeakPanel;
import net.friendswithmoney.fwm.ui.panel.AuctionPanel;
import net.friendswithmoney.fwm.ui.panel.ColorCodePanel;
import net.friendswithmoney.fwm.ui.panel.ColorsPanel;
import net.friendswithmoney.fwm.ui.panel.ExtrasPanel;
import net.friendswithmoney.fwm.ui.panel.KeysPanel;
import net.friendswithmoney.fwm.ui.panel.MarketPanel;
import net.friendswithmoney.fwm.ui.panel.MembersPanel;
import net.friendswithmoney.fwm.ui.panel.MessagesPanel;
import net.friendswithmoney.fwm.ui.panel.OverlayPanel;
import net.friendswithmoney.fwm.ui.panel.OverviewPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RtpInfoPanel;
import net.friendswithmoney.fwm.ui.panel.SecondChatPanel;
import net.friendswithmoney.fwm.ui.panel.SellStatsPanel;
import net.friendswithmoney.fwm.ui.panel.SellmacroPanel;
import net.friendswithmoney.fwm.ui.panel.SettingsPanel;
import net.friendswithmoney.fwm.ui.panel.SidebarPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerAdPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.friendswithmoney.fwm.ui.panel.TabbedPanel;
import net.friendswithmoney.fwm.ui.panel.TestsPanel;
import net.friendswithmoney.fwm.ui.panel.ToolGuardPanel;
import net.friendswithmoney.fwm.ui.widget.Anim;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.KeyMapping;
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
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import org.joml.Matrix3x2fStack;

public class FwmScreen
extends Screen
implements PanelHost {
    private static final int MAX_W = 460;
    private static final int MAX_H = 236;
    private static final int MIN_VIEW_W = 300;
    private static final int MIN_VIEW_H = 220;
    private static final int PAD = 6;
    private static final int HEADER_H = 26;
    private static final int HEADER_BOX_H = 18;
    private static final int FOOTER_H = 16;
    private static final int SIDEBAR_W = 118;
    private static final int SIDEBAR_W_NARROW = 104;
    private static final int NARROW_BELOW = 330;
    private static final int CAT_H = 24;
    private static final int CAT_GAP = 6;
    private static final int SIDEBAR_HEADING_H = 10;
    private static final int ARROW_W = 0;
    private static final int TITLE_H = 14;
    private static final int LOGO_H = 16;
    private static final int BACK_H = 11;
    private static final int HEAD_SIZE = 8;
    private static final int FLAG_W = 12;
    private static final int FLAG_H = 8;
    private static final int FLAG_GAP = 5;
    private final Screen parent;
    private final List<Category> categories = new ArrayList();
    private Panel base;
    private final Deque<Panel> subs = new ArrayDeque();
    private int selected;
    private int panelX;
    private int panelY;
    private int panelW;
    private int panelH;
    private int sidebarW;
    private int sidebarScroll;
    private int frameX;
    private int frameY;
    private int frameW;
    private int frameH;
    private int headerY;
    private float builtForScale = 1.0f;
    private long openedAt;
    private static final int OPEN_MS = 140;
    public static final int TAB_OVERVIEW = 0;
    public static final int TAB_SELLMACRO = 1;
    public static final int TAB_MESSAGES = 3;
    public static final int SUB_STATS = 1;
    private int pendingTab = -1;
    private static final int SUB_KEYS = 4;
    private String pendingKey;
    private int dragCategory = -1;
    private boolean dragMoved;
    private static final List<String> ALWAYS_IN_SIDEBAR = List.of((Object)"fwm.tab.settings", (Object)"fwm.tab.spawner", (Object)"fwm.tab.members");
    private static final int SWITCH_W = 16;
    private static final int SWITCH_H = 9;
    private boolean menuKeyArmed = !FwmScreen.menuKeyHeld();

    public FwmScreen(Screen parent) {
        this(parent, 0, -1);
    }

    public FwmScreen(Screen parent, int tab) {
        this(parent, tab, -1);
    }

    public static FwmScreen at(Screen parent, String categoryKey) {
        FwmScreen screen = new FwmScreen(parent);
        screen.pendingKey = categoryKey;
        return screen;
    }

    public FwmScreen(Screen parent, int tab, int subTab) {
        super((Component)Lang.of("fwm.menu.title", new Object[0]));
        this.parent = parent;
        this.selected = tab;
        this.pendingTab = subTab;
    }

    protected void init() {
        if (this.openedAt == 0L) {
            this.openedAt = System.currentTimeMillis();
        }
        if (this.categories.isEmpty()) {
            this.categories.add((Object)Category.of("fwm.tab.overview", (Supplier<Panel>)((Supplier)() -> new OverviewPanel(this.tiles(), this::select))));
            this.categories.add((Object)Category.of("fwm.tab.sellmacro", (Supplier<Panel>)((Supplier)() -> new TabbedPanel("fwm.tab.sellmacro", (List<TabbedPanel.Tab>)List.of((Object)((Object)new TabbedPanel.Tab("fwm.tab.control", (Supplier<Panel>)((Supplier)() -> new SellmacroPanel(this)))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.statsShort", (Supplier<Panel>)((Supplier)() -> new SellStatsPanel(this)))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.tests", (Supplier<Panel>)((Supplier)() -> new TestsPanel(this)))))))));
            this.categories.add((Object)Category.of("fwm.tab.messages", (Supplier<Panel>)((Supplier)() -> new MessagesPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.toolguard", (Supplier<Panel>)((Supplier)ToolGuardPanel::new)));
            this.categories.add((Object)Category.of("fwm.tab.secondchat", (Supplier<Panel>)((Supplier)() -> new SecondChatPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.auction", (Supplier<Panel>)((Supplier)() -> new AuctionPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.colorcodes", (Supplier<Panel>)((Supplier)() -> new ColorCodePanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.market", (Supplier<Panel>)((Supplier)() -> new MarketPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.rtp", (Supplier<Panel>)((Supplier)() -> new RtpInfoPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.extras", (Supplier<Panel>)((Supplier)ExtrasPanel::new)));
            this.categories.add((Object)Category.of("fwm.tab.antileak", (Supplier<Panel>)((Supplier)AntiLeakPanel::new)));
            this.categories.add((Object)Category.of("fwm.tab.spawner", (Supplier<Panel>)((Supplier)() -> new SpawnerPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.members", (Supplier<Panel>)((Supplier)() -> new MembersPanel(this))));
            this.categories.add((Object)Category.of("fwm.tab.settings", (Supplier<Panel>)((Supplier)() -> new TabbedPanel("fwm.tab.settings", (List<TabbedPanel.Tab>)List.of((Object)((Object)new TabbedPanel.Tab("fwm.tab.general", (Supplier<Panel>)((Supplier)SettingsPanel::new))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.gui", (Supplier<Panel>)((Supplier)ColorsPanel::new))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.sidebar", (Supplier<Panel>)((Supplier)() -> new SidebarPanel(this.sidebarKeys())))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.overlay", (Supplier<Panel>)((Supplier)OverlayPanel::new))), (Object)((Object)new TabbedPanel.Tab("fwm.tab.keys", (Supplier<Panel>)((Supplier)KeysPanel::new))))))));
        }
        if (this.base == null) {
            this.base = (Panel)((Category)((Object)this.categories.get(this.selected))).panel().get();
            this.applyPendingTab();
        }
        this.layout();
        if (this.pendingKey != null) {
            String key = this.pendingKey;
            this.pendingKey = null;
            this.tourShow(key, -1);
        }
        if (FwmTutorial.pending() && !FwmTutorial.active()) {
            FwmTutorial.start();
        }
    }

    void tourShow(String categoryKey, int subTab) {
        for (int i = 0; i < this.categories.size(); ++i) {
            if (!((Category)((Object)this.categories.get(i))).labelKey().equals((Object)categoryKey)) continue;
            if (((Category)((Object)this.categories.get(i))).locked()) {
                return;
            }
            if (subTab >= 0) {
                this.pendingTab = subTab;
            }
            if (i != this.selected) {
                this.select(i);
                this.applyPendingTab();
            } else {
                while (!this.subs.isEmpty()) {
                    this.closeSub();
                }
                this.applyPendingTab();
            }
            this.scrollSidebarTo(i);
            return;
        }
    }

    void tourRelayout() {
        this.layout();
        if (FwmTutorial.active()) {
            this.scrollSidebarTo(this.selected);
        }
    }

    private void scrollSidebarTo(int index) {
        int cy = this.frameY;
        int target = -1;
        for (SidebarRow row : this.sidebarRows()) {
            if (row.isHeading()) {
                cy += 10;
                continue;
            }
            if (row.categoryIndex() == index) {
                target = cy;
                break;
            }
            cy += 30;
        }
        if (target < 0) {
            return;
        }
        int offset = target - this.frameY;
        int view = this.sidebarViewBottom() - this.frameY;
        this.sidebarScroll = Math.max((int)0, (int)Math.min((int)this.maxSidebarScroll(), (int)Math.max((int)(offset + 24 - view), (int)Math.min((int)this.sidebarScroll, (int)offset))));
    }

    private float scale() {
        return this.builtForScale;
    }

    private float configuredScale() {
        return Math.max((float)0.1f, (float)((float)Math.min((int)FwmConfig.INSTANCE.menuScale, (int)FwmScreen.maxScalePercent()) / 100.0f));
    }

    public static int maxScalePercent() {
        Minecraft client = Minecraft.getInstance();
        if (client == null || client.getWindow() == null) {
            return 150;
        }
        int fits = Math.min((int)(client.getWindow().getGuiScaledWidth() * 100 / 300), (int)(client.getWindow().getGuiScaledHeight() * 100 / 220));
        return Math.max((int)70, (int)Math.min((int)150, (int)fits));
    }

    private int scaledWidth() {
        return Math.round((float)((float)this.width / this.scale()));
    }

    private int scaledHeight() {
        return Math.round((float)((float)this.height / this.scale()));
    }

    private double toLocal(double value) {
        return value / (double)this.scale();
    }

    private void layout() {
        this.builtForScale = this.configuredScale();
        int viewW = this.scaledWidth();
        int viewH = this.scaledHeight();
        int column = Math.round((float)((float)FwmTutorial.reservedSide(this.width) / this.scale()));
        this.panelW = Math.min((int)(viewW - 8 - column), (int)460);
        this.panelH = Math.min((int)(viewH - 8), (int)236);
        int groupX = Math.max((int)2, (int)((viewW - this.panelW - column) / 2));
        this.panelX = groupX + (column > 0 && FwmTutorial.onLeft() ? column : 0);
        this.panelY = Math.max((int)2, (int)((viewH - this.panelH) / 2));
        FwmTutorial.dock(Math.round((float)((float)this.panelX * this.scale())), Math.round((float)((float)(this.panelX + this.panelW) * this.scale())), Math.round((float)((float)this.panelY * this.scale())), Math.round((float)((float)(this.panelY + this.panelH) * this.scale())));
        this.sidebarW = (this.panelW < 330 ? 104 : 118) + 0;
        this.headerY = this.panelY + 4;
        this.frameX = this.panelX + 6 + this.sidebarW + 6;
        this.frameY = this.panelY + 26 + 4;
        this.frameW = this.panelX + this.panelW - 6 - this.frameX;
        this.frameH = this.panelY + this.panelH - 16 - 2 - this.frameY;
        this.applyPanelBounds();
    }

    private void applyPanelBounds() {
        int strip = this.active() != null && !this.active().showTitle() ? 0 : 14;
        int px = this.frameX + 4;
        int py = this.frameY + strip + 4;
        int pw = Math.max((int)20, (int)(this.frameW - 8));
        int ph = Math.max((int)26, (int)(this.frameH - strip - 8));
        if (this.base != null) {
            this.base.setBounds(px, py, pw, ph);
        }
        for (Panel panel : this.subs) {
            panel.setBounds(px, py, pw, ph);
        }
    }

    private Panel active() {
        return this.subs.isEmpty() ? this.base : (Panel)this.subs.peek();
    }

    private List<OverviewPanel.Tile> tiles() {
        ArrayList out = new ArrayList();
        Iterator iterator = this.orderedIndices(false).iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            Category category = (Category)((Object)this.categories.get(i));
            out.add((Object)new OverviewPanel.Tile(category.labelKey(), category.locked(), i));
        }
        return out;
    }

    private void applyPendingTab() {
        if (this.pendingTab < 0) {
            return;
        }
        Panel panel = this.base;
        if (panel instanceof TabbedPanel) {
            TabbedPanel tabbed = (TabbedPanel)panel;
            tabbed.setTab(this.pendingTab);
        }
        this.pendingTab = -1;
    }

    @Override
    public void openSub(Panel panel) {
        this.subs.push((Object)panel);
        this.applyPanelBounds();
    }

    @Override
    public void closeSub() {
        if (this.subs.isEmpty()) {
            return;
        }
        ((Panel)this.subs.pop()).onHidden();
        this.applyPanelBounds();
    }

    @Override
    public void attach(AbstractWidget widget) {
        this.addWidget((GuiEventListener)widget);
    }

    @Override
    public void detach(AbstractWidget widget) {
        this.removeWidget((GuiEventListener)widget);
    }

    public void extractRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, this.width, this.height, FwmTheme.OVERLAY);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.scale(this.scale(), this.scale());
        float grow = Anim.ease(Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - this.openedAt) / 140.0f)));
        if (grow < 1.0f) {
            float factor = 0.96f + 0.04f * grow;
            matrices.translate((float)this.panelX + (float)this.panelW / 2.0f, (float)this.panelY + (float)this.panelH / 2.0f);
            matrices.scale(factor, factor);
            matrices.translate(-((float)this.panelX + (float)this.panelW / 2.0f), -((float)this.panelY + (float)this.panelH / 2.0f) - 4.0f * (1.0f - grow));
        }
        int localX = (int)this.toLocal(mouseX);
        int localY = (int)this.toLocal(mouseY);
        FwmTheme.panel(ctx, this.panelX, this.panelY, this.panelW, this.panelH);
        this.renderHeader(ctx, localX, localY);
        FwmTheme.divider(ctx, this.panelX + 6, this.panelY + 26, this.panelW - 12);
        this.renderSidebar(ctx, localX, localY);
        this.renderContent(ctx, localX, localY, delta);
        this.renderFooter(ctx, localX, localY);
        matrices.popMatrix();
        FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
        super.extractRenderState(ctx, mouseX, mouseY, delta);
    }

    private void renderHeader(GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        int headingW = this.sidebarW;
        boolean home = this.selected == 0 && this.subs.isEmpty();
        boolean overHome = mouseX >= this.panelX + 6 && mouseX < this.panelX + 6 + headingW && mouseY >= this.headerY && mouseY < this.headerY + 18;
        FwmTheme.box(ctx, this.panelX + 6, this.headerY, headingW, 18, home ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, home || overHome ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent heading = Lang.of("fwm.tab.overview", new Object[0]);
        int n = this.panelX + 6 + (headingW - this.font.width((FormattedText)heading)) / 2;
        Objects.requireNonNull((Object)this.font);
        ctx.text(this.font, (Component)heading, n, this.headerY + (18 - 9) / 2 + 1, home || overHome ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        this.renderBrand(ctx, this.panelX + 6 + headingW, this.identityX());
        this.renderIdentity(ctx);
    }

    private Component identityName() {
        int room = Math.max((int)0, (int)(this.panelW / 3 - 8 - 18));
        return Component.literal((String)this.font.plainSubstrByWidth(FwmGfx.playerName(), room));
    }

    private int identityWidth() {
        int nameW = this.font.width((FormattedText)this.identityName());
        return 22 + (nameW > 0 ? nameW + 4 : 0);
    }

    private int identityX() {
        return this.panelX + this.panelW - 6 - this.identityWidth();
    }

    private boolean overIdentity(double mx, double my) {
        return mx >= (double)this.identityX() && mx < (double)(this.identityX() + this.identityWidth()) && my >= (double)this.headerY && my < (double)(this.headerY + 18);
    }

    private void renderIdentity(GuiGraphicsExtractor ctx) {
        Component name = this.identityName();
        int boxW = this.identityWidth();
        int bx = this.identityX();
        FwmTheme.box(ctx, bx, this.headerY, boxW, 18, FwmTheme.RAISED, FwmTheme.OFF);
        if (this.font.width((FormattedText)name) > 0) {
            Objects.requireNonNull((Object)this.font);
            ctx.text(this.font, name, bx + 6, this.headerY + (18 - 9) / 2 + 1, FwmTheme.TEXT, false);
        }
        FwmGfx.playerHead(ctx, bx + boxW - 8 - 5, this.headerY + 5, 8);
    }

    private void renderBrand(GuiGraphicsExtractor ctx, int fromX, int toX) {
        int room = toX - fromX - 12;
        int logoW = Math.max((int)1, (int)Math.round((float)21.333334f));
        if (room < logoW) {
            return;
        }
        MutableComponent brand = Lang.of("fwm.menu.brand", new Object[0]);
        int textW = this.font.width((FormattedText)brand);
        boolean withText = room >= logoW + 4 + textW;
        int totalW = withText ? textW + 4 + logoW : logoW;
        int startX = fromX + 6 + (room - totalW) / 2;
        if (withText) {
            Objects.requireNonNull((Object)this.font);
            ctx.text(this.font, (Component)brand, startX, this.headerY + (18 - 9) / 2 + 1, FwmTheme.TEXT_DIM, false);
        }
        FwmTheme.logo(ctx, withText ? startX + textW + 4 : startX, this.headerY + 1, logoW);
    }

    private List<SidebarRow> sidebarRows() {
        ArrayList out = new ArrayList();
        boolean sawLocked = false;
        Iterator iterator = this.orderedIndices().iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            if (((Category)((Object)this.categories.get(i))).locked() && !sawLocked) {
                out.add((Object)new SidebarRow(-1, "fwm.tab.comingSoon"));
                sawLocked = true;
            }
            out.add((Object)new SidebarRow(i, null));
        }
        return out;
    }

    private int sidebarContentHeight() {
        int h = 0;
        for (SidebarRow row : this.sidebarRows()) {
            h += row.isHeading() ? 10 : 30;
        }
        return h;
    }

    private int sidebarViewBottom() {
        return this.panelY + this.panelH - 16;
    }

    private int maxSidebarScroll() {
        return Math.max((int)0, (int)(this.sidebarContentHeight() - (this.sidebarViewBottom() - this.frameY)));
    }

    private void renderSidebar(GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        int viewBottom = this.sidebarViewBottom();
        this.sidebarScroll = Math.max((int)0, (int)Math.min((int)this.maxSidebarScroll(), (int)this.sidebarScroll));
        ctx.enableScissor(this.panelX + 6, this.frameY, this.panelX + 6 + this.sidebarW, viewBottom);
        int cy = this.frameY - this.sidebarScroll;
        for (SidebarRow row : this.sidebarRows()) {
            if (row.isHeading()) {
                this.renderSidebarHeading(ctx, row.headingKey(), cy);
                cy += 10;
                continue;
            }
            int i = row.categoryIndex();
            Category category = (Category)((Object)this.categories.get(i));
            boolean hovered = mouseX >= this.panelX + 6 && mouseX < this.panelX + 6 + this.sidebarW && mouseY >= cy && mouseY < cy + 24;
            boolean active = i == this.selected;
            boolean on = this.isOn(i);
            FwmTheme.sidebarButton(ctx, this.sidebarButtonX(), cy, this.sidebarButtonW(), 24, active, hovered, category.locked(), on);
            int textX = this.sidebarButtonX() + 6 + 16 + 5;
            MutableComponent label = Component.literal((String)this.font.plainSubstrByWidth(Lang.str(category.labelKey(), new Object[0]), this.sidebarButtonX() + this.sidebarButtonW() - 5 - textX));
            int color = FwmTheme.sidebarTextColor(active, hovered, category.locked(), on);
            FwmIcons.draw(ctx, category.labelKey(), this.sidebarButtonX() + 6, cy + 4, color);
            if (category.locked()) {
                ctx.text(this.font, (Component)label, textX, cy + 4, color, false);
                this.smallText(ctx, Lang.str("fwm.tab.comingSoon", new Object[0]), textX + this.font.width((FormattedText)label) / 4, cy + 15, FwmTheme.OFF);
            } else {
                Objects.requireNonNull((Object)this.font);
                ctx.text(this.font, (Component)label, textX, cy + (24 - 9) / 2 + 1, color, false);
            }
            if (category.labelKey().equals((Object)FwmTutorial.highlightKey())) {
                FwmTheme.box(ctx, this.sidebarButtonX() - 1, cy - 1, this.sidebarButtonW() + 2, 26, 0, FwmTheme.ACCENT);
            }
            if (i == this.dragCategory && this.dragMoved) {
                FwmTheme.box(ctx, this.sidebarButtonX(), cy, this.sidebarButtonW(), 24, 0, FwmTheme.ACCENT);
            }
            cy += 30;
        }
        ctx.disableScissor();
        int maxScroll = this.maxSidebarScroll();
        if (maxScroll > 0) {
            this.renderSidebarScrollbar(ctx, viewBottom, maxScroll);
        }
    }

    @Override
    public List<String> sidebarKeys() {
        ArrayList out = new ArrayList();
        for (int i = 1; i < this.categories.size(); ++i) {
            String key = ((Category)((Object)this.categories.get(i))).labelKey();
            if (ALWAYS_IN_SIDEBAR.contains((Object)key)) continue;
            out.add((Object)key);
        }
        return out;
    }

    private int sidebarButtonX() {
        return this.panelX + 6 + 0;
    }

    private int sidebarButtonW() {
        return this.sidebarW - 0;
    }

    private List<Integer> orderedIndices() {
        return this.orderedIndices(true);
    }

    private List<Integer> orderedIndices(boolean respectHidden) {
        FwmConfig config = FwmConfig.INSTANCE;
        ArrayList free = new ArrayList();
        ArrayList locked = new ArrayList();
        for (int i = 1; i < this.categories.size(); ++i) {
            Category category = (Category)((Object)this.categories.get(i));
            if (category.locked()) {
                locked.add((Object)i);
                continue;
            }
            if (respectHidden && this.hiddenInSidebar(i)) continue;
            free.add((Object)i);
        }
        free.sort((a, b) -> Integer.compare((int)this.rank(config, (int)a), (int)this.rank(config, (int)b)));
        for (int k = 0; k < free.size(); ++k) {
            if (!((Category)((Object)this.categories.get(((Integer)free.get(k)).intValue()))).labelKey().equals((Object)"fwm.tab.settings")) continue;
            free.add((Object)((Integer)free.remove(k)));
            break;
        }
        free.addAll((Collection)locked);
        return free;
    }

    private int rank(FwmConfig config, int index) {
        int pos = config.sidebarOrder.indexOf((Object)((Category)((Object)this.categories.get(index))).labelKey());
        return pos < 0 ? Integer.MAX_VALUE : pos;
    }

    private boolean hiddenInSidebar(int index) {
        FwmConfig config = FwmConfig.INSTANCE;
        String key = ((Category)((Object)this.categories.get(index))).labelKey();
        if (config.sidebarHidden.contains((Object)key)) {
            return true;
        }
        return config.sidebarHideDisabled && index != this.selected && Features.canSwitch(key) && !Features.isOn(key);
    }

    private void moveCategory(int index, int direction) {
        FwmConfig config = FwmConfig.INSTANCE;
        ArrayList order = new ArrayList();
        Iterator iterator = this.orderedIndices(false).iterator();
        while (iterator.hasNext()) {
            int i = (Integer)iterator.next();
            if (((Category)((Object)this.categories.get(i))).locked()) continue;
            order.add((Object)i);
        }
        int at = order.indexOf((Object)index);
        if (at < 0) {
            return;
        }
        int to = -1;
        for (int p = at + direction; p >= 0 && p < order.size(); p += direction) {
            if (this.hiddenInSidebar((Integer)order.get(p))) continue;
            to = p;
            break;
        }
        if (to < 0) {
            return;
        }
        order.add(to, (Object)((Integer)order.remove(at)));
        FwmConfig.INSTANCE.sidebarOrder = new ArrayList();
        Iterator iterator2 = order.iterator();
        while (iterator2.hasNext()) {
            int i = (Integer)iterator2.next();
            FwmConfig.INSTANCE.sidebarOrder.add((Object)((Category)((Object)this.categories.get(i))).labelKey());
        }
        ConfigManager.save();
        if (this.selected == 0) {
            this.base = (Panel)((Category)((Object)this.categories.get(0))).panel().get();
        }
        this.layout();
    }

    private void renderSidebarHeading(GuiGraphicsExtractor ctx, String key, int top) {
        this.smallText(ctx, Lang.str(key, new Object[0]), this.sidebarButtonX() + this.sidebarButtonW() / 2, top + 2, FwmTheme.TEXT_DIM);
    }

    private void renderSidebarScrollbar(GuiGraphicsExtractor ctx, int viewBottom, int maxScroll) {
        int trackX = this.panelX + 6 + this.sidebarW + 2;
        int trackH = viewBottom - this.frameY;
        int thumbH = Math.max((int)10, (int)(trackH * trackH / (trackH + maxScroll)));
        int thumbY = this.frameY + (trackH - thumbH) * this.sidebarScroll / maxScroll;
        ctx.fill(trackX, this.frameY, trackX + 2, viewBottom, FwmTheme.OFF);
        ctx.fill(trackX, thumbY, trackX + 2, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private boolean isOn(int index) {
        Category category = (Category)((Object)this.categories.get(index));
        return category.locked() || Features.isOn(category.labelKey());
    }

    private void smallText(GuiGraphicsExtractor ctx, String text, int cx, int top, int color) {
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)cx - (float)this.font.width(text) / 4.0f, (float)top);
        matrices.scale(0.5f, 0.5f);
        ctx.text(this.font, (Component)Component.literal((String)text), 0, 0, color, false);
        matrices.popMatrix();
    }

    private void renderContent(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        FwmTheme.contentFrame(ctx, this.frameX, this.frameY, this.frameW, this.frameH);
        int backW = this.subs.isEmpty() ? 0 : this.backWidth();
        boolean cog = this.active().settingsCog();
        String headerSwitch = this.active().headerSwitch();
        if (this.active().showTitle()) {
            int titleRoom = (headerSwitch != null ? this.switchX(backW, cog) : (cog ? this.cogX(backW) : (backW > 0 ? this.backX(backW) : this.frameX + this.frameW))) - this.frameX - 9;
            MutableComponent title = Component.literal((String)this.font.plainSubstrByWidth(this.active().title().getString(), Math.max((int)8, (int)titleRoom)));
            Objects.requireNonNull((Object)this.font);
            ctx.text(this.font, (Component)title, this.frameX + 5, this.frameY + (14 - 9) / 2 + 1, FwmTheme.ACCENT, false);
            if (backW > 0) {
                this.renderBack(ctx, backW, mouseX, mouseY);
            }
            if (cog) {
                this.renderCog(ctx, backW, mouseX, mouseY);
            }
            if (headerSwitch != null) {
                this.renderSwitch(ctx, headerSwitch, backW, cog, mouseX, mouseY);
            }
            FwmTheme.divider(ctx, this.frameX + 4, this.frameY + 14, this.frameW - 8);
        }
        if (this.switchedOff()) {
            MutableComponent note = Lang.of("fwm.feature.off", new Object[0]);
            ctx.text(this.font, (Component)note, this.frameX + (this.frameW - this.font.width((FormattedText)note)) / 2, this.frameY + this.frameH / 2 - 8, FwmTheme.DISABLED_TEXT, false);
            MutableComponent hint = Lang.of("fwm.feature.offHint", new Object[0]);
            ctx.text(this.font, (Component)hint, this.frameX + (this.frameW - this.font.width((FormattedText)hint)) / 2, this.frameY + this.frameH / 2 + 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        this.active().render(ctx, this.font, mouseX, mouseY, delta);
    }

    private boolean switchedOff() {
        if (!this.subs.isEmpty() || this.base == null) {
            return false;
        }
        Category category = (Category)((Object)this.categories.get(this.selected));
        return !category.locked() && !Features.isOn(category.labelKey());
    }

    private int backWidth() {
        return this.font.width((FormattedText)Lang.of("fwm.items.back", new Object[0])) + 10;
    }

    private void renderBack(GuiGraphicsExtractor ctx, int backW, int mouseX, int mouseY) {
        int bx = this.backX(backW);
        int by = this.backY();
        boolean hovered = mouseX >= bx && mouseX < bx + backW && mouseY >= by && mouseY < by + 11;
        FwmTheme.box(ctx, bx, by, backW, 11, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = Lang.of("fwm.items.back", new Object[0]);
        int n = bx + (backW - this.font.width((FormattedText)label)) / 2;
        Objects.requireNonNull((Object)this.font);
        ctx.text(this.font, (Component)label, n, by + (11 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    private int cogX(int backW) {
        return backW > 0 ? this.backX(backW) - 3 - 11 : this.frameX + this.frameW - 4 - 11;
    }

    private int switchX(int backW, boolean cog) {
        int right = cog ? this.cogX(backW) : (backW > 0 ? this.backX(backW) : this.frameX + this.frameW - 4);
        return right - 4 - 16 - 3 - this.font.width((FormattedText)Lang.of("fwm.header.minimap", new Object[0]));
    }

    private boolean overSwitch(int backW, boolean cog, double mx, double my) {
        int right = cog ? this.cogX(backW) : (backW > 0 ? this.backX(backW) : this.frameX + this.frameW - 4);
        return mx >= (double)this.switchX(backW, cog) && mx < (double)(right - 4) && my >= (double)this.backY() && my < (double)(this.backY() + 11);
    }

    private void renderSwitch(GuiGraphicsExtractor ctx, String featureKey, int backW, boolean cog, int mouseX, int mouseY) {
        boolean on = Features.isOn(featureKey);
        boolean hovered = this.overSwitch(backW, cog, mouseX, mouseY);
        MutableComponent label = Lang.of("fwm.header.minimap", new Object[0]);
        int sx = this.switchX(backW, cog);
        int n = this.backY();
        Objects.requireNonNull((Object)this.font);
        ctx.text(this.font, (Component)label, sx, n + (11 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : (on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM), false);
        FwmTheme.toggle(ctx, sx + this.font.width((FormattedText)label) + 3, this.backY() + 1, 16, 9, Anim.toward("fwm.header." + featureKey, on, 120));
    }

    private void renderCog(GuiGraphicsExtractor ctx, int backW, int mouseX, int mouseY) {
        int cx = this.cogX(backW);
        int cy = this.backY();
        boolean hovered = mouseX >= cx && mouseX < cx + 11 && mouseY >= cy && mouseY < cy + 11;
        FwmTheme.box(ctx, cx, cy, 11, 11, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        SharpIcons.draw(ctx, "icon_cog", cx + 1, cy + 1, 9, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT);
    }

    private int backX(int backW) {
        return this.frameX + this.frameW - 4 - backW;
    }

    private int backY() {
        return this.frameY + 1;
    }

    private void renderFooter(GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        int footerTop = this.panelY + this.panelH - 16;
        FwmTheme.divider(ctx, this.panelX + 6, footerTop, this.panelW - 12);
        ctx.text(this.font, (Component)Lang.of("fwm.menu.footer", FwmClient.MOD_VERSION), this.panelX + 6 + 2, footerTop + 5, FwmTheme.TEXT_DIM, false);
        int fy = this.flagY();
        FwmTheme.germanFlag(ctx, this.flagX(0), fy, 12, 8, Lang.isGerman());
        FwmTheme.usFlag(ctx, this.flagX(1), fy, 12, 8, !Lang.isGerman());
        int tw = this.tourButtonW();
        int tx = this.tourButtonX(tw);
        boolean over = mouseX >= tx && mouseX < tx + tw && mouseY >= fy - 1 && mouseY < fy + 8 + 1;
        ctx.text(this.font, (Component)Lang.of("fwm.tutorial.replayShort", new Object[0]), tx, footerTop + 5, over ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
    }

    private int tourButtonW() {
        return this.font.width((FormattedText)Lang.of("fwm.tutorial.replayShort", new Object[0]));
    }

    private int tourButtonX(int w) {
        return this.flagX(0) - 5 - 2 - w;
    }

    private int flagY() {
        return this.panelY + this.panelH - 16 + 4 + 1;
    }

    private int flagX(int index) {
        int right = this.panelX + this.panelW - 6 - 2;
        return right - (2 - index) * 12 - (1 - index) * 5;
    }

    private int flagAt(double mx, double my) {
        if (my < (double)(this.flagY() - 1) || my >= (double)(this.flagY() + 8 + 1)) {
            return -1;
        }
        for (int i = 0; i < 2; ++i) {
            if (!(mx >= (double)(this.flagX(i) - 1)) || !(mx < (double)(this.flagX(i) + 12 + 1))) continue;
            return i;
        }
        return -1;
    }

    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        boolean hit = this.handleClick(click, doubled);
        if (hit && FwmConfig.INSTANCE.menuSounds) {
            Minecraft.getInstance().getSoundManager().play((SoundInstance)SimpleSoundInstance.forUI((Holder)SoundEvents.UI_BUTTON_CLICK, (float)1.0f));
        }
        return hit;
    }

    private boolean handleClick(MouseButtonEvent click, boolean doubled) {
        int category;
        if (FwmTutorial.mouseClicked(click.x(), click.y(), GLFW.button(click.button()))) {
            return true;
        }
        if (FwmTutorial.consumes(click.x(), this.width)) {
            return true;
        }
        double mx = this.toLocal(click.x());
        double my = this.toLocal(click.y());
        if (GLFW.button(click.button()) == 0) {
            int backW;
            int cx;
            int tw = this.tourButtonW();
            int tx = this.tourButtonX(tw);
            if (mx >= (double)tx && mx < (double)(tx + tw) && my >= (double)(this.flagY() - 1) && my < (double)(this.flagY() + 8 + 1)) {
                FwmConfig.INSTANCE.tutorialSeen = false;
                ConfigManager.save();
                FwmTutorial.start();
                return true;
            }
            int flag = this.flagAt(mx, my);
            if (flag >= 0) {
                Lang.set(flag == 0 ? "de" : "en");
                ConfigManager.save();
                this.layout();
                return true;
            }
            if (this.active().showTitle() && this.active().headerSwitch() != null && this.overSwitch(this.subs.isEmpty() ? 0 : this.backWidth(), this.active().settingsCog(), mx, my)) {
                Features.toggle(this.active().headerSwitch());
                return true;
            }
            if (this.active().showTitle() && this.active().settingsCog() && mx >= (double)(cx = this.cogX(this.subs.isEmpty() ? 0 : this.backWidth())) && mx < (double)(cx + 11) && my >= (double)this.backY() && my < (double)(this.backY() + 11)) {
                this.tourShow("fwm.tab.settings", 4);
                return true;
            }
            if (!this.subs.isEmpty() && mx >= (double)this.backX(backW = this.backWidth()) && mx < (double)(this.backX(backW) + backW) && my >= (double)this.backY() && my < (double)(this.backY() + 11)) {
                this.closeSub();
                return true;
            }
            if (mx >= (double)(this.panelX + 6) && mx < (double)(this.panelX + 6 + this.sidebarW) && my >= (double)this.headerY && my < (double)(this.headerY + 18)) {
                while (!this.subs.isEmpty()) {
                    this.closeSub();
                }
                this.select(0);
                return true;
            }
            if (this.subs.isEmpty() && this.base instanceof SpawnerPanel && this.overIdentity(mx, my) && Members.isMember(FwmGfx.playerName())) {
                this.openSub(new SpawnerAdPanel(this));
                return true;
            }
            int category2 = this.categoryAt(mx, my);
            if (category2 >= 0) {
                this.select(category2);
                return true;
            }
        }
        if (GLFW.button(click.button()) == 1 && (category = this.categoryAt(mx, my)) >= 0 && !((Category)((Object)this.categories.get(category))).locked()) {
            this.dragCategory = category;
            this.dragMoved = false;
            return true;
        }
        if (this.active().mouseClicked(mx, my, GLFW.button(click.button()))) {
            return true;
        }
        return super.mouseClicked(click, doubled);
    }

    private int categoryAt(double mx, double my) {
        if (mx < (double)(this.panelX + 6) || mx >= (double)(this.panelX + 6 + this.sidebarW)) {
            return -1;
        }
        if (my < (double)this.frameY || my >= (double)this.sidebarViewBottom()) {
            return -1;
        }
        int cy = this.frameY - this.sidebarScroll;
        for (SidebarRow row : this.sidebarRows()) {
            if (row.isHeading()) {
                cy += 10;
                continue;
            }
            if (my >= (double)cy && my < (double)(cy + 24)) {
                return row.categoryIndex();
            }
            cy += 30;
        }
        return -1;
    }

    private boolean overSidebar(double mx, double my) {
        return mx >= (double)(this.panelX + 6) && mx < (double)(this.panelX + 6 + this.sidebarW) && my >= (double)this.frameY && my < (double)this.sidebarViewBottom();
    }

    private void select(int index) {
        if (((Category)((Object)this.categories.get(index))).locked() || index == this.selected) {
            return;
        }
        while (!this.subs.isEmpty()) {
            this.closeSub();
        }
        if (this.base != null) {
            this.base.onHidden();
        }
        this.selected = index;
        this.base = (Panel)((Category)((Object)this.categories.get(index))).panel().get();
        this.layout();
    }

    public boolean mouseDragged(MouseButtonEvent click, double deltaX, double deltaY) {
        if (this.dragCategory >= 0) {
            this.dragTo(this.toLocal(click.x()), this.toLocal(click.y()));
            return true;
        }
        if (this.active().mouseDragged(this.toLocal(click.x()), this.toLocal(click.y()), GLFW.button(click.button()))) {
            return true;
        }
        return super.mouseDragged(click, deltaX, deltaY);
    }

    private void dragTo(double mx, double my) {
        int over = this.categoryAt(mx, my);
        if (over < 0 || over == this.dragCategory) {
            return;
        }
        if (((Category)((Object)this.categories.get(over))).locked()) {
            return;
        }
        List<Integer> order = this.orderedIndices();
        int from = order.indexOf((Object)this.dragCategory);
        int to = order.indexOf((Object)over);
        if (from < 0 || to < 0) {
            return;
        }
        this.moveCategory(this.dragCategory, to > from ? 1 : -1);
        this.dragMoved = true;
    }

    public boolean mouseReleased(MouseButtonEvent click) {
        if (this.dragCategory >= 0) {
            if (!this.dragMoved && Features.canSwitch(((Category)((Object)this.categories.get(this.dragCategory))).labelKey())) {
                Features.toggle(((Category)((Object)this.categories.get(this.dragCategory))).labelKey());
            }
            this.dragCategory = -1;
            this.dragMoved = false;
            ConfigManager.save();
        }
        this.active().mouseReleased();
        if (this.builtForScale != this.configuredScale()) {
            this.layout();
        }
        return super.mouseReleased(click);
    }

    public boolean mouseScrolled(double mouseX, double mouseY, double horizontal, double vertical) {
        int maxScroll;
        double my;
        double mx = this.toLocal(mouseX);
        if (this.overSidebar(mx, my = this.toLocal(mouseY)) && (maxScroll = this.maxSidebarScroll()) > 0) {
            this.sidebarScroll = Math.max((int)0, (int)Math.min((int)maxScroll, (int)(this.sidebarScroll - (int)Math.signum((double)vertical) * 30)));
            return true;
        }
        if (this.active().mouseScrolled(mx, my, vertical)) {
            return true;
        }
        return super.mouseScrolled(mouseX, mouseY, horizontal, vertical);
    }

    public boolean keyPressed(KeyEvent input) {
        if (this.active().isCapturingKeys() && this.active().keyPressed(input.key(), input.keycode(), input.modifiers())) {
            return true;
        }
        if (FwmTutorial.keyPressed(input.key())) {
            return true;
        }
        if (this.menuKeyArmed && FwmScreen.isMenuKey(input) && !this.active().isCapturingKeys() && !this.typingInField()) {
            this.onClose();
            return true;
        }
        if (input.key() == 41) {
            if (!this.subs.isEmpty()) {
                this.closeSub();
                return true;
            }
            if (this.selected != 0) {
                this.select(0);
                return true;
            }
            this.onClose();
            return true;
        }
        if (this.active().keyPressed(input.key(), input.keycode(), input.modifiers())) {
            return true;
        }
        return super.keyPressed(input);
    }

    private static boolean menuKeyHeld() {
        KeyMapping binding = Keybinds.OPEN_MENU;
        if (binding == null) {
            return false;
        }
        InputConstants.Key key = InputConstants.getKey((String)binding.saveString());
        if (key == null || key.getType() != InputConstants.Type.KEYBOARD) {
            return false;
        }
        return InputConstants.isKeyDown((int)key.getValue());
    }

    private static boolean isMenuKey(KeyEvent input) {
        KeyMapping binding = Keybinds.OPEN_MENU;
        return binding != null && binding.matches(input);
    }

    private boolean typingInField() {
        for (Object child : this.children()) {
            EditBox field;
            if (!(child instanceof EditBox) || !(field = (EditBox)child).isFocused()) continue;
            return true;
        }
        return false;
    }

    public boolean keyReleased(KeyEvent input) {
        if (FwmScreen.isMenuKey(input)) {
            this.menuKeyArmed = true;
        }
        return super.keyReleased(input);
    }

    public boolean charTyped(CharacterEvent input) {
        if (this.active().charTyped(input)) {
            return true;
        }
        return super.charTyped(input);
    }

    public void onClose() {
        while (!this.subs.isEmpty()) {
            this.closeSub();
        }
        if (this.base != null) {
            this.base.onHidden();
        }
        FwmConfig.INSTANCE.clamp();
        ConfigManager.save();
        AntiLeak.sync(this.minecraft);
        if (this.minecraft != null) {
            this.minecraft.gui.setScreen(this.parent);
        }
    }

    public boolean isPauseScreen() {
        return false;
    }

    private record SidebarRow(int categoryIndex, String headingKey) {
        boolean isHeading() {
            return this.categoryIndex < 0;
        }
    }
}
