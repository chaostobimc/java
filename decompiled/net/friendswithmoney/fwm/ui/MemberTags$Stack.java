// class net/friendswithmoney/fwm/ui/MemberTags$Stack  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field top D;
field tick J;
field sneaking Z;
private void <init>(double top, long a2, boolean tick) {
    this.top = top;
    this.tick = tick;
    this.sneaking = v5;
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

public double top() {
    return this.top;
}

public long tick() {
    return this.tick;
}

public boolean sneaking() {
    return this.sneaking;
}

