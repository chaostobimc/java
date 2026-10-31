// class net/friendswithmoney/fwm/config/ChatFilter  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field text Ljava/lang/String;;
field on Z;
public void <init>() {
    this.on = 1;
    return;
}

public void <init>(String text) {
    this.on = 1;
    this.text = text;
    return;
}

public boolean matches(String line) {
    if (this.on ==0) goto L24;
    if (this.text == null) goto L24;
    if (this.text.isBlank() ==0) goto L26;
    L24:
    return 0;
    L26:
    return line.toLowerCase(Locale.ROOT).contains(this.text.toLowerCase(Locale.ROOT));
}

