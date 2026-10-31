// class net/friendswithmoney/fwm/net/SpawnerData$Item  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field id Ljava/lang/String;;
field name Ljava/lang/String;;
field buyPrice Ljava/lang/String;;
field sellPrice Ljava/lang/String;;
field updatedAt Ljava/lang/String;;
public void <init>() {
    return;
}

public boolean tradedBack() {
    if (this.sellPrice == null) goto L39;
    if (this.sellPrice.trim().isEmpty() !=0) goto L39;
    if ("0".equals(this.sellPrice.trim()) !=0) goto L39;
    goto L40;
    L39:
    L40:
    return 0;
}

public String updatedDate() {
    if (this.updatedAt == null) goto L19;
    if (this.updatedAt.length() >= 10) goto L21;
    L19:
    return null;
    L21:
    return this.updatedAt.substring(8, 10) + '.' + this.updatedAt.substring(5, 7) + '.' + this.updatedAt.substring(0, 4);
}

