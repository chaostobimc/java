// class net/friendswithmoney/fwm/ui/panel/MoneyPanels$Settings  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.money.settings", new Object[0]);
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
    this.rows.add(?);
    return;
}

private static boolean lambda$buildRows$25(config.FwmConfig c, Boolean v) {
    c.ecValue = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$24(config.FwmConfig c) {
    return c.ecValue;
}

private static boolean lambda$buildRows$23(config.FwmConfig c, Boolean v) {
    c.invValueShulkers = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$22(config.FwmConfig c) {
    return c.invValueShulkers;
}

private static void lambda$buildRows$21(config.FwmConfig c, int v) {
    c.invValueSource = v;
    return;
}

private static int lambda$buildRows$20(config.FwmConfig c) {
    return c.invValueSource;
}

private static void lambda$buildRows$19(config.FwmConfig c, int v) {
    if (v ==0) goto L10;
    goto L11;
    L10:
    L11:
    config.Features.set(1, 0);
    if (v == 1) goto L25;
    if (v != 3) goto L29;
    L25:
    goto L30;
    L29:
    L30:
    1.invValueHud = 0;
    if (v == 2) goto L44;
    if (v != 3) goto L48;
    L44:
    goto L49;
    L48:
    L49:
    1.invValueInInventory = 0;
    return;
}

private static boolean lambda$buildRows$18(config.FwmConfig c) {
    if (config.Features.isOn("fwm.feature.invvalue") !=0) goto L12;
    goto L69;
    L12:
    if (c.invValueHud ==0) goto L23;
    goto L24;
    L23:
    L24:
    if (c.invValueInInventory ==0) goto L35;
    goto L36;
    L35:
    L36:
    if ((2 + 0) !=0) goto L44;
    goto L69;
    L44:
    if (c.invValueHud ==0) goto L55;
    goto L56;
    L55:
    L56:
    if (c.invValueInInventory ==0) goto L67;
    goto L68;
    L67:
    L68:
    L69:
    return (2 + 0);
}

private static boolean lambda$buildRows$17(config.FwmConfig c, Boolean v) {
    c.historyMergeSell = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$16(config.FwmConfig c) {
    return c.historyMergeSell;
}

private static network.chat.MutableComponent lambda$buildRows$15(int v) {
    if (v >0) goto L16;
    goto L31;
    L16:
    L31:
    return network.chat.Component.literal(v + ui.Lang.t(" Tage", " days"));
}

private static void lambda$buildRows$14(config.FwmConfig c, int v) {
    if (v <=0) goto L9;
    goto L10;
    L9:
    L10:
    1.historyEnabled = 0;
    if (v <=0) goto L22;
    c.historyDays = v;
    L22:
    return;
}

private static int lambda$buildRows$13(config.FwmConfig c) {
    if (c.historyEnabled ==0) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

private static boolean lambda$buildRows$12(config.FwmConfig c, Boolean v) {
    c.moneyGoalSound = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$11(config.FwmConfig c) {
    return c.moneyGoalSound;
}

private static network.chat.MutableComponent lambda$buildRows$10(long v) {
    if (cmp(v,0) >0) goto L18;
    goto L26;
    L18:
    L26:
    return network.chat.Component.literal(util.Fmt.compactMoney((v * 100)));
}

private static long lambda$buildRows$9(config.FwmConfig c, long v) {
    c.moneyGoal = Math.max(0, v);
    return;
}

private static long lambda$buildRows$8(config.FwmConfig c) {
    return c.moneyGoal;
}

private static boolean lambda$buildRows$7(config.FwmConfig c, Boolean v) {
    c.moneySidebarSync = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$6(config.FwmConfig c) {
    return c.moneySidebarSync;
}

private static boolean lambda$buildRows$5(config.FwmConfig c, Boolean v) {
    c.moneyHideAmounts = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$4(config.FwmConfig c) {
    return c.moneyHideAmounts;
}

private static boolean lambda$buildRows$3(config.FwmConfig c, Boolean v) {
    c.moneyHud = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$2(config.FwmConfig c) {
    return c.moneyHud;
}

private static void lambda$buildRows$1(Boolean v) {
    config.Features.set("fwm.tab.money", v.booleanValue());
    return;
}

private static boolean lambda$buildRows$0() {
    return config.Features.isOn("fwm.tab.money");
}

