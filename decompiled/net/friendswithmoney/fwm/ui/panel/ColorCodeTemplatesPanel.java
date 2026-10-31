// class net/friendswithmoney/fwm/ui/panel/ColorCodeTemplatesPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field BTN_H I;
field LIST_Y I;
field ROW_H I;
field SCROLLBAR_W I;
field DELETE_W I;
field TAG_W I;
field NOTE_MS J;
field user Lnet/friendswithmoney/fwm/ui/panel/ColorCodePanel$User;;
field confirm Lnet/friendswithmoney/fwm/ui/ConfirmBox;;
field scroll I;
field note Lnet/friendswithmoney/fwm/colorcode/Templates$Added;;
field noteAt J;
public void <init>(ui.panel.ColorCodePanel$User user) {
    this.confirm = ?;
    this.user = user;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.colorcodes.templates", new Object[0]);
}

private List templates() {
    return colorcode.Templates.all();
}

private int rowsVisible() {
    return Math.max(1, ((this.height - 18) / 16));
}

private int maxScroll() {
    return Math.max(0, (this.templates().size() - this.rowsVisible()));
}

private Object at(int index) {
    return ((colorcode.Templates$Template)this.templates().get(((this.templates().size() - 1) - index)));
}

protected int onResize() {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), this.scroll));
    return;
}

private int saveW(client.gui.Font font) {
    return (font.width(ui.Lang.of("fwm.colorcodes.save", new Object[0])) + 12);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.saveW(font);
    if (this.confirm.open() !=0) goto L44;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), this.x, this.y, v6, 13) ==0) goto L44;
    goto L45;
    L44:
    L45:
    v7 = 0;
    ui.panel.ColorCodePanel.button(ctx, font, this.x, this.y, v6, 13, ui.Lang.of("fwm.colorcodes.save", new Object[0]), v7, 0, 1);
    new Object[2][0] = Integer.valueOf(this.templates().size());
    new Object[2][1] = Integer.valueOf(50);
    v8 = ui.Lang.str("fwm.colorcodes.count", new Object[2]);
    ctx.text(font, v8, ((this.x + this.width) - font.width(v8)), (this.y + 3), ui.FwmTheme.TEXT_DIM, 0);
    if (this.note == null) goto L341;
    if (cmp((System.currentTimeMillis() - this.noteAt),2000) >=0) goto L341;
    // switch (ui.panel.ColorCodeTemplatesPanel$1.$SwitchMap$net$friendswithmoney$fwm$colorcode$Templates$Added[this.note.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=31), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=4), Operand(op_type=<OperandTypes.BRANCH: 40>, value=41), Operand(op_type=<OperandTypes.BRANCH: 40>, value=53), Operand(op_type=<OperandTypes.BRANCH: 40>, value=65), Operand(op_type=<OperandTypes.BRANCH: 40>, value=85)]
    L208:
    throw ?;
    L218:
    goto L271;
    L230:
    goto L271;
    L242:
    new Object[1][0] = Integer.valueOf(50);
    goto L271;
    L262:
    L271:
    v9 = ui.Lang.of("fwm.colorcodes.empty", new Object[0]);
    v10 = (((this.width - v6) - 12) - font.width(v8));
    if (this.note != colorcode.Templates$Added.OK) goto L334;
    goto L337;
    L334:
    L337:
    font.text(ui.panel.ColorCodePanel.trimmed(font, v9, v10), ((this.x + v6) + 6), (this.y + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.DISABLED_TEXT, 0);
    L341:
    ui.FwmTheme.divider(ctx, this.x, ((this.y + 18) - 3), this.width);
    if (this.templates().isEmpty() ==0) goto L436;
    v9 = ui.Lang.of("fwm.colorcodes.none", new Object[0]);
    ctx.text(font, v9, (this.x + ((this.width - font.width(v9)) / 2)), (((this.y + 18) + ((this.height - 18) / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    goto L541;
    L436:
    v9 = this.rowWidth();
    if (this.confirm.open() ==0) goto L458;
    goto L460;
    L458:
    L460:
    v10 = mouseY;
    v11 = 0;
    L465:
    if (v11 >= this.rowsVisible()) goto L536;
    v12 = (this.scroll + v11);
    if (v12 < this.templates().size()) goto L500;
    goto L536;
    L500:
    this.renderRow(ctx, font, this.at(v12), ((this.y + 18) + (v11 * 16)), v9, mouseX, v10);
    v11 = v11 + 1;
    goto L465;
    L536:
    this.renderScrollbar(ctx);
    L541:
    this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    return;
}

private int rowWidth() {
    if (this.maxScroll() <=0) goto L16;
    goto L17;
    L16:
    L17:
    return (6 - 0);
}

private void renderRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, colorcode.Templates$Template template, int rowY, int rowW, int mouseX, int mouseY) {
    if (mouseX < this.x) goto L42;
    if (mouseX >= (this.x + rowW)) goto L42;
    if (mouseY < rowY) goto L42;
    if (mouseY >= (rowY + 16)) goto L42;
    goto L43;
    L42:
    L43:
    v8 = 0;
    if (v8 ==0) goto L64;
    ui.FwmTheme.hover(ctx, this.x, rowY, rowW, 16);
    L64:
    Objects.requireNonNull(font);
    v9 = ((rowY + ((16 - 9) / 2)) + 1);
    if (template.mode() != colorcode.ColorCode$Mode.SIGN) goto L98;
    goto L100;
    L98:
    L100:
    v10 = "R";
    ctx.text(font, v10, (this.x + 2), v9, ui.FwmTheme.TEXT_DIM, 0);
    v11 = ((this.x + rowW) - 12);
    if (v8 ==0) goto L149;
    if (mouseX < v11) goto L149;
    goto L150;
    L149:
    L150:
    v12 = 0;
    if (v12 ==0) goto L173;
    goto L176;
    L173:
    L176:
    ctx.cross((v11 + 3), (rowY + 5), ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.TEXT_DIM);
    v13 = String.valueOf(template.code.length());
    v14 = ((v11 - 4) - font.width(v13));
    if (template.code.length() <= template.mode().limit) goto L225;
    goto L226;
    L225:
    L226:
    v15 = 0;
    if (v15 ==0) goto L247;
    goto L250;
    L247:
    L250:
    font.text(v13, v14, v9, -43691, ui.FwmTheme.TEXT_DIM, 0);
    v16 = ui.panel.ColorCodePanel.text(colorcode.ColorCode.parse(template.code));
    ctx.text(font, ui.panel.ColorCodePanel.trimmed(font, v16, ((v14 - 6) - (this.x + 12))), (this.x + 12), v9, m1, 1);
    return;
}

private void cross(client.gui.GuiGraphicsExtractor ctx, int cx, int cy, int color) {
    v5 = 0;
    L3:
    if (v5 >= 5) goto L69;
    ctx.fill((cx + v5), (cy + v5), ((cx + v5) + 1), ((cy + v5) + 1), color);
    ctx.fill(((cx + 4) - v5), (cy + v5), ((cx + 5) - v5), ((cy + v5) + 1), color);
    v5 = v5 + 1;
    goto L3;
    L69:
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    if (this.maxScroll() >0) goto L8;
    return;
    L8:
    v2 = ((this.x + this.width) - 4);
    v3 = (this.y + 18);
    v4 = (this.rowsVisible() * 16);
    ctx.fill(v2, v3, (v2 + 4), (v3 + v4), ui.FwmTheme.OFF);
    v5 = Math.max(12, ((v4 * this.rowsVisible()) / Math.max(1, this.templates().size())));
    v6 = (v3 + (((v4 - v5) * this.scroll) / this.maxScroll()));
    ctx.fill(v2, v6, (v2 + 4), (v6 + v5), ui.FwmTheme.ACCENT);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.confirm.open() ==0) goto L20;
    return this.confirm.mouseClicked(mouseX, mouseY);
    L20:
    if (v5 !=0) goto L34;
    if (this.contains(mouseX, mouseY) !=0) goto L36;
    L34:
    return 0;
    L36:
    v6 = client.Minecraft.getInstance().font;
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, this.x, this.y, this.saveW(v6), 13) ==0) goto L103;
    this.note = colorcode.Templates.add(this.user.code, this.user.mode);
    this.noteAt = System.currentTimeMillis();
    this.scroll = 0;
    return 1;
    L103:
    v7 = this.rowWidth();
    v8 = 0;
    L112:
    if (v8 >= this.rowsVisible()) goto L337;
    v9 = (this.scroll + v8);
    if (v9 < this.templates().size()) goto L147;
    goto L337;
    L147:
    v10 = ((this.y + 18) + (v8 * 16));
    if (cmp(mouseY,((d)v10)) <0) goto L331;
    if (cmp(mouseY,((d)(v10 + 16))) >=0) goto L331;
    if (cmp(mouseX,((d)(this.x + v7))) <0) goto L197;
    goto L331;
    L197:
    v11 = this.at(v9);
    if (cmp(mouseX,((d)((this.x + v7) - 12))) <0) goto L288;
    if (v11.name == null) goto L240;
    if (v11.name.isBlank() ==0) goto L248;
    L240:
    goto L253;
    L248:
    L253:
    v12 = v11.name;
    new Object[1][0] = v12;
    this.confirm.ask(ui.Lang.of("fwm.colorcodes.deleteAsk", new Object[1]), /*indy*/run(this, v11));
    goto L329;
    L288:
    this.user.load(colorcode.ColorCode.parse(v11.code), v11.mode());
    if (this.user.owner == null) goto L329;
    this.user.owner.setTab(0);
    L329:
    return 1;
    L331:
    v8 = v8 + 1;
    goto L112;
    L337:
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - ((i)Math.signum(v5)))));
    return 1;
}

public boolean isCapturingKeys() {
    return this.confirm.open();
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.confirm.open() ==0) goto L25;
    if (this.confirm.keyPressed(keyCode) ==0) goto L25;
    goto L26;
    L25:
    L26:
    return 0;
}

private int lambda$mouseClicked$0(colorcode.Templates$Template template) {
    colorcode.Templates.remove(template);
    this.scroll = Math.max(0, Math.min(this.maxScroll(), this.scroll));
    return;
}

