// class net/friendswithmoney/fwm/net/MarketPrices  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REFRESH_MS J;
field RETRY_MS J;
field items Ljava/util/Map;;
field byName Ljava/util/Map;;
field busy Z;
field nextLoad J;
field requests I;
private void <init>() {
    return;
}

public static concurrent.CompletableFuture ensure() {
    if (config.Features.isOn("fwm.feature.ahprice") !=0) goto L9;
    return;
    L9:
    if (net.MarketPrices.busy !=0) goto L25;
    if (cmp(System.currentTimeMillis(),net.MarketPrices.nextLoad) >=0) goto L26;
    L25:
    return;
    L26:
    net.MarketPrices.busy = 1;
    net.MarketPrices.requests = (net.MarketPrices.requests + 1);
    v0 = client.Minecraft.getInstance();
    net.FwmApi.getUrl(net.MarketPrices.base() + '/prices', net.MarketPrices$Data.class).whenCompleteAsync(/*indy*/accept(v0), net.FwmApi.executor());
    return;
}

public static Object get(String key) {
    if (key != null) goto L8;
    goto L20;
    L8:
    L20:
    return (([Ljava.lang.Double;)net.MarketPrices.items.get(key));
}

public static Object keyForName(String displayName) {
    if (displayName != null) goto L8;
    goto L23;
    L8:
    L23:
    return ((String)net.MarketPrices.byName.get(net.MarketPrices.normalize(displayName)));
}

public static int requests() {
    return net.MarketPrices.requests;
}

private static String normalize(String name) {
    return name.replaceAll("\u00a7.", "").trim().toLowerCase(Locale.ROOT);
}

private static String base() {
    return config.FwmConfig.INSTANCE.marketHistoryUrl.replaceAll("/+$", "");
}

private static void lambda$ensure$0(client.Minecraft client, net.MarketPrices$Data result, Throwable error) {
    client.execute(/*indy*/run(error, result));
    return;
}

private static long lambda$ensure$1(Throwable error, net.MarketPrices$Data result) {
    net.MarketPrices.busy = 0;
    if (error != null) goto L19;
    if (result == null) goto L19;
    if (result.items != null) goto L54;
    L19:
    if (error != null) goto L31;
    goto L35;
    L31:
    L35:
    "empty response".println('[FWM] Market prices failed: ' + error.getMessage());
    net.MarketPrices.nextLoad = (System.currentTimeMillis() + 600000);
    return;
    L54:
    net.MarketPrices.items = result.items;
    v2 = ?;
    if (result.names == null) goto L91;
    result.names.forEach(/*indy*/accept(v2));
    L91:
    net.MarketPrices.byName = v2;
    net.MarketPrices.nextLoad = (System.currentTimeMillis() + 3600000);
    return;
}

private static Object lambda$ensure$2(Map names, String key, String name) {
    names.put(net.MarketPrices.normalize(name), key);
    return;
}

static Map <clinit>() {
    net.MarketPrices.items = Map.of();
    net.MarketPrices.byName = Map.of();
    return;
}

