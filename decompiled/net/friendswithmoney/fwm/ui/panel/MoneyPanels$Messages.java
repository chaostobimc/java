// class net/friendswithmoney/fwm/ui/panel/MoneyPanels$Messages  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.kommt.ahMessages", new Object[0]);
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
    if (config.Features.isOn("fwm.tab.secondchat") !=0) goto L289;
    this.rows.add(?);
    L289:
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static boolean lambda$buildRows$11(config.FwmConfig c, Boolean v) {
    c.marketAlertPanel = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$10(config.FwmConfig c) {
    return c.marketAlertPanel;
}

private static boolean lambda$buildRows$9(config.FwmConfig c, Boolean v) {
    c.secondChatMarket = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$8(config.FwmConfig c) {
    return c.secondChatMarket;
}

private static boolean lambda$buildRows$7(config.FwmConfig c, Boolean v) {
    c.moneyShowOrderDelivered = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$6(config.FwmConfig c) {
    return c.moneyShowOrderDelivered;
}

private static boolean lambda$buildRows$5(config.FwmConfig c, Boolean v) {
    c.moneyShowOrderFilled = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$4(config.FwmConfig c) {
    return c.moneyShowOrderFilled;
}

private static boolean lambda$buildRows$3(config.FwmConfig c, Boolean v) {
    c.moneyShowAhBought = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig c) {
    return c.moneyShowAhBought;
}

private static boolean lambda$buildRows$1(config.FwmConfig c, Boolean v) {
    c.moneyShowAhSold = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig c) {
    return c.moneyShowAhSold;
}

