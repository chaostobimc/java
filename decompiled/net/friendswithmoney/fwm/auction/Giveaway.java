// class net/friendswithmoney/fwm/auction/Giveaway  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FILE Ljava/lang/String;;
field AFTERGLOW_MS J;
field DRAW_MS J;
field item Lnet/minecraft/world/item/ItemStack;;
field word Ljava/lang/String;;
field limit I;
field durationMs J;
field startedAt J;
field pausedAt J;
field pausedTotal J;
field status Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
field participants Ljava/util/List;;
field winner Ljava/lang/String;;
field drawnAt J;
field restored Z;
private void <init>() {
    return;
}

public static world.item.ItemStack hold(world.item.ItemStack stack) {
    if (auction.Giveaway.status == auction.Giveaway$Status.RUNNING) goto L18;
    if (auction.Giveaway.status != auction.Giveaway$Status.PAUSED) goto L19;
    L18:
    return;
    L19:
    if (stack != null) goto L29;
    goto L33;
    L29:
    L33:
    auction.Giveaway.item = stack.copy();
    return;
}

public static void start(world.item.ItemStack stack, String joinWord, int maxParticipants, int seconds) {
    if (stack != null) goto L10;
    goto L14;
    L10:
    L14:
    auction.Giveaway.item = stack.copy();
    if (joinWord != null) goto L26;
    goto L30;
    L26:
    L30:
    auction.Giveaway.word = joinWord.trim();
    auction.Giveaway.limit = Math.max(1, maxParticipants);
    auction.Giveaway.durationMs = (((l)Math.max(1, seconds)) * 1000);
    auction.Giveaway.startedAt = System.currentTimeMillis();
    auction.Giveaway.pausedAt = 0;
    auction.Giveaway.pausedTotal = 0;
    auction.Giveaway.participants.clear();
    auction.Giveaway.winner = null;
    auction.Giveaway.drawnAt = 0;
    auction.Giveaway.status = auction.Giveaway$Status.RUNNING;
    auction.Giveaway.save();
    return;
}

public static void pause() {
    if (auction.Giveaway.status == auction.Giveaway$Status.RUNNING) goto L10;
    return;
    L10:
    auction.Giveaway.status = auction.Giveaway$Status.PAUSED;
    auction.Giveaway.pausedAt = System.currentTimeMillis();
    auction.Giveaway.save();
    return;
}

public static void resume() {
    if (auction.Giveaway.status == auction.Giveaway$Status.PAUSED) goto L10;
    return;
    L10:
    auction.Giveaway.pausedTotal = (auction.Giveaway.pausedTotal + (System.currentTimeMillis() - auction.Giveaway.pausedAt));
    auction.Giveaway.pausedAt = 0;
    auction.Giveaway.status = auction.Giveaway$Status.RUNNING;
    auction.Giveaway.save();
    return;
}

public static void draw() {
    if (auction.Giveaway.status == auction.Giveaway$Status.RUNNING) goto L19;
    if (auction.Giveaway.status == auction.Giveaway$Status.PAUSED) goto L19;
    return;
    L19:
    if (auction.Giveaway.status != auction.Giveaway$Status.PAUSED) goto L31;
    auction.Giveaway.resume();
    L31:
    auction.Giveaway.status = auction.Giveaway$Status.ENDED;
    auction.Giveaway.drawnAt = System.currentTimeMillis();
    if (auction.Giveaway.participants.isEmpty() ==0) goto L58;
    goto L86;
    L58:
    L86:
    auction.Giveaway.winner = ((auction.Giveaway$Participant)auction.Giveaway.participants.get(concurrent.ThreadLocalRandom.current().nextInt(auction.Giveaway.participants.size()))).name();
    if (auction.Giveaway.winner != null) goto L100;
    goto L116;
    L100:
    L116:
    auction.Giveaway.notify('Gewinner: ' + auction.Giveaway.winner + ' (' + auction.Giveaway.participants.size() + ' Teilnehmer). Übergib das Item manuell.');
    auction.Giveaway.save();
    return;
}

public static void cancel() {
    auction.Giveaway.status = auction.Giveaway$Status.NONE;
    auction.Giveaway.participants.clear();
    auction.Giveaway.winner = null;
    auction.Giveaway.drawnAt = 0;
    auction.Giveaway.save();
    return;
}

public static void reset() {
    auction.Giveaway.cancel();
    return;
}

public static auction.Giveaway$Status status() {
    return auction.Giveaway.status;
}

public static boolean active() {
    if (auction.Giveaway.status == auction.Giveaway$Status.RUNNING) goto L18;
    if (auction.Giveaway.status != auction.Giveaway$Status.PAUSED) goto L22;
    L18:
    goto L23;
    L22:
    L23:
    return 0;
}

public static world.item.ItemStack item() {
    return auction.Giveaway.item;
}

public static String word() {
    return auction.Giveaway.word;
}

public static int limit() {
    return auction.Giveaway.limit;
}

public static void participants() {
    return ?;
}

public static String winner() {
    return auction.Giveaway.winner;
}

public static long drawnAt() {
    return auction.Giveaway.drawnAt;
}

public static long drawing() {
    if (auction.Giveaway.status != auction.Giveaway$Status.ENDED) goto L33;
    if (auction.Giveaway.winner == null) goto L33;
    if (cmp((System.currentTimeMillis() - auction.Giveaway.drawnAt),4500) >=0) goto L33;
    goto L34;
    L33:
    L34:
    return 0;
}

public static long remainingMs() {
    if (auction.Giveaway.status != auction.Giveaway$Status.PAUSED) goto L15;
    goto L18;
    L15:
    L18:
    v0 = System.currentTimeMillis();
    return Math.max(0, (((auction.Giveaway.startedAt + auction.Giveaway.pausedTotal) + auction.Giveaway.durationMs) - v0));
}

public static float progress() {
    if (cmp(auction.Giveaway.durationMs,0) >0) goto L10;
    return 0;
    L10:
    return Math.max(0, Math.min(1, (((f)auction.Giveaway.remainingMs()) / ((f)auction.Giveaway.durationMs))));
}

public static long visible() {
    if (auction.Giveaway.active() !=0) goto L29;
    if (auction.Giveaway.status != auction.Giveaway$Status.ENDED) goto L33;
    if (cmp(System.currentTimeMillis(),(auction.Giveaway.drawnAt + 60000)) >=0) goto L33;
    L29:
    goto L34;
    L33:
    L34:
    return 0;
}

public static String joinCommand() {
    return '/msg ' + ui.FwmGfx.playerName() + ' ' + auction.Giveaway.word;
}

public static void accept(network.chat.Component message) {
    if (auction.Giveaway.status != auction.Giveaway$Status.RUNNING) goto L22;
    if (message == null) goto L22;
    if (auction.Giveaway.word.isEmpty() ==0) goto L23;
    L22:
    return;
    L23:
    v1 = chat.Messages.parse(message);
    if (v1 == null) goto L39;
    if (v1.sent() ==0) goto L40;
    L39:
    return;
    L40:
    v2 = v1.partner();
    v3 = v1.text();
    if (v3.trim().toLowerCase(Locale.ROOT).equals(auction.Giveaway.word.toLowerCase(Locale.ROOT)) !=0) goto L76;
    return;
    L76:
    v4 = auction.Giveaway.participants.iterator();
    L86:
    if (v4.hasNext() ==0) goto L124;
    v5 = ((auction.Giveaway$Participant)v4.next());
    if (v5.name().equalsIgnoreCase(v2) ==0) goto L121;
    return;
    L121:
    goto L86;
    L124:
    auction.Giveaway.participants.add(?);
    auction.Giveaway.save();
    return;
}

private static void notify(String text) {
    v1 = client.Minecraft.getInstance();
    if (v1.player != null) goto L12;
    return;
    L12:
    v1.player.sendSystemMessage(network.chat.Component.literal("[Giveaway] ").withStyle(ChatFormatting.LIGHT_PURPLE).append(network.chat.Component.literal(text).withStyle(ChatFormatting.WHITE)));
    return;
}

public static void tick(client.Minecraft client) {
    if (client.level == null) goto L14;
    if (client.player != null) goto L15;
    L14:
    return;
    L15:
    if (auction.Giveaway.restored !=0) goto L28;
    auction.Giveaway.restored = 1;
    auction.Giveaway.load();
    L28:
    if (auction.Giveaway.status != auction.Giveaway$Status.RUNNING) goto L48;
    if (cmp(auction.Giveaway.remainingMs(),0) >0) goto L48;
    auction.Giveaway.draw();
    L48:
    return;
}

private static void save() {
    if (auction.Giveaway.status == auction.Giveaway$Status.NONE) goto L18;
    if (auction.Giveaway.item.isEmpty() ==0) goto L26;
    L18:
    auction.StreamerStore.save("fwm-giveaway-state.json", null);
    return;
    L26:
    v0 = ?;
    v0.item = auction.StreamerStore.encode(auction.Giveaway.item);
    if (v0.item != null) goto L52;
    return;
    L52:
    v0.word = auction.Giveaway.word;
    v0.limit = auction.Giveaway.limit;
    v0.durationMs = auction.Giveaway.durationMs;
    v0.startedAt = auction.Giveaway.startedAt;
    v0.pausedAt = auction.Giveaway.pausedAt;
    v0.pausedTotal = auction.Giveaway.pausedTotal;
    v0.status = auction.Giveaway.status;
    v0.participants = ?;
    v0.winner = auction.Giveaway.winner;
    v0.drawnAt = auction.Giveaway.drawnAt;
    auction.StreamerStore.save("fwm-giveaway-state.json", v0);
    return;
}

private static boolean load() {
    v0 = ((auction.Giveaway$State)auction.StreamerStore.load("fwm-giveaway-state.json", auction.Giveaway$State.class));
    if (v0 == null) goto L41;
    if (v0.item == null) goto L41;
    if (v0.status == null) goto L41;
    if (v0.status != auction.Giveaway$Status.NONE) goto L42;
    L41:
    return;
    L42:
    if (v0.status != auction.Giveaway$Status.ENDED) goto L75;
    if (cmp(System.currentTimeMillis(),(v0.drawnAt + 86400000)) <=0) goto L75;
    auction.StreamerStore.save("fwm-giveaway-state.json", null);
    return;
    L75:
    v1 = auction.StreamerStore.decode(v0.item);
    if (v1.isEmpty() ==0) goto L91;
    return;
    L91:
    auction.Giveaway.item = v1;
    if (v0.word != null) goto L107;
    goto L111;
    L107:
    L111:
    auction.Giveaway.word = v0.word;
    auction.Giveaway.limit = v0.limit;
    auction.Giveaway.durationMs = v0.durationMs;
    auction.Giveaway.startedAt = v0.startedAt;
    auction.Giveaway.pausedAt = v0.pausedAt;
    auction.Giveaway.pausedTotal = v0.pausedTotal;
    auction.Giveaway.status = v0.status;
    auction.Giveaway.participants.clear();
    if (v0.participants == null) goto L184;
    auction.Giveaway.participants.addAll(v0.participants);
    L184:
    auction.Giveaway.winner = v0.winner;
    auction.Giveaway.drawnAt = v0.drawnAt;
    return;
}

static void <clinit>() {
    auction.Giveaway.item = world.item.ItemStack.EMPTY;
    auction.Giveaway.word = "";
    auction.Giveaway.status = auction.Giveaway$Status.NONE;
    auction.Giveaway.participants = ?;
    return;
}

