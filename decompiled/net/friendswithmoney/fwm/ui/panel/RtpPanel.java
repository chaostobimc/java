/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  java.lang.Integer
 *  java.lang.MatchException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Short
 *  java.lang.String
 *  java.net.URI
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.ConfirmLinkScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.ui.panel;

import com.mojang.blaze3d.platform.NativeImage;
import java.net.URI;
import java.util.Objects;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.RtpPoints;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public class RtpPanel
extends Panel {
    private static final int STRIP_H = 14;
    private static final int STATUS_H = 12;
    private static final int BTN_H = 16;
    private static final int BTN_GAP = 4;
    private static final int GAP = 3;
    private static final double MIN_BPP = 2.0;
    private static final double ZOOM_STEP = 1.5;
    private static final int GRID_MIN_PX = 4;
    private static final int GRID_FULL_PX = 10;
    private static final int BUTTONS = 3;
    private static final int LABEL_MIN_PX = 44;
    private static final int PICK_PX = 5;
    private static final RtpPoints.Dim[] DIMS = RtpPoints.Dim.values();
    private static final int POINT_COLOR = -8395864;
    private static final double DOT_FULL_BPP = 48.0;
    private static final int GRID_COLOR = 0x33FFFFFF;
    private static final int GRID_FAINT = 0x14FFFFFF;
    private static final int WORLD_COLOR = -2136411964;
    private static final int OWN_AREA_COLOR = -2131376830;
    private static final int PLAYER_COLOR = -50373;
    private static final int PLAYER_EDGE = -870710011;
    private static final int PICKED_COLOR = -1;
    private RtpPoints.Dim dim = RtpPoints.Dim.OVERWORLD;
    private double centerX;
    private double centerZ;
    private double bpp = 1024.0;
    private boolean moved;
    private boolean fittedToPoints;
    private static final Identifier MAP_TEXTURE = Identifier.fromNamespaceAndPath((String)"fwm", (String)"rtp_map");
    private static DynamicTexture texture;
    private static int texW;
    private static int texH;
    private int builtW;
    private int builtH;
    private int builtGui;
    private boolean dirty = true;
    private int lastCount = -1;
    private double lastMouseX;
    private double lastMouseY;
    private boolean dragging;
    private int[] picked;
    private boolean copied;

    private static int worldSize(RtpPoints.Dim dim) {
        return switch (dim) {
            default -> throw new MatchException(null, null);
            case RtpPoints.Dim.OVERWORLD -> 250000;
            case RtpPoints.Dim.NETHER -> 170000;
            case RtpPoints.Dim.END -> 80000;
        };
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.rtp", new Object[0]);
    }

    @Override
    public boolean settingsCog() {
        return true;
    }

    @Override
    protected void onResize() {
        RtpPoints.open(this.dim);
        if (!this.moved) {
            this.fitWorld();
        }
        this.dirty = true;
    }

    private int mapY() {
        return this.y + 14 + 3;
    }

    private int mapH() {
        return Math.max((int)16, (int)(this.y + this.height - 16 - 3 - 12 - 3 - this.mapY()));
    }

    private int mapW() {
        return this.width;
    }

    private int statusY() {
        return this.y + this.height - 16 - 3 - 12;
    }

    private int buttonY() {
        return this.y + this.height - 16;
    }

    private int buttonW() {
        return (this.width - 8) / 3;
    }

    private int buttonX(int index) {
        return this.x + index * (this.buttonW() + 4);
    }

    private int slotW() {
        return this.width / DIMS.length;
    }

    private int slotX(int slot) {
        return this.x + slot * this.slotW();
    }

    private double bpp() {
        return this.bpp;
    }

    private static boolean ownPosOn() {
        return FwmConfig.INSTANCE.rtpShowOwnPos;
    }

    private static LocalPlayer ownPlayer() {
        return RtpPanel.ownPosOn() ? Minecraft.getInstance().player : null;
    }

    private static RtpPoints.Dim playerDim() {
        ClientLevel world = Minecraft.getInstance().level;
        if (world == null) {
            return null;
        }
        return RtpPoints.Dim.of(world);
    }

    private static long areaStart(double block, int area) {
        int half = area / 2;
        return Math.floorDiv((long)((long)Math.floor((double)block) + (long)half), (long)area) * (long)area - (long)half;
    }

    private long[] worldBounds() {
        RtpPoints.Index index = RtpPoints.index(this.dim);
        int area = RtpPoints.area(this.dim);
        if (index == null) {
            long low = -RtpPanel.worldSize(this.dim) / 2 - area / 2;
            long high = low + (long)RtpPanel.worldSize(this.dim);
            return new long[]{low, low, high, high};
        }
        return new long[]{RtpPanel.areaStart(index.minX, area), RtpPanel.areaStart(index.minZ, area), RtpPanel.areaStart(index.maxX, area) + (long)area, RtpPanel.areaStart(index.maxZ, area) + (long)area};
    }

    private double zoomToFit(double blocksW, double blocksH) {
        return Math.max((double)2.0, (double)Math.max((double)(blocksW / (double)Math.max((int)1, (int)(this.mapW() - 2))), (double)(blocksH / (double)Math.max((int)1, (int)(this.mapH() - 2)))));
    }

    private void fitWorld() {
        long[] b = this.worldBounds();
        this.centerX = (double)(b[0] + b[2]) / 2.0;
        this.centerZ = (double)(b[1] + b[3]) / 2.0;
        this.bpp = this.zoomToFit(b[2] - b[0], b[3] - b[1]);
        this.fittedToPoints = RtpPoints.index(this.dim) != null;
    }

    private double maxBpp() {
        long[] b = this.worldBounds();
        return this.zoomToFit(b[2] - b[0], b[3] - b[1]);
    }

    private void zoomToMe() {
        LocalPlayer player = RtpPanel.ownPlayer();
        if (player == null) {
            return;
        }
        RtpPoints.Dim here = RtpPanel.playerDim();
        if (here != null && here != this.dim) {
            this.dim = here;
            this.picked = null;
            RtpPoints.open(this.dim);
        }
        int area = RtpPoints.area(this.dim);
        this.centerX = (double)RtpPanel.areaStart(player.getX(), area) + (double)area / 2.0;
        this.centerZ = (double)RtpPanel.areaStart(player.getZ(), area) + (double)area / 2.0;
        this.bpp = Math.min((double)this.maxBpp(), (double)this.zoomToFit(area, area));
        this.moved = true;
        this.dirty = true;
    }

    private double minX() {
        return this.centerX - (double)this.mapW() / 2.0 * this.bpp();
    }

    private double minZ() {
        return this.centerZ - (double)this.mapH() / 2.0 * this.bpp();
    }

    private static int guiScale() {
        return Math.max((int)1, (int)Minecraft.getInstance().getWindow().getGuiScale());
    }

    private int dotSize(int gui) {
        return Math.max((int)1, (int)Math.min((int)gui, (int)((int)Math.round((double)((double)gui * 48.0 / this.bpp())))));
    }

    private static NativeImage canvas(int w, int h) {
        Minecraft client = Minecraft.getInstance();
        if (texture == null || texW != w || texH != h) {
            if (texture != null) {
                client.getTextureManager().release(MAP_TEXTURE);
            }
            texture = new DynamicTexture(() -> "fwm rtp map", new NativeImage(w, h, true));
            client.getTextureManager().register(MAP_TEXTURE, (AbstractTexture)texture);
            texW = w;
            texH = h;
        }
        return texture.getPixels();
    }

    private void rebuild() {
        int gui = RtpPanel.guiScale();
        int w = this.mapW() * gui;
        int h = this.mapH() * gui;
        NativeImage image = RtpPanel.canvas(w, h);
        image.fillRect(0, 0, w, h, 0);
        double perPixel = this.bpp() / (double)gui;
        long scale = (long)(4.294967296E9 / perPixel);
        int originX = (int)Math.floor((double)this.minX());
        int originZ = (int)Math.floor((double)this.minZ());
        int endX = originX + (int)Math.ceil((double)((double)w * perPixel));
        int endZ = originZ + (int)Math.ceil((double)((double)h * perPixel));
        int dot = this.dotSize(gui);
        int[] points = RtpPoints.points(this.dim);
        RtpPoints.Index index = RtpPoints.index(this.dim);
        if (index == null) {
            int i = 0;
            while (i + 2 < points.length) {
                RtpPanel.mark(image, points, i, originX, originZ, endX, endZ, scale, w, h, dot);
                i += 3;
            }
        } else {
            int fromX = Math.max((int)0, (int)((originX >> index.shift) - index.minCellX));
            int toX = Math.min((int)(index.cellsW - 1), (int)((endX >> index.shift) - index.minCellX));
            int fromZ = Math.max((int)0, (int)((originZ >> index.shift) - index.minCellZ));
            int toZ = Math.min((int)(index.cellsH - 1), (int)((endZ >> index.shift) - index.minCellZ));
            for (int cz = fromZ; cz <= toZ; ++cz) {
                int row = cz * index.cellsW;
                for (int cx = fromX; cx <= toX; ++cx) {
                    int cell = row + cx;
                    for (int k = index.start[cell]; k < index.start[cell + 1]; ++k) {
                        RtpPanel.mark(image, points, index.order[k], originX, originZ, endX, endZ, scale, w, h, dot);
                    }
                }
            }
        }
        texture.upload();
        this.builtW = this.mapW();
        this.builtH = this.mapH();
        this.builtGui = gui;
        this.dirty = false;
        this.lastCount = RtpPoints.count(this.dim);
    }

    private static void mark(NativeImage image, int[] points, int i, int originX, int originZ, int endX, int endZ, long scale, int w, int h, int dot) {
        int px = points[i];
        if (px < originX || px >= endX) {
            return;
        }
        int pz = points[i + 1];
        if (pz < originZ || pz >= endZ) {
            return;
        }
        int col = (int)((long)(px - originX) * scale >>> 32);
        int row = (int)((long)(pz - originZ) * scale >>> 32);
        if (col >= w || row >= h) {
            return;
        }
        int toCol = Math.min((int)w, (int)(col + dot));
        int toRow = Math.min((int)h, (int)(row + dot));
        for (int r = row; r < toRow; ++r) {
            for (int c = col; c < toCol; ++c) {
                image.setPixel(c, r, -8395864);
            }
        }
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.renderStrip(ctx, font, mouseX, mouseY);
        if (!this.moved && !this.fittedToPoints && RtpPoints.index(this.dim) != null) {
            this.fitWorld();
            this.dirty = true;
        }
        if (this.dirty || this.builtW != this.mapW() || this.builtH != this.mapH() || this.builtGui != RtpPanel.guiScale() || texture == null || this.lastCount != RtpPoints.count(this.dim)) {
            this.rebuild();
        }
        this.renderMap(ctx, font);
        this.renderStatus(ctx, font, mouseX, mouseY);
        this.renderButtons(ctx, font, mouseX, mouseY);
    }

    private void renderStrip(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        for (int slot = 0; slot < DIMS.length; ++slot) {
            int bx = this.slotX(slot);
            int bw = this.slotW();
            boolean active = DIMS[slot] == this.dim;
            boolean hovered = !active && mouseX >= bx && mouseX < bx + bw && mouseY >= this.y && mouseY < this.y + 14;
            FwmTheme.sidebarButton(ctx, bx, this.y, bw, 14, active, hovered, false);
            MutableComponent label = Component.literal((String)font.plainSubstrByWidth(Lang.str("fwm.rtp.dim." + DIMS[slot].key(), new Object[0]), bw - 4));
            int n = bx + (bw - font.width((FormattedText)label)) / 2;
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)label, n, this.y + (14 - 9) / 2 + 1, FwmTheme.sidebarTextColor(active, hovered, false), false);
        }
    }

    private void renderMap(GuiGraphicsExtractor ctx, Font font) {
        int mapX = this.x;
        int mapY = this.mapY();
        int w = this.mapW();
        int h = this.mapH();
        FwmTheme.box(ctx, mapX, mapY, w, h, FwmTheme.INSET, FwmTheme.EDGE);
        ctx.enableScissor(mapX + 1, mapY + 1, mapX + w - 1, mapY + h - 1);
        this.renderGrid(ctx, font, mapX, mapY, w, h);
        this.renderWorldEdge(ctx, mapX, mapY);
        this.renderOwnArea(ctx, mapX, mapY);
        ctx.blit(RenderPipelines.GUI_TEXTURED, MAP_TEXTURE, mapX, mapY, 0.0f, 0.0f, w, h, texW, texH, texW, texH);
        this.renderPlayer(ctx, mapX, mapY, w, h);
        this.renderPicked(ctx, mapX, mapY, w, h);
        ctx.disableScissor();
        if (RtpPoints.count(this.dim) == 0) {
            MutableComponent empty = Lang.of(RtpPoints.state(this.dim) == RtpPoints.State.ERROR ? "fwm.rtp.offline" : "fwm.rtp.loading", new Object[0]);
            ctx.text(font, (Component)empty, mapX + (w - font.width((FormattedText)empty)) / 2, mapY + h / 2 - 4, FwmTheme.TEXT_DIM, false);
        }
    }

    private void renderGrid(GuiGraphicsExtractor ctx, Font font, int mapX, int mapY, int w, int h) {
        int area = RtpPoints.area(this.dim);
        int half = area / 2;
        double bpp = this.bpp();
        double spacing = (double)area / bpp;
        if (spacing < 4.0) {
            return;
        }
        int color = spacing < 10.0 ? 0x14FFFFFF : 0x33FFFFFF;
        double minX = this.minX();
        double minZ = this.minZ();
        long[] b = this.worldBounds();
        long cellX0 = Math.floorDiv((long)(b[0] + (long)half), (long)area);
        long cellZ0 = Math.floorDiv((long)(b[1] + (long)half), (long)area);
        long cellX1 = Math.floorDiv((long)(b[2] + (long)half), (long)area);
        long cellZ1 = Math.floorDiv((long)(b[3] + (long)half), (long)area);
        int top = Math.max((int)0, (int)((int)Math.round((double)(((double)b[1] - minZ) / bpp))));
        int bottom = Math.min((int)h, (int)((int)Math.round((double)(((double)b[3] - minZ) / bpp)) + 1));
        int leftEdge = Math.max((int)0, (int)((int)Math.round((double)(((double)b[0] - minX) / bpp))));
        int rightEdge = Math.min((int)w, (int)((int)Math.round((double)(((double)b[2] - minX) / bpp)) + 1));
        long firstX = Math.max((long)cellX0, (long)((long)Math.floor((double)((minX + (double)half) / (double)area))));
        long firstZ = Math.max((long)cellZ0, (long)((long)Math.floor((double)((minZ + (double)half) / (double)area))));
        for (long gx = firstX; gx <= cellX1 && (double)(gx * (long)area - (long)half) < minX + (double)w * bpp; ++gx) {
            int px = (int)Math.round((double)(((double)gx * (double)area - (double)half - minX) / bpp));
            if (px < 0 || px >= w || top >= bottom) continue;
            ctx.fill(mapX + px, mapY + top, mapX + px + 1, mapY + bottom, color);
        }
        for (long gz = firstZ; gz <= cellZ1 && (double)(gz * (long)area - (long)half) < minZ + (double)h * bpp; ++gz) {
            int pz = (int)Math.round((double)(((double)gz * (double)area - (double)half - minZ) / bpp));
            if (pz < 0 || pz >= h || leftEdge >= rightEdge) continue;
            ctx.fill(mapX + leftEdge, mapY + pz, mapX + rightEdge, mapY + pz + 1, color);
        }
        if (spacing < 44.0) {
            return;
        }
        long fromX = Math.max((long)cellX0, (long)(firstX - 1L));
        long fromZ = Math.max((long)cellZ0, (long)(firstZ - 1L));
        for (long gx = fromX; gx < cellX1 && (double)(gx * (long)area - (long)half) < minX + (double)w * bpp; ++gx) {
            for (long gz = fromZ; gz < cellZ1 && (double)(gz * (long)area - (long)half) < minZ + (double)h * bpp; ++gz) {
                int left = (int)Math.round((double)(((double)gx * (double)area - (double)half - minX) / bpp));
                int cellTop = (int)Math.round((double)(((double)gz * (double)area - (double)half - minZ) / bpp));
                int right = (int)Math.round((double)(((double)(gx + 1L) * (double)area - (double)half - minX) / bpp));
                int below = (int)Math.round((double)(((double)(gz + 1L) * (double)area - (double)half - minZ) / bpp));
                int px = Math.max((int)2, (int)(left + 2));
                int pz = Math.max((int)2, (int)(cellTop + 2));
                if (px >= w - 10 || pz >= h - 8 || right - px < 30 || below - pz < 10) continue;
                ctx.text(font, (Component)Component.literal((String)(gx + " / " + gz)), mapX + px, mapY + pz, 0x55FFFFFF, false);
            }
        }
    }

    private void renderWorldEdge(GuiGraphicsExtractor ctx, int mapX, int mapY) {
        long[] b = this.worldBounds();
        this.outline(ctx, mapX, mapY, b[0], b[1], b[2], b[3], -2136411964);
    }

    private void renderOwnArea(GuiGraphicsExtractor ctx, int mapX, int mapY) {
        LocalPlayer player = RtpPanel.ownPlayer();
        if (player == null || RtpPanel.playerDim() != this.dim) {
            return;
        }
        int area = RtpPoints.area(this.dim);
        if ((double)area / this.bpp() < 4.0) {
            return;
        }
        long sx = RtpPanel.areaStart(player.getX(), area);
        long sz = RtpPanel.areaStart(player.getZ(), area);
        this.outline(ctx, mapX, mapY, sx, sz, sx + (long)area, sz + (long)area, -2131376830);
    }

    private void outline(GuiGraphicsExtractor ctx, int mapX, int mapY, long bx0, long bz0, long bx1, long bz1, int color) {
        double bpp = this.bpp();
        int x0 = mapX + (int)Math.round((double)(((double)bx0 - this.minX()) / bpp));
        int z0 = mapY + (int)Math.round((double)(((double)bz0 - this.minZ()) / bpp));
        int x1 = mapX + (int)Math.round((double)(((double)bx1 - this.minX()) / bpp));
        int z1 = mapY + (int)Math.round((double)(((double)bz1 - this.minZ()) / bpp));
        ctx.fill(x0, z0, x1 + 1, z0 + 1, color);
        ctx.fill(x0, z1, x1 + 1, z1 + 1, color);
        ctx.fill(x0, z0, x0 + 1, z1 + 1, color);
        ctx.fill(x1, z0, x1 + 1, z1 + 1, color);
    }

    private void renderPlayer(GuiGraphicsExtractor ctx, int mapX, int mapY, int w, int h) {
        LocalPlayer player = RtpPanel.ownPlayer();
        if (player == null || RtpPanel.playerDim() != this.dim) {
            return;
        }
        int px = (int)((player.getX() - this.minX()) / this.bpp());
        int pz = (int)((player.getZ() - this.minZ()) / this.bpp());
        if (px < 0 || px >= w || pz < 0 || pz >= h) {
            return;
        }
        int cx = mapX + px;
        int cz = mapY + pz;
        ctx.fill(cx - 2, cz - 3, cx + 3, cz + 4, -870710011);
        ctx.fill(cx - 3, cz - 2, cx + 4, cz + 3, -870710011);
        ctx.fill(cx - 1, cz - 2, cx + 2, cz + 3, -50373);
        ctx.fill(cx - 2, cz - 1, cx + 3, cz + 2, -50373);
    }

    private void renderPicked(GuiGraphicsExtractor ctx, int mapX, int mapY, int w, int h) {
        if (this.picked == null) {
            return;
        }
        int px = (int)(((double)this.picked[0] - this.minX()) / this.bpp());
        int pz = (int)(((double)this.picked[1] - this.minZ()) / this.bpp());
        if (px < 0 || px >= w || pz < 0 || pz >= h) {
            return;
        }
        FwmTheme.box(ctx, mapX + px - 3, mapY + pz - 3, 7, 7, 0, -1);
    }

    private void renderStatus(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int n = this.statusY();
        Objects.requireNonNull((Object)font);
        int textY = n + (12 - 9) / 2;
        MutableComponent left = this.picked == null ? Lang.of("fwm.rtp.count", Fmt.compact(RtpPoints.count(this.dim))) : Lang.of(this.copied ? "fwm.rtp.copied" : "fwm.rtp.picked", this.picked[0], this.picked[2] == Short.MIN_VALUE ? "?" : Integer.valueOf((int)this.picked[2]), this.picked[1]);
        ctx.text(font, (Component)left, this.x, textY, this.picked == null ? FwmTheme.TEXT_DIM : FwmTheme.TEXT, false);
        if (!this.overMap(mouseX, mouseY) || this.dragging) {
            return;
        }
        long bx = (long)Math.floor((double)(this.minX() + (double)(mouseX - this.x) * this.bpp()));
        long bz = (long)Math.floor((double)(this.minZ() + (double)(mouseY - this.mapY()) * this.bpp()));
        MutableComponent right = Lang.of("fwm.rtp.hover", bx, bz);
        ctx.text(font, (Component)right, this.x + this.width - font.width((FormattedText)right), textY, FwmTheme.ACCENT, false);
    }

    private void renderButtons(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean canGoToMe = RtpPanel.ownPlayer() != null;
        this.button(ctx, font, 0, Lang.str("fwm.rtp.toMe", new Object[0]), canGoToMe, canGoToMe && this.overButton(mouseX, mouseY, 0));
        this.button(ctx, font, 1, Lang.str(RtpPanel.ownPosOn() ? "fwm.rtp.ownPos.on" : "fwm.rtp.ownPos.off", new Object[0]), true, this.overButton(mouseX, mouseY, 1));
        this.button(ctx, font, 2, Lang.str("fwm.rtp.browser", new Object[0]), true, this.overButton(mouseX, mouseY, 2));
    }

    private void button(GuiGraphicsExtractor ctx, Font font, int index, String label, boolean enabled, boolean hovered) {
        int bx = this.buttonX(index);
        FwmTheme.box(ctx, bx, this.buttonY(), this.buttonW(), 16, enabled ? FwmTheme.RAISED : FwmTheme.LOCKED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label, this.buttonW() - 6));
        int color = !enabled ? FwmTheme.TEXT_DIM : (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT);
        int n = bx + (this.buttonW() - font.width((FormattedText)shown)) / 2;
        int n2 = this.buttonY();
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)shown, n, n2 + (16 - 9) / 2 + 1, color, false);
    }

    private boolean overButton(double mouseX, double mouseY, int index) {
        int bx = this.buttonX(index);
        return mouseX >= (double)bx && mouseX < (double)(bx + this.buttonW()) && mouseY >= (double)this.buttonY() && mouseY < (double)(this.buttonY() + 16);
    }

    private boolean overMap(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.mapW()) && mouseY >= (double)this.mapY() && mouseY < (double)(this.mapY() + this.mapH());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseY >= (double)this.y && mouseY < (double)(this.y + 14)) {
            this.selectDim(mouseX);
            return true;
        }
        if (this.overButton(mouseX, mouseY, 0)) {
            this.zoomToMe();
            return true;
        }
        if (this.overButton(mouseX, mouseY, 1)) {
            FwmConfig.INSTANCE.rtpShowOwnPos = !FwmConfig.INSTANCE.rtpShowOwnPos;
            ConfigManager.save();
            return true;
        }
        if (this.overButton(mouseX, mouseY, 2)) {
            Minecraft client = Minecraft.getInstance();
            ConfirmLinkScreen.confirmLinkNow((Screen)client.gui.screen(), (URI)URI.create((String)FwmConfig.INSTANCE.rtpSiteUrl));
            return true;
        }
        if (this.overMap(mouseX, mouseY)) {
            this.lastMouseX = mouseX;
            this.lastMouseY = mouseY;
            this.dragging = true;
            this.pick(mouseX, mouseY);
        }
        return true;
    }

    private void selectDim(double mouseX) {
        for (int slot = 0; slot < DIMS.length; ++slot) {
            if (mouseX < (double)this.slotX(slot) || mouseX >= (double)(this.slotX(slot) + this.slotW())) continue;
            if (DIMS[slot] == this.dim) {
                return;
            }
            this.dim = DIMS[slot];
            this.picked = null;
            RtpPoints.open(this.dim);
            this.moved = false;
            this.fitWorld();
            this.dirty = true;
            return;
        }
    }

    private void pick(double mouseX, double mouseY) {
        double bpp = this.bpp();
        double worldX = this.minX() + (mouseX - (double)this.x) * bpp;
        double worldZ = this.minZ() + (mouseY - (double)this.mapY()) * bpp;
        double reach = 5.0 * bpp;
        int[] points = RtpPoints.points(this.dim);
        double best = reach * reach;
        int[] found = null;
        int i = 0;
        while (i + 2 < points.length) {
            double distance;
            double dx = (double)points[i] - worldX;
            double dz = (double)points[i + 1] - worldZ;
            if (!(Math.abs((double)dx) > reach) && !(Math.abs((double)dz) > reach) && (distance = dx * dx + dz * dz) < best) {
                best = distance;
                found = new int[]{points[i], points[i + 1], points[i + 2]};
            }
            i += 3;
        }
        this.picked = found;
        this.copied = false;
        if (found == null) {
            return;
        }
        String text = (int)found[0] + " " + (found[2] == Short.MIN_VALUE ? "~" : String.valueOf((int)found[2])) + " " + (int)found[1];
        Minecraft.getInstance().keyboardHandler.setClipboard(text);
        this.copied = true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (!this.dragging || button != 0) {
            return false;
        }
        this.centerX -= (mouseX - this.lastMouseX) * this.bpp();
        this.centerZ -= (mouseY - this.lastMouseY) * this.bpp();
        this.moved = true;
        this.lastMouseX = mouseX;
        this.lastMouseY = mouseY;
        this.dirty = true;
        return true;
    }

    @Override
    public void mouseReleased() {
        this.dragging = false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (!this.overMap(mouseX, mouseY)) {
            return false;
        }
        double next = vertical > 0.0 ? this.bpp / 1.5 : this.bpp * 1.5;
        next = Math.max((double)2.0, (double)Math.min((double)this.maxBpp(), (double)next));
        if (next == this.bpp) {
            return true;
        }
        this.moved = true;
        double beforeX = this.minX() + (mouseX - (double)this.x) * this.bpp();
        double beforeZ = this.minZ() + (mouseY - (double)this.mapY()) * this.bpp();
        this.bpp = next;
        double afterX = this.minX() + (mouseX - (double)this.x) * this.bpp();
        double afterZ = this.minZ() + (mouseY - (double)this.mapY()) * this.bpp();
        this.centerX += beforeX - afterX;
        this.centerZ += beforeZ - afterZ;
        this.dirty = true;
        return true;
    }
}
