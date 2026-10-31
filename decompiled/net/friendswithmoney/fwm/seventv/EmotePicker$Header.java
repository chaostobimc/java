// class net/friendswithmoney/fwm/seventv/EmotePicker$Header  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field title Ljava/lang/String;;
field color I;
private void <init>(String title, int color) {
    this.title = title;
    this.color = color;
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

public String title() {
    return this.title;
}

public int color() {
    return this.color;
}

