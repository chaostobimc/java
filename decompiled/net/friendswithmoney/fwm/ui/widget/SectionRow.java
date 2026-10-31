// class net/friendswithmoney/fwm/ui/widget/SectionRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field open Ljava/util/function/BooleanSupplier;;
field toggle Ljava/lang/Runnable;;
field hint Ljava/lang/String;;
public void <init>(network.chat.Component label, String hint, function.BooleanSupplier open, Runnable toggle) {
    this.hint = hint;
    this.open = open;
    this.toggle = toggle;
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.open.getAsBoolean();
    v6 = this.isHovered(((d)mouseX), ((d)mouseY));
    if (v5 ==0) goto L50;
    goto L64;
    L50:
    if (v6 ==0) goto L61;
    goto L64;
    L61:
    L64:
    if (v5 !=0) goto L74;
    if (v6 ==0) goto L80;
    L74:
    goto L83;
    L80:
    L83:
    ui.FwmTheme.box(this.width, 22, ui.FwmTheme.RAISED, ui.FwmTheme.HOVER, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    if (v5 ==0) goto L96;
    goto L98;
    L96:
    L98:
    v7 = "\u25b6";
    v8 = (this.y + 9);
    ctx.text(font, network.chat.Component.literal(v7), (this.x + 7), v8, ui.FwmTheme.ACCENT, 0);
    ctx.text(font, this.label.copy().withStyle(ChatFormatting.BOLD), (this.x + 19), v8, ui.FwmTheme.TEXT, 0);
    if (this.hint == null) goto L265;
    if (this.hint.isEmpty() !=0) goto L265;
    v9 = (((this.width - 19) - font.width(this.label.copy().withStyle(ChatFormatting.BOLD))) - 20);
    v10 = font.plainSubstrByWidth(this.hint, Math.max(0, v9));
    ctx.text(font, network.chat.Component.literal(v10), (((this.x + this.width) - 8) - font.width(v10)), v8, ui.FwmTheme.TEXT_DIM, 0);
    L265:
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    this.toggle.run();
    return 1;
}

