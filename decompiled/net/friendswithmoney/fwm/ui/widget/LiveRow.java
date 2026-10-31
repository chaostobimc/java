// class net/friendswithmoney/fwm/ui/widget/LiveRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field value Ljava/util/function/Supplier;;
field colour Ljava/util/function/IntSupplier;;
public void <init>(network.chat.Component label, function.Supplier value) {
    return;
}

public void <init>(network.chat.Component label, function.Supplier value, function.IntSupplier colour) {
    this.value = value;
    this.colour = colour;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = ((network.chat.Component)this.value.get());
    v6 = (this.controlRight() - (((this.x + this.padding()) + font.width(this.trimmedLabel(font))) + 8));
    v7 = font.plainSubstrByWidth(v5.getString(), Math.max(10, v6));
    ctx.text(font, network.chat.Component.literal(v7), (this.controlRight() - font.width(v7)), this.textY(font), this.colour.getAsInt(), 0);
    return;
}

private static int lambda$new$0() {
    return ui.FwmTheme.ACCENT;
}

