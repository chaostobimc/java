// class net/friendswithmoney/fwm/net/LiveAuctionData  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ok Z;
field items Ljava/util/List;;
field meta Lnet/friendswithmoney/fwm/net/LiveAuctionData$Meta;;
public void <init>() {
    return;
}

public List list() {
    if (this.items != null) goto L13;
    goto L17;
    L13:
    L17:
    return this.items;
}

