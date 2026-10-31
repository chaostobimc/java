// class net/friendswithmoney/fwm/ui/panel/SellmacroTuningPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field OPTIMAL_ITEM_DELAY I;
field OPTIMAL_BURST I;
field OPTIMAL_PRESS_DELAY I;
field OPTIMAL_REOPEN_TICKS I;
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.tuning", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private void optimal() {
    v1 = config.FwmConfig.INSTANCE;
    v1.itemMoveDelayTicks = 1;
    v1.transferBurst = 10;
    v1.sellPressDelayTicks = 1;
    v1.reopenDelayTicks = 100;
    v1.clamp();
    config.ConfigManager.save();
    this.setBounds(this.x, this.y, this.width, this.height);
    return;
}

public void onHidden() {
    config.ConfigManager.save();
    return;
}

private static network.chat.MutableComponent lambda$buildRows$12() {
    return ui.Lang.of("fwm.option.tuningReset", new Object[0]);
}

private static network.chat.MutableComponent lambda$buildRows$11(int ticks) {
    new Object[1][0] = Double.valueOf((((d)ticks) / 20.0));
    new Object[1][0] = String.format(Locale.ROOT, "%.1f", new Object[1]);
    return ui.Lang.of("fwm.value.seconds", new Object[1]);
}

private static void lambda$buildRows$10(config.FwmConfig config, int value) {
    config.reopenDelayTicks = value;
    return;
}

private static int lambda$buildRows$9(config.FwmConfig config) {
    return config.reopenDelayTicks;
}

private static network.chat.MutableComponent lambda$buildRows$8(int ticks) {
    if (ticks != 1) goto L10;
    goto L12;
    L10:
    L12:
    new Object[1][0] = Integer.valueOf(ticks);
    return ui.Lang.of("fwm.value.ticks", new Object[1]);
}

private static void lambda$buildRows$7(config.FwmConfig config, int value) {
    config.sellPressDelayTicks = value;
    return;
}

private static int lambda$buildRows$6(config.FwmConfig config) {
    return config.sellPressDelayTicks;
}

private static network.chat.MutableComponent lambda$buildRows$5(int stacks) {
    return network.chat.Component.literal(String.valueOf(stacks));
}

private static void lambda$buildRows$4(config.FwmConfig config, int value) {
    config.transferBurst = value;
    return;
}

private static int lambda$buildRows$3(config.FwmConfig config) {
    return config.transferBurst;
}

private static network.chat.MutableComponent lambda$buildRows$2(int ticks) {
    if (ticks != 1) goto L10;
    goto L12;
    L10:
    L12:
    new Object[1][0] = Integer.valueOf(ticks);
    return ui.Lang.of("fwm.value.ticks", new Object[1]);
}

private static void lambda$buildRows$1(config.FwmConfig config, int value) {
    config.itemMoveDelayTicks = value;
    return;
}

private static int lambda$buildRows$0(config.FwmConfig config) {
    return config.itemMoveDelayTicks;
}

