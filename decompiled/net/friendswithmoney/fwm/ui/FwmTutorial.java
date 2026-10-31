// class net/friendswithmoney/fwm/ui/FwmTutorial  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FIGURE Lnet/minecraft/resources/Identifier;;
field FIG_W I;
field FIG_H I;
field SHEET_W I;
field SHEET_H I;
field PAD I;
field LINE_H I;
field BTN_H I;
field TOGGLE_W I;
field TOGGLE_H I;
field SLIDE_MS I;
field OVERVIEW Ljava/lang/String;;
field STEPS Ljava/util/List;;
field HUD_CARDS Ljava/util/List;;
field step I;
field colX I;
field colY I;
field colScale F;
field backBox [I;
field nextBox [I;
field skipBox [I;
field pickerBoxes Ljava/util/List;;
field toggleBox [I;
field MENU_ROOM I;
field COLUMN_H I;
field DOCK_GAP I;
field docked Z;
field dockLeft I;
field dockRight I;
field dockTop I;
field dockBottom I;
private void <init>() {
    return;
}

public static boolean active() {
    if (ui.FwmTutorial.step <0) goto L10;
    goto L11;
    L10:
    L11:
    return 0;
}

public static boolean pending() {
    if (config.FwmConfig.INSTANCE.tutorialSeen !=0) goto L13;
    goto L14;
    L13:
    L14:
    return 0;
}

public static void start() {
    ui.FwmTutorial.step = 0;
    ui.widget.Anim.toward("fwm.tutorial.slide", 0, 1);
    ui.FwmTutorial.sync();
    return;
}

private static void finish() {
    ui.FwmTutorial.step = m1;
    config.FwmConfig.INSTANCE.tutorialSeen = 1;
    config.FwmConfig.INSTANCE.tutorialVersion = FwmClient.MOD_VERSION;
    config.ConfigManager.save();
    ui.FwmTutorial.relayout();
    return;
}

public static void skip() {
    ui.FwmTutorial.finish();
    return;
}

private static void next() {
    if (ui.FwmTutorial.step >=0) goto L7;
    return;
    L7:
    if ((ui.FwmTutorial.step + 1) < ui.FwmTutorial.STEPS.size()) goto L27;
    ui.FwmTutorial.finish();
    return;
    L27:
    ui.FwmTutorial.step = (ui.FwmTutorial.step + 1);
    ui.FwmTutorial.sync();
    return;
}

private static void back() {
    if (ui.FwmTutorial.step >0) goto L7;
    return;
    L7:
    ui.FwmTutorial.step = (ui.FwmTutorial.step - 1);
    ui.FwmTutorial.sync();
    return;
}

private static Object current() {
    if (ui.FwmTutorial.step <0) goto L20;
    if (ui.FwmTutorial.step < ui.FwmTutorial.STEPS.size()) goto L24;
    L20:
    goto L38;
    L24:
    L38:
    return ((ui.FwmTutorial$Step)ui.FwmTutorial.STEPS.get(ui.FwmTutorial.step));
}

private static void sync() {
    v0 = client.Minecraft.getInstance();
    v1 = ui.FwmTutorial.current();
    if (v0 == null) goto L16;
    if (v1 != null) goto L17;
    L16:
    return;
    L17:
    v2 = v0.gui.screen();
    if (v1.kind() != ui.FwmTutorial$Kind.OVERLAY) goto L58;
    if ((v2 instanceof ui.HudEditScreen) !=0) goto L57;
    v0.gui.setScreen(?);
    L57:
    return;
    L58:
    if ((v2 instanceof ui.HudEditScreen) ==0) goto L82;
    v3 = ((ui.HudEditScreen)v2);
    v3.onClose();
    v2 = v0.gui.screen();
    L82:
    if ((v2 instanceof ui.DesignHost) ==0) goto L116;
    v3 = ((ui.DesignHost)v2);
    if (v1.categoryKey() == null) goto L116;
    v3.tourShow(v1.categoryKey(), v1.subTab());
    goto L149;
    L116:
    if ((v2 instanceof ui.FwmScreen) ==0) goto L149;
    v4 = ((ui.FwmScreen)v2);
    if (v1.categoryKey() == null) goto L149;
    v4.tourShow(v1.categoryKey(), v1.subTab());
    L149:
    ui.FwmTutorial.relayout();
    return;
}

private static void relayout() {
    v0 = client.Minecraft.getInstance();
    if (v0 == null) goto L32;
    v2 = v0.gui.screen();
    if ((v2 instanceof ui.FwmScreen) ==0) goto L32;
    v1 = ((ui.FwmScreen)v2);
    v1.tourRelayout();
    L32:
    return;
}

public static void tick(client.Minecraft client) {
    v1 = config.FwmConfig.INSTANCE;
    if (v1.tutorialGreeted !=0) goto L17;
    if (ui.FwmTutorial.pending() !=0) goto L18;
    L17:
    return;
    L18:
    if (client == null) goto L29;
    if (client.player != null) goto L30;
    L29:
    return;
    L30:
    if (ui.FwmTutorial.onWantedServer(client) !=0) goto L38;
    return;
    L38:
    v1.tutorialGreeted = 1;
    config.ConfigManager.save();
    ui.FwmHud.showGreeting();
    return;
}

private static boolean onWantedServer(client.Minecraft client) {
    v1 = client.getCurrentServer();
    if (v1 != null) goto L11;
    return 0;
    L11:
    v2 = config.FwmConfig.INSTANCE.tutorialServerMatch;
    if (v2 == null) goto L29;
    if (v2.isBlank() ==0) goto L31;
    L29:
    return 1;
    L31:
    v2 = v2.toLowerCase(Locale.ROOT);
    if (v1.ip != null) goto L51;
    goto L61;
    L51:
    L61:
    v3 = v1.ip.toLowerCase(Locale.ROOT);
    if (v1.name != null) goto L74;
    goto L84;
    L74:
    L84:
    v4 = v1.name.toLowerCase(Locale.ROOT);
    if (v3.contains(v2) !=0) goto L103;
    if (v4.contains(v2) ==0) goto L107;
    L103:
    goto L108;
    L107:
    L108:
    return 0;
}

public static int reservedSide(int viewW) {
    if (ui.FwmTutorial.active() !=0) goto L8;
    return 0;
    L8:
    return Math.min(150, Math.max(112, (viewW - 338)));
}

public static void dock(int left, int right, int top, int bottom) {
    ui.FwmTutorial.docked = 1;
    ui.FwmTutorial.dockLeft = left;
    ui.FwmTutorial.dockRight = right;
    ui.FwmTutorial.dockTop = top;
    ui.FwmTutorial.dockBottom = bottom;
    return;
}

public static void undock() {
    ui.FwmTutorial.docked = 0;
    return;
}

public static boolean onLeft() {
    v0 = ui.FwmTutorial.current();
    if (v0 == null) goto L19;
    if (v0.right() !=0) goto L19;
    goto L20;
    L19:
    L20:
    return 0;
}

public static int reservedLeft(int viewW) {
    if (ui.FwmTutorial.active() ==0) goto L19;
    if (ui.FwmTutorial.onLeft() ==0) goto L19;
    goto L20;
    L19:
    L20:
    return 0;
}

public static int reservedRight(int viewW) {
    if (ui.FwmTutorial.active() ==0) goto L19;
    if (ui.FwmTutorial.onLeft() !=0) goto L19;
    goto L20;
    L19:
    L20:
    return 0;
}

public static String highlightKey() {
    v0 = ui.FwmTutorial.current();
    if (v0 == null) goto L18;
    if (v0.kind() != ui.FwmTutorial$Kind.PICKER) goto L20;
    L18:
    return null;
    L20:
    return v0.categoryKey();
}

public static boolean consumes(double mouseX, int a1) {
    if (ui.FwmTutorial.active() !=0) goto L8;
    return 0;
    L8:
    v3 = ui.FwmTutorial.reservedSide(v2);
    if (ui.FwmTutorial.onLeft() ==0) goto L34;
    if (cmp(mouseX,((d)v3)) >=0) goto L30;
    goto L48;
    L30:
    goto L48;
    L34:
    if (cmp(mouseX,((d)(v2 - v3))) <0) goto L47;
    goto L48;
    L47:
    L48:
    return 0;
}

public static org.joml.Matrix3x2fStack render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int viewW, int viewH, int mouseX, int mouseY) {
    v6 = ui.FwmTutorial.current();
    if (v6 != null) goto L11;
    return;
    L11:
    v7 = (ui.FwmTutorial.reservedSide(viewW) - 8);
    v8 = Math.min((viewH - 8), 236);
    v9 = (ui.FwmTutorial.dockBottom - ui.FwmTutorial.dockTop);
    if (ui.FwmTutorial.docked ==0) goto L69;
    if (v9 <=0) goto L69;
    if (v9 >= v8) goto L69;
    goto L70;
    L69:
    L70:
    v10 = 1;
    if (ui.FwmTutorial.docked ==0) goto L88;
    goto L90;
    L88:
    L90:
    v11 = v8;
    v12 = Math.round((((f)v7) / v10));
    v13 = ui.widget.Anim.ease(ui.widget.Anim.toward("fwm.tutorial.slide", 1, 260));
    if (ui.FwmTutorial.docked ==0) goto L148;
    if (ui.FwmTutorial.onLeft() ==0) goto L140;
    goto L164;
    L140:
    goto L164;
    L148:
    if (ui.FwmTutorial.onLeft() ==0) goto L158;
    goto L164;
    L158:
    L164:
    v14 = ((viewW - v7) - 4);
    if (ui.FwmTutorial.onLeft() ==0) goto L180;
    goto L183;
    L180:
    L183:
    v15 = (viewW + 4);
    v16 = Math.round((((f)v15) + (((f)(v14 - v15)) * v13)));
    if (ui.FwmTutorial.docked ==0) goto L215;
    goto L225;
    L215:
    L225:
    v17 = Math.max(4, ((viewH - v11) / 2));
    ui.FwmTutorial.colX = v16;
    ui.FwmTutorial.colY = v17;
    ui.FwmTutorial.colScale = v10;
    mouseX = ((i)Math.floor(((d)(((f)(mouseX - v16)) / v10))));
    mouseY = ((i)Math.floor(((d)(((f)(mouseY - v17)) / v10))));
    v18 = ctx.pose();
    v18.pushMatrix();
    v18.translate(((f)v16), ((f)v17));
    v18.scale(v10, v10);
    v19 = 0;
    v20 = 0;
    ui.FwmTheme.panel(ctx, v19, v20, v12, v11);
    v21 = (v19 + 6);
    v22 = (v12 - 12);
    if (v6.kind() != ui.FwmTutorial$Kind.TEXT) goto L363;
    goto L372;
    L363:
    L372:
    v23 = Math.min(44, (v11 / 5));
    v24 = Math.max(1, Math.round(((((f)v23) * 256.0) / 498.0)));
    ui.FwmGfx.blit(ctx, ui.FwmTutorial.FIGURE, (v19 + ((v12 - v24) / 2)), ((v20 + 4) + ui.FwmTutorial.bob()), v24, v23, 256, 498, 256, 512);
    v25 = ui.FwmTutorial.renderButtons(ctx, font, v21, v22, ((v20 + v11) - 6), mouseX, mouseY);
    v26 = (((v20 + 4) + v23) + 6);
    new Object[2][0] = Integer.valueOf((ui.FwmTutorial.step + 1));
    new Object[2][1] = Integer.valueOf(ui.FwmTutorial.STEPS.size());
    ctx.text(font, network.chat.Component.literal(ui.Lang.str("fwm.tutorial.step", new Object[2])), v21, v26, ui.FwmTheme.TEXT_DIM);
    v26 = v26 + 12;
    ui.FwmTutorial.pickerBoxes.clear();
    ui.FwmTutorial.toggleBox = null;
    // switch (v6.kind().ordinal()) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=78), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=2), Operand(op_type=<OperandTypes.BRANCH: 40>, value=63), Operand(op_type=<OperandTypes.BRANCH: 40>, value=25), Operand(op_type=<OperandTypes.BRANCH: 40>, value=45)]
    L568:
    ui.FwmTutorial.renderPicker(ctx, font, v21, v26, v22, v25, mouseX, mouseY);
    goto L621;
    L588:
    ui.FwmTutorial.renderOverlay(ctx, font, v6, v21, v26, v22, v25);
    goto L621;
    L606:
    ui.FwmTutorial.renderText(ctx, font, v6, v21, v26, v22, v25);
    L621:
    v18.popMatrix();
    return;
}

private static long bob() {
    return ((i)Math.round((Math.sin((((d)System.currentTimeMillis()) / 900.0)) * 1.5)));
}

private static void text(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String body, int x, int y, int width, int bottom, int color) {
    v8 = ui.FwmTutorial.wrap(font, body, width).iterator();
    L14:
    if (v8.hasNext() ==0) goto L70;
    v9 = ((String)v8.next());
    if ((y + 10) <= bottom) goto L49;
    goto L70;
    L49:
    ctx.text(font, network.chat.Component.literal(v9), x, y, color);
    y = y + 10;
    goto L14;
    L70:
    return y;
}

private static void renderText(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, ui.FwmTutorial$Step at, int x, int y, int width, int bottom) {
    y = ui.FwmTutorial.text(ctx, font, ui.FwmTutorial.body(at), x, y, width, bottom, ui.FwmTheme.TEXT);
    if (at.toggleKey() != null) goto L29;
    return;
    L29:
    y = y + 6;
    if ((y + 9) <= bottom) goto L43;
    return;
    L43:
    v7 = config.Features.isOn(at.toggleKey());
    ui.FwmTheme.toggle(ctx, x, y, 18, 9, ui.widget.Anim.toward('fwm.tutorial.tog.' + at.toggleKey(), v7, 120));
    if (v7 ==0) goto L125;
    goto L128;
    L125:
    L128:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str(at.toggleKey(), new Object[0]), ((width - 18) - 5))), ((x + 18) + 5), y, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM);
    new int[4][0] = x;
    new int[4][1] = (y - 2);
    new int[4][2] = width;
    new int[4][3] = 13;
    ui.FwmTutorial.toggleBox = new int[4];
    return;
}

private static boolean renderPicker(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int width, int bottom, int mouseX, int mouseY) {
    y = (ui.FwmTutorial.text(ctx, font, ui.Lang.str("fwm.tutorial.picker", new Object[0]), x, y, width, bottom, ui.FwmTheme.TEXT) + 4);
    v8 = 0;
    L30:
    if (v8 >= config.Features.SWITCHABLE.size()) goto L242;
    if ((y + 9) <= bottom) goto L55;
    goto L242;
    L55:
    v9 = ((String)config.Features.SWITCHABLE.get(v8));
    v10 = config.Features.isOn(v9);
    if (mouseX < x) goto L115;
    if (mouseX >= (x + width)) goto L115;
    if (mouseY < (y - 1)) goto L115;
    if (mouseY >= ((y + 9) + 2)) goto L115;
    goto L116;
    L115:
    L116:
    v11 = 0;
    ui.FwmTheme.toggle(ctx, x, y, 18, 9, ui.widget.Anim.toward('fwm.tutorial.pick.' + v9, v10, 120));
    if (v10 !=0) goto L184;
    if (v11 ==0) goto L190;
    L184:
    goto L193;
    L190:
    L193:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str(v9, new Object[0]), ((width - 18) - 5))), ((x + 18) + 5), y, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM);
    new int[5][0] = x;
    new int[5][1] = (y - 1);
    new int[5][2] = width;
    new int[5][3] = 11;
    new int[5][4] = v8;
    ui.FwmTutorial.pickerBoxes.add(new int[5]);
    y = y + 11;
    v8 = v8 + 1;
    goto L30;
    L242:
    return;
}

private static int renderOverlay(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, ui.FwmTutorial$Step at, int x, int y, int width, int bottom) {
    y = (ui.FwmTutorial.text(ctx, font, ui.FwmTutorial.body(at), x, y, width, bottom, ui.FwmTheme.TEXT) + 4);
    v7 = ui.FwmTutorial.HUD_CARDS.iterator();
    L33:
    if (v7.hasNext() ==0) goto L123;
    v8 = ((String)v7.next());
    if ((y + 10) <= bottom) goto L68;
    goto L123;
    L68:
    y = (ui.FwmTutorial.text(ctx, font, ui.Lang.str('fwm.hudedit.' + v8, new Object[0]) + ': ' + ui.Lang.str('fwm.tutorial.hud.' + v8, new Object[0]), x, y, width, bottom, ui.FwmTheme.TEXT_DIM) + 3);
    goto L33;
    L123:
    return;
}

private static int[] renderButtons(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int width, int bottom, int mouseX, int mouseY) {
    v7 = ui.Lang.str("fwm.tutorial.skipNote", new Object[0]);
    Objects.requireNonNull(font);
    v8 = (bottom - 9);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v7, width)), x, v8, ui.FwmTheme.TEXT_DIM);
    v9 = ((v8 - 3) - 14);
    ui.FwmTutorial.backBox = null;
    if (ui.FwmTutorial.step <=0) goto L157;
    v10 = Math.min((width / 2), (font.width(ui.Lang.str("fwm.tutorial.back", new Object[0])) + 10));
    ui.FwmTutorial.backBox = ui.FwmTutorial.button(ctx, font, ui.Lang.str("fwm.tutorial.back", new Object[0]), x, v9, v10, mouseX, mouseY, 0);
    ui.FwmTutorial.skipBox = ui.FwmTutorial.button(ctx, font, ui.Lang.str("fwm.tutorial.skip", new Object[0]), ((x + v10) + 4), v9, ((width - v10) - 4), mouseX, mouseY, 0);
    goto L184;
    L157:
    ui.FwmTutorial.skipBox = ui.FwmTutorial.button(ctx, font, ui.Lang.str("fwm.tutorial.skip", new Object[0]), x, v9, width, mouseX, mouseY, 0);
    L184:
    v9 = v9 + 238;
    if ((ui.FwmTutorial.step + 1) < ui.FwmTutorial.STEPS.size()) goto L209;
    goto L212;
    L209:
    L212:
    v10 = ui.Lang.str("fwm.tutorial.next", new Object[0]);
    ui.FwmTutorial.nextBox = ui.FwmTutorial.button(ctx, font, v10, x, v9, width, mouseX, mouseY, 1);
    return (v9 - 5);
}

private static void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String label, int x, int y, int w, int mouseX, int mouseY, boolean accent) {
    if (mouseX < x) goto L36;
    if (mouseX >= (x + w)) goto L36;
    if (mouseY < y) goto L36;
    if (mouseY >= (y + 14)) goto L36;
    goto L37;
    L36:
    L37:
    v9 = 0;
    if (accent ==0) goto L58;
    goto L61;
    L58:
    L61:
    if (accent !=0) goto L71;
    if (v9 ==0) goto L77;
    L71:
    goto L80;
    L77:
    L80:
    ui.FwmTheme.box(y, w, 14, ui.FwmTheme.RAISED, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    if (v9 ==0) goto L99;
    ui.FwmTheme.hover(ctx, x, y, w, 14);
    L99:
    v10 = font.plainSubstrByWidth(label, (w - 6));
    Objects.requireNonNull(font);
    if (accent ==0) goto L159;
    goto L162;
    L159:
    L162:
    font.text(network.chat.Component.literal(v10), (x + ((w - font.width(v10)) / 2)), ((y + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT);
    new int[4][0] = x;
    new int[4][1] = y;
    new int[4][2] = w;
    new int[4][3] = 14;
    return new int[4];
}

private static String body(ui.FwmTutorial$Step at) {
    if ("fwm.tutorial.keys".equals(at.textKey()) ==0) goto L43;
    new Object[3][0] = input.Keybinds.menuKeyName();
    new Object[3][1] = input.Keybinds.toggleKeyName();
    new Object[3][2] = input.Keybinds.messagesKeyName();
    return ui.Lang.str(at.textKey(), new Object[3]);
    L43:
    return ui.Lang.str(at.textKey(), new Object[0]);
}

private static void wrap(client.gui.Font font, String text, int width) {
    v3 = ?;
    font.getSplitter().splitLines(network.chat.Component.literal(text), Math.max(20, width), network.chat.Style.EMPTY, /*indy*/accept(v3));
    return v3;
}

public static void mouseClicked(double mouseX, double a1, int mouseY) {
    if (ui.FwmTutorial.active() ==0) goto L11;
    if (v4 ==0) goto L13;
    L11:
    return 0;
    L13:
    mouseX = ((mouseX - ((d)ui.FwmTutorial.colX)) / ((d)ui.FwmTutorial.colScale));
    mouseY = ((mouseY - ((d)ui.FwmTutorial.colY)) / ((d)ui.FwmTutorial.colScale));
    if (ui.FwmTutorial.hit(ui.FwmTutorial.skipBox, mouseX, mouseY) ==0) goto L53;
    ui.FwmTutorial.skip();
    return 1;
    L53:
    if (ui.FwmTutorial.hit(ui.FwmTutorial.nextBox, mouseX, mouseY) ==0) goto L69;
    ui.FwmTutorial.next();
    return 1;
    L69:
    if (ui.FwmTutorial.hit(ui.FwmTutorial.backBox, mouseX, mouseY) ==0) goto L85;
    ui.FwmTutorial.back();
    return 1;
    L85:
    if (ui.FwmTutorial.hit(ui.FwmTutorial.toggleBox, mouseX, mouseY) ==0) goto L124;
    v5 = ui.FwmTutorial.current();
    if (v5 == null) goto L122;
    if (v5.toggleKey() == null) goto L122;
    config.Features.toggle(v5.toggleKey());
    L122:
    return 1;
    L124:
    v5 = ui.FwmTutorial.pickerBoxes.iterator();
    L134:
    if (v5.hasNext() ==0) goto L229;
    v6 = (([I)v5.next());
    if (cmp(mouseX,((d)v6[0])) <0) goto L226;
    if (cmp(mouseX,((d)(v6[0] + v6[2]))) >=0) goto L226;
    if (cmp(mouseY,((d)v6[1])) <0) goto L226;
    if (cmp(mouseY,((d)(v6[1] + v6[3]))) >=0) goto L226;
    config.Features.toggle(((String)config.Features.SWITCHABLE.get(v6[4])));
    return 1;
    L226:
    goto L134;
    L229:
    return 0;
}

private static boolean hit(int[] box, double mouseX, double a2) {
    if (box == null) goto L52;
    if (cmp(mouseX,((d)box[0])) <0) goto L52;
    if (cmp(mouseX,((d)(box[0] + box[2]))) >=0) goto L52;
    if (cmp(v3,((d)box[1])) <0) goto L52;
    if (cmp(v3,((d)(box[1] + box[3]))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

// DECOMPILE ERROR in keyPressed: 'dict' object has no attribute 'op_type'
private static boolean lambda$wrap$0(List out, network.chat.FormattedText part, Boolean continued) {
    out.add(part.getString());
    return;
}

static void <clinit>() {
    ui.FwmTutorial.FIGURE = resources.Identifier.fromNamespaceAndPath("fwm", "textures/gui/hydravb.png");
    new ui.FwmTutorial$Step[30][0] = ui.FwmTutorial$Step.of("fwm.tab.overview", "fwm.tutorial.intro");
    new ui.FwmTutorial$Step[30][1] = ?;
    new ui.FwmTutorial$Step[30][2] = ui.FwmTutorial$Step.of("fwm.tab.overview", "fwm.tutorial.keys");
    new ui.FwmTutorial$Step[30][3] = ui.FwmTutorial$Step.of("fwm.tab.overview", "fwm.tutorial.overview");
    new ui.FwmTutorial$Step[30][4] = ui.FwmTutorial$Step.tab("fwm.tab.sellmacro", 0, "fwm.tutorial.sellmacro", "fwm.tab.sellmacro");
    new ui.FwmTutorial$Step[30][5] = ui.FwmTutorial$Step.tab("fwm.tab.sellmacro", 0, "fwm.tutorial.items", null);
    new ui.FwmTutorial$Step[30][6] = ui.FwmTutorial$Step.tab("fwm.tab.sellmacro", 1, "fwm.tutorial.stats", null);
    new ui.FwmTutorial$Step[30][7] = ui.FwmTutorial$Step.tab("fwm.tab.sellmacro", 2, "fwm.tutorial.tests", null);
    new ui.FwmTutorial$Step[30][8] = ui.FwmTutorial$Step.of("fwm.tab.market", "fwm.tutorial.market", "fwm.tab.market");
    new ui.FwmTutorial$Step[30][9] = ui.FwmTutorial$Step.of("fwm.tab.kommt", "fwm.tutorial.pricepings", "fwm.feature.priceping");
    new ui.FwmTutorial$Step[30][10] = ui.FwmTutorial$Step.tab("fwm.tab.spawner", 0, "fwm.tutorial.spawner", null);
    new ui.FwmTutorial$Step[30][11] = ui.FwmTutorial$Step.tab("fwm.tab.spawner", 1, "fwm.tutorial.spawnerhud", null);
    new ui.FwmTutorial$Step[30][12] = ui.FwmTutorial$Step.of("fwm.tab.spawnermacro", "fwm.tutorial.spawnermacro", "fwm.tab.spawnermacro");
    new ui.FwmTutorial$Step[30][13] = ui.FwmTutorial$Step.of("fwm.tab.money", "fwm.tutorial.money", "fwm.tab.money");
    new ui.FwmTutorial$Step[30][14] = ui.FwmTutorial$Step.of("fwm.tab.messages", "fwm.tutorial.messages", "fwm.tab.messages");
    new ui.FwmTutorial$Step[30][15] = ui.FwmTutorial$Step.of("fwm.tab.secondchat", "fwm.tutorial.secondchat", "fwm.tab.secondchat");
    new ui.FwmTutorial$Step[30][16] = ui.FwmTutorial$Step.of("fwm.tab.colorcodes", "fwm.tutorial.colorcodes", "fwm.tab.colorcodes");
    new ui.FwmTutorial$Step[30][17] = ui.FwmTutorial$Step.of("fwm.tab.members", "fwm.tutorial.members");
    new ui.FwmTutorial$Step[30][18] = ui.FwmTutorial$Step.of("fwm.tab.emotes", "fwm.tutorial.emotes", "fwm.tab.emotes");
    new ui.FwmTutorial$Step[30][19] = ui.FwmTutorial$Step.of("fwm.tab.autotpa", "fwm.tutorial.autotpa", "fwm.tab.autotpa");
    new ui.FwmTutorial$Step[30][20] = ui.FwmTutorial$Step.of("fwm.tab.toolguard", "fwm.tutorial.toolguard", "fwm.tab.toolguard");
    new ui.FwmTutorial$Step[30][21] = ui.FwmTutorial$Step.of("fwm.tab.rtp", "fwm.tutorial.rtp", "fwm.tab.rtp");
    new ui.FwmTutorial$Step[30][22] = ui.FwmTutorial$Step.of("fwm.tab.antileak", "fwm.tutorial.antileak", "fwm.feature.antileak");
    new ui.FwmTutorial$Step[30][23] = ui.FwmTutorial$Step.of("fwm.tab.textures", "fwm.tutorial.textures");
    new ui.FwmTutorial$Step[30][24] = ui.FwmTutorial$Step.tab("fwm.tab.auction", 0, "fwm.tutorial.auction", "fwm.tab.auction");
    new ui.FwmTutorial$Step[30][25] = ui.FwmTutorial$Step.tab("fwm.tab.auction", 1, "fwm.tutorial.giveaway", null);
    new ui.FwmTutorial$Step[30][26] = ui.FwmTutorial$Step.of("fwm.tab.extras", "fwm.tutorial.extras", "fwm.feature.ahprice");
    new ui.FwmTutorial$Step[30][27] = ui.FwmTutorial$Step.of("fwm.tab.settings", "fwm.tutorial.settings");
    new ui.FwmTutorial$Step[30][28] = ?;
    new ui.FwmTutorial$Step[30][29] = ui.FwmTutorial$Step.of("fwm.tab.overview", "fwm.tutorial.done");
    ui.FwmTutorial.STEPS = List.of(new ui.FwmTutorial$Step[30]);
    new String[12][0] = "macro";
    new String[12][1] = "toast";
    new String[12][2] = "envelope";
    new String[12][3] = "tpa";
    new String[12][4] = "tool";
    new String[12][5] = "secondchat";
    new String[12][6] = "priceping";
    new String[12][7] = "minimap";
    new String[12][8] = "money";
    new String[12][9] = "invvalue";
    new String[12][10] = "auction";
    new String[12][11] = "spawners";
    ui.FwmTutorial.HUD_CARDS = List.of(new String[12]);
    ui.FwmTutorial.step = m1;
    ui.FwmTutorial.colScale = 1;
    ui.FwmTutorial.pickerBoxes = ?;
    return;
}

