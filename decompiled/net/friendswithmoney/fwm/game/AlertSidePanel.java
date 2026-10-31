// class net/friendswithmoney/fwm/game/AlertSidePanel  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field AH Ljava/util/regex/Pattern;;
field ORDER Ljava/util/regex/Pattern;;
field WINDOW_MS J;
field W I;
field PAD I;
field CARD I;
field CARD_HOVER I;
field EDGE I;
field EDGE_HOVER I;
field WHITE I;
field SOFT I;
field DIM I;
field GREEN I;
field GREEN_DARK I;
field RED I;
field GOLD I;
field hits Ljava/util/List;;
field pendingKind Lnet/friendswithmoney/fwm/game/AlertSidePanel$Kind;;
field pendingUntil J;
field ahTitle Ljava/lang/String;;
field orderTitle Ljava/lang/String;;
field scale F;
field originX I;
field originY I;
field boxH I;
field picking Z;
field pickedKey Ljava/lang/String;;
field pickedName Ljava/lang/String;;
field price Ljava/lang/String;;
field pickBelow Ljava/lang/Boolean;;
field pickRepeat Z;
field pickToast Z;
field pickChat Z;
field drawnW I;
field KNOB I;
field typing Z;
field note Ljava/lang/String;;
field DOLLAR Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.message.v1.ClientSendMessageEvents.COMMAND.register(/*indy*/onSendCommandMessage());
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static long set(game.AlertSidePanel$Kind kind) {
    game.AlertSidePanel.pendingKind = kind;
    game.AlertSidePanel.pendingUntil = (System.currentTimeMillis() + 3000);
    return;
}

public static game.AlertSidePanel$Kind isMarketMenu(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (game.AlertSidePanel.kind(screen) == game.AlertSidePanel$Kind.NONE) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

private static boolean kind(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (config.Features.isOn("fwm.feature.priceping") !=0) goto L20;
    if (config.Features.canSwitch("fwm.feature.priceping") !=0) goto L20;
    return game.AlertSidePanel$Kind.NONE;
    L20:
    v1 = screen.getTitle().getString();
    if (game.AlertSidePanel.ahTitle == null) goto L50;
    if (game.AlertSidePanel.ahTitle.equals(v1) ==0) goto L50;
    return game.AlertSidePanel$Kind.AH;
    L50:
    if (game.AlertSidePanel.orderTitle == null) goto L70;
    if (game.AlertSidePanel.orderTitle.equals(v1) ==0) goto L70;
    return game.AlertSidePanel$Kind.ORDER;
    L70:
    return game.AlertSidePanel$Kind.NONE;
}

private static org.joml.Matrix3x2fStack render(client.Minecraft client, client.gui.screens.inventory.AbstractContainerScreen screen, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    game.AlertSidePanel.hits.clear();
    v5 = game.AlertSidePanel.kind(screen);
    if (v5 == game.AlertSidePanel$Kind.NONE) goto L38;
    if (config.FwmConfig.INSTANCE.marketAlertPanel ==0) goto L38;
    if (client.player != null) goto L39;
    L38:
    return;
    L39:
    v6 = ((mixin.HandledScreenAccessor)screen);
    v7 = ((v6.fwm$x() - 6) - 4);
    if (v7 >= 70) goto L67;
    return;
    L67:
    if (v5 != game.AlertSidePanel$Kind.ORDER) goto L79;
    goto L80;
    L79:
    L80:
    v8 = 0;
    if (config.FwmConfig.INSTANCE.pricePingCollapsed ==0) goto L157;
    game.AlertSidePanel.scale = 1;
    game.AlertSidePanel.drawnW = 20;
    game.AlertSidePanel.boxH = 20;
    game.AlertSidePanel.originX = ((v6.fwm$x() - 6) - 20);
    game.AlertSidePanel.originY = Math.max(4, v6.fwm$y());
    game.AlertSidePanel.knob(ctx, client.font, v8, (mouseX - game.AlertSidePanel.originX), (mouseY - game.AlertSidePanel.originY));
    return;
    L157:
    game.AlertSidePanel.drawnW = 150;
    game.AlertSidePanel.boxH = game.AlertSidePanel.height(v8);
    game.AlertSidePanel.scale = Math.min(1, Math.min((((f)v7) / 150.0), (((f)(screen.height - 8)) / ((f)game.AlertSidePanel.boxH))));
    game.AlertSidePanel.originX = ((v6.fwm$x() - 6) - Math.round((150.0 * game.AlertSidePanel.scale)));
    game.AlertSidePanel.originY = Math.max(4, Math.min(v6.fwm$y(), ((screen.height - 4) - Math.round((((f)game.AlertSidePanel.boxH) * game.AlertSidePanel.scale)))));
    v9 = ((i)Math.floor(((d)(((f)(mouseX - game.AlertSidePanel.originX)) / game.AlertSidePanel.scale))));
    v10 = ((i)Math.floor(((d)(((f)(mouseY - game.AlertSidePanel.originY)) / game.AlertSidePanel.scale))));
    v11 = ctx.pose();
    v11.pushMatrix();
    v11.translate(((f)game.AlertSidePanel.originX), ((f)game.AlertSidePanel.originY));
    v11.scale(game.AlertSidePanel.scale, game.AlertSidePanel.scale);
    game.AlertSidePanel.draw(ctx, client.font, v8, v9, v10);
    v11.popMatrix();
    return;
}

private static boolean pings(boolean order) {
    v1 = ?;
    v2 = net.PricePings.list().iterator();
    L17:
    if (v2.hasNext() ==0) goto L55;
    v3 = ((config.FwmConfig$PricePing)v2.next());
    if (v3.order != order) goto L52;
    v1.add(v3);
    L52:
    goto L17;
    L55:
    return v1;
}

private static int height(boolean order) {
    return (222 + (Math.max(1, game.AlertSidePanel.pings(order).size()) * 15));
}

private static boolean knob(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, boolean order, int mx, int my) {
    v5 = ctx.pose();
    v5.pushMatrix();
    v5.translate(((f)game.AlertSidePanel.originX), ((f)game.AlertSidePanel.originY));
    v6 = game.AlertSidePanel.inside(mx, my, 0, 0, 20, 20);
    if (v6 ==0) goto L59;
    goto L62;
    L59:
    L62:
    ui.LauncherGfx.round(0, 0, 20, 20, 10, 2048870964, -536078574);
    if (v6 ==0) goto L85;
    goto L88;
    L85:
    L88:
    ui.LauncherGfx.ring(0, 0, 20, 20, 10, 1717364884, 536870911);
    ctx.fill(9, 4, 11, 6, m1);
    ctx.fill(7, 6, 13, 7, m1);
    ctx.fill(6, 7, 7, 13, m1);
    ctx.fill(13, 7, 14, 13, m1);
    ctx.fill(5, 13, 15, 14, m1);
    ctx.fill(9, 15, 11, 16, m1);
    v7 = 0;
    v8 = game.AlertSidePanel.pings(order).iterator();
    L181:
    if (v8.hasNext() ==0) goto L217;
    v9 = ((config.FwmConfig$PricePing)v8.next());
    if (v9.fired !=0) goto L214;
    v7 = v7 + 1;
    L214:
    goto L181;
    L217:
    if (v7 <=0) goto L294;
    v8 = String.valueOf(v7);
    v9 = Math.max(8, (font.width(v8) + 3));
    ui.LauncherGfx.round(ctx, ((20 - v9) + 2), -3, v9, 9, 4, -2075843);
    ui.LauncherGfx.text(ctx, font, v8, (((20 - v9) + 2) + ((v9 - font.width(v8)) / 2)), -2, m1);
    L294:
    v5.popMatrix();
    game.AlertSidePanel.hits.add(?);
    return;
}

private static void draw(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, boolean order, int mx, int my) {
    ui.LauncherGfx.round(ctx, 0, 0, 150, game.AlertSidePanel.boxH, 8, -536078574);
    ui.LauncherGfx.card(ctx, 0, 0, 150, game.AlertSidePanel.boxH, 8);
    v5 = 8;
    v6 = 134;
    v7 = 8;
    if (order ==0) goto L62;
    goto L71;
    L62:
    L71:
    ui.LauncherGfx.bold(font, ui.Lang.t("Order-Wecker", "Order alert"), ui.Lang.t("AH-Wecker", "AH alert"), v5, (v7 + 2), m1);
    v8 = game.AlertSidePanel.inside(mx, my, 131, v7, 12, 12);
    if (v8 ==0) goto L121;
    goto L124;
    L121:
    L124:
    ui.LauncherGfx.text(font, "\u00ab", 134, (v7 + 2), -12136081, -9464447);
    game.AlertSidePanel.hits.add(?);
    v7 = v7 + 16;
    if (config.Features.isOn("fwm.feature.priceping") !=0) goto L226;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("Wecker sind aus.", "Alerts are off."), v5, (v7 + 2), -1017230);
    v7 = v7 + 14;
    game.AlertSidePanel.button(ctx, font, v5, v7, v6, 16, ui.Lang.t("Einschalten", "Switch on"), 1, mx, my, /*indy*/run());
    return;
    L226:
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("DEINE PINGS", "YOUR PINGS"), v5, v7, -9464447);
    v7 = v7 + 11;
    v9 = game.AlertSidePanel.pings(order);
    if (v9.isEmpty() ==0) goto L292;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("noch keiner", "none yet"), v5, (v7 + 3), -9464447);
    v7 = v7 + 15;
    L292:
    v10 = v9.iterator();
    L301:
    if (v10.hasNext() ==0) goto L630;
    v11 = ((config.FwmConfig$PricePing)v10.next());
    if (v11.fired ==0) goto L343;
    goto L397;
    L343:
    if (v11.below() ==0) goto L357;
    goto L360;
    L357:
    L360:
    if (v11.repeat ==0) goto L389;
    goto L392;
    L389:
    L392:
    L397:
    v12 = util.Fmt.compactMoney(Math.round((v11.maxPrice * 100.0))) + " \u21bb" + "";
    v13 = game.AlertSidePanel.inside(mx, my, ((v5 + v6) - 10), v7, 10, 13);
    ui.LauncherGfx.round(ctx, v5, v7, v6, 13, 5, 1544960042);
    if (v11.name != null) goto L456;
    goto L461;
    L456:
    L461:
    if (v11.fired ==0) goto L498;
    goto L499;
    L498:
    L499:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(v11.itemKey, v11.name, ((v6 - 24) - font.width(v12))), (v5 + 4), (v7 + 3), -9464447, m1);
    if (v11.fired ==0) goto L539;
    goto L542;
    L539:
    L542:
    ui.LauncherGfx.text(font, v12, (((v5 + v6) - 14) - font.width(v12)), (v7 + 3), -9464447, -865972);
    if (v13 ==0) goto L573;
    goto L576;
    L573:
    L576:
    ui.LauncherGfx.text(font, "\u00d7", ((v5 + v6) - 8), (v7 + 3), -1017230, -9464447);
    v14 = v11.itemKey;
    game.AlertSidePanel.hits.add(?);
    v7 = v7 + 15;
    goto L301;
    L630:
    v7 = v7 + 4;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("NEUER PING", "NEW PING"), v5, v7, -9464447);
    v7 = v7 + 11;
    if (game.AlertSidePanel.picking ==0) goto L675;
    goto L696;
    L675:
    if (game.AlertSidePanel.pickedName == null) goto L687;
    goto L696;
    L687:
    L696:
    v10 = ui.Lang.t("+ Ping", "+ Ping");
    if (game.AlertSidePanel.picking ==0) goto L722;
    goto L725;
    L722:
    L725:
    game.AlertSidePanel.tile(font, v5, v7, v6, 14, v10, "", "\u25b8", game.AlertSidePanel.picking, mx, my, /*indy*/run());
    v7 = v7 + 18;
    if (game.AlertSidePanel.pickBelow == null) goto L757;
    goto L766;
    L757:
    if (order !=0) goto L765;
    goto L766;
    L765:
    L766:
    v11 = 0;
    if (v11 ==0) goto L795;
    goto L804;
    L795:
    L804:
    game.AlertSidePanel.tile(font, v5, v7, v6, 14, ui.Lang.t("g\u00fcnstiger als", "cheaper than"), ui.Lang.t("h\u00f6her als", "more than"), ui.Lang.t("niedriger / h\u00f6her", "lower / higher"), 0, mx, my, /*indy*/run(order));
    v7 = v7 + 18;
    v12 = game.AlertSidePanel.inside(mx, my, v5, v7, v6, 14);
    if (game.AlertSidePanel.typing ==0) goto L866;
    goto L869;
    L866:
    L869:
    ui.LauncherGfx.round(v5, v7, v6, 14, 5, 2048870964, 1544960042);
    if (game.AlertSidePanel.typing !=0) goto L893;
    if (v12 ==0) goto L899;
    L893:
    goto L902;
    L899:
    L902:
    ui.LauncherGfx.ring(v5, v7, v6, 14, 5, 1717364884, 536870911);
    if (game.AlertSidePanel.price.isEmpty() ==0) goto L932;
    if (game.AlertSidePanel.typing !=0) goto L932;
    goto L971;
    L932:
    if (game.AlertSidePanel.typing ==0) goto L963;
    if (cmp(((System.currentTimeMillis() / 500) % 2),0) !=0) goto L963;
    goto L966;
    L963:
    L966:
    L971:
    v13 = "_" + "";
    if (game.AlertSidePanel.price.isEmpty() ==0) goto L1006;
    if (game.AlertSidePanel.typing !=0) goto L1006;
    goto L1007;
    L1006:
    L1007:
    ui.LauncherGfx.text(font, v13, (v5 + 5), (v7 + 3), -9464447, m1);
    game.AlertSidePanel.hits.add(?);
    v7 = v7 + 18;
    v14 = ((v6 - 3) / 2);
    if (game.AlertSidePanel.pickRepeat !=0) goto L1077;
    goto L1078;
    L1077:
    L1078:
    game.AlertSidePanel.chip(font, v5, v7, v14, ui.Lang.t("einmal", "once"), 1, 0, mx, my, /*indy*/run());
    game.AlertSidePanel.chip(ctx, font, ((v5 + v14) + 3), v7, ((v6 - v14) - 3), ui.Lang.t("mehrmals", "every time"), game.AlertSidePanel.pickRepeat, mx, my, /*indy*/run());
    v7 = v7 + 16;
    if (game.AlertSidePanel.pickToast ==0) goto L1153;
    goto L1156;
    L1153:
    L1156:
    game.AlertSidePanel.chip(font, v5, v7, v14, "\u2714 ", "\u2715 " + ui.Lang.t("Mitteilung", "Toast"), game.AlertSidePanel.pickToast, mx, my, /*indy*/run());
    if (game.AlertSidePanel.pickChat ==0) goto L1214;
    goto L1217;
    L1214:
    L1217:
    game.AlertSidePanel.chip(font, ((v5 + v14) + 3), v7, ((v6 - v14) - 3), "\u2714 ", "\u2715 " + 'Chat', game.AlertSidePanel.pickChat, mx, my, /*indy*/run());
    v7 = v7 + 18;
    game.AlertSidePanel.button(ctx, font, v5, v7, v6, 16, ui.Lang.t("Setzen", "Set"), 1, mx, my, /*indy*/run(order));
    v7 = v7 + 20;
    if (game.AlertSidePanel.note.isEmpty() !=0) goto L1325;
    if (game.AlertSidePanel.note.startsWith("\u2714") ==0) goto L1316;
    goto L1319;
    L1316:
    L1319:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(font, game.AlertSidePanel.note, v6), v5, v7, -12136081, -1017230);
    v7 = v7 + 11;
    L1325:
    ui.LauncherGfx.text(ctx, font, ui.LauncherGfx.trim(font, ui.Lang.t("\u201emehrmals\u201c: jedes neue Angebot", "\"every time\": each new offer"), v6), v5, v7, -9464447);
    ui.LauncherGfx.text(ctx, font, ui.LauncherGfx.trim(font, ui.Lang.t("genau einmal.", "exactly once."), v6), v5, (v7 + 10), -9464447);
    return;
}

private static boolean chip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, String label, boolean on, int mx, int my, Runnable action) {
    v10 = game.AlertSidePanel.inside(mx, my, x, y, w, 13);
    if (on ==0) goto L33;
    goto L36;
    L33:
    L36:
    ui.LauncherGfx.round(x, y, w, 13, 5, 2048870964, 1544960042);
    if (on !=0) goto L57;
    if (v10 ==0) goto L63;
    L57:
    goto L66;
    L63:
    L66:
    ui.LauncherGfx.ring(x, y, w, 13, 5, 1717364884, 536870911);
    v11 = ui.LauncherGfx.trim(font, label, (w - 6));
    if (on ==0) goto L113;
    goto L116;
    L113:
    L116:
    ui.LauncherGfx.text(font, v11, (x + ((w - font.width(v11)) / 2)), (y + 3), -12136081, -5846860);
    game.AlertSidePanel.hits.add(?);
    return;
}

private static String add(boolean order) {
    if (game.AlertSidePanel.pickedKey != null) goto L19;
    game.AlertSidePanel.note = ui.Lang.t("Erst ein Item w\u00e4hlen.", "Pick an item first.");
    return;
    L19:
    v1 = net.PricePings.parsePrice(game.AlertSidePanel.price);
    if (Double.isNaN(v1) ==0) goto L46;
    game.AlertSidePanel.note = ui.Lang.t("Preis ung\u00fcltig (z. B. 500k).", "Price not valid (e.g. 500k).");
    return;
    L46:
    v3 = ?;
    v3.itemKey = game.AlertSidePanel.pickedKey;
    v4 = game.AlertSidePanel.pickedKey.indexOf(35);
    if (v4 <=0) goto L89;
    goto L92;
    L89:
    L92:
    game.AlertSidePanel.pickedKey.substring(0, v4).baseItemKey = game.AlertSidePanel.pickedKey;
    v3.displayName = game.AlertSidePanel.pickedName;
    if (game.AlertSidePanel.pickBelow == null) goto L117;
    goto L126;
    L117:
    if (order !=0) goto L125;
    goto L126;
    L125:
    L126:
    v5 = 0;
    if (net.PricePings.set(v3, game.AlertSidePanel.pickedName, v1, order, v5, game.AlertSidePanel.pickRepeat, game.AlertSidePanel.pickToast, game.AlertSidePanel.pickChat) !=0) goto L164;
    game.AlertSidePanel.note = ui.Lang.t("H\u00f6chstens 10 Wecker.", "At most 10 alerts.");
    return;
    L164:
    game.AlertSidePanel.note = '✔ ' + game.AlertSidePanel.pickedName + ' · ' + util.Fmt.compactMoney(Math.round((v1 * 100.0)));
    game.AlertSidePanel.pickedKey = null;
    game.AlertSidePanel.pickedName = null;
    game.AlertSidePanel.price = "";
    game.AlertSidePanel.pickBelow = null;
    game.AlertSidePanel.typing = 0;
    return;
}

private static String offerPrice(world.item.ItemStack stack) {
    v1 = ((world.item.component.ItemLore)stack.get(core.component.DataComponents.LORE));
    if (v1 != null) goto L19;
    return "";
    L19:
    v2 = -1;
    v4 = v1.lines().iterator();
    L34:
    if (v4.hasNext() ==0) goto L175;
    v5 = ((network.chat.Component)v4.next());
    v6 = chat.ServerLine.strip(v5.getString());
    v7 = game.AlertSidePanel.DOLLAR.matcher(v6);
    if (v7.find() !=0) goto L89;
    goto L34;
    L89:
    if (v7.group(2) != null) goto L110;
    goto L116;
    L110:
    L116:
    v8 = auction.Money.cents("" + v7.group(2));
    if (cmp(v8,0) >0) goto L136;
    goto L34;
    L136:
    if (v6.toLowerCase(Locale.ROOT).contains("pro ") ==0) goto L163;
    return String.valueOf((v8 / 100));
    L163:
    if (cmp(v2,0) >=0) goto L172;
    v2 = v8;
    L172:
    goto L34;
    L175:
    if (cmp(v2,0) >=0) goto L185;
    return "";
    L185:
    return String.valueOf(((v2 / 100) / ((l)Math.max(1, stack.getCount()))));
}

private static boolean button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, int h, String label, boolean primary, int mx, int my, Runnable action) {
    v11 = game.AlertSidePanel.inside(mx, my, x, y, w, h);
    if (primary ==0) goto L43;
    if (v11 !=0) goto L43;
    ui.LauncherGfx.round(ctx, x, y, w, h, 6, -218103809);
    goto L94;
    L43:
    if (v11 ==0) goto L62;
    goto L65;
    L62:
    L65:
    ui.LauncherGfx.round(x, y, w, h, 6, 2048870964, 1544960042);
    if (v11 ==0) goto L88;
    goto L91;
    L88:
    L91:
    ui.LauncherGfx.ring(x, y, w, h, 6, 1717364884, 536870911);
    L94:
    v12 = ui.LauncherGfx.trim(font, label, (w - 8));
    if (primary ==0) goto L149;
    if (v11 !=0) goto L149;
    goto L150;
    L149:
    L150:
    ui.LauncherGfx.text(font, v12, (x + ((w - font.width(v12)) / 2)), (y + ((h - 8) / 2)), -16045545, m1);
    game.AlertSidePanel.hits.add(?);
    return;
}

private static boolean tile(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, int h, String label, String value, boolean active, int mx, int my, Runnable action) {
    v12 = game.AlertSidePanel.inside(mx, my, x, y, w, h);
    if (v12 ==0) goto L33;
    goto L36;
    L33:
    L36:
    ui.LauncherGfx.round(x, y, w, h, 5, 2048870964, 1544960042);
    if (v12 !=0) goto L57;
    if (active ==0) goto L63;
    L57:
    goto L66;
    L63:
    L66:
    ui.LauncherGfx.ring(x, y, w, h, 5, 1717364884, 536870911);
    if (active ==0) goto L112;
    goto L113;
    L112:
    L113:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(font, label, ((w - 12) - font.width(value))), (x + 5), (y + ((h - 8) / 2)), -865972, m1);
    ui.LauncherGfx.text(ctx, font, value, (((x + w) - 5) - font.width(value)), (y + ((h - 8) / 2)), -5846860);
    game.AlertSidePanel.hits.add(?);
    return;
}

private static boolean inside(int mx, int my, int x, int y, int w, int h) {
    if (mx < x) goto L30;
    if (mx >= (x + w)) goto L30;
    if (my < y) goto L30;
    if (my >= (y + h)) goto L30;
    goto L31;
    L30:
    L31:
    return 0;
}

private static boolean click(client.gui.screens.inventory.AbstractContainerScreen screen, double mouseX, double a2, int mouseY) {
    if (game.AlertSidePanel.kind(screen) == game.AlertSidePanel$Kind.NONE) goto L19;
    if (config.FwmConfig.INSTANCE.marketAlertPanel !=0) goto L21;
    L19:
    return 0;
    L21:
    if (game.AlertSidePanel.picking ==0) goto L130;
    v6 = ((mixin.HandledScreenAccessor)screen).fwm$focusedSlot();
    if (v6 == null) goto L130;
    if (v6.hasItem() ==0) goto L130;
    v7 = v6.getItem();
    v8 = shop.MarketKey.of(v7);
    if (v8 != null) goto L85;
    game.AlertSidePanel.note = ui.Lang.t("F\u00fcr dieses Item gibt es keinen Marktpreis.", "No market price for this item.");
    goto L124;
    L85:
    game.AlertSidePanel.pickedKey = v8;
    game.AlertSidePanel.pickedName = chat.ServerLine.strip(v7.getHoverName().getString());
    game.AlertSidePanel.price = game.AlertSidePanel.offerPrice(v7);
    game.AlertSidePanel.note = "";
    game.AlertSidePanel.typing = 1;
    L124:
    game.AlertSidePanel.picking = 0;
    return 1;
    L130:
    if (v5 !=0) goto L146;
    if (game.AlertSidePanel.hits.isEmpty() ==0) goto L152;
    L146:
    return game.AlertSidePanel.insideBox(mouseX, mouseY);
    L152:
    v6 = ((i)Math.floor(((mouseX - ((d)game.AlertSidePanel.originX)) / ((d)game.AlertSidePanel.scale))));
    v7 = ((i)Math.floor(((mouseY - ((d)game.AlertSidePanel.originY)) / ((d)game.AlertSidePanel.scale))));
    v8 = ?.iterator();
    L201:
    if (v8.hasNext() ==0) goto L268;
    v9 = ((game.AlertSidePanel$Hit)v8.next());
    if (game.AlertSidePanel.inside(v6, v7, v9.x(), v9.y(), v9.w(), v9.h()) ==0) goto L265;
    v9.action().run();
    return 1;
    L265:
    goto L201;
    L268:
    if (game.AlertSidePanel.insideBox(mouseX, mouseY) ==0) goto L278;
    return 1;
    L278:
    game.AlertSidePanel.typing = 0;
    return 0;
}

private static double insideBox(double mouseX, double a1) {
    v4 = ((i)Math.floor(((mouseX - ((d)game.AlertSidePanel.originX)) / ((d)game.AlertSidePanel.scale))));
    v5 = ((i)Math.floor(((v2 - ((d)game.AlertSidePanel.originY)) / ((d)game.AlertSidePanel.scale))));
    if (v4 <0) goto L64;
    if (v5 <0) goto L64;
    if (v4 >= game.AlertSidePanel.drawnW) goto L64;
    if (v5 >= game.AlertSidePanel.boxH) goto L64;
    goto L65;
    L64:
    L65:
    return 0;
}

private static String key(int code) {
    if (game.AlertSidePanel.typing !=0) goto L8;
    return 0;
    L8:
    if (code != 41) goto L20;
    game.AlertSidePanel.typing = 0;
    return 1;
    L20:
    if (code == 40) goto L32;
    if (code != 88) goto L38;
    L32:
    game.AlertSidePanel.typing = 0;
    return 1;
    L38:
    if (code != 42) goto L73;
    if (game.AlertSidePanel.price.isEmpty() !=0) goto L71;
    game.AlertSidePanel.price = game.AlertSidePanel.price.substring(0, (game.AlertSidePanel.price.length() - 1));
    L71:
    return 1;
    L73:
    v1 = null;
    if (code < 48) goto L102;
    if (code > 57) goto L102;
    v1 = String.valueOf(((c)((48 + code) - 48)));
    goto L200;
    L102:
    if (code < 320) goto L132;
    if (code > 329) goto L132;
    v1 = String.valueOf(((c)((48 + code) - 320)));
    goto L200;
    L132:
    if (code != 14) goto L145;
    v1 = "k";
    goto L200;
    L145:
    if (code != 16) goto L158;
    v1 = "m";
    goto L200;
    L158:
    if (code != 5) goto L170;
    v1 = "b";
    goto L200;
    L170:
    if (code == 46) goto L183;
    if (code != 330) goto L190;
    L183:
    v1 = ".";
    goto L200;
    L190:
    if (code != 44) goto L200;
    v1 = ",";
    L200:
    if (v1 == null) goto L233;
    if (game.AlertSidePanel.price.length() >= 14) goto L233;
    game.AlertSidePanel.price = game.AlertSidePanel.price + v1.toLowerCase(Locale.ROOT);
    L233:
    return 1;
}

private static void lambda$draw$10(boolean order) {
    game.AlertSidePanel.add(order);
    return;
}

private static void lambda$draw$9() {
    if (game.AlertSidePanel.pickChat !=0) goto L10;
    goto L11;
    L10:
    L11:
    game.AlertSidePanel.pickChat = 0;
    return;
}

private static void lambda$draw$8() {
    if (game.AlertSidePanel.pickToast !=0) goto L10;
    goto L11;
    L10:
    L11:
    game.AlertSidePanel.pickToast = 0;
    return;
}

private static void lambda$draw$7() {
    game.AlertSidePanel.pickRepeat = 1;
    return;
}

private static void lambda$draw$6() {
    game.AlertSidePanel.pickRepeat = 0;
    return;
}

private static void lambda$draw$5() {
    game.AlertSidePanel.typing = 1;
    game.AlertSidePanel.picking = 0;
    return;
}

private static Boolean lambda$draw$4(boolean order) {
    if (game.AlertSidePanel.pickBelow == null) goto L18;
    if (game.AlertSidePanel.pickBelow.booleanValue() ==0) goto L22;
    goto L26;
    L18:
    if (order ==0) goto L26;
    L22:
    goto L27;
    L26:
    L27:
    game.AlertSidePanel.pickBelow = Boolean.valueOf(0);
    return;
}

private static void lambda$draw$3() {
    if (game.AlertSidePanel.picking !=0) goto L10;
    goto L11;
    L10:
    L11:
    game.AlertSidePanel.picking = 0;
    game.AlertSidePanel.typing = 0;
    return;
}

private static void lambda$draw$2(String key, boolean order) {
    net.PricePings.remove(key, order);
    return;
}

private static void lambda$draw$1() {
    config.Features.set("fwm.feature.priceping", 1);
    return;
}

private static void lambda$draw$0() {
    config.FwmConfig.INSTANCE.pricePingCollapsed = 1;
    game.AlertSidePanel.typing = 0;
    game.AlertSidePanel.picking = 0;
    config.ConfigManager.save();
    return;
}

private static void lambda$knob$0() {
    config.FwmConfig.INSTANCE.pricePingCollapsed = 0;
    config.ConfigManager.save();
    return;
}

private static void lambda$register$1(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L16;
    v4 = ((client.gui.screens.inventory.AbstractContainerScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    v5 = screen.getTitle().getString();
    if (cmp(System.currentTimeMillis(),game.AlertSidePanel.pendingUntil) >=0) goto L70;
    if (game.AlertSidePanel.pendingKind != game.AlertSidePanel$Kind.AH) goto L52;
    game.AlertSidePanel.ahTitle = v5;
    L52:
    if (game.AlertSidePanel.pendingKind != game.AlertSidePanel$Kind.ORDER) goto L66;
    game.AlertSidePanel.orderTitle = v5;
    L66:
    game.AlertSidePanel.pendingUntil = 0;
    L70:
    if (game.AlertSidePanel.kind(v4) != game.AlertSidePanel$Kind.NONE) goto L82;
    return;
    L82:
    fabric.api.client.screen.v1.ScreenEvents.afterExtract(screen).register(/*indy*/afterExtract(client, v4));
    fabric.api.client.screen.v1.ScreenMouseEvents.allowMouseClick(screen).register(/*indy*/allowMouseClick(v4));
    fabric.api.client.screen.v1.ScreenKeyboardEvents.allowKeyPress(screen).register(/*indy*/allowKeyPress());
    fabric.api.client.screen.v1.ScreenEvents.remove(screen).register(/*indy*/onRemove());
    return;
}

private static void lambda$register$5(client.gui.screens.Screen s) {
    game.AlertSidePanel.typing = 0;
    game.AlertSidePanel.picking = 0;
    return;
}

private static boolean lambda$register$4(client.gui.screens.Screen s, client.input.KeyEvent input) {
    if (game.AlertSidePanel.key(input.key()) !=0) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

private static boolean lambda$register$3(client.gui.screens.inventory.AbstractContainerScreen handled, client.gui.screens.Screen s, client.input.MouseButtonEvent click) {
    if (game.AlertSidePanel.click(handled, click.x(), click.y(), click.button()) !=0) goto L23;
    goto L24;
    L23:
    L24:
    return 0;
}

private static void lambda$register$2(client.Minecraft client, client.gui.screens.inventory.AbstractContainerScreen handled, client.gui.screens.Screen s, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    game.AlertSidePanel.render(client, handled, ctx, mouseX, mouseY);
    return;
}

private static void lambda$register$0(String command) {
    if (command != null) goto L5;
    return;
    L5:
    v1 = command.trim();
    if (game.AlertSidePanel.AH.matcher(v1).matches() ==0) goto L32;
    game.AlertSidePanel.set(game.AlertSidePanel$Kind.AH);
    goto L51;
    L32:
    if (game.AlertSidePanel.ORDER.matcher(v1).matches() ==0) goto L51;
    game.AlertSidePanel.set(game.AlertSidePanel$Kind.ORDER);
    L51:
    return;
}

static regex.Pattern <clinit>() {
    game.AlertSidePanel.AH = regex.Pattern.compile("^(ah|auction|auctions|auctionhouse)(\\s.*)?$", 2);
    game.AlertSidePanel.ORDER = regex.Pattern.compile("^(order|orders)(\\s.*)?$", 2);
    game.AlertSidePanel.hits = ?;
    game.AlertSidePanel.pendingKind = game.AlertSidePanel$Kind.NONE;
    game.AlertSidePanel.scale = 1;
    game.AlertSidePanel.price = "";
    game.AlertSidePanel.pickToast = 1;
    game.AlertSidePanel.drawnW = 150;
    game.AlertSidePanel.note = "";
    game.AlertSidePanel.DOLLAR = regex.Pattern.compile("\\$\\s*([0-9][0-9.,]*)\\s*([kKmMbB])?");
    return;
}

