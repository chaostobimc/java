// class net/friendswithmoney/fwm/ui/panel/NewsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LINE_H I;
field GAP I;
field SCROLL_STEP I;
field kind Lnet/friendswithmoney/fwm/net/ModNews$Kind;;
field scroll I;
field contentH I;
public void <init>(net.ModNews$Kind kind) {
    this.kind = kind;
    return;
}

public network.chat.MutableComponent title() {
    // switch (ui.panel.NewsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind[this.kind.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=25), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=35), Operand(op_type=<OperandTypes.BRANCH: 40>, value=40), Operand(op_type=<OperandTypes.BRANCH: 40>, value=45)]
    L36:
    throw ?;
    L46:
    goto L58;
    L51:
    goto L58;
    L56:
    L58:
    return ui.Lang.of("fwm.news.fixes", new Object[0]);
}

public boolean showTitle() {
    return 0;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = net.ModNews.list(this.kind);
    if (v6.isEmpty() ==0) goto L51;
    ctx.text(font, ui.Lang.of("fwm.news.empty", new Object[0]), (this.x + 4), (this.y + 6), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L51:
    v7 = Math.max(0, (this.contentH - this.height));
    this.scroll = Math.max(0, Math.min(this.scroll, v7));
    v8 = ?;
    v9 = (this.width - 14);
    v10 = (this.y - this.scroll);
    ctx.enableScissor(this.x, this.y, (this.x + this.width), (this.y + this.height));
    v11 = v6.iterator();
    L153:
    if (v11.hasNext() ==0) goto L510;
    v12 = ((net.ModNews$Entry)v11.next());
    if (v12.text != null) goto L189;
    goto L194;
    L189:
    L194:
    v13 = "".split(network.chat.Component.literal(v12.text), (v9 - 8));
    v14 = ((18 + (v13.size() * 10)) + 4);
    if ((v10 + v14) <= this.y) goto L497;
    if (v10 >= (this.y + this.height)) goto L497;
    ui.FwmTheme.box(ctx, this.x, v10, (v9 + 4), v14, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    ctx.fill(this.x, v10, (this.x + 2), (v10 + v14), ui.FwmTheme.ACCENT);
    if (cmp(v12.createdAt,0) <=0) goto L326;
    goto L328;
    L326:
    L328:
    v15 = "";
    v16 = font.width(v15);
    if (v12.title != null) goto L351;
    goto L356;
    L351:
    L356:
    v17 = v12.title;
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v17, ((v9 - v16) - 14))).withStyle(ChatFormatting.BOLD), (this.x + 7), (v10 + 6), ui.FwmTheme.TEXT, 0);
    ctx.text(font, v15, ((this.x + v9) - v16), (v10 + 6), ui.FwmTheme.TEXT_DIM, 0);
    v18 = (((v10 + 6) + 10) + 2);
    v19 = v13.iterator();
    L449:
    if (v19.hasNext() ==0) goto L497;
    v20 = ((util.FormattedCharSequence)v19.next());
    ctx.text(font, v20, (this.x + 7), v18, ui.FwmTheme.TEXT_DIM, 0);
    v18 = v18 + 10;
    goto L449;
    L497:
    v10 = (v10 + (v14 + 8));
    goto L153;
    L510:
    ctx.disableScissor();
    this.contentH = (((v10 + this.scroll) - this.y) - 8);
    if (this.contentH <= this.height) goto L675;
    v11 = Math.max(12, ((this.height * this.height) / this.contentH));
    v12 = (this.y + (((this.height - v11) * this.scroll) / Math.max(1, (this.contentH - this.height))));
    ctx.fill(((this.x + this.width) - 3), this.y, ((this.x + this.width) - 1), (this.y + this.height), 587202559);
    ctx.fill(((this.x + this.width) - 3), v12, ((this.x + this.width) - 1), (v12 + v11), ui.FwmTheme.ACCENT);
    L675:
    return;
}

public double mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    this.scroll = (this.scroll - (((i)Math.signum(v5)) * 20));
    return 1;
}

