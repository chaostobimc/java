// class net/friendswithmoney/fwm/ui/panel/TabbedPanel$Tab  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field labelKey Ljava/lang/String;;
field panel Ljava/util/function/Supplier;;
public void <init>(String labelKey, function.Supplier panel) {
    this.labelKey = labelKey;
    this.panel = panel;
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

public function.Supplier panel() {
    return this.panel;
}

