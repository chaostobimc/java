/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.net.URI
 *  java.time.Instant
 *  java.time.ZoneId
 *  java.time.ZonedDateTime
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.concurrent.CompletableFuture
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.ConfirmLinkScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.net.URI;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.ModAccount;
import net.friendswithmoney.fwm.net.Tickets;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class TicketsPanel
extends Panel {
    private static final int GREEN = -10688364;
    private static final int RED = -2067340;
    private static final int GOLD = -865972;
    private static final int ROW = 13;
    private final PanelHost host;
    private final List<EditBox> fields = new ArrayList();
    private EditBox keyField;
    private EditBox amountField;
    private EditBox notesField;
    private EditBox descField;
    private final List<Hit> hits = new ArrayList();
    private Mode mode = Mode.BUY;
    private int spawnerIndex;
    private String note = "";
    private boolean noteOk;
    private boolean sending;
    private int ticketScroll;
    private String wanted;
    private Tickets.Ticket chat;
    private int chatScroll;

    public TicketsPanel(PanelHost host) {
        this(host, null);
    }

    public TicketsPanel(PanelHost host, String spawner) {
        this.host = host;
        this.wanted = spawner;
        Tickets.loadSpawners();
        Tickets.refresh();
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.tickets", new Object[0]);
    }

    private int colW() {
        return (this.width - 8) / 2;
    }

    private int rightX() {
        return this.x + this.colW() + 8;
    }

    private int bodyY() {
        return this.y + 34;
    }

    @Override
    protected void onResize() {
        for (EditBox f : this.fields) {
            this.host.detach((AbstractWidget)f);
        }
        this.fields.clear();
        Font font = Minecraft.getInstance().font;
        this.keyField = this.field(font, this.x + 4, this.y + 59, this.width - 90, 120, Lang.str("fwm.tickets.keyHint", new Object[0]));
        this.amountField = this.field(font, this.x + 4, this.bodyY() + 61, this.colW() - 8, 5, "1");
        this.notesField = this.field(font, this.x + 4, this.bodyY() + 93, this.colW() - 8, 1000, Lang.str("fwm.tickets.notes", new Object[0]));
        this.descField = this.field(font, this.x + 4, this.bodyY() + 41, this.colW() - 8, 1000, Lang.str("fwm.tickets.describe", new Object[0]));
        if (this.amountField.getValue().isEmpty()) {
            this.amountField.setValue("1");
        }
        this.showFields();
    }

    private EditBox field(Font font, int fx, int fy, int w, int max, String hint) {
        EditBox f = new EditBox(font, fx, fy, Math.max((int)20, (int)w), 10, (Component)Component.literal((String)hint));
        f.setBordered(false);
        f.setMaxLength(max);
        f.setTextColor(FwmTheme.TEXT);
        f.setHint((Component)Component.literal((String)hint));
        this.fields.add((Object)f);
        return f;
    }

    private void showFields() {
        for (EditBox f : this.fields) {
            this.host.detach((AbstractWidget)f);
        }
        if (!ModAccount.hasKey()) {
            this.host.attach((AbstractWidget)this.keyField);
            return;
        }
        if (this.mode == Mode.MIDDLEMAN) {
            this.host.attach((AbstractWidget)this.descField);
        } else {
            this.host.attach((AbstractWidget)this.amountField);
            this.host.attach((AbstractWidget)this.notesField);
        }
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.hits.clear();
        if (!ModAccount.hasKey()) {
            ctx.text(font, (Component)Component.literal((String)Lang.t("Du hast noch keinen Key", "You have no key yet")).withStyle(ChatFormatting.BOLD), this.x, this.y + 2, -865972, false);
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.t("Im FWM-Discord: Kanal \ud83e\udd16\u1d00\u1d18\u026a \u203a \u201eAPI-Schl\u00fcssel generieren\u201c (kommt per DM).", "Get it in the FWM Discord: channel \ud83e\udd16\u1d00\u1d18\u026a \u203a \"Generate API key\", it comes by DM."), this.width)), this.x, this.y + 14, FwmTheme.TEXT_DIM, false);
            String url = FwmConfig.INSTANCE.discordUrl;
            FwmTheme.box(ctx, this.x, this.y + 26, this.width - 168, 16, FwmTheme.INSET, FwmTheme.EDGE);
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(url, this.width - 176)), this.x + 4, this.y + 30, FwmTheme.ACCENT, false);
            this.button(ctx, font, this.x + this.width - 164, this.y + 26, 80, 16, Lang.t("Kopieren", "Copy"), mouseX, mouseY, () -> {
                Minecraft.getInstance().keyboardHandler.setClipboard(url);
                this.result(Lang.t("Link kopiert.", "Link copied."));
                this.noteOk = true;
            });
            this.button(ctx, font, this.x + this.width - 80, this.y + 26, 80, 16, Lang.t("\u00d6ffnen", "Open"), mouseX, mouseY, () -> ConfirmLinkScreen.confirmLinkNow((Screen)Minecraft.getInstance().gui.screen(), (URI)URI.create((String)url)));
            FwmTheme.box(ctx, this.x, this.y + 55, this.width - 84, 16, FwmTheme.INSET, this.keyField.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            this.keyField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
            if (!this.note.isEmpty()) {
                ctx.text(font, (Component)Component.literal((String)this.note), this.x, this.y + 46, this.noteOk ? -10688364 : -2067340, false);
            }
            this.button(ctx, font, this.x + this.width - 80, this.y + 55, 80, 16, Lang.str("fwm.tickets.connect", new Object[0]), mouseX, mouseY, () -> {
                ModAccount.setKey(this.keyField.getValue());
                this.keyField.setValue("");
                this.showFields();
            });
            String msg = ModAccount.state() == ModAccount.State.INVALID || ModAccount.state() == ModAccount.State.ERROR ? ModAccount.message() : "";
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(msg, this.width)), this.x, this.y + 75, ModAccount.state() == ModAccount.State.INVALID ? -2067340 : FwmTheme.TEXT_DIM, false);
            return;
        }
        String who = switch (ModAccount.state()) {
            case ModAccount.State.OK -> Lang.str("fwm.tickets.connectedAs", new Object[0]) + " " + ModAccount.minecraftName();
            case ModAccount.State.CHECKING -> Lang.str("fwm.tickets.checking", new Object[0]);
            default -> ModAccount.message();
        };
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(who, this.width - 90)), this.x, this.y + 5, ModAccount.state() == ModAccount.State.OK ? -10688364 : -2067340, false);
        this.button(ctx, font, this.x + this.width - 80, this.y + 1, 80, 16, Lang.str("fwm.tickets.disconnect", new Object[0]), mouseX, mouseY, () -> {
            ModAccount.forget();
            this.showFields();
        });
        ctx.fill(this.x, this.y + 24, this.x + this.width, this.y + 25, FwmTheme.OFF);
        this.drawNew(ctx, font, mouseX, mouseY, delta);
        this.drawList(ctx, font, mouseX, mouseY);
    }

    private void drawNew(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int cx = this.x;
        int cy = this.bodyY();
        int cw = this.colW();
        ctx.text(font, (Component)Lang.of("fwm.tickets.new", new Object[0]), cx, cy - 6, FwmTheme.TEXT_DIM, false);
        String[] kinds = new String[]{Lang.str("fwm.tickets.buy", new Object[0]), Lang.str("fwm.tickets.sell", new Object[0]), "Middleman"};
        int textSum = 0;
        for (String k : kinds) {
            textSum += font.width(k);
        }
        int spare = Math.max((int)0, (int)(cw - 4 - textSum));
        int chipX = cx;
        for (int i = 0; i < 3; ++i) {
            int w;
            int n = w = i == 2 ? cx + cw - chipX : font.width(kinds[i]) + spare / 3;
            Mode m = i == 0 ? Mode.BUY : (i == 1 ? Mode.SELL : Mode.MIDDLEMAN);
            this.chip(ctx, font, chipX, cy + 6, w, kinds[i], this.mode == m, mouseX, mouseY, () -> this.setMode(m));
            chipX += w + 2;
        }
        int bottom = cy + 114;
        if (this.mode == Mode.MIDDLEMAN) {
            ctx.text(font, (Component)Lang.of("fwm.tickets.describeLabel", new Object[0]), cx, cy + 25, FwmTheme.TEXT_DIM, false);
            FwmTheme.box(ctx, cx, cy + 36, cw, 16, FwmTheme.INSET, this.descField.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            this.descField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
            ctx.text(font, (Component)Component.literal((String)(this.descField.getValue().length() + " / 1000")), cx + cw - font.width(this.descField.getValue().length() + " / 1000"), cy + 56, FwmTheme.TEXT_DIM, false);
        } else {
            String label;
            boolean buy = this.mode == Mode.BUY;
            List<Tickets.Spawner> list = this.offered(buy);
            if (list.isEmpty()) {
                label = Tickets.spawnerError().isEmpty() ? Lang.str("fwm.tickets.noSpawners", new Object[0]) : Tickets.spawnerError();
            } else {
                if (this.wanted != null) {
                    for (int i = 0; i < list.size(); ++i) {
                        if (!((Tickets.Spawner)list.get((int)i)).name.equalsIgnoreCase(this.wanted)) continue;
                        this.spawnerIndex = i;
                    }
                    this.wanted = null;
                }
                this.spawnerIndex = Math.floorMod((int)this.spawnerIndex, (int)list.size());
                Tickets.Spawner s = (Tickets.Spawner)list.get(this.spawnerIndex);
                label = s.name + " \u00b7 $" + (buy ? s.ankaufPreis : s.verkaufPreis);
            }
            FwmTheme.box(ctx, cx, cy + 24, cw, 16, FwmTheme.RAISED, FwmTheme.EDGE);
            ctx.text(font, (Component)Component.literal((String)"\u2039"), cx + 5, cy + 28, FwmTheme.ACCENT, false);
            ctx.text(font, (Component)Component.literal((String)"\u203a"), cx + cw - 9, cy + 28, FwmTheme.ACCENT, false);
            MutableComponent shown = Component.literal((String)font.plainSubstrByWidth(label, cw - 24));
            ctx.text(font, (Component)shown, cx + (cw - font.width((FormattedText)shown)) / 2, cy + 28, FwmTheme.TEXT, false);
            this.hits.add((Object)new Hit(cx, cy + 24, cw / 2, 16, () -> --this.spawnerIndex));
            this.hits.add((Object)new Hit(cx + cw / 2, cy + 24, cw - cw / 2, 16, () -> ++this.spawnerIndex));
            ctx.text(font, (Component)Lang.of("fwm.tickets.amount", new Object[0]), cx, cy + 46, FwmTheme.TEXT_DIM, false);
            FwmTheme.box(ctx, cx, cy + 57, cw, 15, FwmTheme.INSET, this.amountField.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            this.amountField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
            ctx.text(font, (Component)Lang.of("fwm.tickets.notesLabel", new Object[0]), cx, cy + 78, FwmTheme.TEXT_DIM, false);
            FwmTheme.box(ctx, cx, cy + 89, cw, 15, FwmTheme.INSET, this.notesField.isFocused() ? FwmTheme.ACCENT : FwmTheme.EDGE);
            this.notesField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        }
        String kind = this.mode == Mode.MIDDLEMAN ? "middleman" : "spawner";
        boolean blocked = Tickets.hasLive(kind);
        String send = this.sending ? Lang.str("fwm.tickets.sending", new Object[0]) : (blocked ? Lang.str("fwm.tickets.alreadyOpen", new Object[0]) : Lang.str("fwm.tickets.open", new Object[0]));
        this.button(ctx, font, cx, bottom, cw, 16, send, mouseX, mouseY, blocked || this.sending ? null : this::send);
        if (!this.note.isEmpty()) {
            List lines = font.split((FormattedText)Component.literal((String)this.note), cw);
            for (int i = 0; i < Math.min((int)2, (int)lines.size()); ++i) {
                ctx.text(font, (FormattedCharSequence)lines.get(i), cx, bottom + 20 + i * 10, this.noteOk ? -10688364 : -2067340, false);
            }
        }
    }

    private void drawList(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        int lx = this.rightX();
        int ly = this.bodyY();
        int lw = this.colW();
        if (this.chat != null) {
            this.drawChat(ctx, font, lx, ly, lw, mouseX, mouseY);
            return;
        }
        ctx.text(font, (Component)Lang.of("fwm.tickets.mine", new Object[0]), lx, ly - 6, FwmTheme.TEXT_DIM, false);
        String again = Lang.str("fwm.tickets.reload", new Object[0]);
        int aw = font.width(again);
        boolean overAgain = mouseX >= lx + lw - aw && mouseX < lx + lw && mouseY >= ly - 7 && mouseY < ly + 3;
        ctx.text(font, (Component)Component.literal((String)again), lx + lw - aw, ly - 6, overAgain ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
        this.hits.add((Object)new Hit(lx + lw - aw, ly - 7, aw, 10, Tickets::refresh));
        List<Tickets.Ticket> list = Tickets.tickets();
        if (list.isEmpty()) {
            ctx.text(font, (Component)Lang.of("fwm.tickets.none", new Object[0]), lx, ly + 8, FwmTheme.TEXT_DIM, false);
            return;
        }
        int rows = Math.max((int)1, (int)((this.y + this.height - (ly + 6)) / 26));
        this.ticketScroll = Math.max((int)0, (int)Math.min((int)this.ticketScroll, (int)(list.size() - rows)));
        for (int i = 0; i < rows && this.ticketScroll + i < list.size(); ++i) {
            String kind;
            Tickets.Ticket t = (Tickets.Ticket)list.get(this.ticketScroll + i);
            int ry = ly + 6 + i * 2 * 13;
            FwmTheme.box(ctx, lx, ry, lw, 24, FwmTheme.INSET, FwmTheme.OFF);
            String string = kind = "spawner".equals((Object)t.type) ? "Spawner" : "Middleman";
            int color = "success".equals((Object)t.status) ? -10688364 : ("failure".equals((Object)t.status) ? -2067340 : (t.live() ? -865972 : FwmTheme.TEXT_DIM));
            ctx.text(font, (Component)Component.literal((String)kind), lx + 4, ry + 3, FwmTheme.TEXT, false);
            MutableComponent status = Component.literal((String)font.plainSubstrByWidth(Tickets.statusText(t), lw - 12 - font.width(kind)));
            ctx.text(font, (Component)status, lx + lw - 4 - font.width((FormattedText)status), ry + 3, color, false);
            ctx.text(font, (Component)Component.literal((String)TicketsPanel.date(t.createdAt)), lx + 4, ry + 3 + 13 - 2, FwmTheme.TEXT_DIM, false);
            String open = Lang.t("Chat \u203a", "Chat \u203a");
            ctx.text(font, (Component)Component.literal((String)open), lx + lw - 4 - font.width(open), ry + 3 + 13 - 2, FwmTheme.ACCENT, false);
            this.hits.add((Object)new Hit(lx, ry, lw, 24, () -> {
                this.chat = t;
                this.chatScroll = 0;
                Tickets.watch(t.id);
                Tickets.loadMessages(t.id, true);
            }));
        }
    }

    private void drawChat(GuiGraphicsExtractor ctx, Font font, int lx, int ly, int lw, int mouseX, int mouseY) {
        Tickets.Ticket t = this.chat;
        String back = "\u2039 " + Lang.t("Tickets", "Tickets");
        boolean overBack = mouseX >= lx && mouseX < lx + font.width(back) && mouseY >= ly - 7 && mouseY < ly + 3;
        ctx.text(font, (Component)Component.literal((String)back), lx, ly - 6, overBack ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
        this.hits.add((Object)new Hit(lx, ly - 7, font.width(back), 10, () -> {
            this.chat = null;
            Tickets.watch(null);
        }));
        String status = Tickets.statusText(t);
        int color = "success".equals((Object)t.status) ? -10688364 : ("failure".equals((Object)t.status) ? -2067340 : (t.live() ? -865972 : FwmTheme.TEXT_DIM));
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(status, lw - font.width(back) - 8)), lx + lw - font.width(font.plainSubstrByWidth(status, lw - font.width(back) - 8)), ly - 6, color, false);
        int top = ly + 6;
        int bottom = this.y + this.height - 12;
        FwmTheme.box(ctx, lx, top, lw, bottom - top, FwmTheme.INSET, FwmTheme.OFF);
        List<Tickets.Message> msgs = Tickets.messages(t.id);
        if (msgs.isEmpty()) {
            String none = Tickets.loading(t.id) ? Lang.t("L\u00e4dt \u2026", "Loading \u2026") : Lang.t("Noch keine Nachrichten.", "No messages yet.");
            ctx.text(font, (Component)Component.literal((String)none), lx + 6, top + 6, FwmTheme.TEXT_DIM, false);
        } else {
            int h;
            ArrayList blocks = new ArrayList();
            int total = 0;
            for (Tickets.Message m : msgs) {
                String who = m.author == null ? "?" : (m.author.displayName != null ? m.author.displayName : m.author.username);
                List lines = font.split((FormattedText)Component.literal((String)(m.content == null ? "" : m.content)), lw - 16);
                h = 10 + lines.size() * 10 + 4;
                blocks.add((Object)new Object[]{who, TicketsPanel.date(m.createdAt), lines, h});
                total += h;
            }
            int view = bottom - top - 6;
            this.chatScroll = Math.max((int)0, (int)Math.min((int)this.chatScroll, (int)Math.max((int)0, (int)(total - view))));
            ctx.enableScissor(lx + 1, top + 1, lx + lw - 1, bottom - 1);
            int cy = bottom - 3 - total + this.chatScroll;
            for (Object[] b : blocks) {
                h = (Integer)b[3];
                if (cy + h > top && cy < bottom) {
                    ctx.text(font, (Component)Component.literal((String)((String)b[0])).withStyle(ChatFormatting.BOLD), lx + 6, cy, -865972, false);
                    String when = (String)b[1];
                    ctx.text(font, (Component)Component.literal((String)when), lx + lw - 6 - font.width(when), cy, FwmTheme.TEXT_DIM, false);
                    List lines = (List)b[2];
                    for (int i = 0; i < lines.size(); ++i) {
                        ctx.text(font, (FormattedCharSequence)lines.get(i), lx + 10, cy + 10 + i * 10, FwmTheme.TEXT, false);
                    }
                }
                cy += h;
            }
            ctx.disableScissor();
        }
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.t("Antworten im Discord-Ticket", "Reply in the Discord ticket"), lw)), lx, bottom + 3, FwmTheme.TEXT_DIM, false);
    }

    private static String date(String iso) {
        try {
            ZonedDateTime at = Instant.parse((CharSequence)iso).atZone(ZoneId.systemDefault());
            return String.format((String)"%02d.%02d. %02d:%02d", (Object[])new Object[]{at.getDayOfMonth(), at.getMonthValue(), at.getHour(), at.getMinute()});
        }
        catch (Exception e) {
            return "";
        }
    }

    private List<Tickets.Spawner> offered(boolean buy) {
        ArrayList out = new ArrayList();
        for (Tickets.Spawner s : Tickets.spawners()) {
            if (!s.can(buy)) continue;
            out.add((Object)s);
        }
        return out;
    }

    private void setMode(Mode m) {
        this.mode = m;
        this.note = "";
        this.showFields();
    }

    private void send() {
        CompletableFuture<String> sent;
        String kind;
        String string = kind = this.mode == Mode.MIDDLEMAN ? "middleman" : "spawner";
        if (Tickets.hasLive(kind)) {
            return;
        }
        if (this.mode == Mode.MIDDLEMAN) {
            String d = this.descField.getValue().trim();
            if (d.length() < 5) {
                this.result(Lang.str("fwm.tickets.tooShort", new Object[0]));
                return;
            }
            sent = Tickets.openMiddleman(d);
        } else {
            int amount;
            List<Tickets.Spawner> list = this.offered(this.mode == Mode.BUY);
            if (list.isEmpty()) {
                return;
            }
            try {
                amount = Integer.parseInt((String)this.amountField.getValue().trim());
            }
            catch (NumberFormatException e) {
                amount = 0;
            }
            if (amount < 1 || amount > 10000) {
                this.result(Lang.str("fwm.tickets.badAmount", new Object[0]));
                return;
            }
            sent = Tickets.openSpawner(this.mode == Mode.BUY, (Tickets.Spawner)list.get(Math.floorMod((int)this.spawnerIndex, (int)list.size())), amount, this.notesField.getValue());
        }
        this.sending = true;
        this.note = "";
        sent.whenComplete((error, ex) -> Minecraft.getInstance().execute(() -> {
            this.sending = false;
            if (ex != null) {
                this.result(Lang.str("fwm.tickets.noConnection", new Object[0]));
            } else if (error != null) {
                this.result((String)error);
            } else {
                this.noteOk = true;
                this.note = Lang.str("fwm.tickets.opened", new Object[0]);
                this.descField.setValue("");
                this.notesField.setValue("");
            }
        }));
    }

    private void result(String text) {
        this.noteOk = false;
        this.note = text;
    }

    private void chip(GuiGraphicsExtractor ctx, Font font, int cx, int cy, int w, String label, boolean on, int mouseX, int mouseY, Runnable action) {
        boolean over = mouseX >= cx && mouseX < cx + w && mouseY >= cy && mouseY < cy + 13;
        FwmTheme.box(ctx, cx, cy, w, 13, on ? FwmTheme.RAISED : FwmTheme.INSET, on || over ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent t = Component.literal((String)font.plainSubstrByWidth(label, w - 4));
        ctx.text(font, (Component)t, cx + (w - font.width((FormattedText)t)) / 2, cy + 3, on ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
        this.hits.add((Object)new Hit(cx, cy, w, 13, action));
    }

    private void button(GuiGraphicsExtractor ctx, Font font, int bx, int by, int w, int h, String label, int mouseX, int mouseY, Runnable action) {
        boolean over = action != null && mouseX >= bx && mouseX < bx + w && mouseY >= by && mouseY < by + h;
        FwmTheme.box(ctx, bx, by, w, h, action == null ? FwmTheme.LOCKED : FwmTheme.RAISED, over ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent t = Component.literal((String)font.plainSubstrByWidth(label, w - 4));
        ctx.text(font, (Component)t, bx + (w - font.width((FormattedText)t)) / 2, by + (h - 8) / 2, action == null ? FwmTheme.TEXT_DIM : (over ? FwmTheme.ACCENT : FwmTheme.TEXT), false);
        if (action != null) {
            this.hits.add((Object)new Hit(bx, by, w, h, action));
        }
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        for (EditBox f : this.fields) {
            boolean in;
            boolean bl = in = mouseX >= (double)(f.getX() - 2) && mouseX < (double)(f.getX() + f.getWidth() + 2) && mouseY >= (double)(f.getY() - 3) && mouseY < (double)(f.getY() + 12);
            f.setFocused(in && (f == this.keyField ? !ModAccount.hasKey() : (this.mode == Mode.MIDDLEMAN ? f == this.descField : f != this.descField && f != this.keyField)));
        }
        for (Hit hit : new ArrayList(this.hits)) {
            if (!(mouseX >= (double)hit.x()) || !(mouseX < (double)(hit.x() + hit.w())) || !(mouseY >= (double)hit.y()) || !(mouseY < (double)(hit.y() + hit.h()))) continue;
            hit.action().run();
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (this.chat != null && mouseX >= (double)this.rightX()) {
            this.chatScroll += (int)Math.signum((double)vertical) * 20;
            return true;
        }
        if (mouseX >= (double)this.rightX()) {
            this.ticketScroll = Math.max((int)0, (int)(this.ticketScroll - (int)Math.signum((double)vertical)));
        }
        return true;
    }

    private EditBox focused() {
        for (EditBox f : this.fields) {
            if (!f.isFocused()) continue;
            return f;
        }
        return null;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.focused() != null;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        EditBox f = this.focused();
        if (f == null) {
            return false;
        }
        if (keyCode == 41) {
            f.setFocused(false);
            return true;
        }
        if (keyCode == 40 && f == this.keyField) {
            ModAccount.setKey(this.keyField.getValue());
            this.keyField.setValue("");
            this.showFields();
            return true;
        }
        return f.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        EditBox f = this.focused();
        return f != null && f.charTyped(input);
    }

    @Override
    public void onHidden() {
        Tickets.watch(null);
        for (EditBox f : this.fields) {
            this.host.detach((AbstractWidget)f);
        }
    }

    private static enum Mode {
        BUY,
        SELL,
        MIDDLEMAN;

    }

    private record Hit(int x, int y, int w, int h, Runnable action) {
    }
}
