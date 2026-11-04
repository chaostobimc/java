/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.network.chat.Component;

public class SettingsPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.general", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.option.sounds", new Object[0]), () -> config.menuSounds, (Consumer<Boolean>)((Consumer)value -> {
            config.menuSounds = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.option.hideDisabled", new Object[0]), () -> config.sidebarHideDisabled, (Consumer<Boolean>)((Consumer)value -> {
            config.sidebarHideDisabled = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.option.hideDisabledHint", new Object[0]), null));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.option.presence", new Object[0]), () -> config.presenceShowOthers, (Consumer<Boolean>)((Consumer)value -> {
            config.presenceShowOthers = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.option.language", new Object[0]), (Supplier<Component>)((Supplier)() -> Component.literal((String)(Lang.isGerman() ? "Deutsch" : "English"))), () -> {
            Lang.set(Lang.isGerman() ? "en" : "de");
            ConfigManager.save();
        }));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.option.languageHint", new Object[0]), null));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.option.replayTutorial", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.tutorial.start", new Object[0])), () -> {
            config.tutorialSeen = false;
            ConfigManager.save();
            FwmTutorial.start();
        }));
    }
}
