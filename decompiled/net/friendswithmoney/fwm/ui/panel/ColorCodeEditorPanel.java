// class net/friendswithmoney/fwm/ui/panel/ColorCodeEditorPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field BTN_H I;
field NAME_Y I;
field NAME_H I;
field COMMAND_Y I;
field STATUS_Y I;
field TOP_H I;
field HEAD_H I;
field SCROLL_W I;
field GAP I;
field SCROLL_STEP I;
field PREVIEW_Y I;
field PREVIEW_H I;
field COLORS_Y I;
field SWATCH_H I;
field FORMATS_Y I;
field HEX_Y I;
field IMPORT_Y I;
field CONTENT_H I;
field HEX_FIELD_W I;
field RED I;
field GOLD I;
field COPIED_MS J;
field FLASH_MS J;
field DOUBLE_CLICK_MS J;
field FORMATS [Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field FORMAT_KEYS [Ljava/lang/String;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field user Lnet/friendswithmoney/fwm/ui/panel/ColorCodePanel$User;;
field name Lnet/minecraft/client/gui/components/EditBox;;
field hex Lnet/minecraft/client/gui/components/EditBox;;
field importField Lnet/minecraft/client/gui/components/EditBox;;
field hexText Ljava/lang/String;;
field importText Ljava/lang/String;;
field hsvRows Ljava/util/List;;
field hue D;
field sat D;
field val D;
field hexError Z;
field syncing Z;
field seenVersion I;
field scroll I;
field copiedAt J;
field flashAt J;
field dragging Lnet/minecraft/client/gui/components/EditBox;;
field draggingRow Lnet/friendswithmoney/fwm/ui/widget/ColorRow;;
field lastClicked Lnet/minecraft/client/gui/components/EditBox;;
field lastClickAt J;
public void <init>(ui.panel.PanelHost host, ui.panel.ColorCodePanel$User user) {
    this.hexText = "FF3DAF";
    this.importText = "";
    this.hsvRows = ?;
    this.seenVersion = m1;
    this.host = host;
    this.user = user;
    this.setHsvFrom(Integer.parseInt(this.hexText, 16));
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.colorcodes.editor", new Object[0]);
}

protected void onResize() {
    this.detachFields();
    v1 = client.Minecraft.getInstance().font;
    this.name = ?;
    this.name.setBordered(0);
    this.name.setMaxLength(64);
    this.name.setTextColor(m1);
    this.name.setHint(ui.Lang.of("fwm.colorcodes.placeholder", new Object[0]));
    this.name.addFormatter(/*indy*/format(this));
    this.name.setResponder(/*indy*/accept(this));
    this.hex = ?;
    this.hex.setBordered(0);
    this.hex.setMaxLength(7);
    this.hex.setTextColor(ui.FwmTheme.TEXT);
    this.hex.setValue(this.hexText);
    this.hex.setResponder(/*indy*/accept(this));
    this.importField = ?;
    this.importField.setBordered(0);
    this.importField.setMaxLength(256);
    this.importField.setTextColor(ui.FwmTheme.TEXT);
    this.importField.setHint(ui.Lang.of("fwm.colorcodes.importPlaceholder", new Object[0]));
    this.importField.setValue(this.importText);
    this.importField.setResponder(/*indy*/accept(this));
    this.hsvRows.clear();
    this.hsvRows.add(?);
    this.hsvRows.add(?);
    this.hsvRows.add(?);
    this.seenVersion = m1;
    this.syncFromSession();
    this.host.attach(this.name);
    this.host.attach(this.hex);
    this.host.attach(this.importField);
    this.scroll = Math.max(0, Math.min(this.maxScroll(), this.scroll));
    this.position();
    return;
}

private void detachFields() {
    if (this.name == null) goto L20;
    this.host.detach(this.name);
    L20:
    if (this.hex == null) goto L40;
    this.host.detach(this.hex);
    L40:
    if (this.importField == null) goto L60;
    this.host.detach(this.importField);
    L60:
    return;
}

public void onHidden() {
    this.detachFields();
    this.dragging = null;
    this.draggingRow = null;
    return;
}

private void syncFromSession() {
    if (this.user.version != this.seenVersion) goto L15;
    return;
    L15:
    this.seenVersion = this.user.version;
    this.syncing = 1;
    this.name.setValue(this.user.code.text());
    this.name.moveCursorToEnd(0);
    this.syncing = 0;
    if (this.name.getValue().equals(this.user.code.text()) !=0) goto L101;
    this.user.code.reconcile(this.name.getValue());
    L101:
    return;
}

private util.FormattedCharSequence formatName(String visible, int first) {
    v3 = this.user.code.styles();
    v4 = ?;
    v5 = 0;
    L27:
    if (v5 >= visible.length()) goto L104;
    v6 = (first + v5);
    if (v6 >= v3.size()) goto L67;
    goto L70;
    L67:
    L70:
    v7 = colorcode.CodeStyle.DEFAULT;
    v4.add(util.FormattedCharSequence.forward(String.valueOf(visible.charAt(v5)), ui.panel.ColorCodePanel.style(v7, 0)));
    v5 = v5 + 1;
    goto L27;
    L104:
    return util.FormattedCharSequence.composite(v4);
}

private int selectionStart() {
    return this.clampToText(Math.min(this.name.getCursorPosition(), ((mixin.TextFieldWidgetAccessor)this.name).getFwmSelectionEnd()));
}

private int selectionEnd() {
    return this.clampToText(Math.max(this.name.getCursorPosition(), ((mixin.TextFieldWidgetAccessor)this.name).getFwmSelectionEnd()));
}

private int clampToText(int index) {
    return Math.max(0, Math.min(this.user.code.text().length(), index));
}

private int hasSelection() {
    if (this.selectionStart() >= this.selectionEnd()) goto L15;
    goto L16;
    L15:
    L16:
    return 0;
}

private void onSelection(function.BiConsumer edit) {
    if (this.hasSelection() !=0) goto L15;
    this.flashAt = System.currentTimeMillis();
    return;
    L15:
    edit.accept(Integer.valueOf(this.selectionStart()), Integer.valueOf(this.selectionEnd()));
    this.focus(this.name);
    return;
}

private void focus(client.gui.components.EditBox field) {
    if (field != this.name) goto L16;
    goto L17;
    L16:
    L17:
    1.setFocused(0);
    if (field != this.hex) goto L36;
    goto L37;
    L36:
    L37:
    1.setFocused(0);
    if (field != this.importField) goto L56;
    goto L57;
    L56:
    L57:
    1.setFocused(0);
    return;
}

private static long hsv(double h, double a1, double s) {
    v6 = ((((h % 1) + 1) % 1) * 6.0);
    v8 = (((i)Math.floor(v6)) % 6);
    v9 = (v6 - Math.floor(v6));
    v11 = (v4 * (1 - s));
    v13 = (v4 * (1 - (v9 * s)));
    v15 = (v4 * (1 - ((1 - v9) * s)));
    // switch (v8) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=111), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=4), Operand(op_type=<OperandTypes.BRANCH: 40>, value=36), Operand(op_type=<OperandTypes.BRANCH: 40>, value=51), Operand(op_type=<OperandTypes.BRANCH: 40>, value=66), Operand(op_type=<OperandTypes.BRANCH: 40>, value=81), Operand(op_type=<OperandTypes.BRANCH: 40>, value=96)]
    L104:
    v17 = v4;
    v19 = v15;
    v21 = v11;
    goto L191;
    L119:
    v17 = v13;
    v19 = v4;
    v21 = v11;
    goto L191;
    L134:
    v17 = v11;
    v19 = v4;
    v21 = v15;
    goto L191;
    L149:
    v17 = v11;
    v19 = v13;
    v21 = v4;
    goto L191;
    L164:
    v17 = v15;
    v19 = v11;
    v21 = v4;
    goto L191;
    L179:
    v17 = v4;
    v19 = v11;
    v21 = v13;
    L191:
    return (((((i)Math.round((v17 * 255.0))) << 16) | (((i)Math.round((v19 * 255.0))) << 8)) | ((i)Math.round((v21 * 255.0))));
}

private double setHsvFrom(int rgb) {
    v2 = (((d)((rgb >> 16) & 255)) / 255.0);
    v4 = (((d)((rgb >> 8) & 255)) / 255.0);
    v6 = (((d)(rgb & 255)) / 255.0);
    v8 = Math.max(v2, Math.max(v4, v6));
    v10 = Math.min(v2, Math.min(v4, v6));
    v12 = (v8 - v10);
    this.val = v8;
    if (cmp(v8,0) !=0) goto L92;
    goto L97;
    L92:
    L97:
    0.sat = (v12 / v8);
    if (cmp(v12,0) !=0) goto L108;
    return;
    L108:
    if (cmp(v8,v2) !=0) goto L132;
    v14 = (((v4 - v6) / v12) % 6.0);
    goto L169;
    L132:
    if (cmp(v8,v4) !=0) goto L156;
    v14 = (((v6 - v2) / v12) + 2.0);
    goto L169;
    L156:
    v14 = (((v2 - v4) / v12) + 4.0);
    L169:
    this.hue = (((v14 / 6.0) + 1) % 1);
    return;
}

private void pushHex() {
    this.syncing = 1;
    new Object[1][0] = Integer.valueOf(ui.panel.ColorCodeEditorPanel.hsv(this.hue, this.sat, this.val));
    this.hex.setValue(String.format("%06X", new Object[1]));
    this.syncing = 0;
    this.hexError = 0;
    return;
}

private int currentHexRgb() {
    v1 = colorcode.ColorCode.normalizeHex(this.hexText);
    if (v1 == null) goto L21;
    goto L36;
    L21:
    L36:
    return ui.panel.ColorCodeEditorPanel.hsv(this.hue, this.sat, this.val);
}

private void applyHex() {
    v1 = colorcode.ColorCode.normalizeHex(this.hexText);
    if (v1 != null) goto L18;
    this.hexError = 1;
    return;
    L18:
    this.hexError = 0;
    this.onSelection(/*indy*/accept(this, v1));
    return;
}

private int bodyTop() {
    return (this.y + 62);
}

private int bodyHeight() {
    return Math.max(0, ((this.y + this.height) - this.bodyTop()));
}

private int maxScroll() {
    return Math.max(0, (274 - this.bodyHeight()));
}

private int contentW() {
    return ((this.width - 4) - 2);
}

private int at(int offset) {
    return ((this.bodyTop() - this.scroll) + offset);
}

private int inBody(double my) {
    if (cmp(my,((d)this.bodyTop())) <0) goto L29;
    if (cmp(my,((d)(this.y + this.height))) >=0) goto L29;
    goto L30;
    L29:
    L30:
    return 0;
}

private int copyW(client.gui.Font font) {
    return (Math.max(font.width(ui.Lang.of("fwm.colorcodes.copy", new Object[0])), font.width(ui.Lang.of("fwm.colorcodes.copied", new Object[0]))) + 12);
}

private int modeW(client.gui.Font font, colorcode.ColorCode$Mode mode) {
    return (font.width(ui.Lang.of(ui.panel.ColorCodeEditorPanel.modeKey(mode), new Object[0])) + 12);
}

private static String modeKey(colorcode.ColorCode$Mode mode) {
    if (mode != colorcode.ColorCode$Mode.SIGN) goto L13;
    goto L16;
    L13:
    L16:
    return "fwm.colorcodes.rename";
}

private int swatchW() {
    return (this.contentW() / 8);
}

private int formatW() {
    return ((this.contentW() - 8) / 3);
}

private int hexRowY() {
    return (this.at(123) + 11);
}

private int applyX() {
    return (((this.x + 17) + 52) + 4);
}

private int loadW(client.gui.Font font) {
    return (font.width(ui.Lang.of("fwm.colorcodes.importLoad", new Object[0])) + 12);
}

private void position() {
    v1 = client.Minecraft.getInstance().font;
    this.name.setX((this.x + 4));
    this.name.setY(((this.y + 17) + 4));
    this.hex.setX(((this.x + 17) + 4));
    this.hex.setY((this.hexRowY() + 2));
    v2 = this.loadW(v1);
    this.importField.setX((this.x + 4));
    this.importField.setY(((this.at(245) + 11) + 2));
    this.importField.setWidth(Math.max(20, (((this.contentW() - v2) - 4) - 8)));
    v3 = (((this.hexRowY() + 13) + 2) + 11);
    v4 = 0;
    L142:
    if (v4 >= this.hsvRows.size()) goto L194;
    ((ui.widget.ColorRow)this.hsvRows.get(v4)).setBounds(this.x, (v3 + (v4 * 26)), this.contentW());
    v4 = v4 + 1;
    goto L142;
    L194:
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    if (this.name != null) goto L8;
    return;
    L8:
    this.syncFromSession();
    this.position();
    this.renderTop(ctx, font, mouseX, mouseY, delta);
    ctx.enableScissor(this.x, this.bodyTop(), (this.x + this.width), (this.y + this.height));
    this.renderPreview(ctx, font);
    this.renderColors(ctx, font, mouseX, mouseY);
    this.renderFormats(ctx, font, mouseX, mouseY);
    this.renderHex(ctx, font, mouseX, mouseY, delta);
    this.renderImport(ctx, font, mouseX, mouseY, delta);
    ctx.disableScissor();
    this.renderScrollbar(ctx);
    if (this.inBody(((d)mouseY)) ==0) goto L131;
    this.renderHoverTip(ctx, font, mouseX, mouseY);
    L131:
    return;
}

private void renderTop(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.user.code;
    v7 = this.user.mode;
    v8 = v6.length();
    if (v8 <= v7.limit) goto L39;
    goto L40;
    L39:
    L40:
    v9 = 0;
    v10 = this.x;
    v11 = colorcode.ColorCode$Mode.values();
    v12 = v11.length;
    v13 = 0;
    L61:
    if (v13 >= v12) goto L157;
    v14 = v11[v13];
    v15 = this.modeW(font, v14);
    if (v14 != v7) goto L137;
    goto L138;
    L137:
    L138:
    ui.panel.ColorCodePanel.button(font, v10, this.y, v15, 13, ui.Lang.of(ui.panel.ColorCodeEditorPanel.modeKey(v14), new Object[0]), ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v10, this.y, v15, 13), 1, 0, 1);
    v10 = (v10 + (v15 + 2));
    v13 = v13 + 1;
    goto L61;
    L157:
    v11 = this.copyW(font);
    v12 = ((this.x + this.width) - v11);
    if (cmp((System.currentTimeMillis() - this.copiedAt),1600) >=0) goto L197;
    goto L198;
    L197:
    L198:
    v13 = 0;
    if (v9 !=0) goto L220;
    if (v6.text().isEmpty() !=0) goto L220;
    goto L221;
    L220:
    L221:
    v14 = 0;
    if (v13 ==0) goto L246;
    goto L249;
    L246:
    L249:
    if (v14 ==0) goto L286;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v12, this.y, v11, 13) ==0) goto L286;
    goto L287;
    L286:
    L287:
    ui.panel.ColorCodePanel.button(v12, this.y, v11, 13, "fwm.colorcodes.copied", ui.Lang.of("fwm.colorcodes.copy", new Object[0]), 1, 0, v13, v14);
    new Object[2][0] = Integer.valueOf(v8);
    new Object[2][1] = Integer.valueOf(v7.limit);
    v15 = ui.Lang.str("fwm.colorcodes.counter", new Object[2]);
    if (v9 ==0) goto L336;
    goto L362;
    L336:
    if (cmp(((d)v8),(((d)v7.limit) * 0.85)) <0) goto L359;
    goto L362;
    L359:
    L362:
    v16 = ui.FwmTheme.TEXT_DIM;
    Objects.requireNonNull(font);
    ctx.text(font, v15, ((v12 - 5) - font.width(v15)), ((this.y + ((13 - 9) / 2)) + 1), v16, 0);
    if (this.name.isFocused() ==0) goto L441;
    goto L444;
    L441:
    L444:
    ui.FwmTheme.box(this.x, (this.y + 17), this.width, 16, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.name.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    if (this.name.isFocused() !=0) goto L568;
    if (this.hasSelection() ==0) goto L568;
    v17 = Math.max((this.x + 4), this.name.getScreenX(this.selectionStart()));
    v18 = Math.min(((this.x + this.width) - 4), this.name.getScreenX(this.selectionEnd()));
    if (v18 <= v17) goto L568;
    ctx.fill(v17, (((this.y + 17) + 16) - 3), v18, (((this.y + 17) + 16) - 2), ui.FwmTheme.ACCENT);
    L568:
    if (v9 ==0) goto L606;
    goto L609;
    L606:
    L609:
    v17 = network.chat.Component.literal(v6.serialize()).append(-43691.withColor(ui.FwmTheme.TEXT));
    ctx.text(font, ui.panel.ColorCodePanel.trimmed(font, v17, this.width), this.x, (this.y + 37), ui.FwmTheme.TEXT, 0);
    if (cmp((System.currentTimeMillis() - this.flashAt),900) >=0) goto L666;
    goto L667;
    L666:
    L667:
    v20 = 0;
    if (v9 ==0) goto L705;
    new Object[1][0] = Integer.valueOf(v7.limit);
    v18 = ui.Lang.of("fwm.colorcodes.tooLong", new Object[1]);
    v19 = -43691;
    goto L775;
    L705:
    if (this.hasSelection() !=0) goto L743;
    v18 = ui.Lang.of("fwm.colorcodes.hint", new Object[0]);
    if (v20 ==0) goto L735;
    goto L738;
    L735:
    L738:
    v19 = ui.FwmTheme.TEXT_DIM;
    goto L775;
    L743:
    new Object[1][0] = Integer.valueOf((this.selectionEnd() - this.selectionStart()));
    v18 = ui.Lang.of("fwm.colorcodes.selected", new Object[1]);
    v19 = ui.FwmTheme.TEXT_DIM;
    L775:
    ctx.text(font, ui.panel.ColorCodePanel.trimmed(font, v18, this.width), this.x, (this.y + 49), v19, 0);
    ui.FwmTheme.divider(ctx, this.x, (this.bodyTop() - 2), this.width);
    return;
}

private void heading(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String key, int top) {
    ctx.text(font, ui.Lang.of(key, new Object[0]), this.x, (top + 1), ui.FwmTheme.ACCENT, 0);
    return;
}

private void renderPreview(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = this.at(0);
    this.heading(ctx, font, "fwm.colorcodes.preview", v3);
    v4 = (v3 + 11);
    if (this.user.mode != colorcode.ColorCode$Mode.SIGN) goto L41;
    goto L44;
    L41:
    L44:
    v5 = ui.panel.ColorCodeEditorPanel.previewIcon(world.item.Items.NAME_TAG);
    ctx.item(v5, (this.x + 2), (v4 + 3));
    if (this.user.code.text().isEmpty() ==0) goto L96;
    goto L106;
    L96:
    L106:
    v6 = ui.panel.ColorCodePanel.text(this.user.code);
    v7 = (this.contentW() - 24);
    v8 = Math.min(v7, (font.width(v6) + 8));
    v9 = (this.x + 22);
    v10 = 16;
    v11 = (v4 + ((22 - v10) / 2));
    ui.panel.ColorCodePanel.tooltipBox(ctx, v9, v11, v8, v10);
    ctx.text(font, ui.panel.ColorCodePanel.trimmed(font, v6, (v8 - 8)), (v9 + 4), (v11 + 4), m1, 1);
    return;
}

private void renderColors(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.at(37);
    this.heading(ctx, font, "fwm.colorcodes.colors", v5);
    v6 = this.swatchW();
    v7 = 0;
    L28:
    if (v7 >= 16) goto L148;
    v8 = (this.x + ((v7 % 8) * v6));
    v9 = ((v5 + 11) + ((v7 / 8) * 13));
    if (this.inBody(((d)mouseY)) ==0) goto L101;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v8, v9, (v6 - 2), 11) ==0) goto L101;
    goto L102;
    L101:
    L102:
    v10 = 0;
    if (v10 ==0) goto L136;
    goto L139;
    L136:
    L139:
    ui.FwmTheme.box(v8, v9, (v6 - 2), 11, (-16777216 | colorcode.ColorCode.RGB[v7]), ui.FwmTheme.TEXT, ui.FwmTheme.OFF);
    v7 = v7 + 1;
    goto L28;
    L148:
    return;
}

private void renderFormats(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.at(77);
    this.heading(ctx, font, "fwm.colorcodes.formats", v5);
    v6 = this.formatW();
    v7 = this.hasSelection();
    v8 = 0;
    L34:
    if (v8 >= ui.panel.ColorCodeEditorPanel.FORMAT_KEYS.length) goto L288;
    v9 = (this.x + ((v8 % 3) * (v6 + 4)));
    v10 = ((v5 + 11) + ((v8 / 3) * 16));
    v11 = ui.Lang.of(ui.panel.ColorCodeEditorPanel.FORMAT_KEYS[v8], new Object[0]);
    v12 = 0;
    if (v8 >= ui.panel.ColorCodeEditorPanel.FORMATS.length) goto L230;
    v13 = ui.panel.ColorCodeEditorPanel.FORMATS[v8];
    if (v13 != colorcode.CodeStyle$Format.BOLD) goto L126;
    goto L127;
    L126:
    L127:
    if (v13 != colorcode.CodeStyle$Format.UNDERLINE) goto L145;
    goto L146;
    L145:
    L146:
    if (v13 != colorcode.CodeStyle$Format.ITALIC) goto L164;
    goto L165;
    L164:
    L165:
    if (v13 != colorcode.CodeStyle$Format.STRIKE) goto L183;
    goto L184;
    L183:
    L184:
    v11 = 1.withItalic(Boolean.valueOf(0)).setStyle(1.withStrikethrough(Boolean.valueOf(0)));
    if (v7 ==0) goto L227;
    if (this.user.code.allHave(this.selectionStart(), this.selectionEnd(), v13) ==0) goto L227;
    goto L228;
    L227:
    L228:
    v12 = 0;
    L230:
    if (this.inBody(((d)mouseY)) ==0) goto L275;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v9, v10, v6, 13) ==0) goto L275;
    goto L276;
    L275:
    L276:
    ui.panel.ColorCodePanel.button(font, v9, v10, v6, 13, v11, 1, 0, v12, 1);
    v8 = v8 + 1;
    goto L34;
    L288:
    return;
}

private void renderHex(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.heading(ctx, font, "fwm.colorcodes.hex", this.at(123));
    v6 = this.hexRowY();
    v7 = this.currentHexRgb();
    ui.FwmTheme.box(ctx, this.x, v6, 13, 13, (-16777216 | v7), ui.FwmTheme.OFF);
    if (this.hexError ==0) goto L79;
    goto L98;
    L79:
    if (this.hex.isFocused() ==0) goto L95;
    goto L98;
    L95:
    L98:
    ui.FwmTheme.box(v6, 52, 13, ui.FwmTheme.INSET, -43691, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.hex.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v8 = this.applyX();
    v9 = ((this.x + this.contentW()) - v8);
    if (this.inBody(((d)mouseY)) ==0) goto L187;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v8, v6, v9, 13) ==0) goto L187;
    goto L188;
    L187:
    L188:
    ui.panel.ColorCodePanel.button(font, v8, v6, v9, 13, ui.Lang.of("fwm.colorcodes.hexApply", new Object[0]), 1, 0, 0, 1);
    if (this.hexError ==0) goto L220;
    v10 = ui.Lang.of("fwm.colorcodes.hexError", new Object[0]);
    v11 = -43691;
    goto L269;
    L220:
    v12 = colorcode.ColorCode.nearest16(v7);
    new Object[2][0] = ui.Lang.str('fwm.colorcodes.color.' + v12, new Object[0]);
    new Object[2][1] = Character.valueOf(v12);
    v10 = ui.Lang.of("fwm.colorcodes.hexNearest", new Object[2]);
    v11 = ui.FwmTheme.TEXT_DIM;
    L269:
    ctx.text(font, ui.panel.ColorCodePanel.trimmed(font, v10, this.contentW()), this.x, ((v6 + 13) + 3), v11, 0);
    v12 = this.hsvRows.iterator();
    L309:
    if (v12.hasNext() ==0) goto L360;
    v13 = ((ui.widget.ColorRow)v12.next());
    if (this.inBody(((d)mouseY)) ==0) goto L351;
    goto L354;
    L351:
    L354:
    ctx.render(font, mouseX, mouseY, -1000);
    goto L309;
    L360:
    return;
}

private void renderImport(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.at(245);
    this.heading(ctx, font, "fwm.colorcodes.import", v6);
    v7 = (v6 + 11);
    v8 = this.loadW(font);
    v9 = ((this.contentW() - v8) - 4);
    if (this.importField.isFocused() ==0) goto L74;
    goto L77;
    L74:
    L77:
    ui.FwmTheme.box(this.x, v7, v9, 13, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.importField.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    v10 = ((this.x + v9) + 4);
    if (this.inBody(((d)mouseY)) ==0) goto L157;
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v10, v7, v8, 13) ==0) goto L157;
    goto L158;
    L157:
    L158:
    if (this.importText.isBlank() !=0) goto L173;
    goto L174;
    L173:
    L174:
    ui.panel.ColorCodePanel.button(v10, v7, v8, 13, ui.Lang.of("fwm.colorcodes.importLoad", new Object[0]), 1, 0, 0, 1, 0);
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    v2 = this.maxScroll();
    v3 = this.bodyHeight();
    if (v2 <=0) goto L18;
    if (v3 >0) goto L19;
    L18:
    return;
    L19:
    v4 = ((this.x + this.width) - 4);
    v5 = Math.max(10, ((v3 * v3) / 274));
    v6 = (this.bodyTop() + (((v3 - v5) * this.scroll) / v2));
    ctx.fill(v4, this.bodyTop(), (v4 + 4), (this.y + this.height), ui.FwmTheme.OFF);
    ctx.fill(v4, v6, (v4 + 4), (v6 + v5), ui.FwmTheme.ACCENT);
    return;
}

private void renderHoverTip(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = null;
    v6 = this.swatchW();
    v7 = (this.at(37) + 11);
    v8 = 0;
    L23:
    if (v8 >= 16) goto L123;
    if (v5 != null) goto L123;
    v9 = (this.x + ((v8 % 8) * v6));
    v10 = (v7 + ((v8 / 8) * 13));
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v9, v10, (v6 - 2), 11) ==0) goto L117;
    v11 = "0123456789abcdef".charAt(v8);
    v5 = ui.Lang.str('fwm.colorcodes.color.' + v11, new Object[0]) + '  &' + v11;
    L117:
    v8 = v8 + 1;
    goto L23;
    L123:
    v8 = this.formatW();
    v9 = (this.at(77) + 11);
    v10 = 0;
    L143:
    if (v10 >= ui.panel.ColorCodeEditorPanel.FORMAT_KEYS.length) goto L253;
    if (v5 != null) goto L253;
    v11 = (this.x + ((v10 % 3) * (v8 + 4)));
    v12 = (v9 + ((v10 / 3) * 16));
    if (ui.panel.ColorCodePanel.over(((d)mouseX), ((d)mouseY), v11, v12, v8, 13) ==0) goto L247;
    if (v10 >= ui.panel.ColorCodeEditorPanel.FORMATS.length) goto L238;
    goto L240;
    L238:
    L240:
    v5 = ui.panel.ColorCodeEditorPanel.FORMATS[v10].code + '  &' + 114;
    L247:
    v10 = v10 + 1;
    goto L143;
    L253:
    if (v5 != null) goto L259;
    return;
    L259:
    v10 = (font.width(v5) + 8);
    v11 = Math.max(this.x, Math.min(((this.x + this.width) - v10), (mouseX + 8)));
    if ((mouseY - 16) < this.y) goto L318;
    goto L323;
    L318:
    L323:
    v12 = (mouseY + 12);
    ui.panel.ColorCodePanel.tooltipBox(ctx, v11, v12, v10, 14);
    ctx.text(font, v5, (v11 + 4), (v12 + 3), ui.FwmTheme.TEXT, 0);
    return;
}

private static void previewIcon(world.item.Item item) {
    return ?;
    v1 = /*catch RuntimeException*/ ex;
    return world.item.ItemStack.EMPTY;
}

private static void click(double mx, double a1, int my) {
    return ?;
}

private boolean clickField(client.gui.components.EditBox field, double mx, double a3, int my) {
    v7 = System.currentTimeMillis();
    if (field != this.lastClicked) goto L31;
    if (cmp((v7 - this.lastClickAt),250) >=0) goto L31;
    goto L32;
    L31:
    L32:
    v9 = 0;
    this.lastClicked = field;
    this.lastClickAt = v7;
    this.focus(field);
    field.mouseClicked(ui.panel.ColorCodeEditorPanel.click(mx, my, v6), v9);
    this.dragging = field;
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.name == null) goto L16;
    if (this.contains(mouseX, mouseY) !=0) goto L18;
    L16:
    return 0;
    L18:
    if (v5 ==0) goto L25;
    return 1;
    L25:
    this.position();
    v6 = client.Minecraft.getInstance().font;
    if (cmp(mouseY,((d)this.bodyTop())) >=0) goto L58;
    return this.clickTop(v6, mouseX, mouseY, v5);
    L58:
    v7 = this.hsvRows.iterator();
    L69:
    if (v7.hasNext() ==0) goto L114;
    v8 = ((ui.widget.ColorRow)v7.next());
    if (v8.mouseClicked(mouseX, mouseY, v5) ==0) goto L111;
    this.draggingRow = v8;
    return 1;
    L111:
    goto L69;
    L114:
    v7 = this.swatchW();
    v8 = (this.at(37) + 11);
    v9 = 0;
    L134:
    if (v9 >= 16) goto L220;
    v10 = (this.x + ((v9 % 8) * v7));
    v11 = (v8 + ((v9 / 8) * 13));
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, v10, v11, (v7 - 2), 11) ==0) goto L214;
    v12 = String.valueOf("0123456789abcdef".charAt(v9));
    this.onSelection(/*indy*/accept(this, v12));
    return 1;
    L214:
    v9 = v9 + 1;
    goto L134;
    L220:
    v9 = this.formatW();
    v10 = (this.at(77) + 11);
    v11 = 0;
    L240:
    if (v11 >= ui.panel.ColorCodeEditorPanel.FORMAT_KEYS.length) goto L354;
    v12 = (this.x + ((v11 % 3) * (v9 + 4)));
    v13 = (v10 + ((v11 / 3) * 16));
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, v12, v13, v9, 13) ==0) goto L348;
    if (v11 >= ui.panel.ColorCodeEditorPanel.FORMATS.length) goto L325;
    v14 = ui.panel.ColorCodeEditorPanel.FORMATS[v11];
    this.onSelection(/*indy*/accept(this, v14));
    goto L346;
    L325:
    Objects.requireNonNull(this.user.code);
    this.onSelection(/*indy*/accept(this.user.code));
    L346:
    return 1;
    L348:
    v11 = v11 + 1;
    goto L240;
    L354:
    v11 = this.hexRowY();
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, (this.x + 17), v11, 52, 13) ==0) goto L395;
    this.clickField(this.hex, mouseX, mouseY, v5);
    return 1;
    L395:
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, this.applyX(), v11, ((this.x + this.contentW()) - this.applyX()), 13) ==0) goto L431;
    this.applyHex();
    return 1;
    L431:
    v12 = (this.at(245) + 11);
    v13 = this.loadW(v6);
    v14 = ((this.contentW() - v13) - 4);
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, this.x, v12, v14, 13) ==0) goto L494;
    this.clickField(this.importField, mouseX, mouseY, v5);
    return 1;
    L494:
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, ((this.x + v14) + 4), v12, v13, 13) ==0) goto L523;
    this.importCode();
    return 1;
    L523:
    return 1;
}

private void clickTop(client.gui.Font font, double mouseX, double a3, int mouseY) {
    v7 = this.x;
    v8 = colorcode.ColorCode$Mode.values();
    v9 = v8.length;
    v10 = 0;
    L19:
    if (v10 >= v9) goto L87;
    v11 = v8[v10];
    v12 = this.modeW(font, v11);
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, v7, this.y, v12, 13) ==0) goto L72;
    this.user.mode = v11;
    return 1;
    L72:
    v7 = (v7 + (v12 + 2));
    v10 = v10 + 1;
    goto L19;
    L87:
    v8 = this.copyW(font);
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, ((this.x + this.width) - v8), this.y, v8, 13) ==0) goto L129;
    this.copy();
    return 1;
    L129:
    if (ui.panel.ColorCodePanel.over(mouseX, mouseY, this.x, (this.y + 17), this.width, 16) ==0) goto L170;
    this.clickField(this.name, mouseX, mouseY, v6);
    return 1;
    L170:
    return 1;
}

private long copy() {
    v1 = this.user.code;
    if (v1.text().isEmpty() !=0) goto L32;
    if (v1.tooLong(this.user.mode) ==0) goto L33;
    L32:
    return;
    L33:
    client.Minecraft.getInstance().keyboardHandler.setClipboard(v1.command(this.user.mode));
    this.copiedAt = System.currentTimeMillis();
    return;
}

private void importCode() {
    if (this.importText.isBlank() ==0) goto L11;
    return;
    L11:
    v1 = colorcode.ColorCode.importCode(this.importText);
    this.user.load(v1.code(), v1.mode());
    this.syncFromSession();
    this.focus(this.name);
    return;
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    if (this.draggingRow == null) goto L19;
    return this.draggingRow.mouseDragged(mouseX, mouseY, v5);
    L19:
    if (this.dragging == null) goto L49;
    this.position();
    this.dragging.mouseDragged(ui.panel.ColorCodeEditorPanel.click(mouseX, mouseY, v5), 0, 0);
    return 1;
    L49:
    return 0;
}

public void mouseReleased() {
    if (this.draggingRow == null) goto L14;
    this.draggingRow.mouseReleased();
    L14:
    this.draggingRow = null;
    this.dragging = null;
    return;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.inBody(mouseY) !=0) goto L10;
    return 0;
    L10:
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - (((i)Math.signum(v5)) * 14))));
    return 1;
}

private boolean focused() {
    if (this.name != null) goto L9;
    return null;
    L9:
    if (this.name.isFocused() ==0) goto L24;
    return this.name;
    L24:
    if (this.hex.isFocused() ==0) goto L39;
    return this.hex;
    L39:
    if (this.importField.isFocused() ==0) goto L54;
    return this.importField;
    L54:
    return null;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    v4 = this.focused();
    if (v4 != null) goto L13;
    return 0;
    L13:
    if (keyCode == 40) goto L25;
    if (keyCode != 88) goto L53;
    L25:
    if (v4 != this.hex) goto L38;
    this.applyHex();
    L38:
    if (v4 != this.importField) goto L51;
    this.importCode();
    L51:
    return 1;
    L53:
    return v4.keyPressed(?);
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.focused();
    if (v2 == null) goto L21;
    if (v2.charTyped(input) ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

private void lambda$mouseClicked$1(colorcode.CodeStyle$Format format, Integer s, Integer e) {
    this.user.code.toggleFormat(s.intValue(), e.intValue(), format);
    return;
}

private void lambda$mouseClicked$0(String color, Integer s, Integer e) {
    this.user.code.applyColor(s.intValue(), e.intValue(), color);
    return;
}

private void lambda$applyHex$0(String normalized, Integer s, Integer e) {
    this.user.code.applyHex(s.intValue(), e.intValue(), normalized);
    return;
}

private int lambda$onResize$11(float t) {
    return ui.panel.ColorCodeEditorPanel.hsv(this.hue, this.sat, ((d)t));
}

private void lambda$onResize$10(double v) {
    this.val = v;
    this.pushHex();
    return;
}

private double lambda$onResize$9() {
    return this.val;
}

private int lambda$onResize$8(float t) {
    return ui.panel.ColorCodeEditorPanel.hsv(this.hue, ((d)t), this.val);
}

private void lambda$onResize$7(double v) {
    this.sat = v;
    this.pushHex();
    return;
}

private double lambda$onResize$6() {
    return this.sat;
}

private static int lambda$onResize$5(float t) {
    return ui.panel.ColorCodeEditorPanel.hsv(((d)t), 1, 1);
}

private void lambda$onResize$4(double v) {
    this.hue = v;
    this.pushHex();
    return;
}

private double lambda$onResize$3() {
    return this.hue;
}

private void lambda$onResize$2(String text) {
    this.importText = text;
    return;
}

private void lambda$onResize$1(String text) {
    v2 = ?;
    v3 = 0;
    v4 = 0;
    L13:
    if (v4 >= text.length()) goto L86;
    v5 = text.charAt(v4);
    if (v5 != 35) goto L54;
    if (v2.length() !=0) goto L54;
    v2.append(v5);
    goto L80;
    L54:
    if (Character.digit(v5, 16) <0) goto L80;
    if (v3 >= 6) goto L80;
    v2.append(v5);
    v3 = v3 + 1;
    L80:
    v4 = v4 + 1;
    goto L13;
    L86:
    if (v2.toString().equals(text) !=0) goto L109;
    this.hex.setValue(v2.toString());
    return;
    L109:
    this.hexText = text;
    if (this.syncing ==0) goto L122;
    return;
    L122:
    v4 = colorcode.ColorCode.normalizeHex(text);
    if (v4 == null) goto L149;
    this.setHsvFrom(Integer.parseInt(v4, 16));
    this.hexError = 0;
    L149:
    return;
}

private void lambda$onResize$0(String text) {
    if (this.syncing !=0) goto L18;
    this.user.code.reconcile(text);
    L18:
    return;
}

static colorcode.CodeStyle$Format[] <clinit>() {
    ui.panel.ColorCodeEditorPanel.FORMATS = colorcode.CodeStyle$Format.values();
    new String[6][0] = "fwm.colorcodes.bold";
    new String[6][1] = "fwm.colorcodes.underline";
    new String[6][2] = "fwm.colorcodes.italic";
    new String[6][3] = "fwm.colorcodes.strike";
    new String[6][4] = "fwm.colorcodes.obf";
    new String[6][5] = "fwm.colorcodes.reset";
    ui.panel.ColorCodeEditorPanel.FORMAT_KEYS = new String[6];
    return;
}

