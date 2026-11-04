/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 */
package net.friendswithmoney.fwm.textures;

import com.mojang.blaze3d.platform.NativeImage;

final class IsoRender {
    private static final float COS = 0.8660254f;

    private IsoRender() {
    }

    static NativeImage render(String categoryId, String path, NativeImage src) {
        int frameH;
        int w = src.getWidth();
        int h = src.getHeight();
        int n = frameH = h > w && h % w == 0 ? w : h;
        if (path.endsWith("textures/entity/end_crystal/end_crystal.png") && w >= 64 && w == h * 2) {
            return IsoRender.trim(IsoRender.crystal(src));
        }
        if (path.contains((CharSequence)"/block/") && w == frameH) {
            return IsoRender.trim(IsoRender.cube(src, 0, 0, w, frameH));
        }
        if (path.contains((CharSequence)"/item/") && w <= 64 && frameH <= 64) {
            return IsoRender.trim(IsoRender.item(src, w, frameH));
        }
        return null;
    }

    private static NativeImage trim(NativeImage img) {
        int minX = img.getWidth();
        int minY = img.getHeight();
        int maxX = -1;
        int maxY = -1;
        for (int y = 0; y < img.getHeight(); ++y) {
            for (int x = 0; x < img.getWidth(); ++x) {
                if (img.getPixel(x, y) >>> 24 < 8) continue;
                minX = Math.min((int)minX, (int)x);
                minY = Math.min((int)minY, (int)y);
                maxX = Math.max((int)maxX, (int)x);
                maxY = Math.max((int)maxY, (int)y);
            }
        }
        if (maxX < 0) {
            return img;
        }
        int w = maxX - minX + 3;
        int h = maxY - minY + 3;
        NativeImage out = new NativeImage(w, h, true);
        for (int y = minY; y <= maxY; ++y) {
            for (int x = minX; x <= maxX; ++x) {
                out.setPixel(x - minX + 1, y - minY + 1, img.getPixel(x, y));
            }
        }
        img.close();
        return out;
    }

    private static NativeImage cube(NativeImage src, int sx, int sy, int sw, int sh) {
        int size = 72;
        NativeImage out = new NativeImage(size, size + 6, true);
        IsoRender.shadow(out, (float)size / 2.0f, size + 1, 26.0f, 5.0f);
        float edge = 34.0f;
        float bx = (float)size / 2.0f;
        float by = size - 2;
        IsoRender.box(out, src, bx, by, edge, edge, edge, new int[]{sx, sy, sw, sh}, new int[]{sx, sy, sw, sh}, new int[]{sx, sy, sw, sh}, 1.0f);
        return out;
    }

    private static NativeImage item(NativeImage src, int w, int h) {
        int scale = Math.max((int)1, (int)(64 / Math.max((int)w, (int)h)));
        int ow = w * scale;
        int oh = h * scale;
        NativeImage out = new NativeImage(ow + 8, oh + 12, true);
        IsoRender.shadow(out, (float)(ow + 8) / 2.0f, oh + 7, (float)ow * 0.36f, 3.5f);
        for (int y = 0; y < oh; ++y) {
            for (int x = 0; x < ow; ++x) {
                int c = src.getPixel(x / scale, y / scale);
                if (c >>> 24 == 0) continue;
                IsoRender.blend(out, x + 4, y + 2, c);
            }
        }
        return out;
    }

    private static NativeImage crystal(NativeImage src) {
        int u = src.getWidth() / 64;
        int W = 110;
        int H = 104;
        NativeImage out = new NativeImage(W, H, true);
        float k = 3.1f;
        float cx = (float)W / 2.0f;
        float baseY = H - 6;
        IsoRender.shadow(out, cx, baseY + 2.0f, 40.0f, 5.0f);
        IsoRender.box(out, src, cx, baseY, 12.0f * k, 12.0f * k, 4.0f * k, IsoRender.face(u, 12, 16, 12, 12), IsoRender.face(u, 12, 28, 12, 4), IsoRender.face(u, 0, 28, 12, 4), 1.0f);
        float lift = baseY - 4.0f * k - 3.0f * k;
        IsoRender.box(out, src, cx, lift - 2.0f * k, 6.0f * k, 6.0f * k, 6.0f * k, IsoRender.face(u, 40, 0, 8, 8), IsoRender.face(u, 40, 8, 8, 8), IsoRender.face(u, 32, 8, 8, 8), 1.0f);
        IsoRender.box(out, src, cx, lift, 10.0f * k, 10.0f * k, 10.0f * k, IsoRender.face(u, 8, 0, 8, 8), IsoRender.face(u, 8, 8, 8, 8), IsoRender.face(u, 0, 8, 8, 8), 0.95f);
        return out;
    }

    private static int[] face(int u, int x, int y, int w, int h) {
        return new int[]{x * u, y * u, w * u, h * u};
    }

    private static void box(NativeImage out, NativeImage src, float bx, float by, float width, float depth, float height, int[] top, int[] front, int[] side, float opacity) {
        float[] a = new float[]{-0.8660254f * depth, -0.5f * depth};
        float[] b = new float[]{0.8660254f * width, -0.5f * width};
        IsoRender.face(out, src, side, bx + a[0], by + a[1] - height, -a[0], -a[1], 0.0f, height, 0.78f, opacity);
        IsoRender.face(out, src, front, bx, by - height, b[0], b[1], 0.0f, height, 0.6f, opacity);
        IsoRender.face(out, src, top, bx + a[0] + b[0], by + a[1] + b[1] - height, -b[0], -b[1], -a[0], -a[1], 1.0f, opacity);
    }

    private static void face(NativeImage out, NativeImage src, int[] r, float ox, float oy, float ux, float uy, float vx, float vy, float shade, float opacity) {
        if (r[2] <= 0 || r[3] <= 0) {
            return;
        }
        float det = ux * vy - uy * vx;
        if (Math.abs((float)det) < 1.0E-4f) {
            return;
        }
        int minX = (int)Math.floor((double)Math.min((float)Math.min((float)ox, (float)(ox + ux)), (float)Math.min((float)(ox + vx), (float)(ox + ux + vx))));
        int maxX = (int)Math.ceil((double)Math.max((float)Math.max((float)ox, (float)(ox + ux)), (float)Math.max((float)(ox + vx), (float)(ox + ux + vx))));
        int minY = (int)Math.floor((double)Math.min((float)Math.min((float)oy, (float)(oy + uy)), (float)Math.min((float)(oy + vy), (float)(oy + uy + vy))));
        int maxY = (int)Math.ceil((double)Math.max((float)Math.max((float)oy, (float)(oy + uy)), (float)Math.max((float)(oy + vy), (float)(oy + uy + vy))));
        for (int py = Math.max((int)0, (int)minY); py < Math.min((int)out.getHeight(), (int)maxY); ++py) {
            for (int px = Math.max((int)0, (int)minX); px < Math.min((int)out.getWidth(), (int)maxX); ++px) {
                int c;
                int alpha;
                float dx = (float)px + 0.5f - ox;
                float dy = (float)py + 0.5f - oy;
                float s = (dx * vy - dy * vx) / det;
                float t = (ux * dy - uy * dx) / det;
                if (s < 0.0f || s >= 1.0f || t < 0.0f || t >= 1.0f) continue;
                int tx = r[0] + Math.min((int)(r[2] - 1), (int)((int)(s * (float)r[2])));
                int ty = r[1] + Math.min((int)(r[3] - 1), (int)((int)(t * (float)r[3])));
                if (tx >= src.getWidth() || ty >= src.getHeight() || (alpha = Math.round((float)((float)((c = src.getPixel(tx, ty)) >>> 24) * opacity))) == 0) continue;
                int red = Math.round((float)((float)(c >> 16 & 0xFF) * shade));
                int green = Math.round((float)((float)(c >> 8 & 0xFF) * shade));
                int blue = Math.round((float)((float)(c & 0xFF) * shade));
                IsoRender.blend(out, px, py, alpha << 24 | red << 16 | green << 8 | blue);
            }
        }
    }

    private static void shadow(NativeImage out, float cx, float cy, float rx, float ry) {
        for (int y = (int)(cy - ry); y <= (int)(cy + ry); ++y) {
            for (int x = (int)(cx - rx); x <= (int)(cx + rx); ++x) {
                float dy;
                float dx;
                float d;
                if (x < 0 || y < 0 || x >= out.getWidth() || y >= out.getHeight() || (d = (dx = ((float)x - cx) / rx) * dx + (dy = ((float)y - cy) / ry) * dy) >= 1.0f) continue;
                IsoRender.blend(out, x, y, Math.round((float)(90.0f * (1.0f - d))) << 24);
            }
        }
    }

    private static void blend(NativeImage out, int x, int y, int argb) {
        int a = argb >>> 24;
        if (a == 0) {
            return;
        }
        if (a == 255) {
            out.setPixel(x, y, argb);
            return;
        }
        int under = out.getPixel(x, y);
        int ua = under >>> 24;
        int na = a + ua * (255 - a) / 255;
        if (na == 0) {
            return;
        }
        int r = ((argb >> 16 & 0xFF) * a + (under >> 16 & 0xFF) * ua * (255 - a) / 255) / na;
        int g = ((argb >> 8 & 0xFF) * a + (under >> 8 & 0xFF) * ua * (255 - a) / 255) / na;
        int b = ((argb & 0xFF) * a + (under & 0xFF) * ua * (255 - a) / 255) / na;
        out.setPixel(x, y, na << 24 | r << 16 | g << 8 | b);
    }
}
