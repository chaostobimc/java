/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.HudEditScreen;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class OverlayPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.overlay", new Object[0]);
    }

    @Override
    protected void buildRows() {
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.option.hudEditHint", new Object[0]), null));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.option.hudEdit", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.option.hudEditOpen", new Object[0])), () -> {
            Minecraft client = Minecraft.getInstance();
            client.gui.setScreen((Screen)new HudEditScreen(client.gui.screen()));
        }));
    }
}
