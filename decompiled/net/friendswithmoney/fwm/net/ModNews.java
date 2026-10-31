// class net/friendswithmoney/fwm/net/ModNews  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REFRESH_MS J;
field RETRY_MS J;
field CARD_MS J;
field GSON Lcom/google/gson/Gson;;
field data Lnet/friendswithmoney/fwm/net/ModNews$Data;;
field busy Z;
field fromDisk Z;
field nextLoad J;
field cards Ljava/util/Deque;;
field card Lnet/friendswithmoney/fwm/net/ModNews$Entry;;
field cardAt J;
private void <init>() {
    return;
}

public static List list(net.ModNews$Kind kind) {
    v1 = net.ModNews.data;
    // switch (kind.ordinal()) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=28), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=2), Operand(op_type=<OperandTypes.BRANCH: 40>, value=38), Operand(op_type=<OperandTypes.BRANCH: 40>, value=45), Operand(op_type=<OperandTypes.BRANCH: 40>, value=52)]
    L36:
    throw ?;
    L46:
    goto L64;
    L53:
    goto L64;
    L60:
    L64:
    v2 = v1.fixes;
    if (v2 != null) goto L75;
    goto L76;
    L75:
    L76:
    return v2;
}

public static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (net.ModNews.fromDisk !=0) goto L25;
    net.ModNews.fromDisk = 1;
    v1 = net.ModNews.load();
    if (v1 == null) goto L25;
    net.ModNews.data = net.ModNews.clean(v1);
    L25:
    if (net.ModNews.busy !=0) goto L41;
    if (cmp(System.currentTimeMillis(),net.ModNews.nextLoad) >=0) goto L42;
    L41:
    return;
    L42:
    net.ModNews.busy = 1;
    net.FwmApi.get("/api/mod/news", net.ModNews$Data.class).whenCompleteAsync(/*indy*/accept(client), net.FwmApi.executor());
    return;
}

private static boolean clean(net.ModNews$Data in) {
    if (in.infos != null) goto L18;
    in.infos = ?;
    L18:
    if (in.bugs != null) goto L36;
    in.bugs = ?;
    L36:
    if (in.fixes != null) goto L54;
    in.fixes = ?;
    L54:
    in.infos.removeIf(/*indy*/test());
    in.bugs.removeIf(/*indy*/test());
    in.fixes.removeIf(/*indy*/test());
    return in;
}

private static void queueNew() {
    v0 = config.FwmConfig.INSTANCE;
    if (v0.newsShownIds != null) goto L15;
    goto L16;
    L15:
    L16:
    v1 = 0;
    if (v1 ==0) goto L31;
    goto L35;
    L31:
    L35:
    v2 = ?;
    v3 = ?;
    v4 = net.ModNews.data.infos.iterator();
    L60:
    if (v4.hasNext() ==0) goto L108;
    v5 = ((net.ModNews$Entry)v4.next());
    if (v2.contains(v5.id) !=0) goto L105;
    v3.add(v5);
    L105:
    goto L60;
    L108:
    if (v3.isEmpty() ==0) goto L122;
    if (v1 !=0) goto L122;
    return;
    L122:
    if (v1 ==0) goto L150;
    if (v3.size() <= 1) goto L150;
    v3 = List.of(((net.ModNews$Entry)v3.get(0)));
    L150:
    v4 = (v3.size() - 1);
    L160:
    if (v4 <0) goto L191;
    net.ModNews.cards.add(((net.ModNews$Entry)v3.get(v4)));
    v4 = v4 + 255;
    goto L160;
    L191:
    v4 = ?;
    v5 = net.ModNews.data.infos.iterator();
    L213:
    if (v5.hasNext() ==0) goto L251;
    v6 = ((net.ModNews$Entry)v5.next());
    v4.add(v6.id);
    goto L213;
    L251:
    v0.newsShownIds = v4;
    config.ConfigManager.save();
    return;
}

public static Object card() {
    v0 = System.currentTimeMillis();
    if (net.ModNews.card == null) goto L26;
    if (cmp((v0 - net.ModNews.cardAt),10000) >=0) goto L26;
    return net.ModNews.card;
    L26:
    net.ModNews.card = ((net.ModNews$Entry)net.ModNews.cards.poll());
    net.ModNews.cardAt = v0;
    return net.ModNews.card;
}

public static float cardProgress() {
    if (net.ModNews.card != null) goto L10;
    goto L25;
    L10:
    L25:
    return Math.min(1, (((f)(System.currentTimeMillis() - net.ModNews.cardAt)) / 10000.0));
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-news.json");
}

private static void load() {
    v0 = net.ModNews.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L17;
    return null;
    L17:
    return ((net.ModNews$Data)net.ModNews.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.ModNews$Data.class));
    v0 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read news cache: ' + v0.getMessage());
    return null;
}

private static void save(net.ModNews$Data value) {
    v1 = net.ModNews.file();
    Files.createDirectories(v1.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v1, net.ModNews.GSON.toJson(value), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L56;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write news cache: ' + v1.getMessage());
    L56:
    return;
}

private static boolean lambda$clean$2(net.ModNews$Entry e) {
    if (e == null) goto L11;
    if (e.id != null) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static boolean lambda$clean$1(net.ModNews$Entry e) {
    if (e == null) goto L11;
    if (e.id != null) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static boolean lambda$clean$0(net.ModNews$Entry e) {
    if (e == null) goto L11;
    if (e.id != null) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static void lambda$tick$0(client.Minecraft client, net.ModNews$Data result, Throwable error) {
    client.execute(/*indy*/run(error, result));
    return;
}

private static void lambda$tick$1(Throwable error, net.ModNews$Data result) {
    net.ModNews.busy = 0;
    if (error != null) goto L12;
    if (result != null) goto L48;
    L12:
    if (error != null) goto L25;
    goto L29;
    L25:
    L29:
    "empty response".println('[FWM] News failed: ' + error.getMessage());
    net.ModNews.nextLoad = (System.currentTimeMillis() + 300000);
    return;
    L48:
    net.ModNews.data = net.ModNews.clean(result);
    net.ModNews.nextLoad = (System.currentTimeMillis() + 600000);
    net.ModNews.save(net.ModNews.data);
    net.ModNews.queueNew();
    return;
}

static void <clinit>() {
    net.ModNews.GSON = ?.setPrettyPrinting().create();
    net.ModNews.data = ?;
    net.ModNews.cards = ?;
    return;
}

