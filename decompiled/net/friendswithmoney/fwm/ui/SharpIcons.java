// class net/friendswithmoney/fwm/ui/SharpIcons  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field COG Ljava/lang/String;;
field REFRESH Ljava/lang/String;;
field TEXTURES Ljava/util/Map;;
field MASTERS Ljava/util/Map;;
private void <init>() {
    return;
}

public static org.joml.Matrix3x2fStack draw(client.gui.GuiGraphicsExtractor ctx, String name, int x, int y, int size, int color) {
    v6 = Math.max(1, client.Minecraft.getInstance().getWindow().getGuiScale());
    v7 = (size * v6);
    v8 = ui.SharpIcons.texture(name, v7);
    if (v8 != null) goto L36;
    return;
    L36:
    v9 = ctx.pose();
    v9.pushMatrix();
    v9.translate(((f)x), ((f)y));
    v9.scale((1 / ((f)v6)), (1 / ((f)v6)));
    ctx.blit(client.renderer.RenderPipelines.GUI_TEXTURED, v8, 0, 0, 0, 0, v7, v7, v7, v7, v7, v7, color);
    v9.popMatrix();
    return;
}

private static Object texture(String name, int px) {
    v2 = name + '_' + px;
    v3 = ((resources.Identifier)ui.SharpIcons.TEXTURES.get(v2));
    if (v3 == null) goto L27;
    return v3;
    L27:
    v4 = ui.SharpIcons.master(name);
    if (v4 != null) goto L40;
    return null;
    L40:
    v5 = ?;
    v6 = ui.SharpIcons.shrink(v4, px);
    v7 = 0;
    L63:
    if (v7 >= px) goto L125;
    v8 = 0;
    L72:
    if (v8 >= px) goto L119;
    v9 = Math.round((v6[((v7 * px) + v8)] * 255.0));
    v5.setPixel(v8, v7, ((v9 << 24) | 16777215));
    v8 = v8 + 1;
    goto L72;
    L119:
    v7 = v7 + 1;
    goto L63;
    L125:
    v7 = resources.Identifier.fromNamespaceAndPath("fwm", 'sharp/' + v2);
    client.Minecraft.getInstance().getTextureManager().register(v7, ?);
    ui.SharpIcons.TEXTURES.put(v2, v7);
    return v7;
}

private static Object master(String name) {
    if (ui.SharpIcons.MASTERS.containsKey(name) ==0) goto L25;
    return ((image.BufferedImage)ui.SharpIcons.MASTERS.get(name));
    L25:
    v1 = null;
    v2 = ui.SharpIcons.class.getResourceAsStream('/assets/fwm/textures/gui/' + name + '.png');
    if (v2 == null) goto L48;
    v1 = ImageIO.read(v2);
    L48:
    if (v2 == null) goto L81;
    v2.close();
    goto L81;
    v3 = /*catch Throwable*/ ex;
    if (v2 == null) goto L79;
    v2.close();
    goto L79;
    v4 = /*catch Throwable*/ ex;
    v3.addSuppressed(v4);
    L79:
    throw v3;
    L81:
    goto L101;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Icon ' + name + ' could not be read: ' + v2.getMessage());
    L101:
    ui.SharpIcons.MASTERS.put(name, v1);
    return v1;
}

private static int shrink(image.BufferedImage src, int px) {
    v2 = src.getWidth();
    v3 = src.getHeight();
    v4 = (((d)v2) / ((d)px));
    v6 = (((d)v3) / ((d)px));
    v8 = new float[(px * px)];
    v9 = 0;
    L34:
    if (v9 >= px) goto L258;
    v10 = (((d)v9) * v6);
    v12 = (v10 + v6);
    v14 = 0;
    L58:
    if (v14 >= px) goto L252;
    v15 = (((d)v14) * v4);
    v17 = (v15 + v4);
    v19 = 0;
    v21 = ((i)v10);
    L87:
    if (cmp(((d)v21),Math.min(((d)v3), Math.ceil(v12))) >=0) goto L227;
    v22 = (Math.min(v12, ((d)(v21 + 1))) - Math.max(v10, ((d)v21)));
    if (cmp(v22,0) >0) goto L135;
    goto L221;
    L135:
    v24 = ((i)v15);
    L140:
    if (cmp(((d)v24),Math.min(((d)v2), Math.ceil(v17))) >=0) goto L221;
    v25 = (Math.min(v17, ((d)(v24 + 1))) - Math.max(v15, ((d)v24)));
    if (cmp(v25,0) >0) goto L188;
    goto L215;
    L188:
    v19 = (v19 + (((((d)(src.getRGB(v24, v21) >>> 24)) / 255.0) * v25) * v22));
    L215:
    v24 = v24 + 1;
    goto L140;
    L221:
    v21 = v21 + 1;
    goto L87;
    L227:
    v8[((v9 * px) + v14)] = ((f)(v19 / (v4 * v6)));
    v14 = v14 + 1;
    goto L58;
    L252:
    v9 = v9 + 1;
    goto L34;
    L258:
    return v8;
}

private static String lambda$texture$0(String key) {
    return 'fwm icon ' + key;
}

static void <clinit>() {
    ui.SharpIcons.TEXTURES = ?;
    ui.SharpIcons.MASTERS = ?;
    return;
}

