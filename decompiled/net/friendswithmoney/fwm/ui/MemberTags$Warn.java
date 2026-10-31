// class net/friendswithmoney/fwm/ui/MemberTags$Warn  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field fake Z;
field foreign Z;
field market Ljava/lang/String;;
field at J;
private void <init>(boolean fake, boolean foreign, String market, long at) {
    this.fake = fake;
    this.foreign = foreign;
    this.market = market;
    this.at = at;
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

public boolean fake() {
    return this.fake;
}

public boolean foreign() {
    return this.foreign;
}

public String market() {
    return this.market;
}

public long at() {
    return this.at;
}

