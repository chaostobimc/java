// class net/friendswithmoney/fwm/net/TrustedClans  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REFRESH_MS J;
field RETRY_MS J;
field GSON Lcom/google/gson/Gson;;
field FWM Lnet/friendswithmoney/fwm/net/TrustedClans$Clan;;
field dashClans Ljava/util/List;;
field botClans Ljava/util/List;;
field botFakes Ljava/util/List;;
field clans Ljava/util/List;;
field players Ljava/util/List;;
field nextFetch J;
field nextBotFetch J;
field botCache Lnet/friendswithmoney/fwm/net/TrustedClans$BotCache;;
field logoTicks I;
private static void fwm() {
    v0 = ?;
    v0.id = "fwm";
    v0.name = "FWM";
    return v0;
}

private void <init>() {
    return;
}

private static List builtIn() {
    v0 = ?;
    v0.id = "mrket";
    v0.name = "HugoSMP Market";
    v0.display = "{tag}";
    v0.gradient = "#AA0000,#00AA00";
    v0.tags = List.of("MRKET", "MRK2T");
    return List.of(v0);
}

private static Path cacheFile() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-clans.json");
}

private static Path botCacheFile() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-trusted-clans.json");
}

public static void load() {
    v0 = net.TrustedClans.cacheFile();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L16;
    return;
    L16:
    v1 = ((net.TrustedClans$Data)net.TrustedClans.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.TrustedClans$Data.class));
    if (v1 == null) goto L68;
    if (v1.clans == null) goto L68;
    if (v1.clans.isEmpty() !=0) goto L68;
    net.TrustedClans.dashClans = net.TrustedClans.tidy(v1.clans);
    L68:
    if (v1 == null) goto L89;
    if (v1.players == null) goto L89;
    net.TrustedClans.players = List.copyOf(v1.players);
    L89:
    goto L93;
    v0 = /*catch Exception*/ ex;
    L93:
    v0 = net.TrustedClans.botCacheFile();
    if (Files.exists(v0, new LinkOption[0]) ==0) goto L138;
    v1 = ((net.TrustedClans$BotCache)net.TrustedClans.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), net.TrustedClans$BotCache.class));
    if (v1 == null) goto L138;
    net.TrustedClans.botCache = v1;
    net.TrustedClans.applyBot();
    L138:
    goto L142;
    v0 = /*catch Exception*/ ex;
    L142:
    net.TrustedClans.merge();
    return;
}

public static void tick(client.Minecraft client) {
    v1 = System.currentTimeMillis();
    if (cmp(v1,net.TrustedClans.nextFetch) <0) goto L36;
    net.TrustedClans.nextFetch = (v1 + 600000);
    net.FwmApi.get("/api/mod/clans", net.TrustedClans$Data.class).whenComplete(/*indy*/accept());
    L36:
    if (cmp(v1,net.TrustedClans.nextBotFetch) <0) goto L55;
    net.TrustedClans.nextBotFetch = (v1 + 600000);
    net.TrustedClans.fetchBot();
    L55:
    net.TrustedClans.logoTicks = (net.TrustedClans.logoTicks + 1);
    if (((net.TrustedClans.logoTicks + 1) % 20) !=0) goto L80;
    net.ClanLogos.tick(client, net.TrustedClans.clans, net.TrustedClans.players);
    L80:
    return;
}

private static concurrent.CompletableFuture fetchBot() {
    v0 = net.TrustedClans.botCache;
    net.FwmApi.request("GET", "https://api.friendswithmoney.net/trusted-clans", v0.listEtag, null, null, 2000000).thenCompose(/*indy*/apply(v0)).whenComplete(/*indy*/accept(v0));
    return;
}

private static boolean isFwm(String text) {
    if (text != null) goto L6;
    return 0;
    L6:
    v1 = text.replaceAll("[^A-Za-z]", "").toLowerCase(Locale.ROOT);
    if (v1.equals("fwm") !=0) goto L39;
    if (v1.equals("friendswithmoney") ==0) goto L43;
    L39:
    goto L44;
    L43:
    L44:
    return 0;
}

private static List applyBot() {
    v0 = net.TrustedClans.botCache;
    v1 = ?;
    if (v0.list == null) goto L352;
    if (v0.list.clans == null) goto L352;
    v2 = v0.list.clans.iterator();
    L42:
    if (v2.hasNext() ==0) goto L352;
    v3 = ((net.TrustedClans$BotClan)v2.next());
    if (v3 == null) goto L42;
    if (v3.groupId == null) goto L42;
    if (v3.tag == null) goto L42;
    if (v3.tag.isBlank() ==0) goto L92;
    goto L42;
    L92:
    v4 = ((net.TrustedClans$Clan)v1.computeIfAbsent(v3.groupId, /*indy*/apply()));
    v5 = net.TrustedClans.exact(v3.tag);
    if ("main".equals(v3.type) ==0) goto L150;
    v4.tags.add(0, v5);
    goto L163;
    L150:
    v4.tags.add(v5);
    L163:
    if (v4.name == null) goto L184;
    if ("main".equals(v3.type) ==0) goto L207;
    L184:
    if (v3.displayName == null) goto L200;
    goto L204;
    L200:
    L204:
    v3.displayName.name = v3.name;
    L207:
    if (v3.nameColor != null) goto L219;
    goto L234;
    L219:
    L234:
    v6 = v3.nameColor.trim().replace("#", "");
    if (v6.matches("[0-9A-Fa-f]{6}") ==0) goto L292;
    if ("main".equals(v3.type) !=0) goto L274;
    if (v4.display.equals("&f&l{tag}") ==0) goto L292;
    L274:
    v4.display = '&#' + v6.toUpperCase(Locale.ROOT) + '&l{tag}';
    L292:
    if (v3.iconUrl == null) goto L333;
    if (v3.iconUrl.startsWith("http") ==0) goto L321;
    goto L330;
    L321:
    L330:
    v3.iconUrl.iconUrl = 'https://api.friendswithmoney.net' + v3.iconUrl;
    L333:
    if (v3.iconEtag == null) goto L349;
    v4.iconEtag = v3.iconEtag;
    L349:
    goto L42;
    L352:
    v2 = ?;
    v3 = ?;
    v4 = v1.entrySet().iterator();
    L381:
    if (v4.hasNext() ==0) goto L510;
    v5 = ((Map$Entry)v4.next());
    v6 = ((net.TrustedClans$Clan)v5.getValue());
    v7 = net.TrustedClans.isFwm(v6.name);
    v8 = v6.tags.iterator();
    L437:
    if (v8.hasNext() ==0) goto L473;
    v9 = ((String)v8.next());
    if (net.TrustedClans.isFwm(v9) ==0) goto L470;
    v7 = 1;
    L470:
    goto L437;
    L473:
    if (v7 ==0) goto L498;
    v3.add(((String)v5.getKey()));
    goto L381;
    L498:
    v2.add(v6);
    goto L381;
    L510:
    v4 = ?;
    if (v0.fakes == null) goto L682;
    if (v0.fakes.tags == null) goto L682;
    v5 = v0.fakes.tags.iterator();
    L550:
    if (v5.hasNext() ==0) goto L682;
    v6 = ((net.TrustedClans$BotFake)v5.next());
    if (v6 == null) goto L550;
    if (v6.tag == null) goto L550;
    if (v6.tag.isBlank() ==0) goto L599;
    goto L550;
    L599:
    if (v3.contains(v6.groupId) !=0) goto L624;
    if (net.TrustedClans.isFwm(v6.trustedClan) ==0) goto L630;
    L624:
    goto L644;
    L630:
    L644:
    v7 = ((net.TrustedClans$Clan)v1.get(v6.groupId));
    if (v7 != null) goto L654;
    goto L550;
    L654:
    v4.add(?);
    goto L550;
    L682:
    net.TrustedClans.botClans = List.copyOf(v2);
    net.TrustedClans.botFakes = List.copyOf(v4);
    return;
}

private static List merge() {
    v0 = ?;
    v1 = ?;
    v2 = net.TrustedClans.botClans.iterator();
    L28:
    if (v2.hasNext() ==0) goto L101;
    v3 = ((net.TrustedClans$Clan)v2.next());
    v4 = v3.tags.iterator();
    L58:
    if (v4.hasNext() ==0) goto L98;
    v5 = ((String)v4.next());
    v1.add(v5.toUpperCase(Locale.ROOT));
    goto L58;
    L98:
    goto L28;
    L101:
    v2 = net.TrustedClans.dashClans.iterator();
    L110:
    if (v2.hasNext() ==0) goto L198;
    v3 = ((net.TrustedClans$Clan)v2.next());
    v4 = 0;
    v5 = v3.tags.iterator();
    L143:
    if (v5.hasNext() ==0) goto L182;
    v6 = ((String)v5.next());
    if (v1.contains(v6) ==0) goto L179;
    v4 = 1;
    L179:
    goto L143;
    L182:
    if (v4 !=0) goto L195;
    v0.add(v3);
    L195:
    goto L110;
    L198:
    net.TrustedClans.clans = List.copyOf(v0);
    return;
}

static String exact(String tag) {
    return tag.trim().replaceAll("[^\\x00-\\x7F]", "").replace("[", "").replace("]", "");
}

private static List tidy(List raw) {
    v1 = ?;
    v2 = raw.iterator();
    L15:
    if (v2.hasNext() ==0) goto L223;
    v3 = ((net.TrustedClans$Clan)v2.next());
    if (v3 == null) goto L15;
    if (v3.id == null) goto L15;
    if ("fwm".equals(v3.id) ==0) goto L60;
    goto L15;
    L60:
    v4 = ?;
    if (v3.tags == null) goto L130;
    v5 = v3.tags.iterator();
    L87:
    if (v5.hasNext() ==0) goto L130;
    v6 = ((String)v5.next());
    if (v6 == null) goto L127;
    v4.add(net.TrustedClans.plain(v6));
    L127:
    goto L87;
    L130:
    v5 = ?;
    if (v3.fakeTags == null) goto L200;
    v6 = v3.fakeTags.iterator();
    L157:
    if (v6.hasNext() ==0) goto L200;
    v7 = ((String)v6.next());
    if (v7 == null) goto L197;
    v5.add(net.TrustedClans.plain(v7));
    L197:
    goto L157;
    L200:
    v3.tags = v4;
    v3.fakeTags = v5;
    v1.add(v3);
    goto L15;
    L223:
    return List.copyOf(v1);
}

static String plain(String tag) {
    return tag.trim().replaceAll("[^\\x00-\\x7F]", "").replace("[", "").replace("]", "").toUpperCase(Locale.ROOT);
}

public static List all() {
    return net.TrustedClans.clans;
}

public static boolean match(String tag) {
    if (tag != null) goto L6;
    return null;
    L6:
    v1 = net.TrustedClans.plain(tag);
    v2 = net.TrustedClans.exact(tag);
    v3 = net.TrustedClans.clans.iterator();
    L25:
    if (v3.hasNext() ==0) goto L90;
    v4 = ((net.TrustedClans$Clan)v3.next());
    if ("fwm".equals(v4.id) !=0) goto L87;
    if (v4.exact ==0) goto L75;
    goto L76;
    L75:
    L76:
    if (v2.contains(v1) ==0) goto L87;
    return v4;
    L87:
    goto L25;
    L90:
    return null;
}

public static boolean isFwmTag(String tag) {
    if (tag != null) goto L6;
    return 0;
    L6:
    v1 = net.TrustedClans.plain(tag);
    v2 = net.TrustedClans.dashClans.iterator();
    L20:
    if (v2.hasNext() ==0) goto L69;
    v3 = ((net.TrustedClans$Clan)v2.next());
    if ("fwm".equals(v3.id) ==0) goto L66;
    if (v3.tags.contains(v1) ==0) goto L66;
    return 1;
    L66:
    goto L20;
    L69:
    return 0;
}

public static boolean fakeOf(String tag) {
    if (tag != null) goto L6;
    return null;
    L6:
    v1 = net.TrustedClans.plain(tag);
    if (v1.isEmpty() ==0) goto L20;
    return null;
    L20:
    v2 = net.TrustedClans.exact(tag);
    v3 = net.TrustedClans.botFakes.iterator();
    L34:
    if (v3.hasNext() ==0) goto L75;
    v4 = ((net.TrustedClans$BotFakeTag)v3.next());
    if (v4.tag().equals(v2) ==0) goto L72;
    return v4.clan();
    L72:
    goto L34;
    L75:
    v3 = net.TrustedClans.dashClans.iterator();
    L84:
    if (v3.hasNext() ==0) goto L124;
    v4 = ((net.TrustedClans$Clan)v3.next());
    if (v4.fakeTags.contains(v1) ==0) goto L121;
    return v4;
    L121:
    goto L84;
    L124:
    return null;
}

public static network.chat.MutableComponent label(net.TrustedClans$Clan clan, String wornTag) {
    if (clan.display == null) goto L17;
    if (clan.display.isBlank() ==0) goto L22;
    L17:
    goto L26;
    L22:
    L26:
    v2 = clan.display;
    if (wornTag != null) goto L37;
    goto L38;
    L37:
    L38:
    return net.TrustedClans.styled("", wornTag, net.TrustedClans.gradient(clan.gradient), clan.id);
}

public static network.chat.MutableComponent playerLabel(UUID uuid) {
    if (uuid != null) goto L6;
    return null;
    L6:
    v1 = uuid.toString().replace("-", "");
    v2 = net.TrustedClans.players.iterator();
    L28:
    if (v2.hasNext() ==0) goto L95;
    v3 = ((net.TrustedClans$Player)v2.next());
    if (v1.equals(v3.uuid) ==0) goto L92;
    if (v3.display == null) goto L92;
    if (v3.display.isBlank() !=0) goto L92;
    return net.TrustedClans.styled(v3.display, "", null, 'p_' + v1);
    L92:
    goto L28;
    L95:
    return null;
}

private static network.chat.MutableComponent styled(String display, String tag, int[] gradient, String logoId) {
    v4 = network.chat.Component.empty();
    v5 = network.chat.Style.EMPTY.withColor(ChatFormatting.WHITE);
    v6 = ?;
    v7 = 0;
    L28:
    if (v7 >= display.length()) goto L414;
    v8 = display.charAt(v7);
    if (v8 != 38) goto L146;
    if ((v7 + 7) >= display.length()) goto L146;
    if (display.charAt((v7 + 1)) != 35) goto L146;
    if (display.substring((v7 + 2), Math.min(display.length(), (v7 + 8))).matches("[0-9a-fA-F]{6}") ==0) goto L146;
    net.TrustedClans.flush(v4, v6, v5);
    v5 = v5.withColor(Integer.parseInt(display.substring((v7 + 2), (v7 + 8)), 16));
    v7 = v7 + 7;
    goto L408;
    L146:
    if (v8 != 38) goto L264;
    if ((v7 + 1) >= display.length()) goto L264;
    v9 = ChatFormatting.getByCode(Character.toLowerCase(display.charAt((v7 + 1))));
    if (v9 == null) goto L264;
    net.TrustedClans.flush(v4, v6, v5);
    if (v9 != ChatFormatting.RESET) goto L216;
    v5 = network.chat.Style.EMPTY.withColor(ChatFormatting.WHITE);
    goto L258;
    L216:
    if ("0123456789abcdef".indexOf(Character.toLowerCase(display.charAt((v7 + 1)))) <0) goto L249;
    v5 = network.chat.Style.EMPTY.withColor(v9);
    goto L258;
    L249:
    v5 = v5.applyFormat(v9);
    L258:
    v7 = v7 + 1;
    goto L408;
    L264:
    if (display.startsWith("{tag}", v7) ==0) goto L400;
    net.TrustedClans.flush(v4, v6, v5);
    if (gradient == null) goto L379;
    v9 = tag.length();
    v10 = 0;
    L297:
    if (v10 >= v9) goto L376;
    if (v9 != 1) goto L314;
    goto L323;
    L314:
    L323:
    v11 = (((f)v10) / ((f)(v9 - 1)));
    v4.append(network.chat.Component.literal(String.valueOf(tag.charAt(v10))).setStyle(network.chat.Style.EMPTY.withColor(net.TrustedClans.mix(gradient[0], gradient[1], v11)).withBold(Boolean.valueOf(1))));
    v10 = v10 + 1;
    goto L297;
    L376:
    goto L394;
    L379:
    v4.append(network.chat.Component.literal(tag).setStyle(v5));
    L394:
    v7 = v7 + 4;
    goto L408;
    L400:
    v6.append(v8);
    L408:
    v7 = v7 + 1;
    goto L28;
    L414:
    net.TrustedClans.flush(v4, v6, v5);
    v7 = net.ClanLogos.font(logoId);
    if (v7 == null) goto L482;
    v4.append(network.chat.Component.literal(" "));
    v4.append(network.chat.Component.literal("\ue000").setStyle(network.chat.Style.EMPTY.withFont(?).withColor(ChatFormatting.WHITE)));
    L482:
    return v4;
}

private static void flush(network.chat.MutableComponent out, StringBuilder run, network.chat.Style style) {
    if (run.length() !=0) goto L8;
    return;
    L8:
    out.append(network.chat.Component.literal(run.toString()).setStyle(style));
    run.setLength(0);
    return;
}

private static int gradient(String spec) {
    if (spec == null) goto L14;
    if (spec.matches("#[0-9a-fA-F]{6},#[0-9a-fA-F]{6}") !=0) goto L16;
    L14:
    return null;
    L16:
    new int[2][0] = Integer.parseInt(spec.substring(1, 7), 16);
    new int[2][1] = Integer.parseInt(spec.substring(9, 15), 16);
    return new int[2];
}

private static int mix(int a, int b, float t) {
    v3 = Math.round(((((f)((a >> 16) & 255)) * (1 - t)) + (((f)((b >> 16) & 255)) * t)));
    v4 = Math.round(((((f)((a >> 8) & 255)) * (1 - t)) + (((f)((b >> 8) & 255)) * t)));
    v5 = Math.round(((((f)(a & 255)) * (1 - t)) + (((f)(b & 255)) * t)));
    return (((v3 << 16) | (v4 << 8)) | v5);
}

public static String fakeWarning(net.TrustedClans$Clan clan) {
    if (clan.tags.isEmpty() !=0) goto L28;
    goto L46;
    L28:
    if (clan.name != null) goto L42;
    goto L46;
    L42:
    L46:
    v1 = clan.name;
    return 'ACHTUNG FAKE ' + v1.toUpperCase(Locale.ROOT);
}

private static String lambda$applyBot$0(String id) {
    v1 = ?;
    v1.id = 'g' + id.replaceAll("[^0-9A-Za-z_-]", "").toLowerCase(Locale.ROOT);
    v1.display = "&f&l{tag}";
    v1.exact = 1;
    return v1;
}

private static Path lambda$fetchBot$1(net.TrustedClans$BotCache cache, net.FwmApi$Fetched fakes, Throwable error) {
    if (error == null) goto L15;
    net.TrustedClans.nextBotFetch = (System.currentTimeMillis() + 60000);
    return;
    L15:
    if (fakes.status() != 200) goto L63;
    v3 = ((net.TrustedClans$BotFakes)fakes.json(net.TrustedClans$BotFakes.class));
    if (v3 == null) goto L60;
    if (v3.tags == null) goto L60;
    cache.fakes = v3;
    cache.fakesEtag = fakes.etag();
    L60:
    goto L94;
    L63:
    if (fakes.status() != 429) goto L94;
    net.TrustedClans.nextBotFetch = (System.currentTimeMillis() + (Math.max(60, fakes.retryAfterSeconds()) * 1000));
    L94:
    net.TrustedClans.botCache = cache;
    net.TrustedClans.applyBot();
    net.TrustedClans.merge();
    Files.writeString(net.TrustedClans.botCacheFile(), net.TrustedClans.GSON.toJson(cache), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L129;
    v3 = /*catch Exception*/ ex;
    L129:
    return;
}

private static concurrent.CompletableFuture lambda$fetchBot$0(net.TrustedClans$BotCache cache, net.FwmApi$Fetched list) {
    if (list.status() != 200) goto L47;
    v2 = ((net.TrustedClans$BotList)list.json(net.TrustedClans$BotList.class));
    if (v2 == null) goto L44;
    if (v2.clans == null) goto L44;
    cache.list = v2;
    cache.listEtag = list.etag();
    L44:
    goto L78;
    L47:
    if (list.status() != 429) goto L78;
    net.TrustedClans.nextBotFetch = (System.currentTimeMillis() + (Math.max(60, list.retryAfterSeconds()) * 1000));
    L78:
    return net.FwmApi.request("GET", "https://api.friendswithmoney.net/fake-clans", cache.fakesEtag, null, null, 2000000);
}

private static Path lambda$tick$0(net.TrustedClans$Data data, Throwable error) {
    if (error != null) goto L15;
    if (data == null) goto L15;
    if (data.clans != null) goto L26;
    L15:
    net.TrustedClans.nextFetch = (System.currentTimeMillis() + 60000);
    return;
    L26:
    net.TrustedClans.dashClans = net.TrustedClans.tidy(data.clans);
    if (data.players != null) goto L49;
    goto L56;
    L49:
    L56:
    net.TrustedClans.players = List.copyOf(data.players);
    net.TrustedClans.merge();
    Files.writeString(net.TrustedClans.cacheFile(), net.TrustedClans.GSON.toJson(data), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L87;
    v2 = /*catch Exception*/ ex;
    L87:
    return;
}

static void <clinit>() {
    net.TrustedClans.GSON = ?;
    net.TrustedClans.FWM = net.TrustedClans.fwm();
    net.TrustedClans.dashClans = net.TrustedClans.builtIn();
    net.TrustedClans.botClans = List.of();
    net.TrustedClans.botFakes = List.of();
    net.TrustedClans.clans = net.TrustedClans.dashClans;
    net.TrustedClans.players = List.of();
    net.TrustedClans.botCache = ?;
    return;
}

