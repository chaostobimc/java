// class net/friendswithmoney/fwm/seventv/EmoteText  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field TOKEN Ljava/util/regex/Pattern;;
field MAX_PER_MESSAGE I;
field GLYPH Ljava/lang/String;;
public static boolean count(String text) {
    if (text != null) goto L12;
    goto L13;
    L12:
    L13:
    v1 = "".matcher(text);
    v2 = 0;
    L19:
    if (v1.find() ==0) goto L32;
    v2 = v2 + 1;
    goto L19;
    L32:
    return v2;
}

public static void roomFor(String fieldText) {
    if (seventv.EmoteText.count(fieldText) >= 5) goto L10;
    return 1;
    L10:
    ui.FwmNotice.show("EMOTES", ui.Lang.t("H\u00f6chstens 5 Emotes pro Nachricht", "At most 5 emotes per message"), "", 15911244, 3000);
    return 0;
}

private void <init>() {
    return;
}

public static boolean mayContainEmote(network.chat.Component c) {
    v1 = c.getString();
    if (v1.indexOf(58) <0) goto L33;
    if (seventv.EmoteText.TOKEN.matcher(v1).find() ==0) goto L33;
    goto L34;
    L33:
    L34:
    return 0;
}

public static Optional transform(network.chat.Component in) {
    if (seventv.EmoteText.mayContainEmote(in) !=0) goto L9;
    return in;
    L9:
    v1 = network.chat.Component.empty();
    new boolean[1][0] = 0;
    v2 = new boolean[1];
    new int[1][0] = 0;
    v3 = new int[1];
    in.visit(/*indy*/accept(v1, v2, v3), network.chat.Style.EMPTY);
    if (v2[0] ==0) goto L57;
    goto L58;
    L57:
    L58:
    return in;
}

private static network.chat.MutableComponent appendSegment(network.chat.MutableComponent out, network.chat.Style style, String text, boolean[] changed, int[] made) {
    if (text.isEmpty() ==0) goto L8;
    return;
    L8:
    v6 = style.getFont();
    if ((v6 instanceof network.chat.FontDescription$Resource) ==0) goto L65;
    v5 = ((network.chat.FontDescription$Resource)v6);
    if (seventv.EmoteGlyphs.isEmoteFont(v5.id()) ==0) goto L65;
    out.append(network.chat.Component.literal(text).setStyle(style));
    made[0] = (made[0] + text.length());
    return;
    L65:
    v5 = seventv.EmoteText.TOKEN.matcher(text);
    v6 = 0;
    L77:
    if (v5.find() ==0) goto L198;
    v7 = v5.group(1);
    if (made[0] < 5) goto L104;
    goto L198;
    L104:
    v8 = seventv.EmoteRegistry.byName(v7);
    if (v8 != null) goto L132;
    if (seventv.EmoteRegistry.isKnownMissing(v7) !=0) goto L77;
    seventv.EmoteRegistry.resolveAsync(v7);
    goto L77;
    L132:
    if (v5.start() <= v6) goto L165;
    out.append(network.chat.Component.literal(text.substring(v6, v5.start())).setStyle(style));
    L165:
    out.append(seventv.EmoteText.emoteComponent(v8, style));
    made[0] = (made[0] + 1);
    v6 = v5.end();
    changed[0] = 1;
    goto L77;
    L198:
    if (v6 >= text.length()) goto L225;
    out.append(network.chat.Component.literal(text.substring(v6)).setStyle(style));
    L225:
    return;
}

public static network.chat.MutableComponent emoteComponent(seventv.Emote e, network.chat.Style base) {
    v2 = base.withFont(?).withHoverEvent(?).withInsertion(e.chatCode());
    seventv.EmoteTexture.get(e);
    seventv.SevenTv.inChat(e.key());
    return network.chat.Component.literal("\ue000").setStyle(v2);
}

private static Optional lambda$transform$0(network.chat.MutableComponent out, boolean[] changed, int[] made, network.chat.Style style, String text) {
    seventv.EmoteText.appendSegment(out, style, text, changed, made);
    return Optional.empty();
}

static regex.Pattern <clinit>() {
    seventv.EmoteText.TOKEN = regex.Pattern.compile(":([A-Za-z0-9_\\-!?.'()]{2,40}):");
    return;
}

