// class net/friendswithmoney/fwm/ui/panel/RtpPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field STRIP_H I;
field STATUS_H I;
field BTN_H I;
field BTN_GAP I;
field GAP I;
field MIN_BPP D;
field ZOOM_STEP D;
field GRID_MIN_PX I;
field GRID_FULL_PX I;
field BUTTONS I;
field LABEL_MIN_PX I;
field PICK_PX I;
field DIMS [Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
field POINT_COLOR I;
field DOT_FULL_BPP D;
field GRID_COLOR I;
field GRID_FAINT I;
field WORLD_COLOR I;
field OWN_AREA_COLOR I;
field PLAYER_COLOR I;
field PLAYER_EDGE I;
field PICKED_COLOR I;
field dim Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
field centerX D;
field centerZ D;
field bpp D;
field moved Z;
field fittedToPoints Z;
field MAP_TEXTURE Lnet/minecraft/resources/Identifier;;
field texture Lnet/minecraft/client/renderer/texture/DynamicTexture;;
field texW I;
field texH I;
field builtW I;
field builtH I;
field builtGui I;
field dirty Z;
field lastCount I;
field lastMouseX D;
field lastMouseY D;
field dragging Z;
field picked [I;
field copied Z;
public void <init>() {
    this.dim = net.RtpPoints$Dim.OVERWORLD;
    this.bpp = 1024.0;
    this.dirty = 1;
    this.lastCount = m1;
    return;
}

private static void worldSize(net.RtpPoints$Dim dim) {
    // switch (ui.panel.RtpPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim[dim.ordinal()]) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=28), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=38), Operand(op_type=<OperandTypes.BRANCH: 40>, value=43), Operand(op_type=<OperandTypes.BRANCH: 40>, value=48)]
    L36:
    throw ?;
    L46:
    goto L58;
    L51:
    goto L58;
    L56:
    L58:
    return 80000;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.rtp", new Object[0]);
}

public boolean settingsCog() {
    return 1;
}

protected void onResize() {
    net.RtpPoints.open(this.dim);
    if (this.moved !=0) goto L18;
    this.fitWorld();
    L18:
    this.dirty = 1;
    return;
}

private int mapY() {
    return ((this.y + 14) + 3);
}

private int mapH() {
    return Math.max(16, ((((((this.y + this.height) - 16) - 3) - 12) - 3) - this.mapY()));
}

private int mapW() {
    return this.width;
}

private int statusY() {
    return ((((this.y + this.height) - 16) - 3) - 12);
}

private int buttonY() {
    return ((this.y + this.height) - 16);
}

private int buttonW() {
    return ((this.width - 8) / 3);
}

private int buttonX(int index) {
    return (this.x + (index * (this.buttonW() + 4)));
}

private int slotW() {
    return (this.width / ui.panel.RtpPanel.DIMS.length);
}

private int slotX(int slot) {
    return (this.x + (slot * this.slotW()));
}

private double bpp() {
    return this.bpp;
}

private static boolean ownPosOn() {
    return config.FwmConfig.INSTANCE.rtpShowOwnPos;
}

private static client.Minecraft ownPlayer() {
    if (ui.panel.RtpPanel.ownPosOn() ==0) goto L15;
    goto L16;
    L15:
    L16:
    return null;
}

private static net.RtpPoints$Dim playerDim() {
    v0 = client.Minecraft.getInstance().level;
    if (v0 != null) goto L13;
    return null;
    L13:
    return net.RtpPoints$Dim.of(v0);
}

private static long areaStart(double block, int a1) {
    v3 = (v2 / 2);
    return ((Math.floorDiv((((l)Math.floor(block)) + ((l)v3)), ((l)v2)) * ((l)v2)) - ((l)v3));
}

private long worldBounds() {
    v1 = net.RtpPoints.index(this.dim);
    v2 = net.RtpPoints.area(this.dim);
    if (v1 != null) goto L70;
    v3 = ((l)(((-ui.panel.RtpPanel.worldSize(this.dim)) / 2) - (v2 / 2)));
    v5 = (v3 + ((l)ui.panel.RtpPanel.worldSize(this.dim)));
    new long[4][0] = v3;
    new long[4][1] = v3;
    new long[4][2] = v5;
    new long[4][3] = v5;
    return new long[4];
    L70:
    new long[4][0] = ui.panel.RtpPanel.areaStart(((d)v1.minX), v2);
    new long[4][1] = ui.panel.RtpPanel.areaStart(((d)v1.minZ), v2);
    new long[4][2] = (ui.panel.RtpPanel.areaStart(((d)v1.maxX), v2) + ((l)v2));
    new long[4][3] = (ui.panel.RtpPanel.areaStart(((d)v1.maxZ), v2) + ((l)v2));
    return new long[4];
}

private double zoomToFit(double blocksW, double a2) {
    return Math.max(2.0, Math.max((blocksW / ((d)Math.max(1, (this.mapW() - 2)))), (v3 / ((d)Math.max(1, (this.mapH() - 2))))));
}

private net.RtpPoints$Index fitWorld() {
    v1 = this.worldBounds();
    this.centerX = (((d)(v1[0] + v1[2])) / 2.0);
    this.centerZ = (((d)(v1[1] + v1[3])) / 2.0);
    this.bpp = this.zoomToFit(((d)(v1[2] - v1[0])), ((d)(v1[3] - v1[1])));
    if (net.RtpPoints.index(this.dim) == null) goto L76;
    goto L77;
    L76:
    L77:
    1.fittedToPoints = 0;
    return;
}

private double maxBpp() {
    v1 = this.worldBounds();
    return this.zoomToFit(((d)(v1[2] - v1[0])), ((d)(v1[3] - v1[1])));
}

private double zoomToMe() {
    v1 = ui.panel.RtpPanel.ownPlayer();
    if (v1 != null) goto L9;
    return;
    L9:
    v2 = ui.panel.RtpPanel.playerDim();
    if (v2 == null) goto L42;
    if (v2 == this.dim) goto L42;
    this.dim = v2;
    this.picked = null;
    net.RtpPoints.open(this.dim);
    L42:
    v3 = net.RtpPoints.area(this.dim);
    this.centerX = (((d)ui.panel.RtpPanel.areaStart(v1.getX(), v3)) + (((d)v3) / 2.0));
    this.centerZ = (((d)ui.panel.RtpPanel.areaStart(v1.getZ(), v3)) + (((d)v3) / 2.0));
    this.bpp = Math.min(this.maxBpp(), this.zoomToFit(((d)v3), ((d)v3)));
    this.moved = 1;
    this.dirty = 1;
    return;
}

private double minX() {
    return (this.centerX - ((((d)this.mapW()) / 2.0) * this.bpp()));
}

private double minZ() {
    return (this.centerZ - ((((d)this.mapH()) / 2.0) * this.bpp()));
}

private static int guiScale() {
    return Math.max(1, client.Minecraft.getInstance().getWindow().getGuiScale());
}

private int dotSize(int gui) {
    return Math.max(1, Math.min(gui, ((i)Math.round(((((d)gui) * 48.0) / this.bpp())))));
}

private static blaze3d.platform.NativeImage canvas(int w, int h) {
    v2 = client.Minecraft.getInstance();
    if (ui.panel.RtpPanel.texture == null) goto L24;
    if (ui.panel.RtpPanel.texW != w) goto L24;
    if (ui.panel.RtpPanel.texH == h) goto L86;
    L24:
    if (ui.panel.RtpPanel.texture == null) goto L40;
    v2.getTextureManager().release(ui.panel.RtpPanel.MAP_TEXTURE);
    L40:
    ui.panel.RtpPanel.texture = ?;
    v2.getTextureManager().register(ui.panel.RtpPanel.MAP_TEXTURE, ui.panel.RtpPanel.texture);
    ui.panel.RtpPanel.texW = w;
    ui.panel.RtpPanel.texH = h;
    L86:
    return ui.panel.RtpPanel.texture.getPixels();
}

private int rebuild() {
    v1 = ui.panel.RtpPanel.guiScale();
    v2 = (this.mapW() * v1);
    v3 = (this.mapH() * v1);
    v4 = ui.panel.RtpPanel.canvas(v2, v3);
    v4.fillRect(0, 0, v2, v3, 0);
    v5 = (this.bpp() / ((d)v1));
    v7 = ((l)(4294967296.0 / v5));
    v9 = ((i)Math.floor(this.minX()));
    v10 = ((i)Math.floor(this.minZ()));
    v11 = (v9 + ((i)Math.ceil((((d)v2) * v5))));
    v12 = (v10 + ((i)Math.ceil((((d)v3) * v5))));
    v13 = this.dotSize(v1);
    v14 = net.RtpPoints.points(this.dim);
    v15 = net.RtpPoints.index(this.dim);
    if (v15 != null) goto L176;
    v16 = 0;
    L134:
    if ((v16 + 2) >= v14.length) goto L173;
    ui.panel.RtpPanel.mark(v4, v14, v16, v9, v10, v11, v12, v7, v2, v3, v13);
    v16 = v16 + 3;
    goto L134;
    L173:
    goto L379;
    L176:
    v16 = Math.max(0, ((v9 >> v15.shift) - v15.minCellX));
    v17 = Math.min((v15.cellsW - 1), ((v11 >> v15.shift) - v15.minCellX));
    v18 = Math.max(0, ((v10 >> v15.shift) - v15.minCellZ));
    v19 = Math.min((v15.cellsH - 1), ((v12 >> v15.shift) - v15.minCellZ));
    v20 = v18;
    L272:
    if (v20 > v19) goto L379;
    v21 = (v20 * v15.cellsW);
    v22 = v16;
    L293:
    if (v22 > v17) goto L373;
    v23 = (v21 + v22);
    v24 = v15.start[v23];
    L317:
    if (v24 >= v15.start[(v23 + 1)]) goto L367;
    ui.panel.RtpPanel.mark(v4, v14, v15.order[v24], v9, v10, v11, v12, v7, v2, v3, v13);
    v24 = v24 + 1;
    goto L317;
    L367:
    v22 = v22 + 1;
    goto L293;
    L373:
    v20 = v20 + 1;
    goto L272;
    L379:
    ui.panel.RtpPanel.texture.upload();
    this.builtW = this.mapW();
    this.builtH = this.mapH();
    this.builtGui = v1;
    this.dirty = 0;
    this.lastCount = net.RtpPoints.count(this.dim);
    return;
}

private static void mark(blaze3d.platform.NativeImage image, int[] points, int i, int originX, int originZ, int endX, int endZ, long scale, int a8, int w, int h) {
    v12 = points[i];
    if (v12 < originX) goto L18;
    if (v12 < endX) goto L19;
    L18:
    return;
    L19:
    v13 = points[(i + 1)];
    if (v13 < originZ) goto L40;
    if (v13 < endZ) goto L41;
    L40:
    return;
    L41:
    v14 = ((i)((((l)(v12 - originX)) * scale) >>> 32));
    v15 = ((i)((((l)(v13 - originZ)) * scale) >>> 32));
    if (v14 >= w) goto L84;
    if (v15 < h) goto L85;
    L84:
    return;
    L85:
    v16 = Math.min(w, (v14 + v11));
    v17 = Math.min(h, (v15 + v11));
    v18 = v15;
    L113:
    if (v18 >= v17) goto L154;
    v19 = v14;
    L124:
    if (v19 >= v16) goto L148;
    image.setPixel(v19, v18, -8395864);
    v19 = v19 + 1;
    goto L124;
    L148:
    v18 = v18 + 1;
    goto L113;
    L154:
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.renderStrip(ctx, font, mouseX, mouseY);
    if (this.moved !=0) goto L42;
    if (this.fittedToPoints !=0) goto L42;
    if (net.RtpPoints.index(this.dim) == null) goto L42;
    this.fitWorld();
    this.dirty = 1;
    L42:
    if (this.dirty !=0) goto L101;
    if (this.builtW != this.mapW()) goto L101;
    if (this.builtH != this.mapH()) goto L101;
    if (this.builtGui != ui.panel.RtpPanel.guiScale()) goto L101;
    if (ui.panel.RtpPanel.texture == null) goto L101;
    if (this.lastCount == net.RtpPoints.count(this.dim)) goto L105;
    L101:
    this.rebuild();
    L105:
    this.renderMap(ctx, font);
    this.renderStatus(ctx, font, mouseX, mouseY);
    this.renderButtons(ctx, font, mouseX, mouseY);
    return;
}

private void renderStrip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = 0;
    L3:
    if (v5 >= ui.panel.RtpPanel.DIMS.length) goto L202;
    v6 = this.slotX(v5);
    v7 = this.slotW();
    if (ui.panel.RtpPanel.DIMS[v5] != this.dim) goto L43;
    goto L44;
    L43:
    L44:
    v8 = 0;
    if (v8 !=0) goto L91;
    if (mouseX < v6) goto L91;
    if (mouseX >= (v6 + v7)) goto L91;
    if (mouseY < this.y) goto L91;
    if (mouseY >= (this.y + 14)) goto L91;
    goto L92;
    L91:
    L92:
    v9 = 0;
    ui.FwmTheme.sidebarButton(ctx, v6, this.y, v7, 14, v8, v9, 0);
    v10 = network.chat.Component.literal(font.plainSubstrByWidth(ui.Lang.str('fwm.rtp.dim.' + ui.panel.RtpPanel.DIMS[v5].key(), new Object[0]), (v7 - 4)));
    Objects.requireNonNull(font);
    ctx.text(font, v10, (v6 + ((v7 - font.width(v10)) / 2)), ((this.y + ((14 - 9) / 2)) + 1), ui.FwmTheme.sidebarTextColor(v8, v9, 0), 0);
    v5 = v5 + 1;
    goto L3;
    L202:
    return;
}

private void renderMap(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = this.x;
    v4 = this.mapY();
    v5 = this.mapW();
    v6 = this.mapH();
    ui.FwmTheme.box(ctx, v3, v4, v5, v6, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    ctx.enableScissor((v3 + 1), (v4 + 1), ((v3 + v5) - 1), ((v4 + v6) - 1));
    this.renderGrid(ctx, font, v3, v4, v5, v6);
    this.renderWorldEdge(ctx, v3, v4);
    this.renderOwnArea(ctx, v3, v4);
    ctx.blit(client.renderer.RenderPipelines.GUI_TEXTURED, ui.panel.RtpPanel.MAP_TEXTURE, v3, v4, 0, 0, v5, v6, ui.panel.RtpPanel.texW, ui.panel.RtpPanel.texH, ui.panel.RtpPanel.texW, ui.panel.RtpPanel.texH);
    this.renderPlayer(ctx, v3, v4, v5, v6);
    this.renderPicked(ctx, v3, v4, v5, v6);
    ctx.disableScissor();
    if (net.RtpPoints.count(this.dim) !=0) goto L226;
    if (net.RtpPoints.state(this.dim) != net.RtpPoints$State.ERROR) goto L181;
    goto L184;
    L181:
    L184:
    v7 = ui.Lang.of("fwm.rtp.loading", new Object[0]);
    ctx.text(font, v7, (v3 + ((v5 - font.width(v7)) / 2)), ((v4 + (v6 / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    L226:
    return;
}

private void renderGrid(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mapX, int mapY, int w, int h) {
    v7 = net.RtpPoints.area(this.dim);
    v8 = (v7 / 2);
    v9 = this.bpp();
    v11 = (((d)v7) / v9);
    if (cmp(v11,4.0) >=0) goto L39;
    return;
    L39:
    if (cmp(v11,10.0) >=0) goto L54;
    goto L57;
    L54:
    L57:
    v13 = 872415231;
    v14 = this.minX();
    v16 = this.minZ();
    v18 = this.worldBounds();
    v19 = Math.floorDiv((v18[0] + ((l)v8)), ((l)v7));
    v21 = Math.floorDiv((v18[1] + ((l)v8)), ((l)v7));
    v23 = Math.floorDiv((v18[2] + ((l)v8)), ((l)v7));
    v25 = Math.floorDiv((v18[3] + ((l)v8)), ((l)v7));
    v27 = Math.max(0, ((i)Math.round(((((d)v18[1]) - v16) / v9))));
    v28 = Math.min(h, (((i)Math.round(((((d)v18[3]) - v16) / v9))) + 1));
    v29 = Math.max(0, ((i)Math.round(((((d)v18[0]) - v14) / v9))));
    v30 = Math.min(w, (((i)Math.round(((((d)v18[2]) - v14) / v9))) + 1));
    v31 = Math.max(v19, ((l)Math.floor(((v14 + ((d)v8)) / ((d)v7)))));
    v33 = Math.max(v21, ((l)Math.floor(((v16 + ((d)v8)) / ((d)v7)))));
    v35 = v31;
    L277:
    if (cmp(v35,v23) >0) goto L386;
    if (cmp(((d)((v35 * ((l)v7)) - ((l)v8))),(v14 + (((d)w) * v9))) >=0) goto L386;
    v37 = ((i)Math.round(((((((d)v35) * ((d)v7)) - ((d)v8)) - v14) / v9)));
    if (v37 <0) goto L377;
    if (v37 >= w) goto L377;
    if (v27 >= v28) goto L377;
    ctx.fill((mapX + v37), (mapY + v27), ((mapX + v37) + 1), (mapY + v28), v13);
    L377:
    v35 = (v35 + 1);
    goto L277;
    L386:
    v35 = v33;
    L390:
    if (cmp(v35,v25) >0) goto L499;
    if (cmp(((d)((v35 * ((l)v7)) - ((l)v8))),(v16 + (((d)h) * v9))) >=0) goto L499;
    v37 = ((i)Math.round(((((((d)v35) * ((d)v7)) - ((d)v8)) - v16) / v9)));
    if (v37 <0) goto L490;
    if (v37 >= h) goto L490;
    if (v29 >= v30) goto L490;
    ctx.fill((mapX + v29), (mapY + v37), (mapX + v30), ((mapY + v37) + 1), v13);
    L490:
    v35 = (v35 + 1);
    goto L390;
    L499:
    if (cmp(v11,44.0) >=0) goto L509;
    return;
    L509:
    v35 = Math.max(v19, (v31 - 1));
    v37 = Math.max(v21, (v33 - 1));
    v39 = v35;
    L535:
    if (cmp(v39,v23) >=0) goto L810;
    if (cmp(((d)((v39 * ((l)v7)) - ((l)v8))),(v14 + (((d)w) * v9))) >=0) goto L810;
    v41 = v37;
    L571:
    if (cmp(v41,v25) >=0) goto L801;
    if (cmp(((d)((v41 * ((l)v7)) - ((l)v8))),(v16 + (((d)h) * v9))) >=0) goto L801;
    v43 = ((i)Math.round(((((((d)v39) * ((d)v7)) - ((d)v8)) - v14) / v9)));
    v44 = ((i)Math.round(((((((d)v41) * ((d)v7)) - ((d)v8)) - v16) / v9)));
    v45 = ((i)Math.round(((((((d)(v39 + 1)) * ((d)v7)) - ((d)v8)) - v14) / v9)));
    v46 = ((i)Math.round(((((((d)(v41 + 1)) * ((d)v7)) - ((d)v8)) - v16) / v9)));
    v47 = Math.max(2, (v43 + 2));
    v48 = Math.max(2, (v44 + 2));
    if (v47 >= (w - 10)) goto L792;
    if (v48 >= (h - 8)) goto L792;
    if ((v45 - v47) < 30) goto L792;
    if ((v46 - v48) >= 10) goto L762;
    goto L792;
    L762:
    ctx.text(font, network.chat.Component.literal(v39 + ' / ' + v41), (mapX + v47), (mapY + v48), 1442840575, 0);
    L792:
    v41 = (v41 + 1);
    goto L571;
    L801:
    v39 = (v39 + 1);
    goto L535;
    L810:
    return;
}

private void renderWorldEdge(client.gui.GuiGraphicsExtractor ctx, int mapX, int mapY) {
    v4 = this.worldBounds();
    this.outline(ctx, mapX, mapY, v4[0], v4[1], v4[2], v4[3], -2136411964);
    return;
}

private void renderOwnArea(client.gui.GuiGraphicsExtractor ctx, int mapX, int mapY) {
    v4 = ui.panel.RtpPanel.ownPlayer();
    if (v4 == null) goto L20;
    if (ui.panel.RtpPanel.playerDim() == this.dim) goto L21;
    L20:
    return;
    L21:
    v5 = net.RtpPoints.area(this.dim);
    if (cmp((((d)v5) / this.bpp()),4.0) >=0) goto L46;
    return;
    L46:
    v6 = ui.panel.RtpPanel.areaStart(v4.getX(), v5);
    v8 = ui.panel.RtpPanel.areaStart(v4.getZ(), v5);
    this.outline(ctx, mapX, mapY, v6, v8, (v6 + ((l)v5)), (v8 + ((l)v5)), -2131376830);
    return;
}

private void outline(client.gui.GuiGraphicsExtractor ctx, int mapX, int mapY, long bx0, long a5, long bz0, long a7, int bx1) {
    v13 = this.bpp();
    v15 = (mapX + ((i)Math.round(((((d)bx0) - this.minX()) / v13))));
    v16 = (mapY + ((i)Math.round(((((d)bz0) - this.minZ()) / v13))));
    v17 = (mapX + ((i)Math.round(((((d)bx1) - this.minX()) / v13))));
    v18 = (mapY + ((i)Math.round(((((d)v10) - this.minZ()) / v13))));
    ctx.fill(v15, v16, (v17 + 1), (v16 + 1), v12);
    ctx.fill(v15, v18, (v17 + 1), (v18 + 1), v12);
    ctx.fill(v15, v16, (v15 + 1), (v18 + 1), v12);
    ctx.fill(v17, v16, (v17 + 1), (v18 + 1), v12);
    return;
}

private void renderPlayer(client.gui.GuiGraphicsExtractor ctx, int mapX, int mapY, int w, int h) {
    v6 = ui.panel.RtpPanel.ownPlayer();
    if (v6 == null) goto L20;
    if (ui.panel.RtpPanel.playerDim() == this.dim) goto L21;
    L20:
    return;
    L21:
    v7 = ((i)((v6.getX() - this.minX()) / this.bpp()));
    v8 = ((i)((v6.getZ() - this.minZ()) / this.bpp()));
    if (v7 <0) goto L81;
    if (v7 >= w) goto L81;
    if (v8 <0) goto L81;
    if (v8 < h) goto L82;
    L81:
    return;
    L82:
    v9 = (mapX + v7);
    v10 = (mapY + v8);
    ctx.fill((v9 - 2), (v10 - 3), (v9 + 3), (v10 + 4), -870710011);
    ctx.fill((v9 - 3), (v10 - 2), (v9 + 4), (v10 + 3), -870710011);
    ctx.fill((v9 - 1), (v10 - 2), (v9 + 2), (v10 + 3), -50373);
    ctx.fill((v9 - 2), (v10 - 1), (v9 + 3), (v10 + 2), -50373);
    return;
}

private void renderPicked(client.gui.GuiGraphicsExtractor ctx, int mapX, int mapY, int w, int h) {
    if (this.picked != null) goto L8;
    return;
    L8:
    v6 = ((i)((((d)this.picked[0]) - this.minX()) / this.bpp()));
    v7 = ((i)((((d)this.picked[1]) - this.minZ()) / this.bpp()));
    if (v6 <0) goto L72;
    if (v6 >= w) goto L72;
    if (v7 <0) goto L72;
    if (v7 < h) goto L73;
    L72:
    return;
    L73:
    ui.FwmTheme.box(ctx, ((mapX + v6) - 3), ((mapY + v7) - 3), 7, 7, 0, m1);
    return;
}

private void renderStatus(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    Objects.requireNonNull(font);
    v5 = (this.statusY() + ((12 - 9) / 2));
    if (this.picked != null) goto L53;
    new Object[1][0] = util.Fmt.compact(((l)net.RtpPoints.count(this.dim)));
    goto L130;
    L53:
    if (this.copied ==0) goto L66;
    goto L69;
    L66:
    L69:
    new Object[3][0] = Integer.valueOf(this.picked[0]);
    if (this.picked[2] != -32768) goto L105;
    goto L114;
    L105:
    L114:
    1["?"] = Integer.valueOf(this.picked[2]);
    new Object[3][2] = Integer.valueOf(this.picked[1]);
    L130:
    v6 = ui.Lang.of(new Object[3], new Object[3]);
    if (this.picked != null) goto L155;
    goto L158;
    L155:
    L158:
    font.text(v6, this.x, v5, ui.FwmTheme.TEXT_DIM, ui.FwmTheme.TEXT, 0);
    if (this.overMap(((d)mouseX), ((d)mouseY)) ==0) goto L181;
    if (this.dragging ==0) goto L182;
    L181:
    return;
    L182:
    v7 = ((l)Math.floor((this.minX() + (((d)(mouseX - this.x)) * this.bpp()))));
    v9 = ((l)Math.floor((this.minZ() + (((d)(mouseY - this.mapY())) * this.bpp()))));
    new Object[2][0] = Long.valueOf(v7);
    new Object[2][1] = Long.valueOf(v9);
    v11 = ui.Lang.of("fwm.rtp.hover", new Object[2]);
    ctx.text(font, v11, ((this.x + this.width) - font.width(v11)), v5, ui.FwmTheme.ACCENT, 0);
    return;
}

private void renderButtons(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    if (ui.panel.RtpPanel.ownPlayer() == null) goto L10;
    goto L11;
    L10:
    L11:
    v5 = 0;
    if (v5 ==0) goto L51;
    if (this.overButton(((d)mouseX), ((d)mouseY), 0) ==0) goto L51;
    goto L52;
    L51:
    L52:
    ctx.button(font, 0, ui.Lang.str("fwm.rtp.toMe", new Object[0]), v5, 1, 0);
    if (ui.panel.RtpPanel.ownPosOn() ==0) goto L71;
    goto L74;
    L71:
    L74:
    ctx.button(font, 1, "fwm.rtp.ownPos.on", ui.Lang.str("fwm.rtp.ownPos.off", new Object[0]), 1, this.overButton(((d)mouseX), ((d)mouseY), 1));
    this.button(ctx, font, 2, ui.Lang.str("fwm.rtp.browser", new Object[0]), 1, this.overButton(((d)mouseX), ((d)mouseY), 2));
    return;
}

private void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int index, String label, boolean enabled, boolean hovered) {
    v7 = this.buttonX(index);
    if (enabled ==0) goto L31;
    goto L34;
    L31:
    L34:
    if (hovered ==0) goto L45;
    goto L48;
    L45:
    L48:
    ui.FwmTheme.box(this.buttonY(), this.buttonW(), 16, ui.FwmTheme.RAISED, ui.FwmTheme.LOCKED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v8 = network.chat.Component.literal(font.plainSubstrByWidth(label, (this.buttonW() - 6)));
    if (enabled !=0) goto L80;
    goto L94;
    L80:
    if (hovered ==0) goto L91;
    goto L94;
    L91:
    L94:
    v9 = ui.FwmTheme.TEXT;
    Objects.requireNonNull(font);
    ctx.text(font, v8, (v7 + ((this.buttonW() - font.width(v8)) / 2)), ((this.buttonY() + ((16 - 9) / 2)) + 1), v9, 0);
    return;
}

private int overButton(double mouseX, double a2, int mouseY) {
    v6 = this.buttonX(v5);
    if (cmp(mouseX,((d)v6)) <0) goto L56;
    if (cmp(mouseX,((d)(v6 + this.buttonW()))) >=0) goto L56;
    if (cmp(mouseY,((d)this.buttonY())) <0) goto L56;
    if (cmp(mouseY,((d)(this.buttonY() + 16))) >=0) goto L56;
    goto L57;
    L56:
    L57:
    return 0;
}

private int overMap(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L54;
    if (cmp(mouseX,((d)(this.x + this.mapW()))) >=0) goto L54;
    if (cmp(v3,((d)this.mapY())) <0) goto L54;
    if (cmp(v3,((d)(this.mapY() + this.mapH()))) >=0) goto L54;
    goto L55;
    L54:
    L55:
    return 0;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (cmp(mouseY,((d)this.y)) <0) goto L46;
    if (cmp(mouseY,((d)(this.y + 14))) >=0) goto L46;
    this.selectDim(mouseX);
    return 1;
    L46:
    if (this.overButton(mouseX, mouseY, 0) ==0) goto L62;
    this.zoomToMe();
    return 1;
    L62:
    if (this.overButton(mouseX, mouseY, 1) ==0) goto L97;
    if (config.FwmConfig.INSTANCE.rtpShowOwnPos !=0) goto L88;
    goto L89;
    L88:
    L89:
    1.rtpShowOwnPos = 0;
    config.ConfigManager.save();
    return 1;
    L97:
    if (this.overButton(mouseX, mouseY, 2) ==0) goto L134;
    v6 = client.Minecraft.getInstance();
    client.gui.screens.ConfirmLinkScreen.confirmLinkNow(v6.gui.screen(), URI.create(config.FwmConfig.INSTANCE.rtpSiteUrl));
    return 1;
    L134:
    if (this.overMap(mouseX, mouseY) ==0) goto L164;
    this.lastMouseX = mouseX;
    this.lastMouseY = mouseY;
    this.dragging = 1;
    this.pick(mouseX, mouseY);
    L164:
    return 1;
}

private void selectDim(double mouseX) {
    v3 = 0;
    L2:
    if (v3 >= ui.panel.RtpPanel.DIMS.length) goto L95;
    if (cmp(mouseX,((d)this.slotX(v3))) <0) goto L89;
    if (cmp(mouseX,((d)(this.slotX(v3) + this.slotW()))) <0) goto L40;
    goto L89;
    L40:
    if (ui.panel.RtpPanel.DIMS[v3] != this.dim) goto L53;
    return;
    L53:
    this.dim = ui.panel.RtpPanel.DIMS[v3];
    this.picked = null;
    net.RtpPoints.open(this.dim);
    this.moved = 0;
    this.fitWorld();
    this.dirty = 1;
    return;
    L89:
    v3 = v3 + 1;
    goto L2;
    L95:
    return;
}

private void pick(double mouseX, double a2) {
    v5 = this.bpp();
    v7 = (this.minX() + ((mouseX - ((d)this.x)) * v5));
    v9 = (this.minZ() + ((v3 - ((d)this.mapY())) * v5));
    v11 = (5.0 * v5);
    v13 = net.RtpPoints.points(this.dim);
    v14 = (v11 * v11);
    v16 = null;
    v17 = 0;
    L70:
    if ((v17 + 2) >= v13.length) goto L193;
    v18 = (((d)v13[v17]) - v7);
    v20 = (((d)v13[(v17 + 1)]) - v9);
    if (cmp(Math.abs(v18),v11) >0) goto L187;
    if (cmp(Math.abs(v20),v11) <=0) goto L129;
    goto L187;
    L129:
    v22 = ((v18 * v18) + (v20 * v20));
    if (cmp(v22,v14) >=0) goto L187;
    v14 = v22;
    new int[3][0] = v13[v17];
    new int[3][1] = v13[(v17 + 1)];
    new int[3][2] = v13[(v17 + 2)];
    v16 = new int[3];
    L187:
    v17 = v17 + 3;
    goto L70;
    L193:
    this.picked = v16;
    this.copied = 0;
    if (v16 != null) goto L210;
    return;
    L210:
    if (v16[2] != -32768) goto L230;
    goto L237;
    L230:
    L237:
    v17 = "~" + ' ' + String.valueOf(v16[2]) + ' ' + v16[1];
    client.Minecraft.getInstance().keyboardHandler.setClipboard(v17);
    this.copied = 1;
    return;
}

public double mouseDragged(double mouseX, double a2, int mouseY) {
    if (this.dragging ==0) goto L12;
    if (v5 ==0) goto L14;
    L12:
    return 0;
    L14:
    this.centerX = (this.centerX - ((mouseX - this.lastMouseX) * this.bpp()));
    this.centerZ = (this.centerZ - ((mouseY - this.lastMouseY) * this.bpp()));
    this.moved = 1;
    this.lastMouseX = mouseX;
    this.lastMouseY = mouseY;
    this.dirty = 1;
    return 1;
}

public void mouseReleased() {
    this.dragging = 0;
    return;
}

public double mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.overMap(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    if (cmp(v5,0) <=0) goto L29;
    goto L37;
    L29:
    L37:
    v7 = (this.bpp * 1.5);
    v7 = Math.max(2.0, Math.min(this.maxBpp(), v7));
    if (cmp(v7,this.bpp) !=0) goto L68;
    return 1;
    L68:
    this.moved = 1;
    v9 = (this.minX() + ((mouseX - ((d)this.x)) * this.bpp()));
    v11 = (this.minZ() + ((mouseY - ((d)this.mapY())) * this.bpp()));
    this.bpp = v7;
    v13 = (this.minX() + ((mouseX - ((d)this.x)) * this.bpp()));
    v15 = (this.minZ() + ((mouseY - ((d)this.mapY())) * this.bpp()));
    this.centerX = (this.centerX + (v9 - v13));
    this.centerZ = (this.centerZ + (v11 - v15));
    this.dirty = 1;
    return 1;
}

private static String lambda$canvas$0() {
    return "fwm rtp map";
}

static resources.Identifier <clinit>() {
    ui.panel.RtpPanel.DIMS = net.RtpPoints$Dim.values();
    ui.panel.RtpPanel.MAP_TEXTURE = resources.Identifier.fromNamespaceAndPath("fwm", "rtp_map");
    return;
}

