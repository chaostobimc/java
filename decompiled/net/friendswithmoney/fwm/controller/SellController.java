// class net/friendswithmoney/fwm/controller/SellController  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field OPEN_TIMEOUT_TICKS I;
field SETTLE_TICKS I;
field STUCK_TICKS I;
field CLICK_GRACE_TICKS I;
field MAX_CLICK_WAIT_TICKS I;
field config Lnet/friendswithmoney/fwm/config/FwmConfig;;
field step Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field timer I;
field menuSeen Z;
field stuckTicks I;
field ticks I;
field clickedAt Ljava/util/Map;;
public void <init>() {
    this.config = config.FwmConfig.INSTANCE;
    this.step = controller.SellController$Step.OFF;
    this.clickedAt = ?;
    return;
}

public boolean isEnabled() {
    if (this.step == controller.SellController$Step.OFF) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

public boolean isRunning() {
    if (this.step == controller.SellController$Step.OFF) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

public void enable() {
    if (this.step != controller.SellController$Step.OFF) goto L18;
    this.go(controller.SellController$Step.SEND, 0);
    L18:
    return;
}

public void disable(client.Minecraft client) {
    this.step = controller.SellController$Step.OFF;
    this.menuSeen = 0;
    if (client == null) goto L37;
    if (client.player == null) goto L37;
    if (controller.SellController.sellScreen(client) == null) goto L37;
    client.player.closeContainer();
    L37:
    return;
}

public void follow(boolean wanted, client.Minecraft client) {
    if (wanted != this.isEnabled()) goto L10;
    return 0;
    L10:
    if (wanted ==0) goto L20;
    this.enable();
    return 0;
    L20:
    this.disable(client);
    return 1;
}

public void onClientTick(client.Minecraft client) {
    if (this.step == controller.SellController$Step.OFF) goto L27;
    if (this.config.enabled ==0) goto L27;
    if (client.player != null) goto L28;
    L27:
    return;
    L28:
    this.ticks = (this.ticks + 1);
    v2 = controller.SellController.sellScreen(client);
    if (this.menuSeen ==0) goto L74;
    if (v2 != null) goto L74;
    this.menuSeen = 0;
    this.go(controller.SellController$Step.PAUSE, this.config.reopenDelayTicks);
    return;
    L74:
    // switch (this.step.ordinal()) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=81), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=4), Operand(op_type=<OperandTypes.BRANCH: 40>, value=31), Operand(op_type=<OperandTypes.BRANCH: 40>, value=39), Operand(op_type=<OperandTypes.BRANCH: 40>, value=47), Operand(op_type=<OperandTypes.BRANCH: 40>, value=63)]
    L112:
    this.send(client);
    goto L162;
    L120:
    this.await(v2);
    goto L162;
    L128:
    if (this.due() ==0) goto L162;
    this.sell(client, v2);
    goto L162;
    L144:
    if (this.due() ==0) goto L162;
    this.go(controller.SellController$Step.SEND, 0);
    goto L162;
    L162:
    return;
}

private void send(client.Minecraft client) {
    v2 = this.config.sellCommand.trim();
    if (v2.startsWith("/") ==0) goto L26;
    v2 = v2.substring(1);
    L26:
    client.player.connection.sendCommand(v2);
    this.go(controller.SellController$Step.AWAIT, 40);
    return;
}

private void await(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (screen == null) goto L32;
    this.menuSeen = 1;
    this.stuckTicks = 0;
    this.clickedAt.clear();
    this.go(controller.SellController$Step.SELL, 5);
    return;
    L32:
    if (this.due() ==0) goto L47;
    this.go(controller.SellController$Step.SEND, 0);
    L47:
    return;
}

private void sell(client.Minecraft client, client.gui.screens.inventory.AbstractContainerScreen screen) {
    v3 = client.gameMode;
    if (screen == null) goto L13;
    if (v3 != null) goto L14;
    L13:
    return;
    L14:
    v4 = screen.getMenu();
    if (this.isFull(v4) ==0) goto L49;
    this.pressSellButton(client, v3, v4);
    this.timer = this.config.sellPressDelayTicks;
    return;
    L49:
    v5 = 0;
    v6 = controller.SellController.clickWait(client);
    v7 = Math.max(1, this.config.transferBurst);
    L71:
    if (v7 <=0) goto L170;
    if (this.isFull(v4) !=0) goto L170;
    v8 = this.firstSellable(v4, v6);
    if (v8 != null) goto L103;
    goto L170;
    L103:
    this.clickedAt.put(Integer.valueOf(v8.index), Integer.valueOf(this.ticks));
    stats.SellStats.countItems(v8.getItem().getCount());
    v3.handleContainerInput(v4.containerId, v8.index, 0, world.inventory.ContainerInput.QUICK_MOVE, client.player);
    v5 = v5 + 1;
    v7 = v7 + 255;
    goto L71;
    L170:
    if (v5 <=0) goto L183;
    this.stuckTicks = 0;
    goto L216;
    L183:
    if (this.firstSellable(v4, v6) == null) goto L216;
    this.stuckTicks = (this.stuckTicks + 1);
    if ((this.stuckTicks + 1) < 60) goto L216;
    this.reopen(client);
    return;
    L216:
    this.timer = this.config.itemMoveDelayTicks;
    return;
}

private void pressSellButton(client.Minecraft client, client.multiplayer.MultiPlayerGameMode clicks, world.inventory.AbstractContainerMenu menu) {
    v4 = this.config.sellButtonSlot;
    if (v4 <0) goto L26;
    if (v4 < menu.slots.size()) goto L27;
    L26:
    return;
    L27:
    clicks.handleContainerInput(menu.containerId, v4, 0, world.inventory.ContainerInput.PICKUP, client.player);
    stats.SellStats.countSellPress();
    return;
}

private void reopen(client.Minecraft client) {
    this.stuckTicks = 0;
    client.player.closeContainer();
    return;
}

private static client.gui.screens.Screen sellScreen(client.Minecraft client) {
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L23;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    goto L25;
    L23:
    return null;
    L25:
    if ((v1 instanceof client.gui.screens.inventory.InventoryScreen) !=0) goto L39;
    if ((v1 instanceof client.gui.screens.inventory.CreativeModeInventoryScreen) ==0) goto L41;
    L39:
    return null;
    L41:
    return v1;
}

private boolean isFull(world.inventory.AbstractContainerMenu menu) {
    v2 = menu.slots.iterator();
    L8:
    if (v2.hasNext() ==0) goto L69;
    v3 = ((world.inventory.Slot)v2.next());
    if ((v3.container instanceof world.entity.player.Inventory) ==0) goto L40;
    goto L8;
    L40:
    if (v3.index != this.config.sellButtonSlot) goto L57;
    goto L8;
    L57:
    if (v3.hasItem() !=0) goto L66;
    return 0;
    L66:
    goto L8;
    L69:
    return 1;
}

private static int clickWait(client.Minecraft client) {
    v1 = 0;
    if (client.getConnection() == null) goto L37;
    v2 = client.getConnection().getPlayerInfo(client.player.getUUID());
    if (v2 == null) goto L37;
    v1 = Math.max(0, v2.getLatency());
    L37:
    return Math.min(30, ((v1 / 50) + 8));
}

private boolean firstSellable(world.inventory.AbstractContainerMenu menu, int wait) {
    v3 = menu.slots.iterator();
    L8:
    if (v3.hasNext() ==0) goto L115;
    v4 = ((world.inventory.Slot)v3.next());
    if ((v4.container instanceof world.entity.player.Inventory) !=0) goto L42;
    goto L8;
    L42:
    if (v4.hasItem() !=0) goto L53;
    goto L8;
    L53:
    v5 = ((Integer)this.clickedAt.get(Integer.valueOf(v4.index)));
    if (v5 == null) goto L97;
    if ((this.ticks - v5.intValue()) >= wait) goto L97;
    goto L8;
    L97:
    if (this.isSellable(v4.getItem()) ==0) goto L112;
    return v4;
    L112:
    goto L8;
    L115:
    return null;
}

private boolean isSellable(world.item.ItemStack stack) {
    return this.config.isWhitelisted(core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString());
}

private void go(controller.SellController$Step next, int ticks) {
    this.step = next;
    this.timer = ticks;
    return;
}

private boolean due() {
    if (this.timer <=0) goto L19;
    this.timer = (this.timer - 1);
    return 0;
    L19:
    return 1;
}

