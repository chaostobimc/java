// class net/friendswithmoney/fwm/ui/panel/MembersPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field PREVIEW_H I;
field SEARCH_H I;
field ROW_H I;
field FOOTER_H I;
field SCROLLBAR_W I;
field lines Ljava/util/List;;
field builtFor I;
field builtQuery Ljava/lang/String;;
field matches I;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field search Lnet/minecraft/client/gui/components/EditBox;;
field query Ljava/lang/String;;
field listY I;
field listH I;
field rowsVisible I;
field scroll I;
public void <init>(ui.panel.PanelHost host) {
    this.lines = ?;
    this.builtFor = m1;
    this.builtQuery = "";
    this.query = "";
    this.host = host;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.members", new Object[0]);
}

protected int onResize() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    v1 = client.Minecraft.getInstance().font;
    v2 = ((this.y + 15) + 3);
    this.search = ?;
    this.search.setBordered(0);
    this.search.setMaxLength(32);
    this.search.setTextColor(ui.FwmTheme.TEXT);
    this.search.setHint(ui.Lang.of("fwm.members.search", new Object[0]));
    this.search.setValue(this.query);
    this.search.setResponder(/*indy*/accept(this));
    this.host.attach(this.search);
    this.listY = ((v2 + 15) + 3);
    this.listH = Math.max(11, ((((this.y + this.height) - 11) - 2) - this.listY));
    this.rowsVisible = Math.max(1, (this.listH / 11));
    this.scroll = Math.min(this.scroll, this.maxScroll());
    return;
}

private int build() {
    v1 = net.Members.list();
    if (this.builtFor != v1.size()) goto L54;
    if (this.builtQuery.equals(this.query) ==0) goto L54;
    if (this.lines.isEmpty() ==0) goto L53;
    if (this.query.isEmpty() !=0) goto L54;
    L53:
    return;
    L54:
    v2 = this.query.toLowerCase(Locale.ROOT);
    v3 = ?;
    v4 = v1.iterator();
    L81:
    if (v4.hasNext() ==0) goto L158;
    v5 = ((net.MemberData$Member)v4.next());
    if (v2.isEmpty() !=0) goto L146;
    if (ui.panel.MembersPanel.contains(v5.name, v2) !=0) goto L146;
    if (ui.panel.MembersPanel.contains(v5.clan, v2) !=0) goto L146;
    if (ui.panel.MembersPanel.contains(v5.role, v2) ==0) goto L155;
    L146:
    v3.add(v5);
    L155:
    goto L81;
    L158:
    v3.sort(Comparator.comparingInt(/*indy*/applyAsInt()).thenComparing(/*indy*/apply()).thenComparingInt(/*indy*/applyAsInt()).thenComparing(/*indy*/apply()));
    this.lines.clear();
    v4 = null;
    v5 = v3.iterator();
    L222:
    if (v5.hasNext() ==0) goto L322;
    v6 = ((net.MemberData$Member)v5.next());
    if (v6.clan != null) goto L257;
    goto L262;
    L257:
    L262:
    v7 = v6.clan;
    if (v7.equals(v4) !=0) goto L298;
    v4 = v7;
    this.lines.add(?);
    L298:
    this.lines.add(?);
    goto L222;
    L322:
    this.builtFor = v1.size();
    this.builtQuery = this.query;
    this.matches = v3.size();
    this.scroll = Math.min(this.scroll, this.maxScroll());
    return;
}

private static boolean contains(String text, String needle) {
    if (text == null) goto L22;
    if (text.toLowerCase(Locale.ROOT).contains(needle) ==0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

private int maxScroll() {
    return Math.max(0, (this.lines.size() - this.rowsVisible));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.build();
    this.renderPreview(ctx, font);
    if (this.search.isFocused() ==0) goto L49;
    goto L52;
    L49:
    L52:
    ui.FwmTheme.box(this.x, ((this.y + 15) + 3), this.width, 15, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    this.renderList(ctx, font);
    this.renderScrollbar(ctx);
    this.renderFooter(ctx, font);
    return;
}

private void renderPreview(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    new ChatFormatting[2][0] = ChatFormatting.GREEN;
    new ChatFormatting[2][1] = ChatFormatting.BOLD;
    v3 = network.chat.Component.literal("FWM").withStyle(new ChatFormatting[2]).append(network.chat.Component.literal(" ").append(ui.Lang.of("fwm.members.header", new Object[0])).withStyle(ChatFormatting.WHITE));
    Objects.requireNonNull(font);
    ctx.text(font, v3, (this.x + ((this.width - font.width(v3)) / 2)), ((this.y + ((15 - 9) / 2)) + 1), ui.FwmTheme.TEXT, 0);
    ui.FwmTheme.divider(ctx, this.x, (this.y + 15), this.width);
    return;
}

private void renderList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    if (this.lines.isEmpty() ==0) goto L106;
    if (this.query.isEmpty() !=0) goto L39;
    if (net.Members.list().isEmpty() !=0) goto L39;
    goto L57;
    L39:
    if (net.Members.state() != net.Members$State.ERROR) goto L54;
    goto L57;
    L54:
    L57:
    v3 = ui.Lang.of("fwm.board.loading", new Object[0]);
    ctx.text(font, v3, (this.x + ((this.width - font.width(v3)) / 2)), ((this.listY + (this.listH / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L106:
    v3 = ui.FwmGfx.playerName();
    v4 = (((this.x + this.width) - 2) - 4);
    v5 = 0;
    L128:
    if (v5 >= this.rowsVisible) goto L485;
    v6 = (this.scroll + v5);
    if (v6 < this.lines.size()) goto L163;
    goto L485;
    L163:
    v7 = ((ui.panel.MembersPanel$Line)this.lines.get(v6));
    v8 = (this.listY + (v5 * 11));
    Objects.requireNonNull(font);
    v9 = ((v8 + ((11 - 9) / 2)) + 1);
    if (v7.isHeading() ==0) goto L292;
    ctx.text(font, network.chat.Component.literal(v7.clan()), (this.x + 2), v9, ui.FwmTheme.ACCENT, 0);
    ui.FwmTheme.divider(ctx, (((this.x + 2) + font.width(v7.clan())) + 4), (v8 + 5), (((v4 - this.x) - font.width(v7.clan())) - 6));
    goto L479;
    L292:
    v10 = v7.member();
    if (v10.name != null) goto L312;
    goto L317;
    L312:
    L317:
    v11 = v10.name;
    v12 = v11.equalsIgnoreCase(v3);
    if (v12 ==0) goto L361;
    ctx.fill(this.x, v8, ((this.x + this.width) - 2), (v8 + 11), ui.FwmTheme.HOVER);
    L361:
    if (v12 ==0) goto L388;
    goto L391;
    L388:
    L391:
    font.text(network.chat.Component.literal(v11), (this.x + 8), v9, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    if (v10.role != null) goto L408;
    goto L413;
    L408:
    L413:
    v13 = v10.role;
    if (v13.isEmpty() ==0) goto L426;
    goto L479;
    L426:
    v14 = ui.RankColors.flat(net.Members.rankIndex(v13));
    v15 = font.plainSubstrByWidth(v13, Math.max(20, (this.width / 2)));
    ctx.text(font, network.chat.Component.literal(v15), (v4 - font.width(v15)), v9, v14, 0);
    L479:
    v5 = v5 + 1;
    goto L128;
    L485:
    return;
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    if (this.maxScroll() >0) goto L8;
    return;
    L8:
    v2 = ((this.x + this.width) - 2);
    ctx.fill(v2, this.listY, (v2 + 2), (this.listY + this.listH), ui.FwmTheme.OFF);
    v3 = Math.max(12, ((this.listH * this.rowsVisible) / Math.max(1, this.lines.size())));
    v4 = (this.listY + (((this.listH - v3) * this.scroll) / this.maxScroll()));
    ctx.fill(v2, v4, (v2 + 2), (v4 + v3), ui.FwmTheme.ACCENT);
    return;
}

private void renderFooter(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = ((this.y + this.height) - 11);
    ui.FwmTheme.divider(ctx, this.x, (v3 - 1), this.width);
    if (this.query.isEmpty() ==0) goto L65;
    new Object[1][0] = Integer.valueOf(net.Members.list().size());
    goto L99;
    L65:
    new Object[2][0] = Integer.valueOf(this.matches);
    new Object[2][1] = Integer.valueOf(net.Members.list().size());
    L99:
    v4 = ui.Lang.of("fwm.members.countFiltered", new Object[2]);
    ctx.text(font, v4, (this.x + 4), (v3 + 2), ui.FwmTheme.TEXT_DIM, 0);
    v5 = net.Members.ageMinutes();
    if (cmp(v5,0) >=0) goto L146;
    goto L164;
    L146:
    new Object[1][0] = Long.valueOf(v5);
    L164:
    v7 = ui.Lang.of("fwm.board.updated", new Object[1]);
    ctx.text(font, v7, (((this.x + this.width) - 4) - font.width(v7)), (v3 + 2), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = ((this.y + 15) + 3);
    if (cmp(mouseY,((d)v6)) <0) goto L54;
    if (cmp(mouseY,((d)(v6 + 15))) >=0) goto L54;
    goto L55;
    L54:
    L55:
    1.setFocused(0);
    return 1;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.search == null) goto L41;
    if (this.search.isFocused() ==0) goto L41;
    if (this.search.keyPressed(?) ==0) goto L41;
    goto L42;
    L41:
    L42:
    return 0;
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.search == null) goto L32;
    if (this.search.isFocused() ==0) goto L32;
    if (this.search.charTyped(input) ==0) goto L32;
    goto L33;
    L32:
    L33:
    return 0;
}

public void onHidden() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    return;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - (((i)Math.signum(v5)) * 3))));
    return 1;
}

private static String lambda$build$3(net.MemberData$Member m) {
    if (m.name != null) goto L12;
    goto L22;
    L12:
    L22:
    return m.name.toLowerCase(Locale.ROOT);
}

private static int lambda$build$2(net.MemberData$Member m) {
    return net.Members.rankIndex(m.role);
}

private static String lambda$build$1(net.MemberData$Member m) {
    if (m.clan != null) goto L12;
    goto L16;
    L12:
    L16:
    return m.clan;
}

private static int lambda$build$0(net.MemberData$Member m) {
    return net.Members.clanOrder(m.clan);
}

private String lambda$onResize$0(String text) {
    if (text != null) goto L10;
    goto L14;
    L10:
    L14:
    "".query = text.trim();
    this.scroll = 0;
    return;
}

