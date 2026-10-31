// class net/friendswithmoney/fwm/ui/panel/SecondChatPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
public void <init>(ui.panel.PanelHost host) {
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.secondchat", new Object[0]);
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

private static boolean activeFilters() {
    v0 = 0;
    v1 = config.FwmConfig.INSTANCE.secondChatFilters.iterator();
    L14:
    if (v1.hasNext() ==0) goto L67;
    v2 = ((config.ChatFilter)v1.next());
    if (v2 == null) goto L64;
    if (v2.on ==0) goto L64;
    if (v2.text == null) goto L64;
    if (v2.text.isBlank() !=0) goto L64;
    v0 = v0 + 1;
    L64:
    goto L14;
    L67:
    return v0;
}

private static boolean lambda$buildRows$30(config.FwmConfig config, Boolean value) {
    config.secondChatRemove = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$29(config.FwmConfig config) {
    return config.secondChatRemove;
}

private static network.chat.MutableComponent lambda$buildRows$28(int value) {
    return network.chat.Component.literal(value + ' %');
}

private static void lambda$buildRows$27(config.FwmConfig config, int value) {
    config.secondChatBackgroundOpacity = value;
    return;
}

private static int lambda$buildRows$26(config.FwmConfig config) {
    return config.secondChatBackgroundOpacity;
}

private static boolean lambda$buildRows$25(config.FwmConfig config, Boolean value) {
    config.secondChatBackground = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$24(config.FwmConfig config) {
    return config.secondChatBackground;
}

private static network.chat.MutableComponent lambda$buildRows$23(int lines) {
    new Object[1][0] = Integer.valueOf(lines);
    return ui.Lang.of("fwm.secondchat.linesValue", new Object[1]);
}

private static void lambda$buildRows$22(config.FwmConfig config, int value) {
    config.secondChatLines = value;
    return;
}

private static int lambda$buildRows$21(config.FwmConfig config) {
    return config.secondChatLines;
}

// DECOMPILE ERROR in lambda$buildRows$20: 'dict' object has no attribute 'op_type'
private static void lambda$buildRows$19(config.FwmConfig config, int value) {
    config.secondChatAlign = value;
    return;
}

private static int lambda$buildRows$18(config.FwmConfig config) {
    return config.secondChatAlign;
}

private static void lambda$buildRows$17() {
    v0 = client.Minecraft.getInstance();
    v0.gui.setScreen(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$16() {
    return ui.Lang.of("fwm.option.hudEditOpen", new Object[0]);
}

private void lambda$buildRows$15() {
    this.host.openSub(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$14() {
    new Object[1][0] = Integer.valueOf(ui.panel.SecondChatPanel.activeFilters());
    return ui.Lang.of("fwm.secondchat.filterCount", new Object[1]);
}

private static boolean lambda$buildRows$13(config.FwmConfig config, Boolean value) {
    config.secondChatClan = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$12(config.FwmConfig config) {
    return config.secondChatClan;
}

private static boolean lambda$buildRows$11(config.FwmConfig config, Boolean value) {
    config.secondChatHugoOther = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$10(config.FwmConfig config) {
    return config.secondChatHugoOther;
}

private static boolean lambda$buildRows$9(config.FwmConfig config, Boolean value) {
    config.secondChatMarket = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$8(config.FwmConfig config) {
    return config.secondChatMarket;
}

private static boolean lambda$buildRows$7(config.FwmConfig config, Boolean value) {
    config.secondChatPay = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$6(config.FwmConfig config) {
    return config.secondChatPay;
}

private static boolean lambda$buildRows$5(config.FwmConfig config, Boolean value) {
    config.secondChatTpa = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$4(config.FwmConfig config) {
    return config.secondChatTpa;
}

private static boolean lambda$buildRows$3(config.FwmConfig config, Boolean value) {
    config.secondChatMsg = value.booleanValue();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig config) {
    return config.secondChatMsg;
}

private static void lambda$buildRows$1(Boolean value) {
    config.Features.set("fwm.tab.secondchat", value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.tab.secondchat");
}

