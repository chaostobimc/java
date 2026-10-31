// class net/friendswithmoney/fwm/ui/widget/TextInputRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field MAX_BOX_W I;
field BOX_H I;
field getter Ljava/util/function/Supplier;;
field setter Ljava/util/function/Consumer;;
field empty Ljava/lang/String;;
field max I;
field typed Ljava/lang/StringBuilder;;
public void <init>(network.chat.Component label, function.Supplier getter, function.Consumer setter, String empty, int max) {
    this.getter = getter;
    this.setter = setter;
    this.empty = empty;
    this.max = max;
    return;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = Math.min(120, this.controlWidth());
    v6 = (this.controlRight() - v5);
    v7 = (this.y + 6);
    if (mouseX < v6) goto L65;
    if (mouseX >= (v6 + v5)) goto L65;
    if (mouseY < v7) goto L65;
    if (mouseY >= (v7 + 14)) goto L65;
    goto L66;
    L65:
    L66:
    v8 = 0;
    if (this.typed != null) goto L92;
    if (v8 ==0) goto L98;
    L92:
    goto L101;
    L98:
    L101:
    ui.FwmTheme.box(v6, v7, v5, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v9 = ((String)this.getter.get());
    if (this.typed != null) goto L142;
    if (v9 == null) goto L138;
    if (v9.isBlank() ==0) goto L142;
    L138:
    goto L143;
    L142:
    L143:
    v10 = 0;
    if (this.typed == null) goto L167;
    goto L181;
    L167:
    if (v10 ==0) goto L179;
    goto L181;
    L179:
    L181:
    v11 = v9;
    v12 = network.chat.Component.literal(font.plainSubstrByWidth(v11, (v5 - 6)));
    if (this.typed == null) goto L224;
    goto L238;
    L224:
    if (v10 ==0) goto L235;
    goto L238;
    L235:
    L238:
    v12.text((v6 + 4), (v7 + 4), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, ui.FwmTheme.TEXT, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    v6 = Math.min(120, this.controlWidth());
    v7 = (this.controlRight() - v6);
    v8 = (this.y + 6);
    if (v5 !=0) goto L111;
    if (cmp(mouseX,((d)v7)) <0) goto L111;
    if (cmp(mouseX,((d)(v7 + v6))) >=0) goto L111;
    if (cmp(mouseY,((d)v8)) <0) goto L111;
    if (cmp(mouseY,((d)(v8 + 14))) >=0) goto L111;
    v9 = ((String)this.getter.get());
    if (v9 != null) goto L101;
    goto L103;
    L101:
    L103:
    ?.typed = ?;
    return 1;
    L111:
    this.commit();
    return 0;
}

public boolean isCapturingKeys() {
    if (this.typed == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.typed != null) goto L9;
    return 0;
    L9:
    if (keyCode != 41) goto L23;
    this.typed = null;
    goto L74;
    L23:
    if (keyCode == 40) goto L35;
    if (keyCode != 88) goto L42;
    L35:
    this.commit();
    goto L74;
    L42:
    if (keyCode != 42) goto L74;
    if (this.typed.isEmpty() !=0) goto L74;
    this.typed.setLength((this.typed.length() - 1));
    L74:
    return 1;
}

public StringBuilder charTyped(client.input.CharacterEvent input) {
    if (this.typed != null) goto L9;
    return 0;
    L9:
    if (this.typed.length() >= this.max) goto L42;
    if (input.isAllowedChatCharacter() ==0) goto L42;
    this.typed.append(input.codepointAsString());
    L42:
    return 1;
}

public void commitTyped() {
    this.commit();
    return;
}

private void commit() {
    if (this.typed != null) goto L8;
    return;
    L8:
    v1 = this.typed.toString().trim();
    this.typed = null;
    this.setter.accept(v1);
    return;
}

