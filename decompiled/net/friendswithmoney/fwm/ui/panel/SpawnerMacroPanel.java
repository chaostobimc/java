/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  java.util.function.Consumer
 *  java.util.function.IntFunction
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.SpawnerMacro;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerGuidePanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.ChipsRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;

public class SpawnerMacroPanel
extends RowsPanel {
    private final PanelHost host;

    public SpawnerMacroPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.spawnermacro", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.spawnermacro.active", new Object[0]), () -> Features.isOn("fwm.tab.spawnermacro"), (Consumer<Boolean>)((Consumer)value -> Features.set("fwm.tab.spawnermacro", value))));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.spawnermacro.key", Keybinds.spawnerKeyName()), (Supplier<Component>)((Supplier)() -> Component.literal((String)(SpawnerMacro.running() ? Lang.t("Stoppen", "Stop") : Lang.t("Starten", "Start")))), () -> {
            Minecraft client = Minecraft.getInstance();
            if (SpawnerMacro.running()) {
                SpawnerMacro.toggle(client);
            } else {
                client.gui.setScreen(null);
                SpawnerMacro.start(client);
            }
        }));
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("So baust du es am besten", "How to build it best")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Bild ansehen", "View picture"))), () -> this.host.openSub(new SpawnerGuidePanel())));
        this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Seitenfeld am Spawner-Men\u00fc", "Side box at the spawner menu")), () -> config.spawnerSidePanel, (Consumer<Boolean>)((Consumer)value -> {
            config.spawnerSidePanel = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Infos \u00fcber ausgew\u00e4hlten Spawnern", "Info over picked spawners")), () -> config.spawnerLabels, (Consumer<Boolean>)((Consumer)value -> {
            config.spawnerLabels = value;
        })));
        this.rows.add((Object)new ChipsRow((Component)Component.literal((String)Lang.t("Standard-Modus", "Default mode")), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip(Lang.t("Knochen", "Bones"), 0)), (Object)((Object)new ChipsRow.Chip(Lang.t("Droppen", "Drop"), 1)), (Object)((Object)new ChipsRow.Chip(Lang.t("Verkauf", "Sell"), 2))), () -> "drop".equals((Object)config.spawnerMode) ? 1 : ("sell".equals((Object)config.spawnerMode) ? 2 : 0), v -> {
            config.spawnerMode = v == 1 ? "drop" : (v == 2 ? "sell" : "bones");
        }));
        this.rows.add((Object)new SliderRow((Component)Component.literal((String)Lang.t("Verkaufen ab Auslastung", "Sell from fill")), 0, 100, () -> config.spawnerSellFill, value -> {
            config.spawnerSellFill = value > 0 && value < 10 ? 10 : value;
        }, (IntFunction<Component>)((IntFunction)v -> Component.literal((String)(v <= 0 ? Lang.t("nie", "never") : v + " %")))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.spawnermacro.autoDrop", new Object[0]), () -> config.spawnerAutoDrop, (Consumer<Boolean>)((Consumer)value -> {
            config.spawnerAutoDrop = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.spawnermacro.autoStop", new Object[0]), () -> config.spawnerAutoStop, (Consumer<Boolean>)((Consumer)value -> {
            config.spawnerAutoStop = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.spawnermacro.autoSell", new Object[0]), () -> config.spawnerAutoSell, (Consumer<Boolean>)((Consumer)value -> {
            config.spawnerAutoSell = value;
        })));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.spawnermacro.delay", new Object[0]), 2, 20, () -> config.spawnerClickDelayTicks, value -> {
            config.spawnerClickDelayTicks = value;
        }, (IntFunction<Component>)((IntFunction)ticks -> Component.literal((String)(ticks * 50 + " ms")))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.spawnermacro.repeat", new Object[0]), 10, 600, () -> config.spawnerRepeatSeconds, value -> {
            config.spawnerRepeatSeconds = value;
        }, (IntFunction<Component>)((IntFunction)seconds -> Component.literal((String)(seconds + " s")))));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.spawnermacro.hint", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.spawnermacro.hint2", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.spawnermacro.hint3", new Object[0]), null));
    }
}
