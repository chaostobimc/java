/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.InventoryScreen
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.inventory.InventoryMenu
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.Item
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.ItemLike
 */
package net.friendswithmoney.fwm.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.mixin.HandledScreenAccessor;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;

public final class FightKits {
    public static final int OFFHAND = 40;
    private static final Pattern TIERED = Pattern.compile((String)"^(netherite|diamond|iron|golden|stone|wooden|copper|leather|chainmail)_(sword|axe|pickaxe|shovel|hoe|spear|helmet|chestplate|leggings|boots)$");
    private static int[] toggleBox;
    private static int[] kitBox;
    private static final Map<String, ItemStack> STACKS;

    public static boolean kitSlot(int index) {
        return index >= 0 && index < 36 || index == 40;
    }

    private FightKits() {
    }

    public static String kind(String id) {
        if (id == null) {
            return "";
        }
        String path = id.startsWith("minecraft:") ? id.substring(10) : id;
        Matcher m = TIERED.matcher((CharSequence)path);
        return m.matches() ? "*_" + m.group(2) : path;
    }

    public static List<Missing> missing(Map<Integer, FwmConfig.KitSlot> slots, Map<String, Integer> have) {
        HashMap haveByKind = new HashMap();
        have.forEach((arg_0, arg_1) -> FightKits.lambda$missing$0((Map)haveByKind, arg_0, arg_1));
        LinkedHashMap need = new LinkedHashMap();
        HashMap sample = new HashMap();
        slots.entrySet().stream().sorted(Map.Entry.comparingByKey()).forEach(arg_0 -> FightKits.lambda$missing$1((Map)need, (Map)sample, arg_0));
        ArrayList out = new ArrayList();
        need.forEach((arg_0, arg_1) -> FightKits.lambda$missing$2((Map)haveByKind, (List)out, (Map)sample, arg_0, arg_1));
        return out;
    }

    public static FwmConfig.FightKit selected() {
        List<FwmConfig.FightKit> kits = FwmConfig.INSTANCE.fightKits;
        if (kits == null || kits.isEmpty()) {
            return null;
        }
        int i = Math.max((int)0, (int)Math.min((int)(kits.size() - 1), (int)FwmConfig.INSTANCE.fightKitSelected));
        return (FwmConfig.FightKit)kits.get(i);
    }

    private static Map<String, Integer> have(Inventory inv) {
        HashMap out = new HashMap();
        for (int i = 0; i < 36; ++i) {
            FightKits.count((Map<String, Integer>)out, inv.getItem(i));
        }
        FightKits.count((Map<String, Integer>)out, inv.getItem(40));
        return out;
    }

    private static void count(Map<String, Integer> out, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        out.merge((Object)BuiltInRegistries.ITEM.getKey((Object)stack.getItem()).toString(), (Object)stack.getCount(), Integer::sum);
    }

    public static void capture(FwmConfig.FightKit kit, Inventory inv) {
        kit.slots.clear();
        for (int i = 0; i < 36; ++i) {
            FightKits.put(kit, i, inv.getItem(i));
        }
        FightKits.put(kit, 40, inv.getItem(40));
    }

    private static void put(FwmConfig.FightKit kit, int index, ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return;
        }
        FwmConfig.KitSlot slot = new FwmConfig.KitSlot();
        slot.item = BuiltInRegistries.ITEM.getKey((Object)stack.getItem()).toString();
        slot.count = stack.getCount();
        kit.slots.put((Object)index, (Object)slot);
    }

    public static ItemStack stack(String id, int count) {
        ItemStack base = (ItemStack)STACKS.computeIfAbsent((Object)(id == null ? "" : id), key -> {
            try {
                Identifier ident = Identifier.tryParse((String)key);
                if (ident == null || !BuiltInRegistries.ITEM.containsKey(ident)) {
                    return ItemStack.EMPTY;
                }
                Item item = (Item)BuiltInRegistries.ITEM.getValue(ident);
                return new ItemStack((ItemLike)item);
            }
            catch (Exception e) {
                return null;
            }
        });
        if (base == null) {
            STACKS.remove((Object)(id == null ? "" : id));
            return ItemStack.EMPTY;
        }
        return base.isEmpty() ? ItemStack.EMPTY : base.copyWithCount(Math.max((int)1, (int)Math.min((int)99, (int)count)));
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof InventoryScreen)) {
                return;
            }
            InventoryScreen inventory = (InventoryScreen)screen;
            ScreenEvents.afterExtract((Screen)screen).register((s, ctx, mouseX, mouseY, delta) -> FightKits.render(client, inventory, ctx, mouseX, mouseY));
            ScreenMouseEvents.allowMouseClick((Screen)screen).register((s, click) -> !FightKits.click(click.x(), click.y(), click.button()));
        });
    }

    private static boolean active() {
        return Features.isOn("fwm.tab.fightkit") && FightKits.selected() != null;
    }

    private static boolean click(double x, double y, int button) {
        if (!FightKits.active() || button != 0) {
            return false;
        }
        if (FightKits.inside(toggleBox, x, y)) {
            FwmConfig.INSTANCE.fightKitShowInInventory = !FwmConfig.INSTANCE.fightKitShowInInventory;
            ConfigManager.save();
            return true;
        }
        if (FightKits.inside(kitBox, x, y)) {
            List<FwmConfig.FightKit> kits = FwmConfig.INSTANCE.fightKits;
            FwmConfig.INSTANCE.fightKitSelected = (FwmConfig.INSTANCE.fightKitSelected + 1) % kits.size();
            ConfigManager.save();
            return true;
        }
        return false;
    }

    private static boolean inside(int[] box, double x, double y) {
        return box != null && x >= (double)box[0] && x < (double)(box[0] + box[2]) && y >= (double)box[1] && y < (double)(box[1] + box[3]);
    }

    private static void render(Minecraft client, InventoryScreen screen, GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        toggleBox = null;
        kitBox = null;
        if (!FightKits.active() || client.player == null) {
            return;
        }
        Font font = client.font;
        HandledScreenAccessor at = (HandledScreenAccessor)screen;
        FwmConfig.FightKit kit = FightKits.selected();
        int invX = at.fwm$x();
        int invY = at.fwm$y();
        int effects = client.player.getActiveEffects().size();
        int effectsH = effects == 0 ? 0 : (effects > 5 ? 166 : effects * 33);
        int left = invX + 176 + 4;
        int w = Math.max((int)90, (int)Math.min((int)150, (int)(screen.width - left - 4)));
        int top = invY + effectsH + (effects > 0 ? 4 : 0);
        int listX = left;
        int listY = top + 34;
        int listW = w;
        boolean showing = FwmConfig.INSTANCE.fightKitShowInInventory;
        String label = Lang.str(showing ? "fwm.fightkit.hide" : "fwm.fightkit.show", new Object[0]);
        toggleBox = FightKits.button(ctx, font, left, top, w, font.plainSubstrByWidth(label, w - 6), mouseX, mouseY, true);
        int kx = left;
        int ky = top + 16;
        String kitLabel = "< " + font.plainSubstrByWidth(kit.name == null ? "Kit" : kit.name, w - 24) + " >";
        kitBox = FightKits.button(ctx, font, kx, ky, w, kitLabel, mouseX, mouseY, FwmConfig.INSTANCE.fightKits.size() > 1);
        if (!showing) {
            return;
        }
        Inventory inv = client.player.getInventory();
        List<Missing> missing = FightKits.missing(kit.slots, FightKits.have(inv));
        if (listW > 0) {
            int ly = listY;
            if (missing.isEmpty()) {
                ctx.text(font, Lang.str("fwm.fightkit.complete", new Object[0]), listX, ly, -10688364);
            } else {
                ctx.text(font, Lang.str("fwm.fightkit.missing", new Object[0]), listX, ly, -1017230);
                ly += 11;
                for (Missing m : missing) {
                    ItemStack stack = FightKits.stack(m.item(), 1);
                    if (!stack.isEmpty()) {
                        ctx.item(stack, listX, ly - 2);
                    }
                    String name = stack.isEmpty() ? m.item() : stack.getHoverName().getString();
                    ctx.text(font, font.plainSubstrByWidth(m.count() + "x " + name, listW - 18), listX + 18, ly + 2, FwmTheme.TEXT);
                    if ((ly += 17) <= screen.height - 12) continue;
                    break;
                }
            }
        }
        for (Slot slot : ((InventoryMenu)screen.getMenu()).slots) {
            boolean right;
            int index;
            FwmConfig.KitSlot want;
            if (!(slot.container instanceof Inventory) || (want = (FwmConfig.KitSlot)kit.slots.get((Object)(index = slot.getContainerSlot()))) == null || want.item == null || !FightKits.kitSlot(index)) continue;
            ItemStack has = inv.getItem(index);
            boolean bl = right = !has.isEmpty() && FightKits.kind(BuiltInRegistries.ITEM.getKey((Object)has.getItem()).toString()).equals((Object)FightKits.kind(want.item));
            if (right) continue;
            int sx = at.fwm$x() + slot.x;
            int sy = at.fwm$y() + slot.y;
            int red = -1750963;
            ctx.fill(sx - 1, sy - 1, sx + 17, sy, red);
            ctx.fill(sx - 1, sy + 16, sx + 17, sy + 17, red);
            ctx.fill(sx - 1, sy, sx, sy + 16, red);
            ctx.fill(sx + 16, sy, sx + 17, sy + 16, red);
        }
    }

    private static int[] button(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, String label, int mouseX, int mouseY, boolean enabled) {
        boolean over = enabled && mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + 14;
        ctx.fill(x, y, x + w, y + 14, over ? -534754765 : -804250602);
        int edge = over ? -10688364 : -13739454;
        ctx.fill(x, y, x + w, y + 1, edge);
        ctx.fill(x, y + 13, x + w, y + 14, edge);
        ctx.fill(x, y, x + 1, y + 14, edge);
        ctx.fill(x + w - 1, y, x + w, y + 14, edge);
        ctx.text(font, label, x + (w - font.width(label)) / 2, y + 3, enabled ? FwmTheme.TEXT : FwmTheme.TEXT_DIM);
        return new int[]{x, y, w, 14};
    }

    private static /* synthetic */ void lambda$missing$2(Map haveByKind, List out, Map sample, String k, Integer n) {
        int lack = n - (Integer)haveByKind.getOrDefault((Object)k, (Object)0);
        if (lack > 0) {
            out.add((Object)new Missing((String)sample.get((Object)k), lack));
        }
    }

    private static /* synthetic */ void lambda$missing$1(Map need, Map sample, Map.Entry e) {
        FwmConfig.KitSlot slot = (FwmConfig.KitSlot)e.getValue();
        if (slot == null || slot.item == null || slot.item.isBlank()) {
            return;
        }
        String k = FightKits.kind(slot.item);
        need.merge((Object)k, (Object)Math.max((int)1, (int)slot.count), Integer::sum);
        sample.putIfAbsent((Object)k, (Object)slot.item);
    }

    private static /* synthetic */ void lambda$missing$0(Map haveByKind, String id, Integer n) {
        haveByKind.merge((Object)FightKits.kind(id), (Object)n, Integer::sum);
    }

    static {
        STACKS = new HashMap();
    }

    public record Missing(String item, int count) {
    }
}
