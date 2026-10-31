// class net/friendswithmoney/fwm/net/TrustedClans$BotFakeTag  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field tag Ljava/lang/String;;
field clan Lnet/friendswithmoney/fwm/net/TrustedClans$Clan;;
private void <init>(String tag, net.TrustedClans$Clan clan) {
    this.tag = tag;
    this.clan = clan;
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

public String tag() {
    return this.tag;
}

public net.TrustedClans$Clan clan() {
    return this.clan;
}

