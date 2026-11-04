/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.util.ArrayList
 *  java.util.HashSet
 *  java.util.LinkedHashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Set
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.EditBox
 */
package net.friendswithmoney.fwm.seventv;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteApi;
import net.friendswithmoney.fwm.seventv.EmoteConfig;
import net.friendswithmoney.fwm.seventv.EmotePicker;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.EmoteText;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;

public final class EmoteSuggest {
    private static final Pattern TYPING = Pattern.compile((String)":([A-Za-z0-9_\\-!?.'()]{2,40})$");
    private static final int MAX = 8;
    private static final int ROW_H = 12;
    private static final long SEARCH_DELAY_MS = 250L;
    private static String prefix;
    private static String dismissed;
    private static int start;
    private static List<Emote> shown;
    private static int selected;
    private static String searched;
    private static String pending;
    private static long pendingSince;
    private static final Set<String> SEARCHED;
    private static int boxX;
    private static int boxY;
    private static int boxW;

    private EmoteSuggest() {
    }

    public static boolean active() {
        return !shown.isEmpty();
    }

    public static void update(EditBox field) {
        int cursor;
        String text = field.getValue();
        Matcher m = TYPING.matcher((CharSequence)text.substring(0, cursor = Math.min((int)field.getCursorPosition(), (int)text.length())));
        if (!m.find()) {
            EmoteSuggest.clear();
            return;
        }
        String now = m.group(1);
        if (now.equals((Object)dismissed)) {
            shown = List.of();
            return;
        }
        dismissed = null;
        if (!now.equals((Object)prefix)) {
            prefix = now;
            selected = 0;
            pending = now;
            pendingSince = System.currentTimeMillis();
        }
        start = m.start();
        String key = now.toLowerCase(Locale.ROOT);
        if (!key.equals((Object)searched) && System.currentTimeMillis() - pendingSince > 250L && SEARCHED.add((Object)key)) {
            searched = key;
            EmoteApi.searchSevenTv(now, 1, 25).whenComplete((list, err) -> {
                if (list != null) {
                    for (Emote e : list) {
                        EmoteRegistry.register(e);
                    }
                }
            });
        }
        LinkedHashSet names = new LinkedHashSet();
        ArrayList out = new ArrayList();
        for (Emote e : EmoteConfig.frequent(200)) {
            if (!e.name().toLowerCase(Locale.ROOT).startsWith(key) || !names.add((Object)e.name().toLowerCase(Locale.ROOT))) continue;
            out.add((Object)e);
        }
        for (Emote e : EmoteRegistry.startingWith(now, 16)) {
            if (out.size() >= 8) break;
            if (!names.add((Object)e.name().toLowerCase(Locale.ROOT))) continue;
            out.add((Object)e);
        }
        Object object = shown = out.size() > 8 ? out.subList(0, 8) : out;
        if (selected >= shown.size()) {
            selected = 0;
        }
    }

    public static void clear() {
        prefix = null;
        shown = List.of();
        selected = 0;
    }

    public static void render(GuiGraphicsExtractor g, EditBox field, int mouseX, int mouseY) {
        if (shown.isEmpty()) {
            return;
        }
        Font font = Minecraft.getInstance().font;
        int w = 0;
        for (Emote e : shown) {
            w = Math.max((int)w, (int)font.width(e.chatCode()));
        }
        boxW = w + 22;
        String before = field.getValue().substring(0, Math.min((int)start, (int)field.getValue().length()));
        boxX = Math.min((int)(field.getX() + font.width(before) - 1), (int)(g.guiWidth() - boxW - 2));
        boxY = field.getY() - 4 - shown.size() * 12;
        g.nextStratum();
        g.fill(boxX, boxY, boxX + boxW, boxY + shown.size() * 12, -805306368);
        for (int i = 0; i < shown.size(); ++i) {
            boolean over;
            Emote e = (Emote)((Object)shown.get(i));
            int ry = boxY + i * 12;
            boolean bl = over = mouseX >= boxX && mouseX < boxX + boxW && mouseY >= ry && mouseY < ry + 12;
            if (over) {
                selected = i;
            }
            if (i == selected) {
                g.fill(boxX, ry, boxX + boxW, ry + 12, 0x40FFFFFF);
            }
            EmotePicker.drawEmote(g, e, boxX + 2, ry + 1, 10, 10);
            g.text(font, e.chatCode(), boxX + 16, ry + 2, i == selected ? -171 : -5592406, true);
        }
    }

    public static boolean keyPressed(EditBox field, int key) {
        if (shown.isEmpty()) {
            return false;
        }
        switch (key) {
            case 43: {
                EmoteSuggest.accept(field, (Emote)((Object)shown.get(selected)));
                return true;
            }
            case 82: {
                selected = (selected + shown.size() - 1) % shown.size();
                return true;
            }
            case 81: {
                selected = (selected + 1) % shown.size();
                return true;
            }
            case 41: {
                dismissed = prefix;
                shown = List.of();
                return true;
            }
        }
        return false;
    }

    public static boolean mouseClicked(EditBox field, double mx, double my) {
        if (shown.isEmpty() || mx < (double)boxX || mx >= (double)(boxX + boxW) || my < (double)boxY || my >= (double)(boxY + shown.size() * 12)) {
            return false;
        }
        EmoteSuggest.accept(field, (Emote)((Object)shown.get((int)((my - (double)boxY) / 12.0))));
        return true;
    }

    private static void accept(EditBox field, Emote e) {
        if (!EmoteText.roomFor(field.getValue())) {
            EmoteSuggest.clear();
            return;
        }
        String text = field.getValue();
        int cursor = Math.min((int)field.getCursorPosition(), (int)text.length());
        String code = e.chatCode() + " ";
        String next = text.substring(0, start) + code + text.substring(cursor);
        if (next.length() > 256) {
            return;
        }
        field.setValue(next);
        field.moveCursorTo(start + code.length(), false);
        EmoteRegistry.bindName(e);
        EmoteConfig.recordUse(e);
        EmoteSuggest.clear();
    }

    static {
        shown = List.of();
        searched = "";
        pending = "";
        SEARCHED = new HashSet();
    }
}
