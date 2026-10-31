// class net/friendswithmoney/fwm/chat/ServerLine  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field SERVER_WORDS Ljava/util/Set;;
field NAME_COLON Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static String plain(network.chat.Component message) {
    if (message != null) goto L9;
    goto L18;
    L9:
    L18:
    return chat.ServerLine.strip(message.getString());
}

public static String strip(String line) {
    if (line == null) goto L11;
    if (line.isEmpty() ==0) goto L14;
    L11:
    return "";
    L14:
    v1 = ?;
    v2 = 0;
    L28:
    if (v2 >= line.length()) goto L84;
    v3 = line.charAt(v2);
    if (v3 != 167) goto L65;
    if ((v2 + 1) >= line.length()) goto L65;
    v2 = v2 + 1;
    goto L78;
    L65:
    if (chat.ServerLine.invisible(v3) !=0) goto L78;
    v1.append(v3);
    L78:
    v2 = v2 + 1;
    goto L28;
    L84:
    return v1.toString();
}

private static boolean invisible(char c) {
    if (c == 8203) goto L34;
    if (c == 8204) goto L34;
    if (c == 8205) goto L34;
    if (c == 8288) goto L34;
    if (c != 65279) goto L38;
    L34:
    goto L39;
    L38:
    L39:
    return 0;
}

public static boolean fromServer(network.chat.Component message) {
    return chat.ServerLine.tagged(message, chat.ServerLine.prefix());
}

public static boolean tagged(network.chat.Component message, String tag) {
    if (chat.ServerLine.startsColoured(message, tag) !=0) goto L10;
    return 0;
    L10:
    v2 = chat.ServerLine.plain(message).substring(tag.length());
    v3 = chat.ServerLine.NAME_COLON.matcher(v2);
    if (v3.find() ==0) goto L59;
    if (chat.ServerLine.SERVER_WORDS.contains(v3.group(1).toLowerCase(Locale.ROOT)) ==0) goto L63;
    L59:
    goto L64;
    L63:
    L64:
    return 0;
}

public static boolean startsColoured(network.chat.Component message, String prefix) {
    if (message == null) goto L15;
    if (prefix == null) goto L15;
    if (prefix.isEmpty() ==0) goto L17;
    L15:
    return 0;
    L17:
    if (chat.ServerLine.plain(message).startsWith(prefix) !=0) goto L30;
    return 0;
    L30:
    new int[1][0] = 0;
    v2 = new int[1];
    new boolean[1][0] = 0;
    v3 = new boolean[1];
    v4 = message.visit(/*indy*/accept(v3, v2, prefix), network.chat.Style.EMPTY);
    return ((Boolean)v4.orElse(Boolean.FALSE)).booleanValue();
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

private static Optional lambda$startsColoured$0(boolean[] legacy, int[] seen, String prefix, network.chat.Style style, String part) {
    v5 = 0;
    L3:
    if (v5 >= part.length()) goto L182;
    v6 = part.charAt(v5);
    if (v6 != 167) goto L113;
    if ((v5 + 1) >= part.length()) goto L113;
    v5 = v5 + 1;
    v7 = Character.toLowerCase(part.charAt(v5));
    if (v7 != 114) goto L71;
    legacy[0] = 0;
    goto L176;
    L71:
    if (v7 < 48) goto L85;
    if (v7 <= 57) goto L106;
    L85:
    if (v7 < 97) goto L99;
    if (v7 <= 102) goto L106;
    L99:
    if (v7 != 120) goto L176;
    L106:
    legacy[0] = 1;
    goto L176;
    L113:
    if (chat.ServerLine.invisible(v6) ==0) goto L124;
    goto L176;
    L124:
    if (seen[0] < prefix.length()) goto L141;
    return Optional.of(Boolean.TRUE);
    L141:
    if (Character.isLetter(v6) ==0) goto L169;
    if (style.getColor() != null) goto L169;
    if (legacy[0] !=0) goto L169;
    return Optional.of(Boolean.FALSE);
    L169:
    seen[0] = (seen[0] + 1);
    L176:
    v5 = v5 + 1;
    goto L3;
    L182:
    if (seen[0] < prefix.length()) goto L201;
    goto L204;
    L201:
    L204:
    return Optional.empty();
}

static regex.Pattern <clinit>() {
    chat.ServerLine.SERVER_WORDS = Set.of("gedroppt", "geliefert", "achtung", "abgeholt", "verwendung", "sellmulti");
    chat.ServerLine.NAME_COLON = regex.Pattern.compile("^\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:");
    return;
}

