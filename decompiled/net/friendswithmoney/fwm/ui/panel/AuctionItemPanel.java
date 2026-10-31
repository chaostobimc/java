// class net/friendswithmoney/fwm/ui/panel/AuctionItemPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field COLS I;
field CELL I;
field GAP I;
field MAIN_SLOTS I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field current Ljava/util/function/Supplier;;
field pick Ljava/util/function/Consumer;;
field slots Ljava/util/List;;
field gridX I;
field gridY I;
public void <init>(ui.panel.PanelHost host) {
    return;
}

public void <init>(ui.panel.PanelHost host, function.Supplier current, function.Consumer pick) {
    this.slots = ?;
    this.host = host;
    this.current = current;
    this.pick = pick;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.auction.pickTitle", new Object[0]);
}

protected int onResize() {
    this.slots.clear();
    v1 = client.Minecraft.getInstance();
    if (v1.player != null) goto L21;
    return;
    L21:
    v2 = v1.player.getInventory();
    v3 = 0;
    L31:
    if (v3 >= 27) goto L72;
    this.slots.add(?);
    v3 = v3 + 1;
    goto L31;
    L72:
    v3 = 0;
    L74:
    if (v3 >= 9) goto L109;
    this.slots.add(?);
    v3 = v3 + 1;
    goto L74;
    L109:
    v3 = 196;
    this.gridX = (this.x + Math.max(0, ((this.width - v3) / 2)));
    this.gridY = (this.y + 6);
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.slots.isEmpty() ==0) goto L42;
    ctx.text(font, ui.Lang.of("fwm.auction.noInventory", new Object[0]), this.x, (this.y + 8), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L42:
    v6 = world.item.ItemStack.EMPTY;
    v7 = 0;
    L50:
    if (v7 >= this.slots.size()) goto L283;
    v8 = ((ui.panel.AuctionItemPanel$Slot)this.slots.get(v7));
    v9 = this.cellX(v7);
    v10 = this.cellY(v7);
    if (mouseX < v9) goto L132;
    if (mouseX >= (v9 + 20)) goto L132;
    if (mouseY < v10) goto L132;
    if (mouseY >= (v10 + 20)) goto L132;
    goto L133;
    L132:
    L133:
    v11 = 0;
    if (v8.stack().isEmpty() !=0) goto L173;
    if (world.item.ItemStack.matches(v8.stack(), ((world.item.ItemStack)this.current.get())) ==0) goto L173;
    goto L174;
    L173:
    L174:
    v12 = 0;
    if (v12 ==0) goto L199;
    goto L213;
    L199:
    if (v11 ==0) goto L210;
    goto L213;
    L210:
    L213:
    ui.FwmTheme.box(v10, 20, 20, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v8.stack().isEmpty() ==0) goto L230;
    goto L277;
    L230:
    ctx.item(v8.stack(), (v9 + 2), (v10 + 2));
    ctx.itemDecorations(font, v8.stack(), (v9 + 2), (v10 + 2));
    if (v11 ==0) goto L277;
    v6 = v8.stack();
    L277:
    v7 = v7 + 1;
    goto L50;
    L283:
    v7 = ((this.cellY((this.slots.size() - 1)) + 20) + 8);
    if (v6.isEmpty() !=0) goto L337;
    ctx.text(font, ui.panel.AuctionItemPanel.counted(v6), this.x, v7, ui.FwmTheme.TEXT, 0);
    goto L385;
    L337:
    if (((world.item.ItemStack)this.current.get()).isEmpty() !=0) goto L385;
    ctx.text(font, ui.panel.AuctionItemPanel.counted(((world.item.ItemStack)this.current.get())), this.x, v7, ui.FwmTheme.TEXT_DIM, 0);
    L385:
    return;
}

private static network.chat.MutableComponent counted(world.item.ItemStack stack) {
    if (stack.getCount() > 1) goto L13;
    return stack.getHoverName();
    L13:
    return network.chat.Component.literal(stack.getCount() + 'x ').append(stack.getHoverName());
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 ==0) goto L7;
    return 0;
    L7:
    v6 = 0;
    L10:
    if (v6 >= this.slots.size()) goto L142;
    v7 = ((ui.panel.AuctionItemPanel$Slot)this.slots.get(v6));
    if (v7.stack().isEmpty() ==0) goto L54;
    goto L136;
    L54:
    v8 = this.cellX(v6);
    v9 = this.cellY(v6);
    if (cmp(mouseX,((d)v8)) <0) goto L136;
    if (cmp(mouseX,((d)(v8 + 20))) >=0) goto L136;
    if (cmp(mouseY,((d)v9)) <0) goto L136;
    if (cmp(mouseY,((d)(v9 + 20))) <0) goto L111;
    goto L136;
    L111:
    this.pick.accept(v7.stack());
    this.host.closeSub();
    return 1;
    L136:
    v6 = v6 + 1;
    goto L10;
    L142:
    return 0;
}

private int cellX(int index) {
    return (this.gridX + ((index % 9) * 22));
}

private int cellY(int index) {
    v2 = (index / 9);
    if (index < 27) goto L25;
    goto L26;
    L25:
    L26:
    return (6 + 0);
}

