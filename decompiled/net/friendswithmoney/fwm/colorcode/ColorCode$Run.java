// class net/friendswithmoney/fwm/colorcode/ColorCode$Run  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field style Lnet/friendswithmoney/fwm/colorcode/CodeStyle;;
field text Ljava/lang/String;;
public void <init>(colorcode.CodeStyle style, String text) {
    this.style = style;
    this.text = text;
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

public colorcode.CodeStyle style() {
    return this.style;
}

public String text() {
    return this.text;
}

