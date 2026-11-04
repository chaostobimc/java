/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.time.Instant
 *  java.time.ZoneId
 *  java.time.ZonedDateTime
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.AbstractWidget
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.friendswithmoney.fwm.chat.Conversation;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.chat.PlayerSuggest;
import net.friendswithmoney.fwm.chat.PrivateMessage;
import net.friendswithmoney.fwm.ui.ConfirmBox;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.SharpIcons;
import net.friendswithmoney.fwm.ui.panel.MessageSettingsPanel;
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
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class MessagesPanel
extends Panel {
    private static final int LIST_PERCENT = 36;
    private static final int LIST_MIN_W = 66;
    private static final int CONTACT_H = 24;
    private static final int INPUT_H = 14;
    private static final int GAP = 4;
    private static final int LINE_H = 10;
    private static final int BUBBLE_PAD = 4;
    private static final int HEADER_H = 10;
    private static final int SCROLLBAR_W = 3;
    private static final int SEARCH_H = 13;
    private static final int DELETE_W = 12;
    private final ConfirmBox confirm = new ConfirmBox();
    private static final float BUBBLE_MAX = 0.78f;
    private final PanelHost host;
    private EditBox input;
    private EditBox search;
    private String selected;
    private int contactScroll;
    private int threadScroll;
    private boolean newChat;
    private List<Bubble> layout = new ArrayList();
    private String layoutPartner = "";
    private int layoutCount = -1;
    private int layoutWidth = -1;
    private boolean stickToBottom = true;
    private static final int BAR_H = 11;
    private static final int COG = 11;

    public MessagesPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.messages", new Object[0]);
    }

    @Override
    protected void onResize() {
        String previousSearch;
        String previousInput = this.input == null ? "" : this.input.getValue();
        String string = previousSearch = this.search == null ? "" : this.search.getValue();
        if (this.input != null) {
            this.host.detach((AbstractWidget)this.input);
        }
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        Font font = Minecraft.getInstance().font;
        this.input = new EditBox(font, this.threadX() + 3, this.y + this.height - 14 + 2, this.threadWidth() - 6, 10, (Component)Lang.of("fwm.messages.write", new Object[0]));
        this.input.setBordered(false);
        this.input.setMaxLength(200);
        this.input.setTextColor(FwmTheme.TEXT);
        this.input.setHint((Component)Lang.of("fwm.messages.write", new Object[0]));
        this.input.setValue(previousInput);
        this.search = new EditBox(font, this.x + 3, this.y + 2, this.searchWidth() - 6, 9, this.searchHint());
        this.search.setBordered(false);
        this.search.setMaxLength(16);
        this.search.setTextColor(FwmTheme.TEXT);
        this.search.setHint(this.searchHint());
        this.search.setValue(previousSearch);
        this.search.setResponder(text -> {
            this.contactScroll = 0;
        });
        this.host.attach((AbstractWidget)this.input);
        this.host.attach((AbstractWidget)this.search);
        this.layoutWidth = -1;
    }

    @Override
    public void onHidden() {
        if (this.input != null) {
            this.host.detach((AbstractWidget)this.input);
        }
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        PlayerSuggest.reset();
    }

    private Component searchHint() {
        return Lang.of(this.newChat ? "fwm.messages.findPlayer" : "fwm.messages.search", new Object[0]);
    }

    private void toggleNewChat() {
        this.newChat = !this.newChat;
        this.contactScroll = 0;
        this.search.setValue("");
        this.search.setHint(this.searchHint());
        this.search.setFocused(true);
        PlayerSuggest.reset();
    }

    private int listWidth() {
        return Math.max((int)66, (int)(this.width * 36 / 100));
    }

    private int threadX() {
        return this.x + this.listWidth() + 6;
    }

    private int threadWidth() {
        return this.width - this.listWidth() - 6;
    }

    private int threadBottom() {
        return this.y + this.height - 14 - 4;
    }

    private int searchWidth() {
        return this.listWidth() - 13 - 1 - (this.showReadAll() ? 14 : 0);
    }

    private int buttonX() {
        return this.x + this.listWidth() - 13;
    }

    private boolean showReadAll() {
        return !this.newChat && Messages.unreadTotal() > 0;
    }

    private int readAllX() {
        return this.buttonX() - 13 - 1;
    }

    private int barH() {
        return this.selected == null ? 0 : 11;
    }

    private int barY() {
        return this.y;
    }

    private int threadTop() {
        return this.y + this.barH();
    }

    private int cogX() {
        return this.threadX() + this.threadWidth() - 11;
    }

    private int ignoreX(Font font) {
        return this.cogX() - 3 - this.ignoreWidth(font);
    }

    private int ignoreWidth(Font font) {
        return font.width((FormattedText)this.ignoreLabel()) + 6;
    }

    private int hideX(Font font) {
        return this.ignoreX(font) - 3 - MessagesPanel.smallWidth(font, "fwm.messages.hide");
    }

    private int tpaHereX(Font font) {
        return this.hideX(font) - 3 - MessagesPanel.smallWidth(font, "fwm.messages.tpahere");
    }

    private int tpaX(Font font) {
        return this.tpaHereX(font) - 3 - MessagesPanel.smallWidth(font, "fwm.messages.tpa");
    }

    private static int smallWidth(Font font, String key) {
        return font.width((FormattedText)Lang.of(key, new Object[0])) + 6;
    }

    private Component ignoreLabel() {
        return Lang.of(Messages.isIgnored(this.selected) ? "fwm.messages.unignore" : "fwm.messages.ignore", new Object[0]);
    }

    private int contactsTop() {
        return this.y + 13 + 2;
    }

    private int contactsHeight() {
        return this.y + this.height - this.contactsTop();
    }

    private int contactsShown() {
        return Math.max((int)1, (int)((this.contactsHeight() + 24 - 1) / 24));
    }

    private int maxContactScroll(int total) {
        return Math.max((int)0, (int)(total - Math.max((int)1, (int)(this.contactsHeight() / 24))));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        List<Entry> entries = this.entries();
        if (this.selected == null && !entries.isEmpty() && ((Entry)((Object)entries.get(0))).conversation() != null) {
            this.select((Entry)((Object)entries.get(0)));
        }
        this.renderSearch(ctx, font, mouseX, mouseY, delta);
        this.renderContacts(ctx, font, entries, mouseX, mouseY);
        ctx.fill(this.x + this.listWidth() + 2, this.y, this.x + this.listWidth() + 3, this.y + this.height, FwmTheme.OFF);
        this.renderThread(ctx, font);
        this.renderThreadBar(ctx, font, mouseX, mouseY);
        this.renderInput(ctx, mouseX, mouseY, delta);
        this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    }

    private void renderSearch(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int i;
        FwmTheme.box(ctx, this.x, this.y, this.searchWidth(), 13, FwmTheme.RAISED, this.search.isFocused() ? FwmTheme.ACCENT : FwmTheme.OFF);
        this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        boolean hovered = mouseX >= this.buttonX() && mouseX < this.buttonX() + 13 && mouseY >= this.y && mouseY < this.y + 13;
        String label = this.newChat ? "x" : "+";
        FwmTheme.box(ctx, this.buttonX(), this.y, 13, 13, this.newChat ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, this.newChat || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        ctx.text(font, label, this.buttonX() + (13 - font.width(label)) / 2 + 1, this.y + 3, this.newChat || hovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
        if (!this.showReadAll()) {
            return;
        }
        boolean readHovered = mouseX >= this.readAllX() && mouseX < this.readAllX() + 13 && mouseY >= this.y && mouseY < this.y + 13;
        FwmTheme.box(ctx, this.readAllX(), this.y, 13, 13, FwmTheme.RAISED, readHovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        int cx = this.readAllX() + 4;
        int cy = this.y + 7;
        int color = readHovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM;
        for (i = 0; i < 2; ++i) {
            ctx.fill(cx + i, cy + i, cx + i + 1, cy + i + 2, color);
        }
        for (i = 0; i < 4; ++i) {
            ctx.fill(cx + 2 + i, cy + 2 - i, cx + 3 + i, cy + 4 - i, color);
        }
    }

    private void renderThreadBar(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY) {
        if (this.selected == null) {
            return;
        }
        int tx = this.threadX();
        int tw = this.threadWidth();
        int by = this.barY();
        int btnW = this.ignoreWidth(font);
        int btnX = this.ignoreX(font);
        boolean ignored = Messages.isIgnored(this.selected);
        boolean hovered = mouseX >= btnX && mouseX < btnX + btnW && mouseY >= by && mouseY < by + 11;
        ctx.text(font, MessagesPanel.trim(font, this.selected, this.tpaX(font) - tx - 4), tx, by + 2, ignored ? FwmTheme.DISABLED_TEXT : FwmTheme.TEXT_DIM, false);
        boolean cogHovered = mouseX >= this.cogX() && mouseX < this.cogX() + 11 && mouseY >= by && mouseY < by + 11;
        FwmTheme.box(ctx, this.cogX(), by, 11, 11, FwmTheme.RAISED, cogHovered ? FwmTheme.ACCENT : FwmTheme.OFF);
        SharpIcons.draw(ctx, "icon_cog", this.cogX() + 1, by + 1, 9, cogHovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM);
        FwmTheme.box(ctx, btnX, by, btnW, 11, ignored ? FwmTheme.DISABLED_FILL : FwmTheme.RAISED, hovered ? FwmTheme.ACCENT : (ignored ? FwmTheme.DISABLED_EDGE : FwmTheme.OFF));
        Component label = this.ignoreLabel();
        ctx.text(font, label, btnX + (btnW - font.width((FormattedText)label)) / 2, by + 2, hovered ? FwmTheme.ACCENT : (ignored ? FwmTheme.DISABLED_TEXT : FwmTheme.TEXT_DIM), false);
        this.smallButton(ctx, font, "fwm.messages.tpa", this.tpaX(font), by, mouseX, mouseY);
        this.smallButton(ctx, font, "fwm.messages.tpahere", this.tpaHereX(font), by, mouseX, mouseY);
        this.smallButton(ctx, font, "fwm.messages.hide", this.hideX(font), by, mouseX, mouseY);
    }

    private void smallButton(GuiGraphicsExtractor ctx, Font font, String key, int bx, int by, int mouseX, int mouseY) {
        int bw = MessagesPanel.smallWidth(font, key);
        boolean over = mouseX >= bx && mouseX < bx + bw && mouseY >= by && mouseY < by + 11;
        FwmTheme.box(ctx, bx, by, bw, 11, FwmTheme.RAISED, over ? FwmTheme.ACCENT : FwmTheme.OFF);
        MutableComponent label = Lang.of(key, new Object[0]);
        ctx.text(font, (Component)label, bx + (bw - font.width((FormattedText)label)) / 2, by + 2, over ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
    }

    private void askTeleport(String command, String askKey) {
        String partner = this.selected;
        if (partner == null) {
            return;
        }
        this.confirm.ask((Component)Lang.of(askKey, partner), "fwm.confirm.send", () -> {
            Minecraft client = Minecraft.getInstance();
            if (client.getConnection() != null) {
                client.getConnection().sendCommand(command + " " + partner);
            }
        });
    }

    private void renderContacts(GuiGraphicsExtractor ctx, Font font, List<Entry> entries, int mouseX, int mouseY) {
        int listW = this.listWidth();
        int top = this.contactsTop();
        if (entries.isEmpty()) {
            String key = !this.newChat ? (this.search.getValue().isBlank() ? "fwm.messages.noContacts" : "fwm.messages.noMatch") : (this.search.getValue().isBlank() ? "fwm.messages.typeName" : (PlayerSuggest.waiting() ? "fwm.messages.searching" : "fwm.messages.noMatch"));
            int hintY = top + 2;
            for (FormattedCharSequence line : font.split((FormattedText)Lang.of(key, new Object[0]), listW - 6)) {
                ctx.text(font, line, this.x + 3, hintY, FwmTheme.TEXT_DIM, false);
                hintY += 10;
            }
            return;
        }
        int shown = this.contactsShown();
        this.contactScroll = Math.max((int)0, (int)Math.min((int)this.contactScroll, (int)this.maxContactScroll(entries.size())));
        ctx.enableScissor(this.x, top, this.x + listW, this.y + this.height);
        for (int i = 0; i < shown && this.contactScroll + i < entries.size(); ++i) {
            PrivateMessage newest;
            Entry entry = (Entry)((Object)entries.get(this.contactScroll + i));
            Conversation conversation = entry.conversation();
            int ry = top + i * 24;
            boolean active = entry.name().equals((Object)this.selected);
            boolean hovered = mouseX >= this.x && mouseX < this.x + listW && mouseY >= ry && mouseY < ry + 24 && mouseY < this.y + this.height;
            FwmTheme.sidebarButton(ctx, this.x, ry, listW, 22, active, hovered, false);
            int unread = conversation == null ? 0 : conversation.unread;
            int textColor = Messages.isIgnored(entry.name()) ? FwmTheme.OFF : FwmTheme.sidebarTextColor(active, hovered, false);
            int nameW = listW - 8 - (unread > 0 ? 12 : 0);
            ctx.text(font, MessagesPanel.trim(font, entry.name(), nameW), this.x + 4, ry + 4, textColor, false);
            PrivateMessage privateMessage = newest = conversation == null ? null : conversation.newest();
            String preview = newest == null ? Lang.str("fwm.messages.startChat", new Object[0]) : (newest.outgoing ? Lang.str("fwm.messages.youShort", new Object[0]) : "") + newest.text;
            ctx.text(font, MessagesPanel.trim(font, preview, listW - 8), this.x + 4, ry + 13, FwmTheme.TEXT_DIM, false);
            if (hovered && conversation != null) {
                this.cross(ctx, this.x + listW - 12 + 2, ry + 7, FwmTheme.DISABLED_TEXT);
                continue;
            }
            if (unread <= 0) continue;
            String badge = unread > 9 ? "9+" : String.valueOf((int)unread);
            int bw = font.width(badge) + 4;
            FwmTheme.pill(ctx, this.x + listW - bw - 3, ry + 3, bw, 9, FwmTheme.ACCENT);
            ctx.text(font, badge, this.x + listW - bw - 1, ry + 4, FwmTheme.TEXT_ON_ACCENT, false);
        }
        ctx.disableScissor();
        int maxScroll = this.maxContactScroll(entries.size());
        if (maxScroll > 0) {
            int trackX = this.x + listW - 3;
            int listH = this.contactsHeight();
            int thumbH = Math.max((int)10, (int)(listH * (listH / 24) / entries.size()));
            ctx.fill(trackX, top, trackX + 3, top + listH, FwmTheme.OFF);
            ctx.fill(trackX, top + (listH - thumbH) * this.contactScroll / maxScroll, trackX + 3, top + (listH - thumbH) * this.contactScroll / maxScroll + thumbH, FwmTheme.ACCENT);
        }
    }

    private void renderThread(GuiGraphicsExtractor ctx, Font font) {
        Conversation conversation;
        int tx = this.threadX();
        int tw = this.threadWidth();
        Conversation conversation2 = conversation = this.selected == null ? null : Messages.find(this.selected);
        if (conversation == null || conversation.messages.isEmpty()) {
            MutableComponent hint = this.selected == null ? Lang.of("fwm.messages.empty", new Object[0]) : Lang.of("fwm.messages.emptyChat", this.selected);
            int hintY = this.threadTop() + 4;
            for (FormattedCharSequence line : font.split((FormattedText)hint, tw - 4)) {
                ctx.text(font, line, tx, hintY, FwmTheme.TEXT_DIM, false);
                hintY += 10;
            }
            return;
        }
        this.rebuild(font, conversation, tw);
        int threadTop = this.threadTop();
        int viewH = this.threadBottom() - threadTop;
        int contentH = this.contentHeight();
        int maxScroll = Math.max((int)0, (int)(contentH - viewH));
        if (this.stickToBottom) {
            this.threadScroll = maxScroll;
            this.stickToBottom = false;
        }
        this.threadScroll = Math.max((int)0, (int)Math.min((int)maxScroll, (int)this.threadScroll));
        ctx.enableScissor(tx, threadTop, tx + tw, this.threadBottom());
        int cursor = threadTop - this.threadScroll + Math.max((int)0, (int)(viewH - contentH));
        for (Bubble bubble : this.layout) {
            this.drawBubble(ctx, font, bubble, tx, tw, cursor);
            cursor += bubble.height() + 4;
        }
        ctx.disableScissor();
        if (maxScroll > 0) {
            int trackX = tx + tw - 3;
            int thumbH = Math.max((int)10, (int)(viewH * viewH / contentH));
            int thumbY = threadTop + (viewH - thumbH) * this.threadScroll / maxScroll;
            ctx.fill(trackX, threadTop, trackX + 3, threadTop + viewH, FwmTheme.OFF);
            ctx.fill(trackX, thumbY, trackX + 3, thumbY + thumbH, FwmTheme.ACCENT);
        }
    }

    private void drawBubble(GuiGraphicsExtractor ctx, Font font, Bubble bubble, int tx, int tw, int top) {
        int bx;
        int n = bx = bubble.outgoing() ? tx + tw - bubble.width() - 3 - 2 : tx;
        if (bubble.header() != null) {
            int hx = bubble.outgoing() ? bx + bubble.width() - font.width(bubble.header()) : bx;
            ctx.text(font, bubble.header(), hx, top, FwmTheme.TEXT_DIM, false);
        }
        int bodyY = top + (bubble.header() == null ? 0 : 10);
        int bodyH = bubble.height() - (bubble.header() == null ? 0 : 10);
        FwmTheme.box(ctx, bx, bodyY, bubble.width(), bodyH, bubble.outgoing() ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, bubble.outgoing() ? FwmTheme.ACCENT : FwmTheme.OFF);
        int ly = bodyY + 4;
        for (FormattedCharSequence line : bubble.lines()) {
            ctx.text(font, line, bx + 4, ly, FwmTheme.TEXT, false);
            ly += 10;
        }
    }

    private void renderInput(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        int tx = this.threadX();
        int tw = this.threadWidth();
        boolean ready = this.selected != null;
        FwmTheme.box(ctx, tx, this.y + this.height - 14, tw, 14, FwmTheme.RAISED, this.input.isFocused() ? FwmTheme.ACCENT : FwmTheme.OFF);
        if (ready) {
            this.input.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        }
    }

    private List<Entry> entries() {
        String query = this.search.getValue().trim();
        if (this.newChat) {
            ArrayList out = new ArrayList();
            for (String name : PlayerSuggest.forQuery(query)) {
                out.add((Object)new Entry(name, Messages.find(name)));
            }
            return out;
        }
        String lower = query.toLowerCase(Locale.ROOT);
        ArrayList out = new ArrayList();
        for (Conversation conversation : Messages.conversations()) {
            if (!lower.isEmpty() && !conversation.partner.toLowerCase(Locale.ROOT).contains((CharSequence)lower)) continue;
            out.add((Object)new Entry(conversation.partner, conversation));
        }
        return out;
    }

    private int contentHeight() {
        int total = 0;
        for (Bubble bubble : this.layout) {
            total += bubble.height() + 4;
        }
        return Math.max((int)0, (int)(total - 4));
    }

    private void rebuild(Font font, Conversation conversation, int columnWidth) {
        int count = conversation.messages.size();
        if (columnWidth == this.layoutWidth && count == this.layoutCount && conversation.partner.equals((Object)this.layoutPartner)) {
            return;
        }
        this.layoutWidth = columnWidth;
        this.layoutCount = count;
        this.layoutPartner = conversation.partner;
        this.layout = new ArrayList();
        int maxBubble = Math.max((int)40, (int)Math.round((float)((float)(columnWidth - 3 - 2) * 0.78f)));
        int maxText = maxBubble - 8;
        Boolean previousSide = null;
        for (PrivateMessage message : conversation.messages) {
            boolean newBlock = previousSide == null || previousSide != message.outgoing;
            previousSide = message.outgoing;
            String header = newBlock ? (message.outgoing ? Lang.str("fwm.messages.you", new Object[0]) : conversation.partner) + "  " + MessagesPanel.clock(message.time) : null;
            List lines = font.split((FormattedText)Component.literal((String)message.text), maxText);
            if (lines.isEmpty()) continue;
            int textW = 0;
            for (FormattedCharSequence line : lines) {
                textW = Math.max((int)textW, (int)font.width(line));
            }
            int bubbleW = Math.min((int)maxBubble, (int)(textW + 8));
            int bubbleH = lines.size() * 10 + 8 - 2;
            if (header != null) {
                bubbleW = Math.max((int)bubbleW, (int)font.width(header));
            }
            this.layout.add((Object)new Bubble(message.outgoing, header, (List<FormattedCharSequence>)lines, bubbleW, bubbleH + (header == null ? 0 : 10)));
        }
        this.stickToBottom = true;
    }

    private static String clock(long time) {
        ZonedDateTime moment = Instant.ofEpochMilli((long)time).atZone(ZoneId.systemDefault());
        return String.format((String)"%02d:%02d", (Object[])new Object[]{moment.getHour(), moment.getMinute()});
    }

    private static Component trim(Font font, String value, int width) {
        if (font.width(value) <= width) {
            return Component.literal((String)value);
        }
        return Component.literal((String)(font.plainSubstrByWidth(value, Math.max((int)4, (int)(width - 6))) + "..."));
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (this.confirm.open()) {
            return this.confirm.mouseClicked(mouseX, mouseY);
        }
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        if (mouseX < (double)(this.x + this.listWidth())) {
            boolean onHeader = mouseY < (double)this.contactsTop();
            this.input.setFocused(false);
            if (onHeader && mouseX >= (double)this.buttonX()) {
                this.toggleNewChat();
                return true;
            }
            if (onHeader && this.showReadAll() && mouseX >= (double)this.readAllX()) {
                Messages.markAllRead();
                return true;
            }
            this.search.setFocused(onHeader);
            if (!onHeader) {
                if (mouseX >= (double)(this.x + this.listWidth() - 12) && this.deleteAt(mouseY)) {
                    return true;
                }
                this.selectAt(mouseY);
            }
            return true;
        }
        if (this.selected != null && mouseY >= (double)this.barY() && mouseY < (double)(this.barY() + this.barH())) {
            Font font = Minecraft.getInstance().font;
            if (mouseX >= (double)this.cogX()) {
                this.host.openSub(new MessageSettingsPanel());
                return true;
            }
            if (mouseX >= (double)this.tpaX(font) && mouseX < (double)(this.tpaHereX(font) - 3)) {
                this.askTeleport("tpa", "fwm.messages.tpaAsk");
                return true;
            }
            if (mouseX >= (double)this.tpaHereX(font) && mouseX < (double)(this.hideX(font) - 3)) {
                this.askTeleport("tpahere", "fwm.messages.tpahereAsk");
                return true;
            }
            if (mouseX >= (double)this.hideX(font) && mouseX < (double)(this.ignoreX(font) - 3)) {
                Messages.hide(this.selected);
                this.selected = null;
                return true;
            }
            if (mouseX >= (double)this.ignoreX(font)) {
                if (Messages.isIgnored(this.selected)) {
                    Messages.unignore(this.selected);
                } else {
                    Messages.ignore(this.selected);
                }
            }
            return true;
        }
        this.search.setFocused(false);
        this.input.setFocused(this.selected != null && mouseY >= (double)(this.y + this.height - 14));
        return true;
    }

    private void cross(GuiGraphicsExtractor ctx, int cx, int cy, int color) {
        for (int i = 0; i < 5; ++i) {
            ctx.fill(cx + i, cy + i, cx + i + 1, cy + i + 1, color);
            ctx.fill(cx + 4 - i, cy + i, cx + 5 - i, cy + i + 1, color);
        }
    }

    private boolean deleteAt(double mouseY) {
        List<Entry> entries = this.entries();
        int index = this.contactScroll + (int)((mouseY - (double)this.contactsTop()) / 24.0);
        if (index < 0 || index >= entries.size()) {
            return false;
        }
        Entry entry = (Entry)((Object)entries.get(index));
        if (entry.conversation() == null) {
            return false;
        }
        String partner = entry.name();
        this.confirm.ask((Component)Lang.of("fwm.messages.deleteAsk", partner), () -> {
            Messages.forget(partner);
            if (partner.equals((Object)this.selected)) {
                this.selected = null;
            }
        });
        return true;
    }

    private void selectAt(double mouseY) {
        List<Entry> entries = this.entries();
        int index = this.contactScroll + (int)((mouseY - (double)this.contactsTop()) / 24.0);
        if (index < 0 || index >= entries.size()) {
            return;
        }
        this.select((Entry)((Object)entries.get(index)));
    }

    private void select(Entry entry) {
        this.selected = entry.name();
        Messages.markRead(entry.conversation());
        if (this.newChat) {
            this.newChat = false;
            this.search.setValue("");
            this.search.setHint(this.searchHint());
            this.search.setFocused(false);
            this.input.setFocused(true);
            this.contactScroll = 0;
            PlayerSuggest.reset();
        }
        this.layoutCount = -1;
        this.stickToBottom = true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (mouseX < (double)(this.x + this.listWidth())) {
            this.contactScroll = Math.max((int)0, (int)Math.min((int)this.maxContactScroll(this.entries().size()), (int)(this.contactScroll - (int)Math.signum((double)vertical))));
            return true;
        }
        this.threadScroll = Math.max((int)0, (int)(this.threadScroll - (int)Math.signum((double)vertical) * 10 * 2));
        return true;
    }

    @Override
    public boolean isCapturingKeys() {
        return this.confirm.open();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        boolean enter;
        if (this.confirm.open()) {
            return this.confirm.keyPressed(keyCode);
        }
        boolean bl = enter = keyCode == 40 || keyCode == 88;
        if (this.search.isFocused()) {
            if (enter) {
                List<Entry> entries = this.entries();
                if (!entries.isEmpty()) {
                    this.select((Entry)((Object)entries.get(0)));
                    this.search.setFocused(false);
                    this.input.setFocused(true);
                }
                return true;
            }
            return this.search.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
        }
        if (!this.input.isFocused()) {
            return false;
        }
        if (enter) {
            String text = this.input.getValue();
            if (!text.isBlank() && this.selected != null) {
                Messages.send(this.selected, text);
                this.input.setValue("");
            }
            return true;
        }
        return this.input.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent chr) {
        if (this.search.isFocused()) {
            return this.search.charTyped(chr);
        }
        return this.input.isFocused() && this.input.charTyped(chr);
    }

    private record Entry(String name, Conversation conversation) {
    }

    private record Bubble(boolean outgoing, String header, List<FormattedCharSequence> lines, int width, int height) {
    }
}
