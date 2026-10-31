// class net/friendswithmoney/fwm/seventv/EmoteGlyphs  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FONT_PREFIX Ljava/lang/String;;
field HEIGHT F;
field SOURCES Ljava/util/Map;;
field RENDER_TYPES Ljava/util/Map;;
private void <init>() {
    return;
}

public static resources.Identifier fontId(seventv.Emote e) {
    return resources.Identifier.fromNamespaceAndPath("fwm", 'emote/' + e.key());
}

public static boolean isEmoteFont(resources.Identifier id) {
    if ("fwm".equals(id.getNamespace()) ==0) goto L28;
    if (id.getPath().startsWith("emote/") ==0) goto L28;
    goto L29;
    L28:
    L29:
    return 0;
}

public static Object sourceFor(resources.Identifier fontId) {
    if (seventv.EmoteGlyphs.isEmoteFont(fontId) !=0) goto L9;
    return null;
    L9:
    v1 = fontId.getPath().substring("emote/".length());
    return ((client.gui.GlyphSource)seventv.EmoteGlyphs.SOURCES.computeIfAbsent(v1, /*indy*/apply()));
}

private static seventv.EmoteTexture texture(String key) {
    v1 = seventv.EmoteTexture.byKey(key);
    if (v1 == null) goto L11;
    return v1;
    L11:
    v2 = seventv.EmoteRegistry.byKey(key);
    if (v2 != null) goto L22;
    return null;
    L22:
    return seventv.EmoteTexture.get(v2);
}

private static float width(seventv.EmoteTexture t) {
    if (t == null) goto L14;
    if (t.state == seventv.EmoteTexture$State.READY) goto L17;
    L14:
    return 9.0;
    L17:
    return Math.max(4.0, Math.min(36.0, (9.0 * t.aspect())));
}

static void <clinit>() {
    seventv.EmoteGlyphs.SOURCES = ?;
    seventv.EmoteGlyphs.RENDER_TYPES = ?;
    return;
}

