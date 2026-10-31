// class net/friendswithmoney/fwm/net/FakeList  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REFRESH_MS J;
field RETRY_MS J;
field GSON Lcom/google/gson/Gson;;
field tags Ljava/util/Set;;
field players Ljava/util/Set;;
field similar Ljava/util/List;;
field managed Z;
field real Ljava/util/Map;;
field busy Z;
field fromDisk Z;
field nextLoad J;
private void <init>() {
    return;
}

public static boolean managed() {
    return net.FakeList.managed;
}

public static List similarTags() {
    return net.FakeList.similar;
}

public static boolean isFakeTag(String tag) {
    v1 = net.FakeList.tag(tag);
    if (v1.isEmpty() !=0) goto L28;
    if (net.FakeList.tags.contains(v1) ==0) goto L28;
    goto L29;
    L28:
    L29:
    return 0;
}

public static boolean isFakePlayer(String name) {
    if (name == null) goto L29;
    if (net.FakeList.players.contains(name.trim().toLowerCase(Locale.ROOT)) ==0) goto L29;
    goto L30;
    L29:
    L30:
    return 0;
}

public static Object realLabel(String name) {
    if (name != null) goto L6;
    return null;
    L6:
    return ((String)net.FakeList.real.get(chat.ServerLine.strip(name).trim().toLowerCase(Locale.ROOT)));
}

public static network.chat.MutableComponent realBadge(String name) {
    v1 = net.FakeList.realLabel(name);
    if (v1 != null) goto L13;
    goto L28;
    L13:
    L28:
    return network.chat.Component.literal(' (' + v1 + ')').withStyle(ChatFormatting.GREEN);
}

public static network.chat.MutableComponent decorateReal(String name, network.chat.Component text) {
    v2 = net.FakeList.realBadge(name);
    if (v2 == null) goto L13;
    if (text != null) goto L15;
    L13:
    return text;
    L15:
    return network.chat.Component.empty().append(text).append(v2);
}

public static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (net.FakeList.fromDisk !=0) goto L22;
    net.FakeList.fromDisk = 1;
    v1 = net.FakeList.load();
    if (v1 == null) goto L22;
    net.FakeList.apply(v1);
    L22:
    if (net.FakeList.busy !=0) goto L38;
    if (cmp(System.currentTimeMillis(),net.FakeList.nextLoad) >=0) goto L39;
    L38:
    return;
    L39:
    net.FakeList.busy = 1;
    net.FwmApi.get("/api/mod/fakes", net.FakeList$Data.class).whenCompleteAsync(/*indy*/accept(client), net.FwmApi.executor());
    return;
}

private static Map apply(net.FakeList$Data in) {
    v1 = ?;
    if (in.tags == null) goto L68;
    v2 = in.tags.iterator();
    L25:
    if (v2.hasNext() ==0) goto L68;
    v3 = ((String)v2.next());
    if (net.FakeList.tag(v3).isEmpty() !=0) goto L65;
    v1.add(net.FakeList.tag(v3));
    L65:
    goto L25;
    L68:
    v2 = ?;
    if (in.players == null) goto L147;
    v3 = in.players.iterator();
    L93:
    if (v3.hasNext() ==0) goto L147;
    v4 = ((String)v3.next());
    if (v4 == null) goto L144;
    if (v4.isBlank() !=0) goto L144;
    v2.add(v4.trim().toLowerCase(Locale.ROOT));
    L144:
    goto L93;
    L147:
    v3 = ?;
    if (in.real == null) goto L282;
    v4 = in.real.iterator();
    L173:
    if (v4.hasNext() ==0) goto L282;
    v5 = ((net.FakeList$Real)v4.next());
    if (v5 == null) goto L173;
    if (v5.name == null) goto L173;
    if (v5.name.isBlank() ==0) goto L222;
    goto L173;
    L222:
    if (v5.label == null) goto L241;
    if (v5.label.isBlank() ==0) goto L246;
    L241:
    goto L254;
    L246:
    L254:
    v6 = v5.label.trim();
    v3.put(v5.name.trim().toLowerCase(Locale.ROOT), v6);
    goto L173;
    L282:
    net.FakeList.tags = Set.copyOf(v1);
    net.FakeList.players = Set.copyOf(v2);
    v4 = ?;
    if (in.similar == null) goto L372;
    v5 = in.similar.iterator();
    L323:
    if (v5.hasNext() ==0) goto L372;
    v6 = ((String)v5.next());
    if (net.FakeList.tag(v6).isEmpty() !=0) goto L369;
    v4.add(net.FakeList.tag(v6));
    L369:
    goto L323;
    L372:
    net.FakeList.similar = List.copyOf(v4);
    net.FakeList.managed = in.managed;
    net.FakeList.real = Map.copyOf(v3);
    return;
}

private static String tag(String raw) {
    if (raw != null) goto L7;
    return "";
    L7:
    v1 = raw.trim();
    if (v1.startsWith("[") ==0) goto L50;
    if (v1.endsWith("]") ==0) goto L50;
    if (v1.length() <= 1) goto L50;
    v1 = v1.substring(1, (v1.length() - 1));
    L50:
    return v1.replaceAll("\\s", "").toUpperCase(Locale.ROOT);
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-fakes.json");
}

private static void load() {
    v0 = net.FakeList.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L17;
    return null;
    L17:
    return ((net.FakeList$Data)net.FakeList.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.FakeList$Data.class));
    v0 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read fake list cache: ' + v0.getMessage());
    return null;
}

private static void save(net.FakeList$Data value) {
    v1 = net.FakeList.file();
    Files.createDirectories(v1.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v1, net.FakeList.GSON.toJson(value), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L56;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write fake list cache: ' + v1.getMessage());
    L56:
    return;
}

private static void lambda$tick$0(client.Minecraft client, net.FakeList$Data result, Throwable error) {
    client.execute(/*indy*/run(error, result));
    return;
}

private static void lambda$tick$1(Throwable error, net.FakeList$Data result) {
    net.FakeList.busy = 0;
    if (error != null) goto L12;
    if (result != null) goto L48;
    L12:
    if (error != null) goto L25;
    goto L29;
    L25:
    L29:
    "empty response".println('[FWM] Fake list failed: ' + error.getMessage());
    net.FakeList.nextLoad = (System.currentTimeMillis() + 300000);
    return;
    L48:
    net.FakeList.apply(result);
    net.FakeList.nextLoad = (System.currentTimeMillis() + 600000);
    net.FakeList.save(result);
    return;
}

static Map <clinit>() {
    net.FakeList.GSON = ?;
    net.FakeList.tags = Set.of();
    net.FakeList.players = Set.of();
    net.FakeList.similar = List.of();
    net.FakeList.real = Map.of();
    return;
}

