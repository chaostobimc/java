// class net/friendswithmoney/fwm/game/SpawnerSidePanel  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
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
field openKind Ljava/lang/String;;
field openLoot Ljava/lang/String;;
field scale F;
field originX I;
field originY I;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static boolean shown(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (config.FwmConfig.INSTANCE.spawnerSidePanel ==0) goto L27;
    if (game.AlertSidePanel.isMarketMenu(screen) !=0) goto L27;
    if (game.SpawnerMacro.isSpawnerMenu(screen) ==0) goto L27;
    goto L28;
    L27:
    L28:
    return 0;
}

private static org.joml.Matrix3x2fStack render(client.Minecraft client, client.gui.screens.inventory.AbstractContainerScreen screen, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    game.SpawnerSidePanel.hits.clear();
    if (game.SpawnerSidePanel.shown(screen) ==0) goto L22;
    if (client.player != null) goto L23;
    L22:
    return;
    L23:
    v5 = game.SpawnerMacro.describe(screen.getMenu());
    game.SpawnerSidePanel.openKind = v5[0];
    game.SpawnerSidePanel.openLoot = v5[1];
    v6 = game.SpawnerSpots.opened();
    if (v6 == null) goto L139;
    if (game.SpawnerSidePanel.openKind == null) goto L139;
    game.SpawnerSpots.info(v6).kind = game.SpawnerSidePanel.openKind;
    v7 = game.SpawnerSpots.spot(client, v6);
    if (v7 == null) goto L139;
    if (game.SpawnerSidePanel.openKind.equals(v7.kind) ==0) goto L120;
    if (game.SpawnerSidePanel.openLoot == null) goto L139;
    if (game.SpawnerSidePanel.openLoot.equals(v7.loot) !=0) goto L139;
    L120:
    v7.kind = game.SpawnerSidePanel.openKind;
    v7.loot = game.SpawnerSidePanel.openLoot;
    config.ConfigManager.save();
    L139:
    v7 = ((mixin.HandledScreenAccessor)screen);
    v8 = (v7.fwm$x() - 10);
    if (v8 >= 70) goto L165;
    return;
    L165:
    v9 = game.SpawnerSidePanel.height(client);
    game.SpawnerSidePanel.scale = Math.min(1, Math.min((((f)v8) / 150.0), (((f)(screen.height - 8)) / ((f)v9))));
    game.SpawnerSidePanel.originX = Math.round((((f)(v7.fwm$x() - 6)) - (150.0 * game.SpawnerSidePanel.scale)));
    game.SpawnerSidePanel.originY = Math.max(4, Math.min(v7.fwm$y(), ((screen.height - 4) - Math.round((((f)v9) * game.SpawnerSidePanel.scale)))));
    v10 = ((i)Math.floor(((d)(((f)(mouseX - game.SpawnerSidePanel.originX)) / game.SpawnerSidePanel.scale))));
    v11 = ((i)Math.floor(((d)(((f)(mouseY - game.SpawnerSidePanel.originY)) / game.SpawnerSidePanel.scale))));
    v12 = ctx.pose();
    v12.pushMatrix();
    v12.translate(((f)game.SpawnerSidePanel.originX), ((f)game.SpawnerSidePanel.originY));
    v12.scale(game.SpawnerSidePanel.scale, game.SpawnerSidePanel.scale);
    game.SpawnerSidePanel.draw(client, ctx, client.font, v9, v10, v11);
    v12.popMatrix();
    return;
}

private static int height(client.Minecraft client) {
    v1 = Math.max(1, Math.min(5, game.SpawnerSpots.here(client).size()));
    return (250 + (v1 * 11));
}

private static void draw(client.Minecraft client, client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int h, int mx, int my) {
    v6 = config.FwmConfig.INSTANCE;
    ui.LauncherGfx.round(ctx, 0, 0, 150, h, 8, -536078574);
    ui.LauncherGfx.card(ctx, 0, 0, 150, h, 8);
    v7 = 8;
    v8 = 134;
    v9 = 8;
    ui.LauncherGfx.icon(ctx, "fwm.tab.spawnermacro", v7, v9, 0.75, -12136081);
    ui.LauncherGfx.bold(ctx, font, ui.Lang.t("Spawner-Makro", "Spawner macro"), (v7 + 16), (v9 + 3), m1);
    v10 = game.SpawnerSidePanel.inside(mx, my, 133, (v9 + 1), 10, 10);
    if (v10 ==0) goto L121;
    goto L123;
    L121:
    L123:
    ui.LauncherGfx.text(font, "\u00d7", 135, (v9 + 2), -1017230, -9464447);
    game.SpawnerSidePanel.hits.add(?);
    v9 = v9 + 18;
    if (config.Features.isOn("fwm.tab.spawnermacro") !=0) goto L229;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("Das Makro ist aus.", "The macro is off."), v7, (v9 + 2), -1017230);
    v9 = v9 + 14;
    game.SpawnerSidePanel.button(ctx, font, v7, v9, v8, 16, ui.Lang.t("Einschalten", "Switch on"), 1, mx, my, /*indy*/run());
    return;
    L229:
    v11 = game.SpawnerMacro.running();
    if (v11 ==0) goto L261;
    goto L270;
    L261:
    L270:
    if (v11 !=0) goto L279;
    goto L280;
    L279:
    L280:
    game.SpawnerSidePanel.button(v7, v9, v8, 16, ui.Lang.t("\u25a0  Stoppen  (J)", "\u25a0  Stop  (J)"), ui.Lang.t("\u25b6  Makro starten", "\u25b6  Start macro"), 1, 0, mx, my, /*indy*/run(client));
    v9 = v9 + 19;
    if (v11 ==0) goto L307;
    goto L316;
    L307:
    L316:
    v12 = ui.Lang.t("bereit", "ready");
    if (v11 ==0) goto L343;
    goto L345;
    L343:
    L345:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(font, v12, v8), v7, v9, -865972, -9464447);
    v9 = v9 + 14;
    game.SpawnerSidePanel.section(ctx, font, v7, v9, ui.Lang.t("DIESER SPAWNER", "THIS SPAWNER"));
    v9 = v9 + 11;
    v13 = game.SpawnerSpots.opened();
    v14 = game.SpawnerSpots.number(client, v13);
    if (v13 != null) goto L415;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("nicht erkannt", "not recognised"), v7, (v9 + 3), -9464447);
    goto L544;
    L415:
    if (v14 <=0) goto L480;
    if (game.SpawnerSidePanel.openKind != null) goto L442;
    goto L445;
    L442:
    L445:
    game.SpawnerSidePanel.tile(font, v7, v9, v8, 14, "Spawner", '✔ ' + game.SpawnerSidePanel.openKind + ' #' + v14, ui.Lang.t("abw\u00e4hlen", "unpick"), 1, mx, my, /*indy*/run(client, v13));
    goto L544;
    L480:
    if (game.SpawnerSidePanel.openKind != null) goto L508;
    goto L511;
    L508:
    L511:
    game.SpawnerSidePanel.tile(font, v7, v9, v8, 14, ui.Lang.t("Spawner", "spawner"), '+ ' + game.SpawnerSidePanel.openKind + ui.Lang.t(" ausw\u00e4hlen", " - pick"), "", 0, mx, my, /*indy*/run(client, v13));
    L544:
    v9 = v9 + 18;
    if (v14 <=0) goto L561;
    goto L562;
    L561:
    L562:
    v15 = null;
    if (v15 == null) goto L573;
    goto L574;
    L573:
    L574:
    v16 = 0;
    if (v16 ==0) goto L606;
    goto L615;
    L606:
    L615:
    game.SpawnerSidePanel.section(font, v7, v9, ui.Lang.t("REGELN #", "RULES #") + v14, ui.Lang.t("REGELN (STANDARD)", "RULES (DEFAULT)"));
    v9 = v9 + 11;
    if (game.SpawnerMacro.dropOnly(game.SpawnerSidePanel.openKind) ==0) goto L787;
    game.SpawnerSidePanel.tile(ctx, font, v7, v9, v8, 14, ui.Lang.t("Modus", "Mode"), ui.Lang.t("Nur droppen", "Drop only"), 0, mx, my, /*indy*/run());
    v9 = v9 + 17;
    if (v16 ==0) goto L688;
    goto L693;
    L688:
    L693:
    v17 = Math.max(v15.dropMin, Math.round((((f)v6.spawnerDropMin) / 2304.0)));
    if (v17 != 1) goto L737;
    goto L740;
    L737:
    L740:
    if (v17 != 1) goto L752;
    goto L755;
    L752:
    L755:
    game.SpawnerSidePanel.stepper(v7, v9, v8, ui.Lang.t("Droppen ab", "Drop from"), v17, " Seite", " Seiten" + ui.Lang.t(" page", " pages"), mx, my, /*indy*/accept(v16, v15, v6));
    v9 = v9 + 20;
    goto L1157;
    L787:
    if (v16 ==0) goto L800;
    goto L805;
    L800:
    L805:
    v17 = v6.spawnerMode;
    if ("drop".equals(v17) ==0) goto L830;
    goto L888;
    L830:
    if ("sell".equals(v17) ==0) goto L853;
    goto L888;
    L853:
    if (game.SpawnerSidePanel.openLoot == null) goto L874;
    goto L883;
    L874:
    L883:
    L888:
    v18 = game.SpawnerSidePanel.openLoot + ui.Lang.t("Knochen", "bones");
    game.SpawnerSidePanel.tile(ctx, font, v7, v9, v8, 14, ui.Lang.t("Modus", "Mode"), v18 + ' ▸', 0, mx, my, /*indy*/run(v16, v15, v6));
    v9 = v9 + 17;
    if (v16 ==0) goto L951;
    goto L956;
    L951:
    L956:
    v19 = v6.spawnerSellFill;
    if (v19 >0) goto L992;
    goto L999;
    L992:
    L999:
    game.SpawnerSidePanel.stepper(font, v7, v9, v8, ui.Lang.t("Verkaufen ab", "Sell at"), ui.Lang.t("nie", "never"), v19 + ' %', mx, my, /*indy*/accept(v16, v15, v6));
    v9 = v9 + 17;
    if (v16 ==0) goto L1034;
    goto L1039;
    L1034:
    L1039:
    v20 = Math.max(v15.dropMin, Math.round((((f)v6.spawnerDropMin) / 2304.0)));
    if ("sell".equals(v17) ==0) goto L1075;
    goto L1084;
    L1075:
    L1084:
    v21 = ui.Lang.t("Droppen ab", "Drop from");
    if (v20 != 1) goto L1110;
    goto L1113;
    L1110:
    L1113:
    if (v20 != 1) goto L1125;
    goto L1128;
    L1125:
    L1128:
    game.SpawnerSidePanel.stepper(v7, v9, v8, v21, v20, " Seite", " Seiten" + ui.Lang.t(" page", " pages"), mx, my, /*indy*/accept(v16, v15, v6));
    v9 = v9 + 20;
    L1157:
    game.SpawnerSidePanel.section(ctx, font, v7, v9, ui.Lang.t("F\u00dcR ALLE", "FOR ALL"));
    v9 = v9 + 11;
    v9 = game.SpawnerSidePanel.toggleRow(ctx, font, v7, v9, v8, ui.Lang.t("Inventar auswerfen", "Empty inventory"), v6.spawnerAutoDrop, mx, my, /*indy*/run(v6));
    v9 = game.SpawnerSidePanel.toggleRow(ctx, font, v7, v9, v8, "Auto-Stop", v6.spawnerAutoStop, mx, my, /*indy*/run(v6));
    v9 = game.SpawnerSidePanel.toggleRow(ctx, font, v7, v9, v8, "Auto-Sell", v6.spawnerAutoSell, mx, my, /*indy*/run(v6));
    v9 = v9 + 3;
    v17 = game.SpawnerSpots.here(client);
    game.SpawnerSidePanel.section(ctx, font, v7, v9, ui.Lang.t("AUSGEW\u00c4HLT (", "PICKED (") + v17.size() + ')');
    if (v17.isEmpty() !=0) goto L1434;
    v18 = ui.Lang.t("leeren", "clear");
    v19 = font.width(v18);
    v20 = game.SpawnerSidePanel.inside(mx, my, (142 - v19), (v9 - 1), v19, 10);
    if (v20 ==0) goto L1393;
    goto L1395;
    L1393:
    L1395:
    ui.LauncherGfx.text(font, v18, (142 - v19), v9, -1017230, -9464447);
    game.SpawnerSidePanel.hits.add(?);
    L1434:
    v9 = v9 + 12;
    v18 = game.SpawnerMacro.current();
    v19 = Math.min(5, v17.size());
    v20 = 0;
    L1458:
    if (v20 >= v19) goto L1661;
    v21 = ((core.BlockPos)v17.get(v20));
    v22 = v21.equals(v18);
    v23 = v21.equals(v13);
    if (v22 ==0) goto L1523;
    goto L1536;
    L1523:
    if (v23 ==0) goto L1533;
    goto L1536;
    L1533:
    L1536:
    ui.LauncherGfx.text('#' + (v20 + 1), v7, v9, -865972, -12136081, -5846860);
    v24 = game.SpawnerSpots.summary(v21);
    v25 = game.SpawnerSpots.spot(client, v21);
    if (v25 == null) goto L1616;
    if (v25.kind == null) goto L1616;
    if (v24.startsWith(v25.kind) !=0) goto L1616;
    if ("\u2013".equals(v24) ==0) goto L1602;
    goto L1609;
    L1602:
    L1609:
    v24 = "" + ' · ' + v24;
    L1616:
    if (v22 ==0) goto L1647;
    goto L1649;
    L1647:
    L1649:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(font, v24, (v8 - 18)), (v7 + 18), v9, -865972, -9464447);
    v9 = v9 + 11;
    v20 = v20 + 1;
    goto L1458;
    L1661:
    if (v17.isEmpty() ==0) goto L1694;
    ui.LauncherGfx.text(ctx, font, ui.Lang.t("noch keiner", "none yet"), v7, v9, -9464447);
    v9 = v9 + 11;
    L1694:
    v9 = v9 + 3;
    game.SpawnerSidePanel.button(ctx, font, v7, v9, v8, 15, ui.Lang.t("+ Alle in Reichweite", "+ All in reach"), 0, mx, my, /*indy*/run(client));
    return;
}

private static int nextFill(int now, int dir) {
    if (now >= 10) goto L19;
    if (dir <=0) goto L15;
    goto L25;
    L15:
    goto L25;
    L19:
    L25:
    v2 = (now + (dir * 10));
    if (v2 <=0) goto L38;
    if (v2 >= 10) goto L38;
    v2 = 0;
    L38:
    return Math.max(0, Math.min(100, v2));
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

private static void section(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, String label) {
    ui.LauncherGfx.text(ctx, font, label, x, y, -9464447);
    return;
}

private static boolean button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, int h, String label, boolean primary, int mx, int my, Runnable action) {
    v11 = game.SpawnerSidePanel.inside(mx, my, x, y, w, h);
    if (primary ==0) goto L43;
    if (v11 !=0) goto L43;
    ui.LauncherGfx.round(ctx, x, y, w, h, 6, -218103809);
    goto L95;
    L43:
    if (v11 ==0) goto L63;
    goto L66;
    L63:
    L66:
    ui.LauncherGfx.round(x, y, w, h, 6, 2048870964, 1544960042);
    if (v11 ==0) goto L89;
    goto L92;
    L89:
    L92:
    ui.LauncherGfx.ring(x, y, w, h, 6, 1717364884, 536870911);
    L95:
    v12 = ui.LauncherGfx.trim(font, label, (w - 8));
    if (primary ==0) goto L150;
    if (v11 !=0) goto L150;
    goto L151;
    L150:
    L151:
    ui.LauncherGfx.text(font, v12, (x + ((w - font.width(v12)) / 2)), (y + ((h - 8) / 2)), -16045545, m1);
    game.SpawnerSidePanel.hits.add(?);
    return;
}

private static boolean tile(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, int h, String label, String value, boolean active, int mx, int my, Runnable action) {
    v12 = game.SpawnerSidePanel.inside(mx, my, x, y, w, h);
    if (v12 ==0) goto L34;
    goto L37;
    L34:
    L37:
    ui.LauncherGfx.round(x, y, w, h, 5, 2048870964, 1544960042);
    if (v12 !=0) goto L58;
    if (active ==0) goto L64;
    L58:
    goto L67;
    L64:
    L67:
    ui.LauncherGfx.ring(x, y, w, h, 5, 1717364884, 536870911);
    if (active ==0) goto L112;
    goto L113;
    L112:
    L113:
    ui.LauncherGfx.text(font, ui.LauncherGfx.trim(font, label, ((w - 12) - font.width(value))), (x + 5), (y + ((h - 8) / 2)), -12136081, m1);
    ui.LauncherGfx.text(ctx, font, value, (((x + w) - 5) - font.width(value)), (y + ((h - 8) / 2)), -5846860);
    game.SpawnerSidePanel.hits.add(?);
    return;
}

private static boolean stepper(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, String label, String value, int mx, int my, function.IntConsumer change) {
    ui.LauncherGfx.text(ctx, font, label, x, (y + 3), -5846860);
    v10 = 58;
    v11 = ((x + w) - v10);
    ui.LauncherGfx.round(ctx, v11, y, v10, 14, 6, 1544960042);
    ui.LauncherGfx.ring(ctx, v11, y, v10, 14, 6, 536870911);
    ui.LauncherGfx.text(ctx, font, value, (v11 + ((v10 - font.width(value)) / 2)), (y + 3), m1);
    v12 = game.SpawnerSidePanel.inside(mx, my, v11, y, 12, 14);
    v13 = game.SpawnerSidePanel.inside(mx, my, ((v11 + v10) - 12), y, 12, 14);
    if (v12 ==0) goto L144;
    goto L146;
    L144:
    L146:
    ui.LauncherGfx.text(font, "\u2039", (v11 + 4), (y + 3), -12136081, -9464447);
    if (v13 ==0) goto L175;
    goto L177;
    L175:
    L177:
    ui.LauncherGfx.text(font, "\u203a", ((v11 + v10) - 8), (y + 3), -12136081, -9464447);
    game.SpawnerSidePanel.hits.add(?);
    game.SpawnerSidePanel.hits.add(?);
    return;
}

private static boolean toggleRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, String label, boolean on, int mx, int my, Runnable toggle) {
    v10 = game.SpawnerSidePanel.inside(mx, my, x, y, w, 13);
    if (v10 ==0) goto L32;
    goto L35;
    L32:
    L35:
    ui.LauncherGfx.text(font, label, x, (y + 2), m1, -5846860);
    ui.LauncherGfx.toggle(ctx, ((x + w) - 22), y, on, -12136081, 1442840575);
    game.SpawnerSidePanel.hits.add(?);
    return (y + 15);
}

private static int click(client.gui.screens.inventory.AbstractContainerScreen screen, double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L23;
    if (game.SpawnerSidePanel.shown(screen) ==0) goto L23;
    if (game.SpawnerSidePanel.hits.isEmpty() ==0) goto L25;
    L23:
    return 0;
    L25:
    v6 = ((i)Math.floor(((mouseX - ((d)game.SpawnerSidePanel.originX)) / ((d)game.SpawnerSidePanel.scale))));
    v7 = ((i)Math.floor(((mouseY - ((d)game.SpawnerSidePanel.originY)) / ((d)game.SpawnerSidePanel.scale))));
    v8 = ?.iterator();
    L74:
    if (v8.hasNext() ==0) goto L141;
    v9 = ((game.SpawnerSidePanel$Hit)v8.next());
    if (game.SpawnerSidePanel.inside(v6, v7, v9.x(), v9.y(), v9.w(), v9.h()) ==0) goto L138;
    v9.action().run();
    return 1;
    L138:
    goto L74;
    L141:
    if (v6 <0) goto L174;
    if (v7 <0) goto L174;
    if (v6 >= 150) goto L174;
    if (v7 >= game.SpawnerSidePanel.height(client.Minecraft.getInstance())) goto L174;
    goto L175;
    L174:
    L175:
    return 0;
}

private static void pickVisible(client.Minecraft client) {
    v1 = client.player;
    if (v1 == null) goto L16;
    if (client.level != null) goto L17;
    L16:
    return;
    L17:
    v2 = v1.blockInteractionRange();
    v4 = v1.getEyePosition();
    v5 = core.BlockPos.containing(v4);
    v6 = ((i)Math.ceil(v2));
    v7 = game.SpawnerSpots.here(client);
    v8 = 0;
    v9 = core.BlockPos.betweenClosed(v5.offset((-v6), (-v6), (-v6)), v5.offset(v6, v6, v6)).iterator();
    L86:
    if (v9.hasNext() ==0) goto L254;
    v10 = ((core.BlockPos)v9.next());
    if (client.level.getBlockState(v10).is(world.level.block.Blocks.SPAWNER) !=0) goto L129;
    goto L86;
    L129:
    v11 = world.phys.Vec3.atCenterOf(v10);
    if (cmp(v4.distanceTo(v11),v2) <=0) goto L151;
    goto L86;
    L151:
    v12 = client.level.clip(?);
    if (v12 == null) goto L86;
    if (v12.getType() != world.phys.HitResult$Type.BLOCK) goto L86;
    if (v12.getBlockPos().equals(v10) !=0) goto L210;
    goto L86;
    L210:
    v13 = v10.immutable();
    if (v7.contains(v13) ==0) goto L232;
    goto L86;
    L232:
    game.SpawnerSpots.toggle(client, v13);
    v7.add(v13);
    v8 = v8 + 1;
    goto L86;
    L254:
    new Object[1][0] = Integer.valueOf(v8);
    v1.sendSystemMessage(network.chat.Component.literal('[FWM] ' + String.format(Locale.ROOT, ui.Lang.t("%d Spawner ausgew\u00e4hlt.", "%d spawners picked."), new Object[1])));
    return;
}

private static void lambda$toggleRow$0(Runnable toggle) {
    toggle.run();
    config.ConfigManager.save();
    return;
}

private static void lambda$stepper$1(function.IntConsumer change) {
    change.accept(1);
    return;
}

private static void lambda$stepper$0(function.IntConsumer change) {
    change.accept(m1);
    return;
}

private static void lambda$draw$14(client.Minecraft client) {
    game.SpawnerSidePanel.pickVisible(client);
    return;
}

private static void lambda$draw$13(client.Minecraft client) {
    game.SpawnerSpots.clearHere(client);
    return;
}

private static void lambda$draw$12(config.FwmConfig config) {
    if (config.spawnerAutoSell !=0) goto L12;
    goto L13;
    L12:
    L13:
    1.spawnerAutoSell = 0;
    return;
}

private static void lambda$draw$11(config.FwmConfig config) {
    if (config.spawnerAutoStop !=0) goto L12;
    goto L13;
    L12:
    L13:
    1.spawnerAutoStop = 0;
    return;
}

private static void lambda$draw$10(config.FwmConfig config) {
    if (config.spawnerAutoDrop !=0) goto L12;
    goto L13;
    L12:
    L13:
    1.spawnerAutoDrop = 0;
    return;
}

private static void lambda$draw$9(boolean own, config.FwmConfig$SpawnerSpot spot, config.FwmConfig config, int d) {
    if (own ==0) goto L12;
    goto L16;
    L12:
    L16:
    v4 = Math.max(spot.dropMin, Math.round((((f)config.spawnerDropMin) / 2304.0)));
    v5 = (Math.max(1, Math.min(50, (v4 + d))) * 2304);
    if (own ==0) goto L61;
    spot.dropMin = v5;
    goto L67;
    L61:
    config.spawnerDropMin = v5;
    L67:
    config.ConfigManager.save();
    return;
}

private static void lambda$draw$8(boolean own, config.FwmConfig$SpawnerSpot spot, config.FwmConfig config, int d) {
    if (own ==0) goto L11;
    goto L15;
    L11:
    L15:
    v4 = game.SpawnerSidePanel.nextFill(config.spawnerSellFill, d);
    if (own ==0) goto L34;
    spot.sellFill = v4;
    goto L40;
    L34:
    config.spawnerSellFill = v4;
    L40:
    config.ConfigManager.save();
    return;
}

private static void lambda$draw$7(boolean own, config.FwmConfig$SpawnerSpot spot, config.FwmConfig config) {
    if (own ==0) goto L11;
    goto L15;
    L11:
    L15:
    v3 = config.spawnerMode;
    if ("bones".equals(v3) ==0) goto L32;
    goto L51;
    L32:
    if ("drop".equals(v3) ==0) goto L48;
    goto L51;
    L48:
    L51:
    v4 = "bones";
    if (own ==0) goto L66;
    spot.mode = v4;
    goto L72;
    L66:
    config.spawnerMode = v4;
    L72:
    config.ConfigManager.save();
    return;
}

private static void lambda$draw$6(boolean own, config.FwmConfig$SpawnerSpot spot, config.FwmConfig config, int d) {
    if (own ==0) goto L12;
    goto L16;
    L12:
    L16:
    v4 = Math.max(spot.dropMin, Math.round((((f)config.spawnerDropMin) / 2304.0)));
    v5 = (Math.max(1, Math.min(50, (v4 + d))) * 2304);
    if (own ==0) goto L61;
    spot.dropMin = v5;
    goto L67;
    L61:
    config.spawnerDropMin = v5;
    L67:
    config.ConfigManager.save();
    return;
}

private static void lambda$draw$5() {
    return;
}

private static void lambda$draw$4(client.Minecraft client, core.BlockPos open) {
    game.SpawnerSpots.toggle(client, open);
    return;
}

private static void lambda$draw$3(client.Minecraft client, core.BlockPos open) {
    game.SpawnerSpots.toggle(client, open);
    return;
}

private static void lambda$draw$2(client.Minecraft client) {
    game.SpawnerMacro.toggle(client);
    return;
}

private static void lambda$draw$1() {
    config.Features.set("fwm.tab.spawnermacro", 1);
    return;
}

private static void lambda$draw$0(config.FwmConfig config) {
    config.spawnerSidePanel = 0;
    config.ConfigManager.save();
    return;
}

private static void lambda$register$0(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L16;
    v4 = ((client.gui.screens.inventory.AbstractContainerScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    fabric.api.client.screen.v1.ScreenEvents.afterExtract(screen).register(/*indy*/afterExtract(client, v4));
    fabric.api.client.screen.v1.ScreenMouseEvents.allowMouseClick(screen).register(/*indy*/allowMouseClick(v4));
    return;
}

private static boolean lambda$register$2(client.gui.screens.inventory.AbstractContainerScreen handled, client.gui.screens.Screen s, client.input.MouseButtonEvent click) {
    if (game.SpawnerSidePanel.click(handled, click.x(), click.y(), util.GLFW.button(click.button())) !=0) goto L26;
    goto L27;
    L26:
    L27:
    return 0;
}

private static void lambda$register$1(client.Minecraft client, client.gui.screens.inventory.AbstractContainerScreen handled, client.gui.screens.Screen s, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    game.SpawnerSidePanel.render(client, handled, ctx, mouseX, mouseY);
    return;
}

static void <clinit>() {
    game.SpawnerSidePanel.hits = ?;
    game.SpawnerSidePanel.scale = 1;
    return;
}

