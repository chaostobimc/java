/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.file.Files
 *  java.nio.file.Path
 *  net.fabricmc.api.ClientModInitializer
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.loader.api.FabricLoader
 */
package net.friendswithmoney.fwm;

import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.antileak.AntiLeak;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.chat.ChatRouter;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.chat.SecondChatClicks;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.controller.SellController;
import net.friendswithmoney.fwm.game.AlertSidePanel;
import net.friendswithmoney.fwm.game.AutoTpa;
import net.friendswithmoney.fwm.game.EcValue;
import net.friendswithmoney.fwm.game.FightKits;
import net.friendswithmoney.fwm.game.InvValue;
import net.friendswithmoney.fwm.game.SpawnerMacro;
import net.friendswithmoney.fwm.game.SpawnerSidePanel;
import net.friendswithmoney.fwm.game.SpawnerSpots;
import net.friendswithmoney.fwm.game.ToolGuard;
import net.friendswithmoney.fwm.game.TpaWatch;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.money.History;
import net.friendswithmoney.fwm.money.MoneyEvents;
import net.friendswithmoney.fwm.money.MoneyStore;
import net.friendswithmoney.fwm.net.FakeList;
import net.friendswithmoney.fwm.net.Giveaways;
import net.friendswithmoney.fwm.net.Market;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.net.ModAccount;
import net.friendswithmoney.fwm.net.ModBan;
import net.friendswithmoney.fwm.net.ModId;
import net.friendswithmoney.fwm.net.ModNews;
import net.friendswithmoney.fwm.net.Presence;
import net.friendswithmoney.fwm.net.PricePings;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.net.Tickets;
import net.friendswithmoney.fwm.net.TrustedClans;
import net.friendswithmoney.fwm.net.Waypoints;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.friendswithmoney.fwm.shop.MarketTooltip;
import net.friendswithmoney.fwm.shop.RealName;
import net.friendswithmoney.fwm.shop.UnitPrice;
import net.friendswithmoney.fwm.stats.SellStats;
import net.friendswithmoney.fwm.textures.TextureMixer;
import net.friendswithmoney.fwm.ui.Design;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.ui.FwmPalette;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.MemberTags;

public class FwmClient
implements ClientModInitializer {
    public static final String MOD_VERSION = (String)FabricLoader.getInstance().getModContainer("fwm").map(mod -> mod.getMetadata().getVersion().getFriendlyString()).orElse((Object)"?");
    public static final SellController SELL_CONTROLLER = new SellController();

    public void onInitializeClient() {
        ConfigManager.load();
        FwmPalette.applyFromConfig();
        Lang.load();
        Spawners.load();
        Market.load();
        try {
            Files.deleteIfExists((Path)FabricLoader.getInstance().getConfigDir().resolve("fwm-reconnect-log.json"));
        }
        catch (Exception exception) {
            // empty catch block
        }
        TextureMixer.migrate();
        if (!FwmConfig.INSTANCE.envelopeOff66) {
            FwmConfig.INSTANCE.msgShowEnvelope = false;
            FwmConfig.INSTANCE.envelopeOff66 = true;
            ConfigManager.save();
        }
        TrustedClans.load();
        ModAccount.load();
        ModBan.register();
        Members.load();
        Messages.load();
        Keybinds.register();
        FwmHud.register();
        MemberTags.register();
        ToolGuard.register();
        TpaWatch.register();
        ChatRouter.register();
        SecondChatClicks.register();
        UnitPrice.register();
        FightKits.register();
        Waypoints.register();
        RealName.register();
        MarketTooltip.register();
        InvValue.register();
        EcValue.register();
        SpawnerSpots.register();
        SpawnerSidePanel.register();
        AlertSidePanel.register();
        SevenTv.register();
        ClientLifecycleEvents.CLIENT_STOPPING.register(c -> {
            MoneyStore.left();
            MoneyStore.save();
            History.save();
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            FwmConfig config = FwmConfig.INSTANCE;
            if (!Features.isOn("fwm.tab.sellmacro") && config.enabled) {
                config.enabled = false;
            }
            if (SELL_CONTROLLER.follow(config.enabled, client)) {
                FwmHud.showDeactivated();
                SellStats.cancelTest();
            }
            if (config.enabled && SELL_CONTROLLER.isRunning()) {
                SellStats.countActiveTick();
                if (SellStats.tickTest()) {
                    config.enabled = false;
                    SELL_CONTROLLER.disable(client);
                    ConfigManager.save();
                }
            }
            if (SellStats.tickGrace()) {
                client.gui.setScreen(Design.open(null, "fwm.tab.sellmacro", 1));
            }
            SELL_CONTROLLER.onClientTick(client);
            Keybinds.handle(client);
            Spawners.tick(client);
            Members.tick(client);
            ModNews.tick(client);
            FakeList.tick(client);
            Waypoints.tick(client);
            Presence.tick(client);
            ModId.tick(client);
            Giveaways.tick(client);
            TrustedClans.tick(client);
            Tickets.tick(client);
            FwmTutorial.tick(client);
            Messages.tick(client);
            Auction.tick(client);
            Giveaway.tick(client);
            TpaWatch.tick(client);
            PricePings.tick(client);
            AntiLeak.tick(client);
            AutoTpa.tick(client);
            SpawnerMacro.tick(client);
            MoneyEvents.tick(client);
            InvValue.tick(client);
        });
    }
}
