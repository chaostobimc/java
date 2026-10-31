// class net/friendswithmoney/fwm/net/ModAccount  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field key Ljava/lang/String;;
field state Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field minecraftName Ljava/lang/String;;
field message Ljava/lang/String;;
private void <init>() {
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-key.json");
}

public static void load() {
    if (Files.exists(net.ModAccount.file(), new LinkOption[0]) !=0) goto L14;
    return;
    L14:
    v0 = ((net.ModAccount$Stored)net.ModAccount.GSON.fromJson(Files.readString(net.ModAccount.file(), StandardCharsets.UTF_8), net.ModAccount$Stored.class));
    if (v0 == null) goto L62;
    if (net.ModAccount.valid(v0.key) ==0) goto L62;
    net.ModAccount.key = v0.key.trim();
    net.ModAccount.check();
    L62:
    goto L66;
    v0 = /*catch Exception*/ ex;
    L66:
    return;
}

public static boolean valid(String k) {
    if (k == null) goto L20;
    if (k.trim().matches("fwm_[A-Za-z0-9_-]{16,200}") ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

public static boolean hasKey() {
    if (net.ModAccount.key == null) goto L10;
    goto L11;
    L10:
    L11:
    return 0;
}

public static net.ModAccount$State state() {
    return net.ModAccount.state;
}

public static String minecraftName() {
    return net.ModAccount.minecraftName;
}

public static String message() {
    return net.ModAccount.message;
}

public static String hint() {
    v0 = net.ModAccount.key;
    if (v0 == null) goto L17;
    if (v0.length() >= 8) goto L22;
    L17:
    goto L37;
    L22:
    L37:
    return 'fwm_…' + v0.substring((v0.length() - 4));
}

public static void setKey(String k) {
    if (net.ModAccount.valid(k) !=0) goto L19;
    net.ModAccount.state = net.ModAccount$State.INVALID;
    net.ModAccount.message = "Das ist kein FWM-Key (beginnt mit fwm_).";
    return;
    L19:
    net.ModAccount.key = k.trim();
    net.ModAccount.save();
    net.ModAccount.check();
    return;
}

public static boolean forget() {
    net.ModAccount.key = null;
    net.ModAccount.minecraftName = null;
    net.ModAccount.state = net.ModAccount$State.NONE;
    net.ModAccount.message = "";
    Files.deleteIfExists(net.ModAccount.file());
    goto L30;
    v0 = /*catch Exception*/ ex;
    L30:
    return;
}

private static Path save() {
    v0 = ?;
    v0.key = net.ModAccount.key;
    Files.writeString(net.ModAccount.file(), net.ModAccount.GSON.toJson(v0), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L40;
    v0 = /*catch Exception*/ ex;
    L40:
    return;
}

public static concurrent.CompletableFuture check() {
    if (net.ModAccount.key != null) goto L7;
    return;
    L7:
    net.ModAccount.state = net.ModAccount$State.CHECKING;
    net.ModAccount.message = "";
    net.ModAccount.call("GET", "/mod/v1/key/check", null).whenComplete(/*indy*/accept());
    return;
}

public static concurrent.CompletableFuture call(String method, String path, Object body) {
    v3 = net.ModAccount.key;
    if (v3 != null) goto L21;
    return concurrent.CompletableFuture.failedFuture(?);
    L21:
    return net.FwmApi.request(method, 'https://api.friendswithmoney.net' + path, null, v3, body, 1000000).thenApply(/*indy*/apply());
}

static Object parse(net.FwmApi$Fetched got, Class type) {
    return got.json(type);
    v2 = /*catch Exception*/ ex;
    return null;
}

// DECOMPILE ERROR in errorText: 'dict' object has no attribute 'op_type'
private static void lambda$call$0(net.FwmApi$Fetched got) {
    if (got.status() != 401) goto L24;
    net.ModAccount.forget();
    net.ModAccount.state = net.ModAccount$State.INVALID;
    net.ModAccount.message = "Key ung\u00c3\u00bcltig oder ersetzt \u2013 im Discord-Kanal \ud83e\udd16\u1d00\u1d18\u00c9\u00aa einen neuen generieren.";
    L24:
    return got;
}

private static String lambda$check$0(net.FwmApi$Fetched got, Throwable error) {
    if (error != null) goto L8;
    if (got != null) goto L20;
    L8:
    net.ModAccount.state = net.ModAccount$State.ERROR;
    net.ModAccount.message = "Keine Verbindung zur FWM-API.";
    return;
    L20:
    if (got.status() != 401) goto L31;
    return;
    L31:
    v2 = ((net.ModAccount$Check)net.ModAccount.parse(got, net.ModAccount$Check.class));
    if (got.status() != 200) goto L90;
    if (v2 == null) goto L90;
    if (v2.ok ==0) goto L90;
    if (v2.verified ==0) goto L90;
    net.ModAccount.minecraftName = v2.minecraftName;
    net.ModAccount.state = net.ModAccount$State.OK;
    net.ModAccount.message = "";
    goto L103;
    L90:
    net.ModAccount.state = net.ModAccount$State.ERROR;
    net.ModAccount.message = net.ModAccount.errorText(got);
    L103:
    return;
}

static void <clinit>() {
    net.ModAccount.GSON = ?;
    net.ModAccount.state = net.ModAccount$State.NONE;
    net.ModAccount.message = "";
    return;
}

