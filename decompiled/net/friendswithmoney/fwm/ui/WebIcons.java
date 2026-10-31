// class net/friendswithmoney/fwm/ui/WebIcons  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field SIZE I;
field READY Ljava/util/Map;;
field PENDING Ljava/util/Set;;
field FAILED Ljava/util/Set;;
private void <init>() {
    return;
}

public static void get(String path) {
    if (path == null) goto L11;
    if (path.isBlank() ==0) goto L13;
    L11:
    return null;
    L13:
    v1 = ((resources.Identifier)ui.WebIcons.READY.get(path));
    if (v1 != null) goto L54;
    if (ui.WebIcons.PENDING.contains(path) !=0) goto L54;
    if (ui.WebIcons.FAILED.contains(path) ==0) goto L56;
    L54:
    return v1;
    L56:
    ui.WebIcons.PENDING.add(path);
    ui.WebIcons.load(path);
    return null;
}

private static void load(String path) {
    v1 = client.Minecraft.getInstance();
    v2 = ui.WebIcons.cacheFile(path);
    net.FwmApi.executor().execute(/*indy*/run(v2, v1, path));
    return;
}

private static void finish(client.Minecraft client, String path, int[] pixels) {
    client.execute(/*indy*/run(path, pixels, client));
    return;
}

private static int shrink(byte[] png) {
    v1 = ImageIO.read(?);
    if (v1 != null) goto L18;
    return null;
    L18:
    v2 = v1.getWidth();
    v3 = v1.getHeight();
    v4 = new int[256];
    v5 = 0;
    L38:
    if (v5 >= 16) goto L314;
    v6 = ((v5 * v3) / 16);
    v7 = Math.max((v6 + 1), (((v5 + 1) * v3) / 16));
    v8 = 0;
    L75:
    if (v8 >= 16) goto L308;
    v9 = ((v8 * v2) / 16);
    v10 = Math.max((v9 + 1), (((v8 + 1) * v2) / 16));
    v11 = 0;
    v13 = 0;
    v15 = 0;
    v17 = 0;
    v19 = 0;
    v20 = v6;
    L128:
    if (v20 >= v7) goto L240;
    v21 = v9;
    L139:
    if (v21 >= v10) goto L234;
    v22 = v1.getRGB(v21, v20);
    v23 = (v22 >>> 24);
    v11 = (v11 + ((l)v23));
    v13 = (v13 + (((l)((v22 >> 16) & 255)) * ((l)v23)));
    v15 = (v15 + (((l)((v22 >> 8) & 255)) * ((l)v23)));
    v17 = (v17 + (((l)(v22 & 255)) * ((l)v23)));
    v19 = v19 + 1;
    v21 = v21 + 1;
    goto L139;
    L234:
    v20 = v20 + 1;
    goto L128;
    L240:
    if (cmp(v11,0) !=0) goto L250;
    goto L302;
    L250:
    v20 = ((i)(v11 / ((l)v19)));
    v4[((v5 * 16) + v8)] = ((((v20 << 24) | (((i)(v13 / v11)) << 16)) | (((i)(v15 / v11)) << 8)) | ((i)(v17 / v11)));
    L302:
    v8 = v8 + 1;
    goto L75;
    L308:
    v5 = v5 + 1;
    goto L38;
    L314:
    return v4;
    v1 = /*catch Exception*/ ex;
    return null;
}

private static boolean slug(String path) {
    v1 = path.toLowerCase(Locale.ROOT).replaceAll("^/+", "").replaceAll("[^a-z0-9/._-]", "_");
    if (v1.isEmpty() ==0) goto L34;
    goto L35;
    L34:
    L35:
    return v1;
}

private static Path cacheFile(String path) {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-icons").resolve(ui.WebIcons.slug(path).replace(47, 95));
}

private static boolean lambda$finish$0(String path, int[] pixels, client.Minecraft client) {
    ui.WebIcons.PENDING.remove(path);
    if (pixels != null) goto L25;
    ui.WebIcons.FAILED.add(path);
    return;
    L25:
    v3 = ?;
    v4 = 0;
    L41:
    if (v4 >= 16) goto L88;
    v5 = 0;
    L51:
    if (v5 >= 16) goto L82;
    v3.setPixel(v5, v4, pixels[((v4 * 16) + v5)]);
    v5 = v5 + 1;
    goto L51;
    L82:
    v4 = v4 + 1;
    goto L41;
    L88:
    v4 = resources.Identifier.fromNamespaceAndPath("fwm", 'webicon/' + ui.WebIcons.slug(path));
    client.getTextureManager().register(v4, ?);
    ui.WebIcons.READY.put(path, v4);
    goto L169;
    v3 = /*catch Exception*/ ex;
    System.err.println('[FWM] Icon ' + path + ' could not be loaded: ' + v3.getMessage());
    ui.WebIcons.FAILED.add(path);
    L169:
    return;
}

private static String lambda$finish$1(String path) {
    return 'fwm web icon ' + path;
}

private static concurrent.CompletableFuture lambda$load$0(Path file, client.Minecraft client, String path) {
    v3 = null;
    if (Files.exists(file, new LinkOption[0]) ==0) goto L18;
    v3 = Files.readAllBytes(file);
    L18:
    goto L25;
    v4 = /*catch Exception*/ ex;
    v3 = null;
    L25:
    if (v3 == null) goto L39;
    ui.WebIcons.finish(client, path, ui.WebIcons.shrink(v3));
    return;
    L39:
    if (path.startsWith("/") ==0) goto L65;
    goto L71;
    L65:
    L71:
    v4 = path + '/' + path;
    net.FwmApi.getBytes(v4).whenCompleteAsync(/*indy*/accept(path, client, file), net.FwmApi.executor());
    return;
}

private static void lambda$load$1(String path, client.Minecraft client, Path file, byte[] bytes, Throwable error) {
    if (error != null) goto L9;
    if (bytes != null) goto L44;
    L9:
    if (error != null) goto L24;
    goto L29;
    L24:
    L29:
    path.println('[FWM] Icon ' + "empty" + ' failed: ' + error.getMessage());
    ui.WebIcons.finish(client, path, null);
    return;
    L44:
    v5 = ui.WebIcons.shrink(bytes);
    if (v5 == null) goto L104;
    Files.createDirectories(file.getParent(), new attribute.FileAttribute[0]);
    Files.write(file, bytes, new OpenOption[0]);
    goto L104;
    v6 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(file) + ': ' + v6.getMessage());
    L104:
    ui.WebIcons.finish(client, path, v5);
    return;
}

static void <clinit>() {
    ui.WebIcons.READY = ?;
    ui.WebIcons.PENDING = ?;
    ui.WebIcons.FAILED = ?;
    return;
}

