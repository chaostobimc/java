/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.Objects
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.multiplayer.ServerData
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import net.friendswithmoney.fwm.FwmClient;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.input.Keybinds;
import net.friendswithmoney.fwm.ui.DesignHost;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.ui.FwmScreen;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.HudEditScreen;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.widget.Anim;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import org.joml.Matrix3x2fStack;

public final class FwmTutorial {
    private static final Identifier FIGURE = Identifier.fromNamespaceAndPath((String)"fwm", (String)"textures/gui/hydravb.png");
    private static final int FIG_W = 256;
    private static final int FIG_H = 498;
    private static final int SHEET_W = 256;
    private static final int SHEET_H = 512;
    private static final int PAD = 6;
    private static final int LINE_H = 10;
    private static final int BTN_H = 14;
    private static final int TOGGLE_W = 18;
    private static final int TOGGLE_H = 9;
    private static final int SLIDE_MS = 260;
    private static final String OVERVIEW = "fwm.tab.overview";
    private static final List<Step> STEPS = List.of((Object[])new Step[]{Step.of("fwm.tab.overview", "fwm.tutorial.intro"), new Step("fwm.tab.overview", -1, "fwm.tutorial.picker", null, Kind.PICKER, false), Step.of("fwm.tab.overview", "fwm.tutorial.keys"), Step.of("fwm.tab.overview", "fwm.tutorial.overview"), Step.tab("fwm.tab.sellmacro", 0, "fwm.tutorial.sellmacro", "fwm.tab.sellmacro"), Step.tab("fwm.tab.sellmacro", 0, "fwm.tutorial.items", null), Step.tab("fwm.tab.sellmacro", 1, "fwm.tutorial.stats", null), Step.tab("fwm.tab.sellmacro", 2, "fwm.tutorial.tests", null), Step.of("fwm.tab.market", "fwm.tutorial.market", "fwm.tab.market"), Step.of("fwm.tab.kommt", "fwm.tutorial.pricepings", "fwm.feature.priceping"), Step.tab("fwm.tab.spawner", 0, "fwm.tutorial.spawner", null), Step.tab("fwm.tab.spawner", 1, "fwm.tutorial.spawnerhud", null), Step.of("fwm.tab.spawnermacro", "fwm.tutorial.spawnermacro", "fwm.tab.spawnermacro"), Step.of("fwm.tab.money", "fwm.tutorial.money", "fwm.tab.money"), Step.of("fwm.tab.messages", "fwm.tutorial.messages", "fwm.tab.messages"), Step.of("fwm.tab.secondchat", "fwm.tutorial.secondchat", "fwm.tab.secondchat"), Step.of("fwm.tab.colorcodes", "fwm.tutorial.colorcodes", "fwm.tab.colorcodes"), Step.of("fwm.tab.members", "fwm.tutorial.members"), Step.of("fwm.tab.emotes", "fwm.tutorial.emotes", "fwm.tab.emotes"), Step.of("fwm.tab.autotpa", "fwm.tutorial.autotpa", "fwm.tab.autotpa"), Step.of("fwm.tab.toolguard", "fwm.tutorial.toolguard", "fwm.tab.toolguard"), Step.of("fwm.tab.rtp", "fwm.tutorial.rtp", "fwm.tab.rtp"), Step.of("fwm.tab.antileak", "fwm.tutorial.antileak", "fwm.feature.antileak"), Step.of("fwm.tab.textures", "fwm.tutorial.textures"), Step.tab("fwm.tab.auction", 0, "fwm.tutorial.auction", "fwm.tab.auction"), Step.tab("fwm.tab.auction", 1, "fwm.tutorial.giveaway", null), Step.of("fwm.tab.extras", "fwm.tutorial.extras", "fwm.feature.ahprice"), Step.of("fwm.tab.settings", "fwm.tutorial.settings"), new Step("fwm.tab.settings", -1, "fwm.tutorial.overlay", null, Kind.OVERLAY, false), Step.of("fwm.tab.overview", "fwm.tutorial.done")});
    private static final List<String> HUD_CARDS = List.of((Object[])new String[]{"macro", "toast", "envelope", "tpa", "tool", "secondchat", "priceping", "minimap", "money", "invvalue", "auction", "spawners"});
    private static int step = -1;
    private static int colX;
    private static int colY;
    private static float colScale;
    private static int[] backBox;
    private static int[] nextBox;
    private static int[] skipBox;
    private static final List<int[]> pickerBoxes;
    private static int[] toggleBox;
    private static final int MENU_ROOM = 338;
    private static final int COLUMN_H = 236;
    private static final int DOCK_GAP = 4;
    private static boolean docked;
    private static int dockLeft;
    private static int dockRight;
    private static int dockTop;
    private static int dockBottom;

    private FwmTutorial() {
    }

    public static boolean active() {
        return step >= 0;
    }

    public static boolean pending() {
        return !FwmConfig.INSTANCE.tutorialSeen;
    }

    public static void start() {
        step = 0;
        Anim.toward("fwm.tutorial.slide", false, 1);
        FwmTutorial.sync();
    }

    private static void finish() {
        step = -1;
        FwmConfig.INSTANCE.tutorialSeen = true;
        FwmConfig.INSTANCE.tutorialVersion = FwmClient.MOD_VERSION;
        ConfigManager.save();
        FwmTutorial.relayout();
    }

    public static void skip() {
        FwmTutorial.finish();
    }

    private static void next() {
        if (step < 0) {
            return;
        }
        if (step + 1 >= STEPS.size()) {
            FwmTutorial.finish();
            return;
        }
        ++step;
        FwmTutorial.sync();
    }

    private static void back() {
        if (step <= 0) {
            return;
        }
        --step;
        FwmTutorial.sync();
    }

    private static Step current() {
        return step < 0 || step >= STEPS.size() ? null : (Step)((Object)STEPS.get(step));
    }

    /*
     * Unable to fully structure code
     */
    private static void sync() {
        client = Minecraft.getInstance();
        at = FwmTutorial.current();
        if (client == null || at == null) {
            return;
        }
        screen = client.gui.screen();
        if (at.kind() == Kind.OVERLAY) {
            if (!(screen instanceof HudEditScreen)) {
                client.gui.setScreen((Screen)new HudEditScreen(screen));
            }
            return;
        }
        if (screen instanceof HudEditScreen) {
            editor = (HudEditScreen)screen;
            editor.onClose();
            screen = client.gui.screen();
        }
        if (!(screen instanceof DesignHost)) ** GOTO lbl-1000
        menu = (DesignHost)screen;
        if (at.categoryKey() != null) {
            menu.tourShow(at.categoryKey(), at.subTab());
        } else if (screen instanceof FwmScreen) {
            menu = (FwmScreen)screen;
            if (at.categoryKey() != null) {
                menu.tourShow(at.categoryKey(), at.subTab());
            }
        }
        FwmTutorial.relayout();
    }

    private static void relayout() {
        Screen screen;
        Minecraft client = Minecraft.getInstance();
        if (client != null && (screen = client.gui.screen()) instanceof FwmScreen) {
            FwmScreen menu = (FwmScreen)screen;
            menu.tourRelayout();
        }
    }

    public static void tick(Minecraft client) {
        FwmConfig config = FwmConfig.INSTANCE;
        if (config.tutorialGreeted || !FwmTutorial.pending()) {
            return;
        }
        if (client == null || client.player == null) {
            return;
        }
        if (!FwmTutorial.onWantedServer(client)) {
            return;
        }
        config.tutorialGreeted = true;
        ConfigManager.save();
        FwmHud.showGreeting();
    }

    private static boolean onWantedServer(Minecraft client) {
        ServerData server = client.getCurrentServer();
        if (server == null) {
            return false;
        }
        String want = FwmConfig.INSTANCE.tutorialServerMatch;
        if (want == null || want.isBlank()) {
            return true;
        }
        want = want.toLowerCase(Locale.ROOT);
        String ip = server.ip == null ? "" : server.ip.toLowerCase(Locale.ROOT);
        String name = server.name == null ? "" : server.name.toLowerCase(Locale.ROOT);
        return ip.contains((CharSequence)want) || name.contains((CharSequence)want);
    }

    public static int reservedSide(int viewW) {
        if (!FwmTutorial.active()) {
            return 0;
        }
        return Math.min((int)150, (int)Math.max((int)112, (int)(viewW - 338)));
    }

    public static void dock(int left, int right, int top, int bottom) {
        docked = true;
        dockLeft = left;
        dockRight = right;
        dockTop = top;
        dockBottom = bottom;
    }

    public static void undock() {
        docked = false;
    }

    public static boolean onLeft() {
        Step at = FwmTutorial.current();
        return at != null && !at.right();
    }

    public static int reservedLeft(int viewW) {
        return FwmTutorial.active() && FwmTutorial.onLeft() ? FwmTutorial.reservedSide(viewW) : 0;
    }

    public static int reservedRight(int viewW) {
        return FwmTutorial.active() && !FwmTutorial.onLeft() ? FwmTutorial.reservedSide(viewW) : 0;
    }

    public static String highlightKey() {
        Step at = FwmTutorial.current();
        if (at == null || at.kind() == Kind.PICKER) {
            return null;
        }
        return at.categoryKey();
    }

    public static boolean consumes(double mouseX, int viewW) {
        if (!FwmTutorial.active()) {
            return false;
        }
        int w = FwmTutorial.reservedSide(viewW);
        return FwmTutorial.onLeft() ? mouseX < (double)w : mouseX >= (double)(viewW - w);
    }

    public static void render(GuiGraphicsExtractor ctx, Font font, int viewW, int viewH, int mouseX, int mouseY) {
        Step at = FwmTutorial.current();
        if (at == null) {
            return;
        }
        int shownW = FwmTutorial.reservedSide(viewW) - 8;
        int want = Math.min((int)(viewH - 8), (int)236);
        int menuH = dockBottom - dockTop;
        float s = docked && menuH > 0 && menuH < want ? (float)menuH / (float)want : 1.0f;
        int h = docked ? Math.max((int)menuH, (int)want) : want;
        int w = Math.round((float)((float)shownW / s));
        float slide = Anim.ease(Anim.toward("fwm.tutorial.slide", true, 260));
        int home = docked ? (FwmTutorial.onLeft() ? dockLeft - 4 - shownW : dockRight + 4) : (FwmTutorial.onLeft() ? 4 : viewW - shownW - 4);
        int off = FwmTutorial.onLeft() ? -shownW - 4 : viewW + 4;
        int sx = Math.round((float)((float)off + (float)(home - off) * slide));
        int sy = docked ? dockTop : Math.max((int)4, (int)((viewH - h) / 2));
        colX = sx;
        colY = sy;
        colScale = s;
        mouseX = (int)Math.floor((double)((float)(mouseX - sx) / s));
        mouseY = (int)Math.floor((double)((float)(mouseY - sy) / s));
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)sx, (float)sy);
        m.scale(s, s);
        int x = 0;
        int y = 0;
        FwmTheme.panel(ctx, x, y, w, h);
        int inner = x + 6;
        int innerW = w - 12;
        int figH = at.kind() == Kind.TEXT ? Math.min((int)64, (int)(h / 4)) : Math.min((int)44, (int)(h / 5));
        int figW = Math.max((int)1, (int)Math.round((float)((float)figH * 256.0f / 498.0f)));
        FwmGfx.blit(ctx, FIGURE, x + (w - figW) / 2, y + 4 + FwmTutorial.bob(), figW, figH, 256, 498, 256, 512);
        int bottom = FwmTutorial.renderButtons(ctx, font, inner, innerW, y + h - 6, mouseX, mouseY);
        int ty = y + 4 + figH + 6;
        ctx.text(font, (Component)Component.literal((String)Lang.str("fwm.tutorial.step", step + 1, STEPS.size())), inner, ty, FwmTheme.TEXT_DIM);
        ty += 12;
        pickerBoxes.clear();
        toggleBox = null;
        switch (at.kind().ordinal()) {
            case 1: {
                FwmTutorial.renderPicker(ctx, font, inner, ty, innerW, bottom, mouseX, mouseY);
                break;
            }
            case 2: {
                FwmTutorial.renderOverlay(ctx, font, at, inner, ty, innerW, bottom);
                break;
            }
            case 0: {
                FwmTutorial.renderText(ctx, font, at, inner, ty, innerW, bottom);
            }
        }
        m.popMatrix();
    }

    private static int bob() {
        return (int)Math.round((double)(Math.sin((double)((double)System.currentTimeMillis() / 900.0)) * 1.5));
    }

    private static int text(GuiGraphicsExtractor ctx, Font font, String body, int x, int y, int width, int bottom, int color) {
        for (String line : FwmTutorial.wrap(font, body, width)) {
            if (y + 10 > bottom) break;
            ctx.text(font, (Component)Component.literal((String)line), x, y, color);
            y += 10;
        }
        return y;
    }

    private static void renderText(GuiGraphicsExtractor ctx, Font font, Step at, int x, int y, int width, int bottom) {
        y = FwmTutorial.text(ctx, font, FwmTutorial.body(at), x, y, width, bottom, FwmTheme.TEXT);
        if (at.toggleKey() == null) {
            return;
        }
        if ((y += 6) + 9 > bottom) {
            return;
        }
        boolean on = Features.isOn(at.toggleKey());
        FwmTheme.toggle(ctx, x, y, 18, 9, Anim.toward("fwm.tutorial.tog." + at.toggleKey(), on, 120));
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str(at.toggleKey(), new Object[0]), width - 18 - 5)), x + 18 + 5, y, on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM);
        toggleBox = new int[]{x, y - 2, width, 13};
    }

    private static void renderPicker(GuiGraphicsExtractor ctx, Font font, int x, int y, int width, int bottom, int mouseX, int mouseY) {
        y = FwmTutorial.text(ctx, font, Lang.str("fwm.tutorial.picker", new Object[0]), x, y, width, bottom, FwmTheme.TEXT) + 4;
        int i = 0;
        while (i < Features.SWITCHABLE.size() && y + 9 <= bottom) {
            String key = (String)Features.SWITCHABLE.get(i);
            boolean on = Features.isOn(key);
            boolean hovered = mouseX >= x && mouseX < x + width && mouseY >= y - 1 && mouseY < y + 9 + 2;
            FwmTheme.toggle(ctx, x, y, 18, 9, Anim.toward("fwm.tutorial.pick." + key, on, 120));
            ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(Lang.str(key, new Object[0]), width - 18 - 5)), x + 18 + 5, y, on || hovered ? FwmTheme.TEXT : FwmTheme.TEXT_DIM);
            pickerBoxes.add((Object)new int[]{x, y - 1, width, 11, i++});
            y += 11;
        }
    }

    private static void renderOverlay(GuiGraphicsExtractor ctx, Font font, Step at, int x, int y, int width, int bottom) {
        y = FwmTutorial.text(ctx, font, FwmTutorial.body(at), x, y, width, bottom, FwmTheme.TEXT) + 4;
        for (String id : HUD_CARDS) {
            if (y + 10 > bottom) break;
            y = FwmTutorial.text(ctx, font, Lang.str("fwm.hudedit." + id, new Object[0]) + ": " + Lang.str("fwm.tutorial.hud." + id, new Object[0]), x, y, width, bottom, FwmTheme.TEXT_DIM) + 3;
        }
    }

    private static int renderButtons(GuiGraphicsExtractor ctx, Font font, int x, int width, int bottom, int mouseX, int mouseY) {
        String note = Lang.str("fwm.tutorial.skipNote", new Object[0]);
        Objects.requireNonNull((Object)font);
        int noteY = bottom - 9;
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(note, width)), x, noteY, FwmTheme.TEXT_DIM);
        int y = noteY - 3 - 14;
        backBox = null;
        if (step > 0) {
            int backW = Math.min((int)(width / 2), (int)(font.width(Lang.str("fwm.tutorial.back", new Object[0])) + 10));
            backBox = FwmTutorial.button(ctx, font, Lang.str("fwm.tutorial.back", new Object[0]), x, y, backW, mouseX, mouseY, false);
            skipBox = FwmTutorial.button(ctx, font, Lang.str("fwm.tutorial.skip", new Object[0]), x + backW + 4, y, width - backW - 4, mouseX, mouseY, false);
        } else {
            skipBox = FwmTutorial.button(ctx, font, Lang.str("fwm.tutorial.skip", new Object[0]), x, y, width, mouseX, mouseY, false);
        }
        String nextLabel = Lang.str(step + 1 >= STEPS.size() ? "fwm.tutorial.finish" : "fwm.tutorial.next", new Object[0]);
        nextBox = FwmTutorial.button(ctx, font, nextLabel, x, y -= 18, width, mouseX, mouseY, true);
        return y - 5;
    }

    private static int[] button(GuiGraphicsExtractor ctx, Font font, String label, int x, int y, int w, int mouseX, int mouseY, boolean accent) {
        boolean hovered = mouseX >= x && mouseX < x + w && mouseY >= y && mouseY < y + 14;
        FwmTheme.box(ctx, x, y, w, 14, accent ? FwmTheme.RAISED : FwmTheme.INSET, accent || hovered ? FwmTheme.ACCENT : FwmTheme.EDGE);
        if (hovered) {
            FwmTheme.hover(ctx, x, y, w, 14);
        }
        String text = font.plainSubstrByWidth(label, w - 6);
        MutableComponent mutableComponent = Component.literal((String)text);
        int n = x + (w - font.width(text)) / 2;
        Objects.requireNonNull((Object)font);
        ctx.text(font, (Component)mutableComponent, n, y + (14 - 9) / 2 + 1, accent ? FwmTheme.ACCENT : FwmTheme.TEXT);
        return new int[]{x, y, w, 14};
    }

    private static String body(Step at) {
        if ("fwm.tutorial.keys".equals((Object)at.textKey())) {
            return Lang.str(at.textKey(), Keybinds.menuKeyName(), Keybinds.toggleKeyName(), Keybinds.messagesKeyName());
        }
        return Lang.str(at.textKey(), new Object[0]);
    }

    private static List<String> wrap(Font font, String text, int width) {
        ArrayList out = new ArrayList();
        font.getSplitter().splitLines((FormattedText)Component.literal((String)text), Math.max((int)20, (int)width), Style.EMPTY, (arg_0, arg_1) -> FwmTutorial.lambda$wrap$0((List)out, arg_0, arg_1));
        return out;
    }

    public static boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (!FwmTutorial.active() || button != 0) {
            return false;
        }
        if (FwmTutorial.hit(skipBox, mouseX = (mouseX - (double)colX) / (double)colScale, mouseY = (mouseY - (double)colY) / (double)colScale)) {
            FwmTutorial.skip();
            return true;
        }
        if (FwmTutorial.hit(nextBox, mouseX, mouseY)) {
            FwmTutorial.next();
            return true;
        }
        if (FwmTutorial.hit(backBox, mouseX, mouseY)) {
            FwmTutorial.back();
            return true;
        }
        if (FwmTutorial.hit(toggleBox, mouseX, mouseY)) {
            Step at = FwmTutorial.current();
            if (at != null && at.toggleKey() != null) {
                Features.toggle(at.toggleKey());
            }
            return true;
        }
        for (int[] box : pickerBoxes) {
            if (!(mouseX >= (double)box[0]) || !(mouseX < (double)(box[0] + box[2])) || !(mouseY >= (double)box[1]) || !(mouseY < (double)(box[1] + box[3]))) continue;
            Features.toggle((String)Features.SWITCHABLE.get(box[4]));
            return true;
        }
        return false;
    }

    private static boolean hit(int[] box, double mouseX, double mouseY) {
        return box != null && mouseX >= (double)box[0] && mouseX < (double)(box[0] + box[2]) && mouseY >= (double)box[1] && mouseY < (double)(box[1] + box[3]);
    }

    public static boolean keyPressed(int key) {
        if (!FwmTutorial.active()) {
            return false;
        }
        return switch (key) {
            case 41 -> {
                FwmTutorial.skip();
                yield true;
            }
            case 40, 79, 88 -> {
                FwmTutorial.next();
                yield true;
            }
            case 80 -> {
                FwmTutorial.back();
                yield true;
            }
            default -> false;
        };
    }

    private static /* synthetic */ void lambda$wrap$0(List out, FormattedText part, Boolean continued) {
        out.add((Object)part.getString());
    }

    static {
        colScale = 1.0f;
        pickerBoxes = new ArrayList();
    }

    private record Step(String categoryKey, int subTab, String textKey, String toggleKey, Kind kind, boolean right) {
        static Step of(String categoryKey, String textKey) {
            return new Step(categoryKey, -1, textKey, null, Kind.TEXT, false);
        }

        static Step of(String categoryKey, String textKey, String toggleKey) {
            return new Step(categoryKey, -1, textKey, toggleKey, Kind.TEXT, false);
        }

        static Step tab(String categoryKey, int subTab, String textKey, String toggleKey) {
            return new Step(categoryKey, subTab, textKey, toggleKey, Kind.TEXT, true);
        }
    }

    private static enum Kind {
        TEXT,
        PICKER,
        OVERLAY;

    }
}
