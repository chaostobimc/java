/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Map
 *  java.util.Map$Entry
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import java.util.Map;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import org.joml.Matrix3x2fStack;

public final class FwmIcons {
    public static final int SIZE = 16;
    public static final String[] KEYS = new String[]{"fwm.tab.overview", "fwm.tab.sellmacro", "fwm.tab.messages", "fwm.tab.toolguard", "fwm.tab.secondchat", "fwm.tab.colorcodes", "fwm.tab.extras", "fwm.tab.antileak", "fwm.tab.rtp", "fwm.tab.spawner", "fwm.tab.members", "fwm.tab.settings", "fwm.tab.market"};
    private static final Map<String, int[]> PIXEL = Map.ofEntries((Map.Entry[])new Map.Entry[]{Map.entry((Object)"fwm.tab.sellmacro", (Object)new int[]{8, 0, 16, 2, 8, 6, 16, 8, 8, 10, 16, 12, 8, 14, 16, 16, 8, 22, 16, 24, 4, 2, 8, 4, 4, 20, 8, 22, 16, 2, 20, 4, 16, 20, 20, 22, 2, 4, 4, 8, 2, 16, 4, 20, 12, 4, 14, 6, 12, 16, 14, 18, 20, 4, 22, 8, 20, 16, 22, 20, 0, 8, 2, 16, 8, 8, 10, 10, 22, 8, 24, 16, 14, 12, 16, 14}), Map.entry((Object)"fwm.tab.market", (Object)new int[]{2, 2, 4, 20, 20, 6, 22, 8, 10, 8, 12, 10, 18, 8, 20, 10, 8, 10, 10, 12, 12, 10, 14, 12, 16, 10, 18, 12, 6, 12, 8, 14, 14, 12, 16, 14, 4, 20, 22, 22}), Map.entry((Object)"fwm.tab.messages", (Object)new int[]{0, 4, 24, 6, 0, 18, 24, 20, 0, 6, 4, 8, 20, 6, 24, 8, 0, 8, 2, 18, 4, 8, 6, 10, 18, 8, 20, 10, 22, 8, 24, 18, 6, 10, 8, 12, 16, 10, 18, 12, 8, 12, 16, 14}), Map.entry((Object)"fwm.tab.secondchat", (Object)new int[]{4, 2, 20, 4, 2, 4, 4, 18, 2, 20, 4, 22, 20, 4, 22, 16, 6, 8, 14, 10, 6, 12, 10, 14, 6, 16, 20, 18, 2, 18, 6, 20}), Map.entry((Object)"fwm.tab.colorcodes", (Object)new int[]{6, 0, 16, 2, 6, 22, 16, 24, 2, 2, 6, 4, 16, 2, 20, 4, 0, 4, 2, 18, 6, 4, 10, 6, 12, 4, 16, 6, 20, 4, 22, 10, 4, 8, 8, 10, 14, 10, 20, 12, 14, 16, 20, 18, 12, 12, 14, 16, 2, 18, 4, 20, 18, 18, 20, 20, 4, 20, 6, 22, 16, 20, 18, 22}), Map.entry((Object)"fwm.tab.members", (Object)new int[]{5, 2, 11, 4, 5, 10, 11, 12, 15, 2, 19, 4, 15, 10, 19, 12, 3, 4, 5, 10, 11, 4, 13, 10, 19, 4, 21, 10, 4, 14, 12, 16, 16, 14, 20, 16, 2, 16, 4, 18, 12, 16, 14, 18, 20, 16, 22, 18, 0, 18, 2, 22, 14, 18, 16, 22, 22, 18, 24, 22}), Map.entry((Object)"fwm.tab.toolguard", (Object)new int[]{4, 0, 16, 2, 2, 2, 4, 4, 2, 16, 4, 18, 16, 2, 18, 4, 0, 4, 2, 8, 0, 18, 2, 20, 12, 4, 14, 6, 18, 4, 20, 6, 10, 6, 12, 10, 20, 6, 22, 8, 22, 8, 24, 12, 8, 10, 10, 12, 6, 12, 8, 14, 4, 14, 6, 16}), Map.entry((Object)"fwm.tab.antileak", (Object)new int[]{2, 2, 4, 4, 2, 8, 4, 10, 2, 14, 4, 16, 4, 4, 6, 6, 10, 4, 16, 6, 10, 14, 16, 16, 4, 6, 8, 8, 4, 16, 8, 18, 16, 6, 20, 8, 8, 8, 10, 10, 12, 8, 14, 10, 20, 8, 22, 10, 20, 14, 22, 16, 20, 20, 22, 22, 0, 10, 2, 14, 8, 10, 12, 12, 14, 10, 16, 12, 22, 10, 24, 14, 8, 12, 14, 14, 16, 16, 18, 18, 8, 18, 16, 20, 18, 18, 20, 20}), Map.entry((Object)"fwm.tab.auction", (Object)new int[]{12, 0, 24, 2, 10, 2, 12, 4, 10, 22, 12, 24, 22, 2, 24, 12, 8, 4, 10, 6, 8, 20, 10, 22, 6, 6, 8, 8, 6, 18, 8, 20, 16, 6, 20, 10, 4, 8, 6, 10, 4, 16, 6, 18, 2, 10, 4, 12, 2, 14, 4, 16, 0, 12, 2, 14, 20, 12, 22, 14, 18, 14, 20, 16, 16, 16, 18, 18, 14, 18, 16, 20, 12, 20, 14, 22}), Map.entry((Object)"fwm.tab.giveaway", (Object)new int[]{8, 2, 11, 4, 13, 2, 16, 4, 6, 4, 8, 6, 11, 4, 13, 6, 11, 8, 13, 12, 11, 14, 13, 20, 16, 4, 18, 6, 4, 6, 20, 8, 4, 12, 20, 14, 2, 8, 4, 12, 20, 8, 22, 12, 4, 14, 6, 20, 18, 14, 20, 20, 6, 20, 18, 22})});
    private static final Map<String, int[]> FINE = Map.of((Object)"fwm.tab.rtp", (Object)new int[]{21, 6, 27, 8, 21, 17, 27, 19, 21, 29, 27, 31, 19, 8, 29, 10, 19, 15, 29, 17, 17, 10, 23, 12, 27, 10, 31, 14, 17, 12, 25, 14, 17, 14, 31, 15, 23, 19, 25, 25, 23, 27, 25, 29, 23, 31, 25, 34, 14, 25, 34, 27, 12, 27, 14, 31, 27, 27, 29, 29, 34, 27, 36, 29, 33, 29, 36, 31, 10, 31, 12, 34, 19, 31, 21, 33, 31, 31, 33, 33, 36, 31, 38, 34, 17, 33, 19, 34, 29, 33, 31, 40, 8, 34, 10, 38, 12, 34, 17, 36, 38, 34, 40, 38, 17, 36, 21, 38, 6, 38, 8, 40, 21, 38, 23, 40, 40, 38, 42, 40, 4, 40, 44, 42});

    private FwmIcons() {
    }

    private static boolean pixel(GuiGraphicsExtractor ctx, String key, int x, int y, int color) {
        int[] fine = (int[])FINE.get((Object)key);
        if (fine != null) {
            Matrix3x2fStack m = ctx.pose();
            m.pushMatrix();
            m.translate((float)x, (float)y);
            m.scale(0.33333334f, 0.33333334f);
            int i = 0;
            while (i + 3 < fine.length) {
                ctx.fill(fine[i], fine[i + 1], fine[i + 2], fine[i + 3], color);
                i += 4;
            }
            m.popMatrix();
            return true;
        }
        int[] r = (int[])PIXEL.get((Object)key);
        if (r == null) {
            return false;
        }
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)x, (float)y);
        m.scale(0.6666667f, 0.6666667f);
        int i = 0;
        while (i + 3 < r.length) {
            ctx.fill(r[i], r[i + 1], r[i + 2], r[i + 3], color);
            i += 4;
        }
        m.popMatrix();
        return true;
    }

    /*
     * Exception decompiling
     */
    public static void draw(GuiGraphicsExtractor ctx, String key, int x, int y, int color) {
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

    private static void sword(GuiGraphicsExtractor ctx, int x, int y, int c) {
        for (int i = 0; i < 8; ++i) {
            ctx.fill(x + 6 + i, y + 9 - i, x + 8 + i, y + 11 - i, c);
        }
        ctx.fill(x + 3, y + 9, x + 9, y + 11, c);
        ctx.fill(x + 5, y + 7, x + 7, y + 13, c);
        ctx.fill(x + 2, y + 12, x + 5, y + 15, c);
    }

    private static void spawnerMacro(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x, y, 16, 16, c);
        ctx.fill(x + 5, y, x + 6, y + 3, c);
        ctx.fill(x + 10, y, x + 11, y + 3, c);
        ctx.fill(x + 5, y + 13, x + 6, y + 16, c);
        ctx.fill(x + 10, y + 13, x + 11, y + 16, c);
        for (int i = 0; i < 4; ++i) {
            ctx.fill(x + 6 + i, y + 4 + i, x + 7 + i, y + 12 - i, c);
        }
    }

    private static void coin(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 5, y + 1, x + 11, y + 2, c);
        ctx.fill(x + 5, y + 14, x + 11, y + 15, c);
        ctx.fill(x + 3, y + 2, x + 5, y + 3, c);
        ctx.fill(x + 11, y + 2, x + 13, y + 3, c);
        ctx.fill(x + 3, y + 13, x + 5, y + 14, c);
        ctx.fill(x + 11, y + 13, x + 13, y + 14, c);
        ctx.fill(x + 2, y + 3, x + 3, y + 5, c);
        ctx.fill(x + 13, y + 3, x + 14, y + 5, c);
        ctx.fill(x + 2, y + 11, x + 3, y + 13, c);
        ctx.fill(x + 13, y + 11, x + 14, y + 13, c);
        ctx.fill(x + 1, y + 5, x + 2, y + 11, c);
        ctx.fill(x + 14, y + 5, x + 15, y + 11, c);
        ctx.fill(x + 7, y + 3, x + 9, y + 13, c);
        ctx.fill(x + 6, y + 4, x + 10, y + 5, c);
        ctx.fill(x + 5, y + 5, x + 7, y + 7, c);
        ctx.fill(x + 6, y + 7, x + 10, y + 8, c);
        ctx.fill(x + 9, y + 8, x + 11, y + 10, c);
        ctx.fill(x + 6, y + 10, x + 10, y + 11, c);
    }

    private static void arrows(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 1, y + 4, x + 13, y + 5, c);
        ctx.fill(x + 10, y + 2, x + 11, y + 7, c);
        ctx.fill(x + 11, y + 3, x + 12, y + 6, c);
        ctx.fill(x + 3, y + 11, x + 15, y + 12, c);
        ctx.fill(x + 5, y + 9, x + 6, y + 14, c);
        ctx.fill(x + 4, y + 10, x + 5, y + 13, c);
    }

    private static void smiley(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 5, y + 1, x + 11, y + 2, c);
        ctx.fill(x + 5, y + 14, x + 11, y + 15, c);
        ctx.fill(x + 1, y + 5, x + 2, y + 11, c);
        ctx.fill(x + 14, y + 5, x + 15, y + 11, c);
        ctx.fill(x + 2, y + 3, x + 3, y + 5, c);
        ctx.fill(x + 3, y + 2, x + 5, y + 3, c);
        ctx.fill(x + 11, y + 2, x + 13, y + 3, c);
        ctx.fill(x + 13, y + 3, x + 14, y + 5, c);
        ctx.fill(x + 2, y + 11, x + 3, y + 13, c);
        ctx.fill(x + 3, y + 13, x + 5, y + 14, c);
        ctx.fill(x + 11, y + 13, x + 13, y + 14, c);
        ctx.fill(x + 13, y + 11, x + 14, y + 13, c);
        ctx.fill(x + 5, y + 5, x + 7, y + 8, c);
        ctx.fill(x + 9, y + 5, x + 11, y + 8, c);
        ctx.fill(x + 4, y + 9, x + 5, y + 10, c);
        ctx.fill(x + 11, y + 9, x + 12, y + 10, c);
        ctx.fill(x + 5, y + 10, x + 11, y + 11, c);
    }

    private static void layers(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 5, y + 1, 10, 8, c);
        FwmIcons.box(ctx, x + 3, y + 4, 10, 8, c);
        FwmIcons.box(ctx, x + 1, y + 7, 10, 8, c);
    }

    private static void pin(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 5, y + 1, x + 11, y + 2, c);
        ctx.fill(x + 4, y + 2, x + 12, y + 8, c);
        ctx.fill(x + 5, y + 8, x + 11, y + 10, c);
        ctx.fill(x + 6, y + 10, x + 10, y + 12, c);
        ctx.fill(x + 7, y + 12, x + 9, y + 15, c);
    }

    private static void info(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 1, y + 1, x + 15, y + 2, c);
        ctx.fill(x + 1, y + 14, x + 15, y + 15, c);
        ctx.fill(x + 1, y + 1, x + 2, y + 15, c);
        ctx.fill(x + 14, y + 1, x + 15, y + 15, c);
        ctx.fill(x + 7, y + 4, x + 9, y + 6, c);
        ctx.fill(x + 7, y + 7, x + 9, y + 12, c);
    }

    private static void gavel(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 2, y + 2, x + 10, y + 7, c);
        ctx.fill(x + 1, y + 3, x + 2, y + 6, c);
        ctx.fill(x + 10, y + 3, x + 11, y + 6, c);
        for (int i = 0; i < 5; ++i) {
            ctx.fill(x + 8 + i, y + 7 + i, x + 10 + i, y + 9 + i, c);
        }
        ctx.fill(x + 1, y + 13, x + 9, y + 15, c);
    }

    private static void gift(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 1, y + 5, x + 15, y + 8, c);
        ctx.fill(x + 2, y + 8, x + 3, y + 15, c);
        ctx.fill(x + 13, y + 8, x + 14, y + 15, c);
        ctx.fill(x + 2, y + 14, x + 14, y + 15, c);
        ctx.fill(x + 7, y + 8, x + 9, y + 14, c);
        ctx.fill(x + 4, y + 1, x + 7, y + 3, c);
        ctx.fill(x + 9, y + 1, x + 12, y + 3, c);
        ctx.fill(x + 6, y + 3, x + 10, y + 5, c);
    }

    private static void extras(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 1, y + 1, 14, 14, c);
        ctx.fill(x + 7, y + 4, x + 9, y + 12, c);
        ctx.fill(x + 4, y + 7, x + 12, y + 9, c);
    }

    private static void shield(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 3, y + 1, x + 13, y + 3, c);
        ctx.fill(x + 3, y + 3, x + 5, y + 9, c);
        ctx.fill(x + 11, y + 3, x + 13, y + 9, c);
        ctx.fill(x + 4, y + 9, x + 6, y + 11, c);
        ctx.fill(x + 10, y + 9, x + 12, y + 11, c);
        ctx.fill(x + 5, y + 11, x + 7, y + 13, c);
        ctx.fill(x + 9, y + 11, x + 11, y + 13, c);
        ctx.fill(x + 7, y + 13, x + 9, y + 15, c);
        ctx.fill(x + 7, y + 5, x + 9, y + 10, c);
    }

    private static void note(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 7, y + 1, x + 9, y + 15, c);
        ctx.fill(x + 4, y + 3, x + 13, y + 5, c);
        ctx.fill(x + 3, y + 4, x + 5, y + 8, c);
        ctx.fill(x + 4, y + 7, x + 13, y + 9, c);
        ctx.fill(x + 11, y + 8, x + 13, y + 12, c);
        ctx.fill(x + 3, y + 11, x + 12, y + 13, c);
    }

    private static void envelope(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x, y + 3, 16, 10, c);
        for (int i = 0; i < 6; ++i) {
            ctx.fill(x + 1 + i, y + 4 + i, x + 2 + i, y + 5 + i, c);
            ctx.fill(x + 14 - i, y + 4 + i, x + 15 - i, y + 5 + i, c);
        }
    }

    private static void pickaxe(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 1, y + 5, x + 3, y + 6, c);
        ctx.fill(x + 3, y + 3, x + 5, y + 5, c);
        ctx.fill(x + 5, y + 2, x + 11, y + 3, c);
        ctx.fill(x + 11, y + 3, x + 13, y + 5, c);
        ctx.fill(x + 13, y + 5, x + 15, y + 6, c);
        ctx.fill(x + 7, y + 3, x + 9, y + 15, c);
    }

    private static void bubble(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 1, y + 1, 14, 11, c);
        ctx.fill(x + 3, y + 12, x + 7, y + 13, c);
        ctx.fill(x + 3, y + 13, x + 5, y + 15, c);
        ctx.fill(x + 4, y + 4, x + 12, y + 5, c);
        ctx.fill(x + 4, y + 7, x + 9, y + 8, c);
    }

    private static void palette(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 4, y + 1, x + 12, y + 2, c);
        ctx.fill(x + 2, y + 2, x + 4, y + 3, c);
        ctx.fill(x + 12, y + 2, x + 14, y + 3, c);
        ctx.fill(x + 1, y + 3, x + 2, y + 5, c);
        ctx.fill(x + 14, y + 3, x + 15, y + 9, c);
        ctx.fill(x, y + 5, x + 1, y + 11, c);
        ctx.fill(x + 1, y + 11, x + 2, y + 13, c);
        ctx.fill(x + 2, y + 13, x + 5, y + 14, c);
        ctx.fill(x + 5, y + 14, x + 10, y + 15, c);
        ctx.fill(x + 10, y + 13, x + 11, y + 14, c);
        ctx.fill(x + 11, y + 9, x + 14, y + 10, c);
        ctx.fill(x + 10, y + 10, x + 11, y + 13, c);
        FwmIcons.ring(ctx, x + 5, y + 9, 4, c);
        ctx.fill(x + 3, y + 5, x + 5, y + 7, c);
        ctx.fill(x + 7, y + 3, x + 9, y + 5, c);
        ctx.fill(x + 11, y + 5, x + 13, y + 7, c);
    }

    private static void spawner(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x, y, 16, 16, c);
        ctx.fill(x + 5, y, x + 6, y + 3, c);
        ctx.fill(x + 10, y, x + 11, y + 3, c);
        ctx.fill(x + 5, y + 13, x + 6, y + 16, c);
        ctx.fill(x + 10, y + 13, x + 11, y + 16, c);
        FwmIcons.box(ctx, x + 4, y + 4, 8, 8, c);
        ctx.fill(x + 6, y + 6, x + 7, y + 8, c);
        ctx.fill(x + 9, y + 6, x + 10, y + 8, c);
        ctx.fill(x + 6, y + 9, x + 10, y + 10, c);
    }

    private static void bell(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 7, y + 1, x + 9, y + 3, c);
        ctx.fill(x + 5, y + 3, x + 11, y + 4, c);
        ctx.fill(x + 4, y + 4, x + 5, y + 11, c);
        ctx.fill(x + 11, y + 4, x + 12, y + 11, c);
        ctx.fill(x + 2, y + 11, x + 14, y + 12, c);
        ctx.fill(x + 7, y + 13, x + 9, y + 15, c);
    }

    private static void ticket(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x, y + 3, 16, 10, c);
        ctx.fill(x, y + 7, x + 2, y + 9, 0);
        ctx.fill(x + 14, y + 7, x + 16, y + 9, 0);
        ctx.fill(x + 4, y + 6, x + 12, y + 7, c);
        ctx.fill(x + 4, y + 9, x + 10, y + 10, c);
    }

    private static void members(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.ring(ctx, x + 9, y + 2, 5, c);
        ctx.fill(x + 9, y + 8, x + 15, y + 9, c);
        ctx.fill(x + 14, y + 9, x + 15, y + 14, c);
        FwmIcons.ring(ctx, x + 1, y + 1, 7, c);
        ctx.fill(x + 1, y + 9, x + 9, y + 10, c);
        ctx.fill(x + 1, y + 10, x + 2, y + 15, c);
        ctx.fill(x + 8, y + 10, x + 9, y + 15, c);
    }

    private static void cog(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.ring(ctx, x + 2, y + 2, 12, c);
        FwmIcons.ring(ctx, x + 5, y + 5, 6, c);
        ctx.fill(x + 6, y, x + 10, y + 3, c);
        ctx.fill(x + 6, y + 13, x + 10, y + 16, c);
        ctx.fill(x, y + 6, x + 3, y + 10, c);
        ctx.fill(x + 13, y + 6, x + 16, y + 10, c);
        ctx.fill(x + 2, y + 2, x + 5, y + 5, c);
        ctx.fill(x + 11, y + 2, x + 14, y + 5, c);
        ctx.fill(x + 2, y + 11, x + 5, y + 14, c);
        ctx.fill(x + 11, y + 11, x + 14, y + 14, c);
    }

    private static void chart(GuiGraphicsExtractor ctx, int x, int y, int c) {
        ctx.fill(x + 1, y + 1, x + 2, y + 15, c);
        ctx.fill(x + 1, y + 14, x + 15, y + 15, c);
        ctx.fill(x + 3, y + 9, x + 6, y + 14, c);
        ctx.fill(x + 7, y + 6, x + 10, y + 14, c);
        ctx.fill(x + 11, y + 2, x + 14, y + 14, c);
    }

    private static void grid(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 1, y + 1, 6, 6, c);
        FwmIcons.box(ctx, x + 9, y + 1, 6, 6, c);
        FwmIcons.box(ctx, x + 1, y + 9, 6, 6, c);
        FwmIcons.box(ctx, x + 9, y + 9, 6, 6, c);
    }

    private static void map(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 1, y + 3, 14, 11, c);
        ctx.fill(x + 6, y + 3, x + 7, y + 14, c);
        ctx.fill(x + 10, y + 3, x + 11, y + 14, c);
        ctx.fill(x + 2, y + 7, x + 5, y + 8, c);
        ctx.fill(x + 3, y + 6, x + 4, y + 9, c);
    }

    private static void dot(GuiGraphicsExtractor ctx, int x, int y, int c) {
        FwmIcons.box(ctx, x + 4, y + 4, 8, 8, c);
    }

    private static void box(GuiGraphicsExtractor ctx, int x, int y, int w, int h, int c) {
        ctx.fill(x, y, x + w, y + 1, c);
        ctx.fill(x, y + h - 1, x + w, y + h, c);
        ctx.fill(x, y, x + 1, y + h, c);
        ctx.fill(x + w - 1, y, x + w, y + h, c);
    }

    private static void ring(GuiGraphicsExtractor ctx, int x, int y, int d, int c) {
        ctx.fill(x + 1, y, x + d - 1, y + 1, c);
        ctx.fill(x + 1, y + d - 1, x + d - 1, y + d, c);
        ctx.fill(x, y + 1, x + 1, y + d - 1, c);
        ctx.fill(x + d - 1, y + 1, x + d, y + d - 1, c);
    }
}
