// class net/friendswithmoney/fwm/ui/panel/SpawnerAdTextsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field FIELD_H I;
field LABEL_H I;
field SLOT_H I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field fields [Lnet/minecraft/client/gui/components/EditBox;;
public void <init>(ui.panel.PanelHost host) {
    this.fields = new client.gui.components.EditBox[5];
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.spawnerad.textsTitle", new Object[0]);
}

private int slotY(int i) {
    return (((this.y + 20) + 4) + (i * 28));
}

protected void onResize() {
    this.store();
    this.detach();
    v1 = client.Minecraft.getInstance().font;
    v2 = config.FwmConfig.INSTANCE.spawnerAdTemplates;
    v3 = 0;
    L24:
    if (v3 >= this.fields.length) goto L169;
    new Object[1][0] = Integer.valueOf((v3 + 1));
    v4 = ?;
    v4.setBordered(0);
    v4.setMaxLength(256);
    v4.setTextColor(ui.FwmTheme.TEXT);
    if (v3 >= v2.size()) goto L133;
    goto L135;
    L133:
    L135:
    ((String)v2.get(v3)).setValue("");
    v4.moveCursorToStart(0);
    this.host.attach(v4);
    this.fields[v3] = v4;
    v3 = v3 + 1;
    goto L24;
    L169:
    return;
}

private void detach() {
    v1 = this.fields;
    v2 = v1.length;
    v3 = 0;
    L10:
    if (v3 >= v2) goto L42;
    v4 = v1[v3];
    if (v4 == null) goto L36;
    this.host.detach(v4);
    L36:
    v3 = v3 + 1;
    goto L10;
    L42:
    return;
}

private boolean store() {
    if (this.fields[0] != null) goto L10;
    return;
    L10:
    v1 = ?;
    v2 = this.fields;
    v3 = v2.length;
    v4 = 0;
    L29:
    if (v4 >= v3) goto L62;
    v5 = v2[v4];
    v1.add(v5.getValue().trim());
    v4 = v4 + 1;
    goto L29;
    L62:
    config.FwmConfig.INSTANCE.spawnerAdTemplates = v1;
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    ctx.text(font, ui.Lang.of("fwm.spawnerad.textsHint", new Object[0]), (this.x + 2), (this.y + 1), ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str("fwm.spawnerad.placeholders", new Object[0]), (this.width - 4))), (this.x + 2), ((this.y + 1) + 10), ui.FwmTheme.ACCENT, 0);
    v6 = 0;
    L79:
    if (v6 >= this.fields.length) goto L199;
    v7 = this.slotY(v6);
    new Object[1][0] = Integer.valueOf((v6 + 1));
    ctx.text(font, ui.Lang.of("fwm.spawnerad.slot", new Object[1]), (this.x + 2), v7, ui.FwmTheme.TEXT, 0);
    if (this.fields[v6].isFocused() ==0) goto L171;
    goto L174;
    L171:
    L174:
    ui.FwmTheme.box(this.x, (v7 + 10), this.width, 14, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.fields[v6].extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v6 = v6 + 1;
    goto L79;
    L199:
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = 0;
    L19:
    if (v6 >= this.fields.length) goto L80;
    v7 = (this.slotY(v6) + 10);
    if (cmp(mouseY,((d)v7)) <0) goto L70;
    if (cmp(mouseY,((d)(v7 + 14))) >=0) goto L70;
    goto L71;
    L70:
    L71:
    1.setFocused(0);
    v6 = v6 + 1;
    goto L19;
    L80:
    this.store();
    return 1;
}

private boolean focused() {
    v1 = this.fields;
    v2 = v1.length;
    v3 = 0;
    L10:
    if (v3 >= v2) goto L42;
    v4 = v1[v3];
    if (v4 == null) goto L36;
    if (v4.isFocused() ==0) goto L36;
    return v4;
    L36:
    v3 = v3 + 1;
    goto L10;
    L42:
    return null;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    v4 = this.focused();
    if (v4 != null) goto L13;
    return 0;
    L13:
    if (keyCode == 40) goto L25;
    if (keyCode != 88) goto L40;
    L25:
    v4.setFocused(0);
    this.store();
    config.ConfigManager.save();
    return 1;
    L40:
    return v4.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.focused();
    if (v2 == null) goto L21;
    if (v2.charTyped(input) ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public client.gui.components.EditBox isCapturingKeys() {
    if (this.focused() == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public void onHidden() {
    this.store();
    config.ConfigManager.save();
    this.detach();
    return;
}

