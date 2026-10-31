// class net/friendswithmoney/fwm/colorcode/Templates  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAX I;
field GSON Lcom/google/gson/Gson;;
field list Ljava/util/List;;
private void <init>() {
    return;
}

public static void all() {
    if (colorcode.Templates.list != null) goto L9;
    colorcode.Templates.load();
    L9:
    return colorcode.Templates.list;
}

public static void add(colorcode.ColorCode code, colorcode.ColorCode$Mode mode) {
    v2 = code.serialize();
    if (code.text().isBlank() ==0) goto L19;
    return colorcode.Templates$Added.EMPTY;
    L19:
    v3 = colorcode.Templates.all().iterator();
    L28:
    if (v3.hasNext() ==0) goto L76;
    v4 = ((colorcode.Templates$Template)v3.next());
    if (v2.equals(v4.code) ==0) goto L73;
    if (mode != v4.mode()) goto L73;
    return colorcode.Templates$Added.EXISTS;
    L73:
    goto L28;
    L76:
    if (colorcode.Templates.all().size() < 50) goto L93;
    return colorcode.Templates$Added.FULL;
    L93:
    v3 = ?;
    v3.name = code.text().trim();
    v3.code = v2;
    v3.mode = mode.name();
    colorcode.Templates.all().add(v3);
    colorcode.Templates.save();
    return colorcode.Templates$Added.OK;
}

public static void remove(colorcode.Templates$Template template) {
    colorcode.Templates.all().remove(template);
    colorcode.Templates.save();
    return;
}

private static void load() {
    colorcode.Templates.list = ?;
    v0 = colorcode.Templates.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L26;
    return;
    L26:
    v1 = ((colorcode.Templates$Snapshot)colorcode.Templates.GSON.fromJson(Files.readString(v0, StandardCharsets.UTF_8), colorcode.Templates$Snapshot.class));
    if (v1 == null) goto L122;
    if (v1.templates == null) goto L122;
    v2 = v1.templates.iterator();
    L66:
    if (v2.hasNext() ==0) goto L122;
    v3 = ((colorcode.Templates$Template)v2.next());
    if (v3 == null) goto L119;
    if (v3.code == null) goto L119;
    if (colorcode.Templates.list.size() >= 50) goto L119;
    colorcode.Templates.list.add(v3);
    L119:
    goto L66;
    L122:
    goto L145;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L145:
    return;
}

private static void save() {
    v0 = colorcode.Templates.file();
    v1 = ?;
    v1.templates = colorcode.Templates.list;
    Files.createDirectories(v0.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v0, colorcode.Templates.GSON.toJson(v1), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L75;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L75:
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-farbcodes.json");
}

static Gson <clinit>() {
    colorcode.Templates.GSON = ?.setPrettyPrinting().create();
    return;
}

