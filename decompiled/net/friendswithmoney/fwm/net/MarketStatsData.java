// class net/friendswithmoney/fwm/net/MarketStatsData  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ok Z;
field item Lnet/friendswithmoney/fwm/net/MarketData$Item;;
field stats Ljava/util/List;;
public void <init>() {
    return;
}

public List list() {
    if (this.stats != null) goto L13;
    goto L17;
    L13:
    L17:
    return this.stats;
}

public boolean period(String key) {
    v2 = this.list().iterator();
    L10:
    if (v2.hasNext() ==0) goto L49;
    v3 = ((net.MarketStatsData$Period)v2.next());
    if (v3 == null) goto L46;
    if (key.equals(v3.period) ==0) goto L46;
    return v3;
    L46:
    goto L10;
    L49:
    return null;
}

