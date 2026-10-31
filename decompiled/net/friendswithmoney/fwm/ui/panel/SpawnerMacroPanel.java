// class net/friendswithmoney/fwm/ui/panel/SpawnerMacroPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.spawnermacro", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.rows.add(?);
    new Object[1][0] = input.Keybinds.spawnerKeyName();
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
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$26(int seconds) {
    return network.chat.Component.literal(seconds + ' s');
}

private static void lambda$buildRows$25(config.FwmConfig config, int value) {
    config.spawnerRepeatSeconds = value;
    return;
}

private static int lambda$buildRows$24(config.FwmConfig config) {
    return config.spawnerRepeatSeconds;
}

private static network.chat.MutableComponent lambda$buildRows$23(int ticks) {
    return network.chat.Component.literal((ticks * 50) + ' ms');
}

private static void lambda$buildRows$22(config.FwmConfig config, int value) {
    config.spawnerClickDelayTicks = value;
    return;
}

private static int lambda$buildRows$21(config.FwmConfig config) {
    return config.spawnerClickDelayTicks;
}

private static boolean lambda$buildRows$20(config.FwmConfig config, Boolean value) {
    config.spawnerAutoSell = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$19(config.FwmConfig config) {
    return config.spawnerAutoSell;
}

private static boolean lambda$buildRows$18(config.FwmConfig config, Boolean value) {
    config.spawnerAutoStop = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$17(config.FwmConfig config) {
    return config.spawnerAutoStop;
}

private static boolean lambda$buildRows$16(config.FwmConfig config, Boolean value) {
    config.spawnerAutoDrop = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$15(config.FwmConfig config) {
    return config.spawnerAutoDrop;
}

private static network.chat.MutableComponent lambda$buildRows$14(int v) {
    if (v >0) goto L14;
    goto L20;
    L14:
    L20:
    return network.chat.Component.literal(v + ' %');
}

private static void lambda$buildRows$13(config.FwmConfig config, int value) {
    if (value <=0) goto L16;
    if (value >= 10) goto L16;
    goto L17;
    L16:
    L17:
    10.spawnerSellFill = value;
    return;
}

private static int lambda$buildRows$12(config.FwmConfig config) {
    return config.spawnerSellFill;
}

private static void lambda$buildRows$11(config.FwmConfig config, int v) {
    if (v != 1) goto L11;
    goto L23;
    L11:
    if (v != 2) goto L21;
    goto L23;
    L21:
    L23:
    "sell".spawnerMode = "bones";
    return;
}

private static boolean lambda$buildRows$10(config.FwmConfig config) {
    if ("drop".equals(config.spawnerMode) ==0) goto L16;
    goto L33;
    L16:
    if ("sell".equals(config.spawnerMode) ==0) goto L32;
    goto L33;
    L32:
    L33:
    return 0;
}

private static boolean lambda$buildRows$9(config.FwmConfig config, Boolean value) {
    config.spawnerLabels = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$8(config.FwmConfig config) {
    return config.spawnerLabels;
}

private static boolean lambda$buildRows$7(config.FwmConfig config, Boolean value) {
    config.spawnerSidePanel = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$6(config.FwmConfig config) {
    return config.spawnerSidePanel;
}

private void lambda$buildRows$5() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$4() {
    return network.chat.Component.literal(ui.Lang.t("Bild ansehen", "View picture"));
}

private static void lambda$buildRows$3() {
    v0 = client.Minecraft.getInstance();
    if (game.SpawnerMacro.running() ==0) goto L17;
    game.SpawnerMacro.toggle(v0);
    goto L29;
    L17:
    v0.gui.setScreen(null);
    game.SpawnerMacro.start(v0);
    L29:
    return;
}

private static network.chat.MutableComponent lambda$buildRows$2() {
    if (game.SpawnerMacro.running() ==0) goto L18;
    goto L27;
    L18:
    L27:
    return network.chat.Component.literal(ui.Lang.t("Starten", "Start"));
}

private static void lambda$buildRows$1(Boolean value) {
    config.Features.set("fwm.tab.spawnermacro", value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.tab.spawnermacro");
}

