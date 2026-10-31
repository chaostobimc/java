// class net/friendswithmoney/fwm/config/ConfigManager  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
private void <init>() {
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm.json");
}

public static void load() {
    v0 = config.ConfigManager.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L19;
    config.ConfigManager.save();
    return;
    L19:
    v1 = Files.readString(v0, StandardCharsets.UTF_8);
    v2 = ((config.FwmConfig)config.ConfigManager.GSON.fromJson(v1, config.FwmConfig.class));
    config.FwmConfig.INSTANCE.copyFrom(v2);
    goto L76;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    config.FwmConfig.INSTANCE.clamp();
    L76:
    return;
}

public static void save() {
    v0 = config.ConfigManager.file();
    Files.createDirectories(v0.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v0, config.ConfigManager.GSON.toJson(config.FwmConfig.INSTANCE), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L62;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L62:
    return;
}

static Gson <clinit>() {
    config.ConfigManager.GSON = ?.setPrettyPrinting().create();
    return;
}

