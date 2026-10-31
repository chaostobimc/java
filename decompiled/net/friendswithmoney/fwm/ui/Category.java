// class net/friendswithmoney/fwm/ui/Category  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field labelKey Ljava/lang/String;;
field panel Ljava/util/function/Supplier;;
field locked Z;
public void <init>(String labelKey, function.Supplier panel, boolean locked) {
    this.labelKey = labelKey;
    this.panel = panel;
    this.locked = locked;
    return;
}

public static void of(String labelKey, function.Supplier panel) {
    return ?;
}

public static void locked(String labelKey, function.Supplier panel) {
    return ?;
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

public function.Supplier panel() {
    return this.panel;
}

public boolean locked() {
    return this.locked;
}

