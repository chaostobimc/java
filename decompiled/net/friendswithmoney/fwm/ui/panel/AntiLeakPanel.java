/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.antileak.AntiLeak;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class AntiLeakPanel
extends RowsPanel {
    @Override
    public Component title() {
        return Lang.of("fwm.tab.antileak", new Object[0]);
    }

    @Override
    protected void buildRows() {
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.antileak.intro", new Object[0]), null));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.extras.active", new Object[0]), () -> Features.isOn("fwm.feature.antileak"), (Consumer<Boolean>)((Consumer)value -> Features.set("fwm.feature.antileak", value))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.coords", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakCoords, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakCoords = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.antileak.hide", new Object[0])));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.bedrock", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakBedrock, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakBedrock = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.ores", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakOres, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakOres = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.rotation", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakRotation, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakRotation = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.stones", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakStones, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakStones = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.antileak.biome", new Object[0]), () -> FwmConfig.INSTANCE.antiLeakBiome, (Consumer<Boolean>)((Consumer)value -> {
            FwmConfig.INSTANCE.antiLeakBiome = value;
        })));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.antileak.pack", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of(AntiLeak.dirty() ? "fwm.antileak.apply" : "fwm.antileak.current", new Object[0])), () -> AntiLeak.sync(Minecraft.getInstance())));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.antileak.hint", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.antileak.hint2", new Object[0]), null));
    }

    @Override
    public void onHidden() {
        super.onHidden();
        AntiLeak.sync(Minecraft.getInstance());
    }
}
