// class net/friendswithmoney/fwm/chat/Messages$Parsed  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field partner Ljava/lang/String;;
field text Ljava/lang/String;;
field sent Z;
public void <init>(String partner, String text, boolean sent) {
    this.partner = partner;
    this.text = text;
    this.sent = sent;
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

public String partner() {
    return this.partner;
}

public String text() {
    return this.text;
}

public boolean sent() {
    return this.sent;
}

