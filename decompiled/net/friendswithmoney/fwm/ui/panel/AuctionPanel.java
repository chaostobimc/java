// class net/friendswithmoney/fwm/ui/panel/AuctionPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field DURATIONS Ljava/util/List;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field builtFor Ljava/lang/String;;
public void <init>(ui.panel.PanelHost host) {
    this.builtFor = "";
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.auction", new Object[0]);
}

private static String signature() {
    v0 = ?;
    v0.append(auction.Auction.running()).append(auction.Auction.hasResult()).append(auction.Auction.itemMissing()).append(auction.Auction.bidCount());
    return v0.toString();
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.builtFor.equals(ui.panel.AuctionPanel.signature()) !=0) goto L40;
    if (this.isCapturingKeys() !=0) goto L40;
    this.setBounds(this.x, this.y, this.width, this.height);
    L40:
    this.render(ctx, font, mouseX, mouseY, delta);
    return;
}

protected void buildRows() {
    this.builtFor = ui.panel.AuctionPanel.signature();
    if (auction.Auction.running() ==0) goto L20;
    this.running();
    goto L37;
    L20:
    if (auction.Auction.hasResult() ==0) goto L33;
    this.result();
    goto L37;
    L33:
    this.setup();
    L37:
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
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private void start() {
    v1 = config.FwmConfig.INSTANCE;
    if (auction.Auction.item().isEmpty() ==0) goto L34;
    this.host.openSub(?);
    return;
    L34:
    auction.Auction.start(auction.Auction.item(), v1.auctionSeconds, (v1.auctionStartBid * 100), 0);
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

private boolean running() {
    this.rows.add(?);
    this.rows.add(?);
    if (auction.Auction.itemMissing() ==0) goto L120;
    this.rows.add(?);
    L120:
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    v1 = '/pay ' + ui.FwmGfx.playerName() + ' ';
    this.rows.add(?);
    return;
}

private boolean result() {
    v1 = auction.Auction.leader();
    if (v1 != null) goto L28;
    goto L37;
    L28:
    L37:
    if (v1 != null) goto L48;
    goto L63;
    L48:
    L63:
    ?.add(ui.Lang.t("Nicht verkauft \u2013 es gab keine Gebote.", "Not sold \u2013 there were no bids."));
    if (v1 == null) goto L229;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    L229:
    this.rows.add(?);
    return;
}

private static network.chat.MutableComponent itemCaption() {
    v0 = auction.Auction.item();
    if (v0.isEmpty() ==0) goto L22;
    return ui.Lang.of("fwm.auction.pick", new Object[0]);
    L22:
    if (v0.getCount() <= 1) goto L42;
    goto L44;
    L42:
    L44:
    return network.chat.Component.literal("" + v0.getHoverName().getString());
}

private static network.chat.MutableComponent lambda$result$7() {
    return network.chat.Component.literal(ui.Lang.t("Schlie\u00dfen", "Close"));
}

private void lambda$result$5(String winner) {
    this.confirm.ask(network.chat.Component.literal(ui.Lang.t('Gewinner ' + winner + ' per /tpahere zu dir holen?', 'Pull winner ' + winner + ' over with /tpahere?')), "fwm.confirm.send", /*indy*/run(winner));
    return;
}

private static boolean lambda$result$6(String winner) {
    auction.StreamerStore.command('tpahere ' + winner);
    return;
}

private static network.chat.MutableComponent lambda$result$4(String winner) {
    return network.chat.Component.literal('/tpahere ' + winner);
}

private static boolean lambda$result$3(String winner) {
    auction.StreamerStore.command('tpa ' + winner);
    return;
}

private static network.chat.MutableComponent lambda$result$2(String winner) {
    return network.chat.Component.literal('/tpa ' + winner);
}

private static void lambda$result$1(String winner) {
    auction.StreamerStore.copy(winner);
    return;
}

private static network.chat.MutableComponent lambda$result$0() {
    return network.chat.Component.literal(ui.Lang.t("Name kopieren", "Copy name"));
}

private static void lambda$running$10(String pay) {
    auction.StreamerStore.copy(pay);
    return;
}

private static network.chat.MutableComponent lambda$running$9() {
    return network.chat.Component.literal(ui.Lang.t("Kopieren", "Copy"));
}

private static network.chat.MutableComponent lambda$running$8() {
    return network.chat.Component.literal(util.Fmt.dollars(auction.Auction.nextMinimum()));
}

private static network.chat.MutableComponent lambda$running$7() {
    if (auction.Auction.leader() != null) goto L19;
    goto L33;
    L19:
    L33:
    return network.chat.Component.literal(auction.Auction.leader() + '  ' + util.Fmt.dollars(auction.Auction.price()));
}

private static network.chat.MutableComponent lambda$running$6() {
    return network.chat.Component.literal(auction.Times.clock(((i)((auction.Auction.remainingMs() + 999) / 1000))));
}

private static network.chat.MutableComponent lambda$running$5() {
    if (auction.Auction.item().getCount() <= 1) goto L24;
    goto L26;
    L24:
    L26:
    return network.chat.Component.literal("" + auction.Auction.item().getHoverName().getString());
}

private static int lambda$running$4() {
    return ui.FwmTheme.DISABLED_TEXT;
}

private static network.chat.MutableComponent lambda$running$3() {
    return network.chat.Component.literal(ui.Lang.t("Item fehlt im Inventar!", "Item missing from inventory!"));
}

private static network.chat.MutableComponent lambda$running$2() {
    return ui.Lang.of("fwm.auction.stop", new Object[0]);
}

private static void lambda$running$1() {
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

private static network.chat.MutableComponent lambda$running$0() {
    return network.chat.Component.literal(ui.Lang.t("\u00d6ffnen", "Open"));
}

private static network.chat.MutableComponent lambda$setup$18(int value) {
    return network.chat.Component.literal('+' + value + ' s');
}

private static void lambda$setup$17(config.FwmConfig config, int value) {
    config.auctionAntiSnipeSeconds = value;
    config.ConfigManager.save();
    return;
}

private static int lambda$setup$16(config.FwmConfig config) {
    return config.auctionAntiSnipeSeconds;
}

private static void lambda$setup$15(config.FwmConfig config, Boolean value) {
    config.auctionHudCard = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$setup$14(config.FwmConfig config) {
    return config.auctionHudCard;
}

private static void lambda$setup$13(config.FwmConfig config, Boolean value) {
    config.auctionAntiSnipe = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$setup$12(config.FwmConfig config) {
    return config.auctionAntiSnipe;
}

private static void lambda$setup$11(config.FwmConfig config, Boolean value) {
    config.auctionStackPayments = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$setup$10(config.FwmConfig config) {
    return config.auctionStackPayments;
}

private static network.chat.MutableComponent lambda$setup$9(long value) {
    return network.chat.Component.literal(util.Fmt.dollars((value * 100)));
}

private static void lambda$setup$8(config.FwmConfig config, long value) {
    config.auctionStartBid = value;
    config.ConfigManager.save();
    return;
}

private static long lambda$setup$7(config.FwmConfig config) {
    return config.auctionStartBid;
}

private static void lambda$setup$6(config.FwmConfig config, int value) {
    config.auctionSeconds = value;
    config.ConfigManager.save();
    return;
}

private static int lambda$setup$5(config.FwmConfig config) {
    return config.auctionSeconds;
}

private static network.chat.MutableComponent lambda$setup$4(int seconds) {
    return network.chat.Component.literal(auction.Times.clock(seconds));
}

private static void lambda$setup$3(config.FwmConfig config, int value) {
    config.auctionSeconds = value;
    config.ConfigManager.save();
    return;
}

private static int lambda$setup$2(config.FwmConfig config) {
    return config.auctionSeconds;
}

private void lambda$setup$1() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$setup$0() {
    return ui.Lang.of("fwm.auction.start", new Object[0]);
}

static List <clinit>() {
    ui.panel.AuctionPanel.DURATIONS = List.of(?, ?, ?, ?, ?);
    return;
}

