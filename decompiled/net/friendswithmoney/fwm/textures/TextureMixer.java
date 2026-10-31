// class net/friendswithmoney/fwm/textures/TextureMixer  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FOLDER Ljava/lang/String;;
field TMP Ljava/lang/String;;
field PACK_ID Ljava/lang/String;;
field GROUPS Ljava/util/List;;
field M Ljava/lang/String;;
field CATEGORIES Ljava/util/List;;
field OLD_PARENT Ljava/util/Map;;
field GLOBS Ljava/util/Map;;
field GSON Lcom/google/gson/Gson;;
field building Z;
field progress F;
field message Ljava/lang/String;;
field OFFERED Ljava/util/Set;;
field CONTENT Ljava/util/Map;;
field SCANNING Ljava/util/Set;;
field PREVIEWS Ljava/util/Map;;
field MISSING Ljava/util/Set;;
field previewCount I;
field budget I;
public static void migrate() {
    v0 = config.FwmConfig.INSTANCE;
    if (v0.textureMixVersion < 2) goto L13;
    return;
    L13:
    v1 = textures.TextureMixer.OLD_PARENT.entrySet().iterator();
    L27:
    if (v1.hasNext() ==0) goto L117;
    v2 = ((Map$Entry)v1.next());
    v3 = ((String)v0.textureMix.get(v2.getValue()));
    if (v3 == null) goto L114;
    if (v3.isEmpty() !=0) goto L114;
    if (v0.textureMix.containsKey(v2.getKey()) !=0) goto L114;
    v0.textureMix.put(((String)v2.getKey()), v3);
    L114:
    goto L27;
    L117:
    v0.textureMixVersion = 2;
    config.ConfigManager.save();
    return;
}

public static Object category(String id) {
    v1 = textures.TextureMixer.CATEGORIES.iterator();
    L9:
    if (v1.hasNext() ==0) goto L44;
    v2 = ((textures.TextureMixer$Category)v1.next());
    if (v2.id().equals(id) ==0) goto L41;
    return v2;
    L41:
    goto L9;
    L44:
    return ((textures.TextureMixer$Category)textures.TextureMixer.CATEGORIES.get(0));
}

private static boolean fits(String inside, String prefix) {
    if (prefix.indexOf(42) >=0) goto L15;
    return inside.startsWith(prefix);
    L15:
    v2 = ((regex.Pattern)textures.TextureMixer.GLOBS.computeIfAbsent(prefix, /*indy*/apply()));
    return v2.matcher(inside).matches();
}

private void <init>() {
    return;
}

public static void register() {
    return;
}

public static boolean building() {
    return textures.TextureMixer.building;
}

public static float progress() {
    return textures.TextureMixer.progress;
}

public static String message() {
    return textures.TextureMixer.message;
}

private static Path packDir() {
    return client.Minecraft.getInstance().getResourcePackDirectory();
}

public static void packs() {
    v0 = ?;
    v1 = Files.list(textures.TextureMixer.packDir());
    v1.forEach(/*indy*/accept(v0));
    if (v1 == null) goto L62;
    v1.close();
    goto L62;
    v2 = /*catch Throwable*/ ex;
    if (v1 == null) goto L60;
    v1.close();
    goto L60;
    v3 = /*catch Throwable*/ ex;
    v2.addSuppressed(v3);
    L60:
    throw v2;
    L62:
    goto L66;
    v1 = /*catch IOException*/ ex;
    L66:
    v0.sort(String.CASE_INSENSITIVE_ORDER);
    return v0;
}

public static boolean offered(String category) {
    return textures.TextureMixer.OFFERED.contains(category);
}

public static Object chosen(String category) {
    if (textures.TextureMixer.offered(category) !=0) goto L10;
    return "";
    L10:
    v1 = ((String)config.FwmConfig.INSTANCE.textureMix.get(category));
    if (v1 != null) goto L35;
    goto L36;
    L35:
    L36:
    return v1;
}

public static void choose(String category, String pack) {
    if (pack == null) goto L11;
    if (pack.isEmpty() ==0) goto L27;
    L11:
    config.FwmConfig.INSTANCE.textureMix.remove(category);
    goto L41;
    L27:
    config.FwmConfig.INSTANCE.textureMix.put(category, pack);
    L41:
    config.ConfigManager.save();
    return;
}

static String classify(String path) {
    if (path.startsWith("assets/") !=0) goto L11;
    return null;
    L11:
    v1 = path.substring("assets/".length());
    v2 = v1.indexOf(47);
    if (v2 >=0) goto L34;
    return null;
    L34:
    v3 = v1.substring(0, v2);
    v4 = v1.substring((v2 + 1));
    if (v3.equals("fabricskyboxes") !=0) goto L68;
    if (v3.equals("nuit") ==0) goto L71;
    L68:
    return "sky";
    L71:
    v5 = textures.TextureMixer.CATEGORIES.iterator();
    L81:
    if (v5.hasNext() ==0) goto L224;
    v6 = ((textures.TextureMixer$Category)v5.next());
    v7 = v6.prefixes().iterator();
    L115:
    if (v7.hasNext() ==0) goto L221;
    v8 = ((String)v7.next());
    if (textures.TextureMixer.fits(v4, v8) ==0) goto L153;
    return v6.id();
    L153:
    if (v8.startsWith("textures/item/") ==0) goto L218;
    v9 = v8.substring("textures/item/".length()).replace(".png", ".json");
    if (textures.TextureMixer.fits(v4, 'models/item/' + v9) !=0) goto L212;
    if (textures.TextureMixer.fits(v4, 'items/' + v9) ==0) goto L218;
    L212:
    return v6.id();
    L218:
    goto L115;
    L221:
    goto L81;
    L224:
    return "rest";
}

public static void build(client.Minecraft client) {
    if (textures.TextureMixer.building ==0) goto L7;
    return;
    L7:
    v1 = ?;
    v1.values().removeIf(/*indy*/test());
    if (v1.isEmpty() ==0) goto L60;
    textures.TextureMixer.message = ui.Lang.t("W\u00e4hle zuerst f\u00fcr mindestens eine Kategorie ein Pack.", "Choose a pack for at least one category first.");
    return;
    L60:
    textures.TextureMixer.building = 1;
    textures.TextureMixer.progress = 0;
    textures.TextureMixer.message = ui.Lang.t("Baue Mix \u2026", "Building mix \u2026");
    v2 = ?;
    v2.setDaemon(1);
    v2.start();
    return;
}

private static String write(Map choice) {
    v1 = textures.TextureMixer.packDir();
    v2 = v1.resolve(".fwm-mix-tmp");
    v3 = v1.resolve("FWM Mix");
    textures.TextureMixer.deleteTree(v2);
    Files.createDirectories(v2, new attribute.FileAttribute[0]);
    v4 = ?;
    v5 = ?;
    v6 = ?;
    v7 = 0;
    v8 = v4.iterator();
    L82:
    if (v8.hasNext() ==0) goto L472;
    v9 = ((String)v8.next());
    v10 = ?;
    v11 = v10.root.resolve("assets");
    if (Files.isDirectory(v11, new LinkOption[0]) !=0) goto L150;
    v10.close();
    goto L82;
    L150:
    v13 = Files.walk(v11, new FileVisitOption[0]);
    v12 = v13.filter(/*indy*/test()).toList();
    if (v13 == null) goto L224;
    v13.close();
    goto L224;
    v14 = /*catch Throwable*/ ex;
    if (v13 == null) goto L221;
    v13.close();
    goto L221;
    v15 = /*catch Throwable*/ ex;
    v14.addSuppressed(v15);
    L221:
    throw v14;
    L224:
    v13 = v12.iterator();
    L233:
    if (v13.hasNext() ==0) goto L398;
    v14 = ((Path)v13.next());
    v15 = v10.root.relativize(v14).toString().replace(92, 47);
    if (v15.startsWith("/") ==0) goto L300;
    v15 = v15.substring(1);
    L300:
    if (v15.matches("assets/[^/]+/atlases/[^/]+\\.json") ==0) goto L323;
    textures.TextureMixer.mergeAtlas(v5, v15, v14);
    goto L233;
    L323:
    v16 = textures.TextureMixer.classify(v15);
    if (v16 == null) goto L233;
    if (v9.equals(choice.get(v16)) !=0) goto L354;
    goto L233;
    L354:
    v17 = textures.TextureMixer.safe(v2, v15);
    Files.createDirectories(v17.getParent(), new attribute.FileAttribute[0]);
    new CopyOption[1][0] = StandardCopyOption.REPLACE_EXISTING;
    Files.copy(v14, v17, new CopyOption[1]);
    goto L233;
    L398:
    v6.put(v9, Integer.valueOf(textures.TextureMixer.packFormat(v10.root)));
    v10.close();
    goto L449;
    v11 = /*catch Throwable*/ ex;
    v10.close();
    goto L446;
    v12 = /*catch Throwable*/ ex;
    v11.addSuppressed(v12);
    L446:
    throw v11;
    L449:
    v7 = v7 + 1;
    textures.TextureMixer.progress = (((f)v7) / ((f)(v4.size() + 1)));
    goto L82;
    L472:
    v8 = v5.entrySet().iterator();
    L486:
    if (v8.hasNext() ==0) goto L590;
    v9 = ((Map$Entry)v8.next());
    v10 = ?;
    v10.add("sources", ((JsonElement)v9.getValue()));
    v11 = textures.TextureMixer.safe(v2, ((String)v9.getKey()));
    Files.createDirectories(v11.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v11, textures.TextureMixer.GSON.toJson(v10), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L486;
    L590:
    Files.writeString(v2.resolve("pack.mcmeta"), antileak.AntiLeakPack.mcmeta(), StandardCharsets.UTF_8, new OpenOption[0]);
    v8 = textures.TextureMixer.class.getResourceAsStream("/assets/fwm/textures/gui/logo.png");
    if (v8 == null) goto L653;
    new CopyOption[1][0] = StandardCopyOption.REPLACE_EXISTING;
    L653:
    if (v8 == null) goto L693;
    v8.close();
    goto L693;
    v9 = /*catch Throwable*/ ex;
    if (v8 == null) goto L690;
    v8.close();
    goto L690;
    v10 = /*catch Throwable*/ ex;
    v9.addSuppressed(v10);
    L690:
    throw v9;
    L693:
    textures.TextureMixer.deleteTree(v3);
    new CopyOption[1][0] = StandardCopyOption.ATOMIC_MOVE;
    Files.move(v2, v3, new CopyOption[1]);
    goto L728;
    v8 = /*catch IOException*/ ex;
    Files.move(v2, v3, new CopyOption[0]);
    L728:
    v8 = antileak.AntiLeakPack.packFormat();
    v9 = ?;
    v6.forEach(/*indy*/accept(v8, v9));
    textures.TextureMixer.progress = 1;
    if (v9.isEmpty() ==0) goto L784;
    goto L806;
    L784:
    L806:
    textures.TextureMixer.message = ui.Lang.t("Mix gebaut. Pack f\u00fcr \u00e4ltere Version: ", "Mix built. Pack for an older version: ") + String.join(", ", v9);
    return;
}

private static void mergeAtlas(Map atlases, String rel, Path file) {
    v3 = JsonParser.parseString(Files.readString(file, StandardCharsets.UTF_8)).getAsJsonObject();
    if (v3.has("sources") ==0) goto L34;
    goto L41;
    L34:
    L41:
    v4 = ?;
    v5 = ((JsonArray)atlases.computeIfAbsent(rel, /*indy*/apply()));
    v6 = ?;
    v7 = v5.iterator();
    L76:
    if (v7.hasNext() ==0) goto L114;
    v8 = ((JsonElement)v7.next());
    v6.add(v8.toString());
    goto L76;
    L114:
    v7 = v4.iterator();
    L121:
    if (v7.hasNext() ==0) goto L168;
    v8 = ((JsonElement)v7.next());
    if (v6.add(v8.toString()) ==0) goto L165;
    v5.add(v8);
    L165:
    goto L121;
    L168:
    goto L188;
    v3 = /*catch Exception*/ ex;
    System.err.println('[FWM] Skipping broken atlas ' + rel + ': ' + v3.getMessage());
    L188:
    return;
}

private static int packFormat(Path root) {
    v1 = root.resolve("pack.mcmeta");
    if (Files.exists(v1, new LinkOption[0]) !=0) goto L23;
    return 0;
    L23:
    v2 = JsonParser.parseString(Files.readString(v1, StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("pack");
    if (v2 != null) goto L49;
    return 0;
    L49:
    if (v2.has("pack_format") ==0) goto L70;
    return v2.get("pack_format").getAsInt();
    L70:
    if (v2.has("min_format") ==0) goto L114;
    v3 = v2.get("min_format");
    if (v3.isJsonArray() ==0) goto L109;
    goto L113;
    L109:
    L113:
    return v3.getAsInt();
    L114:
    goto L118;
    v1 = /*catch Exception*/ ex;
    L118:
    return 0;
}

private static void safe(Path base, String rel) {
    v2 = base.resolve(rel).normalize();
    if (v2.startsWith(base.normalize()) ==0) goto L38;
    if (rel.contains("..") ==0) goto L52;
    L38:
    throw ?;
    L52:
    return v2;
}

private static void deleteTree(Path dir) {
    v1 = dir.getFileName().toString();
    if (v1.equals("FWM Mix") !=0) goto L49;
    if (v1.equals(".fwm-mix-tmp") !=0) goto L49;
    throw ?;
    L49:
    if (Files.exists(dir, new LinkOption[0]) !=0) goto L61;
    return;
    L61:
    v2 = Files.walk(dir, new FileVisitOption[0]);
    v3 = v2.sorted(Comparator.reverseOrder()).toList().iterator();
    L90:
    if (v3.hasNext() ==0) goto L119;
    v4 = ((Path)v3.next());
    Files.deleteIfExists(v4);
    goto L90;
    L119:
    if (v2 == null) goto L156;
    v2.close();
    goto L156;
    v3 = /*catch Throwable*/ ex;
    if (v2 == null) goto L154;
    v2.close();
    goto L154;
    v4 = /*catch Throwable*/ ex;
    v3.addSuppressed(v4);
    L154:
    throw v3;
    L156:
    return;
}

private static String apply(client.Minecraft client, Set sources) {
    v2 = client.getResourcePackRepository();
    v3 = config.FwmConfig.INSTANCE;
    v2.reload();
    v4 = ?;
    v5 = v4.contains("file/FWM Mix");
    if (v3.textureMixBefore != null) goto L73;
    v6 = ?;
    v6.remove("file/FWM Mix");
    v3.textureMixBefore = v6;
    L73:
    v4.remove("file/FWM Mix");
    if (v3.textureMixDisableSources ==0) goto L139;
    v6 = sources.iterator();
    L99:
    if (v6.hasNext() ==0) goto L139;
    v7 = ((String)v6.next());
    v4.remove('file/' + v7);
    goto L99;
    L139:
    v4.add("file/FWM Mix");
    v2.setSelected(v4);
    client.options.updateResourcePacks(v2);
    if (v5 ==0) goto L174;
    client.reloadResourcePacks();
    L174:
    config.ConfigManager.save();
    textures.TextureMixer.message = textures.TextureMixer.message + ui.Lang.t(" Aktiviert.", " Switched on.");
    return;
}

public static String remove(client.Minecraft client) {
    if (textures.TextureMixer.building ==0) goto L7;
    return;
    L7:
    v1 = client.getResourcePackRepository();
    v2 = config.FwmConfig.INSTANCE;
    v1.reload();
    v3 = ?;
    v3.remove("file/FWM Mix");
    if (v2.textureMixBefore == null) goto L119;
    v4 = v2.textureMixBefore.iterator();
    L60:
    if (v4.hasNext() ==0) goto L114;
    v5 = ((String)v4.next());
    if (v3.contains(v5) !=0) goto L111;
    if (v1.isAvailable(v5) ==0) goto L111;
    v3.add(v5);
    L111:
    goto L60;
    L114:
    v2.textureMixBefore = null;
    L119:
    v1.setSelected(v3);
    client.options.updateResourcePacks(v1);
    textures.TextureMixer.deleteTree(textures.TextureMixer.packDir().resolve("FWM Mix"));
    goto L167;
    v4 = /*catch IOException*/ ex;
    System.err.println('[FWM] Could not delete the mix: ' + v4.getMessage());
    L167:
    config.ConfigManager.save();
    textures.TextureMixer.message = ui.Lang.t("Mix entfernt.", "Mix removed.");
    return;
}

public static boolean active(client.Minecraft client) {
    return client.getResourcePackRepository().getSelectedIds().contains("file/FWM Mix");
}

public static void content(String pack) {
    v1 = ((Map)textures.TextureMixer.CONTENT.get(pack));
    if (v1 == null) goto L19;
    return v1;
    L19:
    if (textures.TextureMixer.SCANNING.add(pack) ==0) goto L57;
    v2 = ?;
    v2.setDaemon(1);
    v2.start();
    L57:
    return null;
}

public static boolean packsWith(List packs, String category) {
    v2 = 0;
    v3 = packs.iterator();
    L9:
    if (v3.hasNext() ==0) goto L58;
    v4 = ((String)v3.next());
    v5 = textures.TextureMixer.content(v4);
    if (v5 == null) goto L55;
    if (v5.containsKey(category) ==0) goto L55;
    v2 = v2 + 1;
    L55:
    goto L9;
    L58:
    return v2;
}

public static boolean preview(String pack, textures.TextureMixer$Category category) {
    if (pack == null) goto L11;
    if (pack.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    v2 = category.preview();
    v3 = ((Map)textures.TextureMixer.CONTENT.get(pack));
    if (v3 != null) goto L39;
    goto L52;
    L39:
    L52:
    v4 = ((String)v3.get(category.id()));
    if (v2 != null) goto L61;
    v2 = v4;
    L61:
    if (v2 == null) goto L72;
    if (v2.isEmpty() ==0) goto L74;
    L72:
    return null;
    L74:
    v5 = pack + '|' + v2;
    v6 = ((textures.TextureMixer$Preview)textures.TextureMixer.PREVIEWS.get(v5));
    if (v6 != null) goto L116;
    if (textures.TextureMixer.MISSING.contains(v5) ==0) goto L119;
    L116:
    return v6;
    L119:
    if (textures.TextureMixer.budget >0) goto L127;
    return null;
    L127:
    textures.TextureMixer.budget = (textures.TextureMixer.budget - 1);
    v7 = ?;
    v8 = v7.root.resolve(v2);
    if (Files.exists(v8, new LinkOption[0]) !=0) goto L206;
    if (v4 == null) goto L206;
    if (v4.isEmpty() !=0) goto L206;
    if (v4.equals(v2) !=0) goto L206;
    v8 = v7.root.resolve(v4);
    L206:
    if (Files.exists(v8, new LinkOption[0]) !=0) goto L240;
    textures.TextureMixer.MISSING.add(v5);
    v9 = null;
    v7.close();
    return v9;
    L240:
    v9 = Files.newInputStream(v8, new OpenOption[0]);
    v10 = textures.TextureMixer.register(v5, v9);
    if (v9 == null) goto L270;
    v9.close();
    L270:
    v7.close();
    return v10;
    v10 = /*catch Throwable*/ ex;
    if (v9 == null) goto L302;
    v9.close();
    goto L302;
    v11 = /*catch Throwable*/ ex;
    v10.addSuppressed(v11);
    L302:
    throw v10;
    v8 = /*catch Throwable*/ ex;
    v7.close();
    goto L324;
    v9 = /*catch Throwable*/ ex;
    v8.addSuppressed(v9);
    L324:
    throw v8;
    v7 = /*catch Exception*/ ex;
    textures.TextureMixer.MISSING.add(v5);
    return null;
}

public static void previewBudget(int n) {
    textures.TextureMixer.budget = n;
    return;
}

public static boolean vanillaPreview(textures.TextureMixer$Category category) {
    v1 = category.preview();
    if (v1 == null) goto L19;
    if (v1.startsWith("assets/minecraft/") !=0) goto L21;
    L19:
    return null;
    L21:
    v2 = '|vanilla|' + v1;
    v3 = ((textures.TextureMixer$Preview)textures.TextureMixer.PREVIEWS.get(v2));
    if (v3 != null) goto L57;
    if (textures.TextureMixer.MISSING.contains(v2) ==0) goto L59;
    L57:
    return v3;
    L59:
    if (textures.TextureMixer.budget >0) goto L67;
    return null;
    L67:
    textures.TextureMixer.budget = (textures.TextureMixer.budget - 1);
    v4 = client.Minecraft.getInstance().getVanillaPackResources().fullResources().getResource(server.packs.PackType.CLIENT_RESOURCES, resources.Identifier.withDefaultNamespace(v1.substring("assets/minecraft/".length())));
    if (v4 != null) goto L124;
    textures.TextureMixer.MISSING.add(v2);
    return null;
    L124:
    v5 = ((InputStream)v4.get());
    v6 = textures.TextureMixer.register(v2, v5);
    if (v5 == null) goto L154;
    v5.close();
    L154:
    return v6;
    v6 = /*catch Throwable*/ ex;
    if (v5 == null) goto L181;
    v5.close();
    goto L181;
    v7 = /*catch Throwable*/ ex;
    v6.addSuppressed(v7);
    L181:
    throw v6;
    v4 = /*catch Exception*/ ex;
    textures.TextureMixer.MISSING.add(v2);
    return null;
}

private static Object register(String key, InputStream in) {
    v2 = blaze3d.platform.NativeImage.read(in);
    v3 = textures.IsoRender.render(null, key.substring((key.lastIndexOf(124) + 1)), v2);
    if (v3 == null) goto L33;
    v2.close();
    v2 = v3;
    L33:
    v4 = v2.getWidth();
    v5 = v2.getHeight();
    if (v5 <= v4) goto L65;
    if ((v5 % v4) !=0) goto L65;
    goto L67;
    L65:
    L67:
    v6 = v5;
    textures.TextureMixer.previewCount = (textures.TextureMixer.previewCount + 1);
    v7 = resources.Identifier.fromNamespaceAndPath("fwm", 'mixpreview/' + textures.TextureMixer.previewCount);
    client.Minecraft.getInstance().getTextureManager().register(v7, ?);
    v8 = ?;
    textures.TextureMixer.PREVIEWS.put(key, v8);
    return v8;
}

public static void releasePreviews() {
    v0 = textures.TextureMixer.PREVIEWS.values().iterator();
    L14:
    if (v0.hasNext() ==0) goto L49;
    v1 = ((textures.TextureMixer$Preview)v0.next());
    client.Minecraft.getInstance().getTextureManager().release(v1.id());
    goto L14;
    L49:
    textures.TextureMixer.PREVIEWS.clear();
    textures.TextureMixer.MISSING.clear();
    return;
}

public static void clearPreviews() {
    v0 = textures.TextureMixer.PREVIEWS.values().iterator();
    L14:
    if (v0.hasNext() ==0) goto L49;
    v1 = ((textures.TextureMixer$Preview)v0.next());
    client.Minecraft.getInstance().getTextureManager().release(v1.id());
    goto L14;
    L49:
    textures.TextureMixer.PREVIEWS.clear();
    textures.TextureMixer.MISSING.clear();
    textures.TextureMixer.CONTENT.clear();
    return;
}

public static boolean favourite(String pack) {
    return config.FwmConfig.INSTANCE.textureFavorites.contains(pack);
}

public static void toggleFavourite(String pack) {
    v1 = config.FwmConfig.INSTANCE.textureFavorites;
    if (v1.remove(pack) !=0) goto L25;
    v1.add(pack);
    L25:
    config.ConfigManager.save();
    return;
}

public static void saveTheme(String name) {
    if (name != null) goto L9;
    goto L13;
    L9:
    L13:
    v1 = name.trim();
    if (v1.isEmpty() ==0) goto L22;
    return;
    L22:
    config.FwmConfig.INSTANCE.textureThemes.removeIf(/*indy*/test(v1));
    v2 = ?;
    v2.name = v1;
    v2.mix = ?;
    config.FwmConfig.INSTANCE.textureThemes.add(v2);
    config.ConfigManager.save();
    return;
}

public static void applyTheme(config.FwmConfig$TextureTheme theme) {
    config.FwmConfig.INSTANCE.textureMix.clear();
    if (theme == null) goto L37;
    if (theme.mix == null) goto L37;
    config.FwmConfig.INSTANCE.textureMix.putAll(theme.mix);
    L37:
    config.ConfigManager.save();
    return;
}

private static boolean lambda$saveTheme$0(String n, config.FwmConfig$TextureTheme t) {
    return n.equalsIgnoreCase(t.name);
}

private static String lambda$register$0() {
    return "fwm mix preview";
}

private static boolean lambda$content$0(String pack) {
    v1 = ?;
    v2 = ?;
    v3 = v2.root.resolve("assets");
    if (Files.isDirectory(v3, new LinkOption[0]) ==0) goto L119;
    v4 = Files.walk(v3, new FileVisitOption[0]);
    v4.filter(/*indy*/test()).forEach(/*indy*/accept(v2, v1));
    if (v4 == null) goto L119;
    v4.close();
    goto L119;
    v5 = /*catch Throwable*/ ex;
    if (v4 == null) goto L116;
    v4.close();
    goto L116;
    v6 = /*catch Throwable*/ ex;
    v5.addSuppressed(v6);
    L116:
    throw v5;
    L119:
    v2.close();
    goto L144;
    v3 = /*catch Throwable*/ ex;
    v2.close();
    goto L142;
    v4 = /*catch Throwable*/ ex;
    v3.addSuppressed(v4);
    L142:
    throw v3;
    L144:
    goto L148;
    v2 = /*catch Exception*/ ex;
    L148:
    textures.TextureMixer.CONTENT.put(pack, v1);
    textures.TextureMixer.SCANNING.remove(pack);
    return;
}

private static Object lambda$content$2(textures.TextureMixer$Source source, Map found, Path file) {
    v3 = source.root.relativize(file).toString().replace(92, 47);
    if (v3.startsWith("/") ==0) goto L39;
    v3 = v3.substring(1);
    L39:
    v4 = textures.TextureMixer.classify(v3);
    if (v4 != null) goto L51;
    return;
    L51:
    v5 = ((String)found.get(v4));
    v6 = v3.endsWith(".png");
    if (v5 == null) goto L90;
    if (v5.isEmpty() ==0) goto L110;
    if (v6 ==0) goto L110;
    L90:
    if (v6 ==0) goto L102;
    goto L104;
    L102:
    L104:
    v4.put(v3, "");
    L110:
    return;
}

private static boolean lambda$content$1(Path x$0) {
    return Files.isRegularFile(x$0, new LinkOption[0]);
}

private static void lambda$mergeAtlas$0(String k) {
    return ?;
}

private static boolean lambda$write$1(int current, List older, String pack, Integer format) {
    if (format.intValue() <=0) goto L23;
    if (format.intValue() >= current) goto L23;
    older.add(pack);
    L23:
    return;
}

private static boolean lambda$write$0(Path x$0) {
    return Files.isRegularFile(x$0, new LinkOption[0]);
}

private static String lambda$build$1(Map choice, client.Minecraft client) {
    textures.TextureMixer.write(choice);
    client.execute(/*indy*/run(client, choice));
    goto L59;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Texture mix failed: ' + String.valueOf(v2));
    textures.TextureMixer.building = 0;
    textures.TextureMixer.message = ui.Lang.t("Fehler beim Bauen: ", "Build failed: ") + v2.getMessage();
    L59:
    return;
}

private static void lambda$build$2(client.Minecraft client, Map choice) {
    textures.TextureMixer.building = 0;
    textures.TextureMixer.apply(client, ?);
    return;
}

private static boolean lambda$build$0(String v) {
    if (v == null) goto L11;
    if (v.isEmpty() ==0) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return 0;
}

private static boolean lambda$packs$0(List out, Path path) {
    v2 = path.getFileName().toString();
    if (v2.equals("FWM Mix") !=0) goto L62;
    if (v2.equals(".fwm-mix-tmp") !=0) goto L62;
    if (v2.equals("FWM Anti-Leak") !=0) goto L62;
    if (v2.equals("FWM Clan-Logos") !=0) goto L62;
    if (v2.startsWith(".") ==0) goto L63;
    L62:
    return;
    L63:
    if (Files.isDirectory(path, new LinkOption[0]) ==0) goto L97;
    if (Files.exists(path.resolve("pack.mcmeta"), new LinkOption[0]) ==0) goto L97;
    goto L98;
    L97:
    L98:
    v3 = 0;
    if (Files.isDirectory(path, new LinkOption[0]) !=0) goto L130;
    if (v2.toLowerCase(Locale.ROOT).endsWith(".zip") ==0) goto L130;
    goto L131;
    L130:
    L131:
    v4 = 0;
    if (v3 !=0) goto L142;
    if (v4 ==0) goto L150;
    L142:
    out.add(v2);
    L150:
    return;
}

private static regex.Pattern lambda$fits$0(String g) {
    if (g.endsWith("/") ==0) goto L55;
    goto L58;
    L55:
    L58:
    return regex.Pattern.compile(".*" + "(/.*)?");
}

private static String lambda$fits$1(String x, String y) {
    return x + '[^/]*' + y;
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    new String[3][0] = "pvp";
    new String[3][1] = "Kampf / PvP";
    new String[3][2] = "Combat / PvP";
    new String[3][0] = "tools";
    new String[3][1] = "Werkzeuge";
    new String[3][2] = "Tools";
    new String[3][0] = "world";
    new String[3][1] = "Welt";
    new String[3][2] = "World";
    new String[3][0] = "ui";
    new String[3][1] = "Oberfl\u00e4che";
    new String[3][2] = "Interface";
    new String[3][0] = "misc";
    new String[3][1] = "Sonstiges";
    new String[3][2] = "Other";
    new String[3][0] = "rest";
    new String[3][1] = "Rest";
    new String[3][2] = "Everything else";
    textures.TextureMixer.GROUPS = List.of(new String[3], new String[3], new String[3], new String[3], new String[3], new String[3]);
    new textures.TextureMixer$Category[28][0] = ?;
    new textures.TextureMixer$Category[28][1] = ?;
    new textures.TextureMixer$Category[28][2] = ?;
    new textures.TextureMixer$Category[28][3] = ?;
    new textures.TextureMixer$Category[28][4] = ?;
    new textures.TextureMixer$Category[28][5] = ?;
    new textures.TextureMixer$Category[28][6] = ?;
    new textures.TextureMixer$Category[28][7] = ?;
    new textures.TextureMixer$Category[28][8] = ?;
    new textures.TextureMixer$Category[28][9] = ?;
    new textures.TextureMixer$Category[28][10] = ?;
    new textures.TextureMixer$Category[28][11] = ?;
    new textures.TextureMixer$Category[28][12] = ?;
    new textures.TextureMixer$Category[28][13] = ?;
    new textures.TextureMixer$Category[28][14] = ?;
    new textures.TextureMixer$Category[28][15] = ?;
    new textures.TextureMixer$Category[28][16] = ?;
    new textures.TextureMixer$Category[28][17] = ?;
    new textures.TextureMixer$Category[28][18] = ?;
    new textures.TextureMixer$Category[28][19] = ?;
    new textures.TextureMixer$Category[28][20] = ?;
    new textures.TextureMixer$Category[28][21] = ?;
    new textures.TextureMixer$Category[28][22] = ?;
    new textures.TextureMixer$Category[28][23] = ?;
    new textures.TextureMixer$Category[28][24] = ?;
    new textures.TextureMixer$Category[28][25] = ?;
    new textures.TextureMixer$Category[28][26] = ?;
    new textures.TextureMixer$Category[28][27] = ?;
    textures.TextureMixer.CATEGORIES = List.of(new textures.TextureMixer$Category[28]);
    new Map$Entry[16][0] = Map.entry("anchor", "blocks");
    new Map$Entry[16][1] = Map.entry("fire", "blocks");
    new Map$Entry[16][2] = Map.entry("deepslate", "blocks");
    new Map$Entry[16][3] = Map.entry("stone", "blocks");
    new Map$Entry[16][4] = Map.entry("snow", "blocks");
    new Map$Entry[16][5] = Map.entry("obsidian", "blocks");
    new Map$Entry[16][6] = Map.entry("ores", "blocks");
    new Map$Entry[16][7] = Map.entry("totem", "items");
    new Map$Entry[16][8] = Map.entry("swords", "items");
    new Map$Entry[16][9] = Map.entry("bows", "items");
    new Map$Entry[16][10] = Map.entry("potions", "items");
    new Map$Entry[16][11] = Map.entry("pearls", "items");
    new Map$Entry[16][12] = Map.entry("apples", "items");
    new Map$Entry[16][13] = Map.entry("tools", "items");
    new Map$Entry[16][14] = Map.entry("crystal", "mobs");
    new Map$Entry[16][15] = Map.entry("inventory", "menus");
    textures.TextureMixer.OLD_PARENT = Map.ofEntries(new Map$Entry[16]);
    textures.TextureMixer.GLOBS = ?;
    textures.TextureMixer.GSON = ?.setPrettyPrinting().create();
    textures.TextureMixer.message = "";
    new String[15][0] = "crystal";
    new String[15][1] = "anchor";
    new String[15][2] = "totem";
    new String[15][3] = "swords";
    new String[15][4] = "armor";
    new String[15][5] = "bows";
    new String[15][6] = "potions";
    new String[15][7] = "pearls";
    new String[15][8] = "apples";
    new String[15][9] = "tools";
    new String[15][10] = "obsidian";
    new String[15][11] = "inventory";
    new String[15][12] = "hud";
    new String[15][13] = "particles";
    new String[15][14] = "effects";
    textures.TextureMixer.OFFERED = Set.of(new String[15]);
    textures.TextureMixer.CONTENT = ?;
    textures.TextureMixer.SCANNING = concurrent.ConcurrentHashMap.newKeySet();
    textures.TextureMixer.PREVIEWS = ?;
    textures.TextureMixer.MISSING = concurrent.ConcurrentHashMap.newKeySet();
    textures.TextureMixer.budget = 2147483647;
    return;
}

