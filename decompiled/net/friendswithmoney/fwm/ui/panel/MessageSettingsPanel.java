/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.network.chat.Component;

public class MessageSettingsPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.messages.settings", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.messages.showEnvelope", new Object[0]), () -> config.msgShowEnvelope, (Consumer<Boolean>)((Consumer)value -> {
            config.msgShowEnvelope = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.messages.showToast", new Object[0]), () -> config.msgShowToast, (Consumer<Boolean>)((Consumer)value -> {
            config.msgShowToast = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.messages.showToastHint", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("AUSGEBLENDETE PERSONEN", "HIDDEN PEOPLE")), null));
        if (config.msgHidden.isEmpty()) {
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Niemand ausgeblendet.", "Nobody hidden.")), null));
        }
        for (String name : new ArrayList(config.msgHidden)) {
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)name), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Einblenden", "Show again"))), () -> {
                Messages.unhide(name);
                this.onResize();
            }));
        }
    }
}
