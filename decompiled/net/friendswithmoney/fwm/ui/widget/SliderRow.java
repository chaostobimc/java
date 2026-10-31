// class net/friendswithmoney/fwm/ui/widget/SliderRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field VALUE_W I;
field MIN_TRACK_W I;
field TRACK_H I;
field KNOB_W I;
field KNOB_H I;
field BOX_H I;
field MAX_TYPED I;
field min I;
field max I;
field getter Ljava/util/function/IntSupplier;;
field setter Ljava/util/function/IntConsumer;;
field display Ljava/util/function/IntFunction;;
field parser Lnet/friendswithmoney/fwm/ui/widget/SliderRow$Parser;;
field dragging Z;
field typed Ljava/lang/StringBuilder;;
public void <init>(network.chat.Component label, int min, int max, function.IntSupplier getter, function.IntConsumer setter, function.IntFunction display) {
    return;
}

public void <init>(network.chat.Component label, int min, int max, function.IntSupplier getter, function.IntConsumer setter, function.IntFunction display, ui.widget.SliderRow$Parser parser) {
    this.min = min;
    this.max = max;
    this.getter = getter;
    this.setter = setter;
    this.display = display;
    this.parser = parser;
    return;
}

private int trackX() {
    return this.controlX();
}

private int trackWidth() {
    return Math.max(24, (this.controlWidth() - 52));
}

private int valueX() {
    return ((this.trackX() + this.trackWidth()) + 6);
}

private int valueWidth() {
    return Math.max(20, (this.controlRight() - this.valueX()));
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.trackX();
    v6 = this.trackWidth();
    v7 = (this.y + 12);
    v8 = Math.max(0, Math.min(1, (((f)(this.getter.getAsInt() - this.min)) / ((f)(this.max - this.min)))));
    v9 = Math.round((((f)v6) * v8));
    ctx.fill(v5, v7, (v5 + v6), (v7 + 2), ui.FwmTheme.OFF);
    ctx.fill(v5, v7, (v5 + v9), (v7 + 2), ui.FwmTheme.ACCENT);
    ui.FwmTheme.pill(ctx, ((v5 + v9) - 2), (this.y + 7), 5, 11, ui.FwmTheme.ACCENT);
    if (this.parser != null) goto L190;
    v10 = ((network.chat.Component)this.display.apply(this.getter.getAsInt()));
    ctx.text(font, v10, (this.controlRight() - font.width(v10)), this.textY(font), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L190:
    v10 = this.valueX();
    v11 = this.valueWidth();
    v12 = (this.y + 7);
    if (this.typed == null) goto L222;
    goto L223;
    L222:
    L223:
    v13 = 0;
    if (mouseX < v10) goto L261;
    if (mouseX >= (v10 + v11)) goto L261;
    if (mouseY < v12) goto L261;
    if (mouseY >= (v12 + 12)) goto L261;
    goto L262;
    L261:
    L262:
    v14 = 0;
    if (v13 !=0) goto L286;
    if (v14 ==0) goto L292;
    L286:
    goto L295;
    L292:
    L295:
    ui.FwmTheme.box(v10, v12, v11, 12, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v13 ==0) goto L318;
    goto L344;
    L318:
    L344:
    v15 = ((network.chat.Component)this.display.apply(this.getter.getAsInt())).getString();
    v16 = network.chat.Component.literal(font.plainSubstrByWidth(v15, (v11 - 4)));
    Objects.requireNonNull(font);
    if (v13 !=0) goto L406;
    if (v14 ==0) goto L412;
    L406:
    goto L415;
    L412:
    L415:
    font.text(v16, (v10 + ((v11 - font.width(v16)) / 2)), ((v12 + ((12 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L20;
    L14:
    this.commit();
    return 0;
    L20:
    if (this.parser == null) goto L50;
    if (cmp(mouseX,((d)this.valueX())) <0) goto L50;
    this.typed = ?;
    return 1;
    L50:
    if (cmp(mouseX,((d)(this.trackX() - 5))) <0) goto L79;
    if (cmp(mouseX,((d)((this.trackX() + this.trackWidth()) + 5))) <=0) goto L81;
    L79:
    return 0;
    L81:
    this.commit();
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
    this.dragging = 0;
    return;
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
    if (this.typed.length() < 8) goto L88;
    return 1;
    L88:
    v4 = ui.widget.SliderRow.digit(keyCode);
    if (v4 <0) goto L130;
    if (v4 !=0) goto L114;
    if (this.typed.isEmpty() !=0) goto L128;
    L114:
    this.typed.append(((c)(48 + v4)));
    L128:
    return 1;
    L130:
    v5 = ui.widget.SliderRow.unit(keyCode);
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
    if (v1.isEmpty() !=0) goto L35;
    if (this.parser != null) goto L36;
    L35:
    return;
    L36:
    v2 = this.parser.parse(v1);
    this.setter.accept(((i)Math.max(((l)this.min), Math.min(((l)this.max), v2))));
    return;
}

private void applyFromMouse(double mouseX) {
    v3 = ((mouseX - ((d)this.trackX())) / ((d)this.trackWidth()));
    v3 = Math.max(0, Math.min(1, v3));
    this.setter.accept((this.min + ((i)Math.round((v3 * ((d)(this.max - this.min)))))));
    return;
}

