// class net/friendswithmoney/fwm/net/Presence$TabMemo  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field in Lnet/minecraft/network/chat/Component;;
field out Lnet/minecraft/network/chat/Component;;
field at J;
private void <init>(network.chat.Component in, network.chat.Component out, long at) {
    this.in = in;
    this.out = out;
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

public network.chat.Component in() {
    return this.in;
}

public network.chat.Component out() {
    return this.out;
}

public long at() {
    return this.at;
}

