// class net/friendswithmoney/fwm/ui/widget/ReelRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field CARD_W I;
field STEP I;
field CARDS I;
field WINNER_AT I;
field GOLD I;
field builtFor J;
field reel Ljava/util/List;;
public void <init>() {
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = auction.Giveaway.participants();
    v6 = auction.Giveaway.winner();
    if (v5.isEmpty() !=0) goto L25;
    if (v6 != null) goto L26;
    L25:
    return;
    L26:
    if (cmp(ui.widget.ReelRow.builtFor,auction.Giveaway.drawnAt()) ==0) goto L129;
    ui.widget.ReelRow.builtFor = auction.Giveaway.drawnAt();
    v7 = ?;
    v8 = ?;
    v9 = 0;
    L66:
    if (v9 >= 40) goto L112;
    v8.add(((auction.Giveaway$Participant)v5.get(v7.nextInt(v5.size()))).name());
    v9 = v9 + 1;
    goto L66;
    L112:
    v8.set(34, v6);
    ui.widget.ReelRow.reel = v8;
    L129:
    v7 = (this.y + 2);
    v8 = 48;
    ui.LauncherGfx.round(ctx, this.x, v7, this.width, v8, 6, 1711675152);
    ui.LauncherGfx.ring(ctx, this.x, v7, this.width, v8, 6, 536870911);
    v9 = (this.x + (this.width / 2));
    v10 = (this.x + this.width);
    v11 = Math.min(1, (((f)(System.currentTimeMillis() - auction.Giveaway.drawnAt())) / 4500.0));
    v12 = (1 - (((1 - v11) * (1 - v11)) * (1 - v11)));
    v13 = ((((f)(v10 + 2244)) + 31.0) - ((f)v9));
    v14 = Math.round((v13 * v12));
    if (cmp(v11,1) <0) goto L277;
    goto L278;
    L277:
    L278:
    v15 = 0;
    ctx.enableScissor((this.x + 2), (v7 + 2), ((this.x + this.width) - 2), ((v7 + v8) - 2));
    v16 = 0;
    L315:
    if (v16 >= ui.widget.ReelRow.reel.size()) goto L550;
    v17 = ((v10 + (v16 * 66)) - v14);
    if ((v17 + 62) < this.x) goto L544;
    if (v17 <= (this.x + this.width)) goto L370;
    goto L544;
    L370:
    if (v15 ==0) goto L386;
    if (v16 != 34) goto L386;
    goto L387;
    L386:
    L387:
    v18 = 0;
    if (v18 ==0) goto L414;
    goto L416;
    L414:
    L416:
    ui.LauncherGfx.round(v17, (v7 + 5), 62, (v8 - 10), 5, -420296372, -870959062);
    if (v18 ==0) goto L443;
    goto L445;
    L443:
    L445:
    ui.LauncherGfx.ring(v17, (v7 + 5), 62, (v8 - 10), 5, m1, 536870911);
    ui.FwmGfx.playerHead(ctx, ((String)ui.widget.ReelRow.reel.get(v16)), (v17 + 23), (v7 + 9), 16);
    v19 = font.plainSubstrByWidth(((String)ui.widget.ReelRow.reel.get(v16)), 56);
    if (v18 ==0) goto L537;
    goto L540;
    L537:
    L540:
    font.text(network.chat.Component.literal(v19), (v17 + ((62 - font.width(v19)) / 2)), ((v7 + v8) - 17), -16045545, ui.FwmTheme.TEXT, 0);
    L544:
    v16 = v16 + 1;
    goto L315;
    L550:
    ctx.disableScissor();
    ctx.fill(v9, (v7 + 2), (v9 + 1), ((v7 + v8) - 2), -865972);
    ctx.fill((v9 - 3), (v7 + 1), (v9 + 4), (v7 + 2), -865972);
    ctx.fill((v9 - 2), (v7 + 2), (v9 + 3), (v7 + 3), -865972);
    ctx.fill((v9 - 1), (v7 + 3), (v9 + 2), (v7 + 4), -865972);
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    return;
}

static List <clinit>() {
    ui.widget.ReelRow.reel = List.of();
    return;
}

