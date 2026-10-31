// class net/friendswithmoney/fwm/ui/panel/GiveawayPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field MIN_SECONDS I;
field MAX_SECONDS I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field builtFor Ljava/lang/String;;
field cancelArmedAt J;
public void <init>(ui.panel.PanelHost host) {
    this.builtFor = "";
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.giveaway", new Object[0]);
}

private static String signature() {
    return String.valueOf(auction.Giveaway.status()) + ':' + auction.Giveaway.participants().size() + ':' + auction.Giveaway.drawing();
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.builtFor.equals(ui.panel.GiveawayPanel.signature()) !=0) goto L40;
    if (this.isCapturingKeys() !=0) goto L40;
    this.setBounds(this.x, this.y, this.width, this.height);
    L40:
    this.render(ctx, font, mouseX, mouseY, delta);
    return;
}

protected void buildRows() {
    this.builtFor = ui.panel.GiveawayPanel.signature();
    // switch (ui.panel.GiveawayPanel$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[auction.Giveaway.status().ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=41), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=34)]
    L44:
    this.running();
    goto L62;
    L51:
    this.ended();
    goto L62;
    L58:
    this.setup();
    L62:
    return;
}

private boolean setup() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private void start() {
    v1 = config.FwmConfig.INSTANCE;
    if (auction.Giveaway.item().isEmpty() ==0) goto L44;
    this.host.openSub(?);
    return;
    L44:
    auction.Giveaway.start(auction.Giveaway.item(), v1.giveawayWord, 2147483647, v1.giveawaySeconds);
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

private void running() {
    if (auction.Giveaway.status() != auction.Giveaway$Status.PAUSED) goto L13;
    goto L14;
    L13:
    L14:
    v1 = 0;
    this.rows.add(?);
    this.rows.add(?);
    if (v1 ==0) goto L113;
    goto L122;
    L113:
    L122:
    ?.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    v2 = auction.Giveaway.joinCommand();
    this.rows.add(?);
    this.participantRows();
    return;
}

private void ended() {
    v1 = auction.Giveaway.winner();
    if (v1 == null) goto L42;
    this.rows.add(?);
    this.rows.add(?);
    L42:
    if (auction.Giveaway.drawing() ==0) goto L53;
    this.participantRows();
    return;
    L53:
    if (v1 != null) goto L77;
    goto L86;
    L77:
    L86:
    if (v1 != null) goto L97;
    goto L101;
    L97:
    L101:
    ?.add(ui.Lang.t("Niemand hat teilgenommen.", "Nobody took part."));
    if (v1 == null) goto L267;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    L267:
    this.rows.add(?);
    this.participantRows();
    return;
}

private boolean participantRows() {
    v1 = auction.Giveaway.participants();
    if (v1.isEmpty() ==0) goto L45;
    goto L54;
    L45:
    L54:
    ?.add(?);
    v2 = 0;
    L68:
    if (v2 >= v1.size()) goto L138;
    v3 = ((auction.Giveaway$Participant)v1.get(v2));
    this.rows.add(?);
    v2 = v2 + 1;
    goto L68;
    L138:
    return;
}

private static String ago(long time) {
    v2 = Math.max(0, ((System.currentTimeMillis() - time) / 1000));
    if (cmp(v2,60) >=0) goto L38;
    return ui.Lang.t('vor ' + v2 + ' s', v2 + ' s ago');
    L38:
    if (cmp(v2,3600) >=0) goto L70;
    return ui.Lang.t('vor ' + (v2 / 60) + ' Min', (v2 / 60) + ' min ago');
    L70:
    return ui.Lang.t('vor ' + (v2 / 3600) + ' Std', (v2 / 3600) + ' h ago');
}

private static network.chat.MutableComponent itemCaption() {
    v0 = auction.Giveaway.item();
    if (v0.isEmpty() ==0) goto L22;
    return ui.Lang.of("fwm.auction.pick", new Object[0]);
    L22:
    if (v0.getCount() <= 1) goto L42;
    goto L44;
    L42:
    L44:
    return network.chat.Component.literal("" + v0.getHoverName().getString());
}

private static int lambda$participantRows$1() {
    return ui.FwmTheme.TEXT_DIM;
}

private static network.chat.MutableComponent lambda$participantRows$0(auction.Giveaway$Participant p) {
    return network.chat.Component.literal(ui.panel.GiveawayPanel.ago(p.joinedAt()));
}

private static network.chat.MutableComponent lambda$ended$7() {
    return network.chat.Component.literal(ui.Lang.t("Schlie\u00dfen", "Close"));
}

private void lambda$ended$5(String winner) {
    this.confirm.ask(network.chat.Component.literal(ui.Lang.t('Gewinner ' + winner + ' per /tpahere zu dir holen?', 'Pull winner ' + winner + ' over with /tpahere?')), "fwm.confirm.send", /*indy*/run(winner));
    return;
}

private static boolean lambda$ended$6(String winner) {
    auction.StreamerStore.command('tpahere ' + winner);
    return;
}

private static network.chat.MutableComponent lambda$ended$4(String winner) {
    return network.chat.Component.literal('/tpahere ' + winner);
}

private static boolean lambda$ended$3(String winner) {
    auction.StreamerStore.command('tpa ' + winner);
    return;
}

private static network.chat.MutableComponent lambda$ended$2(String winner) {
    return network.chat.Component.literal('/tpa ' + winner);
}

private static void lambda$ended$1(String winner) {
    auction.StreamerStore.copy(winner);
    return;
}

private static network.chat.MutableComponent lambda$ended$0() {
    return network.chat.Component.literal(ui.Lang.t("Name kopieren", "Copy name"));
}

private static void lambda$running$13(String join) {
    auction.StreamerStore.copy(join);
    return;
}

private static network.chat.MutableComponent lambda$running$12() {
    return network.chat.Component.literal(ui.Lang.t("Kopieren", "Copy"));
}

private static network.chat.MutableComponent lambda$running$11() {
    return network.chat.Component.literal(String.valueOf(auction.Giveaway.participants().size()));
}

private static network.chat.MutableComponent lambda$running$10() {
    return network.chat.Component.literal(auction.Times.clock(((i)((auction.Giveaway.remainingMs() + 999) / 1000))));
}

private static network.chat.MutableComponent lambda$running$9() {
    if (auction.Giveaway.item().getCount() <= 1) goto L24;
    goto L26;
    L24:
    L26:
    return network.chat.Component.literal("" + auction.Giveaway.item().getHoverName().getString());
}

private long lambda$running$8() {
    if (cmp((System.currentTimeMillis() - this.cancelArmedAt),3000) >=0) goto L21;
    auction.Giveaway.cancel();
    goto L28;
    L21:
    this.cancelArmedAt = System.currentTimeMillis();
    L28:
    return;
}

private network.chat.MutableComponent lambda$running$7() {
    if (cmp((System.currentTimeMillis() - this.cancelArmedAt),3000) >=0) goto L27;
    goto L36;
    L27:
    L36:
    return network.chat.Component.literal(ui.Lang.t("Abbrechen", "Cancel"));
}

private static network.chat.MutableComponent lambda$running$6() {
    return network.chat.Component.literal(ui.Lang.t("Jetzt ziehen", "Draw now"));
}

private static void lambda$running$5(boolean paused) {
    if (paused ==0) goto L10;
    auction.Giveaway.resume();
    goto L13;
    L10:
    auction.Giveaway.pause();
    L13:
    return;
}

private static network.chat.MutableComponent lambda$running$4(boolean paused) {
    if (paused ==0) goto L16;
    goto L25;
    L16:
    L25:
    return network.chat.Component.literal(ui.Lang.t("Pausieren", "Pause"));
}

private static int lambda$running$3(boolean paused) {
    if (paused ==0) goto L10;
    goto L13;
    L10:
    L13:
    return ui.FwmTheme.ACCENT;
}

private static network.chat.MutableComponent lambda$running$2(boolean paused) {
    if (paused ==0) goto L16;
    goto L19;
    L16:
    L19:
    return network.chat.Component.literal("LIVE");
}

private static void lambda$running$1() {
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

private static network.chat.MutableComponent lambda$running$0() {
    return network.chat.Component.literal(ui.Lang.t("\u00d6ffnen", "Open"));
}

private static void lambda$setup$8(config.FwmConfig config, int value) {
    config.giveawaySeconds = value;
    config.ConfigManager.save();
    return;
}

private static int lambda$setup$7(config.FwmConfig config) {
    return config.giveawaySeconds;
}

private static network.chat.MutableComponent lambda$setup$6(int seconds) {
    return network.chat.Component.literal(auction.Times.clock(seconds));
}

private static void lambda$setup$5(config.FwmConfig config, int value) {
    config.giveawaySeconds = value;
    config.ConfigManager.save();
    return;
}

private static int lambda$setup$4(config.FwmConfig config) {
    return config.giveawaySeconds;
}

private static void lambda$setup$3(config.FwmConfig config, String value) {
    config.giveawayWord = value;
    config.ConfigManager.save();
    return;
}

private static String lambda$setup$2(config.FwmConfig config) {
    return config.giveawayWord;
}

private void lambda$setup$1() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$setup$0() {
    return network.chat.Component.literal(ui.Lang.t("Starten", "Start"));
}

