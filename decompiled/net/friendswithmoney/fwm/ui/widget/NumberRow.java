// class net/friendswithmoney/fwm/ui/widget/NumberRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_BOX_W I;
field BOX_H I;
field MAX_TYPED I;
field getter Ljava/util/function/LongSupplier;;
field setter Ljava/util/function/LongConsumer;;
field display Lnet/friendswithmoney/fwm/ui/widget/NumberRow$Formatter;;
field typed Ljava/lang/StringBuilder;;
field parser Lnet/friendswithmoney/fwm/ui/widget/NumberRow$Parser;;
public void <init>(network.chat.Component label, function.LongSupplier getter, function.LongConsumer setter, ui.widget.NumberRow$Formatter display) {
    return;
}

public void <init>(network.chat.Component label, function.LongSupplier getter, function.LongConsumer setter, ui.widget.NumberRow$Formatter display, ui.widget.NumberRow$Parser parser) {
    this.getter = getter;
    this.setter = setter;
    this.display = display;
    this.parser = parser;
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
    goto L159;
    L136:
    L159:
    v10 = this.display.format(this.getter.getAsLong()).getString();
    v11 = network.chat.Component.literal(font.plainSubstrByWidth(v10, (v5 - 6)));
    Objects.requireNonNull(font);
    if (v8 !=0) goto L222;
    if (v9 ==0) goto L228;
    L222:
    goto L231;
    L228:
    L231:
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

public void commitTyped() {
    this.commit();
    return;
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
    if (this.typed.length() < 13) goto L88;
    return 1;
    L88:
    v4 = ui.widget.NumberRow.digit(keyCode);
    if (v4 <0) goto L130;
    if (v4 !=0) goto L114;
    if (this.typed.isEmpty() !=0) goto L128;
    L114:
    this.typed.append(((c)(48 + v4)));
    L128:
    return 1;
    L130:
    v5 = ui.widget.NumberRow.unit(keyCode);
    if (v5 ==0) goto L183;
    if (this.typed.isEmpty() !=0) goto L183;
    if (Character.isDigit(this.typed.charAt((this.typed.length() - 1))) ==0) goto L183;
    this.typed.append(v5);
    L183:
    return 1;
}

private static int digit(int keyCode) {
    return util.GLFW.digit(keyCode);
}

// DECOMPILE ERROR in unit: 'dict' object has no attribute 'op_type'
private void commit() {
    if (this.typed != null) goto L8;
    return;
    L8:
    v1 = this.typed.toString();
    this.typed = null;
    if (v1.isEmpty() ==0) goto L29;
    return;
    L29:
    this.setter.accept(this.parser.parse(v1));
    return;
}

private static long lambda$new$0(String typed) {
    return (auction.Money.cents(typed) / 100);
}

