// class net/friendswithmoney/fwm/ui/panel/RtpInfoPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field PAD I;
field LINE_H I;
field TOGGLE_W I;
field TOGGLE_H I;
field ROW_H I;
field BTN_H I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field radius Lnet/friendswithmoney/fwm/ui/widget/SliderRow;;
public void <init>(ui.panel.PanelHost host) {
    this.radius = ?;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.rtp", new Object[0]);
}

public boolean settingsCog() {
    return 1;
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    return this.radius.mouseDragged(mouseX, mouseY, v5);
}

public void mouseReleased() {
    this.radius.mouseReleased();
    config.ConfigManager.save();
    return;
}

public boolean isCapturingKeys() {
    return this.radius.isCapturingKeys();
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.radius.isCapturingKeys() ==0) goto L27;
    if (this.radius.keyPressed(keyCode, scanCode, modifiers) ==0) goto L27;
    goto L28;
    L27:
    L28:
    return 0;
}

private int buttonY() {
    return ((this.y + this.height) - 18);
}

private int ownPosY() {
    return ((this.buttonY() - 6) - 18);
}

private int radiusY() {
    return ((this.ownPosY() - 4) - 26);
}

private int minimapY() {
    return (this.radiusY() - 18);
}

private int headingY() {
    return (this.minimapY() - 12);
}

private int boxH() {
    return Math.max(20, ((this.headingY() - 6) - this.y));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.renderNote(ctx, font);
    this.renderMinimap(ctx, font, mouseX, mouseY);
    this.toggle(ctx, font, mouseX, mouseY, this.ownPosY(), "fwm.rtp.ownPos", config.FwmConfig.INSTANCE.rtpShowOwnPos);
    this.renderContinue(ctx, font, mouseX, mouseY);
    return;
}

private void renderNote(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    ui.FwmTheme.box(ctx, this.x, this.y, this.width, this.boxH(), ui.FwmTheme.INSET, ui.FwmTheme.ACCENT_DIM);
    v3 = (this.x + 6);
    v4 = (this.width - 12);
    v5 = ((this.y + this.boxH()) - 6);
    v6 = (this.y + 6);
    ctx.text(font, ui.Lang.of("fwm.rtp.info.head", new Object[0]), v3, v6, ui.FwmTheme.ACCENT, 0);
    v6 = v6 + 13;
    v7 = font.split(ui.Lang.of("fwm.rtp.info.body", new Object[0]), v4);
    v8 = v7.iterator();
    L116:
    if (v8.hasNext() ==0) goto L176;
    v9 = ((util.FormattedCharSequence)v8.next());
    Objects.requireNonNull(font);
    if ((v6 + 9) <= v5) goto L156;
    goto L176;
    L156:
    ctx.text(font, v9, v3, v6, ui.FwmTheme.TEXT, 0);
    v6 = v6 + 10;
    goto L116;
    L176:
    return;
}

private void renderMinimap(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    ctx.text(font, ui.Lang.of("fwm.rtp.minimapHead", new Object[0]), (this.x + 6), (this.headingY() + 2), ui.FwmTheme.ACCENT, 0);
    ui.FwmTheme.divider(ctx, (this.x + 6), (this.headingY() + 11), (this.width - 12));
    this.toggle(ctx, font, mouseX, mouseY, this.minimapY(), "fwm.rtp.minimapShow", config.Features.isOn("fwm.feature.rtpminimap"));
    this.radius.setBounds(this.x, this.radiusY(), this.width);
    this.radius.render(ctx, font, mouseX, mouseY);
    return;
}

private void toggle(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, int rowY, String labelKey, boolean on) {
    if (this.overRow(((d)mouseX), ((d)mouseY), rowY) ==0) goto L30;
    ui.FwmTheme.hover(ctx, this.x, rowY, this.width, 18);
    L30:
    v8 = (((this.x + this.width) - 6) - 22);
    ui.FwmTheme.toggle(ctx, v8, (rowY + 4), 22, 10, ui.widget.Anim.toward(labelKey, on, 120));
    v9 = network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str(labelKey, new Object[0]), (((v8 - this.x) - 6) - 4)));
    Objects.requireNonNull(font);
    if (on ==0) goto L139;
    goto L142;
    L139:
    L142:
    font.text(v9, (this.x + 6), ((rowY + ((18 - 9) / 2)) + 1), ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void renderContinue(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.overContinue(((d)mouseX), ((d)mouseY));
    if (v5 ==0) goto L40;
    goto L43;
    L40:
    L43:
    ui.FwmTheme.box(this.x, this.buttonY(), this.width, 18, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v6 = ui.Lang.of("fwm.rtp.info.continue", new Object[0]);
    Objects.requireNonNull(font);
    if (v5 ==0) goto L110;
    goto L113;
    L110:
    L113:
    font.text(v6, (this.x + ((this.width - font.width(v6)) / 2)), ((this.buttonY() + ((18 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private boolean overRow(double mouseX, double a2, int mouseY) {
    if (cmp(mouseX,((d)this.x)) <0) goto L48;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L48;
    if (cmp(mouseY,((d)v5)) <0) goto L48;
    if (cmp(mouseY,((d)(v5 + 18))) >=0) goto L48;
    goto L49;
    L48:
    L49:
    return 0;
}

private int overContinue(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L52;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L52;
    if (cmp(v3,((d)this.buttonY())) <0) goto L52;
    if (cmp(v3,((d)(this.buttonY() + 18))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.overRow(mouseX, mouseY, this.ownPosY()) ==0) goto L54;
    if (config.FwmConfig.INSTANCE.rtpShowOwnPos !=0) goto L45;
    goto L46;
    L45:
    L46:
    1.rtpShowOwnPos = 0;
    config.ConfigManager.save();
    return 1;
    L54:
    if (this.overRow(mouseX, mouseY, this.minimapY()) ==0) goto L77;
    config.Features.toggle("fwm.feature.rtpminimap");
    config.ConfigManager.save();
    return 1;
    L77:
    if (this.radius.mouseClicked(mouseX, mouseY, v5) ==0) goto L93;
    return 1;
    L93:
    if (this.overContinue(mouseX, mouseY) ==0) goto L120;
    this.host.openSub(?);
    return 1;
    L120:
    return 1;
}

private static network.chat.MutableComponent lambda$new$1(int chunks) {
    new Object[1][0] = Integer.valueOf(chunks);
    return ui.Lang.of("fwm.minimap.chunks", new Object[1]);
}

private static void lambda$new$0(int value) {
    config.FwmConfig.INSTANCE.rtpMiniRadius = value;
    return;
}

