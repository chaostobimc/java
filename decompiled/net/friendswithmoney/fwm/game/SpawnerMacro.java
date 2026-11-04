/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.WeakHashMap
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Vec3i
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.util.Mth
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.inventory.AbstractContainerMenu
 *  net.minecraft.world.inventory.ContainerInput
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.Items
 *  net.minecraft.world.item.component.ItemLore
 *  net.minecraft.world.item.component.SwingAnimation
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 */
package net.friendswithmoney.fwm.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.auction.Money;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.SpawnerSpots;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ItemLore;
import net.minecraft.world.item.component.SwingAnimation;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public final class SpawnerMacro {
    private static final int OPEN_TIMEOUT = 60;
    private static final int AIM_TICKS = 6;
    private static final int EMPTY_BELOW = 100;
    private static final long MAX_DROP_PER_OPEN = 200000L;
    private static final Pattern FILL = Pattern.compile((String)"Auslastung:\\s*([0-9][0-9.,]*)\\s*%", (int)2);
    private static final Pattern ANY_COUNT = Pattern.compile((String)"^\\s*([^:%]+):\\s*([0-9][0-9.,]*)\\s*$");
    private static final Pattern CANCEL = Pattern.compile((String)"abbrech|cancel|zur(\u00fc|ue)ck|nein|schlie(\u00df|ss)en", (int)2);
    private static Step step = Step.OFF;
    private static int timer;
    private static final List<BlockPos> targets;
    private static int index;
    private static int tries;
    private static boolean dropping;
    private static boolean sold;
    private static long droppedThisOpen;
    private static long lastAmount;
    private static int reopenCount;
    private static boolean anyDrop;
    private static boolean roundHadBones;
    private static float aimFromYaw;
    private static float aimFromPitch;
    private static float aimToYaw;
    private static float aimToPitch;
    private static int aimTick;
    private static String status;
    private static int awaitTicks;
    private static int dropWaitTicks;
    private static int confirmTicks;
    private static int throwRounds;
    private static final Pattern INFO_LORE;
    private static final Map<AbstractContainerScreen<?>, Boolean> KNOWN;
    private static final Map<String, String> MOB_NAMES;
    private static final Set<Item> LOOT;

    private SpawnerMacro() {
    }

    public static boolean running() {
        return step != Step.OFF;
    }

    public static String status() {
        return SpawnerMacro.running() ? status : "";
    }

    public static BlockPos current() {
        return SpawnerMacro.running() && index < targets.size() ? (BlockPos)targets.get(index) : null;
    }

    public static void toggle(Minecraft client) {
        if (SpawnerMacro.running()) {
            SpawnerMacro.stop(client, Lang.t("Spawner-Makro gestoppt.", "Spawner macro stopped."));
        } else {
            SpawnerMacro.start(client);
        }
    }

    public static void start(Minecraft client) {
        LOOT.clear();
        LocalPlayer player = client.player;
        if (player == null || client.level == null) {
            return;
        }
        if (!Features.isOn("fwm.tab.spawnermacro")) {
            SpawnerMacro.say(client, Lang.t("Das Spawner-Makro ist ausgeschaltet (FWM-Men\u00fc).", "The spawner macro is switched off (FWM menu)."), ChatFormatting.RED);
            return;
        }
        targets.clear();
        double reach = player.blockInteractionRange() + 0.5;
        for (BlockPos pos : SpawnerSpots.here(client)) {
            if (!(player.getEyePosition().distanceTo(Vec3.atCenterOf((Vec3i)pos)) <= reach)) continue;
            targets.add((Object)pos);
        }
        BlockPos open = SpawnerMacro.openMenuSpawner(client);
        if (targets.isEmpty()) {
            if (open != null) {
                targets.add((Object)open);
            } else {
                BlockHitResult hit = SpawnerMacro.lookedAtSpawner(client, null);
                if (hit == null) {
                    SpawnerMacro.say(client, Lang.t("Schau direkt auf einen Spawner oder w\u00e4hle Spawner im Spawner-Men\u00fc aus.", "Look straight at a spawner or pick spawners in the spawner menu."), ChatFormatting.RED);
                    return;
                }
                targets.add((Object)hit.getBlockPos().immutable());
            }
        }
        index = Math.max((int)0, (int)(open == null ? 0 : targets.indexOf((Object)open)));
        roundHadBones = false;
        SpawnerMacro.beginVisit();
        SpawnerMacro.say(client, Lang.t("Spawner-Makro gestartet (" + targets.size() + " Spawner). Stoppen mit J.", "Spawner macro started (" + targets.size() + " spawners). Stop with J."), ChatFormatting.GREEN);
        if (open != null && open.equals(targets.get(index))) {
            SpawnerMacro.go(Step.READ, SpawnerMacro.delay());
        } else {
            SpawnerMacro.startAim(client);
        }
    }

    public static void stop(Minecraft client, String reason) {
        AbstractContainerScreen screen;
        Screen screen2;
        boolean wasInMenu = step == Step.READ || step == Step.DROP_WAIT || step == Step.CONFIRM || step == Step.AWAIT;
        step = Step.OFF;
        status = "";
        if (wasInMenu && client.player != null && (screen2 = client.gui.screen()) instanceof AbstractContainerScreen && (SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2) || SpawnerMacro.isConfirmMenu(screen))) {
            client.player.closeContainer();
        }
        if (reason != null && client.player != null) {
            SpawnerMacro.say(client, reason, ChatFormatting.YELLOW);
        }
    }

    public static void tick(Minecraft client) {
        if (step == Step.OFF) {
            return;
        }
        LocalPlayer player = client.player;
        if (player == null || client.level == null || client.gameMode == null) {
            step = Step.OFF;
            return;
        }
        if (!Features.isOn("fwm.tab.spawnermacro")) {
            SpawnerMacro.stop(client, null);
            return;
        }
        if (step == Step.AIM) {
            SpawnerMacro.aim(client, player);
            return;
        }
        if (timer > 0) {
            --timer;
            return;
        }
        switch (step.ordinal()) {
            case 2: {
                SpawnerMacro.open(client, player);
                break;
            }
            case 3: {
                SpawnerMacro.await(client);
                break;
            }
            case 4: {
                SpawnerMacro.read(client, player);
                break;
            }
            case 5: {
                SpawnerMacro.dropWait(client);
                break;
            }
            case 6: {
                SpawnerMacro.confirm(client);
                break;
            }
            case 7: {
                SpawnerMacro.closeMenu(client);
                SpawnerMacro.startAim(client);
                break;
            }
            case 8: {
                SpawnerMacro.done(client);
                break;
            }
            case 9: {
                SpawnerMacro.throwItems(client, player);
                break;
            }
            case 10: {
                index = 0;
                roundHadBones = false;
                SpawnerMacro.beginVisit();
                SpawnerMacro.startAim(client);
                break;
            }
        }
    }

    private static void beginVisit() {
        tries = 0;
        dropping = false;
        sold = false;
        droppedThisOpen = 0L;
        lastAmount = -1L;
        reopenCount = 0;
        anyDrop = false;
    }

    private static BlockPos target() {
        return (BlockPos)targets.get(index);
    }

    private static void startAim(Minecraft client) {
        LocalPlayer player = client.player;
        BlockPos pos = SpawnerMacro.target();
        status = Lang.t("Spawner ", "Spawner ") + (index + 1) + "/" + targets.size();
        if (!client.level.getBlockState(pos).is((Object)Blocks.SPAWNER) || player.getEyePosition().distanceTo(Vec3.atCenterOf((Vec3i)pos)) > player.blockInteractionRange() + 0.5) {
            SpawnerSpots.note(pos, Lang.t("nicht in Reichweite", "out of reach"));
            SpawnerMacro.go(Step.DONE, 1);
            return;
        }
        Vec3 d = Vec3.atCenterOf((Vec3i)pos).subtract(player.getEyePosition());
        aimFromYaw = player.getYRot();
        aimFromPitch = player.getXRot();
        aimToYaw = aimFromYaw + Mth.wrapDegrees((float)((float)(Mth.atan2((double)d.z, (double)d.x) * 180.0 / Math.PI) - 90.0f - aimFromYaw));
        aimToPitch = (float)(-(Mth.atan2((double)d.y, (double)Math.sqrt((double)(d.x * d.x + d.z * d.z))) * 180.0 / Math.PI));
        aimTick = 0;
        step = Step.AIM;
    }

    private static void aim(Minecraft client, LocalPlayer player) {
        if (client.gui.screen() != null) {
            return;
        }
        float t = Math.min((float)1.0f, (float)((float)(++aimTick) / 6.0f));
        float ease = t * t * (3.0f - 2.0f * t);
        player.setYRot(aimFromYaw + (aimToYaw - aimFromYaw) * ease);
        player.setXRot(aimFromPitch + (aimToPitch - aimFromPitch) * ease);
        if (aimTick >= 8) {
            SpawnerMacro.go(Step.OPEN, 0);
        }
    }

    private static void open(Minecraft client, LocalPlayer player) {
        if (client.gui.screen() != null) {
            timer = 10;
            return;
        }
        if (player.isShiftKeyDown()) {
            status = Lang.t("nicht schleichen", "stop sneaking");
            timer = 10;
            return;
        }
        if (player.getMainHandItem().is((Object)Items.SPAWNER) || player.getOffhandItem().is((Object)Items.SPAWNER)) {
            SpawnerMacro.stop(client, Lang.t("Spawner-Makro gestoppt: Leg den Spawner aus der Hand.", "Spawner macro stopped: put the spawner out of your hand."));
            return;
        }
        BlockHitResult hit = SpawnerMacro.lookedAtSpawner(client, SpawnerMacro.target());
        if (hit == null) {
            SpawnerSpots.note(SpawnerMacro.target(), Lang.t("nicht sichtbar", "not visible"));
            SpawnerMacro.go(Step.DONE, 1);
            return;
        }
        if (++tries > 3) {
            SpawnerSpots.note(SpawnerMacro.target(), Lang.t("\u00f6ffnet nicht", "does not open"));
            SpawnerMacro.go(Step.DONE, 1);
            return;
        }
        client.gameMode.useItemOn(player, InteractionHand.MAIN_HAND, hit);
        player.swing(InteractionHand.MAIN_HAND, SwingAnimation.DEFAULT, false);
        SpawnerMacro.go(Step.AWAIT, 0);
        awaitTicks = 60;
    }

    private static void await(Minecraft client) {
        AbstractContainerScreen screen;
        Screen screen2 = client.gui.screen();
        if (screen2 instanceof AbstractContainerScreen && SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2)) {
            droppedThisOpen = 0L;
            SpawnerMacro.go(Step.READ, SpawnerMacro.delay() + 3);
            return;
        }
        if (--awaitTicks <= 0) {
            SpawnerMacro.startAim(client);
        }
    }

    private static void read(Minecraft client, LocalPlayer player) {
        Slot sell;
        boolean arrowsOnly;
        Slot drop;
        boolean wantDrop;
        int sellFill;
        AbstractContainerScreen screen;
        Screen screen2 = client.gui.screen();
        if (!(screen2 instanceof AbstractContainerScreen) || !SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2)) {
            SpawnerMacro.stop(client, Lang.t("Spawner-Makro gestoppt: Men\u00fc geschlossen.", "Spawner macro stopped: menu closed."));
            return;
        }
        AbstractContainerMenu menu = screen.getMenu();
        Counts c = SpawnerMacro.counts(menu);
        SpawnerSpots.counts(SpawnerMacro.target(), c.bones, c.arrows, c.fill);
        if (c.bones > 0L) {
            roundHadBones = true;
        }
        FwmConfig config = FwmConfig.INSTANCE;
        FwmConfig.SpawnerSpot rules = SpawnerSpots.rules(client, SpawnerMacro.target());
        long total = c.total;
        boolean dropOnly = SpawnerMacro.dropOnly(SpawnerMacro.describe(menu)[0]);
        String mode = dropOnly ? "drop" : rules.mode;
        int n = sellFill = dropOnly ? 0 : rules.sellFill;
        if ("bones".equals((Object)mode)) {
            if (!dropping && c.bones > (long)Math.max((int)rules.dropMin, (int)2304)) {
                dropping = true;
            }
            wantDrop = dropping && c.bones >= 2304L && c.pageOnlyBones;
        } else if ("drop".equals((Object)mode)) {
            if (!dropping && total >= (long)rules.dropMin) {
                dropping = true;
            }
            wantDrop = dropping && total >= 100L;
        } else {
            wantDrop = false;
        }
        if (wantDrop && droppedThisOpen < 200000L && (drop = SpawnerMacro.button(menu, "droppen")) != null) {
            lastAmount = "bones".equals((Object)mode) ? c.bones : total;
            SpawnerMacro.click(client, menu, drop.index, 0, ContainerInput.PICKUP);
            anyDrop = true;
            status = Lang.t("droppe Seite", "dropping page");
            dropWaitTicks = 50;
            SpawnerMacro.go(Step.DROP_WAIT, 3);
            return;
        }
        boolean sellMode = "sell".equals((Object)mode);
        boolean bonesLeft = c.bones >= 2304L;
        boolean overFill = sellFill > 0 && c.fill >= sellFill;
        boolean sellNow = sellMode && total >= (long)rules.dropMin;
        boolean bl = arrowsOnly = config.spawnerAutoSell && c.bones == 0L && c.arrows > 0L;
        if (!(dropOnly || sold || wantDrop || !sellMode && bonesLeft || !overFill && !sellNow && !arrowsOnly || (sell = SpawnerMacro.button(menu, "verkaufen")) == null)) {
            sold = true;
            SpawnerMacro.click(client, menu, sell.index, 0, ContainerInput.PICKUP);
            status = Lang.t("verkaufe", "selling");
            confirmTicks = 60;
            SpawnerMacro.go(Step.CONFIRM, SpawnerMacro.delay());
            return;
        }
        SpawnerMacro.go(Step.DONE, SpawnerMacro.delay());
    }

    private static void dropWait(Minecraft client) {
        long now;
        AbstractContainerScreen screen;
        Screen screen2 = client.gui.screen();
        if (!(screen2 instanceof AbstractContainerScreen) || !SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2)) {
            if (++reopenCount > 3) {
                SpawnerMacro.go(Step.DONE, 1);
                return;
            }
            SpawnerMacro.go(Step.REOPEN, 20);
            return;
        }
        Counts c = SpawnerMacro.counts(screen.getMenu());
        FwmConfig.SpawnerSpot rules = SpawnerSpots.rules(client, SpawnerMacro.target());
        String mode = SpawnerMacro.dropOnly(SpawnerMacro.describe(screen.getMenu())[0]) ? "drop" : rules.mode;
        long l = now = "bones".equals((Object)mode) ? c.bones : c.total;
        if (now >= 0L && lastAmount >= 0L && now < lastAmount) {
            droppedThisOpen += lastAmount - now;
            SpawnerMacro.go(Step.READ, SpawnerMacro.delay());
            return;
        }
        if (--dropWaitTicks <= 0) {
            if (++reopenCount > 2) {
                SpawnerSpots.note(SpawnerMacro.target(), Lang.t("Anzahl sinkt nicht", "count not dropping"));
                SpawnerMacro.go(Step.DONE, 1);
            } else {
                SpawnerMacro.go(Step.REOPEN, 10);
            }
        }
    }

    private static void confirm(Minecraft client) {
        AbstractContainerScreen screen;
        Screen screen2 = client.gui.screen();
        if (screen2 instanceof AbstractContainerScreen && SpawnerMacro.isConfirmMenu(screen = (AbstractContainerScreen)screen2)) {
            Slot eighth;
            AbstractContainerMenu menu = screen.getMenu();
            Slot yes = SpawnerMacro.button(menu, "best\u00e4tig");
            if (yes == null) {
                yes = SpawnerMacro.button(menu, "bestaetig");
            }
            if (yes == null && menu.slots.size() > 7 && !(eighth = (Slot)menu.slots.get(7)).getItem().isEmpty() && !CANCEL.matcher((CharSequence)eighth.getItem().getHoverName().getString()).find()) {
                yes = eighth;
            }
            if (yes == null) {
                SpawnerMacro.stop(client, Lang.t("Spawner-Makro gestoppt: Best\u00e4tigen-Feld nicht gefunden.", "Spawner macro stopped: confirm field not found."));
                return;
            }
            SpawnerMacro.click(client, menu, yes.index, 0, ContainerInput.PICKUP);
            SpawnerMacro.say(client, Lang.t("Spawner " + (index + 1) + ": alles verkauft.", "Spawner " + (index + 1) + ": sold everything."), ChatFormatting.GREEN);
            SpawnerMacro.go(Step.REOPEN, 25);
            return;
        }
        if (--confirmTicks <= 0) {
            SpawnerMacro.stop(client, Lang.t("Spawner-Makro gestoppt: Verkauf nicht best\u00e4tigt.", "Spawner macro stopped: sale not confirmed."));
        }
    }

    private static void done(Minecraft client) {
        SpawnerMacro.closeMenu(client);
        if (FwmConfig.INSTANCE.spawnerAutoDrop) {
            throwRounds = 0;
            SpawnerMacro.go(Step.THROW, 30);
            return;
        }
        SpawnerMacro.next(client);
    }

    private static void throwItems(Minecraft client, LocalPlayer player) {
        if (client.gui.screen() != null) {
            timer = 10;
            return;
        }
        InventoryMenu inventory = player.inventoryMenu;
        for (Slot slot : inventory.slots) {
            ItemStack stack;
            if (!(slot.container instanceof Inventory) || slot.getContainerSlot() >= 36 || !(stack = slot.getItem()).is((Object)Items.BONE) && !stack.is((Object)Items.ARROW) && !LOOT.contains((Object)stack.getItem())) continue;
            client.gameMode.handleContainerInput(inventory.containerId, slot.index, 1, ContainerInput.THROW, (Player)player);
            status = Lang.t("werfe Inventar aus", "emptying inventory");
            timer = SpawnerMacro.delay();
            return;
        }
        if (++throwRounds < 2) {
            timer = 30;
            return;
        }
        SpawnerMacro.next(client);
    }

    private static void next(Minecraft client) {
        SpawnerMacro.beginVisit();
        if (++index < targets.size()) {
            SpawnerMacro.go(Step.REOPEN, 10);
            return;
        }
        if (FwmConfig.INSTANCE.spawnerAutoStop && !roundHadBones) {
            SpawnerMacro.stop(client, Lang.t("Spawner-Makro fertig: in keinem Spawner mehr Haupt-Loot (Knochen, Lohenstaub, Eisen ...).", "Spawner macro done: no main loot left in any spawner."));
            return;
        }
        index = 0;
        status = Lang.t("n\u00e4chste Runde in ", "next round in ") + FwmConfig.INSTANCE.spawnerRepeatSeconds + " s";
        SpawnerMacro.go(Step.ROUND_WAIT, FwmConfig.INSTANCE.spawnerRepeatSeconds * 20);
    }

    static BlockHitResult lookedAtSpawner(Minecraft client, BlockPos want) {
        BlockHitResult cross;
        Vec3 end;
        LocalPlayer player = client.player;
        if (player == null || client.level == null) {
            return null;
        }
        double reach = player.blockInteractionRange();
        Vec3 eyes = player.getEyePosition();
        BlockHitResult ray = client.level.clip(new ClipContext(eyes, end = eyes.add(player.getViewVector(1.0f).scale(reach)), ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)player));
        if (ray == null || ray.getType() != HitResult.Type.BLOCK) {
            return null;
        }
        BlockPos pos = ray.getBlockPos();
        if (!client.level.getBlockState(pos).is((Object)Blocks.SPAWNER)) {
            return null;
        }
        if (want != null && !want.equals((Object)pos)) {
            return null;
        }
        if (eyes.distanceTo(ray.getLocation()) > reach) {
            return null;
        }
        HitResult hitResult = client.hitResult;
        if (!(hitResult instanceof BlockHitResult) || (cross = (BlockHitResult)hitResult).getType() != HitResult.Type.BLOCK || !cross.getBlockPos().equals((Object)pos)) {
            return null;
        }
        return ray;
    }

    private static BlockPos openMenuSpawner(Minecraft client) {
        AbstractContainerScreen screen;
        Screen screen2 = client.gui.screen();
        if (screen2 instanceof AbstractContainerScreen && SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2)) {
            return SpawnerSpots.opened();
        }
        return null;
    }

    public static boolean isSpawnerMenu(AbstractContainerScreen<?> screen) {
        if (KNOWN.containsKey(screen)) {
            return true;
        }
        if (SpawnerMacro.detect(screen)) {
            KNOWN.put(screen, (Object)Boolean.TRUE);
            return true;
        }
        return false;
    }

    private static boolean detect(AbstractContainerScreen<?> screen) {
        if (screen.getTitle().getString().toLowerCase(Locale.ROOT).contains((CharSequence)"spawner")) {
            return true;
        }
        if (SpawnerMacro.isConfirmMenu(screen)) {
            return false;
        }
        for (Slot slot : screen.getMenu().slots) {
            ItemLore lore;
            if (slot.container instanceof Inventory || (lore = (ItemLore)slot.getItem().get(DataComponents.LORE)) == null) continue;
            for (Component line : lore.lines()) {
                if (!INFO_LORE.matcher((CharSequence)line.getString()).find()) continue;
                return true;
            }
        }
        return SpawnerSpots.openedJustNow() && screen.getMenu().slots.size() > 36;
    }

    static boolean isConfirmMenu(AbstractContainerScreen<?> screen) {
        String title = screen.getTitle().getString().toLowerCase(Locale.ROOT);
        if (title.contains((CharSequence)"best\u00e4tig") || title.contains((CharSequence)"bestaetig") || title.contains((CharSequence)"confirm")) {
            return true;
        }
        return SpawnerMacro.button(screen.getMenu(), "best\u00e4tig") != null || SpawnerMacro.button(screen.getMenu(), "bestaetig") != null;
    }

    public static boolean dropOnly(String kind) {
        return kind != null && !"Skelett".equals((Object)kind);
    }

    public static String[] describe(AbstractContainerMenu menu) {
        for (Slot slot : menu.slots) {
            ItemStack stack;
            ItemLore lore;
            if (slot.container instanceof Inventory || (lore = (ItemLore)(stack = slot.getItem()).get(DataComponents.LORE)) == null) continue;
            String loot = null;
            boolean info = false;
            for (Component line : lore.lines()) {
                String s = line.getString();
                if (FILL.matcher((CharSequence)s).find()) {
                    info = true;
                }
                Matcher any = ANY_COUNT.matcher((CharSequence)s);
                if (loot != null || !any.find()) continue;
                loot = any.group(1).trim();
            }
            if (!info) continue;
            String id = BuiltInRegistries.ITEM.getKey((Object)stack.getItem()).getPath();
            String kind = null;
            if (id.endsWith("_spawn_egg")) {
                String mob = id.substring(0, id.length() - "_spawn_egg".length());
                kind = (String)MOB_NAMES.getOrDefault((Object)mob, (Object)(Character.toUpperCase((char)mob.charAt(0)) + mob.substring(1).replace('_', ' ')));
            }
            return new String[]{kind, loot};
        }
        return new String[]{null, null};
    }

    private static Counts counts(AbstractContainerMenu menu) {
        long bones = -1L;
        long arrows = -1L;
        long total = -1L;
        int fill = -1;
        for (Slot slot : menu.slots) {
            ItemLore lore;
            if (slot.container instanceof Inventory || (lore = (ItemLore)slot.getItem().get(DataComponents.LORE)) == null) continue;
            boolean info = false;
            long sum = 0L;
            int counted = 0;
            long first = -1L;
            long second = -1L;
            for (Component line : lore.lines()) {
                String s = line.getString();
                Matcher f = FILL.matcher((CharSequence)s);
                if (f.find()) {
                    fill = (int)Math.round((double)Double.parseDouble((String)f.group(1).replace(',', '.')));
                    info = true;
                    continue;
                }
                Matcher any = ANY_COUNT.matcher((CharSequence)s);
                if (!any.find()) continue;
                long n = SpawnerMacro.number(any.group(2));
                sum += n;
                if (counted == 0) {
                    first = n;
                } else if (counted == 1) {
                    second = n;
                }
                ++counted;
            }
            if (!info || counted <= 0) continue;
            bones = first;
            arrows = second < 0L ? 0L : second;
            total = sum;
            break;
        }
        boolean onlyBones = true;
        boolean any = false;
        Item kind = null;
        long pageBones = 0L;
        for (Slot slot : menu.slots) {
            ItemStack stack;
            if (slot.container instanceof Inventory || slot.index >= 36 || (stack = slot.getItem()).isEmpty()) continue;
            any = true;
            LOOT.add((Object)stack.getItem());
            if (kind == null) {
                kind = stack.getItem();
            }
            if (stack.is((Object)kind)) {
                pageBones += (long)stack.getCount();
                continue;
            }
            onlyBones = false;
        }
        if (bones < 0L) {
            bones = pageBones;
        }
        if (arrows < 0L) {
            arrows = 0L;
        }
        if (total < 0L) {
            total = bones + arrows;
        }
        return new Counts(bones, arrows, total, fill, any && onlyBones);
    }

    private static long number(String digits) {
        return Money.cents(digits) / 100L;
    }

    private static Slot button(AbstractContainerMenu menu, String word) {
        for (Slot slot : menu.slots) {
            ItemStack stack;
            if (slot.container instanceof Inventory || (stack = slot.getItem()).isEmpty() || !stack.getHoverName().getString().toLowerCase(Locale.ROOT).contains((CharSequence)word)) continue;
            return slot;
        }
        return null;
    }

    private static void click(Minecraft client, AbstractContainerMenu menu, int slot, int button, ContainerInput action) {
        client.gameMode.handleContainerInput(menu.containerId, slot, button, action, (Player)client.player);
    }

    private static void closeMenu(Minecraft client) {
        AbstractContainerScreen screen;
        Screen screen2;
        if (client.player != null && (screen2 = client.gui.screen()) instanceof AbstractContainerScreen && (SpawnerMacro.isSpawnerMenu(screen = (AbstractContainerScreen)screen2) || SpawnerMacro.isConfirmMenu(screen))) {
            client.player.closeContainer();
        }
    }

    private static void go(Step next, int ticks) {
        step = next;
        timer = ticks;
    }

    private static int delay() {
        return FwmConfig.INSTANCE.spawnerClickDelayTicks;
    }

    private static void say(Minecraft client, String text, ChatFormatting color) {
        if (client.player == null) {
            return;
        }
        client.player.sendSystemMessage((Component)Component.literal((String)"[FWM] ").withStyle(ChatFormatting.GREEN).append((Component)Component.literal((String)text).withStyle(color)));
    }

    static {
        targets = new ArrayList();
        status = "";
        INFO_LORE = Pattern.compile((String)"Knochen:|Pfeile:|Auslastung", (int)2);
        KNOWN = new WeakHashMap();
        MOB_NAMES = Map.ofEntries((Map.Entry[])new Map.Entry[]{Map.entry((Object)"skeleton", (Object)"Skelett"), Map.entry((Object)"blaze", (Object)"Blaze"), Map.entry((Object)"iron_golem", (Object)"Eisengolem"), Map.entry((Object)"creeper", (Object)"Creeper"), Map.entry((Object)"zombie", (Object)"Zombie"), Map.entry((Object)"spider", (Object)"Spinne"), Map.entry((Object)"cave_spider", (Object)"H\u00f6hlenspinne"), Map.entry((Object)"pig", (Object)"Schwein"), Map.entry((Object)"cow", (Object)"Kuh"), Map.entry((Object)"chicken", (Object)"Huhn"), Map.entry((Object)"sheep", (Object)"Schaf"), Map.entry((Object)"zombified_piglin", (Object)"Zombie-Piglin"), Map.entry((Object)"enderman", (Object)"Enderman"), Map.entry((Object)"witch", (Object)"Hexe"), Map.entry((Object)"slime", (Object)"Schleim"), Map.entry((Object)"magma_cube", (Object)"Magmaw\u00fcrfel"), Map.entry((Object)"wither_skeleton", (Object)"Witherskelett"), Map.entry((Object)"squid", (Object)"Tintenfisch"), Map.entry((Object)"rabbit", (Object)"Hase"), Map.entry((Object)"guardian", (Object)"W\u00e4chter"), Map.entry((Object)"drowned", (Object)"Ertrunkener"), Map.entry((Object)"husk", (Object)"W\u00fcstenzombie"), Map.entry((Object)"stray", (Object)"Eiswanderer")});
        LOOT = new HashSet();
    }

    private static enum Step {
        OFF,
        AIM,
        OPEN,
        AWAIT,
        READ,
        DROP_WAIT,
        CONFIRM,
        REOPEN,
        DONE,
        THROW,
        ROUND_WAIT;

    }

    private record Counts(long bones, long arrows, long total, int fill, boolean pageOnlyBones) {
    }
}
