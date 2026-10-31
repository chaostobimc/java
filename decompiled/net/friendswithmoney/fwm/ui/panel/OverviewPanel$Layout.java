// class net/friendswithmoney/fwm/ui/panel/OverviewPanel$Layout  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field switchable Ljava/util/List;;
field alwaysOn Ljava/util/List;;
field locked Ljava/util/List;;
field switchableTop I;
field alwaysTop I;
field lockedTop I;
field contentBottom I;
private void <init>(List switchable, List alwaysOn, List locked, int switchableTop, int alwaysTop, int lockedTop, int contentBottom) {
    this.switchable = switchable;
    this.alwaysOn = alwaysOn;
    this.locked = locked;
    this.switchableTop = switchableTop;
    this.alwaysTop = alwaysTop;
    this.lockedTop = lockedTop;
    this.contentBottom = contentBottom;
    return;
}

int hits() {
    return ((this.switchable.size() + this.alwaysOn.size()) + this.locked.size());
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

public List switchable() {
    return this.switchable;
}

public List alwaysOn() {
    return this.alwaysOn;
}

public List locked() {
    return this.locked;
}

public int switchableTop() {
    return this.switchableTop;
}

public int alwaysTop() {
    return this.alwaysTop;
}

public int lockedTop() {
    return this.lockedTop;
}

public int contentBottom() {
    return this.contentBottom;
}

