// class net/friendswithmoney/fwm/chat/SecondChat$Line  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field text Lnet/minecraft/network/chat/Component;;
field at J;
public void <init>(network.chat.Component text, long at) {
    this.text = text;
    this.at = at;
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

public network.chat.Component text() {
    return this.text;
}

public long at() {
    return this.at;
}

