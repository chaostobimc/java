/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.function.IntConsumer
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenEvents
 *  net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.gui.screens.inventory.AbstractContainerScreen
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.core.Position
 *  net.minecraft.core.Vec3i
 *  net.minecraft.network.chat.Component
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.level.ClipContext
 *  net.minecraft.world.level.ClipContext$Block
 *  net.minecraft.world.level.ClipContext$Fluid
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult$Type
 *  net.minecraft.world.phys.Vec3
 *  org.joml.Matrix3x2fStack
 */
package net.friendswithmoney.fwm.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.IntConsumer;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenMouseEvents;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.game.AlertSidePanel;
import net.friendswithmoney.fwm.game.SpawnerMacro;
import net.friendswithmoney.fwm.game.SpawnerSpots;
import net.friendswithmoney.fwm.mixin.HandledScreenAccessor;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.LauncherGfx;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.core.Vec3i;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import org.joml.Matrix3x2fStack;

public final class SpawnerSidePanel {
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
    private static String openKind;
    private static String openLoot;
    private static float scale;
    private static int originX;
    private static int originY;

    private SpawnerSidePanel() {
    }

    public static void register() {
        ScreenEvents.AFTER_INIT.register((client, screen, width, height) -> {
            if (!(screen instanceof AbstractContainerScreen)) {
                return;
            }
            AbstractContainerScreen handled = (AbstractContainerScreen)screen;
            ScreenEvents.afterExtract((Screen)screen).register((s, ctx, mouseX, mouseY, delta) -> SpawnerSidePanel.render(client, handled, ctx, mouseX, mouseY));
            ScreenMouseEvents.allowMouseClick((Screen)screen).register((s, click) -> !SpawnerSidePanel.click(handled, click.x(), click.y(), GLFW.button(click.button())));
        });
    }

    private static boolean shown(AbstractContainerScreen<?> screen) {
        return FwmConfig.INSTANCE.spawnerSidePanel && !AlertSidePanel.isMarketMenu(screen) && SpawnerMacro.isSpawnerMenu(screen);
    }

    private static void render(Minecraft client, AbstractContainerScreen<?> screen, GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
        HandledScreenAccessor at;
        int room;
        hits.clear();
        if (!SpawnerSidePanel.shown(screen) || client.player == null) {
            return;
        }
        String[] what = SpawnerMacro.describe(screen.getMenu());
        openKind = what[0];
        openLoot = what[1];
        BlockPos opened = SpawnerSpots.opened();
        if (opened != null && openKind != null) {
            SpawnerSpots.info((BlockPos)opened).kind = openKind;
            FwmConfig.SpawnerSpot known = SpawnerSpots.spot(client, opened);
            if (known != null && (!openKind.equals((Object)known.kind) || openLoot != null && !openLoot.equals((Object)known.loot))) {
                known.kind = openKind;
                known.loot = openLoot;
                ConfigManager.save();
            }
        }
        if ((room = (at = (HandledScreenAccessor)screen).fwm$x() - 10) < 70) {
            return;
        }
        int h = SpawnerSidePanel.height(client);
        scale = Math.min((float)1.0f, (float)Math.min((float)((float)room / 150.0f), (float)((float)(screen.height - 8) / (float)h)));
        originX = Math.round((float)((float)(at.fwm$x() - 6) - 150.0f * scale));
        originY = Math.max((int)4, (int)Math.min((int)at.fwm$y(), (int)(screen.height - 4 - Math.round((float)((float)h * scale)))));
        int mx = (int)Math.floor((double)((float)(mouseX - originX) / scale));
        int my = (int)Math.floor((double)((float)(mouseY - originY) / scale));
        Matrix3x2fStack m = ctx.pose();
        m.pushMatrix();
        m.translate((float)originX, (float)originY);
        m.scale(scale, scale);
        SpawnerSidePanel.draw(client, ctx, client.font, h, mx, my);
        m.popMatrix();
    }

    private static int height(Minecraft client) {
        int list = Math.max((int)1, (int)Math.min((int)5, (int)SpawnerSpots.here(client).size()));
        return 250 + list * 11;
    }

    private static void draw(Minecraft client, GuiGraphicsExtractor ctx, Font font, int h, int mx, int my) {
        FwmConfig config = FwmConfig.INSTANCE;
        LauncherGfx.round(ctx, 0, 0, 150, h, 8, -536078574);
        LauncherGfx.card(ctx, 0, 0, 150, h, 8);
        int x = 8;
        int cw = 134;
        int y = 8;
        LauncherGfx.icon(ctx, "fwm.tab.spawnermacro", x, y, 0.75f, -12136081);
        LauncherGfx.bold(ctx, font, Lang.t("Spawner-Makro", "Spawner macro"), x + 16, y + 3, -1);
        boolean overX = SpawnerSidePanel.inside(mx, my, 133, y + 1, 10, 10);
        LauncherGfx.text(ctx, font, "\u00d7", 135, y + 2, overX ? -1017230 : -9464447);
        hits.add((Object)new Hit(133, y + 1, 10, 10, () -> {
            config.spawnerSidePanel = false;
            ConfigManager.save();
        }));
        y += 18;
        if (!Features.isOn("fwm.tab.spawnermacro")) {
            LauncherGfx.text(ctx, font, Lang.t("Das Makro ist aus.", "The macro is off."), x, y + 2, -1017230);
            SpawnerSidePanel.button(ctx, font, x, y += 14, cw, 16, Lang.t("Einschalten", "Switch on"), true, mx, my, () -> Features.set("fwm.tab.spawnermacro", true));
            return;
        }
        boolean running = SpawnerMacro.running();
        SpawnerSidePanel.button(ctx, font, x, y, cw, 16, running ? Lang.t("\u25a0  Stoppen  (J)", "\u25a0  Stop  (J)") : Lang.t("\u25b6  Makro starten", "\u25b6  Start macro"), !running, mx, my, () -> SpawnerMacro.toggle(client));
        String status = running ? SpawnerMacro.status() : Lang.t("bereit", "ready");
        LauncherGfx.text(ctx, font, LauncherGfx.trim(font, status, cw), x, y += 19, running ? -865972 : -9464447);
        SpawnerSidePanel.section(ctx, font, x, y += 14, Lang.t("DIESER SPAWNER", "THIS SPAWNER"));
        y += 11;
        BlockPos open = SpawnerSpots.opened();
        int number = SpawnerSpots.number(client, open);
        if (open == null) {
            LauncherGfx.text(ctx, font, Lang.t("nicht erkannt", "not recognised"), x, y + 3, -9464447);
        } else if (number > 0) {
            SpawnerSidePanel.tile(ctx, font, x, y, cw, 14, "\u2714 " + (openKind == null ? "Spawner" : openKind) + " #" + number, Lang.t("abw\u00e4hlen", "unpick"), true, mx, my, () -> SpawnerSpots.toggle(client, open));
        } else {
            SpawnerSidePanel.tile(ctx, font, x, y, cw, 14, "+ " + (openKind == null ? Lang.t("Spawner", "spawner") : openKind) + Lang.t(" ausw\u00e4hlen", " - pick"), "", false, mx, my, () -> SpawnerSpots.toggle(client, open));
        }
        FwmConfig.SpawnerSpot spot = number > 0 ? SpawnerSpots.spot(client, open) : null;
        boolean own = spot != null;
        SpawnerSidePanel.section(ctx, font, x, y += 18, (String)(own ? Lang.t("REGELN #", "RULES #") + number : Lang.t("REGELN (STANDARD)", "RULES (DEFAULT)")));
        y += 11;
        if (SpawnerMacro.dropOnly(openKind)) {
            SpawnerSidePanel.tile(ctx, font, x, y, cw, 14, Lang.t("Modus", "Mode"), Lang.t("Nur droppen", "Drop only"), false, mx, my, () -> {});
            int dropPages = Math.max((int)1, (int)Math.round((float)((float)(own ? spot.dropMin : config.spawnerDropMin) / 2304.0f)));
            SpawnerSidePanel.stepper(ctx, font, x, y += 17, cw, Lang.t("Droppen ab", "Drop from"), dropPages + Lang.t(dropPages == 1 ? " Seite" : " Seiten", dropPages == 1 ? " page" : " pages"), mx, my, d -> {
                int now = Math.max((int)1, (int)Math.round((float)((float)(own ? spot.dropMin : config.spawnerDropMin) / 2304.0f)));
                int v = Math.max((int)1, (int)Math.min((int)50, (int)(now + d))) * 2304;
                if (own) {
                    spot.dropMin = v;
                } else {
                    config.spawnerDropMin = v;
                }
                ConfigManager.save();
            });
            y += 20;
        } else {
            String mode;
            String string = mode = own ? spot.mode : config.spawnerMode;
            String modeName = "drop".equals((Object)mode) ? Lang.t("Alles droppen", "Drop all") : ("sell".equals((Object)mode) ? Lang.t("Verkaufen", "Sell") : Lang.t("Nur ", "Only ") + (openLoot != null ? openLoot : Lang.t("Knochen", "bones")));
            SpawnerSidePanel.tile(ctx, font, x, y, cw, 14, Lang.t("Modus", "Mode"), modeName + " \u25b8", false, mx, my, () -> {
                String next;
                String now;
                String string = now = own ? spot.mode : config.spawnerMode;
                String string2 = "bones".equals((Object)now) ? "drop" : (next = "drop".equals((Object)now) ? "sell" : "bones");
                if (own) {
                    spot.mode = next;
                } else {
                    config.spawnerMode = next;
                }
                ConfigManager.save();
            });
            int sellFill = own ? spot.sellFill : config.spawnerSellFill;
            SpawnerSidePanel.stepper(ctx, font, x, y += 17, cw, Lang.t("Verkaufen ab", "Sell at"), sellFill <= 0 ? Lang.t("nie", "never") : sellFill + " %", mx, my, d -> {
                int v = SpawnerSidePanel.nextFill(own ? spot.sellFill : config.spawnerSellFill, d);
                if (own) {
                    spot.sellFill = v;
                } else {
                    config.spawnerSellFill = v;
                }
                ConfigManager.save();
            });
            int pages = Math.max((int)1, (int)Math.round((float)((float)(own ? spot.dropMin : config.spawnerDropMin) / 2304.0f)));
            String startLabel = "sell".equals((Object)mode) ? Lang.t("Verkauf ab", "Sell from") : Lang.t("Droppen ab", "Drop from");
            SpawnerSidePanel.stepper(ctx, font, x, y += 17, cw, startLabel, pages + Lang.t(pages == 1 ? " Seite" : " Seiten", pages == 1 ? " page" : " pages"), mx, my, d -> {
                int now = Math.max((int)1, (int)Math.round((float)((float)(own ? spot.dropMin : config.spawnerDropMin) / 2304.0f)));
                int v = Math.max((int)1, (int)Math.min((int)50, (int)(now + d))) * 2304;
                if (own) {
                    spot.dropMin = v;
                } else {
                    config.spawnerDropMin = v;
                }
                ConfigManager.save();
            });
            y += 20;
        }
        SpawnerSidePanel.section(ctx, font, x, y, Lang.t("F\u00dcR ALLE", "FOR ALL"));
        y += 11;
        y = SpawnerSidePanel.toggleRow(ctx, font, x, y, cw, Lang.t("Inventar auswerfen", "Empty inventory"), config.spawnerAutoDrop, mx, my, () -> {
            config.spawnerAutoDrop = !config.spawnerAutoDrop;
        });
        y = SpawnerSidePanel.toggleRow(ctx, font, x, y, cw, "Auto-Stop", config.spawnerAutoStop, mx, my, () -> {
            config.spawnerAutoStop = !config.spawnerAutoStop;
        });
        y = SpawnerSidePanel.toggleRow(ctx, font, x, y, cw, "Auto-Sell", config.spawnerAutoSell, mx, my, () -> {
            config.spawnerAutoSell = !config.spawnerAutoSell;
        });
        List<BlockPos> picked = SpawnerSpots.here(client);
        SpawnerSidePanel.section(ctx, font, x, y += 3, Lang.t("AUSGEW\u00c4HLT (", "PICKED (") + picked.size() + ")");
        if (!picked.isEmpty()) {
            String clear;
            int cwid;
            boolean over = SpawnerSidePanel.inside(mx, my, 142 - (cwid = font.width(clear = Lang.t("leeren", "clear"))), y - 1, cwid, 10);
            LauncherGfx.text(ctx, font, clear, 142 - cwid, y, over ? -1017230 : -9464447);
            hits.add((Object)new Hit(142 - cwid, y - 1, cwid, 10, () -> SpawnerSpots.clearHere(client)));
        }
        y += 12;
        BlockPos current = SpawnerMacro.current();
        int rows = Math.min((int)5, (int)picked.size());
        for (int i = 0; i < rows; ++i) {
            BlockPos pos = (BlockPos)picked.get(i);
            boolean now = pos.equals((Object)current);
            boolean here = pos.equals((Object)open);
            LauncherGfx.text(ctx, font, "#" + (i + 1), x, y, now ? -865972 : (here ? -12136081 : -5846860));
            String line = SpawnerSpots.summary(pos);
            FwmConfig.SpawnerSpot saved = SpawnerSpots.spot(client, pos);
            if (saved != null && saved.kind != null && !line.startsWith(saved.kind)) {
                line = saved.kind + ("\u2013".equals((Object)line) ? "" : " \u00b7 " + line);
            }
            LauncherGfx.text(ctx, font, LauncherGfx.trim(font, line, cw - 18), x + 18, y, now ? -865972 : -9464447);
            y += 11;
        }
        if (picked.isEmpty()) {
            LauncherGfx.text(ctx, font, Lang.t("noch keiner", "none yet"), x, y, -9464447);
            y += 11;
        }
        SpawnerSidePanel.button(ctx, font, x, y += 3, cw, 15, Lang.t("+ Alle in Reichweite", "+ All in reach"), false, mx, my, () -> SpawnerSidePanel.pickVisible(client));
    }

    private static int nextFill(int now, int dir) {
        int v;
        int n = now < 10 ? (dir > 0 ? 10 : 0) : (v = now + dir * 10);
        if (v > 0 && v < 10) {
            v = 0;
        }
        return Math.max((int)0, (int)Math.min((int)100, (int)v));
    }

    private static boolean inside(int mx, int my, int x, int y, int w, int h) {
        return mx >= x && mx < x + w && my >= y && my < y + h;
    }

    private static void section(GuiGraphicsExtractor ctx, Font font, int x, int y, String label) {
        LauncherGfx.text(ctx, font, label, x, y, -9464447);
    }

    private static void button(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, int h, String label, boolean primary, int mx, int my, Runnable action) {
        boolean over = SpawnerSidePanel.inside(mx, my, x, y, w, h);
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
        boolean over = SpawnerSidePanel.inside(mx, my, x, y, w, h);
        LauncherGfx.round(ctx, x, y, w, h, 5, over ? 2048870964 : 1544960042);
        LauncherGfx.ring(ctx, x, y, w, h, 5, over || active ? 1717364884 : 0x1FFFFFFF);
        LauncherGfx.text(ctx, font, LauncherGfx.trim(font, label, w - 12 - font.width(value)), x + 5, y + (h - 8) / 2, active ? -12136081 : -1);
        LauncherGfx.text(ctx, font, value, x + w - 5 - font.width(value), y + (h - 8) / 2, -5846860);
        hits.add((Object)new Hit(x, y, w, h, action));
    }

    private static void stepper(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, String label, String value, int mx, int my, IntConsumer change) {
        LauncherGfx.text(ctx, font, label, x, y + 3, -5846860);
        int vw = 58;
        int vx = x + w - vw;
        LauncherGfx.round(ctx, vx, y, vw, 14, 6, 1544960042);
        LauncherGfx.ring(ctx, vx, y, vw, 14, 6, 0x1FFFFFFF);
        LauncherGfx.text(ctx, font, value, vx + (vw - font.width(value)) / 2, y + 3, -1);
        boolean overL = SpawnerSidePanel.inside(mx, my, vx, y, 12, 14);
        boolean overR = SpawnerSidePanel.inside(mx, my, vx + vw - 12, y, 12, 14);
        LauncherGfx.text(ctx, font, "\u2039", vx + 4, y + 3, overL ? -12136081 : -9464447);
        LauncherGfx.text(ctx, font, "\u203a", vx + vw - 8, y + 3, overR ? -12136081 : -9464447);
        hits.add((Object)new Hit(vx, y, 12, 14, () -> change.accept(-1)));
        hits.add((Object)new Hit(vx + vw - 12, y, 12, 14, () -> change.accept(1)));
    }

    private static int toggleRow(GuiGraphicsExtractor ctx, Font font, int x, int y, int w, String label, boolean on, int mx, int my, Runnable toggle) {
        boolean over = SpawnerSidePanel.inside(mx, my, x, y, w, 13);
        LauncherGfx.text(ctx, font, label, x, y + 2, over ? -1 : -5846860);
        LauncherGfx.toggle(ctx, x + w - 22, y, on, -12136081, 0x55FFFFFF);
        hits.add((Object)new Hit(x, y, w, 13, () -> {
            toggle.run();
            ConfigManager.save();
        }));
        return y + 15;
    }

    private static boolean click(AbstractContainerScreen<?> screen, double mouseX, double mouseY, int button) {
        if (button != 0 || !SpawnerSidePanel.shown(screen) || hits.isEmpty()) {
            return false;
        }
        int mx = (int)Math.floor((double)((mouseX - (double)originX) / (double)scale));
        int my = (int)Math.floor((double)((mouseY - (double)originY) / (double)scale));
        for (Hit hit : new ArrayList(hits)) {
            if (!SpawnerSidePanel.inside(mx, my, hit.x(), hit.y(), hit.w(), hit.h())) continue;
            hit.action().run();
            return true;
        }
        return mx >= 0 && my >= 0 && mx < 150 && my < SpawnerSidePanel.height(Minecraft.getInstance());
    }

    private static void pickVisible(Minecraft client) {
        LocalPlayer player = client.player;
        if (player == null || client.level == null) {
            return;
        }
        double reach = player.blockInteractionRange();
        Vec3 eyes = player.getEyePosition();
        BlockPos base = BlockPos.containing((Position)eyes);
        int r = (int)Math.ceil((double)reach);
        List<BlockPos> have = SpawnerSpots.here(client);
        int added = 0;
        for (BlockPos pos : BlockPos.betweenClosed((BlockPos)base.offset(-r, -r, -r), (BlockPos)base.offset(r, r, r))) {
            BlockPos fixed;
            BlockHitResult ray;
            Vec3 center;
            if (!client.level.getBlockState(pos).is((Object)Blocks.SPAWNER) || eyes.distanceTo(center = Vec3.atCenterOf((Vec3i)pos)) > reach || (ray = client.level.clip(new ClipContext(eyes, center, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, (Entity)player))) == null || ray.getType() != HitResult.Type.BLOCK || !ray.getBlockPos().equals((Object)pos) || have.contains((Object)(fixed = pos.immutable()))) continue;
            SpawnerSpots.toggle(client, fixed);
            have.add((Object)fixed);
            ++added;
        }
        player.sendSystemMessage((Component)Component.literal((String)("[FWM] " + String.format((Locale)Locale.ROOT, (String)Lang.t("%d Spawner ausgew\u00e4hlt.", "%d spawners picked."), (Object[])new Object[]{added}))));
    }

    static {
        scale = 1.0f;
    }

    private record Hit(int x, int y, int w, int h, Runnable action) {
    }
}
