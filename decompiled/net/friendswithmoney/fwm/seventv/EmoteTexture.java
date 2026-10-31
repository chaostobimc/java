// class net/friendswithmoney/fwm/seventv/EmoteTexture  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FRAME_H I;
field MAX_FRAME_W I;
field MAX_FRAMES I;
field CACHE Ljava/util/Map;;
field emote Lnet/friendswithmoney/fwm/seventv/Emote;;
field id Lnet/minecraft/resources/Identifier;;
field state Lnet/friendswithmoney/fwm/seventv/EmoteTexture$State;;
field frameW I;
field frameH I;
field frames I;
field frameEnds [I;
field hash J;
private resources.Identifier <init>(seventv.Emote emote) {
    this.state = seventv.EmoteTexture$State.LOADING;
    this.frameW = 56;
    this.frameH = 56;
    this.frames = 1;
    new int[1][0] = 100;
    this.frameEnds = new int[1];
    this.emote = emote;
    this.id = resources.Identifier.fromNamespaceAndPath("fwm", 'emotes/' + emote.key());
    return;
}

public static Object get(seventv.Emote emote) {
    return ((seventv.EmoteTexture)seventv.EmoteTexture.CACHE.computeIfAbsent(emote.key(), /*indy*/apply(emote)));
}

public static Object byKey(String key) {
    return ((seventv.EmoteTexture)seventv.EmoteTexture.CACHE.get(key));
}

public static Object peek(seventv.Emote emote) {
    return ((seventv.EmoteTexture)seventv.EmoteTexture.CACHE.get(emote.key()));
}

public float aspect() {
    if (this.frameH !=0) goto L11;
    goto L22;
    L11:
    L22:
    return (((f)this.frameW) / ((f)this.frameH));
}

public long currentFrame() {
    v1 = this.frames;
    if (v1 > 1) goto L12;
    return 0;
    L12:
    v2 = this.frameEnds;
    v3 = v2[(v2.length - 1)];
    if (v3 >0) goto L30;
    return 0;
    L30:
    v4 = (System.currentTimeMillis() % ((l)v3));
    v6 = 0;
    L41:
    if (v6 >= v2.length) goto L68;
    if (cmp(v4,((l)v2[v6])) >=0) goto L62;
    return v6;
    L62:
    v6 = v6 + 1;
    goto L41;
    L68:
    return (v1 - 1);
}

public int v0() {
    return (((f)this.currentFrame()) / ((f)this.frames));
}

public int v1() {
    return (((f)(this.currentFrame() + 1)) / ((f)this.frames));
}

private void load() {
    v1 = this.emote.imageUrls();
    this.tryUrl(v1, 0);
    return;
}

private concurrent.CompletableFuture tryUrl(String[] urls, int idx) {
    if (idx < urls.length) goto L14;
    this.state = seventv.EmoteTexture$State.FAILED;
    return;
    L14:
    seventv.EmoteApi.download(urls[idx]).thenApplyAsync(/*indy*/apply(this), seventv.EmoteApi.IO).whenComplete(/*indy*/accept(this, idx, urls));
    return;
}

private void decode(byte[] data) {
    v2 = ?;
    v3 = ?;
    seventv.EmoteTexture.readFrames(data, v2, v3);
    if (v2.isEmpty() ==0) goto L33;
    return null;
    L33:
    if (v2.size() <= 80) goto L212;
    v4 = ?;
    v5 = ?;
    v6 = (((d)v2.size()) / 80.0);
    v8 = 0;
    L78:
    if (v8 >= 80) goto L206;
    v9 = ((i)Math.floor((((d)v8) * v6)));
    v10 = ((i)Math.floor((((d)(v8 + 1)) * v6)));
    v11 = 0;
    v12 = v9;
    L118:
    if (v12 >= Math.max(v10, (v9 + 1))) goto L168;
    if (v12 >= v3.size()) goto L168;
    v11 = (v11 + ((Integer)v3.get(v12)).intValue());
    v12 = v12 + 1;
    goto L118;
    L168:
    v4.add(((image.BufferedImage)v2.get(v9)));
    v5.add(Integer.valueOf(v11));
    v8 = v8 + 1;
    goto L78;
    L206:
    v2 = v4;
    v3 = v5;
    L212:
    v4 = ((image.BufferedImage)v2.get(0));
    v5 = v4.getWidth();
    v6 = v4.getHeight();
    v7 = 56;
    v8 = Math.max(1, Math.min(224, Math.round((((f)v5) * (((f)v7) / ((f)v6))))));
    v9 = v2.size();
    v10 = ?;
    v11 = v10.createGraphics();
    v11.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    v11.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
    v11.setComposite(AlphaComposite.Src);
    v12 = 0;
    L333:
    if (v12 >= v9) goto L374;
    v11.drawImage(((Image)v2.get(v12)), 0, (v12 * v7), v8, v7, null);
    v12 = v12 + 1;
    goto L333;
    L374:
    v11.dispose();
    v12 = new int[v9];
    v13 = 0;
    v14 = 0;
    L391:
    if (v14 >= v9) goto L461;
    if (v14 >= v3.size()) goto L426;
    goto L428;
    L426:
    L428:
    v15 = 100;
    if (v15 >= 20) goto L441;
    v15 = 100;
    L441:
    v13 = (v13 + v15);
    v12[v14] = v13;
    v14 = v14 + 1;
    goto L391;
    L461:
    v14 = ?;
    v15 = 0;
    L484:
    if (v15 >= v10.getHeight()) goto L537;
    v16 = 0;
    L497:
    if (v16 >= v10.getWidth()) goto L531;
    v14.setPixel(v16, v15, v10.getRGB(v16, v15));
    v16 = v16 + 1;
    goto L497;
    L531:
    v15 = v15 + 1;
    goto L484;
    L537:
    return ?;
    v2 = /*catch Throwable*/ ex;
    seventv.SevenTv.LOG.debug("Dekodieren von {} fehlgeschlagen: {}", this.emote.name(), v2.toString());
    return null;
}

private void upload(seventv.EmoteTexture$Decoded d) {
    v2 = d.image();
    v3 = ?;
    client.Minecraft.getInstance().getTextureManager().register(this.id, v3);
    this.frameW = d.frameW;
    this.frameH = d.frameH;
    this.frames = d.frames;
    this.frameEnds = d.ends;
    this.hash = d.hash;
    this.state = seventv.EmoteTexture$State.READY;
    seventv.SevenTv.onEmoteLoaded(this.emote.key());
    goto L121;
    v2 = /*catch Throwable*/ ex;
    seventv.SevenTv.LOG.warn("Textur-Upload f\u00fcr {} fehlgeschlagen", this.emote.name(), v2);
    this.state = seventv.EmoteTexture$State.FAILED;
    L121:
    return;
}

private static void readFrames(byte[] data, List out, List delays) {
    v3 = ImageIO.createImageInputStream(?);
    v4 = ImageIO.getImageReaders(v3);
    if (v4.hasNext() !=0) goto L39;
    throw ?;
    L39:
    v5 = ((ImageReader)v4.next());
    v5.setInput(v3, 0);
    v6 = v5.getFormatName().toLowerCase();
    if (v6.equals("gif") !=0) goto L127;
    v7 = v5.read(0);
    out.add(seventv.EmoteTexture.toArgb(v7));
    delays.add(Integer.valueOf(100));
    v5.dispose();
    if (v3 == null) goto L126;
    v3.close();
    L126:
    return;
    L127:
    seventv.EmoteTexture.readGif(v5, out, delays);
    v5.dispose();
    goto L152;
    v8 = /*catch any*/ ex;
    v5.dispose();
    throw v8;
    L152:
    if (v3 == null) goto L192;
    v3.close();
    goto L192;
    v4 = /*catch Throwable*/ ex;
    if (v3 == null) goto L189;
    v3.close();
    goto L189;
    v5 = /*catch Throwable*/ ex;
    v4.addSuppressed(v5);
    L189:
    throw v4;
    L192:
    return;
}

// DECOMPILE ERROR in readGif: 'dict' object has no attribute 'op_type'
private static void toArgb(image.BufferedImage img) {
    if (img.getType() != 2) goto L10;
    return img;
    L10:
    v1 = ?;
    v2 = v1.createGraphics();
    v2.drawImage(img, 0, 0, null);
    v2.dispose();
    return v1;
}

private static void copy(image.BufferedImage src) {
    v1 = ?;
    v2 = v1.createGraphics();
    v2.setComposite(AlphaComposite.Src);
    v2.drawImage(src, 0, 0, null);
    v2.dispose();
    return v1;
}

private static Node child(Node parent, String name) {
    if (parent != null) goto L6;
    return null;
    L6:
    v2 = parent.getFirstChild();
    L13:
    if (v2 == null) goto L42;
    if (name.equals(v2.getNodeName()) ==0) goto L32;
    return v2;
    L32:
    v2 = v2.getNextSibling();
    goto L13;
    L42:
    return null;
}

private static int intAttr(Node node, String name, int def) {
    v3 = node.getAttributes();
    if (v3 != null) goto L13;
    return def;
    L13:
    v4 = v3.getNamedItem(name);
    if (v4 != null) goto L29;
    return def;
    L29:
    return Integer.parseInt(v4.getNodeValue());
    v5 = /*catch NumberFormatException*/ ex;
    return def;
}

private static int averageHash(image.BufferedImage img) {
    v1 = ?;
    v2 = v1.createGraphics();
    v2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
    v2.drawImage(img, 0, 0, 8, 8, null);
    v2.dispose();
    v3 = new int[64];
    v4 = 0;
    v6 = 0;
    L56:
    if (v6 >= 64) goto L172;
    v7 = v1.getRGB((v6 % 8), (v6 / 8));
    v8 = ((v7 >>> 24) & 255);
    v9 = ((v7 >> 16) & 255);
    v10 = ((v7 >> 8) & 255);
    v11 = (v7 & 255);
    v12 = ((((((v9 * 299) + (v10 * 587)) + (v11 * 114)) / 1000) * v8) / 255);
    v3[v6] = v12;
    v4 = (v4 + ((l)v12));
    v6 = v6 + 1;
    goto L56;
    L172:
    v6 = (v4 / 64);
    v8 = 0;
    v10 = 0;
    L186:
    if (v10 >= 64) goto L219;
    if (cmp(((l)v3[v10]),v6) <=0) goto L213;
    v8 = (v8 | (1 << v10));
    L213:
    v10 = v10 + 1;
    goto L186;
    L219:
    if (cmp(v8,0) !=0) goto L230;
    goto L232;
    L230:
    L232:
    return v8;
}

private String lambda$upload$0() {
    return 'fwm:emote/' + this.emote.key();
}

private void lambda$tryUrl$0(int idx, String[] urls, seventv.EmoteTexture$Decoded dec, Throwable err) {
    if (err != null) goto L9;
    if (dec != null) goto L49;
    L9:
    if ((idx + 1) < urls.length) goto L40;
    seventv.SevenTv.LOG.debug("Emote {} konnte nicht geladen werden: {}", this.emote.name(), String.valueOf(err));
    L40:
    this.tryUrl(urls, (idx + 1));
    return;
    L49:
    client.Minecraft.getInstance().execute(/*indy*/run(this, dec));
    return;
}

private void lambda$tryUrl$1(seventv.EmoteTexture$Decoded dec) {
    this.upload(dec);
    return;
}

private static void lambda$get$0(seventv.Emote emote, String k) {
    v2 = ?;
    v2.load();
    return v2;
}

static void <clinit>() {
    seventv.EmoteTexture.CACHE = ?;
    return;
}

