// class net/friendswithmoney/fwm/seventv/Emote  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field provider Lnet/friendswithmoney/fwm/seventv/Emote$Provider;;
field id Ljava/lang/String;;
field name Ljava/lang/String;;
field animated Z;
field imageType Ljava/lang/String;;
public void <init>(seventv.Emote$Provider provider, String id, String name, boolean animated, String imageType) {
    this.provider = provider;
    this.id = id;
    this.name = name;
    this.animated = animated;
    this.imageType = imageType;
    return;
}

public String key() {
    return this.provider.key + '_' + this.id.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
}

// DECOMPILE ERROR in imageUrls: 'dict' object has no attribute 'op_type'
public String chatCode() {
    return ':' + this.name + ':';
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

public seventv.Emote$Provider provider() {
    return this.provider;
}

public String id() {
    return this.id;
}

public String name() {
    return this.name;
}

public boolean animated() {
    return this.animated;
}

public String imageType() {
    return this.imageType;
}

