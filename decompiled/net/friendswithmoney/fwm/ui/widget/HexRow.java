// class net/friendswithmoney/fwm/ui/widget/HexRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field BOX_W I;
field BOX_H I;
field getter Ljava/util/function/IntSupplier;;
field setter Ljava/util/function/IntConsumer;;
field typed Ljava/lang/StringBuilder;;
public void <init>(network.chat.Component label, function.IntSupplier getter, function.IntConsumer setter) {
    this.getter = getter;
    this.setter = setter;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = (this.controlRight() - 64);
    v6 = (this.y + 6);
    if (mouseX < (v5 - 16)) goto L57;
    if (mouseX >= (v5 + 64)) goto L57;
    if (mouseY < v6) goto L57;
    if (mouseY >= (v6 + 14)) goto L57;
    goto L58;
    L57:
    L58:
    v7 = 0;
    v8 = (this.getter.getAsInt() & 16777215);
    ctx.fill((v5 - 16), (v6 + 1), (v5 - 4), ((v6 + 14) - 1), (-16777216 | v8));
    if (this.typed != null) goto L127;
    if (v7 ==0) goto L133;
    L127:
    goto L136;
    L133:
    L136:
    ui.FwmTheme.box(v5, v6, 64, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (this.typed == null) goto L161;
    goto L178;
    L161:
    new Object[1][0] = Integer.valueOf(v8);
    L178:
    v9 = String.format("#%06X", new Object[1]);
    if (this.typed == null) goto L218;
    goto L221;
    L218:
    L221:
    font.text(network.chat.Component.literal(v9), (v5 + ((64 - font.width(v9)) / 2)), (v6 + 4), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    v6 = (this.controlRight() - 64);
    v7 = (this.y + 6);
    if (v5 !=0) goto L77;
    if (cmp(mouseX,((d)(v6 - 16))) <0) goto L77;
    if (cmp(mouseX,((d)(v6 + 64))) >=0) goto L77;
    if (cmp(mouseY,((d)v7)) <0) goto L77;
    if (cmp(mouseY,((d)(v7 + 14))) >=0) goto L77;
    this.typed = ?;
    return 1;
    L77:
    this.commit();
    return 0;
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
    if (keyCode == 40) goto L40;
    if (keyCode == 88) goto L40;
    if (keyCode != 43) goto L46;
    L40:
    this.commit();
    return 1;
    L46:
    if (keyCode != 42) goto L80;
    if (this.typed.isEmpty() !=0) goto L78;
    this.typed.setLength((this.typed.length() - 1));
    L78:
    return 1;
    L80:
    if (this.typed.length() < 6) goto L94;
    return 1;
    L94:
    v4 = 0;
    if (util.GLFW.digit(keyCode) <0) goto L117;
    v4 = ((c)(48 + util.GLFW.digit(keyCode)));
    goto L141;
    L117:
    if (util.GLFW.letter(keyCode) < 65) goto L141;
    if (util.GLFW.letter(keyCode) > 70) goto L141;
    v4 = util.GLFW.letter(keyCode);
    L141:
    if (v4 ==0) goto L156;
    this.typed.append(v4);
    L156:
    return 1;
}

public void commitTyped() {
    this.commit();
    return;
}

private void commit() {
    if (this.typed != null) goto L8;
    return;
    L8:
    v1 = this.typed.toString();
    this.typed = null;
    if (v1.length() != 3) goto L65;
    v1 = v1.charAt(0) + v1.charAt(0) + v1.charAt(1) + v1.charAt(1) + v1.charAt(2) + v1.charAt(2);
    L65:
    if (v1.length() != 6) goto L89;
    this.setter.accept(Integer.parseInt(v1, 16));
    L89:
    return;
}

