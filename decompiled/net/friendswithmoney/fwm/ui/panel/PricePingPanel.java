// class net/friendswithmoney/fwm/ui/panel/PricePingPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field FIELD_H I;
field BTN_H I;
field LINE_H I;
field MODE_H I;
field item Lnet/friendswithmoney/fwm/net/MarketData$Item;;
field name Ljava/lang/String;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field field Lnet/minecraft/client/gui/components/EditBox;;
field order Z;
field modeY I;
field modeW I;
field below Z;
field repeat Z;
field toast Z;
field chat Z;
field optY I;
field optW I;
field status Ljava/lang/String;;
field statusError Z;
field fieldY I;
field setX I;
field removeX I;
field buttonW I;
field listTop I;
public void <init>(net.MarketData$Item item, String name, ui.panel.PanelHost host) {
    this.item = item;
    this.name = name;
    this.host = host;
    if (net.PricePings.find(item.itemKey, 0) != null) goto L46;
    if (net.PricePings.find(item.itemKey, 1) == null) goto L46;
    goto L47;
    L46:
    L47:
    1.order = 0;
    this.loadOptions();
    return;
}

private boolean loadOptions() {
    v1 = net.PricePings.find(this.item.itemKey, this.order);
    if (v1 != null) goto L35;
    if (this.order !=0) goto L31;
    goto L39;
    L31:
    goto L39;
    L35:
    L39:
    0.below = v1.below();
    if (v1 == null) goto L58;
    if (v1.repeat ==0) goto L58;
    goto L59;
    L58:
    L59:
    1.repeat = 0;
    if (v1 == null) goto L74;
    if (v1.toastOn() ==0) goto L78;
    L74:
    goto L79;
    L78:
    L79:
    1.toast = 0;
    if (v1 == null) goto L98;
    if (v1.chat ==0) goto L98;
    goto L99;
    L98:
    L99:
    1.chat = 0;
    return;
}

private String explainKey() {
    if (this.order ==0) goto L12;
    goto L14;
    L12:
    L14:
    return "fwm.priceping.explain";
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.priceping.title", new Object[0]);
}

protected void onResize() {
    if (this.field != null) goto L14;
    goto L21;
    L14:
    L21:
    v1 = this.field.getValue();
    if (this.field == null) goto L42;
    this.host.detach(this.field);
    L42:
    v2 = client.Minecraft.getInstance().font;
    this.modeY = (this.y + 19);
    this.optY = ((this.modeY + 13) + 3);
    v3 = v2.split(ui.Lang.of(this.explainKey(), new Object[0]), this.width).size();
    this.fieldY = ((((this.optY + 13) + 4) + (v3 * 10)) + 4);
    this.field = ?;
    this.field.setBordered(0);
    this.field.setMaxLength(16);
    this.field.setTextColor(ui.FwmTheme.TEXT);
    this.field.setValue(v1);
    this.field.setFocused(1);
    this.host.attach(this.field);
    return;
}

private String initialText() {
    v1 = net.PricePings.find(this.item.itemKey, this.order);
    if (v1 == null) goto L30;
    return util.Fmt.compactUp(Math.round(v1.maxPrice));
    L30:
    v2 = net.Market.avgPrice(this.item.itemKey);
    if (v2 != null) goto L50;
    goto L60;
    L50:
    L60:
    return util.Fmt.compactUp(Math.round(v2.doubleValue()));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    ui.panel.MarketPanel.drawIcon(ctx, this.item, this.x, (this.y + 1));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(this.name, (this.width - 22))), (this.x + 20), (this.y + 5), ui.FwmTheme.TEXT, 0);
    this.modeW = ((this.width - 4) / 2);
    if (this.order !=0) goto L97;
    goto L98;
    L97:
    L98:
    ctx.modeChip(font, this.x, ui.Lang.str("fwm.priceping.modeAh", new Object[0]), 1, 0, mouseX, mouseY);
    this.modeChip(ctx, font, ((this.x + this.modeW) + 4), ui.Lang.str("fwm.orderping.mode", new Object[0]), this.order, mouseX, mouseY);
    this.optW = ((this.width - 12) / 4);
    if (this.below ==0) goto L171;
    goto L178;
    L171:
    L178:
    ctx.option(font, 0, ui.Lang.t("G\u00fcnstiger", "Cheaper"), ui.Lang.t("Teurer", "Dearer"), 1, mouseX, mouseY);
    if (this.repeat ==0) goto L207;
    goto L216;
    L207:
    L216:
    ctx.option(font, 1, ui.Lang.t("Mehrmals", "Every time"), ui.Lang.t("Einmal", "Once"), 1, mouseX, mouseY);
    this.option(ctx, font, 2, ui.Lang.t("Mitteilung", "Toast"), this.toast, mouseX, mouseY);
    this.option(ctx, font, 3, "Chat", this.chat, mouseX, mouseY);
    v6 = ((this.optY + 13) + 4);
    v7 = font.split(ui.Lang.of(this.explainKey(), new Object[0]), this.width).iterator();
    L300:
    if (v7.hasNext() ==0) goto L345;
    v8 = ((util.FormattedCharSequence)v7.next());
    ctx.text(font, v8, this.x, v6, ui.FwmTheme.TEXT_DIM, 0);
    v6 = v6 + 10;
    goto L300;
    L345:
    v7 = (this.width / 2);
    if (this.field.isFocused() ==0) goto L385;
    goto L388;
    L385:
    L388:
    ui.FwmTheme.box(this.x, this.fieldY, v7, 14, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.field.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    this.buttonW = (((this.width - v7) - 8) / 2);
    this.setX = ((this.x + v7) + 4);
    this.removeX = ((this.setX + this.buttonW) + 4);
    if (net.PricePings.find(this.item.itemKey, this.order) == null) goto L469;
    goto L470;
    L469:
    L470:
    v8 = 0;
    this.button(ctx, font, this.setX, this.fieldY, ui.Lang.str("fwm.priceping.set", new Object[0]), 1, mouseX, mouseY);
    this.button(ctx, font, this.removeX, this.fieldY, ui.Lang.str("fwm.priceping.remove", new Object[0]), v8, mouseX, mouseY);
    v9 = ((this.fieldY + 14) + 3);
    if (this.status == null) goto L597;
    new Object[1][0] = Integer.valueOf(10);
    if (this.statusError ==0) goto L587;
    goto L590;
    L587:
    L590:
    font.text(ui.Lang.of(this.status, new Object[1]), this.x, v9, -1547164, -12723068, 0);
    goto L631;
    L597:
    if (config.Features.isOn("fwm.feature.priceping") !=0) goto L631;
    ctx.text(font, ui.Lang.of("fwm.priceping.off", new Object[0]), this.x, v9, -1547164, 0);
    L631:
    this.renderList(ctx, font, ((v9 + 10) + 4), mouseX, mouseY);
    return;
}

private void modeChip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, String label, boolean on, int mouseX, int mouseY) {
    if (on !=0) goto L47;
    if (mouseX < bx) goto L47;
    if (mouseX >= (bx + this.modeW)) goto L47;
    if (mouseY < this.modeY) goto L47;
    if (mouseY >= (this.modeY + 13)) goto L47;
    goto L48;
    L47:
    L48:
    v8 = 0;
    if (on ==0) goto L73;
    goto L76;
    L73:
    L76:
    if (on !=0) goto L86;
    if (v8 ==0) goto L92;
    L86:
    goto L95;
    L92:
    L95:
    ui.FwmTheme.box(this.modeY, this.modeW, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v9 = network.chat.Component.literal(font.plainSubstrByWidth(label, (this.modeW - 4)));
    if (on ==0) goto L151;
    goto L165;
    L151:
    if (v8 ==0) goto L162;
    goto L165;
    L162:
    L165:
    v9.text((bx + ((this.modeW - font.width(v9)) / 2)), (this.modeY + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void option(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int i, String label, boolean on, int mouseX, int mouseY) {
    v8 = (this.x + (i * (this.optW + 4)));
    if (mouseX < v8) goto L59;
    if (mouseX >= (v8 + this.optW)) goto L59;
    if (mouseY < this.optY) goto L59;
    if (mouseY >= (this.optY + 13)) goto L59;
    goto L60;
    L59:
    L60:
    v9 = 0;
    if (on ==0) goto L86;
    goto L89;
    L86:
    L89:
    if (v9 ==0) goto L100;
    goto L103;
    L100:
    L103:
    ui.FwmTheme.box(this.optY, this.optW, 13, ui.FwmTheme.RAISED, ui.FwmTheme.LOCKED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = network.chat.Component.literal(font.plainSubstrByWidth(label, (this.optW - 4)));
    if (on ==0) goto L171;
    if (v9 ==0) goto L165;
    goto L174;
    L165:
    goto L174;
    L171:
    L174:
    v10.text((v8 + ((this.optW - font.width(v10)) / 2)), (this.optY + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, String label, boolean enabled, int mouseX, int mouseY) {
    if (enabled ==0) goto L43;
    if (mouseX < bx) goto L43;
    if (mouseX >= (bx + this.buttonW)) goto L43;
    if (mouseY < by) goto L43;
    if (mouseY >= (by + 14)) goto L43;
    goto L44;
    L43:
    L44:
    v9 = 0;
    if (enabled ==0) goto L67;
    goto L70;
    L67:
    L70:
    if (v9 ==0) goto L81;
    goto L84;
    L81:
    L84:
    ui.FwmTheme.box(by, this.buttonW, 14, ui.FwmTheme.RAISED, ui.FwmTheme.LOCKED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = network.chat.Component.literal(font.plainSubstrByWidth(label, (this.buttonW - 4)));
    if (enabled !=0) goto L138;
    goto L152;
    L138:
    if (v9 ==0) goto L149;
    goto L152;
    L149:
    L152:
    v10.text((bx + ((this.buttonW - font.width(v10)) / 2)), (by + 3), ui.FwmTheme.TEXT_DIM, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private void renderList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int top, int mouseX, int mouseY) {
    v6 = net.PricePings.list();
    this.listTop = ((top + 10) + 2);
    new Object[2][0] = Integer.valueOf(v6.size());
    new Object[2][1] = Integer.valueOf(10);
    ctx.text(font, ui.Lang.of("fwm.priceping.active", new Object[2]), this.x, top, ui.FwmTheme.ACCENT, 0);
    ui.FwmTheme.divider(ctx, this.x, (top + 10), this.width);
    v7 = 0;
    L79:
    if (v7 >= v6.size()) goto L433;
    v8 = (this.listTop + (v7 * 14));
    if ((v8 + 14) <= (this.y + this.height)) goto L123;
    goto L433;
    L123:
    v9 = ((config.FwmConfig$PricePing)v6.get(v7));
    if (v9.order ==0) goto L151;
    goto L154;
    L151:
    L154:
    v10 = "<= " + '$' + util.Fmt.compactUp(Math.round(v9.maxPrice));
    if (v9.order ==0) goto L186;
    goto L189;
    L186:
    L189:
    if (v9.name != null) goto L205;
    goto L210;
    L205:
    L210:
    v11 = v9.itemKey + v9.name;
    if (mouseX < ((this.x + this.width) - 12)) goto L269;
    if (mouseX >= (this.x + this.width)) goto L269;
    if (mouseY < v8) goto L269;
    if (mouseY >= (v8 + 14)) goto L269;
    goto L270;
    L269:
    L270:
    v12 = 0;
    if (v12 ==0) goto L307;
    goto L310;
    L307:
    L310:
    font.text(network.chat.Component.literal("x"), ((this.x + this.width) - 9), (v8 + 3), -1547164, ui.FwmTheme.TEXT_DIM, 0);
    v13 = (((this.x + this.width) - 16) - font.width(v10));
    ctx.text(font, network.chat.Component.literal(v10), v13, (v8 + 3), ui.FwmTheme.TEXT, 0);
    if (v9.itemKey.equals(this.item.itemKey) ==0) goto L420;
    if (v9.order != this.order) goto L420;
    goto L423;
    L420:
    L423:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(v11, ((v13 - this.x) - 6))), this.x, (v8 + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    v7 = v7 + 1;
    goto L79;
    L433:
    return;
}

private void apply() {
    v1 = net.PricePings.parsePrice(this.field.getValue());
    if (Double.isNaN(v1) ==0) goto L31;
    this.status = "fwm.priceping.bad";
    this.statusError = 1;
    return;
    L31:
    if (net.PricePings.set(this.item, this.name, v1, this.order, this.below, this.repeat, this.toast, this.chat) !=0) goto L79;
    this.status = "fwm.priceping.full";
    this.statusError = 1;
    return;
    L79:
    this.field.setValue(util.Fmt.compactUp(Math.round(v1)));
    this.status = "fwm.priceping.saved";
    this.statusError = 0;
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (cmp(mouseY,((d)this.modeY)) <0) goto L111;
    if (cmp(mouseY,((d)(this.modeY + 13))) >=0) goto L111;
    if (cmp(mouseX,((d)((this.x + this.modeW) + 4))) <0) goto L60;
    goto L61;
    L60:
    L61:
    v6 = 0;
    if (v6 == this.order) goto L109;
    this.order = v6;
    this.status = null;
    this.loadOptions();
    if (this.field == null) goto L105;
    this.field.setValue(this.initialText());
    L105:
    this.onResize();
    L109:
    return 1;
    L111:
    if (cmp(mouseY,((d)this.optY)) <0) goto L293;
    if (cmp(mouseY,((d)(this.optY + 13))) >=0) goto L293;
    if (this.optW <=0) goto L293;
    v6 = ((i)((mouseX - ((d)this.x)) / ((d)(this.optW + 4))));
    // switch (v6) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=107), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=31), Operand(op_type=<OperandTypes.BRANCH: 40>, value=50), Operand(op_type=<OperandTypes.BRANCH: 40>, value=69), Operand(op_type=<OperandTypes.BRANCH: 40>, value=88)]
    L192:
    if (this.below !=0) goto L204;
    goto L205;
    L204:
    L205:
    1.below = 0;
    goto L270;
    L211:
    if (this.repeat !=0) goto L223;
    goto L224;
    L223:
    L224:
    1.repeat = 0;
    goto L270;
    L230:
    if (this.toast !=0) goto L242;
    goto L243;
    L242:
    L243:
    1.toast = 0;
    goto L270;
    L249:
    if (this.chat !=0) goto L261;
    goto L262;
    L261:
    L262:
    1.chat = 0;
    goto L270;
    L268:
    return 1;
    L270:
    if (net.PricePings.find(this.item.itemKey, this.order) == null) goto L291;
    this.apply();
    L291:
    return 1;
    L293:
    if (cmp(mouseY,((d)this.fieldY)) <0) goto L320;
    if (cmp(mouseY,((d)(this.fieldY + 14))) >=0) goto L320;
    goto L321;
    L320:
    L321:
    v6 = 0;
    if (v6 ==0) goto L359;
    if (cmp(mouseX,((d)this.setX)) <0) goto L359;
    if (cmp(mouseX,((d)(this.setX + this.buttonW))) >=0) goto L359;
    this.apply();
    return 1;
    L359:
    if (v6 ==0) goto L434;
    if (cmp(mouseX,((d)this.removeX)) <0) goto L434;
    if (cmp(mouseX,((d)(this.removeX + this.buttonW))) >=0) goto L434;
    if (net.PricePings.find(this.item.itemKey, this.order) == null) goto L434;
    net.PricePings.remove(this.item.itemKey, this.order);
    this.status = "fwm.priceping.removed";
    this.statusError = 0;
    return 1;
    L434:
    if (v6 ==0) goto L466;
    if (cmp(mouseX,((d)(this.x + (this.width / 2)))) >=0) goto L466;
    this.field.setFocused(1);
    return 1;
    L466:
    if (cmp(mouseX,((d)((this.x + this.width) - 12))) <0) goto L568;
    if (cmp(mouseY,((d)this.listTop)) <0) goto L568;
    v7 = ((i)((mouseY - ((d)this.listTop)) / 14.0));
    v8 = net.PricePings.list();
    if (v7 <0) goto L568;
    if (v7 >= v8.size()) goto L568;
    net.PricePings.remove(((config.FwmConfig$PricePing)v8.get(v7)).itemKey, ((config.FwmConfig$PricePing)v8.get(v7)).order);
    this.status = null;
    L568:
    return 1;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.field == null) goto L17;
    if (this.field.isFocused() !=0) goto L19;
    L17:
    return 0;
    L19:
    if (keyCode == 40) goto L31;
    if (keyCode != 88) goto L37;
    L31:
    this.apply();
    return 1;
    L37:
    return this.field.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.field == null) goto L32;
    if (this.field.isFocused() ==0) goto L32;
    if (this.field.charTyped(input) ==0) goto L32;
    goto L33;
    L32:
    L33:
    return 0;
}

public void onHidden() {
    if (this.field == null) goto L20;
    this.host.detach(this.field);
    L20:
    return;
}

