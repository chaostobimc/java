// class net/friendswithmoney/fwm/ui/panel/ColorsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.gui", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    v2 = ui.LauncherScreen.maxScalePercent();
    v1.menuScale = Math.min(v1.menuScale, v2);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static void lambda$buildRows$20(config.FwmConfig config) {
    config.tintHue = -1.0;
    config.tintSat = 0;
    config.menuHue = -1.0;
    config.menuSat = 1;
    ui.FwmPalette.refresh();
    config.ConfigManager.save();
    return;
}

private static network.chat.MutableComponent lambda$buildRows$19() {
    return ui.Lang.of("fwm.stats.reset", new Object[0]);
}

private static int lambda$buildRows$18(config.FwmConfig config, float t) {
    return ui.FwmPalette.accentAt(config.menuHue, t);
}

private static void lambda$buildRows$17(config.FwmConfig config, double value) {
    config.menuSat = ((f)value);
    ui.FwmPalette.refresh();
    return;
}

private static double lambda$buildRows$16(config.FwmConfig config) {
    return ((d)config.menuSat);
}

private static void lambda$buildRows$15(config.FwmConfig config) {
    config.menuHue = -1.0;
    config.menuSat = 1;
    ui.FwmPalette.refresh();
    return;
}

private static int lambda$buildRows$14() {
    return ui.FwmPalette.accentAt(-1.0, 1);
}

private static int lambda$buildRows$13(config.FwmConfig config, float t) {
    return ui.FwmPalette.accentAt(t, Math.max(0.5, config.menuSat));
}

private static void lambda$buildRows$12(config.FwmConfig config, double value) {
    config.menuHue = ((f)value);
    ui.FwmPalette.refresh();
    return;
}

private static double lambda$buildRows$11(config.FwmConfig config) {
    return ((d)config.menuHue);
}

private static int lambda$buildRows$10(config.FwmConfig config, float t) {
    return ui.FwmPalette.tintAt(config.tintHue, t);
}

private static void lambda$buildRows$9(config.FwmConfig config, double value) {
    config.tintSat = ((f)value);
    ui.FwmPalette.refresh();
    return;
}

private static double lambda$buildRows$8(config.FwmConfig config) {
    return ((d)config.tintSat);
}

private static void lambda$buildRows$7(config.FwmConfig config) {
    config.tintHue = -1.0;
    config.tintSat = 0;
    ui.FwmPalette.refresh();
    return;
}

private static int lambda$buildRows$6() {
    return ui.FwmPalette.tintAt(-1.0, 0);
}

private static int lambda$buildRows$5(config.FwmConfig config, float t) {
    return ui.FwmPalette.tintAt(t, Math.max(0.5, config.tintSat));
}

private static void lambda$buildRows$4(config.FwmConfig config, double value) {
    config.tintHue = ((f)value);
    ui.FwmPalette.refresh();
    return;
}

private static double lambda$buildRows$3(config.FwmConfig config) {
    return ((d)config.tintHue);
}

private static network.chat.MutableComponent lambda$buildRows$2(int percent) {
    return network.chat.Component.literal(percent + '%');
}

private static void lambda$buildRows$1(config.FwmConfig config, int value) {
    config.menuScale = value;
    return;
}

private static int lambda$buildRows$0(config.FwmConfig config) {
    return config.menuScale;
}

