// class net/friendswithmoney/fwm/ui/panel/MoneyPanels$Week  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.money.week", new Object[0]);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = LocalDate.now();
    v7 = new long[7];
    v8 = 1;
    v10 = 0;
    v12 = 0;
    L20:
    if (v12 >= 7) goto L80;
    v7[v12] = money.MoneyStore.net(money.MoneyStore.day(v6.minusDays(((l)(6 - v12)))));
    v8 = Math.max(v8, Math.abs(v7[v12]));
    v10 = (v10 + v7[v12]);
    v12 = v12 + 1;
    goto L20;
    L80:
    v12 = (this.y + 6);
    v13 = Math.max(40, (this.height - 50));
    v14 = (v12 + (v13 / 2));
    v15 = ((this.width - 8) / 7);
    ctx.fill((this.x + 4), v14, ((this.x + this.width) - 4), (v14 + 1), ui.FwmTheme.OFF);
    if (ui.Lang.isGerman() ==0) goto L168;
    goto L171;
    L168:
    L171:
    v16 = format.DateTimeFormatter.ofPattern(Locale.GERMANY, Locale.ENGLISH);
    v17 = 0;
    L179:
    if (v17 >= 7) goto L504;
    v18 = (((this.x + 4) + (v17 * v15)) + 3);
    v19 = (v15 - 6);
    v20 = ((i)((Math.abs(v7[v17]) * ((l)((v13 / 2) - 2))) / v8));
    if (mouseX < v18) goto L267;
    if (mouseX >= (v18 + v19)) goto L267;
    if (mouseY < v12) goto L267;
    if (mouseY >= (v12 + v13)) goto L267;
    goto L268;
    L267:
    L268:
    v21 = 0;
    if (cmp(v7[v17],0) >=0) goto L285;
    goto L287;
    L285:
    L287:
    v22 = -9707377;
    if (v21 !=0) goto L304;
    v22 = ((v22 & 16777215) | -872415232);
    L304:
    if (cmp(v7[v17],0) <0) goto L337;
    ctx.fill(v18, (v14 - v20), (v18 + v19), v14, v22);
    goto L361;
    L337:
    ctx.fill(v18, (v14 + 1), (v18 + v19), ((v14 + 1) + v20), v22);
    L361:
    v23 = v6.minusDays(((l)(6 - v17))).format(v16);
    ctx.text(font, network.chat.Component.literal(v23), (v18 + ((v19 - font.width(v23)) / 2)), ((v12 + v13) + 3), ui.FwmTheme.TEXT_DIM, 0);
    if (v21 ==0) goto L498;
    v24 = ui.panel.MoneyPanels.signed(v7[v17]);
    ctx.text(font, network.chat.Component.literal(v24), Math.min((((this.x + this.width) - font.width(v24)) - 2), Math.max((this.x + 2), ((v18 + (v19 / 2)) - (font.width(v24) / 2)))), (v12 - 2), ui.panel.MoneyPanels.color(v7[v17]), 0);
    L498:
    v17 = v17 + 1;
    goto L179;
    L504:
    v17 = ui.Lang.t("Woche: ", "Week: ");
    ctx.text(font, network.chat.Component.literal(v17), (this.x + 4), ((this.y + this.height) - 12), ui.panel.MoneyPanels.color(v10), 0);
    ui.panel.MoneyPanels.amount(ctx, font, ui.panel.MoneyPanels.signed(v10), v10, ((this.x + 4) + font.width(v17)), ((this.y + this.height) - 12), ui.panel.MoneyPanels.color(v10), mouseX, mouseY);
    ui.panel.MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
    return;
}

