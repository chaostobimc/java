// class net/friendswithmoney/fwm/textures/TextureMixer$Category  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field id Ljava/lang/String;;
field de Ljava/lang/String;;
field en Ljava/lang/String;;
field group Ljava/lang/String;;
field prefixes Ljava/util/List;;
field preview Ljava/lang/String;;
public void <init>(String id, String de, String en, String group, List prefixes, String preview) {
    this.id = id;
    this.de = de;
    this.en = en;
    this.group = group;
    this.prefixes = prefixes;
    this.preview = preview;
    return;
}

public String label() {
    return ui.Lang.t(this.de, this.en);
}

public boolean tiled() {
    if (this.preview == null) goto L23;
    if (this.preview.contains("/block/") ==0) goto L23;
    goto L24;
    L23:
    L24:
    return 0;
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

public String id() {
    return this.id;
}

public String de() {
    return this.de;
}

public String en() {
    return this.en;
}

public String group() {
    return this.group;
}

public List prefixes() {
    return this.prefixes;
}

public String preview() {
    return this.preview;
}

