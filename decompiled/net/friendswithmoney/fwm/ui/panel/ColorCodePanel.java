// class net/friendswithmoney/fwm/ui/panel/ColorCodePanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/TabbedPanel
field SESSION Lnet/friendswithmoney/fwm/ui/panel/ColorCodePanel$User;;
public void <init>(ui.panel.PanelHost host) {
    ui.panel.ColorCodePanel.SESSION.owner = this;
    return;
}

static network.chat.Style style(colorcode.CodeStyle s, boolean withBold) {
    if (withBold ==0) goto L31;
    if (s.bold() ==0) goto L31;
    goto L32;
    L31:
    L32:
    return 1.withBold(Boolean.valueOf(0)).withUnderlined(Boolean.valueOf(s.underline())).withItalic(Boolean.valueOf(s.italic())).withStrikethrough(Boolean.valueOf(s.strike())).withObfuscated(Boolean.valueOf(s.obf()));
}

static network.chat.MutableComponent text(colorcode.ColorCode code) {
    v1 = network.chat.Component.empty();
    v2 = code.runs().iterator();
    L14:
    if (v2.hasNext() ==0) goto L59;
    v3 = ((colorcode.ColorCode$Run)v2.next());
    v1.append(network.chat.Component.literal(v3.text()).setStyle(ui.panel.ColorCodePanel.style(v3.style(), 1)));
    goto L14;
    L59:
    return v1;
}

static util.FormattedCharSequence trimmed(client.gui.Font font, network.chat.Component text, int width) {
    return locale.Language.getInstance().getVisualOrder(font.substrByWidth(text, Math.max(0, width)));
}

static void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int bw, int bh, network.chat.Component label, boolean hovered, boolean active, boolean enabled) {
    if (enabled !=0) goto L11;
    goto L25;
    L11:
    if (active ==0) goto L22;
    goto L25;
    L22:
    L25:
    v10 = ui.FwmTheme.RAISED;
    if (enabled !=0) goto L38;
    goto L57;
    L38:
    if (hovered !=0) goto L48;
    if (active ==0) goto L54;
    L48:
    goto L57;
    L54:
    L57:
    v11 = ui.FwmTheme.OFF;
    ui.FwmTheme.box(ctx, bx, by, bw, bh, v10, v11);
    v12 = ui.panel.ColorCodePanel.trimmed(font, label, (bw - 4));
    if (enabled !=0) goto L96;
    goto L110;
    L96:
    if (hovered ==0) goto L107;
    goto L110;
    L107:
    L110:
    v13 = ui.FwmTheme.TEXT;
    Objects.requireNonNull(font);
    ctx.text(font, v12, (bx + ((bw - font.width(v12)) / 2)), ((by + ((bh - 9) / 2)) + 1), v13, 0);
    return;
}

static boolean over(double mx, double a1, int my, int a3, int bx, int by) {
    if (cmp(mx,((d)bx)) <0) goto L42;
    if (cmp(mx,((d)(bx + v6))) >=0) goto L42;
    if (cmp(my,((d)by)) <0) goto L42;
    if (cmp(my,((d)(by + v7))) >=0) goto L42;
    goto L43;
    L42:
    L43:
    return 0;
}

static void tip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String text, int mouseX, int mouseY) {
    v5 = (font.width(text) + 8);
    v6 = ctx.guiWidth();
    if (((mouseX + 10) + v5) <= v6) goto L38;
    goto L42;
    L38:
    L42:
    v7 = (mouseX + 10);
    v8 = (mouseY - 14);
    ctx.nextStratum();
    ui.panel.ColorCodePanel.tooltipBox(ctx, v7, v8, v5, 13);
    ctx.text(font, network.chat.Component.literal(text), (v7 + 4), (v8 + 3), m1, 1);
    return;
}

static void tooltipBox(client.gui.GuiGraphicsExtractor ctx, int bx, int by, int bw, int bh) {
    ctx.fill((bx + 1), by, ((bx + bw) - 1), (by + bh), -267386864);
    ctx.fill(bx, (by + 1), (bx + 1), ((by + bh) - 1), -267386864);
    ctx.fill(((bx + bw) - 1), (by + 1), (bx + bw), ((by + bh) - 1), -267386864);
    ctx.fill((bx + 1), (by + 1), ((bx + bw) - 1), (by + 2), 1347420415);
    ctx.fill((bx + 1), ((by + bh) - 2), ((bx + bw) - 1), ((by + bh) - 1), 1344798847);
    ctx.fill((bx + 1), (by + 2), (bx + 2), ((by + bh) - 2), 1347420415);
    ctx.fill(((bx + bw) - 2), (by + 2), ((bx + bw) - 1), ((by + bh) - 2), 1347420415);
    return;
}

private static void lambda$new$1() {
    return ?;
}

private static void lambda$new$0(ui.panel.PanelHost host) {
    return ?;
}

static void <clinit>() {
    ui.panel.ColorCodePanel.SESSION = ?;
    return;
}

