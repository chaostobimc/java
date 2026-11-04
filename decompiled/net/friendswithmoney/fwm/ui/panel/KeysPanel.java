/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.KeybindRow;
import net.minecraft.network.chat.Component;

public class KeysPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.keys", new Object[0]);
    }

    @Override
    protected void buildRows() {
        this.rows.add((Object)new KeybindRow((Component)Lang.of("fwm.option.keyToggle", new Object[0]), Keybinds.TOGGLE));
        this.rows.add((Object)new KeybindRow((Component)Lang.of("fwm.option.keyMenu", new Object[0]), Keybinds.OPEN_MENU, false));
        this.rows.add((Object)new KeybindRow((Component)Lang.of("fwm.option.keyMessages", new Object[0]), Keybinds.OPEN_MESSAGES));
        this.rows.add((Object)new KeybindRow((Component)Lang.of("fwm.option.keyAuction", new Object[0]), Keybinds.OPEN_AUCTION));
        this.rows.add((Object)new KeybindRow((Component)Lang.of("fwm.option.keyGiveaway", new Object[0]), Keybinds.OPEN_GIVEAWAY));
    }
}
