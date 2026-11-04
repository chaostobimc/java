/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.HashMap
 *  java.util.Map
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.client.multiplayer.MultiPlayerGameMode
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerInput
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.controller;

import java.util.HashMap;
import java.util.Map;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.stats.SellStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class SellController {
    private static final int OPEN_TIMEOUT_TICKS = 40;
    private static final int SETTLE_TICKS = 5;
    private static final int STUCK_TICKS = 60;
    private static final int CLICK_GRACE_TICKS = 8;
    private static final int MAX_CLICK_WAIT_TICKS = 30;
    private final FwmConfig config = FwmConfig.INSTANCE;
    private Step step = Step.OFF;
    private int timer;
    private boolean menuSeen;
    private int stuckTicks;
    private int ticks;
    private final Map<Integer, Integer> clickedAt = new HashMap();

    public boolean isEnabled() {
        return this.step != Step.OFF;
    }

    public boolean isRunning() {
        return this.step != Step.OFF;
    }

    public void enable() {
        if (this.step == Step.OFF) {
            this.go(Step.SEND, 0);
        }
    }

    public void disable(Minecraft client) {
        this.step = Step.OFF;
        this.menuSeen = false;
        if (client != null && client.player != null && SellController.sellScreen(client) != null) {
            client.player.closeContainer();
        }
    }

    public boolean follow(boolean wanted, Minecraft client) {
        if (wanted == this.isEnabled()) {
            return false;
        }
        if (wanted) {
            this.enable();
            return false;
        }
        this.disable(client);
        return true;
    }

    public void onClientTick(Minecraft client) {
        if (this.step == Step.OFF || !this.config.enabled || client.player == null) {
            return;
        }
        ++this.ticks;
        AbstractContainerScreen<?> screen = SellController.sellScreen(client);
        if (this.menuSeen && screen == null) {
            this.menuSeen = false;
            this.go(Step.PAUSE, this.config.reopenDelayTicks);
            return;
        }
        switch (this.step.ordinal()) {
            case 1: {
                this.send(client);
                break;
            }
            case 2: {
                this.await(screen);
                break;
            }
            case 3: {
                if (!this.due()) break;
                this.sell(client, screen);
                break;
            }
            case 4: {
                if (!this.due()) break;
                this.go(Step.SEND, 0);
                break;
            }
        }
    }

    private void send(Minecraft client) {
        String command = this.config.sellCommand.trim();
        if (command.startsWith("/")) {
            command = command.substring(1);
        }
        client.player.connection.sendCommand(command);
        this.go(Step.AWAIT, 40);
    }

    private void await(AbstractContainerScreen<?> screen) {
        if (screen != null) {
            this.menuSeen = true;
            this.stuckTicks = 0;
            this.clickedAt.clear();
            this.go(Step.SELL, 5);
            return;
        }
        if (this.due()) {
            this.go(Step.SEND, 0);
        }
    }

    private void sell(Minecraft client, AbstractContainerScreen<?> screen) {
        Slot slot;
        MultiPlayerGameMode clicks = client.gameMode;
        if (screen == null || clicks == null) {
            return;
        }
        AbstractContainerMenu menu = screen.getMenu();
        if (this.isFull(menu)) {
            this.pressSellButton(client, clicks, menu);
            this.timer = this.config.sellPressDelayTicks;
            return;
        }
        int moved = 0;
        int wait = SellController.clickWait(client);
        for (int left = Math.max((int)1, (int)this.config.transferBurst); left > 0 && !this.isFull(menu) && (slot = this.firstSellable(menu, wait)) != null; --left) {
            this.clickedAt.put((Object)slot.index, (Object)this.ticks);
            SellStats.countItems(slot.getItem().getCount());
            clicks.handleContainerInput(menu.containerId, slot.index, 0, ContainerInput.QUICK_MOVE, (Player)client.player);
            ++moved;
        }
        if (moved > 0) {
            this.stuckTicks = 0;
        } else if (this.firstSellable(menu, wait) != null && ++this.stuckTicks >= 60) {
            this.reopen(client);
            return;
        }
        this.timer = this.config.itemMoveDelayTicks;
    }

    private void pressSellButton(Minecraft client, MultiPlayerGameMode clicks, AbstractContainerMenu menu) {
        int button = this.config.sellButtonSlot;
        if (button < 0 || button >= menu.slots.size()) {
            return;
        }
        clicks.handleContainerInput(menu.containerId, button, 0, ContainerInput.PICKUP, (Player)client.player);
        SellStats.countSellPress();
    }

    private void reopen(Minecraft client) {
        this.stuckTicks = 0;
        client.player.closeContainer();
    }

    private static AbstractContainerScreen<?> sellScreen(Minecraft client) {
        Screen screen = client.gui.screen();
        if (!(screen instanceof AbstractContainerScreen)) {
            return null;
        }
        AbstractContainerScreen screen2 = (AbstractContainerScreen)screen;
        if (screen2 instanceof InventoryScreen || screen2 instanceof CreativeModeInventoryScreen) {
            return null;
        }
        return screen2;
    }

    private boolean isFull(AbstractContainerMenu menu) {
        for (Slot slot : menu.slots) {
            if (slot.container instanceof Inventory || slot.index == this.config.sellButtonSlot || slot.hasItem()) continue;
            return false;
        }
        return true;
    }

    private static int clickWait(Minecraft client) {
        PlayerInfo entry;
        int ping = 0;
        if (client.getConnection() != null && (entry = client.getConnection().getPlayerInfo(client.player.getUUID())) != null) {
            ping = Math.max((int)0, (int)entry.getLatency());
        }
        return Math.min((int)30, (int)(ping / 50 + 8));
    }

    private Slot firstSellable(AbstractContainerMenu menu, int wait) {
        for (Slot slot : menu.slots) {
            Integer last;
            if (!(slot.container instanceof Inventory) || !slot.hasItem() || (last = (Integer)this.clickedAt.get((Object)slot.index)) != null && this.ticks - last < wait || !this.isSellable(slot.getItem())) continue;
            return slot;
        }
        return null;
    }

    private boolean isSellable(ItemStack stack) {
        return this.config.isWhitelisted(BuiltInRegistries.ITEM.getKey((Object)stack.getItem()).toString());
    }

    private void go(Step next, int ticks) {
        this.step = next;
        this.timer = ticks;
    }

    private boolean due() {
        if (this.timer > 0) {
            --this.timer;
            return false;
        }
        return true;
    }

    private static enum Step {
        OFF,
        SEND,
        AWAIT,
        SELL,
        PAUSE;

    }
}
