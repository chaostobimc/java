// class net/friendswithmoney/fwm/ui/ConfirmBox  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field WIDTH I;
field PAD I;
field LINE_H I;
field BTN_H I;
field BTN_GAP I;
field question Lnet/minecraft/network/chat/Component;;
field onYes Ljava/lang/Runnable;;
field yesKey Ljava/lang/String;;
field yesX I;
field noX I;
field btnY I;
field btnW I;
public void <init>() {
    this.yesKey = "fwm.confirm.delete";
    return;
}

public boolean open() {
    if (this.question == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public void ask(network.chat.Component question, Runnable onYes) {
    this.ask(question, "fwm.confirm.delete", onYes);
    return;
}

public void ask(network.chat.Component question, String yesKey, Runnable onYes) {
    this.question = question;
    this.yesKey = yesKey;
    this.onYes = onYes;
    return;
}

public void close() {
    this.question = null;
    this.onYes = null;
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int width, int height, int mouseX, int mouseY) {
    if (this.open() !=0) goto L8;
    return;
    L8:
    v9 = Math.min(150, (width - 8));
    v10 = font.split(this.question, (v9 - 16));
    v11 = ((((8 + (v10.size() * 10)) + 6) + 14) + 8);
    v12 = (x + ((width - v9) / 2));
    v13 = (y + ((height - v11) / 2));
    ctx.fill(x, y, (x + width), (y + height), -1342177280);
    ui.FwmTheme.panel(ctx, v12, v13, v9, v11);
    v14 = (v13 + 8);
    v15 = v10.iterator();
    L129:
    if (v15.hasNext() ==0) goto L175;
    v16 = ((util.FormattedCharSequence)v15.next());
    ctx.text(font, v16, (v12 + 8), v14, ui.FwmTheme.TEXT, 0);
    v14 = v14 + 10;
    goto L129;
    L175:
    this.btnW = (((v9 - 16) - 6) / 2);
    this.btnY = (((v13 + v11) - 8) - 14);
    this.noX = (v12 + 8);
    this.yesX = ((this.noX + this.btnW) + 6);
    this.button(ctx, font, this.noX, this.btnY, this.btnW, ui.Lang.of("fwm.confirm.cancel", new Object[0]), this.hovered(this.noX, mouseX, mouseY), 0);
    this.button(ctx, font, this.yesX, this.btnY, this.btnW, ui.Lang.of(this.yesKey, new Object[0]), this.hovered(this.yesX, mouseX, mouseY), 1);
    return;
}

private boolean hovered(int bx, int mouseX, int mouseY) {
    if (mouseX < bx) goto L38;
    if (mouseX >= (bx + this.btnW)) goto L38;
    if (mouseY < this.btnY) goto L38;
    if (mouseY >= (this.btnY + 14)) goto L38;
    goto L39;
    L38:
    L39:
    return 0;
}

private void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int bw, network.chat.Component label, boolean hovered, boolean danger) {
    if (danger ==0) goto L22;
    if (hovered ==0) goto L16;
    goto L36;
    L16:
    goto L36;
    L22:
    if (hovered ==0) goto L33;
    goto L36;
    L33:
    L36:
    v9 = ui.FwmTheme.OFF;
    if (danger ==0) goto L57;
    goto L60;
    L57:
    L60:
    ui.FwmTheme.box(bx, by, bw, 14, ui.FwmTheme.DISABLED_FILL, ui.FwmTheme.RAISED, v9);
    if (danger ==0) goto L76;
    goto L90;
    L76:
    if (hovered ==0) goto L87;
    goto L90;
    L87:
    L90:
    v10 = ui.FwmTheme.TEXT;
    Objects.requireNonNull(font);
    ctx.text(font, label, (bx + ((bw - font.width(label)) / 2)), ((by + ((14 - 9) / 2)) + 1), v10, 0);
    return;
}

public void mouseClicked(double mouseX, double a2) {
    if (this.open() !=0) goto L9;
    return 0;
    L9:
    if (cmp(mouseX,((d)this.yesX)) <0) goto L61;
    if (cmp(mouseX,((d)(this.yesX + this.btnW))) >=0) goto L61;
    if (cmp(v3,((d)this.btnY)) <0) goto L61;
    if (cmp(v3,((d)(this.btnY + 14))) >=0) goto L61;
    goto L62;
    L61:
    L62:
    v5 = 0;
    if (v5 ==0) goto L93;
    v6 = this.onYes;
    this.close();
    if (v6 == null) goto L91;
    v6.run();
    L91:
    return 1;
    L93:
    if (cmp(mouseX,((d)this.noX)) <0) goto L145;
    if (cmp(mouseX,((d)(this.noX + this.btnW))) >=0) goto L145;
    if (cmp(v3,((d)this.btnY)) <0) goto L145;
    if (cmp(v3,((d)(this.btnY + 14))) >=0) goto L145;
    this.close();
    L145:
    return 1;
}

public void keyPressed(int keyCode) {
    if (this.open() !=0) goto L9;
    return 0;
    L9:
    if (keyCode != 41) goto L21;
    this.close();
    return 1;
    L21:
    if (keyCode == 40) goto L33;
    if (keyCode != 88) goto L52;
    L33:
    v2 = this.onYes;
    this.close();
    if (v2 == null) goto L52;
    v2.run();
    L52:
    return 1;
}

