// class net/friendswithmoney/fwm/ui/panel/SettingsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.general", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static void lambda$buildRows$9(config.FwmConfig config) {
    config.tutorialSeen = 0;
    config.ConfigManager.save();
    ui.FwmTutorial.start();
    return;
}

private static network.chat.MutableComponent lambda$buildRows$8() {
    return ui.Lang.of("fwm.tutorial.start", new Object[0]);
}

private static void lambda$buildRows$7() {
    if (ui.Lang.isGerman() ==0) goto L11;
    goto L13;
    L11:
    L13:
    ui.Lang.set("de");
    config.ConfigManager.save();
    return;
}

private static network.chat.MutableComponent lambda$buildRows$6() {
    if (ui.Lang.isGerman() ==0) goto L11;
    goto L13;
    L11:
    L13:
    return network.chat.Component.literal("English");
}

private static void lambda$buildRows$5(config.FwmConfig config, Boolean value) {
    config.presenceShowOthers = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$4(config.FwmConfig config) {
    return config.presenceShowOthers;
}

private static void lambda$buildRows$3(config.FwmConfig config, Boolean value) {
    config.sidebarHideDisabled = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig config) {
    return config.sidebarHideDisabled;
}

private static void lambda$buildRows$1(config.FwmConfig config, Boolean value) {
    config.menuSounds = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig config) {
    return config.menuSounds;
}

