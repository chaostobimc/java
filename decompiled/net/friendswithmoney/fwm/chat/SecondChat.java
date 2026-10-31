// class net/friendswithmoney/fwm/chat/SecondChat  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAX I;
field FADE_MS J;
field lines Ljava/util/Deque;;
field PATTERNS Ljava/util/Map;;
private void <init>() {
    return;
}

public static Object accept(network.chat.Component message) {
    if (message == null) goto L12;
    if (config.Features.isOn("fwm.tab.secondchat") !=0) goto L14;
    L12:
    return 1;
    L14:
    v1 = message.getString();
    if (v1 == null) goto L32;
    if (v1.isEmpty() ==0) goto L34;
    L32:
    return 1;
    L34:
    if (chat.ServerLine.fromServer(message) !=0) goto L52;
    if (chat.ServerLine.tagged(message, "[Nachricht]") !=0) goto L52;
    return 1;
    L52:
    if (chat.SecondChat.matches(message, v1) !=0) goto L69;
    if (chat.SecondChat.offersTeleport(message) !=0) goto L69;
    return 1;
    L69:
    chat.SecondChat.lines.addLast(?);
    L88:
    if (chat.SecondChat.lines.size() <= 50) goto L113;
    chat.SecondChat.lines.removeFirst();
    goto L88;
    L113:
    if (config.FwmConfig.INSTANCE.secondChatRemove !=0) goto L126;
    goto L127;
    L126:
    L127:
    return 0;
}

public static Object acceptActionBar(network.chat.Component message) {
    if (message == null) goto L21;
    if (config.Features.isOn("fwm.tab.secondchat") ==0) goto L21;
    if (config.FwmConfig.INSTANCE.secondChatMarket !=0) goto L22;
    L21:
    return;
    L22:
    v1 = chat.ServerLine.plain(message).trim();
    if (money.MoneyEvents.AH_SOLD.matcher(v1).find() !=0) goto L57;
    if (money.MoneyEvents.ORDER_FILLED.matcher(v1).find() !=0) goto L57;
    return;
    L57:
    chat.SecondChat.lines.addLast(?);
    L76:
    if (chat.SecondChat.lines.size() <= 50) goto L101;
    chat.SecondChat.lines.removeFirst();
    goto L76;
    L101:
    return;
}

private static boolean matches(network.chat.Component message, String line) {
    v2 = config.FwmConfig.INSTANCE;
    v3 = chat.ServerLine.plain(message).stripLeading();
    if (v2.secondChatTpa ==0) goto L29;
    if (chat.SecondChat.isTpa(message, v3) ==0) goto L29;
    return 1;
    L29:
    if (v2.secondChatClan ==0) goto L45;
    if (chat.SecondChat.isClan(v3) ==0) goto L45;
    return 1;
    L45:
    if (v2.secondChatMsg ==0) goto L62;
    if (chat.SecondChat.isMessage(message, v3) ==0) goto L62;
    return 1;
    L62:
    if (chat.SecondChat.fromServer(message, v3) ==0) goto L116;
    if (money.MoneyEvents.isMarketLine(v3) ==0) goto L82;
    return v2.secondChatMarket;
    L82:
    v4 = chat.SecondChat.isPayment(v3);
    if (v2.secondChatPay ==0) goto L102;
    if (v4 ==0) goto L102;
    return 1;
    L102:
    if (v2.secondChatHugoOther ==0) goto L116;
    if (v4 !=0) goto L116;
    return 1;
    L116:
    return chat.SecondChat.isCustom(line);
}

private static boolean isClan(String plain) {
    return plain.startsWith("[Clan]");
}

private static boolean isCustom(String line) {
    v1 = config.FwmConfig.INSTANCE.secondChatFilters;
    if (v1 != null) goto L13;
    return 0;
    L13:
    v2 = v1.iterator();
    L20:
    if (v2.hasNext() ==0) goto L56;
    v3 = ((config.ChatFilter)v2.next());
    if (v3 == null) goto L53;
    if (v3.matches(line) ==0) goto L53;
    return 1;
    L53:
    goto L20;
    L56:
    return 0;
}

private static boolean isTpa(network.chat.Component message, String plain) {
    if (chat.SecondChat.fromServer(message, plain) !=0) goto L10;
    return 0;
    L10:
    if (plain.contains("Teleportations-Anfrage") !=0) goto L28;
    if (plain.contains("Teleport-Anfrage") ==0) goto L32;
    L28:
    goto L33;
    L32:
    L33:
    return 0;
}

private static boolean offersTeleport(network.chat.Component message) {
    if (config.FwmConfig.INSTANCE.secondChatTpa ==0) goto L16;
    if (chat.ServerLine.fromServer(message) !=0) goto L18;
    L16:
    return 0;
    L18:
    return message.visit(/*indy*/accept(), network.chat.Style.EMPTY).isPresent();
}

private static String prefix() {
    v0 = config.FwmConfig.INSTANCE.serverPrefix;
    if (v0 == null) goto L18;
    if (v0.isBlank() ==0) goto L23;
    L18:
    goto L27;
    L23:
    L27:
    return v0.trim();
}

private static boolean fromServer(network.chat.Component message, String plain) {
    return chat.ServerLine.fromServer(message);
}

private static boolean isPayment(String plain) {
    v1 = config.FwmConfig.INSTANCE;
    v2 = v1.payPatterns.iterator();
    L14:
    if (v2.hasNext() ==0) goto L46;
    v3 = ((String)v2.next());
    if (chat.SecondChat.find(v3, plain) ==0) goto L43;
    return 1;
    L43:
    goto L14;
    L46:
    v2 = v1.moneyPayOutPatterns.iterator();
    L56:
    if (v2.hasNext() ==0) goto L88;
    v3 = ((String)v2.next());
    if (chat.SecondChat.find(v3, plain) ==0) goto L85;
    return 1;
    L85:
    goto L56;
    L88:
    return chat.SecondChat.find(v1.secondChatPayPattern, plain);
}

private static boolean find(String source, String line) {
    if (source == null) goto L11;
    if (source.isBlank() ==0) goto L13;
    L11:
    return 0;
    L13:
    v2 = ((regex.Pattern)chat.SecondChat.PATTERNS.computeIfAbsent(source, /*indy*/apply()));
    return v2.matcher(line).find();
}

private static chat.Messages$Parsed isMessage(network.chat.Component message, String plain) {
    if (chat.Messages.parse(message) == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public static void lines() {
    return ?;
}

public static void clear() {
    chat.SecondChat.lines.clear();
    return;
}

private static regex.Pattern lambda$find$0(String s) {
    return regex.Pattern.compile(s, 2);
    v1 = /*catch Exception*/ ex;
    return regex.Pattern.compile("(?!)");
}

private static Optional lambda$offersTeleport$0(network.chat.Style style, String part) {
    v3 = style.getClickEvent();
    if ((v3 instanceof network.chat.ClickEvent$RunCommand) ==0) goto L20;
    v2 = ((network.chat.ClickEvent$RunCommand)v3);
    goto L24;
    L20:
    return Optional.empty();
    L24:
    v3 = v2.command();
    if (v3.contains("tpaccept") !=0) goto L49;
    if (v3.contains("tpdeny") ==0) goto L58;
    L49:
    goto L61;
    L58:
    L61:
    return Optional.empty();
}

static void <clinit>() {
    chat.SecondChat.lines = ?;
    chat.SecondChat.PATTERNS = ?;
    return;
}

