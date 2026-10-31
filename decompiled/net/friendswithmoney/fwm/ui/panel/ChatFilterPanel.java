// class net/friendswithmoney/fwm/ui/panel/ChatFilterPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field PILL_W I;
field PILL_H I;
field KNOB I;
field DEL_W I;
field BTN_H I;
field SCROLLBAR_W I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field editor Lnet/minecraft/client/gui/components/EditBox;;
field editing I;
field scroll I;
public void <init>(ui.panel.PanelHost host) {
    this.editing = m1;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.secondchat.filters", new Object[0]);
}

private List filters() {
    return config.FwmConfig.INSTANCE.secondChatFilters;
}

private int listH() {
    return ((this.height - 14) - 3);
}

private int rowsVisible() {
    return Math.max(1, (this.listH() / 16));
}

private int maxScroll() {
    return Math.max(0, (this.filters().size() - this.rowsVisible()));
}

private int rowY(int slot) {
    return (this.y + (slot * 16));
}

private int rowW() {
    if (this.maxScroll() <=0) goto L16;
    goto L17;
    L16:
    L17:
    return (6 - 0);
}

private int deleteX() {
    return ((this.x + this.rowW()) - 12);
}

private int pillX() {
    return ((this.deleteX() - 4) - 34);
}

private int addY() {
    return ((this.y + this.height) - 14);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.filters();
    v7 = 0;
    L9:
    if (v7 >= this.rowsVisible()) goto L79;
    v8 = (this.scroll + v7);
    if (v8 < v6.size()) goto L42;
    goto L79;
    L42:
    this.renderRow(ctx, font, ((config.ChatFilter)v6.get(v8)), v8, this.rowY(v7), mouseX, mouseY, delta);
    v7 = v7 + 1;
    goto L9;
    L79:
    if (v6.isEmpty() ==0) goto L142;
    v7 = ui.Lang.of("fwm.secondchat.filtersNone", new Object[0]);
    ctx.text(font, v7, (this.x + ((this.width - font.width(v7)) / 2)), ((this.y + (this.listH() / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    L142:
    this.renderScrollbar(ctx);
    this.renderAddButton(ctx, font, mouseX, mouseY);
    return;
}

private void renderRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, config.ChatFilter filter, int index, int ry, int mouseX, int mouseY, float delta) {
    if (mouseX < this.x) goto L44;
    if (mouseX >= (this.x + this.rowW())) goto L44;
    if (mouseY < ry) goto L44;
    if (mouseY >= (ry + 16)) goto L44;
    goto L45;
    L44:
    L45:
    v9 = 0;
    if (v9 ==0) goto L68;
    ui.FwmTheme.hover(ctx, this.x, ry, this.rowW(), 16);
    L68:
    Objects.requireNonNull(font);
    v10 = ((ry + ((16 - 9) / 2)) + 1);
    if (this.editing != index) goto L151;
    if (this.editor == null) goto L151;
    ui.FwmTheme.box(ctx, this.x, (ry + 1), ((this.pillX() - this.x) - 4), 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT);
    this.editor.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    goto L237;
    L151:
    if (filter.text == null) goto L168;
    if (filter.text.isBlank() ==0) goto L180;
    L168:
    goto L184;
    L180:
    L184:
    v11 = filter.text;
    if (filter.on ==0) goto L230;
    goto L233;
    L230:
    L233:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(v11, ((this.pillX() - this.x) - 8))), (this.x + 3), v10, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    L237:
    v11 = (ry + 2);
    if (filter.on ==0) goto L267;
    goto L270;
    L267:
    L270:
    ui.FwmTheme.pill(this.pillX(), v11, 34, 12, ui.FwmTheme.ON, ui.FwmTheme.OFF);
    if (filter.on ==0) goto L296;
    goto L302;
    L296:
    L302:
    if (filter.on ==0) goto L322;
    goto L325;
    L322:
    L325:
    ui.FwmTheme.pill((this.pillX() + 2), (v11 + 2), 8, 8, -16375537, ui.FwmTheme.TEXT);
    if (mouseX < this.deleteX()) goto L370;
    if (mouseX >= (this.deleteX() + 12)) goto L370;
    if (mouseY < ry) goto L370;
    if (mouseY >= (ry + 16)) goto L370;
    goto L371;
    L370:
    L371:
    v12 = 0;
    v13 = network.chat.Component.literal("x");
    if (v12 ==0) goto L412;
    goto L415;
    L412:
    L415:
    font.text(v13, (this.deleteX() + ((12 - font.width(v13)) / 2)), v10, -2067340, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    if (this.maxScroll() >0) goto L8;
    return;
    L8:
    v2 = ((this.x + this.width) - 4);
    ctx.fill(v2, this.y, (v2 + 4), (this.y + this.listH()), ui.FwmTheme.OFF);
    v3 = Math.max(12, ((this.listH() * this.rowsVisible()) / Math.max(1, this.filters().size())));
    v4 = (this.y + (((this.listH() - v3) * this.scroll) / this.maxScroll()));
    ctx.fill(v2, v4, (v2 + 4), (v4 + v3), ui.FwmTheme.ACCENT);
    return;
}

private void renderAddButton(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.overAdd(((d)mouseX), ((d)mouseY));
    if (v5 ==0) goto L40;
    goto L43;
    L40:
    L43:
    ui.FwmTheme.box(this.x, this.addY(), this.width, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v6 = ui.Lang.of("fwm.secondchat.filterAdd", new Object[0]);
    Objects.requireNonNull(font);
    if (v5 ==0) goto L109;
    goto L112;
    L109:
    L112:
    font.text(v6, (this.x + ((this.width - font.width(v6)) / 2)), ((this.addY() + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private int overAdd(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L52;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L52;
    if (cmp(v3,((d)this.addY())) <0) goto L52;
    if (cmp(v3,((d)(this.addY() + 14))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

private void startEditing(int index, int ry) {
    this.stopEditing(1);
    v3 = ((config.ChatFilter)this.filters().get(index));
    v4 = client.Minecraft.getInstance().font;
    this.editor = ?;
    this.editor.setBordered(0);
    this.editor.setMaxLength(64);
    this.editor.setTextColor(ui.FwmTheme.TEXT);
    this.editor.setHint(ui.Lang.of("fwm.secondchat.filterHint", new Object[0]));
    if (v3.text != null) goto L136;
    goto L140;
    L136:
    L140:
    "".setValue(v3.text);
    this.editor.setFocused(1);
    this.host.attach(this.editor);
    this.editing = index;
    return;
}

private void stopEditing(boolean keep) {
    if (this.editor != null) goto L8;
    return;
    L8:
    if (keep ==0) goto L93;
    if (this.editing <0) goto L93;
    if (this.editing >= this.filters().size()) goto L93;
    v2 = this.editor.getValue().trim();
    if (v2.isEmpty() ==0) goto L70;
    this.filters().remove(this.editing);
    goto L90;
    L70:
    ((config.ChatFilter)this.filters().get(this.editing)).text = v2;
    L90:
    config.ConfigManager.save();
    L93:
    this.host.detach(this.editor);
    this.editor = null;
    this.editing = m1;
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.overAdd(mouseX, mouseY) ==0) goto L98;
    this.stopEditing(1);
    this.filters().add(?);
    this.scroll = this.maxScroll();
    v6 = ((this.filters().size() - 1) - this.scroll);
    this.startEditing((this.filters().size() - 1), this.rowY(v6));
    return 1;
    L98:
    v6 = 0;
    L101:
    if (v6 >= this.rowsVisible()) goto L270;
    v7 = (this.scroll + v6);
    if (v7 < this.filters().size()) goto L136;
    goto L270;
    L136:
    v8 = this.rowY(v6);
    if (cmp(mouseY,((d)v8)) <0) goto L264;
    if (cmp(mouseY,((d)(v8 + 16))) <0) goto L166;
    goto L264;
    L166:
    if (cmp(mouseX,((d)this.deleteX())) <0) goto L199;
    this.stopEditing(0);
    this.filters().remove(v7);
    config.ConfigManager.save();
    goto L262;
    L199:
    if (cmp(mouseX,((d)this.pillX())) <0) goto L254;
    this.stopEditing(1);
    v9 = ((config.ChatFilter)this.filters().get(v7));
    if (v9.on !=0) goto L244;
    goto L245;
    L244:
    L245:
    1.on = 0;
    config.ConfigManager.save();
    goto L262;
    L254:
    this.startEditing(v7, v8);
    L262:
    return 1;
    L264:
    v6 = v6 + 1;
    goto L101;
    L270:
    this.stopEditing(1);
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - ((i)Math.signum(v5)))));
    return 1;
}

public boolean isCapturingKeys() {
    if (this.editor == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.editor != null) goto L9;
    return 0;
    L9:
    if (keyCode == 40) goto L21;
    if (keyCode != 88) goto L28;
    L21:
    this.stopEditing(1);
    return 1;
    L28:
    if (keyCode != 41) goto L41;
    this.stopEditing(0);
    return 1;
    L41:
    return this.editor.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.editor == null) goto L22;
    if (this.editor.charTyped(input) ==0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

public void onHidden() {
    this.stopEditing(1);
    return;
}

