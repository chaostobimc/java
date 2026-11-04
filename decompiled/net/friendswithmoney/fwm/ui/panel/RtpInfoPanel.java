/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  java.util.Objects
 *  java.util.function.IntFunction
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.Objects;
import java.util.function.IntFunction;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.RtpMinimap;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RtpPanel;
import net.friendswithmoney.fwm.ui.widget.Anim;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class RtpInfoPanel
extends Panel {
    private static final int PAD = 6;
    private static final int LINE_H = 10;
    private static final int TOGGLE_W = 22;
    private static final int TOGGLE_H = 10;
    private static final int ROW_H = 18;
    private static final int BTN_H = 18;
    private final PanelHost host;
    private final SliderRow radius = new SliderRow((Component)Lang.of("fwm.minimap.radius", new Object[0]), 4, 256, RtpMinimap::radiusChunks, value -> {
        FwmConfig.INSTANCE.rtpMiniRadius = value;
    }, (IntFunction<Component>)((IntFunction)chunks -> Lang.of("fwm.minimap.chunks", chunks)));

    public RtpInfoPanel(PanelHost host) {
        this.host = host;
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
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        return this.radius.mouseDragged(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased() {
        this.radius.mouseReleased();
        ConfigManager.save();
    }

    @Override
    public boolean isCapturingKeys() {
        return this.radius.isCapturingKeys();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.radius.isCapturingKeys() && this.radius.keyPressed(keyCode, scanCode, modifiers);
    }

    private int buttonY() {
        return this.y + this.height - 18;
    }

    private int ownPosY() {
        return this.buttonY() - 6 - 18;
    }

    private int radiusY() {
        return this.ownPosY() - 4 - 26;
    }

    private int minimapY() {
        return this.radiusY() - 18;
    }

    private int headingY() {
        return this.minimapY() - 12;
    }

    private int boxH() {
        return Math.max((int)20, (int)(this.headingY() - 6 - this.y));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.renderNote(ctx, font);
        this.renderMinimap(ctx, font, mouseX, mouseY);
        this.toggle(ctx, font, mouseX, mouseY, this.ownPosY(), "fwm.rtp.ownPos", FwmConfig.INSTANCE.rtpShowOwnPos);
        this.renderContinue(ctx, font, mouseX, mouseY);
    }

    private void renderNote(GuiGraphicsExtractor ctx, Font font) {
        FwmTheme.box(ctx, this.x, this.y, this.width, this.boxH(), FwmTheme.INSET, FwmTheme.ACCENT_DIM);
        int textX = this.x + 6;
        int textW = this.width - 12;
        int bottom = this.y + this.boxH() - 6;
        int lineY = this.y + 6;
        ctx.text(font, (Component)Lang.of("fwm.rtp.info.head", new Object[0]), textX, lineY, FwmTheme.ACCENT, false);
        lineY += 13;
        List lines = font.split((FormattedText)Lang.of("fwm.rtp.info.body", new Object[0]), textW);
        for (FormattedCharSequence line : lines) {
            Objects.requireNonNull((Object)font);
            if (lineY + 9 > bottom) break;
            ctx.text(font, line, textX, lineY, FwmTheme.TEXT, false);
            lineY += 10;
        }
    }

    private void renderMinimap(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        ctx.text(font, (Component)Lang.of("fwm.rtp.minimapHead", new Object[0]), this.x + 6, this.headingY() + 2, FwmTheme.ACCENT, false);
        FwmTheme.divider(ctx, this.x + 6, this.headingY() + 11, this.width - 12);
        this.toggle(ctx, font, mouseX, mouseY, this.minimapY(), "fwm.rtp.minimapShow", Features.isOn("fwm.feature.rtpminimap"));
        this.radius.setBounds(this.x, this.radiusY(), this.width);
        this.radius.render(ctx, font, mouseX, mouseY);
    }

    private void toggle(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, int rowY, String labelKey, boolean on) {
        if (this.overRow(mouseX, mouseY, rowY)) {
            FwmTheme.hover(ctx, this.x, rowY, this.width, 18);
        }
        int toggleX = this.x + this.width - 6 - 22;
        FwmTheme.toggle(ctx, toggleX, rowY + 4, 22, 10, Anim.toward(labelKey, on, 120));
        MutableComponent label = Component.literal((String)font.plainSubstrByWidth(Lang.str(labelKey, new Object[0]), toggleX - this.x - 6 - 4));
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)label, this.x + 6, rowY + (18 - 9) / 2 + 1, on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
    }

    private void renderContinue(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean hovered = this.overContinue(mouseX, mouseY);
        FwmTheme.box(ctx, this.x, this.buttonY(), this.width, 18, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = Lang.of("fwm.rtp.info.continue", new Object[0]);
        int n = this.x + (this.width - font.width((FormattedText)label)) / 2;
        int n2 = this.buttonY();
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)label, n, n2 + (18 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    private boolean overRow(double mouseX, double mouseY, int rowY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)rowY && mouseY < (double)(rowY + 18);
    }

    private boolean overContinue(double mouseX, double mouseY) {
        return mouseX >= (double)this.x && mouseX < (double)(this.x + this.width) && mouseY >= (double)this.buttonY() && mouseY < (double)(this.buttonY() + 18);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (this.overRow(mouseX, mouseY, this.ownPosY())) {
            FwmConfig.INSTANCE.rtpShowOwnPos = !FwmConfig.INSTANCE.rtpShowOwnPos;
            ConfigManager.save();
            return true;
        }
        if (this.overRow(mouseX, mouseY, this.minimapY())) {
            Features.toggle("fwm.feature.rtpminimap");
            ConfigManager.save();
            return true;
        }
        if (this.radius.mouseClicked(mouseX, mouseY, button)) {
            return true;
        }
        if (this.overContinue(mouseX, mouseY)) {
            this.host.openSub(new RtpPanel());
            return true;
        }
        return true;
    }
}
