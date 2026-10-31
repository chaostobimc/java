// class net/friendswithmoney/fwm/net/ClanLogos$Logo  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field id Ljava/lang/String;;
field logo Ljava/lang/String;;
field logoVersion J;
field etag Ljava/lang/String;;
private void <init>(String id, String logo, long logoVersion, String a4) {
    this.id = id;
    this.logo = logo;
    this.logoVersion = logoVersion;
    this.etag = v5;
    return;
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

public String logo() {
    return this.logo;
}

public long logoVersion() {
    return this.logoVersion;
}

public String etag() {
    return this.etag;
}

