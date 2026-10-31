// class net/friendswithmoney/fwm/ui/widget/KeybindRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_BTN_W I;
field BTN_H I;
field CONFLICT I;
field CLEAR_W I;
field CLEAR_GAP I;
field CONFLICT_MS J;
field binding Lnet/minecraft/client/KeyMapping;;
field clearable Z;
field listening Z;
field conflictUntil J;
public void <init>(network.chat.Component label, client.KeyMapping binding) {
    return;
}

public void <init>(network.chat.Component label, client.KeyMapping binding, boolean clearable) {
    this.binding = binding;
    this.clearable = clearable;
    return;
}

private int keyRight() {
    return ((this.controlRight() - 12) - 3);
}

private int clearX() {
    return (this.controlRight() - 12);
}

private int overClear(double mouseX, double a2) {
    v5 = (this.y + 6);
    if (this.clearable ==0) goto L62;
    if (cmp(mouseX,((d)this.clearX())) <0) goto L62;
    if (cmp(mouseX,((d)(this.clearX() + 12))) >=0) goto L62;
    if (cmp(v3,((d)v5)) <0) goto L62;
    if (cmp(v3,((d)(v5 + 14))) >=0) goto L62;
    goto L63;
    L62:
    L63:
    return 0;
}

public boolean isCapturingKeys() {
    return this.listening;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = Math.min(60, ((this.controlWidth() - 12) - 3));
    v6 = (this.keyRight() - v5);
    v7 = (this.y + 6);
    if (cmp(System.currentTimeMillis(),this.conflictUntil) >=0) goto L49;
    goto L50;
    L49:
    L50:
    v8 = 0;
    if (v8 ==0) goto L74;
    goto L90;
    L74:
    if (this.listening ==0) goto L87;
    goto L90;
    L87:
    L90:
    ui.FwmTheme.box(v7, v5, 14, ui.FwmTheme.RAISED, -2064256, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v8 ==0) goto L110;
    goto L136;
    L110:
    if (this.listening ==0) goto L129;
    goto L136;
    L129:
    L136:
    v9 = this.binding.getTranslatedKeyMessage();
    Objects.requireNonNull(font);
    if (v8 ==0) goto L183;
    goto L199;
    L183:
    if (this.listening ==0) goto L196;
    goto L199;
    L196:
    L199:
    v9.text((v6 + ((v5 - font.width(v9)) / 2)), ((v7 + ((14 - 9) / 2)) + 1), -2064256, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    if (this.clearable !=0) goto L211;
    return;
    L211:
    v10 = this.overClear(((d)mouseX), ((d)mouseY));
    if (v10 ==0) goto L246;
    goto L249;
    L246:
    L249:
    ui.FwmTheme.box(this.clearX(), v7, 12, 14, ui.FwmTheme.RAISED, -2064256, ui.FwmTheme.OFF);
    Objects.requireNonNull(font);
    if (v10 ==0) goto L302;
    goto L305;
    L302:
    L305:
    font.text(network.chat.Component.literal("x"), (this.clearX() + ((12 - font.width("x")) / 2)), ((v7 + ((14 - 9) / 2)) + 1), -2064256, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.listening ==0) goto L63;
    if (v5 ==0) goto L63;
    this.listening = 0;
    v6 = blaze3d.platform.InputConstants$Type.MOUSE.getOrCreate(util.GLFW.sdlButton(v5));
    if (input.Keybinds.isTaken(this.binding, v6) ==0) goto L55;
    this.conflictUntil = (System.currentTimeMillis() + 1500);
    return 1;
    L55:
    this.bind(v6);
    return 1;
    L63:
    if (v5 !=0) goto L77;
    if (this.isHovered(mouseX, mouseY) !=0) goto L79;
    L77:
    return 0;
    L79:
    if (this.overClear(mouseX, mouseY) ==0) goto L107;
    this.listening = 0;
    this.conflictUntil = 0;
    this.bind(blaze3d.platform.InputConstants.UNKNOWN);
    return 1;
    L107:
    if (this.listening !=0) goto L119;
    goto L120;
    L119:
    L120:
    1.listening = 0;
    this.conflictUntil = 0;
    return 1;
}

public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.listening !=0) goto L9;
    return 0;
    L9:
    this.listening = 0;
    if (keyCode != 41) goto L22;
    return 1;
    L22:
    v4 = blaze3d.platform.InputConstants.getKey(?);
    if (input.Keybinds.isTaken(this.binding, v4) ==0) goto L62;
    this.conflictUntil = (System.currentTimeMillis() + 1500);
    return 1;
    L62:
    this.bind(v4);
    return 1;
}

private void bind(blaze3d.platform.InputConstants$Key key) {
    v2 = client.Minecraft.getInstance();
    this.binding.setKey(key);
    client.KeyMapping.resetMapping();
    v2.options.save();
    input.Keybinds.remember();
    return;
}

