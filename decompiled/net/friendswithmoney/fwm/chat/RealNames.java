/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Optional
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.chat;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.net.FakeList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class RealNames {
    private static final String INVISIBLE = "[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*";
    private static final String NAME = "([.*]?[A-Za-z0-9_]{3,16})";
    private static final Pattern CHAT = Pattern.compile((String)"^(?:\\[[^\\]]{1,12}\\]\\s+)?(?:PREMIUM\\s+)?[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*:\\s");
    private static final Pattern FROM = Pattern.compile((String)"^\\[Nachricht\\]\\s+[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*\\s+->\\s+Du:");
    private static final Pattern TO = Pattern.compile((String)"^\\[Nachricht\\]\\s+Du\\s+->\\s+[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*([.*]?[A-Za-z0-9_]{3,16})[\\u200B\\u200C\\u200D\\u2060\\uFEFF]*:");

    private RealNames() {
    }

    public static Component mark(Component message) {
        if (message == null) {
            return null;
        }
        String flat = message.getString();
        if (flat == null || flat.isEmpty()) {
            return message;
        }
        Matcher m = RealNames.match(flat);
        if (m == null) {
            return message;
        }
        Component badge = FakeList.realBadge(m.group(1));
        if (badge == null) {
            return message;
        }
        return RealNames.insertAt(message, m.end(1), badge);
    }

    private static Matcher match(String flat) {
        for (Pattern pattern : new Pattern[]{FROM, TO, CHAT}) {
            Matcher m = pattern.matcher((CharSequence)flat);
            if (!m.find()) continue;
            return m;
        }
        return null;
    }

    private static Component insertAt(Component message, int at, Component insert) {
        MutableComponent out = Component.empty();
        int[] offset = new int[]{0};
        boolean[] done = new boolean[]{false};
        message.visit((style, string) -> {
            int start = offset[0];
            int end = start + string.length();
            if (!done[0] && at >= start && at <= end) {
                int cut = at - start;
                if (cut > 0) {
                    out.append((Component)Component.literal((String)string.substring(0, cut)).setStyle(style));
                }
                out.append(insert);
                if (cut < string.length()) {
                    out.append((Component)Component.literal((String)string.substring(cut)).setStyle(style));
                }
                done[0] = true;
            } else {
                out.append((Component)Component.literal((String)string).setStyle(style));
            }
            offset[0] = end;
            return Optional.empty();
        }, Style.EMPTY);
        if (!done[0]) {
            out.append(insert);
        }
        return out;
    }
}
