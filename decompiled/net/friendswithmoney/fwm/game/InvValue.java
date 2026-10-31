// class net/friendswithmoney/fwm/game/InvValue  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field timer I;
field lastHash I;
field value J;
field unpriced I;
private void <init>() {
    return;
}

public static boolean active() {
    if (config.Features.isOn("fwm.feature.invvalue") ==0) goto L20;
    if (config.Features.isOn("fwm.feature.ahprice") ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

public static long value() {
    return game.InvValue.value;
}

public static int unpriced() {
    return game.InvValue.unpriced;
}

public static void register() {
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

public static int tick(client.Minecraft client) {
    if (game.InvValue.active() ==0) goto L13;
    if (client.player != null) goto L14;
    L13:
    return;
    L14:
    game.InvValue.timer = (game.InvValue.timer + 1);
    if ((game.InvValue.timer + 1) >= 20) goto L29;
    return;
    L29:
    game.InvValue.timer = 0;
    net.MarketPrices.ensure();
    v1 = client.player.getInventory();
    v2 = 1;
    v3 = 0;
    L48:
    if (v3 >= v1.getContainerSize()) goto L104;
    v4 = v1.getItem(v3);
    if (v4.isEmpty() ==0) goto L79;
    goto L96;
    L79:
    L96:
    v2 = (0 + ((v4.getItem().hashCode() * 97) + v4.getCount()));
    v3 = v3 + 1;
    goto L48;
    L104:
    if (config.FwmConfig.INSTANCE.invValueShulkers ==0) goto L121;
    goto L122;
    L121:
    L122:
    v2 = ((1 + 0) + (config.FwmConfig.INSTANCE.invValueSource * 7));
    if (v2 != game.InvValue.lastHash) goto L150;
    if (cmp(game.InvValue.value,0) <0) goto L150;
    return;
    L150:
    v3 = 0;
    v5 = 0;
    v6 = 0;
    v7 = 0;
    L161:
    if (v7 >= v1.getContainerSize()) goto L359;
    v8 = v1.getItem(v7);
    if (v8.isEmpty() ==0) goto L189;
    goto L353;
    L189:
    v9 = game.InvValue.price(v8);
    if (cmp(v9,0) <0) goto L221;
    v6 = 1;
    v3 = (v3 + (v9 * ((l)v8.getCount())));
    goto L242;
    L221:
    if (v8.get(core.component.DataComponents.CONTAINER) != null) goto L242;
    v5 = (v5 + v8.getCount());
    L242:
    if (config.FwmConfig.INSTANCE.invValueShulkers ==0) goto L353;
    v11 = ((world.item.component.ItemContainerContents)v8.get(core.component.DataComponents.CONTAINER));
    if (v11 == null) goto L353;
    v12 = v11.nonEmptyItemCopyStream().toList().iterator();
    L286:
    if (v12.hasNext() ==0) goto L353;
    v13 = ((world.item.ItemStack)v12.next());
    v14 = game.InvValue.price(v13);
    if (cmp(v14,0) <0) goto L340;
    v6 = 1;
    v3 = (v3 + (v14 * ((l)v13.getCount())));
    goto L350;
    L340:
    v5 = (v5 + v13.getCount());
    L350:
    goto L286;
    L353:
    v7 = v7 + 1;
    goto L161;
    L359:
    if (v6 ==0) goto L368;
    goto L369;
    L368:
    L369:
    game.InvValue.lastHash = 0;
    if (v6 ==0) goto L381;
    goto L384;
    L381:
    L384:
    game.InvValue.value = -1;
    game.InvValue.unpriced = v5;
    return;
}

static long price(world.item.ItemStack stack) {
    v1 = net.MarketPrices.get(shop.MarketKey.of(stack));
    if (v1 == null) goto L18;
    if (v1.length >= 2) goto L22;
    L18:
    return -1;
    L22:
    if (v1[0] != null) goto L32;
    goto L38;
    L32:
    L38:
    v2 = v1[0].doubleValue();
    if (v1[1] != null) goto L49;
    goto L55;
    L49:
    L55:
    v4 = v1[1].doubleValue();
    // switch (config.FwmConfig.INSTANCE.invValueSource) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=81), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=25), Operand(op_type=<OperandTypes.BRANCH: 40>, value=41), Operand(op_type=<OperandTypes.BRANCH: 40>, value=50)]
    L88:
    if (cmp(v4,0) <=0) goto L100;
    goto L156;
    L100:
    goto L156;
    L104:
    goto L156;
    L113:
    if (cmp(v2,0) <=0) goto L135;
    if (cmp(v4,0) <=0) goto L135;
    goto L156;
    L135:
    goto L156;
    L144:
    if (cmp(v2,0) <=0) goto L154;
    goto L156;
    L154:
    L156:
    v6 = v4;
    if (cmp(v6,0) <=0) goto L177;
    goto L180;
    L177:
    L180:
    return -1;
}

public static String line() {
    if (cmp(game.InvValue.value,0) >=0) goto L13;
    goto L33;
    L13:
    if (config.FwmConfig.INSTANCE.moneyHideAmounts ==0) goto L27;
    goto L33;
    L27:
    L33:
    v0 = util.Fmt.compactMoney(game.InvValue.value);
    return ui.Lang.t("Inventar: ", "Inventory: ") + v0;
}

private static void render(client.Minecraft client, client.gui.screens.inventory.InventoryScreen screen, client.gui.GuiGraphicsExtractor ctx) {
    if (game.InvValue.active() ==0) goto L22;
    if (config.FwmConfig.INSTANCE.invValueInInventory ==0) goto L22;
    if (client.player != null) goto L23;
    L22:
    return;
    L23:
    v3 = ((mixin.HandledScreenAccessor)screen);
    v4 = game.InvValue.line();
    v5 = (((v3.fwm$x() + 176) - client.font.width(v4)) - 2);
    v6 = (v3.fwm$y() - 11);
    if (v6 >= 2) goto L75;
    return;
    L75:
    ctx.text(client.font, v4, v5, v6, -1509908);
    return;
}

private static void lambda$register$0(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.InventoryScreen) ==0) goto L16;
    v4 = ((client.gui.screens.inventory.InventoryScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    fabric.api.client.screen.v1.ScreenEvents.afterExtract(screen).register(/*indy*/afterExtract(client, v4));
    return;
}

private static void lambda$register$1(client.Minecraft client, client.gui.screens.inventory.InventoryScreen inventory, client.gui.screens.Screen s, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    game.InvValue.render(client, inventory, ctx);
    return;
}

static void <clinit>() {
    game.InvValue.value = -1;
    return;
}

