// class net/friendswithmoney/fwm/net/MarketHistoryData$History  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ok Z;
field item Ljava/lang/String;;
field since Ljava/lang/String;;
field source Ljava/lang/String;;
field points Ljava/util/List;;
public void <init>() {
    return;
}

public List list() {
    if (this.points != null) goto L13;
    goto L17;
    L13:
    L17:
    return this.points;
}

