// class net/friendswithmoney/fwm/ui/panel/NameListPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field DEL_W I;
field BTN_H I;
field SCROLLBAR_W I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field titleKey Ljava/lang/String;;
field emptyKey Ljava/lang/String;;
field addKey Ljava/lang/String;;
field list Ljava/util/function/Supplier;;
field editor Lnet/minecraft/client/gui/components/EditBox;;
field editing I;
field scroll I;
public void <init>(ui.panel.PanelHost host, String titleKey, String emptyKey, String addKey, function.Supplier list) {
    this.editing = m1;
    this.host = host;
    this.titleKey = titleKey;
    this.emptyKey = emptyKey;
    this.addKey = addKey;
    this.list = list;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of(this.titleKey, new Object[0]);
}

private Object names() {
    return ((List)this.list.get());
}

private int listH() {
    return ((this.height - 14) - 3);
}

private int rowsVisible() {
    return Math.max(1, (this.listH() / 16));
}

private int maxScroll() {
    return Math.max(0, (this.names().size() - this.rowsVisible()));
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

private int addY() {
    return ((this.y + this.height) - 14);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.names();
    v7 = 0;
    L9:
    if (v7 >= this.rowsVisible()) goto L362;
    v8 = (this.scroll + v7);
    if (v8 < v6.size()) goto L42;
    goto L362;
    L42:
    v9 = this.rowY(v7);
    if (mouseX < this.x) goto L92;
    if (mouseX >= (this.x + this.rowW())) goto L92;
    if (mouseY < v9) goto L92;
    if (mouseY >= (v9 + 16)) goto L92;
    goto L93;
    L92:
    L93:
    v10 = 0;
    if (v10 ==0) goto L116;
    ui.FwmTheme.hover(ctx, this.x, v9, this.rowW(), 16);
    L116:
    Objects.requireNonNull(font);
    v11 = ((v9 + ((16 - 9) / 2)) + 1);
    if (this.editing != v8) goto L198;
    if (this.editor == null) goto L198;
    ui.FwmTheme.box(ctx, this.x, (v9 + 1), ((this.deleteX() - this.x) - 4), 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT);
    this.editor.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    goto L267;
    L198:
    if (v6.get(v8) != null) goto L215;
    goto L227;
    L215:
    L227:
    v12 = ((String)v6.get(v8));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v12, ((this.deleteX() - this.x) - 8))), (this.x + 3), v11, ui.FwmTheme.TEXT, 0);
    L267:
    if (mouseX < this.deleteX()) goto L307;
    if (mouseX >= (this.deleteX() + 12)) goto L307;
    if (mouseY < v9) goto L307;
    if (mouseY >= (v9 + 16)) goto L307;
    goto L308;
    L307:
    L308:
    v12 = 0;
    v13 = network.chat.Component.literal("x");
    if (v12 ==0) goto L349;
    goto L352;
    L349:
    L352:
    font.text(v13, (this.deleteX() + ((12 - font.width(v13)) / 2)), v11, -2067340, ui.FwmTheme.TEXT_DIM, 0);
    v7 = v7 + 1;
    goto L9;
    L362:
    if (v6.isEmpty() ==0) goto L427;
    v7 = ui.Lang.of(this.emptyKey, new Object[0]);
    ctx.text(font, v7, (this.x + ((this.width - font.width(v7)) / 2)), ((this.y + (this.listH() / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    L427:
    if (this.maxScroll() <=0) goto L545;
    v7 = ((this.x + this.width) - 4);
    ctx.fill(v7, this.y, (v7 + 4), (this.y + this.listH()), ui.FwmTheme.OFF);
    v8 = Math.max(12, ((this.listH() * this.rowsVisible()) / Math.max(1, v6.size())));
    v9 = (this.y + (((this.listH() - v8) * this.scroll) / this.maxScroll()));
    ctx.fill(v7, v9, (v7 + 4), (v9 + v8), ui.FwmTheme.ACCENT);
    L545:
    v7 = this.overAdd(((d)mouseX), ((d)mouseY));
    if (v7 ==0) goto L585;
    goto L588;
    L585:
    L588:
    ui.FwmTheme.box(this.x, this.addY(), this.width, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v8 = ui.Lang.of(this.addKey, new Object[0]);
    Objects.requireNonNull(font);
    if (v7 ==0) goto L656;
    goto L659;
    L656:
    L659:
    font.text(v8, (this.x + ((this.width - font.width(v8)) / 2)), ((this.addY() + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
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
    v3 = client.Minecraft.getInstance().font;
    this.editor = ?;
    this.editor.setBordered(0);
    this.editor.setMaxLength(17);
    this.editor.setTextColor(ui.FwmTheme.TEXT);
    this.editor.setHint(ui.Lang.of("fwm.autotpa.nameHint", new Object[0]));
    if (this.names().get(index) != null) goto L126;
    goto L139;
    L126:
    L139:
    "".setValue(((String)this.names().get(index)));
    this.editor.setFocused(1);
    this.host.attach(this.editor);
    this.editing = index;
    return;
}

private void stopEditing(boolean keep) {
    if (this.editor != null) goto L8;
    return;
    L8:
    if (keep ==0) goto L95;
    if (this.editing <0) goto L95;
    if (this.editing >= this.names().size()) goto L95;
    v2 = this.editor.getValue().trim().replaceAll("[^A-Za-z0-9_.*]", "");
    if (v2.isEmpty() ==0) goto L77;
    this.names().remove(this.editing);
    goto L92;
    L77:
    this.names().set(this.editing, v2);
    L92:
    config.ConfigManager.save();
    L95:
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
    if (this.overAdd(mouseX, mouseY) ==0) goto L91;
    this.stopEditing(1);
    this.names().add("");
    this.scroll = this.maxScroll();
    v6 = ((this.names().size() - 1) - this.scroll);
    this.startEditing((this.names().size() - 1), this.rowY(v6));
    return 1;
    L91:
    v6 = 0;
    L94:
    if (v6 >= this.rowsVisible()) goto L208;
    v7 = (this.scroll + v6);
    if (v7 < this.names().size()) goto L129;
    goto L208;
    L129:
    v8 = this.rowY(v6);
    if (cmp(mouseY,((d)v8)) <0) goto L202;
    if (cmp(mouseY,((d)(v8 + 16))) <0) goto L159;
    goto L202;
    L159:
    if (cmp(mouseX,((d)this.deleteX())) <0) goto L192;
    this.stopEditing(0);
    this.names().remove(v7);
    config.ConfigManager.save();
    goto L200;
    L192:
    this.startEditing(v7, v8);
    L200:
    return 1;
    L202:
    v6 = v6 + 1;
    goto L94;
    L208:
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
    if (keyCode != 41) goto L59;
    this.stopEditing(0);
    this.names().removeIf(/*indy*/test());
    config.ConfigManager.save();
    return 1;
    L59:
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

private static boolean lambda$keyPressed$0(String name) {
    if (name == null) goto L11;
    if (name.isBlank() ==0) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

