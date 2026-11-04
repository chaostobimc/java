/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
 *  java.util.Objects
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.widget;

import com.mojang.blaze3d.platform.InputConstants;
import java.util.Objects;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.widget.Row;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class KeybindRow
extends Row {
    private static final int MAX_BTN_W = 60;
    private static final int BTN_H = 14;
    private static final int CONFLICT = -2064256;
    private static final int CLEAR_W = 12;
    private static final int CLEAR_GAP = 3;
    private static final long CONFLICT_MS = 1500L;
    private final KeyMapping binding;
    private final boolean clearable;
    private boolean listening;
    private long conflictUntil;

    public KeybindRow(Component label, KeyMapping binding) {
        this(label, binding, true);
    }

    public KeybindRow(Component label, KeyMapping binding, boolean clearable) {
        super(label);
        this.binding = binding;
        this.clearable = clearable;
    }

    private int keyRight() {
        return this.controlRight() - 12 - 3;
    }

    private int clearX() {
        return this.controlRight() - 12;
    }

    private boolean overClear(double mouseX, double mouseY) {
        int by = this.y + 6;
        return this.clearable && mouseX >= (double)this.clearX() && mouseX < (double)(this.clearX() + 12) && mouseY >= (double)by && mouseY < (double)(by + 14);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.listening;
    }

    @Override
    protected void renderControl(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean clash;
        int btnW = Math.min((int)60, (int)(this.controlWidth() - 12 - 3));
        int bx = this.keyRight() - btnW;
        int by = this.y + 6;
        boolean bl = clash = System.currentTimeMillis() < this.conflictUntil;
        FwmTheme.box(ctx, bx, by, btnW, 14, FwmTheme.RAISED, clash ? -2064256 : (this.listening ? FwmTheme.ACCENT : FwmTheme.OFF));
        MutableComponent shown = clash ? Lang.of("fwm.key.taken", new Object[0]) : (this.listening ? Lang.of("fwm.key.listening", new Object[0]) : this.binding.getTranslatedKeyMessage());
        int n = bx + (btnW - font.width((FormattedText)shown)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)shown, n, by + (14 - 9) / 2 + 1, clash ? -2064256 : (this.listening ? FwmTheme.ACCENT : FwmTheme.TEXT), false);
        if (!this.clearable) {
            return;
        }
        boolean hot = this.overClear(mouseX, mouseY);
        FwmTheme.box(ctx, this.clearX(), by, 12, 14, FwmTheme.RAISED, hot ? -2064256 : FwmTheme.OFF);
        MutableComponent mutableComponent = Component.literal((String)"x");
        int n2 = this.clearX() + (12 - font.width("x")) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)mutableComponent, n2, by + (14 - 9) / 2 + 1, hot ? -2064256 : FwmTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.listening && button != 0) {
            this.listening = false;
            InputConstants.Key key = InputConstants.Type.MOUSE.getOrCreate(GLFW.sdlButton(button));
            if (Keybinds.isTaken(this.binding, key)) {
                this.conflictUntil = System.currentTimeMillis() + 1500L;
                return true;
            }
            this.bind(key);
            return true;
        }
        if (button != 0 || !this.isHovered(mouseX, mouseY)) {
            return false;
        }
        if (this.overClear(mouseX, mouseY)) {
            this.listening = false;
            this.conflictUntil = 0L;
            this.bind(InputConstants.UNKNOWN);
            return true;
        }
        this.listening = !this.listening;
        this.conflictUntil = 0L;
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (!this.listening) {
            return false;
        }
        this.listening = false;
        if (keyCode == 41) {
            return true;
        }
        InputConstants.Key key = InputConstants.getKey((KeyEvent)new KeyEvent(keyCode, scanCode, modifiers));
        if (Keybinds.isTaken(this.binding, key)) {
            this.conflictUntil = System.currentTimeMillis() + 1500L;
            return true;
        }
        this.bind(key);
        return true;
    }

    private void bind(InputConstants.Key key) {
        Minecraft client = Minecraft.getInstance();
        this.binding.setKey(key);
        KeyMapping.resetMapping();
        client.options.save();
        Keybinds.remember();
    }
}
