/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.lang.System
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
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.auction.StreamerStore;
import net.friendswithmoney.fwm.auction.Times;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.StreamerScreen;
import net.friendswithmoney.fwm.ui.panel.AuctionItemPanel;
import net.friendswithmoney.fwm.ui.panel.AuctionPanel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ButtonRow;
import net.friendswithmoney.fwm.ui.widget.ChipsRow;
import net.friendswithmoney.fwm.ui.widget.LiveRow;
import net.friendswithmoney.fwm.ui.widget.ReelRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.WordRow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class GiveawayPanel
extends RowsPanel {
    private static final int MIN_SECONDS = 15;
    private static final int MAX_SECONDS = 3600;
    private final PanelHost host;
    private String builtFor = "";
    private long cancelArmedAt;

    public GiveawayPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.giveaway", new Object[0]);
    }

    private static String signature() {
        return String.valueOf((Object)((Object)Giveaway.status())) + ":" + Giveaway.participants().size() + ":" + Giveaway.drawing();
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        if (!this.builtFor.equals((Object)GiveawayPanel.signature()) && !this.isCapturingKeys()) {
            this.setBounds(this.x, this.y, this.width, this.height);
        }
        super.render(ctx, font, mouseX, mouseY, delta);
    }

    @Override
    protected void buildRows() {
        this.builtFor = GiveawayPanel.signature();
        switch (Giveaway.status()) {
            case RUNNING: 
            case PAUSED: {
                this.running();
                break;
            }
            case ENDED: {
                this.ended();
                break;
            }
            default: {
                this.setup();
            }
        }
    }

    private void setup() {
        FwmConfig config = FwmConfig.INSTANCE;
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)"Giveaway"), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Starten", "Start"))), this::start));
        this.rows.add((Object)new ButtonRow((Component)Lang.of("fwm.auction.item", new Object[0]), (Supplier<Component>)((Supplier)GiveawayPanel::itemCaption), () -> this.host.openSub(new AuctionItemPanel(this.host, (Supplier<ItemStack>)((Supplier)Giveaway::item), (Consumer<ItemStack>)((Consumer)Giveaway::hold)))));
        this.rows.add((Object)new WordRow((Component)Component.literal((String)Lang.t("Teilnahme-Wort", "Join word")), (Supplier<String>)((Supplier)() -> config.giveawayWord), (Consumer<String>)((Consumer)value -> {
            config.giveawayWord = value;
            ConfigManager.save();
        })));
        this.rows.add((Object)new SliderRow((Component)Lang.of("fwm.auction.time", new Object[0]), 15, 3600, () -> config.giveawaySeconds, value -> {
            config.giveawaySeconds = value;
            ConfigManager.save();
        }, (IntFunction<Component>)((IntFunction)seconds -> Component.literal((String)Times.clock(seconds))), Times::seconds));
        this.rows.add((Object)new ChipsRow((Component)Component.literal((String)Lang.t("Schnellwahl", "Quick pick")), AuctionPanel.DURATIONS, () -> config.giveawaySeconds, value -> {
            config.giveawaySeconds = value;
            ConfigManager.save();
        }));
        this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Spieler schreiben dir per /msg das Wort.", "Players send you the word via /msg.")), null));
        this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Das Item bleibt bei dir \u2013 \u00fcbergib es nach der Ziehung.", "The item stays with you \u2013 hand it over after the draw.")), null));
    }

    private void start() {
        FwmConfig config = FwmConfig.INSTANCE;
        if (Giveaway.item().isEmpty()) {
            this.host.openSub(new AuctionItemPanel(this.host, (Supplier<ItemStack>)((Supplier)Giveaway::item), (Consumer<ItemStack>)((Consumer)Giveaway::hold)));
            return;
        }
        Giveaway.start(Giveaway.item(), config.giveawayWord, Integer.MAX_VALUE, config.giveawaySeconds);
        Minecraft.getInstance().gui.setScreen((Screen)new StreamerScreen(null, "giveaway"));
    }

    private void running() {
        boolean paused = Giveaway.status() == Giveaway.Status.PAUSED;
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Streamer-Modus", "Streamer mode")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("\u00d6ffnen", "Open"))), () -> Minecraft.getInstance().gui.setScreen((Screen)new StreamerScreen(null, "giveaway"))));
        this.rows.add((Object)new LiveRow((Component)Component.literal((String)"Status"), (Supplier<Component>)((Supplier)() -> Component.literal((String)(paused ? Lang.t("PAUSIERT", "PAUSED") : "LIVE"))), () -> paused ? FwmTheme.TEXT_DIM : FwmTheme.ACCENT));
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)(paused ? Lang.t("Teilnahme ist gestoppt", "Entries are stopped") : Lang.t("Teilnahme l\u00e4uft", "Entries are open"))), (Supplier<Component>)((Supplier)() -> Component.literal((String)(paused ? Lang.t("Fortsetzen", "Resume") : Lang.t("Pausieren", "Pause")))), () -> {
            if (paused) {
                Giveaway.resume();
            } else {
                Giveaway.pause();
            }
        }));
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Gewinner sofort ziehen", "Draw the winner now")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Jetzt ziehen", "Draw now"))), Giveaway::draw));
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Giveaway abbrechen", "Cancel giveaway")), (Supplier<Component>)((Supplier)() -> Component.literal((String)(System.currentTimeMillis() - this.cancelArmedAt < 3000L ? Lang.t("Sicher? Klicken", "Sure? Click") : Lang.t("Abbrechen", "Cancel")))), () -> {
            if (System.currentTimeMillis() - this.cancelArmedAt < 3000L) {
                Giveaway.cancel();
            } else {
                this.cancelArmedAt = System.currentTimeMillis();
            }
        }));
        this.rows.add((Object)new LiveRow((Component)Lang.of("fwm.auction.item", new Object[0]), (Supplier<Component>)((Supplier)() -> Component.literal((String)((String)(Giveaway.item().getCount() > 1 ? Giveaway.item().getCount() + "x " : "") + Giveaway.item().getHoverName().getString())))));
        this.rows.add((Object)new LiveRow((Component)Component.literal((String)Lang.t("Restzeit", "Time left")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Times.clock((int)((Giveaway.remainingMs() + 999L) / 1000L))))));
        this.rows.add((Object)new LiveRow((Component)Component.literal((String)Lang.t("Teilnehmer", "Participants")), (Supplier<Component>)((Supplier)() -> Component.literal((String)String.valueOf((int)Giveaway.participants().size())))));
        String join = Giveaway.joinCommand();
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)(Lang.t("Teilnahme: ", "Join: ") + join)), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Kopieren", "Copy"))), () -> StreamerStore.copy(join)));
        this.participantRows();
    }

    private void ended() {
        String winner = Giveaway.winner();
        if (winner != null) {
            this.rows.add((Object)new ReelRow());
            this.rows.add((Object)new ReelRow.Spacer());
        }
        if (Giveaway.drawing()) {
            this.participantRows();
            return;
        }
        this.rows.add((Object)new TextRow((Component)Component.literal((String)(winner == null ? Lang.t("Niemand hat teilgenommen.", "Nobody took part.") : Lang.t("Gewinner", "Winner"))), (Component)(winner == null ? null : Component.literal((String)winner))));
        if (winner != null) {
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Gewinner", "Winner")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Name kopieren", "Copy name"))), () -> StreamerStore.copy(winner)));
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Zum Gewinner", "To the winner")), (Supplier<Component>)((Supplier)() -> Component.literal((String)("/tpa " + winner))), () -> StreamerStore.command("tpa " + winner)));
            this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Gewinner zu dir", "Winner to you")), (Supplier<Component>)((Supplier)() -> Component.literal((String)("/tpahere " + winner))), () -> this.confirm.ask((Component)Component.literal((String)Lang.t("Gewinner " + winner + " per /tpahere zu dir holen?", "Pull winner " + winner + " over with /tpahere?")), "fwm.confirm.send", () -> StreamerStore.command("tpahere " + winner))));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("\u00dcbergib das Item manuell (Handel oder Drop).", "Hand the item over yourself (trade or drop).")), null));
        }
        this.rows.add((Object)new ButtonRow((Component)Component.literal((String)Lang.t("Neues Giveaway", "New giveaway")), (Supplier<Component>)((Supplier)() -> Component.literal((String)Lang.t("Schlie\u00dfen", "Close"))), Giveaway::reset));
        this.participantRows();
    }

    private void participantRows() {
        List<Giveaway.Participant> all = Giveaway.participants();
        this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Teilnehmerliste", "Participant list")), (Component)Component.literal((String)(all.isEmpty() ? Lang.t("noch niemand", "nobody yet") : String.valueOf((int)all.size())))));
        for (int i = 0; i < all.size(); ++i) {
            Giveaway.Participant p = (Giveaway.Participant)((Object)all.get(i));
            this.rows.add((Object)new LiveRow((Component)Component.literal((String)(i + 1 + ". " + p.name())), (Supplier<Component>)((Supplier)() -> Component.literal((String)GiveawayPanel.ago(p.joinedAt()))), () -> FwmTheme.TEXT_DIM));
        }
    }

    private static String ago(long time) {
        long seconds = Math.max((long)0L, (long)((System.currentTimeMillis() - time) / 1000L));
        if (seconds < 60L) {
            return Lang.t("vor " + seconds + " s", seconds + " s ago");
        }
        if (seconds < 3600L) {
            return Lang.t("vor " + seconds / 60L + " Min", seconds / 60L + " min ago");
        }
        return Lang.t("vor " + seconds / 3600L + " Std", seconds / 3600L + " h ago");
    }

    private static Component itemCaption() {
        ItemStack stack = Giveaway.item();
        if (stack.isEmpty()) {
            return Lang.of("fwm.auction.pick", new Object[0]);
        }
        return Component.literal((String)((String)(stack.getCount() > 1 ? stack.getCount() + "x " : "") + stack.getHoverName().getString()));
    }
}
