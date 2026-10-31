// class net/friendswithmoney/fwm/config/FwmConfig$PricePing  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field itemKey Ljava/lang/String;;
field baseItemKey Ljava/lang/String;;
field name Ljava/lang/String;;
field icon Ljava/lang/String;;
field maxPrice D;
field order Z;
field direction Ljava/lang/String;;
field repeat Z;
field toast Ljava/lang/Boolean;;
field chat Z;
field seen Ljava/util/List;;
field fired Z;
public void <init>() {
    this.seen = ?;
    return;
}

public boolean below() {
    if (this.direction != null) goto L22;
    if (this.order !=0) goto L18;
    goto L39;
    L18:
    goto L39;
    L22:
    if ("above".equals(this.direction) !=0) goto L38;
    goto L39;
    L38:
    L39:
    return 0;
}

public boolean toastOn() {
    if (this.toast == null) goto L17;
    if (this.toast.booleanValue() ==0) goto L21;
    L17:
    goto L22;
    L21:
    L22:
    return 0;
}

