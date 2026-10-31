// class net/friendswithmoney/fwm/stats/SellStats  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field WINDOW_MS J;
field MIN_SAMPLE_MS J;
field RATE_HOLD_MS J;
field TEST_TICKS I;
field TEST_GRACE_TICKS I;
field LOOKS_LIKE_MONEY Ljava/util/regex/Pattern;;
field pattern Ljava/util/regex/Pattern;;
field patternSource Ljava/lang/String;;
field warned Z;
field totalCents J;
field baseCents J;
field bonusCents J;
field sales I;
field itemsMoved I;
field sellButtonPresses I;
field activeMillis J;
field recent Ljava/util/Deque;;
field cachedRate J;
field cachedRateAt J;
field testTicksLeft I;
field testGraceLeft I;
field testBaseline J;
field testBaseBaseline J;
field testItemBaseline I;
field lastTestCents J;
field testAborted Z;
private void <init>() {
    return;
}

public static void acceptActionBar(network.chat.Component message) {
    stats.SellStats.read(message, 1);
    return;
}

public static void accept(network.chat.Component message) {
    stats.SellStats.read(message, 0);
    return;
}

private static void read(network.chat.Component message, boolean actionBar) {
    if (message == null) goto L12;
    if (config.Features.isOn("fwm.tab.sellmacro") !=0) goto L13;
    L12:
    return;
    L13:
    v2 = chat.ServerLine.plain(message);
    if (v2.isEmpty() ==0) goto L26;
    return;
    L26:
    if (actionBar ==0) goto L39;
    if (v2.startsWith("+") !=0) goto L47;
    L39:
    if (chat.ServerLine.fromServer(message) !=0) goto L47;
    return;
    L47:
    v3 = stats.SellStats.pattern().matcher(v2);
    if (v3.find() !=0) goto L80;
    if (stats.SellStats.LOOKS_LIKE_MONEY.matcher(v2).find() ==0) goto L79;
    stats.SellStats.remember(v2);
    L79:
    return;
    L80:
    v4 = stats.SellStats.cents(v3.group(1));
    if (v3.groupCount() < 3) goto L110;
    if (v3.group(2) == null) goto L110;
    goto L111;
    L110:
    L111:
    v6 = 0;
    if (v6 ==0) goto L129;
    goto L131;
    L129:
    L131:
    v7 = v4;
    if (v6 ==0) goto L149;
    goto L150;
    L149:
    L150:
    v9 = 0;
    if (cmp(v4,0) >0) goto L160;
    return;
    L160:
    new long[2][0] = System.currentTimeMillis();
    new long[2][1] = v4;
    stats.SellStats.recent.addLast(new long[2]);
    stats.SellStats.prune();
    stats.SellStats.totalCents = (stats.SellStats.totalCents + v4);
    stats.SellStats.baseCents = (stats.SellStats.baseCents + v7);
    stats.SellStats.bonusCents = (stats.SellStats.bonusCents + v9);
    stats.SellStats.sales = (stats.SellStats.sales + 1);
    config.FwmConfig.INSTANCE.lifetimeEarnedCents = (config.FwmConfig.INSTANCE.lifetimeEarnedCents + v4);
    stats.SellStats.record(v4);
    return;
}

private static void remember(String line) {
    if (config.FwmConfig.INSTANCE.enabled !=0) goto L10;
    return;
    L10:
    if (stats.SellStats.warned ==0) goto L17;
    return;
    L17:
    stats.SellStats.warned = 1;
    System.err.println('[FWM] A server line with an amount did not match sellMessagePattern, statistics may stay empty: ' + line);
    return;
}

private static void roll() {
    v0 = config.FwmConfig.INSTANCE;
    v1 = (System.currentTimeMillis() / 3600000);
    if (cmp(v0.hourlyBaseHour,0) !=0) goto L27;
    v0.hourlyBaseHour = v1;
    return;
    L27:
    v3 = (v1 - v0.hourlyBaseHour);
    if (cmp(v3,0) >0) goto L41;
    return;
    L41:
    v5 = new long[24];
    if (cmp(v3,24) >=0) goto L72;
    System.arraycopy(v0.hourlyEarnedCents, ((i)v3), v5, 0, (24 - ((i)v3)));
    L72:
    v0.hourlyEarnedCents = v5;
    v0.hourlyBaseHour = v1;
    return;
}

private static void record(long cents) {
    stats.SellStats.roll();
    v2 = config.FwmConfig.INSTANCE;
    v2.hourlyEarnedCents[23] = (v2.hourlyEarnedCents[23] + cents);
    return;
}

public static void last24h() {
    stats.SellStats.roll();
    v0 = 0;
    v2 = config.FwmConfig.INSTANCE.hourlyEarnedCents;
    v3 = v2.length;
    v4 = 0;
    L18:
    if (v4 >= v3) goto L41;
    v5 = v2[v4];
    v0 = (v0 + v5);
    v4 = v4 + 1;
    goto L18;
    L41:
    return v0;
}

public static void startMinuteTest() {
    stats.SellStats.testBaseline = stats.SellStats.totalCents;
    stats.SellStats.testBaseBaseline = stats.SellStats.baseCents;
    stats.SellStats.testItemBaseline = stats.SellStats.itemsMoved;
    stats.SellStats.lastTestCents = -1;
    stats.SellStats.testAborted = 0;
    stats.SellStats.testTicksLeft = 1200;
    return;
}

public static boolean testRunning() {
    if (stats.SellStats.testTicksLeft >0) goto L12;
    if (stats.SellStats.testGraceLeft <=0) goto L16;
    L12:
    goto L17;
    L16:
    L17:
    return 0;
}

public static boolean testSettling() {
    if (stats.SellStats.testTicksLeft >0) goto L16;
    if (stats.SellStats.testGraceLeft <=0) goto L16;
    goto L17;
    L16:
    L17:
    return 0;
}

public static int testSecondsLeft() {
    return ((stats.SellStats.testTicksLeft + 19) / 20);
}

public static long lastTest() {
    return stats.SellStats.lastTestCents;
}

public static void cancelTest() {
    if (stats.SellStats.testTicksLeft >0) goto L13;
    if (stats.SellStats.testGraceLeft >0) goto L13;
    return;
    L13:
    stats.SellStats.testTicksLeft = 0;
    stats.SellStats.testGraceLeft = 0;
    stats.SellStats.testAborted = 1;
    return;
}

public static boolean testAborted() {
    return stats.SellStats.testAborted;
}

public static boolean tickTest() {
    if (stats.SellStats.testTicksLeft >0) goto L8;
    return 0;
    L8:
    stats.SellStats.testTicksLeft = (stats.SellStats.testTicksLeft - 1);
    if ((stats.SellStats.testTicksLeft - 1) <=0) goto L22;
    return 0;
    L22:
    stats.SellStats.testGraceLeft = 120;
    return 1;
}

public static void tickGrace() {
    if (stats.SellStats.testGraceLeft >0) goto L8;
    return 0;
    L8:
    stats.SellStats.testGraceLeft = (stats.SellStats.testGraceLeft - 1);
    if ((stats.SellStats.testGraceLeft - 1) <=0) goto L22;
    return 0;
    L22:
    stats.SellStats.lastTestCents = (stats.SellStats.totalCents - stats.SellStats.testBaseline);
    stats.SellStats.save(stats.SellStats.lastTestCents, (stats.SellStats.baseCents - stats.SellStats.testBaseBaseline), (stats.SellStats.itemsMoved - stats.SellStats.testItemBaseline));
    return 1;
}

private static void save(long cents, long a1, int base) {
    v5 = config.FwmConfig.INSTANCE.tests;
    new Object[1][0] = Integer.valueOf((v5.size() + 1));
    v6 = ui.Lang.str("fwm.test.defaultName", new Object[1]);
    v5.add(?);
    L58:
    if (v5.size() <= 30) goto L82;
    v5.remove(0);
    goto L58;
    L82:
    config.ConfigManager.save();
    return;
}

private static Object prune() {
    v0 = (System.currentTimeMillis() - 600000);
    L8:
    if (stats.SellStats.recent.isEmpty() !=0) goto L49;
    if (cmp((([J)stats.SellStats.recent.peekFirst())[0],v0) >=0) goto L49;
    stats.SellStats.recent.removeFirst();
    goto L8;
    L49:
    return;
}

private static long cents(String amount) {
    return auction.Money.cents(amount);
}

private static regex.Pattern pattern() {
    v0 = config.FwmConfig.INSTANCE.sellMessagePattern;
    if (stats.SellStats.pattern == null) goto L23;
    if (v0.equals(stats.SellStats.patternSource) !=0) goto L62;
    L23:
    stats.SellStats.pattern = regex.Pattern.compile(v0);
    goto L58;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Bad sellMessagePattern, using the default: ' + v1.getMessage());
    stats.SellStats.pattern = regex.Pattern.compile("(?:^|\\]\\s?)\\+\\$([\\d.,]+)(?:\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+))?");
    L58:
    stats.SellStats.patternSource = v0;
    L62:
    return stats.SellStats.pattern;
}

public static void countItems(int amount) {
    stats.SellStats.itemsMoved = (stats.SellStats.itemsMoved + amount);
    return;
}

public static void countSellPress() {
    stats.SellStats.sellButtonPresses = (stats.SellStats.sellButtonPresses + 1);
    return;
}

public static void countActiveTick() {
    stats.SellStats.activeMillis = (stats.SellStats.activeMillis + 50);
    return;
}

public static long total() {
    return stats.SellStats.totalCents;
}

public static long base() {
    return stats.SellStats.baseCents;
}

public static long bonus() {
    return stats.SellStats.bonusCents;
}

public static int sales() {
    return stats.SellStats.sales;
}

public static int items() {
    return stats.SellStats.itemsMoved;
}

public static int sellPresses() {
    return stats.SellStats.sellButtonPresses;
}

public static long runtimeMillis() {
    return stats.SellStats.activeMillis;
}

public static long perHour() {
    v0 = System.currentTimeMillis();
    if (cmp(stats.SellStats.cachedRateAt,0) ==0) goto L28;
    if (cmp((v0 - stats.SellStats.cachedRateAt),60000) >=0) goto L28;
    return stats.SellStats.cachedRate;
    L28:
    v2 = stats.SellStats.computeRate();
    if (cmp(v2,0) >=0) goto L42;
    return -1;
    L42:
    stats.SellStats.cachedRate = v2;
    stats.SellStats.cachedRateAt = v0;
    return v2;
}

private static Object computeRate() {
    if (cmp(stats.SellStats.activeMillis,180000) >=0) goto L14;
    return -1;
    L14:
    stats.SellStats.prune();
    if (stats.SellStats.recent.isEmpty() ==0) goto L30;
    return 0;
    L30:
    v0 = Math.min(600000, stats.SellStats.activeMillis);
    v2 = 0;
    v4 = stats.SellStats.recent.iterator();
    L52:
    if (v4.hasNext() ==0) goto L84;
    v5 = (([J)v4.next());
    v2 = (v2 + v5[1]);
    goto L52;
    L84:
    return ((v2 * 3600000) / v0);
}

public static double multiplier() {
    if (cmp(stats.SellStats.baseCents,0) >0) goto L12;
    return -1.0;
    L12:
    return (((d)stats.SellStats.totalCents) / ((d)stats.SellStats.baseCents));
}

public static void reset() {
    stats.SellStats.totalCents = 0;
    stats.SellStats.baseCents = 0;
    stats.SellStats.bonusCents = 0;
    stats.SellStats.sales = 0;
    stats.SellStats.itemsMoved = 0;
    stats.SellStats.sellButtonPresses = 0;
    stats.SellStats.activeMillis = 0;
    stats.SellStats.recent.clear();
    stats.SellStats.cachedRate = 0;
    stats.SellStats.cachedRateAt = 0;
    stats.SellStats.testTicksLeft = 0;
    stats.SellStats.lastTestCents = -1;
    config.ConfigManager.save();
    return;
}

static void <clinit>() {
    stats.SellStats.LOOKS_LIKE_MONEY = regex.Pattern.compile("[$\u20ac]\\s?[\\d,]+");
    stats.SellStats.recent = ?;
    stats.SellStats.lastTestCents = -1;
    return;
}

