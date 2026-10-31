// class net/friendswithmoney/fwm/game/FightKits$Missing  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field item Ljava/lang/String;;
field count I;
public void <init>(String item, int count) {
    this.item = item;
    this.count = count;
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

public String item() {
    return this.item;
}

public int count() {
    return this.count;
}

