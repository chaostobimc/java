// class net/friendswithmoney/fwm/auction/Payments  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field compiled Ljava/util/List;;
field compiledFrom Ljava/util/List;;
private void <init>() {
    return;
}

public static void read(String line, String self) {
    if (line == null) goto L11;
    if (line.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    v2 = auction.Payments.chatPrefixEnd(line);
    if (v2 >=0) goto L24;
    return null;
    L24:
    v3 = auction.Payments.patterns().iterator();
    L33:
    if (v3.hasNext() ==0) goto L158;
    v4 = ((regex.Pattern)v3.next());
    v5 = v4.matcher(line);
    if (v5.find() !=0) goto L72;
    goto L33;
    L72:
    v6 = auction.Payments.group(v5, "name");
    v7 = auction.Payments.group(v5, "amount");
    if (v6 == null) goto L33;
    if (v7 != null) goto L103;
    goto L33;
    L103:
    if (self == null) goto L119;
    if (v6.equalsIgnoreCase(self) ==0) goto L119;
    goto L33;
    L119:
    v8 = auction.Money.cents(v7 + auction.Payments.suffix(v5));
    if (cmp(v8,0) <=0) goto L155;
    return ?;
    L155:
    goto L33;
    L158:
    return null;
}

private static boolean chatPrefixEnd(String line) {
    v1 = line.indexOf(58);
    if (v1 <=0) goto L28;
    if (auction.Payments.isName(auction.Payments.lastPlainWord(line.substring(0, v1))) ==0) goto L28;
    return m1;
    L28:
    v2 = line.indexOf(60);
    v3 = line.indexOf(62);
    if (v2 <0) goto L72;
    if (v3 <= (v2 + 1)) goto L72;
    if (auction.Payments.isName(line.substring((v2 + 1), v3).trim()) ==0) goto L72;
    return m1;
    L72:
    return 0;
}

private static String lastPlainWord(String left) {
    v1 = ?;
    v2 = 0;
    v3 = 0;
    L12:
    if (v3 >= left.length()) goto L102;
    v4 = left.charAt(v3);
    if (v4 == 91) goto L48;
    if (v4 == 40) goto L48;
    if (v4 != 60) goto L54;
    L48:
    v2 = v2 + 1;
    goto L96;
    L54:
    if (v4 == 93) goto L75;
    if (v4 == 41) goto L75;
    if (v4 != 62) goto L85;
    L75:
    if (v2 <=0) goto L96;
    v2 = v2 + 255;
    goto L96;
    L85:
    if (v2 !=0) goto L96;
    v1.append(v4);
    L96:
    v3 = v3 + 1;
    goto L12;
    L102:
    v3 = v1.toString().trim().split(" ");
    if (v3.length !=0) goto L125;
    goto L134;
    L125:
    L134:
    return v3[(v3.length - 1)].trim();
}

public static boolean isName(String word) {
    if (word == null) goto L37;
    if (word.isEmpty() !=0) goto L37;
    if (word.charAt(0) == 46) goto L31;
    if (word.charAt(0) != 42) goto L37;
    L31:
    word = word.substring(1);
    L37:
    if (word == null) goto L58;
    if (word.length() < 3) goto L58;
    if (word.length() <= 16) goto L60;
    L58:
    return 0;
    L60:
    v1 = 0;
    L62:
    if (v1 >= word.length()) goto L97;
    v2 = word.charAt(v1);
    if (Character.isLetterOrDigit(v2) !=0) goto L91;
    if (v2 == 95) goto L91;
    return 0;
    L91:
    v1 = v1 + 1;
    goto L62;
    L97:
    return 1;
}

private static String suffix(regex.Matcher matcher) {
    v1 = auction.Payments.group(matcher, "unit");
    if (v1 != null) goto L16;
    goto L17;
    L16:
    L17:
    return v1;
}

private static String group(regex.Matcher matcher, String name) {
    return matcher.group(name);
    v2 = /*catch IllegalArgumentException*/ ex;
    return null;
}

private static void patterns() {
    v0 = config.FwmConfig.INSTANCE.payPatterns;
    if (v0 == null) goto L20;
    if (v0.isEmpty() ==0) goto L24;
    L20:
    v0 = config.FwmConfig.DEFAULT_PAY_PATTERNS;
    L24:
    if (auction.Payments.compiled == null) goto L46;
    if (v0.equals(auction.Payments.compiledFrom) ==0) goto L46;
    return auction.Payments.compiled;
    L46:
    v1 = ?;
    v2 = v0.iterator();
    L67:
    if (v2.hasNext() ==0) goto L123;
    v3 = ((String)v2.next());
    v1.add(regex.Pattern.compile(v3, 2));
    goto L120;
    v4 = /*catch Exception*/ ex;
    System.err.println('[FWM] Bad pay pattern: ' + v3 + ' (' + v4.getMessage() + ')');
    L120:
    goto L67;
    L123:
    auction.Payments.compiled = v1;
    auction.Payments.compiledFrom = ?;
    return auction.Payments.compiled;
}

