// class net/friendswithmoney/fwm/ui/panel/FightKitPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field CELL I;
field BTN_H I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field nameField Lnet/minecraft/client/gui/components/EditBox;;
field confirmDelete Z;
field boxes Ljava/util/List;;
field actions Ljava/util/List;;
field labels Ljava/util/List;;
public void <init>(ui.panel.PanelHost host) {
    this.boxes = ?;
    this.actions = ?;
    this.labels = ?;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.fightkit", new Object[0]);
}

private static void kits() {
    if (config.FwmConfig.INSTANCE.fightKits != null) goto L22;
    config.FwmConfig.INSTANCE.fightKits = ?;
    L22:
    return config.FwmConfig.INSTANCE.fightKits;
}

private static config.FwmConfig$FightKit current() {
    return game.FightKits.selected();
}

private int gridX() {
    return ((this.x + ((this.width - 162) / 2)) + 12);
}

private int gridY() {
    return (this.y + 36);
}

private int cell(int index) {
    if (index != 40) goto L35;
    new int[2][0] = ((this.gridX() - 18) - 8);
    new int[2][1] = ((this.gridY() + 54) + 4);
    return new int[2];
    L35:
    if (index >= 9) goto L69;
    new int[2][0] = (this.gridX() + (index * 18));
    new int[2][1] = ((this.gridY() + 54) + 4);
    return new int[2];
    L69:
    v2 = ((index - 9) / 9);
    v3 = ((index - 9) % 9);
    new int[2][0] = (this.gridX() + (v3 * 18));
    new int[2][1] = (this.gridY() + (v2 * 18));
    return new int[2];
}

private boolean cellAt(double mx, double a2) {
    v5 = 0;
    L3:
    if (v5 >= 36) goto L30;
    if (this.inCell(v5, mx, v3) ==0) goto L24;
    return v5;
    L24:
    v5 = v5 + 1;
    goto L3;
    L30:
    if (this.inCell(40, mx, v3) ==0) goto L44;
    return 40;
    L44:
    return m1;
}

private int[] inCell(int index, double mx, double a3) {
    v6 = this.cell(index);
    if (cmp(mx,((d)v6[0])) <0) goto L59;
    if (cmp(mx,((d)(v6[0] + 18))) >=0) goto L59;
    if (cmp(v4,((d)v6[1])) <0) goto L59;
    if (cmp(v4,((d)(v6[1] + 18))) >=0) goto L59;
    goto L60;
    L59:
    L60:
    return 0;
}

protected void onResize() {
    if (this.nameField == null) goto L20;
    this.host.detach(this.nameField);
    L20:
    v1 = client.Minecraft.getInstance().font;
    this.nameField = ?;
    this.nameField.setBordered(0);
    this.nameField.setMaxLength(24);
    this.nameField.setTextColor(ui.FwmTheme.TEXT);
    v2 = ui.panel.FightKitPanel.current();
    if (v2 == null) goto L111;
    if (v2.name != null) goto L116;
    L111:
    goto L120;
    L116:
    L120:
    "".setValue(v2.name);
    this.host.attach(this.nameField);
    return;
}

private boolean storeName() {
    v1 = ui.panel.FightKitPanel.current();
    if (v1 == null) goto L15;
    if (this.nameField != null) goto L16;
    L15:
    return;
    L16:
    v2 = this.nameField.getValue().trim();
    if (v2.isEmpty() ==0) goto L40;
    goto L41;
    L40:
    L41:
    "Kit".name = v2;
    return;
}

private void select(int index) {
    this.storeName();
    config.FwmConfig.INSTANCE.fightKitSelected = index;
    this.confirmDelete = 0;
    config.ConfigManager.save();
    this.onResize();
    return;
}

private boolean chip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int cx, int cy, String key, String label, boolean active, int mouseX, int mouseY, Runnable action) {
    v11 = (font.width(label) + 10);
    if (mouseX < cx) goto L47;
    if (mouseX >= (cx + v11)) goto L47;
    if (mouseY < cy) goto L47;
    if (mouseY >= (cy + 13)) goto L47;
    goto L48;
    L47:
    L48:
    v12 = 0;
    if (active ==0) goto L69;
    goto L83;
    L69:
    if (v12 ==0) goto L80;
    goto L83;
    L80:
    L83:
    if (active !=0) goto L93;
    if (v12 ==0) goto L99;
    L93:
    goto L102;
    L99:
    L102:
    ui.FwmTheme.box(v11, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    if (active ==0) goto L127;
    goto L141;
    L127:
    if (v12 ==0) goto L138;
    goto L141;
    L138:
    L141:
    label.text((cx + 5), (cy + 3), ui.FwmTheme.TEXT, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    new int[4][0] = cx;
    new int[4][1] = cy;
    new int[4][2] = v11;
    new int[4][3] = 13;
    this.boxes.add(new int[4]);
    this.actions.add(action);
    this.labels.add(key);
    return v11;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.boxes.clear();
    this.actions.clear();
    this.labels.clear();
    v6 = this.x;
    v7 = ui.panel.FightKitPanel.kits();
    v8 = Math.max(0, Math.min((v7.size() - 1), config.FwmConfig.INSTANCE.fightKitSelected));
    v9 = 0;
    L65:
    if (v9 >= v7.size()) goto L204;
    v10 = v9;
    if (((config.FwmConfig$FightKit)v7.get(v9)).name != null) goto L104;
    goto L119;
    L104:
    L119:
    v11 = ((config.FwmConfig$FightKit)v7.get(v9)).name;
    if (v9 != v8) goto L158;
    goto L159;
    L158:
    L159:
    v6 = (this + (ctx.chip(font, v6, this.y, 'kit:' + v11, font.plainSubstrByWidth(v11, 70), 1, 0, mouseX, mouseY, /*indy*/run(this, v10)) + 4));
    if (v6 <= ((this.x + this.width) - 60)) goto L198;
    goto L204;
    L198:
    v9 = v9 + 1;
    goto L65;
    L204:
    this.chip(ctx, font, v6, this.y, "fwm.fightkit.new", ui.Lang.str("fwm.fightkit.new", new Object[0]), 0, mouseX, mouseY, /*indy*/run(this, v7));
    v9 = ui.panel.FightKitPanel.current();
    if (v9 != null) goto L291;
    ctx.text(font, ui.Lang.of("fwm.fightkit.none", new Object[0]), (this.x + 2), (this.y + 24), ui.FwmTheme.TEXT_DIM, 0);
    this.nameField.visible = 0;
    return;
    L291:
    this.nameField.visible = 1;
    if (this.nameField.isFocused() ==0) goto L334;
    goto L337;
    L334:
    L337:
    ui.FwmTheme.box(this.x, (this.y + 16), 118, 16, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.nameField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v10 = (this.x + 124);
    v11 = config.FwmConfig.INSTANCE.fightKitShowInInventory;
    if (v11 ==0) goto L398;
    goto L401;
    L398:
    L401:
    v10 = (this + (ctx.chip(font, v10, (this.y + 17), "fwm.fightkit.check", "fwm.fightkit.inInventoryOn", ui.Lang.str("fwm.fightkit.inInventoryOff", new Object[0]), v11, mouseX, mouseY, /*indy*/run()) + 4));
    v10 = (v10 + (this.chip(ctx, font, v10, (this.y + 17), "fwm.fightkit.capture", ui.Lang.str("fwm.fightkit.capture", new Object[0]), 0, mouseX, mouseY, /*indy*/run(v9)) + 4));
    v10 = (v10 + (this.chip(ctx, font, v10, (this.y + 17), "fwm.fightkit.clear", ui.Lang.str("fwm.fightkit.clear", new Object[0]), 0, mouseX, mouseY, /*indy*/run(v9)) + 4));
    if (this.confirmDelete ==0) goto L546;
    goto L549;
    L546:
    L549:
    ctx.chip(font, v10, (this.y + 17), "fwm.fightkit.delete", "fwm.fightkit.deleteSure", ui.Lang.str("fwm.fightkit.delete", new Object[0]), this.confirmDelete, mouseX, mouseY, /*indy*/run(this, v7, v9));
    v12 = 0;
    L580:
    if (v12 >= 36) goto L606;
    this.drawCell(ctx, font, v9, v12, mouseX, mouseY);
    v12 = v12 + 1;
    goto L580;
    L606:
    this.drawCell(ctx, font, v9, 40, mouseX, mouseY);
    v12 = this.cell(40);
    ctx.text(font, ui.Lang.of("fwm.fightkit.offhand", new Object[0]), (v12[0] - 2), ((v12[1] + 18) + 2), ui.FwmTheme.TEXT_DIM, 0);
    v13 = ((this.gridY() + 72) + 16);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str("fwm.fightkit.hint", new Object[0]), (this.width - 4))), (this.x + 2), v13, ui.FwmTheme.TEXT_DIM, 0);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str("fwm.fightkit.hint2", new Object[0]), (this.width - 4))), (this.x + 2), (v13 + 10), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void drawCell(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, config.FwmConfig$FightKit kit, int index, int mouseX, int mouseY) {
    v7 = this.cell(index);
    if (mouseX < v7[0]) goto L54;
    if (mouseX >= (v7[0] + 18)) goto L54;
    if (mouseY < v7[1]) goto L54;
    if (mouseY >= (v7[1] + 18)) goto L54;
    goto L55;
    L54:
    L55:
    v8 = 0;
    if (v8 ==0) goto L81;
    goto L84;
    L81:
    L84:
    if (v8 ==0) goto L95;
    goto L98;
    L95:
    L98:
    ui.FwmTheme.box(v7[1], 18, 18, ui.FwmTheme.RAISED, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    v9 = ((config.FwmConfig$KitSlot)kit.slots.get(Integer.valueOf(index)));
    if (v9 == null) goto L133;
    if (v9.item != null) goto L134;
    L133:
    return;
    L134:
    v10 = game.FightKits.stack(v9.item, v9.count);
    if (v10.isEmpty() ==0) goto L158;
    return;
    L158:
    ctx.item(v10, (v7[0] + 1), (v7[1] + 1));
    ctx.itemDecorations(font, v10, (v7[0] + 1), (v7[1] + 1));
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    if (this.nameField == null) goto L130;
    if (cmp(mouseX,((d)this.x)) <0) goto L71;
    if (cmp(mouseX,((d)(this.x + 118))) >=0) goto L71;
    if (cmp(mouseY,((d)(this.y + 16))) <0) goto L71;
    if (cmp(mouseY,((d)(this.y + 32))) >=0) goto L71;
    goto L72;
    L71:
    L72:
    v6 = 0;
    if (v6 !=0) goto L96;
    if (this.nameField.isFocused() ==0) goto L96;
    this.storeName();
    config.ConfigManager.save();
    L96:
    if (v6 ==0) goto L119;
    if (this.nameField.visible ==0) goto L119;
    goto L120;
    L119:
    L120:
    1.setFocused(0);
    if (v6 ==0) goto L130;
    return 1;
    L130:
    if (v5 !=0) goto L246;
    v6 = (this.boxes.size() - 1);
    L148:
    if (v6 <0) goto L246;
    v7 = (([I)this.boxes.get(v6));
    if (cmp(mouseX,((d)v7[0])) <0) goto L240;
    if (cmp(mouseX,((d)(v7[0] + v7[2]))) >=0) goto L240;
    if (cmp(mouseY,((d)v7[1])) <0) goto L240;
    if (cmp(mouseY,((d)(v7[1] + v7[3]))) >=0) goto L240;
    ((Runnable)this.actions.get(v6)).run();
    return 1;
    L240:
    v6 = v6 + 255;
    goto L148;
    L246:
    v6 = ui.panel.FightKitPanel.current();
    v7 = this.cellAt(mouseX, mouseY);
    if (v6 == null) goto L269;
    if (v7 >=0) goto L271;
    L269:
    return 1;
    L271:
    this.confirmDelete = 0;
    if (v5 != 1) goto L303;
    v6.slots.remove(Integer.valueOf(v7));
    config.ConfigManager.save();
    return 1;
    L303:
    if (v5 !=0) goto L337;
    this.host.openSub(?);
    L337:
    return 1;
}

public void mouseScrolled(double mouseX, double a2, double mouseY) {
    v7 = ui.panel.FightKitPanel.current();
    v8 = this.cellAt(mouseX, mouseY);
    if (v7 == null) goto L23;
    if (v8 >=0) goto L25;
    L23:
    return 0;
    L25:
    v9 = ((config.FwmConfig$KitSlot)v7.slots.get(Integer.valueOf(v8)));
    if (v9 != null) goto L52;
    return 1;
    L52:
    if (cmp(v5,0) <=0) goto L73;
    goto L74;
    L73:
    L74:
    1.count = Math.max(64, Math.min(v9.count, (1 + m1)));
    config.ConfigManager.save();
    return 1;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.nameField == null) goto L17;
    if (this.nameField.isFocused() !=0) goto L19;
    L17:
    return 0;
    L19:
    if (keyCode == 40) goto L31;
    if (keyCode != 88) goto L48;
    L31:
    this.nameField.setFocused(0);
    this.storeName();
    config.ConfigManager.save();
    return 1;
    L48:
    return this.nameField.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.nameField == null) goto L32;
    if (this.nameField.isFocused() ==0) goto L32;
    if (this.nameField.charTyped(input) ==0) goto L32;
    goto L33;
    L32:
    L33:
    return 0;
}

public boolean isCapturingKeys() {
    if (this.nameField == null) goto L21;
    if (this.nameField.isFocused() ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public void onHidden() {
    this.storeName();
    config.ConfigManager.save();
    if (this.nameField == null) goto L27;
    this.host.detach(this.nameField);
    L27:
    return;
}

private static void lambda$mouseClicked$0(config.FwmConfig$FightKit kit, int index, String id) {
    v3 = ?;
    v3.item = id;
    v4 = ((config.FwmConfig$KitSlot)kit.slots.get(Integer.valueOf(index)));
    if (v4 == null) goto L57;
    if (id.equals(v4.item) ==0) goto L57;
    goto L58;
    L57:
    L58:
    v4.count.count = 1;
    kit.slots.put(Integer.valueOf(index), v3);
    config.ConfigManager.save();
    return;
}

private void lambda$render$5(List kits, config.FwmConfig$FightKit kit) {
    if (this.confirmDelete !=0) goto L13;
    this.confirmDelete = 1;
    return;
    L13:
    kits.remove(kit);
    config.FwmConfig.INSTANCE.fightKitSelected = Math.max(0, (kits.size() - 1));
    this.confirmDelete = 0;
    config.ConfigManager.save();
    this.onResize();
    return;
}

private static void lambda$render$4(config.FwmConfig$FightKit kit) {
    kit.slots.clear();
    config.ConfigManager.save();
    return;
}

private static void lambda$render$3(config.FwmConfig$FightKit kit) {
    v1 = client.Minecraft.getInstance();
    if (v1.player != null) goto L12;
    return;
    L12:
    game.FightKits.capture(kit, v1.player.getInventory());
    config.ConfigManager.save();
    return;
}

private static void lambda$render$2() {
    if (config.FwmConfig.INSTANCE.fightKitShowInInventory !=0) goto L16;
    goto L17;
    L16:
    L17:
    1.fightKitShowInInventory = 0;
    config.ConfigManager.save();
    return;
}

private void lambda$render$1(List kits) {
    this.storeName();
    v2 = ?;
    v2.name = 'Kit ' + (kits.size() + 1);
    kits.add(v2);
    this.select((kits.size() - 1));
    return;
}

private void lambda$render$0(int index) {
    this.select(index);
    return;
}

