// class net/friendswithmoney/fwm/ui/panel/ItemPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field SEARCH_H I;
field FOOTER_H I;
field SCROLLBAR_W I;
field BUTTONS I;
field BUTTON_GAP I;
field BUTTON_H I;
field catalogue Ljava/util/List;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field visible Ljava/util/List;;
field search Lnet/minecraft/client/gui/components/EditBox;;
field listY I;
field listH I;
field rowsVisible I;
field scroll I;
field pick Ljava/util/function/Consumer;;
public void <init>(ui.panel.PanelHost host) {
    return;
}

public void <init>(ui.panel.PanelHost host, function.Consumer pick) {
    this.visible = ?;
    this.host = host;
    this.pick = pick;
    return;
}

private static List catalogue() {
    if (ui.panel.ItemPanel.catalogue == null) goto L10;
    return ui.panel.ItemPanel.catalogue;
    L10:
    v0 = ?;
    v1 = core.registries.BuiltInRegistries.ITEM.iterator();
    L27:
    if (v1.hasNext() ==0) goto L124;
    v2 = ((world.item.Item)v1.next());
    v3 = ?;
    if (v3.isEmpty() ==0) goto L65;
    goto L27;
    L65:
    v4 = core.registries.BuiltInRegistries.ITEM.getKey(v2).toString();
    v5 = v3.getHoverName().getString();
    v0.add(?);
    goto L121;
    v3 = /*catch Exception*/ ex;
    L121:
    goto L27;
    L124:
    v0.sort(/*indy*/compare());
    if (v0.isEmpty() !=0) goto L155;
    ui.panel.ItemPanel.catalogue = List.copyOf(v0);
    return ui.panel.ItemPanel.catalogue;
    L155:
    return List.of();
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.items.title", new Object[0]);
}

protected void onResize() {
    this.listY = ((this.y + 16) + 4);
    this.listH = Math.max(18, (((this.height - 16) - 4) - 20));
    this.rowsVisible = Math.max(1, (this.listH / 18));
    if (this.search != null) goto L61;
    goto L68;
    L61:
    L68:
    v1 = this.search.getValue();
    if (this.search == null) goto L89;
    this.host.detach(this.search);
    L89:
    v2 = client.Minecraft.getInstance().font;
    this.search = ?;
    this.search.setBordered(0);
    this.search.setMaxLength(48);
    this.search.setTextColor(ui.FwmTheme.TEXT);
    this.search.setHint(ui.Lang.of("fwm.items.search", new Object[0]));
    this.search.setValue(v1);
    this.search.setResponder(/*indy*/accept(this));
    this.search.setFocused(1);
    this.host.attach(this.search);
    this.applyFilter(v1);
    return;
}

private boolean applyFilter(String query) {
    this.visible.clear();
    if (query != null) goto L18;
    goto L28;
    L18:
    L28:
    v2 = query.trim().toLowerCase(Locale.ROOT);
    v3 = ui.panel.ItemPanel.catalogue().iterator();
    L38:
    if (v3.hasNext() ==0) goto L104;
    v4 = ((ui.panel.ItemPanel$Entry)v3.next());
    if (v2.isEmpty() !=0) goto L89;
    if (v4.lowerName.contains(v2) !=0) goto L89;
    if (v4.id.contains(v2) ==0) goto L101;
    L89:
    this.visible.add(v4);
    L101:
    goto L38;
    L104:
    return;
}

private int maxScroll() {
    return Math.max(0, (this.visible.size() - this.rowsVisible));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.search.isFocused() ==0) goto L34;
    goto L37;
    L34:
    L37:
    ui.FwmTheme.box(this.x, this.y, this.width, 16, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    this.renderList(ctx, font, mouseX, mouseY);
    this.renderFooter(ctx, font, mouseX, mouseY);
    return;
}

private void renderList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    ctx.enableScissor(this.x, this.listY, (this.x + this.width), (this.listY + this.listH));
    v5 = 0;
    L33:
    if (v5 >= this.rowsVisible) goto L334;
    v6 = (this.scroll + v5);
    if (v6 < this.visible.size()) goto L68;
    goto L334;
    L68:
    v7 = ((ui.panel.ItemPanel$Entry)this.visible.get(v6));
    v8 = (this.listY + (v5 * 18));
    if (mouseX < this.x) goto L138;
    if (mouseX >= (this.x + this.width)) goto L138;
    if (mouseY < v8) goto L138;
    if (mouseY >= (v8 + 18)) goto L138;
    goto L139;
    L138:
    L139:
    v9 = 0;
    if (v9 ==0) goto L173;
    ctx.fill(this.x, v8, (this.x + this.width), (v8 + 18), ui.FwmTheme.HOVER);
    L173:
    if (this.pick != null) goto L194;
    if (config.FwmConfig.INSTANCE.isWhitelisted(v7.id) ==0) goto L198;
    L194:
    goto L199;
    L198:
    L199:
    v10 = 0;
    v11 = (this.x + 3);
    v12 = (v8 + 4);
    if (this.pick != null) goto L260;
    if (v10 ==0) goto L242;
    goto L243;
    L242:
    L243:
    if (v10 ==0) goto L254;
    goto L257;
    L254:
    L257:
    ui.FwmTheme.box(v12, 10, 10, ui.FwmTheme.ON, 0, ui.FwmTheme.ON, ui.FwmTheme.OFF);
    L260:
    ctx.item(v7.stack, (v11 + 15), (v8 + 1));
    Objects.requireNonNull(font);
    if (v10 ==0) goto L321;
    goto L324;
    L321:
    L324:
    font.text(network.chat.Component.literal(v7.name), (v11 + 34), ((v8 + ((18 - 9) / 2)) + 1), ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    v5 = v5 + 1;
    goto L33;
    L334:
    ctx.disableScissor();
    if (this.visible.isEmpty() ==0) goto L421;
    if (ui.panel.ItemPanel.catalogue().isEmpty() ==0) goto L367;
    goto L370;
    L367:
    L370:
    v5 = ui.Lang.of("fwm.items.noMatch", new Object[0]);
    ctx.text(font, v5, (this.x + ((this.width - font.width(v5)) / 2)), ((this.listY + (this.listH / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    L421:
    this.renderScrollbar(ctx);
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    if (this.maxScroll() >0) goto L8;
    return;
    L8:
    v2 = ((this.x + this.width) - 4);
    ctx.fill(v2, this.listY, (v2 + 4), (this.listY + this.listH), ui.FwmTheme.OFF);
    v3 = Math.max(12, ((this.listH * this.rowsVisible) / Math.max(1, this.visible.size())));
    v4 = (this.listY + (((this.listH - v3) * this.scroll) / this.maxScroll()));
    ctx.fill(v2, v4, (v2 + 4), (v4 + v3), ui.FwmTheme.ACCENT);
    return;
}

private void renderFooter(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    if (this.pick == null) goto L8;
    return;
    L8:
    v5 = this.footerY();
    this.drawButton(ctx, font, this.buttonX(0), v5, this.buttonW(), ui.Lang.of("fwm.items.all", new Object[0]), mouseX, mouseY);
    this.drawButton(ctx, font, this.buttonX(1), v5, this.buttonW(), ui.Lang.of("fwm.items.presetFlower", new Object[0]), mouseX, mouseY);
    this.drawButton(ctx, font, this.buttonX(2), v5, this.buttonW(), ui.Lang.of("fwm.items.none", new Object[0]), mouseX, mouseY);
    return;
}

private int footerY() {
    return ((this.y + this.height) - 14);
}

private int buttonW() {
    return ((this.width - 8) / 3);
}

private int buttonX(int index) {
    return (this.x + (index * (this.buttonW() + 4)));
}

private int buttonAt(double mx, double a2) {
    if (this.pick == null) goto L9;
    return m1;
    L9:
    if (cmp(v3,((d)this.footerY())) <0) goto L32;
    if (cmp(v3,((d)(this.footerY() + 14))) <0) goto L34;
    L32:
    return m1;
    L34:
    v5 = 0;
    L37:
    if (v5 >= 3) goto L81;
    if (cmp(mx,((d)this.buttonX(v5))) <0) goto L75;
    if (cmp(mx,((d)(this.buttonX(v5) + this.buttonW()))) >=0) goto L75;
    return v5;
    L75:
    v5 = v5 + 1;
    goto L37;
    L81:
    return m1;
}

private void drawButton(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int w, network.chat.Component label, int mouseX, int mouseY) {
    if (mouseX < bx) goto L36;
    if (mouseX >= (bx + w)) goto L36;
    if (mouseY < by) goto L36;
    if (mouseY >= (by + 14)) goto L36;
    goto L37;
    L36:
    L37:
    v9 = 0;
    if (v9 ==0) goto L61;
    goto L64;
    L61:
    L64:
    ui.FwmTheme.box(bx, by, w, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = network.chat.Component.literal(font.plainSubstrByWidth(label.getString(), (w - 6)));
    Objects.requireNonNull(font);
    if (v9 ==0) goto L133;
    goto L136;
    L133:
    L136:
    font.text(v10, (bx + ((w - font.width(v10)) / 2)), ((by + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 ==0) goto L7;
    return 0;
    L7:
    // switch (this.buttonAt(mouseX, mouseY)) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=113), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=2), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=94), Operand(op_type=<OperandTypes.BRANCH: 40>, value=105)]
    L40:
    v6 = ?;
    v7 = ui.panel.ItemPanel.catalogue().iterator();
    L59:
    if (v7.hasNext() ==0) goto L97;
    v8 = ((ui.panel.ItemPanel$Entry)v7.next());
    v6.add(v8.id);
    goto L59;
    L97:
    config.FwmConfig.INSTANCE.setWhitelist(v6);
    return 1;
    L107:
    config.FwmConfig.INSTANCE.setWhitelist(config.FwmConfig.PRESET_FLOWER_FARM);
    return 1;
    L118:
    config.FwmConfig.INSTANCE.clearWhitelist();
    return 1;
    L126:
    if (cmp(mouseX,((d)this.x)) <0) goto L284;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L284;
    if (cmp(mouseY,((d)this.listY)) <0) goto L284;
    if (cmp(mouseY,((d)(this.listY + this.listH))) >=0) goto L284;
    v6 = (this.scroll + ((i)((mouseY - ((d)this.listY)) / 18.0)));
    if (v6 <0) goto L284;
    if (v6 >= this.visible.size()) goto L284;
    if (this.pick == null) goto L258;
    this.pick.accept(((ui.panel.ItemPanel$Entry)this.visible.get(v6)).id);
    this.host.closeSub();
    return 1;
    L258:
    config.FwmConfig.INSTANCE.toggleWhitelisted(((ui.panel.ItemPanel$Entry)this.visible.get(v6)).id);
    return 1;
    L284:
    if (cmp(mouseX,((d)this.x)) <0) goto L340;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L340;
    if (cmp(mouseY,((d)this.y)) <0) goto L340;
    if (cmp(mouseY,((d)(this.y + 16))) >=0) goto L340;
    goto L341;
    L340:
    L341:
    1.setFocused(0);
    return this.contains(mouseX, mouseY);
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - (((i)Math.signum(v5)) * 3))));
    return 1;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.search.isFocused() ==0) goto L34;
    if (this.search.keyPressed(?) ==0) goto L34;
    goto L35;
    L34:
    L35:
    return 0;
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.search.isFocused() ==0) goto L25;
    if (this.search.charTyped(input) ==0) goto L25;
    goto L26;
    L25:
    L26:
    return 0;
}

public void onHidden() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    config.ConfigManager.save();
    return;
}

private void lambda$onResize$0(String text) {
    this.applyFilter(text);
    this.scroll = 0;
    return;
}

private static int lambda$catalogue$0(ui.panel.ItemPanel$Entry a, ui.panel.ItemPanel$Entry b) {
    return a.name.compareToIgnoreCase(b.name);
}

