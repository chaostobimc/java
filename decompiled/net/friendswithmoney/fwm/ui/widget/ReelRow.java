/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Random
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.widget;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ReelRow
extends Row {
    private static final int CARD_W = 62;
    private static final int STEP = 66;
    private static final int CARDS = 40;
    private static final int WINNER_AT = 34;
    private static final int GOLD = -865972;
    private static long builtFor;
    private static List<String> reel;

    public ReelRow() {
        super((Component)Component.empty());
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        List<Giveaway.Participant> all = Giveaway.participants();
        String winner = Giveaway.winner();
        if (all.isEmpty() || winner == null) {
            return;
        }
        if (builtFor != Giveaway.drawnAt()) {
            builtFor = Giveaway.drawnAt();
            Random random = new Random(builtFor);
            ArrayList cards = new ArrayList();
            for (int i = 0; i < 40; ++i) {
                cards.add((Object)((Giveaway.Participant)((Object)all.get(random.nextInt(all.size())))).name());
            }
            cards.set(34, (Object)winner);
            reel = cards;
        }
        int top = this.y + 2;
        int h = 48;
        LauncherGfx.round(ctx, this.x, top, this.width, h, 6, 1711675152);
        LauncherGfx.ring(ctx, this.x, top, this.width, h, 6, 0x1FFFFFFF);
        int marker = this.x + this.width / 2;
        int startX = this.x + this.width;
        float t = Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - Giveaway.drawnAt()) / 4500.0f));
        float eased = 1.0f - (1.0f - t) * (1.0f - t) * (1.0f - t);
        float endOffset = (float)(startX + 2244) + 31.0f - (float)marker;
        int offset = Math.round((float)(endOffset * eased));
        boolean stopped = t >= 1.0f;
        ctx.enableScissor(this.x + 2, top + 2, this.x + this.width - 2, top + h - 2);
        for (int i = 0; i < reel.size(); ++i) {
            int cx = startX + i * 66 - offset;
            if (cx + 62 < this.x || cx > this.x + this.width) continue;
            boolean win = stopped && i == 34;
            LauncherGfx.round(ctx, cx, top + 5, 62, h - 10, 5, win ? -420296372 : -870959062);
            LauncherGfx.ring(ctx, cx, top + 5, 62, h - 10, 5, win ? -1 : 0x1FFFFFFF);
            FwmGfx.playerHead(ctx, (String)reel.get(i), cx + 23, top + 9, 16);
            String name = font.plainSubstrByWidth((String)reel.get(i), 56);
            ctx.text(font, (Component)Component.literal((String)name), cx + (62 - font.width(name)) / 2, top + h - 17, win ? -16045545 : FwmTheme.TEXT, false);
        }
        ctx.disableScissor();
        ctx.fill(marker, top + 2, marker + 1, top + h - 2, -865972);
        ctx.fill(marker - 3, top + 1, marker + 4, top + 2, -865972);
        ctx.fill(marker - 2, top + 2, marker + 3, top + 3, -865972);
        ctx.fill(marker - 1, top + 3, marker + 2, top + 4, -865972);
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
    }

    static {
        reel = List.of();
    }

    public static class Spacer
    extends Row {
        public Spacer() {
            super((Component)Component.empty());
        }

        @Override
        public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        }

        @Override
        protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        }
    }
}
