// class net/friendswithmoney/fwm/FwmClient  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MOD_VERSION Ljava/lang/String;;
field SELL_CONTROLLER Lnet/friendswithmoney/fwm/controller/SellController;;
public void <init>() {
    return;
}

public void onInitializeClient() {
    config.ConfigManager.load();
    ui.FwmPalette.applyFromConfig();
    ui.Lang.load();
    net.Spawners.load();
    net.Market.load();
    Files.deleteIfExists(loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-reconnect-log.json"));
    goto L38;
    v1 = /*catch Exception*/ ex;
    L38:
    textures.TextureMixer.migrate();
    if (config.FwmConfig.INSTANCE.envelopeOff66 !=0) goto L67;
    config.FwmConfig.INSTANCE.msgShowEnvelope = 0;
    config.FwmConfig.INSTANCE.envelopeOff66 = 1;
    config.ConfigManager.save();
    L67:
    net.TrustedClans.load();
    net.ModAccount.load();
    net.ModBan.register();
    net.Members.load();
    chat.Messages.load();
    input.Keybinds.register();
    ui.FwmHud.register();
    ui.MemberTags.register();
    game.ToolGuard.register();
    game.TpaWatch.register();
    chat.ChatRouter.register();
    chat.SecondChatClicks.register();
    shop.UnitPrice.register();
    game.FightKits.register();
    net.Waypoints.register();
    shop.RealName.register();
    shop.MarketTooltip.register();
    game.InvValue.register();
    game.EcValue.register();
    game.SpawnerSpots.register();
    game.SpawnerSidePanel.register();
    game.AlertSidePanel.register();
    seventv.SevenTv.register();
    fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents.CLIENT_STOPPING.register(/*indy*/onClientStopping());
    fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(/*indy*/onEndTick());
    return;
}

private static void lambda$onInitializeClient$1(client.Minecraft client) {
    v1 = config.FwmConfig.INSTANCE;
    if (config.Features.isOn("fwm.tab.sellmacro") !=0) goto L24;
    if (v1.enabled ==0) goto L24;
    v1.enabled = 0;
    L24:
    if (FwmClient.SELL_CONTROLLER.follow(v1.enabled, client) ==0) goto L44;
    ui.FwmHud.showDeactivated();
    stats.SellStats.cancelTest();
    L44:
    if (v1.enabled ==0) goto L84;
    if (FwmClient.SELL_CONTROLLER.isRunning() ==0) goto L84;
    stats.SellStats.countActiveTick();
    if (stats.SellStats.tickTest() ==0) goto L84;
    v1.enabled = 0;
    FwmClient.SELL_CONTROLLER.disable(client);
    config.ConfigManager.save();
    L84:
    if (stats.SellStats.tickGrace() ==0) goto L104;
    client.gui.setScreen(ui.Design.open(null, "fwm.tab.sellmacro", 1));
    L104:
    FwmClient.SELL_CONTROLLER.onClientTick(client);
    input.Keybinds.handle(client);
    net.Spawners.tick(client);
    net.Members.tick(client);
    net.ModNews.tick(client);
    net.FakeList.tick(client);
    net.Waypoints.tick(client);
    net.Presence.tick(client);
    net.ModId.tick(client);
    net.Giveaways.tick(client);
    net.TrustedClans.tick(client);
    net.Tickets.tick(client);
    ui.FwmTutorial.tick(client);
    chat.Messages.tick(client);
    auction.Auction.tick(client);
    auction.Giveaway.tick(client);
    game.TpaWatch.tick(client);
    net.PricePings.tick(client);
    antileak.AntiLeak.tick(client);
    game.AutoTpa.tick(client);
    game.SpawnerMacro.tick(client);
    money.MoneyEvents.tick(client);
    game.InvValue.tick(client);
    return;
}

private static void lambda$onInitializeClient$0(client.Minecraft c) {
    money.MoneyStore.left();
    money.MoneyStore.save();
    money.History.save();
    return;
}

private static String lambda$static$0(loader.api.ModContainer mod) {
    return mod.getMetadata().getVersion().getFriendlyString();
}

static void <clinit>() {
    FwmClient.MOD_VERSION = ((String)loader.api.FabricLoader.getInstance().getModContainer("fwm").map(/*indy*/apply()).orElse("?"));
    FwmClient.SELL_CONTROLLER = ?;
    return;
}

