/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.function.Consumer
 *  java.util.function.Supplier
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public class AuctionItemPanel
extends Panel {
    private static final int COLS = 9;
    private static final int CELL = 20;
    private static final int GAP = 2;
    private static final int MAIN_SLOTS = 27;
    private final PanelHost host;
    private final Supplier<ItemStack> current;
    private final Consumer<ItemStack> pick;
    private final List<Slot> slots = new ArrayList();
    private int gridX;
    private int gridY;

    public AuctionItemPanel(PanelHost host) {
        this(host, (Supplier<ItemStack>)((Supplier)Auction::item), (Consumer<ItemStack>)((Consumer)Auction::hold));
    }

    public AuctionItemPanel(PanelHost host, Supplier<ItemStack> current, Consumer<ItemStack> pick) {
        this.host = host;
        this.current = current;
        this.pick = pick;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.auction.pickTitle", new Object[0]);
    }

    @Override
    protected void onResize() {
        int i;
        this.slots.clear();
        Minecraft client = Minecraft.getInstance();
        if (client.player == null) {
            return;
        }
        Inventory inventory = client.player.getInventory();
        for (i = 0; i < 27; ++i) {
            this.slots.add((Object)new Slot(inventory.getItem(9 + i), 9 + i));
        }
        for (i = 0; i < 9; ++i) {
            this.slots.add((Object)new Slot(inventory.getItem(i), i));
        }
        int gridW = 196;
        this.gridX = this.x + Math.max((int)0, (int)((this.width - gridW) / 2));
        this.gridY = this.y + 6;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        if (this.slots.isEmpty()) {
            ctx.text(font, (Component)Lang.of("fwm.auction.noInventory", new Object[0]), this.x, this.y + 8, FwmTheme.TEXT_DIM, false);
            return;
        }
        ItemStack hovered = ItemStack.EMPTY;
        for (int i = 0; i < this.slots.size(); ++i) {
            boolean chosen;
            Slot slot = (Slot)((Object)this.slots.get(i));
            int cx = this.cellX(i);
            int cy = this.cellY(i);
            boolean over = mouseX >= cx && mouseX < cx + 20 && mouseY >= cy && mouseY < cy + 20;
            boolean bl = chosen = !slot.stack().isEmpty() && ItemStack.matches((ItemStack)slot.stack(), (ItemStack)((ItemStack)this.current.get()));
            FwmTheme.box(ctx, cx, cy, 20, 20, FwmTheme.INSET, chosen ? FwmTheme.ACCENT : (over ? FwmTheme.ACCENT : FwmTheme.OFF));
            if (slot.stack().isEmpty()) continue;
            ctx.item(slot.stack(), cx + 2, cy + 2);
            ctx.itemDecorations(font, slot.stack(), cx + 2, cy + 2);
            if (!over) continue;
            hovered = slot.stack();
        }
        int nameY = this.cellY(this.slots.size() - 1) + 20 + 8;
        if (!hovered.isEmpty()) {
            ctx.text(font, AuctionItemPanel.counted(hovered), this.x, nameY, FwmTheme.TEXT, false);
        } else if (!((ItemStack)this.current.get()).isEmpty()) {
            ctx.text(font, AuctionItemPanel.counted((ItemStack)this.current.get()), this.x, nameY, FwmTheme.TEXT_DIM, false);
        }
    }

    private static Component counted(ItemStack stack) {
        if (stack.getCount() <= 1) {
            return stack.getHoverName();
        }
        return Component.literal((String)(stack.getCount() + "x ")).append(stack.getHoverName());
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0) {
            return false;
        }
        for (int i = 0; i < this.slots.size(); ++i) {
            Slot slot = (Slot)((Object)this.slots.get(i));
            if (slot.stack().isEmpty()) continue;
            int cx = this.cellX(i);
            int cy = this.cellY(i);
            if (mouseX < (double)cx || mouseX >= (double)(cx + 20) || mouseY < (double)cy || mouseY >= (double)(cy + 20)) continue;
            this.pick.accept((Object)slot.stack());
            this.host.closeSub();
            return true;
        }
        return false;
    }

    private int cellX(int index) {
        return this.gridX + index % 9 * 22;
    }

    private int cellY(int index) {
        int row = index / 9;
        return this.gridY + row * 22 + (index >= 27 ? 6 : 0);
    }

    private record Slot(ItemStack stack, int index) {
    }
}
