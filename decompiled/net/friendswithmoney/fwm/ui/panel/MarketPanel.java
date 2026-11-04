/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.HashMap
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
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.Market;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.SharpIcons;
import net.friendswithmoney.fwm.ui.WebIcons;
import net.friendswithmoney.fwm.ui.panel.MarketItemPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.PricePingPanel;
import net.friendswithmoney.fwm.util.Fmt;
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
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;

public class MarketPanel
extends Panel {
    private static final int SEARCH_H = 15;
    private static final int HEADER_H = 10;
    private static final int ROW_H = 18;
    private static final int STATUS_H = 13;
    private static final int SCROLLBAR_W = 4;
    private static final int ICON = 16;
    static final int UP = -12723068;
    static final int DOWN = -1547164;
    private static final String PRICE_SAMPLE = "$888.8M";
    private static final int NAME_MIN = 40;
    private static final long TYPE_PAUSE_MS = 300L;
    private static final int PREFETCH_ROWS = 4;
    private static final Map<String, ItemStack> ICONS = new HashMap();
    private final PanelHost host;
    private EditBox search;
    private String pendingQuery;
    private long pendingAt;
    private int listY;
    private int listH;
    private int rowsVisible;
    private int scroll;
    private boolean draggingBar;
    private int grabOffset;
    private static final int BAR_GRIP = 3;
    private final boolean pickForPing;
    private static final int REFRESH = 11;

    public MarketPanel(PanelHost host) {
        this(host, false);
    }

    public MarketPanel(PanelHost host, boolean pickForPing) {
        this.host = host;
        this.pickForPing = pickForPing;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.market", new Object[0]);
    }

    @Override
    protected void onResize() {
        String previous;
        this.listY = this.y + 15 + 2 + 10;
        this.listH = Math.max((int)18, (int)(this.y + this.height - 13 - 2 - this.listY));
        this.rowsVisible = Math.max((int)1, (int)(this.listH / 18));
        String string = previous = this.search == null ? Market.query() : this.search.getValue();
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        Font font = Minecraft.getInstance().font;
        this.search = new EditBox(font, this.x + 5, this.y + 2, this.width - 10, 11, (Component)Lang.of("fwm.market.search", new Object[0]));
        this.search.setBordered(false);
        this.search.setMaxLength(32);
        this.search.setTextColor(FwmTheme.TEXT);
        this.search.setHint((Component)Lang.of("fwm.market.search", new Object[0]));
        this.search.setValue(previous);
        this.search.setResponder(text -> {
            this.pendingQuery = text;
            this.pendingAt = System.currentTimeMillis();
        });
        this.host.attach((AbstractWidget)this.search);
        Market.openPage();
    }

    private List<MarketData.Item> items() {
        return Market.items();
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.items().size() - this.rowsVisible));
    }

    static Component nameFor(MarketData.Item item) {
        boolean hasDisplay;
        boolean german = "de".equalsIgnoreCase(FwmConfig.INSTANCE.language);
        boolean bl = hasDisplay = item.displayName != null && !item.displayName.isBlank();
        if (german && hasDisplay) {
            return Component.literal((String)item.displayName);
        }
        ItemStack stack = MarketPanel.iconFor(item);
        if (!stack.isEmpty()) {
            String base = stack.getHoverName().getString();
            if (item.variantKey == null || item.variantKey.isBlank()) {
                return Component.literal((String)base);
            }
            return Component.literal((String)(base + " - " + MarketPanel.spell(item.variantKey)));
        }
        if (hasDisplay) {
            return Component.literal((String)item.displayName);
        }
        return Component.literal((String)(item.itemKey == null ? "?" : item.itemKey));
    }

    private static String spell(String key) {
        StringBuilder out = new StringBuilder();
        for (String part : key.split("_")) {
            if (part.isEmpty()) continue;
            if (out.length() > 0) {
                out.append(' ');
            }
            out.append(Character.toUpperCase((char)part.charAt(0))).append(part.substring(1));
        }
        return out.length() == 0 ? key : out.toString();
    }

    public static void drawIcon(GuiGraphicsExtractor ctx, MarketData.Item item, int left, int top) {
        ItemStack stack = MarketPanel.iconFor(item);
        if (!stack.isEmpty()) {
            ctx.item(stack, left, top);
            return;
        }
        Identifier web = WebIcons.get(item.icon);
        if (web != null) {
            FwmGfx.blit(ctx, web, left, top, 16, 16, 16, 16, 16);
        }
    }

    static ItemStack iconFor(MarketData.Item item) {
        String key;
        String string = key = item.baseItemKey == null ? item.itemKey : item.baseItemKey;
        if (key == null || key.isBlank()) {
            return ItemStack.EMPTY;
        }
        ItemStack cached = (ItemStack)ICONS.get((Object)key);
        if (cached != null) {
            return cached;
        }
        ItemStack stack = ItemStack.EMPTY;
        try {
            Identifier id = Identifier.tryParse((String)(key.contains((CharSequence)":") ? key : "minecraft:" + key));
            if (id != null) {
                stack = (ItemStack)BuiltInRegistries.ITEM.getOptional(id).map(ItemStack::new).orElse((Object)ItemStack.EMPTY);
            }
        }
        catch (Exception e) {
            stack = ItemStack.EMPTY;
        }
        ICONS.put((Object)key, (Object)stack);
        return stack;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.applyPendingSearch();
        FwmTheme.box(ctx, this.x, this.y, this.width, 15, FwmTheme.RAISED, this.search.isFocused() ? FwmTheme.ACCENT : FwmTheme.OFF);
        this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        this.renderHeader(ctx, font);
        this.renderList(ctx, font, mouseX, mouseY);
        this.renderStatus(ctx, font, mouseX, mouseY);
    }

    private int rowWidth() {
        return this.width - (this.maxScroll() > 0 ? 6 : 0);
    }

    private void renderHeader(GuiGraphicsExtractor ctx, Font font) {
        int rowW = this.rowWidth();
        int textY = this.y + 15 + 2;
        int color = FwmTheme.TEXT_DIM;
        String price = " " + Lang.str("fwm.market.colPrice", new Object[0]);
        int priceRight = this.x + rowW - 4;
        int priceX = priceRight - font.width(price);
        ctx.text(font, (Component)Component.literal((String)price), priceX, textY, color, false);
        MarketPanel.drawAverageSign(ctx, font, priceX - font.width("O"), textY, color);
        String change = Lang.str("fwm.market.colChange", new Object[0]);
        int changeRight = this.x + rowW - 4 - font.width(PRICE_SAMPLE) - 6;
        ctx.text(font, (Component)Component.literal((String)change), changeRight - font.width(change), textY, color, false);
    }

    private static void drawAverageSign(GuiGraphicsExtractor ctx, Font font, int left, int top, int color) {
        ctx.text(font, (Component)Component.literal((String)"O"), left, top, color, false);
        int w = font.width("O") - 1;
        for (int i = 0; i <= 8; ++i) {
            int px = left + w - Math.round((float)((float)(i * (w + 1)) / 8.0f));
            ctx.fill(px, top - 1 + i, px + 1, top + i, color);
        }
    }

    private void applyPendingSearch() {
        if (this.pendingQuery == null) {
            return;
        }
        if (System.currentTimeMillis() - this.pendingAt < 300L) {
            return;
        }
        Market.search(this.pendingQuery);
        this.pendingQuery = null;
        this.scroll = 0;
    }

    private void renderList(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int index;
        List<MarketData.Item> all = this.items();
        if (all.isEmpty()) {
            MutableComponent empty = Lang.of(this.emptyKey(), new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.listY + this.listH / 2 - 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        this.scroll = Math.min((int)this.scroll, (int)this.maxScroll());
        ctx.enableScissor(this.x, this.listY, this.x + this.width, this.listY + this.listH);
        for (int i = 0; i < this.rowsVisible + 1 && (index = this.scroll + i) < all.size(); ++i) {
            this.renderRow(ctx, font, (MarketData.Item)all.get(index), index, this.listY + i * 18, mouseX, mouseY);
        }
        ctx.disableScissor();
        this.renderScrollbar(ctx);
        if (Market.hasMore() && !Market.busy() && all.size() <= this.rowsVisible) {
            Market.loadMore();
        }
    }

    private String emptyKey() {
        if (Market.busy()) {
            return "fwm.market.loading";
        }
        if (Market.state() == Market.State.ERROR) {
            return "fwm.market.offline";
        }
        if (!Market.query().isBlank()) {
            return "fwm.items.noMatch";
        }
        return "fwm.market.loading";
    }

    private void renderRow(GuiGraphicsExtractor ctx, Font font, MarketData.Item item, int index, int rowY, int mouseX, int mouseY) {
        String text;
        int changeX;
        boolean hovered;
        int rowW = this.rowWidth();
        boolean bl = hovered = mouseX >= this.x && mouseX < this.x + rowW && mouseY >= rowY && mouseY < rowY + 18 && mouseY >= this.listY && mouseY < this.listY + this.listH;
        if (hovered) {
            ctx.fill(this.x, rowY, this.x + rowW, rowY + 18, FwmTheme.HOVER);
        }
        MarketPanel.drawIcon(ctx, item, this.x + 2, rowY + 1);
        Objects.requireNonNull((Object)font);
        int textY = rowY + (18 - 9) / 2 + 1;
        int nameX = this.x + 2 + 16 + 4;
        String price = this.priceText(item);
        boolean hasPrice = Market.avgPrice(item.itemKey) != null;
        ctx.text(font, (Component)Component.literal((String)price), this.x + rowW - 4 - font.width(price), textY, hovered ? FwmTheme.ACCENT : (hasPrice ? FwmTheme.TEXT : FwmTheme.TEXT_DIM), false);
        int priceCol = Math.max((int)font.width(price), (int)font.width(PRICE_SAMPLE));
        int changeRight = this.x + rowW - 4 - priceCol - 6;
        int nameRoom = changeRight - 4 - nameX;
        Double change = Market.change(item.itemKey);
        if (change != null && (changeX = changeRight - font.width(text = MarketPanel.changeText(change))) - 4 - nameX >= 40) {
            ctx.text(font, (Component)Component.literal((String)text), changeX, textY, MarketPanel.changeColor(change), false);
            nameRoom = changeX - 4 - nameX;
        }
        MutableComponent name = Component.literal((String)font.plainSubstrByWidth(MarketPanel.nameFor(item).getString(), Math.max((int)8, (int)nameRoom)));
        ctx.text(font, (Component)name, nameX, textY, FwmTheme.TEXT, false);
        FwmTheme.divider(ctx, this.x, rowY + 18 - 1, rowW);
    }

    static String changeText(double change) {
        String number = Math.abs((double)change) >= 100.0 ? String.valueOf((long)Math.round((double)change)) : String.format((Locale)Locale.ROOT, (String)"%.1f", (Object[])new Object[]{change});
        return (change > 0.0 ? "+" : "") + number + "%";
    }

    static int changeColor(double change) {
        if (change > 0.0) {
            return -12723068;
        }
        if (change < 0.0) {
            return -1547164;
        }
        return FwmTheme.TEXT_DIM;
    }

    private String priceText(MarketData.Item item) {
        Double avg = Market.avgPrice(item.itemKey);
        return avg == null ? "-" : "$" + Fmt.compactUp(Math.round((double)avg));
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx) {
        if (this.maxScroll() <= 0) {
            return;
        }
        int trackX = this.x + this.width - 4;
        ctx.fill(trackX, this.listY, trackX + 4, this.listY + this.listH, FwmTheme.OFF);
        int thumbH = this.thumbH();
        int thumbY = this.thumbY(thumbH);
        ctx.fill(trackX, thumbY, trackX + 4, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private int thumbH() {
        return Math.max((int)12, (int)(this.listH * this.rowsVisible / Math.max((int)1, (int)this.items().size())));
    }

    private int thumbY(int thumbH) {
        return this.listY + (this.listH - thumbH) * this.scroll / Math.max((int)1, (int)this.maxScroll());
    }

    private boolean overBar(double mouseX, double mouseY) {
        return this.maxScroll() > 0 && mouseX >= (double)(this.x + this.width - 4 - 3) && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.listY && mouseY < (double)(this.listY + this.listH);
    }

    private void dragTo(double mouseY) {
        int thumbH = this.thumbH();
        int range = Math.max((int)1, (int)(this.listH - thumbH));
        double pos = Math.max((double)0.0, (double)Math.min((double)range, (double)(mouseY - (double)this.grabOffset - (double)this.listY)));
        this.scroll = (int)Math.round((double)(pos / (double)range * (double)this.maxScroll()));
        this.prefetch();
    }

    private void prefetch() {
        if (Market.hasMore() && this.scroll + this.rowsVisible + 4 >= this.items().size()) {
            Market.loadMore();
        }
    }

    private void renderStatus(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int barY = this.y + this.height - 13;
        FwmTheme.divider(ctx, this.x, barY - 1, this.width);
        Objects.requireNonNull((Object)font);
        int textY = barY + (13 - 9) / 2 + 1;
        MutableComponent left = Lang.of("fwm.market.count", this.items().size(), Market.totalItems());
        ctx.text(font, (Component)left, this.x + 4, textY, FwmTheme.TEXT_DIM, false);
        Component right = this.statusRight();
        ctx.text(font, right, this.refreshX() - 4 - font.width((FormattedText)right), textY, Market.state() == Market.State.ERROR ? -2056032 : FwmTheme.TEXT_DIM, false);
        boolean hot = this.overRefresh(mouseX, mouseY) && !Market.busy();
        int by = this.refreshY();
        FwmTheme.box(ctx, this.refreshX(), by, 11, 11, FwmTheme.RAISED, hot ? FwmTheme.ACCENT : FwmTheme.OFF);
        SharpIcons.draw(ctx, "icon_refresh", this.refreshX() + 1, by + 1, 9, Market.busy() ? FwmTheme.TEXT_DIM : (hot ? FwmTheme.ACCENT : FwmTheme.TEXT));
    }

    private int refreshX() {
        return this.x + this.width - 11 - 1;
    }

    private int refreshY() {
        return this.y + this.height - 13 + 1;
    }

    private boolean overRefresh(double mouseX, double mouseY) {
        return mouseX >= (double)this.refreshX() && mouseX < (double)(this.refreshX() + 11) && mouseY >= (double)this.refreshY() && mouseY < (double)(this.refreshY() + 11);
    }

    private Component statusRight() {
        if (Market.busy()) {
            return Lang.of("fwm.market.loading", new Object[0]);
        }
        if (Market.state() == Market.State.ERROR) {
            return Lang.of("fwm.market.offline", new Object[0]);
        }
        long age = Market.ageMinutes();
        return age < 0L ? Lang.of("fwm.market.loading", new Object[0]) : Lang.of("fwm.board.updated", age);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseY < (double)(this.y + 15)) {
            this.search.setFocused(true);
            return true;
        }
        this.search.setFocused(false);
        if (this.overRefresh(mouseX, mouseY)) {
            Market.refresh();
            return true;
        }
        if (this.overBar(mouseX, mouseY)) {
            int thumbH = this.thumbH();
            int thumbY = this.thumbY(thumbH);
            this.grabOffset = mouseY >= (double)thumbY && mouseY < (double)(thumbY + thumbH) ? (int)(mouseY - (double)thumbY) : thumbH / 2;
            this.draggingBar = true;
            this.dragTo(mouseY);
            return true;
        }
        if (mouseY >= (double)this.listY && mouseY < (double)(this.listY + this.listH)) {
            int index = this.scroll + (int)((mouseY - (double)this.listY) / 18.0);
            List<MarketData.Item> all = this.items();
            if (index >= 0 && index < all.size()) {
                MarketData.Item picked = (MarketData.Item)all.get(index);
                if (this.pickForPing) {
                    this.host.closeSub();
                    this.host.openSub(new PricePingPanel(picked, picked.displayName == null ? picked.itemKey : picked.displayName, this.host));
                } else {
                    this.host.openSub(new MarketItemPanel(picked, this.host));
                }
            }
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical) * 3)));
        this.prefetch();
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (!this.draggingBar) {
            return false;
        }
        this.dragTo(mouseY);
        return true;
    }

    @Override
    public void mouseReleased() {
        this.draggingBar = false;
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
    }
}
