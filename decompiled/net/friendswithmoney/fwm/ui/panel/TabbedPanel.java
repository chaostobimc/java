/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Class
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.function.Supplier
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.widget.TabStrip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.network.chat.Component;

public class TabbedPanel
extends Panel {
    private final String titleKey;
    private final List<Tab> tabs;
    private final List<Panel> built;
    private final TabStrip strip;

    public TabbedPanel(String titleKey, List<Tab> tabs) {
        this.titleKey = titleKey;
        this.tabs = tabs;
        this.built = new ArrayList();
        this.strip = new TabStrip((List<String>)tabs.stream().map(Tab::labelKey).toList());
        for (int i = 0; i < tabs.size(); ++i) {
            this.built.add(null);
        }
    }

    @Override
    public Component title() {
        return Lang.of(this.titleKey, new Object[0]);
    }

    public void setTab(int index) {
        if (index < 0 || index >= this.tabs.size() || index == this.strip.active()) {
            return;
        }
        Panel previous = this.current();
        if (previous != null) {
            previous.onHidden();
        }
        this.strip.setActive(index);
        this.applyBounds();
    }

    public boolean shows(Class<? extends Panel> kind) {
        return kind.isInstance((Object)this.current());
    }

    private Panel current() {
        int index = this.strip.active();
        return index < this.built.size() ? (Panel)this.built.get(index) : null;
    }

    private Panel active() {
        int index = this.strip.active();
        Panel panel = (Panel)this.built.get(index);
        if (panel == null) {
            panel = (Panel)((Tab)((Object)this.tabs.get(index))).panel().get();
            this.built.set(index, (Object)panel);
        }
        return panel;
    }

    @Override
    protected void onResize() {
        this.applyBounds();
    }

    private void applyBounds() {
        this.strip.setBounds(this.x, this.y, this.width);
        int top = this.y + 13 + 4;
        this.active().setBounds(this.x, top, this.width, Math.max((int)10, (int)(this.y + this.height - top)));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.strip.render(ctx, font, mouseX, mouseY);
        this.active().render(ctx, font, mouseX, mouseY, delta);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        int tab = this.strip.indexAt(mouseX, mouseY);
        if (tab >= 0) {
            this.setTab(tab);
            return true;
        }
        return this.active().mouseClicked(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseDragged(double mouseX, double mouseY, int button) {
        return this.active().mouseDragged(mouseX, mouseY, button);
    }

    @Override
    public void mouseReleased() {
        this.active().mouseReleased();
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        return this.active().mouseScrolled(mouseX, mouseY, vertical);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.active().isCapturingKeys();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.active().keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.active().charTyped(input);
    }

    @Override
    public void onHidden() {
        for (Panel panel : this.built) {
            if (panel == null) continue;
            panel.onHidden();
        }
    }

    public record Tab(String labelKey, Supplier<Panel> panel) {
    }
}
