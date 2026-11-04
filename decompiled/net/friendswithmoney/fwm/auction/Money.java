/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 */
package net.friendswithmoney.fwm.auction;

public final class Money {
    private Money() {
    }

    public static long cents(String text) {
        double cents;
        int i;
        if (text == null || text.isEmpty()) {
            return 0L;
        }
        StringBuilder digits = new StringBuilder();
        int decimals = -1;
        for (i = 0; i < text.length() && !Character.isDigit((char)text.charAt(i)); ++i) {
        }
        while (i < text.length()) {
            char c = text.charAt(i);
            if (Character.isDigit((char)c)) {
                digits.append(c);
                if (decimals >= 0) {
                    ++decimals;
                }
                ++i;
                continue;
            }
            if (c != ',' && c != '.' || decimals >= 0 || i + 1 >= text.length() || !Character.isDigit((char)text.charAt(i + 1))) break;
            int run = 0;
            while (i + 1 + run < text.length() && Character.isDigit((char)text.charAt(i + 1 + run))) {
                ++run;
            }
            if (run != 3) {
                decimals = 0;
            }
            ++i;
        }
        if (digits.isEmpty() || digits.length() > 15) {
            return 0L;
        }
        long multiplier = 1L;
        while (i < text.length() && text.charAt(i) == ' ') {
            ++i;
        }
        if (i < text.length()) {
            char unit = Character.toLowerCase((char)text.charAt(i));
            if (unit == 'k') {
                multiplier = 1000L;
            } else if (unit == 'm') {
                multiplier = 1000000L;
            } else if (unit == 'b') {
                multiplier = 1000000000L;
            }
        }
        double value = Double.parseDouble((String)digits.toString());
        if (decimals > 0) {
            value /= Math.pow((double)10.0, (double)decimals);
        }
        return (cents = value * (double)multiplier * 100.0) > 4.611686018427388E18 ? 0L : Math.round((double)cents);
    }
}
