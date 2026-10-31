// class net/friendswithmoney/fwm/input/Keybinds  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field CATEGORY Lnet/minecraft/client/KeyMapping$Category;;
field TOGGLE Lnet/minecraft/client/KeyMapping;;
field OPEN_MENU Lnet/minecraft/client/KeyMapping;;
field OPEN_MESSAGES Lnet/minecraft/client/KeyMapping;;
field OPEN_AUCTION Lnet/minecraft/client/KeyMapping;;
field OPEN_GIVEAWAY Lnet/minecraft/client/KeyMapping;;
field SPAWNER_MACRO Lnet/minecraft/client/KeyMapping;;
field WATCH_INTERVAL I;
field watch I;
field restored Z;
private void <init>() {
    return;
}

public static void register() {
    if (input.Keybinds.TOGGLE == null) goto L7;
    return;
    L7:
    input.Keybinds.TOGGLE = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.OPEN_MENU = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.OPEN_MESSAGES = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.OPEN_AUCTION = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.OPEN_GIVEAWAY = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.SPAWNER_MACRO = fabric.api.client.keymapping.v1.KeyMappingHelper.registerKeyMapping(?);
    input.Keybinds.registerContainerScreenHook();
    return;
}

private static void registerContainerScreenHook() {
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static void restore() {
    v0 = config.FwmConfig.INSTANCE;
    v1 = ((((input.Keybinds.apply(input.Keybinds.OPEN_MENU, v0.menuKeyBinding) | input.Keybinds.apply(input.Keybinds.TOGGLE, v0.macroKeyBinding)) | input.Keybinds.apply(input.Keybinds.OPEN_MESSAGES, v0.messagesKeyBinding)) | input.Keybinds.apply(input.Keybinds.OPEN_AUCTION, v0.auctionKeyBinding)) | input.Keybinds.apply(input.Keybinds.OPEN_GIVEAWAY, v0.giveawayKeyBinding));
    if (v1 ==0) goto L75;
    client.KeyMapping.resetMapping();
    client.Minecraft.getInstance().options.save();
    L75:
    input.Keybinds.remember();
    return;
}

private static void apply(client.KeyMapping binding, String wanted) {
    if (binding == null) goto L15;
    if (wanted == null) goto L15;
    if (wanted.isBlank() ==0) goto L17;
    L15:
    return 0;
    L17:
    if (wanted.equals(binding.saveString()) ==0) goto L30;
    return 0;
    L30:
    v2 = blaze3d.platform.InputConstants.getKey(wanted);
    if (v2 != null) goto L41;
    return 0;
    L41:
    binding.setKey(v2);
    return 1;
}

public static void remember() {
    v0 = config.FwmConfig.INSTANCE;
    if (input.Keybinds.OPEN_MENU != null) goto L15;
    goto L21;
    L15:
    L21:
    v1 = input.Keybinds.OPEN_MENU.saveString();
    if (input.Keybinds.TOGGLE != null) goto L33;
    goto L39;
    L33:
    L39:
    v2 = input.Keybinds.TOGGLE.saveString();
    if (input.Keybinds.OPEN_MESSAGES != null) goto L51;
    goto L57;
    L51:
    L57:
    v3 = input.Keybinds.OPEN_MESSAGES.saveString();
    if (input.Keybinds.OPEN_AUCTION != null) goto L69;
    goto L75;
    L69:
    L75:
    v4 = input.Keybinds.OPEN_AUCTION.saveString();
    if (input.Keybinds.OPEN_GIVEAWAY != null) goto L88;
    goto L94;
    L88:
    L94:
    v5 = input.Keybinds.OPEN_GIVEAWAY.saveString();
    if (v1.equals(v0.menuKeyBinding) ==0) goto L154;
    if (v2.equals(v0.macroKeyBinding) ==0) goto L154;
    if (v3.equals(v0.messagesKeyBinding) ==0) goto L154;
    if (v4.equals(v0.auctionKeyBinding) ==0) goto L154;
    if (v5.equals(v0.giveawayKeyBinding) ==0) goto L154;
    return;
    L154:
    v0.menuKeyBinding = v1;
    v0.macroKeyBinding = v2;
    v0.messagesKeyBinding = v3;
    v0.auctionKeyBinding = v4;
    v0.giveawayKeyBinding = v5;
    config.ConfigManager.save();
    return;
}

public static boolean isTaken(client.KeyMapping self, blaze3d.platform.InputConstants$Key key) {
    if (key == null) goto L14;
    if (key.equals(blaze3d.platform.InputConstants.UNKNOWN) ==0) goto L16;
    L14:
    return 0;
    L16:
    new client.KeyMapping[6][0] = input.Keybinds.TOGGLE;
    new client.KeyMapping[6][1] = input.Keybinds.OPEN_MENU;
    new client.KeyMapping[6][2] = input.Keybinds.OPEN_MESSAGES;
    new client.KeyMapping[6][3] = input.Keybinds.OPEN_AUCTION;
    new client.KeyMapping[6][4] = input.Keybinds.OPEN_GIVEAWAY;
    new client.KeyMapping[6][5] = input.Keybinds.SPAWNER_MACRO;
    v2 = new client.KeyMapping[6];
    v3 = v2.length;
    v4 = 0;
    L64:
    if (v4 >= v3) goto L113;
    v5 = v2[v4];
    if (v5 == null) goto L107;
    if (v5 != self) goto L90;
    goto L107;
    L90:
    if (key.equals(blaze3d.platform.InputConstants.getKey(v5.saveString())) ==0) goto L107;
    return 1;
    L107:
    v4 = v4 + 1;
    goto L64;
    L113:
    return 0;
}

private static void toggle() {
    if (config.Features.isOn("fwm.tab.sellmacro") !=0) goto L32;
    if (config.FwmConfig.INSTANCE.enabled !=0) goto L21;
    ui.FwmHud.showBlocked();
    return;
    L21:
    config.FwmConfig.INSTANCE.enabled = 0;
    config.ConfigManager.save();
    return;
    L32:
    if (config.FwmConfig.INSTANCE.enabled !=0) goto L48;
    goto L49;
    L48:
    L49:
    1.enabled = 0;
    config.ConfigManager.save();
    return;
}

public static String toggleKeyName() {
    if (input.Keybinds.TOGGLE != null) goto L11;
    goto L22;
    L11:
    L22:
    return input.Keybinds.TOGGLE.getTranslatedKeyMessage().getString();
}

public static String menuKeyName() {
    if (input.Keybinds.OPEN_MENU != null) goto L11;
    goto L22;
    L11:
    L22:
    return input.Keybinds.OPEN_MENU.getTranslatedKeyMessage().getString();
}

public static String spawnerKeyName() {
    if (input.Keybinds.SPAWNER_MACRO == null) goto L15;
    if (input.Keybinds.SPAWNER_MACRO.isUnbound() ==0) goto L18;
    L15:
    return "-";
    L18:
    return input.Keybinds.SPAWNER_MACRO.getTranslatedKeyMessage().getString();
}

public static String messagesKeyName() {
    if (input.Keybinds.OPEN_MESSAGES != null) goto L11;
    goto L22;
    L11:
    L22:
    return input.Keybinds.OPEN_MESSAGES.getTranslatedKeyMessage().getString();
}

public static void handle(client.Minecraft client) {
    if (input.Keybinds.TOGGLE != null) goto L7;
    return;
    L7:
    if (input.Keybinds.restored !=0) goto L20;
    input.Keybinds.restored = 1;
    input.Keybinds.restore();
    L20:
    input.Keybinds.watch = (input.Keybinds.watch + 1);
    if ((input.Keybinds.watch + 1) < 40) goto L41;
    input.Keybinds.watch = 0;
    input.Keybinds.remember();
    L41:
    if (input.Keybinds.TOGGLE.consumeClick() ==0) goto L56;
    input.Keybinds.toggle();
    goto L41;
    L56:
    if (input.Keybinds.OPEN_MENU.consumeClick() ==0) goto L100;
    if (game.SpawnerMacro.running() ==0) goto L78;
    game.SpawnerMacro.toggle(client);
    goto L56;
    L78:
    client.gui.setScreen(ui.Design.open(client.gui.screen(), null, m1));
    goto L56;
    L100:
    if (input.Keybinds.OPEN_MESSAGES.consumeClick() ==0) goto L140;
    if (config.Features.isOn("fwm.tab.messages") ==0) goto L100;
    client.gui.setScreen(ui.Design.open(client.gui.screen(), "fwm.tab.messages", m1));
    goto L100;
    L140:
    if (input.Keybinds.SPAWNER_MACRO.consumeClick() ==0) goto L156;
    game.SpawnerMacro.toggle(client);
    goto L140;
    L156:
    if (input.Keybinds.OPEN_AUCTION.consumeClick() ==0) goto L202;
    v1 = ui.StreamerScreen.ifLive("fwm.tab.auction");
    if (v1 == null) goto L183;
    goto L196;
    L183:
    L196:
    v1.setScreen(ui.Design.open(client.gui.screen(), "fwm.tab.auction", 0));
    goto L156;
    L202:
    if (input.Keybinds.OPEN_GIVEAWAY.consumeClick() ==0) goto L248;
    v1 = ui.StreamerScreen.ifLive("fwm.tab.giveaway");
    if (v1 == null) goto L229;
    goto L242;
    L229:
    L242:
    v1.setScreen(ui.Design.open(client.gui.screen(), "fwm.tab.auction", 1));
    goto L202;
    L248:
    return;
}

private static void lambda$registerContainerScreenHook$0(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.AbstractContainerScreen) !=0) goto L8;
    return;
    L8:
    fabric.api.client.screen.v1.ScreenKeyboardEvents.afterKeyPress(screen).register(/*indy*/afterKeyPress(client));
    return;
}

private static void lambda$registerContainerScreenHook$1(client.Minecraft client, client.gui.screens.Screen s, client.input.KeyEvent input) {
    if (input.Keybinds.SPAWNER_MACRO == null) goto L25;
    if (input.Keybinds.SPAWNER_MACRO.isUnbound() !=0) goto L25;
    if (input.Keybinds.SPAWNER_MACRO.matches(input) !=0) goto L41;
    L25:
    if (input.Keybinds.OPEN_MENU == null) goto L45;
    if (input.Keybinds.OPEN_MENU.matches(input) ==0) goto L45;
    L41:
    goto L46;
    L45:
    L46:
    v3 = 0;
    if (v3 ==0) goto L62;
    if (game.SpawnerMacro.running() ==0) goto L62;
    game.SpawnerMacro.toggle(client);
    return;
    L62:
    if (input.Keybinds.TOGGLE != null) goto L69;
    return;
    L69:
    if (input.Keybinds.TOGGLE.matches(input) !=0) goto L80;
    return;
    L80:
    input.Keybinds.toggle();
    return;
}

static client.KeyMapping$Category <clinit>() {
    input.Keybinds.CATEGORY = client.KeyMapping$Category.register(resources.Identifier.fromNamespaceAndPath("fwm", "main"));
    return;
}

