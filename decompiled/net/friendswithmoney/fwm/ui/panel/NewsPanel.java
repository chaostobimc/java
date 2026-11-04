/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.text.SimpleDateFormat
 *  java.util.Date
 *  java.util.List
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import net.friendswithmoney.fwm.net.ModNews;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;

public class NewsPanel
extends Panel {
    private static final int LINE_H = 10;
    private static final int GAP = 8;
    private static final int SCROLL_STEP = 20;
    private final ModNews.Kind kind;
    private int scroll;
    private int contentH;

    public NewsPanel(ModNews.Kind kind) {
        this.kind = kind;
    }

    @Override
    public Component title() {
        return Lang.of(switch (this.kind) {
            default -> throw new MatchException(null, null);
            case ModNews.Kind.INFOS -> "fwm.news.infos";
            case ModNews.Kind.BUGS -> "fwm.news.bugs";
            case ModNews.Kind.FIXES -> "fwm.news.fixes";
        }, new Object[0]);
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        List<ModNews.Entry> entries = ModNews.list(this.kind);
        if (entries.isEmpty()) {
            ctx.text(font, (Component)Lang.of("fwm.news.empty", new Object[0]), this.x + 4, this.y + 6, FwmTheme.TEXT_DIM, false);
            return;
        }
        int maxScroll = Math.max((int)0, (int)(this.contentH - this.height));
        this.scroll = Math.max((int)0, (int)Math.min((int)this.scroll, (int)maxScroll));
        SimpleDateFormat date = new SimpleDateFormat("dd.MM.yyyy");
        int textW = this.width - 14;
        int cy = this.y - this.scroll;
        ctx.enableScissor(this.x, this.y, this.x + this.width, this.y + this.height);
        for (ModNews.Entry entry : entries) {
            List lines = font.split((FormattedText)Component.literal((String)(entry.text == null ? "" : entry.text)), textW - 8);
            int boxH = 18 + lines.size() * 10 + 4;
            if (cy + boxH > this.y && cy < this.y + this.height) {
                FwmTheme.box(ctx, this.x, cy, textW + 4, boxH, FwmTheme.INSET, FwmTheme.EDGE);
                ctx.fill(this.x, cy, this.x + 2, cy + boxH, FwmTheme.ACCENT);
                String when = entry.createdAt > 0L ? date.format(new Date(entry.createdAt)) : "";
                int dateW = font.width(when);
                String title = entry.title == null ? "" : entry.title;
                ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(title, textW - dateW - 14)).withStyle(ChatFormatting.BOLD), this.x + 7, cy + 6, FwmTheme.TEXT, false);
                ctx.text(font, when, this.x + textW - dateW, cy + 6, FwmTheme.TEXT_DIM, false);
                int ly = cy + 6 + 10 + 2;
                for (FormattedCharSequence line : lines) {
                    ctx.text(font, line, this.x + 7, ly, FwmTheme.TEXT_DIM, false);
                    ly += 10;
                }
            }
            cy += boxH + 8;
        }
        ctx.disableScissor();
        this.contentH = cy + this.scroll - this.y - 8;
        if (this.contentH > this.height) {
            int barH = Math.max((int)12, (int)(this.height * this.height / this.contentH));
            int barY = this.y + (this.height - barH) * this.scroll / Math.max((int)1, (int)(this.contentH - this.height));
            ctx.fill(this.x + this.width - 3, this.y, this.x + this.width - 1, this.y + this.height, 0x22FFFFFF);
            ctx.fill(this.x + this.width - 3, barY, this.x + this.width - 1, barY + barH, FwmTheme.ACCENT);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        this.scroll -= (int)Math.signum((double)vertical) * 20;
        return true;
    }
}
