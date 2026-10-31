// class net/friendswithmoney/fwm/net/FwmApi  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field EXECUTOR Ljava/util/concurrent/Executor;;
field CLIENT Ljava/net/http/HttpClient;;
field GSON Lcom/google/gson/Gson;;
field BOT_API Ljava/lang/String;;
public static concurrent.Executor executor() {
    return net.FwmApi.EXECUTOR;
}

private void <init>() {
    return;
}

public static concurrent.CompletableFuture request(String method, String url, String ifNoneMatch, String bearer, Object jsonBody, long maxBytes) {
    v7 = URI.create(url);
    goto L17;
    v8 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v8);
    L17:
    v8 = http.HttpRequest.newBuilder(v7).timeout(Duration.ofSeconds(10)).header("Accept", "application/json, image/png").header("User-Agent", 'FWM-Mod/' + FwmClient.MOD_VERSION);
    if (ifNoneMatch == null) goto L81;
    if (ifNoneMatch.isBlank() !=0) goto L81;
    v8.header("If-None-Match", ifNoneMatch);
    L81:
    if (bearer == null) goto L108;
    if (bearer.isBlank() !=0) goto L108;
    v8.header("Authorization", 'Bearer ' + bearer);
    L108:
    if (jsonBody == null) goto L148;
    v8.header("Content-Type", "application/json").method(method, http.HttpRequest$BodyPublishers.ofString(net.FwmApi.GSON.toJson(jsonBody), StandardCharsets.UTF_8));
    goto L160;
    L148:
    v8.method(method, http.HttpRequest$BodyPublishers.noBody());
    L160:
    return net.FwmApi.CLIENT.sendAsync(v8.build(), http.HttpResponse$BodyHandlers.ofByteArray()).thenApply(/*indy*/apply(maxBytes));
}

public static concurrent.CompletableFuture get(String path, Class type) {
    return net.FwmApi.getUrl(config.FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + path, type);
}

public static concurrent.CompletableFuture getUrlStreamed(String url, net.FwmApi$Parser parser) {
    v2 = URI.create(url);
    goto L14;
    v3 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v3);
    L14:
    v3 = http.HttpRequest.newBuilder(v2).timeout(Duration.ofSeconds(60)).header("Accept", "application/json").header("Accept-Encoding", "gzip").header("User-Agent", 'FWM-Mod/' + FwmClient.MOD_VERSION).GET().build();
    return net.FwmApi.CLIENT.sendAsync(v3, http.HttpResponse$BodyHandlers.ofInputStream()).thenApply(/*indy*/apply(parser));
}

public static concurrent.CompletableFuture getBytes(String url) {
    v1 = URI.create(url);
    goto L14;
    v2 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v2);
    L14:
    v2 = http.HttpRequest.newBuilder(v1).timeout(Duration.ofSeconds(10)).header("User-Agent", 'FWM-Mod/' + FwmClient.MOD_VERSION).GET().build();
    return net.FwmApi.CLIENT.sendAsync(v2, http.HttpResponse$BodyHandlers.ofByteArray()).thenApply(/*indy*/apply());
}

public static concurrent.CompletableFuture post(String path, Object body, String token, Class type) {
    v4 = URI.create(config.FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + path);
    goto L35;
    v5 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v5);
    L35:
    v5 = http.HttpRequest.newBuilder(v4).timeout(Duration.ofSeconds(10)).header("Accept", "application/json").header("Content-Type", "application/json").header("User-Agent", "FWM-Mod").POST(http.HttpRequest$BodyPublishers.ofString(net.FwmApi.GSON.toJson(body), StandardCharsets.UTF_8));
    if (token == null) goto L118;
    v5.header("Authorization", 'Bearer ' + token);
    L118:
    return net.FwmApi.CLIENT.sendAsync(v5.build(), http.HttpResponse$BodyHandlers.ofString()).thenApply(/*indy*/apply(type));
}

public static concurrent.CompletableFuture postUrl(String url, Object body, Class type) {
    v3 = URI.create(url);
    goto L16;
    v4 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v4);
    L16:
    v4 = http.HttpRequest.newBuilder(v3).timeout(Duration.ofSeconds(10)).header("Accept", "application/json").header("Content-Type", "application/json").POST(http.HttpRequest$BodyPublishers.ofString(net.FwmApi.GSON.toJson(body), StandardCharsets.UTF_8)).build();
    return net.FwmApi.CLIENT.sendAsync(v4, http.HttpResponse$BodyHandlers.ofString()).thenApply(/*indy*/apply(type));
}

public static concurrent.CompletableFuture getUrl(String url, Class type) {
    v2 = URI.create(url);
    goto L14;
    v3 = /*catch Exception*/ ex;
    return concurrent.CompletableFuture.failedFuture(v3);
    L14:
    v3 = http.HttpRequest.newBuilder(v2).timeout(Duration.ofSeconds(10)).header("Accept", "application/json").header("User-Agent", 'FWM-Mod/' + FwmClient.MOD_VERSION).GET().build();
    return net.FwmApi.CLIENT.sendAsync(v3, http.HttpResponse$BodyHandlers.ofString()).thenApply(/*indy*/apply(type));
}

private static void lambda$getUrl$0(Class type, http.HttpResponse response) {
    if ((response.statusCode() / 100) == 2) goto L32;
    throw ?;
    L32:
    v2 = net.FwmApi.GSON.fromJson(((String)response.body()), type);
    if (v2 != null) goto L63;
    throw ?;
    L63:
    return v2;
}

private static void lambda$postUrl$0(Class type, http.HttpResponse response) {
    if ((response.statusCode() / 100) == 2) goto L27;
    throw ?;
    L27:
    v2 = net.FwmApi.GSON.fromJson(((String)response.body()), type);
    if (v2 != null) goto L58;
    throw ?;
    L58:
    return v2;
}

private static void lambda$post$0(Class type, http.HttpResponse response) {
    if ((response.statusCode() / 100) == 2) goto L27;
    throw ?;
    L27:
    v2 = net.FwmApi.GSON.fromJson(((String)response.body()), type);
    if (v2 != null) goto L58;
    throw ?;
    L58:
    return v2;
}

private static Object lambda$getBytes$0(http.HttpResponse response) {
    if ((response.statusCode() / 100) == 2) goto L32;
    throw ?;
    L32:
    return (([B)response.body());
}

private static void lambda$getUrlStreamed$0(net.FwmApi$Parser parser, http.HttpResponse response) {
    if ((response.statusCode() / 100) == 2) goto L32;
    throw ?;
    L32:
    v2 = ((Boolean)response.headers().firstValue("content-encoding").map(/*indy*/apply()).orElse(Boolean.valueOf(0))).booleanValue();
    v3 = ((InputStream)response.body());
    if (v2 ==0) goto L93;
    goto L94;
    L93:
    L94:
    v4 = v3;
    v5 = ?;
    v6 = parser.parse(v5);
    v5.close();
    if (v4 == null) goto L142;
    v4.close();
    L142:
    if (v3 == null) goto L150;
    v3.close();
    L150:
    return v6;
    v6 = /*catch Throwable*/ ex;
    v5.close();
    goto L172;
    v7 = /*catch Throwable*/ ex;
    v6.addSuppressed(v7);
    L172:
    throw v6;
    v5 = /*catch Throwable*/ ex;
    if (v4 == null) goto L199;
    v4.close();
    goto L199;
    v6 = /*catch Throwable*/ ex;
    v5.addSuppressed(v6);
    L199:
    throw v5;
    v4 = /*catch Throwable*/ ex;
    if (v3 == null) goto L224;
    v3.close();
    goto L224;
    v5 = /*catch Throwable*/ ex;
    v4.addSuppressed(v5);
    L224:
    throw v4;
    v3 = /*catch IOException*/ ex;
    throw ?;
}

private static Boolean lambda$getUrlStreamed$1(String value) {
    return Boolean.valueOf(value.toLowerCase(Locale.ROOT).contains("gzip"));
}

private static void lambda$request$0(long maxBytes, http.HttpResponse a1) {
    v3 = ((String)v2.headers().firstValue("etag").orElse(null));
    v4 = 0;
    v4 = Long.parseLong(((String)v2.headers().firstValue("retry-after").orElse("0")).trim());
    goto L62;
    v6 = /*catch NumberFormatException*/ ex;
    v4 = 60;
    L62:
    if (v2.statusCode() != 304) goto L78;
    goto L87;
    L78:
    L87:
    v6 = (([B)v2.body());
    if (v6 == null) goto L112;
    if (cmp(maxBytes,0) <=0) goto L112;
    if (cmp(((l)v6.length),maxBytes) <=0) goto L112;
    v6 = null;
    L112:
    return ?;
}

private static void lambda$static$0(Runnable runnable) {
    v1 = ?;
    v1.setDaemon(1);
    return v1;
}

static void <clinit>() {
    net.FwmApi.EXECUTOR = concurrent.Executors.newSingleThreadExecutor(/*indy*/newThread());
    net.FwmApi.CLIENT = http.HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(5)).followRedirects(http.HttpClient$Redirect.NORMAL).executor(net.FwmApi.EXECUTOR).build();
    net.FwmApi.GSON = ?;
    return;
}

