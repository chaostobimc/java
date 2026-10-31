// class net/friendswithmoney/fwm/chat/Conversation  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field partner Ljava/lang/String;;
field messages Ljava/util/List;;
field unread I;
field lastActivity J;
public void <init>() {
    this.messages = ?;
    return;
}

public void <init>(String partner) {
    this.messages = ?;
    this.partner = partner;
    return;
}

public void add(chat.PrivateMessage message, int keep) {
    this.add(message, keep, 1);
    return;
}

public Object add(chat.PrivateMessage message, int keep, boolean count) {
    if (this.messages != null) goto L18;
    this.messages = ?;
    L18:
    this.messages.add(message);
    this.lastActivity = message.time;
    if (message.outgoing !=0) goto L58;
    if (count ==0) goto L58;
    this.unread = (this.unread + 1);
    L58:
    if (this.messages.size() <= keep) goto L85;
    this.messages.remove(0);
    goto L58;
    L85:
    return;
}

public Object newest() {
    if (this.messages == null) goto L19;
    if (this.messages.isEmpty() ==0) goto L21;
    L19:
    return null;
    L21:
    return ((chat.PrivateMessage)this.messages.get((this.messages.size() - 1)));
}

