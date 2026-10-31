// class net/friendswithmoney/fwm/ui/panel/RowsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field SCROLLBAR_W I;
field rows Ljava/util/List;;
field confirm Lnet/friendswithmoney/fwm/ui/ConfirmBox;;
field scroll I;
field maxScroll I;
field rowsShown I;
public void <init>() {
    this.rows = ?;
    this.confirm = ?;
    return;
}

// buildRows()V  (abstract/native)

protected void onResize() {
    this.rows.clear();
    this.buildRows();
    this.rowsShown = Math.max(1, Math.min(this.rows.size(), (this.height / 26)));
    this.maxScroll = Math.max(0, (this.rows.size() - this.rowsShown));
    this.scroll = Math.max(0, Math.min(this.maxScroll, this.scroll));
    this.positionRows();
    return;
}

private void positionRows() {
    if (this.maxScroll <=0) goto L15;
    goto L16;
    L15:
    L16:
    v1 = (5 - 0);
    v2 = 0;
    L20:
    if (v2 >= this.rows.size()) goto L114;
    v3 = (v2 - this.scroll);
    if (v3 <0) goto L52;
    if (v3 < this.rowsShown) goto L78;
    L52:
    ((ui.widget.Row)this.rows.get(v2)).setBounds(this.x, -1073741824, v1);
    goto L108;
    L78:
    ((ui.widget.Row)this.rows.get(v2)).setBounds(this.x, (this.y + (v3 * 26)), v1);
    L108:
    v2 = v2 + 1;
    goto L20;
    L114:
    return;
}

private int lastVisible() {
    return Math.min(this.rows.size(), (this.scroll + this.rowsShown));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.scroll;
    L6:
    if (v6 >= this.lastVisible()) goto L43;
    ((ui.widget.Row)this.rows.get(v6)).render(ctx, font, mouseX, mouseY);
    v6 = v6 + 1;
    goto L6;
    L43:
    if (this.maxScroll <=0) goto L162;
    v6 = (this.rowsShown * 26);
    v7 = ((this.x + this.width) - 3);
    v8 = Math.max(10, ((v6 * this.rowsShown) / this.rows.size()));
    v9 = (this.y + (((v6 - v8) * this.scroll) / this.maxScroll));
    ctx.fill(v7, this.y, (v7 + 3), (this.y + v6), ui.FwmTheme.OFF);
    ctx.fill(v7, v9, (v7 + 3), (v9 + v8), ui.FwmTheme.ACCENT);
    L162:
    this.confirm.render(ctx, font, this.x, this.y, this.width, this.height, mouseX, mouseY);
    return;
}

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.confirm.open() ==0) goto L20;
    return this.confirm.mouseClicked(mouseX, mouseY);
    L20:
    v6 = this.rows.iterator();
    L31:
    if (v6.hasNext() ==0) goto L79;
    v7 = ((ui.widget.Row)v6.next());
    if (v7.isCapturingKeys() ==0) goto L76;
    if (v7.isHovered(mouseX, mouseY) !=0) goto L76;
    v7.commitTyped();
    L76:
    goto L31;
    L79:
    v6 = this.scroll;
    L85:
    if (v6 >= this.lastVisible()) goto L126;
    if (((ui.widget.Row)this.rows.get(v6)).mouseClicked(mouseX, mouseY, v5) ==0) goto L120;
    return 1;
    L120:
    v6 = v6 + 1;
    goto L85;
    L126:
    return 0;
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    v6 = this.scroll;
    L6:
    if (v6 >= this.lastVisible()) goto L47;
    if (((ui.widget.Row)this.rows.get(v6)).mouseDragged(mouseX, mouseY, v5) ==0) goto L41;
    return 1;
    L41:
    v6 = v6 + 1;
    goto L6;
    L47:
    return 0;
}

public void mouseReleased() {
    v1 = this.rows.iterator();
    L10:
    if (v1.hasNext() ==0) goto L36;
    v2 = ((ui.widget.Row)v1.next());
    v2.mouseReleased();
    goto L10;
    L36:
    return;
}

public void mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.maxScroll >0) goto L9;
    return 0;
    L9:
    this.scroll = Math.max(0, Math.min(this.maxScroll, (this.scroll - ((i)Math.signum(v5)))));
    this.positionRows();
    return 1;
}

public boolean isCapturingKeys() {
    if (this.confirm.open() ==0) goto L12;
    return 1;
    L12:
    v1 = this.rows.iterator();
    L22:
    if (v1.hasNext() ==0) goto L53;
    v2 = ((ui.widget.Row)v1.next());
    if (v2.isCapturingKeys() ==0) goto L50;
    return 1;
    L50:
    goto L22;
    L53:
    return 0;
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.rows.iterator();
    L10:
    if (v2.hasNext() ==0) goto L49;
    v3 = ((ui.widget.Row)v2.next());
    if (v3.isCapturingKeys() ==0) goto L46;
    if (v3.charTyped(input) ==0) goto L46;
    return 1;
    L46:
    goto L10;
    L49:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.confirm.open() ==0) goto L19;
    return this.confirm.keyPressed(keyCode);
    L19:
    v4 = this.rows.iterator();
    L30:
    if (v4.hasNext() ==0) goto L76;
    v5 = ((ui.widget.Row)v4.next());
    if (v5.isCapturingKeys() ==0) goto L73;
    if (v5.keyPressed(keyCode, scanCode, modifiers) ==0) goto L73;
    return 1;
    L73:
    goto L30;
    L76:
    return 0;
}

