// class net/friendswithmoney/fwm/ui/panel/EmotesPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LINE_H I;
field CELL I;
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.emotes", new Object[0]);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = (this.y + 4);
    v7 = network.chat.Component.literal(ui.Lang.t("Emotes von 7TV und BetterTTV im Chat. Rechts neben dem Chat-Feld ist ein Smiley: Klick \u00f6ffnet die Auswahl, ein Emote wird als :name: gesendet. Jeder mit der FWM-Mod (oder Emote Chat) sieht es als Bild, alle anderen lesen :name:.", "Emotes from 7TV and BetterTTV in the chat. Right of the chat field sits a smiley: click it to pick, an emote is sent as :name:. Everyone with the FWM mod (or Emote Chat) sees the picture, everyone else reads :name:."));
    v8 = font.split(v7, (this.width - 8)).iterator();
    L40:
    if (v8.hasNext() ==0) goto L87;
    v9 = ((util.FormattedCharSequence)v8.next());
    ctx.text(font, v9, (this.x + 4), v6, ui.FwmTheme.TEXT_DIM, 0);
    v6 = v6 + 10;
    goto L40;
    L87:
    v6 = v6 + 8;
    v8 = seventv.EmoteConfig.frequent(40);
    ctx.text(font, network.chat.Component.literal(ui.Lang.t("H\u00c4UFIG BENUTZT", "USED MOST")), (this.x + 4), v6, ui.FwmTheme.TEXT_DIM, 0);
    v6 = v6 + 12;
    if (v8.isEmpty() ==0) goto L165;
    ctx.text(font, network.chat.Component.literal(ui.Lang.t("Noch keine \u2013 im Chat auf den Smiley klicken.", "None yet \u2013 click the smiley in the chat.")), (this.x + 4), v6, ui.FwmTheme.TEXT, 0);
    return;
    L165:
    v9 = Math.max(1, ((this.width - 8) / 22));
    v10 = null;
    v11 = 0;
    L187:
    if (v11 >= v8.size()) goto L339;
    v12 = ((this.x + 4) + ((v11 % v9) * 22));
    v13 = (v6 + ((v11 / v9) * 22));
    if ((v13 + 22) <= (this.y + this.height)) goto L249;
    goto L339;
    L249:
    v14 = ((seventv.Emote)v8.get(v11));
    seventv.EmotePicker.drawEmote(ctx, v14, (v12 + 1), (v13 + 1), 18, 18);
    if (mouseX < v12) goto L333;
    if (mouseX >= (v12 + 22)) goto L333;
    if (mouseY < v13) goto L333;
    if (mouseY >= (v13 + 22)) goto L333;
    v10 = v14.chatCode() + ' · ' + v14.provider().label;
    L333:
    v11 = v11 + 1;
    goto L187;
    L339:
    if (v10 == null) goto L354;
    ui.panel.ColorCodePanel.tip(ctx, font, v10, mouseX, mouseY);
    L354:
    return;
}

