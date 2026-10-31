// class net/friendswithmoney/fwm/ui/panel/SellmacroPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.menu.title", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private void lambda$buildRows$5() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$4() {
    return ui.Lang.of("fwm.option.open", new Object[0]);
}

private void lambda$buildRows$3() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$2(config.FwmConfig config) {
    new Object[1][0] = Integer.valueOf(config.whitelistSize());
    return ui.Lang.of("fwm.option.itemsCount", new Object[1]);
}

private static boolean lambda$buildRows$1(config.FwmConfig config, Boolean value) {
    config.enabled = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig config) {
    return config.enabled;
}

