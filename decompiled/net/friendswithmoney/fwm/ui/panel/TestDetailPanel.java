/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.Locale
 *  java.util.Objects
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.Locale;
import java.util.Objects;
import net.friendswithmoney.fwm.config.TestRun;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class TestDetailPanel
extends Panel {
    private static final int LINE_H = 12;
    private static final int SLIDER_H = 16;
    private static final int TRACK_H = 2;
    private static final int KNOB_W = 5;
    private static final int KNOB_H = 11;
    private static final int MIN_TENTHS = 10;
    private static final int MAX_TENTHS = 30;
    private final TestRun run;
    private int tenths;
    private boolean dragging;

    public TestDetailPanel(TestRun run) {
        this.run = run;
        double measured = run.measuredMultiplier();
        this.tenths = measured < 0.0 ? 20 : TestDetailPanel.clamp((int)Math.round((double)(measured * 10.0)));
    }

    private static int clamp(int value) {
        return Math.max((int)10, (int)Math.min((int)30, (int)value));
    }

    private double multiplier() {
        return (double)this.tenths / 10.0;
    }

    @Override
    public Component title() {
        return Component.literal((String)(this.run.name == null ? Lang.str("fwm.test.listTitle", new Object[0]) : this.run.name));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int line = 0;
        line = this.row(ctx, font, line, Lang.str("fwm.test.measured", new Object[0]), Fmt.compactMoneyUp(this.run.cents), FwmTheme.ACCENT);
        line = this.row(ctx, font, line, Lang.str("fwm.test.base", new Object[0]), Fmt.compactMoneyUp(this.run.baseCents), FwmTheme.TEXT);
        line = this.row(ctx, font, line, Lang.str("fwm.stats.items", new Object[0]), Fmt.compact(this.run.items), FwmTheme.TEXT);
        int sliderY = this.y + line * 12 + 4;
        this.renderSlider(ctx, font, sliderY);
        long perMinute = Math.round((double)((double)this.run.baseCents * this.multiplier()));
        int projected = line + 2;
        FwmTheme.divider(ctx, this.x, sliderY + 16 + 2, this.width);
        projected = this.row(ctx, font, projected, Lang.str("fwm.test.perMinute", new Object[0]), Fmt.compactMoneyUp(perMinute), FwmTheme.ACCENT);
        this.row(ctx, font, projected, Lang.str("fwm.test.perHour", new Object[0]), Fmt.compactMoneyUp(perMinute * 60L), FwmTheme.ACCENT);
    }

    private int row(GuiGraphicsExtractor ctx, Font font, int line, String label, String value, int color) {
        int ly = this.y + line * 12;
        ctx.text(font, (Component)Component.literal((String)label), this.x + 2, ly + 2, FwmTheme.TEXT_DIM, false);
        ctx.text(font, (Component)Component.literal((String)value), this.x + this.width - 2 - font.width(value), ly + 2, color, false);
        return line + 1;
    }

    private int trackX() {
        return this.x + 60;
    }

    private int trackW() {
        return Math.max((int)20, (int)(this.width - 60 - 46));
    }

    private void renderSlider(GuiGraphicsExtractor ctx, Font font, int sliderY) {
        MutableComponent label = Lang.of("fwm.test.multi", new Object[0]);
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)label, this.x + 2, sliderY + (16 - 9) / 2 + 1, FwmTheme.TEXT_DIM, false);
        int ty = sliderY + 7;
        int filled = Math.round((float)((float)this.trackW() * this.progress()));
        ctx.fill(this.trackX(), ty, this.trackX() + this.trackW(), ty + 2, FwmTheme.OFF);
        ctx.fill(this.trackX(), ty, this.trackX() + filled, ty + 2, FwmTheme.ACCENT);
        FwmTheme.pill(ctx, this.trackX() + filled - 2, sliderY + 2, 5, 11, FwmTheme.ACCENT);
        MutableComponent value = Component.literal((String)String.format((Locale)Locale.ROOT, (String)"x%.1f", (Object[])new Object[]{this.multiplier()}));
        int n = this.x + this.width - 2 - font.width((FormattedText)value);
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)value, n, sliderY + (16 - 9) / 2 + 1, FwmTheme.TEXT, false);
    }

    private float progress() {
        return (float)(this.tenths - 10) / 20.0f;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseX >= (double)(this.trackX() - 5) && mouseX <= (double)(this.trackX() + this.trackW() + 5)) {
            this.dragging = true;
            this.applyFromMouse(mouseX);
        }
        return true;
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        if (!this.dragging) {
            return false;
        }
        this.applyFromMouse(mouseX);
        return true;
    }

    @Override
    public void mouseReleased() {
        this.dragging = false;
    }

    private void applyFromMouse(double mouseX) {
        double progress = (mouseX - (double)this.trackX()) / (double)this.trackW();
        progress = Math.max((double)0.0, (double)Math.min((double)1.0, (double)progress));
        this.tenths = 10 + (int)Math.round((double)(progress * 20.0));
    }
}
