// class net/friendswithmoney/fwm/net/Market  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field PAGE I;
field STALE_MS J;
field STATS_CACHE I;
field GSON Lcom/google/gson/Gson;;
field BY_PRICE Ljava/util/Comparator;;
field ITEMS Ljava/util/List;;
field ALL Ljava/util/List;;
field STATS Ljava/util/Map;;
field STATS_PENDING Ljava/util/Set;;
field STATS_FAILED Ljava/util/Set;;
field changes Ljava/util/Map;;
field avgPrices Ljava/util/Map;;
field changeSpan I;
field changesBusy Z;
field HISTORY Ljava/util/Map;;
field HISTORY_PENDING Ljava/util/Set;;
field HISTORY_FAILED Ljava/util/Set;;
field query Ljava/lang/String;;
field allState Lnet/friendswithmoney/fwm/net/Market$State;;
field allBusy Z;
field allFetchedAt J;
field searchState Lnet/friendswithmoney/fwm/net/Market$State;;
field searchBusy Z;
field searchTotal I;
field searchHasMore Z;
field searchFetchedAt J;
field generation I;
private void <init>() {
    return;
}

private static boolean searching() {
    if (net.Market.query.isBlank() !=0) goto L13;
    goto L14;
    L13:
    L14:
    return 0;
}

public static boolean state() {
    if (net.Market.searching() ==0) goto L12;
    goto L15;
    L12:
    L15:
    return net.Market.allState;
}

public static List items() {
    return net.Market.ITEMS;
}

public static int totalItems() {
    if (net.Market.searching() ==0) goto L12;
    goto L20;
    L12:
    L20:
    return net.Market.ALL.size();
}

public static boolean hasMore() {
    if (net.Market.searching() ==0) goto L16;
    if (net.Market.searchHasMore ==0) goto L16;
    goto L17;
    L16:
    L17:
    return 0;
}

public static boolean busy() {
    if (net.Market.searching() ==0) goto L12;
    goto L15;
    L12:
    L15:
    return net.Market.allBusy;
}

public static String query() {
    return net.Market.query;
}

public static long ageMinutes() {
    if (net.Market.searching() ==0) goto L12;
    goto L15;
    L12:
    L15:
    v0 = net.Market.allFetchedAt;
    if (cmp(v0,0) !=0) goto L26;
    return -1;
    L26:
    return ((System.currentTimeMillis() - v0) / 60000);
}

public static void openPage() {
    if (net.Market.changes.isEmpty() ==0) goto L14;
    net.Market.loadChanges();
    L14:
    if (net.Market.searching() ==0) goto L45;
    if (net.Market.searchBusy !=0) goto L44;
    if (net.Market.ITEMS.isEmpty() ==0) goto L44;
    net.Market.requestSearch(0, net.Market.query);
    L44:
    return;
    L45:
    net.Market.showAll();
    if (net.Market.ALL.isEmpty() !=0) goto L73;
    if (cmp((System.currentTimeMillis() - net.Market.allFetchedAt),300000) <0) goto L76;
    L73:
    net.Market.loadAll();
    L76:
    return;
}

public static void search(String text) {
    if (text != null) goto L9;
    goto L13;
    L9:
    L13:
    v1 = text.trim();
    if (v1.equals(net.Market.query) ==0) goto L25;
    return;
    L25:
    net.Market.query = v1;
    net.Market.generation = (net.Market.generation + 1);
    net.Market.searchBusy = 0;
    if (v1.isEmpty() ==0) goto L52;
    net.Market.openPage();
    return;
    L52:
    net.Market.searchTotal = 0;
    net.Market.searchHasMore = 0;
    net.Market.requestSearch(0, v1);
    return;
}

public static void refresh() {
    net.Market.HISTORY.clear();
    net.Market.HISTORY_FAILED.clear();
    net.Market.loadChanges();
    if (net.Market.searching() ==0) goto L39;
    if (net.Market.searchBusy !=0) goto L38;
    net.Market.requestSearch(0, net.Market.query);
    L38:
    return;
    L39:
    net.Market.loadAll();
    return;
}

public static void loadMore() {
    if (net.Market.searching() ==0) goto L18;
    if (net.Market.searchBusy !=0) goto L18;
    if (net.Market.searchHasMore !=0) goto L19;
    L18:
    return;
    L19:
    net.Market.requestSearch(net.Market.ITEMS.size(), net.Market.query);
    return;
}

private static boolean showAll() {
    net.Market.ITEMS.clear();
    net.Market.ITEMS.addAll(net.Market.ALL);
    return;
}

private static concurrent.CompletableFuture loadAll() {
    net.Market.loadChanges();
    if (net.Market.allBusy ==0) goto L10;
    return;
    L10:
    v0 = client.Minecraft.getInstance();
    net.Market.allBusy = 1;
    if (net.Market.ALL.isEmpty() ==0) goto L35;
    net.Market.allState = net.Market$State.LOADING;
    L35:
    net.FwmApi.getUrl(net.Market.overviewUrl(0, ""), net.MarketData.class).thenCompose(/*indy*/apply()).whenCompleteAsync(/*indy*/accept(v0), net.FwmApi.executor());
    return;
}

private static String key(net.MarketData$Item item) {
    if (item.itemKey != null) goto L12;
    goto L16;
    L12:
    L16:
    return item.itemKey;
}

private static concurrent.CompletableFuture requestSearch(int offset, String forQuery) {
    v2 = client.Minecraft.getInstance();
    net.Market.searchBusy = 1;
    if (offset !=0) goto L26;
    net.Market.ITEMS.clear();
    net.Market.searchState = net.Market$State.LOADING;
    L26:
    net.Market.generation = (net.Market.generation + 1);
    v3 = (net.Market.generation + 1);
    net.FwmApi.getUrl(net.Market.overviewUrl(offset, forQuery), net.MarketData.class).whenCompleteAsync(/*indy*/accept(v2, v3), net.FwmApi.executor());
    return;
}

public static Object change(String itemKey) {
    if (itemKey != null) goto L8;
    goto L20;
    L8:
    L20:
    return ((Double)net.Market.changes.get(itemKey));
}

public static Object avgPrice(String itemKey) {
    if (itemKey != null) goto L8;
    goto L20;
    L8:
    L20:
    return ((Double)net.Market.avgPrices.get(itemKey));
}

public static int changeSpan() {
    return net.Market.changeSpan;
}

private static concurrent.CompletableFuture loadChanges() {
    if (net.Market.changesBusy ==0) goto L7;
    return;
    L7:
    net.Market.changesBusy = 1;
    v0 = client.Minecraft.getInstance();
    net.FwmApi.getUrl(net.Market.historyBase() + '/changes', net.MarketHistoryData$Changes.class).whenCompleteAsync(/*indy*/accept(v0), net.FwmApi.executor());
    return;
}

public static Object history(String itemKey) {
    if (itemKey != null) goto L8;
    goto L20;
    L8:
    L20:
    return ((net.MarketHistoryData$History)net.Market.HISTORY.get(itemKey));
}

public static boolean historyFailed(String itemKey) {
    if (itemKey == null) goto L20;
    if (net.Market.HISTORY_FAILED.contains(itemKey) ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

public static concurrent.CompletableFuture requestHistory(String itemKey) {
    if (itemKey == null) goto L11;
    if (itemKey.isBlank() ==0) goto L12;
    L11:
    return;
    L12:
    if (net.Market.HISTORY.containsKey(itemKey) !=0) goto L36;
    if (net.Market.HISTORY_PENDING.contains(itemKey) ==0) goto L37;
    L36:
    return;
    L37:
    v1 = client.Minecraft.getInstance();
    net.Market.HISTORY_PENDING.add(itemKey);
    net.Market.HISTORY_FAILED.remove(itemKey);
    net.FwmApi.getUrl(net.Market.historyBase() + '/history?item=' + net.Market.encode(itemKey), net.MarketHistoryData$History.class).whenCompleteAsync(/*indy*/accept(v1, itemKey), net.FwmApi.executor());
    return;
}

private static String historyBase() {
    return config.FwmConfig.INSTANCE.marketHistoryUrl.replaceAll("/+$", "");
}

public static Object stats(String itemKey) {
    if (itemKey != null) goto L8;
    goto L20;
    L8:
    L20:
    return ((net.MarketStatsData)net.Market.STATS.get(itemKey));
}

public static boolean statsFailed(String itemKey) {
    if (itemKey == null) goto L20;
    if (net.Market.STATS_FAILED.contains(itemKey) ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

public static concurrent.CompletableFuture requestStats(String itemKey) {
    if (itemKey == null) goto L11;
    if (itemKey.isBlank() ==0) goto L12;
    L11:
    return;
    L12:
    if (net.Market.STATS.containsKey(itemKey) !=0) goto L36;
    if (net.Market.STATS_PENDING.contains(itemKey) ==0) goto L37;
    L36:
    return;
    L37:
    v1 = client.Minecraft.getInstance();
    net.Market.STATS_PENDING.add(itemKey);
    net.Market.STATS_FAILED.remove(itemKey);
    net.FwmApi.getUrl(net.Market.statsUrl(itemKey), net.MarketStatsData.class).whenCompleteAsync(/*indy*/accept(v1, itemKey), net.FwmApi.executor());
    return;
}

private static String base() {
    return config.FwmConfig.INSTANCE.marketApiUrl.replaceAll("/+$", "");
}

private static String overviewUrl(int offset, String forQuery) {
    v2 = ?.append("/overview?limit=").append(100).append("&offset=").append(Math.max(0, offset)).append("&sort=entries_desc");
    if (forQuery == null) goto L68;
    if (forQuery.isBlank() !=0) goto L68;
    v2.append("&q=").append(net.Market.encode(forQuery));
    L68:
    return v2.toString();
}

private static String statsUrl(String itemKey) {
    return net.Market.base() + '/items/' + net.Market.encode(itemKey) + '/stats';
}

private static String encode(String raw) {
    return URLEncoder.encode(raw, StandardCharsets.UTF_8).replace("+", "%20");
}

public static double number(String raw) {
    if (raw == null) goto L11;
    if (raw.isBlank() ==0) goto L15;
    L11:
    return nan;
    L15:
    return Double.parseDouble(raw.trim());
    v1 = /*catch NumberFormatException*/ ex;
    return nan;
}

public static long rounded(String raw) {
    v1 = net.Market.number(raw);
    if (Double.isNaN(v1) ==0) goto L16;
    goto L20;
    L16:
    L20:
    return Math.round(v1);
}

public static String lower(String raw) {
    if (raw != null) goto L9;
    goto L16;
    L9:
    L16:
    return raw.toLowerCase(Locale.ROOT);
}

public static void load() {
    v0 = net.Market.cacheFile();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L16;
    return;
    L16:
    v1 = ((net.Market$Snapshot)net.Market.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.Market$Snapshot.class));
    if (v1 == null) goto L59;
    if (v1.items == null) goto L59;
    if (v1.items.isEmpty() ==0) goto L60;
    L59:
    return;
    L60:
    net.Market.ALL.clear();
    net.Market.ALL.addAll(v1.items);
    net.Market.ALL.sort(net.Market.BY_PRICE);
    if (v1.complete ==0) goto L106;
    goto L107;
    L106:
    L107:
    net.Market.allFetchedAt = 0;
    net.Market.allState = net.Market$State.OK;
    net.Market.showAll();
    goto L142;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L142:
    return;
}

private static void save(List sorted) {
    v1 = ?;
    v1.fetchedAt = System.currentTimeMillis();
    v1.complete = 1;
    v1.items = sorted;
    v2 = net.Market.cacheFile();
    Files.createDirectories(v2.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v2, net.Market.GSON.toJson(v1), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L85;
    v3 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v2) + ': ' + v3.getMessage());
    L85:
    return;
}

private static Path cacheFile() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-market.json");
}

private static void lambda$requestStats$0(client.Minecraft client, String itemKey, net.MarketStatsData result, Throwable error) {
    client.execute(/*indy*/run(itemKey, error, result));
    return;
}

private static Object lambda$requestStats$1(String itemKey, Throwable error, net.MarketStatsData result) {
    net.Market.STATS_PENDING.remove(itemKey);
    if (error != null) goto L18;
    if (result != null) goto L55;
    L18:
    if (error != null) goto L32;
    goto L36;
    L32:
    L36:
    itemKey.println('[FWM] Market figures failed for ' + "empty response" + ': ' + error.getMessage());
    net.Market.STATS_FAILED.add(itemKey);
    return;
    L55:
    net.Market.STATS.put(itemKey, result);
    return;
}

private static void lambda$requestHistory$0(client.Minecraft client, String itemKey, net.MarketHistoryData$History result, Throwable error) {
    client.execute(/*indy*/run(itemKey, error, result));
    return;
}

private static Object lambda$requestHistory$1(String itemKey, Throwable error, net.MarketHistoryData$History result) {
    net.Market.HISTORY_PENDING.remove(itemKey);
    if (error != null) goto L18;
    if (result != null) goto L55;
    L18:
    if (error != null) goto L32;
    goto L36;
    L32:
    L36:
    itemKey.println('[FWM] Market history failed for ' + "empty response" + ': ' + error.getMessage());
    net.Market.HISTORY_FAILED.add(itemKey);
    return;
    L55:
    net.Market.HISTORY.put(itemKey, result);
    return;
}

private static void lambda$loadChanges$0(client.Minecraft client, net.MarketHistoryData$Changes result, Throwable error) {
    client.execute(/*indy*/run(error, result));
    return;
}

private static void lambda$loadChanges$1(Throwable error, net.MarketHistoryData$Changes result) {
    net.Market.changesBusy = 0;
    if (error != null) goto L19;
    if (result == null) goto L19;
    if (result.items != null) goto L45;
    L19:
    if (error != null) goto L32;
    goto L36;
    L32:
    L36:
    "empty response".println('[FWM] Market changes failed: ' + error.getMessage());
    return;
    L45:
    net.Market.changes = result.items;
    net.Market.changeSpan = result.span;
    if (result.prices != null) goto L72;
    goto L76;
    L72:
    L76:
    net.Market.avgPrices = result.prices;
    net.Market.ALL.sort(net.Market.BY_PRICE);
    if (net.Market.searching() !=0) goto L99;
    net.Market.showAll();
    L99:
    return;
}

private static void lambda$requestSearch$0(client.Minecraft client, int mine, net.MarketData result, Throwable error) {
    if (error != null) goto L15;
    if (result == null) goto L15;
    if (result.items != null) goto L19;
    L15:
    goto L20;
    L19:
    L20:
    v4 = 0;
    if (v4 ==0) goto L52;
    if (error != null) goto L40;
    goto L44;
    L40:
    L44:
    "empty response".println('[FWM] Market search failed: ' + error.getMessage());
    L52:
    client.execute(/*indy*/run(mine, v4, result));
    return;
}

private static long lambda$requestSearch$1(int mine, boolean failed, net.MarketData result) {
    if (mine == net.Market.generation) goto L8;
    return;
    L8:
    net.Market.searchBusy = 0;
    if (failed ==0) goto L40;
    if (net.Market.ITEMS.isEmpty() ==0) goto L33;
    goto L36;
    L33:
    L36:
    net.Market.searchState = net.Market$State.OK;
    return;
    L40:
    net.Market.ITEMS.addAll(result.list());
    if (result.meta == null) goto L83;
    net.Market.searchTotal = result.meta.totalItems;
    net.Market.searchHasMore = result.meta.hasMore;
    goto L87;
    L83:
    net.Market.searchHasMore = 0;
    L87:
    net.Market.searchState = net.Market$State.OK;
    net.Market.searchFetchedAt = System.currentTimeMillis();
    return;
}

private static void lambda$loadAll$2(client.Minecraft client, List sorted, Throwable error) {
    if (error == null) goto L22;
    System.err.println('[FWM] Market fetch failed: ' + error.getMessage());
    goto L26;
    L22:
    net.Market.save(sorted);
    L26:
    client.execute(/*indy*/run(error, sorted));
    return;
}

private static void lambda$loadAll$3(Throwable error, List sorted) {
    net.Market.allBusy = 0;
    if (error == null) goto L32;
    if (net.Market.ALL.isEmpty() ==0) goto L25;
    goto L28;
    L25:
    L28:
    net.Market.allState = net.Market$State.OK;
    return;
    L32:
    net.Market.ALL.clear();
    net.Market.ALL.addAll(sorted);
    net.Market.ALL.sort(net.Market.BY_PRICE);
    net.Market.allState = net.Market$State.OK;
    net.Market.allFetchedAt = System.currentTimeMillis();
    if (net.Market.searching() !=0) goto L82;
    net.Market.showAll();
    L82:
    return;
}

private static concurrent.CompletableFuture lambda$loadAll$0(net.MarketData first) {
    if (first == null) goto L11;
    if (first.items != null) goto L22;
    L11:
    throw ?;
    L22:
    if (first.meta != null) goto L41;
    goto L48;
    L41:
    L48:
    v1 = first.meta.totalItems;
    v2 = ?;
    v3 = first.list().size();
    L67:
    if (v3 >= v1) goto L96;
    v2.add(net.FwmApi.getUrl(net.Market.overviewUrl(v3, ""), net.MarketData.class));
    v3 = v3 + 100;
    goto L67;
    L96:
    return concurrent.CompletableFuture.allOf((([Ljava.util.concurrent.CompletableFuture;)v2.toArray(new concurrent.CompletableFuture[0]))).thenApply(/*indy*/apply(first, v2));
}

private static void lambda$loadAll$1(net.MarketData first, List rest, Void done) {
    v3 = ?;
    v4 = first.list().iterator();
    L19:
    if (v4.hasNext() ==0) goto L58;
    v5 = ((net.MarketData$Item)v4.next());
    v3.put(net.Market.key(v5), v5);
    goto L19;
    L58:
    v4 = rest.iterator();
    L66:
    if (v4.hasNext() ==0) goto L148;
    v5 = ((concurrent.CompletableFuture)v4.next());
    v6 = ((net.MarketData)v5.join()).list().iterator();
    L106:
    if (v6.hasNext() ==0) goto L145;
    v7 = ((net.MarketData$Item)v6.next());
    v3.putIfAbsent(net.Market.key(v7), v7);
    goto L106;
    L145:
    goto L66;
    L148:
    v4 = ?;
    v4.sort(net.Market.BY_PRICE);
    return v4;
}

private static boolean lambda$static$1(net.MarketData$Item item) {
    v1 = net.Market.avgPrice(item.itemKey);
    if (v1 == null) goto L17;
    return v1.doubleValue();
    L17:
    v2 = net.Market.number(item.highestUnitPrice24h);
    if (Double.isNaN(v2) ==0) goto L38;
    goto L39;
    L38:
    L39:
    return v2;
}

private static Double lambda$static$0(net.MarketData$Item item) {
    if (net.Market.avgPrice(item.itemKey) == null) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

static void <clinit>() {
    net.Market.GSON = ?;
    net.Market.BY_PRICE = Comparator.comparingInt(/*indy*/applyAsInt()).thenComparingDouble(/*indy*/applyAsDouble()).reversed();
    net.Market.ITEMS = ?;
    net.Market.ALL = ?;
    net.Market.STATS = ?;
    net.Market.STATS_PENDING = ?;
    net.Market.STATS_FAILED = ?;
    net.Market.changes = Map.of();
    net.Market.avgPrices = Map.of();
    net.Market.HISTORY = ?;
    net.Market.HISTORY_PENDING = ?;
    net.Market.HISTORY_FAILED = ?;
    net.Market.query = "";
    net.Market.allState = net.Market$State.EMPTY;
    net.Market.searchState = net.Market$State.EMPTY;
    return;
}

