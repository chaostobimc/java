/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.MatchException
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.time.Instant
 *  java.time.LocalDate
 *  java.time.ZoneId
 *  java.time.format.DateTimeFormatter
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.function.Consumer
 *  java.util.function.IntFunction
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

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.IntFunction;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.money.History;
import net.friendswithmoney.fwm.money.MoneyStore;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.ColorCodePanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.friendswithmoney.fwm.ui.panel.RowsPanel;
import net.friendswithmoney.fwm.ui.widget.ChipsRow;
import net.friendswithmoney.fwm.ui.widget.NumberRow;
import net.friendswithmoney.fwm.ui.widget.SliderRow;
import net.friendswithmoney.fwm.ui.widget.TextRow;
import net.friendswithmoney.fwm.ui.widget.ToggleRow;
import net.friendswithmoney.fwm.util.Fmt;
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

public final class MoneyPanels {
    private static String tip;

    private MoneyPanels() {
    }

    static String kindName(MoneyStore.Kind kind) {
        return switch (kind) {
            default -> throw new MatchException(null, null);
            case MoneyStore.Kind.SELL -> "Sell";
            case MoneyStore.Kind.PAY_IN -> Lang.t("/pay rein", "/pay in");
            case MoneyStore.Kind.PAY_OUT -> Lang.t("/pay raus", "/pay out");
            case MoneyStore.Kind.AH_SELL -> Lang.t("AH-Verkauf", "AH sale");
            case MoneyStore.Kind.AH_BUY -> Lang.t("AH-Kauf", "AH buy");
            case MoneyStore.Kind.OTHER_IN -> Lang.t("Sonstiges +", "Other +");
            case MoneyStore.Kind.OTHER_OUT -> Lang.t("Sonstiges \u2212", "Other \u2212");
            case MoneyStore.Kind.ORDER -> Lang.t("Order geliefert", "Order delivered");
            case MoneyStore.Kind.ORDER_BUY -> Lang.t("Order-Kauf", "Order buy");
            case MoneyStore.Kind.FEE -> Lang.t("Geb\u00fchr", "Fee");
        };
    }

    static String signed(long cents) {
        if (FwmConfig.INSTANCE.moneyHideAmounts) {
            return "***";
        }
        return (cents < 0L ? "-" : "+") + Fmt.shortMoney(Math.abs((long)cents));
    }

    static void amount(GuiGraphicsExtractor ctx, Font font, String text, long cents, int ax, int ay, int color, int mouseX, int mouseY) {
        ctx.text(font, (Component)Component.literal((String)text), ax, ay, color, false);
        if (!FwmConfig.INSTANCE.moneyHideAmounts && mouseX >= ax && mouseX < ax + font.width(text) && mouseY >= ay - 1 && mouseY < ay + 9) {
            tip = (cents < 0L ? "-" : "") + Fmt.fullMoney(Math.abs((long)cents));
        }
    }

    static void flushTip(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        if (tip == null) {
            return;
        }
        ColorCodePanel.tip(ctx, font, tip, mouseX, mouseY);
        tip = null;
    }

    static int color(long cents) {
        return cents < 0L ? -2067340 : (cents > 0L ? -9707377 : FwmTheme.TEXT_DIM);
    }

    public static class Settings
    extends RowsPanel {
        @Override
        public Component title() {
            return Lang.of("fwm.money.settings", new Object[0]);
        }

        @Override
        protected void buildRows() {
            FwmConfig c = FwmConfig.INSTANCE;
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Allgemein", "General"))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Geld aktiv (nur HugoSMP)", "Money on (HugoSMP only)")), () -> Features.isOn("fwm.tab.money"), (Consumer<Boolean>)((Consumer)v -> Features.set("fwm.tab.money", v))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("HUD-Karte", "HUD card")), () -> c.moneyHud, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyHud = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Betr\u00e4ge verbergen (Streamer)", "Hide amounts (streamer)")), () -> c.moneyHideAmounts, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyHideAmounts = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Mit Kontostand abgleichen", "Check against balance")), () -> c.moneySidebarSync, (Consumer<Boolean>)((Consumer)v -> {
                c.moneySidebarSync = v;
            })));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Tagesziel", "Daily goal"))));
            this.rows.add((Object)new NumberRow((Component)Component.literal((String)Lang.t("Ziel ($, 0 = aus)", "Goal ($, 0 = off)")), () -> c.moneyGoal, v -> {
                c.moneyGoal = Math.max((long)0L, (long)v);
            }, v -> Component.literal((String)(v <= 0L ? Lang.t("aus", "off") : Fmt.compactMoney(v * 100L)))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Ton, wenn erreicht", "Sound when reached")), () -> c.moneyGoalSound, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyGoalSound = v;
            })));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Verlauf", "History"))));
            this.rows.add((Object)new SliderRow((Component)Component.literal((String)Lang.t("Verlauf aufheben", "Keep history")), 0, 90, () -> c.historyEnabled ? c.historyDays : 0, v -> {
                boolean bl = c.historyEnabled = v > 0;
                if (v > 0) {
                    c.historyDays = v;
                }
            }, (IntFunction<Component>)((IntFunction)v -> Component.literal((String)(v <= 0 ? Lang.t("aus", "off") : v + Lang.t(" Tage", " days"))))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Sells zusammenfassen", "Merge sells")), () -> c.historyMergeSell, (Consumer<Boolean>)((Consumer)v -> {
                c.historyMergeSell = v;
            })));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Inventarwert", "Inventory value"))));
            this.rows.add((Object)new ChipsRow((Component)Component.literal((String)Lang.t("Anzeigen", "Show")), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip(Lang.t("Aus", "Off"), 0)), (Object)((Object)new ChipsRow.Chip("HUD", 1)), (Object)((Object)new ChipsRow.Chip(Lang.t("Inventar", "Inventory"), 2)), (Object)((Object)new ChipsRow.Chip(Lang.t("Beides", "Both"), 3))), () -> !Features.isOn("fwm.feature.invvalue") ? 0 : ((c.invValueHud ? 1 : 0) + (c.invValueInInventory ? 2 : 0) == 0 ? 0 : (c.invValueHud ? 1 : 0) + (c.invValueInInventory ? 2 : 0)), v -> {
                Features.set("fwm.feature.invvalue", v != 0);
                c.invValueHud = v == 1 || v == 3;
                c.invValueInInventory = v == 2 || v == 3;
            }));
            this.rows.add((Object)new ChipsRow((Component)Component.literal((String)Lang.t("Preis", "Price")), (List<ChipsRow.Chip>)List.of((Object)((Object)new ChipsRow.Chip("AH", 0)), (Object)((Object)new ChipsRow.Chip("Orders", 1)), (Object)((Object)new ChipsRow.Chip(Lang.t("h\u00f6her", "higher"), 2)), (Object)((Object)new ChipsRow.Chip(Lang.t("niedriger", "lower"), 3))), () -> c.invValueSource, v -> {
                c.invValueSource = v;
            }));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Shulker-Inhalt mitz\u00e4hlen", "Count shulker contents")), () -> c.invValueShulkers, (Consumer<Boolean>)((Consumer)v -> {
                c.invValueShulkers = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Enderchest-Wert (/ec)", "Ender chest value (/ec)")), () -> c.ecValue, (Consumer<Boolean>)((Consumer)v -> {
                c.ecValue = v;
            })));
        }
    }

    public static class Messages
    extends RowsPanel {
        @Override
        public Component title() {
            return Lang.of("fwm.kommt.ahMessages", new Object[0]);
        }

        @Override
        protected void buildRows() {
            FwmConfig c = FwmConfig.INSTANCE;
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Anzeigen (gez\u00e4hlt wird immer)", "Show (always counted)"))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Auktion verkauft", "Auction sold")), () -> c.moneyShowAhSold, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyShowAhSold = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Auktion gekauft", "Auction bought")), () -> c.moneyShowAhBought, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyShowAhBought = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Deine Order beliefert", "Your order filled")), () -> c.moneyShowOrderFilled, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyShowOrderFilled = v;
            })));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Order geliefert (Auszahlung)", "Order delivered (payout)")), () -> c.moneyShowOrderDelivered, (Consumer<Boolean>)((Consumer)v -> {
                c.moneyShowOrderDelivered = v;
            })));
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Wohin", "Where"))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Im zweiten Chat statt im Chat", "In the second chat")), () -> c.secondChatMarket, (Consumer<Boolean>)((Consumer)v -> {
                c.secondChatMarket = v;
            })));
            if (!Features.isOn("fwm.tab.secondchat")) {
                this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Der zweite Chat ist aus (Chat-Seite).", "The second chat is off (chat page).")), null));
            }
            this.rows.add((Object)new TextRow((Component)Component.literal((String)Lang.t("Wecker", "Alerts"))));
            this.rows.add((Object)new ToggleRow((Component)Component.literal((String)Lang.t("Wecker-Feld in /ah und /order", "Alert box in /ah and /order")), () -> c.marketAlertPanel, (Consumer<Boolean>)((Consumer)v -> {
                c.marketAlertPanel = v;
            })));
        }
    }

    public static class HistoryList
    extends Panel {
        private static final int ROW_H = 12;
        private static final int CHIP_H = 13;
        private static final String[] FILTERS = new String[]{"all", "sell", "pay", "ah"};
        private final PanelHost host;
        private int filter;
        private int scroll;
        private EditBox search;
        private List<History.Entry> shown = new ArrayList();

        public HistoryList(PanelHost host) {
            this.host = host;
        }

        @Override
        public Component title() {
            return Lang.of("fwm.money.history", new Object[0]);
        }

        @Override
        protected void onResize() {
            String old;
            Font font = Minecraft.getInstance().font;
            String string = old = this.search == null ? "" : this.search.getValue();
            if (this.search != null) {
                this.host.detach((AbstractWidget)this.search);
            }
            int sw = Math.max((int)60, (int)(this.width / 3));
            this.search = new EditBox(font, this.x + this.width - sw, this.y + 1, sw, 11, (Component)Component.literal((String)""));
            this.search.setHint((Component)Component.literal((String)Lang.t("Spieler / Item \u2026", "Player / item \u2026")));
            this.search.setMaxLength(32);
            this.search.setValue(old);
            this.host.attach((AbstractWidget)this.search);
        }

        @Override
        public void onHidden() {
            if (this.search != null) {
                this.host.detach((AbstractWidget)this.search);
            }
            this.search = null;
        }

        /*
         * Exception decompiling
         */
        private boolean wanted(History.Entry e) {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1802596)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4489513)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4491475)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820062)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820183)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1024810)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4466968)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:604767)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4234497)
             */
            throw new IllegalStateException("Decompilation failed");
        }

        private int listTop() {
            return this.y + 13 + 4;
        }

        private int listH() {
            return this.height - 17 - 14;
        }

        private int rowsVisible() {
            return Math.max((int)1, (int)(this.listH() / 12));
        }

        @Override
        public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
            int index;
            this.shown = new ArrayList();
            for (History.Entry e : History.newestFirst()) {
                if (!this.wanted(e)) continue;
                this.shown.add((Object)e);
            }
            int maxScroll = Math.max((int)0, (int)(this.shown.size() - this.rowsVisible()));
            this.scroll = Math.min((int)this.scroll, (int)maxScroll);
            int cx = this.x;
            String[] labels = new String[]{Lang.t("Alle", "All"), "Sell", "/pay", "AH"};
            for (int i = 0; i < labels.length; ++i) {
                int w = font.width(labels[i]) + 10;
                boolean over = mouseX >= cx && mouseX < cx + w && mouseY >= this.y && mouseY < this.y + 13;
                FwmTheme.box(ctx, cx, this.y, w, 13, i == this.filter ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, i == this.filter || over ? FwmTheme.ACCENT : FwmTheme.OFF);
                ctx.text(font, (Component)Component.literal((String)labels[i]), cx + 5, this.y + 3, i == this.filter ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
                cx += w + 3;
            }
            if (this.search != null) {
                this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
            }
            String hoverLine = null;
            DateTimeFormatter time = DateTimeFormatter.ofPattern((String)"dd.MM. HH:mm");
            int top = this.listTop();
            ctx.enableScissor(this.x, top, this.x + this.width, top + this.listH());
            for (int slot = 0; slot < this.rowsVisible() && (index = this.scroll + slot) < this.shown.size(); ++slot) {
                boolean over;
                History.Entry e = (History.Entry)this.shown.get(index);
                int ry = top + slot * 12;
                boolean bl = over = mouseX >= this.x && mouseX < this.x + this.width && mouseY >= ry && mouseY < ry + 12;
                if (over) {
                    FwmTheme.hover(ctx, this.x, ry, this.width, 12);
                }
                String when = Instant.ofEpochMilli((long)e.t).atZone(ZoneId.systemDefault()).format(time);
                String what = MoneyPanels.kindName(e.kind()) + (String)(e.n > 1 ? " \u00d7" + e.n : "");
                String who = e.p != null ? e.p : (e.i != null ? e.i : "");
                String amount = MoneyPanels.signed(e.c);
                ctx.text(font, (Component)Component.literal((String)when), this.x + 2, ry + 2, FwmTheme.TEXT_DIM, false);
                int wx = this.x + 2 + font.width("00.00. 00:00") + 6;
                ctx.text(font, (Component)Component.literal((String)what), wx, ry + 2, FwmTheme.TEXT, false);
                int px = wx + font.width("Sonstiges + ") + 4;
                int room = this.x + this.width - font.width(amount) - 6 - px;
                ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(who, Math.max((int)0, (int)room))), px, ry + 2, FwmTheme.TEXT_DIM, false);
                MoneyPanels.amount(ctx, font, amount, e.c, this.x + this.width - font.width(amount) - 2, ry + 2, MoneyPanels.color(e.c), mouseX, mouseY);
                if (!over || e.l == null) continue;
                hoverLine = e.l;
            }
            ctx.disableScissor();
            if (this.shown.isEmpty()) {
                MutableComponent none = Component.literal((String)Lang.t("Noch keine Eintr\u00e4ge.", "No entries yet."));
                ctx.text(font, (Component)none, this.x + (this.width - font.width((FormattedText)none)) / 2, top + 20, FwmTheme.TEXT_DIM, false);
            }
            long todaySum = 0L;
            long start = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();
            for (History.Entry e : this.shown) {
                if (e.t < start) continue;
                todaySum += e.c;
            }
            if (hoverLine != null) {
                ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(hoverLine, this.width - 4)), this.x + 2, this.y + this.height - 10, FwmTheme.TEXT_DIM, false);
            } else {
                String sum = Lang.t("Heute (gefiltert): ", "Today (filtered): ");
                ctx.text(font, (Component)Component.literal((String)sum), this.x + 2, this.y + this.height - 10, MoneyPanels.color(todaySum), false);
                MoneyPanels.amount(ctx, font, MoneyPanels.signed(todaySum), todaySum, this.x + 2 + font.width(sum), this.y + this.height - 10, MoneyPanels.color(todaySum), mouseX, mouseY);
            }
            MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
        }

        @Override
        public boolean mouseClicked(double mouseX, double mouseY, int button) {
            int index;
            if (!this.contains(mouseX, mouseY)) {
                return false;
            }
            Font font = Minecraft.getInstance().font;
            if (this.search != null && this.search.isMouseOver(mouseX, mouseY)) {
                this.search.setFocused(true);
                return true;
            }
            if (this.search != null) {
                this.search.setFocused(false);
            }
            if (mouseY >= (double)this.y && mouseY < (double)(this.y + 13)) {
                int cx = this.x;
                String[] labels = new String[]{Lang.t("Alle", "All"), "Sell", "/pay", "AH"};
                for (int i = 0; i < labels.length; ++i) {
                    int w = font.width(labels[i]) + 10;
                    if (mouseX >= (double)cx && mouseX < (double)(cx + w)) {
                        this.filter = i;
                        this.scroll = 0;
                        return true;
                    }
                    cx += w + 3;
                }
                return true;
            }
            int top = this.listTop();
            int slot = (int)((mouseY - (double)top) / 12.0);
            if (button == 0 && mouseY >= (double)top && slot >= 0 && slot < this.rowsVisible() && (index = this.scroll + slot) < this.shown.size() && ((History.Entry)this.shown.get((int)index)).p != null) {
                Minecraft.getInstance().gui.setScreen((Screen)new ChatScreen("/msg " + ((History.Entry)this.shown.get((int)index)).p + " ", false));
            }
            return true;
        }

        @Override
        public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
            int maxScroll = Math.max((int)0, (int)(this.shown.size() - this.rowsVisible()));
            this.scroll = Math.max((int)0, (int)Math.min((int)maxScroll, (int)(this.scroll - (int)Math.signum((double)vertical) * 2)));
            return true;
        }

        @Override
        public boolean isCapturingKeys() {
            return this.search != null && this.search.isFocused();
        }

        @Override
        public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
            if (this.search == null || !this.search.isFocused()) {
                return false;
            }
            if (keyCode == 41 || keyCode == 40) {
                this.search.setFocused(false);
                return true;
            }
            this.scroll = 0;
            return this.search.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
        }

        @Override
        public boolean charTyped(CharacterEvent input) {
            if (this.search == null || !this.search.isFocused()) {
                return false;
            }
            this.scroll = 0;
            return this.search.charTyped(input);
        }
    }

    public static class Week
    extends Panel {
        @Override
        public Component title() {
            return Lang.of("fwm.money.week", new Object[0]);
        }

        @Override
        public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
            LocalDate today = LocalDate.now();
            long[] nets = new long[7];
            long max = 1L;
            long sum = 0L;
            for (int i = 0; i < 7; ++i) {
                nets[i] = MoneyStore.net(MoneyStore.day(today.minusDays((long)(6 - i))));
                max = Math.max((long)max, (long)Math.abs((long)nets[i]));
                sum += nets[i];
            }
            int chartTop = this.y + 6;
            int chartH = Math.max((int)40, (int)(this.height - 50));
            int mid = chartTop + chartH / 2;
            int colW = (this.width - 8) / 7;
            ctx.fill(this.x + 4, mid, this.x + this.width - 4, mid + 1, FwmTheme.OFF);
            DateTimeFormatter dayName = DateTimeFormatter.ofPattern((String)"EE", (Locale)(Lang.isGerman() ? Locale.GERMANY : Locale.ENGLISH));
            for (int i = 0; i < 7; ++i) {
                int color;
                int bx = this.x + 4 + i * colW + 3;
                int bw = colW - 6;
                int h = (int)(Math.abs((long)nets[i]) * (long)(chartH / 2 - 2) / max);
                boolean over = mouseX >= bx && mouseX < bx + bw && mouseY >= chartTop && mouseY < chartTop + chartH;
                int n = color = nets[i] < 0L ? -2067340 : -9707377;
                if (!over) {
                    color = color & 0xFFFFFF | 0xCC000000;
                }
                if (nets[i] >= 0L) {
                    ctx.fill(bx, mid - h, bx + bw, mid, color);
                } else {
                    ctx.fill(bx, mid + 1, bx + bw, mid + 1 + h, color);
                }
                String label = today.minusDays((long)(6 - i)).format(dayName);
                ctx.text(font, (Component)Component.literal((String)label), bx + (bw - font.width(label)) / 2, chartTop + chartH + 3, FwmTheme.TEXT_DIM, false);
                if (!over) continue;
                String value = MoneyPanels.signed(nets[i]);
                ctx.text(font, (Component)Component.literal((String)value), Math.min((int)(this.x + this.width - font.width(value) - 2), (int)Math.max((int)(this.x + 2), (int)(bx + bw / 2 - font.width(value) / 2))), chartTop - 2, MoneyPanels.color(nets[i]), false);
            }
            String week = Lang.t("Woche: ", "Week: ");
            ctx.text(font, (Component)Component.literal((String)week), this.x + 4, this.y + this.height - 12, MoneyPanels.color(sum), false);
            MoneyPanels.amount(ctx, font, MoneyPanels.signed(sum), sum, this.x + 4 + font.width(week), this.y + this.height - 12, MoneyPanels.color(sum), mouseX, mouseY);
            MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
        }
    }

    public static class Today
    extends Panel {
        @Override
        public Component title() {
            return Lang.of("fwm.money.today", new Object[0]);
        }

        @Override
        public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
            long[] day = MoneyStore.day(LocalDate.now());
            int lineH = 11;
            if (!Features.isOn("fwm.tab.money")) {
                ctx.text(font, (Component)Component.literal((String)Lang.t("Geld ist ausgeschaltet.", "Money is switched off.")), this.x + 4, this.y + 2, FwmTheme.TEXT_DIM, false);
                return;
            }
            int mid = this.x + this.width / 2;
            int ty = this.y + 2;
            ctx.text(font, (Component)Component.literal((String)Lang.t("Heute nach Quelle", "Today by source")), this.x + 4, ty, FwmTheme.TEXT, false);
            ty += lineH + 2;
            boolean any = false;
            for (MoneyStore.Kind kind : MoneyStore.Kind.values()) {
                long cents;
                long l = cents = kind.ordinal() < day.length ? day[kind.ordinal()] : 0L;
                if (cents == 0L) continue;
                any = true;
                this.row(ctx, font, this.x + 4, MoneyPanels.kindName(kind), kind.income ? cents : -cents, ty, mid - 8, false, mouseX, mouseY);
                ty += lineH;
            }
            if (!any) {
                ctx.text(font, (Component)Component.literal((String)Lang.t("Heute noch nichts", "Nothing yet today")), this.x + 4, ty, FwmTheme.TEXT_DIM, false);
            }
            ctx.fill(mid - 2, this.y + 2, mid - 1, this.y + 2 + 8 * lineH + 2, FwmTheme.OFF);
            int rx = mid + 4;
            int right = this.x + this.width - 4;
            ty = this.y + 2;
            this.row(ctx, font, rx, Lang.t("Sitzung", "Session"), MoneyStore.user(), ty, right, true, mouseX, mouseY);
            this.row(ctx, font, rx, Lang.t("Einnahmen", "Income"), MoneyStore.income(day), ty += lineH + 4, right, false, mouseX, mouseY);
            this.row(ctx, font, rx, Lang.t("Ausgaben", "Spent"), -MoneyStore.expenses(day), ty += lineH, right, false, mouseX, mouseY);
            this.row(ctx, font, rx, Lang.t("Saldo heute", "Today"), MoneyStore.net(day), ty += lineH, right, true, mouseX, mouseY);
            long goal = FwmConfig.INSTANCE.moneyGoal * 100L;
            if (goal > 0L) {
                long net = MoneyStore.net(day);
                int barW = right - rx;
                int filled = (int)Math.max((long)0L, (long)Math.min((long)barW, (long)(net * (long)barW / goal)));
                String label = Lang.t("Tagesziel ", "Daily goal ") + Fmt.compactMoney(goal) + "  " + Math.max((long)0L, (long)Math.min((long)999L, (long)(net * 100L / goal))) + " %";
                ctx.text(font, (Component)Component.literal((String)label), rx, ty += lineH + 6, FwmTheme.TEXT_DIM, false);
                ctx.fill(rx, ty + 10, rx + barW, ty + 15, 0x55FFFFFF);
                ctx.fill(rx, ty + 10, rx + filled, ty + 15, net >= goal ? -865972 : FwmTheme.ACCENT);
            }
            MoneyPanels.flushTip(ctx, font, mouseX, mouseY);
        }

        private void row(GuiGraphicsExtractor ctx, Font font, int lx, String label, long cents, int ty, int right, boolean bold, int mouseX, int mouseY) {
            ctx.text(font, (Component)Component.literal((String)label), lx, ty, bold ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
            String value = MoneyPanels.signed(cents);
            MoneyPanels.amount(ctx, font, value, cents, right - font.width(value), ty, MoneyPanels.color(cents), mouseX, mouseY);
        }
    }
}
