/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.ui;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;

public final class SpawnerCard {
    public static final int STYLE_FWM = 0;
    public static final int STYLE_APPLE = 1;
    public static final int STYLE_NEON = 2;
    public static final int STYLE_MINIMAL = 3;

    private SpawnerCard() {
    }

    public static void applyStyle(int style) {
        FwmConfig c = FwmConfig.INSTANCE;
        c.spawnerHudStyle = style;
        switch (style) {
            case 1: {
                c.spawnerHudBg = 1;
                c.spawnerHudBgColor = 0x1C1C1E;
                c.spawnerHudOpacity = 80;
                c.spawnerHudBorder = 1;
                c.spawnerHudBorderColor = 0xFFFFFF;
                c.spawnerHudBorderWidth = 1;
                c.spawnerHudRadius = 10;
                c.spawnerHudPadding = 10;
                c.spawnerHudRowGap = 13;
                c.spawnerHudShadow = true;
                c.spawnerHudTextShadow = false;
                c.spawnerHudDivider = true;
                c.spawnerHudHeadBold = false;
                c.spawnerHudTextColor = 0xFFFFFF;
                c.spawnerHudHeadColor = 0x98989D;
                c.spawnerHudBuyColor = 3199320;
                c.spawnerHudSellColor = 16729402;
                break;
            }
            case 2: {
                c.spawnerHudBg = 0;
                c.spawnerHudBgColor = 460559;
                c.spawnerHudOpacity = 92;
                c.spawnerHudBorder = 2;
                c.spawnerHudBorderWidth = 2;
                c.spawnerHudRadius = 4;
                c.spawnerHudPadding = 9;
                c.spawnerHudRowGap = 12;
                c.spawnerHudShadow = false;
                c.spawnerHudTextShadow = true;
                c.spawnerHudDivider = false;
                c.spawnerHudHeadBold = true;
                c.spawnerHudTextColor = 0xF0F0FF;
                c.spawnerHudHeadColor = 16732120;
                c.spawnerHudBuyColor = 3800852;
                c.spawnerHudSellColor = 16719993;
                break;
            }
            case 3: {
                c.spawnerHudBg = 2;
                c.spawnerHudBorder = 0;
                c.spawnerHudRadius = 0;
                c.spawnerHudPadding = 4;
                c.spawnerHudRowGap = 11;
                c.spawnerHudShadow = false;
                c.spawnerHudTextShadow = true;
                c.spawnerHudDivider = false;
                c.spawnerHudHeadBold = true;
                c.spawnerHudTextColor = 0xFFFFFF;
                c.spawnerHudHeadColor = 0xFFFFFF;
                c.spawnerHudBuyColor = 0x55FF55;
                c.spawnerHudSellColor = 0xFF5555;
                break;
            }
            default: {
                c.spawnerHudBg = 0;
                c.spawnerHudBgColor = 789516;
                c.spawnerHudOpacity = 90;
                c.spawnerHudBorder = 1;
                c.spawnerHudBorderColor = 0xFFFFFF;
                c.spawnerHudBorderWidth = 1;
                c.spawnerHudRadius = 6;
                c.spawnerHudPadding = 8;
                c.spawnerHudRowGap = 12;
                c.spawnerHudShadow = false;
                c.spawnerHudTextShadow = true;
                c.spawnerHudDivider = true;
                c.spawnerHudHeadBold = true;
                c.spawnerHudTextColor = 0xF5F5F5;
                c.spawnerHudHeadColor = 0xF5F5F5;
                c.spawnerHudBuyColor = 65360;
                c.spawnerHudSellColor = 0xFF0000;
            }
        }
    }

    private static List<String[]> rows(FwmConfig c) {
        SpawnerData data = Spawners.data();
        ArrayList out = new ArrayList();
        if (data == null || data.items == null) {
            return out;
        }
        for (SpawnerData.Item item : SpawnerPanel.inShopOrder(data.items)) {
            if (item.name == null || c.spawnerHudHidden.contains((Object)item.name)) continue;
            String[] own = (String[])c.spawnerHudPrices.get((Object)item.name);
            String buy = own != null && own[0] != null && !own[0].isBlank() ? own[0] : item.buyPrice;
            String sell = own != null && own.length > 1 && own[1] != null && !own[1].isBlank() ? own[1] : item.sellPrice;
            out.add((Object)new String[]{item.name, SpawnerCard.price(buy), SpawnerCard.price(sell)});
        }
        return out;
    }

    private static String price(String p) {
        if (p == null || p.isBlank()) {
            return "-";
        }
        String digits = p.replaceAll("[^0-9]", "");
        return digits.isEmpty() || digits.chars().allMatch(ch -> ch == 48) ? "-" : p;
    }

    private static String label(String own, String fallback) {
        return own == null || own.isBlank() ? fallback : own;
    }

    public static int[] size(Font font) {
        int[] nArray;
        Layout l = SpawnerCard.layout(font, FwmConfig.INSTANCE);
        if (l == null) {
            nArray = null;
        } else {
            int[] nArray2 = new int[2];
            nArray2[0] = l.w;
            nArray = nArray2;
            nArray2[1] = l.h;
        }
        return nArray;
    }

    private static Layout layout(Font font, FwmConfig c) {
        int h;
        int w;
        List<String[]> rows = SpawnerCard.rows(c);
        if (rows.isEmpty()) {
            return null;
        }
        boolean buyOn = c.spawnerHudShowBuy;
        boolean sellOn = c.spawnerHudShowSell;
        MutableComponent title = c.spawnerHudTitle == null || c.spawnerHudTitle.isBlank() ? null : Component.literal((String)c.spawnerHudTitle).withStyle(ChatFormatting.BOLD);
        Component headName = SpawnerCard.styled(SpawnerCard.label(c.spawnerHudLabelName, "Spawner"), c.spawnerHudHeadBold);
        Component headBuy = SpawnerCard.styled(SpawnerCard.label(c.spawnerHudLabelBuy, "Kauf"), c.spawnerHudHeadBold);
        Component headSell = SpawnerCard.styled(SpawnerCard.label(c.spawnerHudLabelSell, "Verkauf"), c.spawnerHudHeadBold);
        int iconW = c.spawnerHudIcons ? 12 : 0;
        int nameW = 0;
        int buyW = 0;
        int sellW = 0;
        for (String[] r : rows) {
            nameW = Math.max((int)nameW, (int)font.width(r[0]));
            buyW = Math.max((int)buyW, (int)font.width(r[1]));
            sellW = Math.max((int)sellW, (int)font.width(r[2]));
        }
        if (c.spawnerHudHeader) {
            nameW = Math.max((int)nameW, (int)(font.width((FormattedText)headName) - iconW));
            buyW = Math.max((int)buyW, (int)font.width((FormattedText)headBuy));
            sellW = Math.max((int)sellW, (int)font.width((FormattedText)headSell));
        }
        if (!buyOn) {
            buyW = 0;
        }
        if (!sellOn) {
            sellW = 0;
        }
        int pad = c.spawnerHudPadding;
        int rowH = c.spawnerHudRowGap;
        int titleH = title == null ? 0 : rowH + 2;
        int headerH = c.spawnerHudHeader ? rowH + 3 : 0;
        int gap = 14;
        int cellW = 0;
        int cols = 1;
        if (c.spawnerHudGrid) {
            cellW = Math.max((int)(iconW + nameW), (int)(buyW + sellW + (buyOn && sellOn ? 12 : 0))) + 14;
            cols = Math.min((int)rows.size(), (int)3);
            int lines = (rows.size() + cols - 1) / cols;
            w = 2 * pad + cols * cellW + (cols - 1) * 4;
            h = 2 * pad + titleH + headerH + lines * (2 * rowH + 6) - 4;
            if (title != null) {
                w = Math.max((int)w, (int)(2 * pad + font.width((FormattedText)title)));
            }
        } else {
            w = 2 * pad + iconW + nameW + (buyOn ? gap + buyW : 0) + (sellOn ? gap + sellW : 0);
            if (title != null) {
                w = Math.max((int)w, (int)(2 * pad + font.width((FormattedText)title)));
            }
            h = 2 * pad + titleH + headerH + rows.size() * rowH - 2;
        }
        return new Layout(rows, (Component)title, headName, headBuy, headSell, iconW, nameW, buyW, sellW, titleH, headerH, cellW, cols, w, h);
    }

    private static Component styled(String s, boolean bold) {
        return bold ? Component.literal((String)s).withStyle(ChatFormatting.BOLD) : Component.literal((String)s);
    }

    public static void draw(GuiGraphicsExtractor ctx, Font font) {
        FwmConfig c = FwmConfig.INSTANCE;
        Layout l = SpawnerCard.layout(font, c);
        if (l == null) {
            return;
        }
        int w = l.w;
        int h = l.h;
        int pad = c.spawnerHudPadding;
        int rowH = c.spawnerHudRowGap;
        int r = Math.max((int)0, (int)Math.min((int)12, (int)c.spawnerHudRadius));
        int buy = 0xFF000000 | c.spawnerHudBuyColor;
        int sell = 0xFF000000 | c.spawnerHudSellColor;
        int text = 0xFF000000 | c.spawnerHudTextColor;
        int head = 0xFF000000 | c.spawnerHudHeadColor;
        int alpha = Math.max((int)0, (int)Math.min((int)255, (int)Math.round((float)((float)c.spawnerHudOpacity * 2.55f))));
        boolean shadowText = c.spawnerHudTextShadow;
        if (c.spawnerHudShadow && c.spawnerHudBg != 2) {
            for (int i = 4; i >= 1; --i) {
                LauncherGfx.round(ctx, -i, -i + 3, w + 2 * i, h + 2 * i, r + i, 6 + (4 - i) * 6 << 24);
            }
        }
        int bg = c.spawnerHudBgColor & 0xFFFFFF;
        if (c.spawnerHudBg == 0) {
            LauncherGfx.round(ctx, 0, 0, w, h, r, alpha << 24 | bg);
        } else if (c.spawnerHudBg == 1) {
            LauncherGfx.round(ctx, 0, 0, w, h, r, alpha * 55 / 100 << 24 | bg);
            ctx.fill(r, 0, w - r, 1, 0x40FFFFFF);
        }
        int bw = Math.max((int)1, (int)Math.min((int)3, (int)c.spawnerHudBorderWidth));
        int borderColor = 0xFF000000 | c.spawnerHudBorderColor;
        if (c.spawnerHudBorder == 1) {
            for (int i = 0; i < bw; ++i) {
                LauncherGfx.ring(ctx, i, i, w - 2 * i, h - 2 * i, Math.max((int)0, (int)(r - i)), 0x40000000 | borderColor & 0xFFFFFF);
            }
        } else if (c.spawnerHudBorder == 2) {
            SpawnerCard.gradientBorder(ctx, w, h, buy, sell, bw, c.spawnerHudGradientSpeed);
        }
        int ty = pad;
        if (l.title != null) {
            SpawnerCard.text(ctx, font, l.title, pad, ty, text, shadowText);
            ty += l.titleH;
        }
        boolean buyOn = c.spawnerHudShowBuy;
        boolean sellOn = c.spawnerHudShowSell;
        if (c.spawnerHudGrid) {
            if (l.headerH > 0) {
                SpawnerCard.text(ctx, font, l.headName, pad, ty, head, shadowText);
                ty += l.headerH;
            }
            for (int i = 0; i < l.rows.size(); ++i) {
                String[] row = (String[])l.rows.get(i);
                int cx = pad + i % l.cols * (l.cellW + 4);
                int cy = ty + i / l.cols * (2 * rowH + 6);
                SpawnerCard.icon(ctx, row[0], cx, cy, c.spawnerHudIcons);
                SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[0]), cx + l.iconW, cy, text, shadowText);
                if (buyOn) {
                    SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[1]), cx, cy + rowH, buy, shadowText);
                }
                if (!sellOn) continue;
                SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[2]), cx + l.cellW - 14 - font.width(row[2]), cy + rowH, sell, shadowText);
            }
        } else {
            int buyX = pad + l.iconW + l.nameW + 14;
            int sellX = buyX + (buyOn ? l.buyW + 14 : 0);
            if (l.headerH > 0) {
                SpawnerCard.text(ctx, font, l.headName, pad, ty, head, shadowText);
                if (buyOn) {
                    SpawnerCard.text(ctx, font, l.headBuy, buyX + l.buyW - font.width((FormattedText)l.headBuy), ty, c.spawnerHudHeadBold ? buy : head, shadowText);
                }
                if (sellOn) {
                    SpawnerCard.text(ctx, font, l.headSell, sellX + l.sellW - font.width((FormattedText)l.headSell), ty, c.spawnerHudHeadBold ? sell : head, shadowText);
                }
                if (c.spawnerHudDivider) {
                    ctx.fill(pad, ty + rowH - 1, w - pad, ty + rowH, 0x26FFFFFF);
                }
                ty += l.headerH;
            }
            for (String[] row : l.rows) {
                SpawnerCard.icon(ctx, row[0], pad, ty - 1, c.spawnerHudIcons);
                SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[0]), pad + l.iconW, ty, text, shadowText);
                if (buyOn) {
                    SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[1]), buyX + l.buyW - font.width(row[1]), ty, buy, shadowText);
                }
                if (sellOn) {
                    SpawnerCard.text(ctx, font, (Component)Component.literal((String)row[2]), sellX + l.sellW - font.width(row[2]), ty, sell, shadowText);
                }
                ty += rowH;
            }
        }
    }

    private static void text(GuiGraphicsExtractor ctx, Font font, Component t, int x, int y, int color, boolean shadow) {
        ctx.text(font, t, x, y, color, shadow);
    }

    private static void icon(GuiGraphicsExtractor ctx, String name, int x, int y, boolean on) {
        if (!on) {
            return;
        }
        Identifier id = SpawnerPanel.picture(name);
        if (id != null) {
            FwmGfx.blit(ctx, id, x, y, 10, 10, 64, 64, 64, 64);
        }
    }

    private static void gradientBorder(GuiGraphicsExtractor ctx, int w, int h, int a, int b, int thick, int speed) {
        int perimeter = 2 * (w + h);
        long period = Math.max((long)600L, (long)(6000L - (long)Math.max((int)1, (int)Math.min((int)10, (int)speed)) * 540L));
        float shift = (float)(System.currentTimeMillis() % period) / (float)period;
        for (int i = 0; i < perimeter; i += 2) {
            int y;
            int x;
            float t = ((float)i / (float)perimeter + shift) % 1.0f;
            float mix = t < 0.5f ? t * 2.0f : (1.0f - t) * 2.0f;
            int color = 0xFF000000 | SpawnerCard.lerp(a, b, mix);
            if (i < w) {
                x = i;
                y = 0;
            } else if (i < w + h) {
                x = w - thick;
                y = i - w;
            } else if (i < 2 * w + h) {
                x = w - (i - w - h);
                y = h - thick;
            } else {
                x = 0;
                y = h - (i - 2 * w - h);
            }
            ctx.fill(x, y, x + Math.max((int)2, (int)thick), y + Math.max((int)2, (int)thick), color);
        }
    }

    private static int lerp(int a, int b, float t) {
        int r = Math.round((float)((float)(a >> 16 & 0xFF) * (1.0f - t) + (float)(b >> 16 & 0xFF) * t));
        int g = Math.round((float)((float)(a >> 8 & 0xFF) * (1.0f - t) + (float)(b >> 8 & 0xFF) * t));
        int bl = Math.round((float)((float)(a & 0xFF) * (1.0f - t) + (float)(b & 0xFF) * t));
        return r << 16 | g << 8 | bl;
    }

    private record Layout(List<String[]> rows, Component title, Component headName, Component headBuy, Component headSell, int iconW, int nameW, int buyW, int sellW, int titleH, int headerH, int cellW, int cols, int w, int h) {
    }
}
