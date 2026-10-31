// class net/friendswithmoney/fwm/net/Giveaways  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field POLL_MS J;
field BACKOFF_MS [J;
field active Ljava/util/List;;
field recent Ljava/util/List;;
field clockOffset J;
field loaded Z;
field nextPoll J;
field failures I;
field running Z;
field seen Lnet/friendswithmoney/fwm/net/Giveaways$Seen;;
field JOIN_RESULT Ljava/util/Map;;
field JOINING Ljava/util/Set;;
field CARD_MS J;
private void <init>() {
    return;
}

public static List active() {
    return net.Giveaways.active;
}

public static List recent() {
    return net.Giveaways.recent;
}

public static boolean loaded() {
    return net.Giveaways.loaded;
}

public static long now() {
    return (System.currentTimeMillis() + net.Giveaways.clockOffset);
}

public static Object open() {
    v0 = net.Giveaways.now();
    v2 = 0;
    v3 = net.Giveaways.active.iterator();
    L15:
    if (v3.hasNext() ==0) goto L59;
    v4 = ((net.Giveaways$Active)v3.next());
    if (v4.joined !=0) goto L56;
    if (cmp(v4.endsAt,v0) <=0) goto L56;
    v2 = v2 + 1;
    L56:
    goto L15;
    L59:
    return v2;
}

public static Object joinResult(String id) {
    return ((String)net.Giveaways.JOIN_RESULT.get(id));
}

public static boolean joining(String id) {
    return net.Giveaways.JOINING.contains(id);
}

public static void tick(client.Minecraft client) {
    if (client.player == null) goto L14;
    if (net.Presence.onHugo(client) !=0) goto L15;
    L14:
    return;
    L15:
    v1 = System.currentTimeMillis();
    if (net.Giveaways.running !=0) goto L33;
    if (cmp(v1,net.Giveaways.nextPoll) >=0) goto L34;
    L33:
    return;
    L34:
    net.Giveaways.poll(client.player.getUUID());
    return;
}

public static void refresh() {
    v0 = client.Minecraft.getInstance();
    if (v0.player == null) goto L17;
    if (net.Giveaways.running ==0) goto L18;
    L17:
    return;
    L18:
    net.Giveaways.poll(v0.player.getUUID());
    return;
}

private static concurrent.CompletableFuture poll(UUID uuid) {
    net.Giveaways.running = 1;
    v1 = System.currentTimeMillis();
    net.FwmApi.get('/api/mod/giveaways?uuid=' + uuid.toString().replace("-", ""), net.Giveaways$Answer.class).whenComplete(/*indy*/accept(v1));
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-giveaways.json");
}

private static void seen() {
    if (net.Giveaways.seen == null) goto L10;
    return net.Giveaways.seen;
    L10:
    if (Files.exists(net.Giveaways.file(), new LinkOption[0]) ==0) goto L46;
    net.Giveaways.seen = ((net.Giveaways$Seen)net.Giveaways.GSON.fromJson(Files.readString(net.Giveaways.file(), StandardCharsets.UTF_8), net.Giveaways$Seen.class));
    L46:
    goto L50;
    v0 = /*catch Exception*/ ex;
    L50:
    if (net.Giveaways.seen != null) goto L66;
    net.Giveaways.seen = ?;
    L66:
    if (net.Giveaways.seen.announced != null) goto L88;
    net.Giveaways.seen.announced = ?;
    L88:
    if (net.Giveaways.seen.results != null) goto L110;
    net.Giveaways.seen.results = ?;
    L110:
    return net.Giveaways.seen;
}

private static void announce(List a, List r) {
    v2 = net.Giveaways.seen();
    v3 = ?;
    v4 = a.iterator();
    L20:
    if (v4.hasNext() ==0) goto L113;
    v5 = ((net.Giveaways$Active)v4.next());
    if (v2.announced.add(v5.id) ==0) goto L110;
    v3.add(?);
    L110:
    goto L20;
    L113:
    v4 = r.iterator();
    L121:
    if (v4.hasNext() ==0) goto L296;
    v5 = ((net.Giveaways$Recent)v4.next());
    v2.announced.add(v5.id);
    if (v5.joined ==0) goto L121;
    if (v2.results.add(v5.id) !=0) goto L186;
    goto L121;
    L186:
    if ("drawn".equals(v5.status) !=0) goto L203;
    goto L121;
    L203:
    if (v5.won ==0) goto L246;
    goto L287;
    L246:
    L287:
    ?.add(?);
    goto L121;
    L296:
    net.Giveaways.trim(v2.announced);
    net.Giveaways.trim(v2.results);
    if (v3.isEmpty() ==0) goto L320;
    return;
    L320:
    Files.writeString(net.Giveaways.file(), net.Giveaways.GSON.toJson(v2), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L346;
    v4 = /*catch Exception*/ ex;
    L346:
    v4 = v3.iterator();
    L354:
    if (v4.hasNext() ==0) goto L403;
    v5 = ((net.Giveaways$Card)v4.next());
    ui.FwmNotice.show(v5.head(), v5.title(), v5.line(), 15911244, 30000);
    goto L354;
    L403:
    return;
}

private static boolean trim(Set set) {
    L0:
    if (set.size() <= 200) goto L33;
    set.remove(set.iterator().next());
    goto L0;
    L33:
    return;
}

public static concurrent.CompletableFuture join(String id) {
    v1 = client.Minecraft.getInstance();
    if (v1.player == null) goto L23;
    if (net.Giveaways.JOINING.add(id) !=0) goto L24;
    L23:
    return;
    L24:
    v2 = ?;
    v2.uuid = v1.player.getUUID().toString().replace("-", "");
    v2.name = v1.player.getGameProfile().name();
    v2.modId = net.ModId.get();
    net.FwmApi.post('/api/mod/giveaways/' + id + '/join', v2, null, net.Giveaways$JoinAnswer.class).whenComplete(/*indy*/accept(id));
    return;
}

// DECOMPILE ERROR in explain: 'dict' object has no attribute 'op_type'
private static List lambda$join$0(String id, net.Giveaways$JoinAnswer answer, Throwable error) {
    net.Giveaways.JOINING.remove(id);
    if (error != null) goto L25;
    if (answer == null) goto L25;
    if (answer.result != null) goto L31;
    L25:
    goto L35;
    L31:
    L35:
    v3 = answer.result;
    net.Giveaways.JOIN_RESULT.put(id, v3);
    if ("joined".equals(v3) !=0) goto L67;
    if ("already_joined".equals(v3) ==0) goto L158;
    L67:
    v4 = ?;
    v5 = v4.iterator();
    L88:
    if (v5.hasNext() ==0) goto L150;
    v6 = ((net.Giveaways$Active)v5.next());
    if (v6.id.equals(id) !=0) goto L125;
    goto L88;
    L125:
    v6.joined = 1;
    if (answer.participants <=0) goto L147;
    v6.participants = answer.participants;
    L147:
    goto L88;
    L150:
    net.Giveaways.active = List.copyOf(v4);
    L158:
    return;
}

private static void lambda$poll$0(long sent, net.Giveaways$Answer a1, Throwable answer) {
    net.Giveaways.running = 0;
    v4 = System.currentTimeMillis();
    if (v3 != null) goto L17;
    if (answer != null) goto L48;
    L17:
    net.Giveaways.nextPoll = (v4 + net.Giveaways.BACKOFF_MS[Math.min(net.Giveaways.failures, (net.Giveaways.BACKOFF_MS.length - 1))]);
    net.Giveaways.failures = (net.Giveaways.failures + 1);
    return;
    L48:
    net.Giveaways.failures = 0;
    net.Giveaways.nextPoll = (v4 + 60000);
    if (cmp(answer.now,0) <=0) goto L86;
    net.Giveaways.clockOffset = (answer.now - ((sent + v4) / 2));
    L86:
    if (answer.active != null) goto L99;
    goto L106;
    L99:
    L106:
    v6 = List.copyOf(answer.active);
    if (answer.recent != null) goto L121;
    goto L128;
    L121:
    L128:
    v7 = List.copyOf(answer.recent);
    net.Giveaways.active = v6;
    net.Giveaways.recent = v7;
    net.Giveaways.loaded = 1;
    net.Giveaways.announce(v6, v7);
    return;
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    net.Giveaways.GSON = ?;
    new long[3][0] = 120000;
    new long[3][1] = 300000;
    new long[3][2] = 600000;
    net.Giveaways.BACKOFF_MS = new long[3];
    net.Giveaways.active = List.of();
    net.Giveaways.recent = List.of();
    net.Giveaways.JOIN_RESULT = ?;
    net.Giveaways.JOINING = concurrent.ConcurrentHashMap.newKeySet();
    return;
}

