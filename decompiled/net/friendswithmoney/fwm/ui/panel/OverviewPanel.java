// class net/friendswithmoney/fwm/ui/panel/OverviewPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field TILE_H I;
field GAP I;
field FILTER_H I;
field SECTION_H I;
field SCROLLBAR_W I;
field tiles Ljava/util/List;;
field open Ljava/util/function/IntConsumer;;
field typed Ljava/lang/String;;
field searching Z;
field scroll I;
field scrollbarShown Z;
public void <init>(List tiles, function.IntConsumer open) {
    this.typed = "";
    this.tiles = tiles;
    this.open = open;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.overview", new Object[0]);
}

public boolean showTitle() {
    return 0;
}

private boolean matchingOpen() {
    v1 = ?;
    v2 = this.typed.toLowerCase(Locale.ROOT);
    v3 = this.tiles.iterator();
    L29:
    if (v3.hasNext() ==0) goto L107;
    v4 = ((ui.panel.OverviewPanel$Tile)v3.next());
    if (v4.locked() ==0) goto L60;
    goto L29;
    L60:
    if (this.typed.isEmpty() !=0) goto L95;
    if (ui.Lang.str(v4.labelKey(), new Object[0]).toLowerCase(Locale.ROOT).contains(v2) ==0) goto L104;
    L95:
    v1.add(v4);
    L104:
    goto L29;
    L107:
    return v1;
}

private boolean matchingLocked() {
    v1 = ?;
    v2 = this.typed.toLowerCase(Locale.ROOT);
    v3 = this.tiles.iterator();
    L29:
    if (v3.hasNext() ==0) goto L107;
    v4 = ((ui.panel.OverviewPanel$Tile)v3.next());
    if (v4.locked() !=0) goto L60;
    goto L29;
    L60:
    if (this.typed.isEmpty() !=0) goto L95;
    if (ui.Lang.str(v4.labelKey(), new Object[0]).toLowerCase(Locale.ROOT).contains(v2) ==0) goto L104;
    L95:
    v1.add(v4);
    L104:
    goto L29;
    L107:
    return v1;
}

private void layout() {
    v1 = this.matchingOpen();
    v2 = List.of();
    v3 = this.matchingLocked();
    v4 = this.listTop();
    v5 = v4;
    v4 = (v4 + (this.rowsFor(v1.size()) * 40));
    v6 = v4;
    if (v2.isEmpty() !=0) goto L80;
    v4 = v4 + 15;
    v6 = v4;
    v4 = (v4 + (this.rowsFor(v2.size()) * 40));
    L80:
    v7 = v4;
    if (v3.isEmpty() !=0) goto L118;
    v4 = v4 + 15;
    v7 = v4;
    v4 = (v4 + (this.rowsFor(v3.size()) * 40));
    L118:
    return ?;
}

private int maxScroll(ui.panel.OverviewPanel$Layout layout) {
    return Math.max(0, (layout.contentBottom() - (this.y + this.height)));
}

private Object firstHit() {
    v1 = this.matchingOpen();
    if (v1.isEmpty() ==0) goto L18;
    goto L28;
    L18:
    L28:
    return ((ui.panel.OverviewPanel$Tile)v1.get(0));
}

private boolean overSearch(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.x)) <0) goto L52;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L52;
    if (cmp(v3,((d)this.y)) <0) goto L52;
    if (cmp(v3,((d)(this.y + 17))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

public boolean isCapturingKeys() {
    if (this.searching !=0) goto L17;
    if (this.typed.isEmpty() !=0) goto L21;
    L17:
    goto L22;
    L21:
    L22:
    return 0;
}

public String charTyped(client.input.CharacterEvent input) {
    if (this.searching !=0) goto L9;
    return 0;
    L9:
    if (input.codepoint() < 32) goto L26;
    goto L27;
    L26:
    L27:
    v2 = 0;
    if (v2 ==0) goto L44;
    if (this.typed.length() < 20) goto L46;
    L44:
    return 0;
    L46:
    this.typed = this.typed + v2;
    return 1;
}

public void keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.searching !=0) goto L19;
    if (this.typed.isEmpty() ==0) goto L19;
    return 0;
    L19:
    if (keyCode != 42) goto L58;
    if (this.typed.isEmpty() !=0) goto L58;
    this.typed = this.typed.substring(0, (this.typed.length() - 1));
    return 1;
    L58:
    if (keyCode != 41) goto L94;
    if (this.searching !=0) goto L81;
    if (this.typed.isEmpty() !=0) goto L94;
    L81:
    this.typed = "";
    this.searching = 0;
    return 1;
    L94:
    if (keyCode == 40) goto L106;
    if (keyCode != 88) goto L144;
    L106:
    v4 = this.firstHit();
    if (v4 == null) goto L144;
    this.typed = "";
    this.searching = 0;
    this.open.accept(v4.category());
    return 1;
    L144:
    return 0;
}

private int columns() {
    if (this.width < 190) goto L14;
    goto L15;
    L14:
    L15:
    return 1;
}

private int tileW() {
    if (this.scrollbarShown ==0) goto L16;
    goto L17;
    L16:
    L17:
    v1 = (6 - 0);
    return ((v1 - ((this.columns() - 1) * 6)) / this.columns());
}

private int listTop() {
    return ((this.y + 17) + 6);
}

private int rowsFor(int count) {
    return (((count + this.columns()) - 1) / this.columns());
}

private int tileX(int index) {
    return (this.x + ((index % this.columns()) * (this.tileW() + 6)));
}

private int rowY(int top, int index) {
    return (top + ((index / this.columns()) * 40));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = this.layout();
    v7 = this.maxScroll(v6);
    this.scroll = Math.max(0, Math.min(v7, this.scroll));
    if (v7 <=0) goto L41;
    goto L42;
    L41:
    L42:
    1.scrollbarShown = 0;
    this.renderSearch(ctx, font, v6.hits(), mouseX, mouseY);
    if (v6.hits() !=0) goto L115;
    v8 = ui.Lang.of("fwm.overview.noHit", new Object[0]);
    ctx.text(font, v8, (this.x + ((this.width - font.width(v8)) / 2)), (this.listTop() + 12), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L115:
    ctx.enableScissor(this.x, this.listTop(), (this.x + this.width), (this.y + this.height));
    this.renderTiles(ctx, font, v6.switchable(), (v6.switchableTop() - this.scroll), mouseX, mouseY);
    if (v6.alwaysOn().isEmpty() !=0) goto L220;
    v8 = (v6.alwaysTop() - this.scroll);
    this.renderHeading(ctx, font, "fwm.overview.otherFeatures", v8);
    this.renderTiles(ctx, font, v6.alwaysOn(), v8, mouseX, mouseY);
    L220:
    if (v6.locked().isEmpty() !=0) goto L271;
    v8 = (v6.lockedTop() - this.scroll);
    this.renderHeading(ctx, font, "fwm.tab.comingSoon", v8);
    this.renderTiles(ctx, font, v6.locked(), v8, mouseX, mouseY);
    L271:
    ctx.disableScissor();
    if (this.scrollbarShown ==0) goto L289;
    this.renderScrollbar(ctx, v7);
    L289:
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx, int maxScroll) {
    v3 = this.listTop();
    v4 = ((this.y + this.height) - v3);
    v5 = ((this.x + this.width) - 3);
    v6 = Math.max(10, ((v4 * v4) / (v4 + maxScroll)));
    v7 = (v3 + (((v4 - v6) * this.scroll) / maxScroll));
    ctx.fill(v5, v3, (v5 + 3), (v3 + v4), ui.FwmTheme.OFF);
    ctx.fill(v5, v7, (v5 + 3), (v7 + v6), ui.FwmTheme.ACCENT);
    return;
}

private void renderHeading(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String key, int top) {
    v5 = ((top - 15) + 2);
    v6 = ui.Lang.of(key, new Object[0]);
    ctx.text(font, v6, (this.x + 2), v5, ui.FwmTheme.TEXT_DIM, 0);
    ui.FwmTheme.divider(ctx, ((this.x + font.width(v6)) + 6), (v5 + 3), Math.max(0, ((this.width - font.width(v6)) - 8)));
    return;
}

private void renderTiles(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, List tiles, int top, int mouseX, int mouseY) {
    v7 = 0;
    L3:
    if (v7 >= tiles.size()) goto L55;
    this.renderTile(ctx, font, ((ui.panel.OverviewPanel$Tile)tiles.get(v7)), this.tileX(v7), this.rowY(top, v7), mouseX, mouseY);
    v7 = v7 + 1;
    goto L3;
    L55:
    return;
}

private void renderSearch(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int hits, int mouseX, int mouseY) {
    if (this.searching !=0) goto L17;
    if (this.typed.isEmpty() !=0) goto L21;
    L17:
    goto L22;
    L21:
    L22:
    v6 = 0;
    if (v6 ==0) goto L53;
    goto L56;
    L53:
    L56:
    ui.FwmTheme.box(this.x, this.y, this.width, 17, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    Objects.requireNonNull(font);
    v7 = ((this.y + ((17 - 9) / 2)) + 1);
    if (v6 ==0) goto L105;
    goto L108;
    L105:
    L108:
    ctx.pencil((this.x + 5), (this.y + 5), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    if (v6 ==0) goto L155;
    if (cmp(((System.currentTimeMillis() / 500) % 2),0) !=0) goto L142;
    goto L144;
    L142:
    L144:
    goto L165;
    L155:
    L165:
    v8 = ui.Lang.of("fwm.overview.search", new Object[0]);
    if (v6 ==0) goto L191;
    goto L194;
    L191:
    L194:
    font.text(v8, (this.x + 16), v7, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    if (this.typed.isEmpty() ==0) goto L209;
    return;
    L209:
    new Object[1][0] = Integer.valueOf(hits);
    v9 = ui.Lang.of("fwm.overview.hits", new Object[1]);
    ctx.text(font, v9, (((this.x + this.width) - font.width(v9)) - 5), v7, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private void pencil(client.gui.GuiGraphicsExtractor ctx, int px, int py, int color) {
    v5 = 0;
    L3:
    if (v5 >= 5) goto L44;
    ctx.fill(((px + 5) - v5), (py + v5), ((px + 7) - v5), ((py + v5) + 1), color);
    v5 = v5 + 1;
    goto L3;
    L44:
    ctx.fill(px, (py + 5), (px + 2), (py + 7), color);
    ctx.fill((px + 2), (py + 4), (px + 4), (py + 6), color);
    return;
}

private void renderTile(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, ui.panel.OverviewPanel$Tile tile, int tx, int ty, int mouseX, int mouseY) {
    if ((ty + 34) <= this.listTop()) goto L26;
    if (ty < (this.y + this.height)) goto L27;
    L26:
    return;
    L27:
    if (tile.locked() !=0) goto L74;
    if (mouseX < tx) goto L74;
    if (mouseX >= (tx + this.tileW())) goto L74;
    if (mouseY < ty) goto L74;
    if (mouseY >= (ty + 34)) goto L74;
    goto L75;
    L74:
    L75:
    v8 = 0;
    if (tile.locked() !=0) goto L94;
    if (config.Features.isOn(tile.labelKey()) ==0) goto L98;
    L94:
    goto L99;
    L98:
    L99:
    v9 = 0;
    ui.FwmTheme.tile(ctx, tx, ty, this.tileW(), 34, tile.locked(), v8, v9);
    if (tile.locked() ==0) goto L136;
    goto L150;
    L136:
    if (v9 ==0) goto L147;
    goto L150;
    L147:
    L150:
    v10 = ui.FwmTheme.DISABLED_TEXT;
    ui.FwmIcons.draw(ctx, tile.labelKey(), (tx + 7), (ty + 9), v10);
    v11 = (((tx + 7) + 16) + 7);
    v12 = (((tx + this.tileW()) - 6) - v11);
    if (tile.locked() ==0) goto L212;
    goto L217;
    L212:
    L217:
    v13 = this.subtitle(tile);
    if (v13.isEmpty() ==0) goto L247;
    Objects.requireNonNull(font);
    goto L252;
    L247:
    L252:
    v14 = (ty + 8);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(this.title(tile), v12)), v11, v14, v10, 0);
    if (v13.isEmpty() ==0) goto L289;
    return;
    L289:
    if (font.width(v13) <= v12) goto L336;
    v15 = tile.labelKey() + '.sub.short';
    v16 = ui.Lang.str(v15, new Object[0]);
    if (v16.equals(v15) !=0) goto L336;
    v13 = v16;
    L336:
    if (v9 ==0) goto L367;
    goto L370;
    L367:
    L370:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(v13, v12)), v11, (ty + 20), ui.FwmTheme.TEXT_DIM, ui.FwmTheme.DISABLED_TEXT, 0);
    return;
}

private String title(ui.panel.OverviewPanel$Tile tile) {
    v2 = tile.labelKey() + '.tile';
    v3 = ui.Lang.str(v2, new Object[0]);
    if (v3.equals(v2) ==0) goto L41;
    goto L42;
    L41:
    L42:
    return v3;
}

private boolean subtitle(ui.panel.OverviewPanel$Tile tile) {
    if (config.Features.isOn(tile.labelKey()) !=0) goto L21;
    return ui.Lang.str("fwm.overview.off", new Object[0]);
    L21:
    v2 = tile.labelKey() + '.sub';
    v3 = ui.Lang.str(v2, new Object[0]);
    if (v3.equals(v2) ==0) goto L53;
    goto L54;
    L53:
    L54:
    return v3;
}

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 ==0) goto L13;
    if (v5 == 1) goto L13;
    return 0;
    L13:
    if (v5 !=0) goto L34;
    if (this.overSearch(mouseX, mouseY) ==0) goto L34;
    this.searching = 1;
    return 1;
    L34:
    if (v5 !=0) goto L44;
    this.searching = 0;
    L44:
    v6 = this.layout();
    if (this.clickTiles(v6.switchable(), (v6.switchableTop() - this.scroll), mouseX, mouseY, v5) ==0) goto L78;
    return 1;
    L78:
    return this.clickTiles(v6.alwaysOn(), (v6.alwaysTop() - this.scroll), mouseX, mouseY, v5);
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    v7 = this.maxScroll(this.layout());
    if (v7 >0) goto L17;
    return 0;
    L17:
    this.scroll = Math.max(0, Math.min(v7, (this.scroll - (((i)Math.signum(v5)) * 40))));
    return 1;
}

private boolean clickTiles(List tiles, int top, double mouseX, double a4, int mouseY) {
    v8 = 0;
    L3:
    if (v8 >= tiles.size()) goto L58;
    if (this.hits(((ui.panel.OverviewPanel$Tile)tiles.get(v8)), this.tileX(v8), this.rowY(top, v8), mouseX, mouseY, v7) ==0) goto L52;
    return 1;
    L52:
    v8 = v8 + 1;
    goto L3;
    L58:
    return 0;
}

private void hits(ui.panel.OverviewPanel$Tile tile, int tx, int ty, double mouseX, double a5, int mouseY) {
    if (cmp(mouseX,((d)tx)) <0) goto L40;
    if (cmp(mouseX,((d)(tx + this.tileW()))) >=0) goto L40;
    if (cmp(mouseY,((d)ty)) <0) goto L40;
    if (cmp(mouseY,((d)(ty + 34))) <0) goto L42;
    L40:
    return 0;
    L42:
    if (v8 != 1) goto L69;
    if (config.Features.canSwitch(tile.labelKey()) !=0) goto L60;
    return 0;
    L60:
    config.Features.toggle(tile.labelKey());
    return 1;
    L69:
    this.typed = "";
    this.searching = 0;
    this.open.accept(tile.category());
    return 1;
}

