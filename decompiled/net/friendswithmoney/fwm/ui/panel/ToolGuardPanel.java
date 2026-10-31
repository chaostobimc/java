// class net/friendswithmoney/fwm/ui/panel/ToolGuardPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.toolguard", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static boolean lambda$buildRows$8(config.FwmConfig config, Boolean value) {
    config.toolGuardBlock = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$7(config.FwmConfig config) {
    return config.toolGuardBlock;
}

private static boolean lambda$buildRows$6(config.FwmConfig config, Boolean value) {
    config.toolGuardWarning = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$5(config.FwmConfig config) {
    return config.toolGuardWarning;
}

private static network.chat.MutableComponent lambda$buildRows$4(int percent) {
    new Object[1][0] = Integer.valueOf(percent);
    return ui.Lang.of("fwm.value.percent", new Object[1]);
}

private static void lambda$buildRows$3(config.FwmConfig config, int value) {
    config.toolGuardPercent = value;
    return;
}

private static int lambda$buildRows$2(config.FwmConfig config) {
    return config.toolGuardPercent;
}

private static void lambda$buildRows$1(Boolean value) {
    config.Features.set("fwm.tab.toolguard", value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.tab.toolguard");
}

