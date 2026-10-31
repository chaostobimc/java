// class net/friendswithmoney/fwm/ui/RankColors  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field COLORS Ljava/util/List;;
field UNKNOWN [I;
field MIN_LUMA D;
private void <init>() {
    return;
}

public static int of(int rankIndex) {
    if (rankIndex <0) goto L16;
    if (rankIndex < ui.RankColors.COLORS.size()) goto L22;
    L16:
    goto L34;
    L22:
    L34:
    v1 = (([I)ui.RankColors.COLORS.get(rankIndex));
    new int[2][0] = ui.RankColors.readable(v1[0]);
    new int[2][1] = ui.RankColors.readable(v1[1]);
    return new int[2];
}

static long readable(int rgb) {
    v1 = ((rgb >> 16) & 255);
    v2 = ((rgb >> 8) & 255);
    v3 = (rgb & 255);
    v4 = ui.RankColors.luma(v1, v2, v3);
    if (cmp(v4,0.43) <0) goto L43;
    return rgb;
    L43:
    v6 = ((0.43 - v4) / (1 - v4));
    v1 = ((i)Math.round((((d)v1) + (((d)(255 - v1)) * v6))));
    v2 = ((i)Math.round((((d)v2) + (((d)(255 - v2)) * v6))));
    v3 = ((i)Math.round((((d)v3) + (((d)(255 - v3)) * v6))));
    return (((v1 << 16) | (v2 << 8)) | v3);
}

static double luma(int r, int g, int b) {
    return ((((0.2126 * ((d)r)) + (0.7152 * ((d)g))) + (0.0722 * ((d)b))) / 255.0);
}

public static int[] flat(int rankIndex) {
    return (-16777216 | ui.RankColors.of(rankIndex)[0]);
}

public static network.chat.MutableComponent gradient(String text, int rankIndex) {
    v2 = ui.RankColors.of(rankIndex);
    if (text == null) goto L16;
    if (text.isEmpty() ==0) goto L20;
    L16:
    return network.chat.Component.empty();
    L20:
    if (v2[0] != v2[1]) goto L37;
    return ui.RankColors.colored(text, v2[0]);
    L37:
    v3 = network.chat.Component.empty();
    v4 = Math.max(1, (text.length() - 1));
    v5 = 0;
    L56:
    if (v5 >= text.length()) goto L104;
    v3.append(ui.RankColors.colored(String.valueOf(text.charAt(v5)), ui.RankColors.mix(v2[0], v2[1], (((f)v5) / ((f)v4)))));
    v5 = v5 + 1;
    goto L56;
    L104:
    return v3;
}

private static network.chat.MutableComponent colored(String text, int rgb) {
    return network.chat.Component.literal(text).setStyle(network.chat.Style.EMPTY.withColor(network.chat.TextColor.fromRgb(rgb)));
}

private static int mix(int from, int to, float t) {
    v3 = Math.round((((f)((from >> 16) & 255)) + (((f)(((to >> 16) & 255) - ((from >> 16) & 255))) * t)));
    v4 = Math.round((((f)((from >> 8) & 255)) + (((f)(((to >> 8) & 255) - ((from >> 8) & 255))) * t)));
    v5 = Math.round((((f)(from & 255)) + (((f)((to & 255) - (from & 255))) * t)));
    return (((v3 << 16) | (v4 << 8)) | v5);
}

static List <clinit>() {
    new int[2][0] = 11122664;
    new int[2][1] = 8950998;
    new [I[13][0] = new int[2];
    new int[2][0] = 8936667;
    new int[2][1] = 8936667;
    new [I[13][1] = new int[2];
    new int[2][0] = 13628359;
    new int[2][1] = 8378767;
    new [I[13][2] = new int[2];
    new int[2][0] = 15782522;
    new int[2][1] = 14267466;
    new [I[13][3] = new int[2];
    new int[2][0] = 11190238;
    new int[2][1] = 11190238;
    new [I[13][4] = new int[2];
    new int[2][0] = 12855654;
    new int[2][1] = 12855654;
    new [I[13][5] = new int[2];
    new int[2][0] = 10498249;
    new int[2][1] = 10498249;
    new [I[13][6] = new int[2];
    new int[2][0] = 2771578;
    new int[2][1] = 1319999;
    new [I[13][7] = new int[2];
    new int[2][0] = 5144488;
    new int[2][1] = 2904691;
    new [I[13][8] = new int[2];
    new int[2][0] = 14923013;
    new int[2][1] = 12092939;
    new [I[13][9] = new int[2];
    new int[2][0] = 1853994;
    new int[2][1] = 795669;
    new [I[13][10] = new int[2];
    new int[2][0] = 3116891;
    new int[2][1] = 1332013;
    new [I[13][11] = new int[2];
    new int[2][0] = 3919994;
    new int[2][1] = 1483594;
    new [I[13][12] = new int[2];
    ui.RankColors.COLORS = List.of(new [I[13]);
    new int[2][0] = 12175295;
    new int[2][1] = 12175295;
    ui.RankColors.UNKNOWN = new int[2];
    return;
}

