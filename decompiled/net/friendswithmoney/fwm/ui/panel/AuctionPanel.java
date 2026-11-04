/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.util.List
 *  java.util.function.Consumer
 *  java.util.function.IntFunction
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.auction.StreamerStore;
import net.friendswithmoney.fwm.auction.Times;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.StreamerScreen;
import net.friendswithmoney.fwm.ui.panel.AuctionItemPanel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.ChipsRow;
import net.friendswithmoney.fwm.ui.widget.LiveRow;
import net.friendswithmoney.fwm.ui.widget.NumberRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class AuctionPanel
extends RowsPanel {
    static final List<ChipsRow.Chip> DURATIONS = List.of((Object)((Object)new ChipsRow.Chip("1 Min", 60)), (Object)((Object)new ChipsRow.Chip("5 Min", 300)), (Object)((Object)new ChipsRow.Chip("10 Min", 600)), (Object)((Object)new ChipsRow.Chip("30 Min", 1800)), (Object)((Object)new ChipsRow.Chip("1 h", 3600)));
    private final PanelHost host;
    private String builtFor = "";

    public AuctionPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.auction", new Object[0]);
    }

    private static String signature() {
        StringBuilder s = new StringBuilder();
        s.append(Auction.running()).append(Auction.hasResult()).append(Auction.itemMissing()).append(Auction.bidCount());
        return s.toString();
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        if (!this.builtFor.equals((Object)AuctionPanel.signature()) && !this.isCapturingKeys()) {
            this.setBounds(this.x, this.y, this.width, this.height);
        }
        super.render(ctx, font, mouseX, mouseY, delta);
    }

    @Override
    protected void buildRows() {
        this.builtFor = AuctionPanel.signature();
        if (Auction.running()) {
            this.running();
        } else if (Auction.hasResult()) {
            this.result();
        } else {
            this.setup();
        }
    }

    private void setup() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.auction.auction", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.auction.start", new Object[0])), this::start));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.auction.item", new Object[0]), (Supplier<Component>)((Supplier)AuctionPanel::itemCaption), () -> this.host.openSub(new AuctionItemPanel(this.host))));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.auction.time", new Object[0]), 15, 3600, () -> config.auctionSeconds, value -> {
            config.auctionSeconds = value;
            ConfigManager.save();
        }, (IntFunction<Component>)((IntFunction)seconds -> Component.literal((String)Times.clock(seconds))), Times::seconds));
        this.rows.add((Object)new ChipsRow((Component)Component.literal((String)Lang.t("Schnellwahl", "Quick pick")), DURATIONS, () -> config.auctionSeconds, value -> {
            config.auctionSeconds = value;
            ConfigManager.save();
        }));
        this.rows.add((Object)new NumberRow((Component)Lang.of("fwm.auction.startBid", new Object[0]), () -> config.auctionStartBid, value -> {
            config.auctionStartBid = value;
            ConfigManager.save();
        }, value -> Component.literal((String)Fmt.dollars(value * 100L))));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.auction.stack", new Object[0]), () -> config.auctionStackPayments, (Consumer<Boolean>)((Consumer)value -> {
            config.auctionStackPayments = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new ToggleRow((Component)Lang.of("fwm.auction.antiSnipe", new Object[0]), () -> config.auctionAntiSnipe, (Consumer<Boolean>)((Consumer)value -> {
            config.auctionAntiSnipe = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Auktions-Karte anzeigen", "Show auction card")), () -> config.auctionHudCard, (Consumer<Boolean>)((Consumer)value -> {
            config.auctionHudCard = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.auction.antiSnipeSeconds", new Object[0]), 1, 60, () -> config.auctionAntiSnipeSeconds, value -> {
            config.auctionAntiSnipeSeconds = value;
            ConfigManager.save();
        }, (IntFunction<Component>)((IntFunction)value -> Component.literal((String)("+" + value + " s")))));
        this.rows.add((Object)new TextRow((Component)Lang.of("fwm.auction.payHint", new Object[0]), null));
        this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Das Item bleibt bei dir \u2013 die Mod warnt, falls es fehlt.", "The item stays with you \u2013 the mod warns if it goes missing.")), null));
    }

    private void start() {
        FwmConfig config = FwmConfig.INSTANCE;
        if (Auction.item().isEmpty()) {
            this.host.openSub(new AuctionItemPanel(this.host));
            return;
        }
        Auction.start(Auction.item(), config.auctionSeconds, config.auctionStartBid * 100L, 0L);
        Minecraft.getInstance().gui.setScreen((Screen)new StreamerScreen(null, "auction"));
    }

    private void running() {
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Streamer-Modus", "Streamer mode")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("\u00d6ffnen", "Open"))), () -> Minecraft.getInstance().gui.setScreen((Screen)new StreamerScreen(null, "auction"))));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.auction.auction", new Object[0]), (Supplier<Component>)((Supplier)() -> Lang.of("fwm.auction.stop", new Object[0])), Auction::stop));
        if (Auction.itemMissing()) {
            this.rows.add((Object)new LiveRow((Component)Component.literal((String)Lang.t("Achtung", "Warning")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Item fehlt im Inventar!", "Item missing from inventory!"))), () -> FwmTheme.DISABLED_TEXT));
        }
        this.rows.add((Object)new LiveRow((Component)Lang.of("fwm.auction.item", new Object[0]), (Supplier<Component>)((Supplier)() -> Component.literal((String)((String)(Auction.item().getCount() > 1 ? Auction.item().getCount() + "x " : "") + Auction.item().getHoverName().getString())))));
        this.rows.add((Object)new LiveRow((Component)Component.literal((String)Lang.t("Restzeit", "Time left")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Times.clock((int)((Auction.remainingMs() + 999L) / 1000L))))));
        this.rows.add((Object)new LiveRow((Component)Lang.of("fwm.auction.highest", new Object[0]), (Supplier<Component>)((Supplier)() -> Component.literal((String)(Auction.leader() == null ? Lang.str("fwm.auction.nobody", new Object[0]) : Auction.leader() + "  " + Fmt.dollars(Auction.price()))))));
        this.rows.add((Object)new LiveRow((Component)Component.literal((String)Lang.t("N\u00e4chstes Gebot ab", "Next bid from")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Fmt.dollars(Auction.nextMinimum())))));
        String pay = "/pay " + FwmGfx.playerName() + " ";
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)(Lang.t("Mitbieten: ", "Bid: ") + pay + Lang.t("<Betrag>", "<amount>"))), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Kopieren", "Copy"))), () -> StreamerStore.copy(pay)));
    }

    private void result() {
        String winner = Auction.leader();
        this.rows.add((Object)new TextRow((Component)Component.literal((String)(winner == null ? Lang.t("Nicht verkauft \u2013 es gab keine Gebote.", "Not sold \u2013 there were no bids.") : Lang.t("Verkauft", "Sold"))), (Component)(winner == null ? null : Component.literal((String)(winner + "  " + Fmt.dollars(Auction.price()))))));
        if (winner != null) {
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Gewinner", "Winner")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Name kopieren", "Copy name"))), () -> StreamerStore.copy(winner)));
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Zum Gewinner", "To the winner")), (Supplier<Component>)((Supplier)() -> Component.literal((String)("/tpa " + winner))), () -> StreamerStore.command("tpa " + winner)));
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Gewinner zu dir", "Winner to you")), (Supplier<Component>)((Supplier)() -> Component.literal((String)("/tpahere " + winner))), () -> this.confirm.ask((Component)Component.literal((String)Lang.t("Gewinner " + winner + " per /tpahere zu dir holen?", "Pull winner " + winner + " over with /tpahere?")), "fwm.confirm.send", () -> StreamerStore.command("tpahere " + winner))));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("\u00dcbergib das Item manuell (Handel oder Drop).", "Hand the item over yourself (trade or drop).")), null));
        }
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Neue Auktion", "New auction")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Schlie\u00dfen", "Close"))), Auction::clearResult));
    }

    private static Component itemCaption() {
        ItemStack stack = Auction.item();
        if (stack.isEmpty()) {
            return Lang.of("fwm.auction.pick", new Object[0]);
        }
        return Component.literal((String)((String)(stack.getCount() > 1 ? stack.getCount() + "x " : "") + stack.getHoverName().getString()));
    }
}
