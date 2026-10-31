// class net/friendswithmoney/fwm/seventv/EmoteConfig  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field USAGE Ljava/util/Map;;
field pickerW I;
field pickerH I;
private void <init>() {
    return;
}

public static synchronized int pickerWidth() {
    return seventv.EmoteConfig.pickerW;
}

public static synchronized int pickerHeight() {
    return seventv.EmoteConfig.pickerH;
}

public static synchronized int setPickerSize(int w, int h) {
    seventv.EmoteConfig.pickerW = Math.max(190, Math.min(2000, w));
    seventv.EmoteConfig.pickerH = Math.max(150, Math.min(2000, h));
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-emotes.json");
}

public static synchronized void load() {
    seventv.EmoteConfig.USAGE.clear();
    v0 = seventv.EmoteConfig.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L24;
    return;
    L24:
    v1 = JsonParser.parseString(Files.readString(v0, StandardCharsets.UTF_8)).getAsJsonObject();
    if (v1.has("pickerWidth") ==0) goto L59;
    seventv.EmoteConfig.pickerW = v1.get("pickerWidth").getAsInt();
    L59:
    if (v1.has("pickerHeight") ==0) goto L80;
    seventv.EmoteConfig.pickerH = v1.get("pickerHeight").getAsInt();
    L80:
    v2 = v1.getAsJsonArray("recent");
    if (v2 != null) goto L92;
    return;
    L92:
    v3 = v2.iterator();
    L97:
    if (v3.hasNext() ==0) goto L341;
    v4 = ((JsonElement)v3.next());
    v5 = v4.getAsJsonObject();
    v6 = seventv.Emote$Provider.byKey(v5.get("provider").getAsString());
    if (v6 != null) goto L147;
    goto L97;
    L147:
    if (v5.has("animated") ==0) goto L200;
    if (v5.get("animated").getAsBoolean() ==0) goto L200;
    goto L201;
    L200:
    L201:
    if (v5.has("type") ==0) goto L237;
    if (v5.get("type").isJsonNull() !=0) goto L237;
    goto L238;
    L237:
    L238:
    v7 = v6;
    v8 = ?;
    v8.emote = v7;
    if (v5.has("count") ==0) goto L284;
    goto L285;
    L284:
    L285:
    v5.get("count").getAsInt().count = 1;
    if (v5.has("last") ==0) goto L313;
    goto L314;
    L313:
    L314:
    v5.get("last").getAsLong().last = 0;
    seventv.EmoteConfig.USAGE.put(v7.key(), v8);
    seventv.EmoteRegistry.register(v7);
    goto L97;
    L341:
    goto L356;
    v1 = /*catch Exception*/ ex;
    seventv.SevenTv.LOG.warn("fwm-emotes.json konnte nicht gelesen werden", v1);
    L356:
    return;
}

public static synchronized void save() {
    v0 = ?;
    v1 = seventv.EmoteConfig.USAGE.values().iterator();
    L22:
    if (v1.hasNext() ==0) goto L154;
    v2 = ((seventv.EmoteConfig$Usage)v1.next());
    v3 = ?;
    v3.addProperty("provider", v2.emote.provider().key);
    v3.addProperty("id", v2.emote.id());
    v3.addProperty("name", v2.emote.name());
    v3.addProperty("animated", Boolean.valueOf(v2.emote.animated()));
    v3.addProperty("type", v2.emote.imageType());
    v3.addProperty("count", Integer.valueOf(v2.count));
    v3.addProperty("last", Long.valueOf(v2.last));
    v0.add(v3);
    goto L22;
    L154:
    v1 = ?;
    v1.addProperty("pickerWidth", Integer.valueOf(seventv.EmoteConfig.pickerW));
    v1.addProperty("pickerHeight", Integer.valueOf(seventv.EmoteConfig.pickerH));
    v1.add("recent", v0);
    Files.createDirectories(seventv.EmoteConfig.file().getParent(), new attribute.FileAttribute[0]);
    Files.writeString(seventv.EmoteConfig.file(), seventv.EmoteConfig.GSON.toJson(v1), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L246;
    v2 = /*catch Exception*/ ex;
    seventv.SevenTv.LOG.warn("fwm-emotes.json konnte nicht gespeichert werden", v2);
    L246:
    return;
}

public static synchronized void recordUse(seventv.Emote e) {
    v1 = ((seventv.EmoteConfig$Usage)seventv.EmoteConfig.USAGE.computeIfAbsent(e.key(), /*indy*/apply(e)));
    v1.emote = e;
    v1.count = (v1.count + 1);
    v1.last = System.currentTimeMillis();
    if (seventv.EmoteConfig.USAGE.size() <= 200) goto L145;
    v2 = ?;
    v2.sort(Comparator.comparingInt(/*indy*/applyAsInt()).thenComparingLong(/*indy*/applyAsLong()));
    v3 = 0;
    L100:
    if (v3 >= (v2.size() - 200)) goto L145;
    seventv.EmoteConfig.USAGE.remove(((seventv.EmoteConfig$Usage)v2.get(v3)).emote.key());
    v3 = v3 + 1;
    goto L100;
    L145:
    seventv.EmoteConfig.save();
    return;
}

public static synchronized boolean frequent(int limit) {
    v1 = ?;
    v2 = System.currentTimeMillis();
    v1.sort(Comparator.comparingDouble(/*indy*/applyAsDouble(v2)));
    v4 = ?;
    v5 = v1.iterator();
    L52:
    if (v5.hasNext() ==0) goto L104;
    v6 = ((seventv.EmoteConfig$Usage)v5.next());
    if (v4.size() < limit) goto L88;
    goto L104;
    L88:
    v4.add(v6.emote);
    goto L52;
    L104:
    return v4;
}

private static double score(seventv.EmoteConfig$Usage u, long now) {
    v3 = Math.max(0, (((d)(now - u.last)) / 86400000.0));
    return (((d)u.count) / (1 + (v3 / 7.0)));
}

public static synchronized int size() {
    return seventv.EmoteConfig.USAGE.size();
}

private static double lambda$frequent$0(long now, seventv.EmoteConfig$Usage a1) {
    return (-seventv.EmoteConfig.score(v2, now));
}

private static long lambda$recordUse$2(seventv.EmoteConfig$Usage x) {
    return x.last;
}

private static int lambda$recordUse$1(seventv.EmoteConfig$Usage x) {
    return x.count;
}

private static void lambda$recordUse$0(seventv.Emote e, String k) {
    v2 = ?;
    v2.emote = e;
    return v2;
}

static void <clinit>() {
    seventv.EmoteConfig.GSON = ?.setPrettyPrinting().create();
    seventv.EmoteConfig.USAGE = ?;
    seventv.EmoteConfig.pickerW = 310;
    seventv.EmoteConfig.pickerH = 250;
    return;
}

