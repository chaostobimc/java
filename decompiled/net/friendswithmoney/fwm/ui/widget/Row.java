// class net/friendswithmoney/fwm/ui/widget/Row  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field HEIGHT I;
field MAX_CONTROL_WIDTH I;
field MIN_CONTROL_WIDTH I;
field MIN_LABEL_WIDTH I;
field label Lnet/minecraft/network/chat/Component;;
field x I;
field y I;
field width I;
protected void <init>(network.chat.Component label) {
    this.label = label;
    return;
}

public void setBounds(int x, int y, int width) {
    this.x = x;
    this.y = y;
    this.width = width;
    return;
}

protected int padding() {
    if (this.width >= 180) goto L14;
    goto L16;
    L14:
    L16:
    return 10;
}

protected int controlWidth() {
    v1 = ((this.width - (2 * this.padding())) - 40);
    return Math.max(60, Math.min(106, v1));
}

protected int controlX() {
    return (((this.x + this.width) - this.padding()) - this.controlWidth());
}

protected int controlRight() {
    return ((this.x + this.width) - this.padding());
}

protected network.chat.MutableComponent trimmedLabel(client.gui.Font font) {
    v2 = ((this.controlX() - (this.x + this.padding())) - 4);
    if (font.width(this.label) > v2) goto L34;
    return this.label;
    L34:
    return network.chat.Component.literal(font.plainSubstrByWidth(this.label.getString(), Math.max(8, v2)));
}

public boolean isHovered(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L52;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L52;
    if (cmp(v3,((d)this.y)) <0) goto L52;
    if (cmp(v3,((d)(this.y + 26))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    if (this.isHovered(((d)mouseX), ((d)mouseY)) ==0) goto L30;
    ui.FwmTheme.hover(ctx, this.x, this.y, this.width, 26);
    L30:
    ctx.text(font, this.trimmedLabel(font), (this.x + this.padding()), this.textY(font), ui.FwmTheme.TEXT, 0);
    this.renderControl(ctx, font, mouseX, mouseY);
    return;
}

protected Object textY(client.gui.Font font) {
    Objects.requireNonNull(font);
    return ((this.y + ((26 - 9) / 2)) + 1);
}

// renderControl(Lnet.minecraft.client.gui.GuiGraphicsExtractor;Lnet.minecraft.client.gui.Font;II)V  (abstract/native)

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    return 0;
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    return 0;
}

public void mouseReleased() {
    return;
}

public boolean isCapturingKeys() {
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    return 0;
}

public boolean charTyped(client.input.CharacterEvent input) {
    return 0;
}

public void commitTyped() {
    return;
}

