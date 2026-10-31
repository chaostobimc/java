// class net/friendswithmoney/fwm/ui/panel/ExtrasPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field MIN_TPA_SECONDS I;
field MAX_TPA_SECONDS I;
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.extras", new Object[0]);
}

protected boolean buildRows() {
    this.toggle("fwm.feature.unitprice");
    this.toggle("fwm.feature.ahprice");
    this.toggle("fwm.feature.realname");
    this.toggle("fwm.feature.tpawarn");
    this.rows.add(?);
    return;
}

private boolean toggle(String feature) {
    this.rows.add(?);
    return;
}

private static void lambda$toggle$1(String feature, Boolean value) {
    config.Features.set(feature, value.booleanValue());
    return;
}

private static boolean lambda$toggle$0(String feature) {
    return config.Features.isOn(feature);
}

private static network.chat.MutableComponent lambda$buildRows$2(int seconds) {
    new Object[1][0] = Integer.valueOf(seconds);
    return ui.Lang.of("fwm.value.seconds", new Object[1]);
}

private static void lambda$buildRows$1(int value) {
    config.FwmConfig.INSTANCE.tpaTimeoutSeconds = value;
    return;
}

private static int lambda$buildRows$0() {
    return config.FwmConfig.INSTANCE.tpaTimeoutSeconds;
}

