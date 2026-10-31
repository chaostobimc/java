// class net/friendswithmoney/fwm/ui/LauncherScreen  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/DesignHost
field VW I;
field VH I;
field GROUPS [[Ljava/lang/String;;
field MORE [Ljava/lang/String;;
field STREAMER [Ljava/lang/String;;
field GROUPS_TOP I;
field ROWS I;
field STREAMER_TOP I;
field BELOW_TILES I;
field query Ljava/lang/String;;
field TILE_STEP I;
field VIEW_H I;
field colScroll [F;
field colTarget [F;
field colBounce [F;
field colTouched [J;
field colViews [[I;
field lastFrame J;
field scrolledFor Ljava/lang/String;;
field SEARCH Z;
field tileBoxes Ljava/util/List;;
field tileKeys Ljava/util/List;;
field SPAWNER_CYCLE_MS J;
field TUTORIAL Ljava/lang/String;;
public static int maxScalePercent() {
    v0 = client.Minecraft.getInstance();
    if (v0 == null) goto L15;
    if (v0.getWindow() != null) goto L19;
    L15:
    return 150;
    L19:
    v1 = v0.getWindow().getGuiScaledWidth();
    v2 = v0.getWindow().getGuiScaledHeight();
    v3 = ui.LauncherScreen.crispScale(v1, v2, 426, 256, ((d)v0.getWindow().getGuiScale()));
    v4 = Math.min((((f)v1) / 426.0), (((f)v2) / 256.0));
    v5 = ((i)Math.floor(((d)((v4 / v3) * 100.0))));
    return Math.max(70, Math.min(150, v5));
}

public void <init>(client.gui.screens.Screen parent) {
    this.query = "";
    this.colScroll = new float[ui.LauncherScreen.GROUPS.length];
    this.colTarget = new float[ui.LauncherScreen.GROUPS.length];
    this.colBounce = new float[ui.LauncherScreen.GROUPS.length];
    this.colTouched = new long[ui.LauncherScreen.GROUPS.length];
    this.colViews = new [I[ui.LauncherScreen.GROUPS.length];
    this.scrolledFor = "";
    this.tileBoxes = ?;
    this.tileKeys = ?;
    return;
}

public void <init>(client.gui.screens.Screen parent, String page, int tab) {
    this.query = "";
    this.colScroll = new float[ui.LauncherScreen.GROUPS.length];
    this.colTarget = new float[ui.LauncherScreen.GROUPS.length];
    this.colBounce = new float[ui.LauncherScreen.GROUPS.length];
    this.colTouched = new long[ui.LauncherScreen.GROUPS.length];
    this.colViews = new [I[ui.LauncherScreen.GROUPS.length];
    this.scrolledFor = "";
    this.tileBoxes = ?;
    this.tileKeys = ?;
    if (page == null) goto L100;
    this.openPage(page, tab);
    L100:
    return;
}

protected int vw() {
    return 426;
}

protected int vh() {
    return 256;
}

// DECOMPILE ERROR in status: 'dict' object has no attribute 'op_type'
// DECOMPILE ERROR in groupName: 'dict' object has no attribute 'op_type'
private static String auctionLine() {
    if (auction.Auction.running() ==0) goto L36;
    return ui.Lang.t("L\u00e4uft, noch ", "Running, ") + auction.Times.clock(((i)((auction.Auction.remainingMs() + 999) / 1000)));
    L36:
    if (auction.Auction.hasResult() ==0) goto L52;
    return ui.Lang.t("Ergebnis offen", "Result open");
    L52:
    return ui.Lang.t("Versteigern per /pay", "Auction via /pay");
}

private static String giveawayLine() {
    v0 = auction.Giveaway.status();
    v1 = auction.Giveaway.participants().size();
    // switch (ui.LauncherScreen$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[v0.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=84), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=45), Operand(op_type=<OperandTypes.BRANCH: 40>, value=72)]
    L48:
    goto L114;
    L66:
    goto L114;
    L93:
    goto L114;
    L105:
    L114:
    return ui.Lang.t("Verlosen per /msg", "Raffle via /msg");
}

private static String spawnerLine() {
    v0 = net.Spawners.data();
    if (v0 == null) goto L15;
    if (v0.items != null) goto L23;
    L15:
    return ui.Lang.t("Preise ansehen", "View prices");
    L23:
    v1 = ui.LauncherScreen.shownSpawner();
    if (v1 == null) goto L45;
    return v1.name + ' ' + v1.buyPrice;
    L45:
    return ui.Lang.t("Preise ansehen", "View prices");
}

private static Object shownSpawner() {
    v0 = net.Spawners.data();
    if (v0 == null) goto L15;
    if (v0.items != null) goto L17;
    L15:
    return null;
    L17:
    v1 = ?;
    v2 = v0.items.iterator();
    L35:
    if (v2.hasNext() ==0) goto L89;
    v3 = ((net.SpawnerData$Item)v2.next());
    if (v3.name == null) goto L86;
    if (v3.buyPrice == null) goto L86;
    if (v3.buyPrice.isBlank() !=0) goto L86;
    v1.add(v3);
    L86:
    goto L35;
    L89:
    if (v1.isEmpty() ==0) goto L100;
    return null;
    L100:
    return ((net.SpawnerData$Item)v1.get(((i)((System.currentTimeMillis() / 2500) % ((l)v1.size())))));
}

private static String clanLine() {
    v0 = ?;
    v1 = net.Members.list().iterator();
    L17:
    if (v1.hasNext() ==0) goto L57;
    v2 = ((net.MemberData$Member)v1.next());
    if (v2.clan == null) goto L54;
    v0.add(v2.clan);
    L54:
    goto L17;
    L57:
    if (v0.isEmpty() ==0) goto L78;
    goto L89;
    L78:
    L89:
    return v0.size() + ' Clans';
}

private boolean matches(String key) {
    if (this.query.isBlank() ==0) goto L12;
    return 1;
    L12:
    return this.title(key).toLowerCase(Locale.ROOT).contains(this.query.toLowerCase(Locale.ROOT).trim());
}

private boolean firstMatch() {
    v1 = ui.LauncherScreen.GROUPS;
    v2 = v1.length;
    v3 = 0;
    L9:
    if (v3 >= v2) goto L60;
    v4 = v1[v3];
    v5 = 1;
    L22:
    if (v5 >= v4.length) goto L54;
    if (this.matches(v4[v5]) ==0) goto L48;
    return v4[v5];
    L48:
    v5 = v5 + 1;
    goto L22;
    L54:
    v3 = v3 + 1;
    goto L9;
    L60:
    v1 = 1;
    L62:
    if (v1 >= ui.LauncherScreen.STREAMER.length) goto L94;
    if (this.matches(ui.LauncherScreen.STREAMER[v1]) ==0) goto L88;
    return ui.LauncherScreen.STREAMER[v1];
    L88:
    v1 = v1 + 1;
    goto L62;
    L94:
    v1 = ui.LauncherScreen.MORE;
    v2 = v1.length;
    v3 = 0;
    L103:
    if (v3 >= v2) goto L131;
    v4 = v1[v3];
    if (this.matches(v4) ==0) goto L125;
    return v4;
    L125:
    v3 = v3 + 1;
    goto L103;
    L131:
    return null;
}

protected void drawCanvas(client.gui.GuiGraphicsExtractor ctx, int mx, int my, float delta) {
    this.tileBoxes.clear();
    this.tileKeys.clear();
    this.brand(ctx, 12, 9, 414);
    if (this.pageKey == null) goto L72;
    this.setFrame(14, 34, 398, 200);
    this.drawPage(ctx, mx, my, delta, "Start");
    this.footer(ctx, mx, my, 0);
    return;
    L72:
    this.searchField(ctx);
    this.groups(ctx, mx, my);
    this.footer(ctx, mx, my, 1);
    return;
}

private void searchField(client.gui.GuiGraphicsExtractor ctx) {
    v2 = ((37 + this.font.width("Friends With Money")) + 8);
    v3 = Math.max(113, v2);
    v4 = Math.max(80, Math.min(200, ((this.infoLeft() - 8) - v3)));
    v5 = 10;
    if (this.query.isEmpty() !=0) goto L82;
    ui.LauncherGfx.ring(ctx, (v3 - 1), (v5 - 1), (v4 + 2), 18, 9, -1436751724);
    L82:
    ui.LauncherGfx.round(ctx, v3, v5, v4, 16, 8, -219220752);
    ui.LauncherGfx.ring(ctx, (v3 + 7), (v5 + 4), 7, 7, 3, -13743558);
    ctx.fill((v3 + 13), (v5 + 10), (v3 + 15), (v5 + 12), -13743558);
    if (this.query.isEmpty() ==0) goto L215;
    v6 = ui.Lang.t("Lostippen zum Suchen", "Type to search");
    v7 = (((v4 - 20) - this.font.width(input.Keybinds.menuKeyName())) - 14);
    this.text(ctx, ui.LauncherGfx.trim(this.font, v6, v7), (v3 + 20), (v5 + 4), -9796234);
    goto L293;
    L215:
    this.text(ctx, this.query, (v3 + 20), (v5 + 4), -15716322);
    if (cmp(((System.currentTimeMillis() / 500) % 2),0) !=0) goto L293;
    v6 = (((v3 + 20) + this.font.width(this.query)) + 1);
    ctx.fill(v6, (v5 + 3), (v6 + 1), (v5 + 13), -12136081);
    L293:
    v6 = input.Keybinds.menuKeyName();
    v7 = (this.font.width(v6) + 6);
    ui.LauncherGfx.ring(ctx, (((v3 + v4) - v7) - 5), (v5 + 3), v7, 10, 3, -6442074);
    this.text(ctx, v6, (((v3 + v4) - v7) - 2), (v5 + 4), -9796234);
    return;
}

private void groups(client.gui.GuiGraphicsExtractor ctx, int mx, int my) {
    v4 = 126;
    v5 = 9;
    v6 = ((426 - ((3 * v4) + (2 * v5))) / 2);
    v7 = 32;
    if (this.query.isBlank() ==0) goto L43;
    goto L47;
    L43:
    L47:
    v8 = this.firstMatch();
    this.animateColumns(v8);
    v9 = 0;
    L58:
    if (v9 >= ui.LauncherScreen.GROUPS.length) goto L541;
    v10 = (v6 + (v9 * (v4 + v5)));
    if (this.query.isBlank() ==0) goto L121;
    goto L124;
    L121:
    L124:
    ctx.text(ui.LauncherScreen.groupName(ui.LauncherScreen.GROUPS[v9][0]).toUpperCase(Locale.ROOT), (v10 + 2), v7, -9464447, 1433376129);
    v11 = (v7 + 11);
    v12 = (this.colScroll[v9] + this.colBounce[v9]);
    if (mx < v10) goto L186;
    if (mx >= (v10 + v4)) goto L186;
    if (my < v11) goto L186;
    if (my >= (v11 + 137)) goto L186;
    goto L187;
    L186:
    L187:
    v13 = 0;
    new int[4][0] = v10;
    new int[4][1] = v11;
    new int[4][2] = v4;
    new int[4][3] = 137;
    this.colViews[v9] = new int[4];
    ctx.enableScissor((v10 - 1), v11, ((v10 + v4) + 1), (v11 + 137));
    v14 = ctx.pose();
    v14.pushMatrix();
    v14.translate(0, (-v12));
    v15 = 1;
    L268:
    if (v15 >= ui.LauncherScreen.GROUPS[v9].length) goto L510;
    v16 = ui.LauncherScreen.GROUPS[v9][v15];
    v17 = (v11 + ((v15 - 1) * 28));
    v18 = 25;
    v19 = Math.round((((f)v17) - v12));
    v20 = Math.max(v19, v11);
    v21 = Math.min((v19 + v18), (v11 + 137));
    if (v21 > v20) goto L353;
    goto L504;
    L353:
    if (v13 ==0) goto L389;
    if (mx < v10) goto L389;
    if (mx >= (v10 + v4)) goto L389;
    if (my < v20) goto L389;
    if (my >= v21) goto L389;
    goto L390;
    L389:
    L390:
    v22 = 0;
    v23 = this.matches(v16);
    if (v23 ==0) goto L424;
    if (v22 !=0) goto L420;
    if (v16.equals(v8) ==0) goto L424;
    L420:
    goto L425;
    L424:
    L425:
    v24 = 0;
    new int[4][0] = v10;
    new int[4][1] = v20;
    new int[4][2] = v4;
    new int[4][3] = (v21 - v20);
    this.tileBoxes.add(new int[4]);
    this.tileKeys.add(v16);
    if (v23 !=0) goto L500;
    goto L501;
    L500:
    L501:
    ctx.tile(v10, v17, v4, v18, v16, v24, v22, 1, 0);
    L504:
    v15 = v15 + 1;
    goto L268;
    L510:
    v14.popMatrix();
    ctx.disableScissor();
    this.scrollHints(ctx, v9, v10, v11, v4, v13);
    v9 = v9 + 1;
    goto L58;
    L541:
    v9 = ((3 * v4) + (2 * v5));
    v10 = (ui.LauncherScreen.STREAMER.length - 1);
    v11 = ((v9 - ((v10 - 1) * v5)) / v10);
    if (this.query.isBlank() ==0) goto L611;
    goto L614;
    L611:
    L614:
    ctx.text(ui.LauncherScreen.STREAMER[0].toUpperCase(Locale.ROOT), (v6 + 2), 186, -9464447, 1433376129);
    v12 = 1;
    L620:
    if (v12 >= ui.LauncherScreen.STREAMER.length) goto L813;
    v13 = ui.LauncherScreen.STREAMER[v12];
    v14 = (v6 + ((v12 - 1) * (v11 + v5)));
    v15 = 197;
    v16 = 25;
    if (mx < v14) goto L695;
    if (mx >= (v14 + v11)) goto L695;
    if (my < v15) goto L695;
    if (my >= (v15 + v16)) goto L695;
    goto L696;
    L695:
    L696:
    v17 = 0;
    v18 = this.matches(v13);
    if (v18 ==0) goto L730;
    if (v17 !=0) goto L726;
    if (v13.equals(v8) ==0) goto L730;
    L726:
    goto L731;
    L730:
    L731:
    v19 = 0;
    new int[4][0] = v14;
    new int[4][1] = v15;
    new int[4][2] = v11;
    new int[4][3] = v16;
    this.tileBoxes.add(new int[4]);
    this.tileKeys.add(v13);
    if (v18 !=0) goto L803;
    goto L804;
    L803:
    L804:
    ctx.tile(v14, v15, v11, v16, v13, v19, v17, 1, 0);
    v12 = v12 + 1;
    goto L620;
    L813:
    if (this.query.isBlank() !=0) goto L835;
    this.moreHits(ctx, mx, my, v6, 228);
    L835:
    return;
}

private static int maxScroll(int group) {
    v1 = (((ui.LauncherScreen.GROUPS[group].length - 1) * 28) - 3);
    return Math.max(0, (v1 - 137));
}

private float animateColumns(String first) {
    v2 = System.currentTimeMillis();
    if (cmp(this.lastFrame,0) !=0) goto L17;
    goto L34;
    L17:
    L34:
    v4 = Math.min(0.10000000149011612, (((f)(v2 - this.lastFrame)) / 1000.0));
    this.lastFrame = v2;
    if (this.query.equals(this.scrolledFor) !=0) goto L205;
    this.scrolledFor = this.query;
    if (this.query.isBlank() !=0) goto L205;
    v5 = 0;
    L76:
    if (v5 >= ui.LauncherScreen.GROUPS.length) goto L205;
    v6 = 1;
    L88:
    if (v6 >= ui.LauncherScreen.GROUPS[v5].length) goto L199;
    if (this.matches(ui.LauncherScreen.GROUPS[v5][v6]) !=0) goto L119;
    goto L193;
    L119:
    v7 = ((v6 - 1) * 28);
    if (cmp(((f)v7),this.colTarget[v5]) >=0) goto L152;
    this.colTarget[v5] = ((f)v7);
    L152:
    if (cmp(((f)(v7 + 25)),(this.colTarget[v5] + 137.0)) <=0) goto L199;
    this.colTarget[v5] = ((f)((v7 + 25) - 137));
    goto L199;
    L193:
    v6 = v6 + 1;
    goto L88;
    L199:
    v5 = v5 + 1;
    goto L76;
    L205:
    v5 = (1 - ((f)Math.exp(((d)((-v4) * 14.0)))));
    v6 = ((f)Math.exp(((d)((-v4) * 12.0))));
    v7 = 0;
    L238:
    if (v7 >= ui.LauncherScreen.GROUPS.length) goto L384;
    this.colTarget[v7] = Math.max(0, Math.min(((f)ui.LauncherScreen.maxScroll(v7)), this.colTarget[v7]));
    this.colScroll[v7] = (this.colScroll[v7] + ((this.colTarget[v7] - this.colScroll[v7]) * v5));
    if (cmp(Math.abs((this.colTarget[v7] - this.colScroll[v7])),0.25) >=0) goto L341;
    this.colScroll[v7] = this.colTarget[v7];
    L341:
    this.colBounce[v7] = (this.colBounce[v7] * v6);
    if (cmp(Math.abs(this.colBounce[v7]),0.10000000149011612) >=0) goto L378;
    this.colBounce[v7] = 0;
    L378:
    v7 = v7 + 1;
    goto L238;
    L384:
    return;
}

private void scrollHints(client.gui.GuiGraphicsExtractor ctx, int g, int x, int top, int w, boolean hovered) {
    v7 = ui.LauncherScreen.maxScroll(g);
    if (v7 >0) goto L12;
    return;
    L12:
    v8 = (System.currentTimeMillis() - this.colTouched[g]);
    if (hovered ==0) goto L33;
    goto L57;
    L33:
    if (cmp(v8,900) >=0) goto L54;
    goto L57;
    L54:
    L57:
    v10 = 0.3499999940395355;
    v11 = Math.round((170.0 * v10));
    v12 = ((x + w) + 3);
    v13 = Math.max(14, (18769 / (137 + v7)));
    v14 = (top + Math.round((((f)(137 - v13)) * (this.colScroll[g] / ((f)v7)))));
    ctx.fill(v12, top, (v12 + 2), (top + 137), ((Math.round((34.0 * v10)) << 24) | 16777215));
    ctx.fill(v12, v14, (v12 + 2), (v14 + v13), ((v11 << 24) | 4641135));
    v15 = (x + (w / 2));
    v16 = ((Math.round((153.0 * Math.max(0.5, v10))) << 24) | 16777215);
    if (cmp(this.colScroll[g],((f)(v7 - 1))) >=0) goto L302;
    v17 = ((top + 137) + 1);
    ctx.fill((v15 - 3), v17, (v15 + 4), (v17 + 1), v16);
    ctx.fill((v15 - 2), (v17 + 1), (v15 + 3), (v17 + 2), v16);
    ctx.fill((v15 - 1), (v17 + 2), (v15 + 2), (v17 + 3), v16);
    L302:
    if (cmp(this.colScroll[g],1) <=0) goto L383;
    v17 = (top - 4);
    ctx.fill((v15 - 1), v17, (v15 + 2), (v17 + 1), v16);
    ctx.fill((v15 - 2), (v17 + 1), (v15 + 3), (v17 + 2), v16);
    ctx.fill((v15 - 3), (v17 + 2), (v15 + 4), (v17 + 3), v16);
    L383:
    return;
}

protected float designScrolled(double mx, double a2, double my) {
    if (this.pageKey != null) goto L14;
    if (cmp(v5,0) !=0) goto L16;
    L14:
    return 0;
    L16:
    v7 = 0;
    L19:
    if (v7 >= ui.LauncherScreen.GROUPS.length) goto L159;
    v8 = this.colViews[v7];
    if (v8 == null) goto L153;
    if (ui.LauncherScreen.inside(v8, mx, my) !=0) goto L55;
    goto L153;
    L55:
    this.colTouched[v7] = System.currentTimeMillis();
    v9 = ui.LauncherScreen.maxScroll(v7);
    v10 = (this.colTarget[v7] - (((f)Math.signum(v5)) * 28.0));
    if (cmp(v10,0) <0) goto L108;
    if (cmp(v10,((f)v9)) <=0) goto L132;
    L108:
    if (cmp(v10,0) >=0) goto L125;
    goto L126;
    L125:
    L126:
    v7[m1] = (((f)1) * 7.0);
    L132:
    this.colTarget[v7] = Math.max(0, Math.min(((f)v9), v10));
    return 1;
    L153:
    v7 = v7 + 1;
    goto L19;
    L159:
    return 0;
}

private void tile(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, String key, boolean selected, boolean over, boolean faded) {
    if (ui.LauncherScreen.switchable(key) ==0) goto L20;
    if (ui.LauncherScreen.on(key) !=0) goto L20;
    goto L21;
    L20:
    L21:
    v10 = 0;
    if (faded ==0) goto L86;
    ui.LauncherGfx.ring(ctx, x, y, w, h, 6, 352321535);
    ui.LauncherGfx.icon(ctx, key, (x + 7), (y + 6), 0.75, 872415231);
    this.text(ctx, this.title(key), (x + 25), (y + 9), 872415231);
    return;
    L86:
    if (selected ==0) goto L114;
    if (over !=0) goto L114;
    ui.LauncherGfx.round(ctx, x, y, w, h, 6, -218103809);
    goto L166;
    L114:
    if (over ==0) goto L134;
    goto L137;
    L134:
    L137:
    ui.LauncherGfx.round(x, y, w, h, 6, 2048870964, 1544960042);
    if (over ==0) goto L160;
    goto L163;
    L160:
    L163:
    ui.LauncherGfx.ring(x, y, w, h, 6, 1717364884, 536870911);
    L166:
    if (selected ==0) goto L180;
    if (over !=0) goto L180;
    goto L181;
    L180:
    L181:
    v11 = 0;
    if (key.equals("fwm.tab.spawner") ==0) goto L204;
    if (v10 !=0) goto L204;
    goto L205;
    L204:
    L205:
    v12 = null;
    if (v12 != null) goto L216;
    goto L224;
    L216:
    L224:
    v13 = ui.panel.SpawnerPanel.picture(v12.name);
    if (v13 == null) goto L256;
    ui.FwmGfx.blit(ctx, v13, (x + 5), (y + 4), 16, 16, 64, 64, 64);
    goto L298;
    L256:
    if (v11 ==0) goto L281;
    goto L295;
    L281:
    if (v10 ==0) goto L292;
    goto L295;
    L292:
    L295:
    ui.LauncherGfx.icon((x + 7), (y + 6), 0.75, -16045545, -1017230, -5846860);
    L298:
    if (v11 ==0) goto L336;
    goto L348;
    L336:
    if (v10 ==0) goto L347;
    goto L348;
    L347:
    L348:
    ui.LauncherGfx.trim(this.font, this.title(key), (w - 30)).text((x + 25), (y + 4), -16045545, -1017230, m1);
    if (v11 ==0) goto L389;
    goto L403;
    L389:
    if (v10 ==0) goto L400;
    goto L403;
    L400:
    L403:
    ui.LauncherGfx.trim(this.font, ui.LauncherScreen.status(key), (w - 30)).text((x + 25), (y + 14), -11768998, -1427080590, -9464447);
    if (key.equals("fwm.tab.sellmacro") ==0) goto L542;
    v14 = ui.LauncherGfx.trim(this.font, ui.LauncherScreen.status(key), (w - 30));
    v15 = input.Keybinds.toggleKeyName();
    v16 = (((x + 25) + this.font.width(v14)) + 4);
    v17 = (this.font.width(v15) + 6);
    if ((v16 + v17) > ((x + w) - 4)) goto L542;
    if (v11 ==0) goto L509;
    goto L512;
    L509:
    L512:
    ui.LauncherGfx.ring(v16, (y + 13), v17, 10, 3, -16045545, -5846860);
    if (v11 ==0) goto L538;
    goto L539;
    L538:
    L539:
    ctx.text(v15, (v16 + 3), (y + 14), -16045545, m1);
    L542:
    if (key.equals("fwm.tab.messages") ==0) goto L652;
    if (chat.Messages.unreadTotal() <=0) goto L652;
    v14 = String.valueOf(Math.min(99, chat.Messages.unreadTotal()));
    v15 = Math.max(9, (this.font.width(v14) + 5));
    ui.LauncherGfx.round(ctx, (((x + w) - v15) - 5), (y + 8), v15, 9, 4, -1017230);
    this.text(ctx, v14, ((((x + w) - v15) - 5) + (((v15 - this.font.width(v14)) + 1) / 2)), (y + 9), m1);
    L652:
    return;
}

private int moreHits(client.gui.GuiGraphicsExtractor ctx, int mx, int my, int x, int y) {
    v6 = ui.LauncherScreen.MORE;
    v7 = v6.length;
    v8 = 0;
    L13:
    if (v8 >= v7) goto L65;
    v9 = v6[v8];
    if (this.matches(v9) !=0) goto L39;
    goto L59;
    L39:
    x = (x + (this.chip(ctx, v9, x, y, mx, my) + 5));
    L59:
    v8 = v8 + 1;
    goto L13;
    L65:
    return;
}

private boolean chip(client.gui.GuiGraphicsExtractor ctx, String key, int x, int y, int mx, int my) {
    v7 = this.title(key);
    v8 = (this.font.width(v7) + 20);
    if (mx < x) goto L57;
    if (mx >= (x + v8)) goto L57;
    if (my < y) goto L57;
    if (my >= (y + 13)) goto L57;
    goto L58;
    L57:
    L58:
    v9 = 0;
    if (v9 ==0) goto L81;
    goto L84;
    L81:
    L84:
    ui.LauncherGfx.round(x, y, v8, 13, 6, 872415231, 352321535);
    if (v9 ==0) goto L108;
    goto L111;
    L108:
    L111:
    ui.LauncherGfx.icon(key, (x + 4), (y + 2), 0.5625, m1, -5846860);
    if (v9 ==0) goto L135;
    goto L138;
    L135:
    L138:
    ctx.text(v7, (x + 15), (y + 3), m1, -5846860);
    new int[4][0] = x;
    new int[4][1] = y;
    new int[4][2] = v8;
    new int[4][3] = 13;
    this.tileBoxes.add(new int[4]);
    this.tileKeys.add(key);
    return v8;
}

private boolean tutorialChip(client.gui.GuiGraphicsExtractor ctx, int x, int y, int mx, int my) {
    v6 = ui.Lang.str("fwm.tutorial.replayShort", new Object[0]);
    v7 = (this.font.width(v6) + 20);
    if (mx < x) goto L60;
    if (mx >= (x + v7)) goto L60;
    if (my < y) goto L60;
    if (my >= (y + 13)) goto L60;
    goto L61;
    L60:
    L61:
    v8 = 0;
    if (v8 ==0) goto L83;
    goto L86;
    L83:
    L86:
    ui.LauncherGfx.round(x, y, v7, 13, 6, 872415231, 352321535);
    if (v8 ==0) goto L110;
    goto L113;
    L110:
    L113:
    ui.LauncherGfx.ring((x + 4), (y + 2), 9, 9, 4, m1, -5846860);
    if (v8 ==0) goto L137;
    goto L140;
    L137:
    L140:
    ctx.text("?", (x + 7), (y + 3), m1, -5846860);
    if (v8 ==0) goto L163;
    goto L166;
    L163:
    L166:
    ctx.text(v6, (x + 15), (y + 3), m1, -5846860);
    new int[4][0] = x;
    new int[4][1] = y;
    new int[4][2] = v7;
    new int[4][3] = 13;
    this.tileBoxes.add(new int[4]);
    this.tileKeys.add("fwm.tutorial.replayShort");
    return;
}

private void footer(client.gui.GuiGraphicsExtractor ctx, int mx, int my, boolean chips) {
    v5 = 241;
    if (chips ==0) goto L104;
    if (this.query.isBlank() ==0) goto L104;
    v6 = 14;
    v7 = ui.LauncherScreen.MORE;
    v8 = v7.length;
    v9 = 0;
    L37:
    if (v9 >= v8) goto L79;
    v10 = v7[v9];
    v6 = (v6 + (this.chip(ctx, v10, v6, (v5 - 3), mx, my) + 5));
    v9 = v9 + 1;
    goto L37;
    L79:
    this.tutorialChip(ctx, v6, (v5 - 3), mx, my);
    this.flags(ctx, v5, 412);
    return;
    L104:
    v6 = this.footer(ctx, 14, v5, 412);
    if (this.pageKey != null) goto L149;
    this.right(ctx, ui.Lang.t("Enter \u00f6ffnet, Esc leert die Suche", "Enter opens, Esc clears the search"), (v6 - 10), v5, -9464447);
    L149:
    if (this.pageKey == null) goto L184;
    v7 = ui.Lang.t("Esc zur\u00fcck zum Start", "Esc back to start");
    this.right(ctx, v7, (v6 - 10), v5, -9464447);
    L184:
    return;
}

protected void designClicked(double mx, double a2, int my) {
    if (this.pageKey == null) goto L9;
    return 0;
    L9:
    v6 = 0;
    L12:
    if (v6 >= this.tileBoxes.size()) goto L189;
    if (ui.LauncherScreen.inside((([I)this.tileBoxes.get(v6)), mx, my) !=0) goto L51;
    goto L183;
    L51:
    v7 = ((String)this.tileKeys.get(v6));
    if (v7.equals("fwm.tutorial.replayShort") ==0) goto L97;
    if (v5 !=0) goto L86;
    ui.FwmTutorial.start();
    L86:
    if (v5 !=0) goto L95;
    goto L96;
    L95:
    L96:
    return 0;
    L97:
    if (v5 != 1) goto L126;
    if (ui.LauncherScreen.switchable(v7) !=0) goto L113;
    return 0;
    L113:
    config.Features.toggle(ui.LauncherScreen.feature(v7));
    config.ConfigManager.save();
    return 1;
    L126:
    if (v5 !=0) goto L181;
    if (this.matches(v7) ==0) goto L181;
    this.query = "";
    v8 = ui.StreamerScreen.ifLive(v7);
    if (v8 == null) goto L173;
    this.minecraft.gui.setScreen(v8);
    goto L179;
    L173:
    this.openPage(v7);
    L179:
    return 1;
    L181:
    return 0;
    L183:
    v6 = v6 + 1;
    goto L12;
    L189:
    return 0;
}

protected void leavePage() {
    this.closePage();
    return;
}

protected boolean escape() {
    if (this.escape() ==0) goto L9;
    return 1;
    L9:
    if (this.pageKey == null) goto L22;
    this.closePage();
    return 1;
    L22:
    if (this.query.isEmpty() !=0) goto L40;
    this.query = "";
    return 1;
    L40:
    return 0;
}

protected boolean designTyping() {
    if (this.query.isEmpty() !=0) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

protected void designKey(client.input.KeyEvent input) {
    if (this.pageKey == null) goto L9;
    return 0;
    L9:
    v2 = input.key();
    if (v2 != 42) goto L53;
    if (this.query.isEmpty() !=0) goto L51;
    this.query = this.query.substring(0, (this.query.length() - 1));
    L51:
    return 1;
    L53:
    if (v2 == 40) goto L65;
    if (v2 != 88) goto L91;
    L65:
    v3 = this.firstMatch();
    if (v3 == null) goto L89;
    this.click();
    this.query = "";
    this.openPage(v3);
    L89:
    return 1;
    L91:
    return 0;
}

protected String designChar(client.input.CharacterEvent input) {
    if (this.pageKey != null) goto L10;
    goto L12;
    L10:
    return 0;
    L12:
    v2 = input.codepoint();
    if (v2 < 32) goto L35;
    if (this.query.length() < 30) goto L37;
    L35:
    return 0;
    L37:
    if (this.query.isEmpty() ==0) goto L55;
    if (v2 != 32) goto L55;
    return 1;
    L55:
    this.query = this.query + ?;
    return 1;
}

static void <clinit>() {
    new String[8][0] = "Handel";
    new String[8][1] = "fwm.tab.sellmacro";
    new String[8][2] = "fwm.tab.market";
    new String[8][3] = "fwm.tab.kommt";
    new String[8][4] = "fwm.tab.spawner";
    new String[8][5] = "fwm.tab.spawnermacro";
    new String[8][6] = "fwm.tab.money";
    new String[8][7] = "fwm.tab.waypoints";
    new [Ljava.lang.String;[3][0] = new String[8];
    new String[7][0] = "Chat";
    new String[7][1] = "fwm.tab.messages";
    new String[7][2] = "fwm.tab.secondchat";
    new String[7][3] = "fwm.tab.colorcodes";
    new String[7][4] = "fwm.tab.members";
    new String[7][5] = "fwm.tab.autotpa";
    new String[7][6] = "fwm.tab.emotes";
    new [Ljava.lang.String;[3][1] = new String[7];
    new String[6][0] = "Werkzeuge";
    new String[6][1] = "fwm.tab.toolguard";
    new String[6][2] = "fwm.tab.rtp";
    new String[6][3] = "fwm.tab.antileak";
    new String[6][4] = "fwm.tab.fightkit";
    new String[6][5] = "fwm.tab.textures";
    new [Ljava.lang.String;[3][2] = new String[6];
    ui.LauncherScreen.GROUPS = new [Ljava.lang.String;[3];
    new String[2][0] = "fwm.tab.extras";
    new String[2][1] = "fwm.tab.settings";
    ui.LauncherScreen.MORE = new String[2];
    new String[3][0] = "Streamer Tools";
    new String[3][1] = "fwm.tab.auction";
    new String[3][2] = "fwm.tab.giveaway";
    ui.LauncherScreen.STREAMER = new String[3];
    return;
}

