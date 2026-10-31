// class net/friendswithmoney/fwm/money/History$Entry  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field t J;
field k I;
field c J;
field p Ljava/lang/String;;
field i Ljava/lang/String;;
field n I;
field l Ljava/lang/String;;
public void <init>() {
    this.n = 1;
    return;
}

public money.MoneyStore$Kind[] kind() {
    v1 = money.MoneyStore$Kind.values();
    if (this.k <0) goto L29;
    if (this.k >= v1.length) goto L29;
    goto L32;
    L29:
    L32:
    return money.MoneyStore$Kind.OTHER_IN;
}

