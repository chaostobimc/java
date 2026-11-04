/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.Objects
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.network.chat.TextColor
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.colorcode.CodeStyle;
import net.friendswithmoney.fwm.colorcode.ColorCode;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.panel.ColorCodeEditorPanel;
import net.friendswithmoney.fwm.ui.panel.ColorCodeTemplatesPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.TabbedPanel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.util.FormattedCharSequence;

public class ColorCodePanel
extends TabbedPanel {
    private static final User SESSION = new User();

    public ColorCodePanel(PanelHost host) {
        super("fwm.tab.colorcodes", (List<TabbedPanel.Tab>)List.of((Object)((Object)new TabbedPanel.Tab("fwm.colorcodes.editor", (Supplier<Panel>)((Supplier)() -> new ColorCodeEditorPanel(host, SESSION)))), (Object)((Object)new TabbedPanel.Tab("fwm.colorcodes.templates", (Supplier<Panel>)((Supplier)() -> new ColorCodeTemplatesPanel(SESSION))))));
        ColorCodePanel.SESSION.owner = this;
    }

    static Style style(CodeStyle s, boolean withBold) {
        return Style.EMPTY.withColor(TextColor.fromRgb((int)ColorCode.rgb(s.color()))).withBold(Boolean.valueOf((withBold && s.bold() ? 1 : 0) != 0)).withUnderlined(Boolean.valueOf((boolean)s.underline())).withItalic(Boolean.valueOf((boolean)s.italic())).withStrikethrough(Boolean.valueOf((boolean)s.strike())).withObfuscated(Boolean.valueOf((boolean)s.obf()));
    }

    static MutableComponent text(ColorCode code) {
        MutableComponent out = Component.empty();
        for (ColorCode.Run run : code.runs()) {
            out.append((Component)Component.literal((String)run.text()).setStyle(ColorCodePanel.style(run.style(), true)));
        }
        return out;
    }

    static FormattedCharSequence trimmed(Font font, Component text, int width) {
        return Language.getInstance().getVisualOrder(font.substrByWidth((FormattedText)text, Math.max((int)0, (int)width)));
    }

    static void button(GuiGraphicsExtractor ctx, Font font, int bx, int by, int bw, int bh, Component label, boolean hovered, boolean active, boolean enabled) {
        int fill;
        int n = !enabled ? FwmTheme.LOCKED : (fill = active ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED);
        int edge = !enabled ? FwmTheme.OFF : (hovered || active ? FwmTheme.ACCENT : FwmTheme.OFF);
        FwmTheme.box(ctx, bx, by, bw, bh, fill, edge);
        FormattedCharSequence line = ColorCodePanel.trimmed(font, label, bw - 4);
        int color = !enabled ? FwmTheme.TEXT_DIM : (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT);
        int n2 = bx + (bw - font.width(line)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, line, n2, by + (bh - 9) / 2 + 1, color, false);
    }

    static boolean over(double mx, double my, int bx, int by, int bw, int bh) {
        return mx >= (double)bx && mx < (double)(bx + bw) && my >= (double)by && my < (double)(by + bh);
    }

    static void tip(GuiGraphicsExtractor ctx, Font font, String text, int mouseX, int mouseY) {
        int screenW;
        int tw = font.width(text) + 8;
        int tx = mouseX + 10 + tw > (screenW = ctx.guiWidth()) ? mouseX - 6 - tw : mouseX + 10;
        int ty = mouseY - 14;
        ctx.nextStratum();
        ColorCodePanel.tooltipBox(ctx, tx, ty, tw, 13);
        ctx.text(font, (Component)Component.literal((String)text), tx + 4, ty + 3, -1, true);
    }

    static void tooltipBox(GuiGraphicsExtractor ctx, int bx, int by, int bw, int bh) {
        ctx.fill(bx + 1, by, bx + bw - 1, by + bh, -267386864);
        ctx.fill(bx, by + 1, bx + 1, by + bh - 1, -267386864);
        ctx.fill(bx + bw - 1, by + 1, bx + bw, by + bh - 1, -267386864);
        ctx.fill(bx + 1, by + 1, bx + bw - 1, by + 2, 0x505000FF);
        ctx.fill(bx + 1, by + bh - 2, bx + bw - 1, by + bh - 1, 1344798847);
        ctx.fill(bx + 1, by + 2, bx + 2, by + bh - 2, 0x505000FF);
        ctx.fill(bx + bw - 2, by + 2, bx + bw - 1, by + bh - 2, 0x505000FF);
    }

    static final class User {
        final ColorCode code = new ColorCode();
        ColorCode.Mode mode = ColorCode.Mode.RENAME;
        int version;
        ColorCodePanel owner;

        User() {
        }

        void load(ColorCode content, ColorCode.Mode newMode) {
            this.code.set(content);
            if (newMode != null) {
                this.mode = newMode;
            }
            ++this.version;
        }
    }
}
