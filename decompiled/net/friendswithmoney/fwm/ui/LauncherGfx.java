/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import net.friendswithmoney.fwm.ui.FwmIcons;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import org.joml.Matrix3x2fStack;

public final class LauncherGfx {
    private LauncherGfx() {
    }

    private static int inset(int row, int h, int r) {
        int fromEdge = Math.min((int)row, (int)(h - 1 - row));
        if (fromEdge >= r) {
            return 0;
        }
        double dy = (double)(r - fromEdge) - 0.5;
        return (int)Math.round((double)((double)r - Math.sqrt((double)Math.max((double)0.0, (double)((double)r * (double)r - dy * dy)))));
    }

    public static void round(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int color) {
        if (w <= 0 || h <= 0) {
            return;
        }
        r = Math.max((int)0, (int)Math.min((int)r, (int)(Math.min((int)w, (int)h) / 2)));
        for (int row = 0; row < h; ++row) {
            if (row >= r && row < h - r) {
                ctx.fill(x, y + row, x + w, y + h - r, color);
                row = h - r - 1;
                continue;
            }
            int in = LauncherGfx.inset(row, h, r);
            ctx.fill(x + in, y + row, x + w - in, y + row + 1, color);
        }
    }

    public static void card(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r) {
        LauncherGfx.roundGradient(ctx, x, y, w, h, r, FwmTheme.GLASS_TOP, FwmTheme.GLASS_BOTTOM);
        LauncherGfx.ring(ctx, x, y, w, h, r, FwmTheme.EDGE);
        ctx.fill(x + r, y + 1, x + w - r, y + 2, FwmTheme.SHEEN);
    }

    public static void roundGradient(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int top, int bottom) {
        if (w <= 0 || h <= 0) {
            return;
        }
        r = Math.max((int)0, (int)Math.min((int)r, (int)(Math.min((int)w, (int)h) / 2)));
        for (int row = 0; row < h; ++row) {
            float t = h <= 1 ? 0.0f : (float)row / (float)(h - 1);
            int in = LauncherGfx.inset(row, h, r);
            ctx.fill(x + in, y + row, x + w - in, y + row + 1, LauncherGfx.mix(top, bottom, t));
        }
    }

    private static int mix(int a, int b, float t) {
        int out = 0;
        for (int shift = 0; shift <= 24; shift += 8) {
            int ca = a >>> shift & 0xFF;
            int cb = b >>> shift & 0xFF;
            out |= (Math.round((float)((float)ca + (float)(cb - ca) * t)) & 0xFF) << shift;
        }
        return out;
    }

    public static void infoGlyph(GuiGraphicsExtractor ctx, int x, int y, int color) {
        ctx.fill(x + 6, y + 3, x + 8, y + 5, color);
        ctx.fill(x + 6, y + 6, x + 8, y + 11, color);
    }

    public static void ring(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int r, int color) {
        if (w <= 1 || h <= 1) {
            return;
        }
        r = Math.max((int)0, (int)Math.min((int)r, (int)(Math.min((int)w, (int)h) / 2)));
        int ri = Math.max((int)0, (int)(r - 1));
        for (int row = 0; row < h; ++row) {
            int out = LauncherGfx.inset(row, h, r);
            if (row == 0 || row == h - 1) {
                ctx.fill(x + out, y + row, x + w - out, y + row + 1, color);
                continue;
            }
            int inner = 1 + LauncherGfx.inset(row - 1, h - 2, ri);
            int left = Math.max((int)inner, (int)(out + 1));
            ctx.fill(x + out, y + row, x + left, y + row + 1, color);
            ctx.fill(x + w - left, y + row, x + w - out, y + row + 1, color);
        }
    }

    public static void toggle(GuiGraphicsExtractor ctx, int x, int y, boolean on, int onColor, int offColor) {
        int w = 22;
        int h = 12;
        LauncherGfx.round(ctx, x, y, w, h, h / 2, on ? onColor : offColor);
        int knob = h - 2;
        int kx = on ? x + w - knob - 1 : x + 1;
        LauncherGfx.round(ctx, kx, y + 2, knob, knob, knob / 2, 0x33000000);
        LauncherGfx.round(ctx, kx, y + 1, knob, knob, knob / 2, -1);
    }

    public static void icon(GuiGraphicsExtractor ctx, String key, int x, int y, float scale, int color) {
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)x, (float)y);
        m.scale(scale, scale);
        FwmIcons.draw(ctx, key, 0, 0, color);
        m.popMatrix();
    }

    public static void text(GuiGraphicsExtractor ctx, Font font, String s, int x, int y, int color) {
        ctx.text(font, (Component)Component.literal((String)s), x, y, color, false);
    }

    public static void bold(GuiGraphicsExtractor ctx, Font font, String s, int x, int y, int color) {
        ctx.text(font, (Component)Component.literal((String)s).withStyle(ChatFormatting.BOLD), x, y, color, false);
    }

    public static void big(GuiGraphicsExtractor ctx, Font font, String s, int x, int y, float scale, int color, boolean bold) {
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)x, (float)y);
        m.scale(scale, scale);
        MutableComponent t = bold ? Component.literal((String)s).withStyle(ChatFormatting.BOLD) : Component.literal((String)s);
        ctx.text(font, (Component)t, 0, 0, color, false);
        m.popMatrix();
    }

    public static String trim(Font font, String s, int width) {
        return font.width(s) <= width ? s : font.plainSubstrByWidth(s, Math.max((int)0, (int)(width - 6))) + "\u2026";
    }
}
