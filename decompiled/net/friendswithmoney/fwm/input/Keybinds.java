/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.InputConstants
 *  com.mojang.blaze3d.platform.InputConstants$Key
 *  com.mojang.blaze3d.platform.InputConstants$Type
 *  java.lang.Object
 *  java.lang.String
 *  net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents
 *  net.minecraft.client.KeyMapping
 *  net.minecraft.client.KeyMapping$Category
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.input;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.SpawnerMacro;
import net.friendswithmoney.fwm.ui.Design;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.ui.StreamerScreen;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.resources.Identifier;

public final class Keybinds {
    private static final KeyMapping.Category CATEGORY = KeyMapping.Category.register((Identifier)Identifier.fromNamespaceAndPath((String)"fwm", (String)"main"));
    public static KeyMapping TOGGLE;
    public static KeyMapping OPEN_MENU;
    public static KeyMapping OPEN_MESSAGES;
    public static KeyMapping OPEN_AUCTION;
    public static KeyMapping OPEN_GIVEAWAY;
    public static KeyMapping SPAWNER_MACRO;
    private static final int WATCH_INTERVAL = 40;
    private static int watch;
    private static boolean restored;

    private Keybinds() {
    }

    public static void register() {
        if (TOGGLE != null) {
            return;
        }
        TOGGLE = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.toggle", InputConstants.Type.KEYBOARD, 28, CATEGORY));
        OPEN_MENU = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.menu", InputConstants.Type.KEYBOARD, 13, CATEGORY));
        OPEN_MESSAGES = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.messages", InputConstants.Type.KEYBOARD, 16, CATEGORY));
        OPEN_AUCTION = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.auction", InputConstants.Type.KEYBOARD, -1, CATEGORY));
        OPEN_GIVEAWAY = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.giveaway", InputConstants.Type.KEYBOARD, -1, CATEGORY));
        SPAWNER_MACRO = KeyMappingHelper.registerKeyMapping((KeyMapping)new KeyMapping("key.fwm.spawnermacro", InputConstants.Type.KEYBOARD, -1, CATEGORY));
        Keybinds.registerContainerScreenHook();
    }

    private static void registerContainerScreenHook() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof AbstractContainerScreen)) {
                return;
            }
            ScreenKeyboardEvents.afterKeyPress((Screen)screen).register((s, input) -> {
                boolean stopKey;
                boolean bl = stopKey = SPAWNER_MACRO != null && !SPAWNER_MACRO.isUnbound() && SPAWNER_MACRO.matches(input) || OPEN_MENU != null && OPEN_MENU.matches(input);
                if (stopKey && SpawnerMacro.running()) {
                    SpawnerMacro.toggle(client);
                    return;
                }
                if (TOGGLE == null) {
                    return;
                }
                if (!TOGGLE.matches(input)) {
                    return;
                }
                Keybinds.toggle();
            });
        });
    }

    private static void restore() {
        FwmConfig config = FwmConfig.INSTANCE;
        boolean changed = Keybinds.apply(OPEN_MENU, config.menuKeyBinding) | Keybinds.apply(TOGGLE, config.macroKeyBinding) | Keybinds.apply(OPEN_MESSAGES, config.messagesKeyBinding) | Keybinds.apply(OPEN_AUCTION, config.auctionKeyBinding) | Keybinds.apply(OPEN_GIVEAWAY, config.giveawayKeyBinding);
        if (changed) {
            KeyMapping.resetMapping();
            Minecraft.getInstance().options.save();
        }
        Keybinds.remember();
    }

    private static boolean apply(KeyMapping binding, String wanted) {
        if (binding == null || wanted == null || wanted.isBlank()) {
            return false;
        }
        if (wanted.equals((Object)binding.saveString())) {
            return false;
        }
        InputConstants.Key key = InputConstants.getKey((String)wanted);
        if (key == null) {
            return false;
        }
        binding.setKey(key);
        return true;
    }

    public static void remember() {
        String giveaway;
        FwmConfig config = FwmConfig.INSTANCE;
        String menu = OPEN_MENU == null ? "" : OPEN_MENU.saveString();
        String macro = TOGGLE == null ? "" : TOGGLE.saveString();
        String messages = OPEN_MESSAGES == null ? "" : OPEN_MESSAGES.saveString();
        String auction = OPEN_AUCTION == null ? "" : OPEN_AUCTION.saveString();
        String string = giveaway = OPEN_GIVEAWAY == null ? "" : OPEN_GIVEAWAY.saveString();
        if (menu.equals((Object)config.menuKeyBinding) && macro.equals((Object)config.macroKeyBinding) && messages.equals((Object)config.messagesKeyBinding) && auction.equals((Object)config.auctionKeyBinding) && giveaway.equals((Object)config.giveawayKeyBinding)) {
            return;
        }
        config.menuKeyBinding = menu;
        config.macroKeyBinding = macro;
        config.messagesKeyBinding = messages;
        config.auctionKeyBinding = auction;
        config.giveawayKeyBinding = giveaway;
        ConfigManager.save();
    }

    public static boolean isTaken(KeyMapping self, InputConstants.Key key) {
        if (key == null || key.equals((Object)InputConstants.UNKNOWN)) {
            return false;
        }
        for (KeyMapping other : new KeyMapping[]{TOGGLE, OPEN_MENU, OPEN_MESSAGES, OPEN_AUCTION, OPEN_GIVEAWAY, SPAWNER_MACRO}) {
            if (other == null || other == self || !key.equals((Object)InputConstants.getKey((String)other.saveString()))) continue;
            return true;
        }
        return false;
    }

    private static void toggle() {
        if (!Features.isOn("fwm.tab.sellmacro")) {
            if (!FwmConfig.INSTANCE.enabled) {
                FwmHud.showBlocked();
                return;
            }
            FwmConfig.INSTANCE.enabled = false;
            ConfigManager.save();
            return;
        }
        FwmConfig.INSTANCE.enabled = !FwmConfig.INSTANCE.enabled;
        ConfigManager.save();
    }

    public static String toggleKeyName() {
        return TOGGLE == null ? "?" : TOGGLE.getTranslatedKeyMessage().getString();
    }

    public static String menuKeyName() {
        return OPEN_MENU == null ? "?" : OPEN_MENU.getTranslatedKeyMessage().getString();
    }

    public static String spawnerKeyName() {
        if (SPAWNER_MACRO == null || SPAWNER_MACRO.isUnbound()) {
            return "-";
        }
        return SPAWNER_MACRO.getTranslatedKeyMessage().getString();
    }

    public static String messagesKeyName() {
        return OPEN_MESSAGES == null ? "?" : OPEN_MESSAGES.getTranslatedKeyMessage().getString();
    }

    public static void handle(Minecraft client) {
        Screen live;
        if (TOGGLE == null) {
            return;
        }
        if (!restored) {
            restored = true;
            Keybinds.restore();
        }
        if (++watch >= 40) {
            watch = 0;
            Keybinds.remember();
        }
        while (TOGGLE.consumeClick()) {
            Keybinds.toggle();
        }
        while (OPEN_MENU.consumeClick()) {
            if (SpawnerMacro.running()) {
                SpawnerMacro.toggle(client);
                continue;
            }
            client.gui.setScreen(Design.open(client.gui.screen(), null, -1));
        }
        while (OPEN_MESSAGES.consumeClick()) {
            if (!Features.isOn("fwm.tab.messages")) continue;
            client.gui.setScreen(Design.open(client.gui.screen(), "fwm.tab.messages", -1));
        }
        while (SPAWNER_MACRO.consumeClick()) {
            SpawnerMacro.toggle(client);
        }
        while (OPEN_AUCTION.consumeClick()) {
            live = StreamerScreen.ifLive("fwm.tab.auction");
            client.gui.setScreen(live != null ? live : Design.open(client.gui.screen(), "fwm.tab.auction", 0));
        }
        while (OPEN_GIVEAWAY.consumeClick()) {
            live = StreamerScreen.ifLive("fwm.tab.giveaway");
            client.gui.setScreen(live != null ? live : Design.open(client.gui.screen(), "fwm.tab.auction", 1));
        }
    }
}
