/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.Runnable
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.net.Waypoints;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class WaypointsPanel
extends Panel {
    private static final int ROW_H = 18;
    private Waypoints.Group open;
    private int scroll;
    private final List<int[]> boxes = new ArrayList();
    private final List<Runnable> actions = new ArrayList();

    @Override
    public Component title() {
        return this.open == null ? Lang.of("fwm.waypoints.title", new Object[0]) : Component.literal((String)(Lang.str("fwm.waypoints.title", new Object[0]) + " " + this.open.name));
    }

    private void toggle(GuiGraphicsExtractor ctx, int tx, int ty, boolean on, int mouseX, int mouseY, Runnable action) {
        boolean over = mouseX >= tx && mouseX < tx + 22 && mouseY >= ty && mouseY < ty + 11;
        FwmTheme.box(ctx, tx, ty, 22, 11, on ? FwmTheme.ON : FwmTheme.INSET, over ? FwmTheme.ACCENT : (on ? FwmTheme.ON : FwmTheme.OFF));
        ctx.fill(on ? tx + 13 : tx + 2, ty + 2, on ? tx + 20 : tx + 9, ty + 9, on ? -1 : FwmTheme.TEXT_DIM);
        this.boxes.add((Object)new int[]{tx, ty, 22, 11});
        this.actions.add((Object)action);
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        this.boxes.clear();
        this.actions.clear();
        List<Waypoints.Group> groups = Waypoints.groups();
        if (this.open != null && !groups.contains((Object)this.open)) {
            this.open = null;
        }
        if (groups.isEmpty()) {
            ctx.text(font, (Component)Lang.of("fwm.waypoints.none", new Object[0]), this.x + 2, this.y + 4, FwmTheme.TEXT_DIM, false);
            return;
        }
        int ry = this.y - this.scroll;
        ctx.enableScissor(this.x, this.y, this.x + this.width, this.y + this.height);
        if (this.open == null) {
            for (Waypoints.Group group : groups) {
                boolean over;
                boolean on = Waypoints.groupOn(group);
                boolean bl = over = mouseX >= this.x && mouseX < this.x + this.width - 30 && mouseY >= ry && mouseY < ry + 18 && mouseY >= this.y && mouseY < this.y + this.height;
                if (over) {
                    ctx.fill(this.x, ry, this.x + this.width, ry + 18, FwmTheme.HOVER);
                }
                ctx.text(font, (Component)Component.literal((String)group.name), this.x + 6, ry + 5, on ? FwmTheme.TEXT : FwmTheme.TEXT_DIM, false);
                if (group.credit != null && !group.credit.isBlank()) {
                    ctx.text(font, Lang.str("fwm.waypoints.credit", new Object[0]) + group.credit, this.x + 12 + font.width(group.name), ry + 5, FwmTheme.TEXT_DIM, false);
                }
                String count = Waypoints.found(group) + "/" + group.points.size() + " " + Lang.str("fwm.waypoints.found", new Object[0]);
                ctx.text(font, count, this.x + this.width - 34 - font.width(count), ry + 5, FwmTheme.TEXT_DIM, false);
                Waypoints.Group g = group;
                this.toggle(ctx, this.x + this.width - 26, ry + 3, on, mouseX, mouseY, () -> Waypoints.setGroupOn(g, !Waypoints.groupOn(g)));
                this.boxes.add((Object)new int[]{this.x, ry, this.width - 30, 18});
                this.actions.add(() -> {
                    this.open = g;
                    this.scroll = 0;
                });
                FwmTheme.divider(ctx, this.x, ry + 18 - 1, this.width);
                ry += 18;
            }
        } else {
            boolean overBack = mouseX >= this.x && mouseX < this.x + 60 && mouseY >= ry && mouseY < ry + 12;
            ctx.text(font, "< " + Lang.str("fwm.waypoints.back", new Object[0]), this.x + 2, ry + 2, overBack ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM, false);
            if (this.open.credit != null && !this.open.credit.isBlank()) {
                String credit = Lang.str("fwm.waypoints.credit", new Object[0]) + this.open.credit;
                ctx.text(font, credit, this.x + this.width - 4 - font.width(credit), ry + 2, FwmTheme.ACCENT, false);
            }
            this.boxes.add((Object)new int[]{this.x, ry, 60, 12});
            this.actions.add(() -> {
                this.open = null;
                this.scroll = 0;
            });
            ry += 16;
            Minecraft client = Minecraft.getInstance();
            for (Waypoints.Point point : this.open.points) {
                String state;
                boolean done = Waypoints.done(this.open, point);
                String name = Lang.str("fwm.waypoints.point", new Object[0]) + " " + point.n;
                ctx.text(font, name, this.x + 6, ry + 5, done ? FwmTheme.TEXT_DIM : FwmTheme.TEXT, false);
                String string = state = done ? Lang.str("fwm.waypoints.isFound", new Object[0]) : Lang.str("fwm.waypoints.open", new Object[0]);
                if (!done && client.player != null) {
                    double d = Math.sqrt((double)(Math.pow((double)(client.player.getX() - (double)point.x - 0.5), (double)2.0) + Math.pow((double)(client.player.getY() - (double)point.y), (double)2.0) + Math.pow((double)(client.player.getZ() - (double)point.z - 0.5), (double)2.0)));
                    state = state + " \u00b7 " + Math.round((double)d) + " m";
                }
                ctx.text(font, state, this.x + 90, ry + 5, done ? -10688364 : FwmTheme.TEXT_DIM, false);
                String coords = point.x + " " + point.y + " " + point.z;
                ctx.text(font, coords, this.x + this.width - 34 - font.width(coords), ry + 5, FwmTheme.TEXT_DIM, false);
                Waypoints.Group g = this.open;
                Waypoints.Point p = point;
                this.toggle(ctx, this.x + this.width - 26, ry + 3, !done, mouseX, mouseY, () -> Waypoints.setDone(g, p, !Waypoints.done(g, p)));
                FwmTheme.divider(ctx, this.x, ry + 18 - 1, this.width);
                ry += 18;
            }
        }
        ctx.disableScissor();
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        for (int i = 0; i < this.boxes.size(); ++i) {
            int[] b = (int[])this.boxes.get(i);
            if (!(mouseX >= (double)b[0]) || !(mouseX < (double)(b[0] + b[2])) || !(mouseY >= (double)b[1]) || !(mouseY < (double)(b[1] + b[3]))) continue;
            ((Runnable)this.actions.get(i)).run();
            return true;
        }
        return true;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        int rows = this.open == null ? Waypoints.groups().size() : this.open.points.size() + 1;
        int max = Math.max((int)0, (int)(rows * 18 - this.height));
        this.scroll = Math.max((int)0, (int)Math.min((int)max, (int)(this.scroll - (int)Math.signum((double)vertical) * 18)));
        return true;
    }
}
