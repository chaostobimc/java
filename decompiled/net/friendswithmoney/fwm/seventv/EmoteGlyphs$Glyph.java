// class net/friendswithmoney/fwm/seventv/EmoteGlyphs$Glyph  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field key Ljava/lang/String;;
private void <init>(String key) {
    this.key = key;
    return;
}

public blaze3d.font.GlyphInfo info() {
    v1 = seventv.EmoteGlyphs.width(seventv.EmoteGlyphs.texture(this.key));
    return blaze3d.font.GlyphInfo.simple((v1 + 1));
}

public void createGlyph(float x, float y, int color, int shadowColor, network.chat.Style style, float boldOffset, float shadowOffset) {
    v8 = seventv.EmoteGlyphs.texture(this.key);
    if (v8 == null) goto L25;
    if (v8.state == seventv.EmoteTexture$State.READY) goto L27;
    L25:
    return null;
    L27:
    v9 = client.Minecraft.getInstance().getTextureManager().getTexture(v8.id);
    if (v9 != null) goto L50;
    return null;
    L50:
    v10 = v9.getTextureView();
    if (v10 != null) goto L64;
    return null;
    L64:
    v11 = ((client.gui.font.GlyphRenderTypes)seventv.EmoteGlyphs.RENDER_TYPES.computeIfAbsent(v8.id, /*indy*/apply()));
    v12 = seventv.EmoteGlyphs.width(v8);
    v13 = ((color >>> 24) & 255);
    v14 = ((v13 << 24) | 16777215);
    return ?;
}

public final String toString() {
    return /*indy*/toString(this);
}

public final int hashCode() {
    return /*indy*/hashCode(this);
}

public final boolean equals(Object o) {
    return /*indy*/equals(this, o);
}

public String key() {
    return this.key;
}

