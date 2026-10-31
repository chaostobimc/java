// class net/friendswithmoney/fwm/ui/panel/AntiLeakPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.antileak", new Object[0]);
}

protected boolean buildRows() {
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

public boolean onHidden() {
    this.onHidden();
    antileak.AntiLeak.sync(client.Minecraft.getInstance());
    return;
}

private static boolean lambda$buildRows$15() {
    antileak.AntiLeak.sync(client.Minecraft.getInstance());
    return;
}

private static network.chat.MutableComponent lambda$buildRows$14() {
    if (antileak.AntiLeak.dirty() ==0) goto L11;
    goto L13;
    L11:
    L13:
    return ui.Lang.of("fwm.antileak.current", new Object[0]);
}

private static boolean lambda$buildRows$13(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakBiome = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$12() {
    return config.FwmConfig.INSTANCE.antiLeakBiome;
}

private static boolean lambda$buildRows$11(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakStones = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$10() {
    return config.FwmConfig.INSTANCE.antiLeakStones;
}

private static boolean lambda$buildRows$9(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakRotation = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$8() {
    return config.FwmConfig.INSTANCE.antiLeakRotation;
}

private static boolean lambda$buildRows$7(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakOres = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$6() {
    return config.FwmConfig.INSTANCE.antiLeakOres;
}

private static boolean lambda$buildRows$5(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakBedrock = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$4() {
    return config.FwmConfig.INSTANCE.antiLeakBedrock;
}

private static void lambda$buildRows$3(Boolean value) {
    config.FwmConfig.INSTANCE.antiLeakCoords = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$2() {
    return config.FwmConfig.INSTANCE.antiLeakCoords;
}

private static void lambda$buildRows$1(Boolean value) {
    config.Features.set("fwm.feature.antileak", value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.feature.antileak");
}

