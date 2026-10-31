// class net/friendswithmoney/fwm/ui/panel/AutoTpaPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.autotpa", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    new Object[1][0] = Integer.valueOf(v1.autoTpaNames.size());
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$6(int ticks) {
    new Object[1][0] = Double.valueOf((((d)ticks) / 20.0));
    return network.chat.Component.literal(String.format(Locale.GERMANY, "%.1f s", new Object[1]));
}

private static void lambda$buildRows$5(config.FwmConfig config, int value) {
    config.autoTpaDelayTicks = value;
    return;
}

private static int lambda$buildRows$4(config.FwmConfig config) {
    return config.autoTpaDelayTicks;
}

private static boolean lambda$buildRows$3(config.FwmConfig config, Boolean value) {
    config.autoTpaHere = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig config) {
    return config.autoTpaHere;
}

private static void lambda$buildRows$1(Boolean value) {
    config.Features.set("fwm.tab.autotpa", value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.tab.autotpa");
}

