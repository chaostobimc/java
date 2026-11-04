/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.stats.SellStats;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class SellStatsPanel
extends Panel {
    private final PanelHost host;
    private static final int LINE_H = 12;
    private static final int BTN_H = 14;

    public SellStatsPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.sellstats", new Object[0]);
    }

    private List<Line> lines() {
        ArrayList lines = new ArrayList();
        long perHour = SellStats.perHour();
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.revenue", new Object[0]), Fmt.compactMoneyUp(SellStats.total()), FwmTheme.ACCENT));
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.perHour", new Object[0]), perHour < 0L ? Lang.str("fwm.stats.tooEarly", new Object[0]) : Fmt.compactMoneyUp(perHour), FwmTheme.TEXT));
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.items", new Object[0]), Fmt.compact(SellStats.items()), FwmTheme.TEXT));
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.runtime", new Object[0]), Fmt.duration(SellStats.runtimeMillis()), FwmTheme.TEXT));
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.last24h", new Object[0]), Fmt.compactMoneyUp(SellStats.last24h()), FwmTheme.TEXT));
        lines.add((Object)new Line((Component)Lang.of("fwm.stats.lifetime", new Object[0]), Fmt.compactMoneyUp(FwmConfig.INSTANCE.lifetimeEarnedCents), FwmTheme.TEXT_DIM));
        long test = SellStats.lastTest();
        if (test >= 0L) {
            lines.add((Object)new Line((Component)Lang.of("fwm.stats.testResult", new Object[0]), Fmt.compactMoneyUp(test), FwmTheme.ACCENT));
        } else if (SellStats.testAborted()) {
            lines.add((Object)new Line((Component)Lang.of("fwm.stats.testResult", new Object[0]), Lang.str("fwm.stats.testAborted", new Object[0]), FwmTheme.TEXT_DIM));
        }
        return lines;
    }

    private int buttonY() {
        return this.y + this.height - 14;
    }

    private int testW() {
        return this.width;
    }

    private boolean overButton(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.testW()) && mouseY >= (double)this.buttonY() && mouseY < (double)(this.buttonY() + 14);
    }

    private void renderButton(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean running = SellStats.testRunning();
        boolean hovered = !running && this.overButton(mouseX, mouseY);
        FwmTheme.box(ctx, this.x, this.buttonY(), this.testW(), 14, FwmTheme.RAISED, running || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = SellStats.testSettling() ? Lang.of("fwm.stats.testSettling", new Object[0]) : (running ? Lang.of("fwm.stats.testRunning", SellStats.testSecondsLeft()) : Lang.of("fwm.stats.minuteTest", new Object[0]));
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label.getString(), this.testW() - 6));
        int n = this.x + (this.testW() - font.width((FormattedText)shown)) / 2;
        int n2 = this.buttonY();
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)shown, n, n2 + (14 - 9) / 2 + 1, running || hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    private void startTest() {
        SellStats.startMinuteTest();
        FwmConfig.INSTANCE.enabled = true;
        ConfigManager.save();
        Minecraft.getInstance().gui.setScreen(null);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (!SellStats.testRunning() && this.overButton(mouseX, mouseY)) {
            this.startTest();
        }
        return true;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int ly;
        List<Line> lines = this.lines();
        for (int i = 0; i < lines.size() && (ly = this.y + i * 12) + 12 <= this.buttonY() - 2; ++i) {
            Line line = (Line)((Object)lines.get(i));
            ctx.text(font, line.label(), this.x + 2, ly + 2, FwmTheme.TEXT_DIM, false);
            MutableComponent value = Component.literal((String)line.value());
            ctx.text(font, (Component)value, this.x + this.width - 2 - font.width((FormattedText)value), ly + 2, line.color(), false);
        }
        this.renderButton(ctx, font, mouseX, mouseY);
    }

    private record Line(Component label, String value, int color) {
    }
}
