/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Optional
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FontDescription
 *  net.minecraft.network.chat.FontDescription$Resource
 *  net.minecraft.network.chat.HoverEvent
 *  net.minecraft.network.chat.HoverEvent$ShowText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 */
package net.friendswithmoney.fwm.seventv;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteGlyphs;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.EmoteTexture;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.friendswithmoney.fwm.ui.FwmNotice;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;

public final class EmoteText {
    public static final Pattern TOKEN = Pattern.compile((String)":([A-Za-z0-9_\\-!?.'()]{2,40}):");
    public static final int MAX_PER_MESSAGE = 5;
    public static final String GLYPH = "\ue000";

    public static int count(String text) {
        Matcher m = TOKEN.matcher((CharSequence)(text == null ? "" : text));
        int n = 0;
        while (m.find()) {
            ++n;
        }
        return n;
    }

    public static boolean roomFor(String fieldText) {
        if (EmoteText.count(fieldText) < 5) {
            return true;
        }
        FwmNotice.show("EMOTES", Lang.t("H\u00f6chstens 5 Emotes pro Nachricht", "At most 5 emotes per message"), "", 15911244, 3000L);
        return false;
    }

    private EmoteText() {
    }

    public static boolean mayContainEmote(Component c) {
        String s = c.getString();
        return s.indexOf(58) >= 0 && TOKEN.matcher((CharSequence)s).find();
    }

    public static Component transform(Component in) {
        if (!EmoteText.mayContainEmote(in)) {
            return in;
        }
        MutableComponent out = Component.empty();
        boolean[] changed = new boolean[]{false};
        int[] made = new int[]{0};
        in.visit((style, text) -> {
            EmoteText.appendSegment(out, style, text, changed, made);
            return Optional.empty();
        }, Style.EMPTY);
        return changed[0] ? out : in;
    }

    private static void appendSegment(MutableComponent out, Style style, String text, boolean[] changed, int[] made) {
        FontDescription.Resource f;
        if (text.isEmpty()) {
            return;
        }
        FontDescription fontDescription = style.getFont();
        if (fontDescription instanceof FontDescription.Resource && EmoteGlyphs.isEmoteFont((f = (FontDescription.Resource)fontDescription).id())) {
            out.append((Component)Component.literal((String)text).setStyle(style));
            made[0] = made[0] + text.length();
            return;
        }
        Matcher m = TOKEN.matcher((CharSequence)text);
        int last = 0;
        while (m.find()) {
            String name = m.group(1);
            if (made[0] >= 5) break;
            Emote e = EmoteRegistry.byName(name);
            if (e == null) {
                if (EmoteRegistry.isKnownMissing(name)) continue;
                EmoteRegistry.resolveAsync(name);
                continue;
            }
            if (m.start() > last) {
                out.append((Component)Component.literal((String)text.substring(last, m.start())).setStyle(style));
            }
            out.append((Component)EmoteText.emoteComponent(e, style));
            made[0] = made[0] + 1;
            last = m.end();
            changed[0] = true;
        }
        if (last < text.length()) {
            out.append((Component)Component.literal((String)text.substring(last)).setStyle(style));
        }
    }

    public static MutableComponent emoteComponent(Emote e, Style base) {
        Style s = base.withFont((FontDescription)new FontDescription.Resource(EmoteGlyphs.fontId(e))).withHoverEvent((HoverEvent)new HoverEvent.ShowText((Component)Component.literal((String)(e.chatCode() + "\n\u00a77" + e.provider().label)))).withInsertion(e.chatCode());
        EmoteTexture.get(e);
        SevenTv.inChat(e.key());
        return Component.literal((String)GLYPH).setStyle(s);
    }
}
