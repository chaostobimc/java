// class net/friendswithmoney/fwm/ui/widget/TextRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field detail Lnet/minecraft/network/chat/Component;;
field heading Z;
public void <init>(network.chat.Component label) {
    return;
}

public void <init>(network.chat.Component label, network.chat.Component detail) {
    return;
}

private void <init>(network.chat.Component label, network.chat.Component detail, boolean heading) {
    this.detail = detail;
    this.heading = heading;
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.textY(font);
    v6 = (this.x + this.padding());
    v7 = (this.width - (2 * this.padding()));
    if (this.heading ==0) goto L70;
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(this.label.getString(), v7)), v6, v5, ui.FwmTheme.ACCENT, 0);
    return;
    L70:
    v8 = this.label.getString();
    ctx.text(font, v8, v6, v5, ui.FwmTheme.TEXT_DIM, 0);
    if (this.detail != null) goto L104;
    return;
    L104:
    v9 = ((v6 + font.width(v8)) + 5);
    v10 = Math.max(8, (((this.x + this.width) - this.padding()) - v9));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(this.detail.getString(), v10)), v9, v5, ui.FwmTheme.TEXT, 0);
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    return;
}

