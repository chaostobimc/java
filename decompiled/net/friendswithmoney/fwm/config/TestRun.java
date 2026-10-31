// class net/friendswithmoney/fwm/config/TestRun  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field name Ljava/lang/String;;
field cents J;
field baseCents J;
field items I;
field at J;
public void <init>() {
    return;
}

public void <init>(String name, long cents, long a3, int baseCents, long a5) {
    this.name = name;
    this.cents = cents;
    this.baseCents = baseCents;
    this.items = v6;
    this.at = v7;
    return;
}

public double measuredMultiplier() {
    if (cmp(this.baseCents,0) >0) goto L13;
    return -1.0;
    L13:
    return (((d)this.cents) / ((d)this.baseCents));
}

