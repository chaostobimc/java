// class net/friendswithmoney/fwm/ui/NoRisk  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MOD_ID Ljava/lang/String;;
field EVENTS Ljava/lang/String;;
field EVENT Ljava/lang/String;;
field MANAGER Ljava/lang/String;;
field CONFIG Ljava/lang/String;;
field PRIORITY Ljava/lang/String;;
field FUNCTION1 Ljava/lang/String;;
field UNIT Ljava/lang/String;;
field PING_CLASS Ljava/lang/String;;
field LINE_H D;
field SCALE F;
field attached Z;
field attachFailed Z;
field manager Ljava/lang/Object;;
field renderNameTag Ljava/lang/reflect/Method;;
field configCtor Ljava/lang/reflect/Constructor;;
field getUuid Ljava/lang/reflect/Method;;
field getNameTagPos Ljava/lang/reflect/Method;;
field isSneaking Ljava/lang/reflect/Method;;
field getDistance Ljava/lang/reflect/Method;;
field getContext Ljava/lang/reflect/Method;;
field DRAWN Ljava/util/Map;;
field HANDLED_MS J;
field checkedAt J;
field lift D;
field pingModule Ljava/lang/Object;;
field position Ljava/lang/reflect/Method;;
field scale Ljava/lang/reflect/Method;;
field pingUnavailable Z;
private void <init>() {
    return;
}

public static long handled(UUID uuid) {
    v1 = ((Long)ui.NoRisk.DRAWN.get(uuid));
    if (v1 == null) goto L36;
    if (cmp((System.currentTimeMillis() - v1.longValue()),250) >=0) goto L36;
    goto L37;
    L36:
    L37:
    return 0;
}

public static boolean attached() {
    return ui.NoRisk.attached;
}

public static Object attach() {
    if (ui.NoRisk.attached !=0) goto L12;
    if (ui.NoRisk.attachFailed ==0) goto L13;
    L12:
    return;
    L13:
    if (loader.api.FabricLoader.getInstance().isModLoaded("nrcclient") !=0) goto L31;
    ui.NoRisk.attachFailed = 1;
    return;
    L31:
    v0 = Class.forName("gg.norisk.compat.nametag.NameTagRenderEventKt").getMethod("getAfterRenderNameTagEvent", new Class[0]).invoke(null, new Object[0]);
    v1 = Class.forName("gg.norisk.compat.nametag.NameTagManager");
    ui.NoRisk.manager = v1.getField("INSTANCE").get(null);
    ui.NoRisk.renderNameTag = ui.NoRisk.method(v1, "renderNameTag", 6);
    ui.NoRisk.configCtor = ui.NoRisk.constructor(Class.forName("gg.norisk.compat.nametag.NameTagConfig"), 4);
    v2 = Class.forName("gg.norisk.compat.nametag.NameTagRenderEvent");
    ui.NoRisk.getUuid = v2.getMethod("getUuid", new Class[0]);
    ui.NoRisk.getNameTagPos = v2.getMethod("getNameTagPos", new Class[0]);
    ui.NoRisk.isSneaking = v2.getMethod("isSneaking", new Class[0]);
    ui.NoRisk.getDistance = v2.getMethod("getSquaredDistanceToCamera", new Class[0]);
    ui.NoRisk.getContext = v2.getMethod("getContext", new Class[0]);
    v3 = ui.NoRisk.priority();
    new Object[2][0] = v3;
    new Object[2][1] = ui.NoRisk.listener();
    ui.NoRisk.method(v0.getClass(), "listen", 2).invoke(v0, new Object[2]);
    ui.NoRisk.attached = 1;
    goto L216;
    v0 = /*catch Throwable*/ ex;
    ui.NoRisk.attachFailed = 1;
    ui.NoRisk.manager = null;
    L216:
    return;
}

private static Enum priority() {
    return Enum.valueOf(Class.forName("gg.norisk.abstraction.event.EventPriority"), "LAST");
}

private static Object listener() {
    v0 = Class.forName("kotlin.jvm.functions.Function1");
    v1 = Class.forName("kotlin.Unit").getField("INSTANCE").get(null);
    new Class[1][0] = v0;
    return reflect.Proxy.newProxyInstance(v0.getClassLoader(), new Class[1], /*indy*/invoke(v1));
}

private static Object onNameTag(Object event) {
    v1 = ((UUID)ui.NoRisk.getUuid.invoke(event, new Object[0]));
    if (v1 != null) goto L20;
    return;
    L20:
    v2 = client.Minecraft.getInstance();
    if (v2 == null) goto L42;
    if (v2.level == null) goto L42;
    if (v2.player != null) goto L43;
    L42:
    return;
    L43:
    if (v1.equals(v2.player.getUUID()) ==0) goto L71;
    if (v2.options.getCameraType().isFirstPerson() ==0) goto L71;
    return;
    L71:
    v3 = null;
    v4 = v2.level.players().iterator();
    L87:
    if (v4.hasNext() ==0) goto L130;
    v5 = ((client.player.AbstractClientPlayer)v4.next());
    if (v1.equals(v5.getUUID()) ==0) goto L127;
    v3 = v5;
    goto L130;
    L127:
    goto L87;
    L130:
    if (v3 == null) goto L141;
    if (v3.getGameProfile() != null) goto L142;
    L141:
    return;
    L142:
    v4 = net.Members.member(v3.getGameProfile().name());
    if (ui.MemberTags.wearsFake(v3) ==0) goto L162;
    return;
    L162:
    if (v4 == null) goto L185;
    if (ui.MemberTags.wearsForeignTag(v3) ==0) goto L175;
    return;
    L175:
    v5 = ui.MemberTags.label(v4);
    goto L207;
    L185:
    if (ui.MemberTags.wearsMarket(v3) == null) goto L204;
    v5 = ui.MemberTags.marketLabel(ui.MemberTags.wearsMarket(v3));
    goto L207;
    L204:
    v5 = null;
    L207:
    v6 = net.TrustedClans.playerLabel(v3.getUUID());
    if (v5 != null) goto L228;
    v5 = v6;
    v6 = null;
    L228:
    if (v5 != null) goto L234;
    return;
    L234:
    v7 = Math.max(1, config.FwmConfig.INSTANCE.memberTagRange);
    new Object[4][0] = Float.valueOf(1);
    new Object[4][1] = Boolean.valueOf(0);
    new Object[4][2] = Boolean.valueOf(0);
    new Object[4][3] = Double.valueOf((v7 * v7));
    v9 = ui.NoRisk.configCtor.newInstance(new Object[4]);
    v10 = ui.NoRisk.getNameTagPos.invoke(event, new Object[0]);
    v11 = ((Double)ui.NoRisk.getDistance.invoke(event, new Object[0])).doubleValue();
    v13 = ui.MemberTags.belowNameLift(v2.level, v11);
    if (cmp(v13,0) <=0) goto L366;
    if ((v10 instanceof world.phys.Vec3) ==0) goto L366;
    v15 = ((world.phys.Vec3)v10);
    v10 = v15.add(0, v13, 0);
    L366:
    new Object[6][0] = v5;
    new Object[6][1] = v9;
    new Object[6][2] = v10;
    new Object[6][3] = ui.NoRisk.isSneaking.invoke(event, new Object[0]);
    new Object[6][4] = Double.valueOf(v11);
    new Object[6][5] = ui.NoRisk.getContext.invoke(event, new Object[0]);
    ui.NoRisk.renderNameTag.invoke(ui.NoRisk.manager, new Object[6]);
    if (v6 == null) goto L526;
    if ((v10 instanceof world.phys.Vec3) ==0) goto L526;
    v15 = ((world.phys.Vec3)v10);
    new Object[6][0] = v6;
    new Object[6][1] = v9;
    new Object[6][2] = v15.add(0, 0.25, 0);
    new Object[6][3] = ui.NoRisk.isSneaking.invoke(event, new Object[0]);
    new Object[6][4] = Double.valueOf(v11);
    new Object[6][5] = ui.NoRisk.getContext.invoke(event, new Object[0]);
    ui.NoRisk.renderNameTag.invoke(ui.NoRisk.manager, new Object[6]);
    L526:
    if (ui.NoRisk.DRAWN.size() <= 200) goto L548;
    ui.NoRisk.DRAWN.clear();
    L548:
    ui.NoRisk.DRAWN.put(v1, Long.valueOf(System.currentTimeMillis()));
    goto L568;
    v1 = /*catch Throwable*/ ex;
    L568:
    return;
}

public static double extraLift() {
    if (ui.NoRisk.attached ==0) goto L8;
    return 0;
    L8:
    v0 = System.currentTimeMillis();
    if (cmp((v0 - ui.NoRisk.checkedAt),1000) >=0) goto L28;
    return ui.NoRisk.lift;
    L28:
    ui.NoRisk.checkedAt = v0;
    ui.NoRisk.lift = ui.NoRisk.measure();
    return ui.NoRisk.lift;
}

private static double measure() {
    if (ui.NoRisk.pingUnavailable ==0) goto L8;
    return 0;
    L8:
    if (ui.NoRisk.pingModule != null) goto L81;
    if (loader.api.FabricLoader.getInstance().isModLoaded("nrcclient") !=0) goto L33;
    ui.NoRisk.pingUnavailable = 1;
    return 0;
    L33:
    v0 = Class.forName("gg.norisk.client.v2.modules.ping.PingHud");
    ui.NoRisk.pingModule = v0.getField("INSTANCE").get(null);
    ui.NoRisk.position = v0.getMethod("getPingNameTagPosition", new Class[0]);
    ui.NoRisk.scale = v0.getMethod("getPingNameTagScale", new Class[0]);
    L81:
    v0 = ui.NoRisk.position.invoke(ui.NoRisk.pingModule, new Object[0]);
    if ((v0 instanceof Enum) ==0) goto L120;
    v1 = ((Enum)v0);
    if ("ABOVE".equals(v1.name()) !=0) goto L122;
    L120:
    return 0;
    L122:
    v2 = ui.NoRisk.scale.invoke(ui.NoRisk.pingModule, new Object[0]);
    if ((v2 instanceof Number) ==0) goto L157;
    v5 = ((Number)v2);
    goto L158;
    L157:
    L158:
    v3 = 1;
    return (0.3 * Math.max(0.1, Math.min(3.0, v3)));
    v0 = /*catch Throwable*/ ex;
    ui.NoRisk.pingUnavailable = 1;
    ui.NoRisk.pingModule = null;
    return 0;
}

private static void method(Class type, String name, int args) {
    v3 = type.getMethods();
    v4 = v3.length;
    v5 = 0;
    L12:
    if (v5 >= v4) goto L55;
    v6 = v3[v5];
    if (v6.getName().equals(name) ==0) goto L49;
    if (v6.getParameterCount() != args) goto L49;
    return v6;
    L49:
    v5 = v5 + 1;
    goto L12;
    L55:
    throw ?;
}

private static void constructor(Class type, int args) {
    v2 = type.getConstructors();
    v3 = v2.length;
    v4 = 0;
    L11:
    if (v4 >= v3) goto L41;
    v5 = v2[v4];
    if (v5.getParameterCount() != args) goto L35;
    return v5;
    L35:
    v4 = v4 + 1;
    goto L11;
    L41:
    throw ?;
}

private static boolean lambda$listener$0(Object unit, Object proxy, reflect.Method method, Object[] args) {
    if ("invoke".equals(method.getName()) ==0) goto L31;
    if (args == null) goto L31;
    if (args.length != 1) goto L31;
    ui.NoRisk.onNameTag(args[0]);
    return unit;
    L31:
    if ("equals".equals(method.getName()) ==0) goto L60;
    if (proxy != args[0]) goto L55;
    goto L56;
    L55:
    L56:
    return Boolean.valueOf(0);
    L60:
    if ("hashCode".equals(method.getName()) ==0) goto L81;
    return Integer.valueOf(System.identityHashCode(proxy));
    L81:
    if ("toString".equals(method.getName()) ==0) goto L98;
    return "fwm-badge";
    L98:
    return null;
}

static void <clinit>() {
    ui.NoRisk.DRAWN = ?;
    return;
}

