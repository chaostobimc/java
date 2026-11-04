/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.net.URI
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.ConfirmLinkScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.core.registries.BuiltInRegistries
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.item.ItemStack
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui.panel;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.TicketsPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

public class SpawnerPanel
extends Panel {
    private static final int PREVIEW_W = 52;
    private static final int GAP = 5;
    private static final int HEADER_H = 11;
    private static final int ROW_H = 15;
    private static final int BTN_H = 16;
    private static final int BTN_W = 118;
    private static final int PRICE_W = 52;
    private static final int PLAYER_SELLS = -10688364;
    private static final int PLAYER_BUYS = -2067340;
    private static final List<String> PINNED = List.of((Object)"skelly", (Object)"creeper");
    private static final int PINNED_GAP = 6;
    private static final Map<String, String> ICONS = new HashMap();
    private static final Map<String, String> PICTURES;
    private int selected;
    private final PanelHost host;

    public static Identifier picture(String name) {
        String file = (String)PICTURES.get((Object)(name == null ? "" : name.toLowerCase(Locale.ROOT)));
        return file == null ? null : Identifier.fromNamespaceAndPath((String)"fwm", (String)("textures/gui/spawners/" + file + ".png"));
    }

    public SpawnerPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.spawner", new Object[0]);
    }

    private List<SpawnerData.Item> items() {
        SpawnerData data = Spawners.data();
        return data == null || data.items == null ? List.of() : data.items;
    }

    private List<SpawnerData.Item> ordered() {
        return SpawnerPanel.inShopOrder(this.items());
    }

    public static List<SpawnerData.Item> inShopOrder(List<SpawnerData.Item> items) {
        if (items.isEmpty()) {
            return items;
        }
        ArrayList out = new ArrayList();
        for (String key : PINNED) {
            for (SpawnerData.Item item : items) {
                if (!key.equalsIgnoreCase(item.name)) continue;
                out.add((Object)item);
            }
        }
        for (SpawnerData.Item item : items) {
            if (out.contains((Object)item)) continue;
            out.add((Object)item);
        }
        return out;
    }

    private int pinnedCount() {
        int count = 0;
        for (SpawnerData.Item item : this.items()) {
            for (String key : PINNED) {
                if (!key.equalsIgnoreCase(item.name)) continue;
                ++count;
            }
        }
        return count;
    }

    private SpawnerData.Item current() {
        List<SpawnerData.Item> items = this.ordered();
        if (items.isEmpty()) {
            return null;
        }
        return (SpawnerData.Item)items.get(Math.min((int)this.selected, (int)(items.size() - 1)));
    }

    private int tableX() {
        return this.x + 52 + 5;
    }

    private int tableW() {
        return this.x + this.width - this.tableX();
    }

    private int sellRight() {
        return this.tableX() + this.tableW() - 2;
    }

    private int buyRight() {
        return this.sellRight() - 52;
    }

    private int rowY(int index) {
        int gap = index >= this.pinnedCount() ? 6 : 0;
        return this.y + 11 + index * 15 + gap;
    }

    private int buttonY() {
        return this.y + this.height - 16;
    }

    private int buttonX() {
        return this.x + this.width - 118;
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        if (this.items().isEmpty()) {
            MutableComponent empty = Lang.of(Spawners.state() == Spawners.State.ERROR ? "fwm.board.offline" : "fwm.board.loading", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.y + this.height / 2 - 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        this.renderPreview(ctx, font);
        this.renderTable(ctx, font, mouseX, mouseY);
        this.renderButton(ctx, font, mouseX, mouseY);
    }

    private void renderPreview(GuiGraphicsExtractor ctx, Font font) {
        int h = this.buttonY() - this.y - 2;
        FwmTheme.box(ctx, this.x, this.y, 52, h, FwmTheme.INSET, FwmTheme.EDGE);
        SpawnerData.Item item = this.current();
        if (item == null) {
            return;
        }
        Identifier texture = SpawnerPanel.picture(item.name);
        if (texture != null) {
            FwmGfx.blit(ctx, texture, this.x + 26 - 24, this.y + h / 2 - 40, 48, 48, 64, 64, 64);
            MutableComponent name = Component.literal((String)font.plainSubstrByWidth(item.name, 46));
            ctx.text(font, (Component)name, this.x + (52 - font.width((FormattedText)name)) / 2, this.y + h / 2 + 12, FwmTheme.TEXT, false);
            return;
        }
        ItemStack stack = this.icon(item.name);
        if (stack != null && !stack.isEmpty()) {
            Matrix3x2fStack matrices = ctx.pose();
            matrices.pushMatrix();
            matrices.translate((float)this.x + 26.0f - 16.0f, (float)this.y + (float)h / 2.0f - 24.0f);
            matrices.scale(2.0f, 2.0f);
            ctx.item(stack, 0, 0);
            matrices.popMatrix();
        }
        MutableComponent name = Component.literal((String)font.plainSubstrByWidth(item.name, 46));
        ctx.text(font, (Component)name, this.x + (52 - font.width((FormattedText)name)) / 2, this.y + h / 2 + 12, FwmTheme.TEXT, false);
    }

    private ItemStack icon(String name) {
        String key = name == null ? "" : name.toLowerCase(Locale.ROOT);
        String id = (String)ICONS.getOrDefault((Object)key, (Object)("minecraft:" + key + "_spawn_egg"));
        ItemStack stack = this.stackOf(id);
        return stack != null ? stack : this.stackOf("minecraft:spawner");
    }

    private ItemStack stackOf(String id) {
        try {
            Identifier identifier = Identifier.tryParse((String)id);
            if (identifier == null) {
                return null;
            }
            return (ItemStack)BuiltInRegistries.ITEM.getOptional(identifier).map(ItemStack::new).orElse(null);
        }
        catch (Exception e) {
            return null;
        }
    }

    private void renderTable(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int ry;
        this.columnHeader(ctx, font, Lang.str("fwm.spawner.header", new Object[0]), this.tableX() + 5, false, FwmTheme.ACCENT);
        this.columnHeader(ctx, font, Lang.str("fwm.spawner.buy", new Object[0]), this.buyRight(), true, -10688364);
        this.columnHeader(ctx, font, Lang.str("fwm.spawner.sell", new Object[0]), this.sellRight(), true, -2067340);
        List<SpawnerData.Item> items = this.ordered();
        for (int i = 0; i < items.size() && (ry = this.rowY(i)) + 15 <= this.buttonY() - 2; ++i) {
            this.renderRow(ctx, font, (SpawnerData.Item)items.get(i), i, ry, mouseX, mouseY);
        }
    }

    private void columnHeader(GuiGraphicsExtractor ctx, Font font, String text, int at, boolean rightAligned, int color) {
        MutableComponent shown = Component.literal((String)text);
        ctx.text(font, (Component)shown, rightAligned ? at - font.width((FormattedText)shown) : at, this.y + 1, color, false);
    }

    private void renderRow(GuiGraphicsExtractor ctx, Font font, SpawnerData.Item item, int index, int ry, int mouseX, int mouseY) {
        boolean hovered;
        boolean active = index == Math.min((int)this.selected, (int)(this.ordered().size() - 1));
        boolean bl = hovered = mouseX >= this.tableX() && mouseX < this.x + this.width && mouseY >= ry && mouseY < ry + 15;
        if (active) {
            ctx.fill(this.tableX(), ry, this.x + this.width, ry + 15, FwmTheme.HOVER);
            ctx.fill(this.tableX(), ry, this.tableX() + 2, ry + 15, FwmTheme.ACCENT);
        } else if (hovered) {
            ctx.fill(this.tableX(), ry, this.x + this.width, ry + 15, FwmTheme.HOVER);
        }
        Objects.requireNonNull((Object)font);
        int textY = ry + (15 - 9) / 2 + 1;
        MutableComponent name = Component.literal((String)font.plainSubstrByWidth(item.name, Math.max((int)8, (int)(this.buyRight() - this.tableX() - 12))));
        ctx.text(font, (Component)name, this.tableX() + 5, textY, active ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
        this.price(ctx, font, this.buyRight(), textY, item.buyPrice, -10688364);
        this.price(ctx, font, this.sellRight(), textY, item.tradedBack() ? item.sellPrice : "-", item.tradedBack() ? -2067340 : FwmTheme.OFF);
        FwmTheme.divider(ctx, this.tableX(), ry + 15 - 1, this.tableW());
    }

    private void price(GuiGraphicsExtractor ctx, Font font, int right, int textY, String value, int color) {
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(value == null ? "?" : value, 48));
        ctx.text(font, (Component)shown, right - font.width((FormattedText)shown), textY, color, false);
    }

    private void renderButton(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        boolean hovered = this.overButton(mouseX, mouseY);
        FwmTheme.box(ctx, this.buttonX(), this.buttonY(), 118, 16, FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = Lang.of("fwm.spawner.trade", new Object[0]);
        MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label.getString(), 112));
        int n = this.buttonX() + (118 - font.width((FormattedText)shown)) / 2;
        int n2 = this.buttonY();
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)shown, n, n2 + (16 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
    }

    private boolean overButton(double mouseX, double mouseY) {
        return mouseX >= (double)this.buttonX() && mouseX < (double)(this.buttonX() + 118) && mouseY >= (double)this.buttonY() && mouseY < (double)(this.buttonY() + 16);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        if (this.overButton(mouseX, mouseY)) {
            List<SpawnerData.Item> list = this.ordered();
            String name = list.isEmpty() ? null : ((SpawnerData.Item)list.get((int)Math.min((int)this.selected, (int)(list.size() - 1)))).name;
            this.host.openSub(new TicketsPanel(this.host, name));
            return true;
        }
        if (mouseX >= (double)this.tableX()) {
            for (int i = 0; i < this.ordered().size(); ++i) {
                if (!(mouseY >= (double)this.rowY(i)) || !(mouseY < (double)(this.rowY(i) + 15))) continue;
                this.selected = i;
                return true;
            }
        }
        return true;
    }

    private void openDiscord() {
        Minecraft client = Minecraft.getInstance();
        ConfirmLinkScreen.confirmLinkNow((Screen)client.gui.screen(), (URI)URI.create((String)FwmConfig.INSTANCE.discordUrl));
    }

    static {
        ICONS.put((Object)"iron", (Object)"minecraft:iron_block");
        ICONS.put((Object)"skelly", (Object)"minecraft:skeleton_spawn_egg");
        PICTURES = new HashMap();
        for (String[] pair : new String[][]{{"skelly", "skeleton"}, {"skeleton", "skeleton"}, {"skelett", "skeleton"}, {"creeper", "creeper"}, {"iron", "iron_golem"}, {"iron_golem", "iron_golem"}, {"eisengolem", "iron_golem"}, {"blaze", "blaze"}, {"lohe", "blaze"}, {"spider", "spider"}, {"spinne", "spider"}, {"cow", "cow"}, {"kuh", "cow"}, {"zombie_piglin", "zombie_piglin"}, {"piglin", "zombie_piglin"}, {"zombie-piglin", "zombie_piglin"}}) {
            PICTURES.put((Object)pair[0], (Object)pair[1]);
        }
    }
}
