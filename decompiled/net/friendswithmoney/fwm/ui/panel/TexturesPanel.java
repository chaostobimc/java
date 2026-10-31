// class net/friendswithmoney/fwm/ui/panel/TexturesPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LEFT_W I;
field ROW_H I;
field ITEM_H I;
field BTN_H I;
field COLS I;
field GAP I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field hits Ljava/util/List;;
field packs Ljava/util/List;;
field search Lnet/minecraft/client/gui/components/EditBox;;
field themeName Lnet/minecraft/client/gui/components/EditBox;;
field themesTab Z;
field onlyFavourites Z;
field category Ljava/lang/String;;
field listScroll I;
field gridScroll I;
field themeScroll I;
field renaming Lnet/friendswithmoney/fwm/config/FwmConfig$TextureTheme;;
public List <init>(ui.panel.PanelHost host) {
    this.hits = ?;
    this.packs = List.of();
    this.category = "crystal";
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.textures", new Object[0]);
}

protected void onResize() {
    this.packs = textures.TextureMixer.packs();
    v1 = this.packs.iterator();
    L17:
    if (v1.hasNext() ==0) goto L44;
    v2 = ((String)v1.next());
    textures.TextureMixer.content(v2);
    goto L17;
    L44:
    v1 = client.Minecraft.getInstance().font;
    if (this.search != null) goto L63;
    goto L70;
    L63:
    L70:
    v2 = this.search.getValue();
    if (this.themeName != null) goto L83;
    goto L90;
    L83:
    L90:
    v3 = this.themeName.getValue();
    if (this.search == null) goto L111;
    this.host.detach(this.search);
    L111:
    if (this.themeName == null) goto L131;
    this.host.detach(this.themeName);
    L131:
    this.search = this.field(v1, (this.midX() + 4), (this.y + 26), (this.midW() - 110), 40, ui.Lang.t("Suchen \u2026", "Search \u2026"));
    this.search.setValue(v2);
    this.themeName = this.field(v1, (this.midX() + 4), (this.y + 26), (this.midW() - 70), 40, ui.Lang.t("Name des Themes", "Theme name"));
    this.themeName.setValue(v3);
    this.showField();
    return;
}

private void field(client.gui.Font font, int fx, int fy, int w, int max, String hint) {
    v7 = ?;
    v7.setBordered(0);
    v7.setMaxLength(max);
    v7.setTextColor(ui.FwmTheme.TEXT);
    v7.setHint(network.chat.Component.literal(hint));
    return v7;
}

private void showField() {
    this.host.detach(this.search);
    this.host.detach(this.themeName);
    if (this.themesTab ==0) goto L44;
    goto L48;
    L44:
    L48:
    this.themeName.attach(this.search);
    return;
}

private int midX() {
    return ((this.x + 122) + 6);
}

private int midW() {
    return ((this.width - 122) - 6);
}

private int footY() {
    return ((this.y + this.height) - 14);
}

private int gridY() {
    return (this.y + 42);
}

private int tileW() {
    return Math.max(20, ((this.midW() - 12) / 4));
}

private int tileH() {
    return (Math.round((((f)this.tileW()) * 0.7799999713897705)) + 12);
}

private boolean categories() {
    v1 = 0;
    v2 = this.packs.iterator();
    L12:
    if (v2.hasNext() ==0) goto L43;
    v3 = ((String)v2.next());
    if (textures.TextureMixer.content(v3) != null) goto L40;
    v1 = 1;
    L40:
    goto L12;
    L43:
    v2 = ?;
    v3 = textures.TextureMixer.CATEGORIES.iterator();
    L60:
    if (v3.hasNext() ==0) goto L125;
    v4 = ((textures.TextureMixer$Category)v3.next());
    if (textures.TextureMixer.offered(v4.id()) !=0) goto L94;
    goto L60;
    L94:
    if (v1 !=0) goto L113;
    if (textures.TextureMixer.packsWith(this.packs, v4.id()) <=0) goto L122;
    L113:
    v2.add(v4);
    L122:
    goto L60;
    L125:
    return v2;
}

private boolean styles() {
    if (this.search != null) goto L12;
    goto L28;
    L12:
    L28:
    v1 = this.search.getValue().trim().toLowerCase(Locale.ROOT);
    v2 = ?;
    if (this.onlyFavourites !=0) goto L60;
    if (v1.isEmpty() ==0) goto L60;
    v2.add("");
    L60:
    v3 = this.packs.iterator();
    L70:
    if (v3.hasNext() ==0) goto L177;
    v4 = ((String)v3.next());
    v5 = textures.TextureMixer.content(v4);
    if (v5 == null) goto L70;
    if (v5.containsKey(this.category) !=0) goto L119;
    goto L70;
    L119:
    if (this.onlyFavourites ==0) goto L137;
    if (textures.TextureMixer.favourite(v4) !=0) goto L137;
    goto L70;
    L137:
    if (v1.isEmpty() !=0) goto L165;
    if (ui.panel.TexturesPanel.strip(v4).toLowerCase(Locale.ROOT).contains(v1) !=0) goto L165;
    goto L70;
    L165:
    v2.add(v4);
    goto L70;
    L177:
    v3 = ?;
    if (v2.isEmpty() !=0) goto L219;
    if (((String)v2.get(0)).isEmpty() ==0) goto L219;
    v3.add("");
    L219:
    v4 = v2.iterator();
    L227:
    if (v4.hasNext() ==0) goto L277;
    v5 = ((String)v4.next());
    if (v5.isEmpty() !=0) goto L274;
    if (textures.TextureMixer.favourite(v5) ==0) goto L274;
    v3.add(v5);
    L274:
    goto L227;
    L277:
    v4 = v2.iterator();
    L285:
    if (v4.hasNext() ==0) goto L335;
    v5 = ((String)v4.next());
    if (v5.isEmpty() !=0) goto L332;
    if (textures.TextureMixer.favourite(v5) !=0) goto L332;
    v3.add(v5);
    L332:
    goto L285;
    L335:
    return v3;
}

private static String strip(String pack) {
    if (pack.toLowerCase(Locale.ROOT).endsWith(".zip") ==0) goto L29;
    goto L30;
    L29:
    L30:
    v1 = pack;
    return v1.replaceAll("\u00a7.", "");
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.hits.clear();
    textures.TextureMixer.previewBudget(3);
    v6 = 60;
    if (this.themesTab !=0) goto L50;
    goto L51;
    L50:
    L51:
    ctx.tab(font, this.x, this.y, v6, ui.Lang.t("Kategorien", "Categories"), 1, 0, mouseX, mouseY, /*indy*/run(this));
    this.tab(ctx, font, ((this.x + v6) + 2), this.y, ((122 - v6) - 2), "Themes", this.themesTab, mouseX, mouseY, /*indy*/run(this));
    ctx.fill(((this.x + 122) + 2), this.y, ((this.x + 122) + 3), (this.footY() - 4), ui.FwmTheme.OFF);
    if (this.themesTab ==0) goto L159;
    this.themes(ctx, font, mouseX, mouseY);
    goto L177;
    L159:
    this.categoryList(ctx, font, mouseX, mouseY);
    this.grid(ctx, font, mouseX, mouseY);
    L177:
    this.footer(ctx, font, mouseX, mouseY);
    return;
}

private void setTab(boolean themes) {
    this.themesTab = themes;
    this.showField();
    return;
}

private boolean tab(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int bw, String label, boolean on, int mouseX, int mouseY, Runnable action) {
    v11 = ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), bx, by, bw, 14);
    if (on ==0) goto L37;
    goto L38;
    L37:
    L38:
    if (on !=0) goto L48;
    if (v11 ==0) goto L54;
    L48:
    goto L57;
    L54:
    L57:
    ui.FwmTheme.box(by, bw, 14, ui.FwmTheme.RAISED, 0, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v12 = network.chat.Component.literal(font.plainSubstrByWidth(label, (bw - 4)));
    if (on ==0) goto L107;
    goto L110;
    L107:
    L110:
    font.text(v12, (bx + ((bw - font.width(v12)) / 2)), (by + 4), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    this.hits.add(?);
    return;
}

private boolean categoryList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.categories();
    if (v5.isEmpty() !=0) goto L60;
    if (v5.stream().noneMatch(/*indy*/test(this)) ==0) goto L60;
    this.category = ((textures.TextureMixer$Category)v5.get(0)).id();
    this.gridScroll = 0;
    L60:
    v6 = ?;
    v7 = null;
    v8 = v5.iterator();
    L81:
    if (v8.hasNext() ==0) goto L202;
    v9 = ((textures.TextureMixer$Category)v8.next());
    if (v9.group().equals(v7) !=0) goto L189;
    v7 = v9.group();
    v10 = textures.TextureMixer.GROUPS.iterator();
    L133:
    if (v10.hasNext() ==0) goto L189;
    v11 = (([Ljava.lang.String;)v10.next());
    if (v11[0].equals(v7) ==0) goto L186;
    v6.add(ui.Lang.t(v11[1], v11[2]));
    L186:
    goto L133;
    L189:
    v6.add(v9);
    goto L81;
    L202:
    v8 = ((this.y + 14) + 4);
    v9 = (this.footY() - 6);
    v10 = 0;
    v11 = 0;
    v12 = (v6.size() - 1);
    L239:
    if (v12 <0) goto L293;
    if ((v6.get(v12) instanceof String) ==0) goto L266;
    goto L268;
    L266:
    L268:
    v11 = (11 + 20);
    if (v11 <= (v9 - v8)) goto L284;
    goto L293;
    L284:
    v10 = v10 + 1;
    v12 = v12 + 255;
    goto L239;
    L293:
    this.listScroll = Math.max(0, Math.min(this.listScroll, (v6.size() - v10)));
    v11 = v8;
    v12 = this.listScroll;
    L328:
    if (v12 >= v6.size()) goto L873;
    v13 = v6.get(v12);
    if ((v13 instanceof String) ==0) goto L415;
    v14 = ((String)v13);
    if ((v11 + 11) <= v9) goto L379;
    goto L873;
    L379:
    ctx.text(font, network.chat.Component.literal(v14.toUpperCase(Locale.ROOT)), (this.x + 2), (v11 + 2), ui.FwmTheme.TEXT_DIM, 0);
    v11 = v11 + 11;
    goto L867;
    L415:
    if ((v11 + 20) <= v9) goto L428;
    goto L873;
    L428:
    v14 = ((textures.TextureMixer$Category)v13);
    v15 = v14.id().equals(this.category);
    v16 = ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), this.x, v11, 122, 20);
    if (v15 ==0) goto L526;
    ctx.fill(this.x, v11, (this.x + 122), (v11 + 20), 872415231);
    ctx.fill(this.x, v11, (this.x + 2), (v11 + 20), ui.FwmTheme.ACCENT);
    goto L545;
    L526:
    if (v16 ==0) goto L545;
    ui.FwmTheme.hover(ctx, this.x, v11, 122, 20);
    L545:
    v17 = textures.TextureMixer.chosen(v14.id());
    ui.FwmTheme.box(ctx, (this.x + 4), (v11 + 2), 16, 16, ui.FwmTheme.INSET, ui.FwmTheme.EDGE);
    if (v17.isEmpty() ==0) goto L595;
    goto L602;
    L595:
    L602:
    v18 = textures.TextureMixer.preview(v17, v14);
    if (v18 == null) goto L632;
    ui.panel.TexturesPanel.picture(ctx, v18, v14.tiled(), (this.x + 4), (v11 + 2), 16);
    L632:
    v19 = String.valueOf(textures.TextureMixer.packsWith(this.packs, v14.id()));
    v20 = font.width(v19);
    if (v15 ==0) goto L692;
    goto L695;
    L692:
    L695:
    font.text(network.chat.Component.literal(v19), (((this.x + 122) - 3) - v20), (v11 + 6), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    v21 = ((98 - v20) - 6);
    if (v15 ==0) goto L747;
    goto L750;
    L747:
    L750:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(v14.label(), v21)), (this.x + 24), (v11 + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    if (v17.isEmpty() ==0) goto L768;
    goto L773;
    L768:
    L773:
    v22 = ui.panel.TexturesPanel.strip(v17);
    if (v17.isEmpty() ==0) goto L814;
    goto L817;
    L814:
    L817:
    font.text(network.chat.Component.literal(font.plainSubstrByWidth(v22, v21)), (this.x + 24), (v11 + 11), ui.FwmTheme.TEXT_DIM, -8398438, 0);
    v23 = v14.id();
    this.hits.add(?);
    v11 = v11 + 20;
    L867:
    v12 = v12 + 1;
    goto L328;
    L873:
    return;
}

private void grid(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = textures.TextureMixer.category(this.category);
    v6 = this.styles();
    v7 = textures.TextureMixer.chosen(this.category);
    ctx.text(font, network.chat.Component.literal(v5.label()), this.midX(), (this.y + 2), ui.FwmTheme.TEXT, 0);
    if (v7.isEmpty() ==0) goto L76;
    goto L81;
    L76:
    L81:
    v8 = "Vanilla" + ' Styles · ' + ui.panel.TexturesPanel.strip(v7);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v8, this.midW())), this.midX(), (this.y + 13), ui.FwmTheme.TEXT_DIM, 0);
    v9 = (this.midW() - 106);
    if (this.search.isFocused() ==0) goto L165;
    goto L168;
    L165:
    L168:
    ui.FwmTheme.box(this.midX(), (this.y + 23), v9, 15, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, 0);
    v10 = 0;
    v11 = this.packs.iterator();
    L197:
    if (v11.hasNext() ==0) goto L233;
    v12 = ((String)v11.next());
    if (textures.TextureMixer.favourite(v12) ==0) goto L230;
    v10 = v10 + 1;
    L230:
    goto L197;
    L233:
    v11 = ((this.midX() + v9) + 4);
    if (this.onlyFavourites !=0) goto L278;
    goto L279;
    L278:
    L279:
    ctx.tab(font, v11, (this.y + 23), 34, ui.Lang.t("Alle", "All"), 1, 0, mouseX, mouseY, /*indy*/run(this));
    this.tab(ctx, font, (v11 + 36), (this.y + 23), 66, '★ ' + ui.Lang.t("Favoriten", "Favs") + ' (' + v10 + ')', this.onlyFavourites, mouseX, mouseY, /*indy*/run(this));
    v12 = this.tileW();
    v13 = this.tileH();
    v14 = Math.max(1, (((this.footY() - 18) - this.gridY()) / (v13 + 4)));
    v15 = (((v6.size() + 4) - 1) / 4);
    this.gridScroll = Math.max(0, Math.min(this.gridScroll, (v15 - v14)));
    v16 = (this.gridScroll * 4);
    L418:
    if (v16 >= v6.size()) goto L1073;
    if (v16 >= ((this.gridScroll + v14) * 4)) goto L1073;
    v17 = ((String)v6.get(v16));
    v18 = (v16 - (this.gridScroll * 4));
    v19 = (this.midX() + ((v18 % 4) * (v12 + 4)));
    v20 = (this.gridY() + ((v18 / 4) * (v13 + 4)));
    v21 = v17.equals(v7);
    v22 = ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), v19, v20, v12, v13);
    v23 = (v13 - 12);
    ui.LauncherGfx.roundGradient(ctx, v19, v20, v12, v23, 4, -14208438, -15723749);
    ctx.fill((v19 + 2), (v20 + ((v23 * 2) / 3)), ((v19 + v12) - 2), ((v20 + ((v23 * 2) / 3)) + 1), 352321535);
    if (v21 ==0) goto L624;
    ui.LauncherGfx.ring(ctx, (v19 - 1), (v20 - 1), (v12 + 2), (v23 + 2), 5, ui.FwmTheme.ACCENT);
    goto L653;
    L624:
    if (v22 ==0) goto L653;
    ui.LauncherGfx.ring(ctx, (v19 - 1), (v20 - 1), (v12 + 2), (v23 + 2), 5, 1728053247);
    L653:
    if (v17.isEmpty() ==0) goto L669;
    goto L676;
    L669:
    L676:
    v24 = textures.TextureMixer.preview(v17, v5);
    if (v24 == null) goto L710;
    ui.panel.TexturesPanel.fit(ctx, v24, (v19 + 3), (v20 + 3), (v12 - 6), (v23 - 6));
    goto L772;
    L710:
    if (v5.id().equals("sounds") ==0) goto L730;
    goto L733;
    L730:
    L733:
    v25 = "?";
    ctx.text(font, network.chat.Component.literal(v25), (v19 + ((v12 - font.width(v25)) / 2)), ((v20 + (v23 / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    L772:
    if (v17.isEmpty() ==0) goto L786;
    goto L791;
    L786:
    L791:
    v25 = ui.panel.TexturesPanel.strip(v17);
    v26 = network.chat.Component.literal(font.plainSubstrByWidth(v25, (v12 - 4)));
    if (v21 ==0) goto L845;
    goto L848;
    L845:
    L848:
    font.text(v26, (v19 + ((v12 - font.width(v26)) / 2)), ((v20 + v13) - 9), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    if (v21 ==0) goto L882;
    ctx.fill((v19 + 3), (v20 + 3), (v19 + 6), (v20 + 6), ui.FwmTheme.ACCENT);
    L882:
    if (v17.isEmpty() !=0) goto L1026;
    v27 = textures.TextureMixer.favourite(v17);
    v28 = ((v19 + v12) - 10);
    v29 = ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), (v28 - 1), v20, 11, 11);
    if (v27 !=0) goto L937;
    if (v22 ==0) goto L991;
    L937:
    if (v27 ==0) goto L950;
    goto L953;
    L950:
    L953:
    if (v27 ==0) goto L973;
    goto L987;
    L973:
    if (v29 ==0) goto L984;
    goto L987;
    L984:
    L987:
    network.chat.Component.literal("\u2606").text(v28, (v20 + 2), -865972, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    L991:
    this.hits.add(?);
    L1026:
    v27 = this.category;
    this.hits.add(?);
    v16 = v16 + 1;
    goto L418;
    L1073:
    if (v6.isEmpty() ==0) goto L1152;
    if (this.onlyFavourites ==0) goto L1102;
    goto L1111;
    L1102:
    L1111:
    v16 = ui.Lang.t("Kein Pack passt.", "No pack fits.");
    ctx.text(font, network.chat.Component.literal(v16), (this.midX() + ((this.midW() - font.width(v16)) / 2)), (this.gridY() + 30), ui.FwmTheme.TEXT_DIM, 0);
    L1152:
    return;
}

private static void fit(client.gui.GuiGraphicsExtractor ctx, textures.TextureMixer$Preview p, int px, int py, int bw, int bh) {
    v6 = p.width();
    v7 = p.frameHeight();
    v8 = Math.min((((f)bw) / ((f)v6)), (((f)bh) / ((f)v7)));
    if (cmp(v8,2) <0) goto L47;
    v8 = ((f)Math.floor(((d)v8)));
    L47:
    v9 = Math.max(1, Math.round((((f)v6) * v8)));
    v10 = Math.max(1, Math.round((((f)v7) * v8)));
    ui.FwmGfx.blit(ctx, p.id(), (px + ((bw - v9) / 2)), (py + ((bh - v10) / 2)), v9, v10, v6, v7, v6, p.height());
    return;
}

private static void picture(client.gui.GuiGraphicsExtractor ctx, textures.TextureMixer$Preview p, boolean tiled, int px, int py, int size) {
    if (tiled ==0) goto L104;
    v6 = (size / 3);
    v7 = 0;
    L13:
    if (v7 >= 3) goto L103;
    v8 = 0;
    L22:
    if (v8 >= 3) goto L97;
    ui.FwmGfx.blit(ctx, p.id(), ((px + (v8 * v6)) + ((size - (3 * v6)) / 2)), ((py + (v7 * v6)) + ((size - (3 * v6)) / 2)), v6, v6, p.width(), p.frameHeight(), p.width(), p.height());
    v8 = v8 + 1;
    goto L22;
    L97:
    v7 = v7 + 1;
    goto L13;
    L103:
    return;
    L104:
    v6 = p.width();
    v7 = p.frameHeight();
    v8 = Math.min((((f)size) / ((f)v6)), (((f)size) / ((f)v7)));
    if (cmp(v8,1) <0) goto L151;
    v8 = ((f)Math.floor(((d)v8)));
    L151:
    v9 = Math.max(1, Math.round((((f)v6) * v8)));
    v10 = Math.max(1, Math.round((((f)v7) * v8)));
    ui.FwmGfx.blit(ctx, p.id(), (px + ((size - v9) / 2)), (py + ((size - v10) / 2)), v9, v10, v6, v7, v6, p.height());
    return;
}

private void themes(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = ((this.y + 14) + 6);
    v6 = font.split(network.chat.Component.literal(ui.Lang.t("Ein Theme merkt sich die ganze Auswahl. Anwenden setzt sie, \u201eMix bauen\u201c macht sie aktiv.", "A theme keeps the whole choice. Apply sets it, \"Build\" makes it real.")), 118).stream().map(/*indy*/apply()).toList().iterator();
    L57:
    if (v6.hasNext() ==0) goto L107;
    v7 = ((String)v6.next());
    ctx.text(font, network.chat.Component.literal(v7), (this.x + 2), v5, ui.FwmTheme.TEXT_DIM, 0);
    v5 = v5 + 10;
    goto L57;
    L107:
    if (this.renaming != null) goto L128;
    goto L149;
    L128:
    L149:
    font.text(ui.Lang.t("Aktuelle Auswahl speichern", "Save current choice"), network.chat.Component.literal(ui.Lang.t("Umbenennen: ", "Rename: ") + this.renaming.name), this.midX(), (this.y + 12), ui.FwmTheme.TEXT_DIM, 0);
    v6 = (this.midW() - 66);
    if (this.themeName.isFocused() ==0) goto L214;
    goto L217;
    L214:
    L217:
    ui.FwmTheme.box(this.midX(), (this.y + 23), v6, 15, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.EDGE);
    this.themeName.extractWidgetRenderState(ctx, mouseX, mouseY, 0);
    if (this.renaming != null) goto L272;
    goto L275;
    L272:
    L275:
    ctx.button(font, ((this.midX() + v6) + 4), (this.y + 23), 62, ui.Lang.t("Speichern", "Save"), "OK", mouseX, mouseY, /*indy*/run(this));
    v7 = config.FwmConfig.INSTANCE.textureThemes;
    v8 = this.gridY();
    v9 = 16;
    v10 = Math.max(1, (((this.footY() - 6) - v8) / v9));
    v11 = (v7.size() + 1);
    this.themeScroll = Math.max(0, Math.min(this.themeScroll, (v11 - v10)));
    v12 = 0;
    L358:
    if (v12 >= v10) goto L720;
    if ((this.themeScroll + v12) >= v11) goto L720;
    v13 = (this.themeScroll + v12);
    v14 = (v8 + (v12 * v9));
    if (v13 !=0) goto L405;
    goto L419;
    L405:
    L419:
    v15 = ((config.FwmConfig$TextureTheme)v7.get((v13 - 1)));
    if (ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), this.midX(), v14, this.midW(), v9) ==0) goto L460;
    ui.FwmTheme.hover(ctx, this.midX(), v14, this.midW(), v9);
    L460:
    if (v15 != null) goto L477;
    goto L482;
    L477:
    L482:
    v16 = v15.name;
    if (v15 != null) goto L501;
    goto L525;
    L501:
    L525:
    v17 = v15.mix.size() + ui.Lang.t(" Kategorien", " categories");
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v16, (this.midW() - 200))), (this.midX() + 3), (v14 + 4), ui.FwmTheme.TEXT, 0);
    ctx.text(font, network.chat.Component.literal(v17), ((this.midX() + this.midW()) - 190), (v14 + 4), ui.FwmTheme.TEXT_DIM, 0);
    v18 = ((this.midX() + this.midW()) - 120);
    this.button(ctx, font, v18, (v14 + 1), 58, ui.Lang.t("Anwenden", "Apply"), mouseX, mouseY, /*indy*/run(v15));
    if (v15 == null) goto L714;
    this.button(ctx, font, (v18 + 60), (v14 + 1), 44, ui.Lang.t("Name", "Name"), mouseX, mouseY, /*indy*/run(this, v15));
    this.button(ctx, font, (v18 + 106), (v14 + 1), 14, "\u00d7", mouseX, mouseY, /*indy*/run(this, v15));
    L714:
    v12 = v12 + 1;
    goto L358;
    L720:
    return;
}

private void confirm(config.FwmConfig$TextureTheme theme) {
    config.FwmConfig.INSTANCE.textureThemes.remove(theme);
    if (this.renaming != theme) goto L26;
    this.renaming = null;
    L26:
    config.ConfigManager.save();
    return;
}

private void saveTheme() {
    v1 = this.themeName.getValue().trim();
    if (v1.isEmpty() ==0) goto L19;
    return;
    L19:
    if (this.renaming == null) goto L64;
    config.FwmConfig.INSTANCE.textureThemes.removeIf(/*indy*/test(this, v1));
    this.renaming.name = v1;
    this.renaming = null;
    config.ConfigManager.save();
    goto L68;
    L64:
    textures.TextureMixer.saveTheme(v1);
    L68:
    this.themeName.setValue("");
    this.themeName.setFocused(0);
    return;
}

private void footer(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = (this.footY() - 11);
    if (textures.TextureMixer.building() ==0) goto L97;
    v6 = (this.width - 6);
    ctx.fill((this.x + 3), (v5 + 2), ((this.x + 3) + v6), (v5 + 7), 1442840575);
    ctx.fill((this.x + 3), (v5 + 2), ((this.x + 3) + Math.round((((f)v6) * textures.TextureMixer.progress()))), (v5 + 7), ui.FwmTheme.ACCENT);
    goto L194;
    L97:
    v6 = textures.TextureMixer.message();
    if (v6.isEmpty() ==0) goto L133;
    if (this.packs.isEmpty() ==0) goto L133;
    v6 = ui.Lang.t("Keine Resource-Packs im Ordner gefunden.", "No resource packs found in the folder.");
    L133:
    if (v6.isEmpty() ==0) goto L161;
    if (textures.TextureMixer.active(client.Minecraft.getInstance()) ==0) goto L161;
    v6 = ui.Lang.t("FWM Mix ist aktiv.", "FWM Mix is on.");
    L161:
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v6, (this.width - 6))), (this.x + 3), v5, ui.FwmTheme.TEXT_DIM, 0);
    L194:
    v6 = client.Minecraft.getInstance();
    v7 = ((this.width - 12) / 4);
    v8 = this.footY();
    this.button(ctx, font, this.x, v8, v7, ui.Lang.t("Mix bauen", "Build mix"), mouseX, mouseY, /*indy*/run(v6));
    this.button(ctx, font, (this.x + (v7 + 4)), v8, v7, ui.Lang.t("Mix entfernen", "Remove mix"), mouseX, mouseY, /*indy*/run(v6));
    if (config.FwmConfig.INSTANCE.textureMixDisableSources ==0) goto L335;
    goto L344;
    L335:
    L344:
    ctx.button(font, (this.x + (2 * (v7 + 4))), v8, v7, ui.Lang.t("Quell-Packs aus: ", "Sources off: "), ui.Lang.t("An", "On") + ui.Lang.t("Aus", "Off"), mouseX, mouseY, /*indy*/run());
    this.button(ctx, font, (this.x + (3 * (v7 + 4))), v8, (this.width - (3 * (v7 + 4))), ui.Lang.t("Neu einlesen", "Rescan"), mouseX, mouseY, /*indy*/run(this));
    return;
}

private boolean button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int bx, int by, int bw, String label, int mouseX, int mouseY, Runnable action) {
    v10 = ui.panel.TexturesPanel.in(((d)mouseX), ((d)mouseY), bx, by, bw, 14);
    if (v10 ==0) goto L40;
    goto L43;
    L40:
    L43:
    ui.FwmTheme.box(bx, by, bw, 14, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v11 = network.chat.Component.literal(font.plainSubstrByWidth(label, (bw - 4)));
    Objects.requireNonNull(font);
    if (v10 ==0) goto L106;
    goto L109;
    L106:
    L109:
    font.text(v11, (bx + ((bw - font.width(v11)) / 2)), ((by + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    this.hits.add(?);
    return;
}

private static boolean in(double mx, double a1, int my, int a3, int bx, int by) {
    if (cmp(mx,((d)bx)) <0) goto L42;
    if (cmp(mx,((d)(bx + v6))) >=0) goto L42;
    if (cmp(my,((d)by)) <0) goto L42;
    if (cmp(my,((d)(by + v7))) >=0) goto L42;
    goto L43;
    L42:
    L43:
    return 0;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    if (this.themesTab ==0) goto L25;
    goto L29;
    L25:
    L29:
    v6 = this.search;
    if (this.themesTab ==0) goto L61;
    goto L68;
    L61:
    L68:
    v7 = ui.panel.TexturesPanel.in(mouseY, this.midX(), (this.y + 23), (this.midW() - 66), (this.midW() - 106), 15);
    v6.setFocused(v7);
    if (v7 ==0) goto L89;
    return 1;
    L89:
    if (v5 ==0) goto L96;
    return 1;
    L96:
    v8 = this.hits.iterator();
    L107:
    if (v8.hasNext() ==0) goto L172;
    v9 = ((ui.panel.TexturesPanel$Hit)v8.next());
    if (ui.panel.TexturesPanel.in(mouseX, mouseY, v9.x(), v9.y(), v9.w(), v9.h()) ==0) goto L169;
    v9.action().run();
    return 1;
    L169:
    goto L107;
    L172:
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    v7 = (-((i)Math.signum(v5)));
    if (cmp(mouseX,((d)(this.x + 122))) >=0) goto L40;
    this.listScroll = Math.max(0, (this.listScroll + v7));
    goto L80;
    L40:
    if (this.themesTab ==0) goto L65;
    this.themeScroll = Math.max(0, (this.themeScroll + v7));
    goto L80;
    L65:
    this.gridScroll = Math.max(0, (this.gridScroll + v7));
    L80:
    return 1;
}

private boolean focused() {
    if (this.themesTab ==0) goto L33;
    if (this.themeName == null) goto L31;
    if (this.themeName.isFocused() ==0) goto L31;
    goto L32;
    L31:
    L32:
    return null;
    L33:
    if (this.search == null) goto L57;
    if (this.search.isFocused() ==0) goto L57;
    goto L58;
    L57:
    L58:
    return null;
}

public client.gui.components.EditBox isCapturingKeys() {
    if (this.focused() == null) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    v4 = this.focused();
    if (v4 != null) goto L13;
    return 0;
    L13:
    if (keyCode != 41) goto L41;
    v4.setFocused(0);
    if (v4 != this.themeName) goto L39;
    this.renaming = null;
    L39:
    return 1;
    L41:
    if (keyCode != 40) goto L62;
    if (v4 != this.themeName) goto L62;
    this.saveTheme();
    return 1;
    L62:
    v5 = v4.keyPressed(?);
    if (v4 != this.search) goto L93;
    this.gridScroll = 0;
    L93:
    return v5;
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.focused();
    if (v2 != null) goto L11;
    return 0;
    L11:
    if (v2 != this.search) goto L24;
    this.gridScroll = 0;
    L24:
    return v2.charTyped(input);
}

public void onHidden() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    if (this.themeName == null) goto L40;
    this.host.detach(this.themeName);
    L40:
    textures.TextureMixer.releasePreviews();
    return;
}

private void lambda$footer$3() {
    textures.TextureMixer.clearPreviews();
    this.onResize();
    return;
}

private static void lambda$footer$2() {
    if (config.FwmConfig.INSTANCE.textureMixDisableSources !=0) goto L16;
    goto L17;
    L16:
    L17:
    1.textureMixDisableSources = 0;
    config.ConfigManager.save();
    return;
}

private static void lambda$footer$1(client.Minecraft client) {
    textures.TextureMixer.remove(client);
    return;
}

private static void lambda$footer$0(client.Minecraft client) {
    textures.TextureMixer.build(client);
    return;
}

private boolean lambda$saveTheme$0(String name, config.FwmConfig$TextureTheme t) {
    if (t == this.renaming) goto L23;
    if (name.equalsIgnoreCase(t.name) ==0) goto L23;
    goto L24;
    L23:
    L24:
    return 0;
}

private void lambda$themes$4(config.FwmConfig$TextureTheme theme) {
    this.confirm(theme);
    return;
}

private void lambda$themes$3(config.FwmConfig$TextureTheme theme) {
    this.renaming = theme;
    this.themeName.setValue(theme.name);
    this.themeName.setFocused(1);
    return;
}

private static void lambda$themes$2(config.FwmConfig$TextureTheme theme) {
    textures.TextureMixer.applyTheme(theme);
    return;
}

private static String lambda$themes$0(util.FormattedCharSequence o) {
    v1 = ?;
    o.accept(/*indy*/accept(v1));
    return v1.toString();
}

private static StringBuilder lambda$themes$1(StringBuilder b, int index, network.chat.Style style, int cp) {
    b.appendCodePoint(cp);
    return 1;
}

private static void lambda$grid$3(String id, String pack) {
    textures.TextureMixer.choose(id, pack);
    return;
}

private static void lambda$grid$2(String pack) {
    textures.TextureMixer.toggleFavourite(pack);
    return;
}

private void lambda$grid$1() {
    this.onlyFavourites = 1;
    this.gridScroll = 0;
    return;
}

private void lambda$grid$0() {
    this.onlyFavourites = 0;
    this.gridScroll = 0;
    return;
}

private void lambda$categoryList$1(String id) {
    this.category = id;
    this.gridScroll = 0;
    return;
}

private boolean lambda$categoryList$0(textures.TextureMixer$Category c) {
    return c.id().equals(this.category);
}

private void lambda$render$1() {
    this.setTab(1);
    return;
}

private void lambda$render$0() {
    this.setTab(0);
    return;
}

