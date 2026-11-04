/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.RtpPoints;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import org.joml.Matrix3x2fStack;

public final class RtpMinimap {
    public static final int MAP = 120;
    public static final int PAD = 4;
    public static final int TITLE_H = 11;
    public static final int LABEL_H = 11;
    public static final int WIDTH = 128;
    public static final int HEIGHT = 150;
    public static final int MIN_CHUNKS = 4;
    public static final int MAX_CHUNKS = 256;
    private static final int MAP_Y = 15;
    private static final double ON_POINT = 5.0;
    private static final double CHUNK_MIN_SCREEN_PX = 3.0;
    private static final int BACK = 0x66000000;
    private static final int POINT_COLOR = -8395864;
    private static final int GRID_COLOR = 0x55FFFFFF;
    private static final int CHUNK_COLOR = 0x2EFFFFFF;
    private static final int PLAYER_COLOR = -670398;
    private static final int HIT_COLOR = -11556609;
    private static final int HIT_GLOW = 1431283967;
    private static final long OPEN_EVERY_MS = 2000L;
    private static long lastOpen;
    private static RtpPoints.Dim lastDim;

    private RtpMinimap() {
    }

    public static RtpPoints.Dim dimOf(ClientLevel world) {
        return RtpPoints.Dim.of(world);
    }

    public static int radiusChunks() {
        return Math.max((int)4, (int)Math.min((int)256, (int)FwmConfig.INSTANCE.rtpMiniRadius));
    }

    public static void draw(GuiGraphicsExtractor ctx, Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null) {
            return;
        }
        RtpPoints.Dim dim = RtpMinimap.dimOf(client.level);
        long now = System.currentTimeMillis();
        if (dim != lastDim || now - lastOpen > 2000L) {
            lastDim = dim;
            lastOpen = now;
            RtpPoints.open(dim);
        }
        FwmTheme.panel(ctx, 0, 0, 128, 150);
        Font font = client.font;
        MutableComponent title = Lang.of("fwm.minimap.title", new Object[0]).withStyle(ChatFormatting.BOLD);
        ctx.text(font, (Component)title, (128 - font.width((FormattedText)title)) / 2, 5, FwmTheme.ACCENT, false);
        ctx.fill(4, 15, 124, 135, 0x66000000);
        int radius = RtpMinimap.radiusChunks() * 16;
        double bpp = 2.0 * (double)radius / 120.0;
        double minX = player.getX() - (double)radius;
        double minZ = player.getZ() - (double)radius;
        RtpMinimap.drawChunks(ctx, client, minX, minZ, bpp);
        RtpMinimap.drawGrid(ctx, dim, minX, minZ, bpp);
        int[] found = RtpMinimap.drawPoints(ctx, dim, minX, minZ, bpp, radius, player.getX(), player.getZ());
        RtpMinimap.drawPlayer(ctx, player, found[1] > 0);
        String count = RtpPoints.count(dim) == 0 ? "..." : String.valueOf((int)found[0]);
        int textY = 137;
        ctx.text(font, (Component)Component.literal((String)count), 124 - font.width(count), textY, FwmTheme.ACCENT, false);
        MutableComponent label = Lang.of("fwm.minimap.count", new Object[0]);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(label.getString(), 120 - font.width(count) - 4)), 4, textY, FwmTheme.TEXT_DIM, false);
    }

    private static void drawChunks(GuiGraphicsExtractor ctx, Minecraft client, double minX, double minZ, double bpp) {
        double gui = client.getWindow().getGuiScale();
        if (16.0 / bpp * gui < 3.0) {
            return;
        }
        int size = (int)Math.round((double)(120.0 * gui));
        int left = (int)Math.round((double)(4.0 * gui));
        int top = (int)Math.round((double)(15.0 * gui));
        double span = 120.0 * bpp;
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.scale((float)(1.0 / gui), (float)(1.0 / gui));
        long cx = (long)Math.ceil((double)(minX / 16.0));
        while ((double)(cx * 16L) < minX + span) {
            int px = (int)Math.round((double)(((double)(cx * 16L) - minX) / bpp * gui));
            if (px >= 0 && px < size) {
                ctx.fill(left + px, top, left + px + 1, top + size, 0x2EFFFFFF);
            }
            ++cx;
        }
        long cz = (long)Math.ceil((double)(minZ / 16.0));
        while ((double)(cz * 16L) < minZ + span) {
            int pz = (int)Math.round((double)(((double)(cz * 16L) - minZ) / bpp * gui));
            if (pz >= 0 && pz < size) {
                ctx.fill(left, top + pz, left + size, top + pz + 1, 0x2EFFFFFF);
            }
            ++cz;
        }
        matrices.popMatrix();
    }

    private static void drawGrid(GuiGraphicsExtractor ctx, RtpPoints.Dim dim, double minX, double minZ, double bpp) {
        int area = RtpPoints.area(dim);
        int half = area / 2;
        double span = 120.0 * bpp;
        long gx = (long)Math.ceil((double)((minX + (double)half) / (double)area));
        while ((double)(gx * (long)area - (long)half) < minX + span) {
            int px = (int)(((double)gx * (double)area - (double)half - minX) / bpp);
            if (px >= 0 && px < 120) {
                ctx.fill(4 + px, 15, 4 + px + 1, 135, 0x55FFFFFF);
            }
            ++gx;
        }
        long gz = (long)Math.ceil((double)((minZ + (double)half) / (double)area));
        while ((double)(gz * (long)area - (long)half) < minZ + span) {
            int pz = (int)(((double)gz * (double)area - (double)half - minZ) / bpp);
            if (pz >= 0 && pz < 120) {
                ctx.fill(4, 15 + pz, 124, 15 + pz + 1, 0x55FFFFFF);
            }
            ++gz;
        }
    }

    private static int[] drawPoints(GuiGraphicsExtractor ctx, RtpPoints.Dim dim, double minX, double minZ, double bpp, int radius, double meX, double meZ) {
        RtpPoints.Index index = RtpPoints.index(dim);
        if (index == null) {
            return new int[]{0, 0};
        }
        int[] points = RtpPoints.points(dim);
        int x0 = (int)Math.floor((double)minX);
        int z0 = (int)Math.floor((double)minZ);
        int x1 = x0 + 2 * radius;
        int z1 = z0 + 2 * radius;
        int fromX = Math.max((int)0, (int)((x0 >> index.shift) - index.minCellX));
        int toX = Math.min((int)(index.cellsW - 1), (int)((x1 >> index.shift) - index.minCellX));
        int fromZ = Math.max((int)0, (int)((z0 >> index.shift) - index.minCellZ));
        int toZ = Math.min((int)(index.cellsH - 1), (int)((z1 >> index.shift) - index.minCellZ));
        int count = 0;
        int hits = 0;
        double near = 25.0;
        for (int cz = fromZ; cz <= toZ; ++cz) {
            for (int cx = fromX; cx <= toX; ++cx) {
                int cell = cz * index.cellsW + cx;
                for (int k = index.start[cell]; k < index.start[cell + 1]; ++k) {
                    int i = index.order[k];
                    int col = (int)Math.floor((double)(((double)points[i] - minX) / bpp));
                    int row = (int)Math.floor((double)(((double)points[i + 1] - minZ) / bpp));
                    if (col < 0 || col >= 120 || row < 0 || row >= 120) continue;
                    ++count;
                    double dx = (double)points[i] + 0.5 - meX;
                    double dz = (double)points[i + 1] + 0.5 - meZ;
                    if (dx * dx + dz * dz <= near) {
                        ++hits;
                        continue;
                    }
                    int left = 4 + Math.min((int)col, (int)118);
                    int top = 15 + Math.min((int)row, (int)118);
                    ctx.fill(left, top, left + 2, top + 2, -8395864);
                }
            }
        }
        return new int[]{count, hits};
    }

    private static void drawPlayer(GuiGraphicsExtractor ctx, LocalPlayer player, boolean onPoint) {
        int color;
        int cx = 64;
        int cz = 75;
        int n = color = onPoint ? -11556609 : -670398;
        if (onPoint) {
            ctx.fill(cx - 4, cz - 4, cx + 5, cz + 5, 1431283967);
            ctx.fill(cx - 3, cz - 5, cx + 4, cz + 6, 1431283967);
            ctx.fill(cx - 5, cz - 3, cx + 6, cz + 4, 1431283967);
        }
        double yaw = Math.toRadians((double)player.getYRot());
        double dx = -Math.sin((double)yaw);
        double dz = Math.cos((double)yaw);
        for (int t = 2; t <= 6; ++t) {
            int px = cx + (int)Math.round((double)(dx * (double)t));
            int pz = cz + (int)Math.round((double)(dz * (double)t));
            ctx.fill(px, pz, px + 1, pz + 1, color);
        }
        ctx.fill(cx - 1, cz - 1, cx + 2, cz + 2, color);
    }
}
