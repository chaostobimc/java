// class net/friendswithmoney/fwm/game/SpawnerMacro$Counts  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field bones J;
field arrows J;
field total J;
field fill I;
field pageOnlyBones Z;
private void <init>(long bones, long a2, long arrows, int a4, boolean total) {
    this.bones = bones;
    this.arrows = arrows;
    this.total = total;
    this.fill = v7;
    this.pageOnlyBones = v8;
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

public long bones() {
    return this.bones;
}

public long arrows() {
    return this.arrows;
}

public long total() {
    return this.total;
}

public int fill() {
    return this.fill;
}

public boolean pageOnlyBones() {
    return this.pageOnlyBones;
}

