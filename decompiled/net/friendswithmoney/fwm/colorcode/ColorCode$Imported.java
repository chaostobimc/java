// class net/friendswithmoney/fwm/colorcode/ColorCode$Imported  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field code Lnet/friendswithmoney/fwm/colorcode/ColorCode;;
field mode Lnet/friendswithmoney/fwm/colorcode/ColorCode$Mode;;
public void <init>(colorcode.ColorCode code, colorcode.ColorCode$Mode mode) {
    this.code = code;
    this.mode = mode;
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

public colorcode.ColorCode code() {
    return this.code;
}

public colorcode.ColorCode$Mode mode() {
    return this.mode;
}

