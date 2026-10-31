// class net/friendswithmoney/fwm/textures/IsoRender  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field COS F;
private void <init>() {
    return;
}

static blaze3d.platform.NativeImage render(String categoryId, String path, blaze3d.platform.NativeImage src) {
    v3 = src.getWidth();
    v4 = src.getHeight();
    if (v4 <= v3) goto L28;
    if ((v4 % v3) !=0) goto L28;
    goto L30;
    L28:
    L30:
    v5 = v4;
    if (path.endsWith("textures/entity/end_crystal/end_crystal.png") ==0) goto L63;
    if (v3 < 64) goto L63;
    if (v3 != (v4 * 2)) goto L63;
    return textures.IsoRender.trim(textures.IsoRender.crystal(src));
    L63:
    if (path.contains("/block/") ==0) goto L91;
    if (v3 != v5) goto L91;
    return textures.IsoRender.trim(textures.IsoRender.cube(src, 0, 0, v3, v5));
    L91:
    if (path.contains("/item/") ==0) goto L124;
    if (v3 > 64) goto L124;
    if (v5 > 64) goto L124;
    return textures.IsoRender.trim(textures.IsoRender.item(src, v3, v5));
    L124:
    return null;
}

private static void trim(blaze3d.platform.NativeImage img) {
    v1 = img.getWidth();
    v2 = img.getHeight();
    v3 = m1;
    v4 = m1;
    v5 = 0;
    L18:
    if (v5 >= img.getHeight()) goto L100;
    v6 = 0;
    L30:
    if (v6 >= img.getWidth()) goto L94;
    if ((img.getPixel(v6, v5) >>> 24) >= 8) goto L58;
    goto L88;
    L58:
    v1 = Math.min(v1, v6);
    v2 = Math.min(v2, v5);
    v3 = Math.max(v3, v6);
    v4 = Math.max(v4, v5);
    L88:
    v6 = v6 + 1;
    goto L30;
    L94:
    v5 = v5 + 1;
    goto L18;
    L100:
    if (v3 >=0) goto L106;
    return img;
    L106:
    v5 = ((v3 - v1) + 3);
    v6 = ((v4 - v2) + 3);
    v7 = ?;
    v8 = v2;
    L138:
    if (v8 > v4) goto L191;
    v9 = v1;
    L148:
    if (v9 > v3) goto L185;
    v7.setPixel(((v9 - v1) + 1), ((v8 - v2) + 1), img.getPixel(v9, v8));
    v9 = v9 + 1;
    goto L148;
    L185:
    v8 = v8 + 1;
    goto L138;
    L191:
    img.close();
    return v7;
}

private static void cube(blaze3d.platform.NativeImage src, int sx, int sy, int sw, int sh) {
    v5 = 72;
    v6 = ?;
    textures.IsoRender.shadow(v6, (((f)v5) / 2), ((f)(v5 + 1)), 26.0, 5.0);
    v7 = 34.0;
    v8 = (((f)v5) / 2);
    v9 = ((f)(v5 - 2));
    new int[4][0] = sx;
    new int[4][1] = sy;
    new int[4][2] = sw;
    new int[4][3] = sh;
    new int[4][0] = sx;
    new int[4][1] = sy;
    new int[4][2] = sw;
    new int[4][3] = sh;
    new int[4][0] = sx;
    new int[4][1] = sy;
    new int[4][2] = sw;
    new int[4][3] = sh;
    textures.IsoRender.box(v6, src, v8, v9, v7, v7, v7, new int[4], new int[4], new int[4], 1);
    return v6;
}

private static void item(blaze3d.platform.NativeImage src, int w, int h) {
    v3 = Math.max(1, (64 / Math.max(w, h)));
    v4 = (w * v3);
    v5 = (h * v3);
    v6 = ?;
    textures.IsoRender.shadow(v6, (((f)(v4 + 8)) / 2), ((f)(v5 + 7)), (((f)v4) * 0.36000001430511475), 3.5);
    v7 = 0;
    L73:
    if (v7 >= v5) goto L142;
    v8 = 0;
    L83:
    if (v8 >= v4) goto L136;
    v9 = src.getPixel((v8 / v3), (v7 / v3));
    if ((v9 >>> 24) !=0) goto L115;
    goto L130;
    L115:
    textures.IsoRender.blend(v6, (v8 + 4), (v7 + 2), v9);
    L130:
    v8 = v8 + 1;
    goto L83;
    L136:
    v7 = v7 + 1;
    goto L73;
    L142:
    return v6;
}

private static void crystal(blaze3d.platform.NativeImage src) {
    v1 = (src.getWidth() / 64);
    v2 = 110;
    v3 = 104;
    v4 = ?;
    v5 = 3.0999999046325684;
    v6 = (((f)v2) / 2);
    v7 = ((f)(v3 - 6));
    textures.IsoRender.shadow(v4, v6, (v7 + 2), 40.0, 5.0);
    textures.IsoRender.box(v4, src, v6, v7, (12.0 * v5), (12.0 * v5), (4.0 * v5), textures.IsoRender.face(v1, 12, 16, 12, 12), textures.IsoRender.face(v1, 12, 28, 12, 4), textures.IsoRender.face(v1, 0, 28, 12, 4), 1);
    v8 = ((v7 - (4.0 * v5)) - (3.0 * v5));
    textures.IsoRender.box(v4, src, v6, (v8 - (2 * v5)), (6.0 * v5), (6.0 * v5), (6.0 * v5), textures.IsoRender.face(v1, 40, 0, 8, 8), textures.IsoRender.face(v1, 40, 8, 8, 8), textures.IsoRender.face(v1, 32, 8, 8, 8), 1);
    textures.IsoRender.box(v4, src, v6, v8, (10.0 * v5), (10.0 * v5), (10.0 * v5), textures.IsoRender.face(v1, 8, 0, 8, 8), textures.IsoRender.face(v1, 8, 8, 8, 8), textures.IsoRender.face(v1, 0, 8, 8, 8), 0.949999988079071);
    return v4;
}

private static int[] face(int u, int x, int y, int w, int h) {
    new int[4][0] = (x * u);
    new int[4][1] = (y * u);
    new int[4][2] = (w * u);
    new int[4][3] = (h * u);
    return new int[4];
}

private static void box(blaze3d.platform.NativeImage out, blaze3d.platform.NativeImage src, float bx, float by, float width, float depth, float height, int[] top, int[] front, int[] side, float opacity) {
    new float[2][0] = (-0.8660253882408142 * depth);
    new float[2][1] = (-0.5 * depth);
    v11 = new float[2];
    new float[2][0] = (0.8660253882408142 * width);
    new float[2][1] = (-0.5 * width);
    v12 = new float[2];
    textures.IsoRender.face(out, src, side, (bx + v11[0]), ((by + v11[1]) - height), (-v11[0]), (-v11[1]), 0, height, 0.7799999713897705, opacity);
    textures.IsoRender.face(out, src, front, bx, (by - height), v12[0], v12[1], 0, height, 0.6000000238418579, opacity);
    textures.IsoRender.face(out, src, top, ((bx + v11[0]) + v12[0]), (((by + v11[1]) + v12[1]) - height), (-v12[0]), (-v12[1]), (-v11[0]), (-v11[1]), 1, opacity);
    return;
}

private static void face(blaze3d.platform.NativeImage out, blaze3d.platform.NativeImage src, int[] r, float ox, float oy, float ux, float uy, float vx, float vy, float shade, float opacity) {
    if (r[2] <=0) goto L12;
    if (r[3] >0) goto L13;
    L12:
    return;
    L13:
    v11 = ((ux * vy) - (uy * vx));
    if (cmp(Math.abs(v11),9.999999747378752e-05) >=0) goto L38;
    return;
    L38:
    v12 = ((i)Math.floor(((d)Math.min(Math.min(ox, (ox + ux)), Math.min((ox + vx), ((ox + ux) + vx))))));
    v13 = ((i)Math.ceil(((d)Math.max(Math.max(ox, (ox + ux)), Math.max((ox + vx), ((ox + ux) + vx))))));
    v14 = ((i)Math.floor(((d)Math.min(Math.min(oy, (oy + uy)), Math.min((oy + vy), ((oy + uy) + vy))))));
    v15 = ((i)Math.ceil(((d)Math.max(Math.max(oy, (oy + uy)), Math.max((oy + vy), ((oy + uy) + vy))))));
    v16 = Math.max(0, v14);
    L182:
    if (v16 >= Math.min(out.getHeight(), v15)) goto L490;
    v17 = Math.max(0, v12);
    L204:
    if (v17 >= Math.min(out.getWidth(), v13)) goto L484;
    v18 = ((((f)v17) + 0.5) - ox);
    v19 = ((((f)v16) + 0.5) - oy);
    v20 = (((v18 * vy) - (v19 * vx)) / v11);
    v21 = (((ux * v19) - (uy * v18)) / v11);
    if (cmp(v20,0) <0) goto L478;
    if (cmp(v20,1) >=0) goto L478;
    if (cmp(v21,0) <0) goto L478;
    if (cmp(v21,1) <0) goto L302;
    goto L478;
    L302:
    v22 = (r[0] + Math.min((r[2] - 1), ((i)(v20 * ((f)r[2])))));
    v23 = (r[1] + Math.min((r[3] - 1), ((i)(v21 * ((f)r[3])))));
    if (v22 >= src.getWidth()) goto L478;
    if (v23 < src.getHeight()) goto L367;
    goto L478;
    L367:
    v24 = src.getPixel(v22, v23);
    v25 = Math.round((((f)(v24 >>> 24)) * opacity));
    if (v25 !=0) goto L399;
    goto L478;
    L399:
    v26 = Math.round((((f)((v24 >> 16) & 255)) * shade));
    v27 = Math.round((((f)((v24 >> 8) & 255)) * shade));
    v28 = Math.round((((f)(v24 & 255)) * shade));
    textures.IsoRender.blend(out, v17, v16, ((((v25 << 24) | (v26 << 16)) | (v27 << 8)) | v28));
    L478:
    v17 = v17 + 1;
    goto L204;
    L484:
    v16 = v16 + 1;
    goto L182;
    L490:
    return;
}

private static void shadow(blaze3d.platform.NativeImage out, float cx, float cy, float rx, float ry) {
    v5 = ((i)(cy - ry));
    L7:
    if (v5 > ((i)(cy + ry))) goto L138;
    v6 = ((i)(cx - rx));
    L23:
    if (v6 > ((i)(cx + rx))) goto L132;
    if (v6 <0) goto L126;
    if (v5 <0) goto L126;
    if (v6 >= out.getWidth()) goto L126;
    if (v5 < out.getHeight()) goto L63;
    goto L126;
    L63:
    v7 = ((((f)v6) - cx) / rx);
    v8 = ((((f)v5) - cy) / ry);
    v9 = ((v7 * v7) + (v8 * v8));
    if (cmp(v9,1) <0) goto L105;
    goto L126;
    L105:
    textures.IsoRender.blend(out, v6, v5, (Math.round((90.0 * (1 - v9))) << 24));
    L126:
    v6 = v6 + 1;
    goto L23;
    L132:
    v5 = v5 + 1;
    goto L7;
    L138:
    return;
}

private static void blend(blaze3d.platform.NativeImage out, int x, int y, int argb) {
    v4 = (argb >>> 24);
    if (v4 !=0) goto L12;
    return;
    L12:
    if (v4 != 255) goto L28;
    out.setPixel(x, y, argb);
    return;
    L28:
    v5 = out.getPixel(x, y);
    v6 = (v5 >>> 24);
    v7 = (v4 + ((v6 * (255 - v4)) / 255));
    if (v7 !=0) goto L67;
    return;
    L67:
    v8 = (((((argb >> 16) & 255) * v4) + (((((v5 >> 16) & 255) * v6) * (255 - v4)) / 255)) / v7);
    v9 = (((((argb >> 8) & 255) * v4) + (((((v5 >> 8) & 255) * v6) * (255 - v4)) / 255)) / v7);
    v10 = ((((argb & 255) * v4) + ((((v5 & 255) * v6) * (255 - v4)) / 255)) / v7);
    out.setPixel(x, y, ((((v7 << 24) | (v8 << 16)) | (v9 << 8)) | v10));
    return;
}

