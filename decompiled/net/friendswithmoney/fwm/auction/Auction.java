// class net/friendswithmoney/fwm/auction/Auction  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field HISTORY I;
field AFTERGLOW_MS J;
field LATE_MS J;
field FILE Ljava/lang/String;;
field item Lnet/minecraft/world/item/ItemStack;;
field startBid J;
field minStep J;
field endsAt J;
field durationMs J;
field bids Ljava/util/List;;
field late Ljava/util/List;;
field paidBy Ljava/util/Map;;
field missing Z;
field extendedAt J;
field extendedBy I;
field restored Z;
field ticks I;
private void <init>() {
    return;
}

public static void start(world.item.ItemStack stack, int seconds, long startBidCents, long a3) {
    if (stack != null) goto L10;
    goto L14;
    L10:
    L14:
    auction.Auction.item = stack.copy();
    auction.Auction.startBid = Math.max(0, startBidCents);
    auction.Auction.minStep = Math.max(0, v4);
    auction.Auction.durationMs = (((l)Math.max(1, seconds)) * 1000);
    auction.Auction.endsAt = (System.currentTimeMillis() + auction.Auction.durationMs);
    auction.Auction.bids.clear();
    auction.Auction.late.clear();
    auction.Auction.paidBy.clear();
    auction.Auction.missing = 0;
    auction.Auction.save();
    return;
}

public static world.item.ItemStack hold(world.item.ItemStack stack) {
    if (auction.Auction.running() ==0) goto L7;
    return;
    L7:
    auction.Auction.clearResult();
    if (stack != null) goto L20;
    goto L24;
    L20:
    L24:
    auction.Auction.item = stack.copy();
    return;
}

public static void stop() {
    if (cmp(auction.Auction.endsAt,System.currentTimeMillis()) <=0) goto L16;
    auction.Auction.endsAt = System.currentTimeMillis();
    L16:
    auction.Auction.save();
    return;
}

public static void clearResult() {
    auction.Auction.endsAt = 0;
    auction.Auction.bids.clear();
    auction.Auction.late.clear();
    auction.Auction.paidBy.clear();
    auction.Auction.missing = 0;
    auction.Auction.save();
    return;
}

public static void clear() {
    auction.Auction.clearResult();
    auction.Auction.item = world.item.ItemStack.EMPTY;
    return;
}

public static long running() {
    if (auction.Auction.item.isEmpty() !=0) goto L23;
    if (cmp(System.currentTimeMillis(),auction.Auction.endsAt) >=0) goto L23;
    goto L24;
    L23:
    L24:
    return 0;
}

public static long hasResult() {
    if (auction.Auction.item.isEmpty() !=0) goto L31;
    if (cmp(auction.Auction.endsAt,0) <=0) goto L31;
    if (cmp(System.currentTimeMillis(),auction.Auction.endsAt) <0) goto L31;
    goto L32;
    L31:
    L32:
    return 0;
}

public static long visible() {
    if (auction.Auction.item.isEmpty() !=0) goto L35;
    if (cmp(auction.Auction.endsAt,0) <=0) goto L35;
    if (cmp(System.currentTimeMillis(),(auction.Auction.endsAt + 60000)) >=0) goto L35;
    goto L36;
    L35:
    L36:
    return 0;
}

public static world.item.ItemStack item() {
    return auction.Auction.item;
}

public static long remainingMs() {
    return Math.max(0, (auction.Auction.endsAt - System.currentTimeMillis()));
}

public static float progress() {
    if (cmp(auction.Auction.durationMs,0) >0) goto L10;
    return 0;
    L10:
    return Math.max(0, Math.min(1, (((f)auction.Auction.remainingMs()) / ((f)auction.Auction.durationMs))));
}

public static String leader() {
    v0 = auction.Auction.top();
    if (v0 != null) goto L12;
    goto L16;
    L12:
    L16:
    return v0.name();
}

public static long price() {
    v0 = auction.Auction.top();
    if (v0 != null) goto L14;
    goto L18;
    L14:
    L18:
    return v0.amount();
}

private static Object top() {
    v0 = auction.Auction.ranking();
    if (v0.isEmpty() !=0) goto L33;
    if (cmp(((auction.Auction$Bid)v0.get(0)).amount(),auction.Auction.startBid) >=0) goto L35;
    L33:
    return null;
    L35:
    return ((auction.Auction$Bid)v0.get(0));
}

public static void ranking() {
    v0 = ?;
    if (config.FwmConfig.INSTANCE.auctionStackPayments ==0) goto L172;
    v1 = ?;
    v2 = auction.Auction.bids.iterator();
    L34:
    if (v2.hasNext() ==0) goto L169;
    v3 = ((auction.Auction$Bid)v2.next());
    v4 = v3.name().toLowerCase(Locale.ROOT);
    v5 = ((Integer)v1.get(v4));
    if (v5 != null) goto L112;
    v1.put(v4, Integer.valueOf(v0.size()));
    v0.add(v3);
    goto L166;
    L112:
    v6 = ((auction.Auction$Bid)v0.get(v5.intValue()));
    v0.set(v5.intValue(), ?);
    L166:
    goto L34;
    L169:
    goto L182;
    L172:
    v0.addAll(auction.Auction.bids);
    L182:
    v0.sort(/*indy*/compare());
    return v0;
}

public static void late() {
    return ?;
}

public static long startBid() {
    return auction.Auction.startBid;
}

public static long minStep() {
    return auction.Auction.minStep;
}

public static long nextMinimum() {
    if (auction.Auction.top() != null) goto L10;
    return auction.Auction.startBid;
    L10:
    return (auction.Auction.price() + Math.max(1, auction.Auction.minStep));
}

public static void bids() {
    v0 = auction.Auction.ranking();
    return ?;
}

public static List allBids() {
    return auction.Auction.ranking();
}

public static int bidCount() {
    return auction.Auction.bids.size();
}

public static boolean itemMissing() {
    return auction.Auction.missing;
}

public static long earnings() {
    v0 = 0;
    v2 = auction.Auction.paidBy.values().iterator();
    L16:
    if (v2.hasNext() ==0) goto L45;
    v3 = ((Long)v2.next()).longValue();
    v0 = (v0 + v3);
    goto L16;
    L45:
    v2 = auction.Auction.late.iterator();
    L54:
    if (v2.hasNext() ==0) goto L83;
    v3 = ((auction.Auction$Bid)v2.next());
    v0 = (v0 + v3.amount());
    goto L54;
    L83:
    return v0;
}

public static String plain(long cents) {
    if (cmp((cents % 100),0) !=0) goto L21;
    goto L45;
    L21:
    new Object[1][0] = Double.valueOf((((d)cents) / 100.0));
    L45:
    return String.format(Locale.ROOT, "%.2f", new Object[1]);
}

public static void accept(network.chat.Component message) {
    if (message == null) goto L21;
    if (auction.Auction.item.isEmpty() !=0) goto L21;
    if (cmp(auction.Auction.endsAt,0) >0) goto L22;
    L21:
    return;
    L22:
    v1 = System.currentTimeMillis();
    if (cmp(v1,auction.Auction.endsAt) >=0) goto L38;
    goto L39;
    L38:
    L39:
    v3 = 0;
    if (v3 !=0) goto L57;
    if (cmp(v1,(auction.Auction.endsAt + 600000)) <=0) goto L57;
    return;
    L57:
    if (chat.ServerLine.fromServer(message) !=0) goto L65;
    return;
    L65:
    v4 = auction.Payments.read(chat.ServerLine.plain(message), auction.Auction.self());
    if (v4 == null) goto L92;
    if (cmp(v4.amount(),0) >0) goto L93;
    L92:
    return;
    L93:
    if (v3 !=0) goto L128;
    auction.Auction.late.add(?);
    auction.Auction.save();
    return;
    L128:
    v5 = auction.Auction.leader();
    v6 = auction.Auction.price();
    auction.Auction.paidBy.merge(v4.name().toLowerCase(Locale.ROOT), Long.valueOf(v4.amount()), /*indy*/apply());
    auction.Auction.bids.add(?);
    if (auction.Auction.leader() == null) goto L222;
    if (v5 == null) goto L218;
    if (cmp(auction.Auction.price(),v6) <=0) goto L222;
    L218:
    goto L223;
    L222:
    L223:
    v8 = 0;
    if (v8 ==0) goto L234;
    auction.Auction.extendIfLate(v1);
    L234:
    auction.Auction.save();
    return;
}

static int extendIfLate(long now) {
    v2 = config.FwmConfig.INSTANCE;
    if (v2.auctionAntiSnipe ==0) goto L23;
    if (cmp((auction.Auction.endsAt - now),3000) <=0) goto L24;
    L23:
    return;
    L24:
    v3 = Math.max(1, v2.auctionAntiSnipeSeconds);
    auction.Auction.endsAt = (auction.Auction.endsAt + (((l)v3) * 1000));
    auction.Auction.durationMs = (auction.Auction.durationMs + (((l)v3) * 1000));
    auction.Auction.extendedAt = now;
    auction.Auction.extendedBy = v3;
    return;
}

public static long recentExtension() {
    if (cmp((System.currentTimeMillis() - auction.Auction.extendedAt),2000) >=0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

private static boolean self() {
    v0 = ui.FwmGfx.playerName();
    if (v0.isEmpty() ==0) goto L15;
    goto L16;
    L15:
    L16:
    return v0;
}

private static void notify(String text) {
    v1 = client.Minecraft.getInstance();
    if (v1.player != null) goto L12;
    return;
    L12:
    v1.player.sendSystemMessage(network.chat.Component.literal("[Auktion] ").withStyle(ChatFormatting.GOLD).append(network.chat.Component.literal(text).withStyle(ChatFormatting.WHITE)));
    return;
}

public static void tick(client.Minecraft client) {
    if (client.level == null) goto L14;
    if (client.player != null) goto L15;
    L14:
    return;
    L15:
    if (auction.Auction.restored !=0) goto L28;
    auction.Auction.restored = 1;
    auction.Auction.load();
    L28:
    if (auction.Auction.running() ==0) goto L49;
    auction.Auction.ticks = (auction.Auction.ticks + 1);
    if (((auction.Auction.ticks + 1) % 20) ==0) goto L50;
    L49:
    return;
    L50:
    if (auction.StreamerStore.carried(auction.Auction.item) >= auction.Auction.item.getCount()) goto L69;
    goto L70;
    L69:
    L70:
    v1 = 0;
    if (v1 ==0) goto L87;
    if (auction.Auction.missing !=0) goto L87;
    auction.Auction.notify("Achtung: Das versteigerte Item fehlt im Inventar!");
    L87:
    auction.Auction.missing = v1;
    return;
}

private static void save() {
    if (auction.Auction.item.isEmpty() !=0) goto L17;
    if (cmp(auction.Auction.endsAt,0) >0) goto L25;
    L17:
    auction.StreamerStore.save("fwm-auction-state.json", null);
    return;
    L25:
    v0 = ?;
    v0.item = auction.StreamerStore.encode(auction.Auction.item);
    if (v0.item != null) goto L51;
    return;
    L51:
    v0.startBid = auction.Auction.startBid;
    v0.minStep = auction.Auction.minStep;
    v0.endsAt = auction.Auction.endsAt;
    v0.durationMs = auction.Auction.durationMs;
    v0.payments = ?;
    v0.late = ?;
    v0.paidBy = ?;
    auction.StreamerStore.save("fwm-auction-state.json", v0);
    return;
}

private static void load() {
    v0 = ((auction.Auction$State)auction.StreamerStore.load("fwm-auction-state.json", auction.Auction$State.class));
    if (v0 == null) goto L24;
    if (v0.item != null) goto L25;
    L24:
    return;
    L25:
    if (cmp(System.currentTimeMillis(),(v0.endsAt + 86400000)) <=0) goto L48;
    auction.StreamerStore.save("fwm-auction-state.json", null);
    return;
    L48:
    v1 = auction.StreamerStore.decode(v0.item);
    if (v1.isEmpty() ==0) goto L64;
    return;
    L64:
    auction.Auction.item = v1;
    auction.Auction.startBid = v0.startBid;
    auction.Auction.minStep = v0.minStep;
    auction.Auction.endsAt = v0.endsAt;
    auction.Auction.durationMs = v0.durationMs;
    auction.Auction.bids.clear();
    auction.Auction.late.clear();
    auction.Auction.paidBy.clear();
    if (v0.payments == null) goto L143;
    auction.Auction.bids.addAll(v0.payments);
    goto L163;
    L143:
    if (v0.bids == null) goto L163;
    auction.Auction.bids.addAll(v0.bids);
    L163:
    if (v0.late == null) goto L183;
    auction.Auction.late.addAll(v0.late);
    L183:
    if (v0.paidBy == null) goto L202;
    auction.Auction.paidBy.putAll(v0.paidBy);
    L202:
    return;
}

public static void start(world.item.ItemStack stack, int seconds, long startBidCents) {
    auction.Auction.start(stack, seconds, startBidCents, 0);
    return;
}

private static int lambda$ranking$0(auction.Auction$Bid a, auction.Auction$Bid b) {
    if (cmp(a.amount(),b.amount()) ==0) goto L26;
    goto L37;
    L26:
    L37:
    return Long.compare(a.time(), b.time());
}

static void <clinit>() {
    auction.Auction.item = world.item.ItemStack.EMPTY;
    auction.Auction.bids = ?;
    auction.Auction.late = ?;
    auction.Auction.paidBy = ?;
    return;
}

