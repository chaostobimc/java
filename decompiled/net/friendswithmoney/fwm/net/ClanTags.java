// class net/friendswithmoney/fwm/net/ClanTags  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field REAL_TAG Ljava/util/regex/Pattern;;
field CANDIDATE Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static boolean fakeRawIn(String line) {
    if (line == null) goto L11;
    if (line.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    v1 = net.ClanTags.CANDIDATE.matcher(line);
    L21:
    if (v1.find() ==0) goto L48;
    v2 = net.ClanTags.bare(v1.group());
    if (net.ClanTags.isFake(v2) ==0) goto L45;
    return v2;
    L45:
    goto L21;
    L48:
    return null;
}

public static String fakeIn(String line) {
    if (line == null) goto L11;
    if (line.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    v1 = net.ClanTags.CANDIDATE.matcher(line);
    L21:
    if (v1.find() ==0) goto L51;
    v2 = net.ClanTags.bare(v1.group());
    if (net.ClanTags.isFake(v2) ==0) goto L48;
    return net.ClanTags.tidy(v2);
    L48:
    goto L21;
    L51:
    return null;
}

public static List fakeSpans(String line) {
    if (line == null) goto L11;
    if (line.isEmpty() ==0) goto L15;
    L11:
    return List.of();
    L15:
    v1 = null;
    v2 = net.ClanTags.CANDIDATE.matcher(line);
    L25:
    if (v2.find() ==0) goto L88;
    if (net.ClanTags.isFake(net.ClanTags.bare(v2.group())) !=0) goto L48;
    goto L25;
    L48:
    if (v1 != null) goto L61;
    v1 = ?;
    L61:
    new int[2][0] = v2.start();
    new int[2][1] = v2.end();
    v1.add(new int[2]);
    goto L25;
    L88:
    if (v1 != null) goto L98;
    goto L99;
    L98:
    L99:
    return v1;
}

public static String bare(String token) {
    if (token == null) goto L34;
    if (token.length() <= 1) goto L34;
    if (token.charAt(0) != 91) goto L34;
    return token.substring(1, (token.length() - 1));
    L34:
    return token;
}

public static boolean isFake(String tag) {
    if (net.FakeList.isFakeTag(tag) ==0) goto L9;
    return 1;
    L9:
    if (net.TrustedClans.fakeOf(tag) == null) goto L18;
    return 1;
    L18:
    v1 = net.ClanTags.letters(tag);
    if (v1.isEmpty() ==0) goto L32;
    return 0;
    L32:
    if (net.ClanTags.isReal(tag) ==0) goto L41;
    return 0;
    L41:
    if (net.ClanTags.sameTag(v1, net.ClanTags.letters("FWM")) ==0) goto L55;
    return 1;
    L55:
    v2 = net.FakeList.managed();
    if (v2 ==0) goto L78;
    goto L84;
    L78:
    L84:
    v3 = config.FwmConfig.INSTANCE.fakeTags;
    if (v2 ==0) goto L138;
    v4 = net.FakeList.similarTags().iterator();
    L99:
    if (v4.hasNext() ==0) goto L138;
    v5 = ((String)v4.next());
    if (net.ClanTags.sameTag(v1, net.ClanTags.letters(v5)) ==0) goto L135;
    return 1;
    L135:
    goto L99;
    L138:
    if (v3 == null) goto L189;
    v4 = v3.iterator();
    L150:
    if (v4.hasNext() ==0) goto L189;
    v5 = ((String)v4.next());
    if (net.ClanTags.sameTag(v1, net.ClanTags.letters(v5)) ==0) goto L186;
    return 1;
    L186:
    goto L150;
    L189:
    if (v2 ==0) goto L208;
    goto L214;
    L208:
    L214:
    v4 = config.FwmConfig.INSTANCE.fakeTagsExact;
    if (v4 == null) goto L281;
    v5 = net.ClanTags.tidy(tag);
    v6 = v4.iterator();
    L236:
    if (v6.hasNext() ==0) goto L281;
    v7 = ((String)v6.next());
    if (v7 == null) goto L278;
    if (v5.equals(net.ClanTags.tidy(v7)) ==0) goto L278;
    return 1;
    L278:
    goto L236;
    L281:
    return 0;
}

private static boolean ownOnly(List mine, List builtIn) {
    if (mine == null) goto L13;
    if (mine.isEmpty() ==0) goto L17;
    L13:
    return List.of();
    L17:
    v2 = ?;
    v3 = builtIn.iterator();
    L32:
    if (v3.hasNext() ==0) goto L67;
    v4 = ((String)v3.next());
    v2.add(net.ClanTags.tidy(v4));
    goto L32;
    L67:
    v3 = ?;
    v4 = mine.iterator();
    L83:
    if (v4.hasNext() ==0) goto L136;
    v5 = ((String)v4.next());
    if (v5 == null) goto L133;
    if (v2.contains(net.ClanTags.tidy(v5)) !=0) goto L133;
    v3.add(v5);
    L133:
    goto L83;
    L136:
    return v3;
}

private static boolean sameTag(String letters, String fake) {
    if (letters.equals(fake) ==0) goto L10;
    return 1;
    L10:
    v2 = fake.replace("X", "");
    if (v2.length() < 3) goto L46;
    if (letters.replace("X", "").equals(v2) ==0) goto L46;
    goto L47;
    L46:
    L47:
    return 0;
}

public static boolean isReal(String tag) {
    if (tag != null) goto L6;
    return 0;
    L6:
    v1 = tag.trim().replaceAll("[^\\x00-\\x7F]", "");
    if (net.TrustedClans.isFwmTag(v1) ==0) goto L27;
    return 1;
    L27:
    if (net.Members.clanTags().contains(v1.toUpperCase(Locale.ROOT)) ==0) goto L47;
    return 1;
    L47:
    return net.ClanTags.REAL_TAG.matcher(v1).matches();
}

private static String letters(String tag) {
    if (tag != null) goto L7;
    return "";
    L7:
    v1 = ?;
    v2 = 0;
    L21:
    if (v2 >= tag.length()) goto L77;
    v3 = Character.toUpperCase(tag.charAt(v2));
    if (v3 == 79) goto L71;
    if (v3 != 73) goto L53;
    goto L71;
    L53:
    if (v3 < 65) goto L71;
    if (v3 > 90) goto L71;
    v1.append(v3);
    L71:
    v2 = v2 + 1;
    goto L21;
    L77:
    return v1.toString();
}

public static String tidy(String tag) {
    if (tag != null) goto L9;
    goto L19;
    L9:
    L19:
    return tag.trim().toUpperCase(Locale.ROOT);
}

static regex.Pattern <clinit>() {
    net.ClanTags.REAL_TAG = regex.Pattern.compile("(?i)^FWM(?:[0-9]|X)?$");
    net.ClanTags.CANDIDATE = regex.Pattern.compile("\\[[^\\[\\]]{1,8}\\]");
    return;
}

