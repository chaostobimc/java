// class net/friendswithmoney/fwm/ui/panel/SpawnerHudPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field open Ljava/lang/String;;
public void <init>() {
    this.open = "prices";
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.spawnerhud", new Object[0]);
}

private static network.chat.MutableComponent t(String de, String en) {
    return network.chat.Component.literal(ui.Lang.t(de, en));
}

private boolean heading(String de, String en) {
    this.rows.add(?);
    return;
}

private boolean toggle(String de, String en, function.BooleanSupplier get, function.Consumer set) {
    this.rows.add(?);
    return;
}

private boolean slider(String de, String en, int min, int max, function.IntSupplier get, function.IntConsumer set, String unit) {
    this.rows.add(?);
    return;
}

private boolean color(String de, String en, function.IntSupplier get, function.IntConsumer set) {
    this.rows.add(?);
    return;
}

private boolean words(String de, String en, function.Supplier get, function.Consumer set, String empty) {
    this.rows.add(?);
    return;
}

private boolean section(String id, String de, String en, String hintDe, String hintEn) {
    this.rows.add(?);
    return;
}

protected void buildRows() {
    v1 = config.FwmConfig.INSTANCE;
    this.toggle("Anzeige im Spiel", "Show in game", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.rows.add(?);
    v2 = net.Spawners.data();
    if (v2 == null) goto L73;
    if (v2.items != null) goto L79;
    L73:
    goto L86;
    L79:
    L86:
    v3 = ui.panel.SpawnerPanel.inShopOrder(v2.items);
    this.section("prices", "Eigene Preise", "Own prices", "leer = Preis der Website", "empty = website price");
    if ("prices".equals(this.open) ==0) goto L222;
    if (v3.isEmpty() ==0) goto L130;
    this.heading("Spawner werden geladen \u2026", "Loading spawners \u2026");
    L130:
    v4 = v3.iterator();
    L138:
    if (v4.hasNext() ==0) goto L222;
    v5 = ((net.SpawnerData$Item)v4.next());
    v6 = v5.name;
    this.rows.add(?);
    goto L138;
    L222:
    this.section("look", "Aussehen", "Look", "Stil, Hintergrund, Rahmen", "style, background, border");
    if ("look".equals(this.open) ==0) goto L582;
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    this.rows.add(?);
    L582:
    this.section("content", "Inhalt", "Content", "Titel, Spalten, Bilder", "title, columns, pictures");
    if ("content".equals(this.open) ==0) goto L808;
    this.words("Titel", "Title", /*indy*/get(v1), /*indy*/accept(v1), ui.Lang.t("kein Titel", "no title"));
    this.toggle("Kopfzeile", "Header", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.words("Spalte Name", "Name column", /*indy*/get(v1), /*indy*/accept(v1), "Spawner");
    this.words("Spalte Kauf", "Buy column", /*indy*/get(v1), /*indy*/accept(v1), "Kauf");
    this.words("Spalte Verkauf", "Sell column", /*indy*/get(v1), /*indy*/accept(v1), "Verkauf");
    this.toggle("Kauf-Preise", "Buy prices", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.toggle("Verkauf-Preise", "Sell prices", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.toggle("Bilder der Spawner", "Spawner pictures", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    L808:
    this.section("spawners", "Spawner ausw\u00e4hlen", "Choose spawners", (v3.size() - v1.spawnerHudHidden.size()) + ' von ' + v3.size(), (v3.size() - v1.spawnerHudHidden.size()) + ' of ' + v3.size());
    if ("spawners".equals(this.open) ==0) goto L966;
    v4 = v3.iterator();
    L896:
    if (v4.hasNext() ==0) goto L966;
    v5 = ((net.SpawnerData$Item)v4.next());
    v6 = v5.name;
    this.rows.add(?);
    goto L896;
    L966:
    this.section("colors", "Farben", "Colours", "Kauf, Verkauf, Text \u2026", "buy, sell, text \u2026");
    if ("colors".equals(this.open) ==0) goto L1130;
    this.color("Kauf", "Buy", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    this.color("Verkauf", "Sell", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    this.color("Namen", "Names", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    this.color("Kopfzeile", "Header", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    this.color("Hintergrund", "Background", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    this.color("Rahmen", "Border", /*indy*/getAsInt(v1), /*indy*/accept(v1));
    L1130:
    this.section("fine", "Feinschliff", "Fine-tuning", "Deckkraft, Ecken, Abst\u00e4nde \u2026", "opacity, corners, spacing \u2026");
    if ("fine".equals(this.open) ==0) goto L1419;
    this.slider("Deckkraft", "Opacity", 10, 100, /*indy*/getAsInt(v1), /*indy*/accept(v1), " %");
    this.slider("Ecken", "Corners", 0, 12, /*indy*/getAsInt(v1), /*indy*/accept(v1), " px");
    this.slider("Innenabstand", "Padding", 2, 16, /*indy*/getAsInt(v1), /*indy*/accept(v1), " px");
    this.slider("Zeilenh\u00f6he", "Row height", 10, 20, /*indy*/getAsInt(v1), /*indy*/accept(v1), " px");
    this.slider("Rahmenst\u00e4rke", "Border width", 1, 3, /*indy*/getAsInt(v1), /*indy*/accept(v1), " px");
    this.slider("Tempo Farbverlauf", "Gradient speed", 1, 10, /*indy*/getAsInt(v1), /*indy*/accept(v1), "");
    this.toggle("Schatten unter der Karte", "Shadow under the card", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.toggle("Textschatten", "Text shadow", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.toggle("Trennlinie", "Divider line", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    this.toggle("Kopfzeile fett und farbig", "Header bold and coloured", /*indy*/getAsBoolean(v1), /*indy*/accept(v1));
    L1419:
    return;
}

private static boolean lambda$buildRows$65(config.FwmConfig c, Boolean v) {
    c.spawnerHudHeadBold = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$64(config.FwmConfig c) {
    return c.spawnerHudHeadBold;
}

private static boolean lambda$buildRows$63(config.FwmConfig c, Boolean v) {
    c.spawnerHudDivider = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$62(config.FwmConfig c) {
    return c.spawnerHudDivider;
}

private static boolean lambda$buildRows$61(config.FwmConfig c, Boolean v) {
    c.spawnerHudTextShadow = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$60(config.FwmConfig c) {
    return c.spawnerHudTextShadow;
}

private static boolean lambda$buildRows$59(config.FwmConfig c, Boolean v) {
    c.spawnerHudShadow = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$58(config.FwmConfig c) {
    return c.spawnerHudShadow;
}

private static void lambda$buildRows$57(config.FwmConfig c, int v) {
    c.spawnerHudGradientSpeed = v;
    return;
}

private static int lambda$buildRows$56(config.FwmConfig c) {
    return c.spawnerHudGradientSpeed;
}

private static void lambda$buildRows$55(config.FwmConfig c, int v) {
    c.spawnerHudBorderWidth = v;
    return;
}

private static int lambda$buildRows$54(config.FwmConfig c) {
    return c.spawnerHudBorderWidth;
}

private static void lambda$buildRows$53(config.FwmConfig c, int v) {
    c.spawnerHudRowGap = v;
    return;
}

private static int lambda$buildRows$52(config.FwmConfig c) {
    return c.spawnerHudRowGap;
}

private static void lambda$buildRows$51(config.FwmConfig c, int v) {
    c.spawnerHudPadding = v;
    return;
}

private static int lambda$buildRows$50(config.FwmConfig c) {
    return c.spawnerHudPadding;
}

private static void lambda$buildRows$49(config.FwmConfig c, int v) {
    c.spawnerHudRadius = v;
    return;
}

private static int lambda$buildRows$48(config.FwmConfig c) {
    return c.spawnerHudRadius;
}

private static void lambda$buildRows$47(config.FwmConfig c, int v) {
    c.spawnerHudOpacity = v;
    return;
}

private static int lambda$buildRows$46(config.FwmConfig c) {
    return c.spawnerHudOpacity;
}

private static void lambda$buildRows$45(config.FwmConfig c, int v) {
    c.spawnerHudBorderColor = v;
    return;
}

private static int lambda$buildRows$44(config.FwmConfig c) {
    return c.spawnerHudBorderColor;
}

private static void lambda$buildRows$43(config.FwmConfig c, int v) {
    c.spawnerHudBgColor = v;
    return;
}

private static int lambda$buildRows$42(config.FwmConfig c) {
    return c.spawnerHudBgColor;
}

private static void lambda$buildRows$41(config.FwmConfig c, int v) {
    c.spawnerHudHeadColor = v;
    return;
}

private static int lambda$buildRows$40(config.FwmConfig c) {
    return c.spawnerHudHeadColor;
}

private static void lambda$buildRows$39(config.FwmConfig c, int v) {
    c.spawnerHudTextColor = v;
    return;
}

private static int lambda$buildRows$38(config.FwmConfig c) {
    return c.spawnerHudTextColor;
}

private static void lambda$buildRows$37(config.FwmConfig c, int v) {
    c.spawnerHudSellColor = v;
    return;
}

private static int lambda$buildRows$36(config.FwmConfig c) {
    return c.spawnerHudSellColor;
}

private static void lambda$buildRows$35(config.FwmConfig c, int v) {
    c.spawnerHudBuyColor = v;
    return;
}

private static int lambda$buildRows$34(config.FwmConfig c) {
    return c.spawnerHudBuyColor;
}

private static void lambda$buildRows$33(config.FwmConfig c, String name, Boolean v) {
    if (v.booleanValue() ==0) goto L21;
    c.spawnerHudHidden.remove(name);
    goto L45;
    L21:
    if (c.spawnerHudHidden.contains(name) !=0) goto L45;
    c.spawnerHudHidden.add(name);
    L45:
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$32(config.FwmConfig c, String name) {
    if (c.spawnerHudHidden.contains(name) !=0) goto L17;
    goto L18;
    L17:
    L18:
    return 0;
}

private static boolean lambda$buildRows$31(config.FwmConfig c, Boolean v) {
    c.spawnerHudIcons = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$30(config.FwmConfig c) {
    return c.spawnerHudIcons;
}

private static boolean lambda$buildRows$29(config.FwmConfig c, Boolean v) {
    c.spawnerHudShowSell = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$28(config.FwmConfig c) {
    return c.spawnerHudShowSell;
}

private static boolean lambda$buildRows$27(config.FwmConfig c, Boolean v) {
    c.spawnerHudShowBuy = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$26(config.FwmConfig c) {
    return c.spawnerHudShowBuy;
}

private static void lambda$buildRows$25(config.FwmConfig c, String v) {
    c.spawnerHudLabelSell = v;
    return;
}

private static String lambda$buildRows$24(config.FwmConfig c) {
    return c.spawnerHudLabelSell;
}

private static void lambda$buildRows$23(config.FwmConfig c, String v) {
    c.spawnerHudLabelBuy = v;
    return;
}

private static String lambda$buildRows$22(config.FwmConfig c) {
    return c.spawnerHudLabelBuy;
}

private static void lambda$buildRows$21(config.FwmConfig c, String v) {
    c.spawnerHudLabelName = v;
    return;
}

private static String lambda$buildRows$20(config.FwmConfig c) {
    return c.spawnerHudLabelName;
}

private static boolean lambda$buildRows$19(config.FwmConfig c, Boolean v) {
    c.spawnerHudHeader = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$18(config.FwmConfig c) {
    return c.spawnerHudHeader;
}

private static void lambda$buildRows$17(config.FwmConfig c, String v) {
    c.spawnerHudTitle = v;
    return;
}

private static String lambda$buildRows$16(config.FwmConfig c) {
    return c.spawnerHudTitle;
}

private static void lambda$buildRows$15(config.FwmConfig c, int v) {
    if (v != 1) goto L10;
    goto L11;
    L10:
    L11:
    1.spawnerHudGrid = 0;
    config.ConfigManager.save();
    return;
}

private static int lambda$buildRows$14(config.FwmConfig c) {
    if (c.spawnerHudGrid ==0) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

private static void lambda$buildRows$13(config.FwmConfig c, int v) {
    c.spawnerHudBorder = v;
    config.ConfigManager.save();
    return;
}

private static int lambda$buildRows$12(config.FwmConfig c) {
    return c.spawnerHudBorder;
}

private static void lambda$buildRows$11(config.FwmConfig c, int v) {
    c.spawnerHudBg = v;
    config.ConfigManager.save();
    return;
}

private static int lambda$buildRows$10(config.FwmConfig c) {
    return c.spawnerHudBg;
}

private static void lambda$buildRows$9(int v) {
    ui.SpawnerCard.applyStyle(v);
    config.ConfigManager.save();
    return;
}

private static int lambda$buildRows$8(config.FwmConfig c) {
    return c.spawnerHudStyle;
}

private static void lambda$buildRows$7(config.FwmConfig c, String name, String[] v) {
    if (v[0] == null) goto L15;
    if (v[0].isBlank() ==0) goto L34;
    L15:
    if (v[1] == null) goto L30;
    if (v[1].isBlank() ==0) goto L34;
    L30:
    goto L35;
    L34:
    L35:
    v3 = 0;
    if (v3 ==0) goto L54;
    c.spawnerHudPrices.remove(name);
    goto L66;
    L54:
    c.spawnerHudPrices.put(name, v);
    L66:
    config.ConfigManager.save();
    return;
}

private static Object lambda$buildRows$6(config.FwmConfig c, String name) {
    return (([Ljava.lang.String;)c.spawnerHudPrices.get(name));
}

private static String lambda$buildRows$5(net.SpawnerData$Item item) {
    return item.sellPrice;
}

private static String lambda$buildRows$4(net.SpawnerData$Item item) {
    return item.buyPrice;
}

private static void lambda$buildRows$3() {
    client.Minecraft.getInstance().gui.setScreen(?);
    return;
}

private static network.chat.Component lambda$buildRows$2() {
    return ui.panel.SpawnerHudPanel.t("HUD anpassen", "Adjust HUD");
}

private static boolean lambda$buildRows$1(config.FwmConfig c, Boolean v) {
    c.spawnerHudCard = v.booleanValue();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig c) {
    return c.spawnerHudCard;
}

private void lambda$section$1(String id) {
    if (id.equals(this.open) ==0) goto L18;
    goto L19;
    L18:
    L19:
    "".open = id;
    this.onResize();
    return;
}

private boolean lambda$section$0(String id) {
    return id.equals(this.open);
}

private static void lambda$words$0(function.Consumer set, String v) {
    set.accept(v);
    config.ConfigManager.save();
    return;
}

private static void lambda$color$0(function.IntConsumer set, int v) {
    set.accept(v);
    config.ConfigManager.save();
    return;
}

private static network.chat.MutableComponent lambda$slider$1(String unit, int v) {
    return network.chat.Component.literal(v + unit);
}

private static void lambda$slider$0(function.IntConsumer set, int v) {
    set.accept(v);
    config.ConfigManager.save();
    return;
}

private static void lambda$toggle$0(function.Consumer set, Boolean v) {
    set.accept(v);
    config.ConfigManager.save();
    return;
}

