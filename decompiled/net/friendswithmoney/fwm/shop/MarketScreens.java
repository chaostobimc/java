// class net/friendswithmoney/fwm/shop/MarketScreens  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ENDER_CHEST_COMMAND Ljava/util/regex/Pattern;;
field WINDOW_MS J;
field pendingUntil J;
field allowed Lnet/minecraft/client/gui/screens/Screen;;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.message.v1.ClientSendMessageEvents.COMMAND.register(/*indy*/onSendCommandMessage());
    fabric.api.event.player.UseBlockCallback.EVENT.register(/*indy*/interact());
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static boolean opensStorage(world.level.block.Block block) {
    if ((block instanceof world.level.block.ChestBlock) !=0) goto L28;
    if ((block instanceof world.level.block.BarrelBlock) !=0) goto L28;
    if ((block instanceof world.level.block.EnderChestBlock) !=0) goto L28;
    if ((block instanceof world.level.block.ShulkerBoxBlock) ==0) goto L32;
    L28:
    goto L33;
    L32:
    L33:
    return 0;
}

public static boolean allowed() {
    v0 = client.Minecraft.getInstance();
    if (shop.MarketScreens.onHugoSmp(v0) ==0) goto L28;
    if (shop.MarketScreens.allowedHere(v0.gui.screen()) ==0) goto L28;
    goto L29;
    L28:
    L29:
    return 0;
}

private static boolean allowedHere(client.gui.screens.Screen screen) {
    if ((screen instanceof client.gui.screens.inventory.InventoryScreen) !=0) goto L18;
    if (screen == null) goto L22;
    if (screen != shop.MarketScreens.allowed) goto L22;
    L18:
    goto L23;
    L22:
    L23:
    return 0;
}

private static boolean onHugoSmp(client.Minecraft client) {
    if (client.isLocalServer() ==0) goto L9;
    return 0;
    L9:
    v1 = client.getCurrentServer();
    if (v1 == null) goto L47;
    if (v1.ip == null) goto L47;
    if (v1.ip.toLowerCase(Locale.ROOT).contains("hugosmp") ==0) goto L47;
    goto L48;
    L47:
    L48:
    return 0;
}

private static long lambda$register$2(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if (screen != shop.MarketScreens.allowed) goto L8;
    return;
    L8:
    if ((screen instanceof client.gui.screens.inventory.ContainerScreen) !=0) goto L23;
    if ((screen instanceof client.gui.screens.inventory.ShulkerBoxScreen) !=0) goto L23;
    return;
    L23:
    if (cmp(System.currentTimeMillis(),shop.MarketScreens.pendingUntil) >=0) goto L41;
    shop.MarketScreens.pendingUntil = 0;
    shop.MarketScreens.allowed = screen;
    L41:
    return;
}

private static long lambda$register$1(world.entity.player.Player player, world.level.Level world, world.InteractionHand hand, world.phys.BlockHitResult hit) {
    if (world.isClientSide() ==0) goto L34;
    if (shop.MarketScreens.opensStorage(world.getBlockState(hit.getBlockPos()).getBlock()) ==0) goto L34;
    shop.MarketScreens.pendingUntil = (System.currentTimeMillis() + 3000);
    L34:
    return world.InteractionResult.PASS;
}

private static long lambda$register$0(String command) {
    if (command == null) goto L30;
    if (shop.MarketScreens.ENDER_CHEST_COMMAND.matcher(command.trim()).matches() ==0) goto L30;
    shop.MarketScreens.pendingUntil = (System.currentTimeMillis() + 3000);
    L30:
    return;
}

static regex.Pattern <clinit>() {
    shop.MarketScreens.ENDER_CHEST_COMMAND = regex.Pattern.compile("^(ec|ecs|ecsee|echest|ender|enderchest|endersee|enderchestsee)(\\s.*)?$", 2);
    return;
}

