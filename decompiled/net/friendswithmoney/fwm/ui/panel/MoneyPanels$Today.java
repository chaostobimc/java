// class net/friendswithmoney/fwm/ui/panel/MoneyPanels$Today  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.money.today", new Object[0]);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = money.MoneyStore.day(LocalDate.now());
    v7 = 11;
    if (config.Features.isOn("fwm.tab.money") !=0) goto L52;
    ctx.text(font, network.chat.Component.literal(ui.Lang.t("Geld ist ausgeschaltet.", "Money is switched off.")), (this.x + 4), (this.y + 2), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L52:
    v8 = (this.x + (this.width / 2));
    v9 = (this.y + 2);
    ctx.text(font, network.chat.Component.literal(ui.Lang.t("Heute nach Quelle", "Today by source")), (this.x + 4), v9, ui.FwmTheme.TEXT, 0);
    v9 = (v9 + (v7 + 2));
    v10 = 0;
    v11 = money.MoneyStore$Kind.values();
    v12 = v11.length;
    v13 = 0;
    L125:
    if (v13 >= v12) goto L234;
    v14 = v11[v13];
    if (v14.ordinal() >= v6.length) goto L161;
    goto L162;
    L161:
    L162:
    v15 = 0;
    if (cmp(v15,0) !=0) goto L174;
    goto L228;
    L174:
    v10 = 1;
    if (v14.income ==0) goto L204;
    goto L207;
    L204:
    L207:
    ctx.row(font, (this.x + 4), ui.panel.MoneyPanels.kindName(v14), v15, (-v15), v9, (v8 - 8), 0, mouseX, mouseY);
    v9 = (v9 + v7);
    L228:
    v13 = v13 + 1;
    goto L125;
    L234:
    if (v10 !=0) goto L266;
    ctx.text(font, network.chat.Component.literal(ui.Lang.t("Heute noch nichts", "Nothing yet today")), (this.x + 4), v9, ui.FwmTheme.TEXT_DIM, 0);
    L266:
    ctx.fill((v8 - 2), (this.y + 2), (v8 - 1), (((this.y + 2) + (8 * v7)) + 2), ui.FwmTheme.OFF);
    v11 = (v8 + 4);
    v12 = ((this.x + this.width) - 4);
    v9 = (this.y + 2);
    this.row(ctx, font, v11, ui.Lang.t("Sitzung", "Session"), money.MoneyStore.user(), v9, v12, 1, mouseX, mouseY);
    v9 = (v9 + (v7 + 4));
    this.row(ctx, font, v11, ui.Lang.t("Einnahmen", "Income"), money.MoneyStore.income(v6), v9, v12, 0, mouseX, mouseY);
    v9 = (v9 + v7);
    this.row(ctx, font, v11, ui.Lang.t("Ausgaben", "Spent"), (-money.MoneyStore.expenses(v6)), v9, v12, 0, mouseX, mouseY);
    v9 = (v9 + v7);
    this.row(ctx, font, v11, ui.Lang.t("Saldo heute", "Today"), money.MoneyStore.net(v6), v9, v12, 1, mouseX, mouseY);
    v13 = (config.FwmConfig.INSTANCE.moneyGoal * 100);
    if (cmp(v13,0) <=0) goto L643;
    v9 = (v9 + (v7 + 6));
    v15 = money.MoneyStore.net(v6);
    v17 = (v12 - v11);
    v18 = ((i)Math.max(0, Math.min(((l)v17), ((v15 * ((l)v17)) / v13))));
    v19 = ui.Lang.t("Tagesziel ", "Daily goal ") + util.Fmt.compactMoney(v13) + '  ' + Math.max(0, Math.min(999, ((v15 * 100) / v13))) + ' %';
    ctx.text(font, network.chat.Component.literal(v19), v11, v9, ui.FwmTheme.TEXT_DIM, 0);
    ctx.fill(v11, (v9 + 10), (v11 + v17), (v9 + 15), 1442840575);
    if (cmp(v15,v13) <0) goto L637;
    goto L640;
    L637:
    L640:
    v11.fill((v9 + 10), (v11 + v18), (v9 + 15), -865972, ui.FwmTheme.ACCENT);
    L643:
    ui.panel.MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
    return;
}

private void row(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int lx, String label, long cents, int a6, int ty, boolean right, int bold, int mouseX) {
    if (bold ==0) goto L21;
    goto L24;
    L21:
    L24:
    font.text(network.chat.Component.literal(label), lx, ty, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    v12 = ui.panel.MoneyPanels.signed(cents);
    ui.panel.MoneyPanels.amount(ctx, font, v12, cents, (right - font.width(v12)), ty, ui.panel.MoneyPanels.color(cents), mouseX, v11);
    return;
}

