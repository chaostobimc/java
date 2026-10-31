// class net/friendswithmoney/fwm/ui/widget/WordRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_BOX_W I;
field BOX_H I;
field MAX_TYPED I;
field getter Ljava/util/function/Supplier;;
field setter Ljava/util/function/Consumer;;
field typed Ljava/lang/StringBuilder;;
public void <init>(network.chat.Component label, function.Supplier getter, function.Consumer setter) {
    this.getter = getter;
    this.setter = setter;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = Math.min(96, this.controlWidth());
    v6 = (this.controlRight() - v5);
    v7 = (this.y + 6);
    if (this.typed == null) goto L40;
    goto L41;
    L40:
    L41:
    v8 = 0;
    if (mouseX < v6) goto L79;
    if (mouseX >= (v6 + v5)) goto L79;
    if (mouseY < v7) goto L79;
    if (mouseY >= (v7 + 14)) goto L79;
    goto L80;
    L79:
    L80:
    v9 = 0;
    if (v8 !=0) goto L104;
    if (v9 ==0) goto L110;
    L104:
    goto L113;
    L110:
    L113:
    ui.FwmTheme.box(v6, v7, v5, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v8 ==0) goto L136;
    goto L148;
    L136:
    L148:
    v10 = ((String)this.getter.get());
    v11 = network.chat.Component.literal(font.plainSubstrByWidth(v10, (v5 - 6)));
    Objects.requireNonNull(font);
    if (v8 !=0) goto L211;
    if (v9 ==0) goto L217;
    L211:
    goto L220;
    L217:
    L220:
    font.text(v11, (v6 + ((v5 - font.width(v11)) / 2)), ((v7 + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L20;
    L14:
    this.commit();
    return 0;
    L20:
    this.typed = ?;
    return 1;
}

public boolean isCapturingKeys() {
    if (this.typed == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public StringBuilder keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.typed != null) goto L9;
    return 0;
    L9:
    if (keyCode != 41) goto L22;
    this.typed = null;
    return 1;
    L22:
    if (keyCode == 40) goto L34;
    if (keyCode != 88) goto L40;
    L34:
    this.commit();
    return 1;
    L40:
    if (keyCode != 42) goto L74;
    if (this.typed.isEmpty() !=0) goto L72;
    this.typed.setLength((this.typed.length() - 1));
    L72:
    return 1;
    L74:
    if (this.typed.length() < 16) goto L88;
    return 1;
    L88:
    v4 = 0;
    if (util.GLFW.letter(keyCode) ==0) goto L107;
    v4 = util.GLFW.letter(keyCode);
    goto L148;
    L107:
    if (util.GLFW.digit(keyCode) <0) goto L127;
    v4 = ((c)(48 + util.GLFW.digit(keyCode)));
    goto L148;
    L127:
    if (keyCode != 45) goto L148;
    if ((modifiers & 3) ==0) goto L144;
    goto L146;
    L144:
    L146:
    v4 = 45;
    L148:
    if (v4 ==0) goto L163;
    this.typed.append(v4);
    L163:
    return 1;
}

private void commit() {
    if (this.typed != null) goto L8;
    return;
    L8:
    v1 = this.typed.toString();
    this.typed = null;
    if (v1.length() < 2) goto L39;
    this.setter.accept(v1);
    L39:
    return;
}

