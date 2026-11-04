/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
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
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.network.chat.Component;

public class ExtrasPanel
extends RowsPanel {
    private static final int MIN_TPA_SECONDS = 15;
    private static final int MAX_TPA_SECONDS = 300;

    @Override
    public Component title() {
        return Lang.of("fwm.tab.extras", new Object[0]);
    }

    @Override
    protected void buildRows() {
        this.toggle("fwm.feature.unitprice");
        this.toggle("fwm.feature.ahprice");
        this.toggle("fwm.feature.realname");
        this.toggle("fwm.feature.tpawarn");
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.tpa.timeout", new Object[0]), 15, 300, () -> FwmConfig.INSTANCE.tpaTimeoutSeconds, value -> {
            FwmConfig.INSTANCE.tpaTimeoutSeconds = value;
        }, (IntFunction<Component>)((IntFunction)seconds -> Lang.of("fwm.value.seconds", seconds))));
    }

    private void toggle(String feature) {
        this.rows.add((Object)new ToggleRow((Component)Lang.of(feature, new Object[0]), () -> Features.isOn(feature), (Consumer<Boolean>)((Consumer)value -> Features.set(feature, value))));
    }
}
