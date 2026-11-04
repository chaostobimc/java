/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 */
package net.friendswithmoney.fwm.ui;

import java.util.List;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;

public final class RankColors {
    private static final List<int[]> COLORS = List.of((Object[])new int[][]{{11122664, 8950998}, {8936667, 8936667}, {13628359, 8378767}, {15782522, 14267466}, {11190238, 11190238}, {12855654, 12855654}, {10498249, 10498249}, {2771578, 1319999}, {5144488, 2904691}, {14923013, 12092939}, {1853994, 795669}, {3116891, 1332013}, {3919994, 1483594}});
    private static final int[] UNKNOWN = new int[]{12175295, 12175295};
    private static final double MIN_LUMA = 0.43;

    private RankColors() {
    }

    public static int[] of(int rankIndex) {
        int[] stops = rankIndex < 0 || rankIndex >= COLORS.size() ? UNKNOWN : (int[])COLORS.get(rankIndex);
        return new int[]{RankColors.readable(stops[0]), RankColors.readable(stops[1])};
    }

    static int readable(int rgb) {
        int r = rgb >> 16 & 0xFF;
        int g = rgb >> 8 & 0xFF;
        int b = rgb & 0xFF;
        double luma = RankColors.luma(r, g, b);
        if (luma >= 0.43) {
            return rgb;
        }
        double t = (0.43 - luma) / (1.0 - luma);
        r = (int)Math.round((double)((double)r + (double)(255 - r) * t));
        g = (int)Math.round((double)((double)g + (double)(255 - g) * t));
        b = (int)Math.round((double)((double)b + (double)(255 - b) * t));
        return r << 16 | g << 8 | b;
    }

    static double luma(int r, int g, int b) {
        return (0.2126 * (double)r + 0.7152 * (double)g + 0.0722 * (double)b) / 255.0;
    }

    public static int flat(int rankIndex) {
        return 0xFF000000 | RankColors.of(rankIndex)[0];
    }

    public static MutableComponent gradient(String text, int rankIndex) {
        int[] stops = RankColors.of(rankIndex);
        if (text == null || text.isEmpty()) {
            return Component.empty();
        }
        if (stops[0] == stops[1]) {
            return RankColors.colored(text, stops[0]);
        }
        MutableComponent out = Component.empty();
        int last = Math.max((int)1, (int)(text.length() - 1));
        for (int i = 0; i < text.length(); ++i) {
            out.append((Component)RankColors.colored(String.valueOf((char)text.charAt(i)), RankColors.mix(stops[0], stops[1], (float)i / (float)last)));
        }
        return out;
    }

    private static MutableComponent colored(String text, int rgb) {
        return Component.literal((String)text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb((int)rgb)));
    }

    private static int mix(int from, int to, float t) {
        int r = Math.round((float)((float)(from >> 16 & 0xFF) + (float)((to >> 16 & 0xFF) - (from >> 16 & 0xFF)) * t));
        int g = Math.round((float)((float)(from >> 8 & 0xFF) + (float)((to >> 8 & 0xFF) - (from >> 8 & 0xFF)) * t));
        int b = Math.round((float)((float)(from & 0xFF) + (float)((to & 0xFF) - (from & 0xFF)) * t));
        return r << 16 | g << 8 | b;
    }
}
