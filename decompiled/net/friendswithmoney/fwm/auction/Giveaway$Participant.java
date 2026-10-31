// class net/friendswithmoney/fwm/auction/Giveaway$Participant  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field name Ljava/lang/String;;
field joinedAt J;
public void <init>(String name, long joinedAt) {
    this.name = name;
    this.joinedAt = joinedAt;
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

public String name() {
    return this.name;
}

public long joinedAt() {
    return this.joinedAt;
}

