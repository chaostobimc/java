/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Double
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.core.component.DataComponents
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.inventory.Slot
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.item.component.ItemLore
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.message.v1.ClientSendMessageEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.friendswithmoney.fwm.auction.Money;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.mixin.HandledScreenAccessor;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.net.PricePings;
import net.friendswithmoney.fwm.shop.MarketKey;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemLore;
import org.joml.Matrix3x2fStack;

public final class AlertSidePanel {
    private static final Pattern AH = Pattern.compile((String)"^(ah|auction|auctions|auctionhouse)(\\s.*)?$", (int)2);
    private static final Pattern ORDER = Pattern.compile((String)"^(order|orders)(\\s.*)?$", (int)2);
    private static final long WINDOW_MS = 3000L;
    private static final int W = 150;
    private static final int PAD = 8;
    private static final int CARD = 1544960042;
    private static final int CARD_HOVER = 2048870964;
    private static final int EDGE = 0x1FFFFFFF;
    private static final int EDGE_HOVER = 1717364884;
    private static final int WHITE = -1;
    private static final int SOFT = -5846860;
    private static final int DIM = -9464447;
    private static final int GREEN = -12136081;
    private static final int GREEN_DARK = -16045545;
    private static final int RED = -1017230;
    private static final int GOLD = -865972;
    private static final List<Hit> hits = new ArrayList();
    private static Kind pendingKind = Kind.NONE;
    private static long pendingUntil;
    private static String ahTitle;
    private static String orderTitle;
    private static float scale;
    private static int originX;
    private static int originY;
    private static int boxH;
    private static boolean picking;
    private static String pickedKey;
    private static String pickedName;
    private static String price;
    private static Boolean pickBelow;
    private static boolean pickRepeat;
    private static boolean pickToast;
    private static boolean pickChat;
    private static int drawnW;
    private static final int KNOB = 20;
    private static boolean typing;
    private static String note;
    private static final Pattern DOLLAR;

    private AlertSidePanel() {
    }

    public static void register() {
        ClientSendMessageEvents.COMMAND.register(command -> {
            if (command == null) {
                return;
            }
            String c = command.trim();
            if (AH.matcher((CharSequence)c).matches()) {
                AlertSidePanel.set(Kind.AH);
            } else if (ORDER.matcher((CharSequence)c).matches()) {
                AlertSidePanel.set(Kind.ORDER);
            }
        });
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof AbstractContainerScreen)) {
                return;
            }
            AbstractContainerScreen handled = (AbstractContainerScreen)screen;
            String title = screen.getTitle().getString();
            if (System.currentTimeMillis() < pendingUntil) {
                if (pendingKind == Kind.AH) {
                    ahTitle = title;
                }
                if (pendingKind == Kind.ORDER) {
                    orderTitle = title;
                }
                pendingUntil = 0L;
            }
            if (AlertSidePanel.kind(handled) == Kind.NONE) {
                return;
            }
            ScreenEvents.afterExtract((Screen)screen).register((s, ctx, mouseX, mouseY, delta) -> AlertSidePanel.render(client, handled, ctx, mouseX, mouseY));
            ScreenMouseEvents.allowMouseClick((Screen)screen).register((s, click) -> !AlertSidePanel.click(handled, click.x(), click.y(), click.button()));
            ScreenKeyboardEvents.allowKeyPress((Screen)screen).register((s, input) -> !AlertSidePanel.key(input.key()));
            ScreenEvents.remove((Screen)screen).register(s -> {
                typing = false;
                picking = false;
            });
        });
    }

    private static void set(Kind kind) {
        pendingKind = kind;
        pendingUntil = System.currentTimeMillis() + 3000L;
    }

    public static boolean isMarketMenu(AbstractContainerScreen<?> screen) {
        return AlertSidePanel.kind(screen) != Kind.NONE;
    }

    private static Kind kind(AbstractContainerScreen<?> screen) {
        if (!Features.isOn("fwm.feature.priceping") && !Features.canSwitch("fwm.feature.priceping")) {
            return Kind.NONE;
        }
        String title = screen.getTitle().getString();
        if (ahTitle != null && ahTitle.equals((Object)title)) {
            return Kind.AH;
        }
        if (orderTitle != null && orderTitle.equals((Object)title)) {
            return Kind.ORDER;
        }
        return Kind.NONE;
    }

    private static void render(Minecraft client, AbstractContainerScreen<?> screen, GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        boolean order;
        hits.clear();
        Kind kind = AlertSidePanel.kind(screen);
        if (kind == Kind.NONE || !FwmConfig.INSTANCE.marketAlertPanel || client.player == null) {
            return;
        }
        HandledScreenAccessor at = (HandledScreenAccessor)screen;
        int room = at.fwm$x() - 6 - 4;
        if (room < 70) {
            return;
        }
        boolean bl = order = kind == Kind.ORDER;
        if (FwmConfig.INSTANCE.pricePingCollapsed) {
            scale = 1.0f;
            drawnW = 20;
            boxH = 20;
            originX = at.fwm$x() - 6 - 20;
            originY = Math.max((int)4, (int)at.fwm$y());
            AlertSidePanel.knob(ctx, client.font, order, mouseX - originX, mouseY - originY);
            return;
        }
        drawnW = 150;
        boxH = AlertSidePanel.height(order);
        scale = Math.min((float)1.0f, (float)Math.min((float)((float)room / 150.0f), (float)((float)(screen.height - 8) / (float)boxH)));
        originX = at.fwm$x() - 6 - Math.round((float)(150.0f * scale));
        originY = Math.max((int)4, (int)Math.min((int)at.fwm$y(), (int)(screen.height - 4 - Math.round((float)((float)boxH * scale)))));
        int mx = (int)Math.floor((double)((float)(mouseX - originX) / scale));
        int my = (int)Math.floor((double)((float)(mouseY - originY) / scale));
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)originX, (float)originY);
        m.scale(scale, scale);
        AlertSidePanel.draw(ctx, client.font, order, mx, my);
        m.popMatrix();
    }

    private static List<FwmConfig.PricePing> pings(boolean order) {
        ArrayList out = new ArrayList();
        for (FwmConfig.PricePing p : PricePings.list()) {
            if (p.order != order) continue;
            out.add((Object)p);
        }
        return out;
    }

    private static int height(boolean order) {
        return 222 + Math.max((int)1, (int)AlertSidePanel.pings(order).size()) * 15;
    }

    private static void knob(GuiGraphicsExtractor ctx, Font font, boolean order, int mx, int my) {
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)originX, (float)originY);
        boolean over = AlertSidePanel.inside(mx, my, 0, 0, 20, 20);
        LauncherGfx.round(ctx, 0, 0, 20, 20, 10, over ? 2048870964 : -536078574);
        LauncherGfx.ring(ctx, 0, 0, 20, 20, 10, over ? 1717364884 : 0x1FFFFFFF);
        ctx.fill(9, 4, 11, 6, -1);
        ctx.fill(7, 6, 13, 7, -1);
        ctx.fill(6, 7, 7, 13, -1);
        ctx.fill(13, 7, 14, 13, -1);
        ctx.fill(5, 13, 15, 14, -1);
        ctx.fill(9, 15, 11, 16, -1);
        int active = 0;
        for (FwmConfig.PricePing p : AlertSidePanel.pings(order)) {
            if (p.fired) continue;
            ++active;
        }
        if (active > 0) {
            String n = String.valueOf((int)active);
            int bw = Math.max((int)8, (int)(font.width(n) + 3));
            LauncherGfx.round(ctx, 20 - bw + 2, -3, bw, 9, 4, -2075843);
            LauncherGfx.text(ctx, font, n, 20 - bw + 2 + (bw - font.width(n)) / 2, -2, -1);
        }
        m.popMatrix();
        hits.add((Object)new Hit(0, 0, 20, 20, () -> {
            FwmConfig.INSTANCE.pricePingCollapsed = false;
            ConfigManager.save();
        }));
    }

    private static void draw(GuiGraphicsExtractor ctx, Font font, boolean order, int mx, int my) {
        LauncherGfx.round(ctx, 0, 0, 150, boxH, 8, -536078574);
        LauncherGfx.card(ctx, 0, 0, 150, boxH, 8);
        int x = 8;
        int cw = 134;
        int y = 8;
        LauncherGfx.bold(ctx, font, order ? Lang.t("Order-Wecker", "Order alert") : Lang.t("AH-Wecker", "AH alert"), x, y + 2, -1);
        boolean overHide = AlertSidePanel.inside(mx, my, 131, y, 12, 12);
        LauncherGfx.text(ctx, font, "\u00ab", 134, y + 2, overHide ? -12136081 : -9464447);
        hits.add((Object)new Hit(131, y, 12, 12, () -> {
            FwmConfig.INSTANCE.pricePingCollapsed = true;
            typing = false;
            picking = false;
            ConfigManager.save();
        }));
        y += 16;
        if (!Features.isOn("fwm.feature.priceping")) {
            LauncherGfx.text(ctx, font, Lang.t("Wecker sind aus.", "Alerts are off."), x, y + 2, -1017230);
            AlertSidePanel.button(ctx, font, x, y += 14, cw, 16, Lang.t("Einschalten", "Switch on"), true, mx, my, () -> Features.set("fwm.feature.priceping", true));
            return;
        }
        LauncherGfx.text(ctx, font, Lang.t("DEINE PINGS", "YOUR PINGS"), x, y, -9464447);
        y += 11;
        List<FwmConfig.PricePing> list = AlertSidePanel.pings(order);
        if (list.isEmpty()) {
            LauncherGfx.text(ctx, font, Lang.t("noch keiner", "none yet"), x, y + 3, -9464447);
            y += 15;
        }
        for (FwmConfig.PricePing p : list) {
            String value = p.fired ? Lang.t("ausgel\u00f6st", "done") : (p.below() ? "\u2264 " : "\u2265 ") + Fmt.compactMoney(Math.round((double)(p.maxPrice * 100.0))) + (p.repeat ? " \u21bb" : "");
            boolean overX = AlertSidePanel.inside(mx, my, x + cw - 10, y, 10, 13);
            LauncherGfx.round(ctx, x, y, cw, 13, 5, 1544960042);
            LauncherGfx.text(ctx, font, LauncherGfx.trim(font, p.name == null ? p.itemKey : p.name, cw - 24 - font.width(value)), x + 4, y + 3, p.fired ? -9464447 : -1);
            LauncherGfx.text(ctx, font, value, x + cw - 14 - font.width(value), y + 3, p.fired ? -9464447 : -865972);
            LauncherGfx.text(ctx, font, "\u00d7", x + cw - 8, y + 3, overX ? -1017230 : -9464447);
            String key = p.itemKey;
            hits.add((Object)new Hit(x + cw - 10, y, 10, 13, () -> PricePings.remove(key, order)));
            y += 15;
        }
        LauncherGfx.text(ctx, font, Lang.t("NEUER PING", "NEW PING"), x, y += 4, -9464447);
        String itemLabel = picking ? Lang.t("Jetzt ein Item anklicken \u2026", "Now click an item \u2026") : (pickedName != null ? pickedName : Lang.t("+ Ping", "+ Ping"));
        AlertSidePanel.tile(ctx, font, x, y += 11, cw, 14, itemLabel, picking ? "" : "\u25b8", picking, mx, my, () -> {
            picking = !picking;
            typing = false;
        });
        boolean below = pickBelow != null ? pickBelow : !order;
        AlertSidePanel.tile(ctx, font, x, y += 18, cw, 14, below ? Lang.t("g\u00fcnstiger als", "cheaper than") : Lang.t("h\u00f6her als", "more than"), Lang.t("niedriger / h\u00f6her", "lower / higher"), false, mx, my, () -> {
            pickBelow = !(pickBelow == null ? !order : pickBelow != false);
        });
        boolean over = AlertSidePanel.inside(mx, my, x, y += 18, cw, 14);
        LauncherGfx.round(ctx, x, y, cw, 14, 5, typing ? 2048870964 : 1544960042);
        LauncherGfx.ring(ctx, x, y, cw, 14, 5, typing || over ? 1717364884 : 0x1FFFFFFF);
        String shown = price.isEmpty() && !typing ? Lang.t("Preis, z. B. 500k", "Price, e.g. 500k") : price + (typing && System.currentTimeMillis() / 500L % 2L == 0L ? "_" : "");
        LauncherGfx.text(ctx, font, shown, x + 5, y + 3, price.isEmpty() && !typing ? -9464447 : -1);
        hits.add((Object)new Hit(x, y, cw, 14, () -> {
            typing = true;
            picking = false;
        }));
        int half = (cw - 3) / 2;
        AlertSidePanel.chip(ctx, font, x, y += 18, half, Lang.t("einmal", "once"), !pickRepeat, mx, my, () -> {
            pickRepeat = false;
        });
        AlertSidePanel.chip(ctx, font, x + half + 3, y, cw - half - 3, Lang.t("mehrmals", "every time"), pickRepeat, mx, my, () -> {
            pickRepeat = true;
        });
        AlertSidePanel.chip(ctx, font, x, y += 16, half, (pickToast ? "\u2714 " : "\u2715 ") + Lang.t("Mitteilung", "Toast"), pickToast, mx, my, () -> {
            pickToast = !pickToast;
        });
        AlertSidePanel.chip(ctx, font, x + half + 3, y, cw - half - 3, (pickChat ? "\u2714 " : "\u2715 ") + "Chat", pickChat, mx, my, () -> {
            pickChat = !pickChat;
        });
        AlertSidePanel.button(ctx, font, x, y += 18, cw, 16, Lang.t("Setzen", "Set"), true, mx, my, () -> AlertSidePanel.add(order));
        y += 20;
        if (!note.isEmpty()) {
            LauncherGfx.text(ctx, font, LauncherGfx.trim(font, note, cw), x, y, note.startsWith("\u2714") ? -12136081 : -1017230);
            y += 11;
        }
        LauncherGfx.text(ctx, font, LauncherGfx.trim(font, Lang.t("\u201emehrmals\u201c: jedes neue Angebot", "\"every time\": each new offer"), cw), x, y, -9464447);
        LauncherGfx.text(ctx, font, LauncherGfx.trim(font, Lang.t("genau einmal.", "exactly once."), cw), x, y + 10, -9464447);
    }

    private static void chip(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, String label, boolean on, int mx, int my, Runnable action) {
        boolean over = AlertSidePanel.inside(mx, my, x, y, w, 13);
        LauncherGfx.round(ctx, x, y, w, 13, 5, on ? 2048870964 : 1544960042);
        LauncherGfx.ring(ctx, x, y, w, 13, 5, on || over ? 1717364884 : 0x1FFFFFFF);
        String t = LauncherGfx.trim(font, label, w - 6);
        LauncherGfx.text(ctx, font, t, x + (w - font.width(t)) / 2, y + 3, on ? -12136081 : -5846860);
        hits.add((Object)new Hit(x, y, w, 13, action));
    }

    private static void add(boolean order) {
        boolean below;
        if (pickedKey == null) {
            note = Lang.t("Erst ein Item w\u00e4hlen.", "Pick an item first.");
            return;
        }
        double value = PricePings.parsePrice(price);
        if (Double.isNaN((double)value)) {
            note = Lang.t("Preis ung\u00fcltig (z. B. 500k).", "Price not valid (e.g. 500k).");
            return;
        }
        MarketData.Item item = new MarketData.Item();
        item.itemKey = pickedKey;
        int hash = pickedKey.indexOf(35);
        item.baseItemKey = hash > 0 ? pickedKey.substring(0, hash) : pickedKey;
        item.displayName = pickedName;
        boolean bl = pickBelow != null ? pickBelow : (below = !order);
        if (!PricePings.set(item, pickedName, value, order, below, pickRepeat, pickToast, pickChat)) {
            note = Lang.t("H\u00f6chstens 10 Wecker.", "At most 10 alerts.");
            return;
        }
        note = "\u2714 " + pickedName + " \u00b7 " + Fmt.compactMoney(Math.round((double)(value * 100.0)));
        pickedKey = null;
        pickedName = null;
        price = "";
        pickBelow = null;
        typing = false;
    }

    private static String offerPrice(ItemStack stack) {
        ItemLore lore = (ItemLore)stack.get(DataComponents.LORE);
        if (lore == null) {
            return "";
        }
        long first = -1L;
        for (Component line : lore.lines()) {
            long cents;
            String plain = ServerLine.strip(line.getString());
            Matcher m = DOLLAR.matcher((CharSequence)plain);
            if (!m.find() || (cents = Money.cents(m.group(1) + (m.group(2) == null ? "" : m.group(2)))) <= 0L) continue;
            if (plain.toLowerCase(Locale.ROOT).contains((CharSequence)"pro ")) {
                return String.valueOf((long)(cents / 100L));
            }
            if (first >= 0L) continue;
            first = cents;
        }
        if (first < 0L) {
            return "";
        }
        return String.valueOf((long)(first / 100L / (long)Math.max((int)1, (int)stack.getCount())));
    }

    private static void button(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, int h, String label, boolean primary, int mx, int my, Runnable action) {
        boolean over = AlertSidePanel.inside(mx, my, x, y, w, h);
        if (primary && !over) {
            LauncherGfx.round(ctx, x, y, w, h, 6, -218103809);
        } else {
            LauncherGfx.round(ctx, x, y, w, h, 6, over ? 2048870964 : 1544960042);
            LauncherGfx.ring(ctx, x, y, w, h, 6, over ? 1717364884 : 0x1FFFFFFF);
        }
        String shown = LauncherGfx.trim(font, label, w - 8);
        LauncherGfx.text(ctx, font, shown, x + (w - font.width(shown)) / 2, y + (h - 8) / 2, primary && !over ? -16045545 : -1);
        hits.add((Object)new Hit(x, y, w, h, action));
    }

    private static void tile(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, int h, String label, String value, boolean active, int mx, int my, Runnable action) {
        boolean over = AlertSidePanel.inside(mx, my, x, y, w, h);
        LauncherGfx.round(ctx, x, y, w, h, 5, over ? 2048870964 : 1544960042);
        LauncherGfx.ring(ctx, x, y, w, h, 5, over || active ? 1717364884 : 0x1FFFFFFF);
        LauncherGfx.text(ctx, font, LauncherGfx.trim(font, label, w - 12 - font.width(value)), x + 5, y + (h - 8) / 2, active ? -865972 : -1);
        LauncherGfx.text(ctx, font, value, x + w - 5 - font.width(value), y + (h - 8) / 2, -5846860);
        hits.add((Object)new Hit(x, y, w, h, action));
    }

    private static boolean inside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static boolean click(AbstractContainerScreen<?> screen, double mouseX, double mouseY, int button) {
        Slot slot;
        if (AlertSidePanel.kind(screen) == Kind.NONE || !FwmConfig.INSTANCE.marketAlertPanel) {
            return false;
        }
        if (picking && (slot = ((HandledScreenAccessor)screen).fwm$focusedSlot()) != null && slot.hasItem()) {
            ItemStack stack = slot.getItem();
            String key = MarketKey.of(stack);
            if (key == null) {
                note = Lang.t("F\u00fcr dieses Item gibt es keinen Marktpreis.", "No market price for this item.");
            } else {
                pickedKey = key;
                pickedName = ServerLine.strip(stack.getHoverName().getString());
                price = AlertSidePanel.offerPrice(stack);
                note = "";
                typing = true;
            }
            picking = false;
            return true;
        }
        if (button != 0 || hits.isEmpty()) {
            return AlertSidePanel.insideBox(mouseX, mouseY);
        }
        int mx = (int)Math.floor((double)((mouseX - (double)originX) / (double)scale));
        int my = (int)Math.floor((double)((mouseY - (double)originY) / (double)scale));
        for (Hit hit : new ArrayList(hits)) {
            if (!AlertSidePanel.inside(mx, my, hit.x(), hit.y(), hit.w(), hit.h())) continue;
            hit.action().run();
            return true;
        }
        if (AlertSidePanel.insideBox(mouseX, mouseY)) {
            return true;
        }
        typing = false;
        return false;
    }

    private static boolean insideBox(double mouseX, double mouseY) {
        int mx = (int)Math.floor((double)((mouseX - (double)originX) / (double)scale));
        int my = (int)Math.floor((double)((mouseY - (double)originY) / (double)scale));
        return mx >= 0 && my >= 0 && mx < drawnW && my < boxH;
    }

    private static boolean key(int code) {
        if (!typing) {
            return false;
        }
        if (code == 41) {
            typing = false;
            return true;
        }
        if (code == 40 || code == 88) {
            typing = false;
            return true;
        }
        if (code == 42) {
            if (!price.isEmpty()) {
                price = price.substring(0, price.length() - 1);
            }
            return true;
        }
        String add = null;
        if (code >= 48 && code <= 57) {
            add = String.valueOf((char)((char)(48 + code - 48)));
        } else if (code >= 320 && code <= 329) {
            add = String.valueOf((char)((char)(48 + code - 320)));
        } else if (code == 14) {
            add = "k";
        } else if (code == 16) {
            add = "m";
        } else if (code == 5) {
            add = "b";
        } else if (code == 46 || code == 330) {
            add = ".";
        } else if (code == 44) {
            add = ",";
        }
        if (add != null && price.length() < 14) {
            price = (price + add).toLowerCase(Locale.ROOT);
        }
        return true;
    }

    static {
        scale = 1.0f;
        price = "";
        pickToast = true;
        drawnW = 150;
        note = "";
        DOLLAR = Pattern.compile((String)"\\$\\s*([0-9][0-9.,]*)\\s*([kKmMbB])?");
    }

    private static enum Kind {
        NONE,
        AH,
        ORDER;

    }

    private record Hit(int x, int y, int w, int h, Runnable action) {
    }
}
