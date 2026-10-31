// class net/friendswithmoney/fwm/ui/FwmScreen$SidebarRow  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field categoryIndex I;
field headingKey Ljava/lang/String;;
private void <init>(int categoryIndex, String headingKey) {
    this.categoryIndex = categoryIndex;
    this.headingKey = headingKey;
    return;
}

boolean isHeading() {
    if (this.categoryIndex >=0) goto L11;
    goto L12;
    L11:
    L12:
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

public int categoryIndex() {
    return this.categoryIndex;
}

public String headingKey() {
    return this.headingKey;
}

