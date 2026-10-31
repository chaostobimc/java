// class net/friendswithmoney/fwm/ui/RtpMinimap  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAP I;
field PAD I;
field TITLE_H I;
field LABEL_H I;
field WIDTH I;
field HEIGHT I;
field MIN_CHUNKS I;
field MAX_CHUNKS I;
field MAP_Y I;
field ON_POINT D;
field CHUNK_MIN_SCREEN_PX D;
field BACK I;
field POINT_COLOR I;
field GRID_COLOR I;
field CHUNK_COLOR I;
field PLAYER_COLOR I;
field HIT_COLOR I;
field HIT_GLOW I;
field OPEN_EVERY_MS J;
field lastOpen J;
field lastDim Lnet/friendswithmoney/fwm/net/RtpPoints$Dim;;
private void <init>() {
    return;
}

public static net.RtpPoints$Dim dimOf(client.multiplayer.ClientLevel world) {
    return net.RtpPoints$Dim.of(world);
}

public static int radiusChunks() {
    return Math.max(4, Math.min(256, config.FwmConfig.INSTANCE.rtpMiniRadius));
}

public static void draw(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    v2 = client.player;
    if (v2 != null) goto L10;
    return;
    L10:
    v3 = ui.RtpMinimap.dimOf(client.level);
    v4 = System.currentTimeMillis();
    if (v3 != ui.RtpMinimap.lastDim) goto L43;
    if (cmp((v4 - ui.RtpMinimap.lastOpen),2000) <=0) goto L56;
    L43:
    ui.RtpMinimap.lastDim = v3;
    ui.RtpMinimap.lastOpen = v4;
    net.RtpPoints.open(v3);
    L56:
    ui.FwmTheme.panel(ctx, 0, 0, 128, 150);
    v6 = client.font;
    v7 = ui.Lang.of("fwm.minimap.title", new Object[0]).withStyle(ChatFormatting.BOLD);
    ctx.text(v6, v7, ((128 - v6.width(v7)) / 2), 5, ui.FwmTheme.ACCENT, 0);
    ctx.fill(4, 15, 124, 135, 1711276032);
    v8 = (ui.RtpMinimap.radiusChunks() * 16);
    v9 = ((2.0 * ((d)v8)) / 120.0);
    v11 = (v2.getX() - ((d)v8));
    v13 = (v2.getZ() - ((d)v8));
    ui.RtpMinimap.drawChunks(ctx, client, v11, v13, v9);
    ui.RtpMinimap.drawGrid(ctx, v3, v11, v13, v9);
    v15 = ui.RtpMinimap.drawPoints(ctx, v3, v11, v13, v9, v8, v2.getX(), v2.getZ());
    if (v15[1] <=0) goto L230;
    goto L231;
    L230:
    L231:
    ui.RtpMinimap.drawPlayer(v2, 1, 0);
    if (net.RtpPoints.count(v3) !=0) goto L246;
    goto L253;
    L246:
    L253:
    v16 = String.valueOf(v15[0]);
    v17 = 137;
    ctx.text(v6, network.chat.Component.literal(v16), (124 - v6.width(v16)), v17, ui.FwmTheme.ACCENT, 0);
    v18 = ui.Lang.of("fwm.minimap.count", new Object[0]);
    ctx.text(v6, network.chat.Component.literal(v6.plainSubstrByWidth(v18.getString(), ((120 - v6.width(v16)) - 4))), 4, v17, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private static org.joml.Matrix3x2fStack drawChunks(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, double minX, double a3, double minZ) {
    v8 = ((d)client.getWindow().getGuiScale());
    if (cmp(((16.0 / v6) * v8),3.0) >=0) goto L27;
    return;
    L27:
    v10 = ((i)Math.round((120.0 * v8)));
    v11 = ((i)Math.round((4.0 * v8)));
    v12 = ((i)Math.round((15.0 * v8)));
    v13 = (120.0 * v6);
    v15 = ctx.pose();
    v15.pushMatrix();
    v15.scale(((f)(1 / v8)), ((f)(1 / v8)));
    v16 = ((l)Math.ceil((minX / 16.0)));
    L110:
    if (cmp(((d)(v16 * 16)),(minX + v13)) >=0) goto L192;
    v18 = ((i)Math.round((((((d)(v16 * 16)) - minX) / v6) * v8)));
    if (v18 <0) goto L183;
    if (v18 >= v10) goto L183;
    ctx.fill((v11 + v18), v12, ((v11 + v18) + 1), (v12 + v10), 788529151);
    L183:
    v16 = (v16 + 1);
    goto L110;
    L192:
    v16 = ((l)Math.ceil((minZ / 16.0)));
    L204:
    if (cmp(((d)(v16 * 16)),(minZ + v13)) >=0) goto L288;
    v18 = ((i)Math.round((((((d)(v16 * 16)) - minZ) / v6) * v8)));
    if (v18 <0) goto L279;
    if (v18 >= v10) goto L279;
    ctx.fill(v11, (v12 + v18), (v11 + v10), ((v12 + v18) + 1), 788529151);
    L279:
    v16 = (v16 + 1);
    goto L204;
    L288:
    v15.popMatrix();
    return;
}

private static void drawGrid(client.gui.GuiGraphicsExtractor ctx, net.RtpPoints$Dim dim, double minX, double a3, double minZ) {
    v8 = net.RtpPoints.area(dim);
    v9 = (v8 / 2);
    v10 = (120.0 * v6);
    v12 = ((l)Math.ceil(((minX + ((d)v9)) / ((d)v8))));
    L35:
    if (cmp(((d)((v12 * ((l)v8)) - ((l)v9))),(minX + v10)) >=0) goto L115;
    v14 = ((i)((((((d)v12) * ((d)v8)) - ((d)v9)) - minX) / v6));
    if (v14 <0) goto L106;
    if (v14 >= 120) goto L106;
    ctx.fill((4 + v14), 15, ((4 + v14) + 1), 135, 1442840575);
    L106:
    v12 = (v12 + 1);
    goto L35;
    L115:
    v12 = ((l)Math.ceil(((minZ + ((d)v9)) / ((d)v8))));
    L131:
    if (cmp(((d)((v12 * ((l)v8)) - ((l)v9))),(minZ + v10)) >=0) goto L213;
    v14 = ((i)((((((d)v12) * ((d)v8)) - ((d)v9)) - minZ) / v6));
    if (v14 <0) goto L204;
    if (v14 >= 120) goto L204;
    ctx.fill(4, (15 + v14), 124, ((15 + v14) + 1), 1442840575);
    L204:
    v12 = (v12 + 1);
    goto L131;
    L213:
    return;
}

private static void drawPoints(client.gui.GuiGraphicsExtractor ctx, net.RtpPoints$Dim dim, double minX, double a3, double minZ, int a5, double bpp, double a7) {
    v13 = net.RtpPoints.index(dim);
    if (v13 != null) goto L23;
    new int[2][0] = 0;
    new int[2][1] = 0;
    return new int[2];
    L23:
    v14 = net.RtpPoints.points(dim);
    v15 = ((i)Math.floor(minX));
    v16 = ((i)Math.floor(minZ));
    v17 = (v15 + (2 * v8));
    v18 = (v16 + (2 * v8));
    v19 = Math.max(0, ((v15 >> v13.shift) - v13.minCellX));
    v20 = Math.min((v13.cellsW - 1), ((v17 >> v13.shift) - v13.minCellX));
    v21 = Math.max(0, ((v16 >> v13.shift) - v13.minCellZ));
    v22 = Math.min((v13.cellsH - 1), ((v18 >> v13.shift) - v13.minCellZ));
    v23 = 0;
    v24 = 0;
    v25 = 25.0;
    v27 = v21;
    L169:
    if (v27 > v22) goto L417;
    v28 = v19;
    L180:
    if (v28 > v20) goto L411;
    v29 = ((v27 * v13.cellsW) + v28);
    v30 = v13.start[v29];
    L210:
    if (v30 >= v13.start[(v29 + 1)]) goto L405;
    v31 = v13.order[v30];
    v32 = ((i)Math.floor(((((d)v14[v31]) - minX) / bpp)));
    v33 = ((i)Math.floor(((((d)v14[(v31 + 1)]) - minZ) / bpp)));
    if (v32 <0) goto L399;
    if (v32 >= 120) goto L399;
    if (v33 <0) goto L399;
    if (v33 < 120) goto L299;
    goto L399;
    L299:
    v23 = v23 + 1;
    v34 = ((((d)v14[v31]) + 0.5) - v9);
    v36 = ((((d)v14[(v31 + 1)]) + 0.5) - v11);
    if (cmp(((v34 * v34) + (v36 * v36)),v25) >0) goto L357;
    v24 = v24 + 1;
    goto L399;
    L357:
    v38 = (4 + Math.min(v32, 118));
    v39 = (15 + Math.min(v33, 118));
    ctx.fill(v38, v39, (v38 + 2), (v39 + 2), -8395864);
    L399:
    v30 = v30 + 1;
    goto L210;
    L405:
    v28 = v28 + 1;
    goto L180;
    L411:
    v27 = v27 + 1;
    goto L169;
    L417:
    new int[2][0] = v23;
    new int[2][1] = v24;
    return new int[2];
}

private static void drawPlayer(client.gui.GuiGraphicsExtractor ctx, client.player.LocalPlayer player, boolean onPoint) {
    v3 = 64;
    v4 = 75;
    if (onPoint ==0) goto L17;
    goto L20;
    L17:
    L20:
    v5 = -670398;
    if (onPoint ==0) goto L91;
    ctx.fill((v3 - 4), (v4 - 4), (v3 + 5), (v4 + 5), 1431283967);
    ctx.fill((v3 - 3), (v4 - 5), (v3 + 4), (v4 + 6), 1431283967);
    ctx.fill((v3 - 5), (v4 - 3), (v3 + 6), (v4 + 4), 1431283967);
    L91:
    v6 = Math.toRadians(((d)player.getYRot()));
    v8 = (-Math.sin(v6));
    v10 = Math.cos(v6);
    v12 = 2;
    L119:
    if (v12 > 6) goto L179;
    v13 = (v3 + ((i)Math.round((v8 * ((d)v12)))));
    v14 = (v4 + ((i)Math.round((v10 * ((d)v12)))));
    ctx.fill(v13, v14, (v13 + 1), (v14 + 1), v5);
    v12 = v12 + 1;
    goto L119;
    L179:
    ctx.fill((v3 - 1), (v4 - 1), (v3 + 2), (v4 + 2), v5);
    return;
}

