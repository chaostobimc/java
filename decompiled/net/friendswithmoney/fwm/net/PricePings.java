// class net/friendswithmoney/fwm/net/PricePings  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAX_PINGS I;
field ROUND_MS J;
field MIN_GAP_MS J;
field SOUND_GAP_MS J;
field LAST_ID Ljava/util/Map;;
field BUSY Ljava/util/Set;;
field nextAt J;
field cursor I;
field lastSound J;
private void <init>() {
    return;
}

public static List list() {
    return config.FwmConfig.INSTANCE.pricePings;
}

public static boolean find(String itemKey, boolean order) {
    v2 = net.PricePings.list().iterator();
    L9:
    if (v2.hasNext() ==0) goto L59;
    v3 = ((config.FwmConfig$PricePing)v2.next());
    if (v3.order != order) goto L56;
    if (v3.itemKey == null) goto L56;
    if (v3.itemKey.equals(itemKey) ==0) goto L56;
    return v3;
    L56:
    goto L9;
    L59:
    return null;
}

public static boolean set(net.MarketData$Item item, String name, double maxPrice, boolean a3) {
    if (v4 !=0) goto L14;
    goto L15;
    L14:
    L15:
    return net.PricePings.set(name, maxPrice, v4, 1, 0, 0, 1, 0);
}

public static void set(net.MarketData$Item item, String name, double maxPrice, boolean a3, boolean order, boolean below, boolean repeat, boolean toast) {
    v9 = net.PricePings.find(item.itemKey, order);
    if (v9 != null) goto L67;
    if (net.PricePings.list().size() < 10) goto L31;
    return 0;
    L31:
    v9 = ?;
    v9.itemKey = item.itemKey;
    v9.order = order;
    net.PricePings.list().add(v9);
    L67:
    v9.baseItemKey = item.baseItemKey;
    v9.icon = item.icon;
    v9.name = name;
    v9.maxPrice = maxPrice;
    if (below ==0) goto L109;
    goto L111;
    L109:
    L111:
    "below".direction = "above";
    v9.repeat = repeat;
    v9.toast = Boolean.valueOf(toast);
    v9.chat = v8;
    v9.fired = 0;
    if (v9.seen != null) goto L164;
    v9.seen = ?;
    L164:
    v9.seen.clear();
    net.PricePings.LAST_ID.remove(net.PricePings.slot(item.itemKey, order));
    config.ConfigManager.save();
    return 1;
}

public static void remove(String itemKey, boolean order) {
    net.PricePings.list().removeIf(/*indy*/test(order, itemKey));
    net.PricePings.LAST_ID.remove(net.PricePings.slot(itemKey, order));
    config.ConfigManager.save();
    return;
}

private static String slot(String itemKey, boolean order) {
    if (order ==0) goto L9;
    goto L11;
    L9:
    L11:
    return "a:" + itemKey;
}

public static double parsePrice(String raw) {
    if (raw != null) goto L8;
    return nan;
    L8:
    v1 = raw.trim().toLowerCase(Locale.ROOT).replace("$", "").replace(" ", "");
    if (v1.isEmpty() ==0) goto L44;
    return nan;
    L44:
    v2 = 1;
    v4 = v1.charAt((v1.length() - 1));
    if (v4 == 107) goto L79;
    if (v4 == 109) goto L79;
    if (v4 != 98) goto L131;
    L79:
    if (v4 != 107) goto L92;
    goto L108;
    L92:
    if (v4 != 109) goto L105;
    goto L108;
    L105:
    L108:
    v2 = 1000000000.0;
    v1 = v1.substring(0, (v1.length() - 1)).replace(44, 46);
    goto L147;
    L131:
    v1 = v1.replace(".", "").replace(",", "");
    L147:
    v5 = (Double.parseDouble(v1) * v2);
    if (cmp(v5,0) <=0) goto L167;
    goto L170;
    L167:
    L170:
    return nan;
    v5 = /*catch NumberFormatException*/ ex;
    return nan;
}

public static void tick(client.Minecraft client) {
    if (client.player == null) goto L15;
    if (config.Features.isOn("fwm.feature.priceping") !=0) goto L16;
    L15:
    return;
    L16:
    v1 = ?;
    v2 = net.PricePings.list().iterator();
    L33:
    if (v2.hasNext() ==0) goto L70;
    v3 = ((config.FwmConfig$PricePing)v2.next());
    if (v3.fired !=0) goto L67;
    v1.add(v3);
    L67:
    goto L33;
    L70:
    if (v1.isEmpty() ==0) goto L80;
    return;
    L80:
    v2 = System.currentTimeMillis();
    if (cmp(v2,net.PricePings.nextAt) >=0) goto L93;
    return;
    L93:
    net.PricePings.nextAt = (v2 + Math.max(2000, (20000 / ((l)v1.size()))));
    net.PricePings.cursor = (net.PricePings.cursor % v1.size());
    net.PricePings.cursor = (net.PricePings.cursor + 1);
    net.PricePings.poll(client, ((config.FwmConfig$PricePing)v1.get(net.PricePings.cursor)));
    return;
}

private static concurrent.CompletableFuture poll(client.Minecraft client, config.FwmConfig$PricePing ping) {
    v2 = ping.itemKey;
    v3 = ping.order;
    v4 = net.PricePings.slot(v2, v3);
    if (v2 == null) goto L34;
    if (net.PricePings.BUSY.contains(v4) ==0) goto L35;
    L34:
    return;
    L35:
    v5 = ((Long)net.PricePings.LAST_ID.get(v4));
    if (v3 ==0) goto L63;
    goto L66;
    L63:
    L66:
    v6 = "/live-orders" + "/live-auctions";
    if (v5 != null) goto L88;
    goto L101;
    L88:
    L101:
    v7 = v6 + '?itemKey=' + net.PricePings.encode(v2) + '&afterId=' + v5 + '&limit=100';
    net.PricePings.BUSY.add(v4);
    net.FwmApi.getUrl(v7, net.LiveAuctionData.class).whenCompleteAsync(/*indy*/accept(client, v4, v2, v3, v5), net.FwmApi.executor());
    return;
}

private static void announce(client.Minecraft client, config.FwmConfig$PricePing ping, net.LiveAuctionData$Entry entry, double unitPrice) {
    if (ping.name == null) goto L17;
    if (ping.name.isBlank() ==0) goto L24;
    L17:
    goto L28;
    L24:
    L28:
    v5 = ping.name;
    v6 = '$' + util.Fmt.compactUp(Math.round(unitPrice));
    if (entry.sellerName != null) goto L57;
    goto L61;
    L57:
    L61:
    v7 = entry.sellerName;
    v8 = net.Market.rounded(entry.amount);
    if (ping.toastOn() ==0) goto L131;
    v10 = System.currentTimeMillis();
    if (cmp((v10 - net.PricePings.lastSound),3000) <0) goto L119;
    net.PricePings.lastSound = v10;
    client.getSoundManager().play(client.resources.sounds.SimpleSoundInstance.forUI(sounds.SoundEvents.NOTE_BLOCK_PLING, 1.600000023841858));
    L119:
    ui.FwmHud.showPricePing(ping, v5, v6, v7, v8);
    L131:
    if (ping.chat ==0) goto L211;
    if (client.gui == null) goto L211;
    if (ping.order ==0) goto L180;
    goto L183;
    L180:
    L183:
    network.chat.Component.literal("[FWM] ").withStyle(ChatFormatting.GREEN).addClientSystemMessage("Order: ".append(network.chat.Component.literal("AH: " + v8 + 'x ' + v5 + ' für ' + v6 + ' · ' + v7).withStyle(ChatFormatting.GRAY)));
    L211:
    return;
}

private static String base() {
    return config.FwmConfig.INSTANCE.marketApiUrl.replaceAll("/+$", "");
}

private static String encode(String raw) {
    return URLEncoder.encode(raw, StandardCharsets.UTF_8).replace("+", "%20");
}

private static long parseLong(String raw) {
    if (raw != null) goto L8;
    goto L15;
    L8:
    L15:
    return Long.parseLong(raw.trim());
    v1 = /*catch NumberFormatException*/ ex;
    return 0;
}

private static void lambda$poll$0(client.Minecraft client, String slot, String key, boolean order, Long after, net.LiveAuctionData result, Throwable error) {
    client.execute(/*indy*/run(slot, error, result, key, order, after, client));
    return;
}

private static Object lambda$poll$1(String slot, Throwable error, net.LiveAuctionData result, String key, boolean order, Long after, client.Minecraft client) {
    net.PricePings.BUSY.remove(slot);
    if (error != null) goto L18;
    if (result != null) goto L45;
    L18:
    if (error != null) goto L32;
    goto L36;
    L32:
    L36:
    key.println('[FWM] Price ping failed for ' + "empty response" + ': ' + error.getMessage());
    return;
    L45:
    v7 = net.PricePings.find(key, order);
    if (v7 != null) goto L59;
    return;
    L59:
    if (after != null) goto L68;
    goto L73;
    L68:
    L73:
    v8 = after.longValue();
    v10 = result.list().iterator();
    L86:
    if (v10.hasNext() ==0) goto L389;
    v11 = ((net.LiveAuctionData$Entry)v10.next());
    v12 = net.PricePings.parseLong(v11.id);
    v8 = Math.max(v8, v12);
    if (after == null) goto L86;
    if (cmp(v12,after.longValue()) >0) goto L146;
    goto L86;
    L146:
    if (key.equals(v11.itemKey) !=0) goto L161;
    goto L86;
    L161:
    if (order ==0) goto L174;
    goto L179;
    L174:
    L179:
    v14 = net.Market.number(v11.unitPrice);
    if (Double.isNaN(v14) ==0) goto L195;
    goto L86;
    L195:
    if (v7.fired ==0) goto L206;
    goto L86;
    L206:
    if (v7.below() ==0) goto L228;
    if (cmp(v14,v7.maxPrice) >0) goto L386;
    goto L239;
    L228:
    if (cmp(v14,v7.maxPrice) <0) goto L386;
    L239:
    if (v11.id == null) goto L255;
    goto L272;
    L255:
    L272:
    v16 = v11.sellerName + '|' + v14 + '|' + v11.amount;
    if (v7.seen != null) goto L294;
    v7.seen = ?;
    L294:
    if (v7.seen.contains(v16) ==0) goto L312;
    goto L86;
    L312:
    v7.seen.add(v16);
    L325:
    if (v7.seen.size() <= 50) goto L355;
    v7.seen.remove(0);
    goto L325;
    L355:
    net.PricePings.announce(client, v7, v11, v14);
    if (v7.repeat !=0) goto L386;
    v7.fired = 1;
    config.ConfigManager.save();
    goto L389;
    L386:
    goto L86;
    L389:
    if (result.meta == null) goto L413;
    v8 = Math.max(v8, net.PricePings.parseLong(result.meta.nextAfterId));
    L413:
    net.PricePings.LAST_ID.put(slot, Long.valueOf(v8));
    return;
}

private static boolean lambda$remove$0(boolean order, String itemKey, config.FwmConfig$PricePing ping) {
    if (ping.order != order) goto L27;
    if (itemKey == null) goto L27;
    if (itemKey.equals(ping.itemKey) ==0) goto L27;
    goto L28;
    L27:
    L28:
    return 0;
}

static void <clinit>() {
    net.PricePings.LAST_ID = ?;
    net.PricePings.BUSY = ?;
    return;
}

