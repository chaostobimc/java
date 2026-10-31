// class net/friendswithmoney/fwm/net/Members  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field data Lnet/friendswithmoney/fwm/net/MemberData;;
field state Lnet/friendswithmoney/fwm/net/Members$State;;
field byName Ljava/util/Map;;
field clanTags Ljava/util/Set;;
field RANKS Ljava/util/List;;
field LEADER_RANK I;
field fetchedAt J;
field MAX_AGE_MS J;
field lastAttempt J;
field inFlight Z;
private void <init>() {
    return;
}

public static String isMember(String accountName) {
    if (net.Members.role(accountName) == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public static Object member(String accountName) {
    if (accountName == null) goto L11;
    if (accountName.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    if (cmp(net.Members.fetchedAt,0) <=0) goto L35;
    if (cmp((System.currentTimeMillis() - net.Members.fetchedAt),172800000) <=0) goto L37;
    L35:
    return null;
    L37:
    return ((net.MemberData$Member)net.Members.byName.get(accountName.toLowerCase(Locale.ROOT)));
}

public static net.MemberData$Member role(String accountName) {
    v1 = net.Members.member(accountName);
    if (v1 != null) goto L13;
    goto L17;
    L13:
    L17:
    return v1.role;
}

public static int clanOrder(String clan) {
    if (clan != null) goto L9;
    goto L19;
    L9:
    L19:
    v1 = clan.trim().toUpperCase(Locale.ROOT);
    if (v1.equals("FWM") ==0) goto L31;
    return 0;
    L31:
    if (v1.equals("FWM$") ==0) goto L42;
    return 1;
    L42:
    if (v1.matches("FWM[0-9]{1,3}") ==0) goto L62;
    return (2 + Integer.parseInt(v1.substring(3)));
    L62:
    return 10000;
}

public static int rankIndex(String role) {
    if (role != null) goto L13;
    return net.Members.RANKS.size();
    L13:
    v1 = net.Members.normalize(role);
    v2 = 0;
    L20:
    if (v2 >= net.Members.RANKS.size()) goto L59;
    if (v1.startsWith(((String)net.Members.RANKS.get(v2))) ==0) goto L53;
    return v2;
    L53:
    v2 = v2 + 1;
    goto L20;
    L59:
    return net.Members.RANKS.size();
}

private static String normalize(String role) {
    return role.toLowerCase(Locale.ROOT).trim().replaceAll("[\\s_-]", "");
}

public static List list() {
    if (net.Members.data == null) goto L15;
    if (net.Members.data.members != null) goto L21;
    L15:
    goto L27;
    L21:
    L27:
    return net.Members.data.members;
}

public static net.Members$State state() {
    return net.Members.state;
}

public static long ageMinutes() {
    if (cmp(net.Members.fetchedAt,0) !=0) goto L12;
    return -1;
    L12:
    return ((System.currentTimeMillis() - net.Members.fetchedAt) / 60000);
}

public static void load() {
    v0 = net.Members.cacheFile();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L16;
    return;
    L16:
    v1 = ((net.Members$Snapshot)net.Members.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.Members$Snapshot.class));
    if (v1 == null) goto L57;
    if (v1.data == null) goto L57;
    net.Members.apply(v1.data, v1.fetchedAt);
    L57:
    goto L80;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L80:
    return;
}

public static concurrent.CompletableFuture tick(client.Minecraft client) {
    if (net.Members.inFlight ==0) goto L7;
    return;
    L7:
    v1 = (((l)Math.max(1, config.FwmConfig.INSTANCE.memberRefreshMinutes)) * 60000);
    if (cmp((System.currentTimeMillis() - Math.max(net.Members.fetchedAt, net.Members.lastAttempt)),v1) >=0) goto L42;
    return;
    L42:
    net.Members.inFlight = 1;
    net.Members.lastAttempt = System.currentTimeMillis();
    if (net.Members.data != null) goto L64;
    net.Members.state = net.Members$State.LOADING;
    L64:
    net.FwmApi.getUrl(config.FwmConfig.INSTANCE.membersApiUrl, JsonObject.class).thenApply(/*indy*/apply()).whenCompleteAsync(/*indy*/accept(client), net.FwmApi.executor());
    return;
}

private static boolean parse(JsonObject root) {
    if (root != null) goto L15;
    throw ?;
    L15:
    if (root.has("ok") ==0) goto L62;
    if (root.get("ok").isJsonPrimitive() ==0) goto L62;
    if (root.get("ok").getAsBoolean() !=0) goto L62;
    throw ?;
    L62:
    v1 = ?;
    v2 = root.entrySet().iterator();
    L80:
    if (v2.hasNext() ==0) goto L286;
    v3 = ((Map$Entry)v2.next());
    if (((JsonElement)v3.getValue()).isJsonArray() !=0) goto L117;
    goto L80;
    L117:
    v4 = ((String)v3.getKey()).toUpperCase(Locale.ROOT);
    v1.clans.add(v4);
    v5 = ((JsonElement)v3.getValue()).getAsJsonArray().iterator();
    L163:
    if (v5.hasNext() ==0) goto L283;
    v6 = ((JsonElement)v5.next());
    if (v6.isJsonObject() !=0) goto L196;
    goto L163;
    L196:
    v7 = v6.getAsJsonObject();
    v8 = net.Members.string(v7, "mcName");
    if (v8 == null) goto L163;
    if (v8.isBlank() ==0) goto L229;
    goto L163;
    L229:
    v9 = ?;
    v9.name = v8.trim();
    v9.role = net.Members.string(v7, "rolle");
    v9.clan = v4;
    v1.members.add(v9);
    goto L163;
    L283:
    goto L80;
    L286:
    return v1;
}

private static String string(JsonObject object, String key) {
    v2 = object.get(key);
    if (v2 == null) goto L17;
    if (v2.isJsonPrimitive() !=0) goto L21;
    L17:
    goto L25;
    L21:
    L25:
    return v2.getAsString();
}

private static Set apply(net.MemberData value, long at) {
    v3 = ?;
    if (value.members != null) goto L25;
    goto L29;
    L25:
    L29:
    v4 = value.members.iterator();
    L36:
    if (v4.hasNext() ==0) goto L185;
    v5 = ((net.MemberData$Member)v4.next());
    if (v5 == null) goto L36;
    if (v5.name == null) goto L36;
    if (v5.name.isBlank() ==0) goto L85;
    goto L36;
    L85:
    if (v5.role == null) goto L104;
    if (v5.role.isBlank() ==0) goto L112;
    L104:
    v5.role = "Mitglied";
    L112:
    if (v5.role.trim().equalsIgnoreCase("Kapital") ==0) goto L137;
    v5.role = "Kapitalstratege";
    L137:
    if (net.Members.normalize(v5.role).equals("headclanrat") ==0) goto L162;
    v5.role = "Head-Clanrat";
    L162:
    v3.put(v5.name.toLowerCase(Locale.ROOT), v5);
    goto L36;
    L185:
    v4 = ?;
    if (value.clans == null) goto L269;
    v5 = value.clans.iterator();
    L212:
    if (v5.hasNext() ==0) goto L269;
    v6 = ((String)v5.next());
    if (v6 == null) goto L266;
    if (v6.isBlank() !=0) goto L266;
    v4.add(v6.trim().toUpperCase(Locale.ROOT));
    L266:
    goto L212;
    L269:
    if (v4.isEmpty() ==0) goto L354;
    if (value.members == null) goto L354;
    v5 = value.members.iterator();
    L297:
    if (v5.hasNext() ==0) goto L354;
    v6 = ((net.MemberData$Member)v5.next());
    if (v6 == null) goto L351;
    if (v6.clan == null) goto L351;
    v4.add(v6.clan.toUpperCase(Locale.ROOT));
    L351:
    goto L297;
    L354:
    net.Members.data = value;
    net.Members.byName = Map.copyOf(v3);
    net.Members.clanTags = Set.copyOf(v4);
    net.Members.fetchedAt = at;
    net.Members.state = net.Members$State.OK;
    return;
}

public static Set clanTags() {
    return net.Members.clanTags;
}

private static void save(net.MemberData value, long at) {
    v3 = net.Members.cacheFile();
    v4 = ?;
    v4.fetchedAt = at;
    v4.data = value;
    Files.createDirectories(v3.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v3, net.Members.GSON.toJson(v4), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L84;
    v4 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v3) + ': ' + v4.getMessage());
    L84:
    return;
}

private static Path cacheFile() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-members.json");
}

private static void lambda$tick$0(client.Minecraft client, net.MemberData result, Throwable error) {
    if (error != null) goto L20;
    if (result == null) goto L20;
    if (result.members.isEmpty() ==0) goto L55;
    L20:
    if (error != null) goto L33;
    goto L37;
    L33:
    L37:
    "empty list".println('[FWM] Member fetch failed: ' + error.getMessage());
    client.execute(/*indy*/run());
    return;
    L55:
    v3 = System.currentTimeMillis();
    net.Members.save(result, v3);
    client.execute(/*indy*/run(result, v3));
    return;
}

private static void lambda$tick$2(net.MemberData result, long now) {
    net.Members.inFlight = 0;
    net.Members.apply(result, now);
    return;
}

private static void lambda$tick$1() {
    net.Members.inFlight = 0;
    net.Members.state = net.Members$State.ERROR;
    return;
}

static int <clinit>() {
    net.Members.GSON = ?.setPrettyPrinting().create();
    net.Members.state = net.Members$State.EMPTY;
    net.Members.byName = Map.of();
    net.Members.clanTags = Set.of();
    new String[13][0] = "management";
    new String[13][1] = "headclanrat";
    new String[13][2] = "clanrat";
    new String[13][3] = "leader";
    new String[13][4] = "titan";
    new String[13][5] = "marktlegende";
    new String[13][6] = "kapitalf\u00fcrst";
    new String[13][7] = "elite";
    new String[13][8] = "kapitalstratege";
    new String[13][9] = "investor";
    new String[13][10] = "anteilseigner";
    new String[13][11] = "kassenfreund";
    new String[13][12] = "mitglied";
    net.Members.RANKS = List.of(new String[13]);
    net.Members.LEADER_RANK = net.Members.RANKS.indexOf("leader");
    return;
}

