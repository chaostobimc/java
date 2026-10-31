// class net/friendswithmoney/fwm/ui/panel/PingListPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field DEL_W I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field scroll I;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.kommt.pings", new Object[0]);
}

private int rows() {
    return Math.max(1, ((this.height - 32) / 16));
}

private int addY() {
    return ((this.y + this.height) - 30);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = net.PricePings.list();
    this.scroll = Math.max(0, Math.min(this.scroll, Math.max(0, (v6.size() - this.rows()))));
    v7 = 0;
    L39:
    if (v7 >= this.rows()) goto L512;
    if ((this.scroll + v7) >= v6.size()) goto L512;
    v8 = ((config.FwmConfig$PricePing)v6.get((this.scroll + v7)));
    v9 = (this.y + (v7 * 16));
    if (mouseX < this.x) goto L138;
    if (mouseX >= (this.x + this.width)) goto L138;
    if (mouseY < v9) goto L138;
    if (mouseY >= (v9 + 16)) goto L138;
    goto L139;
    L138:
    L139:
    v10 = 0;
    if (v10 ==0) goto L162;
    ui.FwmTheme.hover(ctx, this.x, v9, this.width, 16);
    L162:
    if (v8.order ==0) goto L180;
    goto L182;
    L180:
    L182:
    v11 = "AH";
    if (v8.fired ==0) goto L202;
    goto L248;
    L202:
    if (v8.below() ==0) goto L215;
    goto L217;
    L215:
    L217:
    if (v8.repeat ==0) goto L241;
    goto L243;
    L241:
    L243:
    L248:
    v12 = util.Fmt.compactUp(Math.round(v8.maxPrice)) + " \u21bb" + "";
    v13 = (v9 + 4);
    if (v8.order ==0) goto L284;
    goto L287;
    L284:
    L287:
    font.text(network.chat.Component.literal(v11), (this.x + 3), v13, -865972, ui.FwmTheme.ACCENT, 0);
    v14 = (((this.x + 3) + font.width("Order")) + 6);
    v15 = font.width(v12);
    if (v8.name == null) goto L336;
    if (v8.name.isBlank() ==0) goto L344;
    L336:
    goto L349;
    L344:
    L349:
    v16 = v8.name;
    if (v16 != null) goto L364;
    goto L366;
    L364:
    L366:
    font.text(font, network.chat.Component.literal("?".plainSubstrByWidth(v16, ((((this.width - (v14 - this.x)) - v15) - 12) - 10))), v14, v13, ui.FwmTheme.TEXT, 0);
    ctx.text(font, network.chat.Component.literal(v12), ((((this.x + this.width) - 12) - 6) - v15), v13, ui.FwmTheme.TEXT, 0);
    if (v10 ==0) goto L463;
    if (mouseX < ((this.x + this.width) - 12)) goto L463;
    goto L464;
    L463:
    L464:
    v17 = 0;
    if (v17 ==0) goto L499;
    goto L502;
    L499:
    L502:
    font.text(network.chat.Component.literal("\u00d7"), (((this.x + this.width) - 12) + 3), v13, -2067340, ui.FwmTheme.TEXT_DIM, 0);
    v7 = v7 + 1;
    goto L39;
    L512:
    if (v6.isEmpty() ==0) goto L576;
    v7 = ui.Lang.of("fwm.kommt.noPings", new Object[0]);
    ctx.text(font, v7, (this.x + ((this.width - font.width(v7)) / 2)), ((this.y + (this.height / 2)) - 10), ui.FwmTheme.TEXT_DIM, 0);
    L576:
    v7 = ui.Lang.t("+ Neuer Preis-Ping", "+ New price ping");
    v8 = (font.width(v7) + 16);
    if (mouseX < this.x) goto L640;
    if (mouseX >= (this.x + v8)) goto L640;
    if (mouseY < this.addY()) goto L640;
    if (mouseY >= (this.addY() + 16)) goto L640;
    goto L641;
    L640:
    L641:
    v9 = 0;
    if (v9 ==0) goto L670;
    goto L673;
    L670:
    L673:
    ui.FwmTheme.box(this.x, this.addY(), v8, 16, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v9 ==0) goto L707;
    goto L710;
    L707:
    L710:
    font.text(network.chat.Component.literal(v7), (this.x + 8), (this.addY() + 4), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    v10 = ui.Lang.of("fwm.kommt.pingHint", new Object[0]);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v10.getString(), this.width)), this.x, ((this.y + this.height) - 9), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (cmp(mouseY,((d)this.addY())) <0) goto L76;
    if (cmp(mouseY,((d)(this.addY() + 16))) >=0) goto L76;
    if (cmp(mouseX,((d)(this.x + 140))) >=0) goto L76;
    this.host.openSub(?);
    return 1;
    L76:
    v6 = ?;
    v7 = ((i)((mouseY - ((d)this.y)) / 16.0));
    if (v7 <0) goto L133;
    if (v7 >= this.rows()) goto L133;
    if ((this.scroll + v7) < v6.size()) goto L135;
    L133:
    return 1;
    L135:
    v8 = ((config.FwmConfig$PricePing)v6.get((this.scroll + v7)));
    if (cmp(mouseX,((d)((this.x + this.width) - 12))) <0) goto L187;
    net.PricePings.remove(v8.itemKey, v8.order);
    return 1;
    L187:
    v9 = ?;
    v9.itemKey = v8.itemKey;
    v9.baseItemKey = v8.baseItemKey;
    v9.icon = v8.icon;
    if (v8.name != null) goto L252;
    goto L257;
    L252:
    L257:
    ?.openSub(?);
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, (this.scroll - ((i)Math.signum(v5))));
    return 1;
}

