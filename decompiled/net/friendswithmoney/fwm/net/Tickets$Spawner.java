// class net/friendswithmoney/fwm/net/Tickets$Spawner  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field id Ljava/lang/String;;
field name Ljava/lang/String;;
field ankauf Z;
field verkauf Z;
field ankaufPreis Ljava/lang/String;;
field verkaufPreis Ljava/lang/String;;
public void <init>() {
    return;
}

public boolean can(boolean buy) {
    if (buy ==0) goto L11;
    goto L15;
    L11:
    L15:
    v2 = this.verkaufPreis;
    if (buy ==0) goto L30;
    if (this.ankauf ==0) goto L64;
    goto L37;
    L30:
    if (this.verkauf ==0) goto L64;
    L37:
    if (v2 == null) goto L64;
    if (v2.isBlank() !=0) goto L64;
    if ("0".equals(v2.trim()) !=0) goto L64;
    goto L65;
    L64:
    L65:
    return 0;
}

