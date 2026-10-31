// class net/friendswithmoney/fwm/seventv/EmotePicker$Line  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field emotes Ljava/util/List;;
field section Ljava/lang/String;;
private void <init>(List emotes, String section) {
    this.emotes = emotes;
    this.section = section;
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

public List emotes() {
    return this.emotes;
}

public String section() {
    return this.section;
}

