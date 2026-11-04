/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Optional
 *  net.minecraft.ChatFormatting
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.chat;

import java.util.List;
import java.util.Optional;
import net.friendswithmoney.fwm.net.ClanTags;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class FakeTags {
    private FakeTags() {
    }

    public static Component mark(Component message) {
        if (message == null) {
            return null;
        }
        String flat = message.getString();
        if (flat == null || flat.isEmpty()) {
            return message;
        }
        List<int[]> spans = ClanTags.fakeSpans(flat);
        if (spans.isEmpty()) {
            return message;
        }
        MutableComponent out = Component.empty();
        int[] offset = new int[]{0};
        message.visit((style, string) -> {
            FakeTags.append(out, string, style, offset[0], spans);
            offset[0] = offset[0] + string.length();
            return Optional.empty();
        }, Style.EMPTY);
        return out;
    }

    public static String fakeIn(String line) {
        return ClanTags.fakeIn(line);
    }

    private static void append(MutableComponent out, String string, Style style, int offset, List<int[]> spans) {
        int position = 0;
        while (position < string.length()) {
            int absolute = offset + position;
            int[] covering = FakeTags.covering(absolute, spans);
            if (covering != null) {
                int end = Math.min((int)string.length(), (int)(covering[1] - offset));
                out.append((Component)Component.literal((String)string.substring(position, end)).setStyle(style.withColor(ChatFormatting.RED).withStrikethrough(Boolean.valueOf((boolean)true))));
                position = end;
                continue;
            }
            int next = Math.min((int)string.length(), (int)(FakeTags.nextStart(absolute, spans) - offset));
            out.append((Component)Component.literal((String)string.substring(position, next)).setStyle(style));
            position = next;
        }
    }

    private static int[] covering(int index, List<int[]> spans) {
        for (int[] span : spans) {
            if (index < span[0] || index >= span[1]) continue;
            return span;
        }
        return null;
    }

    private static int nextStart(int index, List<int[]> spans) {
        int best = Integer.MAX_VALUE;
        for (int[] span : spans) {
            if (span[0] <= index || span[0] >= best) continue;
            best = span[0];
        }
        return best;
    }
}
