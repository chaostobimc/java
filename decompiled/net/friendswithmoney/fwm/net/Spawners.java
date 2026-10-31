// class net/friendswithmoney/fwm/net/Spawners  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field data Lnet/friendswithmoney/fwm/net/SpawnerData;;
field state Lnet/friendswithmoney/fwm/net/Spawners$State;;
field fetchedAt J;
field lastAttempt J;
field inFlight Z;
private void <init>() {
    return;
}

public static net.SpawnerData data() {
    return net.Spawners.data;
}

public static net.Spawners$State state() {
    return net.Spawners.state;
}

public static long ageMinutes() {
    if (cmp(net.Spawners.fetchedAt,0) !=0) goto L12;
    return -1;
    L12:
    return ((System.currentTimeMillis() - net.Spawners.fetchedAt) / 60000);
}

public static void load() {
    v0 = net.Spawners.cacheFile();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L16;
    return;
    L16:
    v1 = ((net.Spawners$Snapshot)net.Spawners.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.Spawners$Snapshot.class));
    if (v1 == null) goto L66;
    if (v1.data == null) goto L66;
    net.Spawners.data = v1.data;
    net.Spawners.fetchedAt = v1.fetchedAt;
    net.Spawners.state = net.Spawners$State.OK;
    L66:
    goto L89;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L89:
    return;
}

public static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (net.Spawners.inFlight ==0) goto L7;
    return;
    L7:
    v1 = (((l)Math.max(1, config.FwmConfig.INSTANCE.spawnerRefreshMinutes)) * 60000);
    if (cmp((System.currentTimeMillis() - Math.max(net.Spawners.fetchedAt, net.Spawners.lastAttempt)),v1) >=0) goto L42;
    return;
    L42:
    net.Spawners.inFlight = 1;
    net.Spawners.lastAttempt = System.currentTimeMillis();
    if (net.Spawners.data != null) goto L64;
    net.Spawners.state = net.Spawners$State.LOADING;
    L64:
    net.FwmApi.getUrl(config.FwmConfig.INSTANCE.spawnerApiUrl, net.SpawnerData.class).whenCompleteAsync(/*indy*/accept(client), net.FwmApi.executor());
    return;
}

private static void save(net.SpawnerData value, long at) {
    v3 = net.Spawners.cacheFile();
    v4 = ?;
    v4.fetchedAt = at;
    v4.data = value;
    Files.createDirectories(v3.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v3, net.Spawners.GSON.toJson(v4), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L84;
    v4 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v3) + ': ' + v4.getMessage());
    L84:
    return;
}

private static Path cacheFile() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-spawners.json");
}

private static void lambda$tick$0(client.Minecraft client, net.SpawnerData result, Throwable error) {
    if (error != null) goto L15;
    if (result == null) goto L15;
    if (result.items != null) goto L49;
    L15:
    if (error != null) goto L27;
    goto L31;
    L27:
    L31:
    "empty response".println('[FWM] Spawner fetch failed: ' + error.getMessage());
    client.execute(/*indy*/run());
    return;
    L49:
    v3 = System.currentTimeMillis();
    net.Spawners.save(result, v3);
    client.execute(/*indy*/run(result, v3));
    return;
}

private static void lambda$tick$2(net.SpawnerData result, long now) {
    net.Spawners.inFlight = 0;
    net.Spawners.data = result;
    net.Spawners.fetchedAt = now;
    net.Spawners.state = net.Spawners$State.OK;
    return;
}

private static void lambda$tick$1() {
    net.Spawners.inFlight = 0;
    net.Spawners.state = net.Spawners$State.ERROR;
    return;
}

static Gson <clinit>() {
    net.Spawners.GSON = ?.setPrettyPrinting().create();
    net.Spawners.state = net.Spawners$State.EMPTY;
    return;
}

