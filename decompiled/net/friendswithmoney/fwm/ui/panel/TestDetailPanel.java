// class net/friendswithmoney/fwm/ui/panel/TestDetailPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LINE_H I;
field SLIDER_H I;
field TRACK_H I;
field KNOB_W I;
field KNOB_H I;
field MIN_TENTHS I;
field MAX_TENTHS I;
field run Lnet/friendswithmoney/fwm/config/TestRun;;
field tenths I;
field dragging Z;
public int <init>(config.TestRun run) {
    this.run = run;
    v2 = run.measuredMultiplier();
    if (cmp(v2,0) >=0) goto L26;
    goto L38;
    L26:
    L38:
    20.tenths = ui.panel.TestDetailPanel.clamp(((i)Math.round((v2 * 10.0))));
    return;
}

private static int clamp(int value) {
    return Math.max(10, Math.min(30, value));
}

private double multiplier() {
    return (((d)this.tenths) / 10.0);
}

public network.chat.MutableComponent title() {
    if (this.run.name != null) goto L22;
    goto L29;
    L22:
    L29:
    return network.chat.Component.literal(this.run.name);
}

public int render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = 0;
    v6 = this.row(ctx, font, v6, ui.Lang.str("fwm.test.measured", new Object[0]), util.Fmt.compactMoneyUp(this.run.cents), ui.FwmTheme.ACCENT);
    v6 = this.row(ctx, font, v6, ui.Lang.str("fwm.test.base", new Object[0]), util.Fmt.compactMoneyUp(this.run.baseCents), ui.FwmTheme.TEXT);
    v6 = this.row(ctx, font, v6, ui.Lang.str("fwm.stats.items", new Object[0]), util.Fmt.compact(((l)this.run.items)), ui.FwmTheme.TEXT);
    v7 = ((this.y + (v6 * 12)) + 4);
    this.renderSlider(ctx, font, v7);
    v8 = Math.round((((d)this.run.baseCents) * this.multiplier()));
    v10 = (v6 + 2);
    ui.FwmTheme.divider(ctx, this.x, ((v7 + 16) + 2), this.width);
    v10 = this.row(ctx, font, v10, ui.Lang.str("fwm.test.perMinute", new Object[0]), util.Fmt.compactMoneyUp(v8), ui.FwmTheme.ACCENT);
    this.row(ctx, font, v10, ui.Lang.str("fwm.test.perHour", new Object[0]), util.Fmt.compactMoneyUp((v8 * 60)), ui.FwmTheme.ACCENT);
    return;
}

private void row(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int line, String label, String value, int color) {
    v7 = (this.y + (line * 12));
    ctx.text(font, network.chat.Component.literal(label), (this.x + 2), (v7 + 2), ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(value), (((this.x + this.width) - 2) - font.width(value)), (v7 + 2), color, 0);
    return (line + 1);
}

private int trackX() {
    return (this.x + 60);
}

private int trackW() {
    return Math.max(20, ((this.width - 60) - 46));
}

private void renderSlider(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int sliderY) {
    v4 = ui.Lang.of("fwm.test.multi", new Object[0]);
    Objects.requireNonNull(font);
    ctx.text(font, v4, (this.x + 2), ((sliderY + ((16 - 9) / 2)) + 1), ui.FwmTheme.TEXT_DIM, 0);
    v5 = (sliderY + 7);
    v6 = Math.round((((f)this.trackW()) * this.progress()));
    ctx.fill(this.trackX(), v5, (this.trackX() + this.trackW()), (v5 + 2), ui.FwmTheme.OFF);
    ctx.fill(this.trackX(), v5, (this.trackX() + v6), (v5 + 2), ui.FwmTheme.ACCENT);
    ui.FwmTheme.pill(ctx, ((this.trackX() + v6) - 2), (sliderY + 2), 5, 11, ui.FwmTheme.ACCENT);
    new Object[1][0] = Double.valueOf(this.multiplier());
    v7 = network.chat.Component.literal(String.format(Locale.ROOT, "x%.1f", new Object[1]));
    Objects.requireNonNull(font);
    ctx.text(font, v7, (((this.x + this.width) - 2) - font.width(v7)), ((sliderY + ((16 - 9) / 2)) + 1), ui.FwmTheme.TEXT, 0);
    return;
}

private float progress() {
    return (((f)(this.tenths - 10)) / 20.0);
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (cmp(mouseX,((d)(this.trackX() - 5))) <0) goto L55;
    if (cmp(mouseX,((d)((this.trackX() + this.trackW()) + 5))) >0) goto L55;
    this.dragging = 1;
    this.applyFromMouse(mouseX);
    L55:
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

private long applyFromMouse(double mouseX) {
    v3 = ((mouseX - ((d)this.trackX())) / ((d)this.trackW()));
    v3 = Math.max(0, Math.min(1, v3));
    this.tenths = (10 + ((i)Math.round((v3 * 20.0))));
    return;
}

