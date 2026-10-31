// class net/friendswithmoney/fwm/auction/Money  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static long cents(String text) {
    if (text == null) goto L11;
    if (text.isEmpty() ==0) goto L13;
    L11:
    return 0;
    L13:
    v1 = ?;
    v2 = m1;
    v3 = 0;
    L25:
    if (v3 >= text.length()) goto L50;
    if (Character.isDigit(text.charAt(v3)) !=0) goto L50;
    v3 = v3 + 1;
    goto L25;
    L50:
    if (v3 >= text.length()) goto L186;
    v4 = text.charAt(v3);
    if (Character.isDigit(v4) ==0) goto L93;
    v1.append(v4);
    if (v2 <0) goto L87;
    v2 = v2 + 1;
    L87:
    v3 = v3 + 1;
    goto L50;
    L93:
    if (v4 == 44) goto L107;
    if (v4 != 46) goto L186;
    L107:
    if (v2 >=0) goto L186;
    if ((v3 + 1) >= text.length()) goto L186;
    if (Character.isDigit(text.charAt((v3 + 1))) ==0) goto L186;
    v5 = 0;
    L137:
    if (((v3 + 1) + v5) >= text.length()) goto L172;
    if (Character.isDigit(text.charAt(((v3 + 1) + v5))) ==0) goto L172;
    v5 = v5 + 1;
    goto L137;
    L172:
    if (v5 == 3) goto L180;
    v2 = 0;
    L180:
    v3 = v3 + 1;
    goto L50;
    L186:
    if (v1.isEmpty() !=0) goto L202;
    if (v1.length() <= 15) goto L204;
    L202:
    return 0;
    L204:
    v4 = 1;
    L207:
    if (v3 >= text.length()) goto L231;
    if (text.charAt(v3) != 32) goto L231;
    v3 = v3 + 1;
    goto L207;
    L231:
    if (v3 >= text.length()) goto L291;
    v6 = Character.toLowerCase(text.charAt(v3));
    if (v6 != 107) goto L264;
    v4 = 1000;
    goto L291;
    L264:
    if (v6 != 109) goto L279;
    v4 = 1000000;
    goto L291;
    L279:
    if (v6 != 98) goto L291;
    v4 = 1000000000;
    L291:
    v6 = Double.parseDouble(v1.toString());
    if (v2 <=0) goto L317;
    v6 = (v6 / Math.pow(10.0, ((d)v2)));
    L317:
    v8 = ((v6 * ((d)v4)) * 100.0);
    if (cmp(v8,4.611686018427388e+18) <=0) goto L342;
    goto L347;
    L342:
    L347:
    return Math.round(v8);
}

