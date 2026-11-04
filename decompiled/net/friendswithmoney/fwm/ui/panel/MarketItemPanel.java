/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.time.LocalDate
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.net.Market;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.net.MarketHistoryData;
import net.friendswithmoney.fwm.net.MarketStatsData;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.MarketPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix3x2fStack;

public class MarketItemPanel
extends Panel {
    private static final int HEAD_H = 20;
    private static final int STRIP_H = 14;
    private static final int ROW_H = 14;
    private static final int ICON = 16;
    private static final List<String> PERIODS = List.of((Object)"1h", (Object)"24h", (Object)"3d", (Object)"7d", (Object)"31d");
    private static final int DEFAULT_PERIOD = 1;
    private static final int CHART = PERIODS.size();
    private static final int SLOTS = PERIODS.size() + 1;
    private static final int CHART_HEAD = 12;
    private static final int CHART_FOOT = 11;
    private final MarketData.Item item;
    private int active = 1;
    private double[] chartX = new double[0];
    private int chartBoxX;
    private int chartBoxY;
    private int chartBoxW;
    private int chartBoxH;
    private String series;
    private static final String[] SERIES = new String[]{"ah", "orders"};
    private static final int SERIES_H = 10;
    private int markFrom = -1;
    private int markTo = -1;
    private final PanelHost host;
    private int pingX;
    private int pingW;
    private static final int PING_H = 12;

    public MarketItemPanel(MarketData.Item item, PanelHost host) {
        this.item = item;
        this.host = host;
        Market.requestStats(item.itemKey);
        Market.requestHistory(item.itemKey);
    }

    @Override
    public Component title() {
        return MarketPanel.nameFor(this.item);
    }

    private int stripY() {
        return this.y + 20;
    }

    private int listY() {
        return this.stripY() + 14 + 4;
    }

    private int slotW() {
        return this.width / SLOTS;
    }

    private int slotX(int slot) {
        return this.x + slot * this.slotW();
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.renderHead(ctx, font, mouseX, mouseY);
        this.renderStrip(ctx, font, mouseX, mouseY);
        if (this.active == CHART) {
            this.renderChart(ctx, font, mouseX, mouseY);
            return;
        }
        MarketStatsData data = Market.stats(this.item.itemKey);
        if (data == null) {
            this.centered(ctx, font, (Component)Lang.of(Market.statsFailed(this.item.itemKey) ? "fwm.market.offline" : "fwm.market.loading", new Object[0]));
            return;
        }
        MarketStatsData.Period period = data.period((String)PERIODS.get(this.active));
        if (period == null) {
            this.centered(ctx, font, (Component)Lang.of("fwm.market.noData", new Object[0]));
            return;
        }
        this.renderFigures(ctx, font, period);
    }

    private void renderHead(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        MarketPanel.drawIcon(ctx, this.item, this.x, this.y + 1);
        this.pingX = this.x + this.width;
        this.pingW = 0;
        MutableComponent key = Component.literal((String)font.plainSubstrByWidth(this.item.itemKey == null ? "?" : this.item.itemKey, this.pingX - this.x - 16 - 10));
        ctx.text(font, (Component)key, this.x + 16 + 4, this.y + 5, FwmTheme.TEXT_DIM, false);
    }

    private void renderStrip(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        for (int slot = 0; slot < SLOTS; ++slot) {
            int bx = this.slotX(slot);
            int bw = this.slotW();
            boolean isActive = slot == this.active;
            boolean hovered = !isActive && mouseX >= bx && mouseX < bx + bw && mouseY >= this.stripY() && mouseY < this.stripY() + 14;
            FwmTheme.sidebarButton(ctx, bx, this.stripY(), bw, 14, isActive, hovered, false);
            MutableComponent label = Lang.of(slot == CHART ? "fwm.market.chart" : "fwm.market.period." + (String)PERIODS.get(slot), new Object[0]);
            MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label.getString(), bw - 4));
            int n = bx + (bw - font.width((FormattedText)shown)) / 2;
            int n2 = this.stripY();
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)shown, n, n2 + (14 - 9) / 2 + 1, FwmTheme.sidebarTextColor(isActive, hovered, false), false);
        }
    }

    private void renderFigures(GuiGraphicsExtractor ctx, Font font, MarketStatsData.Period p) {
        int row = 0;
        row = this.figure(ctx, font, row, "fwm.market.ahAvg", this.price(p.ahAvgPrice), FwmTheme.TEXT);
        row = this.figure(ctx, font, row, "fwm.market.orderAvg", this.price(p.ordersAvgPrice), FwmTheme.TEXT);
        row = this.figure(ctx, font, row, "fwm.market.highest", this.price(p.highestUnitPrice), FwmTheme.ACCENT);
        ++row;
        row = this.figure(ctx, font, row, "fwm.market.volume", this.volume(p.totalMarketVolume), FwmTheme.TEXT);
        row = this.figure(ctx, font, row, "fwm.market.amount", this.amount(p.totalAmountMarkt), FwmTheme.TEXT);
        row = this.figure(ctx, font, row, "fwm.market.amountSplit", this.amount(p.amountAh) + " / " + this.amount(p.amountOrders), FwmTheme.TEXT);
        row = this.figure(ctx, font, row, "fwm.market.entries", this.entries(p), FwmTheme.TEXT);
        if (p.calculatedAt == null || p.calculatedAt.isBlank()) {
            return;
        }
        MutableComponent when = Lang.of("fwm.market.calculated", p.calculatedAt);
        Objects.requireNonNull((Object)font);
        int whenY = Math.min((int)(this.y + this.height - 9), (int)(this.listY() + (row + 1) * 14 + 2));
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(when.getString(), this.width)), this.x, whenY, FwmTheme.TEXT_DIM, false);
    }

    private int figure(GuiGraphicsExtractor ctx, Font font, int row, String labelKey, String value, int color) {
        int rowY = this.listY() + row * 14;
        if (rowY + 14 > this.y + this.height) {
            return row + 1;
        }
        Objects.requireNonNull((Object)font);
        int textY = rowY + (14 - 9) / 2;
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str(labelKey, new Object[0]), this.width - font.width(value) - 8)), this.x, textY, FwmTheme.TEXT_DIM, false);
        ctx.text(font, (Component)Component.literal((String)value), this.x + this.width - font.width(value), textY, color, false);
        FwmTheme.divider(ctx, this.x, rowY + 14 - 1, this.width);
        return row + 1;
    }

    private void centered(GuiGraphicsExtractor ctx, Font font, Component text) {
        ctx.text(font, text, this.x + (this.width - font.width((FormattedText)text)) / 2, this.listY() + (this.y + this.height - this.listY()) / 2 - 4, FwmTheme.TEXT_DIM, false);
    }

    private void renderSeriesToggle(GuiGraphicsExtractor ctx, Font font, String chosen, int mouseX, int mouseY) {
        int bx = this.x;
        int by = this.listY();
        for (String name : SERIES) {
            String label = Lang.str("fwm.market.series." + name, new Object[0]);
            int bw = font.width(label) + 8;
            boolean on = name.equals((Object)chosen);
            boolean hovered = !on && mouseX >= bx && mouseX < bx + bw && mouseY >= by && mouseY < by + 10;
            FwmTheme.box(ctx, bx, by, bw, 10, on ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, on || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
            ctx.text(font, (Component)Component.literal((String)label), bx + 4, by + 1, on ? FwmTheme.TEXT : (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM), false);
            bx += bw + 2;
        }
    }

    private int seriesToggleWidth(Font font) {
        int w = 0;
        for (String name : SERIES) {
            w += font.width(Lang.str("fwm.market.series." + name, new Object[0])) + 8 + 2;
        }
        return w - 2;
    }

    private String seriesAt(Font font, double mouseX, double mouseY) {
        int bx = this.x;
        int by = this.listY();
        if (mouseY < (double)by || mouseY >= (double)(by + 10)) {
            return null;
        }
        for (String name : SERIES) {
            int bw = font.width(Lang.str("fwm.market.series." + name, new Object[0])) + 8;
            if (mouseX >= (double)bx && mouseX < (double)(bx + bw)) {
                return name;
            }
            bx += bw + 2;
        }
        return null;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (button != 0 || this.markFrom < 0) {
            return false;
        }
        this.markTo = this.nearestDay(mouseX);
        return true;
    }

    @Override
    public void mouseReleased() {
        this.markFrom = -1;
        this.markTo = -1;
    }

    private boolean overChart(double mouseX, double mouseY) {
        return this.chartX.length > 1 && mouseX >= (double)this.chartBoxX && mouseX < (double)(this.chartBoxX + this.chartBoxW) && mouseY >= (double)this.chartBoxY && mouseY < (double)(this.chartBoxY + this.chartBoxH);
    }

    private int nearestDay(double mouseX) {
        int best = -1;
        double distance = Double.MAX_VALUE;
        for (int i = 0; i < this.chartX.length; ++i) {
            double d = Math.abs((double)(this.chartX[i] - mouseX));
            if (!(d < distance)) continue;
            distance = d;
            best = i;
        }
        return best;
    }

    private void renderChart(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean marking;
        MarketHistoryData.History data = Market.history(this.item.itemKey);
        if (data == null) {
            this.centered(ctx, font, (Component)Lang.of(Market.historyFailed(this.item.itemKey) ? "fwm.market.offline" : "fwm.market.loading", new Object[0]));
            return;
        }
        String chosen = this.series != null ? this.series : ("orders".equals((Object)data.source) ? "orders" : "ah");
        this.renderSeriesToggle(ctx, font, chosen, mouseX, mouseY);
        ArrayList points = new ArrayList();
        for (MarketHistoryData.Point raw : data.list()) {
            Double value;
            Double d = value = "orders".equals((Object)chosen) ? raw.orders : raw.ah;
            if (raw.ah == null && raw.orders == null) {
                Double d2 = value = raw.p > 0.0 ? Double.valueOf((double)raw.p) : null;
            }
            if (value == null || value <= 0.0) continue;
            MarketHistoryData.Point copy = new MarketHistoryData.Point();
            copy.d = raw.d;
            copy.p = value;
            points.add((Object)copy);
        }
        if (points.size() < 2 && data.list().size() >= 2) {
            this.wrapped(ctx, font, (Component)Lang.of("fwm.market.chartNoSeries", Lang.str("fwm.market.series." + chosen, new Object[0])));
            return;
        }
        if (points.size() < 2) {
            this.wrapped(ctx, font, (Component)Lang.of("fwm.market.chartEmpty", MarketItemPanel.shortDate(data.since == null ? "2026-10-01" : data.since)));
            return;
        }
        int boxX = this.x;
        int boxY = this.listY() + 12;
        int boxW = this.width;
        int boxH = Math.max((int)20, (int)(this.y + this.height - 11 - boxY));
        FwmTheme.box(ctx, boxX, boxY, boxW, boxH, FwmTheme.INSET, FwmTheme.EDGE);
        double min = Double.MAX_VALUE;
        double max = -1.7976931348623157E308;
        for (MarketHistoryData.Point point : points) {
            min = Math.min((double)min, (double)point.p);
            max = Math.max((double)max, (double)point.p);
        }
        double pad = (max - min) * 0.12;
        if (pad <= 0.0) {
            pad = Math.max((double)1.0, (double)(max * 0.05));
        }
        double lo = min - pad;
        double hi = max + pad;
        long firstDay = MarketItemPanel.epochDay(((MarketHistoryData.Point)points.get((int)0)).d);
        long span = Math.max((long)1L, (long)(MarketItemPanel.epochDay(((MarketHistoryData.Point)points.get((int)(points.size() - 1))).d) - firstDay));
        int innerX = boxX + 3;
        int innerW = boxW - 6;
        int innerY = boxY + 3;
        int innerH = boxH - 6;
        double[] px = new double[points.size()];
        double[] py = new double[points.size()];
        for (int i = 0; i < points.size(); ++i) {
            px[i] = (double)innerX + (double)(MarketItemPanel.epochDay(((MarketHistoryData.Point)points.get((int)i)).d) - firstDay) * (double)innerW / (double)span;
            py[i] = (double)innerY + (hi - ((MarketHistoryData.Point)points.get((int)i)).p) * (double)innerH / (hi - lo);
        }
        this.chartX = px;
        this.chartBoxX = boxX;
        this.chartBoxY = boxY;
        this.chartBoxW = boxW;
        this.chartBoxH = boxH;
        int markA = Math.min((int)this.markFrom, (int)this.markTo);
        int markB = Math.max((int)this.markFrom, (int)this.markTo);
        boolean bl = marking = this.markFrom >= 0 && markA < markB && markB < px.length;
        if (marking) {
            ctx.fill((int)Math.round((double)px[markA]), boxY + 1, (int)Math.round((double)px[markB]) + 1, boxY + boxH - 1, 0x22FFFFFF);
        }
        MarketItemPanel.drawLine(ctx, px, py, (List<MarketHistoryData.Point>)points, boxY + boxH - 1);
        ctx.text(font, (Component)Component.literal((String)this.price(String.valueOf((double)max))), innerX + 1, innerY, FwmTheme.TEXT_DIM, false);
        MutableComponent mutableComponent = Component.literal((String)this.price(String.valueOf((double)min)));
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)mutableComponent, innerX + 1, innerY + innerH - 9, FwmTheme.TEXT_DIM, false);
        int footY = boxY + boxH + 2;
        String first = MarketItemPanel.shortDate(((MarketHistoryData.Point)points.get((int)0)).d);
        String last = MarketItemPanel.shortDate(((MarketHistoryData.Point)points.get((int)(points.size() - 1))).d);
        ctx.text(font, (Component)Component.literal((String)first), boxX, footY, FwmTheme.TEXT_DIM, false);
        ctx.text(font, (Component)Component.literal((String)last), boxX + boxW - font.width(last), footY, FwmTheme.TEXT_DIM, false);
        int hover = -1;
        if (mouseX >= boxX && mouseX < boxX + boxW && mouseY >= boxY && mouseY < boxY + boxH) {
            double best = Double.MAX_VALUE;
            for (int i = 0; i < px.length; ++i) {
                double distance = Math.abs((double)(px[i] - (double)mouseX));
                if (!(distance < best)) continue;
                best = distance;
                hover = i;
            }
        }
        int headY = this.listY() + 1;
        int textX = this.x + this.seriesToggleWidth(font) + 6;
        if (marking) {
            for (int i : new int[]{markA, markB}) {
                int lineX = (int)Math.round((double)px[i]);
                ctx.fill(lineX, boxY + 1, lineX + 1, boxY + boxH - 1, -1996488705);
            }
            MutableComponent range = Component.literal((String)(MarketItemPanel.shortDate(((MarketHistoryData.Point)points.get((int)markA)).d) + " - " + MarketItemPanel.shortDate(((MarketHistoryData.Point)points.get((int)markB)).d)));
            ctx.text(font, (Component)range, textX, headY, FwmTheme.TEXT, false);
            double marked = (((MarketHistoryData.Point)points.get((int)markB)).p / ((MarketHistoryData.Point)points.get((int)markA)).p - 1.0) * 100.0;
            String markedText = MarketPanel.changeText((double)Math.round((double)(marked * 10.0)) / 10.0);
            ctx.text(font, (Component)Component.literal((String)markedText), this.x + this.width - font.width(markedText), headY, MarketPanel.changeColor(marked), false);
            return;
        }
        if (hover >= 0) {
            int lineX = (int)Math.round((double)px[hover]);
            ctx.fill(lineX, boxY + 1, lineX + 1, boxY + boxH - 1, 0x55FFFFFF);
            int dotY = (int)Math.round((double)py[hover]);
            ctx.fill(lineX - 1, dotY - 1, lineX + 2, dotY + 2, FwmTheme.TEXT);
            MutableComponent day = Component.literal((String)(MarketItemPanel.shortDate(((MarketHistoryData.Point)points.get((int)hover)).d) + "  " + this.price(String.valueOf((double)((MarketHistoryData.Point)points.get((int)hover)).p))));
            ctx.text(font, (Component)day, textX, headY, FwmTheme.TEXT, false);
        } else {
            ctx.text(font, (Component)Lang.of("fwm.market.chartDays", span), textX, headY, FwmTheme.TEXT_DIM, false);
        }
        double change = (((MarketHistoryData.Point)points.get((int)(points.size() - 1))).p / ((MarketHistoryData.Point)points.get((int)0)).p - 1.0) * 100.0;
        String changeText = MarketPanel.changeText((double)Math.round((double)(change * 10.0)) / 10.0);
        ctx.text(font, (Component)Component.literal((String)changeText), this.x + this.width - font.width(changeText), headY, MarketPanel.changeColor(change), false);
    }

    private static void drawLine(GuiGraphicsExtractor ctx, double[] px, double[] py, List<MarketHistoryData.Point> points, int floorY) {
        int gui = Math.max((int)1, (int)Minecraft.getInstance().getWindow().getGuiScale());
        int thick = Math.max((int)1, (int)(gui / 2 + 1));
        int floor = floorY * gui;
        int dash = 3 * gui;
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.scale(1.0f / (float)gui, 1.0f / (float)gui);
        int i = 0;
        while (i + 1 < px.length) {
            boolean up = ((MarketHistoryData.Point)points.get((int)(i + 1))).p >= ((MarketHistoryData.Point)points.get((int)i)).p;
            int color = up ? -12723068 : -1547164;
            int fill = color & 0xFFFFFF | 0x26000000;
            boolean gap = MarketItemPanel.epochDay(((MarketHistoryData.Point)points.get((int)(i + 1))).d) - MarketItemPanel.epochDay(((MarketHistoryData.Point)points.get((int)i)).d) > 1L;
            int x0 = (int)Math.round((double)(px[i] * (double)gui));
            int x1 = (int)Math.round((double)(px[i + 1] * (double)gui));
            double y0 = py[i] * (double)gui;
            double y1 = py[i + 1] * (double)gui;
            int steps = Math.max((int)1, (int)(x1 - x0));
            for (int c = x0; c < Math.max((int)(x0 + 1), (int)x1); ++c) {
                if (gap && (c - x0) / dash % 2 == 1) continue;
                double a = y0 + (y1 - y0) * (double)(c - x0) / (double)steps;
                double b = y0 + (y1 - y0) * (double)Math.min((int)steps, (int)(c - x0 + 1)) / (double)steps;
                int top = (int)Math.floor((double)Math.min((double)a, (double)b)) - thick / 2;
                int bottom = (int)Math.ceil((double)Math.max((double)a, (double)b)) + (thick + 1) / 2;
                if (!gap) {
                    ctx.fill(c, bottom, c + 1, floor, fill);
                }
                ctx.fill(c, top, c + 1, bottom, gap ? color & 0xFFFFFF | 0xB0000000 : color);
            }
            ++i;
        }
        int r = Math.max((int)1, (int)gui);
        for (int i2 = 0; i2 < px.length; ++i2) {
            int from = i2 == 0 ? 0 : i2 - 1;
            int to = i2 == 0 ? Math.min((int)1, (int)(px.length - 1)) : i2;
            boolean up = ((MarketHistoryData.Point)points.get((int)to)).p >= ((MarketHistoryData.Point)points.get((int)from)).p;
            int cx = (int)Math.round((double)(px[i2] * (double)gui));
            int cy = (int)Math.round((double)(py[i2] * (double)gui));
            ctx.fill(cx - r - 1, cy - r - 1, cx + r + 2, cy + r + 2, -871751916);
            ctx.fill(cx - r, cy - r, cx + r + 1, cy + r + 1, up ? -12723068 : -1547164);
        }
        matrices.popMatrix();
    }

    private static long epochDay(String day) {
        try {
            return LocalDate.parse((CharSequence)day).toEpochDay();
        }
        catch (Exception e) {
            return 0L;
        }
    }

    private static String shortDate(String day) {
        if (day == null || day.length() < 10) {
            return "?";
        }
        return day.substring(8, 10) + "." + day.substring(5, 7) + ".";
    }

    private void wrapped(GuiGraphicsExtractor ctx, Font font, Component text) {
        List lines = font.split((FormattedText)text, this.width - 8);
        int lineY = this.listY() + (this.y + this.height - this.listY() - lines.size() * 10) / 2;
        for (FormattedCharSequence line : lines) {
            ctx.text(font, line, this.x + (this.width - font.width(line)) / 2, lineY, FwmTheme.TEXT_DIM, false);
            lineY += 10;
        }
    }

    private String price(String raw) {
        double value = Market.number(raw);
        if (Double.isNaN((double)value)) {
            return "-";
        }
        long rounded = Math.round((double)value);
        return rounded < 1000000L ? Fmt.dollars(rounded * 100L) : "$" + Fmt.compactUp(rounded);
    }

    private String volume(String raw) {
        double value = Market.number(raw);
        return Double.isNaN((double)value) ? "-" : "$" + Fmt.compactUp(Math.round((double)value));
    }

    private String amount(String raw) {
        double value = Market.number(raw);
        return Double.isNaN((double)value) ? "-" : Fmt.compact(Math.round((double)value));
    }

    private String entries(MarketStatsData.Period p) {
        long ah = Market.rounded(p.ahEntryCount);
        long orders = Market.rounded(p.ordersEntryCount);
        return Fmt.compact(ah) + " / " + Fmt.compact(orders);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        String hit;
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (this.active == CHART && (hit = this.seriesAt(Minecraft.getInstance().font, mouseX, mouseY)) != null) {
            this.series = hit;
            this.markFrom = -1;
            this.markTo = -1;
            return true;
        }
        if (this.active == CHART && this.overChart(mouseX, mouseY)) {
            this.markTo = this.markFrom = this.nearestDay(mouseX);
            return true;
        }
        if (mouseY < (double)this.stripY() || mouseY >= (double)(this.stripY() + 14)) {
            return false;
        }
        for (int slot = 0; slot < SLOTS; ++slot) {
            if (!(mouseX >= (double)this.slotX(slot)) || !(mouseX < (double)(this.slotX(slot) + this.slotW()))) continue;
            this.active = slot;
            return true;
        }
        return true;
    }
}
