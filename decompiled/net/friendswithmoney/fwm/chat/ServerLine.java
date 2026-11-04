/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.Locale
 *  java.util.Optional
 *  java.util.Set
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.chat;

import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;

public final class ServerLine {
    private static final Set<String> SERVER_WORDS = Set.of((Object)"gedroppt", (Object)"geliefert", (Object)"achtung", (Object)"abgeholt", (Object)"verwendung", (Object)"sellmulti");
    private static final Pattern NAME_COLON = Pattern.compile((String)"^\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:");

    private ServerLine() {
    }

    public static String plain(Component message) {
        return message == null ? "" : ServerLine.strip(message.getString());
    }

    public static String strip(String line) {
        if (line == null || line.isEmpty()) {
            return "";
        }
        StringBuilder out = new StringBuilder(line.length());
        for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            if (c == '\u00a7' && i + 1 < line.length()) {
                ++i;
                continue;
            }
            if (ServerLine.invisible(c)) continue;
            out.append(c);
        }
        return out.toString();
    }

    private static boolean invisible(char c) {
        return c == '\u200b' || c == '\u200c' || c == '\u200d' || c == '\u2060' || c == '\ufeff';
    }

    public static boolean fromServer(Component message) {
        return ServerLine.tagged(message, ServerLine.prefix());
    }

    public static boolean tagged(Component message, String tag) {
        if (!ServerLine.startsColoured(message, tag)) {
            return false;
        }
        String rest = ServerLine.plain(message).substring(tag.length());
        Matcher m = NAME_COLON.matcher((CharSequence)rest);
        return !m.find() || SERVER_WORDS.contains((Object)m.group(1).toLowerCase(Locale.ROOT));
    }

    public static boolean startsColoured(Component message, String prefix) {
        if (message == null || prefix == null || prefix.isEmpty()) {
            return false;
        }
        if (!ServerLine.plain(message).startsWith(prefix)) {
            return false;
        }
        int[] seen = new int[]{0};
        boolean[] legacy = new boolean[]{false};
        Optional coloured = message.visit((style, part) -> {
            for (int i = 0; i < part.length(); ++i) {
                char c = part.charAt(i);
                if (c == '\u00a7' && i + 1 < part.length()) {
                    char code;
                    if ((code = Character.toLowerCase((char)part.charAt(++i))) == 'r') {
                        legacy[0] = false;
                        continue;
                    }
                    if (!(code >= '0' && code <= '9' || code >= 'a' && code <= 'f') && code != 'x') continue;
                    legacy[0] = true;
                    continue;
                }
                if (ServerLine.invisible(c)) continue;
                if (seen[0] >= prefix.length()) {
                    return Optional.of((Object)Boolean.TRUE);
                }
                if (Character.isLetter((char)c) && style.getColor() == null && !legacy[0]) {
                    return Optional.of((Object)Boolean.FALSE);
                }
                seen[0] = seen[0] + 1;
            }
            return seen[0] >= prefix.length() ? Optional.of((Object)Boolean.TRUE) : Optional.empty();
        }, Style.EMPTY);
        return (Boolean)coloured.orElse((Object)Boolean.FALSE);
    }

    private static String prefix() {
        String prefix = FwmConfig.INSTANCE.serverPrefix;
        return prefix == null || prefix.isBlank() ? "[HugoSMP]" : prefix.trim();
    }
}
