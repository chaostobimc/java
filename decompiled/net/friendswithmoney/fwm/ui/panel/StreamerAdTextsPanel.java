// class net/friendswithmoney/fwm/ui/panel/StreamerAdTextsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field FIELD_H I;
field LABEL_H I;
field SLOT_H I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field fields [Lnet/minecraft/client/gui/components/EditBox;;
public int <init>(ui.panel.PanelHost host) {
    this.fields = new client.gui.components.EditBox[config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION.size()];
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.streamerad.title", new Object[0]);
}

private int slotY(int i) {
    return (((this.y + 20) + 4) + (i * 28));
}

protected client.gui.components.EditBox onResize() {
    this.store();
    this.detach();
    v1 = client.Minecraft.getInstance().font;
    v2 = config.FwmConfig.INSTANCE;
    v3 = v2.streamerAdRotation;
    v4 = 0;
    L27:
    if (v4 >= this.fields.length) goto L138;
    if (v3 == null) goto L115;
    if (v4 >= v3.size()) goto L115;
    goto L128;
    L115:
    L128:
    v4[this] = v1.field((this.x + 4), ((this.slotY(v4) + 10) + 3), (this.width - 8), ui.Lang.of('fwm.streamerad.rot' + v4, new Object[0]), 256, ((String)v3.get(v4)), ((String)config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION.get(v4)));
    v4 = v4 + 1;
    goto L27;
    L138:
    return;
}

private void field(client.gui.Font font, int fx, int fy, int fw, network.chat.Component label, int max, String text) {
    v8 = ?;
    v8.setBordered(0);
    v8.setMaxLength(max);
    v8.setTextColor(ui.FwmTheme.TEXT);
    v8.setValue(text);
    v8.moveCursorToStart(0);
    this.host.attach(v8);
    return v8;
}

private client.gui.components.EditBox[] all() {
    return this.fields;
}

private void detach() {
    v1 = this.all();
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
    v2 = 0;
    L20:
    if (v2 >= this.fields.length) goto L78;
    v3 = this.fields[v2].getValue().trim();
    if (v3.isEmpty() ==0) goto L65;
    goto L66;
    L65:
    L66:
    v2 = v2 + 1;
    goto L20;
    L78:
    config.FwmConfig.INSTANCE.streamerAdRotation = v1;
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str("fwm.streamerad.hint", new Object[0]), (this.width - 4))), (this.x + 2), (this.y + 1), ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str("fwm.streamerad.placeholders", new Object[0]), (this.width - 4))), (this.x + 2), ((this.y + 1) + 10), ui.FwmTheme.ACCENT, 0);
    v6 = 0;
    L92:
    if (v6 >= this.fields.length) goto L207;
    v7 = this.slotY(v6);
    ctx.text(font, ui.Lang.of('fwm.streamerad.rot' + v6, new Object[0]), (this.x + 2), v7, ui.FwmTheme.TEXT, 0);
    if (this.fields[v6].isFocused() ==0) goto L179;
    goto L182;
    L179:
    L182:
    ui.FwmTheme.box(this.x, (v7 + 10), this.width, 14, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.fields[v6].extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v6 = v6 + 1;
    goto L92;
    L207:
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
    v1 = this.all();
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

