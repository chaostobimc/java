/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Objects
 *  net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry
 *  net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.renderer.RenderPipelines
 *  net.minecraft.locale.Language
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 *  net.minecraft.util.FormattedCharSequence
 *  net.minecraft.world.item.ItemStack
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.hud.VanillaHudElements;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.chat.Messages;
import net.friendswithmoney.fwm.chat.SecondChat;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.InvValue;
import net.friendswithmoney.fwm.game.ToolGuard;
import net.friendswithmoney.fwm.game.TpaWatch;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.money.MoneyEvents;
import net.friendswithmoney.fwm.money.MoneyStore;
import net.friendswithmoney.fwm.net.MarketData;
import net.friendswithmoney.fwm.net.ModNews;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.friendswithmoney.fwm.stats.SellStats;
import net.friendswithmoney.fwm.ui.DesignHost;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmNotice;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.RtpMinimap;
import net.friendswithmoney.fwm.ui.SpawnerCard;
import net.friendswithmoney.fwm.ui.panel.MarketPanel;
import net.friendswithmoney.fwm.ui.widget.Anim;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.locale.Language;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

public final class FwmHud {
    private static final Identifier ID = Identifier.fromNamespaceAndPath((String)"fwm", (String)"watermark");
    private static final long FAREWELL_MS = 3000L;
    private static final long FLARE_MS = 700L;
    private static final int WARN_ICON = 16;
    private static final int WARN_CARD_H = 33;
    private static final float WARN_SCALE = 1.0f;
    private static final int MARGIN = 6;
    private static final int PADDING = 5;
    private static final int GAP = 5;
    private static final int LOGO_H = 20;
    private static final int CARD_H = 32;
    private static final int LINE_H = 11;
    private static final int STACK_GAP = 4;
    private static final long TOAST_MS = 5000L;
    private static final int TOAST_TEXT_W = 120;
    private static final int CHAT_RESERVE = 54;
    private static final int TOAST_TOP = 64;
    private static final int ENVELOPE_TOP = 96;
    private static final int TPA_TOP = 43;
    public static final int MINIMAP_TOP = 90;
    public static final int PRICEPING_TOP = 24;
    private static final long PRICEPING_MS = 20000L;
    private static long pingAt;
    private static MarketData.Item pingItem;
    private static String pingName;
    private static String pingPrice;
    private static String pingSeller;
    private static long pingAmount;
    private static boolean pingOrder;
    private static final int TPA_FLARE_S = 10;
    private static final int TPA_SOON = -2056032;
    private static final int ENVELOPE_W = 11;
    private static final int ENVELOPE_H = 8;
    private static final float SCALE = 1.25f;
    private static long switchedOffAt;
    private static long blockedAt;
    private static long greetAt;
    private static final long GREET_MS = 30000L;
    public static final String ID_MACRO = "macro";
    public static final String ID_TOAST = "toast";
    public static final String ID_ENVELOPE = "envelope";
    public static final String ID_TOOL = "tool";
    public static final String ID_SECOND_CHAT = "secondchat";
    public static final String ID_TPA = "tpa";
    public static final String ID_MINIMAP = "minimap";
    public static final String ID_PRICEPING = "priceping";
    public static final String ID_MONEY = "money";
    public static final String ID_INVVALUE = "invvalue";
    public static final String ID_AUCTION = "auction";
    public static final String ID_SPAWNERS = "spawners";
    public static final int MONEY_TOP = 120;
    private static final Map<String, int[]> bounds;
    private static final Identifier FIGURE;
    private static final int FIGURE_W = 256;
    private static final int FIGURE_H = 498;
    private static final int FIGURE_SHEET_W = 256;
    private static final int FIGURE_SHEET_H = 512;
    private static final float FIGURE_RATIO = 0.5140562f;
    private static final List<ChatHit> chatHits;
    private static int chatScroll;
    private static int chatMaxScroll;
    private static int chatWrappedCount;
    private static final int SCROLLBAR_W = 2;
    private static final int SCROLLBAR_GAP = 2;
    public static final int AUCTION_TOP = 180;
    private static final int AUCTION_W = 160;
    private static final long AUCTION_SOLD_MS = 5000L;
    private static int auctionBids;
    private static long auctionFlashUntil;
    private static long auctionEndedAt;
    private static boolean auctionWasRunning;
    public static final int SPAWNERS_TOP = 40;
    private static String toastFrom;
    private static String toastText;
    private static long toastAt;

    private FwmHud() {
    }

    public static void register() {
        HudElementRegistry.attachElementAfter((Identifier)VanillaHudElements.MISC_OVERLAYS, (Identifier)ID, (ctx, tickCounter) -> FwmHud.render(ctx));
    }

    public static void showDeactivated() {
        switchedOffAt = System.currentTimeMillis();
    }

    public static void showBlocked() {
        blockedAt = System.currentTimeMillis();
    }

    public static void showGreeting() {
        greetAt = System.currentTimeMillis();
    }

    public static Map<String, int[]> bounds() {
        return new LinkedHashMap(bounds);
    }

    /*
     * Exception decompiling
     */
    public static FwmConfig.HudSpot spot(String id) {
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

    public static float scaleOf(String id) {
        return ID_MACRO.equals((Object)id) ? 1.0f : FwmHud.spot(id).scale();
    }

    private static int[] place(GuiGraphicsExtractor ctx, String id, int w, int h, int stackX, int stackTop) {
        FwmConfig.HudSpot moved = FwmHud.spot(id);
        int x = stackX;
        int y = stackTop;
        if (moved.moved()) {
            x = Math.round((float)(moved.x * (float)Math.max((int)0, (int)(ctx.guiWidth() - w))));
            y = Math.round((float)(moved.y * (float)Math.max((int)0, (int)(ctx.guiHeight() - h))));
        }
        bounds.put((Object)id, (Object)new int[]{x, y, w, h});
        return new int[]{x, y, moved.moved() ? 0 : h + 4};
    }

    private static void render(GuiGraphicsExtractor ctx) {
        Minecraft client = Minecraft.getInstance();
        if (client.gui.hud.isHidden()) {
            return;
        }
        FwmHud.drawMacroCard(ctx, client, 6);
        FwmHud.drawMessage(ctx, client, 64);
        FwmHud.drawEnvelope(ctx, client, 96);
        FwmHud.drawToolWarning(ctx, client);
        FwmHud.drawTpa(ctx, client);
        FwmHud.drawMinimap(ctx, client);
        FwmHud.drawPricePing(ctx, client);
        int moneyBottom = FwmHud.drawMoney(ctx, client);
        FwmHud.drawInvValue(ctx, client, moneyBottom);
        FwmHud.drawAuction(ctx, client);
        FwmHud.drawSpawnerPrices(ctx, client);
        FwmHud.drawSecondChat(ctx, client);
        FwmHud.drawGreeting(ctx, client);
        int newsBottom = FwmHud.drawNews(ctx, client);
        FwmHud.drawGiveawayCard(ctx, client, newsBottom);
    }

    private static void drawGiveawayCard(GuiGraphicsExtractor ctx, Minecraft client, int top) {
        FwmNotice.Notice card = FwmNotice.current();
        if (card == null) {
            return;
        }
        Font font = client.font;
        int color = card.color();
        int textW = 170;
        MutableComponent head = Component.literal((String)card.head()).withStyle(ChatFormatting.BOLD);
        String name = card.title();
        if (font.width((FormattedText)Component.literal((String)name).withStyle(ChatFormatting.BOLD)) > textW) {
            while (!name.isEmpty() && font.width((FormattedText)Component.literal((String)(name + "\u2026")).withStyle(ChatFormatting.BOLD)) > textW) {
                name = name.substring(0, name.length() - 1);
            }
            name = name + "\u2026";
        }
        MutableComponent title = Component.literal((String)name).withStyle(ChatFormatting.BOLD);
        List lines = font.split((FormattedText)Component.literal((String)card.line()), textW);
        if (lines.size() > 3) {
            lines = lines.subList(0, 3);
        }
        int cardW = 24 + textW + 5;
        int cardH = 5 + 11 * ((name.isEmpty() ? 1 : 2) + lines.size()) + 5 + 2;
        int x = ctx.guiWidth() - cardW - 6;
        int y = top;
        LauncherGfx.card(ctx, x, y, cardW, cardH, 6);
        int gx = x + 5;
        int gy = y + 5;
        LauncherGfx.round(ctx, gx, gy, 14, 14, 4, 0x33000000 | color & 0xFFFFFF);
        LauncherGfx.ring(ctx, gx, gy, 14, 14, 4, color);
        LauncherGfx.infoGlyph(ctx, gx, gy, color);
        int tx = x + 5 + 14 + 5;
        int ty = y + 5;
        ctx.text(font, (Component)head, tx, ty, color);
        if (!name.isEmpty()) {
            ctx.text(font, (Component)title, tx, ty += 11, FwmTheme.TEXT);
        }
        for (int i = 0; i < lines.size(); ++i) {
            ctx.text(font, (FormattedCharSequence)lines.get(i), tx, ty + 11 * (1 + i), FwmTheme.TEXT_DIM);
        }
        float left = 1.0f - FwmNotice.progress();
        ctx.fill(x + 6, y + cardH - 3, x + 6 + Math.round((float)((float)(cardW - 12) * left)), y + cardH - 2, color);
    }

    private static int drawNews(GuiGraphicsExtractor ctx, Minecraft client) {
        ModNews.Entry entry = ModNews.card();
        if (entry == null) {
            return 6;
        }
        Font font = client.font;
        int textW = 170;
        MutableComponent head = Lang.of("fwm.news.card", new Object[0]).withStyle(ChatFormatting.BOLD);
        MutableComponent title = Component.literal((String)(entry.title == null ? "" : entry.title)).withStyle(ChatFormatting.BOLD);
        List lines = font.split((FormattedText)Component.literal((String)(entry.text == null ? "" : entry.text)), textW);
        if (lines.size() > 6) {
            lines = lines.subList(0, 6);
        }
        int cardW = 24 + textW + 5;
        int cardH = 5 + 11 * (2 + lines.size()) + 5 + 2;
        int x = ctx.guiWidth() - cardW - 6;
        int y = 6;
        LauncherGfx.card(ctx, x, y, cardW, cardH, 6);
        LauncherGfx.round(ctx, x + 5, y + 5, 14, 14, 4, 0x33FFFFFF);
        LauncherGfx.ring(ctx, x + 5, y + 5, 14, 14, 4, FwmTheme.ACCENT);
        LauncherGfx.infoGlyph(ctx, x + 5, y + 5, FwmTheme.ACCENT);
        int tx = x + 5 + 14 + 5;
        int ty = y + 5;
        ctx.text(font, (Component)head, tx, ty, FwmTheme.ACCENT);
        ctx.text(font, (Component)title, tx, ty + 11, FwmTheme.TEXT);
        for (int i = 0; i < lines.size(); ++i) {
            ctx.text(font, (FormattedCharSequence)lines.get(i), tx, ty + 11 * (2 + i), FwmTheme.TEXT_DIM);
        }
        float left = 1.0f - ModNews.cardProgress();
        ctx.fill(x + 6, y + cardH - 3, x + 6 + Math.round((float)((float)(cardW - 12) * left)), y + cardH - 2, FwmTheme.ACCENT);
        return y + cardH + 4;
    }

    private static void drawGreeting(GuiGraphicsExtractor ctx, Minecraft client) {
        if (greetAt == 0L) {
            return;
        }
        long age = System.currentTimeMillis() - greetAt;
        if (age >= 30000L) {
            return;
        }
        Font font = client.font;
        MutableComponent line1 = Lang.of("fwm.tutorial.greet", new Object[0]).withStyle(ChatFormatting.BOLD);
        MutableComponent line2 = Lang.of("fwm.tutorial.greetSub", Keybinds.menuKeyName());
        int figH = 76;
        int figW = Math.max((int)1, (int)Math.round((float)((float)figH * 0.5140562f)));
        int textW = Math.max((int)font.width((FormattedText)line1), (int)font.width((FormattedText)line2));
        int cardW = 5 + textW + 5;
        int cardH = 32;
        float in = Math.min((float)1.0f, (float)((float)age / 200.0f));
        float out = Math.min((float)1.0f, (float)Math.max((float)0.0f, (float)((float)(30000L - age) / 300.0f)));
        float slide = Anim.ease(Math.min((float)in, (float)out));
        int bottom = ctx.guiHeight() - 6;
        int figX = Math.round((float)((float)ctx.guiWidth() - slide * (float)(figW + 6)));
        int figY = bottom - figH;
        ctx.blit(RenderPipelines.GUI_TEXTURED, FIGURE, figX, figY, 256.0f, 0.0f, figW, figH, -256, 498, 256, 512);
        int cardX = figX - 5 - cardW;
        int cardY = bottom - cardH - 6;
        FwmTheme.panel(ctx, cardX, cardY, cardW, cardH);
        ctx.text(font, (Component)line1, cardX + 5, cardY + 5, FwmTheme.ACCENT);
        ctx.text(font, (Component)line2, cardX + 5, cardY + 5 + 11, FwmTheme.TEXT);
    }

    private static int backingColour() {
        FwmConfig config = FwmConfig.INSTANCE;
        if (!config.secondChatBackground) {
            return 0;
        }
        int percent = Math.max((int)0, (int)Math.min((int)100, (int)config.secondChatBackgroundOpacity));
        int alpha = Math.round((float)((float)(255 * percent) / 100.0f));
        return alpha == 0 ? 0 : alpha << 24;
    }

    public static List<ChatHit> chatHits() {
        return new ArrayList(chatHits);
    }

    public static boolean scrollSecondChat(double mouseX, double mouseY, double vertical) {
        if (!Features.isOn("fwm.tab.secondchat") || vertical == 0.0) {
            return false;
        }
        if (chatHits.isEmpty() || chatMaxScroll <= 0) {
            return false;
        }
        int[] box = (int[])bounds.get((Object)ID_SECOND_CHAT);
        if (box == null) {
            return false;
        }
        if (mouseX < (double)box[0] || mouseX >= (double)(box[0] + box[2] + 2 + 2)) {
            return false;
        }
        if (mouseY < (double)box[1] || mouseY >= (double)(box[1] + box[3])) {
            return false;
        }
        int steps = (int)Math.round((double)vertical);
        if (steps == 0) {
            steps = vertical > 0.0 ? 1 : -1;
        }
        chatScroll = Math.max((int)0, (int)Math.min((int)chatMaxScroll, (int)(chatScroll + steps)));
        return true;
    }

    private static void drawSecondChat(GuiGraphicsExtractor ctx, Minecraft client) {
        chatHits.clear();
        chatMaxScroll = 0;
        if (!Features.isOn("fwm.tab.secondchat")) {
            return;
        }
        List<SecondChat.Line> all = SecondChat.lines();
        if (all.isEmpty()) {
            return;
        }
        Font font = client.font;
        long now = System.currentTimeMillis();
        boolean chatOpen = client.gui.screen() instanceof ChatScreen;
        ArrayList wrapped = new ArrayList();
        for (SecondChat.Line line : all) {
            if (!chatOpen && now - line.at() > 10000L) continue;
            font.getSplitter().splitLines((FormattedText)SevenTv.transform(line.text()), FwmHud.chatWidth(), Style.EMPTY, (arg_0, arg_1) -> FwmHud.lambda$drawSecondChat$0((List)wrapped, arg_0, arg_1));
        }
        if (wrapped.isEmpty()) {
            return;
        }
        int max = FwmConfig.INSTANCE.secondChatLines;
        if (!chatOpen) {
            chatScroll = 0;
        } else if (chatScroll > 0 && wrapped.size() > chatWrappedCount) {
            chatScroll += wrapped.size() - chatWrappedCount;
        }
        chatWrappedCount = wrapped.size();
        chatMaxScroll = chatOpen ? Math.max((int)0, (int)(wrapped.size() - max)) : 0;
        chatScroll = Math.max((int)0, (int)Math.min((int)chatMaxScroll, (int)chatScroll));
        int end = wrapped.size() - chatScroll;
        List shown = wrapped.subList(Math.max((int)0, (int)(end - max)), end);
        float scale = FwmHud.scaleOf(ID_SECOND_CHAT);
        int width = FwmHud.chatWidth();
        int drawnW = Math.round((float)((float)width * scale));
        int drawnH = Math.round((float)((float)(shown.size() * 11) * scale));
        int stackTop = Math.max((int)6, (int)(ctx.guiHeight() - 54 - drawnH));
        int stackX = Math.max((int)6, (int)(ctx.guiWidth() - 6 - drawnW));
        int[] at = FwmHud.place(ctx, ID_SECOND_CHAT, drawnW, drawnH, stackX, stackTop);
        FwmHud.drawChatLines(ctx, font, (List<FormattedText>)shown, at[0], at[1], scale, true);
        if (chatMaxScroll > 0) {
            int trackX = at[0] + drawnW + 2;
            int thumbH = Math.max((int)4, (int)(drawnH * shown.size() / wrapped.size()));
            int thumbY = at[1] + (drawnH - thumbH) * (chatMaxScroll - chatScroll) / chatMaxScroll;
            ctx.fill(trackX, at[1], trackX + 2, at[1] + drawnH, 0x40FFFFFF);
            ctx.fill(trackX, thumbY, trackX + 2, thumbY + thumbH, FwmTheme.ACCENT);
        }
    }

    public static int chatWidth() {
        return Math.max((int)40, (int)FwmConfig.INSTANCE.secondChatWidth);
    }

    private static int alignX(int width, int lineWidth) {
        return switch (FwmConfig.INSTANCE.secondChatAlign) {
            case 0 -> 0;
            case 2 -> width - lineWidth;
            default -> (width - lineWidth) / 2;
        };
    }

    private static void drawChatLines(GuiGraphicsExtractor ctx, Font font, List<FormattedText> lines, int left, int top, float scale, boolean record) {
        int width = FwmHud.chatWidth();
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)left, (float)top);
        matrices.scale(scale, scale);
        int lineY = 0;
        int backing = FwmHud.backingColour();
        for (FormattedText line : lines) {
            FormattedCharSequence ordered = Language.getInstance().getVisualOrder(line);
            int lineWidth = font.width(line);
            int lineX = FwmHud.alignX(width, lineWidth);
            if (backing != 0) {
                ctx.fill(0, lineY - 1, width, lineY + 11 - 1, backing);
            }
            ctx.text(font, ordered, lineX, lineY, -1);
            if (record) {
                chatHits.add((Object)new ChatHit(left + Math.round((float)((float)lineX * scale)), top + Math.round((float)((float)lineY * scale)), Math.round((float)((float)lineWidth * scale)), Math.round((float)(11.0f * scale)), line));
            }
            lineY += 11;
        }
        matrices.popMatrix();
    }

    public static List<FormattedText> sampleLines(Font font) {
        ArrayList out = new ArrayList();
        font.getSplitter().splitLines((FormattedText)Lang.of("fwm.hudedit.sample", FwmHud.sampleName()), FwmHud.chatWidth(), Style.EMPTY, (arg_0, arg_1) -> FwmHud.lambda$sampleLines$0((List)out, arg_0, arg_1));
        return out;
    }

    private static String sampleName() {
        String name = FwmGfx.playerName();
        return name.isEmpty() ? "HydraVB" : name;
    }

    public static int[] sampleSize(Font font, float scale) {
        int lines = Math.max((int)1, (int)FwmHud.sampleLines(font).size());
        return new int[]{Math.round((float)((float)FwmHud.chatWidth() * scale)), Math.round((float)((float)(lines * 11) * scale))};
    }

    public static int[] drawSample(GuiGraphicsExtractor ctx, Font font, int left, int top, float scale) {
        FwmHud.drawChatLines(ctx, font, FwmHud.sampleLines(font), left, top, scale, false);
        return FwmHud.sampleSize(font, scale);
    }

    private static void drawMacroCard(GuiGraphicsExtractor ctx, Minecraft client, int top) {
        boolean blocked;
        boolean active = FwmConfig.INSTANCE.enabled;
        boolean farewell = !active && switchedOffAt > 0L && System.currentTimeMillis() - switchedOffAt < 3000L;
        boolean bl = blocked = !active && blockedAt > 0L && System.currentTimeMillis() - blockedAt < 3000L;
        if (!(active || farewell || blocked)) {
            return;
        }
        FwmHud.draw(ctx, client, active, blocked, top);
    }

    private static void drawTpa(GuiGraphicsExtractor ctx, Minecraft client) {
        if (!TpaWatch.active()) {
            return;
        }
        Font font = client.font;
        int left = TpaWatch.remaining();
        boolean soon = left <= 10;
        MutableComponent title = Lang.of(TpaWatch.kind() == TpaWatch.Kind.TPAHERE ? "fwm.tpa.cardTitleHere" : "fwm.tpa.cardTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
        MutableComponent sub = Lang.of("fwm.tpa.cardSub", TpaWatch.target(), left);
        MutableComponent hint = Lang.of("fwm.tpa.cardHint", new Object[0]);
        int textW = Math.max((int)font.width((FormattedText)title), (int)Math.max((int)font.width((FormattedText)sub), (int)font.width((FormattedText)hint)));
        int cardW = 5 + textW + 5;
        int cardH = 41;
        float scale = FwmHud.scaleOf(ID_TPA);
        int[] at = FwmHud.place(ctx, ID_TPA, Math.round((float)((float)cardW * scale)), Math.round((float)((float)cardH * scale)), 6, 43);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        int top = 4;
        ctx.text(font, (Component)title, 5, top, soon ? -2056032 : FwmTheme.TEXT);
        ctx.text(font, (Component)sub, 5, top + 11, soon ? -2056032 : FwmTheme.ACCENT);
        ctx.text(font, (Component)hint, 5, top + 22, FwmTheme.TEXT_DIM);
        matrices.popMatrix();
    }

    public static void showPricePing(FwmConfig.PricePing ping, String name, String price, String seller, long amount) {
        MarketData.Item item = new MarketData.Item();
        item.itemKey = ping.itemKey;
        item.baseItemKey = ping.baseItemKey;
        item.icon = ping.icon;
        pingItem = item;
        pingName = name;
        pingPrice = price;
        pingSeller = seller;
        pingAmount = amount;
        pingOrder = ping.order;
        pingAt = System.currentTimeMillis();
    }

    private static void drawPricePing(GuiGraphicsExtractor ctx, Minecraft client) {
        if (pingItem == null || System.currentTimeMillis() - pingAt >= 20000L) {
            return;
        }
        Font font = client.font;
        MutableComponent title = Lang.of(pingOrder ? "fwm.orderping.cardTitle" : "fwm.priceping.cardTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
        MutableComponent line = Lang.of("fwm.priceping.cardLine", pingName, pingPrice + (String)(pingAmount > 1L ? " (" + pingAmount + "x)" : ""));
        MutableComponent from = Lang.of(pingOrder ? "fwm.orderping.cardFrom" : "fwm.priceping.cardFrom", pingSeller);
        int textW = Math.max((int)font.width((FormattedText)title), (int)Math.max((int)font.width((FormattedText)line), (int)font.width((FormattedText)from)));
        int cardW = 26 + textW + 5;
        int cardH = 41;
        float scale = FwmHud.scaleOf(ID_PRICEPING);
        int drawnW = Math.round((float)((float)cardW * scale));
        int[] at = FwmHud.place(ctx, ID_PRICEPING, drawnW, Math.round((float)((float)cardH * scale)), (ctx.guiWidth() - drawnW) / 2, 24);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        MarketPanel.drawIcon(ctx, pingItem, 5, (cardH - 16) / 2);
        int textX = 26;
        int top = 4;
        ctx.text(font, (Component)title, textX, top, FwmTheme.ACCENT);
        ctx.text(font, (Component)line, textX, top + 11, FwmTheme.TEXT);
        ctx.text(font, (Component)from, textX, top + 22, FwmTheme.TEXT_DIM);
        matrices.popMatrix();
    }

    private static int drawMoney(GuiGraphicsExtractor ctx, Minecraft client) {
        FwmConfig config = FwmConfig.INSTANCE;
        if (!config.moneyHud || !MoneyEvents.active(client)) {
            return 120;
        }
        Font font = client.font;
        long user = MoneyStore.user();
        long today = MoneyStore.todayNet();
        long goal = config.moneyGoal * 100L;
        String sessionLabel = Lang.t("Sitzung", "Session");
        String todayLabel = Lang.t("Heute", "Today");
        int labelW = Math.max((int)font.width(sessionLabel), (int)font.width(todayLabel)) + 6;
        String s1 = FwmHud.signedMoney(user);
        String s2 = FwmHud.signedMoney(today);
        int amountW = Math.max((int)font.width(s1), (int)font.width(s2));
        int barW = 50;
        String percent = goal > 0L ? Math.max((long)0L, (long)Math.min((long)999L, (long)(today * 100L / goal))) + " %" : "";
        int goalW = goal > 0L ? 6 + barW + 4 + font.width(percent) : 0;
        int cardW = 5 + labelW + amountW + goalW + 5;
        int cardH = 30;
        float scale = FwmHud.scaleOf(ID_MONEY);
        int drawnW = Math.round((float)((float)cardW * scale));
        int drawnH = Math.round((float)((float)cardH * scale));
        int[] at = FwmHud.place(ctx, ID_MONEY, drawnW, drawnH, ctx.guiWidth() - drawnW - 6, 120);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        int top = 4;
        int ax = 5 + labelW;
        ctx.text(font, sessionLabel, 5, top, FwmTheme.TEXT_DIM);
        ctx.text(font, s1, ax, top, user < 0L ? -2067340 : -9707377);
        ctx.text(font, todayLabel, 5, top + 11, FwmTheme.TEXT_DIM);
        ctx.text(font, s2, ax, top + 11, today < 0L ? -2067340 : -9707377);
        if (goal > 0L) {
            int bx = ax + amountW + 6;
            int by = top + 11 + 1;
            int filled = (int)Math.max((long)0L, (long)Math.min((long)barW, (long)(today * (long)barW / goal)));
            ctx.fill(bx, by, bx + barW, by + 6, 0x55FFFFFF);
            ctx.fill(bx, by, bx + filled, by + 6, today >= goal ? -865972 : FwmTheme.ACCENT);
            ctx.text(font, percent, bx + barW + 4, top + 11, FwmTheme.TEXT_DIM);
        }
        matrices.popMatrix();
        return at[1] + (FwmHud.spot(ID_MONEY).moved() ? 0 : drawnH + 4);
    }

    private static String signedMoney(long cents) {
        if (FwmConfig.INSTANCE.moneyHideAmounts) {
            return "\u2022\u2022\u2022";
        }
        return (cents < 0L ? "-" : "+") + Fmt.compactMoney(Math.abs((long)cents));
    }

    private static void drawInvValue(GuiGraphicsExtractor ctx, Minecraft client, int stackTop) {
        if (!FwmConfig.INSTANCE.invValueHud || !InvValue.active() || client.player == null) {
            return;
        }
        Font font = client.font;
        String text = InvValue.line();
        int cardW = 5 + font.width(text) + 5;
        int cardH = 19;
        float scale = FwmHud.scaleOf(ID_INVVALUE);
        int drawnW = Math.round((float)((float)cardW * scale));
        int[] at = FwmHud.place(ctx, ID_INVVALUE, drawnW, Math.round((float)((float)cardH * scale)), ctx.guiWidth() - drawnW - 6, stackTop);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        ctx.text(font, text, 5, 4, FwmTheme.TEXT);
        matrices.popMatrix();
    }

    private static void drawAuction(GuiGraphicsExtractor ctx, Minecraft client) {
        String clock;
        boolean sold;
        if (!FwmConfig.INSTANCE.auctionHudCard || !Features.isOn("fwm.tab.auction")) {
            return;
        }
        boolean running = Auction.running();
        long now = System.currentTimeMillis();
        if (auctionWasRunning && !running) {
            auctionEndedAt = now;
        }
        auctionWasRunning = running;
        boolean bl = sold = !running && Auction.hasResult() && now - auctionEndedAt < 5000L;
        if (!running && !sold) {
            auctionBids = -1;
            return;
        }
        if (client.gui.screen() instanceof DesignHost) {
            return;
        }
        int bids = Auction.bidCount();
        if (auctionBids >= 0 && bids > auctionBids) {
            auctionFlashUntil = now + 700L;
        }
        auctionBids = bids;
        Font font = client.font;
        ItemStack item = Auction.item();
        String name = item.isEmpty() ? "?" : item.getHoverName().getString();
        String leader = Auction.leader();
        String price = "$" + Fmt.shortMoney(Auction.price());
        long left = Auction.remainingMs();
        long secs = (left + 999L) / 1000L;
        boolean hurry = !sold && secs <= 10L;
        int gold = -865972;
        String string = clock = sold ? Lang.t("vorbei", "ended") : String.format((Locale)Locale.ROOT, (String)"%d:%02d", (Object[])new Object[]{secs / 60L, secs % 60L});
        String label = sold ? (leader == null ? Lang.t("Keine Gebote", "No bids") : Lang.t("Verkauft an", "Sold to")) : (leader == null ? Lang.t("Startgebot", "Starting bid") : Lang.t("H\u00f6chstgebot", "Highest bid"));
        String who = leader == null ? "" : leader;
        int cardW = Math.max((int)160, (int)(30 + font.width(name) + 10 + font.width(clock)));
        int cardH = 57;
        float scale = FwmHud.scaleOf(ID_AUCTION);
        int drawnW = Math.round((float)((float)cardW * scale));
        int[] at = FwmHud.place(ctx, ID_AUCTION, drawnW, Math.round((float)((float)cardH * scale)), ctx.guiWidth() - drawnW - 6, 180);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        if (now < auctionFlashUntil) {
            LauncherGfx.ring(ctx, 0, 0, cardW, cardH, 6, gold);
        }
        int y = 5;
        if (!item.isEmpty()) {
            ctx.item(item, 5, y);
        }
        int clockW = font.width(clock);
        String shownName = font.plainSubstrByWidth(name, cardW - 10 - 20 - clockW - 8);
        ctx.text(font, (Component)Component.literal((String)shownName).withStyle(ChatFormatting.BOLD), 25, y + 4, FwmTheme.TEXT);
        ctx.text(font, clock, cardW - 5 - clockW, y + 4, hurry ? -2067340 : (sold ? FwmTheme.TEXT_DIM : FwmTheme.ACCENT));
        ctx.text(font, label, 5, y += 19, FwmTheme.TEXT_DIM);
        String value = sold && leader == null ? "" : price;
        ctx.text(font, (Component)Component.literal((String)value).withStyle(ChatFormatting.BOLD), cardW - 5 - font.width((FormattedText)Component.literal((String)value).withStyle(ChatFormatting.BOLD)), y, gold);
        y += 11;
        if (!who.isEmpty()) {
            ctx.text(font, font.plainSubstrByWidth(who, cardW - 10), 5, y, FwmTheme.TEXT);
        } else if (!sold) {
            ctx.text(font, Lang.t("noch kein Gebot", "no bid yet"), 5, y, FwmTheme.TEXT_DIM);
        }
        int barY = cardH - 5 - 2;
        int barW = cardW - 10;
        float rest = sold ? 0.0f : Auction.progress();
        LauncherGfx.round(ctx, 5, barY, barW, 2, 1, 0x33FFFFFF);
        if (rest > 0.0f) {
            LauncherGfx.round(ctx, 5, barY, Math.max((int)2, (int)Math.round((float)((float)barW * rest))), 2, 1, hurry ? -2067340 : FwmTheme.ACCENT);
        }
        matrices.popMatrix();
    }

    private static void drawSpawnerPrices(GuiGraphicsExtractor ctx, Minecraft client) {
        if (!FwmConfig.INSTANCE.spawnerHudCard) {
            return;
        }
        int[] size = SpawnerCard.size(client.font);
        if (size == null) {
            return;
        }
        float scale = FwmHud.scaleOf(ID_SPAWNERS);
        int[] at = FwmHud.place(ctx, ID_SPAWNERS, Math.round((float)((float)size[0] * scale)), Math.round((float)((float)size[1] * scale)), 6, 40);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        SpawnerCard.draw(ctx, client.font);
        matrices.popMatrix();
    }

    private static void drawMinimap(GuiGraphicsExtractor ctx, Minecraft client) {
        if (!Features.isOn("fwm.tab.rtp") || !Features.isOn("fwm.feature.rtpminimap") || client.player == null) {
            return;
        }
        float scale = FwmHud.scaleOf(ID_MINIMAP);
        int[] at = FwmHud.place(ctx, ID_MINIMAP, Math.round((float)(128.0f * scale)), Math.round((float)(150.0f * scale)), 6, 90);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        RtpMinimap.draw(ctx, client);
        matrices.popMatrix();
    }

    private static void drawToolWarning(GuiGraphicsExtractor ctx, Minecraft client) {
        if (!FwmConfig.INSTANCE.toolGuardWarning) {
            return;
        }
        ItemStack stack = ToolGuard.warningStack(client);
        if (stack == null) {
            return;
        }
        Font font = client.font;
        long since = ToolGuard.sinceBlocked();
        boolean flaring = since >= 0L && since < 700L;
        MutableComponent title = Lang.of("fwm.toolguard.warnTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
        MutableComponent sub = flaring ? Lang.of("fwm.toolguard.warnBlocked", new Object[0]) : Lang.of("fwm.toolguard.warnSub", ToolGuard.usesLeft(stack));
        int textW = Math.max((int)font.width((FormattedText)title), (int)font.width((FormattedText)sub));
        int cardW = 26 + textW + 5;
        int cardH = 33;
        float scale = 1.0f * FwmHud.scaleOf(ID_TOOL);
        int[] at = FwmHud.place(ctx, ID_TOOL, Math.round((float)((float)cardW * scale)), Math.round((float)((float)cardH * scale)), 6, 6);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        FwmTheme.box(ctx, 0, 0, cardW, cardH, FwmTheme.DISABLED_FILL, flaring ? FwmTheme.DISABLED_TEXT : FwmTheme.DISABLED_EDGE);
        ctx.item(stack, 5, (cardH - 16) / 2);
        int textX = 26;
        ctx.text(font, (Component)title, textX, 4, flaring ? -1 : FwmTheme.DISABLED_TEXT);
        ctx.text(font, (Component)sub, textX, 14, FwmTheme.DISABLED_TEXT);
        int barY = cardH - 8;
        int filled = Math.max((int)1, (int)Math.round((float)((float)textW * ToolGuard.remaining(stack))));
        ctx.fill(textX, barY, textX + textW, barY + 2, FwmTheme.OFF);
        ctx.fill(textX, barY, textX + filled, barY + 2, FwmTheme.DISABLED_TEXT);
        matrices.popMatrix();
    }

    private static void draw(GuiGraphicsExtractor ctx, Minecraft client, boolean active, boolean blocked, int top) {
        int dotColour;
        Font font = client.font;
        MutableComponent brand = Component.literal((String)"Sellmacro");
        MutableComponent status = Lang.of(blocked ? "fwm.hud.blocked" : (active ? "fwm.hud.active" : "fwm.hud.inactive"), new Object[0]);
        MutableComponent countdown = SellStats.testSettling() ? Lang.of("fwm.stats.testSettling", new Object[0]) : (SellStats.testRunning() ? Lang.of("fwm.hud.test", SellStats.testSecondsLeft()) : (active ? Lang.of("fwm.hud.stopWith", Keybinds.toggleKeyName()) : null));
        int logoW = Math.max((int)1, (int)Math.round((float)18.666668f));
        int textW = Math.max((int)font.width((FormattedText)brand), (int)font.width((FormattedText)status));
        if (countdown != null) {
            textW = Math.max((int)textW, (int)font.width((FormattedText)countdown));
        }
        int lines = countdown == null ? 2 : 3;
        int cardH = 8 + lines * 11 + 2;
        int cardW = 20 + logoW + 6 + textW + 10;
        float scale = 1.25f;
        int[] at = FwmHud.place(ctx, ID_MACRO, Math.round((float)((float)cardW * scale)), Math.round((float)((float)cardH * scale)), ctx.guiWidth() - 6 - Math.round((float)((float)cardW * scale)), top);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        int n = blocked ? FwmTheme.DISABLED_TEXT : (dotColour = active ? FwmTheme.ON : FwmTheme.TEXT_DIM);
        if (active) {
            float pulse = 0.55f + 0.45f * (float)Math.abs((double)Math.sin((double)((double)System.currentTimeMillis() / 400.0)));
            int alpha = Math.round((float)(pulse * 255.0f));
            dotColour = alpha << 24 | dotColour & 0xFFFFFF;
        }
        int midY = cardH / 2;
        LauncherGfx.round(ctx, 8, midY - 3, 6, 6, 3, dotColour);
        FwmTheme.logo(ctx, 20, midY - 7, logoW);
        int textX = 20 + logoW + 6;
        int y = 5;
        ctx.text(font, (Component)brand, textX, y, FwmTheme.TEXT);
        ctx.text(font, (Component)status, textX, y + 11, blocked ? FwmTheme.DISABLED_TEXT : (active ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM));
        if (countdown != null) {
            ctx.text(font, (Component)countdown, textX, y + 22, SellStats.testRunning() ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM);
        }
        matrices.popMatrix();
    }

    public static void showMessage(String from, String text) {
        toastFrom = from;
        toastText = text;
        toastAt = System.currentTimeMillis();
    }

    private static void drawMessage(GuiGraphicsExtractor ctx, Minecraft client, int top) {
        if (!Features.isOn("fwm.tab.messages") || !FwmConfig.INSTANCE.msgShowToast) {
            return;
        }
        if (toastFrom == null) {
            return;
        }
        if (System.currentTimeMillis() - toastAt >= 5000L) {
            return;
        }
        Font font = client.font;
        MutableComponent from = Component.literal((String)toastFrom).withStyle(ChatFormatting.BOLD);
        String shown = font.plainSubstrByWidth(toastText, 120);
        MutableComponent body = Component.literal((String)(shown.length() < toastText.length() ? font.plainSubstrByWidth(toastText, 120 - font.width("...")) + "..." : shown));
        int textW = Math.max((int)font.width((FormattedText)from), (int)font.width((FormattedText)body));
        int cardW = 21 + textW + 5;
        int cardH = 26;
        float scale = FwmHud.scaleOf(ID_TOAST);
        int drawnW = Math.round((float)((float)cardW * scale));
        int[] at = FwmHud.place(ctx, ID_TOAST, drawnW, Math.round((float)((float)cardH * scale)), ctx.guiWidth() - 6 - drawnW, top);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        FwmHud.envelope(ctx, 5, (cardH - 8) / 2, FwmTheme.ACCENT);
        int textX = 21;
        ctx.text(font, (Component)from, textX, 5, FwmTheme.ACCENT);
        ctx.text(font, (Component)body, textX, 15, FwmTheme.TEXT);
        matrices.popMatrix();
    }

    private static void drawEnvelope(GuiGraphicsExtractor ctx, Minecraft client, int top) {
        if (!Features.isOn("fwm.tab.messages") || !FwmConfig.INSTANCE.msgShowEnvelope) {
            return;
        }
        int unread = Messages.unreadTotal();
        if (unread <= 0) {
            return;
        }
        Font font = client.font;
        String count = unread > 99 ? "99+" : String.valueOf((int)unread);
        int cardW = 20 + font.width(count) + 5;
        int cardH = 16;
        float scale = FwmHud.scaleOf(ID_ENVELOPE);
        int drawnW = Math.round((float)((float)cardW * scale));
        int[] at = FwmHud.place(ctx, ID_ENVELOPE, drawnW, Math.round((float)((float)cardH * scale)), ctx.guiWidth() - 6 - drawnW, top);
        Matrix3x2fStack matrices = ctx.pose();
        matrices.pushMatrix();
        matrices.translate((float)at[0], (float)at[1]);
        matrices.scale(scale, scale);
        LauncherGfx.card(ctx, 0, 0, cardW, cardH, 6);
        FwmHud.envelope(ctx, 5, (cardH - 8) / 2, FwmTheme.ACCENT);
        Objects.requireNonNull((Object)font);
        ctx.text(font, count, 20, (cardH - 9) / 2 + 1, FwmTheme.TEXT);
        matrices.popMatrix();
    }

    private static void envelope(GuiGraphicsExtractor ctx, int x, int y, int color) {
        FwmTheme.box(ctx, x, y, 11, 8, FwmTheme.ACCENT_DIM, color);
        int steps = Math.min((int)5, (int)6);
        for (int i = 0; i < steps; ++i) {
            ctx.fill(x + 1 + i, y + 1 + i, x + 2 + i, y + 2 + i, color);
            ctx.fill(x + 11 - 2 - i, y + 1 + i, x + 11 - 1 - i, y + 2 + i, color);
        }
    }

    private static /* synthetic */ void lambda$sampleLines$0(List out, FormattedText part, Boolean continued) {
        out.add((Object)part);
    }

    private static /* synthetic */ void lambda$drawSecondChat$0(List wrapped, FormattedText part, Boolean continued) {
        wrapped.add((Object)part);
    }

    static {
        bounds = new LinkedHashMap();
        FIGURE = Identifier.fromNamespaceAndPath((String)"fwm", (String)"textures/gui/hydravb.png");
        chatHits = new ArrayList();
        auctionBids = -1;
    }

    public record ChatHit(int x, int y, int width, int height, FormattedText text) {
    }
}
