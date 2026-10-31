// class net/friendswithmoney/fwm/game/SpawnerMacro  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field OPEN_TIMEOUT I;
field AIM_TICKS I;
field EMPTY_BELOW I;
field MAX_DROP_PER_OPEN J;
field FILL Ljava/util/regex/Pattern;;
field ANY_COUNT Ljava/util/regex/Pattern;;
field CANCEL Ljava/util/regex/Pattern;;
field step Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field timer I;
field targets Ljava/util/List;;
field index I;
field tries I;
field dropping Z;
field sold Z;
field droppedThisOpen J;
field lastAmount J;
field reopenCount I;
field anyDrop Z;
field roundHadBones Z;
field aimFromYaw F;
field aimFromPitch F;
field aimToYaw F;
field aimToPitch F;
field aimTick I;
field status Ljava/lang/String;;
field awaitTicks I;
field dropWaitTicks I;
field confirmTicks I;
field throwRounds I;
field INFO_LORE Ljava/util/regex/Pattern;;
field KNOWN Ljava/util/Map;;
field MOB_NAMES Ljava/util/Map;;
field LOOT Ljava/util/Set;;
private void <init>() {
    return;
}

public static boolean running() {
    if (game.SpawnerMacro.step == game.SpawnerMacro$Step.OFF) goto L13;
    goto L14;
    L13:
    L14:
    return 0;
}

public static boolean status() {
    if (game.SpawnerMacro.running() ==0) goto L12;
    goto L14;
    L12:
    L14:
    return "";
}

public static Object current() {
    if (game.SpawnerMacro.running() ==0) goto L37;
    if (game.SpawnerMacro.index >= game.SpawnerMacro.targets.size()) goto L37;
    goto L38;
    L37:
    L38:
    return null;
}

public static void toggle(client.Minecraft client) {
    if (game.SpawnerMacro.running() ==0) goto L20;
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro gestoppt.", "Spawner macro stopped."));
    goto L24;
    L20:
    game.SpawnerMacro.start(client);
    L24:
    return;
}

public static void start(client.Minecraft client) {
    game.SpawnerMacro.LOOT.clear();
    v1 = client.player;
    if (v1 == null) goto L24;
    if (client.level != null) goto L25;
    L24:
    return;
    L25:
    if (config.Features.isOn("fwm.tab.spawnermacro") !=0) goto L48;
    game.SpawnerMacro.say(client, ui.Lang.t("Das Spawner-Makro ist ausgeschaltet (FWM-Men\u00fc).", "The spawner macro is switched off (FWM menu)."), ChatFormatting.RED);
    return;
    L48:
    game.SpawnerMacro.targets.clear();
    v2 = (v1.blockInteractionRange() + 0.5);
    v4 = game.SpawnerSpots.here(client).iterator();
    L76:
    if (v4.hasNext() ==0) goto L129;
    v5 = ((core.BlockPos)v4.next());
    if (cmp(v1.getEyePosition().distanceTo(world.phys.Vec3.atCenterOf(v5)),v2) >0) goto L126;
    game.SpawnerMacro.targets.add(v5);
    L126:
    goto L76;
    L129:
    v4 = game.SpawnerMacro.openMenuSpawner(client);
    if (game.SpawnerMacro.targets.isEmpty() ==0) goto L209;
    if (v4 == null) goto L165;
    game.SpawnerMacro.targets.add(v4);
    goto L209;
    L165:
    v5 = game.SpawnerMacro.lookedAtSpawner(client, null);
    if (v5 != null) goto L192;
    game.SpawnerMacro.say(client, ui.Lang.t("Schau direkt auf einen Spawner oder w\u00e4hle Spawner im Spawner-Men\u00fc aus.", "Look straight at a spawner or pick spawners in the spawner menu."), ChatFormatting.RED);
    return;
    L192:
    game.SpawnerMacro.targets.add(v5.getBlockPos().immutable());
    L209:
    if (v4 != null) goto L219;
    goto L229;
    L219:
    L229:
    game.SpawnerMacro.index = Math.max(0, game.SpawnerMacro.targets.indexOf(v4));
    game.SpawnerMacro.roundHadBones = 0;
    game.SpawnerMacro.beginVisit();
    game.SpawnerMacro.say(client, ui.Lang.t('Spawner-Makro gestartet (' + game.SpawnerMacro.targets.size() + ' Spawner). Stoppen mit J.', 'Spawner macro started (' + game.SpawnerMacro.targets.size() + ' spawners). Stop with J.'), ChatFormatting.GREEN);
    if (v4 == null) goto L314;
    if (v4.equals(game.SpawnerMacro.targets.get(game.SpawnerMacro.index)) ==0) goto L314;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.READ, game.SpawnerMacro.delay());
    goto L318;
    L314:
    game.SpawnerMacro.startAim(client);
    L318:
    return;
}

public static void stop(client.Minecraft client, String reason) {
    if (game.SpawnerMacro.step == game.SpawnerMacro$Step.READ) goto L36;
    if (game.SpawnerMacro.step == game.SpawnerMacro$Step.DROP_WAIT) goto L36;
    if (game.SpawnerMacro.step == game.SpawnerMacro$Step.CONFIRM) goto L36;
    if (game.SpawnerMacro.step != game.SpawnerMacro$Step.AWAIT) goto L40;
    L36:
    goto L41;
    L40:
    L41:
    v2 = 0;
    game.SpawnerMacro.step = game.SpawnerMacro$Step.OFF;
    game.SpawnerMacro.status = "";
    if (v2 ==0) goto L108;
    if (client.player == null) goto L108;
    v4 = client.gui.screen();
    if ((v4 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L108;
    v3 = ((client.gui.screens.inventory.AbstractContainerScreen)v4);
    if (game.SpawnerMacro.isSpawnerMenu(v3) !=0) goto L101;
    if (game.SpawnerMacro.isConfirmMenu(v3) ==0) goto L108;
    L101:
    client.player.closeContainer();
    L108:
    if (reason == null) goto L127;
    if (client.player == null) goto L127;
    game.SpawnerMacro.say(client, reason, ChatFormatting.YELLOW);
    L127:
    return;
}

public static void tick(client.Minecraft client) {
    if (game.SpawnerMacro.step != game.SpawnerMacro$Step.OFF) goto L10;
    return;
    L10:
    v1 = client.player;
    if (v1 == null) goto L33;
    if (client.level == null) goto L33;
    if (client.gameMode != null) goto L40;
    L33:
    game.SpawnerMacro.step = game.SpawnerMacro$Step.OFF;
    return;
    L40:
    if (config.Features.isOn("fwm.tab.spawnermacro") !=0) goto L54;
    game.SpawnerMacro.stop(client, null);
    return;
    L54:
    if (game.SpawnerMacro.step != game.SpawnerMacro$Step.AIM) goto L69;
    game.SpawnerMacro.aim(client, v1);
    return;
    L69:
    if (game.SpawnerMacro.timer <=0) goto L84;
    game.SpawnerMacro.timer = (game.SpawnerMacro.timer - 1);
    return;
    L84:
    // switch (game.SpawnerMacro.step.ordinal()) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=131), Operand(op_type=<OperandTypes.LITERAL: 10>, value=2), Operand(op_type=<OperandTypes.LITERAL: 10>, value=10), Operand(op_type=<OperandTypes.BRANCH: 40>, value=50), Operand(op_type=<OperandTypes.BRANCH: 40>, value=58), Operand(op_type=<OperandTypes.BRANCH: 40>, value=65), Operand(op_type=<OperandTypes.BRANCH: 40>, value=73), Operand(op_type=<OperandTypes.BRANCH: 40>, value=80), Operand(op_type=<OperandTypes.BRANCH: 40>, value=87), Operand(op_type=<OperandTypes.BRANCH: 40>, value=98), Operand(op_type=<OperandTypes.BRANCH: 40>, value=105), Operand(op_type=<OperandTypes.BRANCH: 40>, value=113)]
    L140:
    game.SpawnerMacro.open(client, v1);
    goto L221;
    L148:
    game.SpawnerMacro.await(client);
    goto L221;
    L155:
    game.SpawnerMacro.read(client, v1);
    goto L221;
    L163:
    game.SpawnerMacro.dropWait(client);
    goto L221;
    L170:
    game.SpawnerMacro.confirm(client);
    goto L221;
    L177:
    game.SpawnerMacro.closeMenu(client);
    game.SpawnerMacro.startAim(client);
    goto L221;
    L188:
    game.SpawnerMacro.done(client);
    goto L221;
    L195:
    game.SpawnerMacro.throwItems(client, v1);
    goto L221;
    L203:
    game.SpawnerMacro.index = 0;
    game.SpawnerMacro.roundHadBones = 0;
    game.SpawnerMacro.beginVisit();
    game.SpawnerMacro.startAim(client);
    goto L221;
    L221:
    return;
}

private static void beginVisit() {
    game.SpawnerMacro.tries = 0;
    game.SpawnerMacro.dropping = 0;
    game.SpawnerMacro.sold = 0;
    game.SpawnerMacro.droppedThisOpen = 0;
    game.SpawnerMacro.lastAmount = -1;
    game.SpawnerMacro.reopenCount = 0;
    game.SpawnerMacro.anyDrop = 0;
    return;
}

private static Object target() {
    return ((core.BlockPos)game.SpawnerMacro.targets.get(game.SpawnerMacro.index));
}

private static double startAim(client.Minecraft client) {
    v1 = client.player;
    v2 = game.SpawnerMacro.target();
    game.SpawnerMacro.status = ui.Lang.t("Spawner ", "Spawner ") + (game.SpawnerMacro.index + 1) + '/' + game.SpawnerMacro.targets.size();
    if (client.level.getBlockState(v2).is(world.level.block.Blocks.SPAWNER) ==0) goto L79;
    if (cmp(v1.getEyePosition().distanceTo(world.phys.Vec3.atCenterOf(v2)),(v1.blockInteractionRange() + 0.5)) <=0) goto L100;
    L79:
    game.SpawnerSpots.note(v2, ui.Lang.t("nicht in Reichweite", "out of reach"));
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, 1);
    return;
    L100:
    v3 = world.phys.Vec3.atCenterOf(v2).subtract(v1.getEyePosition());
    game.SpawnerMacro.aimFromYaw = v1.getYRot();
    game.SpawnerMacro.aimFromPitch = v1.getXRot();
    game.SpawnerMacro.aimToYaw = (game.SpawnerMacro.aimFromYaw + util.Mth.wrapDegrees(((((f)((util.Mth.atan2(v3.z, v3.x) * 180.0) / 3.141592653589793)) - 90.0) - game.SpawnerMacro.aimFromYaw)));
    game.SpawnerMacro.aimToPitch = ((f)(-((util.Mth.atan2(v3.y, Math.sqrt(((v3.x * v3.x) + (v3.z * v3.z)))) * 180.0) / 3.141592653589793)));
    game.SpawnerMacro.aimTick = 0;
    game.SpawnerMacro.step = game.SpawnerMacro$Step.AIM;
    return;
}

private static void aim(client.Minecraft client, client.player.LocalPlayer player) {
    if (client.gui.screen() == null) goto L11;
    return;
    L11:
    game.SpawnerMacro.aimTick = (game.SpawnerMacro.aimTick + 1);
    v2 = Math.min(1, (((f)game.SpawnerMacro.aimTick) / 6.0));
    v3 = ((v2 * v2) * (3.0 - (2 * v2)));
    player.setYRot((game.SpawnerMacro.aimFromYaw + ((game.SpawnerMacro.aimToYaw - game.SpawnerMacro.aimFromYaw) * v3)));
    player.setXRot((game.SpawnerMacro.aimFromPitch + ((game.SpawnerMacro.aimToPitch - game.SpawnerMacro.aimFromPitch) * v3)));
    if (game.SpawnerMacro.aimTick < 8) goto L93;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.OPEN, 0);
    L93:
    return;
}

private static void open(client.Minecraft client, client.player.LocalPlayer player) {
    if (client.gui.screen() == null) goto L16;
    game.SpawnerMacro.timer = 10;
    return;
    L16:
    if (player.isShiftKeyDown() ==0) goto L41;
    game.SpawnerMacro.status = ui.Lang.t("nicht schleichen", "stop sneaking");
    game.SpawnerMacro.timer = 10;
    return;
    L41:
    if (player.getMainHandItem().is(world.item.Items.SPAWNER) !=0) goto L67;
    if (player.getOffhandItem().is(world.item.Items.SPAWNER) ==0) goto L81;
    L67:
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro gestoppt: Leg den Spawner aus der Hand.", "Spawner macro stopped: put the spawner out of your hand."));
    return;
    L81:
    v2 = game.SpawnerMacro.lookedAtSpawner(client, game.SpawnerMacro.target());
    if (v2 != null) goto L116;
    game.SpawnerSpots.note(game.SpawnerMacro.target(), ui.Lang.t("nicht sichtbar", "not visible"));
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, 1);
    return;
    L116:
    game.SpawnerMacro.tries = (game.SpawnerMacro.tries + 1);
    if ((game.SpawnerMacro.tries + 1) <= 3) goto L152;
    game.SpawnerSpots.note(game.SpawnerMacro.target(), ui.Lang.t("\u00f6ffnet nicht", "does not open"));
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, 1);
    return;
    L152:
    client.gameMode.useItemOn(player, world.InteractionHand.MAIN_HAND, v2);
    player.swing(world.InteractionHand.MAIN_HAND, world.item.component.SwingAnimation.DEFAULT, 0);
    game.SpawnerMacro.go(game.SpawnerMacro$Step.AWAIT, 0);
    game.SpawnerMacro.awaitTicks = 60;
    return;
}

private static void await(client.Minecraft client) {
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L43;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    if (game.SpawnerMacro.isSpawnerMenu(v1) ==0) goto L43;
    game.SpawnerMacro.droppedThisOpen = 0;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.READ, (game.SpawnerMacro.delay() + 3));
    return;
    L43:
    game.SpawnerMacro.awaitTicks = (game.SpawnerMacro.awaitTicks - 1);
    if ((game.SpawnerMacro.awaitTicks - 1) >0) goto L59;
    game.SpawnerMacro.startAim(client);
    L59:
    return;
}

private static void read(client.Minecraft client, client.player.LocalPlayer player) {
    v3 = client.gui.screen();
    if ((v3 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L27;
    v2 = ((client.gui.screens.inventory.AbstractContainerScreen)v3);
    if (game.SpawnerMacro.isSpawnerMenu(v2) !=0) goto L41;
    L27:
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro gestoppt: Men\u00fc geschlossen.", "Spawner macro stopped: menu closed."));
    return;
    L41:
    v3 = v2.getMenu();
    v4 = game.SpawnerMacro.counts(v3);
    game.SpawnerSpots.counts(game.SpawnerMacro.target(), v4.bones, v4.arrows, v4.fill);
    if (cmp(v4.bones,0) <=0) goto L87;
    game.SpawnerMacro.roundHadBones = 1;
    L87:
    v5 = config.FwmConfig.INSTANCE;
    v6 = game.SpawnerSpots.rules(client, game.SpawnerMacro.target());
    v7 = v4.total;
    v9 = game.SpawnerMacro.dropOnly(game.SpawnerMacro.describe(v3)[0]);
    if (v9 ==0) goto L130;
    goto L135;
    L130:
    L135:
    v10 = v6.mode;
    if (v9 ==0) goto L146;
    goto L151;
    L146:
    L151:
    v11 = v6.sellFill;
    if ("bones".equals(v10) ==0) goto L231;
    if (game.SpawnerMacro.dropping !=0) goto L195;
    if (cmp(v4.bones,((l)Math.max(v6.dropMin, 2304))) <=0) goto L195;
    game.SpawnerMacro.dropping = 1;
    L195:
    if (game.SpawnerMacro.dropping ==0) goto L225;
    if (cmp(v4.bones,2304) <0) goto L225;
    if (v4.pageOnlyBones ==0) goto L225;
    goto L226;
    L225:
    L226:
    v12 = 0;
    goto L292;
    L231:
    if ("drop".equals(v10) ==0) goto L289;
    if (game.SpawnerMacro.dropping !=0) goto L264;
    if (cmp(v7,((l)v6.dropMin)) <0) goto L264;
    game.SpawnerMacro.dropping = 1;
    L264:
    if (game.SpawnerMacro.dropping ==0) goto L283;
    if (cmp(v7,100) <0) goto L283;
    goto L284;
    L283:
    L284:
    v12 = 0;
    goto L292;
    L289:
    v12 = 0;
    L292:
    if (v12 ==0) goto L388;
    if (cmp(game.SpawnerMacro.droppedThisOpen,200000) >=0) goto L388;
    v13 = game.SpawnerMacro.button(v3, "droppen");
    if (v13 == null) goto L388;
    if ("bones".equals(v10) ==0) goto L340;
    goto L342;
    L340:
    L342:
    game.SpawnerMacro.lastAmount = v7;
    game.SpawnerMacro.click(client, v3, v13.index, 0, world.inventory.ContainerInput.PICKUP);
    game.SpawnerMacro.anyDrop = 1;
    game.SpawnerMacro.status = ui.Lang.t("droppe Seite", "dropping page");
    game.SpawnerMacro.dropWaitTicks = 50;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DROP_WAIT, 3);
    return;
    L388:
    v13 = "sell".equals(v10);
    if (cmp(v4.bones,2304) <0) goto L414;
    goto L415;
    L414:
    L415:
    v14 = 0;
    if (v11 <=0) goto L436;
    if (v4.fill < v11) goto L436;
    goto L437;
    L436:
    L437:
    v15 = 0;
    if (v13 ==0) goto L460;
    if (cmp(v7,((l)v6.dropMin)) <0) goto L460;
    goto L461;
    L460:
    L461:
    v16 = 0;
    if (v5.spawnerAutoSell ==0) goto L495;
    if (cmp(v4.bones,0) !=0) goto L495;
    if (cmp(v4.arrows,0) <=0) goto L495;
    goto L496;
    L495:
    L496:
    v17 = 0;
    if (v9 !=0) goto L598;
    if (game.SpawnerMacro.sold !=0) goto L598;
    if (v12 !=0) goto L598;
    if (v13 !=0) goto L524;
    if (v14 !=0) goto L598;
    L524:
    if (v15 !=0) goto L539;
    if (v16 !=0) goto L539;
    if (v17 ==0) goto L598;
    L539:
    v18 = game.SpawnerMacro.button(v3, "verkaufen");
    if (v18 == null) goto L598;
    game.SpawnerMacro.sold = 1;
    game.SpawnerMacro.click(client, v3, v18.index, 0, world.inventory.ContainerInput.PICKUP);
    game.SpawnerMacro.status = ui.Lang.t("verkaufe", "selling");
    game.SpawnerMacro.confirmTicks = 60;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.CONFIRM, game.SpawnerMacro.delay());
    return;
    L598:
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, game.SpawnerMacro.delay());
    return;
}

private static void dropWait(client.Minecraft client) {
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L27;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    if (game.SpawnerMacro.isSpawnerMenu(v1) !=0) goto L57;
    L27:
    game.SpawnerMacro.reopenCount = (game.SpawnerMacro.reopenCount + 1);
    if ((game.SpawnerMacro.reopenCount + 1) <= 3) goto L48;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, 1);
    return;
    L48:
    game.SpawnerMacro.go(game.SpawnerMacro$Step.REOPEN, 20);
    return;
    L57:
    v2 = game.SpawnerMacro.counts(v1.getMenu());
    v3 = game.SpawnerSpots.rules(client, game.SpawnerMacro.target());
    if (game.SpawnerMacro.dropOnly(game.SpawnerMacro.describe(v1.getMenu())[0]) ==0) goto L94;
    goto L98;
    L94:
    L98:
    v4 = v3.mode;
    if ("bones".equals(v4) ==0) goto L118;
    goto L122;
    L118:
    L122:
    v5 = v2.total;
    if (cmp(v5,0) <0) goto L171;
    if (cmp(game.SpawnerMacro.lastAmount,0) <0) goto L171;
    if (cmp(v5,game.SpawnerMacro.lastAmount) >=0) goto L171;
    game.SpawnerMacro.droppedThisOpen = (game.SpawnerMacro.droppedThisOpen + (game.SpawnerMacro.lastAmount - v5));
    game.SpawnerMacro.go(game.SpawnerMacro$Step.READ, game.SpawnerMacro.delay());
    return;
    L171:
    game.SpawnerMacro.dropWaitTicks = (game.SpawnerMacro.dropWaitTicks - 1);
    if ((game.SpawnerMacro.dropWaitTicks - 1) >0) goto L229;
    game.SpawnerMacro.reopenCount = (game.SpawnerMacro.reopenCount + 1);
    if ((game.SpawnerMacro.reopenCount + 1) <= 2) goto L221;
    game.SpawnerSpots.note(game.SpawnerMacro.target(), ui.Lang.t("Anzahl sinkt nicht", "count not dropping"));
    game.SpawnerMacro.go(game.SpawnerMacro$Step.DONE, 1);
    goto L229;
    L221:
    game.SpawnerMacro.go(game.SpawnerMacro$Step.REOPEN, 10);
    L229:
    return;
}

private static void confirm(client.Minecraft client) {
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L191;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    if (game.SpawnerMacro.isConfirmMenu(v1) ==0) goto L191;
    v2 = v1.getMenu();
    v3 = game.SpawnerMacro.button(v2, "best\u00e4tig");
    if (v3 != null) goto L52;
    v3 = game.SpawnerMacro.button(v2, "bestaetig");
    L52:
    if (v3 != null) goto L121;
    if (v2.slots.size() <= 7) goto L121;
    v4 = ((world.inventory.Slot)v2.slots.get(7));
    if (v4.getItem().isEmpty() !=0) goto L121;
    if (game.SpawnerMacro.CANCEL.matcher(v4.getItem().getHoverName().getString()).find() !=0) goto L121;
    v3 = v4;
    L121:
    if (v3 != null) goto L139;
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro gestoppt: Best\u00e4tigen-Feld nicht gefunden.", "Spawner macro stopped: confirm field not found."));
    return;
    L139:
    game.SpawnerMacro.click(client, v2, v3.index, 0, world.inventory.ContainerInput.PICKUP);
    game.SpawnerMacro.say(client, ui.Lang.t('Spawner ' + (game.SpawnerMacro.index + 1) + ': alles verkauft.', 'Spawner ' + (game.SpawnerMacro.index + 1) + ': sold everything.'), ChatFormatting.GREEN);
    game.SpawnerMacro.go(game.SpawnerMacro$Step.REOPEN, 25);
    return;
    L191:
    game.SpawnerMacro.confirmTicks = (game.SpawnerMacro.confirmTicks - 1);
    if ((game.SpawnerMacro.confirmTicks - 1) >0) goto L216;
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro gestoppt: Verkauf nicht best\u00e4tigt.", "Spawner macro stopped: sale not confirmed."));
    L216:
    return;
}

private static void done(client.Minecraft client) {
    game.SpawnerMacro.closeMenu(client);
    if (config.FwmConfig.INSTANCE.spawnerAutoDrop ==0) goto L26;
    game.SpawnerMacro.throwRounds = 0;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.THROW, 30);
    return;
    L26:
    game.SpawnerMacro.next(client);
    return;
}

private static void throwItems(client.Minecraft client, client.player.LocalPlayer player) {
    if (client.gui.screen() == null) goto L16;
    game.SpawnerMacro.timer = 10;
    return;
    L16:
    v2 = player.inventoryMenu;
    v3 = v2.slots.iterator();
    L29:
    if (v3.hasNext() ==0) goto L161;
    v4 = ((world.inventory.Slot)v3.next());
    if ((v4.container instanceof world.entity.player.Inventory) ==0) goto L29;
    if (v4.getContainerSlot() < 36) goto L73;
    goto L29;
    L73:
    v5 = v4.getItem();
    if (v5.is(world.item.Items.BONE) !=0) goto L118;
    if (v5.is(world.item.Items.ARROW) !=0) goto L118;
    if (game.SpawnerMacro.LOOT.contains(v5.getItem()) ==0) goto L158;
    L118:
    client.gameMode.handleContainerInput(v2.containerId, v4.index, 1, world.inventory.ContainerInput.THROW, player);
    game.SpawnerMacro.status = ui.Lang.t("werfe Inventar aus", "emptying inventory");
    game.SpawnerMacro.timer = game.SpawnerMacro.delay();
    return;
    L158:
    goto L29;
    L161:
    game.SpawnerMacro.throwRounds = (game.SpawnerMacro.throwRounds + 1);
    if ((game.SpawnerMacro.throwRounds + 1) >= 2) goto L180;
    game.SpawnerMacro.timer = 30;
    return;
    L180:
    game.SpawnerMacro.next(client);
    return;
}

private static void next(client.Minecraft client) {
    game.SpawnerMacro.index = (game.SpawnerMacro.index + 1);
    game.SpawnerMacro.beginVisit();
    if (game.SpawnerMacro.index >= game.SpawnerMacro.targets.size()) goto L34;
    game.SpawnerMacro.go(game.SpawnerMacro$Step.REOPEN, 10);
    return;
    L34:
    if (config.FwmConfig.INSTANCE.spawnerAutoStop ==0) goto L63;
    if (game.SpawnerMacro.roundHadBones !=0) goto L63;
    game.SpawnerMacro.stop(client, ui.Lang.t("Spawner-Makro fertig: in keinem Spawner mehr Haupt-Loot (Knochen, Lohenstaub, Eisen ...).", "Spawner macro done: no main loot left in any spawner."));
    return;
    L63:
    game.SpawnerMacro.index = 0;
    game.SpawnerMacro.status = ui.Lang.t("n\u00e4chste Runde in ", "next round in ") + config.FwmConfig.INSTANCE.spawnerRepeatSeconds + ' s';
    game.SpawnerMacro.go(game.SpawnerMacro$Step.ROUND_WAIT, (config.FwmConfig.INSTANCE.spawnerRepeatSeconds * 20));
    return;
}

static boolean lookedAtSpawner(client.Minecraft client, core.BlockPos want) {
    v2 = client.player;
    if (v2 == null) goto L16;
    if (client.level != null) goto L18;
    L16:
    return null;
    L18:
    v3 = v2.blockInteractionRange();
    v5 = v2.getEyePosition();
    v6 = v5.add(v2.getViewVector(1).scale(v3));
    v7 = client.level.clip(?);
    if (v7 == null) goto L88;
    if (v7.getType() == world.phys.HitResult$Type.BLOCK) goto L90;
    L88:
    return null;
    L90:
    v8 = v7.getBlockPos();
    if (client.level.getBlockState(v8).is(world.level.block.Blocks.SPAWNER) !=0) goto L117;
    return null;
    L117:
    if (want == null) goto L132;
    if (want.equals(v8) !=0) goto L132;
    return null;
    L132:
    if (cmp(v5.distanceTo(v7.getLocation()),v3) <=0) goto L149;
    return null;
    L149:
    v10 = client.hitResult;
    if ((v10 instanceof world.phys.BlockHitResult) ==0) goto L194;
    v9 = ((world.phys.BlockHitResult)v10);
    if (v9.getType() != world.phys.HitResult$Type.BLOCK) goto L194;
    if (v9.getBlockPos().equals(v8) !=0) goto L196;
    L194:
    return null;
    L196:
    return v7;
}

private static core.BlockPos openMenuSpawner(client.Minecraft client) {
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L31;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    if (game.SpawnerMacro.isSpawnerMenu(v1) ==0) goto L31;
    return game.SpawnerSpots.opened();
    L31:
    return null;
}

public static Object isSpawnerMenu(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (game.SpawnerMacro.KNOWN.containsKey(screen) ==0) goto L14;
    return 1;
    L14:
    if (game.SpawnerMacro.detect(screen) ==0) goto L36;
    game.SpawnerMacro.KNOWN.put(screen, Boolean.TRUE);
    return 1;
    L36:
    return 0;
}

private static int detect(client.gui.screens.inventory.AbstractContainerScreen screen) {
    if (screen.getTitle().getString().toLowerCase(Locale.ROOT).contains("spawner") ==0) goto L26;
    return 1;
    L26:
    if (game.SpawnerMacro.isConfirmMenu(screen) ==0) goto L35;
    return 0;
    L35:
    v1 = screen.getMenu().slots.iterator();
    L46:
    if (v1.hasNext() ==0) goto L159;
    v2 = ((world.inventory.Slot)v1.next());
    if ((v2.container instanceof world.entity.player.Inventory) ==0) goto L78;
    goto L46;
    L78:
    v3 = ((world.item.component.ItemLore)v2.getItem().get(core.component.DataComponents.LORE));
    if (v3 != null) goto L99;
    goto L46;
    L99:
    v4 = v3.lines().iterator();
    L110:
    if (v4.hasNext() ==0) goto L156;
    v5 = ((network.chat.Component)v4.next());
    if (game.SpawnerMacro.INFO_LORE.matcher(v5.getString()).find() ==0) goto L153;
    return 1;
    L153:
    goto L110;
    L156:
    goto L46;
    L159:
    if (game.SpawnerSpots.openedJustNow() ==0) goto L184;
    if (screen.getMenu().slots.size() <= 36) goto L184;
    goto L185;
    L184:
    L185:
    return 0;
}

static world.inventory.Slot isConfirmMenu(client.gui.screens.inventory.AbstractContainerScreen screen) {
    v1 = screen.getTitle().getString().toLowerCase(Locale.ROOT);
    if (v1.contains("best\u00e4tig") !=0) goto L46;
    if (v1.contains("bestaetig") !=0) goto L46;
    if (v1.contains("confirm") ==0) goto L48;
    L46:
    return 1;
    L48:
    if (game.SpawnerMacro.button(screen.getMenu(), "best\u00e4tig") != null) goto L74;
    if (game.SpawnerMacro.button(screen.getMenu(), "bestaetig") == null) goto L78;
    L74:
    goto L79;
    L78:
    L79:
    return 0;
}

public static boolean dropOnly(String kind) {
    if (kind == null) goto L18;
    if ("Skelett".equals(kind) !=0) goto L18;
    goto L19;
    L18:
    L19:
    return 0;
}

public static Object describe(world.inventory.AbstractContainerMenu menu) {
    v1 = menu.slots.iterator();
    L8:
    if (v1.hasNext() ==0) goto L284;
    v2 = ((world.inventory.Slot)v1.next());
    if ((v2.container instanceof world.entity.player.Inventory) ==0) goto L40;
    goto L8;
    L40:
    v3 = v2.getItem();
    v4 = ((world.item.component.ItemLore)v3.get(core.component.DataComponents.LORE));
    if (v4 != null) goto L65;
    goto L8;
    L65:
    v5 = null;
    v6 = 0;
    v7 = v4.lines().iterator();
    L83:
    if (v7.hasNext() ==0) goto L168;
    v8 = ((network.chat.Component)v7.next());
    v9 = v8.getString();
    if (game.SpawnerMacro.FILL.matcher(v9).find() ==0) goto L131;
    v6 = 1;
    L131:
    v10 = game.SpawnerMacro.ANY_COUNT.matcher(v9);
    if (v5 != null) goto L165;
    if (v10.find() ==0) goto L165;
    v5 = v10.group(1).trim();
    L165:
    goto L83;
    L168:
    if (v6 !=0) goto L176;
    goto L8;
    L176:
    v7 = core.registries.BuiltInRegistries.ITEM.getKey(v3.getItem()).getPath();
    v8 = null;
    if (v7.endsWith("_spawn_egg") ==0) goto L269;
    v9 = v7.substring(0, (v7.length() - "_spawn_egg".length()));
    v8 = ((String)game.SpawnerMacro.MOB_NAMES.getOrDefault(v9, Character.toUpperCase(v9.charAt(0)) + v9.substring(1).replace(95, 32)));
    L269:
    new String[2][0] = v8;
    new String[2][1] = v5;
    return new String[2];
    L284:
    new String[2][0] = null;
    new String[2][1] = null;
    return new String[2];
}

private static void counts(world.inventory.AbstractContainerMenu menu) {
    v1 = -1;
    v3 = -1;
    v5 = -1;
    v7 = m1;
    v8 = menu.slots.iterator();
    L25:
    if (v8.hasNext() ==0) goto L294;
    v9 = ((world.inventory.Slot)v8.next());
    if ((v9.container instanceof world.entity.player.Inventory) ==0) goto L61;
    goto L25;
    L61:
    v10 = ((world.item.component.ItemLore)v9.getItem().get(core.component.DataComponents.LORE));
    if (v10 != null) goto L85;
    goto L25;
    L85:
    v11 = 0;
    v12 = 0;
    v14 = 0;
    v15 = -1;
    v17 = -1;
    v19 = v10.lines().iterator();
    L116:
    if (v19.hasNext() ==0) goto L257;
    v20 = ((network.chat.Component)v19.next());
    v21 = v20.getString();
    v22 = game.SpawnerMacro.FILL.matcher(v21);
    if (v22.find() ==0) goto L193;
    v7 = ((i)Math.round(Double.parseDouble(v22.group(1).replace(44, 46))));
    v11 = 1;
    goto L116;
    L193:
    v23 = game.SpawnerMacro.ANY_COUNT.matcher(v21);
    if (v23.find() ==0) goto L254;
    v24 = game.SpawnerMacro.number(v23.group(2));
    v12 = (v12 + v24);
    if (v14 !=0) goto L241;
    v15 = v24;
    goto L251;
    L241:
    if (v14 != 1) goto L251;
    v17 = v24;
    L251:
    v14 = v14 + 1;
    L254:
    goto L116;
    L257:
    if (v11 ==0) goto L291;
    if (v14 <=0) goto L291;
    v1 = v15;
    if (cmp(v17,0) >=0) goto L281;
    goto L283;
    L281:
    L283:
    v3 = v17;
    v5 = v12;
    goto L294;
    L291:
    goto L25;
    L294:
    v8 = 1;
    v9 = 0;
    v10 = null;
    v11 = 0;
    v13 = menu.slots.iterator();
    L315:
    if (v13.hasNext() ==0) goto L438;
    v14 = ((world.inventory.Slot)v13.next());
    if ((v14.container instanceof world.entity.player.Inventory) !=0) goto L315;
    if (v14.index < 36) goto L361;
    goto L315;
    L361:
    v15 = v14.getItem();
    if (v15.isEmpty() ==0) goto L379;
    goto L315;
    L379:
    v9 = 1;
    game.SpawnerMacro.LOOT.add(v15.getItem());
    if (v10 != null) goto L408;
    v10 = v15.getItem();
    L408:
    if (v15.is(v10) ==0) goto L432;
    v11 = (v11 + ((l)v15.getCount()));
    goto L435;
    L432:
    v8 = 0;
    L435:
    goto L315;
    L438:
    if (cmp(v1,0) >=0) goto L447;
    v1 = v11;
    L447:
    if (cmp(v3,0) >=0) goto L455;
    v3 = 0;
    L455:
    if (cmp(v5,0) >=0) goto L467;
    v5 = (v1 + v3);
    L467:
    if (v9 ==0) goto L491;
    if (v8 ==0) goto L491;
    goto L492;
    L491:
    L492:
    return ?;
}

private static long number(String digits) {
    return (auction.Money.cents(digits) / 100);
}

private static boolean button(world.inventory.AbstractContainerMenu menu, String word) {
    v2 = menu.slots.iterator();
    L8:
    if (v2.hasNext() ==0) goto L85;
    v3 = ((world.inventory.Slot)v2.next());
    if ((v3.container instanceof world.entity.player.Inventory) ==0) goto L40;
    goto L8;
    L40:
    v4 = v3.getItem();
    if (v4.isEmpty() ==0) goto L57;
    goto L8;
    L57:
    if (v4.getHoverName().getString().toLowerCase(Locale.ROOT).contains(word) ==0) goto L82;
    return v3;
    L82:
    goto L8;
    L85:
    return null;
}

private static void click(client.Minecraft client, world.inventory.AbstractContainerMenu menu, int slot, int button, world.inventory.ContainerInput action) {
    client.gameMode.handleContainerInput(menu.containerId, slot, button, action, client.player);
    return;
}

private static void closeMenu(client.Minecraft client) {
    if (client.player == null) goto L48;
    v2 = client.gui.screen();
    if ((v2 instanceof client.gui.screens.inventory.AbstractContainerScreen) ==0) goto L48;
    v1 = ((client.gui.screens.inventory.AbstractContainerScreen)v2);
    if (game.SpawnerMacro.isSpawnerMenu(v1) !=0) goto L41;
    if (game.SpawnerMacro.isConfirmMenu(v1) ==0) goto L48;
    L41:
    client.player.closeContainer();
    L48:
    return;
}

private static void go(game.SpawnerMacro$Step next, int ticks) {
    game.SpawnerMacro.step = next;
    game.SpawnerMacro.timer = ticks;
    return;
}

private static int delay() {
    return config.FwmConfig.INSTANCE.spawnerClickDelayTicks;
}

private static void say(client.Minecraft client, String text, ChatFormatting color) {
    if (client.player != null) goto L8;
    return;
    L8:
    client.player.sendSystemMessage(network.chat.Component.literal("[FWM] ").withStyle(ChatFormatting.GREEN).append(network.chat.Component.literal(text).withStyle(color)));
    return;
}

static void <clinit>() {
    game.SpawnerMacro.FILL = regex.Pattern.compile("Auslastung:\\s*([0-9][0-9.,]*)\\s*%", 2);
    game.SpawnerMacro.ANY_COUNT = regex.Pattern.compile("^\\s*([^:%]+):\\s*([0-9][0-9.,]*)\\s*$");
    game.SpawnerMacro.CANCEL = regex.Pattern.compile("abbrech|cancel|zur(\u00fc|ue)ck|nein|schlie(\u00df|ss)en", 2);
    game.SpawnerMacro.step = game.SpawnerMacro$Step.OFF;
    game.SpawnerMacro.targets = ?;
    game.SpawnerMacro.status = "";
    game.SpawnerMacro.INFO_LORE = regex.Pattern.compile("Knochen:|Pfeile:|Auslastung", 2);
    game.SpawnerMacro.KNOWN = ?;
    new Map$Entry[23][0] = Map.entry("skeleton", "Skelett");
    new Map$Entry[23][1] = Map.entry("blaze", "Blaze");
    new Map$Entry[23][2] = Map.entry("iron_golem", "Eisengolem");
    new Map$Entry[23][3] = Map.entry("creeper", "Creeper");
    new Map$Entry[23][4] = Map.entry("zombie", "Zombie");
    new Map$Entry[23][5] = Map.entry("spider", "Spinne");
    new Map$Entry[23][6] = Map.entry("cave_spider", "H\u00f6hlenspinne");
    new Map$Entry[23][7] = Map.entry("pig", "Schwein");
    new Map$Entry[23][8] = Map.entry("cow", "Kuh");
    new Map$Entry[23][9] = Map.entry("chicken", "Huhn");
    new Map$Entry[23][10] = Map.entry("sheep", "Schaf");
    new Map$Entry[23][11] = Map.entry("zombified_piglin", "Zombie-Piglin");
    new Map$Entry[23][12] = Map.entry("enderman", "Enderman");
    new Map$Entry[23][13] = Map.entry("witch", "Hexe");
    new Map$Entry[23][14] = Map.entry("slime", "Schleim");
    new Map$Entry[23][15] = Map.entry("magma_cube", "Magmaw\u00fcrfel");
    new Map$Entry[23][16] = Map.entry("wither_skeleton", "Witherskelett");
    new Map$Entry[23][17] = Map.entry("squid", "Tintenfisch");
    new Map$Entry[23][18] = Map.entry("rabbit", "Hase");
    new Map$Entry[23][19] = Map.entry("guardian", "W\u00e4chter");
    new Map$Entry[23][20] = Map.entry("drowned", "Ertrunkener");
    new Map$Entry[23][21] = Map.entry("husk", "W\u00fcstenzombie");
    new Map$Entry[23][22] = Map.entry("stray", "Eiswanderer");
    game.SpawnerMacro.MOB_NAMES = Map.ofEntries(new Map$Entry[23]);
    game.SpawnerMacro.LOOT = ?;
    return;
}

