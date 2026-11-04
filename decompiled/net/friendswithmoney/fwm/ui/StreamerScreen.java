/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Random
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.item.ItemStack
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.friendswithmoney.fwm.auction.Auction;
import net.friendswithmoney.fwm.auction.Giveaway;
import net.friendswithmoney.fwm.auction.StreamerStore;
import net.friendswithmoney.fwm.auction.Times;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.ConfirmBox;
import net.friendswithmoney.fwm.ui.Design;
import net.friendswithmoney.fwm.ui.DesignHost;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.ui.SharpIcons;
import net.friendswithmoney.fwm.ui.panel.StreamerAdTextsPanel;
import net.friendswithmoney.fwm.util.Fmt;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import org.joml.Matrix3x2fStack;

public class StreamerScreen
extends DesignHost {
    private static final int VW = 460;
    private static final int VH = 300;
    private static final int BODY_TOP = 64;
    private static final int BODY_BOTTOM = 266;
    private static final int LEFT_X = 10;
    private static final int LEFT_W = 158;
    private static final int RIGHT_X = 176;
    private static final int RIGHT_W = 274;
    private static final int ROW_H = 20;
    private static final int GOLD = -865972;
    private static final int GREY = -6052957;
    public static final long REEL_MS = 4500L;
    private boolean giveawayTab;
    private int scroll;
    private final List<int[]> boxes = new ArrayList();
    private final List<Runnable> actions = new ArrayList();
    private static final Map<String, Long> sent = new HashMap();
    private static String toast;
    private static long toastAt;
    private long cancelArmedAt;
    private static boolean earningsShown;
    private final ConfirmBox confirm = new ConfirmBox();
    private static boolean reopen;
    private static boolean reopenGiveaway;
    private static boolean registered;
    private static long reelFor;
    private static List<String> reel;
    private static int reelWinner;
    private int listTop;
    private int listBottom;
    private int rowBoxesFrom;
    private int rowBoxesTo;
    private static final long ADVERT_GAP_MS = 5000L;
    private static long advertAt;
    private static int rotation;

    public StreamerScreen(Screen parent, String tab) {
        super(parent);
        this.giveawayTab = tab != null ? tab.equals((Object)"giveaway") : !Auction.running() && !Auction.hasResult() && Giveaway.status() != Giveaway.Status.NONE;
        StreamerScreen.registerOnce();
    }

    public static Screen ifLive(String key) {
        boolean live;
        boolean giveaway = "fwm.tab.giveaway".equals((Object)key);
        if (!giveaway && !"fwm.tab.auction".equals((Object)key)) {
            return null;
        }
        boolean bl = giveaway ? Giveaway.status() != Giveaway.Status.NONE : (live = Auction.running() || Auction.hasResult());
        return live ? new StreamerScreen(null, giveaway ? "giveaway" : "auction") : null;
    }

    private static void registerOnce() {
        if (registered) {
            return;
        }
        registered = true;
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (reopen && client.gui.screen() == null) {
                reopen = false;
                client.gui.setScreen((Screen)new StreamerScreen(null, reopenGiveaway ? "giveaway" : "auction"));
            }
        });
    }

    @Override
    protected int vw() {
        return 460;
    }

    @Override
    protected int vh() {
        return 300;
    }

    @Override
    protected void drawCanvas(GuiGraphicsExtractor ctx, int mx, int my, float delta) {
        this.boxes.clear();
        this.actions.clear();
        this.rowBoxesTo = 0;
        this.rowBoxesFrom = 0;
        int hx = this.confirm.open() ? -1 : mx;
        int hy = this.confirm.open() ? -1 : my;
        this.header(ctx, hx, hy);
        if (this.giveawayTab) {
            this.giveaway(ctx, hx, hy);
        } else {
            this.auction(ctx, hx, hy);
        }
        this.confirm.render(ctx, this.font, 0, 0, 460, 300, mx, my);
    }

    private void header(GuiGraphicsExtractor ctx, int mx, int my) {
        boolean overLine;
        int chipsW;
        float progress;
        long remaining;
        String line;
        int badgeColour;
        String badge;
        LauncherGfx.round(ctx, 10, 8, 108, 26, 6, 1711675152);
        if (this.giveawayTab) {
            this.text(ctx, Lang.t("TEILNEHMER", "PARTICIPANTS"), 16, 11, -5846860);
            this.text(ctx, String.valueOf((int)Giveaway.participants().size()), 16, 22, -865972);
        } else {
            String hint;
            boolean over = mx >= 10 && mx < 118 && my >= 8 && my < 34;
            String value = earningsShown ? Fmt.dollars(Auction.earnings()) : "$ \u2022\u2022\u2022\u2022\u2022\u2022";
            this.text(ctx, Lang.t("EINNAHMEN", "EARNINGS"), 16, 11, -5846860);
            this.text(ctx, value, 16, 22, -865972);
            String string = hint = earningsShown ? Lang.t("verbergen", "hide") : Lang.t("aufdecken", "show");
            if ((!earningsShown || over) && 16 + this.font.width(value) + 6 + this.font.width(hint) <= 112) {
                this.right(ctx, hint, 112, 22, over ? -1 : -9464447);
            }
            this.button(10, 8, 108, 26, () -> {
                earningsShown = !earningsShown;
            });
        }
        String title = FwmGfx.playerName() + "'s " + (this.giveawayTab ? "Giveaway" : Lang.t("Auktion", "Auction"));
        float scale = 1.5f;
        int tw = (int)((float)this.font.width(title) * scale);
        int max = 220;
        if (tw > max) {
            scale = 1.0f;
            tw = this.font.width(title);
        }
        LauncherGfx.big(ctx, this.font, title, 124 + (max - tw) / 2, 13, scale, -1, false);
        int x = 450;
        x = this.tab(ctx, "Giveaway", x, this.giveawayTab, mx, my, () -> {
            this.giveawayTab = true;
            this.scroll = 0;
        });
        this.tab(ctx, Lang.t("Auktion", "Auction"), x - 4, !this.giveawayTab, mx, my, () -> {
            this.giveawayTab = false;
            this.scroll = 0;
        });
        if (this.giveawayTab) {
            Giveaway.Status status = Giveaway.status();
            switch (status) {
                case RUNNING: {
                    String string = Lang.t("LIVE", "LIVE");
                    break;
                }
                case PAUSED: {
                    String string = Lang.t("PAUSIERT", "PAUSED");
                    break;
                }
                case ENDED: {
                    String string;
                    if (Giveaway.drawing()) {
                        string = Lang.t("ZIEHUNG", "DRAWING");
                        break;
                    }
                    string = Lang.t("BEENDET", "ENDED");
                    break;
                }
                default: {
                    String string = badge = Lang.t("BEREIT", "READY");
                }
            }
            int n = status == Giveaway.Status.RUNNING ? -10688364 : (badgeColour = status == Giveaway.Status.ENDED ? -865972 : -5846860);
            line = status == Giveaway.Status.ENDED && !Giveaway.drawing() ? (Giveaway.winner() == null ? Lang.t("Keine Teilnehmer", "No participants") : Lang.t("Gewinner: ", "Winner: ") + Giveaway.winner()) : (status == Giveaway.Status.NONE ? Lang.t("Kein Giveaway l\u00e4uft", "No giveaway running") : Lang.t("Teilnahme: ", "Join: ") + Giveaway.joinCommand());
            remaining = Giveaway.active() ? Giveaway.remainingMs() : 0L;
            progress = Giveaway.active() ? Giveaway.progress() : 0.0f;
        } else {
            boolean running = Auction.running();
            boolean result = Auction.hasResult();
            String string = running ? Lang.t("L\u00c4UFT", "RUNNING") : (badge = result ? Lang.t("BEENDET", "ENDED") : Lang.t("BEREIT", "READY"));
            badgeColour = running ? -10688364 : (result ? -865972 : -5846860);
            String leader = Auction.leader();
            line = running ? (leader == null ? Lang.t("Noch kein Gebot \u2013 ab ", "No bid yet \u2013 from ") + Fmt.dollars(Auction.nextMinimum()) : Lang.t("H\u00f6chstgebot: ", "Highest bid: ") + leader + Lang.t(" mit ", " with ") + Fmt.dollars(Auction.price())) : (result ? (leader == null ? Lang.t("Nicht verkauft", "Not sold") : Lang.t("Gewinner: ", "Winner: ") + leader + Lang.t(" mit ", " with ") + Fmt.dollars(Auction.price())) : Lang.t("Keine Auktion l\u00e4uft", "No auction running"));
            remaining = running ? Auction.remainingMs() : 0L;
            progress = running ? Auction.progress() : 0.0f;
        }
        int bw = this.font.width(badge) + 16;
        LauncherGfx.ring(ctx, 10, 39, bw, 13, 6, badgeColour);
        LauncherGfx.round(ctx, 15, 44, 3, 3, 1, badgeColour);
        this.text(ctx, badge, 21, 42, badgeColour);
        if (toast != null && System.currentTimeMillis() - toastAt < 2500L) {
            line = "\u2714 " + toast;
        }
        int extension = this.giveawayTab ? 0 : Auction.recentExtension();
        String flash = extension > 0 ? "+" + extension + " s" : null;
        int n = chipsW = flash == null ? 0 : this.font.width(flash) + 6;
        if (flash != null) {
            this.text(ctx, flash, 380 - chipsW, 42, -865972);
        }
        int lineRoom = 380 - chipsW - (10 + bw + 8);
        String shown = LauncherGfx.trim(this.font, line, lineRoom);
        boolean copyLine = line.startsWith(Lang.t("Teilnahme: ", "Join: "));
        int lx = 10 + bw + 8;
        boolean bl = overLine = copyLine && mx >= lx && mx < lx + this.font.width(shown) && my >= 39 && my < 52;
        this.text(ctx, shown, lx, 42, line.startsWith("\u2714 ") ? -10688364 : (overLine ? -1 : -865972));
        if (copyLine) {
            this.button(lx, 39, this.font.width(shown), 13, () -> this.copy(Giveaway.joinCommand(), Lang.t("Kopiert: ", "Copied: ") + Giveaway.joinCommand()));
        }
        if (!this.giveawayTab && Auction.running() && !line.startsWith("\u2714 ")) {
            String pay = "/pay " + FwmGfx.playerName();
            String join = "  \u00b7  " + Lang.t("Mach mit: ", "Join: ") + pay;
            int hx = lx + this.font.width(shown);
            if (shown.equals((Object)line) && hx + this.font.width(join) <= lx + lineRoom) {
                boolean overHint = mx >= hx && mx < hx + this.font.width(join) && my >= 39 && my < 52;
                this.text(ctx, join, hx, 42, overHint ? -1 : -5846860);
                this.button(hx, 39, this.font.width(join), 13, () -> this.copy(pay, Lang.t("Kopiert: ", "Copied: ") + pay));
            }
        }
        String clock = StreamerScreen.clock(remaining);
        int cw = (int)((float)this.font.width(clock) * 1.5f);
        LauncherGfx.big(ctx, this.font, clock, 450 - cw, 38, 1.5f, -1, false);
        LauncherGfx.round(ctx, 10, 56, 440, 3, 1, 0x33FFFFFF);
        int filled = Math.round((float)(440.0f * progress));
        if (filled > 1) {
            LauncherGfx.round(ctx, 10, 56, filled, 3, 1, progress < 0.1f ? -1017230 : -865972);
        }
    }

    private int tab(GuiGraphicsExtractor ctx, String label, int right, boolean active, int mx, int my, Runnable action) {
        boolean over;
        int w = this.font.width(label) + 14;
        int x = right - w;
        boolean bl = over = mx >= x && mx < x + w && my >= 12 && my < 26;
        LauncherGfx.round(ctx, x, 12, w, 14, 7, active ? -218103809 : (over ? 0x33FFFFFF : 0x1AFFFFFF));
        this.text(ctx, label, x + 7, 15, active ? -16045545 : -1);
        this.button(x, 12, w, 14, action);
        return x;
    }

    private void auction(GuiGraphicsExtractor ctx, int mx, int my) {
        boolean running = Auction.running();
        boolean resultShown = Auction.hasResult();
        boolean result = Auction.hasResult();
        this.itemBox(ctx, Auction.item(), running ? Lang.t("N\u00e4chstes Gebot ab ", "Next bid from ") + Fmt.dollars(Auction.nextMinimum()) : Lang.t("Mindestgebot ", "Minimum bid ") + Fmt.dollars(Auction.startBid()));
        this.settingChips(ctx);
        String leader = Auction.leader();
        int cy = 188;
        boolean won = result && leader != null;
        LauncherGfx.round(ctx, 10, cy, 158, 266 - cy, 6, won ? 1039321420 : 1544960042);
        LauncherGfx.ring(ctx, 10, cy, 158, 266 - cy, 6, won ? -865972 : 0x1FFFFFFF);
        if (leader == null) {
            this.text(ctx, running ? Lang.t("Noch kein Gebot", "No bid yet") : Lang.t("Kein Gewinner", "No winner"), 18, cy + 8, -9464447);
            if (Auction.itemMissing()) {
                this.text(ctx, Lang.t("Item fehlt im Inventar!", "Item missing from inventory!"), 18, cy + 20, -1017230);
            }
        } else {
            FwmGfx.playerHead(ctx, leader, 18, cy + 8, 22);
            this.text(ctx, won ? Lang.t("GEWINNER", "WINNER") : Lang.t("F\u00dcHRT", "LEADING"), 46, cy + 7, -9464447);
            this.text(ctx, LauncherGfx.trim(this.font, leader, 114), 46, cy + 17, won ? -865972 : -1);
            this.text(ctx, Fmt.dollars(Auction.price()), 46, cy + 27, -10688364);
            this.playerButtons(ctx, leader, 18, cy + 40, mx, my, won);
            if (Auction.itemMissing()) {
                this.text(ctx, Lang.t("Item fehlt!", "Item missing!"), 160 - this.font.width(Lang.t("Item fehlt!", "Item missing!")), cy + 7, -1017230);
            }
        }
        List<Auction.Bid> bids = Auction.ranking();
        List<Auction.Bid> late = Auction.late();
        boolean hasWinner = leader != null;
        int paidCount = Auction.bidCount() + late.size();
        this.listBox(ctx, Lang.t("ZAHLUNGEN", "PAYMENTS"), paidCount + (paidCount == 1 ? Lang.t(" Zahlung", " payment") : Lang.t(" Zahlungen", " payments")), 64, 266);
        if (bids.isEmpty() && late.isEmpty()) {
            this.text(ctx, running ? Lang.t("Noch keine Zahlungen", "No payments yet") : Lang.t("Keine Gebote", "No bids"), 184, 86, -9464447);
        }
        this.rows(ctx, bids.size() + late.size(), 82, 262, (i, y) -> {
            int colour;
            boolean top;
            boolean after = i >= bids.size();
            Auction.Bid bid = after ? (Auction.Bid)((Object)((Object)late.get(i - bids.size()))) : (Auction.Bid)((Object)((Object)bids.get(i)));
            boolean bl = top = !after && i == 0 && hasWinner;
            int n = after ? -1017230 : (colour = top ? -865972 : -6052957);
            LauncherGfx.round(ctx, 180, y, 266, 18, 4, top ? 1039321420 : (after ? 653294194 : 0x14FFFFFF));
            int shift = 0;
            if (!after) {
                String rank = i + 1 + ".";
                int bw = Math.max((int)12, (int)(this.font.width(rank) + 4));
                shift = bw - 12;
                LauncherGfx.round(ctx, 184, y + 3, bw, 12, 3, top ? -865972 : 0x33FFFFFF);
                this.text(ctx, rank, 184 + (bw - this.font.width(rank)) / 2 + 1, y + 5, top ? -16045545 : -1);
            } else {
                LauncherGfx.round(ctx, 184, y + 3, 12, 12, 3, 1441823346);
                this.text(ctx, "!", 189, y + 5, -1);
            }
            FwmGfx.playerHead(ctx, bid.name(), 200 + shift, y + 3, 12);
            int btnX = top ? 442 : 442 - this.buttonsWidth();
            String amount = Fmt.dollars(bid.amount());
            int amountX = btnX - 6 - this.font.width(amount);
            String label = top ? (result ? Lang.t("Gewinner: ", "Winner: ") : Lang.t("H\u00f6chstbietender: ", "Highest bidder: ")) + bid.name() : bid.name();
            int nameX = 216 + shift;
            this.text(ctx, LauncherGfx.trim(this.font, label, amountX - 6 - nameX), nameX, y + 5, colour);
            this.text(ctx, amount, amountX, y + 5, top ? -10688364 : colour);
            if (!top) {
                this.playerButtons(ctx, bid.name(), btnX, y + 2, mx, my, false);
            }
        }, mx, my);
        int x = 10;
        int by = 274;
        x = running ? (x += this.pill(ctx, Lang.t("Beenden", "End"), x, by, mx, my, true, Auction::stop) + 6) : (result ? (x += this.pill(ctx, Lang.t("Neue Auktion", "New auction"), x, by, mx, my, true, () -> {
            Auction.clearResult();
            this.minecraft.gui.setScreen(Design.open(null, "fwm.tab.auction", -1));
        }) + 6) : (x += this.pill(ctx, Lang.t("Auktion einrichten", "Set up auction"), x, by, mx, my, true, () -> this.minecraft.gui.setScreen(Design.open(null, "fwm.tab.auction", -1))) + 6));
        if (running || result) {
            x += this.pill(ctx, Lang.t("Werbung machen", "Advertise"), x, by, mx, my, false, this::advertAuction) + 3;
            x += this.cog(ctx, x, by, mx, my, false) + 6;
        }
        int cw = this.font.width(Lang.t("Schlie\u00dfen", "Close")) + 16;
        this.pill(ctx, Lang.t("Schlie\u00dfen", "Close"), 450 - cw, by, mx, my, false, this::onClose);
    }

    private void giveaway(GuiGraphicsExtractor ctx, int mx, int my) {
        Giveaway.Status status = Giveaway.status();
        boolean ended = status == Giveaway.Status.ENDED;
        this.itemBox(ctx, Giveaway.item(), Giveaway.word().isEmpty() ? "" : Lang.t("Wort: ", "Word: ") + Giveaway.word());
        int cy = 188;
        String winner = ended && !Giveaway.drawing() ? Giveaway.winner() : null;
        LauncherGfx.round(ctx, 10, cy, 158, 266 - cy, 6, winner != null ? 1039321420 : 1544960042);
        LauncherGfx.ring(ctx, 10, cy, 158, 266 - cy, 6, winner != null ? -865972 : 0x1FFFFFFF);
        if (winner != null) {
            FwmGfx.playerHead(ctx, winner, 18, cy + 8, 22);
            this.text(ctx, Lang.t("GEWINNER", "WINNER"), 46, cy + 7, -9464447);
            this.text(ctx, LauncherGfx.trim(this.font, winner, 114), 46, cy + 17, -865972);
            this.text(ctx, Giveaway.participants().size() + Lang.t(" Teilnehmer", " participants"), 46, cy + 27, -5846860);
            this.playerButtons(ctx, winner, 18, cy + 40, mx, my, true);
        } else if (status == Giveaway.Status.NONE) {
            this.text(ctx, Lang.t("Kein Giveaway l\u00e4uft", "No giveaway running"), 18, cy + 8, -9464447);
        } else {
            this.text(ctx, Lang.t("So macht man mit:", "How to join:"), 18, cy + 8, -9464447);
            String join = Giveaway.joinCommand();
            this.text(ctx, LauncherGfx.trim(this.font, join, 142), 18, cy + 20, -10688364);
            this.pill(ctx, Lang.t("Kopieren", "Copy"), 18, cy + 34, mx, my, false, () -> this.copy(join, Lang.t("Kopiert: ", "Copied: ") + join));
        }
        int listTop = 64;
        if (ended && Giveaway.winner() != null) {
            this.reel(ctx, 176, 64, 274, 58);
            listTop = 128;
        }
        List<Giveaway.Participant> all = Giveaway.participants();
        this.listBox(ctx, Lang.t("TEILNEHMER", "PARTICIPANTS"), all.size() + Lang.t(" dabei", " joined"), listTop, 266);
        if (all.isEmpty()) {
            this.text(ctx, status == Giveaway.Status.NONE ? "\u2013" : Lang.t("Noch niemand \u2013 Wort per /msg schicken", "Nobody yet \u2013 send the word via /msg"), 184, listTop + 22, -9464447);
        }
        this.rows(ctx, all.size(), listTop + 18, 262, (i, y) -> {
            Giveaway.Participant p = (Giveaway.Participant)((Object)((Object)all.get(i)));
            boolean win = p.name().equals((Object)winner);
            LauncherGfx.round(ctx, 180, y, 266, 18, 4, win ? 1039321420 : 0x14FFFFFF);
            String n = String.valueOf((int)(i + 1));
            this.text(ctx, n, 186, y + 5, -9464447);
            FwmGfx.playerHead(ctx, p.name(), 200, y + 3, 12);
            int btnX = 442 - this.buttonsWidth();
            String when = StreamerScreen.ago(p.joinedAt());
            int whenX = btnX - 6 - this.font.width(when);
            this.text(ctx, LauncherGfx.trim(this.font, p.name(), whenX - 6 - 216), 216, y + 5, win ? -865972 : -1);
            this.text(ctx, when, whenX, y + 5, -9464447);
            this.playerButtons(ctx, p.name(), btnX, y + 2, mx, my, win);
        }, mx, my);
        int x = 10;
        int by = 274;
        if (Giveaway.active()) {
            boolean paused = status == Giveaway.Status.PAUSED;
            x += this.pill(ctx, Lang.t("Auslosen", "Draw"), x, by, mx, my, true, Giveaway::draw) + 6;
            x += this.pill(ctx, paused ? Lang.t("Fortsetzen", "Resume") : Lang.t("Pausieren", "Pause"), x, by, mx, my, false, paused ? Giveaway::resume : Giveaway::pause) + 6;
            boolean armed = System.currentTimeMillis() - this.cancelArmedAt < 3000L;
            x += this.pill(ctx, armed ? Lang.t("Sicher?", "Sure?") : Lang.t("Abbrechen", "Cancel"), x, by, mx, my, false, () -> {
                if (System.currentTimeMillis() - this.cancelArmedAt < 3000L) {
                    Giveaway.cancel();
                } else {
                    this.cancelArmedAt = System.currentTimeMillis();
                }
            }) + 6;
            x += this.pill(ctx, Lang.t("Werbung machen", "Advertise"), x, by, mx, my, false, this::advertGiveaway) + 3;
            this.cog(ctx, x, by, mx, my, true);
        } else if (ended) {
            x += this.pill(ctx, Lang.t("Neues Giveaway", "New giveaway"), x, by, mx, my, true, () -> {
                Giveaway.reset();
                this.minecraft.gui.setScreen(Design.open(null, "fwm.tab.giveaway", -1));
            }) + 6;
        } else {
            this.pill(ctx, Lang.t("Giveaway einrichten", "Set up giveaway"), x, by, mx, my, true, () -> this.minecraft.gui.setScreen(Design.open(null, "fwm.tab.giveaway", -1)));
        }
        int cw = this.font.width(Lang.t("Schlie\u00dfen", "Close")) + 16;
        this.pill(ctx, Lang.t("Schlie\u00dfen", "Close"), 450 - cw, by, mx, my, false, this::onClose);
    }

    private void reel(GuiGraphicsExtractor ctx, int x, int y, int w, int h) {
        List<Giveaway.Participant> all = Giveaway.participants();
        if (all.isEmpty() || Giveaway.winner() == null) {
            return;
        }
        if (reelFor != Giveaway.drawnAt()) {
            reelFor = Giveaway.drawnAt();
            Random random = new Random(reelFor);
            ArrayList cards = new ArrayList();
            for (int i = 0; i < 40; ++i) {
                cards.add((Object)((Giveaway.Participant)((Object)all.get(random.nextInt(all.size())))).name());
            }
            reelWinner = 34;
            cards.set(reelWinner, (Object)Giveaway.winner());
            reel = cards;
        }
        LauncherGfx.round(ctx, x, y, w, h, 6, 1711675152);
        LauncherGfx.ring(ctx, x, y, w, h, 6, 0x1FFFFFFF);
        int cardW = 62;
        int step = cardW + 4;
        int marker = x + w / 2;
        int startX = x + w;
        float t = Math.min((float)1.0f, (float)((float)(System.currentTimeMillis() - Giveaway.drawnAt()) / 4500.0f));
        float eased = 1.0f - (1.0f - t) * (1.0f - t) * (1.0f - t);
        float endOffset = (float)(startX + reelWinner * step) + (float)cardW / 2.0f - (float)marker;
        int offset = Math.round((float)(endOffset * eased));
        boolean stopped = t >= 1.0f;
        ctx.enableScissor(x + 2, y + 2, x + w - 2, y + h - 2);
        for (int i = 0; i < reel.size(); ++i) {
            int cx = startX + i * step - offset;
            if (cx + cardW < x || cx > x + w) continue;
            boolean win = stopped && i == reelWinner;
            LauncherGfx.round(ctx, cx, y + 8, cardW, h - 16, 5, win ? -420296372 : -870959062);
            LauncherGfx.ring(ctx, cx, y + 8, cardW, h - 16, 5, win ? -1 : 0x1FFFFFFF);
            FwmGfx.playerHead(ctx, (String)reel.get(i), cx + (cardW - 16) / 2, y + 13, 16);
            String name = LauncherGfx.trim(this.font, (String)reel.get(i), cardW - 6);
            this.text(ctx, name, cx + (cardW - this.font.width(name)) / 2, y + h - 20, win ? -16045545 : -1);
        }
        ctx.disableScissor();
        ctx.fill(marker, y + 3, marker + 1, y + h - 3, -865972);
        ctx.fill(marker - 3, y + 2, marker + 4, y + 3, -865972);
        ctx.fill(marker - 2, y + 3, marker + 3, y + 4, -865972);
        ctx.fill(marker - 1, y + 4, marker + 2, y + 5, -865972);
    }

    private void itemBox(GuiGraphicsExtractor ctx, ItemStack stack, String sub) {
        int h = 118;
        LauncherGfx.round(ctx, 10, 64, 158, h, 6, 1544960042);
        LauncherGfx.ring(ctx, 10, 64, 158, h, 6, 0x1FFFFFFF);
        int size = 64;
        int bx = 10 + (158 - size) / 2;
        int by = 72;
        LauncherGfx.round(ctx, bx, by, size, size, 8, 856037136);
        if (!stack.isEmpty()) {
            Matrix3x2fStack m = ctx.pose();
            m.pushMatrix();
            m.translate((float)(bx + 8), (float)(by + 8));
            m.scale(3.0f, 3.0f);
            ctx.item(stack, 0, 0);
            m.popMatrix();
            String name = (String)(stack.getCount() > 1 ? stack.getCount() + "x " : "") + stack.getHoverName().getString();
            name = LauncherGfx.trim(this.font, name, 146);
            this.text(ctx, name, 10 + (158 - this.font.width(name)) / 2, by + size + 6, -1);
        } else {
            String none = Lang.t("Kein Item", "No item");
            this.text(ctx, none, 10 + (158 - this.font.width(none)) / 2, by + size + 6, -9464447);
        }
        if (!sub.isEmpty()) {
            sub = LauncherGfx.trim(this.font, sub, 146);
            this.text(ctx, sub, 10 + (158 - this.font.width(sub)) / 2, by + size + 17, -5846860);
        }
    }

    private void settingChips(GuiGraphicsExtractor ctx) {
        ArrayList chips = new ArrayList();
        if (FwmConfig.INSTANCE.auctionStackPayments) {
            chips.add((Object)Lang.str("fwm.streamer.stack", new Object[0]));
        }
        if (FwmConfig.INSTANCE.auctionAntiSnipe) {
            chips.add((Object)String.format((String)Lang.str("fwm.streamer.antiSnipe", new Object[0]), (Object[])new Object[]{FwmConfig.INSTANCE.auctionAntiSnipeSeconds}));
        }
        if (chips.isEmpty()) {
            return;
        }
        int total = -4;
        for (String chip : chips) {
            total += this.font.width(chip) + 12;
        }
        int cx = 10 + Math.max((int)4, (int)((158 - total) / 2));
        int cy = 165;
        for (String chip : chips) {
            String shown = LauncherGfx.trim(this.font, chip, 164 - cx - 8);
            int w = this.font.width(shown) + 8;
            LauncherGfx.round(ctx, cx, cy, w, 12, 6, 526182548);
            LauncherGfx.ring(ctx, cx, cy, w, 12, 6, 1717364884);
            this.text(ctx, shown, cx + 4, cy + 2, -5846860);
            cx += w + 4;
        }
    }

    private void listBox(GuiGraphicsExtractor ctx, String title, String count, int top, int bottom) {
        LauncherGfx.round(ctx, 176, top, 274, bottom - top, 6, 1544960042);
        LauncherGfx.ring(ctx, 176, top, 274, bottom - top, 6, 0x1FFFFFFF);
        this.text(ctx, title, 184, top + 6, -5846860);
        this.right(ctx, count, 442, top + 6, -9464447);
    }

    private void rows(GuiGraphicsExtractor ctx, int count, int top, int bottom, RowPainter painter, int mx, int my) {
        this.listTop = top;
        this.listBottom = bottom;
        int visible = Math.max((int)1, (int)((bottom - top) / 20));
        this.scroll = Math.max((int)0, (int)Math.min((int)this.scroll, (int)Math.max((int)0, (int)(count - visible))));
        this.rowBoxesFrom = this.boxes.size();
        ctx.enableScissor(176, top, 450, bottom);
        for (int i = this.scroll; i < count && i < this.scroll + visible + 1; ++i) {
            painter.paint(i, top + (i - this.scroll) * 20);
        }
        ctx.disableScissor();
        this.rowBoxesTo = this.boxes.size();
        if (count > visible) {
            int track = bottom - top;
            int bar = Math.max((int)10, (int)(track * visible / count));
            int barY = top + (track - bar) * this.scroll / Math.max((int)1, (int)(count - visible));
            LauncherGfx.round(ctx, 446, barY, 2, bar, 1, 0x40FFFFFF);
        }
    }

    @Override
    protected boolean designScrolled(double mx, double my, double vertical) {
        if (mx < 176.0 || mx >= 450.0 || my < (double)this.listTop || my >= (double)this.listBottom) {
            return false;
        }
        this.scroll = Math.max((int)0, (int)(this.scroll - (int)Math.signum((double)vertical)));
        return true;
    }

    private int buttonsWidth() {
        return this.smallW("TPA") + this.smallW("HERE") + this.smallW(Lang.t("Kopieren", "Copy")) + 6;
    }

    private int smallW(String label) {
        return this.font.width(label) + 8;
    }

    private void playerButtons(GuiGraphicsExtractor ctx, String name, int x, int y, int mx, int my, boolean winner) {
        x += this.small(ctx, "TPA", x, y, mx, my, () -> this.command("tpa", name)) + 3;
        x += this.small(ctx, "HERE", x, y, mx, my, () -> {
            if (winner) {
                this.confirm.ask((Component)Component.literal((String)Lang.t("Gewinner " + name + " per /tpahere zu dir holen?", "Pull winner " + name + " over with /tpahere?")), "fwm.confirm.send", () -> this.command("tpahere", name));
            } else {
                this.command("tpahere", name);
            }
        }) + 3;
        this.small(ctx, Lang.t("Kopieren", "Copy"), x, y, mx, my, () -> this.copy(name, Lang.t("Kopiert: ", "Copied: ") + name));
    }

    private int small(GuiGraphicsExtractor ctx, String label, int x, int y, int mx, int my, Runnable action) {
        int w = this.smallW(label);
        int h = 14;
        boolean over = mx >= x && mx < x + w && my >= y && my < y + h;
        LauncherGfx.round(ctx, x, y, w, h, 4, over ? -218103809 : 0x2EFFFFFF);
        this.text(ctx, label, x + 4, y + 3, over ? -16045545 : -1);
        this.button(x, y, w, h, action);
        return w;
    }

    private int pill(GuiGraphicsExtractor ctx, String label, int x, int y, int mx, int my, boolean primary, Runnable action) {
        boolean over;
        int w = this.font.width(label) + 16;
        int h = 16;
        boolean bl = over = mx >= x && mx < x + w && my >= y && my < y + h;
        int fill = primary ? (over ? -9703262 : -12136081) : (over ? 0x40FFFFFF : 0x1FFFFFFF);
        LauncherGfx.round(ctx, x, y, w, h, 8, fill);
        if (!primary) {
            LauncherGfx.ring(ctx, x, y, w, h, 8, 0x40FFFFFF);
        }
        this.text(ctx, label, x + 8, y + 4, primary ? -16045545 : -1);
        this.button(x, y, w, h, action);
        return w;
    }

    private int cog(GuiGraphicsExtractor ctx, int x, int y, int mx, int my, boolean giveaway) {
        int s = 16;
        boolean over = mx >= x && mx < x + s && my >= y && my < y + s;
        LauncherGfx.round(ctx, x, y, s, s, 8, over ? 0x40FFFFFF : 0x1FFFFFFF);
        LauncherGfx.ring(ctx, x, y, s, s, 8, 0x40FFFFFF);
        SharpIcons.draw(ctx, "icon_cog", x + 3, y + 3, 10, over ? -1 : -5846860);
        this.button(x, y, s, s, () -> this.editAdverts(giveaway));
        return s;
    }

    private void button(int x, int y, int w, int h, Runnable action) {
        this.boxes.add((Object)new int[]{x, y, w, h});
        this.actions.add((Object)action);
    }

    private static String clock(long millis) {
        long seconds = (millis + 999L) / 1000L;
        if (seconds >= 3600L) {
            return Times.clock((int)seconds);
        }
        return String.format((String)"%02d:%02d", (Object[])new Object[]{seconds / 60L, seconds % 60L});
    }

    private static String ago(long time) {
        long seconds = Math.max((long)0L, (long)((System.currentTimeMillis() - time) / 1000L));
        if (seconds < 60L) {
            return Lang.t("vor " + seconds + " s", seconds + " s ago");
        }
        if (seconds < 3600L) {
            return Lang.t("vor " + seconds / 60L + " Min", seconds / 60L + " min ago");
        }
        return Lang.t("vor " + seconds / 3600L + " Std", seconds / 3600L + " h ago");
    }

    private void command(String command, String name) {
        String key = command + ":" + name.toLowerCase();
        long now = System.currentTimeMillis();
        Long last = (Long)sent.get((Object)key);
        if (last != null && now - last < 3000L) {
            return;
        }
        sent.put((Object)key, (Object)now);
        if (StreamerStore.command(command + " " + name)) {
            toast = Lang.t("Gesendet: /", "Sent: /") + command + " " + name;
            toastAt = now;
        }
    }

    private void copy(String text, String note) {
        StreamerStore.copy(text);
        toast = note;
        toastAt = System.currentTimeMillis();
    }

    private static String what(ItemStack stack) {
        return (String)(stack.getCount() > 1 ? stack.getCount() + "x " : "") + stack.getHoverName().getString();
    }

    private static String template(int slot) {
        List<String> texts = FwmConfig.INSTANCE.streamerAdTemplates;
        String text = texts != null && slot < texts.size() ? (String)texts.get(slot) : null;
        return text == null || text.isBlank() ? (String)FwmConfig.DEFAULT_STREAMER_AD_TEMPLATES.get(slot) : text;
    }

    private static String streamerServer() {
        String server = FwmConfig.INSTANCE.streamerServer;
        return server == null || server.isBlank() ? "Twitch" : server.trim();
    }

    private static String common(String template) {
        return template.replace((CharSequence)"[Spieler]", (CharSequence)FwmGfx.playerName()).replace((CharSequence)"[Name]", (CharSequence)FwmGfx.playerName()).replace((CharSequence)"[Server]", (CharSequence)StreamerScreen.streamerServer());
    }

    private static String runningAdvert() {
        List<String> texts = FwmConfig.INSTANCE.streamerAdRotation;
        if (texts == null || texts.isEmpty()) {
            texts = FwmConfig.DEFAULT_STREAMER_AD_ROTATION;
        }
        String text = (String)texts.get(rotation % texts.size());
        rotation = (rotation + 1) % texts.size();
        return text == null || text.isBlank() ? (String)FwmConfig.DEFAULT_STREAMER_AD_ROTATION.get(0) : text;
    }

    private void advertAuction() {
        String leader = Auction.leader();
        String line = StreamerScreen.common(Auction.running() ? StreamerScreen.runningAdvert() : StreamerScreen.template(1)).replace((CharSequence)"[Item]", (CharSequence)StreamerScreen.what(Auction.item())).replace((CharSequence)"[Zeit]", (CharSequence)StreamerScreen.clock(Auction.remainingMs())).replace((CharSequence)"[Gebot]", (CharSequence)Fmt.dollars(Auction.nextMinimum())).replace((CharSequence)"[H\u00f6chstgebot]", (CharSequence)(leader == null ? "\u2013" : Fmt.dollars(Auction.price()))).replace((CharSequence)"[Gewinner]", (CharSequence)(leader == null ? "niemand" : leader)).replace((CharSequence)"[Preis]", (CharSequence)(leader == null ? "\u2013" : Fmt.dollars(Auction.price())));
        this.send(line);
    }

    private void advertGiveaway() {
        String line = StreamerScreen.common(StreamerScreen.template(2)).replace((CharSequence)"[Item]", (CharSequence)StreamerScreen.what(Giveaway.item())).replace((CharSequence)"[Zeit]", (CharSequence)StreamerScreen.clock(Giveaway.active() ? Giveaway.remainingMs() : 0L)).replace((CharSequence)"[Befehl]", (CharSequence)Giveaway.joinCommand()).replace((CharSequence)"[Wort]", (CharSequence)Giveaway.word()).replace((CharSequence)"[Teilnehmer]", (CharSequence)String.valueOf((int)Giveaway.participants().size()));
        this.send(line);
    }

    private void send(String line) {
        long now = System.currentTimeMillis();
        if (now - advertAt < 5000L) {
            toast = Lang.t("Werbung gerade gesendet \u2013 kurz warten", "Advert just sent \u2013 wait a moment");
            toastAt = now;
            return;
        }
        if (StreamerStore.say(line)) {
            advertAt = now;
            toast = Lang.t("Werbung gesendet", "Advert sent");
            toastAt = now;
        }
    }

    private void editAdverts(boolean giveaway) {
        reopen = true;
        reopenGiveaway = giveaway;
        Screen menu = Design.open(null, "fwm.tab.auction", giveaway ? 1 : 0);
        Minecraft.getInstance().gui.setScreen(menu);
        if (menu instanceof DesignHost) {
            DesignHost host = (DesignHost)menu;
            host.openSub(new StreamerAdTextsPanel(host));
        }
    }

    @Override
    protected boolean designClicked(double mx, double my, int button) {
        if (this.confirm.open()) {
            return this.confirm.mouseClicked(mx, my);
        }
        if (button != 0) {
            return false;
        }
        for (int i = this.boxes.size() - 1; i >= 0; --i) {
            int[] b = (int[])this.boxes.get(i);
            if (!StreamerScreen.inside(b, mx, my) || i >= this.rowBoxesFrom && i < this.rowBoxesTo && (my < (double)this.listTop || my >= (double)this.listBottom)) continue;
            ((Runnable)this.actions.get(i)).run();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyPressed(KeyEvent input) {
        if (this.confirm.open()) {
            return this.confirm.keyPressed(input.key());
        }
        return super.keyPressed(input);
    }

    @Override
    protected void leavePage() {
    }

    @Override
    public void onClose() {
        reopen = false;
        FwmConfig.INSTANCE.clamp();
        super.onClose();
    }

    static {
        reel = List.of();
    }

    private static interface RowPainter {
        public void paint(int var1, int var2);
    }
}
