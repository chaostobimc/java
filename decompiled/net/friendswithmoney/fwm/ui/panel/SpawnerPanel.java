// class net/friendswithmoney/fwm/ui/panel/SpawnerPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field PREVIEW_W I;
field GAP I;
field HEADER_H I;
field ROW_H I;
field BTN_H I;
field BTN_W I;
field PRICE_W I;
field PLAYER_SELLS I;
field PLAYER_BUYS I;
field PINNED Ljava/util/List;;
field PINNED_GAP I;
field ICONS Ljava/util/Map;;
field PICTURES Ljava/util/Map;;
field selected I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
public static resources.Identifier picture(String name) {
    if (name != null) goto L12;
    goto L19;
    L12:
    L19:
    v1 = ((String)"".get(name.toLowerCase(Locale.ROOT)));
    if (v1 != null) goto L36;
    goto L47;
    L36:
    L47:
    return resources.Identifier.fromNamespaceAndPath("fwm", 'textures/gui/spawners/' + v1 + '.png');
}

public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.spawner", new Object[0]);
}

private List items() {
    v1 = net.Spawners.data();
    if (v1 == null) goto L15;
    if (v1.items != null) goto L21;
    L15:
    goto L25;
    L21:
    L25:
    return v1.items;
}

private List ordered() {
    return ui.panel.SpawnerPanel.inShopOrder(this.items());
}

public static boolean inShopOrder(List items) {
    if (items.isEmpty() ==0) goto L11;
    return items;
    L11:
    v1 = ?;
    v2 = ui.panel.SpawnerPanel.PINNED.iterator();
    L28:
    if (v2.hasNext() ==0) goto L104;
    v3 = ((String)v2.next());
    v4 = items.iterator();
    L55:
    if (v4.hasNext() ==0) goto L101;
    v5 = ((net.SpawnerData$Item)v4.next());
    if (v3.equalsIgnoreCase(v5.name) ==0) goto L98;
    v1.add(v5);
    L98:
    goto L55;
    L101:
    goto L28;
    L104:
    v2 = items.iterator();
    L111:
    if (v2.hasNext() ==0) goto L151;
    v3 = ((net.SpawnerData$Item)v2.next());
    if (v1.contains(v3) !=0) goto L148;
    v1.add(v3);
    L148:
    goto L111;
    L151:
    return v1;
}

private boolean pinnedCount() {
    v1 = 0;
    v2 = this.items().iterator();
    L12:
    if (v2.hasNext() ==0) goto L84;
    v3 = ((net.SpawnerData$Item)v2.next());
    v4 = ui.panel.SpawnerPanel.PINNED.iterator();
    L41:
    if (v4.hasNext() ==0) goto L81;
    v5 = ((String)v4.next());
    if (v5.equalsIgnoreCase(v3.name) ==0) goto L78;
    v1 = v1 + 1;
    L78:
    goto L41;
    L81:
    goto L12;
    L84:
    return v1;
}

private Object current() {
    v1 = this.ordered();
    if (v1.isEmpty() ==0) goto L16;
    return null;
    L16:
    return ((net.SpawnerData$Item)v1.get(Math.min(this.selected, (v1.size() - 1))));
}

private int tableX() {
    return ((this.x + 52) + 5);
}

private int tableW() {
    return ((this.x + this.width) - this.tableX());
}

private int sellRight() {
    return ((this.tableX() + this.tableW()) - 2);
}

private int buyRight() {
    return (this.sellRight() - 52);
}

private int rowY(int index) {
    if (index < this.pinnedCount()) goto L13;
    goto L14;
    L13:
    L14:
    v2 = 0;
    return (((this.y + 11) + (index * 15)) + v2);
}

private int buttonY() {
    return ((this.y + this.height) - 16);
}

private int buttonX() {
    return ((this.x + this.width) - 118);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.items().isEmpty() ==0) goto L80;
    if (net.Spawners.state() != net.Spawners$State.ERROR) goto L26;
    goto L28;
    L26:
    L28:
    v6 = ui.Lang.of("fwm.board.loading", new Object[0]);
    ctx.text(font, v6, (this.x + ((this.width - font.width(v6)) / 2)), ((this.y + (this.height / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L80:
    this.renderPreview(ctx, font);
    this.renderTable(ctx, font, mouseX, mouseY);
    this.renderButton(ctx, font, mouseX, mouseY);
    return;
}

private void renderPreview(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = ((this.buttonY() - this.y) - 2);
    ui.FwmTheme.box(ctx, this.x, this.y, 52, v3, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    v4 = this.current();
    if (v4 != null) goto L45;
    return;
    L45:
    v5 = ui.panel.SpawnerPanel.picture(v4.name);
    if (v5 == null) goto L152;
    ui.FwmGfx.blit(ctx, v5, ((this.x + 26) - 24), ((this.y + (v3 / 2)) - 40), 48, 48, 64, 64, 64);
    v6 = network.chat.Component.literal(font.plainSubstrByWidth(v4.name, 46));
    ctx.text(font, v6, (this.x + ((52 - font.width(v6)) / 2)), ((this.y + (v3 / 2)) + 12), ui.FwmTheme.TEXT, 0);
    return;
    L152:
    v6 = this.icon(v4.name);
    if (v6 == null) goto L243;
    if (v6.isEmpty() !=0) goto L243;
    v7 = ctx.pose();
    v7.pushMatrix();
    v7.translate(((((f)this.x) + 26.0) - 16.0), ((((f)this.y) + (((f)v3) / 2)) - 24.0));
    v7.scale(2, 2);
    ctx.item(v6, 0, 0);
    v7.popMatrix();
    L243:
    v7 = network.chat.Component.literal(font.plainSubstrByWidth(v4.name, 46));
    ctx.text(font, v7, (this.x + ((52 - font.width(v7)) / 2)), ((this.y + (v3 / 2)) + 12), ui.FwmTheme.TEXT, 0);
    return;
}

private world.item.ItemStack icon(String name) {
    if (name != null) goto L9;
    goto L16;
    L9:
    L16:
    v2 = name.toLowerCase(Locale.ROOT);
    v3 = ((String)ui.panel.SpawnerPanel.ICONS.getOrDefault(v2, 'minecraft:' + v2 + '_spawn_egg'));
    v4 = this.stackOf(v3);
    if (v4 == null) goto L53;
    goto L60;
    L53:
    L60:
    return this.stackOf("minecraft:spawner");
}

private Object stackOf(String id) {
    v2 = resources.Identifier.tryParse(id);
    if (v2 != null) goto L11;
    return null;
    L11:
    return ((world.item.ItemStack)core.registries.BuiltInRegistries.ITEM.getOptional(v2).map(/*indy*/apply()).orElse(null));
    v2 = /*catch Exception*/ ex;
    return null;
}

private void renderTable(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    this.columnHeader(ctx, font, ui.Lang.str("fwm.spawner.header", new Object[0]), (this.tableX() + 5), 0, ui.FwmTheme.ACCENT);
    this.columnHeader(ctx, font, ui.Lang.str("fwm.spawner.buy", new Object[0]), this.buyRight(), 1, -10688364);
    this.columnHeader(ctx, font, ui.Lang.str("fwm.spawner.sell", new Object[0]), this.sellRight(), 1, -2067340);
    v5 = this.ordered();
    v6 = 0;
    L83:
    if (v6 >= v5.size()) goto L151;
    v7 = this.rowY(v6);
    if ((v7 + 15) <= (this.buttonY() - 2)) goto L120;
    goto L151;
    L120:
    this.renderRow(ctx, font, ((net.SpawnerData$Item)v5.get(v6)), v6, v7, mouseX, mouseY);
    v6 = v6 + 1;
    goto L83;
    L151:
    return;
}

private void columnHeader(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String text, int at, boolean rightAligned, int color) {
    v7 = network.chat.Component.literal(text);
    if (rightAligned ==0) goto L27;
    goto L29;
    L27:
    L29:
    font.text(v7, (at - font.width(v7)), at, (this.y + 1), color, 0);
    return;
}

private void renderRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, net.SpawnerData$Item item, int index, int ry, int mouseX, int mouseY) {
    if (index != Math.min(this.selected, (this.ordered().size() - 1))) goto L27;
    goto L28;
    L27:
    L28:
    v8 = 0;
    if (mouseX < this.tableX()) goto L74;
    if (mouseX >= (this.x + this.width)) goto L74;
    if (mouseY < ry) goto L74;
    if (mouseY >= (ry + 15)) goto L74;
    goto L75;
    L74:
    L75:
    v9 = 0;
    if (v8 ==0) goto L136;
    ctx.fill(this.tableX(), ry, (this.x + this.width), (ry + 15), ui.FwmTheme.HOVER);
    ctx.fill(this.tableX(), ry, (this.tableX() + 2), (ry + 15), ui.FwmTheme.ACCENT);
    goto L168;
    L136:
    if (v9 ==0) goto L168;
    ctx.fill(this.tableX(), ry, (this.x + this.width), (ry + 15), ui.FwmTheme.HOVER);
    L168:
    Objects.requireNonNull(font);
    v10 = ((ry + ((15 - 9) / 2)) + 1);
    v11 = network.chat.Component.literal(font.plainSubstrByWidth(item.name, Math.max(8, ((this.buyRight() - this.tableX()) - 12))));
    if (v8 ==0) goto L240;
    goto L243;
    L240:
    L243:
    font.text(v11, (this.tableX() + 5), v10, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    this.price(ctx, font, this.buyRight(), v10, item.buyPrice, -10688364);
    if (item.tradedBack() ==0) goto L289;
    goto L292;
    L289:
    L292:
    if (item.tradedBack() ==0) goto L305;
    goto L308;
    L305:
    L308:
    font.price(this.sellRight(), v10, item.sellPrice, "-", -2067340, ui.FwmTheme.OFF);
    ui.FwmTheme.divider(ctx, this.tableX(), ((ry + 15) - 1), this.tableW());
    return;
}

private void price(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int right, int textY, String value, int color) {
    if (value != null) goto L12;
    goto L14;
    L12:
    L14:
    v7 = network.chat.Component.literal("?".plainSubstrByWidth(value, 48));
    ctx.text(font, v7, (right - font.width(v7)), textY, color, 0);
    return;
}

private void renderButton(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.overButton(((d)mouseX), ((d)mouseY));
    if (v5 ==0) goto L38;
    goto L41;
    L38:
    L41:
    ui.FwmTheme.box(this.buttonX(), this.buttonY(), 118, 16, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v6 = ui.Lang.of("fwm.spawner.trade", new Object[0]);
    v7 = network.chat.Component.literal(font.plainSubstrByWidth(v6.getString(), 112));
    Objects.requireNonNull(font);
    if (v5 ==0) goto L124;
    goto L127;
    L124:
    L127:
    font.text(v7, (this.buttonX() + ((118 - font.width(v7)) / 2)), ((this.buttonY() + ((16 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private int overButton(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.buttonX())) <0) goto L50;
    if (cmp(mouseX,((d)(this.buttonX() + 118))) >=0) goto L50;
    if (cmp(v3,((d)this.buttonY())) <0) goto L50;
    if (cmp(v3,((d)(this.buttonY() + 16))) >=0) goto L50;
    goto L51;
    L50:
    L51:
    return 0;
}

public int mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (this.overButton(mouseX, mouseY) ==0) goto L100;
    v6 = this.ordered();
    if (v6.isEmpty() ==0) goto L45;
    goto L74;
    L45:
    L74:
    v7 = ((net.SpawnerData$Item)v6.get(Math.min(this.selected, (v6.size() - 1)))).name;
    this.host.openSub(?);
    return 1;
    L100:
    if (cmp(mouseX,((d)this.tableX())) <0) goto L168;
    v6 = 0;
    L113:
    if (v6 >= this.ordered().size()) goto L168;
    if (cmp(mouseY,((d)this.rowY(v6))) <0) goto L162;
    if (cmp(mouseY,((d)(this.rowY(v6) + 15))) >=0) goto L162;
    this.selected = v6;
    return 1;
    L162:
    v6 = v6 + 1;
    goto L113;
    L168:
    return 1;
}

private void openDiscord() {
    v1 = client.Minecraft.getInstance();
    client.gui.screens.ConfirmLinkScreen.confirmLinkNow(v1.gui.screen(), URI.create(config.FwmConfig.INSTANCE.discordUrl));
    return;
}

static Object <clinit>() {
    ui.panel.SpawnerPanel.PINNED = List.of("skelly", "creeper");
    ui.panel.SpawnerPanel.ICONS = ?;
    ui.panel.SpawnerPanel.ICONS.put("iron", "minecraft:iron_block");
    ui.panel.SpawnerPanel.ICONS.put("skelly", "minecraft:skeleton_spawn_egg");
    ui.panel.SpawnerPanel.PICTURES = ?;
    new String[2][0] = "skelly";
    new String[2][1] = "skeleton";
    new [Ljava.lang.String;[16][0] = new String[2];
    new String[2][0] = "skeleton";
    new String[2][1] = "skeleton";
    new [Ljava.lang.String;[16][1] = new String[2];
    new String[2][0] = "skelett";
    new String[2][1] = "skeleton";
    new [Ljava.lang.String;[16][2] = new String[2];
    new String[2][0] = "creeper";
    new String[2][1] = "creeper";
    new [Ljava.lang.String;[16][3] = new String[2];
    new String[2][0] = "iron";
    new String[2][1] = "iron_golem";
    new [Ljava.lang.String;[16][4] = new String[2];
    new String[2][0] = "iron_golem";
    new String[2][1] = "iron_golem";
    new [Ljava.lang.String;[16][5] = new String[2];
    new String[2][0] = "eisengolem";
    new String[2][1] = "iron_golem";
    new [Ljava.lang.String;[16][6] = new String[2];
    new String[2][0] = "blaze";
    new String[2][1] = "blaze";
    new [Ljava.lang.String;[16][7] = new String[2];
    new String[2][0] = "lohe";
    new String[2][1] = "blaze";
    new [Ljava.lang.String;[16][8] = new String[2];
    new String[2][0] = "spider";
    new String[2][1] = "spider";
    new [Ljava.lang.String;[16][9] = new String[2];
    new String[2][0] = "spinne";
    new String[2][1] = "spider";
    new [Ljava.lang.String;[16][10] = new String[2];
    new String[2][0] = "cow";
    new String[2][1] = "cow";
    new [Ljava.lang.String;[16][11] = new String[2];
    new String[2][0] = "kuh";
    new String[2][1] = "cow";
    new [Ljava.lang.String;[16][12] = new String[2];
    new String[2][0] = "zombie_piglin";
    new String[2][1] = "zombie_piglin";
    new [Ljava.lang.String;[16][13] = new String[2];
    new String[2][0] = "piglin";
    new String[2][1] = "zombie_piglin";
    new [Ljava.lang.String;[16][14] = new String[2];
    new String[2][0] = "zombie-piglin";
    new String[2][1] = "zombie_piglin";
    new [Ljava.lang.String;[16][15] = new String[2];
    v0 = new [Ljava.lang.String;[16];
    v1 = v0.length;
    v2 = 0;
    L387:
    if (v2 >= v1) goto L417;
    v3 = v0[v2];
    ui.panel.SpawnerPanel.PICTURES.put(v3[0], v3[1]);
    v2 = v2 + 1;
    goto L387;
    L417:
    return;
}

