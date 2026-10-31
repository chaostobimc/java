// class net/friendswithmoney/fwm/ui/panel/MessagesPanel$Entry  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field name Ljava/lang/String;;
field conversation Lnet/friendswithmoney/fwm/chat/Conversation;;
private void <init>(String name, chat.Conversation conversation) {
    this.name = name;
    this.conversation = conversation;
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

public String name() {
    return this.name;
}

public chat.Conversation conversation() {
    return this.conversation;
}

