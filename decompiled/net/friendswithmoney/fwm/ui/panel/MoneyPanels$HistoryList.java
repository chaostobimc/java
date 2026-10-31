// class net/friendswithmoney/fwm/ui/panel/MoneyPanels$HistoryList  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field CHIP_H I;
field FILTERS [Ljava/lang/String;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field filter I;
field scroll I;
field search Lnet/minecraft/client/gui/components/EditBox;;
field shown Ljava/util/List;;
public void <init>(ui.panel.PanelHost host) {
    this.shown = ?;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.money.history", new Object[0]);
}

protected void onResize() {
    v1 = client.Minecraft.getInstance().font;
    if (this.search != null) goto L19;
    goto L26;
    L19:
    L26:
    v2 = this.search.getValue();
    if (this.search == null) goto L47;
    this.host.detach(this.search);
    L47:
    v3 = Math.max(60, (this.width / 3));
    this.search = ?;
    this.search.setHint(network.chat.Component.literal(ui.Lang.t("Spieler / Item \u2026", "Player / item \u2026")));
    this.search.setMaxLength(32);
    this.search.setValue(v2);
    this.host.attach(this.search);
    return;
}

public void onHidden() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    this.search = null;
    return;
}

// DECOMPILE ERROR in wanted: 'dict' object has no attribute 'op_type'
private int listTop() {
    return ((this.y + 13) + 4);
}

private int listH() {
    return ((this.height - 17) - 14);
}

private int rowsVisible() {
    return Math.max(1, (this.listH() / 12));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.shown = ?;
    v6 = money.History.newestFirst().iterator();
    L21:
    if (v6.hasNext() ==0) goto L67;
    v7 = ((money.History$Entry)v6.next());
    if (this.wanted(v7) ==0) goto L64;
    this.shown.add(v7);
    L64:
    goto L21;
    L67:
    v6 = Math.max(0, (this.shown.size() - this.rowsVisible()));
    this.scroll = Math.min(this.scroll, v6);
    v7 = this.x;
    new String[4][0] = ui.Lang.t("Alle", "All");
    new String[4][1] = "Sell";
    new String[4][2] = "/pay";
    new String[4][3] = "AH";
    v8 = new String[4];
    v9 = 0;
    L140:
    if (v9 >= v8.length) goto L317;
    v10 = (font.width(v8[v9]) + 10);
    if (mouseX < v7) goto L202;
    if (mouseX >= (v7 + v10)) goto L202;
    if (mouseY < this.y) goto L202;
    if (mouseY >= (this.y + 13)) goto L202;
    goto L203;
    L202:
    L203:
    v11 = 0;
    if (v9 != this.filter) goto L231;
    goto L234;
    L231:
    L234:
    if (v9 == this.filter) goto L248;
    if (v11 ==0) goto L254;
    L248:
    goto L257;
    L254:
    L257:
    ui.FwmTheme.box(this.y, v10, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v9 != this.filter) goto L295;
    goto L298;
    L295:
    L298:
    font.text(network.chat.Component.literal(v8[v9]), (v7 + 5), (this.y + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    v7 = (v7 + (v10 + 3));
    v9 = v9 + 1;
    goto L140;
    L317:
    if (this.search == null) goto L337;
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    L337:
    v9 = null;
    v10 = format.DateTimeFormatter.ofPattern("dd.MM. HH:mm");
    v11 = this.listTop();
    ctx.enableScissor(this.x, v11, (this.x + this.width), (v11 + this.listH()));
    v12 = 0;
    L383:
    if (v12 >= this.rowsVisible()) goto L818;
    v13 = (this.scroll + v12);
    if (v13 < this.shown.size()) goto L418;
    goto L818;
    L418:
    v14 = ((money.History$Entry)this.shown.get(v13));
    v15 = (v11 + (v12 * 12));
    if (mouseX < this.x) goto L486;
    if (mouseX >= (this.x + this.width)) goto L486;
    if (mouseY < v15) goto L486;
    if (mouseY >= (v15 + 12)) goto L486;
    goto L487;
    L486:
    L487:
    v16 = 0;
    if (v16 ==0) goto L510;
    ui.FwmTheme.hover(ctx, this.x, v15, this.width, 12);
    L510:
    v17 = Instant.ofEpochMilli(v14.t).atZone(ZoneId.systemDefault()).format(v10);
    if (v14.n <= 1) goto L561;
    goto L563;
    L561:
    L563:
    v18 = ' ×' + v14.n + "";
    if (v14.p == null) goto L586;
    goto L604;
    L586:
    if (v14.i == null) goto L602;
    goto L604;
    L602:
    L604:
    v19 = "";
    v20 = ui.panel.MoneyPanels.signed(v14.c);
    ctx.text(font, network.chat.Component.literal(v17), (this.x + 2), (v15 + 2), ui.FwmTheme.TEXT_DIM, 0);
    v21 = (((this.x + 2) + font.width("00.00. 00:00")) + 6);
    ctx.text(font, network.chat.Component.literal(v18), v21, (v15 + 2), ui.FwmTheme.TEXT, 0);
    v22 = ((v21 + font.width("Sonstiges + ")) + 4);
    v23 = ((((this.x + this.width) - font.width(v20)) - 6) - v22);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v19, Math.max(0, v23))), v22, (v15 + 2), ui.FwmTheme.TEXT_DIM, 0);
    ui.panel.MoneyPanels.amount(ctx, font, v20, v14.c, (((this.x + this.width) - font.width(v20)) - 2), (v15 + 2), ui.panel.MoneyPanels.color(v14.c), mouseX, mouseY);
    if (v16 ==0) goto L812;
    if (v14.l == null) goto L812;
    v9 = v14.l;
    L812:
    v12 = v12 + 1;
    goto L383;
    L818:
    ctx.disableScissor();
    if (this.shown.isEmpty() ==0) goto L882;
    v12 = network.chat.Component.literal(ui.Lang.t("Noch keine Eintr\u00e4ge.", "No entries yet."));
    ctx.text(font, v12, (this.x + ((this.width - font.width(v12)) / 2)), (v11 + 20), ui.FwmTheme.TEXT_DIM, 0);
    L882:
    v12 = 0;
    v14 = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
    v16 = this.shown.iterator();
    L913:
    if (v16.hasNext() ==0) goto L959;
    v17 = ((money.History$Entry)v16.next());
    if (cmp(v17.t,v14) <0) goto L956;
    v12 = (v12 + v17.c);
    L956:
    goto L913;
    L959:
    if (v9 == null) goto L1009;
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v9, (this.width - 4))), (this.x + 2), ((this.y + this.height) - 10), ui.FwmTheme.TEXT_DIM, 0);
    goto L1099;
    L1009:
    v16 = ui.Lang.t("Heute (gefiltert): ", "Today (filtered): ");
    ctx.text(font, network.chat.Component.literal(v16), (this.x + 2), ((this.y + this.height) - 10), ui.panel.MoneyPanels.color(v12), 0);
    ui.panel.MoneyPanels.amount(ctx, font, ui.panel.MoneyPanels.signed(v12), v12, ((this.x + 2) + font.width(v16)), ((this.y + this.height) - 10), ui.panel.MoneyPanels.color(v12), mouseX, mouseY);
    L1099:
    ui.panel.MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    v6 = client.Minecraft.getInstance().font;
    if (this.search == null) goto L48;
    if (this.search.isMouseOver(mouseX, mouseY) ==0) goto L48;
    this.search.setFocused(1);
    return 1;
    L48:
    if (this.search == null) goto L63;
    this.search.setFocused(0);
    L63:
    if (cmp(mouseY,((d)this.y)) <0) goto L198;
    if (cmp(mouseY,((d)(this.y + 13))) >=0) goto L198;
    v7 = this.x;
    new String[4][0] = ui.Lang.t("Alle", "All");
    new String[4][1] = "Sell";
    new String[4][2] = "/pay";
    new String[4][3] = "AH";
    v8 = new String[4];
    v9 = 0;
    L126:
    if (v9 >= v8.length) goto L196;
    v10 = (v6.width(v8[v9]) + 10);
    if (cmp(mouseX,((d)v7)) <0) goto L181;
    if (cmp(mouseX,((d)(v7 + v10))) >=0) goto L181;
    this.filter = v9;
    this.scroll = 0;
    return 1;
    L181:
    v7 = (v7 + (v10 + 3));
    v9 = v9 + 1;
    goto L126;
    L196:
    return 1;
    L198:
    v7 = this.listTop();
    v8 = ((i)((mouseY - ((d)v7)) / 12.0));
    if (v5 !=0) goto L325;
    if (cmp(mouseY,((d)v7)) <0) goto L325;
    if (v8 <0) goto L325;
    if (v8 >= this.rowsVisible()) goto L325;
    v9 = (this.scroll + v8);
    if (v9 >= this.shown.size()) goto L325;
    if (((money.History$Entry)this.shown.get(v9)).p == null) goto L325;
    client.Minecraft.getInstance().gui.setScreen(?);
    L325:
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    v7 = Math.max(0, (this.shown.size() - this.rowsVisible()));
    this.scroll = Math.max(0, Math.min(v7, (this.scroll - (((i)Math.signum(v5)) * 2))));
    return 1;
}

public boolean isCapturingKeys() {
    if (this.search == null) goto L21;
    if (this.search.isFocused() ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.search == null) goto L17;
    if (this.search.isFocused() !=0) goto L19;
    L17:
    return 0;
    L19:
    if (keyCode == 41) goto L31;
    if (keyCode != 40) goto L41;
    L31:
    this.search.setFocused(0);
    return 1;
    L41:
    this.scroll = 0;
    return this.search.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.search == null) goto L17;
    if (this.search.isFocused() !=0) goto L19;
    L17:
    return 0;
    L19:
    this.scroll = 0;
    return this.search.charTyped(input);
}

static void <clinit>() {
    new String[4][0] = "all";
    new String[4][1] = "sell";
    new String[4][2] = "pay";
    new String[4][3] = "ah";
    ui.panel.MoneyPanels$HistoryList.FILTERS = new String[4];
    return;
}

