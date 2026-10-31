// class net/friendswithmoney/fwm/ui/panel/SellStatsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field LINE_H I;
field BTN_H I;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.sellstats", new Object[0]);
}

private boolean lines() {
    v1 = ?;
    v2 = stats.SellStats.perHour();
    v1.add(?);
    if (cmp(v2,0) >=0) goto L76;
    goto L80;
    L76:
    L80:
    ?.add(?);
    v1.add(?);
    v1.add(?);
    v1.add(?);
    v1.add(?);
    v4 = stats.SellStats.lastTest();
    if (cmp(v4,0) <0) goto L270;
    v1.add(?);
    goto L311;
    L270:
    if (stats.SellStats.testAborted() ==0) goto L311;
    v1.add(?);
    L311:
    return v1;
}

private int buttonY() {
    return ((this.y + this.height) - 14);
}

private int testW() {
    return this.width;
}

private int overButton(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L52;
    if (cmp(mouseX,((d)(this.x + this.testW()))) >=0) goto L52;
    if (cmp(v3,((d)this.buttonY())) <0) goto L52;
    if (cmp(v3,((d)(this.buttonY() + 14))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

private void renderButton(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = stats.SellStats.testRunning();
    if (v5 !=0) goto L26;
    if (this.overButton(((d)mouseX), ((d)mouseY)) ==0) goto L26;
    goto L27;
    L26:
    L27:
    v6 = 0;
    if (v5 !=0) goto L57;
    if (v6 ==0) goto L63;
    L57:
    goto L66;
    L63:
    L66:
    ui.FwmTheme.box(this.x, this.buttonY(), this.testW(), 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (stats.SellStats.testSettling() ==0) goto L87;
    goto L122;
    L87:
    if (v5 ==0) goto L113;
    new Object[1][0] = Integer.valueOf(stats.SellStats.testSecondsLeft());
    goto L122;
    L113:
    L122:
    v7 = ui.Lang.of("fwm.stats.minuteTest", new Object[0]);
    v8 = network.chat.Component.literal(font.plainSubstrByWidth(v7.getString(), (this.testW() - 6)));
    Objects.requireNonNull(font);
    if (v5 !=0) goto L198;
    if (v6 ==0) goto L204;
    L198:
    goto L207;
    L204:
    L207:
    font.text(v8, (this.x + ((this.testW() - font.width(v8)) / 2)), ((this.buttonY() + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private void startTest() {
    stats.SellStats.startMinuteTest();
    config.FwmConfig.INSTANCE.enabled = 1;
    config.ConfigManager.save();
    client.Minecraft.getInstance().gui.setScreen(null);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (stats.SellStats.testRunning() !=0) goto L35;
    if (this.overButton(mouseX, mouseY) ==0) goto L35;
    this.startTest();
    L35:
    return 1;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.lines();
    v7 = 0;
    L9:
    if (v7 >= v6.size()) goto L139;
    v8 = (this.y + (v7 * 12));
    if ((v8 + 12) <= (this.buttonY() - 2)) goto L50;
    goto L139;
    L50:
    v9 = ((ui.panel.SellStatsPanel$Line)v6.get(v7));
    ctx.text(font, v9.label(), (this.x + 2), (v8 + 2), ui.FwmTheme.TEXT_DIM, 0);
    v10 = network.chat.Component.literal(v9.value());
    ctx.text(font, v10, (((this.x + this.width) - 2) - font.width(v10)), (v8 + 2), v9.color(), 0);
    v7 = v7 + 1;
    goto L9;
    L139:
    this.renderButton(ctx, font, mouseX, mouseY);
    return;
}

