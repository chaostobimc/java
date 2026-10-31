// class net/friendswithmoney/fwm/net/ModId  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field FORM Ljava/util/regex/Pattern;;
field modId Ljava/lang/String;;
field loaded Z;
field sentFor Lnet/minecraft/client/multiplayer/ClientPacketListener;;
private void <init>() {
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-id.json");
}

public static void get() {
    if (net.ModId.loaded !=0) goto L9;
    net.ModId.load();
    L9:
    return net.ModId.modId;
}

private static boolean load() {
    net.ModId.loaded = 1;
    if (Files.exists(net.ModId.file(), new LinkOption[0]) !=0) goto L18;
    return;
    L18:
    v0 = ((net.ModId$Stored)net.ModId.GSON.fromJson(Files.readString(net.ModId.file(), StandardCharsets.UTF_8), net.ModId$Stored.class));
    if (v0 == null) goto L73;
    if (v0.modId == null) goto L73;
    if (net.ModId.FORM.matcher(v0.modId).matches() ==0) goto L73;
    net.ModId.modId = v0.modId;
    L73:
    goto L77;
    v0 = /*catch Exception*/ ex;
    L77:
    return;
}

private static Path save(String id) {
    net.ModId.modId = id;
    v1 = ?;
    v1.modId = id;
    Files.writeString(net.ModId.file(), net.ModId.GSON.toJson(v1), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L42;
    v1 = /*catch Exception*/ ex;
    L42:
    return;
}

public static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (client.player == null) goto L14;
    if (net.Presence.onHugo(client) !=0) goto L15;
    L14:
    return;
    L15:
    v1 = client.getConnection();
    if (v1 == null) goto L31;
    if (v1 != net.ModId.sentFor) goto L32;
    L31:
    return;
    L32:
    net.ModId.sentFor = v1;
    v2 = ?;
    v2.uuid = client.player.getUUID().toString().replace("-", "");
    v2.name = client.player.getGameProfile().name();
    v2.modId = net.ModId.get();
    v2.modVersion = FwmClient.MOD_VERSION;
    net.FwmApi.post("/api/mod/identify", v2, null, net.ModId$Answer.class).whenComplete(/*indy*/accept());
    return;
}

private static void lambda$tick$0(net.ModId$Answer answer, Throwable error) {
    if (error != null) goto L15;
    if (answer == null) goto L15;
    if (answer.modId != null) goto L16;
    L15:
    return;
    L16:
    if (net.ModId.FORM.matcher(answer.modId).matches() !=0) goto L33;
    return;
    L33:
    if (answer.modId.equals(net.ModId.modId) !=0) goto L53;
    net.ModId.save(answer.modId);
    L53:
    return;
}

static regex.Pattern <clinit>() {
    net.ModId.GSON = ?;
    net.ModId.FORM = regex.Pattern.compile("fwm_[A-Z2-9]{8}");
    return;
}

