// class net/friendswithmoney/fwm/net/Tickets  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field POLL_MS J;
field spawners Ljava/util/List;;
field spawnerError Ljava/lang/String;;
field tickets Ljava/util/List;;
field nextPoll J;
field pausedUntil J;
field SEEN Ljava/util/Map;;
field MESSAGES Ljava/util/Map;;
field AFTER Ljava/util/Map;;
field LOADING Ljava/util/Set;;
field watched Ljava/lang/String;;
field nextWatch J;
field WATCH_MS J;
private void <init>() {
    return;
}

public static List spawners() {
    return net.Tickets.spawners;
}

public static String spawnerError() {
    return net.Tickets.spawnerError;
}

public static List tickets() {
    return net.Tickets.tickets;
}

public static Object messages(String ticketId) {
    return ((List)net.Tickets.MESSAGES.getOrDefault(ticketId, List.of()));
}

public static boolean loading(String ticketId) {
    return net.Tickets.LOADING.contains(ticketId);
}

public static void watch(String ticketId) {
    net.Tickets.watched = ticketId;
    net.Tickets.nextWatch = 0;
    return;
}

public static concurrent.CompletableFuture loadMessages(String ticketId, boolean quiet) {
    if (ticketId == null) goto L32;
    if (net.ModAccount.hasKey() ==0) goto L32;
    if (cmp(System.currentTimeMillis(),net.Tickets.pausedUntil) <0) goto L32;
    if (net.Tickets.LOADING.add(ticketId) !=0) goto L33;
    L32:
    return;
    L33:
    v2 = ((String)net.Tickets.AFTER.get(ticketId));
    if (v2 != null) goto L56;
    goto L62;
    L56:
    L62:
    v3 = '/mod/v1/tickets/' + "" + '/messages?limit=50' + '&after=' + v2;
    net.ModAccount.call("GET", v3, null).whenComplete(/*indy*/accept(ticketId, quiet));
    return;
}

public static boolean hasLive(String type) {
    v1 = net.Tickets.tickets.iterator();
    L9:
    if (v1.hasNext() ==0) goto L51;
    v2 = ((net.Tickets$Ticket)v1.next());
    if (type.equals(v2.type) ==0) goto L48;
    if (v2.live() ==0) goto L48;
    return 1;
    L48:
    goto L9;
    L51:
    return 0;
}

public static concurrent.CompletableFuture loadSpawners() {
    if (net.ModAccount.hasKey() ==0) goto L16;
    if (cmp(System.currentTimeMillis(),net.Tickets.pausedUntil) >=0) goto L17;
    L16:
    return;
    L17:
    net.ModAccount.call("GET", "/mod/v1/spawners", null).whenComplete(/*indy*/accept());
    return;
}

public static concurrent.CompletableFuture refresh() {
    if (net.ModAccount.hasKey() ==0) goto L16;
    if (cmp(System.currentTimeMillis(),net.Tickets.pausedUntil) >=0) goto L17;
    L16:
    return;
    L17:
    net.Tickets.nextPoll = (System.currentTimeMillis() + 60000);
    net.ModAccount.call("GET", "/mod/v1/tickets", null).whenComplete(/*indy*/accept());
    return;
}

private static long pause(net.FwmApi$Fetched got) {
    if (got.status() != 429) goto L31;
    net.Tickets.pausedUntil = (System.currentTimeMillis() + (Math.max(60, got.retryAfterSeconds()) * 1000));
    L31:
    return;
}

public static concurrent.CompletableFuture openSpawner(boolean buy, net.Tickets$Spawner spawner, int amount, String notes) {
    v4 = ?;
    v4.put("type", "spawner");
    if (buy ==0) goto L34;
    goto L36;
    L34:
    L36:
    "action".put("ankauf", "verkauf");
    if (spawner.id == null) goto L60;
    goto L64;
    L60:
    L64:
    "spawner".put(spawner.id, spawner.name);
    v4.put("amount", Integer.valueOf(amount));
    if (notes == null) goto L109;
    if (notes.isBlank() !=0) goto L109;
    v4.put("notes", notes.trim());
    L109:
    return net.Tickets.open(v4);
}

public static concurrent.CompletableFuture openMiddleman(String description) {
    v1 = ?;
    v1.put("type", "middleman");
    v1.put("description", description.trim());
    return net.Tickets.open(v1);
}

private static concurrent.CompletableFuture open(Map body) {
    return net.ModAccount.call("POST", "/mod/v1/tickets", body).handle(/*indy*/apply());
}

public static void tick(client.Minecraft client) {
    if (net.ModAccount.hasKey() !=0) goto L7;
    return;
    L7:
    v1 = System.currentTimeMillis();
    if (net.Tickets.watched == null) goto L48;
    if (cmp(v1,net.Tickets.nextWatch) <0) goto L48;
    if (cmp(v1,net.Tickets.pausedUntil) <0) goto L48;
    net.Tickets.nextWatch = (v1 + 15000);
    net.Tickets.loadMessages(net.Tickets.watched, 1);
    L48:
    if (cmp(v1,net.Tickets.nextPoll) <0) goto L64;
    if (cmp(v1,net.Tickets.pausedUntil) >=0) goto L65;
    L64:
    return;
    L65:
    v3 = 0;
    v4 = net.Tickets.tickets.iterator();
    L77:
    if (v4.hasNext() ==0) goto L112;
    v5 = ((net.Tickets$Ticket)v4.next());
    if (v5.live() ==0) goto L109;
    v3 = 1;
    L109:
    goto L77;
    L112:
    if (v3 !=0) goto L125;
    if (cmp(net.Tickets.nextPoll,0) ==0) goto L125;
    return;
    L125:
    net.Tickets.refresh();
    return;
}

private static void announce(List fresh) {
    v1 = net.Tickets.SEEN.isEmpty();
    v2 = fresh.iterator();
    L16:
    if (v2.hasNext() ==0) goto L160;
    v3 = ((net.Tickets$Ticket)v2.next());
    if (v3.id != null) goto L45;
    goto L16;
    L45:
    v4 = v3.status + '/' + v3.outcome;
    v5 = ((String)net.Tickets.SEEN.put(v3.id, v4));
    if (v1 !=0) goto L16;
    if (v5 == null) goto L16;
    if (v5.equals(v4) ==0) goto L101;
    goto L16;
    L101:
    if ("spawner".equals(v3.type) ==0) goto L118;
    goto L120;
    L118:
    L120:
    v6 = "Middleman-Ticket";
    v7 = v6 + ': ' + net.Tickets.statusText(v3);
    v8 = client.Minecraft.getInstance();
    v8.execute(/*indy*/run(v3, v6, v8, v7));
    goto L16;
    L160:
    return;
}

// DECOMPILE ERROR in statusText: 'dict' object has no attribute 'op_type'
private static void lambda$announce$0(net.Tickets$Ticket t, String what, client.Minecraft client, String line) {
    if ("success".equals(t.status) !=0) goto L26;
    if ("success".equals(t.outcome) ==0) goto L30;
    L26:
    goto L31;
    L30:
    L31:
    v4 = 0;
    if ("failure".equals(t.status) !=0) goto L59;
    if ("failure".equals(t.outcome) ==0) goto L63;
    L59:
    goto L64;
    L63:
    L64:
    v5 = 0;
    if (v4 ==0) goto L90;
    goto L104;
    L90:
    if (v5 ==0) goto L101;
    goto L104;
    L101:
    L104:
    ui.FwmNotice.show("", 6088852, 14709876, 15911244, 10000);
    if (client.gui == null) goto L155;
    client.gui.hud.getChat().addClientSystemMessage(network.chat.Component.literal("[FWM] ").withStyle(ChatFormatting.GREEN).append(network.chat.Component.literal(line).withStyle(ChatFormatting.GRAY)));
    L155:
    return;
}

private static String lambda$open$0(net.FwmApi$Fetched got, Throwable error) {
    if (error != null) goto L8;
    if (got != null) goto L12;
    L8:
    return "Keine Verbindung zur FWM-API.";
    L12:
    net.Tickets.pause(got);
    if (got.status() != 201) goto L31;
    net.Tickets.refresh();
    return null;
    L31:
    return net.ModAccount.errorText(got);
}

private static void lambda$refresh$0(net.FwmApi$Fetched got, Throwable error) {
    if (error != null) goto L8;
    if (got != null) goto L9;
    L8:
    return;
    L9:
    net.Tickets.pause(got);
    v2 = ((net.Tickets$TicketList)net.ModAccount.parse(got, net.Tickets$TicketList.class));
    if (got.status() != 200) goto L45;
    if (v2 == null) goto L45;
    if (v2.tickets != null) goto L46;
    L45:
    return;
    L46:
    net.Tickets.announce(v2.tickets);
    net.Tickets.tickets = List.copyOf(v2.tickets);
    v3 = v2.tickets.iterator();
    L73:
    if (v3.hasNext() ==0) goto L141;
    v4 = ((net.Tickets$Ticket)v3.next());
    if (v4.live() ==0) goto L138;
    if (v4.id == null) goto L138;
    if (net.Tickets.AFTER.containsKey(v4.id) !=0) goto L134;
    goto L135;
    L134:
    L135:
    net.Tickets.loadMessages(1, 0);
    L138:
    goto L73;
    L141:
    return;
}

private static String lambda$loadSpawners$0(net.FwmApi$Fetched got, Throwable error) {
    if (error != null) goto L8;
    if (got != null) goto L15;
    L8:
    net.Tickets.spawnerError = "Keine Verbindung.";
    return;
    L15:
    net.Tickets.pause(got);
    v2 = ((net.Tickets$SpawnerList)net.ModAccount.parse(got, net.Tickets$SpawnerList.class));
    if (got.status() != 200) goto L69;
    if (v2 == null) goto L69;
    if (v2.spawners == null) goto L69;
    net.Tickets.spawners = List.copyOf(v2.spawners);
    net.Tickets.spawnerError = "";
    goto L76;
    L69:
    net.Tickets.spawnerError = net.ModAccount.errorText(got);
    L76:
    return;
}

private static void lambda$loadMessages$0(String ticketId, boolean quiet, net.FwmApi$Fetched got, Throwable error) {
    net.Tickets.LOADING.remove(ticketId);
    if (error != null) goto L18;
    if (got != null) goto L19;
    L18:
    return;
    L19:
    net.Tickets.pause(got);
    v4 = ((net.Tickets$MessagePage)net.ModAccount.parse(got, net.Tickets$MessagePage.class));
    if (got.status() != 200) goto L58;
    if (v4 == null) goto L58;
    if (v4.messages != null) goto L59;
    L58:
    return;
    L59:
    if (net.Tickets.AFTER.containsKey(ticketId) !=0) goto L75;
    goto L76;
    L75:
    L76:
    v5 = 0;
    v6 = ?;
    v7 = ?;
    v8 = v6.iterator();
    L120:
    if (v8.hasNext() ==0) goto L158;
    v9 = ((net.Tickets$Message)v8.next());
    v7.add(v9.id);
    goto L120;
    L158:
    v8 = ?;
    v9 = v4.messages.iterator();
    L179:
    if (v9.hasNext() ==0) goto L237;
    v10 = ((net.Tickets$Message)v9.next());
    if (v10.id == null) goto L234;
    if (v7.add(v10.id) ==0) goto L234;
    v8.add(v10);
    L234:
    goto L179;
    L237:
    v6.addAll(v8);
    v6.sort(Comparator.comparing(/*indy*/apply()));
    net.Tickets.MESSAGES.put(ticketId, List.copyOf(v6));
    if (v4.pagination == null) goto L317;
    if (v4.pagination.nextAfter == null) goto L317;
    net.Tickets.AFTER.put(ticketId, v4.pagination.nextAfter);
    goto L370;
    L317:
    if (v5 ==0) goto L370;
    if (v6.isEmpty() ==0) goto L342;
    goto L364;
    L342:
    L364:
    ticketId.put("0", ((net.Tickets$Message)v6.get((v6.size() - 1))).id);
    L370:
    if (quiet !=0) goto L485;
    if (v5 !=0) goto L485;
    if (v8.isEmpty() !=0) goto L485;
    if (ticketId.equals(net.Tickets.watched) !=0) goto L485;
    v9 = ((net.Tickets$Message)v8.get((v8.size() - 1)));
    if (v9.author != null) goto L434;
    goto L464;
    L434:
    if (v9.author.displayName == null) goto L456;
    goto L464;
    L456:
    L464:
    v10 = v9.author.username;
    v11 = client.Minecraft.getInstance();
    v11.execute(/*indy*/run(v10, v9));
    L485:
    return;
}

private static void lambda$loadMessages$2(String who, net.Tickets$Message last) {
    if (last.content != null) goto L22;
    goto L26;
    L22:
    L26:
    ui.FwmNotice.show(who, "", last.content, 6088852, 10000);
    return;
}

private static String lambda$loadMessages$1(net.Tickets$Message m) {
    if (m.createdAt != null) goto L12;
    goto L16;
    L12:
    L16:
    return m.createdAt;
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    net.Tickets.spawners = List.of();
    net.Tickets.spawnerError = "";
    net.Tickets.tickets = List.of();
    net.Tickets.SEEN = ?;
    net.Tickets.MESSAGES = ?;
    net.Tickets.AFTER = ?;
    net.Tickets.LOADING = concurrent.ConcurrentHashMap.newKeySet();
    return;
}

