// class net/friendswithmoney/fwm/ui/StreamerScreen  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/DesignHost
field VW I;
field VH I;
field BODY_TOP I;
field BODY_BOTTOM I;
field LEFT_X I;
field LEFT_W I;
field RIGHT_X I;
field RIGHT_W I;
field ROW_H I;
field GOLD I;
field GREY I;
field REEL_MS J;
field giveawayTab Z;
field scroll I;
field boxes Ljava/util/List;;
field actions Ljava/util/List;;
field sent Ljava/util/Map;;
field toast Ljava/lang/String;;
field toastAt J;
field cancelArmedAt J;
field earningsShown Z;
field confirm Lnet/friendswithmoney/fwm/ui/ConfirmBox;;
field reopen Z;
field reopenGiveaway Z;
field registered Z;
field reelFor J;
field reel Ljava/util/List;;
field reelWinner I;
field listTop I;
field listBottom I;
field rowBoxesFrom I;
field rowBoxesTo I;
field ADVERT_GAP_MS J;
field advertAt J;
field rotation I;
public void <init>(client.gui.screens.Screen parent, String tab) {
    this.boxes = ?;
    this.actions = ?;
    this.confirm = ?;
    if (tab == null) goto L55;
    this.giveawayTab = tab.equals("giveaway");
    goto L85;
    L55:
    if (auction.Auction.running() !=0) goto L81;
    if (auction.Auction.hasResult() !=0) goto L81;
    if (auction.Giveaway.status() == auction.Giveaway$Status.NONE) goto L81;
    goto L82;
    L81:
    L82:
    1.giveawayTab = 0;
    L85:
    ui.StreamerScreen.registerOnce();
    return;
}

public static void ifLive(String key) {
    v1 = "fwm.tab.giveaway".equals(key);
    if (v1 !=0) goto L22;
    if ("fwm.tab.auction".equals(key) !=0) goto L22;
    return null;
    L22:
    if (v1 ==0) goto L43;
    if (auction.Giveaway.status() == auction.Giveaway$Status.NONE) goto L39;
    goto L60;
    L39:
    goto L60;
    L43:
    if (auction.Auction.running() !=0) goto L55;
    if (auction.Auction.hasResult() ==0) goto L59;
    L55:
    goto L60;
    L59:
    L60:
    v2 = 0;
    if (v2 ==0) goto L87;
    if (v1 ==0) goto L79;
    goto L81;
    L79:
    L81:
    goto L88;
    L87:
    L88:
    return null;
}

private static void registerOnce() {
    if (ui.StreamerScreen.registered ==0) goto L7;
    return;
    L7:
    ui.StreamerScreen.registered = 1;
    fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(/*indy*/onEndTick());
    return;
}

protected int vw() {
    return 460;
}

protected int vh() {
    return 300;
}

protected void drawCanvas(client.gui.GuiGraphicsExtractor ctx, int mx, int my, float delta) {
    this.boxes.clear();
    this.actions.clear();
    this.rowBoxesTo = 0;
    this.rowBoxesFrom = 0;
    if (this.confirm.open() ==0) goto L42;
    goto L43;
    L42:
    L43:
    v5 = mx;
    if (this.confirm.open() ==0) goto L59;
    goto L60;
    L59:
    L60:
    v6 = my;
    this.header(ctx, v5, v6);
    if (this.giveawayTab ==0) goto L90;
    this.giveaway(ctx, v5, v6);
    goto L99;
    L90:
    this.auction(ctx, v5, v6);
    L99:
    this.confirm.render(ctx, this.font, 0, 0, 460, 300, mx, my);
    return;
}

private void header(client.gui.GuiGraphicsExtractor ctx, int mx, int my) {
    ui.LauncherGfx.round(ctx, 10, 8, 108, 26, 6, 1711675152);
    if (this.giveawayTab ==0) goto L66;
    this.text(ctx, ui.Lang.t("TEILNEHMER", "PARTICIPANTS"), 16, 11, -5846860);
    this.text(ctx, String.valueOf(auction.Giveaway.participants().size()), 16, 22, -865972);
    goto L252;
    L66:
    if (mx < 10) goto L94;
    if (mx >= 118) goto L94;
    if (my < 8) goto L94;
    if (my >= 34) goto L94;
    goto L95;
    L94:
    L95:
    v4 = 0;
    if (ui.StreamerScreen.earningsShown ==0) goto L112;
    goto L114;
    L112:
    L114:
    v5 = "$ \u2022\u2022\u2022\u2022\u2022\u2022";
    this.text(ctx, ui.Lang.t("EINNAHMEN", "EARNINGS"), 16, 11, -5846860);
    this.text(ctx, v5, 16, 22, -865972);
    if (ui.StreamerScreen.earningsShown ==0) goto L163;
    goto L170;
    L163:
    L170:
    v6 = ui.Lang.t("aufdecken", "show");
    if (ui.StreamerScreen.earningsShown ==0) goto L183;
    if (v4 ==0) goto L235;
    L183:
    if ((((16 + this.font.width(v5)) + 6) + this.font.width(v6)) > 112) goto L235;
    if (v4 ==0) goto L230;
    goto L232;
    L230:
    L232:
    ctx.right(v6, 112, 22, m1, -9464447);
    L235:
    this.button(10, 8, 108, 26, /*indy*/run());
    L252:
    if (this.giveawayTab ==0) goto L267;
    goto L274;
    L267:
    L274:
    v4 = "Giveaway" + "'s " + ui.Lang.t("Auktion", "Auction");
    v5 = 1.5;
    v6 = ((i)(((f)this.font.width(v4)) * v5));
    v7 = 220;
    if (v6 <= v7) goto L327;
    v5 = 1;
    v6 = this.font.width(v4);
    L327:
    ui.LauncherGfx.big(ctx, this.font, v4, (124 + ((v7 - v6) / 2)), 13, v5, m1, 0);
    v8 = 450;
    v8 = this.tab(ctx, "Giveaway", v8, this.giveawayTab, mx, my, /*indy*/run(this));
    if (this.giveawayTab !=0) goto L405;
    goto L406;
    L405:
    L406:
    ctx.tab(ui.Lang.t("Auktion", "Auction"), (v8 - 4), 1, 0, mx, my, /*indy*/run(this));
    if (this.giveawayTab ==0) goto L676;
    v15 = auction.Giveaway.status();
    // switch (ui.StreamerScreen$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[v15.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=71), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=25), Operand(op_type=<OperandTypes.BRANCH: 40>, value=35), Operand(op_type=<OperandTypes.BRANCH: 40>, value=45)]
    L464:
    goto L519;
    L474:
    goto L519;
    L484:
    if (auction.Giveaway.drawing() ==0) goto L500;
    goto L519;
    L500:
    goto L519;
    L510:
    L519:
    v9 = ui.Lang.t("BEREIT", "READY");
    if (v15 != auction.Giveaway$Status.RUNNING) goto L535;
    goto L550;
    L535:
    if (v15 != auction.Giveaway$Status.ENDED) goto L548;
    goto L550;
    L548:
    L550:
    v10 = -5846860;
    if (v15 != auction.Giveaway$Status.ENDED) goto L604;
    if (auction.Giveaway.drawing() !=0) goto L604;
    if (auction.Giveaway.winner() != null) goto L584;
    goto L641;
    L584:
    goto L641;
    L604:
    if (v15 != auction.Giveaway$Status.NONE) goto L624;
    goto L641;
    L624:
    L641:
    v11 = ui.Lang.t("Teilnahme: ", "Join: ") + auction.Giveaway.joinCommand();
    if (auction.Giveaway.active() ==0) goto L655;
    goto L656;
    L655:
    L656:
    v12 = 0;
    if (auction.Giveaway.active() ==0) goto L670;
    goto L671;
    L670:
    L671:
    v14 = 0;
    goto L921;
    L676:
    v15 = auction.Auction.running();
    v16 = auction.Auction.hasResult();
    if (v15 ==0) goto L703;
    goto L727;
    L703:
    if (v16 ==0) goto L718;
    goto L727;
    L718:
    L727:
    v9 = ui.Lang.t("BEREIT", "READY");
    if (v15 ==0) goto L740;
    goto L752;
    L740:
    if (v16 ==0) goto L750;
    goto L752;
    L750:
    L752:
    v10 = -5846860;
    v17 = auction.Auction.leader();
    if (v15 ==0) goto L826;
    if (v17 != null) goto L792;
    goto L891;
    L792:
    goto L891;
    L826:
    if (v16 ==0) goto L882;
    if (v17 != null) goto L848;
    goto L891;
    L848:
    goto L891;
    L882:
    L891:
    v11 = ui.Lang.t("Keine Auktion l\u00e4uft", "No auction running");
    if (v15 ==0) goto L904;
    goto L905;
    L904:
    L905:
    v12 = 0;
    if (v15 ==0) goto L918;
    goto L919;
    L918:
    L919:
    v14 = 0;
    L921:
    v15 = (this.font.width(v9) + 16);
    ui.LauncherGfx.ring(ctx, 10, 39, v15, 13, 6, v10);
    ui.LauncherGfx.round(ctx, 15, 44, 3, 3, 1, v10);
    this.text(ctx, v9, 21, 42, v10);
    if (ui.StreamerScreen.toast == null) goto L1007;
    if (cmp((System.currentTimeMillis() - ui.StreamerScreen.toastAt),2500) >=0) goto L1007;
    v11 = '✔ ' + ui.StreamerScreen.toast;
    L1007:
    if (this.giveawayTab ==0) goto L1018;
    goto L1021;
    L1018:
    L1021:
    v16 = auction.Auction.recentExtension();
    if (v16 <=0) goto L1038;
    goto L1039;
    L1038:
    L1039:
    v17 = null;
    if (v17 != null) goto L1050;
    goto L1062;
    L1050:
    L1062:
    v18 = (this.font.width(v17) + 6);
    if (v17 == null) goto L1086;
    this.text(ctx, v17, (380 - v18), 42, -865972);
    L1086:
    v19 = ((380 - v18) - ((10 + v15) + 8));
    v20 = ui.LauncherGfx.trim(this.font, v11, v19);
    v21 = v11.startsWith(ui.Lang.t("Teilnahme: ", "Join: "));
    v22 = ((10 + v15) + 8);
    if (v21 ==0) goto L1185;
    if (mx < v22) goto L1185;
    if (mx >= (v22 + this.font.width(v20))) goto L1185;
    if (my < 39) goto L1185;
    if (my >= 52) goto L1185;
    goto L1186;
    L1185:
    L1186:
    v23 = 0;
    if (v11.startsWith("\u2714 ") ==0) goto L1213;
    goto L1224;
    L1213:
    if (v23 ==0) goto L1222;
    goto L1224;
    L1222:
    L1224:
    v20.text(v22, 42, -10688364, m1, -865972);
    if (v21 ==0) goto L1257;
    this.button(v22, 39, this.font.width(v20), 13, /*indy*/run(this));
    L1257:
    if (this.giveawayTab !=0) goto L1443;
    if (auction.Auction.running() ==0) goto L1443;
    if (v11.startsWith("\u2714 ") !=0) goto L1443;
    v24 = '/pay ' + ui.FwmGfx.playerName();
    v25 = '  ·  ' + ui.Lang.t("Mach mit: ", "Join: ") + v24;
    v26 = (v22 + this.font.width(v20));
    if (v20.equals(v11) ==0) goto L1443;
    if ((v26 + this.font.width(v25)) > (v22 + v19)) goto L1443;
    if (mx < v26) goto L1391;
    if (mx >= (v26 + this.font.width(v25))) goto L1391;
    if (my < 39) goto L1391;
    if (my >= 52) goto L1391;
    goto L1392;
    L1391:
    L1392:
    v27 = 0;
    if (v27 ==0) goto L1411;
    goto L1413;
    L1411:
    L1413:
    ctx.text(v25, v26, 42, m1, -5846860);
    this.button(v26, 39, this.font.width(v25), 13, /*indy*/run(this, v24));
    L1443:
    v24 = ui.StreamerScreen.clock(v12);
    v25 = ((i)(((f)this.font.width(v24)) * 1.5));
    ui.LauncherGfx.big(ctx, this.font, v24, (450 - v25), 38, 1.5, m1, 0);
    ui.LauncherGfx.round(ctx, 10, 56, 440, 3, 1, 872415231);
    v26 = Math.round((440.0 * v14));
    if (v26 <= 1) goto L1550;
    if (cmp(v14,0.10000000149011612) >=0) goto L1545;
    goto L1547;
    L1545:
    L1547:
    ui.LauncherGfx.round(10, 56, v26, 3, 1, -1017230, -865972);
    L1550:
    return;
}

private void tab(client.gui.GuiGraphicsExtractor ctx, String label, int right, boolean active, int mx, int my, Runnable action) {
    v8 = (this.font.width(label) + 14);
    v9 = (right - v8);
    if (mx < v9) goto L54;
    if (mx >= (v9 + v8)) goto L54;
    if (my < 12) goto L54;
    if (my >= 26) goto L54;
    goto L55;
    L54:
    L55:
    v10 = 0;
    if (active ==0) goto L79;
    goto L93;
    L79:
    if (v10 ==0) goto L90;
    goto L93;
    L90:
    L93:
    ui.LauncherGfx.round(12, v8, 14, 7, -218103809, 872415231, 452984831);
    if (active ==0) goto L117;
    goto L118;
    L117:
    L118:
    ctx.text(label, (v9 + 7), 15, -16045545, m1);
    this.button(v9, 12, v8, 14, action);
    return v9;
}

private int auction(client.gui.GuiGraphicsExtractor ctx, int mx, int my) {
    v4 = auction.Auction.running();
    v5 = auction.Auction.hasResult();
    v6 = auction.Auction.hasResult();
    if (v4 ==0) goto L48;
    goto L68;
    L48:
    L68:
    ctx.itemBox(auction.Auction.item(), ui.Lang.t("N\u00e4chstes Gebot ab ", "Next bid from ") + util.Fmt.dollars(auction.Auction.nextMinimum()), ui.Lang.t("Mindestgebot ", "Minimum bid ") + util.Fmt.dollars(auction.Auction.startBid()));
    this.settingChips(ctx);
    v7 = auction.Auction.leader();
    v8 = 188;
    if (v6 ==0) goto L100;
    if (v7 == null) goto L100;
    goto L101;
    L100:
    L101:
    v9 = 0;
    if (v9 ==0) goto L130;
    goto L133;
    L130:
    L133:
    ui.LauncherGfx.round(10, v8, 158, (266 - v8), 6, 1039321420, 1544960042);
    if (v9 ==0) goto L162;
    goto L165;
    L162:
    L165:
    ui.LauncherGfx.ring(10, v8, 158, (266 - v8), 6, -865972, 536870911);
    if (v7 != null) goto L246;
    if (v4 ==0) goto L192;
    goto L201;
    L192:
    L201:
    ctx.text(ui.Lang.t("Noch kein Gebot", "No bid yet"), ui.Lang.t("Kein Gewinner", "No winner"), 18, (v8 + 8), -9464447);
    if (auction.Auction.itemMissing() ==0) goto L422;
    this.text(ctx, ui.Lang.t("Item fehlt im Inventar!", "Item missing from inventory!"), 18, (v8 + 20), -1017230);
    goto L422;
    L246:
    ui.FwmGfx.playerHead(ctx, v7, 18, (v8 + 8), 22);
    if (v9 ==0) goto L280;
    goto L289;
    L280:
    L289:
    ctx.text(ui.Lang.t("GEWINNER", "WINNER"), ui.Lang.t("F\u00dcHRT", "LEADING"), 46, (v8 + 7), -9464447);
    if (v9 ==0) goto L331;
    goto L332;
    L331:
    L332:
    ctx.text(ui.LauncherGfx.trim(this.font, v7, 114), 46, (v8 + 17), -865972, m1);
    this.text(ctx, util.Fmt.dollars(auction.Auction.price()), 46, (v8 + 27), -10688364);
    this.playerButtons(ctx, v7, 18, (v8 + 40), mx, my, v9);
    if (auction.Auction.itemMissing() ==0) goto L422;
    this.text(ctx, ui.Lang.t("Item fehlt!", "Item missing!"), (160 - this.font.width(ui.Lang.t("Item fehlt!", "Item missing!"))), (v8 + 7), -1017230);
    L422:
    v10 = auction.Auction.ranking();
    v11 = auction.Auction.late();
    if (v7 == null) goto L441;
    goto L442;
    L441:
    L442:
    v12 = 0;
    v13 = (auction.Auction.bidCount() + v11.size());
    if (v13 != 1) goto L488;
    goto L497;
    L488:
    L497:
    ctx.listBox(ui.Lang.t("ZAHLUNGEN", "PAYMENTS"), v13, ui.Lang.t(" Zahlung", " payment") + ui.Lang.t(" Zahlungen", " payments"), 64, 266);
    if (v10.isEmpty() ==0) goto L568;
    if (v11.isEmpty() ==0) goto L568;
    if (v4 ==0) goto L549;
    goto L558;
    L549:
    L558:
    ctx.text(ui.Lang.t("Noch keine Zahlungen", "No payments yet"), ui.Lang.t("Keine Gebote", "No bids"), 184, 86, -9464447);
    L568:
    this.rows(ctx, (v10.size() + v11.size()), 82, 262, /*indy*/paint(this, v10, v11, v12, ctx, v6, mx, my), mx, my);
    v14 = 10;
    v15 = 274;
    if (v4 ==0) goto L663;
    v14 = (v14 + (this.pill(ctx, ui.Lang.t("Beenden", "End"), v14, v15, mx, my, 1, /*indy*/run()) + 6));
    goto L741;
    L663:
    if (v6 ==0) goto L706;
    v14 = (v14 + (this.pill(ctx, ui.Lang.t("Neue Auktion", "New auction"), v14, v15, mx, my, 1, /*indy*/run(this)) + 6));
    goto L741;
    L706:
    v14 = (v14 + (this.pill(ctx, ui.Lang.t("Auktion einrichten", "Set up auction"), v14, v15, mx, my, 1, /*indy*/run(this)) + 6));
    L741:
    if (v4 !=0) goto L751;
    if (v6 ==0) goto L805;
    L751:
    v14 = (v14 + (this.pill(ctx, ui.Lang.t("Werbung machen", "Advertise"), v14, v15, mx, my, 0, /*indy*/run(this)) + 3));
    v14 = (v14 + (this.cog(ctx, v14, v15, mx, my, 0) + 6));
    L805:
    v16 = (this.font.width(ui.Lang.t("Schlie\u00dfen", "Close")) + 16);
    this.pill(ctx, ui.Lang.t("Schlie\u00dfen", "Close"), (450 - v16), v15, mx, my, 0, /*indy*/run(this));
    return;
}

private int giveaway(client.gui.GuiGraphicsExtractor ctx, int mx, int my) {
    v4 = auction.Giveaway.status();
    if (v4 != auction.Giveaway$Status.ENDED) goto L17;
    goto L18;
    L17:
    L18:
    v5 = 0;
    if (auction.Giveaway.word().isEmpty() ==0) goto L40;
    goto L57;
    L40:
    L57:
    ctx.itemBox(auction.Giveaway.item(), "", ui.Lang.t("Wort: ", "Word: ") + auction.Giveaway.word());
    v6 = 188;
    if (v5 ==0) goto L82;
    if (auction.Giveaway.drawing() !=0) goto L82;
    goto L83;
    L82:
    L83:
    v7 = null;
    if (v7 == null) goto L112;
    goto L115;
    L112:
    L115:
    ui.LauncherGfx.round(10, v6, 158, (266 - v6), 6, 1039321420, 1544960042);
    if (v7 == null) goto L144;
    goto L147;
    L144:
    L147:
    ui.LauncherGfx.ring(10, v6, 158, (266 - v6), 6, -865972, 536870911);
    if (v7 == null) goto L274;
    ui.FwmGfx.playerHead(ctx, v7, 18, (v6 + 8), 22);
    this.text(ctx, ui.Lang.t("GEWINNER", "WINNER"), 46, (v6 + 7), -9464447);
    this.text(ctx, ui.LauncherGfx.trim(this.font, v7, 114), 46, (v6 + 17), -865972);
    this.text(ctx, auction.Giveaway.participants().size() + ui.Lang.t(" Teilnehmer", " participants"), 46, (v6 + 27), -5846860);
    this.playerButtons(ctx, v7, 18, (v6 + 40), mx, my, 1);
    goto L396;
    L274:
    if (v4 != auction.Giveaway$Status.NONE) goto L308;
    this.text(ctx, ui.Lang.t("Kein Giveaway l\u00e4uft", "No giveaway running"), 18, (v6 + 8), -9464447);
    goto L396;
    L308:
    this.text(ctx, ui.Lang.t("So macht man mit:", "How to join:"), 18, (v6 + 8), -9464447);
    v8 = auction.Giveaway.joinCommand();
    this.text(ctx, ui.LauncherGfx.trim(this.font, v8, 142), 18, (v6 + 20), -10688364);
    this.pill(ctx, ui.Lang.t("Kopieren", "Copy"), 18, (v6 + 34), mx, my, 0, /*indy*/run(this, v8));
    L396:
    v8 = 64;
    if (v5 ==0) goto L431;
    if (auction.Giveaway.winner() == null) goto L431;
    this.reel(ctx, 176, 64, 274, 58);
    v8 = 128;
    L431:
    v9 = auction.Giveaway.participants();
    this.listBox(ctx, ui.Lang.t("TEILNEHMER", "PARTICIPANTS"), v9.size() + ui.Lang.t(" dabei", " joined"), v8, 266);
    if (v9.isEmpty() ==0) goto L522;
    if (v4 != auction.Giveaway$Status.NONE) goto L500;
    goto L509;
    L500:
    L509:
    ctx.text("\u2013", ui.Lang.t("Noch niemand \u2013 Wort per /msg schicken", "Nobody yet \u2013 send the word via /msg"), 184, (v8 + 22), -9464447);
    L522:
    this.rows(ctx, v9.size(), (v8 + 18), 262, /*indy*/paint(this, v9, v7, ctx, mx, my), mx, my);
    v10 = 10;
    v11 = 274;
    if (auction.Giveaway.active() ==0) goto L809;
    if (v4 != auction.Giveaway$Status.PAUSED) goto L584;
    goto L585;
    L584:
    L585:
    v12 = 0;
    v10 = (v10 + (this.pill(ctx, ui.Lang.t("Auslosen", "Draw"), v10, v11, mx, my, 1, /*indy*/run()) + 6));
    if (v12 ==0) goto L642;
    goto L651;
    L642:
    L651:
    if (v12 ==0) goto L671;
    goto L676;
    L671:
    L676:
    v10 = (ctx + (ui.Lang.t("Fortsetzen", "Resume").pill(ui.Lang.t("Pausieren", "Pause"), v10, v11, mx, my, 0, /*indy*/run(), /*indy*/run()) + 6));
    if (cmp((System.currentTimeMillis() - this.cancelArmedAt),3000) >=0) goto L704;
    goto L705;
    L704:
    L705:
    v13 = 0;
    if (v13 ==0) goto L728;
    goto L737;
    L728:
    L737:
    v10 = (this + (ctx.pill(ui.Lang.t("Sicher?", "Sure?"), ui.Lang.t("Abbrechen", "Cancel"), v10, v11, mx, my, 0, /*indy*/run(this)) + 6));
    v10 = (v10 + (this.pill(ctx, ui.Lang.t("Werbung machen", "Advertise"), v10, v11, mx, my, 0, /*indy*/run(this)) + 3));
    this.cog(ctx, v10, v11, mx, my, 1);
    goto L880;
    L809:
    if (v5 ==0) goto L852;
    v10 = (v10 + (this.pill(ctx, ui.Lang.t("Neues Giveaway", "New giveaway"), v10, v11, mx, my, 1, /*indy*/run(this)) + 6));
    goto L880;
    L852:
    this.pill(ctx, ui.Lang.t("Giveaway einrichten", "Set up giveaway"), v10, v11, mx, my, 1, /*indy*/run(this));
    L880:
    v12 = (this.font.width(ui.Lang.t("Schlie\u00dfen", "Close")) + 16);
    this.pill(ctx, ui.Lang.t("Schlie\u00dfen", "Close"), (450 - v12), v11, mx, my, 0, /*indy*/run(this));
    return;
}

private void reel(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
    v6 = auction.Giveaway.participants();
    if (v6.isEmpty() !=0) goto L21;
    if (auction.Giveaway.winner() != null) goto L22;
    L21:
    return;
    L22:
    if (cmp(ui.StreamerScreen.reelFor,auction.Giveaway.drawnAt()) ==0) goto L132;
    ui.StreamerScreen.reelFor = auction.Giveaway.drawnAt();
    v7 = ?;
    v8 = ?;
    v9 = 0;
    L62:
    if (v9 >= 40) goto L108;
    v8.add(((auction.Giveaway$Participant)v6.get(v7.nextInt(v6.size()))).name());
    v9 = v9 + 1;
    goto L62;
    L108:
    ui.StreamerScreen.reelWinner = 34;
    v8.set(ui.StreamerScreen.reelWinner, auction.Giveaway.winner());
    ui.StreamerScreen.reel = v8;
    L132:
    ui.LauncherGfx.round(ctx, x, y, w, h, 6, 1711675152);
    ui.LauncherGfx.ring(ctx, x, y, w, h, 6, 536870911);
    v7 = 62;
    v8 = (v7 + 4);
    v9 = (x + (w / 2));
    v10 = (x + w);
    v11 = Math.min(1, (((f)(System.currentTimeMillis() - auction.Giveaway.drawnAt())) / 4500.0));
    v12 = (1 - (((1 - v11) * (1 - v11)) * (1 - v11)));
    v13 = ((((f)(v10 + (ui.StreamerScreen.reelWinner * v8))) + (((f)v7) / 2)) - ((f)v9));
    v14 = Math.round((v13 * v12));
    if (cmp(v11,1) <0) goto L264;
    goto L265;
    L264:
    L265:
    v15 = 0;
    ctx.enableScissor((x + 2), (y + 2), ((x + w) - 2), ((y + h) - 2));
    v16 = 0;
    L292:
    if (v16 >= ui.StreamerScreen.reel.size()) goto L530;
    v17 = ((v10 + (v16 * v8)) - v14);
    if ((v17 + v7) < x) goto L524;
    if (v17 <= (x + w)) goto L339;
    goto L524;
    L339:
    if (v15 ==0) goto L356;
    if (v16 != ui.StreamerScreen.reelWinner) goto L356;
    goto L357;
    L356:
    L357:
    v18 = 0;
    if (v18 ==0) goto L385;
    goto L388;
    L385:
    L388:
    ui.LauncherGfx.round(v17, (y + 8), v7, (h - 16), 5, -420296372, -870959062);
    if (v18 ==0) goto L415;
    goto L418;
    L415:
    L418:
    ui.LauncherGfx.ring(v17, (y + 8), v7, (h - 16), 5, m1, 536870911);
    ui.FwmGfx.playerHead(ctx, ((String)ui.StreamerScreen.reel.get(v16)), (v17 + ((v7 - 16) / 2)), (y + 13), 16);
    v19 = ui.LauncherGfx.trim(this.font, ((String)ui.StreamerScreen.reel.get(v16)), (v7 - 6));
    if (v18 ==0) goto L520;
    goto L521;
    L520:
    L521:
    ctx.text(v19, (v17 + ((v7 - this.font.width(v19)) / 2)), ((y + h) - 20), -16045545, m1);
    L524:
    v16 = v16 + 1;
    goto L292;
    L530:
    ctx.disableScissor();
    ctx.fill(v9, (y + 3), (v9 + 1), ((y + h) - 3), -865972);
    ctx.fill((v9 - 3), (y + 2), (v9 + 4), (y + 3), -865972);
    ctx.fill((v9 - 2), (y + 3), (v9 + 3), (y + 4), -865972);
    ctx.fill((v9 - 1), (y + 4), (v9 + 2), (y + 5), -865972);
    return;
}

private void itemBox(client.gui.GuiGraphicsExtractor ctx, world.item.ItemStack stack, String sub) {
    v4 = 118;
    ui.LauncherGfx.round(ctx, 10, 64, 158, v4, 6, 1544960042);
    ui.LauncherGfx.ring(ctx, 10, 64, 158, v4, 6, 536870911);
    v5 = 64;
    v6 = (10 + ((158 - v5) / 2));
    v7 = 72;
    ui.LauncherGfx.round(ctx, v6, v7, v5, v5, 8, 856037136);
    if (stack.isEmpty() !=0) goto L230;
    v8 = ctx.pose();
    v8.pushMatrix();
    v8.translate(((f)(v6 + 8)), ((f)(v7 + 8)));
    v8.scale(3.0, 3.0);
    ctx.item(stack, 0, 0);
    v8.popMatrix();
    if (stack.getCount() <= 1) goto L160;
    goto L163;
    L160:
    L163:
    v9 = "" + stack.getHoverName().getString();
    v9 = ui.LauncherGfx.trim(this.font, v9, 146);
    this.text(ctx, v9, (10 + ((158 - this.font.width(v9)) / 2)), ((v7 + v5) + 6), m1);
    goto L276;
    L230:
    v8 = ui.Lang.t("Kein Item", "No item");
    this.text(ctx, v8, (10 + ((158 - this.font.width(v8)) / 2)), ((v7 + v5) + 6), -9464447);
    L276:
    if (sub.isEmpty() !=0) goto L328;
    sub = ui.LauncherGfx.trim(this.font, sub, 146);
    this.text(ctx, sub, (10 + ((158 - this.font.width(sub)) / 2)), ((v7 + v5) + 17), -5846860);
    L328:
    return;
}

private void settingChips(client.gui.GuiGraphicsExtractor ctx) {
    v2 = ?;
    if (config.FwmConfig.INSTANCE.auctionStackPayments ==0) goto L34;
    v2.add(ui.Lang.str("fwm.streamer.stack", new Object[0]));
    L34:
    if (config.FwmConfig.INSTANCE.auctionAntiSnipe ==0) goto L79;
    new Object[1][0] = Integer.valueOf(config.FwmConfig.INSTANCE.auctionAntiSnipeSeconds);
    v2.add(String.format(ui.Lang.str("fwm.streamer.antiSnipe", new Object[0]), new Object[1]));
    L79:
    if (v2.isEmpty() ==0) goto L89;
    return;
    L89:
    v3 = -4;
    v4 = v2.iterator();
    L100:
    if (v4.hasNext() ==0) goto L140;
    v5 = ((String)v4.next());
    v3 = (v3 + (this.font.width(v5) + 12));
    goto L100;
    L140:
    v4 = (10 + Math.max(4, ((158 - v3) / 2)));
    v5 = 165;
    v6 = v2.iterator();
    L169:
    if (v6.hasNext() ==0) goto L288;
    v7 = ((String)v6.next());
    v8 = ui.LauncherGfx.trim(this.font, v7, ((164 - v4) - 8));
    v9 = (this.font.width(v8) + 8);
    ui.LauncherGfx.round(ctx, v4, v5, v9, 12, 6, 526182548);
    ui.LauncherGfx.ring(ctx, v4, v5, v9, 12, 6, 1717364884);
    this.text(ctx, v8, (v4 + 4), (v5 + 2), -5846860);
    v4 = (v4 + (v9 + 4));
    goto L169;
    L288:
    return;
}

private void listBox(client.gui.GuiGraphicsExtractor ctx, String title, String count, int top, int bottom) {
    ui.LauncherGfx.round(ctx, 176, top, 274, (bottom - top), 6, 1544960042);
    ui.LauncherGfx.ring(ctx, 176, top, 274, (bottom - top), 6, 536870911);
    this.text(ctx, title, 184, (top + 6), -5846860);
    this.right(ctx, count, 442, (top + 6), -9464447);
    return;
}

private void rows(client.gui.GuiGraphicsExtractor ctx, int count, int top, int bottom, ui.StreamerScreen$RowPainter painter, int mx, int my) {
    this.listTop = top;
    this.listBottom = bottom;
    v8 = Math.max(1, ((bottom - top) / 20));
    this.scroll = Math.max(0, Math.min(this.scroll, Math.max(0, (count - v8))));
    this.rowBoxesFrom = this.boxes.size();
    ctx.enableScissor(176, top, 450, bottom);
    v9 = this.scroll;
    L79:
    if (v9 >= count) goto L126;
    if (v9 >= ((this.scroll + v8) + 1)) goto L126;
    painter.paint(v9, (top + ((v9 - this.scroll) * 20)));
    v9 = v9 + 1;
    goto L79;
    L126:
    ctx.disableScissor();
    this.rowBoxesTo = this.boxes.size();
    if (count <= v8) goto L208;
    v9 = (bottom - top);
    v10 = Math.max(10, ((v9 * v8) / count));
    v11 = (top + (((v9 - v10) * this.scroll) / Math.max(1, (count - v8))));
    ui.LauncherGfx.round(ctx, 446, v11, 2, v10, 1, 1090519039);
    L208:
    return;
}

protected int designScrolled(double mx, double a2, double my) {
    if (cmp(mx,176.0) <0) goto L36;
    if (cmp(mx,450.0) >=0) goto L36;
    if (cmp(my,((d)this.listTop)) <0) goto L36;
    if (cmp(my,((d)this.listBottom)) <0) goto L38;
    L36:
    return 0;
    L38:
    this.scroll = Math.max(0, (this.scroll - ((i)Math.signum(v5))));
    return 1;
}

private int buttonsWidth() {
    return (((this.smallW("TPA") + this.smallW("HERE")) + this.smallW(ui.Lang.t("Kopieren", "Copy"))) + 6);
}

private int smallW(String label) {
    return (this.font.width(label) + 8);
}

private int playerButtons(client.gui.GuiGraphicsExtractor ctx, String name, int x, int y, int mx, int my, boolean winner) {
    x = (x + (this.small(ctx, "TPA", x, y, mx, my, /*indy*/run(this, name)) + 3));
    x = (x + (this.small(ctx, "HERE", x, y, mx, my, /*indy*/run(this, winner, name)) + 3));
    this.small(ctx, ui.Lang.t("Kopieren", "Copy"), x, y, mx, my, /*indy*/run(this, name));
    return;
}

private void small(client.gui.GuiGraphicsExtractor ctx, String label, int x, int y, int mx, int my, Runnable action) {
    v8 = this.smallW(label);
    v9 = 14;
    if (mx < x) goto L47;
    if (mx >= (x + v8)) goto L47;
    if (my < y) goto L47;
    if (my >= (y + v9)) goto L47;
    goto L48;
    L47:
    L48:
    v10 = 0;
    if (v10 ==0) goto L70;
    goto L73;
    L70:
    L73:
    ui.LauncherGfx.round(x, y, v8, v9, 4, -218103809, 788529151);
    if (v10 ==0) goto L97;
    goto L98;
    L97:
    L98:
    ctx.text(label, (x + 4), (y + 3), -16045545, m1);
    this.button(x, y, v8, v9, action);
    return v8;
}

private void pill(client.gui.GuiGraphicsExtractor ctx, String label, int x, int y, int mx, int my, boolean primary, Runnable action) {
    v9 = (this.font.width(label) + 16);
    v10 = 16;
    if (mx < x) goto L53;
    if (mx >= (x + v9)) goto L53;
    if (my < y) goto L53;
    if (my >= (y + v10)) goto L53;
    goto L54;
    L53:
    L54:
    v11 = 0;
    if (primary ==0) goto L78;
    if (v11 ==0) goto L72;
    goto L92;
    L72:
    goto L92;
    L78:
    if (v11 ==0) goto L89;
    goto L92;
    L89:
    L92:
    v12 = 536870911;
    ui.LauncherGfx.round(ctx, x, y, v9, v10, 8, v12);
    if (primary !=0) goto L130;
    ui.LauncherGfx.ring(ctx, x, y, v9, v10, 8, 1090519039);
    L130:
    if (primary ==0) goto L152;
    goto L153;
    L152:
    L153:
    ctx.text(label, (x + 8), (y + 4), -16045545, m1);
    this.button(x, y, v9, v10, action);
    return v9;
}

private void cog(client.gui.GuiGraphicsExtractor ctx, int x, int y, int mx, int my, boolean giveaway) {
    v7 = 16;
    if (mx < x) goto L38;
    if (mx >= (x + v7)) goto L38;
    if (my < y) goto L38;
    if (my >= (y + v7)) goto L38;
    goto L39;
    L38:
    L39:
    v8 = 0;
    if (v8 ==0) goto L61;
    goto L64;
    L61:
    L64:
    ui.LauncherGfx.round(x, y, v7, v7, 8, 1090519039, 536870911);
    ui.LauncherGfx.ring(ctx, x, y, v7, v7, 8, 1090519039);
    if (v8 ==0) goto L103;
    goto L105;
    L103:
    L105:
    ui.SharpIcons.draw("icon_cog", (x + 3), (y + 3), 10, m1, -5846860);
    this.button(x, y, v7, v7, /*indy*/run(this, giveaway));
    return v7;
}

private boolean button(int x, int y, int w, int h, Runnable action) {
    new int[4][0] = x;
    new int[4][1] = y;
    new int[4][2] = w;
    new int[4][3] = h;
    this.boxes.add(new int[4]);
    this.actions.add(action);
    return;
}

private static String clock(long millis) {
    v2 = ((millis + 999) / 1000);
    if (cmp(v2,3600) <0) goto L24;
    return auction.Times.clock(((i)v2));
    L24:
    new Object[2][0] = Long.valueOf((v2 / 60));
    new Object[2][1] = Long.valueOf((v2 % 60));
    return String.format("%02d:%02d", new Object[2]);
}

private static String ago(long time) {
    v2 = Math.max(0, ((System.currentTimeMillis() - time) / 1000));
    if (cmp(v2,60) >=0) goto L38;
    return ui.Lang.t('vor ' + v2 + ' s', v2 + ' s ago');
    L38:
    if (cmp(v2,3600) >=0) goto L70;
    return ui.Lang.t('vor ' + (v2 / 60) + ' Min', (v2 / 60) + ' min ago');
    L70:
    return ui.Lang.t('vor ' + (v2 / 3600) + ' Std', (v2 / 3600) + ' h ago');
}

private String command(String command, String name) {
    v3 = command + ':' + name.toLowerCase();
    v4 = System.currentTimeMillis();
    v6 = ((Long)ui.StreamerScreen.sent.get(v3));
    if (v6 == null) goto L51;
    if (cmp((v4 - v6.longValue()),3000) >=0) goto L51;
    return;
    L51:
    ui.StreamerScreen.sent.put(v3, Long.valueOf(v4));
    if (auction.StreamerStore.command(command + ' ' + name) ==0) goto L103;
    ui.StreamerScreen.toast = ui.Lang.t("Gesendet: /", "Sent: /") + command + ' ' + name;
    ui.StreamerScreen.toastAt = v4;
    L103:
    return;
}

private long copy(String text, String note) {
    auction.StreamerStore.copy(text);
    ui.StreamerScreen.toast = note;
    ui.StreamerScreen.toastAt = System.currentTimeMillis();
    return;
}

private static String what(world.item.ItemStack stack) {
    if (stack.getCount() <= 1) goto L20;
    goto L23;
    L20:
    L23:
    return "" + stack.getHoverName().getString();
}

private static Object template(int slot) {
    v1 = config.FwmConfig.INSTANCE.streamerAdTemplates;
    if (v1 == null) goto L34;
    if (slot >= v1.size()) goto L34;
    goto L35;
    L34:
    L35:
    v2 = null;
    if (v2 == null) goto L47;
    if (v2.isBlank() ==0) goto L62;
    L47:
    goto L63;
    L62:
    L63:
    return v2;
}

private static String streamerServer() {
    v0 = config.FwmConfig.INSTANCE.streamerServer;
    if (v0 == null) goto L18;
    if (v0.isBlank() ==0) goto L24;
    L18:
    goto L28;
    L24:
    L28:
    return v0.trim();
}

private static String common(String template) {
    return template.replace("[Spieler]", ui.FwmGfx.playerName()).replace("[Name]", ui.FwmGfx.playerName()).replace("[Server]", ui.StreamerScreen.streamerServer());
}

private static Object runningAdvert() {
    v0 = config.FwmConfig.INSTANCE.streamerAdRotation;
    if (v0 == null) goto L20;
    if (v0.isEmpty() ==0) goto L24;
    L20:
    v0 = config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION;
    L24:
    v1 = ((String)v0.get((ui.StreamerScreen.rotation % v0.size())));
    ui.StreamerScreen.rotation = ((ui.StreamerScreen.rotation + 1) % v0.size());
    if (v1 == null) goto L70;
    if (v1.isBlank() ==0) goto L85;
    L70:
    goto L86;
    L85:
    L86:
    return v1;
}

private void advertAuction() {
    v1 = auction.Auction.leader();
    if (auction.Auction.running() ==0) goto L16;
    goto L20;
    L16:
    L20:
    if (v1 != null) goto L72;
    goto L78;
    L72:
    L78:
    if (v1 != null) goto L94;
    goto L95;
    L94:
    L95:
    if (v1 != null) goto L111;
    goto L117;
    L111:
    L117:
    v2 = "[Preis]".replace("\u2013", util.Fmt.dollars(auction.Auction.price()));
    this.send(v2);
    return;
}

private void advertGiveaway() {
    if (auction.Giveaway.active() ==0) goto L34;
    goto L35;
    L34:
    L35:
    v1 = "[Zeit]".replace(auction.Giveaway.remainingMs(), ui.StreamerScreen.clock(0)).replace("[Befehl]", auction.Giveaway.joinCommand()).replace("[Wort]", auction.Giveaway.word()).replace("[Teilnehmer]", String.valueOf(auction.Giveaway.participants().size()));
    this.send(v1);
    return;
}

private String send(String line) {
    v2 = System.currentTimeMillis();
    if (cmp((v2 - ui.StreamerScreen.advertAt),5000) >=0) goto L33;
    ui.StreamerScreen.toast = ui.Lang.t("Werbung gerade gesendet \u2013 kurz warten", "Advert just sent \u2013 wait a moment");
    ui.StreamerScreen.toastAt = v2;
    return;
    L33:
    if (auction.StreamerStore.say(line) ==0) goto L60;
    ui.StreamerScreen.advertAt = v2;
    ui.StreamerScreen.toast = ui.Lang.t("Werbung gesendet", "Advert sent");
    ui.StreamerScreen.toastAt = v2;
    L60:
    return;
}

private void editAdverts(boolean giveaway) {
    ui.StreamerScreen.reopen = 1;
    ui.StreamerScreen.reopenGiveaway = giveaway;
    if (giveaway ==0) goto L19;
    goto L20;
    L19:
    L20:
    v2 = ui.Design.open("fwm.tab.auction", 1, 0);
    client.Minecraft.getInstance().gui.setScreen(v2);
    if ((v2 instanceof ui.DesignHost) ==0) goto L58;
    v3 = ((ui.DesignHost)v2);
    v3.openSub(?);
    L58:
    return;
}

protected void designClicked(double mx, double a2, int my) {
    if (this.confirm.open() ==0) goto L20;
    return this.confirm.mouseClicked(mx, my);
    L20:
    if (v5 ==0) goto L27;
    return 0;
    L27:
    v6 = (this.boxes.size() - 1);
    L40:
    if (v6 <0) goto L142;
    v7 = (([I)this.boxes.get(v6));
    if (ui.StreamerScreen.inside(v7, mx, my) !=0) goto L74;
    goto L136;
    L74:
    if (v6 < this.rowBoxesFrom) goto L115;
    if (v6 >= this.rowBoxesTo) goto L115;
    if (cmp(my,((d)this.listTop)) <0) goto L136;
    if (cmp(my,((d)this.listBottom)) <0) goto L115;
    goto L136;
    L115:
    ((Runnable)this.actions.get(v6)).run();
    return 1;
    L136:
    v6 = v6 + 255;
    goto L40;
    L142:
    return 0;
}

public boolean keyPressed(client.input.KeyEvent input) {
    if (this.confirm.open() ==0) goto L22;
    return this.confirm.keyPressed(input.key());
    L22:
    return this.keyPressed(input);
}

protected void leavePage() {
    return;
}

public void onClose() {
    ui.StreamerScreen.reopen = 0;
    config.FwmConfig.INSTANCE.clamp();
    this.onClose();
    return;
}

private void lambda$cog$0(boolean giveaway) {
    this.editAdverts(giveaway);
    return;
}

private void lambda$playerButtons$3(String name) {
    this.copy(name, ui.Lang.t("Kopiert: ", "Copied: ") + name);
    return;
}

private void lambda$playerButtons$1(boolean winner, String name) {
    if (winner ==0) goto L42;
    this.confirm.ask(network.chat.Component.literal(ui.Lang.t('Gewinner ' + name + ' per /tpahere zu dir holen?', 'Pull winner ' + name + ' over with /tpahere?')), "fwm.confirm.send", /*indy*/run(this, name));
    goto L50;
    L42:
    this.command("tpahere", name);
    L50:
    return;
}

private void lambda$playerButtons$2(String name) {
    this.command("tpahere", name);
    return;
}

private void lambda$playerButtons$0(String name) {
    this.command("tpa", name);
    return;
}

private void lambda$giveaway$4() {
    this.minecraft.gui.setScreen(ui.Design.open(null, "fwm.tab.giveaway", m1));
    return;
}

private void lambda$giveaway$3() {
    auction.Giveaway.reset();
    this.minecraft.gui.setScreen(ui.Design.open(null, "fwm.tab.giveaway", m1));
    return;
}

private long lambda$giveaway$2() {
    if (cmp((System.currentTimeMillis() - this.cancelArmedAt),3000) >=0) goto L21;
    auction.Giveaway.cancel();
    goto L28;
    L21:
    this.cancelArmedAt = System.currentTimeMillis();
    L28:
    return;
}

private void lambda$giveaway$1(List all, String winner, client.gui.GuiGraphicsExtractor ctx, int mx, int my, int i, int y) {
    v8 = ((auction.Giveaway$Participant)all.get(i));
    v9 = v8.name().equals(winner);
    if (v9 ==0) goto L47;
    goto L50;
    L47:
    L50:
    ui.LauncherGfx.round(180, y, 266, 18, 4, 1039321420, 352321535);
    v10 = String.valueOf((i + 1));
    this.text(ctx, v10, 186, (y + 5), -9464447);
    ui.FwmGfx.playerHead(ctx, v8.name(), 200, (y + 3), 12);
    v11 = (442 - this.buttonsWidth());
    v12 = ui.StreamerScreen.ago(v8.joinedAt());
    v13 = ((v11 - 6) - this.font.width(v12));
    if (v9 ==0) goto L173;
    goto L174;
    L173:
    L174:
    ctx.text(ui.LauncherGfx.trim(this.font, v8.name(), ((v13 - 6) - 216)), 216, (y + 5), -865972, m1);
    this.text(ctx, v12, v13, (y + 5), -9464447);
    this.playerButtons(ctx, v8.name(), v11, (y + 2), mx, my, v9);
    return;
}

private void lambda$giveaway$0(String join) {
    this.copy(join, ui.Lang.t("Kopiert: ", "Copied: ") + join);
    return;
}

private void lambda$auction$2() {
    this.minecraft.gui.setScreen(ui.Design.open(null, "fwm.tab.auction", m1));
    return;
}

private void lambda$auction$1() {
    auction.Auction.clearResult();
    this.minecraft.gui.setScreen(ui.Design.open(null, "fwm.tab.auction", m1));
    return;
}

private void lambda$auction$0(List bids, List late, boolean hasWinner, client.gui.GuiGraphicsExtractor ctx, boolean result, int mx, int my, int i, int y) {
    if (i < bids.size()) goto L15;
    goto L16;
    L15:
    L16:
    v10 = 0;
    if (v10 ==0) goto L44;
    goto L55;
    L44:
    L55:
    v11 = ((auction.Auction$Bid)bids.get(i));
    if (v10 !=0) goto L75;
    if (i !=0) goto L75;
    if (hasWinner ==0) goto L75;
    goto L76;
    L75:
    L76:
    v12 = 0;
    if (v10 ==0) goto L89;
    goto L102;
    L89:
    if (v12 ==0) goto L99;
    goto L102;
    L99:
    L102:
    v13 = -6052957;
    if (v12 ==0) goto L128;
    goto L142;
    L128:
    if (v10 ==0) goto L139;
    goto L142;
    L139:
    L142:
    ui.LauncherGfx.round(y, 266, 18, 4, 1039321420, 653294194, 352321535);
    v14 = 0;
    if (v10 !=0) goto L266;
    v15 = (i + 1) + '.';
    v16 = Math.max(12, (this.font.width(v15) + 4));
    v14 = (v16 - 12);
    if (v12 ==0) goto L213;
    goto L216;
    L213:
    L216:
    ui.LauncherGfx.round(184, (y + 3), v16, 12, 3, -865972, 872415231);
    if (v12 ==0) goto L259;
    goto L260;
    L259:
    L260:
    ctx.text(v15, ((184 + ((v16 - this.font.width(v15)) / 2)) + 1), (y + 5), -16045545, m1);
    goto L303;
    L266:
    ui.LauncherGfx.round(ctx, 184, (y + 3), 12, 12, 3, 1441823346);
    this.text(ctx, "!", 189, (y + 5), m1);
    L303:
    ui.FwmGfx.playerHead(ctx, v11.name(), (200 + v14), (y + 3), 12);
    if (v12 ==0) goto L336;
    goto L344;
    L336:
    L344:
    v15 = (442 - this.buttonsWidth());
    v16 = util.Fmt.dollars(v11.amount());
    v17 = ((v15 - 6) - this.font.width(v16));
    if (v12 ==0) goto L417;
    if (result ==0) goto L395;
    goto L404;
    L395:
    L404:
    goto L422;
    L417:
    L422:
    v18 = v11.name();
    v19 = (216 + v14);
    this.text(ctx, ui.LauncherGfx.trim(this.font, v18, ((v17 - 6) - v19)), v19, (y + 5), v13);
    if (v12 ==0) goto L485;
    goto L487;
    L485:
    L487:
    ctx.text(v16, v17, (y + 5), -10688364, v13);
    if (v12 !=0) goto L517;
    this.playerButtons(ctx, v11.name(), v15, (y + 2), mx, my, 0);
    L517:
    return;
}

private void lambda$header$4(String pay) {
    this.copy(pay, ui.Lang.t("Kopiert: ", "Copied: ") + pay);
    return;
}

private void lambda$header$3() {
    this.copy(auction.Giveaway.joinCommand(), ui.Lang.t("Kopiert: ", "Copied: ") + auction.Giveaway.joinCommand());
    return;
}

private void lambda$header$2() {
    this.giveawayTab = 0;
    this.scroll = 0;
    return;
}

private void lambda$header$1() {
    this.giveawayTab = 1;
    this.scroll = 0;
    return;
}

private static void lambda$header$0() {
    if (ui.StreamerScreen.earningsShown !=0) goto L10;
    goto L11;
    L10:
    L11:
    ui.StreamerScreen.earningsShown = 0;
    return;
}

private static void lambda$registerOnce$0(client.Minecraft client) {
    if (ui.StreamerScreen.reopen ==0) goto L48;
    if (client.gui.screen() != null) goto L48;
    ui.StreamerScreen.reopen = 0;
    if (ui.StreamerScreen.reopenGiveaway ==0) goto L40;
    goto L42;
    L40:
    L42:
    ?.setScreen(?);
    L48:
    return;
}

static List <clinit>() {
    ui.StreamerScreen.sent = ?;
    ui.StreamerScreen.reel = List.of();
    return;
}

