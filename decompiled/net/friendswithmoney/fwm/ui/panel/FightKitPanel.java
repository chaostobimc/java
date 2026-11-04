/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.function.Consumer
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.FightKits;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.ItemPanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;

public class FightKitPanel
extends Panel {
    private static final int CELL = 18;
    private static final int BTN_H = 13;
    private final PanelHost host;
    private EditBox nameField;
    private boolean confirmDelete;
    private final List<int[]> boxes = new ArrayList();
    private final List<Runnable> actions = new ArrayList();
    private final List<String> labels = new ArrayList();

    public FightKitPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.fightkit", new Object[0]);
    }

    private static List<FwmConfig.FightKit> kits() {
        if (FwmConfig.INSTANCE.fightKits == null) {
            FwmConfig.INSTANCE.fightKits = new ArrayList();
        }
        return FwmConfig.INSTANCE.fightKits;
    }

    private static FwmConfig.FightKit current() {
        return FightKits.selected();
    }

    private int gridX() {
        return this.x + (this.width - 162) / 2 + 12;
    }

    private int gridY() {
        return this.y + 36;
    }

    private int[] cell(int index) {
        if (index == 40) {
            return new int[]{this.gridX() - 18 - 8, this.gridY() + 54 + 4};
        }
        if (index < 9) {
            return new int[]{this.gridX() + index * 18, this.gridY() + 54 + 4};
        }
        int row = (index - 9) / 9;
        int col = (index - 9) % 9;
        return new int[]{this.gridX() + col * 18, this.gridY() + row * 18};
    }

    private int cellAt(double mx, double my) {
        for (int i = 0; i < 36; ++i) {
            if (!this.inCell(i, mx, my)) continue;
            return i;
        }
        if (this.inCell(40, mx, my)) {
            return 40;
        }
        return -1;
    }

    private boolean inCell(int index, double mx, double my) {
        int[] c = this.cell(index);
        return mx >= (double)c[0] && mx < (double)(c[0] + 18) && my >= (double)c[1] && my < (double)(c[1] + 18);
    }

    @Override
    protected void onResize() {
        if (this.nameField != null) {
            this.host.detach((AbstractWidget)this.nameField);
        }
        Font font = Minecraft.getInstance().font;
        this.nameField = new EditBox(font, this.x + 4, this.y + 20, 110, 10, (Component)Lang.of("fwm.fightkit.name", new Object[0]));
        this.nameField.setBordered(false);
        this.nameField.setMaxLength(24);
        this.nameField.setTextColor(FwmTheme.TEXT);
        FwmConfig.FightKit kit = FightKitPanel.current();
        this.nameField.setValue(kit == null || kit.name == null ? "" : kit.name);
        this.host.attach((AbstractWidget)this.nameField);
    }

    private void storeName() {
        FwmConfig.FightKit kit = FightKitPanel.current();
        if (kit == null || this.nameField == null) {
            return;
        }
        String name = this.nameField.getValue().trim();
        kit.name = name.isEmpty() ? "Kit" : name;
    }

    private void select(int index) {
        this.storeName();
        FwmConfig.INSTANCE.fightKitSelected = index;
        this.confirmDelete = false;
        ConfigManager.save();
        this.onResize();
    }

    private int chip(GuiGraphicsExtractor ctx, Font font, int cx, int cy, String key, String label, boolean active, int mouseX, int mouseY, Runnable action) {
        boolean over;
        int w = font.width(label) + 10;
        boolean bl = over = mouseX >= cx && mouseX < cx + w && mouseY >= cy && mouseY < cy + 13;
        FwmTheme.box(ctx, cx, cy, w, 13, active ? FwmTheme.ACCENT_DIM : (over ? FwmTheme.RAISED : FwmTheme.INSET), active || over ? FwmTheme.ACCENT : FwmTheme.EDGE);
        ctx.text(font, label, cx + 5, cy + 3, active ? FwmTheme.TEXT : (over ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM), false);
        this.boxes.add((Object)new int[]{cx, cy, w, 13});
        this.actions.add((Object)action);
        this.labels.add((Object)key);
        return w;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.boxes.clear();
        this.actions.clear();
        this.labels.clear();
        int cx = this.x;
        List<FwmConfig.FightKit> kits = FightKitPanel.kits();
        int selected = Math.max((int)0, (int)Math.min((int)(kits.size() - 1), (int)FwmConfig.INSTANCE.fightKitSelected));
        for (int i = 0; i < kits.size(); ++i) {
            int index = i;
            String name = ((FwmConfig.FightKit)kits.get((int)i)).name == null ? "Kit" : ((FwmConfig.FightKit)kits.get((int)i)).name;
            if ((cx += this.chip(ctx, font, cx, this.y, "kit:" + name, font.plainSubstrByWidth(name, 70), i == selected, mouseX, mouseY, () -> this.select(index)) + 4) > this.x + this.width - 60) break;
        }
        this.chip(ctx, font, cx, this.y, "fwm.fightkit.new", Lang.str("fwm.fightkit.new", new Object[0]), false, mouseX, mouseY, () -> {
            this.storeName();
            FwmConfig.FightKit kit = new FwmConfig.FightKit();
            kit.name = "Kit " + (kits.size() + 1);
            kits.add((Object)kit);
            this.select(kits.size() - 1);
        });
        FwmConfig.FightKit kit = FightKitPanel.current();
        if (kit == null) {
            ctx.text(font, (Component)Lang.of("fwm.fightkit.none", new Object[0]), this.x + 2, this.y + 24, FwmTheme.TEXT_DIM, false);
            this.nameField.visible = false;
            return;
        }
        this.nameField.visible = true;
        FwmTheme.box(ctx, this.x, this.y + 16, 118, 16, FwmTheme.INSET, this.nameField.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
        this.nameField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        int bx = this.x + 124;
        boolean inInventory = FwmConfig.INSTANCE.fightKitShowInInventory;
        bx += this.chip(ctx, font, bx, this.y + 17, "fwm.fightkit.check", Lang.str(inInventory ? "fwm.fightkit.inInventoryOn" : "fwm.fightkit.inInventoryOff", new Object[0]), inInventory, mouseX, mouseY, () -> {
            FwmConfig.INSTANCE.fightKitShowInInventory = !FwmConfig.INSTANCE.fightKitShowInInventory;
            ConfigManager.save();
        }) + 4;
        bx += this.chip(ctx, font, bx, this.y + 17, "fwm.fightkit.capture", Lang.str("fwm.fightkit.capture", new Object[0]), false, mouseX, mouseY, () -> {
            Minecraft client = Minecraft.getInstance();
            if (client.player == null) {
                return;
            }
            FightKits.capture(kit, client.player.getInventory());
            ConfigManager.save();
        }) + 4;
        bx += this.chip(ctx, font, bx, this.y + 17, "fwm.fightkit.clear", Lang.str("fwm.fightkit.clear", new Object[0]), false, mouseX, mouseY, () -> {
            kit.slots.clear();
            ConfigManager.save();
        }) + 4;
        this.chip(ctx, font, bx, this.y + 17, "fwm.fightkit.delete", Lang.str(this.confirmDelete ? "fwm.fightkit.deleteSure" : "fwm.fightkit.delete", new Object[0]), this.confirmDelete, mouseX, mouseY, () -> {
            if (!this.confirmDelete) {
                this.confirmDelete = true;
                return;
            }
            kits.remove((Object)kit);
            FwmConfig.INSTANCE.fightKitSelected = Math.max((int)0, (int)(kits.size() - 1));
            this.confirmDelete = false;
            ConfigManager.save();
            this.onResize();
        });
        for (int i = 0; i < 36; ++i) {
            this.drawCell(ctx, font, kit, i, mouseX, mouseY);
        }
        this.drawCell(ctx, font, kit, 40, mouseX, mouseY);
        int[] off = this.cell(40);
        ctx.text(font, (Component)Lang.of("fwm.fightkit.offhand", new Object[0]), off[0] - 2, off[1] + 18 + 2, FwmTheme.TEXT_DIM, false);
        int hy = this.gridY() + 72 + 16;
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str("fwm.fightkit.hint", new Object[0]), this.width - 4)), this.x + 2, hy, FwmTheme.TEXT_DIM, false);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str("fwm.fightkit.hint2", new Object[0]), this.width - 4)), this.x + 2, hy + 10, FwmTheme.TEXT_DIM, false);
    }

    private void drawCell(GuiGraphicsExtractor ctx, Font font, FwmConfig.FightKit kit, int index, int mouseX, int mouseY) {
        int[] c = this.cell(index);
        boolean over = mouseX >= c[0] && mouseX < c[0] + 18 && mouseY >= c[1] && mouseY < c[1] + 18;
        FwmTheme.box(ctx, c[0], c[1], 18, 18, over ? FwmTheme.RAISED : FwmTheme.INSET, over ? FwmTheme.ACCENT : FwmTheme.EDGE);
        FwmConfig.KitSlot slot = (FwmConfig.KitSlot)kit.slots.get((Object)index);
        if (slot == null || slot.item == null) {
            return;
        }
        ItemStack stack = FightKits.stack(slot.item, slot.count);
        if (stack.isEmpty()) {
            return;
        }
        ctx.item(stack, c[0] + 1, c[1] + 1);
        ctx.itemDecorations(font, stack, c[0] + 1, c[1] + 1);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        if (this.nameField != null) {
            boolean onName;
            boolean bl = onName = mouseX >= (double)this.x && mouseX < (double)(this.x + 118) && mouseY >= (double)(this.y + 16) && mouseY < (double)(this.y + 32);
            if (!onName && this.nameField.isFocused()) {
                this.storeName();
                ConfigManager.save();
            }
            this.nameField.setFocused(onName && this.nameField.visible);
            if (onName) {
                return true;
            }
        }
        if (button == 0) {
            for (int i = this.boxes.size() - 1; i >= 0; --i) {
                int[] b = (int[])this.boxes.get(i);
                if (!(mouseX >= (double)b[0]) || !(mouseX < (double)(b[0] + b[2])) || !(mouseY >= (double)b[1]) || !(mouseY < (double)(b[1] + b[3]))) continue;
                ((Runnable)this.actions.get(i)).run();
                return true;
            }
        }
        FwmConfig.FightKit kit = FightKitPanel.current();
        int index = this.cellAt(mouseX, mouseY);
        if (kit == null || index < 0) {
            return true;
        }
        this.confirmDelete = false;
        if (button == 1) {
            kit.slots.remove((Object)index);
            ConfigManager.save();
            return true;
        }
        if (button == 0) {
            this.host.openSub(new ItemPanel(this.host, (Consumer<String>)((Consumer)id -> {
                FwmConfig.KitSlot slot = new FwmConfig.KitSlot();
                slot.item = id;
                FwmConfig.KitSlot old = (FwmConfig.KitSlot)kit.slots.get((Object)index);
                slot.count = old != null && id.equals((Object)old.item) ? old.count : 1;
                kit.slots.put((Object)index, (Object)slot);
                ConfigManager.save();
            })));
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        FwmConfig.FightKit kit = FightKitPanel.current();
        int index = this.cellAt(mouseX, mouseY);
        if (kit == null || index < 0) {
            return false;
        }
        FwmConfig.KitSlot slot = (FwmConfig.KitSlot)kit.slots.get((Object)index);
        if (slot == null) {
            return true;
        }
        slot.count = Math.max((int)1, (int)Math.min((int)64, (int)(slot.count + (vertical > 0.0 ? 1 : -1))));
        ConfigManager.save();
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (this.nameField == null || !this.nameField.isFocused()) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            this.nameField.setFocused(false);
            this.storeName();
            ConfigManager.save();
            return true;
        }
        return this.nameField.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.nameField != null && this.nameField.isFocused() && this.nameField.charTyped(input);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.nameField != null && this.nameField.isFocused();
    }

    @Override
    public void onHidden() {
        this.storeName();
        ConfigManager.save();
        if (this.nameField != null) {
            this.host.detach((AbstractWidget)this.nameField);
        }
    }
}
