// class net/friendswithmoney/fwm/ui/MemberTags  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field BADGE_FONT Lnet/minecraft/network/chat/FontDescription;;
field LOGO_GLYPH Ljava/lang/String;;
field ANCHOR Lnet/minecraft/world/phys/Vec3;;
field NAME_LIFT D;
field LINE_H D;
field FULL_BRIGHT I;
field EXTRA_LINE D;
field HEIGHT D;
field SCALE F;
field BELOW_NAME_LINE D;
field BELOW_NAME_RANGE_SQ D;
field STACKS Ljava/util/Map;;
field RESCAN_MS J;
field WARNS Ljava/util/Map;;
field WARN_MS J;
field BRACKET_TAG Ljava/util/regex/Pattern;;
private static network.chat.MutableComponent logo() {
    return network.chat.Component.literal(" ").append(network.chat.Component.literal("\ue000").setStyle(network.chat.Style.EMPTY.withFont(ui.MemberTags.BADGE_FONT).withColor(ChatFormatting.WHITE)));
}

private void <init>() {
    return;
}

public static world.scores.Objective belowNameLift(client.multiplayer.ClientLevel world, double squaredDistance) {
    if (world == null) goto L12;
    if (cmp(squaredDistance,100.0) <0) goto L14;
    L12:
    return 0;
    L14:
    if (world.getScoreboard().getDisplayObjective(world.scores.DisplaySlot.BELOW_NAME) == null) goto L33;
    goto L34;
    L33:
    L34:
    return 0;
}

private static Object stackTop(client.multiplayer.ClientLevel world, client.player.AbstractClientPlayer player, double nameTop) {
    v4 = System.currentTimeMillis();
    v6 = player.isDiscrete();
    v7 = ((ui.MemberTags$Stack)ui.MemberTags.STACKS.get(player.getUUID()));
    if (v7 == null) goto L64;
    if (v7.sneaking() != v6) goto L64;
    if (cmp((v4 - v7.tick()),150) >=0) goto L64;
    return v7.top();
    L64:
    v8 = nameTop;
    v10 = player.getIndirectPassengers().iterator();
    L78:
    if (v10.hasNext() ==0) goto L124;
    v11 = ((world.entity.Entity)v10.next());
    if (ui.MemberTags.isLabel(v11) ==0) goto L121;
    v8 = Math.max(v8, ui.MemberTags.labelTop(v11, player));
    L121:
    goto L78;
    L124:
    v10 = ?;
    v11 = world.getEntities(player, v10, /*indy*/test()).iterator();
    L202:
    if (v11.hasNext() ==0) goto L240;
    v12 = ((world.entity.Entity)v11.next());
    v8 = Math.max(v8, ui.MemberTags.labelTop(v12, player));
    goto L202;
    L240:
    if (ui.MemberTags.STACKS.size() <= 200) goto L262;
    ui.MemberTags.STACKS.clear();
    L262:
    ui.MemberTags.STACKS.put(player.getUUID(), ?);
    return v8;
}

private static float labelTop(world.entity.Entity entity, client.player.AbstractClientPlayer player) {
    v2 = (entity.getY() - player.getY());
    if ((entity instanceof world.entity.Display) ==0) goto L87;
    v4 = ((world.entity.Display)entity);
    v5 = v4.renderState();
    if (v5 == null) goto L81;
    v6 = ((math.Transformation)v5.transformation().get(1));
    v2 = (v2 + (((d)v6.translation().y()) + (((d)v6.scale().y()) * 0.3)));
    L81:
    return (v2 + 0.3);
    L87:
    v4 = entity.getAttachments().getNullable(world.entity.EntityAttachment.NAME_TAG, 0, 0);
    if (v4 == null) goto L115;
    goto L120;
    L115:
    L120:
    return (((v4.y + ((d)entity.getBbHeight())) + 0.5) + 0.3);
}

private static boolean isLabel(world.entity.Entity entity) {
    if ((entity instanceof world.entity.Display$TextDisplay) ==0) goto L9;
    return 1;
    L9:
    if ((entity instanceof world.entity.decoration.ArmorStand) ==0) goto L46;
    v1 = ((world.entity.decoration.ArmorStand)entity);
    if (v1.isMarker() !=0) goto L35;
    if (v1.isInvisible() ==0) goto L46;
    L35:
    if (v1.hasCustomName() ==0) goto L46;
    goto L47;
    L46:
    L47:
    return 0;
}

public static network.chat.MutableComponent label(net.MemberData$Member member) {
    if (member == null) goto L11;
    if (member.role != null) goto L17;
    L11:
    goto L21;
    L17:
    L21:
    v1 = member.role;
    if (member == null) goto L33;
    if (member.clan != null) goto L39;
    L33:
    goto L43;
    L39:
    L43:
    v2 = member.clan;
    v3 = v1.indexOf(8226);
    if (v3 <=0) goto L68;
    goto L69;
    L68:
    L69:
    v4 = v1;
    if (v2.isEmpty() !=0) goto L82;
    goto L102;
    L82:
    if (v3 <=0) goto L99;
    goto L102;
    L99:
    L102:
    v5 = "";
    if (net.Members.rankIndex(v4) != net.Members.LEADER_RANK) goto L119;
    goto L120;
    L119:
    L120:
    v6 = 0;
    if (v6 ==0) goto L147;
    if (v5.isEmpty() !=0) goto L147;
    goto L149;
    L147:
    L149:
    v7 = v4;
    new ChatFormatting[2][0] = ChatFormatting.GREEN;
    new ChatFormatting[2][1] = ChatFormatting.BOLD;
    v8 = network.chat.Component.empty().append(network.chat.Component.literal("FWM").withStyle(new ChatFormatting[2])).append(network.chat.Component.literal(" - ").withStyle(ChatFormatting.WHITE));
    v9 = ui.RankColors.gradient(v7, net.Members.rankIndex(v4));
    if (v9.getSiblings().isEmpty() ==0) goto L235;
    v8.append(v9);
    goto L280;
    L235:
    v10 = v9.getSiblings().iterator();
    L247:
    if (v10.hasNext() ==0) goto L280;
    v11 = ((network.chat.Component)v10.next());
    v8.append(v11);
    goto L247;
    L280:
    return v8.append(ui.MemberTags.logo());
}

public static network.chat.MutableComponent warning(client.player.AbstractClientPlayer player) {
    if (ui.MemberTags.wearsFake(player) !=0) goto L9;
    return null;
    L9:
    v1 = net.ClanTags.fakeRawIn(ui.MemberTags.wornName(player));
    if (v1 != null) goto L25;
    goto L32;
    L25:
    L32:
    v2 = net.TrustedClans.fakeOf(net.ClanTags.bare(v1));
    if (v2 == null) goto L77;
    if ("fwm".equals(v2.id) !=0) goto L77;
    new ChatFormatting[2][0] = ChatFormatting.RED;
    new ChatFormatting[2][1] = ChatFormatting.BOLD;
    return network.chat.Component.literal(net.TrustedClans.fakeWarning(v2)).withStyle(new ChatFormatting[2]);
    L77:
    new ChatFormatting[2][0] = ChatFormatting.RED;
    new ChatFormatting[2][1] = ChatFormatting.BOLD;
    return ui.Lang.of("fwm.faketag.badge", new Object[0]).withStyle(new ChatFormatting[2]);
}

public static boolean wearsFake(client.player.AbstractClientPlayer player) {
    return ui.MemberTags.look(player).fake();
}

public static boolean wearsForeignTag(client.player.AbstractClientPlayer player) {
    return ui.MemberTags.look(player).foreign();
}

public static String wearsMarket(client.player.AbstractClientPlayer player) {
    return ui.MemberTags.look(player).market();
}

public static network.chat.MutableComponent marketLabel(String tag) {
    v1 = net.TrustedClans.match(tag);
    if (v1 != null) goto L16;
    goto L21;
    L16:
    L21:
    return net.TrustedClans.label(v1, tag);
}

private static Object look(client.player.AbstractClientPlayer player) {
    v1 = System.currentTimeMillis();
    v3 = ((ui.MemberTags$Warn)ui.MemberTags.WARNS.get(player.getUUID()));
    if (v3 == null) goto L39;
    if (cmp((v1 - v3.at()),1000) >=0) goto L39;
    return v3;
    L39:
    v4 = ui.MemberTags.wornName(player);
    if (player.getGameProfile() == null) goto L65;
    if (net.FakeList.isFakePlayer(player.getGameProfile().name()) !=0) goto L73;
    L65:
    if (chat.FakeTags.fakeIn(v4) == null) goto L77;
    L73:
    goto L78;
    L77:
    L78:
    v5 = 0;
    v6 = 0;
    v7 = 0;
    v8 = null;
    v9 = ui.MemberTags.BRACKET_TAG.matcher(chat.ServerLine.strip(v4));
    L102:
    if (v9.find() ==0) goto L156;
    v6 = 1;
    if (net.ClanTags.isReal(v9.group(1)) ==0) goto L128;
    v7 = 1;
    L128:
    if (v8 != null) goto L102;
    if (net.TrustedClans.match(v9.group(1)) == null) goto L102;
    v8 = v9.group(1);
    goto L102;
    L156:
    if (v6 ==0) goto L176;
    if (v7 !=0) goto L176;
    goto L177;
    L176:
    L177:
    v10 = ?;
    if (ui.MemberTags.WARNS.size() <= 200) goto L207;
    ui.MemberTags.WARNS.clear();
    L207:
    ui.MemberTags.WARNS.put(player.getUUID(), v10);
    return v10;
}

private static String wornName(client.player.AbstractClientPlayer player) {
    v1 = ?;
    v2 = client.Minecraft.getInstance().getConnection();
    if (v2 != null) goto L23;
    goto L31;
    L23:
    L31:
    v3 = v2.getPlayerInfo(player.getUUID());
    if (v3 == null) goto L62;
    if (v3.getTabListDisplayName() == null) goto L62;
    v1.append(v3.getTabListDisplayName().getString()).append(32);
    L62:
    v4 = player.getDisplayName();
    if (v4 == null) goto L85;
    v1.append(v4.getString());
    L85:
    return v1.toString();
}

public static void register() {
    fabric.api.client.rendering.v1.level.LevelRenderEvents.COLLECT_SUBMITS.register(/*indy*/collectSubmits());
    return;
}

private static void render(fabric.api.client.rendering.v1.level.LevelRenderContext ctx) {
    ui.NoRisk.attach();
    v1 = client.Minecraft.getInstance();
    v2 = v1.level;
    if (v2 == null) goto L36;
    if (v1.player == null) goto L36;
    if (v1.gui.hud.isHidden() ==0) goto L37;
    L36:
    return;
    L37:
    v3 = ctx.poseStack();
    v4 = ctx.submitNodeCollector();
    if (v3 == null) goto L70;
    if (v4 == null) goto L70;
    if (ctx.levelState() != null) goto L71;
    L70:
    return;
    L71:
    v5 = ctx.levelState().cameraRenderState;
    v6 = v1.gameRenderer.mainCamera();
    if (v5 == null) goto L101;
    if (v6 != null) goto L102;
    L101:
    return;
    L102:
    v7 = config.FwmConfig.INSTANCE;
    v8 = Math.max(1, v7.memberTagRange);
    v10 = (v8 * v8);
    v12 = v1.getDeltaTracker().getGameTimeDeltaPartialTick(0);
    v13 = v6.position();
    v14 = v2.players().iterator();
    L155:
    if (v14.hasNext() ==0) goto L647;
    v15 = ((client.player.AbstractClientPlayer)v14.next());
    if (v15.getGameProfile() != null) goto L188;
    goto L155;
    L188:
    v16 = net.Members.member(v15.getGameProfile().name());
    v17 = ui.MemberTags.warning(v15);
    if (v17 != null) goto L233;
    if (v16 == null) goto L233;
    if (ui.MemberTags.wearsForeignTag(v15) !=0) goto L233;
    v17 = ui.MemberTags.label(v16);
    L233:
    if (v17 != null) goto L262;
    if (v16 != null) goto L262;
    v18 = ui.MemberTags.wearsMarket(v15);
    if (v18 == null) goto L262;
    v17 = ui.MemberTags.marketLabel(v18);
    L262:
    v18 = net.TrustedClans.playerLabel(v15.getUUID());
    if (v17 != null) goto L284;
    v17 = v18;
    v18 = null;
    L284:
    if (v17 != null) goto L292;
    goto L155;
    L292:
    if (v15 != v1.player) goto L317;
    if (v1.options.getCameraType().isFirstPerson() ==0) goto L332;
    goto L155;
    L317:
    if (v15.isInvisibleTo(v1.player) ==0) goto L332;
    goto L155;
    L332:
    if (ui.NoRisk.handled(v15.getUUID()) ==0) goto L346;
    goto L155;
    L346:
    v19 = v15.getPosition(v12);
    v20 = (v19.x - v13.x);
    v22 = (v19.y - v13.y);
    v24 = (v19.z - v13.z);
    v26 = (((v20 * v20) + (v22 * v22)) + (v24 * v24));
    if (cmp(v26,v10) <=0) goto L424;
    goto L155;
    L424:
    v28 = v15.getAttachments().getNullable(world.entity.EntityAttachment.NAME_TAG, 0, v15.getViewYRot(v12));
    if (v28 == null) goto L458;
    goto L468;
    L458:
    L468:
    v29 = (((d)v15.getBbHeight()) + 0.5);
    v31 = ((v29 + 0.5) + ui.MemberTags.belowNameLift(v2, v26));
    v33 = ((ui.MemberTags.stackTop(v2, v15, v31) + ui.NoRisk.extraLift()) + 0.6);
    v3.pushPose();
    v3.translate(v20, (v22 + v33), v24);
    v3.scale(0.75, 0.75, 0.75);
    if (v15.isDiscrete() !=0) goto L554;
    goto L555;
    L554:
    L555:
    v3.submitNameTag(ui.MemberTags.ANCHOR, 0, v17, 1, 0, 15728880, v5);
    v3.popPose();
    if (v18 == null) goto L644;
    v3.pushPose();
    v3.translate(v20, ((v22 + v33) + 0.25), v24);
    v3.scale(0.75, 0.75, 0.75);
    if (v15.isDiscrete() !=0) goto L629;
    goto L630;
    L629:
    L630:
    v3.submitNameTag(ui.MemberTags.ANCHOR, 0, v18, 1, 0, 15728880, v5);
    v3.popPose();
    L644:
    goto L155;
    L647:
    return;
}

static regex.Pattern <clinit>() {
    ui.MemberTags.BADGE_FONT = ?;
    ui.MemberTags.ANCHOR = ?;
    ui.MemberTags.STACKS = ?;
    ui.MemberTags.WARNS = ?;
    ui.MemberTags.BRACKET_TAG = regex.Pattern.compile("\\[([^\\[\\]\\s]{1,8})\\]");
    return;
}

