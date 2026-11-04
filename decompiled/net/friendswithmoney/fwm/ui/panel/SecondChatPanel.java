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
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.config.ChatFilter;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.HudEditScreen;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.ChatFilterPanel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class SecondChatPanel
extends RowsPanel {
    private final PanelHost host;

    public SecondChatPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.secondchat", new Object[0]);
    }

    @Override
    protected void buildRows() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.active", new Object[0]), () -> Features.isOn("fwm.tab.secondchat"), (Consumer<Boolean>)((Consumer)value -> Features.set("fwm.tab.secondchat", value))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.msg", new Object[0]), () -> config.secondChatMsg, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatMsg = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.tpa", new Object[0]), () -> config.secondChatTpa, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatTpa = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.pay", new Object[0]), () -> config.secondChatPay, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatPay = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Auktion/Order-Meldungen", "Auction/order messages")), () -> config.secondChatMarket, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatMarket = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.hugoOther", new Object[0]), () -> config.secondChatHugoOther, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatHugoOther = value;
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.clan", new Object[0]), () -> config.secondChatClan, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatClan = value;
        })));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.secondchat.filters", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.secondchat.filterCount", SecondChatPanel.activeFilters())), () -> this.host.openSub(new ChatFilterPanel(this.host))));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.secondchat.place", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.option.hudEditOpen", new Object[0])), () -> {
            Minecraft client = Minecraft.getInstance();
            client.gui.setScreen((Screen)new HudEditScreen(client.gui.screen()));
        }));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.secondchat.align", new Object[0]), 0, 2, () -> config.secondChatAlign, value -> {
            config.secondChatAlign = value;
        }, (IntFunction<Component>)((IntFunction)align -> Lang.of(switch (align) {
            case 0 -> "fwm.secondchat.alignLeft";
            case 2 -> "fwm.secondchat.alignRight";
            default -> "fwm.secondchat.alignCenter";
        }, new Object[0]))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.secondchat.lines", new Object[0]), 3, 16, () -> config.secondChatLines, value -> {
            config.secondChatLines = value;
        }, (IntFunction<Component>)((IntFunction)lines -> Lang.of("fwm.secondchat.linesValue", lines))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.background", new Object[0]), () -> config.secondChatBackground, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatBackground = value;
        })));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.secondchat.backgroundOpacity", new Object[0]), 0, 100, () -> config.secondChatBackgroundOpacity, value -> {
            config.secondChatBackgroundOpacity = value;
        }, (IntFunction<Component>)((IntFunction)value -> Component.literal((String)(value + " %")))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.secondchat.remove", new Object[0]), () -> config.secondChatRemove, (Consumer<Boolean>)((Consumer)value -> {
            config.secondChatRemove = value;
        })));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.secondchat.removeHint", new Object[0]), null));
    }

    private static int activeFilters() {
        int count = 0;
        for (ChatFilter filter : FwmConfig.INSTANCE.secondChatFilters) {
            if (filter == null || !filter.on || filter.text == null || filter.text.isBlank()) continue;
            ++count;
        }
        return count;
    }
}
