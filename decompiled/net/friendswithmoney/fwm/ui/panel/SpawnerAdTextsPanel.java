/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;

public class SpawnerAdTextsPanel
extends Panel {
    private static final int FIELD_H = 14;
    private static final int LABEL_H = 10;
    private static final int SLOT_H = 28;
    private final PanelHost host;
    private final EditBox[] fields = new EditBox[5];

    public SpawnerAdTextsPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.spawnerad.textsTitle", new Object[0]);
    }

    private int slotY(int i) {
        return this.y + 20 + 4 + i * 28;
    }

    @Override
    protected void onResize() {
        this.store();
        this.detach();
        Font font = Minecraft.getInstance().font;
        List<String> templates = FwmConfig.INSTANCE.spawnerAdTemplates;
        for (int i = 0; i < this.fields.length; ++i) {
            EditBox field = new EditBox(font, this.x + 4, this.slotY(i) + 10 + 3, this.width - 8, 10, (Component)Lang.of("fwm.spawnerad.slot", i + 1));
            field.setBordered(false);
            field.setMaxLength(256);
            field.setTextColor(FwmTheme.TEXT);
            field.setValue(i < templates.size() ? (String)templates.get(i) : "");
            field.moveCursorToStart(false);
            this.host.attach((AbstractWidget)field);
            this.fields[i] = field;
        }
    }

    private void detach() {
        for (EditBox field : this.fields) {
            if (field == null) continue;
            this.host.detach((AbstractWidget)field);
        }
    }

    private void store() {
        if (this.fields[0] == null) {
            return;
        }
        ArrayList templates = new ArrayList();
        for (EditBox field : this.fields) {
            templates.add((Object)field.getValue().trim());
        }
        FwmConfig.INSTANCE.spawnerAdTemplates = templates;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        ctx.text(font, (Component)Lang.of("fwm.spawnerad.textsHint", new Object[0]), this.x + 2, this.y + 1, FwmTheme.TEXT_DIM, false);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str("fwm.spawnerad.placeholders", new Object[0]), this.width - 4)), this.x + 2, this.y + 1 + 10, FwmTheme.ACCENT, false);
        for (int i = 0; i < this.fields.length; ++i) {
            int sy = this.slotY(i);
            ctx.text(font, (Component)Lang.of("fwm.spawnerad.slot", i + 1), this.x + 2, sy, FwmTheme.TEXT, false);
            FwmTheme.box(ctx, this.x, sy + 10, this.width, 14, FwmTheme.INSET, this.fields[i].isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            this.fields[i].extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        for (int i = 0; i < this.fields.length; ++i) {
            int fy = this.slotY(i) + 10;
            this.fields[i].setFocused(mouseY >= (double)fy && mouseY < (double)(fy + 14));
        }
        this.store();
        return true;
    }

    private EditBox focused() {
        for (EditBox field : this.fields) {
            if (field == null || !field.isFocused()) continue;
            return field;
        }
        return null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EditBox field = this.focused();
        if (field == null) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            field.setFocused(false);
            this.store();
            ConfigManager.save();
            return true;
        }
        return field.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        EditBox field = this.focused();
        return field != null && field.charTyped(input);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.focused() != null;
    }

    @Override
    public void onHidden() {
        this.store();
        ConfigManager.save();
        this.detach();
    }
}
