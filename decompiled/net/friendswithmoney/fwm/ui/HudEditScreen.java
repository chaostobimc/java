// class net/friendswithmoney/fwm/ui/HudEditScreen  (classfile vClassVersion(major=69, minor=0))
// extends net/minecraft/client/gui/screens/Screen
field FALLBACK Ljava/util/Map;;
field HANDLE I;
field parent Lnet/minecraft/client/gui/screens/Screen;;
field boxes Ljava/util/Map;;
field unscaled Ljava/util/Map;;
field dragging Ljava/lang/String;;
field resizing Ljava/lang/String;;
field grabX I;
field grabY I;
public void <init>(client.gui.screens.Screen parent) {
    this.boxes = ?;
    this.unscaled = ?;
    this.parent = parent;
    return;
}

protected Object init() {
    ui.FwmTutorial.undock();
    v1 = ui.FwmHud.bounds();
    v2 = ui.HudEditScreen.FALLBACK.keySet().iterator();
    L21:
    if (v2.hasNext() ==0) goto L373;
    v3 = ((String)v2.next());
    v4 = (([I)v1.get(v3));
    v5 = (([I)ui.HudEditScreen.FALLBACK.get(v3));
    v6 = ui.FwmHud.scaleOf(v3);
    if (v4 == null) goto L84;
    goto L95;
    L84:
    L95:
    v7 = Math.round((((f)v5[0]) * v6));
    if (v4 == null) goto L109;
    goto L120;
    L109:
    L120:
    v8 = Math.round((((f)v5[1]) * v6));
    if ("secondchat".equals(v3) ==0) goto L186;
    v9 = ui.FwmHud.sampleSize(this.font, 1);
    this.unscaled.put(v3, v9);
    v7 = Math.round((((f)v9[0]) * v6));
    v8 = Math.round((((f)v9[1]) * v6));
    v4 = null;
    goto L234;
    L186:
    new int[2][0] = Math.max(8, Math.round((((f)v7) / v6)));
    new int[2][1] = Math.max(8, Math.round((((f)v8) / v6)));
    this.unscaled.put(v3, new int[2]);
    L234:
    v9 = ui.FwmHud.spot(v3);
    if (v4 == null) goto L252;
    goto L259;
    L252:
    L259:
    v10 = this.defaultX(v3, v7);
    if (v4 == null) goto L273;
    goto L280;
    L273:
    L280:
    v11 = this.defaultY(v3, v8);
    if (v9.moved() ==0) goto L336;
    v10 = Math.round((v9.x * ((f)Math.max(0, (this.width - v7)))));
    v11 = Math.round((v9.y * ((f)Math.max(0, (this.height - v8)))));
    L336:
    new int[4][0] = v10;
    new int[4][1] = v11;
    new int[4][2] = v7;
    new int[4][3] = v8;
    this.boxes.put(v3, new int[4]);
    goto L21;
    L373:
    return;
}

// DECOMPILE ERROR in defaultX: 'dict' object has no attribute 'op_type'
// DECOMPILE ERROR in defaultY: 'dict' object has no attribute 'op_type'
public void extractRenderState(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    this.extractRenderState(ctx, mouseX, mouseY, delta);
    v5 = this.boxes.entrySet().iterator();
    L25:
    if (v5.hasNext() ==0) goto L485;
    v6 = ((Map$Entry)v5.next());
    v7 = (([I)v6.getValue());
    if (mouseX < v7[0]) goto L105;
    if (mouseX >= (v7[0] + v7[2])) goto L105;
    if (mouseY < v7[1]) goto L105;
    if (mouseY >= (v7[1] + v7[3])) goto L105;
    goto L106;
    L105:
    L106:
    v8 = 0;
    v9 = ((String)v6.getKey()).equals(this.dragging);
    v10 = "secondchat".equals(v6.getKey());
    if (v9 ==0) goto L169;
    goto L172;
    L169:
    L172:
    if (v9 !=0) goto L182;
    if (v8 ==0) goto L188;
    L182:
    goto L191;
    L188:
    L191:
    ui.FwmTheme.box(v7[1], v7[2], v7[3], ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v11 = ui.Lang.of('fwm.hudedit.' + ((String)v6.getKey()), new Object[0]);
    if (v10 ==0) goto L296;
    ui.FwmHud.drawSample(ctx, this.font, v7[0], v7[1], ui.FwmHud.scaleOf(((String)v6.getKey())));
    if (v9 !=0) goto L281;
    if (v8 ==0) goto L287;
    L281:
    goto L290;
    L287:
    L290:
    this.font.text(v11, v7[0], (v7[1] - 10), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    goto L376;
    L296:
    Objects.requireNonNull(this.font);
    if (v9 !=0) goto L364;
    if (v8 ==0) goto L370;
    L364:
    goto L373;
    L370:
    L373:
    this.font.text(v11, (v7[0] + Math.max(3, ((v7[2] - this.font.width(v11)) / 2))), (v7[1] + Math.max(3, ((v7[3] - 9) / 2))), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT);
    L376:
    if (ui.HudEditScreen.resizable(((String)v6.getKey())) !=0) goto L395;
    goto L25;
    L395:
    v12 = ui.HudEditScreen.overHandle(((String)v6.getKey()), v7, ((d)mouseX), ((d)mouseY));
    v13 = ui.HudEditScreen.handleX(((String)v6.getKey()), v7);
    if (v12 !=0) goto L474;
    if (((String)v6.getKey()).equals(this.resizing) ==0) goto L478;
    L474:
    goto L479;
    L478:
    L479:
    ctx.grip(v13, ((v7[1] + v7[3]) - 8), 1, 0);
    goto L25;
    L485:
    if (ui.FwmTutorial.active() !=0) goto L635;
    v5 = ui.Lang.of("fwm.hudedit.hint", new Object[0]);
    ctx.text(this.font, v5, ((this.width - this.font.width(v5)) / 2), (this.height - 35), ui.FwmTheme.TEXT);
    v6 = ui.Lang.of("fwm.hudedit.hint2", new Object[0]);
    ctx.text(this.font, v6, ((this.width - this.font.width(v6)) / 2), (this.height - 24), ui.FwmTheme.TEXT_DIM);
    v7 = ui.Lang.of("fwm.hudedit.reset", new Object[0]);
    ctx.text(this.font, v7, ((this.width - this.font.width(v7)) / 2), (this.height - 13), ui.FwmTheme.TEXT_DIM);
    L635:
    ui.FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
    return;
}

private void grip(client.gui.GuiGraphicsExtractor ctx, int x, int y, boolean hot) {
    if (hot ==0) goto L22;
    goto L25;
    L22:
    L25:
    x.fill(y, (x + 8), (y + 8), ui.FwmTheme.ACCENT, ui.FwmTheme.ACCENT_DIM);
    if (hot ==0) goto L47;
    goto L50;
    L47:
    L50:
    ui.FwmTheme.box(x, y, 8, 8, 0, ui.FwmTheme.TEXT, ui.FwmTheme.ACCENT);
    return;
}

private static boolean resizable(String id) {
    if ("macro".equals(id) !=0) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

private static boolean growsLeft(String id) {
    return "secondchat".equals(id);
}

private static boolean handleX(String id, int[] box) {
    if (ui.HudEditScreen.growsLeft(id) ==0) goto L13;
    goto L23;
    L13:
    L23:
    return ((box[0] + box[2]) - 8);
}

private static int overHandle(String id, int[] box, double mouseX, double a3) {
    v6 = ui.HudEditScreen.handleX(id, box);
    if (cmp(mouseX,((d)v6)) <0) goto L61;
    if (cmp(mouseX,((d)(v6 + 8))) >=0) goto L61;
    if (cmp(v4,((d)((box[1] + box[3]) - 8))) <0) goto L61;
    if (cmp(v4,((d)(box[1] + box[3]))) >=0) goto L61;
    goto L62;
    L61:
    L62:
    return 0;
}

public double mouseClicked(client.input.MouseButtonEvent click, boolean doubled) {
    if (ui.FwmTutorial.mouseClicked(click.x(), click.y(), util.GLFW.button(click.button())) ==0) goto L23;
    return 1;
    L23:
    if (ui.FwmTutorial.consumes(click.x(), this.width) ==0) goto L39;
    return 1;
    L39:
    if (util.GLFW.button(click.button()) ==0) goto L56;
    return this.mouseClicked(click, doubled);
    L56:
    v3 = null;
    v4 = null;
    v5 = this.boxes.entrySet().iterator();
    L77:
    if (v5.hasNext() ==0) goto L241;
    v6 = ((Map$Entry)v5.next());
    v7 = (([I)v6.getValue());
    if (ui.HudEditScreen.resizable(((String)v6.getKey())) ==0) goto L165;
    if (ui.HudEditScreen.overHandle(((String)v6.getKey()), v7, click.x(), click.y()) ==0) goto L165;
    v4 = ((String)v6.getKey());
    L165:
    if (cmp(click.x(),((d)v7[0])) <0) goto L238;
    if (cmp(click.x(),((d)(v7[0] + v7[2]))) >=0) goto L238;
    if (cmp(click.y(),((d)v7[1])) <0) goto L238;
    if (cmp(click.y(),((d)(v7[1] + v7[3]))) >=0) goto L238;
    v3 = ((String)v6.getKey());
    L238:
    goto L77;
    L241:
    if (v4 == null) goto L323;
    v5 = (([I)this.boxes.get(v4));
    this.resizing = v4;
    if (ui.HudEditScreen.growsLeft(v4) ==0) goto L289;
    goto L298;
    L289:
    L298:
    ((i)click.x()).grabX = (v5[0] - (v5[0] + v5[2]));
    this.grabY = (((i)click.y()) - (v5[1] + v5[3]));
    return 1;
    L323:
    if (v3 != null) goto L334;
    return this.mouseClicked(click, doubled);
    L334:
    v5 = (([I)this.boxes.get(v3));
    this.dragging = v3;
    this.grabX = (((i)click.x()) - v5[0]);
    this.grabY = (((i)click.y()) - v5[1]);
    return 1;
}

public int mouseDragged(client.input.MouseButtonEvent click, double deltaX, double a3) {
    if (this.resizing == null) goto L21;
    this.resize(click.x(), click.y());
    return 1;
    L21:
    if (this.dragging != null) goto L37;
    return this.mouseDragged(click, deltaX, v4);
    L37:
    v6 = (([I)this.boxes.get(this.dragging));
    v6[0] = ui.HudEditScreen.clamp((((i)click.x()) - this.grabX), (this.width - v6[2]));
    v6[1] = ui.HudEditScreen.clamp((((i)click.y()) - this.grabY), (this.height - v6[3]));
    return 1;
}

private int resize(double mouseX, double a2) {
    v5 = (([I)this.boxes.get(this.resizing));
    v6 = (([I)this.unscaled.get(this.resizing));
    v7 = ui.FwmHud.spot(this.resizing);
    if ("secondchat".equals(this.resizing) ==0) goto L70;
    this.resizeChat(v5, v6, v7, mouseX, v3);
    return;
    L70:
    v7.resize((((f)((mouseX - ((d)this.grabX)) - ((d)v5[0]))) / ((f)v6[0])));
    v5[2] = Math.round((((f)v6[0]) * v7.scale()));
    v5[3] = Math.round((((f)v6[1]) * v7.scale()));
    return;
}

private int[] resizeChat(int[] box, int[] size, config.FwmConfig$HudSpot spot, double mouseX, double a5) {
    spot.resize((((f)((v6 - ((d)this.grabY)) - ((d)box[1]))) / ((f)Math.max(1, size[1]))));
    v8 = (box[0] + box[2]);
    v9 = Math.round(((((f)v8) - ((f)(mouseX - ((d)this.grabX)))) / spot.scale()));
    config.FwmConfig.INSTANCE.secondChatWidth = Math.max(100, Math.min(420, v9));
    this.unscaled.put("secondchat", ui.FwmHud.sampleSize(this.font, 1));
    v10 = ui.FwmHud.sampleSize(this.font, spot.scale());
    box[0] = (v8 - v10[0]);
    box[2] = v10[0];
    box[3] = v10[1];
    return;
}

public boolean mouseReleased(client.input.MouseButtonEvent click) {
    if (this.resizing == null) goto L15;
    this.resizing = null;
    config.ConfigManager.save();
    L15:
    if (this.dragging == null) goto L38;
    this.store(this.dragging);
    this.dragging = null;
    config.ConfigManager.save();
    L38:
    return this.mouseReleased(click);
}

private void store(String id) {
    v2 = (([I)this.boxes.get(id));
    if ((this.width - v2[2]) >0) goto L29;
    goto L43;
    L29:
    L43:
    v3 = (((f)v2[0]) / ((f)(this.width - v2[2])));
    if ((this.height - v2[3]) >0) goto L59;
    goto L73;
    L59:
    L73:
    v4 = (((f)v2[1]) / ((f)(this.height - v2[3])));
    ui.FwmHud.spot(id).set(v3, v4);
    return;
}

private static int clamp(int value, int max) {
    return Math.max(0, Math.min(Math.max(0, max), value));
}

public boolean keyPressed(client.input.KeyEvent input) {
    if (input.key() == 21) goto L21;
    if (ui.FwmTutorial.keyPressed(input.key()) ==0) goto L21;
    return 1;
    L21:
    if (input.key() != 21) goto L83;
    v2 = this.boxes.keySet().iterator();
    L45:
    if (v2.hasNext() ==0) goto L74;
    v3 = ((String)v2.next());
    ui.FwmHud.spot(v3).reset();
    goto L45;
    L74:
    config.ConfigManager.save();
    this.init();
    return 1;
    L83:
    return this.keyPressed(input);
}

public void onClose() {
    this.minecraft.gui.setScreen(this.parent);
    return;
}

public boolean isPauseScreen() {
    return 0;
}

static Object <clinit>() {
    ui.HudEditScreen.FALLBACK = ?;
    new int[2][0] = 150;
    new int[2][1] = 40;
    ui.HudEditScreen.FALLBACK.put("macro", new int[2]);
    new int[2][0] = 150;
    new int[2][1] = 26;
    ui.HudEditScreen.FALLBACK.put("toast", new int[2]);
    new int[2][0] = 40;
    new int[2][1] = 16;
    ui.HudEditScreen.FALLBACK.put("envelope", new int[2]);
    new int[2][0] = 128;
    new int[2][1] = 41;
    ui.HudEditScreen.FALLBACK.put("tpa", new int[2]);
    new int[2][0] = 200;
    new int[2][1] = 33;
    ui.HudEditScreen.FALLBACK.put("tool", new int[2]);
    new int[2][0] = 240;
    new int[2][1] = 50;
    ui.HudEditScreen.FALLBACK.put("secondchat", new int[2]);
    new int[2][0] = 128;
    new int[2][1] = 150;
    ui.HudEditScreen.FALLBACK.put("minimap", new int[2]);
    new int[2][0] = 170;
    new int[2][1] = 41;
    ui.HudEditScreen.FALLBACK.put("priceping", new int[2]);
    new int[2][0] = 150;
    new int[2][1] = 30;
    ui.HudEditScreen.FALLBACK.put("money", new int[2]);
    new int[2][0] = 90;
    new int[2][1] = 19;
    ui.HudEditScreen.FALLBACK.put("invvalue", new int[2]);
    new int[2][0] = 160;
    new int[2][1] = 41;
    ui.HudEditScreen.FALLBACK.put("auction", new int[2]);
    new int[2][0] = 150;
    new int[2][1] = 100;
    ui.HudEditScreen.FALLBACK.put("spawners", new int[2]);
    return;
}

