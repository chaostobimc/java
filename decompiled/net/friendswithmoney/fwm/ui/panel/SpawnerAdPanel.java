/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Character
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.friendswithmoney.fwm.auction.Money;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.friendswithmoney.fwm.net.Spawners;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.SpawnerAdTextsPanel;
import net.friendswithmoney.fwm.ui.panel.SpawnerPanel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class SpawnerAdPanel
extends Panel {
    private static final int LIST_W = 64;
    private static final int GAP = 6;
    private static final int ROW_H = 13;
    private static final int FIELD_H = 14;
    private static final int LABEL_H = 10;
    private static final int MAX_CHAT = 256;
    private final PanelHost host;
    private int selected;
    private EditBox buyField;
    private EditBox sellField;
    private EditBox amountField;
    private String shownName;
    private int fieldY;
    private int fieldW;
    private int listTop;

    public SpawnerAdPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.spawnerad.title", new Object[0]);
    }

    private List<SpawnerData.Item> items() {
        SpawnerData data = Spawners.data();
        List<SpawnerData.Item> items = data == null || data.items == null ? List.of() : data.items;
        return SpawnerPanel.inShopOrder(items);
    }

    private SpawnerData.Item current() {
        List<SpawnerData.Item> items = this.items();
        if (items.isEmpty()) {
            return null;
        }
        return (SpawnerData.Item)items.get(Math.min((int)this.selected, (int)(items.size() - 1)));
    }

    private int rightX() {
        return this.x + 64 + 6;
    }

    private int rightW() {
        return this.x + this.width - this.rightX();
    }

    @Override
    protected void onResize() {
        this.store();
        this.detachFields();
        Font font = Minecraft.getInstance().font;
        this.fieldY = this.y + 14 + 10;
        this.fieldW = (this.rightW() - 12) / 3;
        this.buyField = this.field(font, 0);
        this.sellField = this.field(font, 1);
        this.amountField = this.field(font, 2);
        this.load();
    }

    private EditBox field(Font font, int column) {
        EditBox field = new EditBox(font, this.rightX() + column * (this.fieldW + 6) + 4, this.fieldY + 3, this.fieldW - 8, 10, (Component)Lang.of("fwm.spawnerad.field", new Object[0]));
        field.setBordered(false);
        field.setMaxLength(12);
        field.setTextColor(FwmTheme.TEXT);
        this.host.attach((AbstractWidget)field);
        return field;
    }

    private void detachFields() {
        if (this.buyField != null) {
            this.host.detach((AbstractWidget)this.buyField);
        }
        if (this.sellField != null) {
            this.host.detach((AbstractWidget)this.sellField);
        }
        if (this.amountField != null) {
            this.host.detach((AbstractWidget)this.amountField);
        }
    }

    private void load() {
        SpawnerData.Item item = this.current();
        String string = this.shownName = item == null ? null : item.name;
        if (item == null || this.buyField == null) {
            return;
        }
        FwmConfig.SpawnerAdPrice saved = (FwmConfig.SpawnerAdPrice)FwmConfig.INSTANCE.spawnerAdPrices.get((Object)SpawnerAdPanel.key(item.name));
        this.buyField.setValue(saved != null ? saved.buy : SpawnerAdPanel.nonZero(item.buyPrice));
        this.sellField.setValue(saved != null ? saved.sell : (item.tradedBack() ? item.sellPrice : ""));
        this.amountField.setValue(saved != null ? saved.amount : "");
    }

    private void store() {
        if (this.shownName == null || this.buyField == null) {
            return;
        }
        FwmConfig.SpawnerAdPrice price = new FwmConfig.SpawnerAdPrice();
        price.buy = this.buyField.getValue().trim();
        price.sell = this.sellField.getValue().trim();
        price.amount = this.amountField.getValue().trim();
        FwmConfig.INSTANCE.spawnerAdPrices.put((Object)SpawnerAdPanel.key(this.shownName), (Object)price);
    }

    private static String key(String name) {
        return name.toLowerCase(Locale.ROOT);
    }

    private static String nonZero(String price) {
        return price == null || "0".equals((Object)price.trim()) ? "" : price.trim();
    }

    private String fill(String template, SpawnerData.Item item) {
        String amount = this.amountField.getValue().isBlank() ? "?" : this.amountField.getValue().trim();
        String text = template.replace((CharSequence)"[Name]", (CharSequence)FwmGfx.playerName()).replace((CharSequence)"[Spawner]", (CharSequence)SpawnerAdPanel.plural(item.name)).replace((CharSequence)"[Ankauf Preis]", (CharSequence)SpawnerAdPanel.price(this.buyField.getValue())).replace((CharSequence)"[Verkauf Preis]", (CharSequence)SpawnerAdPanel.price(this.sellField.getValue())).replace((CharSequence)"[Anzahl]", (CharSequence)amount).replace((CharSequence)"{name}", (CharSequence)FwmGfx.playerName()).replace((CharSequence)"{spawner}", (CharSequence)SpawnerAdPanel.plural(item.name)).replace((CharSequence)"{kauf}", (CharSequence)SpawnerAdPanel.price(this.buyField.getValue())).replace((CharSequence)"{verkauf}", (CharSequence)SpawnerAdPanel.price(this.sellField.getValue())).replace((CharSequence)"{anzahl}", (CharSequence)amount);
        return text.length() > 256 ? text.substring(0, 256) : text;
    }

    private static List<String> slots() {
        ArrayList out = new ArrayList();
        for (String text : FwmConfig.INSTANCE.spawnerAdTemplates) {
            if (text == null || text.isBlank()) continue;
            out.add((Object)text);
        }
        return out;
    }

    private static String plural(String name) {
        if (name == null || name.isEmpty()) {
            return "?";
        }
        String shown = Character.toUpperCase((char)name.charAt(0)) + name.substring(1);
        return shown.endsWith("s") ? shown : shown + "s";
    }

    static String price(String typed) {
        String text;
        String string = text = typed == null ? "" : typed.trim();
        if (text.isEmpty()) {
            return "?";
        }
        long dollars = Money.cents(text) / 100L;
        if (dollars <= 0L) {
            return text;
        }
        if (dollars >= 1000000000L) {
            return SpawnerAdPanel.oneDecimal((double)dollars / 1.0E9) + "B";
        }
        if (dollars >= 1000000L) {
            return SpawnerAdPanel.oneDecimal((double)dollars / 1000000.0) + "M";
        }
        if (dollars >= 1000L) {
            return SpawnerAdPanel.oneDecimal((double)dollars / 1000.0) + "K";
        }
        return String.valueOf((long)dollars);
    }

    private static String oneDecimal(double value) {
        return String.format((Locale)Locale.GERMANY, (String)"%.1f", (Object[])new Object[]{value});
    }

    private void putInChat(String message) {
        this.store();
        ConfigManager.save();
        Minecraft.getInstance().gui.setScreen((Screen)new ChatScreen(message, false));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        List<SpawnerData.Item> items = this.items();
        if (items.isEmpty()) {
            MutableComponent empty = Lang.of(Spawners.state() == Spawners.State.ERROR ? "fwm.board.offline" : "fwm.board.loading", new Object[0]);
            ctx.text(font, (Component)empty, this.x + (this.width - font.width((FormattedText)empty)) / 2, this.y + this.height / 2 - 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        if (this.shownName == null) {
            this.load();
        }
        this.renderList(ctx, font, items, mouseX, mouseY);
        this.renderFields(ctx, font, mouseX, mouseY, delta);
        this.renderMessages(ctx, font, mouseX, mouseY);
    }

    private void renderList(GuiGraphicsExtractor ctx, Font font, List<SpawnerData.Item> items, int mouseX, int mouseY) {
        int ry;
        ctx.text(font, (Component)Lang.of("fwm.spawner.header", new Object[0]), this.x + 4, this.y + 1, FwmTheme.ACCENT, false);
        this.listTop = this.y + 12;
        for (int i = 0; i < items.size() && (ry = this.listTop + i * 13) + 13 <= this.y + this.height; ++i) {
            boolean hovered;
            boolean active = i == Math.min((int)this.selected, (int)(items.size() - 1));
            boolean bl = hovered = mouseX >= this.x && mouseX < this.x + 64 && mouseY >= ry && mouseY < ry + 13;
            if (active || hovered) {
                ctx.fill(this.x, ry, this.x + 64, ry + 13, FwmTheme.HOVER);
            }
            if (active) {
                ctx.fill(this.x, ry, this.x + 2, ry + 13, FwmTheme.ACCENT);
            }
            MutableComponent name = Component.literal((String)font.plainSubstrByWidth(((SpawnerData.Item)items.get((int)i)).name, 56));
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)name, this.x + 5, ry + (13 - 9) / 2 + 1, active ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
        }
    }

    private void renderFields(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        SpawnerData.Item item = this.current();
        if (item == null) {
            return;
        }
        String site = Lang.str("fwm.spawnerad.site", SpawnerAdPanel.nonZero(item.buyPrice).isEmpty() ? "-" : item.buyPrice, item.tradedBack() ? item.sellPrice : "-");
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(site, this.rightW())), this.rightX(), this.y + 1, FwmTheme.TEXT_DIM, false);
        String[] labels = new String[]{"fwm.spawnerad.buy", "fwm.spawnerad.sell", "fwm.spawnerad.amount"};
        EditBox[] fields = new EditBox[]{this.buyField, this.sellField, this.amountField};
        for (int i = 0; i < 3; ++i) {
            int fx = this.rightX() + i * (this.fieldW + 6);
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str(labels[i], new Object[0]), this.fieldW)), fx, this.fieldY - 10, FwmTheme.ACCENT, false);
            FwmTheme.box(ctx, fx, this.fieldY, this.fieldW, 14, FwmTheme.INSET, fields[i].isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            fields[i].extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        }
    }

    private int messagesTop() {
        return this.fieldY + 14 + 6;
    }

    private int firstMessageY() {
        return this.messagesTop() + 20 + 2;
    }

    private static String sharedStart(List<String> messages) {
        if (messages.size() < 2) {
            return "";
        }
        String first = (String)messages.get(0);
        int length = first.length();
        for (String message : messages) {
            int same;
            for (same = 0; same < length && same < message.length() && message.charAt(same) == first.charAt(same); ++same) {
            }
            length = same;
        }
        int cut = first.lastIndexOf(" | ", length - 3);
        return cut < 0 ? "" : first.substring(0, cut + 3);
    }

    private void renderMessages(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int ry;
        SpawnerData.Item item = this.current();
        if (item == null) {
            return;
        }
        ArrayList messages = new ArrayList();
        for (String template : SpawnerAdPanel.slots()) {
            messages.add((Object)this.fill(template, item));
        }
        String shared = SpawnerAdPanel.sharedStart((List<String>)messages);
        int top = this.messagesTop();
        ctx.text(font, (Component)Lang.of("fwm.spawnerad.messages", new Object[0]), this.rightX(), top, FwmTheme.ACCENT, false);
        MutableComponent edit = Lang.of("fwm.spawnerad.edit", new Object[0]);
        int ex = this.x + this.width - font.width((FormattedText)edit);
        boolean overEdit = mouseX >= ex && mouseX < this.x + this.width && mouseY >= top - 1 && mouseY < top + 10;
        ctx.text(font, (Component)edit, ex, top, overEdit ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
        if (!shared.isEmpty()) {
            String start = font.plainSubstrByWidth(Lang.str("fwm.spawnerad.prefix", shared.trim()), this.rightW());
            ctx.text(font, (Component)Component.literal((String)start), this.rightX(), top + 10, FwmTheme.TEXT_DIM, false);
        }
        FwmTheme.divider(ctx, this.rightX(), this.firstMessageY() - 2, this.rightW());
        for (int i = 0; i < messages.size() && (ry = this.firstMessageY() + i * 13) + 13 <= this.y + this.height; ++i) {
            boolean hovered;
            boolean bl = hovered = mouseX >= this.rightX() && mouseX < this.x + this.width && mouseY >= ry && mouseY < ry + 13;
            if (hovered) {
                ctx.fill(this.rightX(), ry, this.x + this.width, ry + 13, FwmTheme.HOVER);
            }
            String tail = ((String)messages.get(i)).substring(shared.length());
            String shown = font.plainSubstrByWidth(tail, this.rightW() - 6);
            MutableComponent mutableComponent = Component.literal((String)shown);
            int n = this.rightX() + 3;
            Objects.requireNonNull((Object)font);
            ctx.text(font, (Component)mutableComponent, n, ry + (13 - 9) / 2 + 1, hovered ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        List<SpawnerData.Item> items = this.items();
        if (mouseX < (double)(this.x + 64) && mouseY >= (double)this.listTop) {
            int index = (int)((mouseY - (double)this.listTop) / 13.0);
            if (index >= 0 && index < items.size() && index != this.selected) {
                this.store();
                this.selected = index;
                this.load();
            }
            return true;
        }
        EditBox[] fields = new EditBox[]{this.buyField, this.sellField, this.amountField};
        if (mouseY >= (double)this.fieldY && mouseY < (double)(this.fieldY + 14)) {
            for (int i = 0; i < 3; ++i) {
                int fx = this.rightX() + i * (this.fieldW + 6);
                fields[i].setFocused(mouseX >= (double)fx && mouseX < (double)(fx + this.fieldW));
            }
            return true;
        }
        for (EditBox field : fields) {
            field.setFocused(false);
        }
        SpawnerData.Item item = this.current();
        int first = this.firstMessageY();
        Font font = Minecraft.getInstance().font;
        int ex = this.x + this.width - font.width((FormattedText)Lang.of("fwm.spawnerad.edit", new Object[0]));
        if (mouseX >= (double)ex && mouseY >= (double)(this.messagesTop() - 1) && mouseY < (double)(this.messagesTop() + 10)) {
            this.store();
            this.host.openSub(new SpawnerAdTextsPanel(this.host));
            return true;
        }
        if (item != null && mouseX >= (double)this.rightX() && mouseY >= (double)first) {
            int index = (int)((mouseY - (double)first) / 13.0);
            List<String> templates = SpawnerAdPanel.slots();
            if (index >= 0 && index < templates.size()) {
                this.putInChat(this.fill((String)templates.get(index), item));
            }
        }
        return true;
    }

    private EditBox focused() {
        for (EditBox field : new EditBox[]{this.buyField, this.sellField, this.amountField}) {
            if (field == null || !field.isFocused()) continue;
            return field;
        }
        return null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EditBox field = this.focused();
        if (field == null) {
            return false;
        }
        if (keyCode == 40 || keyCode == 88) {
            field.setFocused(false);
            this.store();
            return true;
        }
        return field.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        EditBox field = this.focused();
        return field != null && field.charTyped(input);
    }

    @Override
    public boolean isCapturingKeys() {
        return this.focused() != null;
    }

    @Override
    public void onHidden() {
        this.store();
        ConfigManager.save();
        this.detachFields();
    }
}
