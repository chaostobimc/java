/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.Market;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.net.PricePings;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.MarketPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class PricePingPanel
extends Panel {
    private static final int ROW_H = 14;
    private static final int FIELD_H = 14;
    private static final int BTN_H = 14;
    private static final int LINE_H = 10;
    private static final int MODE_H = 13;
    private final MarketData.Item item;
    private final String name;
    private final PanelHost host;
    private EditBox field;
    private boolean order;
    private int modeY;
    private int modeW;
    private boolean below;
    private boolean repeat;
    private boolean toast;
    private boolean chat;
    private int optY;
    private int optW;
    private String status;
    private boolean statusError;
    private int fieldY;
    private int setX;
    private int removeX;
    private int buttonW;
    private int listTop;

    public PricePingPanel(MarketData.Item item, String name, PanelHost host) {
        this.item = item;
        this.name = name;
        this.host = host;
        this.order = PricePings.find(item.itemKey, false) == null && PricePings.find(item.itemKey, true) != null;
        this.loadOptions();
    }

    private void loadOptions() {
        FwmConfig.PricePing ping = PricePings.find(this.item.itemKey, this.order);
        this.below = ping == null ? !this.order : ping.below();
        this.repeat = ping != null && ping.repeat;
        this.toast = ping == null || ping.toastOn();
        this.chat = ping != null && ping.chat;
    }

    private String explainKey() {
        return this.order ? "fwm.orderping.explain" : "fwm.priceping.explain";
    }

    @Override
    public Component title() {
        return Lang.of("fwm.priceping.title", new Object[0]);
    }

    @Override
    protected void onResize() {
        String previous;
        String string = previous = this.field == null ? this.initialText() : this.field.getValue();
        if (this.field != null) {
            this.host.detach((AbstractWidget)this.field);
        }
        Font font = Minecraft.getInstance().font;
        this.modeY = this.y + 19;
        this.optY = this.modeY + 13 + 3;
        int lines = font.split((FormattedText)Lang.of(this.explainKey(), new Object[0]), this.width).size();
        this.fieldY = this.optY + 13 + 4 + lines * 10 + 4;
        this.field = new EditBox(font, this.x + 5, this.fieldY + 3, this.width / 2 - 10, 10, (Component)Lang.of("fwm.priceping.field", new Object[0]));
        this.field.setBordered(false);
        this.field.setMaxLength(16);
        this.field.setTextColor(FwmTheme.TEXT);
        this.field.setValue(previous);
        this.field.setFocused(true);
        this.host.attach((AbstractWidget)this.field);
    }

    private String initialText() {
        FwmConfig.PricePing ping = PricePings.find(this.item.itemKey, this.order);
        if (ping != null) {
            return Fmt.compactUp(Math.round((double)ping.maxPrice));
        }
        Double avg = Market.avgPrice(this.item.itemKey);
        return avg == null ? "" : Fmt.compactUp(Math.round((double)avg));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        MarketPanel.drawIcon(ctx, this.item, this.x, this.y + 1);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(this.name, this.width - 22)), this.x + 20, this.y + 5, FwmTheme.TEXT, false);
        this.modeW = (this.width - 4) / 2;
        this.modeChip(ctx, font, this.x, Lang.str("fwm.priceping.modeAh", new Object[0]), !this.order, mouseX, mouseY);
        this.modeChip(ctx, font, this.x + this.modeW + 4, Lang.str("fwm.orderping.mode", new Object[0]), this.order, mouseX, mouseY);
        this.optW = (this.width - 12) / 4;
        this.option(ctx, font, 0, this.below ? Lang.t("G\u00fcnstiger", "Cheaper") : Lang.t("Teurer", "Dearer"), true, mouseX, mouseY);
        this.option(ctx, font, 1, this.repeat ? Lang.t("Mehrmals", "Every time") : Lang.t("Einmal", "Once"), true, mouseX, mouseY);
        this.option(ctx, font, 2, Lang.t("Mitteilung", "Toast"), this.toast, mouseX, mouseY);
        this.option(ctx, font, 3, "Chat", this.chat, mouseX, mouseY);
        int lineY = this.optY + 13 + 4;
        for (FormattedCharSequence line : font.split((FormattedText)Lang.of(this.explainKey(), new Object[0]), this.width)) {
            ctx.text(font, line, this.x, lineY, FwmTheme.TEXT_DIM, false);
            lineY += 10;
        }
        int fieldW = this.width / 2;
        FwmTheme.box(ctx, this.x, this.fieldY, fieldW, 14, FwmTheme.INSET, this.field.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
        this.field.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        this.buttonW = (this.width - fieldW - 8) / 2;
        this.setX = this.x + fieldW + 4;
        this.removeX = this.setX + this.buttonW + 4;
        boolean hasPing = PricePings.find(this.item.itemKey, this.order) != null;
        this.button(ctx, font, this.setX, this.fieldY, Lang.str("fwm.priceping.set", new Object[0]), true, mouseX, mouseY);
        this.button(ctx, font, this.removeX, this.fieldY, Lang.str("fwm.priceping.remove", new Object[0]), hasPing, mouseX, mouseY);
        int statusY = this.fieldY + 14 + 3;
        if (this.status != null) {
            ctx.text(font, (Component)Lang.of(this.status, 10), this.x, statusY, this.statusError ? -1547164 : -12723068, false);
        } else if (!Features.isOn("fwm.feature.priceping")) {
            ctx.text(font, (Component)Lang.of("fwm.priceping.off", new Object[0]), this.x, statusY, -1547164, false);
        }
        this.renderList(ctx, font, statusY + 10 + 4, mouseX, mouseY);
    }

    private void modeChip(GuiGraphicsExtractor ctx, Font font, int bx, String label, boolean on, int mouseX, int mouseY) {
        boolean hovered = !on && mouseX >= bx && mouseX < bx + this.modeW && mouseY >= this.modeY && mouseY < this.modeY + 13;
        FwmTheme.box(ctx, bx, this.modeY, this.modeW, 13, on ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, on || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label, this.modeW - 4));
        ctx.text(font, (Component)shown, bx + (this.modeW - font.width((FormattedText)shown)) / 2, this.modeY + 3, on ? FwmTheme.ACCENT : (hovered ? FwmTheme.TEXT : FwmTheme.TEXT_DIM), false);
    }

    private void option(GuiGraphicsExtractor ctx, Font font, int i, String label, boolean on, int mouseX, int mouseY) {
        int bx = this.x + i * (this.optW + 4);
        boolean hovered = mouseX >= bx && mouseX < bx + this.optW && mouseY >= this.optY && mouseY < this.optY + 13;
        FwmTheme.box(ctx, bx, this.optY, this.optW, 13, on ? FwmTheme.RAISED : FwmTheme.LOCKED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label, this.optW - 4));
        ctx.text(font, (Component)shown, bx + (this.optW - font.width((FormattedText)shown)) / 2, this.optY + 3, on ? (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT) : FwmTheme.TEXT_DIM, false);
    }

    private void button(GuiGraphicsExtractor ctx, Font font, int bx, int by, String label, boolean enabled, int mouseX, int mouseY) {
        boolean hovered = enabled && mouseX >= bx && mouseX < bx + this.buttonW && mouseY >= by && mouseY < by + 14;
        FwmTheme.box(ctx, bx, by, this.buttonW, 14, enabled ? FwmTheme.RAISED : FwmTheme.LOCKED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label, this.buttonW - 4));
        ctx.text(font, (Component)shown, bx + (this.buttonW - font.width((FormattedText)shown)) / 2, by + 3, !enabled ? FwmTheme.TEXT_DIM : (hovered ? FwmTheme.ACCENT : FwmTheme.TEXT), false);
    }

    private void renderList(GuiGraphicsExtractor ctx, Font font, int top, int mouseX, int mouseY) {
        int rowY;
        List<FwmConfig.PricePing> pings = PricePings.list();
        this.listTop = top + 10 + 2;
        ctx.text(font, (Component)Lang.of("fwm.priceping.active", pings.size(), 10), this.x, top, FwmTheme.ACCENT, false);
        FwmTheme.divider(ctx, this.x, top + 10, this.width);
        for (int i = 0; i < pings.size() && (rowY = this.listTop + i * 14) + 14 <= this.y + this.height; ++i) {
            FwmConfig.PricePing ping = (FwmConfig.PricePing)pings.get(i);
            String price = (ping.order ? ">= " : "<= ") + "$" + Fmt.compactUp(Math.round((double)ping.maxPrice));
            String label = (ping.order ? "Order: " : "AH: ") + (ping.name == null ? ping.itemKey : ping.name);
            boolean overX = mouseX >= this.x + this.width - 12 && mouseX < this.x + this.width && mouseY >= rowY && mouseY < rowY + 14;
            ctx.text(font, (Component)Component.literal((String)"x"), this.x + this.width - 9, rowY + 3, overX ? -1547164 : FwmTheme.TEXT_DIM, false);
            int priceX = this.x + this.width - 16 - font.width(price);
            ctx.text(font, (Component)Component.literal((String)price), priceX, rowY + 3, FwmTheme.TEXT, false);
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(label, priceX - this.x - 6)), this.x, rowY + 3, ping.itemKey.equals((Object)this.item.itemKey) && ping.order == this.order ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        }
    }

    private void apply() {
        double price = PricePings.parsePrice(this.field.getValue());
        if (Double.isNaN((double)price)) {
            this.status = "fwm.priceping.bad";
            this.statusError = true;
            return;
        }
        if (!PricePings.set(this.item, this.name, price, this.order, this.below, this.repeat, this.toast, this.chat)) {
            this.status = "fwm.priceping.full";
            this.statusError = true;
            return;
        }
        this.field.setValue(Fmt.compactUp(Math.round((double)price)));
        this.status = "fwm.priceping.saved";
        this.statusError = false;
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        boolean onButtons;
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseY >= (double)this.modeY && mouseY < (double)(this.modeY + 13)) {
            boolean want;
            boolean bl = want = mouseX >= (double)(this.x + this.modeW + 4);
            if (want != this.order) {
                this.order = want;
                this.status = null;
                this.loadOptions();
                if (this.field != null) {
                    this.field.setValue(this.initialText());
                }
                this.onResize();
            }
            return true;
        }
        if (mouseY >= (double)this.optY && mouseY < (double)(this.optY + 13) && this.optW > 0) {
            int i = (int)((mouseX - (double)this.x) / (double)(this.optW + 4));
            switch (i) {
                case 0: {
                    this.below = !this.below;
                    break;
                }
                case 1: {
                    this.repeat = !this.repeat;
                    break;
                }
                case 2: {
                    this.toast = !this.toast;
                    break;
                }
                case 3: {
                    this.chat = !this.chat;
                    break;
                }
                default: {
                    return true;
                }
            }
            if (PricePings.find(this.item.itemKey, this.order) != null) {
                this.apply();
            }
            return true;
        }
        boolean bl = onButtons = mouseY >= (double)this.fieldY && mouseY < (double)(this.fieldY + 14);
        if (onButtons && mouseX >= (double)this.setX && mouseX < (double)(this.setX + this.buttonW)) {
            this.apply();
            return true;
        }
        if (onButtons && mouseX >= (double)this.removeX && mouseX < (double)(this.removeX + this.buttonW) && PricePings.find(this.item.itemKey, this.order) != null) {
            PricePings.remove(this.item.itemKey, this.order);
            this.status = "fwm.priceping.removed";
            this.statusError = false;
            return true;
        }
        if (onButtons && mouseX < (double)(this.x + this.width / 2)) {
            this.field.setFocused(true);
            return true;
        }
        if (mouseX >= (double)(this.x + this.width - 12) && mouseY >= (double)this.listTop) {
            int index = (int)((mouseY - (double)this.listTop) / 14.0);
            List<FwmConfig.PricePing> pings = PricePings.list();
            if (index >= 0 && index < pings.size()) {
                PricePings.remove(((FwmConfig.PricePing)pings.get((int)index)).itemKey, ((FwmConfig.PricePing)pings.get((int)index)).order);
                this.status = null;
            }
        }
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.field == null || !this.field.isFocused()) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            this.apply();
            return true;
        }
        return this.field.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.field != null && this.field.isFocused() && this.field.charTyped(input);
    }

    @Override
    public void onHidden() {
        if (this.field != null) {
            this.host.detach((AbstractWidget)this.field);
        }
    }
}
