// class net/friendswithmoney/fwm/auction/Auction$Bid  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field name Ljava/lang/String;;
field amount J;
field time J;
public void <init>(String name, long amount, long a3) {
    this.name = name;
    this.amount = amount;
    this.time = v4;
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

public long amount() {
    return this.amount;
}

public long time() {
    return this.time;
}

