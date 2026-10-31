// class net/friendswithmoney/fwm/colorcode/ColorCode  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field CODES Ljava/lang/String;;
field RGB [I;
field PREFIX Ljava/util/regex/Pattern;;
field text Ljava/lang/String;;
field styles Ljava/util/List;;
public void <init>() {
    this.text = "";
    this.styles = ?;
    return;
}

public String text() {
    return this.text;
}

public List styles() {
    return this.styles;
}

public void set(String newText, List newStyles) {
    this.text = newText;
    this.styles = ?;
    return;
}

public void set(colorcode.ColorCode other) {
    this.set(other.text, other.styles);
    return;
}

public Object applyColor(int start, int end, String color) {
    v4 = this.clamp(start);
    L7:
    if (v4 >= this.clamp(end)) goto L53;
    this.styles.set(v4, ((colorcode.CodeStyle)this.styles.get(v4)).withColor(color));
    v4 = v4 + 1;
    goto L7;
    L53:
    return;
}

public void applyHex(int start, int end, String hex) {
    this.applyColor(start, end, hex.toUpperCase(Locale.ROOT));
    return;
}

public Object toggleFormat(int start, int end, colorcode.CodeStyle$Format format) {
    v4 = 1;
    v5 = this.clamp(start);
    L10:
    if (v5 >= this.clamp(end)) goto L53;
    if (((colorcode.CodeStyle)this.styles.get(v5)).has(format) !=0) goto L47;
    v4 = 0;
    goto L53;
    L47:
    v5 = v5 + 1;
    goto L10;
    L53:
    v5 = this.clamp(start);
    L60:
    if (v5 >= this.clamp(end)) goto L116;
    if (v4 !=0) goto L100;
    goto L101;
    L100:
    L101:
    v5.set(((colorcode.CodeStyle)this.styles.get(v5)), format.with(1, 0));
    v5 = v5 + 1;
    goto L60;
    L116:
    return;
}

public Object reset(int start, int end) {
    v3 = this.clamp(start);
    L6:
    if (v3 >= this.clamp(end)) goto L35;
    this.styles.set(v3, colorcode.CodeStyle.DEFAULT);
    v3 = v3 + 1;
    goto L6;
    L35:
    return;
}

public boolean allHave(int start, int end, colorcode.CodeStyle$Format format) {
    if (this.clamp(start) < this.clamp(end)) goto L15;
    return 0;
    L15:
    v4 = this.clamp(start);
    L22:
    if (v4 >= this.clamp(end)) goto L61;
    if (((colorcode.CodeStyle)this.styles.get(v4)).has(format) !=0) goto L55;
    return 0;
    L55:
    v4 = v4 + 1;
    goto L22;
    L61:
    return 1;
}

private int clamp(int index) {
    return Math.max(0, Math.min(this.text.length(), index));
}

public boolean reconcile(String newText) {
    v2 = this.text;
    v3 = 0;
    v4 = Math.min(v2.length(), newText.length());
    L20:
    if (v3 >= v4) goto L45;
    if (v2.charAt(v3) != newText.charAt(v3)) goto L45;
    v3 = v3 + 1;
    goto L20;
    L45:
    v5 = v2.length();
    v6 = newText.length();
    L57:
    if (v5 <= v3) goto L97;
    if (v6 <= v3) goto L97;
    if (v2.charAt((v5 - 1)) != newText.charAt((v6 - 1))) goto L97;
    v5 = v5 + 255;
    v6 = v6 + 255;
    goto L57;
    L97:
    if (v3 <=0) goto L119;
    goto L150;
    L119:
    if (this.styles.isEmpty() ==0) goto L137;
    goto L150;
    L137:
    L150:
    v7 = ((colorcode.CodeStyle)this.styles.get(0));
    v8 = ?;
    v9 = v3;
    L175:
    if (v9 >= v6) goto L198;
    v8.add(v7);
    v9 = v9 + 1;
    goto L175;
    L198:
    v8.addAll(this.styles.subList(v5, this.styles.size()));
    this.text = newText;
    this.styles = v8;
    return;
}

public void runs() {
    v1 = ?;
    v2 = 0;
    L10:
    if (v2 >= this.text.length()) goto L55;
    colorcode.ColorCode.appendMerged(v1, ((colorcode.CodeStyle)this.styles.get(v2)), String.valueOf(this.text.charAt(v2)));
    v2 = v2 + 1;
    goto L10;
    L55:
    return v1;
}

private static boolean appendMerged(List out, colorcode.CodeStyle style, String part) {
    if (out.isEmpty() ==0) goto L13;
    goto L30;
    L13:
    L30:
    v3 = ((colorcode.ColorCode$Run)out.get((out.size() - 1)));
    if (v3 == null) goto L82;
    if (v3.style.equals(style) ==0) goto L82;
    out.set((out.size() - 1), ?);
    goto L98;
    L82:
    out.add(?);
    L98:
    return;
}

public void optimizedRuns() {
    v1 = this.runs();
    v2 = ?;
    v3 = 1;
    L16:
    if (v3 >= (v1.size() - 1)) goto L174;
    v4 = ((colorcode.ColorCode$Run)v1.get(v3));
    if (colorcode.ColorCode.isBlank(v4.text) !=0) goto L54;
    goto L168;
    L54:
    v5 = ((colorcode.ColorCode$Run)v1.get((v3 - 1))).style;
    v6 = ((colorcode.ColorCode$Run)v1.get((v3 + 1))).style;
    if (v5.equals(v6) ==0) goto L168;
    if (v4.style.underline() != v5.underline()) goto L168;
    if (v4.style.strike() != v5.strike()) goto L168;
    if (v4.style.obf() != v5.obf()) goto L168;
    v2.set(v3, ?);
    L168:
    v3 = v3 + 1;
    goto L16;
    L174:
    v3 = ?;
    v4 = v2.iterator();
    L190:
    if (v4.hasNext() ==0) goto L229;
    v5 = ((colorcode.ColorCode$Run)v4.next());
    colorcode.ColorCode.appendMerged(v3, v5.style, v5.text);
    goto L190;
    L229:
    return v3;
}

private static boolean isBlank(String s) {
    if (s.isEmpty() ==0) goto L9;
    return 0;
    L9:
    v1 = 0;
    L11:
    if (v1 >= s.length()) goto L47;
    v2 = s.charAt(v1);
    if (Character.isWhitespace(v2) !=0) goto L41;
    if (Character.isSpaceChar(v2) !=0) goto L41;
    return 0;
    L41:
    v1 = v1 + 1;
    goto L11;
    L47:
    return 1;
}

public String serialize() {
    v1 = ?;
    v2 = colorcode.CodeStyle.DEFAULT;
    v3 = this.optimizedRuns().iterator();
    L22:
    if (v3.hasNext() ==0) goto L334;
    v4 = ((colorcode.ColorCode$Run)v3.next());
    v5 = v4.style;
    if (colorcode.ColorCode.equalColor(v2.color(), v5.color()) !=0) goto L68;
    goto L69;
    L68:
    L69:
    v6 = 0;
    if (v5.bold() !=0) goto L86;
    if (v2.bold() !=0) goto L146;
    L86:
    if (v5.underline() !=0) goto L101;
    if (v2.underline() !=0) goto L146;
    L101:
    if (v5.italic() !=0) goto L116;
    if (v2.italic() !=0) goto L146;
    L116:
    if (v5.strike() !=0) goto L131;
    if (v2.strike() !=0) goto L146;
    L131:
    if (v5.obf() !=0) goto L150;
    if (v2.obf() ==0) goto L150;
    L146:
    goto L151;
    L150:
    L151:
    v7 = 0;
    if (v6 ==0) goto L199;
    if (v5.color() == null) goto L178;
    goto L180;
    L178:
    L180:
    colorcode.ColorCode.colorCode(v5.color()).append("&r");
    v2 = colorcode.CodeStyle.DEFAULT.colorOnly(v5.color());
    goto L246;
    L199:
    if (v7 ==0) goto L246;
    v1.append("&r");
    v2 = colorcode.CodeStyle.DEFAULT;
    if (v5.color() == null) goto L246;
    v1.append(colorcode.ColorCode.colorCode(v5.color()));
    v2 = v2.withColor(v5.color());
    L246:
    v8 = colorcode.CodeStyle$Format.values();
    v9 = v8.length;
    v10 = 0;
    L259:
    if (v10 >= v9) goto L321;
    v11 = v8[v10];
    if (v5.has(v11) ==0) goto L315;
    if (v2.has(v11) !=0) goto L315;
    v1.append(38).append(v11.code);
    v2 = v2.with(v11, 1);
    L315:
    v10 = v10 + 1;
    goto L259;
    L321:
    v1.append(v4.text);
    goto L22;
    L334:
    return v1.toString();
}

private static boolean equalColor(String a, String b) {
    if (a != null) goto L16;
    if (b != null) goto L12;
    goto L21;
    L12:
    goto L21;
    L16:
    L21:
    return a.equals(b);
}

public static String colorCode(String color) {
    if (color != null) goto L7;
    return "";
    L7:
    if (color.length() != 1) goto L28;
    return '&' + color.toLowerCase(Locale.ROOT);
    L28:
    v1 = ?;
    v2 = color.toLowerCase(Locale.ROOT).toCharArray();
    v3 = v2.length;
    v4 = 0;
    L55:
    if (v4 >= v3) goto L85;
    v5 = v2[v4];
    v1.append(38).append(v5);
    v4 = v4 + 1;
    goto L55;
    L85:
    return v1.toString();
}

public int length() {
    return this.serialize().length();
}

public static int limit(colorcode.ColorCode$Mode mode) {
    return mode.limit;
}

public int tooLong(colorcode.ColorCode$Mode mode) {
    if (this.length() <= mode.limit) goto L15;
    goto L16;
    L15:
    L16:
    return 0;
}

public String command(colorcode.ColorCode$Mode mode) {
    return mode.command + ' ' + this.serialize();
}

public static void parse(String code) {
    v1 = ?;
    v2 = ?;
    v3 = colorcode.CodeStyle.DEFAULT;
    v4 = 0;
    L23:
    if (v4 >= code.length()) goto L285;
    v5 = code.charAt(v4);
    if (colorcode.ColorCode.isMarker(v5) ==0) goto L264;
    if ((v4 + 1) >= code.length()) goto L264;
    v6 = Character.toLowerCase(code.charAt((v4 + 1)));
    if (v6 != 120) goto L194;
    v7 = ?;
    v8 = (v4 + 2);
    v9 = 0;
    L97:
    if (v9 >= 6) goto L164;
    if ((v8 + 1) >= code.length()) goto L164;
    if (colorcode.ColorCode.isMarker(code.charAt(v8)) ==0) goto L164;
    if (colorcode.ColorCode.isHexDigit(code.charAt((v8 + 1))) ==0) goto L164;
    v7.append(code.charAt((v8 + 1)));
    v8 = v8 + 2;
    v9 = v9 + 1;
    goto L97;
    L164:
    if (v7.length() != 6) goto L191;
    v3 = v3.colorOnly(v7.toString());
    v4 = v8;
    goto L23;
    L191:
    goto L264;
    L194:
    if ("0123456789abcdef".indexOf(v6) <0) goto L221;
    v3 = v3.colorOnly(String.valueOf(v6));
    v4 = v4 + 2;
    goto L23;
    L221:
    if (v6 != 114) goto L238;
    v3 = colorcode.CodeStyle.DEFAULT;
    v4 = v4 + 2;
    goto L23;
    L238:
    v7 = colorcode.ColorCode.formatFor(v6);
    if (v7 == null) goto L264;
    v3 = v3.with(v7, 1);
    v4 = v4 + 2;
    goto L23;
    L264:
    v1.append(v5);
    v2.add(v3);
    v4 = v4 + 1;
    goto L23;
    L285:
    v5 = ?;
    v5.set(v1.toString(), v2);
    return v5;
}

private static boolean isMarker(char c) {
    if (c == 38) goto L13;
    if (c != 167) goto L17;
    L13:
    goto L18;
    L17:
    L18:
    return 0;
}

private static int isHexDigit(char c) {
    if (Character.digit(c, 16) <0) goto L20;
    if (c >= 128) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

private static colorcode.CodeStyle$Format[] formatFor(char k) {
    v1 = colorcode.CodeStyle$Format.values();
    v2 = v1.length;
    v3 = 0;
    L9:
    if (v3 >= v2) goto L37;
    v4 = v1[v3];
    if (v4.code != k) goto L31;
    return v4;
    L31:
    v3 = v3 + 1;
    goto L9;
    L37:
    return null;
}

public static void importCode(String raw) {
    v1 = raw.trim();
    v2 = null;
    v3 = colorcode.ColorCode.PREFIX.matcher(v1);
    if (v3.find() ==0) goto L55;
    if (v3.group(1).equalsIgnoreCase("sign") ==0) goto L42;
    goto L45;
    L42:
    L45:
    v2 = colorcode.ColorCode$Mode.RENAME;
    v1 = v1.substring(v3.end());
    L55:
    return ?;
}

public static int rgb(String color) {
    if (color != null) goto L8;
    return 16777215;
    L8:
    if (color.length() != 1) goto L35;
    return colorcode.ColorCode.RGB["0123456789abcdef".indexOf(Character.toLowerCase(color.charAt(0)))];
    L35:
    return Integer.parseInt(color, 16);
}

public static char nearest16(int rgb) {
    v1 = ((rgb >> 16) & 255);
    v2 = ((rgb >> 8) & 255);
    v3 = (rgb & 255);
    v4 = 0;
    v5 = 9223372036854775807;
    v7 = 0;
    L35:
    if (v7 >= colorcode.ColorCode.RGB.length) goto L139;
    v8 = (v1 - ((colorcode.ColorCode.RGB[v7] >> 16) & 255));
    v9 = (v2 - ((colorcode.ColorCode.RGB[v7] >> 8) & 255));
    v10 = (v3 - (colorcode.ColorCode.RGB[v7] & 255));
    v11 = (((((l)v8) * ((l)v8)) + (((l)v9) * ((l)v9))) + (((l)v10) * ((l)v10)));
    if (cmp(v11,v5) >=0) goto L133;
    v5 = v11;
    v4 = v7;
    L133:
    v7 = v7 + 1;
    goto L35;
    L139:
    return "0123456789abcdef".charAt(v4);
}

public static String normalizeHex(String input) {
    v1 = input.replace("#", "").trim();
    if (v1.length() == 6) goto L24;
    return null;
    L24:
    v2 = 0;
    L26:
    if (v2 >= 6) goto L51;
    if (colorcode.ColorCode.isHexDigit(v1.charAt(v2)) !=0) goto L45;
    return null;
    L45:
    v2 = v2 + 1;
    goto L26;
    L51:
    return v1.toUpperCase(Locale.ROOT);
}

static regex.Pattern <clinit>() {
    new int[16][0] = 0;
    new int[16][1] = 170;
    new int[16][2] = 43520;
    new int[16][3] = 43690;
    new int[16][4] = 11141120;
    new int[16][5] = 11141290;
    new int[16][6] = 16755200;
    new int[16][7] = 11184810;
    new int[16][8] = 5592405;
    new int[16][9] = 5592575;
    new int[16][10] = 5635925;
    new int[16][11] = 5636095;
    new int[16][12] = 16733525;
    new int[16][13] = 16733695;
    new int[16][14] = 16777045;
    new int[16][15] = 16777215;
    colorcode.ColorCode.RGB = new int[16];
    colorcode.ColorCode.PREFIX = regex.Pattern.compile("^/?(rename|sign)\\s+", 2);
    return;
}

