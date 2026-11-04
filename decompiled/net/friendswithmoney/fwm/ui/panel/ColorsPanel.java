/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.function.IntFunction
 *  java.util.function.Supplier
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmPalette;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherScreen;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.ColorRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.minecraft.network.chat.Component;

public class ColorsPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.gui", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        int maxMenu = LauncherScreen.maxScalePercent();
        config.menuScale = Math.min((int)config.menuScale, (int)maxMenu);
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.option.menuScale", new Object[0]), 70, maxMenu, () -> config.menuScale, value -> {
            config.menuScale = value;
        }, (IntFunction<Component>)((IntFunction)percent -> Component.literal((String)(percent + "%")))));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.colors.hint", new Object[0]), null));
        this.rows.add((Object)new ColorRow((Component)Lang.of("fwm.colors.oneHue", new Object[0]), () -> config.tintHue, value -> {
            config.tintHue = (float)value;
            FwmPalette.refresh();
        }, t -> FwmPalette.tintAt(t, Math.max((float)0.5f, (float)config.tintSat)), FwmPalette::defaultHue, () -> FwmPalette.tintAt(-1.0f, 0.0f), () -> {
            config.tintHue = -1.0f;
            config.tintSat = 0.0f;
            FwmPalette.refresh();
        }));
        this.rows.add((Object)new ColorRow((Component)Lang.of("fwm.colors.oneSat", new Object[0]), () -> config.tintSat, value -> {
            config.tintSat = (float)value;
            FwmPalette.refresh();
        }, t -> FwmPalette.tintAt(config.tintHue, t), null, null, null));
        this.rows.add((Object)new ColorRow((Component)Lang.of("fwm.colors.twoHue", new Object[0]), () -> config.menuHue, value -> {
            config.menuHue = (float)value;
            FwmPalette.refresh();
        }, t -> FwmPalette.accentAt(t, Math.max((float)0.5f, (float)config.menuSat)), FwmPalette::defaultHue, () -> FwmPalette.accentAt(-1.0f, 1.0f), () -> {
            config.menuHue = -1.0f;
            config.menuSat = 1.0f;
            FwmPalette.refresh();
        }));
        this.rows.add((Object)new ColorRow((Component)Lang.of("fwm.colors.twoSat", new Object[0]), () -> config.menuSat, value -> {
            config.menuSat = (float)value;
            FwmPalette.refresh();
        }, t -> FwmPalette.accentAt(config.menuHue, t), null, null, null));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.colors.reset", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.stats.reset", new Object[0])), () -> {
            config.tintHue = -1.0f;
            config.tintSat = 0.0f;
            config.menuHue = -1.0f;
            config.menuSat = 1.0f;
            FwmPalette.refresh();
            ConfigManager.save();
        }));
    }
}
