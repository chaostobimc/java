// class net/friendswithmoney/fwm/net/ModBan  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field CHECK_EVERY_MS J;
field RETRY_MS J;
field note Lnet/friendswithmoney/fwm/net/ModBan$Note;;
field loaded Z;
field nextCheck J;
field busy Z;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(/*indy*/onEndTick());
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-sperre.json");
}

public static void locked() {
    if (net.ModBan.loaded !=0) goto L9;
    net.ModBan.load();
    L9:
    if (net.ModBan.note == null) goto L19;
    goto L20;
    L19:
    L20:
    return 0;
}

private static void load() {
    net.ModBan.loaded = 1;
    v0 = net.ModBan.file();
    if (Files.exists(v0, new LinkOption[0]) ==0) goto L68;
    net.ModBan.note = ((net.ModBan$Note)net.ModBan.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.ModBan$Note.class));
    if (net.ModBan.note == null) goto L68;
    if (net.ModBan.note.reported != null) goto L68;
    net.ModBan.note.reported = ?;
    L68:
    goto L90;
    v0 = /*catch Exception*/ ex;
    net.ModBan.note = ?;
    net.ModBan.note.name = "?";
    L90:
    return;
}

private static void save() {
    if (net.ModBan.note != null) goto L16;
    Files.deleteIfExists(net.ModBan.file());
    goto L39;
    L16:
    Files.writeString(net.ModBan.file(), net.ModBan.GSON.toJson(net.ModBan.note), StandardCharsets.UTF_8, new OpenOption[0]);
    L39:
    goto L58;
    v0 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write the lock note: ' + v0.getMessage());
    L58:
    return;
}

private static String selfUuid() {
    v0 = client.Minecraft.getInstance().getGameProfile();
    if (v0 == null) goto L18;
    if (v0.id() != null) goto L23;
    L18:
    goto L37;
    L23:
    L37:
    return v0.id().toString().replace("-", "");
}

private static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (net.ModBan.loaded !=0) goto L9;
    net.ModBan.load();
    L9:
    if (net.ModBan.busy !=0) goto L25;
    if (cmp(System.currentTimeMillis(),net.ModBan.nextCheck) >=0) goto L26;
    L25:
    return;
    L26:
    v1 = net.ModBan.selfUuid();
    v2 = ui.FwmGfx.playerName();
    if (v1.isEmpty() !=0) goto L48;
    if (v2.isEmpty() ==0) goto L49;
    L48:
    return;
    L49:
    net.ModBan.busy = 1;
    net.ModBan.nextCheck = (System.currentTimeMillis() + 300000);
    if (net.ModBan.note == null) goto L164;
    if (net.ModBan.note.uuid == null) goto L164;
    if (net.ModBan.note.uuid.equals(v1) !=0) goto L164;
    if (net.ModBan.note.reported.contains(v1) !=0) goto L164;
    v3 = ?;
    v3.uuid = v1;
    v3.name = v2;
    v3.of = net.ModBan.note.name;
    v3.ofUuid = net.ModBan.note.uuid;
    net.FwmApi.post("/api/mod/ban/alt", v3, null, net.ModBan$Ok.class).thenAccept(/*indy*/accept(client, v1));
    L164:
    v3 = ?;
    v3.uuid = v1;
    v3.name = v2;
    if (net.ModBan.note != null) goto L193;
    goto L199;
    L193:
    L199:
    null.lockedBy = net.ModBan.note.uuid;
    net.FwmApi.post("/api/mod/ban/check", v3, null, net.ModBan$Answer.class).whenComplete(/*indy*/accept(client, v2, v1));
    return;
}

private static void lambda$tick$2(client.Minecraft client, String name, String uuid, net.ModBan$Answer answer, Throwable error) {
    client.execute(/*indy*/run(error, answer, name, uuid));
    return;
}

private static void lambda$tick$3(Throwable error, net.ModBan$Answer answer, String name, String uuid) {
    net.ModBan.busy = 0;
    if (error != null) goto L12;
    if (answer != null) goto L13;
    L12:
    return;
    L13:
    net.ModBan.nextCheck = (System.currentTimeMillis() + 1800000);
    if (answer.banned ==0) goto L75;
    if (net.ModBan.note != null) goto L88;
    net.ModBan.note = ?;
    net.ModBan.note.name = name;
    net.ModBan.note.uuid = uuid;
    net.ModBan.note.at = System.currentTimeMillis();
    net.ModBan.save();
    goto L88;
    L75:
    if (net.ModBan.note == null) goto L88;
    net.ModBan.note = null;
    net.ModBan.save();
    L88:
    return;
}

private static void lambda$tick$0(client.Minecraft client, String uuid, net.ModBan$Ok ok) {
    client.execute(/*indy*/run(uuid));
    return;
}

private static void lambda$tick$1(String uuid) {
    if (net.ModBan.note == null) goto L37;
    if (net.ModBan.note.reported.contains(uuid) !=0) goto L37;
    net.ModBan.note.reported.add(uuid);
    net.ModBan.save();
    L37:
    return;
}

static void <clinit>() {
    net.ModBan.GSON = ?;
    return;
}

