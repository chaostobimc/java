// class net/friendswithmoney/fwm/ui/widget/PriceRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field BOX_W I;
field BOX_H I;
field MAX_TYPED I;
field siteBuy Ljava/util/function/Supplier;;
field siteSell Ljava/util/function/Supplier;;
field own Ljava/util/function/Supplier;;
field setter Ljava/util/function/Consumer;;
field editing I;
field typed Ljava/lang/StringBuilder;;
public void <init>(network.chat.Component label, function.Supplier siteBuy, function.Supplier siteSell, function.Supplier own, function.Consumer setter) {
    this.editing = m1;
    this.siteBuy = siteBuy;
    this.siteSell = siteSell;
    this.own = own;
    this.setter = setter;
    return;
}

private int boxX(int i) {
    return ((this.controlRight() - ((2 - i) * 52)) - ((1 - i) * 4));
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = (([Ljava.lang.String;)this.own.get());
    v6 = (this.y + 6);
    v7 = 0;
    L26:
    if (v7 >= 2) goto L321;
    v8 = this.boxX(v7);
    if (mouseX < v8) goto L76;
    if (mouseX >= (v8 + 52)) goto L76;
    if (mouseY < v6) goto L76;
    if (mouseY >= (v6 + 14)) goto L76;
    goto L77;
    L76:
    L77:
    v9 = 0;
    if (this.editing != v7) goto L92;
    goto L93;
    L92:
    L93:
    v10 = 0;
    if (v10 !=0) goto L117;
    if (v9 ==0) goto L123;
    L117:
    goto L126;
    L123:
    L126:
    ui.FwmTheme.box(v8, v6, 52, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v5 == null) goto L153;
    if (v5[v7] == null) goto L153;
    if (v5[v7].isBlank() ==0) goto L157;
    L153:
    goto L162;
    L157:
    L162:
    v11 = v5[v7];
    if (v10 ==0) goto L184;
    goto L226;
    L184:
    if (v11 == null) goto L194;
    goto L226;
    L194:
    if (v7 !=0) goto L214;
    goto L226;
    L214:
    L226:
    v12 = ((String)this.siteSell.get());
    if (v10 ==0) goto L239;
    goto L262;
    L239:
    if (v11 == null) goto L259;
    if (v7 !=0) goto L254;
    goto L262;
    L254:
    goto L262;
    L259:
    L262:
    v13 = ui.FwmTheme.TEXT_DIM;
    if (v12 != null) goto L275;
    goto L277;
    L275:
    L277:
    v14 = network.chat.Component.literal("-".plainSubstrByWidth(v12, 48));
    ctx.text(font, v14, (v8 + ((52 - font.width(v14)) / 2)), (v6 + 4), v13, 0);
    v7 = v7 + 1;
    goto L26;
    L321:
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    v6 = (this.y + 6);
    v7 = 0;
    L12:
    if (v7 >= 2) goto L98;
    v8 = this.boxX(v7);
    if (v5 !=0) goto L92;
    if (cmp(mouseX,((d)v8)) <0) goto L92;
    if (cmp(mouseX,((d)(v8 + 52))) >=0) goto L92;
    if (cmp(mouseY,((d)v6)) <0) goto L92;
    if (cmp(mouseY,((d)(v6 + 14))) >=0) goto L92;
    this.commit();
    this.editing = v7;
    this.typed = ?;
    return 1;
    L92:
    v7 = v7 + 1;
    goto L12;
    L98:
    this.commit();
    return 0;
}

public boolean isCapturingKeys() {
    if (this.editing <0) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public StringBuilder keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.editing >=0) goto L9;
    return 0;
    L9:
    if (keyCode != 41) goto L27;
    this.editing = m1;
    this.typed = null;
    return 1;
    L27:
    if (keyCode == 40) goto L45;
    if (keyCode == 88) goto L45;
    if (keyCode != 43) goto L51;
    L45:
    this.commit();
    return 1;
    L51:
    if (keyCode != 42) goto L85;
    if (this.typed.isEmpty() !=0) goto L83;
    this.typed.setLength((this.typed.length() - 1));
    L83:
    return 1;
    L85:
    if (this.typed.length() < 10) goto L99;
    return 1;
    L99:
    v4 = 0;
    if (util.GLFW.digit(keyCode) <0) goto L122;
    v4 = ((c)(48 + util.GLFW.digit(keyCode)));
    goto L190;
    L122:
    if (keyCode != 44) goto L135;
    v4 = 44;
    goto L190;
    L135:
    if (keyCode == 46) goto L148;
    if (keyCode != 330) goto L155;
    L148:
    v4 = 46;
    goto L190;
    L155:
    if (keyCode != 14) goto L168;
    v4 = 75;
    goto L190;
    L168:
    if (keyCode != 16) goto L181;
    v4 = 77;
    goto L190;
    L181:
    if (keyCode != 5) goto L190;
    v4 = 66;
    L190:
    if (v4 ==0) goto L205;
    this.typed.append(v4);
    L205:
    return 1;
}

public void commitTyped() {
    this.commit();
    return;
}

private void commit() {
    if (this.editing >=0) goto L8;
    return;
    L8:
    v1 = (([Ljava.lang.String;)this.own.get());
    if (v1 != null) goto L32;
    goto L39;
    L32:
    L39:
    v2 = (([Ljava.lang.String;)v1.clone());
    v2[this.editing] = this.typed.toString().trim();
    this.editing = m1;
    this.typed = null;
    this.setter.accept(v2);
    return;
}

