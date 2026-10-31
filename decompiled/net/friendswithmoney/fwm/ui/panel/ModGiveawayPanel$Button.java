// class net/friendswithmoney/fwm/ui/panel/ModGiveawayPanel$Button  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field x I;
field y I;
field w I;
field h I;
field id Ljava/lang/String;;
private void <init>(int x, int y, int w, int h, String id) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.id = id;
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

public int x() {
    return this.x;
}

public int y() {
    return this.y;
}

public int w() {
    return this.w;
}

public int h() {
    return this.h;
}

public String id() {
    return this.id;
}

