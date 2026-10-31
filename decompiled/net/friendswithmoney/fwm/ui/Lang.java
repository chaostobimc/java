// class net/friendswithmoney/fwm/ui/Lang  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GERMAN Ljava/lang/String;;
field ENGLISH Ljava/lang/String;;
field GSON Lcom/google/gson/Gson;;
field de Ljava/util/Map;;
field en Ljava/util/Map;;
private void <init>() {
    return;
}

public static Map load() {
    ui.Lang.de = ui.Lang.read("de_de");
    ui.Lang.en = ui.Lang.read("en_us");
    return;
}

private static void read(String file) {
    v1 = '/assets/fwm/lang/' + file + '.json';
    v2 = ui.Lang.class.getResourceAsStream(v1);
    if (v2 != null) goto L32;
    throw ?;
    L32:
    v3 = ((Map)ui.Lang.GSON.fromJson(?, ?.getType()));
    if (v2 == null) goto L71;
    v2.close();
    L71:
    return v3;
    v3 = /*catch Throwable*/ ex;
    if (v2 == null) goto L93;
    v2.close();
    goto L93;
    v4 = /*catch Throwable*/ ex;
    v3.addSuppressed(v4);
    L93:
    throw v3;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + v1 + ': ' + v2.getMessage());
    return ?;
}

public static boolean isGerman() {
    if ("en".equals(config.FwmConfig.INSTANCE.language) !=0) goto L18;
    goto L19;
    L18:
    L19:
    return 0;
}

public static boolean set(String language) {
    if ("en".equals(language) ==0) goto L17;
    goto L19;
    L17:
    L19:
    "en".language = "de";
    return;
}

public static boolean t(String de, String en) {
    if (ui.Lang.isGerman() ==0) goto L10;
    goto L11;
    L10:
    L11:
    return en;
}

public static void toggle() {
    if (ui.Lang.isGerman() ==0) goto L11;
    goto L13;
    L11:
    L13:
    ui.Lang.set("de");
    return;
}

public static String str(String key, Object[] args) {
    if (ui.Lang.de != null) goto L9;
    ui.Lang.load();
    L9:
    if (ui.Lang.isGerman() ==0) goto L21;
    goto L24;
    L21:
    L24:
    v2 = ui.Lang.en;
    v3 = ((String)v2.get(key));
    if (v3 != null) goto L65;
    if (ui.Lang.isGerman() ==0) goto L52;
    goto L55;
    L52:
    L55:
    v3 = ((String)ui.Lang.de.get(key));
    L65:
    if (v3 != null) goto L71;
    return key;
    L71:
    if (args.length !=0) goto L78;
    return v3;
    L78:
    return String.format(Locale.ROOT, v3, args);
    v4 = /*catch Exception*/ ex;
    return v3;
}

public static network.chat.MutableComponent of(String key, Object[] args) {
    return network.chat.Component.literal(ui.Lang.str(key, args));
}

static void <clinit>() {
    ui.Lang.GSON = ?;
    return;
}

