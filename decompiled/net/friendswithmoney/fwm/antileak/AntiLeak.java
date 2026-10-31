// class net/friendswithmoney/fwm/antileak/AntiLeak  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field FOLDER Ljava/lang/String;;
field PACK_ID Ljava/lang/String;;
field checkedAtStart Z;
private void <init>() {
    return;
}

public static boolean dirty() {
    if (antileak.AntiLeak.wanted().equals(config.FwmConfig.INSTANCE.antiLeakApplied) !=0) goto L19;
    goto L20;
    L19:
    L20:
    return 0;
}

public static boolean sync(client.Minecraft client) {
    if (client != null) goto L6;
    return 0;
    L6:
    v1 = antileak.AntiLeak.wanted();
    if (v1.isEmpty() !=0) goto L21;
    goto L22;
    L21:
    L22:
    v2 = 0;
    if (v1.equals(config.FwmConfig.INSTANCE.antiLeakApplied) ==0) goto L76;
    if (v2 ==0) goto L54;
    if (Files.isDirectory(antileak.AntiLeak.folder(client), new LinkOption[0]) ==0) goto L76;
    L54:
    if (v2 != client.getResourcePackRepository().getSelectedIds().contains("file/FWM Anti-Leak")) goto L76;
    goto L77;
    L76:
    L77:
    v3 = 0;
    if (v3 ==0) goto L84;
    return 0;
    L84:
    return antileak.AntiLeak.apply(client, v2, v1);
}

public static boolean tick(client.Minecraft client) {
    if (antileak.AntiLeak.checkedAtStart !=0) goto L16;
    if (client.gui.overlay() == null) goto L17;
    L16:
    return;
    L17:
    antileak.AntiLeak.checkedAtStart = 1;
    antileak.AntiLeak.sync(client);
    return;
}

private static void apply(client.Minecraft client, boolean on, String wanted) {
    v3 = antileak.AntiLeak.folder(client);
    v4 = client.getResourcePackRepository();
    v5 = v4.getSelectedIds().contains("file/FWM Anti-Leak");
    if (on ==0) goto L73;
    antileak.AntiLeakPack.write(v3, antileak.AntiLeak.settings());
    v4.reload();
    if (v5 !=0) goto L81;
    if (v4.addPack("file/FWM Anti-Leak") !=0) goto L81;
    System.err.println('[FWM] Anti-Leak pack not found after writing it: ' + String.valueOf(v3));
    return 0;
    L73:
    v4.removePack("file/FWM Anti-Leak");
    L81:
    client.options.updateResourcePacks(v4);
    if (on ==0) goto L104;
    if (v5 ==0) goto L104;
    client.reloadResourcePacks();
    L104:
    if (on !=0) goto L112;
    antileak.AntiLeakPack.delete(v3);
    L112:
    config.FwmConfig.INSTANCE.antiLeakApplied = wanted;
    config.ConfigManager.save();
    return 1;
    v6 = /*catch Exception*/ ex;
    System.err.println('[FWM] Anti-Leak could not be applied: ' + String.valueOf(v6));
    return 0;
}

private static Path folder(client.Minecraft client) {
    return client.getResourcePackDirectory().resolve("FWM Anti-Leak");
}

private static void settings() {
    v0 = config.FwmConfig.INSTANCE;
    return ?;
}

private static String wanted() {
    if (config.Features.isOn("fwm.feature.antileak") !=0) goto L11;
    return "";
    L11:
    v0 = antileak.AntiLeak.settings();
    return 'v3|' + antileak.AntiLeakPack.packFormat() + '|b' + antileak.AntiLeak.bit(v0.bedrock()) + 'o' + antileak.AntiLeak.bit(v0.ores()) + 'r' + antileak.AntiLeak.bit(v0.rotation()) + 's' + antileak.AntiLeak.bit(v0.stones()) + 'c' + antileak.AntiLeak.bit(v0.biome());
}

private static int bit(boolean value) {
    if (value ==0) goto L8;
    goto L9;
    L8:
    L9:
    return 0;
}

