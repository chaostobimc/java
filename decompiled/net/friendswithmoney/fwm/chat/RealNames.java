// class net/friendswithmoney/fwm/chat/RealNames  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field INVISIBLE Ljava/lang/String;;
field NAME Ljava/lang/String;;
field CHAT Ljava/util/regex/Pattern;;
field FROM Ljava/util/regex/Pattern;;
field TO Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static network.chat.Component mark(network.chat.Component message) {
    if (message != null) goto L6;
    return null;
    L6:
    v1 = message.getString();
    if (v1 == null) goto L24;
    if (v1.isEmpty() ==0) goto L26;
    L24:
    return message;
    L26:
    v2 = chat.RealNames.match(v1);
    if (v2 != null) goto L37;
    return message;
    L37:
    v3 = net.FakeList.realBadge(v2.group(1));
    if (v3 != null) goto L52;
    return message;
    L52:
    return chat.RealNames.insertAt(message, v2.end(1), v3);
}

private static boolean match(String flat) {
    new regex.Pattern[3][0] = chat.RealNames.FROM;
    new regex.Pattern[3][1] = chat.RealNames.TO;
    new regex.Pattern[3][2] = chat.RealNames.CHAT;
    v1 = new regex.Pattern[3];
    v2 = v1.length;
    v3 = 0;
    L28:
    if (v3 >= v2) goto L63;
    v4 = v1[v3];
    v5 = v4.matcher(flat);
    if (v5.find() ==0) goto L57;
    return v5;
    L57:
    v3 = v3 + 1;
    goto L28;
    L63:
    return null;
}

private static network.chat.MutableComponent insertAt(network.chat.Component message, int at, network.chat.Component insert) {
    v3 = network.chat.Component.empty();
    new int[1][0] = 0;
    v4 = new int[1];
    new boolean[1][0] = 0;
    v5 = new boolean[1];
    message.visit(/*indy*/accept(v4, v5, at, v3, insert), network.chat.Style.EMPTY);
    if (v5[0] !=0) goto L57;
    v3.append(insert);
    L57:
    return v3;
}

private static Optional lambda$insertAt$0(int[] offset, boolean[] done, int at, network.chat.MutableComponent out, network.chat.Component insert, network.chat.Style style, String string) {
    v7 = offset[0];
    v8 = (v7 + string.length());
    if (done[0] !=0) goto L109;
    if (at < v7) goto L109;
    if (at > v8) goto L109;
    v9 = (at - v7);
    if (v9 <=0) goto L65;
    out.append(network.chat.Component.literal(string.substring(0, v9)).setStyle(style));
    L65:
    out.append(insert);
    if (v9 >= string.length()) goto L102;
    out.append(network.chat.Component.literal(string.substring(v9)).setStyle(style));
    L102:
    done[0] = 1;
    goto L124;
    L109:
    out.append(network.chat.Component.literal(string).setStyle(style));
    L124:
    offset[0] = v8;
    return Optional.empty();
}

static regex.Pattern <clinit>() {
    chat.RealNames.CHAT = regex.Pattern.compile("^(?:\\[[^\\]]{1,12}\\]\\s+)?(?:PREMIUM\\s+)?[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*:\\s");
    chat.RealNames.FROM = regex.Pattern.compile("^\\[Nachricht\\]\\s+[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*\\s+->\\s+Du:");
    chat.RealNames.TO = regex.Pattern.compile("^\\[Nachricht\\]\\s+Du\\s+->\\s+[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*:");
    return;
}

