// class net/friendswithmoney/fwm/net/Waypoints  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FOUND_RADIUS D;
field SHOW_RADIUS D;
field MAX_DRAW D;
field REFRESH_MS J;
field RETRY_MS J;
field GSON Lcom/google/gson/Gson;;
field data Lnet/friendswithmoney/fwm/net/Waypoints$Data;;
field busy Z;
field fromDisk Z;
field nextLoad J;
field ticks I;
field doneSet Ljava/util/Set;;
field doneFrom Ljava/util/List;;
field doneSize I;
private void <init>() {
    return;
}

public static List groups() {
    v0 = net.Waypoints.data.groups;
    if (v0 != null) goto L17;
    goto L18;
    L17:
    L18:
    return v0;
}

public static String key(net.Waypoints$Group group, net.Waypoints$Point point) {
    return group.id + '/' + point.id;
}

public static boolean groupOn(net.Waypoints$Group group) {
    if (config.FwmConfig.INSTANCE.waypointsGroupOff.contains(group.id) !=0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

public static void setGroupOn(net.Waypoints$Group group, boolean on) {
    v2 = config.FwmConfig.INSTANCE.waypointsGroupOff;
    v2.remove(group.id);
    if (on !=0) goto L33;
    v2.add(group.id);
    L33:
    config.ConfigManager.save();
    return;
}

public static boolean done(net.Waypoints$Group group, net.Waypoints$Point point) {
    v2 = config.FwmConfig.INSTANCE.waypointsDone;
    if (v2 != net.Waypoints.doneFrom) goto L26;
    if (v2.size() == net.Waypoints.doneSize) goto L50;
    L26:
    net.Waypoints.doneSet = ?;
    net.Waypoints.doneFrom = v2;
    net.Waypoints.doneSize = v2.size();
    L50:
    return net.Waypoints.doneSet.contains(net.Waypoints.key(group, point));
}

public static void setDone(net.Waypoints$Group group, net.Waypoints$Point point, boolean done) {
    v3 = config.FwmConfig.INSTANCE.waypointsDone;
    v3.remove(net.Waypoints.key(group, point));
    if (done ==0) goto L35;
    v3.add(net.Waypoints.key(group, point));
    L35:
    config.ConfigManager.save();
    return;
}

public static boolean found(net.Waypoints$Group group) {
    v1 = 0;
    v2 = group.points.iterator();
    L12:
    if (v2.hasNext() ==0) goto L45;
    v3 = ((net.Waypoints$Point)v2.next());
    if (net.Waypoints.done(group, v3) ==0) goto L42;
    v1 = v1 + 1;
    L42:
    goto L12;
    L45:
    return v1;
}

public static void register() {
    fabric.api.client.rendering.v1.level.LevelRenderEvents.COLLECT_SUBMITS.register(/*indy*/collectSubmits());
    return;
}

public static void tick(client.Minecraft client) {
    if (config.Features.isOn("fwm.tab.waypoints") !=0) goto L9;
    return;
    L9:
    if (net.Waypoints.fromDisk !=0) goto L34;
    net.Waypoints.fromDisk = 1;
    v1 = net.Waypoints.load();
    if (v1 == null) goto L34;
    net.Waypoints.data = net.Waypoints.clean(v1);
    L34:
    if (net.Waypoints.busy !=0) goto L74;
    if (cmp(System.currentTimeMillis(),net.Waypoints.nextLoad) <0) goto L74;
    net.Waypoints.busy = 1;
    net.FwmApi.get("/api/mod/waypoints", net.Waypoints$Data.class).whenCompleteAsync(/*indy*/accept(client), net.FwmApi.executor());
    L74:
    net.Waypoints.ticks = (net.Waypoints.ticks + 1);
    if (((net.Waypoints.ticks + 1) % 10) !=0) goto L93;
    net.Waypoints.checkFound(client);
    L93:
    return;
}

private static void clean(net.Waypoints$Data in) {
    if (in.groups != null) goto L18;
    in.groups = ?;
    L18:
    in.groups.removeIf(/*indy*/test());
    v1 = in.groups.iterator();
    L43:
    if (v1.hasNext() ==0) goto L112;
    v2 = ((net.Waypoints$Group)v1.next());
    if (v2.points != null) goto L80;
    v2.points = ?;
    L80:
    v2.points.removeIf(/*indy*/test());
    v2.points.sort(/*indy*/compare());
    goto L43;
    L112:
    return in;
}

public static double reached(net.Waypoints$Point point, double px, double a2, double py) {
    v7 = (px - (((d)point.x) + 0.5));
    v9 = (py - ((d)point.y));
    v11 = (v5 - (((d)point.z) + 0.5));
    if (cmp(((v7 * v7) + (v11 * v11)),4.0) >0) goto L70;
    if (cmp(Math.abs(v9),3.0) >0) goto L70;
    goto L71;
    L70:
    L71:
    return 0;
}

private static void checkFound(client.Minecraft client) {
    if (client.player == null) goto L14;
    if (client.level != null) goto L15;
    L14:
    return;
    L15:
    v1 = net.RtpPoints$Dim.of(client.level).key();
    v2 = net.Waypoints.groups().iterator();
    L35:
    if (v2.hasNext() ==0) goto L226;
    v3 = ((net.Waypoints$Group)v2.next());
    if (net.Waypoints.groupOn(v3) !=0) goto L64;
    goto L35;
    L64:
    v4 = v3.points.iterator();
    L75:
    if (v4.hasNext() ==0) goto L223;
    v5 = ((net.Waypoints$Point)v4.next());
    if (v1.equals(v5.dim) ==0) goto L75;
    if (net.Waypoints.done(v3, v5) ==0) goto L121;
    goto L75;
    L121:
    if (net.Waypoints.reached(v5, client.player.getX(), client.player.getY(), client.player.getZ()) !=0) goto L153;
    goto L75;
    L153:
    net.Waypoints.setDone(v3, v5, 1);
    client.player.sendOverlayMessage(network.chat.Component.literal('✔ ' + v3.name + ' ' + v5.n + ' ').withStyle(ChatFormatting.GREEN).append(network.chat.Component.literal('(' + net.Waypoints.found(v3) + '/' + v3.points.size() + ')').withStyle(ChatFormatting.GRAY)));
    goto L75;
    L223:
    goto L35;
    L226:
    return;
}

private static void render(fabric.api.client.rendering.v1.level.LevelRenderContext ctx) {
    if (config.Features.isOn("fwm.tab.waypoints") !=0) goto L9;
    return;
    L9:
    v1 = client.Minecraft.getInstance();
    if (v1.level == null) goto L27;
    if (v1.player != null) goto L28;
    L27:
    return;
    L28:
    v2 = ctx.poseStack();
    v3 = ctx.submitNodeCollector();
    if (v2 == null) goto L59;
    if (v3 == null) goto L59;
    if (ctx.levelState() != null) goto L60;
    L59:
    return;
    L60:
    v4 = ctx.levelState().cameraRenderState;
    v5 = v1.gameRenderer.mainCamera();
    if (v4 == null) goto L90;
    if (v5 != null) goto L91;
    L90:
    return;
    L91:
    v6 = v5.position();
    v7 = net.RtpPoints$Dim.of(v1.level).key();
    v8 = net.Waypoints.groups().iterator();
    L120:
    if (v8.hasNext() ==0) goto L470;
    v9 = ((net.Waypoints$Group)v8.next());
    if (net.Waypoints.groupOn(v9) !=0) goto L153;
    goto L120;
    L153:
    v10 = v9.points.iterator();
    L165:
    if (v10.hasNext() ==0) goto L467;
    v11 = ((net.Waypoints$Point)v10.next());
    if (v7.equals(v11.dim) ==0) goto L165;
    if (net.Waypoints.done(v9, v11) ==0) goto L213;
    goto L165;
    L213:
    v12 = ((((d)v11.x) + 0.5) - v6.x);
    v14 = ((((d)v11.y) + 1.2) - v6.y);
    v16 = ((((d)v11.z) + 0.5) - v6.z);
    v18 = Math.sqrt((((v12 * v12) + (v14 * v14)) + (v16 * v16)));
    if (cmp(v18,500.0) <=0) goto L301;
    goto L165;
    L301:
    if (cmp(v18,32.0) <=0) goto L319;
    goto L320;
    L319:
    L320:
    v20 = 1;
    v22 = network.chat.Component.literal("\u25c6 ").withStyle(ChatFormatting.GOLD).append(network.chat.Component.literal(v9.name + ' ' + v11.n).withStyle(ChatFormatting.WHITE)).append(network.chat.Component.literal('  ' + Math.round(v18) + ' m').withStyle(ChatFormatting.GRAY));
    v23 = (1.2999999523162842 + (((f)(Math.min(v18, 32.0) / 32.0)) * 1.7000000476837158));
    v2.pushPose();
    v2.translate((v12 * v20), (v14 * v20), (v16 * v20));
    v2.scale(v23, v23, v23);
    v3.submitNameTag(v2, world.phys.Vec3.ZERO, 0, v22, 1, 15728880, v4);
    v2.popPose();
    goto L165;
    L467:
    goto L120;
    L470:
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-waypoints.json");
}

private static void load() {
    v0 = net.Waypoints.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L17;
    return null;
    L17:
    return ((net.Waypoints$Data)net.Waypoints.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.Waypoints$Data.class));
    v0 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read waypoint cache: ' + v0.getMessage());
    return null;
}

private static void save(net.Waypoints$Data value) {
    v1 = net.Waypoints.file();
    Files.createDirectories(v1.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v1, net.Waypoints.GSON.toJson(value), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L56;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write waypoint cache: ' + v1.getMessage());
    L56:
    return;
}

private static int lambda$clean$2(net.Waypoints$Point a, net.Waypoints$Point b) {
    return Integer.compare(a.n, b.n);
}

private static boolean lambda$clean$1(net.Waypoints$Point p) {
    if (p == null) goto L11;
    if (p.id != null) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static boolean lambda$clean$0(net.Waypoints$Group g) {
    if (g == null) goto L11;
    if (g.id != null) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static void lambda$tick$0(client.Minecraft client, net.Waypoints$Data result, Throwable error) {
    client.execute(/*indy*/run(error, result));
    return;
}

private static void lambda$tick$1(Throwable error, net.Waypoints$Data result) {
    net.Waypoints.busy = 0;
    if (error != null) goto L12;
    if (result != null) goto L48;
    L12:
    if (error != null) goto L25;
    goto L29;
    L25:
    L29:
    "empty response".println('[FWM] Waypoints failed: ' + error.getMessage());
    net.Waypoints.nextLoad = (System.currentTimeMillis() + 300000);
    return;
    L48:
    net.Waypoints.data = net.Waypoints.clean(result);
    net.Waypoints.nextLoad = (System.currentTimeMillis() + 600000);
    net.Waypoints.save(net.Waypoints.data);
    return;
}

static Set <clinit>() {
    net.Waypoints.GSON = ?.setPrettyPrinting().create();
    net.Waypoints.data = ?;
    net.Waypoints.doneSet = Set.of();
    net.Waypoints.doneSize = m1;
    return;
}

