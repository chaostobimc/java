/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  java.util.List
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FormattedText
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.util.FormattedCharSequence
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteConfig;
import net.friendswithmoney.fwm.seventv.EmotePicker;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.ColorCodePanel;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.util.FormattedCharSequence;

public class EmotesPanel
extends Panel {
    private static final int LINE_H = 10;
    private static final int CELL = 22;

    @Override
    public Component title() {
        return Lang.of("fwm.tab.emotes", new Object[0]);
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int cy = this.y + 4;
        MutableComponent about = Component.literal((String)Lang.t("Emotes von 7TV und BetterTTV im Chat. Rechts neben dem Chat-Feld ist ein Smiley: Klick \u00f6ffnet die Auswahl, ein Emote wird als :name: gesendet. Jeder mit der FWM-Mod (oder Emote Chat) sieht es als Bild, alle anderen lesen :name:.", "Emotes from 7TV and BetterTTV in the chat. Right of the chat field sits a smiley: click it to pick, an emote is sent as :name:. Everyone with the FWM mod (or Emote Chat) sees the picture, everyone else reads :name:."));
        for (FormattedCharSequence line : font.split((FormattedText)about, this.width - 8)) {
            ctx.text(font, line, this.x + 4, cy, FwmTheme.TEXT_DIM, false);
            cy += 10;
        }
        List<Emote> used = EmoteConfig.frequent(40);
        ctx.text(font, (Component)Component.literal((String)Lang.t("H\u00c4UFIG BENUTZT", "USED MOST")), this.x + 4, cy += 8, FwmTheme.TEXT_DIM, false);
        cy += 12;
        if (used.isEmpty()) {
            ctx.text(font, (Component)Component.literal((String)Lang.t("Noch keine \u2013 im Chat auf den Smiley klicken.", "None yet \u2013 click the smiley in the chat.")), this.x + 4, cy, FwmTheme.TEXT, false);
            return;
        }
        int cols = Math.max((int)1, (int)((this.width - 8) / 22));
        String tip = null;
        for (int i = 0; i < used.size(); ++i) {
            int ex = this.x + 4 + i % cols * 22;
            int ey = cy + i / cols * 22;
            if (ey + 22 > this.y + this.height) break;
            Emote e = (Emote)((Object)used.get(i));
            EmotePicker.drawEmote(ctx, e, ex + 1, ey + 1, 18, 18);
            if (mouseX < ex || mouseX >= ex + 22 || mouseY < ey || mouseY >= ey + 22) continue;
            tip = e.chatCode() + " \u00b7 " + e.provider().label;
        }
        if (tip != null) {
            ColorCodePanel.tip(ctx, font, tip, mouseX, mouseY);
        }
    }
}
