// class net/friendswithmoney/fwm/chat/PrivateMessage  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field outgoing Z;
field text Ljava/lang/String;;
field time J;
public void <init>() {
    return;
}

public void <init>(boolean outgoing, String text, long time) {
    this.outgoing = outgoing;
    this.text = text;
    this.time = time;
    return;
}

