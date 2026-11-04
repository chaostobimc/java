/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Locale
 */
package net.friendswithmoney.fwm.util;

import java.util.Locale;

public final class Fmt {
    private static final String[] UNITS = new String[]{"K", "M", "B", "T"};

    private Fmt() {
    }

    public static String shortMoney(long cents) {
        String sign;
        boolean negative = cents < 0L;
        long dollars = Math.abs((long)cents) / 100L;
        String string = sign = negative ? "-" : "";
        if (dollars < 1000L) {
            return sign + dollars;
        }
        long[] steps = new long[]{1000000000000L, 1000000000L, 1000000L, 1000L};
        String[] units = new String[]{"T", "B", "M", "K"};
        for (int i = 0; i < steps.length; ++i) {
            if (dollars < steps[i]) continue;
            long whole = dollars / steps[i];
            long hundredths = dollars % steps[i] * 100L / steps[i];
            String text = Long.toString((long)whole);
            if (hundredths > 0L) {
                String frac = String.format((Locale)Locale.ROOT, (String)"%02d", (Object[])new Object[]{hundredths});
                if (frac.endsWith("0")) {
                    frac = frac.substring(0, 1);
                }
                text = text + "," + frac;
            }
            return sign + text + units[i];
        }
        return sign + dollars;
    }

    public static String fullMoney(long cents) {
        String de = Fmt.moneyDe(cents);
        return de.endsWith(",00") ? de.substring(0, de.length() - 3) : de;
    }

    public static String money(long cents) {
        boolean negative = cents < 0L;
        long abs = Math.abs((long)cents);
        String whole = Fmt.group(abs / 100L);
        String fraction = String.format((Locale)Locale.ROOT, (String)"%02d", (Object[])new Object[]{abs % 100L});
        return (negative ? "-$" : "$") + whole + "." + fraction;
    }

    public static String moneyDe(long cents) {
        boolean negative = cents < 0L;
        long abs = Math.abs((long)cents);
        String whole = Fmt.group(abs / 100L, '.');
        String fraction = String.format((Locale)Locale.ROOT, (String)"%02d", (Object[])new Object[]{abs % 100L});
        return (negative ? "-$" : "$") + whole + "," + fraction;
    }

    public static String compactMoney(long cents) {
        return "$" + Fmt.compact(cents / 100L);
    }

    public static String compactMoneyUp(long cents) {
        boolean negative = cents < 0L;
        long dollars = (Math.abs((long)cents) + 99L) / 100L;
        return (negative ? "-$" : "$") + Fmt.compactUp(dollars);
    }

    public static String compactUp(long value) {
        int unit;
        if (value < 1000L) {
            return String.valueOf((long)value);
        }
        double scaled = value;
        for (unit = -1; scaled >= 1000.0 && unit < UNITS.length - 1; scaled /= 1000.0, ++unit) {
        }
        double up = Math.ceil((double)(scaled * 10.0)) / 10.0;
        if (up >= 1000.0 && unit < UNITS.length - 1) {
            up /= 1000.0;
            ++unit;
        }
        String number = up == Math.floor((double)up) ? String.valueOf((long)((long)up)) : String.format((Locale)Locale.ROOT, (String)"%.1f", (Object[])new Object[]{up});
        return number + UNITS[unit];
    }

    public static String compact(long value) {
        long abs = Math.abs((long)value);
        if (abs < 10000L) {
            return String.valueOf((long)value);
        }
        if (abs < 1000000L) {
            return Fmt.trim((double)value / 1000.0) + "K";
        }
        if (abs < 1000000000L) {
            return Fmt.trim((double)value / 1000000.0) + "M";
        }
        return Fmt.trim((double)value / 1.0E9) + "B";
    }

    public static String playtime(long minutes) {
        long days = minutes / 1440L;
        long hours = minutes % 1440L / 60L;
        long mins = minutes % 60L;
        if (days > 0L) {
            return days + "d " + hours + "h";
        }
        if (hours > 0L) {
            return hours + "h " + mins + "m";
        }
        return mins + "m";
    }

    public static String duration(long millis) {
        long seconds = millis / 1000L;
        if (seconds < 60L) {
            return seconds + "s";
        }
        return Fmt.playtime(seconds / 60L);
    }

    /*
     * Exception decompiling
     */
    public static String compactBoardValue(Double num, String kind) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1802596)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4489513)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4491475)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820062)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820183)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1024810)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4466968)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:604767)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4234497)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static String trim(double value) {
        return String.format((Locale)Locale.ROOT, (String)"%.1f", (Object[])new Object[]{value});
    }

    public static String dollars(long cents) {
        return "$" + Fmt.group(cents / 100L);
    }

    private static String group(long value) {
        return Fmt.group(value, ',');
    }

    private static String group(long value, char separator) {
        String digits = String.valueOf((long)value);
        StringBuilder out = new StringBuilder();
        for (int i = 0; i < digits.length(); ++i) {
            if (i > 0 && (digits.length() - i) % 3 == 0) {
                out.append(separator);
            }
            out.append(digits.charAt(i));
        }
        return out.toString();
    }
}
