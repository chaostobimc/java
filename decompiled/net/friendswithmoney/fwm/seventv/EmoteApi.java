// class net/friendswithmoney/fwm/seventv/EmoteApi  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field UA Ljava/lang/String;;
field SEVENTV_GQL Ljava/lang/String;;
field SEVENTV_QUERY Ljava/lang/String;;
field IO Ljava/util/concurrent/ExecutorService;;
field HTTP Ljava/net/http/HttpClient;;
private void <init>() {
    return;
}

public static concurrent.CompletableFuture searchSevenTv(String query, int page, int perPage) {
    v3 = ?;
    if (query == null) goto L19;
    if (query.isBlank() ==0) goto L29;
    L19:
    v3.add("q", null);
    goto L39;
    L29:
    v3.addProperty("q", query.trim());
    L39:
    v3.addProperty("page", Integer.valueOf(page));
    v3.addProperty("perPage", Integer.valueOf(perPage));
    v4 = ?;
    v4.addProperty("query", "query($q:String,$page:Int!,$perPage:Int!){emotes{search(query:$q,page:$page,perPage:$perPage,sort:{sortBy:TOP_ALL_TIME,order:DESCENDING}){items{id defaultName flags{animated}}}}}");
    v4.add("variables", v3);
    v5 = http.HttpRequest.newBuilder(URI.create("https://api.7tv.app/v4/gql")).timeout(Duration.ofSeconds(15)).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36").header("Content-Type", "application/json").POST(http.HttpRequest$BodyPublishers.ofString(v4.toString())).build();
    return seventv.EmoteApi.HTTP.sendAsync(v5, http.HttpResponse$BodyHandlers.ofString()).thenApply(/*indy*/apply());
}

public static concurrent.CompletableFuture searchBttv(String query, int offset, int limit) {
    if (query == null) goto L11;
    if (query.isBlank() ==0) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    v4 = 0;
    if (v4 ==0) goto L53;
    if (offset <=0) goto L38;
    return concurrent.CompletableFuture.completedFuture(?);
    L38:
    v3 = 'https://api.betterttv.net/3/emotes/shared/top?limit=' + Math.min(100, limit);
    goto L100;
    L53:
    v5 = query.trim();
    if (v5.length() >= 3) goto L79;
    return concurrent.CompletableFuture.completedFuture(?);
    L79:
    v3 = 'https://api.betterttv.net/3/emotes/shared/search?query=' + URLEncoder.encode(v5, StandardCharsets.UTF_8) + '&offset=' + offset + '&limit=' + Math.min(100, limit);
    L100:
    v5 = http.HttpRequest.newBuilder(URI.create(v3)).timeout(Duration.ofSeconds(15)).header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36").header("Accept", "application/json, text/plain, */*").header("Accept-Language", "de-DE,de;q=0.9,en;q=0.8").header("Origin", "https://betterttv.com").header("Referer", "https://betterttv.com/").GET().build();
    return seventv.EmoteApi.HTTP.sendAsync(v5, http.HttpResponse$BodyHandlers.ofString()).thenApply(/*indy*/apply());
}

public static concurrent.CompletableFuture lookupByName(String name) {
    return seventv.EmoteApi.searchSevenTv(name, 1, 25).exceptionally(/*indy*/apply()).thenCompose(/*indy*/apply(name));
}

private static boolean pick(List list, String name) {
    v2 = list.iterator();
    L7:
    if (v2.hasNext() ==0) goto L42;
    v3 = ((seventv.Emote)v2.next());
    if (v3.name().equals(name) ==0) goto L39;
    return v3;
    L39:
    goto L7;
    L42:
    v2 = name.toLowerCase(Locale.ROOT);
    v3 = list.iterator();
    L57:
    if (v3.hasNext() ==0) goto L101;
    v4 = ((seventv.Emote)v3.next());
    if (v4.name().toLowerCase(Locale.ROOT).equals(v2) ==0) goto L98;
    return v4;
    L98:
    goto L57;
    L101:
    return null;
}

public static concurrent.CompletableFuture download(String url) {
    if (url.contains("betterttv") ==0) goto L52;
    goto L54;
    L52:
    L54:
    v1 = "Referer".header("https://betterttv.com/", "https://7tv.app/").GET().build();
    return seventv.EmoteApi.HTTP.sendAsync(v1, http.HttpResponse$BodyHandlers.ofByteArray()).thenApply(/*indy*/apply(url));
}

private static JsonObject obj(JsonObject o, String key) {
    if (o == null) goto L23;
    if (o.has(key) ==0) goto L23;
    if (o.get(key).isJsonObject() !=0) goto L25;
    L23:
    return null;
    L25:
    return o.getAsJsonObject(key);
}

private static String str(JsonObject o, String key) {
    if (o == null) goto L23;
    if (o.has(key) ==0) goto L23;
    if (o.get(key).isJsonNull() ==0) goto L25;
    L23:
    return null;
    L25:
    return o.get(key).getAsString();
}

public static String lower(String s) {
    return s.toLowerCase(Locale.ROOT);
}

private static Object lambda$download$0(String url, http.HttpResponse resp) {
    if (resp.statusCode() == 200) goto L32;
    throw ?;
    L32:
    return (([B)resp.body());
}

private static concurrent.CompletableFuture lambda$lookupByName$1(String name, List seven) {
    v2 = seventv.EmoteApi.pick(seven, name);
    if (v2 == null) goto L15;
    return concurrent.CompletableFuture.completedFuture(v2);
    L15:
    return seventv.EmoteApi.searchBttv(name, 0, 25).exceptionally(/*indy*/apply()).thenApply(/*indy*/apply(name));
}

private static seventv.Emote lambda$lookupByName$3(String name, List b) {
    return seventv.EmoteApi.pick(b, name);
}

private static void lambda$lookupByName$2(Throwable t) {
    return ?;
}

private static void lambda$lookupByName$0(Throwable t) {
    return ?;
}

private static boolean lambda$searchBttv$0(http.HttpResponse resp) {
    v1 = ?;
    if (resp.statusCode() == 200) goto L106;
    if (resp.body() != null) goto L35;
    goto L44;
    L35:
    L44:
    v2 = ((String)resp.body());
    seventv.SevenTv.LOG.warn("BTTV-Suche fehlgeschlagen: HTTP {} {}", Integer.valueOf(resp.statusCode()), v2.substring(0, Math.min(200, v2.length())).replace(10, 32));
    throw ?;
    L106:
    v2 = JsonParser.parseString(((String)resp.body()));
    if (v2.isJsonArray() !=0) goto L128;
    return v1;
    L128:
    v3 = v2.getAsJsonArray();
    v4 = v3.iterator();
    L139:
    if (v4.hasNext() ==0) goto L323;
    v5 = ((JsonElement)v4.next());
    v6 = v5.getAsJsonObject();
    if (v6.has("emote") ==0) goto L203;
    if (v6.get("emote").isJsonObject() ==0) goto L203;
    v6 = v6.getAsJsonObject("emote");
    L203:
    v7 = seventv.EmoteApi.str(v6, "id");
    v8 = seventv.EmoteApi.str(v6, "code");
    if (v7 == null) goto L139;
    if (v8 != null) goto L236;
    goto L139;
    L236:
    v9 = seventv.EmoteApi.str(v6, "imageType");
    if (v6.has("animated") ==0) goto L285;
    if (v6.get("animated").isJsonNull() !=0) goto L285;
    goto L293;
    L285:
    L293:
    v10 = "gif".equalsIgnoreCase(v9);
    v1.add(?);
    goto L139;
    L323:
    return v1;
}

private static boolean lambda$searchSevenTv$0(http.HttpResponse resp) {
    v1 = ?;
    if (resp.statusCode() == 200) goto L59;
    seventv.SevenTv.LOG.warn("7TV-Suche fehlgeschlagen: HTTP {}", Integer.valueOf(resp.statusCode()));
    throw ?;
    L59:
    v2 = JsonParser.parseString(((String)resp.body())).getAsJsonObject();
    v3 = seventv.EmoteApi.obj(v2, "data");
    v4 = seventv.EmoteApi.obj(v3, "emotes");
    v5 = seventv.EmoteApi.obj(v4, "search");
    if (v5 == null) goto L118;
    if (v5.has("items") !=0) goto L120;
    L118:
    return v1;
    L120:
    v6 = v5.getAsJsonArray("items").iterator();
    L133:
    if (v6.hasNext() ==0) goto L291;
    v7 = ((JsonElement)v6.next());
    v8 = v7.getAsJsonObject();
    v9 = seventv.EmoteApi.str(v8, "id");
    v10 = seventv.EmoteApi.str(v8, "defaultName");
    if (v9 == null) goto L133;
    if (v10 != null) goto L195;
    goto L133;
    L195:
    v11 = 0;
    v12 = seventv.EmoteApi.obj(v8, "flags");
    if (v12 == null) goto L251;
    if (v12.has("animated") ==0) goto L251;
    if (v12.get("animated").isJsonNull() !=0) goto L251;
    v11 = v12.get("animated").getAsBoolean();
    L251:
    if (v11 ==0) goto L276;
    goto L279;
    L276:
    L279:
    ?.add(?);
    goto L133;
    L291:
    return v1;
}

private static void lambda$static$0(Runnable r) {
    v1 = ?;
    v1.setDaemon(1);
    return v1;
}

static http.HttpClient <clinit>() {
    seventv.EmoteApi.IO = concurrent.Executors.newFixedThreadPool(8, /*indy*/newThread());
    seventv.EmoteApi.HTTP = http.HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(10)).followRedirects(http.HttpClient$Redirect.NORMAL).executor(seventv.EmoteApi.IO).build();
    return;
}

