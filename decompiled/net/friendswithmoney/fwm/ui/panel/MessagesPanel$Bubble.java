// class net/friendswithmoney/fwm/ui/panel/MessagesPanel$Bubble  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field outgoing Z;
field header Ljava/lang/String;;
field lines Ljava/util/List;;
field width I;
field height I;
private void <init>(boolean outgoing, String header, List lines, int width, int height) {
    this.outgoing = outgoing;
    this.header = header;
    this.lines = lines;
    this.width = width;
    this.height = height;
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

public boolean outgoing() {
    return this.outgoing;
}

public String header() {
    return this.header;
}

public List lines() {
    return this.lines;
}

public int width() {
    return this.width;
}

public int height() {
    return this.height;
}

