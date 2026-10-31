// class net/friendswithmoney/fwm/ui/panel/MessagesPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LIST_PERCENT I;
field LIST_MIN_W I;
field CONTACT_H I;
field INPUT_H I;
field GAP I;
field LINE_H I;
field BUBBLE_PAD I;
field HEADER_H I;
field SCROLLBAR_W I;
field SEARCH_H I;
field DELETE_W I;
field confirm Lnet/friendswithmoney/fwm/ui/ConfirmBox;;
field BUBBLE_MAX F;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field input Lnet/minecraft/client/gui/components/EditBox;;
field search Lnet/minecraft/client/gui/components/EditBox;;
field selected Ljava/lang/String;;
field contactScroll I;
field threadScroll I;
field newChat Z;
field layout Ljava/util/List;;
field layoutPartner Ljava/lang/String;;
field layoutCount I;
field layoutWidth I;
field stickToBottom Z;
field BAR_H I;
field COG I;
public void <init>(ui.panel.PanelHost host) {
    this.confirm = ?;
    this.layout = ?;
    this.layoutPartner = "";
    this.layoutCount = m1;
    this.layoutWidth = m1;
    this.stickToBottom = 1;
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.messages", new Object[0]);
}

protected void onResize() {
    if (this.input != null) goto L12;
    goto L19;
    L12:
    L19:
    v1 = this.input.getValue();
    if (this.search != null) goto L32;
    goto L39;
    L32:
    L39:
    v2 = this.search.getValue();
    if (this.input == null) goto L60;
    this.host.detach(this.input);
    L60:
    if (this.search == null) goto L80;
    this.host.detach(this.search);
    L80:
    v3 = client.Minecraft.getInstance().font;
    this.input = ?;
    this.input.setBordered(0);
    this.input.setMaxLength(200);
    this.input.setTextColor(ui.FwmTheme.TEXT);
    this.input.setHint(ui.Lang.of("fwm.messages.write", new Object[0]));
    this.input.setValue(v1);
    this.search = ?;
    this.search.setBordered(0);
    this.search.setMaxLength(16);
    this.search.setTextColor(ui.FwmTheme.TEXT);
    this.search.setHint(this.searchHint());
    this.search.setValue(v2);
    this.search.setResponder(/*indy*/accept(this));
    this.host.attach(this.input);
    this.host.attach(this.search);
    this.layoutWidth = m1;
    return;
}

public void onHidden() {
    if (this.input == null) goto L20;
    this.host.detach(this.input);
    L20:
    if (this.search == null) goto L40;
    this.host.detach(this.search);
    L40:
    chat.PlayerSuggest.reset();
    return;
}

private network.chat.MutableComponent searchHint() {
    if (this.newChat ==0) goto L12;
    goto L14;
    L12:
    L14:
    return ui.Lang.of("fwm.messages.search", new Object[0]);
}

private void toggleNewChat() {
    if (this.newChat !=0) goto L12;
    goto L13;
    L12:
    L13:
    1.newChat = 0;
    this.contactScroll = 0;
    this.search.setValue("");
    this.search.setHint(this.searchHint());
    this.search.setFocused(1);
    chat.PlayerSuggest.reset();
    return;
}

private int listWidth() {
    return Math.max(66, ((this.width * 36) / 100));
}

private int threadX() {
    return ((this.x + this.listWidth()) + 6);
}

private int threadWidth() {
    return ((this.width - this.listWidth()) - 6);
}

private int threadBottom() {
    return (((this.y + this.height) - 14) - 4);
}

private boolean searchWidth() {
    if (this.showReadAll() ==0) goto L21;
    goto L22;
    L21:
    L22:
    return (14 - 0);
}

private int buttonX() {
    return ((this.x + this.listWidth()) - 13);
}

private int showReadAll() {
    if (this.newChat !=0) goto L17;
    if (chat.Messages.unreadTotal() <=0) goto L17;
    goto L18;
    L17:
    L18:
    return 0;
}

private int readAllX() {
    return ((this.buttonX() - 13) - 1);
}

private int barH() {
    if (this.selected != null) goto L11;
    goto L13;
    L11:
    L13:
    return 11;
}

private int barY() {
    return this.y;
}

private int threadTop() {
    return (this.y + this.barH());
}

private int cogX() {
    return ((this.threadX() + this.threadWidth()) - 11);
}

private int ignoreX(client.gui.Font font) {
    return ((this.cogX() - 3) - this.ignoreWidth(font));
}

private int ignoreWidth(client.gui.Font font) {
    return (font.width(this.ignoreLabel()) + 6);
}

private int hideX(client.gui.Font font) {
    return ((this.ignoreX(font) - 3) - ui.panel.MessagesPanel.smallWidth(font, "fwm.messages.hide"));
}

private int tpaHereX(client.gui.Font font) {
    return ((this.hideX(font) - 3) - ui.panel.MessagesPanel.smallWidth(font, "fwm.messages.tpahere"));
}

private int tpaX(client.gui.Font font) {
    return ((this.tpaHereX(font) - 3) - ui.panel.MessagesPanel.smallWidth(font, "fwm.messages.tpa"));
}

private static int smallWidth(client.gui.Font font, String key) {
    return (font.width(ui.Lang.of(key, new Object[0])) + 6);
}

private network.chat.MutableComponent ignoreLabel() {
    if (chat.Messages.isIgnored(this.selected) ==0) goto L15;
    goto L17;
    L15:
    L17:
    return ui.Lang.of("fwm.messages.ignore", new Object[0]);
}

private int contactsTop() {
    return ((this.y + 13) + 2);
}

private int contactsHeight() {
    return ((this.y + this.height) - this.contactsTop());
}

private int contactsShown() {
    return Math.max(1, (((this.contactsHeight() + 24) - 1) / 24));
}

private int maxContactScroll(int total) {
    return Math.max(0, (total - Math.max(1, (this.contactsHeight() / 24))));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.entries();
    if (this.selected != null) goto L55;
    if (v6.isEmpty() !=0) goto L55;
    if (((ui.panel.MessagesPanel$Entry)v6.get(0)).conversation() == null) goto L55;
    this.select(((ui.panel.MessagesPanel$Entry)v6.get(0)));
    L55:
    this.renderSearch(ctx, font, mouseX, mouseY, delta);
    this.renderContacts(ctx, font, v6, mouseX, mouseY);
    ctx.fill(((this.x + this.listWidth()) + 2), this.y, ((this.x + this.listWidth()) + 3), (this.y + this.height), ui.FwmTheme.OFF);
    this.renderThread(ctx, font);
    this.renderThreadBar(ctx, font, mouseX, mouseY);
    this.renderInput(ctx, mouseX, mouseY, delta);
    this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    return;
}

private void renderSearch(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.search.isFocused() ==0) goto L34;
    goto L37;
    L34:
    L37:
    ui.FwmTheme.box(this.x, this.y, this.searchWidth(), 13, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    if (mouseX < this.buttonX()) goto L97;
    if (mouseX >= (this.buttonX() + 13)) goto L97;
    if (mouseY < this.y) goto L97;
    if (mouseY >= (this.y + 13)) goto L97;
    goto L98;
    L97:
    L98:
    v6 = 0;
    if (this.newChat ==0) goto L113;
    goto L116;
    L113:
    L116:
    v7 = "+";
    if (this.newChat ==0) goto L144;
    goto L147;
    L144:
    L147:
    if (this.newChat !=0) goto L159;
    if (v6 ==0) goto L165;
    L159:
    goto L168;
    L165:
    L168:
    ui.FwmTheme.box(this.y, 13, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (this.newChat !=0) goto L211;
    if (v6 ==0) goto L217;
    L211:
    goto L220;
    L217:
    L220:
    font.text(v7, ((this.buttonX() + ((13 - font.width(v7)) / 2)) + 1), (this.y + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    if (this.showReadAll() !=0) goto L232;
    return;
    L232:
    if (mouseX < this.readAllX()) goto L276;
    if (mouseX >= (this.readAllX() + 13)) goto L276;
    if (mouseY < this.y) goto L276;
    if (mouseY >= (this.y + 13)) goto L276;
    goto L277;
    L276:
    L277:
    v8 = 0;
    if (v8 ==0) goto L306;
    goto L309;
    L306:
    L309:
    ui.FwmTheme.box(this.readAllX(), this.y, 13, 13, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v9 = (this.readAllX() + 4);
    v10 = (this.y + 7);
    if (v8 ==0) goto L340;
    goto L343;
    L340:
    L343:
    v11 = ui.FwmTheme.TEXT_DIM;
    v12 = 0;
    L348:
    if (v12 >= 2) goto L390;
    ctx.fill((v9 + v12), (v10 + v12), ((v9 + v12) + 1), ((v10 + v12) + 2), v11);
    v12 = v12 + 1;
    goto L348;
    L390:
    v12 = 0;
    L393:
    if (v12 >= 4) goto L439;
    ctx.fill(((v9 + 2) + v12), ((v10 + 2) - v12), ((v9 + 3) + v12), ((v10 + 4) - v12), v11);
    v12 = v12 + 1;
    goto L393;
    L439:
    return;
}

private void renderThreadBar(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    if (this.selected != null) goto L8;
    return;
    L8:
    v5 = this.threadX();
    v6 = this.threadWidth();
    v7 = this.barY();
    v8 = this.ignoreWidth(font);
    v9 = this.ignoreX(font);
    v10 = chat.Messages.isIgnored(this.selected);
    if (mouseX < v9) goto L85;
    if (mouseX >= (v9 + v8)) goto L85;
    if (mouseY < v7) goto L85;
    if (mouseY >= (v7 + 11)) goto L85;
    goto L86;
    L85:
    L86:
    v11 = 0;
    if (v10 ==0) goto L125;
    goto L128;
    L125:
    L128:
    font.text(ui.panel.MessagesPanel.trim(font, this.selected, ((this.tpaX(font) - v5) - 4)), v5, (v7 + 2), ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.TEXT_DIM, 0);
    if (mouseX < this.cogX()) goto L172;
    if (mouseX >= (this.cogX() + 11)) goto L172;
    if (mouseY < v7) goto L172;
    if (mouseY >= (v7 + 11)) goto L172;
    goto L173;
    L172:
    L173:
    v12 = 0;
    if (v12 ==0) goto L200;
    goto L203;
    L200:
    L203:
    ui.FwmTheme.box(this.cogX(), v7, 11, 11, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v12 ==0) goto L233;
    goto L236;
    L233:
    L236:
    ui.SharpIcons.draw("icon_cog", (this.cogX() + 1), (v7 + 1), 9, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    if (v10 ==0) goto L259;
    goto L262;
    L259:
    L262:
    if (v11 ==0) goto L273;
    goto L287;
    L273:
    if (v10 ==0) goto L284;
    goto L287;
    L284:
    L287:
    ui.FwmTheme.box(v8, 11, ui.FwmTheme.DISABLED_FILL, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.DISABLED_EDGE, ui.FwmTheme.OFF);
    v13 = this.ignoreLabel();
    if (v11 ==0) goto L329;
    goto L343;
    L329:
    if (v10 ==0) goto L340;
    goto L343;
    L340:
    L343:
    v13.text((v9 + ((v8 - font.width(v13)) / 2)), (v7 + 2), ui.FwmTheme.ACCENT, ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.TEXT_DIM, 0);
    this.smallButton(ctx, font, "fwm.messages.tpa", this.tpaX(font), v7, mouseX, mouseY);
    this.smallButton(ctx, font, "fwm.messages.tpahere", this.tpaHereX(font), v7, mouseX, mouseY);
    this.smallButton(ctx, font, "fwm.messages.hide", this.hideX(font), v7, mouseX, mouseY);
    return;
}

private void smallButton(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String key, int bx, int by, int mouseX, int mouseY) {
    v8 = ui.panel.MessagesPanel.smallWidth(font, key);
    if (mouseX < bx) goto L45;
    if (mouseX >= (bx + v8)) goto L45;
    if (mouseY < by) goto L45;
    if (mouseY >= (by + 11)) goto L45;
    goto L46;
    L45:
    L46:
    v9 = 0;
    if (v9 ==0) goto L71;
    goto L74;
    L71:
    L74:
    ui.FwmTheme.box(bx, by, v8, 11, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = ui.Lang.of(key, new Object[0]);
    if (v9 ==0) goto L120;
    goto L123;
    L120:
    L123:
    font.text(v10, (bx + ((v8 - font.width(v10)) / 2)), (by + 2), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void askTeleport(String command, String askKey) {
    v3 = this.selected;
    if (v3 != null) goto L10;
    return;
    L10:
    new Object[1][0] = v3;
    this.confirm.ask(ui.Lang.of(askKey, new Object[1]), "fwm.confirm.send", /*indy*/run(command, v3));
    return;
}

private void renderContacts(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, List entries, int mouseX, int mouseY) {
    v6 = this.listWidth();
    v7 = this.contactsTop();
    if (entries.isEmpty() ==0) goto L172;
    if (this.newChat !=0) goto L55;
    if (this.search.getValue().isBlank() ==0) goto L47;
    goto L50;
    L47:
    L50:
    v8 = "fwm.messages.noMatch";
    goto L93;
    L55:
    if (this.search.getValue().isBlank() ==0) goto L76;
    v8 = "fwm.messages.typeName";
    goto L93;
    L76:
    if (chat.PlayerSuggest.waiting() ==0) goto L88;
    goto L91;
    L88:
    L91:
    v8 = "fwm.messages.noMatch";
    L93:
    v9 = (v7 + 2);
    v10 = font.split(ui.Lang.of(v8, new Object[0]), (v6 - 6)).iterator();
    L124:
    if (v10.hasNext() ==0) goto L171;
    v11 = ((util.FormattedCharSequence)v10.next());
    ctx.text(font, v11, (this.x + 3), v9, ui.FwmTheme.TEXT_DIM, 0);
    v9 = v9 + 10;
    goto L124;
    L171:
    return;
    L172:
    v8 = this.contactsShown();
    this.contactScroll = Math.max(0, Math.min(this.contactScroll, this.maxContactScroll(entries.size())));
    ctx.enableScissor(this.x, v7, (this.x + v6), (this.y + this.height));
    v9 = 0;
    L232:
    if (v9 >= v8) goto L706;
    if ((this.contactScroll + v9) >= entries.size()) goto L706;
    v10 = ((ui.panel.MessagesPanel$Entry)entries.get((this.contactScroll + v9)));
    v11 = v10.conversation();
    v12 = (v7 + (v9 * 24));
    v13 = v10.name().equals(this.selected);
    if (mouseX < this.x) goto L360;
    if (mouseX >= (this.x + v6)) goto L360;
    if (mouseY < v12) goto L360;
    if (mouseY >= (v12 + 24)) goto L360;
    if (mouseY >= (this.y + this.height)) goto L360;
    goto L361;
    L360:
    L361:
    v14 = 0;
    ui.FwmTheme.sidebarButton(ctx, this.x, v12, v6, 22, v13, v14, 0);
    if (v11 != null) goto L391;
    goto L396;
    L391:
    L396:
    v15 = v11.unread;
    if (chat.Messages.isIgnored(v10.name()) ==0) goto L415;
    goto L423;
    L415:
    L423:
    v16 = ui.FwmTheme.sidebarTextColor(v13, v14, 0);
    if (v15 <=0) goto L440;
    goto L441;
    L440:
    L441:
    v17 = (12 - 0);
    ctx.text(font, ui.panel.MessagesPanel.trim(font, v10.name(), v17), (this.x + 4), (v12 + 4), v16, 0);
    if (v11 != null) goto L482;
    goto L487;
    L482:
    L487:
    v18 = v11.newest();
    if (v18 != null) goto L507;
    goto L540;
    L507:
    if (v18.outgoing ==0) goto L528;
    goto L530;
    L528:
    L530:
    L540:
    v19 = "" + v18.text;
    ctx.text(font, ui.panel.MessagesPanel.trim(font, v19, (v6 - 8)), (this.x + 4), (v12 + 13), ui.FwmTheme.TEXT_DIM, 0);
    if (v14 ==0) goto L611;
    if (v11 == null) goto L611;
    this.cross(ctx, (((this.x + v6) - 12) + 2), (v12 + 7), ui.FwmTheme.DISABLED_TEXT);
    goto L700;
    L611:
    if (v15 <=0) goto L700;
    if (v15 <= 9) goto L629;
    goto L634;
    L629:
    L634:
    v20 = String.valueOf(v15);
    v21 = (font.width(v20) + 4);
    ui.FwmTheme.pill(ctx, (((this.x + v6) - v21) - 3), (v12 + 3), v21, 9, ui.FwmTheme.ACCENT);
    ctx.text(font, v20, (((this.x + v6) - v21) - 1), (v12 + 4), ui.FwmTheme.TEXT_ON_ACCENT, 0);
    L700:
    v9 = v9 + 1;
    goto L232;
    L706:
    ctx.disableScissor();
    v9 = this.maxContactScroll(entries.size());
    if (v9 <=0) goto L834;
    v10 = ((this.x + v6) - 3);
    v11 = this.contactsHeight();
    v12 = Math.max(10, ((v11 * (v11 / 24)) / entries.size()));
    ctx.fill(v10, v7, (v10 + 3), (v7 + v11), ui.FwmTheme.OFF);
    ctx.fill(v10, (v7 + (((v11 - v12) * this.contactScroll) / v9)), (v10 + 3), ((v7 + (((v11 - v12) * this.contactScroll) / v9)) + v12), ui.FwmTheme.ACCENT);
    L834:
    return;
}

private void renderThread(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = this.threadX();
    v4 = this.threadWidth();
    if (this.selected != null) goto L22;
    goto L29;
    L22:
    L29:
    v5 = chat.Messages.find(this.selected);
    if (v5 == null) goto L49;
    if (v5.messages.isEmpty() ==0) goto L156;
    L49:
    if (this.selected != null) goto L69;
    goto L86;
    L69:
    new Object[1][0] = this.selected;
    L86:
    v6 = ui.Lang.of("fwm.messages.emptyChat", new Object[1]);
    v7 = (this.threadTop() + 4);
    v8 = font.split(v6, (v4 - 4)).iterator();
    L113:
    if (v8.hasNext() ==0) goto L155;
    v9 = ((util.FormattedCharSequence)v8.next());
    ctx.text(font, v9, v3, v7, ui.FwmTheme.TEXT_DIM, 0);
    v7 = v7 + 10;
    goto L113;
    L155:
    return;
    L156:
    this.rebuild(font, v5, v4);
    v6 = this.threadTop();
    v7 = (this.threadBottom() - v6);
    v8 = this.contentHeight();
    v9 = Math.max(0, (v8 - v7));
    if (this.stickToBottom ==0) goto L215;
    this.threadScroll = v9;
    this.stickToBottom = 0;
    L215:
    this.threadScroll = Math.max(0, Math.min(v9, this.threadScroll));
    ctx.enableScissor(v3, v6, (v3 + v4), this.threadBottom());
    v10 = ((v6 - this.threadScroll) + Math.max(0, (v7 - v8)));
    v11 = this.layout.iterator();
    L277:
    if (v11.hasNext() ==0) goto L327;
    v12 = ((ui.panel.MessagesPanel$Bubble)v11.next());
    this.drawBubble(ctx, font, v12, v3, v4, v10);
    v10 = (v10 + (v12.height() + 4));
    goto L277;
    L327:
    ctx.disableScissor();
    if (v9 <=0) goto L417;
    v11 = ((v3 + v4) - 3);
    v12 = Math.max(10, ((v7 * v7) / v8));
    v13 = (v6 + (((v7 - v12) * this.threadScroll) / v9));
    ctx.fill(v11, v6, (v11 + 3), (v6 + v7), ui.FwmTheme.OFF);
    ctx.fill(v11, v13, (v11 + 3), (v13 + v12), ui.FwmTheme.ACCENT);
    L417:
    return;
}

private void drawBubble(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, ui.panel.MessagesPanel$Bubble bubble, int tx, int tw, int top) {
    if (bubble.outgoing() ==0) goto L24;
    goto L26;
    L24:
    L26:
    v7 = tx;
    if (bubble.header() == null) goto L82;
    if (bubble.outgoing() ==0) goto L61;
    goto L63;
    L61:
    L63:
    v8 = v7;
    ctx.text(font, bubble.header(), v8, top, ui.FwmTheme.TEXT_DIM, 0);
    L82:
    if (bubble.header() != null) goto L95;
    goto L97;
    L95:
    L97:
    v8 = (0 + 10);
    if (bubble.header() != null) goto L115;
    goto L117;
    L115:
    L117:
    v9 = (0 - 10);
    if (bubble.outgoing() ==0) goto L144;
    goto L147;
    L144:
    L147:
    if (bubble.outgoing() ==0) goto L160;
    goto L163;
    L160:
    L163:
    ui.FwmTheme.box(v8, bubble.width(), v9, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = (v8 + 4);
    v11 = bubble.lines().iterator();
    L183:
    if (v11.hasNext() ==0) goto L228;
    v12 = ((util.FormattedCharSequence)v11.next());
    ctx.text(font, v12, (v7 + 4), v10, ui.FwmTheme.TEXT, 0);
    v10 = v10 + 10;
    goto L183;
    L228:
    return;
}

private void renderInput(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    v5 = this.threadX();
    v6 = this.threadWidth();
    if (this.selected == null) goto L23;
    goto L24;
    L23:
    L24:
    v7 = 0;
    if (this.input.isFocused() ==0) goto L64;
    goto L67;
    L64:
    L67:
    ui.FwmTheme.box(v5, ((this.y + this.height) - 14), v6, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v7 ==0) goto L87;
    this.input.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    L87:
    return;
}

private boolean entries() {
    v1 = this.search.getValue().trim();
    if (this.newChat ==0) goto L82;
    v2 = ?;
    v3 = chat.PlayerSuggest.forQuery(v1).iterator();
    L36:
    if (v3.hasNext() ==0) goto L80;
    v4 = ((String)v3.next());
    v2.add(?);
    goto L36;
    L80:
    return v2;
    L82:
    v2 = v1.toLowerCase(Locale.ROOT);
    v3 = ?;
    v4 = chat.Messages.conversations().iterator();
    L108:
    if (v4.hasNext() ==0) goto L179;
    v5 = ((chat.Conversation)v4.next());
    if (v2.isEmpty() !=0) goto L155;
    if (v5.partner.toLowerCase(Locale.ROOT).contains(v2) ==0) goto L176;
    L155:
    v3.add(?);
    L176:
    goto L108;
    L179:
    return v3;
}

private int contentHeight() {
    v1 = 0;
    v2 = this.layout.iterator();
    L12:
    if (v2.hasNext() ==0) goto L43;
    v3 = ((ui.panel.MessagesPanel$Bubble)v2.next());
    v1 = (v1 + (v3.height() + 4));
    goto L12;
    L43:
    return Math.max(0, (v1 - 4));
}

private boolean rebuild(client.gui.Font font, chat.Conversation conversation, int columnWidth) {
    v4 = conversation.messages.size();
    if (columnWidth != this.layoutWidth) goto L43;
    if (v4 != this.layoutCount) goto L43;
    if (conversation.partner.equals(this.layoutPartner) ==0) goto L43;
    return;
    L43:
    this.layoutWidth = columnWidth;
    this.layoutCount = v4;
    this.layoutPartner = conversation.partner;
    this.layout = ?;
    v5 = Math.max(40, Math.round((((f)((columnWidth - 3) - 2)) * 0.7799999713897705)));
    v6 = (v5 - 8);
    v7 = null;
    v8 = conversation.messages.iterator();
    L114:
    if (v8.hasNext() ==0) goto L391;
    v9 = ((chat.PrivateMessage)v8.next());
    if (v7 == null) goto L154;
    if (v7.booleanValue() == v9.outgoing) goto L158;
    L154:
    goto L159;
    L158:
    L159:
    v10 = 0;
    v7 = Boolean.valueOf(v9.outgoing);
    if (v10 ==0) goto L217;
    if (v9.outgoing ==0) goto L197;
    goto L201;
    L197:
    L201:
    goto L218;
    L217:
    L218:
    v11 = null;
    v12 = font.split(network.chat.Component.literal(v9.text), v6);
    if (v12.isEmpty() ==0) goto L249;
    goto L114;
    L249:
    v13 = 0;
    v14 = v12.iterator();
    L261:
    if (v14.hasNext() ==0) goto L299;
    v15 = ((util.FormattedCharSequence)v14.next());
    v13 = Math.max(v13, font.width(v15));
    goto L261;
    L299:
    v14 = Math.min(v5, (v13 + 8));
    v15 = (((v12.size() * 10) + 8) - 2);
    if (v11 == null) goto L346;
    v14 = Math.max(v14, font.width(v11));
    L346:
    if (v11 != null) goto L376;
    goto L378;
    L376:
    L378:
    ?.add(?);
    goto L114;
    L391:
    this.stickToBottom = 1;
    return;
}

private static String clock(long time) {
    v2 = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault());
    new Object[2][0] = Integer.valueOf(v2.getHour());
    new Object[2][1] = Integer.valueOf(v2.getMinute());
    return String.format("%02d:%02d", new Object[2]);
}

private static network.chat.MutableComponent trim(client.gui.Font font, String value, int width) {
    if (font.width(value) > width) goto L14;
    return network.chat.Component.literal(value);
    L14:
    return network.chat.Component.literal(font.plainSubstrByWidth(value, Math.max(4, (width - 6))) + '...');
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.confirm.open() ==0) goto L20;
    return this.confirm.mouseClicked(mouseX, mouseY);
    L20:
    if (this.contains(mouseX, mouseY) !=0) goto L31;
    return 0;
    L31:
    if (cmp(mouseX,((d)(this.x + this.listWidth()))) >=0) goto L168;
    if (cmp(mouseY,((d)this.contactsTop())) >=0) goto L60;
    goto L61;
    L60:
    L61:
    v6 = 0;
    this.input.setFocused(0);
    if (v6 ==0) goto L92;
    if (cmp(mouseX,((d)this.buttonX())) <0) goto L92;
    this.toggleNewChat();
    return 1;
    L92:
    if (v6 ==0) goto L119;
    if (this.showReadAll() ==0) goto L119;
    if (cmp(mouseX,((d)this.readAllX())) <0) goto L119;
    chat.Messages.markAllRead();
    return 1;
    L119:
    this.search.setFocused(v6);
    if (v6 !=0) goto L166;
    if (cmp(mouseX,((d)((this.x + this.listWidth()) - 12))) <0) goto L161;
    if (this.deleteAt(mouseY) ==0) goto L161;
    return 1;
    L161:
    this.selectAt(mouseY);
    L166:
    return 1;
    L168:
    if (this.selected == null) goto L393;
    if (cmp(mouseY,((d)this.barY())) <0) goto L393;
    if (cmp(mouseY,((d)(this.barY() + this.barH()))) >=0) goto L393;
    v6 = client.Minecraft.getInstance().font;
    if (cmp(mouseX,((d)this.cogX())) <0) goto L236;
    this.host.openSub(?);
    return 1;
    L236:
    if (cmp(mouseX,((d)this.tpaX(v6))) <0) goto L274;
    if (cmp(mouseX,((d)(this.tpaHereX(v6) - 3))) >=0) goto L274;
    this.askTeleport("tpa", "fwm.messages.tpaAsk");
    return 1;
    L274:
    if (cmp(mouseX,((d)this.tpaHereX(v6))) <0) goto L312;
    if (cmp(mouseX,((d)(this.hideX(v6) - 3))) >=0) goto L312;
    this.askTeleport("tpahere", "fwm.messages.tpahereAsk");
    return 1;
    L312:
    if (cmp(mouseX,((d)this.hideX(v6))) <0) goto L352;
    if (cmp(mouseX,((d)(this.ignoreX(v6) - 3))) >=0) goto L352;
    chat.Messages.hide(this.selected);
    this.selected = null;
    return 1;
    L352:
    if (cmp(mouseX,((d)this.ignoreX(v6))) <0) goto L391;
    if (chat.Messages.isIgnored(this.selected) ==0) goto L384;
    chat.Messages.unignore(this.selected);
    goto L391;
    L384:
    chat.Messages.ignore(this.selected);
    L391:
    return 1;
    L393:
    this.search.setFocused(0);
    if (this.selected == null) goto L434;
    if (cmp(mouseY,((d)((this.y + this.height) - 14))) <0) goto L434;
    goto L435;
    L434:
    L435:
    1.setFocused(0);
    return 1;
}

private void cross(client.gui.GuiGraphicsExtractor ctx, int cx, int cy, int color) {
    v5 = 0;
    L3:
    if (v5 >= 5) goto L69;
    ctx.fill((cx + v5), (cy + v5), ((cx + v5) + 1), ((cy + v5) + 1), color);
    ctx.fill(((cx + 4) - v5), (cy + v5), ((cx + 5) - v5), ((cy + v5) + 1), color);
    v5 = v5 + 1;
    goto L3;
    L69:
    return;
}

private void deleteAt(double mouseY) {
    v3 = this.entries();
    v4 = (this.contactScroll + ((i)((mouseY - ((d)this.contactsTop())) / 24.0)));
    if (v4 <0) goto L40;
    if (v4 < v3.size()) goto L42;
    L40:
    return 0;
    L42:
    v5 = ((ui.panel.MessagesPanel$Entry)v3.get(v4));
    if (v5.conversation() != null) goto L65;
    return 0;
    L65:
    v6 = v5.name();
    new Object[1][0] = v6;
    this.confirm.ask(ui.Lang.of("fwm.messages.deleteAsk", new Object[1]), /*indy*/run(this, v6));
    return 1;
}

private void selectAt(double mouseY) {
    v3 = this.entries();
    v4 = (this.contactScroll + ((i)((mouseY - ((d)this.contactsTop())) / 24.0)));
    if (v4 <0) goto L40;
    if (v4 < v3.size()) goto L41;
    L40:
    return;
    L41:
    this.select(((ui.panel.MessagesPanel$Entry)v3.get(v4)));
    return;
}

private void select(ui.panel.MessagesPanel$Entry entry) {
    this.selected = entry.name();
    chat.Messages.markRead(entry.conversation());
    if (this.newChat ==0) goto L71;
    this.newChat = 0;
    this.search.setValue("");
    this.search.setHint(this.searchHint());
    this.search.setFocused(0);
    this.input.setFocused(1);
    this.contactScroll = 0;
    chat.PlayerSuggest.reset();
    L71:
    this.layoutCount = m1;
    this.stickToBottom = 1;
    return;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    if (cmp(mouseX,((d)(this.x + this.listWidth()))) >=0) goto L52;
    this.contactScroll = Math.max(0, Math.min(this.maxContactScroll(this.entries().size()), (this.contactScroll - ((i)Math.signum(v5)))));
    return 1;
    L52:
    this.threadScroll = Math.max(0, (this.threadScroll - ((((i)Math.signum(v5)) * 10) * 2)));
    return 1;
}

public boolean isCapturingKeys() {
    return this.confirm.open();
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.confirm.open() ==0) goto L19;
    return this.confirm.keyPressed(keyCode);
    L19:
    if (keyCode == 40) goto L31;
    if (keyCode != 88) goto L35;
    L31:
    goto L36;
    L35:
    L36:
    v4 = 0;
    if (this.search.isFocused() ==0) goto L120;
    if (v4 ==0) goto L102;
    v5 = this.entries();
    if (v5.isEmpty() !=0) goto L100;
    this.select(((ui.panel.MessagesPanel$Entry)v5.get(0)));
    this.search.setFocused(0);
    this.input.setFocused(1);
    L100:
    return 1;
    L102:
    return this.search.keyPressed(?);
    L120:
    if (this.input.isFocused() !=0) goto L132;
    return 0;
    L132:
    if (v4 ==0) goto L181;
    v5 = this.input.getValue();
    if (v5.isBlank() !=0) goto L179;
    if (this.selected == null) goto L179;
    chat.Messages.send(this.selected, v5);
    this.input.setValue("");
    L179:
    return 1;
    L181:
    return this.input.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent chr) {
    if (this.search.isFocused() ==0) goto L19;
    return this.search.charTyped(chr);
    L19:
    if (this.input.isFocused() ==0) goto L44;
    if (this.input.charTyped(chr) ==0) goto L44;
    goto L45;
    L44:
    L45:
    return 0;
}

private boolean lambda$deleteAt$0(String partner) {
    chat.Messages.forget(partner);
    if (partner.equals(this.selected) ==0) goto L20;
    this.selected = null;
    L20:
    return;
}

private static void lambda$askTeleport$0(String command, String partner) {
    v2 = client.Minecraft.getInstance();
    if (v2.getConnection() == null) goto L25;
    v2.getConnection().sendCommand(command + ' ' + partner);
    L25:
    return;
}

private void lambda$onResize$0(String text) {
    this.contactScroll = 0;
    return;
}

