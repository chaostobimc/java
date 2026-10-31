// class net/friendswithmoney/fwm/net/Tickets$Ticket  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field id Ljava/lang/String;;
field type Ljava/lang/String;;
field status Ljava/lang/String;;
field outcome Ljava/lang/String;;
field claimedByMinecraftName Ljava/lang/String;;
field createdAt Ljava/lang/String;;
field updatedAt Ljava/lang/String;;
field closedAt Ljava/lang/String;;
public void <init>() {
    return;
}

public boolean live() {
    if ("open".equals(this.status) !=0) goto L24;
    if ("claimed".equals(this.status) ==0) goto L28;
    L24:
    goto L29;
    L28:
    L29:
    return 0;
}

