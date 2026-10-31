// class net/friendswithmoney/fwm/net/RtpPoints  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAGIC I;
field FORMAT I;
field STALE_MS J;
field NO_HEIGHT I;
field CLOUDS Ljava/util/Map;;
field CELL_SHIFT I;
private void <init>() {
    return;
}

private static Object cloud(net.RtpPoints$Dim dim) {
    return ((net.RtpPoints$Cloud)net.RtpPoints.CLOUDS.computeIfAbsent(dim, /*indy*/apply()));
}

public static net.RtpPoints$Cloud index(net.RtpPoints$Dim dim) {
    return net.RtpPoints.cloud(dim).index;
}

public static net.RtpPoints$Cloud points(net.RtpPoints$Dim dim) {
    return net.RtpPoints.cloud(dim).points;
}

public static net.RtpPoints$Cloud count(net.RtpPoints$Dim dim) {
    return (net.RtpPoints.cloud(dim).points.length / 3);
}

public static net.RtpPoints$Cloud state(net.RtpPoints$Dim dim) {
    return net.RtpPoints.cloud(dim).state;
}

public static int area(net.RtpPoints$Dim dim) {
    return Math.max(1, net.RtpPoints.cloud(dim).area);
}

public static long ageMinutes(net.RtpPoints$Dim dim) {
    v1 = net.RtpPoints.cloud(dim).fetchedAt;
    if (cmp(v1,0) !=0) goto L20;
    goto L29;
    L20:
    L29:
    return ((System.currentTimeMillis() - v1) / 60000);
}

public static void open(net.RtpPoints$Dim dim) {
    v1 = net.RtpPoints.cloud(dim);
    if (v1.busy ==0) goto L13;
    return;
    L13:
    if (v1.points.length !=0) goto L49;
    v1.busy = 1;
    v1.state = net.RtpPoints$State.LOADING;
    net.FwmApi.executor().execute(/*indy*/run(dim, v1));
    return;
    L49:
    if (cmp((System.currentTimeMillis() - v1.fetchedAt),86400000) <=0) goto L68;
    net.RtpPoints.fetch(dim);
    L68:
    return;
}

private static concurrent.CompletableFuture fetch(net.RtpPoints$Dim dim) {
    v1 = net.RtpPoints.cloud(dim);
    if (v1.busy ==0) goto L13;
    return;
    L13:
    v1.busy = 1;
    if (v1.points.length !=0) goto L33;
    v1.state = net.RtpPoints$State.LOADING;
    L33:
    v2 = client.Minecraft.getInstance();
    v3 = net.RtpPoints.url(dim);
    net.FwmApi.getUrlStreamed(v3, /*indy*/parse()).whenCompleteAsync(/*indy*/accept(dim, v2, v1), net.FwmApi.executor());
    return;
}

private static net.RtpPoints$Cloud apply(net.RtpPoints$Dim dim, net.RtpPoints$Loaded loaded) {
    v2 = net.RtpPoints.cloud(dim);
    v2.points = loaded.points;
    v2.index = loaded.index;
    v2.area = loaded.area;
    v2.fetchedAt = loaded.at;
    v2.state = net.RtpPoints$State.OK;
    return;
}

private static String url(net.RtpPoints$Dim dim) {
    v1 = config.FwmConfig.INSTANCE.rtpApiUrl.replaceAll("/+$", "");
    return v1 + '?dim=' + dim.key();
}

// DECOMPILE ERROR in parse: 'dict' object has no attribute 'op_type'
private static void readPoints(stream.JsonReader reader, int count) {
    if (count <=0) goto L15;
    goto L17;
    L15:
    L17:
    v2 = new int[65536];
    v3 = 0;
    reader.beginArray();
    L26:
    if (reader.peek() == stream.JsonToken.END_ARRAY) goto L78;
    if (v3 != v2.length) goto L65;
    v4 = new int[(v2.length + (v2.length >> 1))];
    System.arraycopy(v2, 0, v4, 0, v3);
    v2 = v4;
    L65:
    v3 = v3 + 1;
    v2[v3] = reader.nextInt();
    goto L26;
    L78:
    reader.endArray();
    v3 = (v3 - (v3 % 3));
    if (v3 != v2.length) goto L96;
    return v2;
    L96:
    v4 = new int[v3];
    System.arraycopy(v2, 0, v4, 0, v3);
    return v4;
}

private static void buildIndex(int[] points) {
    if (points.length >= 3) goto L8;
    return null;
    L8:
    v1 = 2147483647;
    v2 = 2147483647;
    v3 = -2147483648;
    v4 = -2147483648;
    v5 = 0;
    L24:
    if ((v5 + 2) >= points.length) goto L81;
    v1 = Math.min(v1, points[v5]);
    v3 = Math.max(v3, points[v5]);
    v2 = Math.min(v2, points[(v5 + 1)]);
    v4 = Math.max(v4, points[(v5 + 1)]);
    v5 = v5 + 3;
    goto L24;
    L81:
    v5 = (v1 >> 12);
    v6 = (v2 >> 12);
    v7 = (((v3 >> 12) - v5) + 1);
    v8 = (((v4 >> 12) - v6) + 1);
    if (cmp((((l)v7) * ((l)v8)),4000000) <=0) goto L132;
    return null;
    L132:
    v9 = new int[((v7 * v8) + 1)];
    v10 = 0;
    L146:
    if ((v10 + 2) >= points.length) goto L189;
    v9[(net.RtpPoints.cellOf(points[v10], points[(v10 + 1)], v5, v6, v7) + 1)] = (v9[(net.RtpPoints.cellOf(points[v10], points[(v10 + 1)], v5, v6, v7) + 1)] + 1);
    v10 = v10 + 3;
    goto L146;
    L189:
    v10 = 0;
    L192:
    if (v10 >= (v7 * v8)) goto L223;
    v9[(v10 + 1)] = (v9[(v10 + 1)] + v9[v10]);
    v10 = v10 + 1;
    goto L192;
    L223:
    v10 = new int[(points.length / 3)];
    v11 = new int[(v7 * v8)];
    v12 = 0;
    L243:
    if ((v12 + 2) >= points.length) goto L300;
    v13 = net.RtpPoints.cellOf(points[v12], points[(v12 + 1)], v5, v6, v7);
    v11[v13] = (v11[v13] + 1);
    v10[(v9[v13] + v11[v13])] = v12;
    v12 = v12 + 3;
    goto L243;
    L300:
    v12 = ?;
    v12.minX = v1;
    v12.minZ = v2;
    v12.maxX = v3;
    v12.maxZ = v4;
    return v12;
}

private static int cellOf(int x, int z, int minCellX, int minCellZ, int cellsW) {
    return ((((z >> 12) - minCellZ) * cellsW) + ((x >> 12) - minCellX));
}

private static void writeFile(net.RtpPoints$Dim dim, net.RtpPoints$Loaded loaded) {
    v2 = net.RtpPoints.cacheFile(dim);
    Files.createDirectories(v2.getParent(), new attribute.FileAttribute[0]);
    v3 = ?;
    v3.writeInt(1180126546);
    v3.writeInt(1);
    v3.writeLong(loaded.at);
    v3.writeInt(loaded.area);
    v3.writeInt(loaded.points.length);
    v4 = loaded.points;
    v5 = v4.length;
    v6 = 0;
    L95:
    if (v6 >= v5) goto L121;
    v7 = v4[v6];
    v3.writeInt(v7);
    v6 = v6 + 1;
    goto L95;
    L121:
    v3.close();
    goto L149;
    v4 = /*catch Throwable*/ ex;
    v3.close();
    goto L146;
    v5 = /*catch Throwable*/ ex;
    v4.addSuppressed(v5);
    L146:
    throw v4;
    L149:
    goto L172;
    v3 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v2) + ': ' + v3.getMessage());
    L172:
    return;
}

private static void readFile(net.RtpPoints$Dim dim) {
    v1 = net.RtpPoints.cacheFile(dim);
    if (Files.exists(v1, new LinkOption[0]) !=0) goto L18;
    return null;
    L18:
    v2 = ?;
    if (v2.readInt() != 1180126546) goto L61;
    if (v2.readInt() == 1) goto L69;
    L61:
    v3 = null;
    v2.close();
    return v3;
    L69:
    v3 = ?;
    v3.at = v2.readLong();
    v3.area = v2.readInt();
    v4 = v2.readInt();
    if (v4 <0) goto L111;
    if ((v4 % 3) ==0) goto L121;
    L111:
    v5 = null;
    v2.close();
    return v5;
    L121:
    v3.points = new int[v4];
    v5 = 0;
    L132:
    if (v5 >= v4) goto L156;
    v3.points[v5] = v2.readInt();
    v5 = v5 + 1;
    goto L132;
    L156:
    v3.index = net.RtpPoints.buildIndex(v3.points);
    v5 = v3;
    v2.close();
    return v5;
    v3 = /*catch Throwable*/ ex;
    v2.close();
    goto L193;
    v4 = /*catch Throwable*/ ex;
    v3.addSuppressed(v4);
    L193:
    throw v3;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v1) + ': ' + v2.getMessage());
    return null;
}

private static Path cacheFile(net.RtpPoints$Dim dim) {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve('fwm-rtp-' + dim.key() + '.bin');
}

private static void lambda$fetch$1(net.RtpPoints$Dim dim, client.Minecraft client, net.RtpPoints$Cloud cloud, net.RtpPoints$Loaded loaded, Throwable error) {
    if (error != null) goto L17;
    if (loaded == null) goto L17;
    net.RtpPoints.writeFile(dim, loaded);
    goto L48;
    L17:
    if (error != null) goto L35;
    goto L40;
    L35:
    L40:
    dim.key().println('[FWM] RTP fetch failed for ' + "empty response" + ': ' + error.getMessage());
    L48:
    client.execute(/*indy*/run(cloud, error, loaded, dim));
    return;
}

private static void lambda$fetch$2(net.RtpPoints$Cloud cloud, Throwable error, net.RtpPoints$Loaded loaded, net.RtpPoints$Dim dim) {
    cloud.busy = 0;
    if (error != null) goto L13;
    if (loaded != null) goto L29;
    L13:
    if (cloud.points.length !=0) goto L28;
    cloud.state = net.RtpPoints$State.ERROR;
    L28:
    return;
    L29:
    net.RtpPoints.apply(dim, loaded);
    return;
}

private static net.RtpPoints$Loaded lambda$fetch$0(stream.JsonReader reader) {
    return net.RtpPoints.parse(reader);
}

private static void lambda$open$0(net.RtpPoints$Dim dim, net.RtpPoints$Cloud cloud) {
    v2 = net.RtpPoints.readFile(dim);
    v3 = client.Minecraft.getInstance();
    v3.execute(/*indy*/run(cloud, v2, dim));
    return;
}

private static void lambda$open$1(net.RtpPoints$Cloud cloud, net.RtpPoints$Loaded local, net.RtpPoints$Dim dim) {
    cloud.busy = 0;
    if (local == null) goto L34;
    net.RtpPoints.apply(dim, local);
    if (cmp((System.currentTimeMillis() - local.at),86400000) <=0) goto L33;
    net.RtpPoints.fetch(dim);
    L33:
    return;
    L34:
    net.RtpPoints.fetch(dim);
    return;
}

private static void lambda$cloud$0(net.RtpPoints$Dim key) {
    return ?;
}

static void <clinit>() {
    net.RtpPoints.CLOUDS = ?;
    return;
}

