// class net/friendswithmoney/fwm/game/TpaWatch  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REQUEST Ljava/util/regex/Pattern;;
field CANCEL Ljava/util/List;;
field ANSWER_MS J;
field INCOMING Ljava/util/regex/Pattern;;
field kind Lnet/friendswithmoney/fwm/game/TpaWatch$Kind;;
field target Ljava/lang/String;;
field sentAt J;
field askedKind Lnet/friendswithmoney/fwm/game/TpaWatch$Kind;;
field askedTarget Ljava/lang/String;;
field askedAt J;
field PATTERNS Ljava/util/Map;;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.message.v1.ClientSendMessageEvents.COMMAND.register(/*indy*/onSendCommandMessage());
    return;
}

public static boolean active() {
    if (game.TpaWatch.target == null) goto L10;
    goto L11;
    L10:
    L11:
    return 0;
}

public static game.TpaWatch$Kind kind() {
    return game.TpaWatch.kind;
}

public static String target() {
    return game.TpaWatch.target;
}

public static long remaining() {
    if (game.TpaWatch.target != null) goto L8;
    return 0;
    L8:
    v0 = ((System.currentTimeMillis() - game.TpaWatch.sentAt) / 1000);
    return ((i)Math.max(0, (((l)game.TpaWatch.timeout()) - v0)));
}

public static void clear() {
    game.TpaWatch.kind = null;
    game.TpaWatch.target = null;
    game.TpaWatch.sentAt = 0;
    return;
}

private static int timeout() {
    return Math.max(5, config.FwmConfig.INSTANCE.tpaTimeoutSeconds);
}

static long onCommand(String command) {
    if (config.Features.isOn("fwm.feature.tpawarn") !=0) goto L9;
    return;
    L9:
    if (command != null) goto L14;
    return;
    L14:
    v1 = command.trim();
    v2 = v1.toLowerCase(Locale.ROOT);
    v3 = game.TpaWatch.CANCEL.iterator();
    L36:
    if (v3.hasNext() ==0) goto L90;
    v4 = ((String)v3.next());
    if (v2.equals(v4) !=0) goto L79;
    if (v2.startsWith(v4 + ' ') ==0) goto L87;
    L79:
    game.TpaWatch.clear();
    game.TpaWatch.askedTarget = null;
    return;
    L87:
    goto L36;
    L90:
    v3 = game.TpaWatch.REQUEST.matcher(v1);
    if (v3.matches() !=0) goto L106;
    return;
    L106:
    if (v3.group("cmd").toLowerCase(Locale.ROOT).startsWith("tpaher") ==0) goto L132;
    goto L135;
    L132:
    L135:
    game.TpaWatch.askedKind = game.TpaWatch$Kind.TPA;
    game.TpaWatch.askedTarget = v3.group("name");
    game.TpaWatch.askedAt = System.currentTimeMillis();
    return;
}

public static void accept(network.chat.Component message) {
    if (message == null) goto L11;
    if (chat.ServerLine.fromServer(message) !=0) goto L12;
    L11:
    return;
    L12:
    v1 = chat.ServerLine.plain(message);
    if (v1.isEmpty() !=0) goto L37;
    if (game.TpaWatch.INCOMING.matcher(v1).find() ==0) goto L38;
    L37:
    return;
    L38:
    v2 = System.currentTimeMillis();
    if (game.TpaWatch.askedTarget == null) goto L64;
    if (cmp((v2 - game.TpaWatch.askedAt),5000) >=0) goto L64;
    goto L65;
    L64:
    L65:
    v4 = 0;
    v5 = game.TpaWatch.pattern(config.FwmConfig.INSTANCE.tpaSentPattern).matcher(v1);
    if (v5.find() ==0) goto L209;
    v6 = game.TpaWatch.group(v5, "name");
    if (v6 != null) goto L118;
    if (v4 ==0) goto L115;
    goto L116;
    L115:
    L116:
    v6 = null;
    L118:
    if (v6 != null) goto L124;
    return;
    L124:
    if (v4 ==0) goto L146;
    if (game.TpaWatch.askedTarget.equalsIgnoreCase(v6) ==0) goto L146;
    goto L170;
    L146:
    if (v1.toLowerCase(Locale.ROOT).contains("gefragt") ==0) goto L167;
    goto L170;
    L167:
    L170:
    game.TpaWatch.kind = game.TpaWatch$Kind.TPA;
    if (v4 ==0) goto L195;
    if (game.TpaWatch.askedTarget.equalsIgnoreCase(v6) ==0) goto L195;
    goto L197;
    L195:
    L197:
    game.TpaWatch.target = v6;
    game.TpaWatch.sentAt = v2;
    game.TpaWatch.askedTarget = null;
    return;
    L209:
    if (game.TpaWatch.pattern(config.FwmConfig.INSTANCE.tpaAlreadyPattern).matcher(v1).find() ==0) goto L233;
    game.TpaWatch.askedTarget = null;
    return;
    L233:
    if (v4 ==0) goto L262;
    if (game.TpaWatch.pattern(config.FwmConfig.INSTANCE.tpaRefusedPattern).matcher(v1).find() ==0) goto L262;
    game.TpaWatch.askedTarget = null;
    return;
    L262:
    if (game.TpaWatch.target != null) goto L269;
    return;
    L269:
    if (game.TpaWatch.pattern(config.FwmConfig.INSTANCE.tpaEndedPattern).matcher(v1).find() ==0) goto L292;
    game.TpaWatch.clear();
    return;
    L292:
    if (v1.toLowerCase(Locale.ROOT).contains(game.TpaWatch.target.toLowerCase(Locale.ROOT)) ==0) goto L336;
    if (game.TpaWatch.pattern(config.FwmConfig.INSTANCE.tpaDonePattern).matcher(v1).find() ==0) goto L336;
    game.TpaWatch.clear();
    L336:
    return;
}

private static String group(regex.Matcher matcher, String name) {
    return matcher.group(name);
    v2 = /*catch IllegalArgumentException*/ ex;
    return null;
}

private static Object pattern(String source) {
    if (source == null) goto L11;
    if (source.isBlank() ==0) goto L17;
    L11:
    return regex.Pattern.compile("(?!)");
    L17:
    return ((regex.Pattern)game.TpaWatch.PATTERNS.computeIfAbsent(source, /*indy*/apply()));
}

public static void tick(client.Minecraft client) {
    if (game.TpaWatch.target != null) goto L7;
    return;
    L7:
    if (client.player == null) goto L22;
    if (config.Features.isOn("fwm.feature.tpawarn") !=0) goto L26;
    L22:
    game.TpaWatch.clear();
    return;
    L26:
    if (game.TpaWatch.remaining() >0) goto L35;
    game.TpaWatch.clear();
    L35:
    return;
}

private static regex.Pattern lambda$pattern$0(String s) {
    return regex.Pattern.compile(s, 2);
    v1 = /*catch Exception*/ ex;
    System.err.println("[FWM] Bad TPA pattern '" + s + "': " + v1.getMessage());
    return regex.Pattern.compile("(?!)");
}

static void <clinit>() {
    game.TpaWatch.REQUEST = regex.Pattern.compile("^(?<cmd>tpa|tpask|tpahere|tpaher)\\s+(?<name>[.*]?[A-Za-z0-9_]{2,16})\\s*$", 2);
    game.TpaWatch.CANCEL = List.of("tpadelete", "tpacancel", "tpcancel", "tpdelete");
    game.TpaWatch.INCOMING = regex.Pattern.compile("hat dir eine Teleportations-Anfrage|hat gefragt, ob du|Du hast die Anfrage von|keine Anfrage von", 2);
    game.TpaWatch.PATTERNS = ?;
    return;
}

