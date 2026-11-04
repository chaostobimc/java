/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.ui;

import net.friendswithmoney.fwm.ui.FwmGfx;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.resources.Identifier;

public final class FwmTheme {
    public static int OVERLAY = 0x99090C10;
    public static int GLASS_TOP = 0xF21A1E24;
    public static int GLASS_BOTTOM = 0xF21A1E24;
    public static int EDGE = 0xFF343B45;
    public static int SHEEN = 0x00000000;
    public static int RAISED = 0xFF242A32;
    public static int INSET = 0xFF15191F;
    public static int LOCKED = 0x6620262D;
    public static int DISABLED_FILL = 0xFF30343A;
    public static int DISABLED_EDGE = 0xFF565C64;
    public static int DISABLED_TEXT = 0xFF969DA5;
    public static int ACCENT = 0xFF8DBAAF;
    public static int ACCENT_DIM = 0x80587972;
    public static int ON = 0xFF8DBAAF;
    public static int OFF = 0xFF6D757D;
    public static int TEXT = 0xFFF1F3F4;
    public static int TEXT_DIM = 0xFFAAB2BA;
    public static int TEXT_ON_ACCENT = 0xFF18211F;
    public static int HOVER = 0xFF303840;
    public static final Identifier LOGO = Identifier.fromNamespaceAndPath((String)"fwm", (String)"textures/gui/logo.png");
    public static final int LOGO_SHEET = 256;
    public static final int LOGO_W = 256;
    public static final int LOGO_H = 192;
    public static final float LOGO_RATIO = 1.3333334f;
    private static final int BAR_W = 2;

    private FwmTheme() {
    }

    public static void panel(GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
        ctx.fill(x + 1, y + 1, x + w - 1, y + h - 1, GLASS_BOTTOM);
        FwmTheme.outline(ctx, x, y, w, h, EDGE);
    }

    public static void box(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int fill, int border) {
        ctx.fill(x + 1, y + 1, x + w - 1, y + h - 1, fill);
        FwmTheme.outline(ctx, x, y, w, h, border);
    }

    private static void outline(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int color) {
        ctx.fill(x + 1, y, x + w - 1, y + 1, color);
        ctx.fill(x + 1, y + h - 1, x + w - 1, y + h, color);
        ctx.fill(x, y + 1, x + 1, y + h - 1, color);
        ctx.fill(x + w - 1, y + 1, x + w, y + h - 1, color);
    }

    public static void pill(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int fill) {
        ctx.fill(x + 1, y, x + w - 1, y + h, fill);
        ctx.fill(x, y + 1, x + 1, y + h - 1, fill);
        ctx.fill(x + w - 1, y + 1, x + w, y + h - 1, fill);
    }

    public static void toggle(GuiGraphicsExtractor ctx, int x, int y, int w, int h, float t) {
        int track = FwmTheme.mix(DISABLED_FILL, ACCENT_DIM, t);
        int edge = FwmTheme.mix(DISABLED_EDGE, ON, t);
        FwmTheme.box(ctx, x, y, w, h, track, edge);
        int knobW = Math.max((int)6, (int)(h - 4));
        int travel = w - knobW - 4;
        int kx = x + 2 + Math.round((float)(t * (float)travel));
        ctx.fill(kx, y + 2, kx + knobW, y + h - 2, edge);
    }

    private static int mix(int from, int to, float t) {
        float f = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)t));
        int out = 0;
        for (int shift = 0; shift < 32; shift += 8) {
            int a = from >> shift & 0xFF;
            int b = to >> shift & 0xFF;
            out |= Math.round((float)((float)a + (float)(b - a) * f)) << shift;
        }
        return out;
    }

    public static void hover(GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
        ctx.fill(x, y, x + w, y + h, HOVER);
        ctx.fill(x, y, x + 2, y + h, ACCENT);
    }

    public static void contentFrame(GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
        FwmTheme.box(ctx, x, y, w, h, INSET, EDGE);
    }

    public static void sidebarButton(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active, boolean hovered, boolean locked) {
        FwmTheme.sidebarButton(ctx, x, y, w, h, active, hovered, locked, true);
    }

    public static void sidebarButton(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active, boolean hovered, boolean locked, boolean on) {
        if (locked) {
            return;
        }
        if (active) {
            ctx.fill(x + 2, y, x + w, y + h, on ? ACCENT_DIM : DISABLED_FILL);
        } else if (hovered) {
            ctx.fill(x + 2, y, x + w, y + h, HOVER);
        }
        if (active || !on) {
            ctx.fill(x, y, x + 2, y + h, on ? ACCENT : DISABLED_EDGE);
        }
    }

    public static void tile(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean locked, boolean hovered, boolean on) {
        ctx.fill(x + 2, y, x + w, y + h, locked ? LOCKED : (hovered ? HOVER : RAISED));
        if (hovered && !locked) {
            FwmTheme.outline(ctx, x, y, w, h, ACCENT);
        }
        if (!on && !locked) {
            ctx.fill(x, y, x + 2, y + h, DISABLED_EDGE);
        }
    }

    public static int sidebarTextColor(boolean active, boolean hovered, boolean locked) {
        return FwmTheme.sidebarTextColor(active, hovered, locked, true);
    }

    public static int sidebarTextColor(boolean active, boolean hovered, boolean locked, boolean on) {
        if (locked) {
            return OFF;
        }
        if (!on) {
            return DISABLED_TEXT;
        }
        if (active) {
            return TEXT;
        }
        return hovered ? ACCENT : TEXT_DIM;
    }

    public static void germanFlag(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
        int band = Math.max((int)1, (int)(h / 3));
        ctx.fill(x, y, x + w, y + band, -15724528);
        ctx.fill(x, y + band, x + w, y + 2 * band, -3407872);
        ctx.fill(x, y + 2 * band, x + w, y + h, -12800);
        FwmTheme.flagFrame(ctx, x, y, w, h, active);
    }

    public static void usFlag(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
        int sy;
        ctx.fill(x, y, x + w, y + h, -723724);
        for (int stripe = 0; stripe < 4 && (sy = y + stripe * 2) + 1 <= y + h; ++stripe) {
            ctx.fill(x, sy, x + w, Math.min((int)(sy + 1), (int)(y + h)), -5103052);
        }
        int canton = Math.max((int)2, (int)(w / 2 - 1));
        ctx.fill(x, y, x + canton, y + Math.max((int)2, (int)(h / 2)), -12829842);
        ctx.fill(x + 1, y + 1, x + 2, y + 2, -723724);
        ctx.fill(x + 3, y + 2, x + 4, y + 3, -723724);
        FwmTheme.flagFrame(ctx, x, y, w, h, active);
    }

    private static void flagFrame(GuiGraphicsExtractor ctx, int x, int y, int w, int h, boolean active) {
        if (!active) {
            ctx.fill(x, y, x + w, y + h, -1726997996);
        }
        FwmTheme.outline(ctx, x - 1, y - 1, w + 2, h + 2, active ? ACCENT : OFF);
    }

    public static void divider(GuiGraphicsExtractor ctx, int x, int y, int w) {
        ctx.fill(x, y, x + w, y + 1, 866709700);
    }

    public static void logo(GuiGraphicsExtractor ctx, int x, int y, int width) {
        FwmGfx.blit(ctx, LOGO, x, y, width, FwmTheme.logoHeight(width), 256, 192, 256);
    }

    public static int logoHeight(int width) {
        return Math.round((float)((float)width / 1.3333334f));
    }
}
