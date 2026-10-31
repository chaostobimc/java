// class net/friendswithmoney/fwm/ui/widget/ChipsRow$Chip  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field label Ljava/lang/String;;
field value I;
public void <init>(String label, int value) {
    this.label = label;
    this.value = value;
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

public String label() {
    return this.label;
}

public int value() {
    return this.value;
}

