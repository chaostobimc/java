// class net/friendswithmoney/fwm/auction/Times  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field CLOCK Ljava/util/regex/Pattern;;
field PART Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static boolean seconds(String typed) {
    if (typed != null) goto L6;
    return 0;
    L6:
    v1 = typed.trim().toLowerCase(Locale.ROOT);
    if (v1.isEmpty() ==0) goto L26;
    return 0;
    L26:
    v2 = auction.Times.CLOCK.matcher(v1);
    if (v2.matches() ==0) goto L99;
    v3 = Long.parseLong(v2.group(1));
    v5 = Long.parseLong(v2.group(2));
    if (v2.group(3) != null) goto L77;
    return ((v3 * 60) + v5);
    L77:
    return (((v3 * 3600) + (v5 * 60)) + Long.parseLong(v2.group(3)));
    L99:
    v3 = 0;
    v5 = auction.Times.PART.matcher(v1);
    v6 = 0;
    L113:
    if (v5.find() ==0) goto L240;
    v7 = Long.parseLong(v5.group(1));
    v9 = v5.group(2);
    v6 = 1;
    if (v9.isEmpty() !=0) goto L161;
    if (v9.startsWith("s") ==0) goto L169;
    L161:
    v3 = (v3 + v7);
    goto L225;
    L169:
    if (v9.startsWith("m") ==0) goto L191;
    v3 = (v3 + (v7 * 60));
    goto L225;
    L191:
    if (v9.startsWith("h") !=0) goto L211;
    if (v9.startsWith("st") ==0) goto L223;
    L211:
    v3 = (v3 + (v7 * 3600));
    goto L225;
    L223:
    return 0;
    L225:
    if (cmp(v3,10000000) <=0) goto L237;
    return 10000000;
    L237:
    goto L113;
    L240:
    if (v6 ==0) goto L249;
    goto L250;
    L249:
    L250:
    return 0;
}

public static String clock(int seconds) {
    if (seconds >= 60) goto L13;
    return seconds + 's';
    L13:
    v1 = (seconds / 3600);
    v2 = ((seconds % 3600) / 60);
    v3 = (seconds % 60);
    if (v1 <=0) goto L72;
    if (v2 >= 10) goto L49;
    goto L51;
    L49:
    L51:
    if (v3 >= 10) goto L63;
    goto L65;
    L63:
    L65:
    return "" + ':' + v2 + "0" + ':' + "" + v3;
    L72:
    if (v3 >= 10) goto L84;
    goto L86;
    L84:
    L86:
    return "0" + ':' + "" + v3;
}

static regex.Pattern <clinit>() {
    auction.Times.CLOCK = regex.Pattern.compile("^(\\d{1,4}):(\\d{1,2})(?::(\\d{1,2}))?$");
    auction.Times.PART = regex.Pattern.compile("(\\d{1,6})\\s*([a-z\u00e4\u00f6\u00fc]*)");
    return;
}

