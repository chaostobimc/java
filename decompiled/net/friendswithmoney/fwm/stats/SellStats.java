/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayDeque
 *  java.util.Deque
 *  java.util.List
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.stats;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.auction.Money;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.config.TestRun;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.network.chat.Component;

public final class SellStats {
    private static final long WINDOW_MS = 600000L;
    private static final long MIN_SAMPLE_MS = 180000L;
    private static final long RATE_HOLD_MS = 60000L;
    private static final int TEST_TICKS = 1200;
    private static final int TEST_GRACE_TICKS = 120;
    private static final Pattern LOOKS_LIKE_MONEY = Pattern.compile((String)"[$\u20ac]\\s?[\\d,]+");
    private static Pattern pattern;
    private static String patternSource;
    private static boolean warned;
    private static long totalCents;
    private static long baseCents;
    private static long bonusCents;
    private static int sales;
    private static int itemsMoved;
    private static int sellButtonPresses;
    private static long activeMillis;
    private static final Deque<long[]> recent;
    private static long cachedRate;
    private static long cachedRateAt;
    private static int testTicksLeft;
    private static int testGraceLeft;
    private static long testBaseline;
    private static long testBaseBaseline;
    private static int testItemBaseline;
    private static long lastTestCents;
    private static boolean testAborted;

    private SellStats() {
    }

    public static void acceptActionBar(Component message) {
        SellStats.read(message, true);
    }

    public static void accept(Component message) {
        SellStats.read(message, false);
    }

    private static void read(Component message, boolean actionBar) {
        long bonus;
        if (message == null || !Features.isOn("fwm.tab.sellmacro")) {
            return;
        }
        String line = ServerLine.plain(message);
        if (line.isEmpty()) {
            return;
        }
        if (!(actionBar && line.startsWith("+") || ServerLine.fromServer(message))) {
            return;
        }
        Matcher matcher = SellStats.pattern().matcher((CharSequence)line);
        if (!matcher.find()) {
            if (LOOKS_LIKE_MONEY.matcher((CharSequence)line).find()) {
                SellStats.remember(line);
            }
            return;
        }
        long total = SellStats.cents(matcher.group(1));
        boolean split = matcher.groupCount() >= 3 && matcher.group(2) != null;
        long base = split ? SellStats.cents(matcher.group(2)) : total;
        long l = bonus = split ? SellStats.cents(matcher.group(3)) : 0L;
        if (total <= 0L) {
            return;
        }
        recent.addLast((Object)new long[]{System.currentTimeMillis(), total});
        SellStats.prune();
        totalCents += total;
        baseCents += base;
        bonusCents += bonus;
        ++sales;
        FwmConfig.INSTANCE.lifetimeEarnedCents += total;
        SellStats.record(total);
    }

    private static void remember(String line) {
        if (!FwmConfig.INSTANCE.enabled) {
            return;
        }
        if (warned) {
            return;
        }
        warned = true;
        System.err.println("[FWM] A server line with an amount did not match sellMessagePattern, statistics may stay empty: " + line);
    }

    private static void roll() {
        FwmConfig config = FwmConfig.INSTANCE;
        long hour = System.currentTimeMillis() / 3600000L;
        if (config.hourlyBaseHour == 0L) {
            config.hourlyBaseHour = hour;
            return;
        }
        long shift = hour - config.hourlyBaseHour;
        if (shift <= 0L) {
            return;
        }
        long[] moved = new long[24];
        if (shift < 24L) {
            System.arraycopy((Object)config.hourlyEarnedCents, (int)((int)shift), (Object)moved, (int)0, (int)(24 - (int)shift));
        }
        config.hourlyEarnedCents = moved;
        config.hourlyBaseHour = hour;
    }

    private static void record(long cents) {
        SellStats.roll();
        FwmConfig config = FwmConfig.INSTANCE;
        config.hourlyEarnedCents[23] = config.hourlyEarnedCents[23] + cents;
    }

    public static long last24h() {
        SellStats.roll();
        long sum = 0L;
        for (long bucket : FwmConfig.INSTANCE.hourlyEarnedCents) {
            sum += bucket;
        }
        return sum;
    }

    public static void startMinuteTest() {
        testBaseline = totalCents;
        testBaseBaseline = baseCents;
        testItemBaseline = itemsMoved;
        lastTestCents = -1L;
        testAborted = false;
        testTicksLeft = 1200;
    }

    public static boolean testRunning() {
        return testTicksLeft > 0 || testGraceLeft > 0;
    }

    public static boolean testSettling() {
        return testTicksLeft <= 0 && testGraceLeft > 0;
    }

    public static int testSecondsLeft() {
        return (testTicksLeft + 19) / 20;
    }

    public static long lastTest() {
        return lastTestCents;
    }

    public static void cancelTest() {
        if (testTicksLeft <= 0 && testGraceLeft <= 0) {
            return;
        }
        testTicksLeft = 0;
        testGraceLeft = 0;
        testAborted = true;
    }

    public static boolean testAborted() {
        return testAborted;
    }

    public static boolean tickTest() {
        if (testTicksLeft <= 0) {
            return false;
        }
        if (--testTicksLeft > 0) {
            return false;
        }
        testGraceLeft = 120;
        return true;
    }

    public static boolean tickGrace() {
        if (testGraceLeft <= 0) {
            return false;
        }
        if (--testGraceLeft > 0) {
            return false;
        }
        lastTestCents = totalCents - testBaseline;
        SellStats.save(lastTestCents, baseCents - testBaseBaseline, itemsMoved - testItemBaseline);
        return true;
    }

    private static void save(long cents, long base, int items) {
        List<TestRun> tests = FwmConfig.INSTANCE.tests;
        String name = Lang.str("fwm.test.defaultName", tests.size() + 1);
        tests.add((Object)new TestRun(name, cents, base, items, System.currentTimeMillis()));
        while (tests.size() > 30) {
            tests.remove(0);
        }
        ConfigManager.save();
    }

    private static void prune() {
        long cutoff = System.currentTimeMillis() - 600000L;
        while (!recent.isEmpty() && ((long[])recent.peekFirst())[0] < cutoff) {
            recent.removeFirst();
        }
    }

    private static long cents(String amount) {
        return Money.cents(amount);
    }

    private static Pattern pattern() {
        String source = FwmConfig.INSTANCE.sellMessagePattern;
        if (pattern == null || !source.equals((Object)patternSource)) {
            try {
                pattern = Pattern.compile((String)source);
            }
            catch (Exception e) {
                System.err.println("[FWM] Bad sellMessagePattern, using the default: " + e.getMessage());
                pattern = Pattern.compile((String)"(?:^|\\]\\s?)\\+\\$([\\d.,]+)(?:\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+))?");
            }
            patternSource = source;
        }
        return pattern;
    }

    public static void countItems(int amount) {
        itemsMoved += amount;
    }

    public static void countSellPress() {
        ++sellButtonPresses;
    }

    public static void countActiveTick() {
        activeMillis += 50L;
    }

    public static long total() {
        return totalCents;
    }

    public static long base() {
        return baseCents;
    }

    public static long bonus() {
        return bonusCents;
    }

    public static int sales() {
        return sales;
    }

    public static int items() {
        return itemsMoved;
    }

    public static int sellPresses() {
        return sellButtonPresses;
    }

    public static long runtimeMillis() {
        return activeMillis;
    }

    public static long perHour() {
        long now = System.currentTimeMillis();
        if (cachedRateAt != 0L && now - cachedRateAt < 60000L) {
            return cachedRate;
        }
        long rate = SellStats.computeRate();
        if (rate < 0L) {
            return -1L;
        }
        cachedRate = rate;
        cachedRateAt = now;
        return rate;
    }

    private static long computeRate() {
        if (activeMillis < 180000L) {
            return -1L;
        }
        SellStats.prune();
        if (recent.isEmpty()) {
            return 0L;
        }
        long span = Math.min((long)600000L, (long)activeMillis);
        long sum = 0L;
        for (long[] sale : recent) {
            sum += sale[1];
        }
        return sum * 3600000L / span;
    }

    public static double multiplier() {
        if (baseCents <= 0L) {
            return -1.0;
        }
        return (double)totalCents / (double)baseCents;
    }

    public static void reset() {
        totalCents = 0L;
        baseCents = 0L;
        bonusCents = 0L;
        sales = 0;
        itemsMoved = 0;
        sellButtonPresses = 0;
        activeMillis = 0L;
        recent.clear();
        cachedRate = 0L;
        cachedRateAt = 0L;
        testTicksLeft = 0;
        lastTestCents = -1L;
        ConfigManager.save();
    }

    static {
        recent = new ArrayDeque();
        lastTestCents = -1L;
    }
}
