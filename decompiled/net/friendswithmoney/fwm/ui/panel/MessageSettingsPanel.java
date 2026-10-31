// class net/friendswithmoney/fwm/ui/panel/MessageSettingsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.messages.settings", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    if (v1.msgHidden.isEmpty() ==0) goto L175;
    this.rows.add(?);
    L175:
    v2 = ?.iterator();
    L190:
    if (v2.hasNext() ==0) goto L245;
    v3 = ((String)v2.next());
    this.rows.add(?);
    goto L190;
    L245:
    return;
}

private void lambda$buildRows$5(String name) {
    chat.Messages.unhide(name);
    this.onResize();
    return;
}

private static network.chat.MutableComponent lambda$buildRows$4() {
    return network.chat.Component.literal(ui.Lang.t("Einblenden", "Show again"));
}

private static void lambda$buildRows$3(config.FwmConfig config, Boolean value) {
    config.msgShowToast = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig config) {
    return config.msgShowToast;
}

private static void lambda$buildRows$1(config.FwmConfig config, Boolean value) {
    config.msgShowEnvelope = value.booleanValue();
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig config) {
    return config.msgShowEnvelope;
}

