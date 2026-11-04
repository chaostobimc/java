/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.LinkedHashMap
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Objects
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmHud;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.FwmTutorial;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.util.GLFW;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;

public class HudEditScreen
extends Screen {
    private static final Map<String, int[]> FALLBACK = new LinkedHashMap();
    private static final int HANDLE = 8;
    private final Screen parent;
    private final Map<String, int[]> boxes = new LinkedHashMap();
    private final Map<String, int[]> unscaled = new LinkedHashMap();
    private String dragging;
    private String resizing;
    private int grabX;
    private int grabY;

    public HudEditScreen(Screen parent) {
        super((Component)Lang.of("fwm.hudedit.title", new Object[0]));
        this.parent = parent;
    }

    protected void init() {
        FwmTutorial.undock();
        Map<String, int[]> live = FwmHud.bounds();
        for (String id : FALLBACK.keySet()) {
            int y;
            int h;
            int[] known = (int[])live.get((Object)id);
            int[] size = (int[])FALLBACK.get((Object)id);
            float scale = FwmHud.scaleOf(id);
            int w = known != null ? known[2] : Math.round((float)((float)size[0] * scale));
            int n = h = known != null ? known[3] : Math.round((float)((float)size[1] * scale));
            if ("secondchat".equals((Object)id)) {
                int[] sample = FwmHud.sampleSize(this.font, 1.0f);
                this.unscaled.put((Object)id, (Object)sample);
                w = Math.round((float)((float)sample[0] * scale));
                h = Math.round((float)((float)sample[1] * scale));
                known = null;
            } else {
                this.unscaled.put((Object)id, (Object)new int[]{Math.max((int)8, (int)Math.round((float)((float)w / scale))), Math.max((int)8, (int)Math.round((float)((float)h / scale)))});
            }
            FwmConfig.HudSpot spot = FwmHud.spot(id);
            int x = known != null ? known[0] : this.defaultX(id, w);
            int n2 = y = known != null ? known[1] : this.defaultY(id, h);
            if (spot.moved()) {
                x = Math.round((float)(spot.x * (float)Math.max((int)0, (int)(this.width - w))));
                y = Math.round((float)(spot.y * (float)Math.max((int)0, (int)(this.height - h))));
            }
            this.boxes.put((Object)id, (Object)new int[]{x, y, w, h});
        }
    }

    /*
     * Exception decompiling
     */
    private int defaultX(String id, int w) {
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

    /*
     * Exception decompiling
     */
    private int defaultY(String id, int h) {
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

    public void extractRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        super.extractRenderState(ctx, mouseX, mouseY, delta);
        for (Map.Entry entry : this.boxes.entrySet()) {
            int[] box = (int[])entry.getValue();
            boolean hovered = mouseX >= box[0] && mouseX < box[0] + box[2] && mouseY >= box[1] && mouseY < box[1] + box[3];
            boolean active = ((String)entry.getKey()).equals((Object)this.dragging);
            boolean preview = "secondchat".equals(entry.getKey());
            FwmTheme.box(ctx, box[0], box[1], box[2], box[3], active ? FwmTheme.ACCENT_DIM : FwmTheme.RAISED, active || hovered ? FwmTheme.ACCENT : FwmTheme.OFF);
            MutableComponent label = Lang.of("fwm.hudedit." + (String)entry.getKey(), new Object[0]);
            if (preview) {
                FwmHud.drawSample(ctx, this.font, box[0], box[1], FwmHud.scaleOf((String)entry.getKey()));
                ctx.text(this.font, (Component)label, box[0], box[1] - 10, active || hovered ? FwmTheme.ACCENT : FwmTheme.TEXT_DIM);
            } else {
                int n = box[0] + Math.max((int)3, (int)((box[2] - this.font.width((FormattedText)label)) / 2));
                int n2 = box[1];
                int n3 = box[3];
                Objects.requireNonNull((Object)this.font);
                ctx.text(this.font, (Component)label, n, n2 + Math.max((int)3, (int)((n3 - 9) / 2)), active || hovered ? FwmTheme.ACCENT : FwmTheme.TEXT);
            }
            if (!HudEditScreen.resizable((String)entry.getKey())) continue;
            boolean onHandle = HudEditScreen.overHandle((String)entry.getKey(), box, mouseX, mouseY);
            int hx = HudEditScreen.handleX((String)entry.getKey(), box);
            this.grip(ctx, hx, box[1] + box[3] - 8, onHandle || ((String)entry.getKey()).equals((Object)this.resizing));
        }
        if (!FwmTutorial.active()) {
            MutableComponent hint = Lang.of("fwm.hudedit.hint", new Object[0]);
            ctx.text(this.font, (Component)hint, (this.width - this.font.width((FormattedText)hint)) / 2, this.height - 35, FwmTheme.TEXT);
            MutableComponent hint2 = Lang.of("fwm.hudedit.hint2", new Object[0]);
            ctx.text(this.font, (Component)hint2, (this.width - this.font.width((FormattedText)hint2)) / 2, this.height - 24, FwmTheme.TEXT_DIM);
            MutableComponent reset = Lang.of("fwm.hudedit.reset", new Object[0]);
            ctx.text(this.font, (Component)reset, (this.width - this.font.width((FormattedText)reset)) / 2, this.height - 13, FwmTheme.TEXT_DIM);
        }
        FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
    }

    private void grip(GuiGraphicsExtractor ctx, int x, int y, boolean hot) {
        ctx.fill(x, y, x + 8, y + 8, hot ? FwmTheme.ACCENT : FwmTheme.ACCENT_DIM);
        FwmTheme.box(ctx, x, y, 8, 8, 0, hot ? FwmTheme.TEXT : FwmTheme.ACCENT);
    }

    private static boolean resizable(String id) {
        return !"macro".equals((Object)id);
    }

    private static boolean growsLeft(String id) {
        return "secondchat".equals((Object)id);
    }

    private static int handleX(String id, int[] box) {
        return HudEditScreen.growsLeft(id) ? box[0] : box[0] + box[2] - 8;
    }

    private static boolean overHandle(String id, int[] box, double mouseX, double mouseY) {
        int hx = HudEditScreen.handleX(id, box);
        return mouseX >= (double)hx && mouseX < (double)(hx + 8) && mouseY >= (double)(box[1] + box[3] - 8) && mouseY < (double)(box[1] + box[3]);
    }

    public boolean mouseClicked(MouseButtonEvent click, boolean doubled) {
        int[] box;
        if (FwmTutorial.mouseClicked(click.x(), click.y(), GLFW.button(click.button()))) {
            return true;
        }
        if (FwmTutorial.consumes(click.x(), this.width)) {
            return true;
        }
        if (GLFW.button(click.button()) != 0) {
            return super.mouseClicked(click, doubled);
        }
        String hit = null;
        String handle = null;
        for (Map.Entry entry : this.boxes.entrySet()) {
            int[] box2 = (int[])entry.getValue();
            if (HudEditScreen.resizable((String)entry.getKey()) && HudEditScreen.overHandle((String)entry.getKey(), box2, click.x(), click.y())) {
                handle = (String)entry.getKey();
            }
            if (!(click.x() >= (double)box2[0]) || !(click.x() < (double)(box2[0] + box2[2])) || !(click.y() >= (double)box2[1]) || !(click.y() < (double)(box2[1] + box2[3]))) continue;
            hit = (String)entry.getKey();
        }
        if (handle != null) {
            box = (int[])this.boxes.get(handle);
            this.resizing = handle;
            this.grabX = (int)click.x() - (HudEditScreen.growsLeft(handle) ? box[0] : box[0] + box[2]);
            this.grabY = (int)click.y() - (box[1] + box[3]);
            return true;
        }
        if (hit == null) {
            return super.mouseClicked(click, doubled);
        }
        box = (int[])this.boxes.get(hit);
        this.dragging = hit;
        this.grabX = (int)click.x() - box[0];
        this.grabY = (int)click.y() - box[1];
        return true;
    }

    public boolean mouseDragged(MouseButtonEvent click, double deltaX, double deltaY) {
        if (this.resizing != null) {
            this.resize(click.x(), click.y());
            return true;
        }
        if (this.dragging == null) {
            return super.mouseDragged(click, deltaX, deltaY);
        }
        int[] box = (int[])this.boxes.get((Object)this.dragging);
        box[0] = HudEditScreen.clamp((int)click.x() - this.grabX, this.width - box[2]);
        box[1] = HudEditScreen.clamp((int)click.y() - this.grabY, this.height - box[3]);
        return true;
    }

    private void resize(double mouseX, double mouseY) {
        int[] box = (int[])this.boxes.get((Object)this.resizing);
        int[] size = (int[])this.unscaled.get((Object)this.resizing);
        FwmConfig.HudSpot spot = FwmHud.spot(this.resizing);
        if ("secondchat".equals((Object)this.resizing)) {
            this.resizeChat(box, size, spot, mouseX, mouseY);
            return;
        }
        spot.resize((float)(mouseX - (double)this.grabX - (double)box[0]) / (float)size[0]);
        box[2] = Math.round((float)((float)size[0] * spot.scale()));
        box[3] = Math.round((float)((float)size[1] * spot.scale()));
    }

    private void resizeChat(int[] box, int[] size, FwmConfig.HudSpot spot, double mouseX, double mouseY) {
        spot.resize((float)(mouseY - (double)this.grabY - (double)box[1]) / (float)Math.max((int)1, (int)size[1]));
        int right = box[0] + box[2];
        int wanted = Math.round((float)(((float)right - (float)(mouseX - (double)this.grabX)) / spot.scale()));
        FwmConfig.INSTANCE.secondChatWidth = Math.max((int)100, (int)Math.min((int)420, (int)wanted));
        this.unscaled.put((Object)"secondchat", (Object)FwmHud.sampleSize(this.font, 1.0f));
        int[] drawn = FwmHud.sampleSize(this.font, spot.scale());
        box[0] = right - drawn[0];
        box[2] = drawn[0];
        box[3] = drawn[1];
    }

    public boolean mouseReleased(MouseButtonEvent click) {
        if (this.resizing != null) {
            this.resizing = null;
            ConfigManager.save();
        }
        if (this.dragging != null) {
            this.store(this.dragging);
            this.dragging = null;
            ConfigManager.save();
        }
        return super.mouseReleased(click);
    }

    private void store(String id) {
        int[] box = (int[])this.boxes.get((Object)id);
        float fx = this.width - box[2] <= 0 ? 0.0f : (float)box[0] / (float)(this.width - box[2]);
        float fy = this.height - box[3] <= 0 ? 0.0f : (float)box[1] / (float)(this.height - box[3]);
        FwmHud.spot(id).set(fx, fy);
    }

    private static int clamp(int value, int max) {
        return Math.max((int)0, (int)Math.min((int)Math.max((int)0, (int)max), (int)value));
    }

    public boolean keyPressed(KeyEvent input) {
        if (input.key() != 21 && FwmTutorial.keyPressed(input.key())) {
            return true;
        }
        if (input.key() == 21) {
            for (String id : this.boxes.keySet()) {
                FwmHud.spot(id).reset();
            }
            ConfigManager.save();
            this.init();
            return true;
        }
        return super.keyPressed(input);
    }

    public void onClose() {
        this.minecraft.gui.setScreen(this.parent);
    }

    public boolean isPauseScreen() {
        return false;
    }

    static {
        FALLBACK.put((Object)"macro", (Object)new int[]{150, 40});
        FALLBACK.put((Object)"toast", (Object)new int[]{150, 26});
        FALLBACK.put((Object)"envelope", (Object)new int[]{40, 16});
        FALLBACK.put((Object)"tpa", (Object)new int[]{128, 41});
        FALLBACK.put((Object)"tool", (Object)new int[]{200, 33});
        FALLBACK.put((Object)"secondchat", (Object)new int[]{240, 50});
        FALLBACK.put((Object)"minimap", (Object)new int[]{128, 150});
        FALLBACK.put((Object)"priceping", (Object)new int[]{170, 41});
        FALLBACK.put((Object)"money", (Object)new int[]{150, 30});
        FALLBACK.put((Object)"invvalue", (Object)new int[]{90, 19});
        FALLBACK.put((Object)"auction", (Object)new int[]{160, 41});
        FALLBACK.put((Object)"spawners", (Object)new int[]{150, 100});
    }
}
