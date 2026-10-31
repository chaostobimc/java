// class net/friendswithmoney/fwm/ui/widget/ToggleRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_PILL_W I;
field PILL_H I;
field KNOB I;
field getter Ljava/util/function/BooleanSupplier;;
field setter Ljava/util/function/Consumer;;
public void <init>(network.chat.Component label, function.BooleanSupplier getter, function.Consumer setter) {
    this.getter = getter;
    this.setter = setter;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.getter.getAsBoolean();
    v6 = Math.min(46, this.controlWidth());
    v7 = (this.controlRight() - v6);
    v8 = (this.y + 6);
    if (v5 ==0) goto L60;
    goto L63;
    L60:
    L63:
    ui.FwmTheme.pill(v7, v8, v6, 14, ui.FwmTheme.ON, ui.FwmTheme.OFF);
    if (v5 ==0) goto L84;
    goto L88;
    L84:
    L88:
    v9 = (v7 + 2);
    if (v5 ==0) goto L111;
    goto L114;
    L111:
    L114:
    ui.FwmTheme.pill(v9, (v8 + 2), 10, 10, -16375537, ui.FwmTheme.TEXT);
    if (v5 ==0) goto L127;
    goto L129;
    L127:
    L129:
    v10 = ui.Lang.of("fwm.state.off", new Object[0]);
    v11 = ((v6 - 10) - 4);
    if (v5 ==0) goto L159;
    goto L164;
    L159:
    L164:
    v12 = ((v9 + 10) + ((v11 - font.width(v10)) / 2));
    Objects.requireNonNull(font);
    if (v5 ==0) goto L211;
    goto L214;
    L211:
    L214:
    font.text(v10, v12, ((v8 + ((14 - 9) / 2)) + 1), -16375537, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.getter.getAsBoolean() !=0) goto L36;
    goto L37;
    L36:
    L37:
    1.accept(Boolean.valueOf(0));
    return 1;
}

