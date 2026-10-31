// class net/friendswithmoney/fwm/ui/FwmHud$ChatHit  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field x I;
field y I;
field width I;
field height I;
field text Lnet/minecraft/network/chat/FormattedText;;
public void <init>(int x, int y, int width, int height, network.chat.FormattedText text) {
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
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

public int x() {
    return this.x;
}

public int y() {
    return this.y;
}

public int width() {
    return this.width;
}

public int height() {
    return this.height;
}

public network.chat.FormattedText text() {
    return this.text;
}

