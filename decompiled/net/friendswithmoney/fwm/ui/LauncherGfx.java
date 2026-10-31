// class net/friendswithmoney/fwm/ui/LauncherGfx  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

private static long inset(int row, int h, int r) {
    v3 = Math.min(row, ((h - 1) - row));
    if (v3 < r) goto L17;
    return 0;
    L17:
    v4 = (((d)(r - v3)) - 0.5);
    return ((i)Math.round((((d)r) - Math.sqrt(Math.max(0, ((((d)r) * ((d)r)) - (v4 * v4)))))));
}

public static void round(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int color) {
    if (w <=0) goto L9;
    if (h >0) goto L10;
    L9:
    return;
    L10:
    r = Math.max(0, Math.min(r, (Math.min(w, h) / 2)));
    v7 = 0;
    L32:
    if (v7 >= h) goto L132;
    if (v7 < r) goto L89;
    if (v7 >= (h - r)) goto L89;
    ctx.fill(x, (y + v7), (x + w), ((y + h) - r), color);
    v7 = ((h - r) - 1);
    goto L126;
    L89:
    v8 = ui.LauncherGfx.inset(v7, h, r);
    ctx.fill((x + v8), (y + v7), ((x + w) - v8), ((y + v7) + 1), color);
    L126:
    v7 = v7 + 1;
    goto L32;
    L132:
    return;
}

public static void card(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r) {
    ui.LauncherGfx.roundGradient(ctx, x, y, w, h, r, ui.FwmTheme.GLASS_TOP, ui.FwmTheme.GLASS_BOTTOM);
    ui.LauncherGfx.ring(ctx, x, y, w, h, r, ui.FwmTheme.EDGE);
    ctx.fill((x + r), (y + 1), ((x + w) - r), (y + 2), ui.FwmTheme.SHEEN);
    return;
}

public static void roundGradient(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int top, int bottom) {
    if (w <=0) goto L9;
    if (h >0) goto L10;
    L9:
    return;
    L10:
    r = Math.max(0, Math.min(r, (Math.min(w, h) / 2)));
    v8 = 0;
    L32:
    if (v8 >= h) goto L110;
    if (h > 1) goto L49;
    goto L58;
    L49:
    L58:
    v9 = (((f)v8) / ((f)(h - 1)));
    v10 = ui.LauncherGfx.inset(v8, h, r);
    ctx.fill((x + v10), (y + v8), ((x + w) - v10), ((y + v8) + 1), ui.LauncherGfx.mix(top, bottom, v9));
    v8 = v8 + 1;
    goto L32;
    L110:
    return;
}

private static int mix(int a, int b, float t) {
    v3 = 0;
    v4 = 0;
    L5:
    if (v4 > 24) goto L63;
    v5 = ((a >>> v4) & 255);
    v6 = ((b >>> v4) & 255);
    v3 = (v3 | ((Math.round((((f)v5) + (((f)(v6 - v5)) * t))) & 255) << v4));
    v4 = v4 + 8;
    goto L5;
    L63:
    return v3;
}

public static void infoGlyph(client.gui.GuiGraphicsExtractor ctx, int x, int y, int color) {
    ctx.fill((x + 6), (y + 3), (x + 8), (y + 5), color);
    ctx.fill((x + 6), (y + 6), (x + 8), (y + 11), color);
    return;
}

public static void ring(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int color) {
    if (w <= 1) goto L11;
    if (h > 1) goto L12;
    L11:
    return;
    L12:
    r = Math.max(0, Math.min(r, (Math.min(w, h) / 2)));
    v7 = Math.max(0, (r - 1));
    v8 = 0;
    L44:
    if (v8 >= h) goto L191;
    v9 = ui.LauncherGfx.inset(v8, h, r);
    if (v8 ==0) goto L76;
    if (v8 != (h - 1)) goto L105;
    L76:
    ctx.fill((x + v9), (y + v8), ((x + w) - v9), ((y + v8) + 1), color);
    goto L185;
    L105:
    v10 = (1 + ui.LauncherGfx.inset((v8 - 1), (h - 2), v7));
    v11 = Math.max(v10, (v9 + 1));
    ctx.fill((x + v9), (y + v8), (x + v11), ((y + v8) + 1), color);
    ctx.fill(((x + w) - v11), (y + v8), ((x + w) - v9), ((y + v8) + 1), color);
    L185:
    v8 = v8 + 1;
    goto L44;
    L191:
    return;
}

public static void toggle(client.gui.GuiGraphicsExtractor ctx, int x, int y, boolean on, int onColor, int offColor) {
    v6 = 22;
    v7 = 12;
    if (on ==0) goto L28;
    goto L30;
    L28:
    L30:
    ui.LauncherGfx.round(x, y, v6, v7, (v7 / 2), onColor, offColor);
    v8 = (v7 - 2);
    if (on ==0) goto L55;
    goto L58;
    L55:
    L58:
    v9 = (x + 1);
    ui.LauncherGfx.round(ctx, v9, (y + 2), v8, v8, (v8 / 2), 855638016);
    ui.LauncherGfx.round(ctx, v9, (y + 1), v8, v8, (v8 / 2), m1);
    return;
}

public static org.joml.Matrix3x2fStack icon(client.gui.GuiGraphicsExtractor ctx, String key, int x, int y, float scale, int color) {
    v6 = ctx.pose();
    v6.pushMatrix();
    v6.translate(((f)x), ((f)y));
    v6.scale(scale, scale);
    ui.FwmIcons.draw(ctx, key, 0, 0, color);
    v6.popMatrix();
    return;
}

public static void text(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String s, int x, int y, int color) {
    ctx.text(font, network.chat.Component.literal(s), x, y, color, 0);
    return;
}

public static void bold(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String s, int x, int y, int color) {
    ctx.text(font, network.chat.Component.literal(s).withStyle(ChatFormatting.BOLD), x, y, color, 0);
    return;
}

public static org.joml.Matrix3x2fStack big(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String s, int x, int y, float scale, int color, boolean bold) {
    v8 = ctx.pose();
    v8.pushMatrix();
    v8.translate(((f)x), ((f)y));
    v8.scale(scale, scale);
    if (bold ==0) goto L51;
    goto L55;
    L51:
    L55:
    v9 = network.chat.Component.literal(s);
    ctx.text(font, v9, 0, 0, color, 0);
    v8.popMatrix();
    return;
}

public static String trim(client.gui.Font font, String s, int width) {
    if (font.width(s) > width) goto L13;
    goto L31;
    L13:
    L31:
    return font.plainSubstrByWidth(s, Math.max(0, (width - 6))) + '…';
}

