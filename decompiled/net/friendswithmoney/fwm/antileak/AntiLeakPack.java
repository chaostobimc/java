// class net/friendswithmoney/fwm/antileak/AntiLeakPack  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ORES Ljava/util/Map;;
field STONES Ljava/util/List;;
field COLORMAPS Ljava/util/Map;;
field GSON Lcom/google/gson/Gson;;
field vanilla Lcom/google/gson/JsonObject;;
private void <init>() {
    return;
}

public static int packFormat() {
    return antileak.AntiLeakPack.data().get("pack_format").getAsInt();
}

public static void write(Path dir, antileak.AntiLeakPack$Settings settings) {
    antileak.AntiLeakPack.delete(dir);
    Files.createDirectories(dir, new attribute.FileAttribute[0]);
    antileak.AntiLeakPack.writeText(dir.resolve("pack.mcmeta"), antileak.AntiLeakPack.mcmeta());
    antileak.AntiLeakPack.writeLogo(dir.resolve("pack.png"));
    v2 = dir.resolve("assets/minecraft/blockstates");
    Files.createDirectories(v2, new attribute.FileAttribute[0]);
    v3 = antileak.AntiLeakPack.blockstates(settings).entrySet().iterator();
    L71:
    if (v3.hasNext() ==0) goto L134;
    v4 = ((Map$Entry)v3.next());
    antileak.AntiLeakPack.writeText(v2.resolve(((String)v4.getKey()) + '.json'), antileak.AntiLeakPack.GSON.toJson(((JsonElement)v4.getValue())));
    goto L71;
    L134:
    if (settings.bedrock() ==0) goto L191;
    v3 = dir.resolve("assets/minecraft/models/block");
    Files.createDirectories(v3, new attribute.FileAttribute[0]);
    antileak.AntiLeakPack.writeText(v3.resolve("bedrock.json"), antileak.AntiLeakPack.deepslateModel("cube_column"));
    antileak.AntiLeakPack.writeText(v3.resolve("bedrock_mirrored.json"), antileak.AntiLeakPack.deepslateModel("cube_column_mirrored"));
    L191:
    if (settings.biome() ==0) goto L301;
    v3 = dir.resolve("assets/minecraft/textures/colormap");
    Files.createDirectories(v3, new attribute.FileAttribute[0]);
    v4 = antileak.AntiLeakPack.data().getAsJsonArray("colormaps").iterator();
    L229:
    if (v4.hasNext() ==0) goto L301;
    v5 = ((JsonElement)v4.next());
    v6 = ((Integer)antileak.AntiLeakPack.COLORMAPS.get(v5.getAsString()));
    if (v6 == null) goto L298;
    antileak.AntiLeakPack.writeFlat(v3.resolve(v5.getAsString() + '.png'), v6.intValue());
    L298:
    goto L229;
    L301:
    return;
}

public static void delete(Path dir) {
    if (Files.exists(dir, new LinkOption[0]) !=0) goto L12;
    return;
    L12:
    v1 = Files.walk(dir, new FileVisitOption[0]);
    v2 = v1.sorted(Comparator.reverseOrder()).toList().iterator();
    L41:
    if (v2.hasNext() ==0) goto L78;
    v3 = ((Path)v2.next());
    if (v3.startsWith(dir) ==0) goto L75;
    Files.deleteIfExists(v3);
    L75:
    goto L41;
    L78:
    if (v1 == null) goto L113;
    v1.close();
    goto L113;
    v2 = /*catch Throwable*/ ex;
    if (v1 == null) goto L111;
    v1.close();
    goto L111;
    v3 = /*catch Throwable*/ ex;
    v2.addSuppressed(v3);
    L111:
    throw v2;
    L113:
    return;
}

static Object blockstates(antileak.AntiLeakPack$Settings settings) {
    v1 = ?;
    v2 = antileak.AntiLeakPack.data().getAsJsonObject("blockstates");
    if (settings.rotation() ==0) goto L75;
    v3 = v2.keySet().iterator();
    L34:
    if (v3.hasNext() ==0) goto L75;
    v4 = ((String)v3.next());
    v1.put(v4, antileak.AntiLeakPack.unturned(v2.getAsJsonObject(v4)));
    goto L34;
    L75:
    if (settings.bedrock() ==0) goto L97;
    v1.put("bedrock", antileak.AntiLeakPack.shownAs("deepslate", settings));
    L97:
    if (settings.ores() ==0) goto L119;
    antileak.AntiLeakPack.ORES.forEach(/*indy*/accept(v1, settings));
    L119:
    if (settings.stones() ==0) goto L174;
    v3 = antileak.AntiLeakPack.STONES.iterator();
    L135:
    if (v3.hasNext() ==0) goto L174;
    v4 = ((String)v3.next());
    v1.put(v4, antileak.AntiLeakPack.shownAs("stone", settings));
    goto L135;
    L174:
    return v1;
}

private static void shownAs(String host, antileak.AntiLeakPack$Settings settings) {
    v2 = antileak.AntiLeakPack.data().getAsJsonObject("blockstates").getAsJsonObject(host).getAsJsonObject("variants");
    if (v2.has("") ==0) goto L39;
    goto L84;
    L39:
    if (v2.has("axis=y") ==0) goto L59;
    goto L84;
    L59:
    L84:
    v3 = ((JsonElement)((Map$Entry)v2.entrySet().iterator().next()).getValue());
    if (settings.rotation() ==0) goto L97;
    v3 = antileak.AntiLeakPack.first(v3);
    L97:
    v4 = ?;
    v4.add("", v3.deepCopy());
    v5 = ?;
    v5.add("variants", v4);
    return v5;
}

private static void unturned(JsonObject vanillaState) {
    v1 = vanillaState.getAsJsonObject("variants");
    v2 = ?;
    v3 = v1.entrySet().iterator();
    L26:
    if (v3.hasNext() ==0) goto L79;
    v4 = ((Map$Entry)v3.next());
    v2.add(((String)v4.getKey()), antileak.AntiLeakPack.first(((JsonElement)v4.getValue())).deepCopy());
    goto L26;
    L79:
    v3 = ?;
    v3.add("variants", v2);
    return v3;
}

private static JsonElement first(JsonElement models) {
    if (models.isJsonArray() ==0) goto L25;
    v1 = models.getAsJsonArray();
    if (v1.isEmpty() !=0) goto L25;
    return v1.get(0);
    L25:
    return models;
}

public static String mcmeta() {
    v0 = antileak.AntiLeakPack.packFormat();
    v1 = ?;
    if (v0 < 65) goto L43;
    v1.addProperty("min_format", Integer.valueOf(v0));
    v1.addProperty("max_format", Integer.valueOf(v0));
    goto L53;
    L43:
    v1.addProperty("pack_format", Integer.valueOf(v0));
    L53:
    v1.addProperty("description", "By FriendsWithMoney");
    v2 = ?;
    v2.add("pack", v1);
    return antileak.AntiLeakPack.GSON.toJson(v2);
}

private static String deepslateModel(String parent) {
    v1 = ?;
    v1.addProperty("end", "minecraft:block/deepslate_top");
    v1.addProperty("side", "minecraft:block/deepslate");
    v2 = ?;
    v2.addProperty("parent", 'minecraft:block/' + parent);
    v2.add("textures", v1);
    return antileak.AntiLeakPack.GSON.toJson(v2);
}

private static void writeLogo(Path file) {
    v1 = antileak.AntiLeakPack.class.getResourceAsStream("/icon.png");
    if (v1 == null) goto L23;
    L23:
    if (v1 == null) goto L54;
    v1.close();
    goto L54;
    v2 = /*catch Throwable*/ ex;
    if (v1 == null) goto L52;
    v1.close();
    goto L52;
    v3 = /*catch Throwable*/ ex;
    v2.addSuppressed(v3);
    L52:
    throw v2;
    L54:
    return;
}

private static Path writeText(Path file, String text) {
    Files.writeString(file, text, StandardCharsets.UTF_8, new OpenOption[0]);
    return;
}

private static void writeFlat(Path file, int argb) {
    v2 = ?;
    v3 = 0;
    L17:
    if (v3 >= 256) goto L55;
    v4 = 0;
    L27:
    if (v4 >= 256) goto L49;
    v2.setRGB(v4, v3, argb);
    v4 = v4 + 1;
    goto L27;
    L49:
    v3 = v3 + 1;
    goto L17;
    L55:
    v3 = Files.newOutputStream(file, new OpenOption[0]);
    ImageIO.write(v2, "png", v3);
    if (v3 == null) goto L109;
    v3.close();
    goto L109;
    v4 = /*catch Throwable*/ ex;
    if (v3 == null) goto L106;
    v3.close();
    goto L106;
    v5 = /*catch Throwable*/ ex;
    v4.addSuppressed(v5);
    L106:
    throw v4;
    L109:
    return;
}

private static void data() {
    if (antileak.AntiLeakPack.vanilla == null) goto L10;
    return antileak.AntiLeakPack.vanilla;
    L10:
    v0 = antileak.AntiLeakPack.class.getResourceAsStream("/assets/fwm/antileak/variants.json");
    if (v0 != null) goto L34;
    throw ?;
    L34:
    antileak.AntiLeakPack.vanilla = JsonParser.parseReader(?).getAsJsonObject();
    if (v0 == null) goto L85;
    v0.close();
    goto L85;
    v1 = /*catch Throwable*/ ex;
    if (v0 == null) goto L83;
    v0.close();
    goto L83;
    v2 = /*catch Throwable*/ ex;
    v1.addSuppressed(v2);
    L83:
    throw v1;
    L85:
    goto L101;
    v0 = /*catch IOException*/ ex;
    throw ?;
    L101:
    return antileak.AntiLeakPack.vanilla;
}

private static Object lambda$blockstates$0(Map out, antileak.AntiLeakPack$Settings settings, String ore, String host) {
    out.put(ore, antileak.AntiLeakPack.shownAs(host, settings));
    return;
}

static Gson <clinit>() {
    antileak.AntiLeakPack.ORES = ?;
    antileak.AntiLeakPack.STONES = List.of("granite", "diorite", "andesite", "tuff", "gravel");
    v0 = List.of("coal", "iron", "copper", "gold", "redstone", "lapis", "diamond", "emerald").iterator();
    L64:
    if (v0.hasNext() ==0) goto L121;
    v1 = ((String)v0.next());
    antileak.AntiLeakPack.ORES.put(v1 + '_ore', "stone");
    antileak.AntiLeakPack.ORES.put('deepslate_' + v1 + '_ore', "deepslate");
    goto L64;
    L121:
    antileak.AntiLeakPack.ORES.put("nether_gold_ore", "netherrack");
    antileak.AntiLeakPack.ORES.put("nether_quartz_ore", "netherrack");
    antileak.AntiLeakPack.ORES.put("ancient_debris", "netherrack");
    antileak.AntiLeakPack.COLORMAPS = Map.of("grass", Integer.valueOf(-7226023), "foliage", Integer.valueOf(-8934609), "dry_foliage", Integer.valueOf(-5993892));
    antileak.AntiLeakPack.GSON = ?.setPrettyPrinting().create();
    return;
}

