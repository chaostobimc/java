// class net/friendswithmoney/fwm/game/SpawnerSpots  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field LABEL_RADIUS D;
field INFO Ljava/util/Map;;
field lastOpened Lnet/minecraft/core/BlockPos;;
field lastOpenedAt J;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.event.player.UseBlockCallback.EVENT.register(/*indy*/interact());
    fabric.api.client.rendering.v1.level.LevelRenderEvents.COLLECT_SUBMITS.register(/*indy*/collectSubmits());
    return;
}

public static long openedJustNow() {
    if (game.SpawnerSpots.lastOpened == null) goto L24;
    if (cmp((System.currentTimeMillis() - game.SpawnerSpots.lastOpenedAt),3000) >=0) goto L24;
    goto L25;
    L24:
    L25:
    return 0;
}

public static long opened() {
    if (game.SpawnerSpots.lastOpened == null) goto L26;
    if (cmp((System.currentTimeMillis() - game.SpawnerSpots.lastOpenedAt),60000) >=0) goto L26;
    goto L27;
    L26:
    L27:
    return null;
}

static String server(client.Minecraft client) {
    if (client.isLocalServer() ==0) goto L10;
    return "singleplayer";
    L10:
    v1 = client.getCurrentServer();
    if (v1 == null) goto L26;
    if (v1.ip != null) goto L31;
    L26:
    goto L41;
    L31:
    L41:
    return v1.ip.toLowerCase(Locale.ROOT);
}

static String dim(client.Minecraft client) {
    if (client.level != null) goto L12;
    goto L22;
    L12:
    L22:
    return net.RtpPoints$Dim.of(client.level).key();
}

public static boolean here(client.Minecraft client) {
    v1 = ?;
    v2 = game.SpawnerSpots.server(client);
    v3 = game.SpawnerSpots.dim(client);
    v4 = config.FwmConfig.INSTANCE.spawnerSpots.iterator();
    L31:
    if (v4.hasNext() ==0) goto L114;
    v5 = ((config.FwmConfig$SpawnerSpot)v4.next());
    if (v5 == null) goto L111;
    if (v2.equals(v5.server) ==0) goto L111;
    if (v3.equals(v5.dim) ==0) goto L111;
    v1.add(?);
    L111:
    goto L31;
    L114:
    return v1;
}

public static boolean spot(client.Minecraft client, core.BlockPos pos) {
    if (pos != null) goto L6;
    return null;
    L6:
    v2 = game.SpawnerSpots.server(client);
    v3 = game.SpawnerSpots.dim(client);
    v4 = config.FwmConfig.INSTANCE.spawnerSpots.iterator();
    L29:
    if (v4.hasNext() ==0) goto L122;
    v5 = ((config.FwmConfig$SpawnerSpot)v4.next());
    if (v5 == null) goto L119;
    if (v5.x != pos.getX()) goto L119;
    if (v5.y != pos.getY()) goto L119;
    if (v5.z != pos.getZ()) goto L119;
    if (v2.equals(v5.server) ==0) goto L119;
    if (v3.equals(v5.dim) ==0) goto L119;
    return v5;
    L119:
    goto L29;
    L122:
    return null;
}

public static void rules(client.Minecraft client, core.BlockPos pos) {
    v2 = game.SpawnerSpots.spot(client, pos);
    if (v2 == null) goto L12;
    return v2;
    L12:
    v3 = ?;
    v3.mode = config.FwmConfig.INSTANCE.spawnerMode;
    v3.sellFill = config.FwmConfig.INSTANCE.spawnerSellFill;
    v3.dropMin = config.FwmConfig.INSTANCE.spawnerDropMin;
    return v3;
}

public static boolean selected(client.Minecraft client, core.BlockPos pos) {
    if (pos == null) goto L21;
    if (game.SpawnerSpots.here(client).contains(pos) ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public static void toggle(client.Minecraft client, core.BlockPos pos) {
    if (pos != null) goto L5;
    return;
    L5:
    v2 = game.SpawnerSpots.server(client);
    v3 = game.SpawnerSpots.dim(client);
    v4 = config.FwmConfig.INSTANCE.spawnerSpots.removeIf(/*indy*/test(pos, v2, v3));
    if (v4 !=0) goto L111;
    v5 = ?;
    v5.mode = config.FwmConfig.INSTANCE.spawnerMode;
    v5.sellFill = config.FwmConfig.INSTANCE.spawnerSellFill;
    v5.dropMin = config.FwmConfig.INSTANCE.spawnerDropMin;
    config.FwmConfig.INSTANCE.spawnerSpots.add(v5);
    L111:
    game.SpawnerSpots.INFO.remove(pos);
    config.ConfigManager.save();
    return;
}

public static void clearHere(client.Minecraft client) {
    v1 = game.SpawnerSpots.server(client);
    v2 = game.SpawnerSpots.dim(client);
    config.FwmConfig.INSTANCE.spawnerSpots.removeIf(/*indy*/test(v1, v2));
    game.SpawnerSpots.INFO.clear();
    config.ConfigManager.save();
    return;
}

public static int number(client.Minecraft client, core.BlockPos pos) {
    return (game.SpawnerSpots.here(client).indexOf(pos) + 1);
}

public static Object info(core.BlockPos pos) {
    return ((game.SpawnerSpots$Info)game.SpawnerSpots.INFO.computeIfAbsent(pos.immutable(), /*indy*/apply()));
}

static game.SpawnerSpots$Info counts(core.BlockPos pos, long bones, long a2, int arrows) {
    v6 = game.SpawnerSpots.info(pos);
    v6.bones = bones;
    v6.arrows = arrows;
    v6.fill = v5;
    v6.note = "";
    return;
}

static game.SpawnerSpots$Info note(core.BlockPos pos, String note) {
    game.SpawnerSpots.info(pos).note = note;
    return;
}

public static String summary(core.BlockPos pos) {
    v1 = ((game.SpawnerSpots$Info)game.SpawnerSpots.INFO.get(pos));
    if (v1 != null) goto L21;
    return "\u2013";
    L21:
    if (v1.note.isEmpty() !=0) goto L36;
    return v1.note;
    L36:
    if (cmp(v1.bones,0) >=0) goto L63;
    if (v1.kind != null) goto L58;
    goto L62;
    L58:
    L62:
    return v1.kind;
    L63:
    if (v1.kind != null) goto L75;
    goto L84;
    L75:
    L84:
    if (cmp(v1.arrows,0) <=0) goto L115;
    goto L117;
    L115:
    L117:
    if (v1.fill <0) goto L136;
    goto L138;
    L136:
    L138:
    return ' · ' + game.SpawnerSpots.group(v1.arrows) + "" + ' · ' + v1.fill + ' %' + "";
}

private static String group(long n) {
    new Object[1][0] = Long.valueOf(Math.max(0, n));
    return String.format(Locale.GERMANY, "%,d", new Object[1]);
}

private static void render(fabric.api.client.rendering.v1.level.LevelRenderContext ctx) {
    if (config.FwmConfig.INSTANCE.spawnerLabels ==0) goto L18;
    if (config.Features.isOn("fwm.tab.spawnermacro") !=0) goto L19;
    L18:
    return;
    L19:
    v1 = client.Minecraft.getInstance();
    if (v1.level == null) goto L37;
    if (v1.player != null) goto L38;
    L37:
    return;
    L38:
    v2 = ctx.poseStack();
    v3 = ctx.submitNodeCollector();
    if (v2 == null) goto L69;
    if (v3 == null) goto L69;
    if (ctx.levelState() != null) goto L70;
    L69:
    return;
    L70:
    v4 = ctx.levelState().cameraRenderState;
    v5 = v1.gameRenderer.mainCamera();
    if (v4 == null) goto L100;
    if (v5 != null) goto L101;
    L100:
    return;
    L101:
    v6 = v5.position();
    v7 = game.SpawnerSpots.here(v1);
    v8 = game.SpawnerMacro.current();
    v9 = 0;
    L122:
    if (v9 >= v7.size()) goto L346;
    v10 = ((core.BlockPos)v7.get(v9));
    v11 = ((((d)v10.getX()) + 0.5) - v6.x);
    v13 = ((((d)v10.getY()) + 1.35) - v6.y);
    v15 = ((((d)v10.getZ()) + 0.5) - v6.z);
    v17 = Math.sqrt((((v11 * v11) + (v13 * v13)) + (v15 * v15)));
    if (cmp(v17,12.0) <=0) goto L236;
    goto L340;
    L236:
    v19 = v10.equals(v8);
    if (v19 ==0) goto L256;
    goto L259;
    L256:
    L259:
    if (v19 ==0) goto L282;
    goto L285;
    L282:
    L285:
    v20 = ChatFormatting.GOLD.withStyle(ChatFormatting.GREEN);
    v2.pushPose();
    v2.translate(v11, v13, v15);
    v2.scale(0.800000011920929, 0.800000011920929, 0.800000011920929);
    v3.submitNameTag(v2, world.phys.Vec3.ZERO, 0, v20, 1, 15728880, v4);
    v2.popPose();
    L340:
    v9 = v9 + 1;
    goto L122;
    L346:
    return;
}

private static void lambda$info$0(core.BlockPos p) {
    return ?;
}

private static boolean lambda$clearHere$0(String server, String dim, config.FwmConfig$SpawnerSpot spot) {
    if (spot == null) goto L26;
    if (server.equals(spot.server) ==0) goto L30;
    if (dim.equals(spot.dim) ==0) goto L30;
    L26:
    goto L31;
    L30:
    L31:
    return 0;
}

private static boolean lambda$toggle$0(core.BlockPos pos, String server, String dim, config.FwmConfig$SpawnerSpot spot) {
    if (spot == null) goto L63;
    if (spot.x != pos.getX()) goto L63;
    if (spot.y != pos.getY()) goto L63;
    if (spot.z != pos.getZ()) goto L63;
    if (server.equals(spot.server) ==0) goto L63;
    if (dim.equals(spot.dim) ==0) goto L63;
    goto L64;
    L63:
    L64:
    return 0;
}

private static long lambda$register$0(world.entity.player.Player player, world.level.Level world, world.InteractionHand hand, world.phys.BlockHitResult hit) {
    if (world.isClientSide() ==0) goto L44;
    if (hit == null) goto L44;
    if (world.getBlockState(hit.getBlockPos()).is(world.level.block.Blocks.SPAWNER) ==0) goto L44;
    game.SpawnerSpots.lastOpened = hit.getBlockPos().immutable();
    game.SpawnerSpots.lastOpenedAt = System.currentTimeMillis();
    L44:
    return world.InteractionResult.PASS;
}

static void <clinit>() {
    game.SpawnerSpots.INFO = ?;
    return;
}

