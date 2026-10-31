// class net/friendswithmoney/fwm/ui/panel/TicketsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field GREEN I;
field RED I;
field GOLD I;
field ROW I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field fields Ljava/util/List;;
field keyField Lnet/minecraft/client/gui/components/EditBox;;
field amountField Lnet/minecraft/client/gui/components/EditBox;;
field notesField Lnet/minecraft/client/gui/components/EditBox;;
field descField Lnet/minecraft/client/gui/components/EditBox;;
field hits Ljava/util/List;;
field mode Lnet/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode;;
field spawnerIndex I;
field note Ljava/lang/String;;
field noteOk Z;
field sending Z;
field ticketScroll I;
field wanted Ljava/lang/String;;
field chat Lnet/friendswithmoney/fwm/net/Tickets$Ticket;;
field chatScroll I;
public void <init>(ui.panel.PanelHost host) {
    return;
}

public void <init>(ui.panel.PanelHost host, String spawner) {
    this.fields = ?;
    this.hits = ?;
    this.mode = ui.panel.TicketsPanel$Mode.BUY;
    this.note = "";
    this.host = host;
    this.wanted = spawner;
    net.Tickets.loadSpawners();
    net.Tickets.refresh();
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.tickets", new Object[0]);
}

private int colW() {
    return ((this.width - 8) / 2);
}

private int rightX() {
    return ((this.x + this.colW()) + 8);
}

private int bodyY() {
    return (this.y + 34);
}

protected void onResize() {
    v1 = this.fields.iterator();
    L10:
    if (v1.hasNext() ==0) goto L42;
    v2 = ((client.gui.components.EditBox)v1.next());
    this.host.detach(v2);
    goto L10;
    L42:
    this.fields.clear();
    v1 = client.Minecraft.getInstance().font;
    this.keyField = this.field(v1, (this.x + 4), (this.y + 59), (this.width - 90), 120, ui.Lang.str("fwm.tickets.keyHint", new Object[0]));
    this.amountField = this.field(v1, (this.x + 4), (this.bodyY() + 61), (this.colW() - 8), 5, "1");
    this.notesField = this.field(v1, (this.x + 4), (this.bodyY() + 93), (this.colW() - 8), 1000, ui.Lang.str("fwm.tickets.notes", new Object[0]));
    this.descField = this.field(v1, (this.x + 4), (this.bodyY() + 41), (this.colW() - 8), 1000, ui.Lang.str("fwm.tickets.describe", new Object[0]));
    if (this.amountField.getValue().isEmpty() ==0) goto L234;
    this.amountField.setValue("1");
    L234:
    this.showFields();
    return;
}

private boolean field(client.gui.Font font, int fx, int fy, int w, int max, String hint) {
    v7 = ?;
    v7.setBordered(0);
    v7.setMaxLength(max);
    v7.setTextColor(ui.FwmTheme.TEXT);
    v7.setHint(network.chat.Component.literal(hint));
    this.fields.add(v7);
    return v7;
}

private void showFields() {
    v1 = this.fields.iterator();
    L10:
    if (v1.hasNext() ==0) goto L42;
    v2 = ((client.gui.components.EditBox)v1.next());
    this.host.detach(v2);
    goto L10;
    L42:
    if (net.ModAccount.hasKey() !=0) goto L62;
    this.host.attach(this.keyField);
    return;
    L62:
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L88;
    this.host.attach(this.descField);
    goto L114;
    L88:
    this.host.attach(this.amountField);
    this.host.attach(this.notesField);
    L114:
    return;
}

// DECOMPILE ERROR in render: 'dict' object has no attribute 'op_type'
private void drawNew(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.x;
    v7 = this.bodyY();
    v8 = this.colW();
    ctx.text(font, ui.Lang.of("fwm.tickets.new", new Object[0]), v6, (v7 - 6), ui.FwmTheme.TEXT_DIM, 0);
    new String[3][0] = ui.Lang.str("fwm.tickets.buy", new Object[0]);
    new String[3][1] = ui.Lang.str("fwm.tickets.sell", new Object[0]);
    new String[3][2] = "Middleman";
    v9 = new String[3];
    v10 = 0;
    v11 = v9;
    v12 = v11.length;
    v13 = 0;
    L97:
    if (v13 >= v12) goto L128;
    v14 = v11[v13];
    v10 = (v10 + font.width(v14));
    v13 = v13 + 1;
    goto L97;
    L128:
    v11 = Math.max(0, ((v8 - 4) - v10));
    v12 = v6;
    v13 = 0;
    L148:
    if (v13 >= 3) goto L275;
    if (v13 != 2) goto L171;
    goto L185;
    L171:
    L185:
    v14 = (font.width(v9[v13]) + (v11 / 3));
    if (v13 !=0) goto L198;
    goto L213;
    L198:
    if (v13 != 1) goto L210;
    goto L213;
    L210:
    L213:
    v15 = ui.panel.TicketsPanel$Mode.MIDDLEMAN;
    if (this.mode != v15) goto L245;
    goto L246;
    L245:
    L246:
    ctx.chip(font, v12, (v7 + 6), v14, v9[v13], 1, 0, mouseX, mouseY, /*indy*/run(this, v15));
    v12 = (v12 + (v14 + 2));
    v13 = v13 + 1;
    goto L148;
    L275:
    v13 = (v7 + 114);
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L428;
    ctx.text(font, ui.Lang.of("fwm.tickets.describeLabel", new Object[0]), v6, (v7 + 25), ui.FwmTheme.TEXT_DIM, 0);
    if (this.descField.isFocused() ==0) goto L349;
    goto L352;
    L349:
    L352:
    ui.FwmTheme.box(v6, (v7 + 36), v8, 16, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.descField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    ctx.text(font, network.chat.Component.literal(this.descField.getValue().length() + ' / 1000'), ((v6 + v8) - font.width(this.descField.getValue().length() + ' / 1000')), (v7 + 56), ui.FwmTheme.TEXT_DIM, 0);
    goto L972;
    L428:
    if (this.mode != ui.panel.TicketsPanel$Mode.BUY) goto L442;
    goto L443;
    L442:
    L443:
    v14 = 0;
    v15 = this.offered(v14);
    if (v15.isEmpty() ==0) goto L493;
    if (net.Tickets.spawnerError().isEmpty() ==0) goto L485;
    goto L488;
    L485:
    L488:
    v16 = net.Tickets.spawnerError();
    goto L621;
    L493:
    if (this.wanted == null) goto L557;
    v17 = 0;
    L503:
    if (v17 >= v15.size()) goto L552;
    if (((net.Tickets$Spawner)v15.get(v17)).name.equalsIgnoreCase(this.wanted) ==0) goto L546;
    this.spawnerIndex = v17;
    L546:
    v17 = v17 + 1;
    goto L503;
    L552:
    this.wanted = null;
    L557:
    this.spawnerIndex = Math.floorMod(this.spawnerIndex, v15.size());
    v17 = ((net.Tickets$Spawner)v15.get(this.spawnerIndex));
    if (v14 ==0) goto L609;
    goto L614;
    L609:
    L614:
    v16 = v17.ankaufPreis + ' · $' + v17.verkaufPreis;
    L621:
    ui.FwmTheme.box(ctx, v6, (v7 + 24), v8, 16, ui.FwmTheme.RAISED, ui.FwmTheme.EDGE);
    ctx.text(font, network.chat.Component.literal("\u2039"), (v6 + 5), (v7 + 28), ui.FwmTheme.ACCENT, 0);
    ctx.text(font, network.chat.Component.literal("\u203a"), ((v6 + v8) - 9), (v7 + 28), ui.FwmTheme.ACCENT, 0);
    v17 = network.chat.Component.literal(font.plainSubstrByWidth(v16, (v8 - 24)));
    ctx.text(font, v17, (v6 + ((v8 - font.width(v17)) / 2)), (v7 + 28), ui.FwmTheme.TEXT, 0);
    this.hits.add(?);
    this.hits.add(?);
    ctx.text(font, ui.Lang.of("fwm.tickets.amount", new Object[0]), v6, (v7 + 46), ui.FwmTheme.TEXT_DIM, 0);
    if (this.amountField.isFocused() ==0) goto L877;
    goto L880;
    L877:
    L880:
    ui.FwmTheme.box(v6, (v7 + 57), v8, 15, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.amountField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    ctx.text(font, ui.Lang.of("fwm.tickets.notesLabel", new Object[0]), v6, (v7 + 78), ui.FwmTheme.TEXT_DIM, 0);
    if (this.notesField.isFocused() ==0) goto L953;
    goto L956;
    L953:
    L956:
    ui.FwmTheme.box(v6, (v7 + 89), v8, 15, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.notesField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    L972:
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L988;
    goto L991;
    L988:
    L991:
    v14 = "spawner";
    v15 = net.Tickets.hasLive(v14);
    if (this.sending ==0) goto L1020;
    goto L1048;
    L1020:
    if (v15 ==0) goto L1038;
    goto L1048;
    L1038:
    L1048:
    v16 = ui.Lang.str("fwm.tickets.open", new Object[0]);
    if (v15 !=0) goto L1078;
    if (this.sending ==0) goto L1082;
    L1078:
    goto L1088;
    L1082:
    L1088:
    ctx.button(font, v6, v13, v8, 16, v16, mouseX, mouseY, null, /*indy*/run(this));
    if (this.note.isEmpty() !=0) goto L1188;
    v17 = font.split(network.chat.Component.literal(this.note), v8);
    v18 = 0;
    L1119:
    if (v18 >= Math.min(2, v17.size())) goto L1188;
    if (this.noteOk ==0) goto L1175;
    goto L1178;
    L1175:
    L1178:
    font.text(((util.FormattedCharSequence)v17.get(v18)), v6, ((v13 + 20) + (v18 * 10)), -10688364, -2067340, 0);
    v18 = v18 + 1;
    goto L1119;
    L1188:
    return;
}

private boolean drawList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.rightX();
    v6 = this.bodyY();
    v7 = this.colW();
    if (this.chat == null) goto L41;
    this.drawChat(ctx, font, v5, v6, v7, mouseX, mouseY);
    return;
    L41:
    ctx.text(font, ui.Lang.of("fwm.tickets.mine", new Object[0]), v5, (v6 - 6), ui.FwmTheme.TEXT_DIM, 0);
    v8 = ui.Lang.str("fwm.tickets.reload", new Object[0]);
    v9 = font.width(v8);
    if (mouseX < ((v5 + v7) - v9)) goto L131;
    if (mouseX >= (v5 + v7)) goto L131;
    if (mouseY < (v6 - 7)) goto L131;
    if (mouseY >= (v6 + 3)) goto L131;
    goto L132;
    L131:
    L132:
    v10 = 0;
    if (v10 ==0) goto L165;
    goto L168;
    L165:
    L168:
    font.text(network.chat.Component.literal(v8), ((v5 + v7) - v9), (v6 - 6), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    this.hits.add(?);
    v11 = net.Tickets.tickets();
    if (v11.isEmpty() ==0) goto L253;
    ctx.text(font, ui.Lang.of("fwm.tickets.none", new Object[0]), v5, (v6 + 8), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L253:
    v12 = Math.max(1, (((this.y + this.height) - (v6 + 6)) / 26));
    this.ticketScroll = Math.max(0, Math.min(this.ticketScroll, (v11.size() - v12)));
    v13 = 0;
    L305:
    if (v13 >= v12) goto L660;
    if ((this.ticketScroll + v13) >= v11.size()) goto L660;
    v14 = ((net.Tickets$Ticket)v11.get((this.ticketScroll + v13)));
    v15 = ((v6 + 6) + ((v13 * 2) * 13));
    ui.FwmTheme.box(ctx, v5, v15, v7, 24, ui.FwmTheme.INSET, ui.FwmTheme.OFF);
    if ("spawner".equals(v14.type) ==0) goto L401;
    goto L404;
    L401:
    L404:
    v16 = "Middleman";
    if ("success".equals(v14.status) ==0) goto L426;
    goto L462;
    L426:
    if ("failure".equals(v14.status) ==0) goto L446;
    goto L462;
    L446:
    if (v14.live() ==0) goto L459;
    goto L462;
    L459:
    L462:
    v17 = ui.FwmTheme.TEXT_DIM;
    ctx.text(font, network.chat.Component.literal(v16), (v5 + 4), (v15 + 3), ui.FwmTheme.TEXT, 0);
    v18 = network.chat.Component.literal(font.plainSubstrByWidth(net.Tickets.statusText(v14), ((v7 - 12) - font.width(v16))));
    ctx.text(font, v18, (((v5 + v7) - 4) - font.width(v18)), (v15 + 3), v17, 0);
    ctx.text(font, network.chat.Component.literal(ui.panel.TicketsPanel.date(v14.createdAt)), (v5 + 4), (((v15 + 3) + 13) - 2), ui.FwmTheme.TEXT_DIM, 0);
    v19 = ui.Lang.t("Chat \u203a", "Chat \u203a");
    ctx.text(font, network.chat.Component.literal(v19), (((v5 + v7) - 4) - font.width(v19)), (((v15 + 3) + 13) - 2), ui.FwmTheme.ACCENT, 0);
    this.hits.add(?);
    v13 = v13 + 1;
    goto L305;
    L660:
    return;
}

private void drawChat(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int lx, int ly, int lw, int mouseX, int mouseY) {
    v8 = this.chat;
    v9 = '‹ ' + ui.Lang.t("Tickets", "Tickets");
    if (mouseX < lx) goto L64;
    if (mouseX >= (lx + font.width(v9))) goto L64;
    if (mouseY < (ly - 7)) goto L64;
    if (mouseY >= (ly + 3)) goto L64;
    goto L65;
    L64:
    L65:
    v10 = 0;
    if (v10 ==0) goto L91;
    goto L94;
    L91:
    L94:
    font.text(network.chat.Component.literal(v9), lx, (ly - 6), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    this.hits.add(?);
    v11 = net.Tickets.statusText(v8);
    if ("success".equals(v8.status) ==0) goto L162;
    goto L198;
    L162:
    if ("failure".equals(v8.status) ==0) goto L182;
    goto L198;
    L182:
    if (v8.live() ==0) goto L195;
    goto L198;
    L195:
    L198:
    v12 = ui.FwmTheme.TEXT_DIM;
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v11, ((lw - font.width(v9)) - 8))), ((lx + lw) - font.width(font.plainSubstrByWidth(v11, ((lw - font.width(v9)) - 8)))), (ly - 6), v12, 0);
    v13 = (ly + 6);
    v14 = ((this.y + this.height) - 12);
    ui.FwmTheme.box(ctx, lx, v13, lw, (v14 - v13), ui.FwmTheme.INSET, ui.FwmTheme.OFF);
    v15 = net.Tickets.messages(v8.id);
    if (v15.isEmpty() ==0) goto L382;
    if (net.Tickets.loading(v8.id) ==0) goto L345;
    goto L354;
    L345:
    L354:
    v16 = ui.Lang.t("Noch keine Nachrichten.", "No messages yet.");
    ctx.text(font, network.chat.Component.literal(v16), (lx + 6), (v13 + 6), ui.FwmTheme.TEXT_DIM, 0);
    goto L849;
    L382:
    v16 = ?;
    v17 = 0;
    v18 = v15.iterator();
    L403:
    if (v18.hasNext() ==0) goto L571;
    v19 = ((net.Tickets$Message)v18.next());
    if (v19.author != null) goto L439;
    goto L469;
    L439:
    if (v19.author.displayName == null) goto L461;
    goto L469;
    L461:
    L469:
    v20 = v19.author.username;
    if (v19.content != null) goto L485;
    goto L490;
    L485:
    L490:
    v21 = "".split(network.chat.Component.literal(v19.content), (lw - 16));
    v22 = ((10 + (v21.size() * 10)) + 4);
    new Object[4][0] = v20;
    new Object[4][1] = ui.panel.TicketsPanel.date(v19.createdAt);
    new Object[4][2] = v21;
    new Object[4][3] = Integer.valueOf(v22);
    v16.add(new Object[4]);
    v17 = (v17 + v22);
    goto L403;
    L571:
    v18 = ((v14 - v13) - 6);
    this.chatScroll = Math.max(0, Math.min(this.chatScroll, Math.max(0, (v17 - v18))));
    ctx.enableScissor((lx + 1), (v13 + 1), ((lx + lw) - 1), (v14 - 1));
    v19 = (((v14 - 3) - v17) + this.chatScroll);
    v20 = v16.iterator();
    L649:
    if (v20.hasNext() ==0) goto L845;
    v21 = (([Ljava.lang.Object;)v20.next());
    v22 = ((Integer)v21[3]).intValue();
    if ((v19 + v22) <= v13) goto L835;
    if (v19 >= v14) goto L835;
    ctx.text(font, network.chat.Component.literal(((String)v21[0])).withStyle(ChatFormatting.BOLD), (lx + 6), v19, -865972, 0);
    v23 = ((String)v21[1]);
    ctx.text(font, network.chat.Component.literal(v23), (((lx + lw) - 6) - font.width(v23)), v19, ui.FwmTheme.TEXT_DIM, 0);
    v24 = ((List)v21[2]);
    v25 = 0;
    L781:
    if (v25 >= v24.size()) goto L835;
    ctx.text(font, ((util.FormattedCharSequence)v24.get(v25)), (lx + 10), ((v19 + 10) + (v25 * 10)), ui.FwmTheme.TEXT, 0);
    v25 = v25 + 1;
    goto L781;
    L835:
    v19 = (v19 + v22);
    goto L649;
    L845:
    ctx.disableScissor();
    L849:
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.t("Antworten im Discord-Ticket", "Reply in the Discord ticket"), lw)), lx, (v14 + 3), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private static String date(String iso) {
    v1 = Instant.parse(iso).atZone(ZoneId.systemDefault());
    new Object[4][0] = Integer.valueOf(v1.getDayOfMonth());
    new Object[4][1] = Integer.valueOf(v1.getMonthValue());
    new Object[4][2] = Integer.valueOf(v1.getHour());
    new Object[4][3] = Integer.valueOf(v1.getMinute());
    return String.format("%02d.%02d. %02d:%02d", new Object[4]);
    v1 = /*catch Exception*/ ex;
    return "";
}

private boolean offered(boolean buy) {
    v2 = ?;
    v3 = net.Tickets.spawners().iterator();
    L17:
    if (v3.hasNext() ==0) goto L58;
    v4 = ((net.Tickets$Spawner)v3.next());
    if (v4.can(buy) ==0) goto L55;
    v2.add(v4);
    L55:
    goto L17;
    L58:
    return v2;
}

private void setMode(ui.panel.TicketsPanel$Mode m) {
    this.mode = m;
    this.note = "";
    this.showFields();
    return;
}

private concurrent.CompletableFuture send() {
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L16;
    goto L19;
    L16:
    L19:
    v1 = "spawner";
    if (net.Tickets.hasLive(v1) ==0) goto L28;
    return;
    L28:
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L80;
    v3 = this.descField.getValue().trim();
    if (v3.length() >= 5) goto L72;
    this.result(ui.Lang.str("fwm.tickets.tooShort", new Object[0]));
    return;
    L72:
    v2 = net.Tickets.openMiddleman(v3);
    goto L212;
    L80:
    if (this.mode != ui.panel.TicketsPanel$Mode.BUY) goto L95;
    goto L96;
    L95:
    L96:
    v3 = 1.offered(0);
    if (v3.isEmpty() ==0) goto L110;
    return;
    L110:
    v4 = Integer.parseInt(this.amountField.getValue().trim());
    goto L133;
    v5 = /*catch NumberFormatException*/ ex;
    v4 = 0;
    L133:
    if (v4 < 1) goto L147;
    if (v4 <= 10000) goto L162;
    L147:
    this.result(ui.Lang.str("fwm.tickets.badAmount", new Object[0]));
    return;
    L162:
    if (this.mode != ui.panel.TicketsPanel$Mode.BUY) goto L176;
    goto L177;
    L176:
    L177:
    v2 = net.Tickets.openSpawner(0, ((net.Tickets$Spawner)v3.get(Math.floorMod(this.spawnerIndex, v3.size()))), v4, this.notesField.getValue());
    L212:
    this.sending = 1;
    this.note = "";
    v2.whenComplete(/*indy*/accept(this));
    return;
}

private void result(String text) {
    this.noteOk = 0;
    this.note = text;
    return;
}

private boolean chip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int cx, int cy, int w, String label, boolean on, int mouseX, int mouseY, Runnable action) {
    if (mouseX < cx) goto L36;
    if (mouseX >= (cx + w)) goto L36;
    if (mouseY < cy) goto L36;
    if (mouseY >= (cy + 13)) goto L36;
    goto L37;
    L36:
    L37:
    v11 = 0;
    if (on ==0) goto L58;
    goto L61;
    L58:
    L61:
    if (on !=0) goto L71;
    if (v11 ==0) goto L77;
    L71:
    goto L80;
    L77:
    L80:
    ui.FwmTheme.box(cy, w, 13, ui.FwmTheme.RAISED, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v12 = network.chat.Component.literal(font.plainSubstrByWidth(label, (w - 4)));
    if (on ==0) goto L130;
    goto L133;
    L130:
    L133:
    font.text(v12, (cx + ((w - font.width(v12)) / 2)), (cy + 3), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    this.hits.add(?);
    return;
}

private boolean button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int w, int h, String label, int mouseX, int mouseY, Runnable action) {
    if (action == null) goto L41;
    if (mouseX < bx) goto L41;
    if (mouseX >= (bx + w)) goto L41;
    if (mouseY < by) goto L41;
    if (mouseY >= (by + h)) goto L41;
    goto L42;
    L41:
    L42:
    v11 = 0;
    if (action != null) goto L63;
    goto L66;
    L63:
    L66:
    if (v11 ==0) goto L77;
    goto L80;
    L77:
    L80:
    ui.FwmTheme.box(by, w, h, ui.FwmTheme.LOCKED, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v12 = network.chat.Component.literal(font.plainSubstrByWidth(label, (w - 4)));
    if (action != null) goto L136;
    goto L150;
    L136:
    if (v11 ==0) goto L147;
    goto L150;
    L147:
    L150:
    v12.text((bx + ((w - font.width(v12)) / 2)), (by + ((h - 8) / 2)), ui.FwmTheme.TEXT_DIM, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    if (action == null) goto L185;
    this.hits.add(?);
    L185:
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = this.fields.iterator();
    L27:
    if (v6.hasNext() ==0) goto L191;
    v7 = ((client.gui.components.EditBox)v6.next());
    if (cmp(mouseX,((d)(v7.getX() - 2))) <0) goto L112;
    if (cmp(mouseX,((d)((v7.getX() + v7.getWidth()) + 2))) >=0) goto L112;
    if (cmp(mouseY,((d)(v7.getY() - 3))) <0) goto L112;
    if (cmp(mouseY,((d)(v7.getY() + 12))) >=0) goto L112;
    goto L113;
    L112:
    L113:
    v8 = 0;
    if (v8 ==0) goto L184;
    if (v7 != this.keyField) goto L140;
    if (net.ModAccount.hasKey() !=0) goto L184;
    goto L180;
    L140:
    if (this.mode != ui.panel.TicketsPanel$Mode.MIDDLEMAN) goto L162;
    if (v7 != this.descField) goto L184;
    goto L180;
    L162:
    if (v7 == this.descField) goto L184;
    if (v7 == this.keyField) goto L184;
    L180:
    goto L185;
    L184:
    L185:
    1.setFocused(0);
    goto L27;
    L191:
    v6 = ?.iterator();
    L207:
    if (v6.hasNext() ==0) goto L300;
    v7 = ((ui.panel.TicketsPanel$Hit)v6.next());
    if (cmp(mouseX,((d)v7.x())) <0) goto L297;
    if (cmp(mouseX,((d)(v7.x() + v7.w()))) >=0) goto L297;
    if (cmp(mouseY,((d)v7.y())) <0) goto L297;
    if (cmp(mouseY,((d)(v7.y() + v7.h()))) >=0) goto L297;
    v7.action().run();
    return 1;
    L297:
    goto L207;
    L300:
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.chat == null) goto L37;
    if (cmp(mouseX,((d)this.rightX())) <0) goto L37;
    this.chatScroll = (this.chatScroll + (((i)Math.signum(v5)) * 20));
    return 1;
    L37:
    if (cmp(mouseX,((d)this.rightX())) <0) goto L66;
    this.ticketScroll = Math.max(0, (this.ticketScroll - ((i)Math.signum(v5))));
    L66:
    return 1;
}

private boolean focused() {
    v1 = this.fields.iterator();
    L10:
    if (v1.hasNext() ==0) goto L41;
    v2 = ((client.gui.components.EditBox)v1.next());
    if (v2.isFocused() ==0) goto L38;
    return v2;
    L38:
    goto L10;
    L41:
    return null;
}

public client.gui.components.EditBox isCapturingKeys() {
    if (this.focused() == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    v4 = this.focused();
    if (v4 != null) goto L13;
    return 0;
    L13:
    if (keyCode != 41) goto L27;
    v4.setFocused(0);
    return 1;
    L27:
    if (keyCode != 40) goto L67;
    if (v4 != this.keyField) goto L67;
    net.ModAccount.setKey(this.keyField.getValue());
    this.keyField.setValue("");
    this.showFields();
    return 1;
    L67:
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

public void onHidden() {
    net.Tickets.watch(null);
    v1 = this.fields.iterator();
    L14:
    if (v1.hasNext() ==0) goto L46;
    v2 = ((client.gui.components.EditBox)v1.next());
    this.host.detach(v2);
    goto L14;
    L46:
    return;
}

private void lambda$send$0(String error, Throwable ex) {
    client.Minecraft.getInstance().execute(/*indy*/run(this, ex, error));
    return;
}

private void lambda$send$1(Throwable ex, String error) {
    this.sending = 0;
    if (ex == null) goto L26;
    this.result(ui.Lang.str("fwm.tickets.noConnection", new Object[0]));
    goto L75;
    L26:
    if (error == null) goto L38;
    this.result(error);
    goto L75;
    L38:
    this.noteOk = 1;
    this.note = ui.Lang.str("fwm.tickets.opened", new Object[0]);
    this.descField.setValue("");
    this.notesField.setValue("");
    L75:
    return;
}

private void lambda$drawChat$0() {
    this.chat = null;
    net.Tickets.watch(null);
    return;
}

private void lambda$drawList$0(net.Tickets$Ticket t) {
    this.chat = t;
    this.chatScroll = 0;
    net.Tickets.watch(t.id);
    net.Tickets.loadMessages(t.id, 1);
    return;
}

private void lambda$drawNew$2() {
    this.spawnerIndex = (this.spawnerIndex + 1);
    return;
}

private void lambda$drawNew$1() {
    this.spawnerIndex = (this.spawnerIndex - 1);
    return;
}

private void lambda$drawNew$0(ui.panel.TicketsPanel$Mode m) {
    this.setMode(m);
    return;
}

private void lambda$render$3() {
    net.ModAccount.forget();
    this.showFields();
    return;
}

private void lambda$render$2() {
    net.ModAccount.setKey(this.keyField.getValue());
    this.keyField.setValue("");
    this.showFields();
    return;
}

private static void lambda$render$1(String url) {
    client.gui.screens.ConfirmLinkScreen.confirmLinkNow(client.Minecraft.getInstance().gui.screen(), URI.create(url));
    return;
}

private void lambda$render$0(String url) {
    client.Minecraft.getInstance().keyboardHandler.setClipboard(url);
    this.result(ui.Lang.t("Link kopiert.", "Link copied."));
    this.noteOk = 1;
    return;
}

