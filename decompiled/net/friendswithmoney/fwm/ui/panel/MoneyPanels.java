// class net/friendswithmoney/fwm/ui/panel/MoneyPanels  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field tip Ljava/lang/String;;
private void <init>() {
    return;
}

static String kindName(money.MoneyStore$Kind kind) {
    // switch (ui.panel.MoneyPanels$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind[kind.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=56), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=10), Operand(op_type=<OperandTypes.BRANCH: 40>, value=66), Operand(op_type=<OperandTypes.BRANCH: 40>, value=71), Operand(op_type=<OperandTypes.BRANCH: 40>, value=81), Operand(op_type=<OperandTypes.BRANCH: 40>, value=91), Operand(op_type=<OperandTypes.BRANCH: 40>, value=101), Operand(op_type=<OperandTypes.BRANCH: 40>, value=111), Operand(op_type=<OperandTypes.BRANCH: 40>, value=121), Operand(op_type=<OperandTypes.BRANCH: 40>, value=131), Operand(op_type=<OperandTypes.BRANCH: 40>, value=141), Operand(op_type=<OperandTypes.BRANCH: 40>, value=151)]
    L64:
    throw ?;
    L74:
    goto L166;
    L79:
    goto L166;
    L89:
    goto L166;
    L99:
    goto L166;
    L109:
    goto L166;
    L119:
    goto L166;
    L129:
    goto L166;
    L139:
    goto L166;
    L149:
    goto L166;
    L159:
    L166:
    return ui.Lang.t("Geb\u00fchr", "Fee");
}

static String signed(long cents) {
    if (config.FwmConfig.INSTANCE.moneyHideAmounts ==0) goto L12;
    return "***";
    L12:
    if (cmp(cents,0) >=0) goto L23;
    goto L25;
    L23:
    L25:
    return "+" + util.Fmt.shortMoney(Math.abs(cents));
}

static String amount(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String text, long cents, int a4, int ax, int ay, int color, int mouseX) {
    ctx.text(font, network.chat.Component.literal(text), ax, ay, color, 0);
    if (config.FwmConfig.INSTANCE.moneyHideAmounts !=0) goto L92;
    if (mouseX < ax) goto L92;
    if (mouseX >= (ax + font.width(text))) goto L92;
    if (v9 < (ay - 1)) goto L92;
    if (v9 >= (ay + 9)) goto L92;
    if (cmp(cents,0) >=0) goto L75;
    goto L77;
    L75:
    L77:
    ui.panel.MoneyPanels.tip = "" + util.Fmt.fullMoney(Math.abs(cents));
    L92:
    return;
}

static void flushTip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    if (ui.panel.MoneyPanels.tip != null) goto L7;
    return;
    L7:
    ui.panel.ColorCodePanel.tip(ctx, font, ui.panel.MoneyPanels.tip, mouseX, mouseY);
    ui.panel.MoneyPanels.tip = null;
    return;
}

static int color(long cents) {
    if (cmp(cents,0) >=0) goto L11;
    goto L25;
    L11:
    if (cmp(cents,0) <=0) goto L22;
    goto L25;
    L22:
    L25:
    return ui.FwmTheme.TEXT_DIM;
}

