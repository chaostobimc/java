/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.List
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.ArrayList;
import java.util.List;
import net.friendswithmoney.fwm.net.Giveaways;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;

public class ModGiveawayPanel
extends Panel {
    private static final int LINE_H = 10;
    private static final int GAP = 6;
    private static final int SCROLL_STEP = 20;
    private static final int GOLD = -865972;
    private static final int GREEN = -10688364;
    private final List<Button> buttons = new ArrayList();
    private int scroll;
    private int contentH;

    public ModGiveawayPanel() {
        Giveaways.refresh();
    }

    @Override
    public Component title() {
        return Lang.of("fwm.news.giveaway", new Object[0]);
    }

    @Override
    public boolean showTitle() {
        return false;
    }

    private static String left(long ms) {
        if (ms <= 0L) {
            return Lang.t("vorbei", "ended");
        }
        long s = ms / 1000L;
        long d = s / 86400L;
        long h = s % 86400L / 3600L;
        long m = s % 3600L / 60L;
        if (d > 0L) {
            return Lang.t("noch ", "") + d + "d " + h + "h" + Lang.t("", " left");
        }
        if (h > 0L) {
            return Lang.t("noch ", "") + h + "h " + m + "m" + Lang.t("", " left");
        }
        return Lang.t("noch ", "") + m + "m " + s % 60L + "s" + Lang.t("", " left");
    }

    /*
     * Exception decompiling
     */
    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * java.lang.NullPointerException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:340076)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3306330)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3004011)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:2344684)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:934660)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:420409)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:2100477)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1145698)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1131922)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        if (button != 0 || !this.contains(mouseX, mouseY)) {
            return false;
        }
        for (Button b : this.buttons) {
            if (!(mouseX >= (double)b.x()) || !(mouseX < (double)(b.x() + b.w())) || !(mouseY >= (double)b.y()) || !(mouseY < (double)(b.y() + b.h()))) continue;
            Giveaways.join(b.id());
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseScrolled(double mouseX, double mouseY, double vertical) {
        if (!this.contains(mouseX, mouseY)) {
            return false;
        }
        this.scroll -= (int)Math.signum((double)vertical) * 20;
        return true;
    }

    private record Button(int x, int y, int w, int h, String id) {
    }
}
