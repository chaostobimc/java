// class net/friendswithmoney/fwm/util/Fmt  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field UNITS [Ljava/lang/String;;
private void <init>() {
    return;
}

public static String shortMoney(long cents) {
    if (cmp(cents,0) >=0) goto L10;
    goto L11;
    L10:
    L11:
    v2 = 0;
    v3 = (Math.abs(cents) / 100);
    if (v2 ==0) goto L30;
    goto L32;
    L30:
    L32:
    v5 = "";
    if (cmp(v3,1000) >=0) goto L51;
    return v5 + v3;
    L51:
    new long[4][0] = 1000000000000;
    new long[4][1] = 1000000000;
    new long[4][2] = 1000000;
    new long[4][3] = 1000;
    v6 = new long[4];
    new String[4][0] = "T";
    new String[4][1] = "B";
    new String[4][2] = "M";
    new String[4][3] = "K";
    v7 = new String[4];
    v8 = 0;
    L109:
    if (v8 >= v6.length) goto L245;
    if (cmp(v3,v6[v8]) >=0) goto L130;
    goto L239;
    L130:
    v9 = (v3 / v6[v8]);
    v11 = (((v3 % v6[v8]) * 100) / v6[v8]);
    v13 = Long.toString(v9);
    if (cmp(v11,0) <=0) goto L224;
    new Object[1][0] = Long.valueOf(v11);
    v14 = String.format(Locale.ROOT, "%02d", new Object[1]);
    if (v14.endsWith("0") ==0) goto L213;
    v14 = v14.substring(0, 1);
    L213:
    v13 = v13 + ',' + v14;
    L224:
    return v5 + v13 + v7[v8];
    L239:
    v8 = v8 + 1;
    goto L109;
    L245:
    return v5 + v3;
}

public static String fullMoney(long cents) {
    v2 = util.Fmt.moneyDe(cents);
    if (v2.endsWith(",00") ==0) goto L28;
    goto L29;
    L28:
    L29:
    return v2;
}

public static String money(long cents) {
    if (cmp(cents,0) >=0) goto L10;
    goto L11;
    L10:
    L11:
    v2 = 0;
    v3 = Math.abs(cents);
    v5 = util.Fmt.group((v3 / 100));
    new Object[1][0] = Long.valueOf((v3 % 100));
    v6 = String.format(Locale.ROOT, "%02d", new Object[1]);
    if (v2 ==0) goto L61;
    goto L63;
    L61:
    L63:
    return "$" + v5 + '.' + v6;
}

public static String moneyDe(long cents) {
    if (cmp(cents,0) >=0) goto L10;
    goto L11;
    L10:
    L11:
    v2 = 0;
    v3 = Math.abs(cents);
    v5 = util.Fmt.group((v3 / 100), 46);
    new Object[1][0] = Long.valueOf((v3 % 100));
    v6 = String.format(Locale.ROOT, "%02d", new Object[1]);
    if (v2 ==0) goto L63;
    goto L65;
    L63:
    L65:
    return "$" + v5 + ',' + v6;
}

public static String compactMoney(long cents) {
    return '$' + util.Fmt.compact((cents / 100));
}

public static String compactMoneyUp(long cents) {
    if (cmp(cents,0) >=0) goto L10;
    goto L11;
    L10:
    L11:
    v2 = 0;
    v3 = ((Math.abs(cents) + 99) / 100);
    if (v2 ==0) goto L34;
    goto L36;
    L34:
    L36:
    return "$" + util.Fmt.compactUp(v3);
}

public static String compactUp(long value) {
    if (cmp(value,1000) >=0) goto L13;
    return String.valueOf(value);
    L13:
    v2 = ((d)value);
    v4 = m1;
    L19:
    if (cmp(v2,1000.0) <0) goto L50;
    if (v4 >= (util.Fmt.UNITS.length - 1)) goto L50;
    v2 = (v2 / 1000.0);
    v4 = v4 + 1;
    goto L19;
    L50:
    v5 = (Math.ceil((v2 * 10.0)) / 10.0);
    if (cmp(v5,1000.0) <0) goto L95;
    if (v4 >= (util.Fmt.UNITS.length - 1)) goto L95;
    v5 = (v5 / 1000.0);
    v4 = v4 + 1;
    L95:
    if (cmp(v5,Math.floor(v5)) !=0) goto L115;
    goto L135;
    L115:
    new Object[1][0] = Double.valueOf(v5);
    L135:
    v7 = String.format(Locale.ROOT, "%.1f", new Object[1]);
    return v7 + util.Fmt.UNITS[v4];
}

public static String compact(long value) {
    v2 = Math.abs(value);
    if (cmp(v2,10000) >=0) goto L18;
    return String.valueOf(value);
    L18:
    if (cmp(v2,1000000) >=0) goto L41;
    return util.Fmt.trim((((d)value) / 1000.0)) + 'K';
    L41:
    if (cmp(v2,1000000000) >=0) goto L64;
    return util.Fmt.trim((((d)value) / 1000000.0)) + 'M';
    L64:
    return util.Fmt.trim((((d)value) / 1000000000.0)) + 'B';
}

public static String playtime(long minutes) {
    v2 = (minutes / 1440);
    v4 = ((minutes % 1440) / 60);
    v6 = (minutes % 60);
    if (cmp(v2,0) <=0) goto L39;
    return v2 + 'd ' + v4 + 'h';
    L39:
    if (cmp(v4,0) <=0) goto L56;
    return v4 + 'h ' + v6 + 'm';
    L56:
    return v6 + 'm';
}

public static String duration(long millis) {
    v2 = (millis / 1000);
    if (cmp(v2,60) >=0) goto L21;
    return v2 + 's';
    L21:
    return util.Fmt.playtime((v2 / 60));
}

// DECOMPILE ERROR in compactBoardValue: 'dict' object has no attribute 'op_type'
private static String trim(double value) {
    new Object[1][0] = Double.valueOf(value);
    return String.format(Locale.ROOT, "%.1f", new Object[1]);
}

public static String dollars(long cents) {
    return '$' + util.Fmt.group((cents / 100));
}

private static String group(long value) {
    return util.Fmt.group(value, 44);
}

private static String group(long value, char a1) {
    v3 = String.valueOf(value);
    v4 = ?;
    v5 = 0;
    L17:
    if (v5 >= v3.length()) goto L68;
    if (v5 <=0) goto L50;
    if (((v3.length() - v5) % 3) !=0) goto L50;
    v4.append(v2);
    L50:
    v4.append(v3.charAt(v5));
    v5 = v5 + 1;
    goto L17;
    L68:
    return v4.toString();
}

static void <clinit>() {
    new String[4][0] = "K";
    new String[4][1] = "M";
    new String[4][2] = "B";
    new String[4][3] = "T";
    util.Fmt.UNITS = new String[4];
    return;
}

