// class net/friendswithmoney/fwm/game/EcValue  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field COMMAND Ljava/util/regex/Pattern;;
field WINDOW_MS J;
field TITLE_COLOR I;
field pendingUntil J;
field shown Lnet/minecraft/client/gui/screens/inventory/ContainerScreen;;
field lastHash I;
field value J;
field unpriced I;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.message.v1.ClientSendMessageEvents.COMMAND.register(/*indy*/onSendCommandMessage());
    fabric.api.event.player.UseBlockCallback.EVENT.register(/*indy*/interact());
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static boolean on() {
    return config.FwmConfig.INSTANCE.ecValue;
}

private static String widest() {
    return ui.Lang.t("Wert: ", "Value: ") + '999,99M';
}

private static void fitTitle(client.Minecraft client, client.gui.screens.inventory.ContainerScreen chest) {
    if (game.EcValue.on() !=0) goto L7;
    return;
    L7:
    v2 = client.font;
    v3 = ((mixin.HandledScreenAccessor)chest);
    v4 = ((((v3.fwm$backgroundWidth() - 8) - 8) - v2.width(game.EcValue.widest())) - 6);
    v5 = chest.getTitle().getString();
    if (v2.width(v5) > v4) goto L65;
    return;
    L65:
    v6 = v2.plainSubstrByWidth(v5, Math.max(10, (v4 - v2.width("\u2026")))) + '…';
    ((mixin.ScreenTitleAccessor)chest).fwm$setTitle(network.chat.Component.literal(v6).setStyle(chest.getTitle().getStyle()));
    return;
}

private static int recompute(client.gui.screens.inventory.ContainerScreen chest, world.entity.player.Inventory own) {
    v2 = 1;
    v3 = ((world.inventory.ChestMenu)chest.getMenu()).slots.iterator();
    L16:
    if (v3.hasNext() ==0) goto L123;
    v4 = ((world.inventory.Slot)v3.next());
    if (v4.container != own) goto L48;
    goto L16;
    L48:
    v5 = v4.getItem();
    if (v5.isEmpty() ==0) goto L71;
    goto L118;
    L71:
    if (v5.get(core.component.DataComponents.CONTAINER) != null) goto L103;
    goto L117;
    L103:
    L117:
    L118:
    v2 = (((v5.getItem().hashCode() * 97) + v5.getCount()) + (0 + ((world.item.component.ItemContainerContents)v5.get(core.component.DataComponents.CONTAINER)).hashCode()));
    goto L16;
    L123:
    v2 = ((31 * v2) + config.FwmConfig.INSTANCE.invValueSource);
    if (v2 != game.EcValue.lastHash) goto L151;
    if (cmp(game.EcValue.value,0) <0) goto L151;
    return;
    L151:
    v3 = 0;
    v5 = 0;
    v6 = 0;
    v7 = ((world.inventory.ChestMenu)chest.getMenu()).slots.iterator();
    L174:
    if (v7.hasNext() ==0) goto L384;
    v8 = ((world.inventory.Slot)v7.next());
    if (v8.container != own) goto L208;
    goto L174;
    L208:
    v9 = v8.getItem();
    if (v9.isEmpty() ==0) goto L226;
    goto L174;
    L226:
    v10 = game.InvValue.price(v9);
    if (cmp(v10,0) <0) goto L258;
    v6 = 1;
    v3 = (v3 + (v10 * ((l)v9.getCount())));
    goto L279;
    L258:
    if (v9.get(core.component.DataComponents.CONTAINER) != null) goto L279;
    v5 = (v5 + v9.getCount());
    L279:
    v12 = ((world.item.component.ItemContainerContents)v9.get(core.component.DataComponents.CONTAINER));
    if (v12 == null) goto L381;
    v13 = v12.nonEmptyItemCopyStream().toList().iterator();
    L314:
    if (v13.hasNext() ==0) goto L381;
    v14 = ((world.item.ItemStack)v13.next());
    v15 = game.InvValue.price(v14);
    if (cmp(v15,0) <0) goto L368;
    v6 = 1;
    v3 = (v3 + (v15 * ((l)v14.getCount())));
    goto L378;
    L368:
    v5 = (v5 + v14.getCount());
    L378:
    goto L314;
    L381:
    goto L174;
    L384:
    if (cmp(v3,0) !=0) goto L399;
    if (v5 !=0) goto L399;
    goto L400;
    L399:
    L400:
    v7 = 0;
    if (v6 !=0) goto L412;
    if (v7 ==0) goto L416;
    L412:
    goto L417;
    L416:
    L417:
    game.EcValue.lastHash = 0;
    if (v6 !=0) goto L430;
    if (v7 ==0) goto L434;
    L430:
    goto L437;
    L434:
    L437:
    game.EcValue.value = -1;
    game.EcValue.unpriced = v5;
    return;
}

private static void render(client.Minecraft client, client.gui.screens.inventory.ContainerScreen chest, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    if (chest != game.EcValue.shown) goto L20;
    if (game.EcValue.on() ==0) goto L20;
    if (client.player != null) goto L21;
    L20:
    return;
    L21:
    game.EcValue.recompute(chest, client.player.getInventory());
    if (cmp(game.EcValue.value,0) >=0) goto L47;
    v5 = "\u2026";
    goto L88;
    L47:
    if (config.FwmConfig.INSTANCE.moneyHideAmounts ==0) goto L63;
    v5 = "***";
    goto L88;
    L63:
    v5 = util.Fmt.shortMoney(game.EcValue.value);
    if (ui.Lang.isGerman() !=0) goto L88;
    v5 = v5.replace(44, 46);
    L88:
    v6 = client.font;
    v7 = ((mixin.HandledScreenAccessor)chest);
    v8 = ((v7.fwm$x() + v7.fwm$backgroundWidth()) - 8);
    v9 = ui.Lang.t("Wert: ", "Value: ") + v5;
    v10 = (((v7.fwm$x() + 8) + v6.width(chest.getTitle())) + 6);
    if ((v8 - v6.width(v9)) >= v10) goto L180;
    v9 = v5;
    L180:
    v11 = (v8 - v6.width(v9));
    v12 = (v7.fwm$y() + 6);
    ctx.text(v6, network.chat.Component.literal(v9), v11, v12, -12566464, 0);
    if (game.EcValue.unpriced <=0) goto L289;
    if (mouseX < v11) goto L289;
    if (mouseX >= v8) goto L289;
    if (mouseY < (v12 - 1)) goto L289;
    if (mouseY >= (v12 + 9)) goto L289;
    ctx.setTooltipForNextFrame(v6, network.chat.Component.literal(game.EcValue.unpriced + ui.Lang.t(" Items ohne Preis", " items without a price")), mouseX, mouseY);
    L289:
    return;
}

private static void lambda$register$2(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.ContainerScreen) ==0) goto L16;
    v4 = ((client.gui.screens.inventory.ContainerScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    if (screen == game.EcValue.shown) goto L79;
    if (cmp(System.currentTimeMillis(),game.EcValue.pendingUntil) <0) goto L35;
    return;
    L35:
    if (screen.getTitle().getString().toLowerCase(Locale.ROOT).contains("ender") !=0) goto L60;
    return;
    L60:
    game.EcValue.pendingUntil = 0;
    game.EcValue.shown = v4;
    game.EcValue.lastHash = 0;
    game.EcValue.value = -1;
    L79:
    game.EcValue.fitTitle(client, v4);
    fabric.api.client.screen.v1.ScreenEvents.afterExtract(screen).register(/*indy*/afterExtract(client, v4));
    fabric.api.client.screen.v1.ScreenEvents.remove(screen).register(/*indy*/onRemove());
    return;
}

private static void lambda$register$4(client.gui.screens.Screen s) {
    if (s != game.EcValue.shown) goto L11;
    game.EcValue.shown = null;
    L11:
    return;
}

private static void lambda$register$3(client.Minecraft client, client.gui.screens.inventory.ContainerScreen chest, client.gui.screens.Screen s, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    game.EcValue.render(client, chest, ctx, mouseX, mouseY);
    return;
}

private static void lambda$register$1(world.entity.player.Player player, world.level.Level world, world.InteractionHand hand, world.phys.BlockHitResult hit) {
    if (world.isClientSide() ==0) goto L37;
    if ((world.getBlockState(hit.getBlockPos()).getBlock() instanceof world.level.block.EnderChestBlock) ==0) goto L37;
    game.EcValue.pendingUntil = (System.currentTimeMillis() + 3000);
    net.MarketPrices.ensure();
    L37:
    return world.InteractionResult.PASS;
}

private static void lambda$register$0(String command) {
    if (command == null) goto L20;
    if (game.EcValue.COMMAND.matcher(command.trim()).matches() !=0) goto L21;
    L20:
    return;
    L21:
    game.EcValue.pendingUntil = (System.currentTimeMillis() + 3000);
    net.MarketPrices.ensure();
    return;
}

static regex.Pattern <clinit>() {
    game.EcValue.COMMAND = regex.Pattern.compile("^(ec|ecsee)(\\s.*)?$", 2);
    game.EcValue.value = -1;
    return;
}

