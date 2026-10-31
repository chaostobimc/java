// class net/friendswithmoney/fwm/ui/panel/OverviewPanel$Tile  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field labelKey Ljava/lang/String;;
field locked Z;
field category I;
public void <init>(String labelKey, boolean locked, int category) {
    this.labelKey = labelKey;
    this.locked = locked;
    this.category = category;
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

public String labelKey() {
    return this.labelKey;
}

public boolean locked() {
    return this.locked;
}

public int category() {
    return this.category;
}

