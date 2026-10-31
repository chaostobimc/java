// class net/friendswithmoney/fwm/money/MoneyEvents$Fee  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field cents J;
field item Ljava/lang/String;;
field at J;
private void <init>(long cents, String a2, long item) {
    this.cents = cents;
    this.item = item;
    this.at = v4;
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

public long cents() {
    return this.cents;
}

public String item() {
    return this.item;
}

public long at() {
    return this.at;
}

