// class net/friendswithmoney/fwm/ui/panel/MarketItemPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field HEAD_H I;
field STRIP_H I;
field ROW_H I;
field ICON I;
field PERIODS Ljava/util/List;;
field DEFAULT_PERIOD I;
field CHART I;
field SLOTS I;
field CHART_HEAD I;
field CHART_FOOT I;
field item Lnet/friendswithmoney/fwm/net/MarketData$Item;;
field active I;
field chartX [D;
field chartBoxX I;
field chartBoxY I;
field chartBoxW I;
field chartBoxH I;
field series Ljava/lang/String;;
field SERIES [Ljava/lang/String;;
field SERIES_H I;
field markFrom I;
field markTo I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field pingX I;
field pingW I;
field PING_H I;
public void <init>(net.MarketData$Item item, ui.panel.PanelHost host) {
    this.active = 1;
    this.chartX = new double[0];
    this.markFrom = m1;
    this.markTo = m1;
    this.item = item;
    this.host = host;
    net.Market.requestStats(item.itemKey);
    net.Market.requestHistory(item.itemKey);
    return;
}

public network.chat.Component title() {
    return ui.panel.MarketPanel.nameFor(this.item);
}

private int stripY() {
    return (this.y + 20);
}

private int listY() {
    return ((this.stripY() + 14) + 4);
}

private int slotW() {
    return (this.width / ui.panel.MarketItemPanel.SLOTS);
}

private int slotX(int slot) {
    return (this.x + (slot * this.slotW()));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.renderHead(ctx, font, mouseX, mouseY);
    this.renderStrip(ctx, font, mouseX, mouseY);
    if (this.active != ui.panel.MarketItemPanel.CHART) goto L38;
    this.renderChart(ctx, font, mouseX, mouseY);
    return;
    L38:
    v6 = net.Market.stats(this.item.itemKey);
    if (v6 != null) goto L89;
    if (net.Market.statsFailed(this.item.itemKey) ==0) goto L76;
    goto L78;
    L76:
    L78:
    ctx.centered(font, "fwm.market.offline", ui.Lang.of("fwm.market.loading", new Object[0]));
    return;
    L89:
    v7 = v6.period(((String)ui.panel.MarketItemPanel.PERIODS.get(this.active)));
    if (v7 != null) goto L132;
    this.centered(ctx, font, ui.Lang.of("fwm.market.noData", new Object[0]));
    return;
    L132:
    this.renderFigures(ctx, font, v7);
    return;
}

private void renderHead(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    ui.panel.MarketPanel.drawIcon(ctx, this.item, this.x, (this.y + 1));
    this.pingX = (this.x + this.width);
    this.pingW = 0;
    if (this.item.itemKey != null) goto L52;
    goto L59;
    L52:
    L59:
    v5 = network.chat.Component.literal("?".plainSubstrByWidth(this.item.itemKey, (((this.pingX - this.x) - 16) - 10)));
    ctx.text(font, v5, ((this.x + 16) + 4), (this.y + 5), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void renderStrip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = 0;
    L3:
    if (v5 >= ui.panel.MarketItemPanel.SLOTS) goto L223;
    v6 = this.slotX(v5);
    v7 = this.slotW();
    if (v5 != this.active) goto L38;
    goto L39;
    L38:
    L39:
    v8 = 0;
    if (v8 !=0) goto L86;
    if (mouseX < v6) goto L86;
    if (mouseX >= (v6 + v7)) goto L86;
    if (mouseY < this.stripY()) goto L86;
    if (mouseY >= (this.stripY() + 14)) goto L86;
    goto L87;
    L86:
    L87:
    v9 = 0;
    ui.FwmTheme.sidebarButton(ctx, v6, this.stripY(), v7, 14, v8, v9, 0);
    if (v5 != ui.panel.MarketItemPanel.CHART) goto L121;
    goto L139;
    L121:
    L139:
    v10 = ui.Lang.of('fwm.market.period.' + ((String)ui.panel.MarketItemPanel.PERIODS.get(v5)), new Object[0]);
    v11 = network.chat.Component.literal(font.plainSubstrByWidth(v10.getString(), (v7 - 4)));
    Objects.requireNonNull(font);
    ctx.text(font, v11, (v6 + ((v7 - font.width(v11)) / 2)), ((this.stripY() + ((14 - 9) / 2)) + 1), ui.FwmTheme.sidebarTextColor(v8, v9, 0), 0);
    v5 = v5 + 1;
    goto L3;
    L223:
    return;
}

private void renderFigures(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, net.MarketStatsData$Period p) {
    v4 = 0;
    v4 = this.figure(ctx, font, v4, "fwm.market.ahAvg", this.price(p.ahAvgPrice), ui.FwmTheme.TEXT);
    v4 = this.figure(ctx, font, v4, "fwm.market.orderAvg", this.price(p.ordersAvgPrice), ui.FwmTheme.TEXT);
    v4 = this.figure(ctx, font, v4, "fwm.market.highest", this.price(p.highestUnitPrice), ui.FwmTheme.ACCENT);
    v4 = v4 + 1;
    v4 = this.figure(ctx, font, v4, "fwm.market.volume", this.volume(p.totalMarketVolume), ui.FwmTheme.TEXT);
    v4 = this.figure(ctx, font, v4, "fwm.market.amount", this.amount(p.totalAmountMarkt), ui.FwmTheme.TEXT);
    v4 = this.figure(ctx, font, v4, "fwm.market.amountSplit", this.amount(p.amountAh) + ' / ' + this.amount(p.amountOrders), ui.FwmTheme.TEXT);
    v4 = this.figure(ctx, font, v4, "fwm.market.entries", this.entries(p), ui.FwmTheme.TEXT);
    if (p.calculatedAt == null) goto L194;
    if (p.calculatedAt.isBlank() ==0) goto L195;
    L194:
    return;
    L195:
    new Object[1][0] = p.calculatedAt;
    v5 = ui.Lang.of("fwm.market.calculated", new Object[1]);
    Objects.requireNonNull(font);
    v6 = Math.min(((this.y + this.height) - 9), ((this.listY() + ((v4 + 1) * 14)) + 2));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v5.getString(), this.width)), this.x, v6, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void figure(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int row, String labelKey, String value, int color) {
    v7 = (this.listY() + (row * 14));
    if ((v7 + 14) <= (this.y + this.height)) goto L32;
    return (row + 1);
    L32:
    Objects.requireNonNull(font);
    v8 = (v7 + ((14 - 9) / 2));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str(labelKey, new Object[0]), ((this.width - font.width(value)) - 8))), this.x, v8, ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(value), ((this.x + this.width) - font.width(value)), v8, color, 0);
    ui.FwmTheme.divider(ctx, this.x, ((v7 + 14) - 1), this.width);
    return (row + 1);
}

private void centered(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, network.chat.Component text) {
    ctx.text(font, text, (this.x + ((this.width - font.width(text)) / 2)), ((this.listY() + (((this.y + this.height) - this.listY()) / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void renderSeriesToggle(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String chosen, int mouseX, int mouseY) {
    v6 = this.x;
    v7 = this.listY();
    v8 = ui.panel.MarketItemPanel.SERIES;
    v9 = v8.length;
    v10 = 0;
    L25:
    if (v10 >= v9) goto L224;
    v11 = v8[v10];
    v12 = ui.Lang.str('fwm.market.series.' + v11, new Object[0]);
    v13 = (font.width(v12) + 8);
    v14 = v11.equals(chosen);
    if (v14 !=0) goto L117;
    if (mouseX < v6) goto L117;
    if (mouseX >= (v6 + v13)) goto L117;
    if (mouseY < v7) goto L117;
    if (mouseY >= (v7 + 10)) goto L117;
    goto L118;
    L117:
    L118:
    v15 = 0;
    if (v14 ==0) goto L140;
    goto L143;
    L140:
    L143:
    if (v14 !=0) goto L153;
    if (v15 ==0) goto L159;
    L153:
    goto L162;
    L159:
    L162:
    ui.FwmTheme.box(v7, v13, 10, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v14 ==0) goto L191;
    goto L205;
    L191:
    if (v15 ==0) goto L202;
    goto L205;
    L202:
    L205:
    network.chat.Component.literal(v12).text((v6 + 4), (v7 + 1), ui.FwmTheme.TEXT, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    v6 = (v6 + (v13 + 2));
    v10 = v10 + 1;
    goto L25;
    L224:
    return;
}

private int seriesToggleWidth(client.gui.Font font) {
    v2 = 0;
    v3 = ui.panel.MarketItemPanel.SERIES;
    v4 = v3.length;
    v5 = 0;
    L13:
    if (v5 >= v4) goto L58;
    v6 = v3[v5];
    v2 = (v2 + ((font.width(ui.Lang.str('fwm.market.series.' + v6, new Object[0])) + 8) + 2));
    v5 = v5 + 1;
    goto L13;
    L58:
    return (v2 - 2);
}

private int seriesAt(client.gui.Font font, double mouseX, double a3) {
    v6 = this.x;
    v7 = this.listY();
    if (cmp(v4,((d)v7)) <0) goto L33;
    if (cmp(v4,((d)(v7 + 10))) <0) goto L35;
    L33:
    return null;
    L35:
    v8 = ui.panel.MarketItemPanel.SERIES;
    v9 = v8.length;
    v10 = 0;
    L48:
    if (v10 >= v9) goto L122;
    v11 = v8[v10];
    v12 = (font.width(ui.Lang.str('fwm.market.series.' + v11, new Object[0])) + 8);
    if (cmp(mouseX,((d)v6)) <0) goto L107;
    if (cmp(mouseX,((d)(v6 + v12))) >=0) goto L107;
    return v11;
    L107:
    v6 = (v6 + (v12 + 2));
    v10 = v10 + 1;
    goto L48;
    L122:
    return null;
}

public int mouseDragged(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L12;
    if (this.markFrom >=0) goto L14;
    L12:
    return 0;
    L14:
    this.markTo = this.nearestDay(mouseX);
    return 1;
}

public void mouseReleased() {
    this.markFrom = m1;
    this.markTo = m1;
    return;
}

private boolean overChart(double mouseX, double a2) {
    if (this.chartX.length <= 1) goto L63;
    if (cmp(mouseX,((d)this.chartBoxX)) <0) goto L63;
    if (cmp(mouseX,((d)(this.chartBoxX + this.chartBoxW))) >=0) goto L63;
    if (cmp(v3,((d)this.chartBoxY)) <0) goto L63;
    if (cmp(v3,((d)(this.chartBoxY + this.chartBoxH))) >=0) goto L63;
    goto L64;
    L63:
    L64:
    return 0;
}

private double nearestDay(double mouseX) {
    v3 = m1;
    v4 = 1.7976931348623157e+308;
    v6 = 0;
    L10:
    if (v6 >= this.chartX.length) goto L55;
    v7 = Math.abs((this.chartX[v6] - mouseX));
    if (cmp(v7,v4) >=0) goto L49;
    v4 = v7;
    v3 = v6;
    L49:
    v6 = v6 + 1;
    goto L10;
    L55:
    return v3;
}

private void renderChart(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = net.Market.history(this.item.itemKey);
    if (v5 != null) goto L51;
    if (net.Market.historyFailed(this.item.itemKey) ==0) goto L38;
    goto L40;
    L38:
    L40:
    ctx.centered(font, "fwm.market.offline", ui.Lang.of("fwm.market.loading", new Object[0]));
    return;
    L51:
    if (this.series == null) goto L65;
    goto L88;
    L65:
    if ("orders".equals(v5.source) ==0) goto L85;
    goto L88;
    L85:
    L88:
    v6 = "ah";
    this.renderSeriesToggle(ctx, font, v6, mouseX, mouseY);
    v7 = ?;
    v8 = v5.list().iterator();
    L122:
    if (v8.hasNext() ==0) goto L270;
    v9 = ((net.MarketHistoryData$Point)v8.next());
    if ("orders".equals(v6) ==0) goto L163;
    goto L168;
    L163:
    L168:
    v10 = v9.ah;
    if (v9.ah != null) goto L210;
    if (v9.orders != null) goto L210;
    if (cmp(v9.p,0) <=0) goto L207;
    goto L208;
    L207:
    L208:
    v10 = null;
    L210:
    if (v10 == null) goto L122;
    if (cmp(v10.doubleValue(),0) >0) goto L228;
    goto L122;
    L228:
    v11 = ?;
    v11.d = v9.d;
    v11.p = v10.doubleValue();
    v7.add(v11);
    goto L122;
    L270:
    if (v7.size() >= 2) goto L329;
    if (v5.list().size() < 2) goto L329;
    new Object[1][0] = ui.Lang.str('fwm.market.series.' + v6, new Object[0]);
    this.wrapped(ctx, font, ui.Lang.of("fwm.market.chartNoSeries", new Object[1]));
    return;
    L329:
    if (v7.size() >= 2) goto L382;
    if (v5.since != null) goto L366;
    goto L371;
    L366:
    L371:
    0["2026-10-01"] = ui.panel.MarketItemPanel.shortDate(v5.since);
    ctx.wrapped(font, "fwm.market.chartEmpty", ui.Lang.of(new Object[1], new Object[1]));
    return;
    L382:
    v8 = this.x;
    v9 = (this.listY() + 12);
    v10 = this.width;
    v11 = Math.max(20, (((this.y + this.height) - 11) - v9));
    ui.FwmTheme.box(ctx, v8, v9, v10, v11, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    v12 = 1.7976931348623157e+308;
    v14 = -1.7976931348623157e+308;
    v16 = v7.iterator();
    L462:
    if (v16.hasNext() ==0) goto L511;
    v17 = ((net.MarketHistoryData$Point)v16.next());
    v12 = Math.min(v12, v17.p);
    v14 = Math.max(v14, v17.p);
    goto L462;
    L511:
    v16 = ((v14 - v12) * 0.12);
    if (cmp(v16,0) >0) goto L541;
    v16 = Math.max(1, (v14 * 0.05));
    L541:
    v18 = (v12 - v16);
    v20 = (v14 + v16);
    v22 = ui.panel.MarketItemPanel.epochDay(((net.MarketHistoryData$Point)v7.get(0)).d);
    v24 = Math.max(1, (ui.panel.MarketItemPanel.epochDay(((net.MarketHistoryData$Point)v7.get((v7.size() - 1))).d) - v22));
    v26 = (v8 + 3);
    v27 = (v10 - 6);
    v28 = (v9 + 3);
    v29 = (v11 - 6);
    v30 = new double[v7.size()];
    v31 = new double[v7.size()];
    v32 = 0;
    L659:
    if (v32 >= v7.size()) goto L753;
    v30[v32] = (((d)v26) + ((((d)(ui.panel.MarketItemPanel.epochDay(((net.MarketHistoryData$Point)v7.get(v32)).d) - v22)) * ((d)v27)) / ((d)v24)));
    v31[v32] = (((d)v28) + (((v20 - ((net.MarketHistoryData$Point)v7.get(v32)).p) * ((d)v29)) / (v20 - v18)));
    v32 = v32 + 1;
    goto L659;
    L753:
    this.chartX = v30;
    this.chartBoxX = v8;
    this.chartBoxY = v9;
    this.chartBoxW = v10;
    this.chartBoxH = v11;
    v32 = Math.min(this.markFrom, this.markTo);
    v33 = Math.max(this.markFrom, this.markTo);
    if (this.markFrom <0) goto L835;
    if (v32 >= v33) goto L835;
    if (v33 >= v30.length) goto L835;
    goto L836;
    L835:
    L836:
    v34 = 0;
    if (v34 ==0) goto L881;
    ctx.fill(((i)Math.round(v30[v32])), (v9 + 1), (((i)Math.round(v30[v33])) + 1), ((v9 + v11) - 1), 587202559);
    L881:
    ui.panel.MarketItemPanel.drawLine(ctx, v30, v31, v7, ((v9 + v11) - 1));
    ctx.text(font, network.chat.Component.literal(this.price(String.valueOf(v14))), (v26 + 1), v28, ui.FwmTheme.TEXT_DIM, 0);
    Objects.requireNonNull(font);
    ctx.text(font, network.chat.Component.literal(this.price(String.valueOf(v12))), (v26 + 1), ((v28 + v29) - 9), ui.FwmTheme.TEXT_DIM, 0);
    v35 = ((v9 + v11) + 2);
    v36 = ui.panel.MarketItemPanel.shortDate(((net.MarketHistoryData$Point)v7.get(0)).d);
    v37 = ui.panel.MarketItemPanel.shortDate(((net.MarketHistoryData$Point)v7.get((v7.size() - 1))).d);
    ctx.text(font, network.chat.Component.literal(v36), v8, v35, ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(v37), ((v8 + v10) - font.width(v37)), v35, ui.FwmTheme.TEXT_DIM, 0);
    v38 = m1;
    if (mouseX < v8) goto L1150;
    if (mouseX >= (v8 + v10)) goto L1150;
    if (mouseY < v9) goto L1150;
    if (mouseY >= (v9 + v11)) goto L1150;
    v39 = 1.7976931348623157e+308;
    v41 = 0;
    L1107:
    if (v41 >= v30.length) goto L1150;
    v42 = Math.abs((v30[v41] - ((d)mouseX)));
    if (cmp(v42,v39) >=0) goto L1144;
    v39 = v42;
    v38 = v41;
    L1144:
    v41 = v41 + 1;
    goto L1107;
    L1150:
    v39 = (this.listY() + 1);
    v40 = ((this.x + this.seriesToggleWidth(font)) + 6);
    if (v34 ==0) goto L1410;
    new int[2][0] = v32;
    new int[2][1] = v33;
    v41 = new int[2];
    v42 = v41.length;
    v43 = 0;
    L1201:
    if (v43 >= v42) goto L1256;
    v44 = v41[v43];
    v45 = ((i)Math.round(v30[v44]));
    ctx.fill(v45, (v9 + 1), (v45 + 1), ((v9 + v11) - 1), -1996488705);
    v43 = v43 + 1;
    goto L1201;
    L1256:
    v41 = network.chat.Component.literal(ui.panel.MarketItemPanel.shortDate(((net.MarketHistoryData$Point)v7.get(v32)).d) + ' - ' + ui.panel.MarketItemPanel.shortDate(((net.MarketHistoryData$Point)v7.get(v33)).d));
    ctx.text(font, v41, v40, v39, ui.FwmTheme.TEXT, 0);
    v42 = (((((net.MarketHistoryData$Point)v7.get(v33)).p / ((net.MarketHistoryData$Point)v7.get(v32)).p) - 1) * 100.0);
    v44 = ui.panel.MarketPanel.changeText((((d)Math.round((v42 * 10.0))) / 10.0));
    ctx.text(font, network.chat.Component.literal(v44), ((this.x + this.width) - font.width(v44)), v39, ui.panel.MarketPanel.changeColor(v42), 0);
    return;
    L1410:
    if (v38 <0) goto L1552;
    v41 = ((i)Math.round(v30[v38]));
    ctx.fill(v41, (v9 + 1), (v41 + 1), ((v9 + v11) - 1), 1442840575);
    v42 = ((i)Math.round(v31[v38]));
    ctx.fill((v41 - 1), (v42 - 1), (v41 + 2), (v42 + 2), ui.FwmTheme.TEXT);
    v43 = network.chat.Component.literal(ui.panel.MarketItemPanel.shortDate(((net.MarketHistoryData$Point)v7.get(v38)).d) + '  ' + this.price(String.valueOf(((net.MarketHistoryData$Point)v7.get(v38)).p)));
    ctx.text(font, v43, v40, v39, ui.FwmTheme.TEXT, 0);
    goto L1583;
    L1552:
    new Object[1][0] = Long.valueOf(v24);
    ctx.text(font, ui.Lang.of("fwm.market.chartDays", new Object[1]), v40, v39, ui.FwmTheme.TEXT_DIM, 0);
    L1583:
    v41 = (((((net.MarketHistoryData$Point)v7.get((v7.size() - 1))).p / ((net.MarketHistoryData$Point)v7.get(0)).p) - 1) * 100.0);
    v43 = ui.panel.MarketPanel.changeText((((d)Math.round((v41 * 10.0))) / 10.0));
    ctx.text(font, network.chat.Component.literal(v43), ((this.x + this.width) - font.width(v43)), v39, ui.panel.MarketPanel.changeColor(v41), 0);
    return;
}

private static org.joml.Matrix3x2fStack drawLine(client.gui.GuiGraphicsExtractor ctx, double[] px, double[] py, List points, int floorY) {
    v5 = Math.max(1, client.Minecraft.getInstance().getWindow().getGuiScale());
    v6 = Math.max(1, ((v5 / 2) + 1));
    v7 = (floorY * v5);
    v8 = (3 * v5);
    v9 = ctx.pose();
    v9.pushMatrix();
    v9.scale((1 / ((f)v5)), (1 / ((f)v5)));
    v10 = 0;
    L71:
    if ((v10 + 1) >= px.length) goto L455;
    if (cmp(((net.MarketHistoryData$Point)points.get((v10 + 1))).p,((net.MarketHistoryData$Point)points.get(v10)).p) <0) goto L118;
    goto L119;
    L118:
    L119:
    v11 = 0;
    if (v11 ==0) goto L132;
    goto L135;
    L132:
    L135:
    v12 = -1547164;
    v13 = ((v12 & 16777215) | 637534208);
    if (cmp((ui.panel.MarketItemPanel.epochDay(((net.MarketHistoryData$Point)points.get((v10 + 1))).d) - ui.panel.MarketItemPanel.epochDay(((net.MarketHistoryData$Point)points.get(v10)).d)),1) <=0) goto L195;
    goto L196;
    L195:
    L196:
    v14 = 0;
    v15 = ((i)Math.round((px[v10] * ((d)v5))));
    v16 = ((i)Math.round((px[(v10 + 1)] * ((d)v5))));
    v17 = (py[v10] * ((d)v5));
    v19 = (py[(v10 + 1)] * ((d)v5));
    v21 = Math.max(1, (v16 - v15));
    v22 = v15;
    L265:
    if (v22 >= Math.max((v15 + 1), v16)) goto L449;
    if (v14 ==0) goto L301;
    if ((((v22 - v15) / v8) % 2) != 1) goto L301;
    goto L443;
    L301:
    v23 = (v17 + (((v19 - v17) * ((d)(v22 - v15))) / ((d)v21)));
    v25 = (v17 + (((v19 - v17) * ((d)Math.min(v21, ((v22 - v15) + 1)))) / ((d)v21)));
    v27 = (((i)Math.floor(Math.min(v23, v25))) - (v6 / 2));
    v28 = (((i)Math.ceil(Math.max(v23, v25))) + ((v6 + 1) / 2));
    if (v14 !=0) goto L409;
    ctx.fill(v22, v28, (v22 + 1), v7, v13);
    L409:
    if (v14 ==0) goto L438;
    goto L440;
    L438:
    L440:
    v22.fill(v27, (v22 + 1), v28, ((v12 & 16777215) | -1342177280), v12);
    L443:
    v22 = v22 + 1;
    goto L265;
    L449:
    v10 = v10 + 1;
    goto L71;
    L455:
    v10 = Math.max(1, v5);
    v11 = 0;
    L466:
    if (v11 >= px.length) goto L658;
    if (v11 !=0) goto L482;
    goto L486;
    L482:
    L486:
    v12 = (v11 - 1);
    if (v11 !=0) goto L504;
    goto L506;
    L504:
    L506:
    v13 = v11;
    if (cmp(((net.MarketHistoryData$Point)points.get(v13)).p,((net.MarketHistoryData$Point)points.get(v12)).p) <0) goto L544;
    goto L545;
    L544:
    L545:
    v14 = 0;
    v15 = ((i)Math.round((px[v11] * ((d)v5))));
    v16 = ((i)Math.round((py[v11] * ((d)v5))));
    ctx.fill(((v15 - v10) - 1), ((v16 - v10) - 1), ((v15 + v10) + 2), ((v16 + v10) + 2), -871751916);
    if (v14 ==0) goto L646;
    goto L649;
    L646:
    L649:
    (v15 - v10).fill((v16 - v10), ((v15 + v10) + 1), ((v16 + v10) + 1), -12723068, -1547164);
    v11 = v11 + 1;
    goto L466;
    L658:
    v9.popMatrix();
    return;
}

private static long epochDay(String day) {
    return LocalDate.parse(day).toEpochDay();
    v1 = /*catch Exception*/ ex;
    return 0;
}

private static String shortDate(String day) {
    if (day == null) goto L13;
    if (day.length() >= 10) goto L16;
    L13:
    return "?";
    L16:
    return day.substring(8, 10) + '.' + day.substring(5, 7) + '.';
}

private void wrapped(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, network.chat.Component text) {
    v4 = font.split(text, (this.width - 8));
    v5 = (this.listY() + ((((this.y + this.height) - this.listY()) - (v4.size() * 10)) / 2));
    v6 = v4.iterator();
    L57:
    if (v6.hasNext() ==0) goto L116;
    v7 = ((util.FormattedCharSequence)v6.next());
    ctx.text(font, v7, (this.x + ((this.width - font.width(v7)) / 2)), v5, ui.FwmTheme.TEXT_DIM, 0);
    v5 = v5 + 10;
    goto L57;
    L116:
    return;
}

private String price(String raw) {
    v2 = net.Market.number(raw);
    if (Double.isNaN(v2) ==0) goto L16;
    return "-";
    L16:
    v4 = Math.round(v2);
    if (cmp(v4,1000000) >=0) goto L43;
    goto L53;
    L43:
    L53:
    return '$' + util.Fmt.compactUp(v4);
}

private String volume(String raw) {
    v2 = net.Market.number(raw);
    if (Double.isNaN(v2) ==0) goto L18;
    goto L30;
    L18:
    L30:
    return '$' + util.Fmt.compactUp(Math.round(v2));
}

private String amount(String raw) {
    v2 = net.Market.number(raw);
    if (Double.isNaN(v2) ==0) goto L18;
    goto L25;
    L18:
    L25:
    return util.Fmt.compact(Math.round(v2));
}

private String entries(net.MarketStatsData$Period p) {
    v2 = net.Market.rounded(p.ahEntryCount);
    v4 = net.Market.rounded(p.ordersEntryCount);
    return util.Fmt.compact(v2) + ' / ' + util.Fmt.compact(v4);
}

public int mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.active != ui.panel.MarketItemPanel.CHART) goto L63;
    v6 = this.seriesAt(client.Minecraft.getInstance().font, mouseX, mouseY);
    if (v6 == null) goto L63;
    this.series = v6;
    this.markFrom = m1;
    this.markTo = m1;
    return 1;
    L63:
    if (this.active != ui.panel.MarketItemPanel.CHART) goto L101;
    if (this.overChart(mouseX, mouseY) ==0) goto L101;
    this.markFrom = this.nearestDay(mouseX);
    this.markTo = this.markFrom;
    return 1;
    L101:
    if (cmp(mouseY,((d)this.stripY())) <0) goto L124;
    if (cmp(mouseY,((d)(this.stripY() + 14))) <0) goto L126;
    L124:
    return 0;
    L126:
    v6 = 0;
    L129:
    if (v6 >= ui.panel.MarketItemPanel.SLOTS) goto L180;
    if (cmp(mouseX,((d)this.slotX(v6))) <0) goto L174;
    if (cmp(mouseX,((d)(this.slotX(v6) + this.slotW()))) >=0) goto L174;
    this.active = v6;
    return 1;
    L174:
    v6 = v6 + 1;
    goto L129;
    L180:
    return 1;
}

static int <clinit>() {
    ui.panel.MarketItemPanel.PERIODS = List.of("1h", "24h", "3d", "7d", "31d");
    ui.panel.MarketItemPanel.CHART = ui.panel.MarketItemPanel.PERIODS.size();
    ui.panel.MarketItemPanel.SLOTS = (ui.panel.MarketItemPanel.PERIODS.size() + 1);
    new String[2][0] = "ah";
    new String[2][1] = "orders";
    ui.panel.MarketItemPanel.SERIES = new String[2];
    return;
}

