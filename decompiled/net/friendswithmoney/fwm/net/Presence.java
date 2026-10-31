// class net/friendswithmoney/fwm/net/Presence  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field PING_MS J;
field PING_RETRY_MS J;
field BATCH_DELAY_MS J;
field LOOKUP_GAP_MS J;
field NO_RECHECK_MS J;
field FAIL_RETRY_MS J;
field MAX_BATCH I;
field LOGO_FONT Lnet/minecraft/network/chat/FontDescription;;
field TAB_FONT Lnet/minecraft/network/chat/FontDescription;;
field nextPing J;
field FWM Ljava/util/Set;;
field ASKED Ljava/util/Map;;
field PENDING Ljava/util/Set;;
field pendingSince J;
field lookupRunning Z;
field lastLookup J;
field connection Lnet/minecraft/client/multiplayer/ClientPacketListener;;
field onlineNames Ljava/util/List;;
field WORD_EDGE Ljava/util/regex/Pattern;;
field TAB_ASKED I;
field tabRow Ljava/util/Map;;
field TAB_MEMO Ljava/util/Map;;
field PLAYER_NAME Ljava/util/regex/Pattern;;
field RANK Ljava/util/regex/Pattern;;
field MOJANG_BULK Ljava/lang/String;;
field NAME_BATCH I;
field NAME_GAP_MS J;
field NAME_BACKOFF_MS J;
field NAME_IDS Ljava/util/Map;;
field NAME_ASKED Ljava/util/Set;;
field NAME_PENDING Ljava/util/Set;;
field nameLookupRunning Z;
field nextNameLookup J;
field drawingWorld Z;
field testHugo Z;
private void <init>() {
    return;
}

public static network.chat.MutableComponent decorate(UUID uuid, network.chat.Component name) {
    if (uuid == null) goto L8;
    if (name != null) goto L10;
    L8:
    return name;
    L10:
    net.Presence.want(uuid);
    if (config.FwmConfig.INSTANCE.presenceShowOthers ==0) goto L35;
    if (net.Presence.FWM.contains(uuid) !=0) goto L37;
    L35:
    return name;
    L37:
    if (net.Presence.onHugo(client.Minecraft.getInstance()) !=0) goto L48;
    return name;
    L48:
    return name.copy().append(net.Presence.logo());
}

public static Object tabRows(List entries) {
    v1 = entries.size();
    v2 = v1;
    v3 = 1;
    L11:
    if (v2 <= 20) goto L31;
    v3 = v3 + 1;
    v2 = (((v1 + v3) - 1) / v3);
    goto L11;
    L31:
    v4 = ?;
    v5 = 0;
    L43:
    if (v5 >= v1) goto L101;
    v6 = ((client.multiplayer.PlayerInfo)entries.get(v5));
    if (v6.getProfile() == null) goto L95;
    if (v2 <=0) goto L95;
    v4.put(v6.getProfile().id(), Integer.valueOf(v5));
    L95:
    v5 = v5 + 1;
    goto L43;
    L101:
    net.Presence.tabRow = v4;
    return;
}

private static int asksInTab(UUID uuid) {
    v1 = ((Integer)net.Presence.tabRow.get(uuid));
    if (v1 == null) goto L30;
    if (v1.intValue() >= 100) goto L30;
    goto L31;
    L30:
    L31:
    return 0;
}

public static Object decorateTab(UUID uuid, network.chat.Component name) {
    if (uuid == null) goto L8;
    if (name != null) goto L10;
    L8:
    return name;
    L10:
    v2 = System.currentTimeMillis();
    v4 = ((net.Presence$TabMemo)net.Presence.TAB_MEMO.get(uuid));
    if (v4 == null) goto L62;
    if (v4.in() != name) goto L62;
    if (cmp((v2 - v4.at()),500) >=0) goto L62;
    return v4.out();
    L62:
    v5 = net.Presence.decorateTabNow(uuid, name);
    if (net.Presence.TAB_MEMO.size() <= 4000) goto L91;
    net.Presence.TAB_MEMO.clear();
    L91:
    net.Presence.TAB_MEMO.put(uuid, ?);
    return v5;
}

private static int decorateTabNow(UUID uuid, network.chat.Component name) {
    v2 = net.Presence.asksInTab(uuid);
    if (v2 ==0) goto L13;
    net.Presence.want(uuid);
    L13:
    if (config.FwmConfig.INSTANCE.presenceShowOthers !=0) goto L24;
    return name;
    L24:
    if (net.Presence.onHugo(client.Minecraft.getInstance()) !=0) goto L35;
    return name;
    L35:
    if (net.Presence.FWM.contains(uuid) ==0) goto L60;
    return name.copy().append(net.Presence.rowLogo());
    L60:
    v3 = chat.ServerLine.strip(name.getString());
    v4 = net.Presence.rowName(v3);
    if (v4 == null) goto L174;
    v5 = v4.toLowerCase(Locale.ROOT);
    v6 = ((UUID)net.Presence.NAME_IDS.get(v5));
    if (v6 == null) goto L146;
    if (v2 ==0) goto L120;
    net.Presence.want(v6);
    L120:
    if (net.Presence.FWM.contains(v6) ==0) goto L174;
    return name.copy().append(net.Presence.rowLogo());
    L146:
    if (v2 ==0) goto L174;
    if (net.Presence.NAME_ASKED.add(v5) ==0) goto L174;
    net.Presence.NAME_PENDING.add(v4);
    L174:
    v5 = net.Presence.onlineNames.iterator();
    L184:
    if (v5.hasNext() ==0) goto L342;
    v6 = ((String)v5.next());
    v7 = v3.indexOf(v6);
    L214:
    if (v7 <0) goto L339;
    if (v7 ==0) goto L247;
    if (net.Presence.WORD_EDGE.matcher(String.valueOf(v3.charAt((v7 - 1)))).matches() !=0) goto L251;
    L247:
    goto L252;
    L251:
    L252:
    v8 = 0;
    v9 = (v7 + v6.length());
    if (v9 >= v3.length()) goto L294;
    if (net.Presence.WORD_EDGE.matcher(String.valueOf(v3.charAt(v9))).matches() !=0) goto L298;
    L294:
    goto L299;
    L298:
    L299:
    v10 = 0;
    if (v8 ==0) goto L324;
    if (v10 ==0) goto L324;
    return name.copy().append(net.Presence.rowLogo());
    L324:
    v7 = v3.indexOf(v6, (v7 + 1));
    goto L214;
    L339:
    goto L184;
    L342:
    return name;
}

static boolean rowName(String shown) {
    v1 = shown.trim().split("\\s+");
    if (v1.length !=0) goto L17;
    return null;
    L17:
    if (v1.length <= 1) goto L44;
    if (net.Presence.RANK.matcher(v1[0]).matches() ==0) goto L44;
    goto L47;
    L44:
    L47:
    v2 = v1[0];
    if (net.Presence.PLAYER_NAME.matcher(v2).matches() ==0) goto L65;
    goto L66;
    L65:
    L66:
    return null;
}

private static concurrent.CompletableFuture lookupNames() {
    v0 = ?;
    v1 = net.Presence.NAME_PENDING.iterator();
    L17:
    if (v1.hasNext() ==0) goto L61;
    v2 = ((String)v1.next());
    if (v0.size() < 10) goto L50;
    goto L61;
    L50:
    v0.add(v2);
    goto L17;
    L61:
    Objects.requireNonNull(net.Presence.NAME_PENDING);
    v0.forEach(/*indy*/accept(net.Presence.NAME_PENDING));
    if (v0.isEmpty() ==0) goto L90;
    return;
    L90:
    net.Presence.nameLookupRunning = 1;
    net.FwmApi.postUrl("https://api.minecraftservices.com/minecraft/profile/lookup/bulk/byname", v0, [Lnet.friendswithmoney.fwm.net.Presence$Profile;.class).whenComplete(/*indy*/accept(v0));
    return;
}

private static network.chat.MutableComponent rowLogo() {
    if (net.Presence.drawingWorld ==0) goto L12;
    goto L15;
    L12:
    L15:
    return net.Presence.tabLogo();
}

public static network.chat.MutableComponent tabLogo() {
    return network.chat.Component.literal(" ").append(network.chat.Component.literal("\ue000").setStyle(network.chat.Style.EMPTY.withFont(net.Presence.TAB_FONT).withColor(ChatFormatting.WHITE)));
}

public static network.chat.MutableComponent logo() {
    return network.chat.Component.literal(" ").append(network.chat.Component.literal("\ue000").setStyle(network.chat.Style.EMPTY.withFont(net.Presence.LOGO_FONT).withColor(ChatFormatting.WHITE)));
}

public static long want(UUID uuid) {
    if (uuid == null) goto L16;
    if (net.Presence.FWM.contains(uuid) ==0) goto L17;
    L16:
    return;
    L17:
    v1 = ((Long)net.Presence.ASKED.get(uuid));
    if (v1 == null) goto L50;
    if (cmp((System.currentTimeMillis() - v1.longValue()),3600000) >=0) goto L50;
    return;
    L50:
    if (net.Presence.PENDING.add(uuid) ==0) goto L80;
    if (net.Presence.PENDING.size() != 1) goto L80;
    net.Presence.pendingSince = System.currentTimeMillis();
    L80:
    return;
}

public static boolean showOwn() {
    v0 = client.Minecraft.getInstance();
    if (v0.player == null) goto L49;
    if (config.FwmConfig.INSTANCE.presenceShowOthers ==0) goto L49;
    if (net.Presence.onHugo(v0) ==0) goto L49;
    if (net.Presence.FWM.contains(v0.player.getUUID()) ==0) goto L49;
    goto L50;
    L49:
    L50:
    return 0;
}

public static boolean uses(UUID uuid) {
    if (uuid == null) goto L20;
    if (net.Presence.FWM.contains(uuid) ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

public static void tick(client.Minecraft client) {
    if (client.player == null) goto L21;
    if (client.level == null) goto L21;
    if (net.Presence.onHugo(client) !=0) goto L22;
    L21:
    return;
    L22:
    v1 = client.getConnection();
    if (v1 == net.Presence.connection) goto L62;
    net.Presence.connection = v1;
    net.Presence.FWM.clear();
    net.Presence.ASKED.clear();
    net.Presence.PENDING.clear();
    L62:
    v2 = System.currentTimeMillis();
    if (cmp(v2,net.Presence.nextPing) <0) goto L86;
    net.Presence.nextPing = (v2 + 300000);
    net.Presence.ping(client);
    L86:
    if (cmp((client.level.getGameTime() % 20),0) !=0) goto L277;
    v4 = client.level.players().iterator();
    L116:
    if (v4.hasNext() ==0) goto L149;
    v5 = ((client.player.AbstractClientPlayer)v4.next());
    net.Presence.want(v5.getUUID());
    goto L116;
    L149:
    v4 = ?;
    v5 = v1.getOnlinePlayerIds().iterator();
    L169:
    if (v5.hasNext() ==0) goto L269;
    v6 = ((UUID)v5.next());
    if (net.Presence.FWM.contains(v6) !=0) goto L207;
    goto L169;
    L207:
    v7 = v1.getPlayerInfo(v6);
    if (v7 == null) goto L228;
    if (v7.getProfile() != null) goto L232;
    L228:
    goto L240;
    L232:
    L240:
    v8 = v7.getProfile().name();
    if (v8 == null) goto L266;
    if (v8.length() < 3) goto L266;
    v4.add(v8);
    L266:
    goto L169;
    L269:
    net.Presence.onlineNames = List.copyOf(v4);
    L277:
    if (net.Presence.NAME_PENDING.isEmpty() !=0) goto L313;
    if (net.Presence.nameLookupRunning !=0) goto L313;
    if (cmp(v2,net.Presence.nextNameLookup) <0) goto L313;
    net.Presence.nextNameLookup = (v2 + 1500);
    net.Presence.lookupNames();
    L313:
    if (net.Presence.PENDING.isEmpty() !=0) goto L361;
    if (net.Presence.lookupRunning !=0) goto L361;
    if (cmp((v2 - net.Presence.pendingSince),500) <0) goto L361;
    if (cmp((v2 - net.Presence.lastLookup),2500) <0) goto L361;
    net.Presence.lastLookup = v2;
    net.Presence.lookup();
    L361:
    return;
}

public static boolean onHugo(client.Minecraft client) {
    if (net.Presence.testHugo ==0) goto L8;
    return 1;
    L8:
    if (client.isLocalServer() ==0) goto L17;
    return 0;
    L17:
    v1 = client.getCurrentServer();
    if (v1 == null) goto L56;
    if (v1.ip == null) goto L56;
    if (v1.ip.toLowerCase(Locale.ROOT).contains("hugosmp") ==0) goto L56;
    goto L57;
    L56:
    L57:
    return 0;
}

private static concurrent.CompletableFuture ping(client.Minecraft client) {
    v1 = client.player.getUUID();
    v2 = ?;
    v2.uuid = v1.toString().replace("-", "");
    v2.name = client.player.getGameProfile().name();
    net.FwmApi.post("/api/mod/presence", v2, null, net.Presence$Ok.class).whenComplete(/*indy*/accept(v1));
    return;
}

private static concurrent.CompletableFuture lookup() {
    v0 = ?;
    v1 = net.Presence.PENDING.iterator();
    L17:
    if (v1.hasNext() ==0) goto L62;
    v2 = ((UUID)v1.next());
    if (v0.size() < 200) goto L51;
    goto L62;
    L51:
    v0.add(v2);
    goto L17;
    L62:
    Objects.requireNonNull(net.Presence.PENDING);
    v0.forEach(/*indy*/accept(net.Presence.PENDING));
    if (net.Presence.PENDING.isEmpty() !=0) goto L98;
    net.Presence.pendingSince = System.currentTimeMillis();
    L98:
    if (v0.isEmpty() ==0) goto L108;
    return;
    L108:
    v1 = System.currentTimeMillis();
    v3 = ?;
    v3.uuids = ?;
    v4 = v0.iterator();
    L139:
    if (v4.hasNext() ==0) goto L203;
    v5 = ((UUID)v4.next());
    net.Presence.ASKED.put(v5, Long.valueOf(v1));
    v3.uuids.add(v5.toString().replace("-", ""));
    goto L139;
    L203:
    net.Presence.lookupRunning = 1;
    net.FwmApi.post("/api/mod/presence/lookup", v3, null, net.Presence$LookupAnswer.class).whenComplete(/*indy*/accept(v0));
    return;
}

private static UUID parse(String id) {
    if (id == null) goto L13;
    if (id.length() == 32) goto L15;
    L13:
    return null;
    L15:
    return UUID.fromString(id.substring(0, 8) + '-' + id.substring(8, 12) + '-' + id.substring(12, 16) + '-' + id.substring(16, 20) + '-' + id.substring(20));
    v1 = /*catch IllegalArgumentException*/ ex;
    return null;
}

private static boolean lambda$lookup$0(List batch, net.Presence$LookupAnswer answer, Throwable error) {
    net.Presence.lookupRunning = 0;
    if (error != null) goto L19;
    if (answer == null) goto L19;
    if (answer.fwm != null) goto L80;
    L19:
    v3 = ((System.currentTimeMillis() - 3600000) + 60000);
    v5 = batch.iterator();
    L39:
    if (v5.hasNext() ==0) goto L79;
    v6 = ((UUID)v5.next());
    net.Presence.ASKED.put(v6, Long.valueOf(v3));
    goto L39;
    L79:
    return;
    L80:
    v3 = answer.fwm.iterator();
    L90:
    if (v3.hasNext() ==0) goto L136;
    v4 = ((String)v3.next());
    v5 = net.Presence.parse(v4);
    if (v5 == null) goto L133;
    net.Presence.FWM.add(v5);
    L133:
    goto L90;
    L136:
    return;
}

private static boolean lambda$ping$0(UUID self, net.Presence$Ok answer, Throwable error) {
    if (error != null) goto L8;
    if (answer != null) goto L19;
    L8:
    net.Presence.nextPing = (System.currentTimeMillis() + 60000);
    return;
    L19:
    net.Presence.FWM.add(self);
    return;
}

private static void lambda$lookupNames$0(List batch, net.Presence$Profile[] answer, Throwable error) {
    net.Presence.nameLookupRunning = 0;
    if (error != null) goto L12;
    if (answer != null) goto L70;
    L12:
    net.Presence.nextNameLookup = (System.currentTimeMillis() + 30000);
    v3 = batch.iterator();
    L29:
    if (v3.hasNext() ==0) goto L69;
    v4 = ((String)v3.next());
    net.Presence.NAME_ASKED.remove(v4.toLowerCase(Locale.ROOT));
    goto L29;
    L69:
    return;
    L70:
    v3 = answer;
    v4 = v3.length;
    v5 = 0;
    L79:
    if (v5 >= v4) goto L151;
    v6 = v3[v5];
    v7 = net.Presence.parse(v6.id);
    if (v7 == null) goto L145;
    if (v6.name != null) goto L118;
    goto L145;
    L118:
    net.Presence.NAME_IDS.put(v6.name.toLowerCase(Locale.ROOT), v7);
    net.Presence.want(v7);
    L145:
    v5 = v5 + 1;
    goto L79;
    L151:
    return;
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    net.Presence.LOGO_FONT = ?;
    net.Presence.TAB_FONT = ?;
    net.Presence.FWM = concurrent.ConcurrentHashMap.newKeySet();
    net.Presence.ASKED = ?;
    net.Presence.PENDING = concurrent.ConcurrentHashMap.newKeySet();
    net.Presence.onlineNames = List.of();
    net.Presence.WORD_EDGE = regex.Pattern.compile("[A-Za-z0-9_]");
    net.Presence.tabRow = Map.of();
    net.Presence.TAB_MEMO = ?;
    net.Presence.PLAYER_NAME = regex.Pattern.compile("[A-Za-z0-9_]{3,16}");
    net.Presence.RANK = regex.Pattern.compile("[A-Z]+");
    net.Presence.NAME_IDS = ?;
    net.Presence.NAME_ASKED = concurrent.ConcurrentHashMap.newKeySet();
    net.Presence.NAME_PENDING = concurrent.ConcurrentHashMap.newKeySet();
    return;
}

