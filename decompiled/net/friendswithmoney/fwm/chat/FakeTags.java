// class net/friendswithmoney/fwm/chat/FakeTags  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static Optional mark(network.chat.Component message) {
    if (message != null) goto L6;
    return null;
    L6:
    v1 = message.getString();
    if (v1 == null) goto L24;
    if (v1.isEmpty() ==0) goto L26;
    L24:
    return message;
    L26:
    v2 = net.ClanTags.fakeSpans(v1);
    if (v2.isEmpty() ==0) goto L42;
    return message;
    L42:
    v3 = network.chat.Component.empty();
    new int[1][0] = 0;
    v4 = new int[1];
    message.visit(/*indy*/accept(v3, v4, v2), network.chat.Style.EMPTY);
    return v3;
}

public static String fakeIn(String line) {
    return net.ClanTags.fakeIn(line);
}

private static network.chat.MutableComponent append(network.chat.MutableComponent out, String string, network.chat.Style style, int offset, List spans) {
    v5 = 0;
    L3:
    if (v5 >= string.length()) goto L132;
    v6 = (offset + v5);
    v7 = chat.FakeTags.covering(v6, spans);
    if (v7 == null) goto L87;
    v8 = Math.min(string.length(), (v7[1] - offset));
    out.append(network.chat.Component.literal(string.substring(v5, v8)).setStyle(style.withColor(ChatFormatting.RED).withStrikethrough(Boolean.valueOf(1))));
    v5 = v8;
    goto L3;
    L87:
    v8 = Math.min(string.length(), (chat.FakeTags.nextStart(v6, spans) - offset));
    out.append(network.chat.Component.literal(string.substring(v5, v8)).setStyle(style));
    v5 = v8;
    goto L3;
    L132:
    return;
}

private static Object covering(int index, List spans) {
    v2 = spans.iterator();
    L7:
    if (v2.hasNext() ==0) goto L45;
    v3 = (([I)v2.next());
    if (index < v3[0]) goto L42;
    if (index >= v3[1]) goto L42;
    return v3;
    L42:
    goto L7;
    L45:
    return null;
}

private static Object nextStart(int index, List spans) {
    v2 = 2147483647;
    v3 = spans.iterator();
    L10:
    if (v3.hasNext() ==0) goto L54;
    v4 = (([I)v3.next());
    if (v4[0] <= index) goto L51;
    if (v4[0] >= v2) goto L51;
    v2 = v4[0];
    L51:
    goto L10;
    L54:
    return v2;
}

private static Optional lambda$mark$0(network.chat.MutableComponent out, int[] offset, List spans, network.chat.Style style, String string) {
    chat.FakeTags.append(out, string, style, offset[0], spans);
    offset[0] = (offset[0] + string.length());
    return Optional.empty();
}

