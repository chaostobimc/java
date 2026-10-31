// class net/friendswithmoney/fwm/ui/widget/TabStrip  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field HEIGHT I;
field labelKeys Ljava/util/List;;
field active I;
field x I;
field y I;
field width I;
public void <init>(List labelKeys) {
    this.labelKeys = labelKeys;
    return;
}

public void setBounds(int x, int y, int width) {
    this.x = x;
    this.y = y;
    this.width = width;
    return;
}

public int active() {
    return this.active;
}

public int setActive(int index) {
    if (index <0) goto L22;
    if (index >= this.labelKeys.size()) goto L22;
    this.active = index;
    L22:
    return;
}

public int size() {
    return this.labelKeys.size();
}

private int slotW() {
    return Math.max(1, (this.width / this.labelKeys.size()));
}

private int slotX(int slot) {
    return (this.x + (slot * this.slotW()));
}

public int indexAt(double mouseX, double a2) {
    if (cmp(v3,((d)this.y)) <0) goto L23;
    if (cmp(v3,((d)(this.y + 13))) <0) goto L25;
    L23:
    return m1;
    L25:
    v5 = 0;
    L28:
    if (v5 >= this.labelKeys.size()) goto L80;
    if (cmp(mouseX,((d)this.slotX(v5))) <0) goto L74;
    if (cmp(mouseX,((d)(this.slotX(v5) + this.slotW()))) >=0) goto L74;
    return v5;
    L74:
    v5 = v5 + 1;
    goto L28;
    L80:
    return m1;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.indexAt(((d)mouseX), ((d)mouseY));
    v6 = 0;
    L14:
    if (v6 >= this.labelKeys.size()) goto L217;
    if (v6 != this.active) goto L41;
    goto L42;
    L41:
    L42:
    v7 = 0;
    v8 = this.slotX(v6);
    v9 = (this.slotW() - 2);
    if (v7 ==0) goto L82;
    goto L85;
    L82:
    L85:
    if (v7 !=0) goto L97;
    if (v6 != v5) goto L103;
    L97:
    goto L106;
    L103:
    L106:
    ui.FwmTheme.box(this.y, v9, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v10 = font.plainSubstrByWidth(ui.Lang.str(((String)this.labelKeys.get(v6)), new Object[0]), (v9 - 4));
    Objects.requireNonNull(font);
    if (v7 ==0) goto L191;
    goto L207;
    L191:
    if (v6 != v5) goto L204;
    goto L207;
    L204:
    L207:
    network.chat.Component.literal(v10).text((v8 + ((v9 - font.width(v10)) / 2)), ((this.y + ((13 - 9) / 2)) + 1), ui.FwmTheme.TEXT, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    v6 = v6 + 1;
    goto L14;
    L217:
    return;
}

