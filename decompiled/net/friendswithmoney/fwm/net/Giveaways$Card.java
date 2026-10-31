// class net/friendswithmoney/fwm/net/Giveaways$Card  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field head Ljava/lang/String;;
field title Ljava/lang/String;;
field line Ljava/lang/String;;
field won Z;
public void <init>(String head, String title, String line, boolean won) {
    this.head = head;
    this.title = title;
    this.line = line;
    this.won = won;
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

public String head() {
    return this.head;
}

public String title() {
    return this.title;
}

public String line() {
    return this.line;
}

public boolean won() {
    return this.won;
}

