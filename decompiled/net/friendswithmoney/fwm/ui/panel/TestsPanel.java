// class net/friendswithmoney/fwm/ui/panel/TestsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field SCROLLBAR_W I;
field PENCIL_W I;
field DELETE_W I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field confirm Lnet/friendswithmoney/fwm/ui/ConfirmBox;;
field rename Lnet/minecraft/client/gui/components/EditBox;;
field renaming I;
field scroll I;
public void <init>(ui.panel.PanelHost host) {
    this.confirm = ?;
    this.renaming = m1;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.test.listTitle", new Object[0]);
}

private List tests() {
    return config.FwmConfig.INSTANCE.tests;
}

private int rowsVisible() {
    return Math.max(1, (this.height / 16));
}

private int maxScroll() {
    return Math.max(0, (this.tests().size() - this.rowsVisible()));
}

private Object at(int index) {
    v2 = this.tests();
    return ((config.TestRun)v2.get(((v2.size() - 1) - index)));
}

protected int onResize() {
    this.stopRenaming(0);
    this.scroll = Math.max(0, Math.min(this.maxScroll(), this.scroll));
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.tests().isEmpty() ==0) goto L66;
    v6 = ui.Lang.of("fwm.test.none", new Object[0]);
    ctx.text(font, v6, (this.x + ((this.width - font.width(v6)) / 2)), ((this.y + (this.height / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L66:
    if (this.maxScroll() <=0) goto L82;
    goto L83;
    L82:
    L83:
    v6 = (6 - 0);
    v7 = 0;
    L89:
    if (v7 >= this.rowsVisible()) goto L155;
    v8 = (this.scroll + v7);
    if (v8 < this.tests().size()) goto L124;
    goto L155;
    L124:
    this.renderRow(ctx, font, v8, (this.y + (v7 * 16)), v6, mouseX, mouseY, delta);
    v7 = v7 + 1;
    goto L89;
    L155:
    this.renderScrollbar(ctx);
    this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    return;
}

private void renderRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int index, int rowY, int rowW, int mouseX, int mouseY, float delta) {
    v9 = this.at(index);
    if (mouseX < this.x) goto L49;
    if (mouseX >= (this.x + rowW)) goto L49;
    if (mouseY < rowY) goto L49;
    if (mouseY >= (rowY + 16)) goto L49;
    goto L50;
    L49:
    L50:
    v10 = 0;
    if (v10 ==0) goto L71;
    ui.FwmTheme.hover(ctx, this.x, rowY, rowW, 16);
    L71:
    Objects.requireNonNull(font);
    v11 = ((rowY + ((16 - 9) / 2)) + 1);
    v12 = ((this.x + rowW) - 12);
    if (mouseX < v12) goto L140;
    if (mouseX >= (v12 + 12)) goto L140;
    if (mouseY < rowY) goto L140;
    if (mouseY >= (rowY + 16)) goto L140;
    goto L141;
    L140:
    L141:
    v13 = 0;
    if (v13 ==0) goto L164;
    goto L167;
    L164:
    L167:
    ctx.cross((v12 + 3), (rowY + 5), ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.TEXT_DIM);
    v14 = (v12 - 14);
    if (mouseX < v14) goto L215;
    if (mouseX >= (v14 + 14)) goto L215;
    if (mouseY < rowY) goto L215;
    if (mouseY >= (rowY + 16)) goto L215;
    goto L216;
    L215:
    L216:
    v15 = 0;
    if (v15 ==0) goto L239;
    goto L242;
    L239:
    L242:
    ctx.pencil((v14 + 3), (rowY + 4), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    v16 = util.Fmt.compactMoneyUp(v9.cents);
    v17 = ((v14 - 4) - font.width(v16));
    ctx.text(font, network.chat.Component.literal(v16), v17, v11, ui.FwmTheme.ACCENT, 0);
    if (this.renaming != index) goto L345;
    if (this.rename == null) goto L345;
    ui.FwmTheme.box(ctx, this.x, (rowY + 1), ((v17 - this.x) - 4), 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT);
    this.rename.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    return;
    L345:
    if (v9.name != null) goto L359;
    goto L364;
    L359:
    L364:
    v18 = network.chat.Component.literal("?".plainSubstrByWidth(v9.name, Math.max(8, ((v17 - this.x) - 6))));
    ctx.text(font, v18, (this.x + 2), v11, ui.FwmTheme.TEXT, 0);
    return;
}

private void pencil(client.gui.GuiGraphicsExtractor ctx, int px, int py, int color) {
    v5 = 0;
    L3:
    if (v5 >= 5) goto L44;
    ctx.fill((px + v5), ((py + 4) - v5), ((px + v5) + 2), ((py + 6) - v5), color);
    v5 = v5 + 1;
    goto L3;
    L44:
    ctx.fill(px, (py + 5), (px + 2), (py + 7), color);
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
    v3 = (this.rowsVisible() * 16);
    ctx.fill(v2, this.y, (v2 + 4), (this.y + v3), ui.FwmTheme.OFF);
    v4 = Math.max(12, ((v3 * this.rowsVisible()) / Math.max(1, this.tests().size())));
    v5 = (this.y + (((v3 - v4) * this.scroll) / this.maxScroll()));
    ctx.fill(v2, v5, (v2 + 4), (v5 + v4), ui.FwmTheme.ACCENT);
    return;
}

private void startRenaming(int index, int rowY, int fieldW) {
    this.stopRenaming(1);
    v4 = this.at(index);
    v5 = client.Minecraft.getInstance().font;
    this.rename = ?;
    this.rename.setBordered(0);
    this.rename.setMaxLength(24);
    this.rename.setTextColor(ui.FwmTheme.TEXT);
    if (v4.name != null) goto L103;
    goto L108;
    L103:
    L108:
    "".setValue(v4.name);
    this.rename.setFocused(1);
    this.host.attach(this.rename);
    this.renaming = index;
    return;
}

private void stopRenaming(boolean keep) {
    if (this.rename != null) goto L8;
    return;
    L8:
    if (keep ==0) goto L68;
    if (this.renaming <0) goto L68;
    if (this.renaming >= this.tests().size()) goto L68;
    v2 = this.rename.getValue().trim();
    if (v2.isEmpty() !=0) goto L68;
    this.at(this.renaming).name = v2;
    config.ConfigManager.save();
    L68:
    this.host.detach(this.rename);
    this.rename = null;
    this.renaming = m1;
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
    if (this.maxScroll() <=0) goto L52;
    goto L53;
    L52:
    L53:
    v6 = (6 - 0);
    v7 = ((this.x + v6) - 12);
    v8 = (v7 - 14);
    v9 = 0;
    L78:
    if (v9 >= this.rowsVisible()) goto L246;
    v10 = (this.scroll + v9);
    if (v10 < this.tests().size()) goto L113;
    goto L246;
    L113:
    v11 = (this.y + (v9 * 16));
    if (cmp(mouseY,((d)v11)) <0) goto L240;
    if (cmp(mouseY,((d)(v11 + 16))) <0) goto L147;
    goto L240;
    L147:
    if (cmp(mouseX,((d)v7)) <0) goto L173;
    this.stopRenaming(1);
    this.delete(this.at(v10));
    goto L238;
    L173:
    if (cmp(mouseX,((d)v8)) <0) goto L202;
    this.startRenaming(v10, v11, ((v8 - this.x) - 8));
    goto L238;
    L202:
    if (this.renaming == v10) goto L238;
    this.stopRenaming(1);
    this.host.openSub(?);
    L238:
    return 1;
    L240:
    v9 = v9 + 1;
    goto L78;
    L246:
    this.stopRenaming(1);
    return 1;
}

private void delete(config.TestRun run) {
    if (run.name != null) goto L25;
    goto L29;
    L25:
    L29:
    0["?"] = run.name;
    "fwm.test.deleteAsk".ask(ui.Lang.of(new Object[1], new Object[1]), /*indy*/run(this, run));
    return;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - ((i)Math.signum(v5)))));
    return 1;
}

public boolean isCapturingKeys() {
    if (this.rename != null) goto L17;
    if (this.confirm.open() ==0) goto L21;
    L17:
    goto L22;
    L21:
    L22:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.confirm.open() ==0) goto L19;
    return this.confirm.keyPressed(keyCode);
    L19:
    if (this.rename != null) goto L28;
    return 0;
    L28:
    if (keyCode == 40) goto L40;
    if (keyCode != 88) goto L47;
    L40:
    this.stopRenaming(1);
    return 1;
    L47:
    if (keyCode != 41) goto L60;
    this.stopRenaming(0);
    return 1;
    L60:
    return this.rename.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.rename == null) goto L22;
    if (this.rename.charTyped(input) ==0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

public void onHidden() {
    this.stopRenaming(1);
    return;
}

private void lambda$delete$0(config.TestRun run) {
    this.tests().remove(run);
    this.scroll = Math.max(0, Math.min(this.maxScroll(), this.scroll));
    config.ConfigManager.save();
    return;
}

