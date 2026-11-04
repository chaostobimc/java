/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.function.Consumer
 *  java.util.function.IntFunction
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.network.chat.Component;

public class ToolGuardPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.toolguard", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.toolguard.active", new Object[0]), () -> Features.isOn("fwm.tab.toolguard"), (Consumer<Boolean>)((Consumer)value -> Features.set("fwm.tab.toolguard", value))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.toolguard.threshold", new Object[0]), 1, 50, () -> config.toolGuardPercent, value -> {
            config.toolGuardPercent = value;
        }, (IntFunction<Component>)((IntFunction)percent -> Lang.of("fwm.value.percent", percent))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.toolguard.warning", new Object[0]), () -> config.toolGuardWarning, (Consumer<Boolean>)((Consumer)value -> {
            config.toolGuardWarning = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.toolguard.block", new Object[0]), () -> config.toolGuardBlock, (Consumer<Boolean>)((Consumer)value -> {
            config.toolGuardBlock = value;
        })));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.toolguard.blockHint", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.toolguard.blockHint2", new Object[0]), null));
    }
}
