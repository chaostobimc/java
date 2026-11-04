/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.net.PricePings;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.MarketPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.PricePingPanel;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class PingListPanel
extends Panel {
    private static final int ROW_H = 16;
    private static final int DEL_W = 12;
    private final PanelHost host;
    private int scroll;

    public PingListPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.kommt.pings", new Object[0]);
    }

    private int rows() {
        return Math.max((int)1, (int)((this.height - 32) / 16));
    }

    private int addY() {
        return this.y + this.height - 30;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        List<FwmConfig.PricePing> pings = PricePings.list();
        this.scroll = Math.max((int)0, (int)Math.min((int)this.scroll, (int)Math.max((int)0, (int)(pings.size() - this.rows()))));
        for (int i = 0; i < this.rows() && this.scroll + i < pings.size(); ++i) {
            String kind;
            boolean over;
            FwmConfig.PricePing p = (FwmConfig.PricePing)pings.get(this.scroll + i);
            int ry = this.y + i * 16;
            boolean bl = over = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= ry && mouseY < ry + 16;
            if (over) {
                FwmTheme.hover(ctx, this.x, ry, this.width, 16);
            }
            String string = kind = p.order ? Lang.t("Order", "Order") : "AH";
            String rule = p.fired ? Lang.t("ausgel\u00f6st", "done") : (p.below() ? "\u2264 $" : "\u2265 $") + Fmt.compactUp(Math.round((double)p.maxPrice)) + (p.repeat ? " \u21bb" : "");
            int ty = ry + 4;
            ctx.text(font, (Component)Component.literal((String)kind), this.x + 3, ty, p.order ? -865972 : FwmTheme.ACCENT, false);
            int nameX = this.x + 3 + font.width("Order") + 6;
            int ruleW = font.width(rule);
            String name = p.name == null || p.name.isBlank() ? p.itemKey : p.name;
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(name == null ? "?" : name, this.width - (nameX - this.x) - ruleW - 12 - 10)), nameX, ty, FwmTheme.TEXT, false);
            ctx.text(font, (Component)Component.literal((String)rule), this.x + this.width - 12 - 6 - ruleW, ty, FwmTheme.TEXT, false);
            boolean overDel = over && mouseX >= this.x + this.width - 12;
            ctx.text(font, (Component)Component.literal((String)"\u00d7"), this.x + this.width - 12 + 3, ty, overDel ? -2067340 : FwmTheme.TEXT_DIM, false);
        }
        if (pings.isEmpty()) {
            MutableComponent none = Lang.of("fwm.kommt.noPings", new Object[0]);
            ctx.text(font, (Component)none, this.x + (this.width - font.width((FormattedText)none)) / 2, this.y + this.height / 2 - 10, FwmTheme.TEXT_DIM, false);
        }
        String add = Lang.t("+ Neuer Preis-Ping", "+ New price ping");
        int bw = font.width(add) + 16;
        boolean overAdd = mouseX >= this.x && mouseX < this.x + bw && mouseY >= this.addY() && mouseY < this.addY() + 16;
        FwmTheme.box(ctx, this.x, this.addY(), bw, 16, FwmTheme.RAISED, overAdd ? FwmTheme.ACCENT : FwmTheme.OFF);
        ctx.text(font, (Component)Component.literal((String)add), this.x + 8, this.addY() + 4, overAdd ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        MutableComponent hint = Lang.of("fwm.kommt.pingHint", new Object[0]);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(hint.getString(), this.width)), this.x, this.y + this.height - 9, FwmTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseY >= (double)this.addY() && mouseY < (double)(this.addY() + 16) && mouseX < (double)(this.x + 140)) {
            this.host.openSub(new MarketPanel(this.host, true));
            return true;
        }
        ArrayList pings = new ArrayList(PricePings.list());
        int i = (int)((mouseY - (double)this.y) / 16.0);
        if (i < 0 || i >= this.rows() || this.scroll + i >= pings.size()) {
            return true;
        }
        FwmConfig.PricePing p = (FwmConfig.PricePing)pings.get(this.scroll + i);
        if (mouseX >= (double)(this.x + this.width - 12)) {
            PricePings.remove(p.itemKey, p.order);
            return true;
        }
        MarketData.Item item = new MarketData.Item();
        item.itemKey = p.itemKey;
        item.baseItemKey = p.baseItemKey;
        item.icon = p.icon;
        this.host.openSub(new PricePingPanel(item, p.name == null ? p.itemKey : p.name, this.host));
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        this.scroll = Math.max((int)0, (int)(this.scroll - (int)Math.signum((double)vertical)));
        return true;
    }
}
