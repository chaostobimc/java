// class net/friendswithmoney/fwm/ui/panel/MembersPanel$Line  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field clan Ljava/lang/String;;
field member Lnet/friendswithmoney/fwm/net/MemberData$Member;;
private void <init>(String clan, net.MemberData$Member member) {
    this.clan = clan;
    this.member = member;
    return;
}

boolean isHeading() {
    if (this.member != null) goto L11;
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

public String clan() {
    return this.clan;
}

public net.MemberData$Member member() {
    return this.member;
}

