// class net/friendswithmoney/fwm/game/SpawnerSidePanel$Hit  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field x I;
field y I;
field w I;
field h I;
field action Ljava/lang/Runnable;;
private void <init>(int x, int y, int w, int h, Runnable action) {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.action = action;
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

public Runnable action() {
    return this.action;
}

