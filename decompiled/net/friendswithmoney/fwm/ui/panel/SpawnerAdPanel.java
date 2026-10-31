// class net/friendswithmoney/fwm/ui/panel/SpawnerAdPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LIST_W I;
field GAP I;
field ROW_H I;
field FIELD_H I;
field LABEL_H I;
field MAX_CHAT I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field selected I;
field buyField Lnet/minecraft/client/gui/components/EditBox;;
field sellField Lnet/minecraft/client/gui/components/EditBox;;
field amountField Lnet/minecraft/client/gui/components/EditBox;;
field shownName Ljava/lang/String;;
field fieldY I;
field fieldW I;
field listTop I;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.spawnerad.title", new Object[0]);
}

private List items() {
    v1 = net.Spawners.data();
    if (v1 == null) goto L15;
    if (v1.items != null) goto L21;
    L15:
    goto L25;
    L21:
    L25:
    v2 = v1.items;
    return ui.panel.SpawnerPanel.inShopOrder(v2);
}

private Object current() {
    v1 = this.items();
    if (v1.isEmpty() ==0) goto L16;
    return null;
    L16:
    return ((net.SpawnerData$Item)v1.get(Math.min(this.selected, (v1.size() - 1))));
}

private int rightX() {
    return ((this.x + 64) + 6);
}

private int rightW() {
    return ((this.x + this.width) - this.rightX());
}

protected void onResize() {
    this.store();
    this.detachFields();
    v1 = client.Minecraft.getInstance().font;
    this.fieldY = ((this.y + 14) + 10);
    this.fieldW = ((this.rightW() - 12) / 3);
    this.buyField = this.field(v1, 0);
    this.sellField = this.field(v1, 1);
    this.amountField = this.field(v1, 2);
    this.load();
    return;
}

private void field(client.gui.Font font, int column) {
    v3 = ?;
    v3.setBordered(0);
    v3.setMaxLength(12);
    v3.setTextColor(ui.FwmTheme.TEXT);
    this.host.attach(v3);
    return v3;
}

private void detachFields() {
    if (this.buyField == null) goto L20;
    this.host.detach(this.buyField);
    L20:
    if (this.sellField == null) goto L40;
    this.host.detach(this.sellField);
    L40:
    if (this.amountField == null) goto L60;
    this.host.detach(this.amountField);
    L60:
    return;
}

private void load() {
    v1 = this.current();
    if (v1 != null) goto L14;
    goto L18;
    L14:
    L18:
    null.shownName = v1.name;
    if (v1 == null) goto L32;
    if (this.buyField != null) goto L33;
    L32:
    return;
    L33:
    v2 = ((config.FwmConfig$SpawnerAdPrice)config.FwmConfig.INSTANCE.spawnerAdPrices.get(ui.panel.SpawnerAdPanel.key(v1.name)));
    if (v2 == null) goto L70;
    goto L77;
    L70:
    L77:
    v2.buy.setValue(ui.panel.SpawnerAdPanel.nonZero(v1.buyPrice));
    if (v2 == null) goto L95;
    goto L111;
    L95:
    if (v1.tradedBack() ==0) goto L109;
    goto L111;
    L109:
    L111:
    v1.sellPrice.setValue("");
    if (v2 == null) goto L129;
    goto L131;
    L129:
    L131:
    v2.amount.setValue("");
    return;
}

private Object store() {
    if (this.shownName == null) goto L14;
    if (this.buyField != null) goto L15;
    L14:
    return;
    L15:
    v1 = ?;
    v1.buy = this.buyField.getValue().trim();
    v1.sell = this.sellField.getValue().trim();
    v1.amount = this.amountField.getValue().trim();
    config.FwmConfig.INSTANCE.spawnerAdPrices.put(ui.panel.SpawnerAdPanel.key(this.shownName), v1);
    return;
}

private static String key(String name) {
    return name.toLowerCase(Locale.ROOT);
}

private static String nonZero(String price) {
    if (price == null) goto L16;
    if ("0".equals(price.trim()) ==0) goto L21;
    L16:
    goto L25;
    L21:
    L25:
    return price.trim();
}

private String fill(String template, net.SpawnerData$Item item) {
    if (this.amountField.getValue().isBlank() ==0) goto L18;
    goto L28;
    L18:
    L28:
    v3 = this.amountField.getValue().trim();
    v4 = template.replace("[Name]", ui.FwmGfx.playerName()).replace("[Spawner]", ui.panel.SpawnerAdPanel.plural(item.name)).replace("[Ankauf Preis]", ui.panel.SpawnerAdPanel.price(this.buyField.getValue())).replace("[Verkauf Preis]", ui.panel.SpawnerAdPanel.price(this.sellField.getValue())).replace("[Anzahl]", v3).replace("{name}", ui.FwmGfx.playerName()).replace("{spawner}", ui.panel.SpawnerAdPanel.plural(item.name)).replace("{kauf}", ui.panel.SpawnerAdPanel.price(this.buyField.getValue())).replace("{verkauf}", ui.panel.SpawnerAdPanel.price(this.sellField.getValue())).replace("{anzahl}", v3);
    if (v4.length() <= 256) goto L176;
    goto L178;
    L176:
    L178:
    return v4;
}

private static boolean slots() {
    v0 = ?;
    v1 = config.FwmConfig.INSTANCE.spawnerAdTemplates.iterator();
    L20:
    if (v1.hasNext() ==0) goto L61;
    v2 = ((String)v1.next());
    if (v2 == null) goto L58;
    if (v2.isBlank() !=0) goto L58;
    v0.add(v2);
    L58:
    goto L20;
    L61:
    return v0;
}

private static String plural(String name) {
    if (name == null) goto L11;
    if (name.isEmpty() ==0) goto L14;
    L11:
    return "?";
    L14:
    v1 = Character.toUpperCase(name.charAt(0)) + name.substring(1);
    if (v1.endsWith("s") ==0) goto L47;
    goto L53;
    L47:
    L53:
    return v1 + 's';
}

static String price(String typed) {
    if (typed != null) goto L9;
    goto L13;
    L9:
    L13:
    v1 = typed.trim();
    if (v1.isEmpty() ==0) goto L24;
    return "?";
    L24:
    v2 = (auction.Money.cents(v1) / 100);
    if (cmp(v2,0) >0) goto L41;
    return v1;
    L41:
    if (cmp(v2,1000000000) <0) goto L64;
    return ui.panel.SpawnerAdPanel.oneDecimal((((d)v2) / 1000000000.0)) + 'B';
    L64:
    if (cmp(v2,1000000) <0) goto L87;
    return ui.panel.SpawnerAdPanel.oneDecimal((((d)v2) / 1000000.0)) + 'M';
    L87:
    if (cmp(v2,1000) <0) goto L110;
    return ui.panel.SpawnerAdPanel.oneDecimal((((d)v2) / 1000.0)) + 'K';
    L110:
    return String.valueOf(v2);
}

private static String oneDecimal(double value) {
    new Object[1][0] = Double.valueOf(value);
    return String.format(Locale.GERMANY, "%.1f", new Object[1]);
}

private void putInChat(String message) {
    this.store();
    config.ConfigManager.save();
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.items();
    if (v6.isEmpty() ==0) goto L86;
    if (net.Spawners.state() != net.Spawners$State.ERROR) goto L31;
    goto L34;
    L31:
    L34:
    v7 = ui.Lang.of("fwm.board.loading", new Object[0]);
    ctx.text(font, v7, (this.x + ((this.width - font.width(v7)) / 2)), ((this.y + (this.height / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L86:
    if (this.shownName != null) goto L97;
    this.load();
    L97:
    this.renderList(ctx, font, v6, mouseX, mouseY);
    this.renderFields(ctx, font, mouseX, mouseY, delta);
    this.renderMessages(ctx, font, mouseX, mouseY);
    return;
}

private void renderList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, List items, int mouseX, int mouseY) {
    ctx.text(font, ui.Lang.of("fwm.spawner.header", new Object[0]), (this.x + 4), (this.y + 1), ui.FwmTheme.ACCENT, 0);
    this.listTop = (this.y + 12);
    v6 = 0;
    L45:
    if (v6 >= items.size()) goto L300;
    v7 = (this.listTop + (v6 * 13));
    if ((v7 + 13) <= (this.y + this.height)) goto L88;
    goto L300;
    L88:
    if (v6 != Math.min(this.selected, (items.size() - 1))) goto L112;
    goto L113;
    L112:
    L113:
    v8 = 0;
    if (mouseX < this.x) goto L157;
    if (mouseX >= (this.x + 64)) goto L157;
    if (mouseY < v7) goto L157;
    if (mouseY >= (v7 + 13)) goto L157;
    goto L158;
    L157:
    L158:
    v9 = 0;
    if (v8 !=0) goto L170;
    if (v9 ==0) goto L195;
    L170:
    ctx.fill(this.x, v7, (this.x + 64), (v7 + 13), ui.FwmTheme.HOVER);
    L195:
    if (v8 ==0) goto L224;
    ctx.fill(this.x, v7, (this.x + 2), (v7 + 13), ui.FwmTheme.ACCENT);
    L224:
    v10 = network.chat.Component.literal(font.plainSubstrByWidth(((net.SpawnerData$Item)items.get(v6)).name, 56));
    Objects.requireNonNull(font);
    if (v8 ==0) goto L287;
    goto L290;
    L287:
    L290:
    font.text(v10, (this.x + 5), ((v7 + ((13 - 9) / 2)) + 1), ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    v6 = v6 + 1;
    goto L45;
    L300:
    return;
}

private void renderFields(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.current();
    if (v6 != null) goto L12;
    return;
    L12:
    if (ui.panel.SpawnerAdPanel.nonZero(v6.buyPrice).isEmpty() ==0) goto L41;
    goto L46;
    L41:
    L46:
    0["-"] = v6.buyPrice;
    if (v6.tradedBack() ==0) goto L65;
    goto L68;
    L65:
    L68:
    1[v6.sellPrice] = "-";
    v7 = ui.Lang.str(new Object[2], new Object[2]);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v7, this.rightW())), this.rightX(), (this.y + 1), ui.FwmTheme.TEXT_DIM, 0);
    new String[3][0] = "fwm.spawnerad.buy";
    new String[3][1] = "fwm.spawnerad.sell";
    new String[3][2] = "fwm.spawnerad.amount";
    v8 = new String[3];
    new client.gui.components.EditBox[3][0] = this.buyField;
    new client.gui.components.EditBox[3][1] = this.sellField;
    new client.gui.components.EditBox[3][2] = this.amountField;
    v9 = new client.gui.components.EditBox[3];
    v10 = 0;
    L160:
    if (v10 >= 3) goto L283;
    v11 = (this.rightX() + (v10 * (this.fieldW + 6)));
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str(v8[v10], new Object[0]), this.fieldW)), v11, (this.fieldY - 10), ui.FwmTheme.ACCENT, 0);
    if (v9[v10].isFocused() ==0) goto L257;
    goto L260;
    L257:
    L260:
    ui.FwmTheme.box(v11, this.fieldY, this.fieldW, 14, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    v9[v10].extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v10 = v10 + 1;
    goto L160;
    L283:
    return;
}

private int messagesTop() {
    return ((this.fieldY + 14) + 6);
}

private int firstMessageY() {
    return ((this.messagesTop() + 20) + 2);
}

private static String sharedStart(List messages) {
    if (messages.size() >= 2) goto L13;
    return "";
    L13:
    v1 = ((String)messages.get(0));
    v2 = v1.length();
    v3 = messages.iterator();
    L36:
    if (v3.hasNext() ==0) goto L103;
    v4 = ((String)v3.next());
    v5 = 0;
    L59:
    if (v5 >= v2) goto L97;
    if (v5 >= v4.length()) goto L97;
    if (v4.charAt(v5) != v1.charAt(v5)) goto L97;
    v5 = v5 + 1;
    goto L59;
    L97:
    v2 = v5;
    goto L36;
    L103:
    v3 = v1.lastIndexOf(" | ", (v2 - 3));
    if (v3 >=0) goto L123;
    goto L131;
    L123:
    L131:
    return v1.substring(0, (v3 + 3));
}

private void renderMessages(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.current();
    if (v5 != null) goto L12;
    return;
    L12:
    v6 = ?;
    v7 = ui.panel.SpawnerAdPanel.slots().iterator();
    L31:
    if (v7.hasNext() ==0) goto L72;
    v8 = ((String)v7.next());
    v6.add(this.fill(v8, v5));
    goto L31;
    L72:
    v7 = ui.panel.SpawnerAdPanel.sharedStart(v6);
    v8 = this.messagesTop();
    ctx.text(font, ui.Lang.of("fwm.spawnerad.messages", new Object[0]), this.rightX(), v8, ui.FwmTheme.ACCENT, 0);
    v9 = ui.Lang.of("fwm.spawnerad.edit", new Object[0]);
    v10 = ((this.x + this.width) - font.width(v9));
    if (mouseX < v10) goto L182;
    if (mouseX >= (this.x + this.width)) goto L182;
    if (mouseY < (v8 - 1)) goto L182;
    if (mouseY >= (v8 + 10)) goto L182;
    goto L183;
    L182:
    L183:
    v11 = 0;
    if (v11 ==0) goto L204;
    goto L207;
    L204:
    L207:
    font.text(v9, v10, v8, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    if (v7.isEmpty() !=0) goto L270;
    new Object[1][0] = v7.trim();
    v12 = font.plainSubstrByWidth(ui.Lang.str("fwm.spawnerad.prefix", new Object[1]), this.rightW());
    ctx.text(font, network.chat.Component.literal(v12), this.rightX(), (v8 + 10), ui.FwmTheme.TEXT_DIM, 0);
    L270:
    ui.FwmTheme.divider(ctx, this.rightX(), (this.firstMessageY() - 2), this.rightW());
    v12 = 0;
    L291:
    if (v12 >= v6.size()) goto L503;
    v13 = (this.firstMessageY() + (v12 * 13));
    if ((v13 + 13) <= (this.y + this.height)) goto L335;
    goto L503;
    L335:
    if (mouseX < this.rightX()) goto L377;
    if (mouseX >= (this.x + this.width)) goto L377;
    if (mouseY < v13) goto L377;
    if (mouseY >= (v13 + 13)) goto L377;
    goto L378;
    L377:
    L378:
    v14 = 0;
    if (v14 ==0) goto L412;
    ctx.fill(this.rightX(), v13, (this.x + this.width), (v13 + 13), ui.FwmTheme.HOVER);
    L412:
    v15 = ((String)v6.get(v12)).substring(v7.length());
    v16 = font.plainSubstrByWidth(v15, (this.rightW() - 6));
    Objects.requireNonNull(font);
    if (v14 ==0) goto L490;
    goto L493;
    L490:
    L493:
    font.text(network.chat.Component.literal(v16), (this.rightX() + 3), ((v13 + ((13 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    v12 = v12 + 1;
    goto L291;
    L503:
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = this.items();
    if (cmp(mouseX,((d)(this.x + 64))) >=0) goto L101;
    if (cmp(mouseY,((d)this.listTop)) <0) goto L101;
    v7 = ((i)((mouseY - ((d)this.listTop)) / 13.0));
    if (v7 <0) goto L99;
    if (v7 >= v6.size()) goto L99;
    if (v7 == this.selected) goto L99;
    this.store();
    this.selected = v7;
    this.load();
    L99:
    return 1;
    L101:
    new client.gui.components.EditBox[3][0] = this.buyField;
    new client.gui.components.EditBox[3][1] = this.sellField;
    new client.gui.components.EditBox[3][2] = this.amountField;
    v7 = new client.gui.components.EditBox[3];
    if (cmp(mouseY,((d)this.fieldY)) <0) goto L219;
    if (cmp(mouseY,((d)(this.fieldY + 14))) >=0) goto L219;
    v8 = 0;
    L154:
    if (v8 >= 3) goto L217;
    v9 = (this.rightX() + (v8 * (this.fieldW + 6)));
    if (cmp(mouseX,((d)v9)) <0) goto L207;
    if (cmp(mouseX,((d)(v9 + this.fieldW))) >=0) goto L207;
    goto L208;
    L207:
    L208:
    1.setFocused(0);
    v8 = v8 + 1;
    goto L154;
    L217:
    return 1;
    L219:
    v8 = v7;
    v9 = v8.length;
    v10 = 0;
    L231:
    if (v10 >= v9) goto L257;
    v11 = v8[v10];
    v11.setFocused(0);
    v10 = v10 + 1;
    goto L231;
    L257:
    v8 = this.current();
    v9 = this.firstMessageY();
    v10 = client.Minecraft.getInstance().font;
    v11 = ((this.x + this.width) - v10.width(ui.Lang.of("fwm.spawnerad.edit", new Object[0])));
    if (cmp(mouseX,((d)v11)) <0) goto L363;
    if (cmp(mouseY,((d)(this.messagesTop() - 1))) <0) goto L363;
    if (cmp(mouseY,((d)(this.messagesTop() + 10))) >=0) goto L363;
    this.store();
    this.host.openSub(?);
    return 1;
    L363:
    if (v8 == null) goto L442;
    if (cmp(mouseX,((d)this.rightX())) <0) goto L442;
    if (cmp(mouseY,((d)v9)) <0) goto L442;
    v12 = ((i)((mouseY - ((d)v9)) / 13.0));
    v13 = ui.panel.SpawnerAdPanel.slots();
    if (v12 <0) goto L442;
    if (v12 >= v13.size()) goto L442;
    this.putInChat(this.fill(((String)v13.get(v12)), v8));
    L442:
    return 1;
}

private boolean focused() {
    new client.gui.components.EditBox[3][0] = this.buyField;
    new client.gui.components.EditBox[3][1] = this.sellField;
    new client.gui.components.EditBox[3][2] = this.amountField;
    v1 = new client.gui.components.EditBox[3];
    v2 = v1.length;
    v3 = 0;
    L31:
    if (v3 >= v2) goto L63;
    v4 = v1[v3];
    if (v4 == null) goto L57;
    if (v4.isFocused() ==0) goto L57;
    return v4;
    L57:
    v3 = v3 + 1;
    goto L31;
    L63:
    return null;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    v4 = this.focused();
    if (v4 != null) goto L13;
    return 0;
    L13:
    if (keyCode == 40) goto L25;
    if (keyCode != 88) goto L37;
    L25:
    v4.setFocused(0);
    this.store();
    return 1;
    L37:
    return v4.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.focused();
    if (v2 == null) goto L21;
    if (v2.charTyped(input) ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public client.gui.components.EditBox isCapturingKeys() {
    if (this.focused() == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public void onHidden() {
    this.store();
    config.ConfigManager.save();
    this.detachFields();
    return;
}

