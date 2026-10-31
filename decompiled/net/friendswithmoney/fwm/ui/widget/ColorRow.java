// class net/friendswithmoney/fwm/ui/widget/ColorRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field SWATCH I;
field GAP I;
field TRACK_H I;
field KNOB_W I;
field KNOB_H I;
field MIN_TRACK_W I;
field getter Ljava/util/function/DoubleSupplier;;
field setter Ljava/util/function/DoubleConsumer;;
field gradient Lnet/friendswithmoney/fwm/ui/widget/ColorRow$Gradient;;
field fallback Ljava/util/function/DoubleSupplier;;
field swatch Ljava/util/function/IntSupplier;;
field reset Ljava/lang/Runnable;;
field dragging Z;
public void <init>(network.chat.Component label, function.DoubleSupplier getter, function.DoubleConsumer setter, ui.widget.ColorRow$Gradient gradient, function.DoubleSupplier fallback, function.IntSupplier swatch, Runnable reset) {
    this.getter = getter;
    this.setter = setter;
    this.gradient = gradient;
    this.fallback = fallback;
    this.swatch = swatch;
    this.reset = reset;
    return;
}

private boolean hasSwatch() {
    if (this.reset == null) goto L18;
    if (this.swatch == null) goto L18;
    goto L19;
    L18:
    L19:
    return 0;
}

private int swatchX() {
    return this.controlX();
}

private boolean trackX() {
    if (this.hasSwatch() ==0) goto L16;
    goto L17;
    L16:
    L17:
    return (16 + 0);
}

private int trackWidth() {
    return Math.max(30, (this.controlRight() - this.trackX()));
}

private double shown() {
    v1 = this.getter.getAsDouble();
    if (cmp(v1,0) >=0) goto L33;
    if (this.fallback == null) goto L33;
    v1 = this.fallback.getAsDouble();
    L33:
    return ((f)Math.max(0, Math.min(1, v1)));
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.trackX();
    v6 = this.trackWidth();
    v7 = (this.y + 9);
    v8 = 0;
    L24:
    if (v8 >= v6) goto L79;
    ctx.fill((v5 + v8), v7, ((v5 + v8) + 1), (v7 + 8), (-16777216 | this.gradient.at((((f)v8) / ((f)v6)))));
    v8 = v8 + 1;
    goto L24;
    L79:
    v8 = ((v5 + Math.round((this.shown() * ((f)v6)))) - 2);
    ui.FwmTheme.pill(ctx, v8, (this.y + 7), 5, 12, ui.FwmTheme.TEXT);
    if (this.hasSwatch() !=0) goto L124;
    return;
    L124:
    if (mouseX < this.swatchX()) goto L171;
    if (mouseX >= (this.swatchX() + 12)) goto L171;
    if (mouseY < (this.y + 7)) goto L171;
    if (mouseY >= (this.y + 19)) goto L171;
    goto L172;
    L171:
    L172:
    v9 = 0;
    if (v9 ==0) goto L216;
    goto L219;
    L216:
    L219:
    ui.FwmTheme.box(this.swatchX(), (this.y + 7), 12, 12, (-16777216 | (this.swatch.getAsInt() & 16777215)), ui.FwmTheme.TEXT, ui.FwmTheme.OFF);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.hasSwatch() ==0) goto L60;
    if (cmp(mouseX,((d)this.swatchX())) <0) goto L60;
    if (cmp(mouseX,((d)(this.swatchX() + 12))) >=0) goto L60;
    this.reset.run();
    config.ConfigManager.save();
    return 1;
    L60:
    if (cmp(mouseX,((d)(this.trackX() - 5))) >=0) goto L74;
    return 0;
    L74:
    this.dragging = 1;
    this.applyFromMouse(mouseX);
    return 1;
}

public void mouseDragged(double mouseX, double a2, int mouseY) {
    if (this.dragging !=0) goto L9;
    return 0;
    L9:
    this.applyFromMouse(mouseX);
    return 1;
}

public void mouseReleased() {
    if (this.dragging !=0) goto L8;
    return;
    L8:
    this.dragging = 0;
    config.ConfigManager.save();
    return;
}

private void applyFromMouse(double mouseX) {
    v3 = ((mouseX - ((d)this.trackX())) / ((d)this.trackWidth()));
    this.setter.accept(Math.max(0, Math.min(1, v3)));
    return;
}

