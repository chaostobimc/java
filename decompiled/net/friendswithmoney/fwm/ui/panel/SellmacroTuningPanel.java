/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.Locale
 *  java.util.function.IntFunction
 *  java.util.function.Supplier
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.Locale;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.minecraft.network.chat.Component;

public class SellmacroTuningPanel
extends RowsPanel {
    private static final int OPTIMAL_ITEM_DELAY = 1;
    private static final int OPTIMAL_BURST = 10;
    private static final int OPTIMAL_PRESS_DELAY = 1;
    private static final int OPTIMAL_REOPEN_TICKS = 100;

    @Override
    public Component title() {
        return Lang.of("fwm.tab.tuning", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.option.itemDelay", new Object[0]), 1, 20, () -> config.itemMoveDelayTicks, value -> {
            config.itemMoveDelayTicks = value;
        }, (IntFunction<Component>)((IntFunction)ticks -> Lang.of(ticks == 1 ? "fwm.value.tick" : "fwm.value.ticks", ticks))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.option.burst", new Object[0]), 1, 13, () -> config.transferBurst, value -> {
            config.transferBurst = value;
        }, (IntFunction<Component>)((IntFunction)stacks -> Component.literal((String)String.valueOf((int)stacks)))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.option.pressDelay", new Object[0]), 0, 20, () -> config.sellPressDelayTicks, value -> {
            config.sellPressDelayTicks = value;
        }, (IntFunction<Component>)((IntFunction)ticks -> Lang.of(ticks == 1 ? "fwm.value.tick" : "fwm.value.ticks", ticks))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.option.reopenDelay", new Object[0]), 0, 200, () -> config.reopenDelayTicks, value -> {
            config.reopenDelayTicks = value;
        }, (IntFunction<Component>)((IntFunction)ticks -> Lang.of("fwm.value.seconds", String.format((Locale)Locale.ROOT, (String)"%.1f", (Object[])new Object[]{(double)ticks / 20.0})))));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.option.tuningDefault", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.option.tuningReset", new Object[0])), this::optimal));
    }

    private void optimal() {
        FwmConfig config = FwmConfig.INSTANCE;
        config.itemMoveDelayTicks = 1;
        config.transferBurst = 10;
        config.sellPressDelayTicks = 1;
        config.reopenDelayTicks = 100;
        config.clamp();
        ConfigManager.save();
        this.setBounds(this.x, this.y, this.width, this.height);
    }

    @Override
    public void onHidden() {
        ConfigManager.save();
    }
}
