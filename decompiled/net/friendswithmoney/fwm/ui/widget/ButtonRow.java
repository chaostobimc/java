// class net/friendswithmoney/fwm/ui/widget/ButtonRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_BTN_W I;
field BTN_H I;
field caption Ljava/util/function/Supplier;;
field action Ljava/lang/Runnable;;
public void <init>(network.chat.Component label, function.Supplier caption, Runnable action) {
    this.caption = caption;
    this.action = action;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = Math.min(96, this.controlWidth());
    v6 = (this.controlRight() - v5);
    v7 = (this.y + 6);
    if (mouseX < v6) goto L65;
    if (mouseX >= (v6 + v5)) goto L65;
    if (mouseY < v7) goto L65;
    if (mouseY >= (v7 + 14)) goto L65;
    goto L66;
    L65:
    L66:
    v8 = 0;
    if (v8 ==0) goto L91;
    goto L94;
    L91:
    L94:
    ui.FwmTheme.box(v6, v7, v5, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v9 = network.chat.Component.literal(font.plainSubstrByWidth(((network.chat.Component)this.caption.get()).getString(), (v5 - 6)));
    Objects.requireNonNull(font);
    if (v8 ==0) goto L174;
    goto L177;
    L174:
    L177:
    font.text(v9, (v6 + ((v5 - font.width(v9)) / 2)), ((v7 + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    this.action.run();
    return 1;
}

