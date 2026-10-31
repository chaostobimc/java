// class net/friendswithmoney/fwm/game/AutoTpa  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REQUEST Ljava/util/regex/Pattern;;
field ACCEPT Ljava/util/regex/Pattern;;
field pendingCommand Ljava/lang/String;;
field pendingName Ljava/lang/String;;
field pendingTicks I;
private void <init>() {
    return;
}

public static int accept(network.chat.Component message) {
    if (message == null) goto L12;
    if (config.Features.isOn("fwm.tab.autotpa") !=0) goto L13;
    L12:
    return;
    L13:
    if (config.FwmConfig.INSTANCE.autoTpaNames.isEmpty() ==0) goto L28;
    return;
    L28:
    v1 = game.AutoTpa.acceptCommand(message);
    if (chat.ServerLine.fromServer(message) !=0) goto L41;
    return;
    L41:
    v2 = chat.ServerLine.plain(message);
    v3 = game.AutoTpa.REQUEST.matcher(v2);
    if (v3.find() !=0) goto L62;
    return;
    L62:
    v4 = v3.group("name");
    v5 = v2.toLowerCase(Locale.ROOT).contains("gefragt");
    if (v5 ==0) goto L99;
    if (config.FwmConfig.INSTANCE.autoTpaHere !=0) goto L99;
    return;
    L99:
    if (game.AutoTpa.listed(v4) !=0) goto L108;
    return;
    L108:
    if (v1 == null) goto L122;
    goto L141;
    L122:
    if (v5 ==0) goto L134;
    goto L136;
    L134:
    L136:
    L141:
    v6 = 'tpaccept ' + " tpahere" + " tpa";
    if (v6.startsWith("/") ==0) goto L161;
    v6 = v6.substring(1);
    L161:
    if (game.AutoTpa.ACCEPT.matcher(v6).matches() !=0) goto L176;
    return;
    L176:
    game.AutoTpa.pendingCommand = v6;
    game.AutoTpa.pendingName = v4;
    game.AutoTpa.pendingTicks = Math.max(1, config.FwmConfig.INSTANCE.autoTpaDelayTicks);
    return;
}

public static void tick(client.Minecraft client) {
    if (game.AutoTpa.pendingCommand != null) goto L7;
    return;
    L7:
    if (client.player == null) goto L29;
    if (client.getConnection() == null) goto L29;
    if (config.Features.isOn("fwm.tab.autotpa") !=0) goto L34;
    L29:
    game.AutoTpa.pendingCommand = null;
    return;
    L34:
    game.AutoTpa.pendingTicks = (game.AutoTpa.pendingTicks - 1);
    if ((game.AutoTpa.pendingTicks - 1) <=0) goto L47;
    return;
    L47:
    client.getConnection().sendCommand(game.AutoTpa.pendingCommand);
    client.player.sendSystemMessage(network.chat.Component.literal("[FWM] ").withStyle(ChatFormatting.GREEN).append(network.chat.Component.literal(ui.Lang.t('TPA von ' + game.AutoTpa.pendingName + ' automatisch angenommen.', 'Accepted ' + game.AutoTpa.pendingName + "'s TPA automatically.")).withStyle(ChatFormatting.GRAY)));
    game.AutoTpa.pendingCommand = null;
    return;
}

private static boolean listed(String name) {
    if (name != null) goto L6;
    return 0;
    L6:
    v1 = name.replaceFirst("^[.*]", "");
    v2 = config.FwmConfig.INSTANCE.autoTpaNames.iterator();
    L27:
    if (v2.hasNext() ==0) goto L88;
    v3 = ((String)v2.next());
    if (v3 != null) goto L53;
    goto L27;
    L53:
    v4 = v3.trim().replaceFirst("^[.*]", "");
    if (v4.isEmpty() !=0) goto L85;
    if (v4.equalsIgnoreCase(v1) ==0) goto L85;
    return 1;
    L85:
    goto L27;
    L88:
    return 0;
}

private static Object acceptCommand(network.chat.Component message) {
    v1 = message.visit(/*indy*/accept(), network.chat.Style.EMPTY);
    return ((String)v1.orElse(null));
}

private static Optional lambda$acceptCommand$0(network.chat.Style style, String part) {
    v3 = style.getClickEvent();
    if ((v3 instanceof network.chat.ClickEvent$RunCommand) ==0) goto L46;
    v2 = ((network.chat.ClickEvent$RunCommand)v3);
    if (v2.command().toLowerCase(Locale.ROOT).contains("tpaccept") ==0) goto L46;
    return Optional.of(v2.command().trim());
    L46:
    return Optional.empty();
}

static regex.Pattern <clinit>() {
    game.AutoTpa.REQUEST = regex.Pattern.compile("(?<name>[.*]?[A-Za-z0-9_]{2,16})\\s+hat\\s+(?:dir\\s+eine\\s+Teleportations-Anfrage|gefragt,\\s+ob\\s+du)", 2);
    game.AutoTpa.ACCEPT = regex.Pattern.compile("^/?tpaccept(?:\\s+[.*]?[A-Za-z0-9_]{2,16}(?:\\s+(?:tpa|tpahere))?)?$", 2);
    return;
}

