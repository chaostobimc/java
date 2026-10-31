// class net/friendswithmoney/fwm/ui/panel/Panel  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field x I;
field y I;
field width I;
field height I;
public void <init>() {
    return;
}

// title()Lnet.minecraft.network.chat.Component;  (abstract/native)

public boolean showTitle() {
    return 1;
}

public boolean settingsCog() {
    return 0;
}

public String headerSwitch() {
    return null;
}

public final void setBounds(int x, int y, int width, int height) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
    this.onResize();
    return;
}

protected void onResize() {
    return;
}

// render(Lnet.minecraft.client.gui.GuiGraphicsExtractor;Lnet.minecraft.client.gui.Font;IIF)V  (abstract/native)

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    return 0;
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    return 0;
}

public void mouseReleased() {
    return;
}

public boolean mouseScrolled(double mouseX, double a2, double mouseY) {
    return 0;
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

public void onHidden() {
    return;
}

protected boolean contains(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L54;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L54;
    if (cmp(v3,((d)this.y)) <0) goto L54;
    if (cmp(v3,((d)(this.y + this.height))) >=0) goto L54;
    goto L55;
    L54:
    L55:
    return 0;
}

