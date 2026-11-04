/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.Comparator
 *  java.util.List
 *  java.util.Locale
 *  java.util.Objects
 *  net.minecraft.ChatFormatting
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
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.friendswithmoney.fwm.net.MemberData;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.RankColors;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.friendswithmoney.fwm.ui.panel.PanelHost;
import net.minecraft.ChatFormatting;
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

public class MembersPanel
extends Panel {
    private static final int PREVIEW_H = 15;
    private static final int SEARCH_H = 15;
    private static final int ROW_H = 11;
    private static final int FOOTER_H = 11;
    private static final int SCROLLBAR_W = 2;
    private final List<Line> lines = new ArrayList();
    private int builtFor = -1;
    private String builtQuery = "";
    private int matches;
    private final PanelHost host;
    private EditBox search;
    private String query = "";
    private int listY;
    private int listH;
    private int rowsVisible;
    private int scroll;

    public MembersPanel(PanelHost host) {
        this.host = host;
    }

    @Override
    public Component title() {
        return Lang.of("fwm.tab.members", new Object[0]);
    }

    @Override
    protected void onResize() {
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
        Font font = Minecraft.getInstance().font;
        int searchY = this.y + 15 + 3;
        this.search = new EditBox(font, this.x + 5, searchY + 2, this.width - 10, 11, (Component)Lang.of("fwm.members.search", new Object[0]));
        this.search.setBordered(false);
        this.search.setMaxLength(32);
        this.search.setTextColor(FwmTheme.TEXT);
        this.search.setHint((Component)Lang.of("fwm.members.search", new Object[0]));
        this.search.setValue(this.query);
        this.search.setResponder(text -> {
            this.query = text == null ? "" : text.trim();
            this.scroll = 0;
        });
        this.host.attach((AbstractWidget)this.search);
        this.listY = searchY + 15 + 3;
        this.listH = Math.max((int)11, (int)(this.y + this.height - 11 - 2 - this.listY));
        this.rowsVisible = Math.max((int)1, (int)(this.listH / 11));
        this.scroll = Math.min((int)this.scroll, (int)this.maxScroll());
    }

    private void build() {
        List<MemberData.Member> members = Members.list();
        if (!(this.builtFor != members.size() || !this.builtQuery.equals((Object)this.query) || this.lines.isEmpty() && this.query.isEmpty())) {
            return;
        }
        String needle = this.query.toLowerCase(Locale.ROOT);
        ArrayList sorted = new ArrayList();
        for (MemberData.Member m2 : members) {
            if (!needle.isEmpty() && !MembersPanel.contains(m2.name, needle) && !MembersPanel.contains(m2.clan, needle) && !MembersPanel.contains(m2.role, needle)) continue;
            sorted.add((Object)m2);
        }
        sorted.sort(Comparator.comparingInt(m -> Members.clanOrder(m.clan)).thenComparing(m -> m.clan == null ? "" : m.clan).thenComparingInt(m -> Members.rankIndex(m.role)).thenComparing(m -> m.name == null ? "" : m.name.toLowerCase(Locale.ROOT)));
        this.lines.clear();
        String clan = null;
        for (MemberData.Member member : sorted) {
            String group;
            String string = group = member.clan == null ? "?" : member.clan;
            if (!group.equals(clan)) {
                clan = group;
                this.lines.add((Object)new Line(group, null));
            }
            this.lines.add((Object)new Line(group, member));
        }
        this.builtFor = members.size();
        this.builtQuery = this.query;
        this.matches = sorted.size();
        this.scroll = Math.min((int)this.scroll, (int)this.maxScroll());
    }

    private static boolean contains(String text, String needle) {
        return text != null && text.toLowerCase(Locale.ROOT).contains((CharSequence)needle);
    }

    private int maxScroll() {
        return Math.max((int)0, (int)(this.lines.size() - this.rowsVisible));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.build();
        this.renderPreview(ctx, font);
        FwmTheme.box(ctx, this.x, this.y + 15 + 3, this.width, 15, FwmTheme.RAISED, this.search.isFocused() ? FwmTheme.ACCENT : FwmTheme.OFF);
        this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
        this.renderList(ctx, font);
        this.renderScrollbar(ctx);
        this.renderFooter(ctx, font);
    }

    private void renderPreview(GuiGraphicsExtractor ctx, Font font) {
        MutableComponent heading = Component.literal((String)"FWM").withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD}).append((Component)Component.literal((String)" ").append((Component)Lang.of("fwm.members.header", new Object[0])).withStyle(ChatFormatting.WHITE));
        int n = this.x + (this.width - font.width((FormattedText)heading)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)heading, n, this.y + (15 - 9) / 2 + 1, FwmTheme.TEXT, false);
        FwmTheme.divider(ctx, this.x, this.y + 15, this.width);
    }

    private void renderList(GuiGraphicsExtractor ctx, Font font) {
        int index;
        if (this.lines.isEmpty()) {
            MutableComponent note = Lang.of(!this.query.isEmpty() && !Members.list().isEmpty() ? "fwm.items.noMatch" : (Members.state() == Members.State.ERROR ? "fwm.board.offline" : "fwm.board.loading"), new Object[0]);
            ctx.text(font, (Component)note, this.x + (this.width - font.width((FormattedText)note)) / 2, this.listY + this.listH / 2 - 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        String self = FwmGfx.playerName();
        int right = this.x + this.width - 2 - 4;
        for (int i = 0; i < this.rowsVisible && (index = this.scroll + i) < this.lines.size(); ++i) {
            String role;
            Line line = (Line)((Object)this.lines.get(index));
            int rowY = this.listY + i * 11;
            Objects.requireNonNull((Object)font);
            int textY = rowY + (11 - 9) / 2 + 1;
            if (line.isHeading()) {
                ctx.text(font, (Component)Component.literal((String)line.clan()), this.x + 2, textY, FwmTheme.ACCENT, false);
                FwmTheme.divider(ctx, this.x + 2 + font.width(line.clan()) + 4, rowY + 5, right - this.x - font.width(line.clan()) - 6);
                continue;
            }
            MemberData.Member member = line.member();
            String name = member.name == null ? "?" : member.name;
            boolean mine = name.equalsIgnoreCase(self);
            if (mine) {
                ctx.fill(this.x, rowY, this.x + this.width - 2, rowY + 11, FwmTheme.HOVER);
            }
            ctx.text(font, (Component)Component.literal((String)name), this.x + 8, textY, mine ? FwmTheme.ACCENT : FwmTheme.TEXT, false);
            String string = role = member.role == null ? "" : member.role;
            if (role.isEmpty()) continue;
            int color = RankColors.flat(Members.rankIndex(role));
            String shown = font.plainSubstrByWidth(role, Math.max((int)20, (int)(this.width / 2)));
            ctx.text(font, (Component)Component.literal((String)shown), right - font.width(shown), textY, color, false);
        }
    }

    private void renderScrollbar(GuiGraphicsExtractor ctx) {
        if (this.maxScroll() <= 0) {
            return;
        }
        int trackX = this.x + this.width - 2;
        ctx.fill(trackX, this.listY, trackX + 2, this.listY + this.listH, FwmTheme.OFF);
        int thumbH = Math.max((int)12, (int)(this.listH * this.rowsVisible / Math.max((int)1, (int)this.lines.size())));
        int thumbY = this.listY + (this.listH - thumbH) * this.scroll / this.maxScroll();
        ctx.fill(trackX, thumbY, trackX + 2, thumbY + thumbH, FwmTheme.ACCENT);
    }

    private void renderFooter(GuiGraphicsExtractor ctx, Font font) {
        int barY = this.y + this.height - 11;
        FwmTheme.divider(ctx, this.x, barY - 1, this.width);
        MutableComponent left = this.query.isEmpty() ? Lang.of("fwm.members.count", Members.list().size()) : Lang.of("fwm.members.countFiltered", this.matches, Members.list().size());
        ctx.text(font, (Component)left, this.x + 4, barY + 2, FwmTheme.TEXT_DIM, false);
        long age = Members.ageMinutes();
        MutableComponent right = age < 0L ? Lang.of("fwm.board.offline", new Object[0]) : Lang.of("fwm.board.updated", age);
        ctx.text(font, (Component)right, this.x + this.width - 4 - font.width((FormattedText)right), barY + 2, FwmTheme.TEXT_DIM, false);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        int searchY = this.y + 15 + 3;
        this.search.setFocused(mouseY >= (double)searchY && mouseY < (double)(searchY + 15));
        return true;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return this.search != null && this.search.isFocused() && this.search.keyPressed(new KeyEvent(keyCode, scanCode, modifiers));
    }

    @Override
    public boolean charTyped(CharacterEvent input) {
        return this.search != null && this.search.isFocused() && this.search.charTyped(input);
    }

    @Override
    public void onHidden() {
        if (this.search != null) {
            this.host.detach((AbstractWidget)this.search);
        }
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        this.scroll = Math.max((int)0, (int)Math.min((int)this.maxScroll(), (int)(this.scroll - (int)Math.signum((double)vertical) * 3)));
        return true;
    }

    private record Line(String clan, MemberData.Member member) {
        boolean isHeading() {
            return this.member == null;
        }
    }
}
