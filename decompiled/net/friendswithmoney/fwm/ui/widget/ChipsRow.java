// class net/friendswithmoney/fwm/ui/widget/ChipsRow  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/widget/Row
field CHIP_H I;
field GAP I;
field chips Ljava/util/List;;
field getter Ljava/util/function/IntSupplier;;
field setter Ljava/util/function/IntConsumer;;
public void <init>(network.chat.Component label, List chips, function.IntSupplier getter, function.IntConsumer setter) {
    this.chips = chips;
    this.getter = getter;
    this.setter = setter;
    return;
}

private int chipW(client.gui.Font font, ui.widget.ChipsRow$Chip chip) {
    return (font.width(chip.label()) + 8);
}

private int startX(client.gui.Font font) {
    v2 = -3;
    v3 = this.chips.iterator();
    L13:
    if (v3.hasNext() ==0) goto L48;
    v4 = ((ui.widget.ChipsRow$Chip)v3.next());
    v2 = (v2 + (this.chipW(font, v4) + 3));
    goto L13;
    L48:
    return Math.max((((this.x + this.padding()) + font.width(this.label)) + 6), (this.controlRight() - v2));
}

protected network.chat.Component trimmedLabel(client.gui.Font font) {
    return this.label;
}

protected void renderControl(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.startX(font);
    v6 = (this.y + 6);
    v7 = this.getter.getAsInt();
    v8 = this.chips.iterator();
    L38:
    if (v8.hasNext() ==0) goto L236;
    v9 = ((ui.widget.ChipsRow$Chip)v8.next());
    v10 = this.chipW(font, v9);
    if (v9.value() != v7) goto L83;
    goto L84;
    L83:
    L84:
    v11 = 0;
    if (mouseX < v5) goto L122;
    if (mouseX >= (v5 + v10)) goto L122;
    if (mouseY < v6) goto L122;
    if (mouseY >= (v6 + 13)) goto L122;
    goto L123;
    L122:
    L123:
    v12 = 0;
    if (v11 ==0) goto L145;
    goto L148;
    L145:
    L148:
    if (v11 !=0) goto L158;
    if (v12 ==0) goto L164;
    L158:
    goto L167;
    L164:
    L167:
    ui.FwmTheme.box(v6, v10, 13, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    Objects.requireNonNull(font);
    if (v11 !=0) goto L211;
    if (v12 ==0) goto L217;
    L211:
    goto L220;
    L217:
    L220:
    font.text(network.chat.Component.literal(v9.label()), (v5 + 4), ((v6 + ((13 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    v5 = (v5 + (v10 + 3));
    goto L38;
    L236:
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.isHovered(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = client.Minecraft.getInstance().font;
    v7 = this.startX(v6);
    v8 = (this.y + 6);
    v9 = this.chips.iterator();
    L52:
    if (v9.hasNext() ==0) goto L150;
    v10 = ((ui.widget.ChipsRow$Chip)v9.next());
    v11 = this.chipW(v6, v10);
    if (cmp(mouseX,((d)v7)) <0) goto L138;
    if (cmp(mouseX,((d)(v7 + v11))) >=0) goto L138;
    if (cmp(mouseY,((d)v8)) <0) goto L138;
    if (cmp(mouseY,((d)(v8 + 13))) >=0) goto L138;
    this.setter.accept(v10.value());
    return 1;
    L138:
    v7 = (v7 + (v11 + 3));
    goto L52;
    L150:
    return 0;
}

