// class net/friendswithmoney/fwm/ui/FwmTheme  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field OVERLAY I;
field GLASS_TOP I;
field GLASS_BOTTOM I;
field EDGE I;
field SHEEN I;
field RAISED I;
field INSET I;
field LOCKED I;
field DISABLED_FILL I;
field DISABLED_EDGE I;
field DISABLED_TEXT I;
field ACCENT I;
field ACCENT_DIM I;
field ON I;
field OFF I;
field TEXT I;
field TEXT_DIM I;
field TEXT_ON_ACCENT I;
field HOVER I;
field LOGO Lnet/minecraft/resources/Identifier;;
field LOGO_SHEET I;
field LOGO_W I;
field LOGO_H I;
field LOGO_RATIO F;
field BAR_W I;
private void <init>() {
    return;
}

public static void panel(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
    ctx.fillGradient((x + 1), (y + 1), ((x + w) - 1), ((y + h) - 1), ui.FwmTheme.GLASS_TOP, ui.FwmTheme.GLASS_BOTTOM);
    ui.FwmTheme.outline(ctx, x, y, w, h, ui.FwmTheme.EDGE);
    ctx.fill((x + 2), (y + 1), ((x + w) - 2), (y + 2), ui.FwmTheme.SHEEN);
    return;
}

public static void box(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int fill, int border) {
    ctx.fill((x + 1), (y + 1), ((x + w) - 1), ((y + h) - 1), fill);
    ui.FwmTheme.outline(ctx, x, y, w, h, border);
    return;
}

private static void outline(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int color) {
    ctx.fill((x + 1), y, ((x + w) - 1), (y + 1), color);
    ctx.fill((x + 1), ((y + h) - 1), ((x + w) - 1), (y + h), color);
    ctx.fill(x, (y + 1), (x + 1), ((y + h) - 1), color);
    ctx.fill(((x + w) - 1), (y + 1), (x + w), ((y + h) - 1), color);
    return;
}

public static void pill(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, int fill) {
    ctx.fill((x + 1), y, ((x + w) - 1), (y + h), fill);
    ctx.fill(x, (y + 1), (x + 1), ((y + h) - 1), fill);
    ctx.fill(((x + w) - 1), (y + 1), (x + w), ((y + h) - 1), fill);
    return;
}

public static void toggle(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, float t) {
    v6 = ui.FwmTheme.mix(ui.FwmTheme.DISABLED_FILL, ui.FwmTheme.ACCENT_DIM, t);
    v7 = ui.FwmTheme.mix(ui.FwmTheme.DISABLED_EDGE, ui.FwmTheme.ON, t);
    ui.FwmTheme.box(ctx, x, y, w, h, v6, v7);
    v8 = Math.max(6, (h - 4));
    v9 = ((w - v8) - 4);
    v10 = ((x + 2) + Math.round((t * ((f)v9))));
    ctx.fill(v10, (y + 2), (v10 + v8), ((y + h) - 2), v7);
    return;
}

private static int mix(int from, int to, float t) {
    v3 = Math.max(0, Math.min(1, t));
    v4 = 0;
    v5 = 0;
    L16:
    if (v5 >= 32) goto L72;
    v6 = ((from >> v5) & 255);
    v7 = ((to >> v5) & 255);
    v4 = (v4 | (Math.round((((f)v6) + (((f)(v7 - v6)) * v3))) << v5));
    v5 = v5 + 8;
    goto L16;
    L72:
    return v4;
}

public static void hover(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
    ctx.fill(x, y, (x + w), (y + h), ui.FwmTheme.HOVER);
    ctx.fill(x, y, (x + 2), (y + h), ui.FwmTheme.ACCENT);
    return;
}

public static void contentFrame(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
    ui.FwmTheme.box(ctx, x, y, w, h, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    return;
}

public static void sidebarButton(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active, boolean hovered, boolean locked) {
    ui.FwmTheme.sidebarButton(ctx, x, y, w, h, active, hovered, locked, 1);
    return;
}

public static void sidebarButton(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active, boolean hovered, boolean locked, boolean on) {
    if (locked ==0) goto L6;
    return;
    L6:
    if (active ==0) goto L43;
    if (on ==0) goto L34;
    goto L37;
    L34:
    L37:
    (x + 2).fill(y, (x + w), (y + h), ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.DISABLED_FILL);
    goto L66;
    L43:
    if (hovered ==0) goto L66;
    ctx.fill((x + 2), y, (x + w), (y + h), ui.FwmTheme.HOVER);
    L66:
    if (active !=0) goto L76;
    if (on !=0) goto L103;
    L76:
    if (on ==0) goto L97;
    goto L100;
    L97:
    L100:
    x.fill(y, (x + 2), (y + h), ui.FwmTheme.ACCENT, ui.FwmTheme.DISABLED_EDGE);
    L103:
    return;
}

public static void tile(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean locked, boolean hovered, boolean on) {
    if (locked ==0) goto L23;
    goto L37;
    L23:
    if (hovered ==0) goto L34;
    goto L37;
    L34:
    L37:
    y.fill((x + w), (y + h), ui.FwmTheme.LOCKED, ui.FwmTheme.HOVER, ui.FwmTheme.RAISED);
    if (hovered ==0) goto L62;
    if (locked !=0) goto L62;
    ui.FwmTheme.outline(ctx, x, y, w, h, ui.FwmTheme.ACCENT);
    L62:
    if (on !=0) goto L88;
    if (locked !=0) goto L88;
    ctx.fill(x, y, (x + 2), (y + h), ui.FwmTheme.DISABLED_EDGE);
    L88:
    return;
}

public static int sidebarTextColor(boolean active, boolean hovered, boolean locked) {
    return ui.FwmTheme.sidebarTextColor(active, hovered, locked, 1);
}

public static int sidebarTextColor(boolean active, boolean hovered, boolean locked, boolean on) {
    if (locked ==0) goto L8;
    return ui.FwmTheme.OFF;
    L8:
    if (on !=0) goto L16;
    return ui.FwmTheme.DISABLED_TEXT;
    L16:
    if (active ==0) goto L24;
    return ui.FwmTheme.TEXT;
    L24:
    if (hovered ==0) goto L34;
    goto L37;
    L34:
    L37:
    return ui.FwmTheme.TEXT_DIM;
}

public static void germanFlag(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
    v6 = Math.max(1, (h / 3));
    ctx.fill(x, y, (x + w), (y + v6), -15724528);
    ctx.fill(x, (y + v6), (x + w), (y + (2 * v6)), -3407872);
    ctx.fill(x, (y + (2 * v6)), (x + w), (y + h), -12800);
    ui.FwmTheme.flagFrame(ctx, x, y, w, h, active);
    return;
}

public static void usFlag(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
    ctx.fill(x, y, (x + w), (y + h), -723724);
    v6 = 0;
    L18:
    if (v6 >= 4) goto L75;
    v7 = (y + (v6 * 2));
    if ((v7 + 1) <= (y + h)) goto L46;
    goto L75;
    L46:
    ctx.fill(x, v7, (x + w), Math.min((v7 + 1), (y + h)), -5103052);
    v6 = v6 + 1;
    goto L18;
    L75:
    v6 = Math.max(2, ((w / 2) - 1));
    ctx.fill(x, y, (x + v6), (y + Math.max(2, (h / 2))), -12829842);
    ctx.fill((x + 1), (y + 1), (x + 2), (y + 2), -723724);
    ctx.fill((x + 3), (y + 2), (x + 4), (y + 3), -723724);
    ui.FwmTheme.flagFrame(ctx, x, y, w, h, active);
    return;
}

private static void flagFrame(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
    if (active !=0) goto L20;
    ctx.fill(x, y, (x + w), (y + h), -1726997996);
    L20:
    if (active ==0) goto L45;
    goto L48;
    L45:
    L48:
    ui.FwmTheme.outline((x - 1), (y - 1), (w + 2), (h + 2), ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    return;
}

public static void divider(client.gui.GuiGraphicsExtractor ctx, int x, int y, int w) {
    ctx.fill(x, y, (x + w), (y + 1), 866709700);
    return;
}

public static void logo(client.gui.GuiGraphicsExtractor ctx, int x, int y, int width) {
    ui.FwmGfx.blit(ctx, ui.FwmTheme.LOGO, x, y, width, ui.FwmTheme.logoHeight(width), 256, 192, 256);
    return;
}

public static int logoHeight(int width) {
    return Math.round((((f)width) / 1.3333333730697632));
}

static resources.Identifier <clinit>() {
    ui.FwmTheme.OVERLAY = 637999631;
    ui.FwmTheme.GLASS_TOP = -1944370647;
    ui.FwmTheme.GLASS_BOTTOM = -1945163238;
    ui.FwmTheme.EDGE = 1504243908;
    ui.FwmTheme.SHEEN = 452984831;
    ui.FwmTheme.RAISED = 976539754;
    ui.FwmTheme.INSET = 1074141711;
    ui.FwmTheme.LOCKED = 437201434;
    ui.FwmTheme.DISABLED_FILL = 716189744;
    ui.FwmTheme.DISABLED_EDGE = -1713343372;
    ui.FwmTheme.DISABLED_TEXT = -2060144;
    ui.FwmTheme.ACCENT = -10688364;
    ui.FwmTheme.ACCENT_DIM = -2144022422;
    ui.FwmTheme.ON = -12723068;
    ui.FwmTheme.OFF = 1087957214;
    ui.FwmTheme.TEXT = -1378578;
    ui.FwmTheme.TEXT_DIM = -6504534;
    ui.FwmTheme.TEXT_ON_ACCENT = -16440561;
    ui.FwmTheme.HOVER = 641588356;
    ui.FwmTheme.LOGO = resources.Identifier.fromNamespaceAndPath("fwm", "textures/gui/logo.png");
    return;
}

