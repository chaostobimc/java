// class net/friendswithmoney/fwm/ui/panel/SellStatsPanel$Line  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field label Lnet/minecraft/network/chat/Component;;
field value Ljava/lang/String;;
field color I;
private void <init>(network.chat.Component label, String value, int color) {
    this.label = label;
    this.value = value;
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

public network.chat.Component label() {
    return this.label;
}

public String value() {
    return this.value;
}

public int color() {
    return this.color;
}

