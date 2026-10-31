// class net/friendswithmoney/fwm/ui/FwmScreen  (classfile vClassVersion(major=69, minor=0))
// extends net/minecraft/client/gui/screens/Screen
field MAX_W I;
field MAX_H I;
field MIN_VIEW_W I;
field MIN_VIEW_H I;
field PAD I;
field HEADER_H I;
field HEADER_BOX_H I;
field FOOTER_H I;
field SIDEBAR_W I;
field SIDEBAR_W_NARROW I;
field NARROW_BELOW I;
field CAT_H I;
field CAT_GAP I;
field SIDEBAR_HEADING_H I;
field ARROW_W I;
field TITLE_H I;
field LOGO_H I;
field BACK_H I;
field HEAD_SIZE I;
field FLAG_W I;
field FLAG_H I;
field FLAG_GAP I;
field parent Lnet/minecraft/client/gui/screens/Screen;;
field categories Ljava/util/List;;
field base Lnet/friendswithmoney/fwm/ui/panel/Panel;;
field subs Ljava/util/Deque;;
field selected I;
field panelX I;
field panelY I;
field panelW I;
field panelH I;
field sidebarW I;
field sidebarScroll I;
field frameX I;
field frameY I;
field frameW I;
field frameH I;
field headerY I;
field builtForScale F;
field openedAt J;
field OPEN_MS I;
field TAB_OVERVIEW I;
field TAB_SELLMACRO I;
field TAB_MESSAGES I;
field SUB_STATS I;
field pendingTab I;
field SUB_KEYS I;
field pendingKey Ljava/lang/String;;
field dragCategory I;
field dragMoved Z;
field ALWAYS_IN_SIDEBAR Ljava/util/List;;
field SWITCH_W I;
field SWITCH_H I;
field menuKeyArmed Z;
public void <init>(client.gui.screens.Screen parent) {
    return;
}

public void <init>(client.gui.screens.Screen parent, int tab) {
    return;
}

public static void at(client.gui.screens.Screen parent, String categoryKey) {
    v2 = ?;
    v2.pendingKey = categoryKey;
    return v2;
}

public boolean <init>(client.gui.screens.Screen parent, int tab, int subTab) {
    this.categories = ?;
    this.subs = ?;
    this.builtForScale = 1;
    this.pendingTab = m1;
    this.dragCategory = m1;
    if (ui.FwmScreen.menuKeyHeld() !=0) goto L61;
    goto L62;
    L61:
    L62:
    1.menuKeyArmed = 0;
    this.parent = parent;
    this.selected = tab;
    this.pendingTab = subTab;
    return;
}

protected void init() {
    if (cmp(this.openedAt,0) !=0) goto L16;
    this.openedAt = System.currentTimeMillis();
    L16:
    if (this.categories.isEmpty() ==0) goto L319;
    this.categories.add(ui.Category.of("fwm.tab.overview", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.sellmacro", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.messages", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.toolguard", /*indy*/get()));
    this.categories.add(ui.Category.of("fwm.tab.secondchat", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.auction", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.colorcodes", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.market", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.rtp", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.extras", /*indy*/get()));
    this.categories.add(ui.Category.of("fwm.tab.antileak", /*indy*/get()));
    this.categories.add(ui.Category.of("fwm.tab.spawner", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.members", /*indy*/get(this)));
    this.categories.add(ui.Category.of("fwm.tab.settings", /*indy*/get(this)));
    L319:
    if (this.base != null) goto L361;
    this.base = ((ui.panel.Panel)((ui.Category)this.categories.get(this.selected)).panel().get());
    this.applyPendingTab();
    L361:
    this.layout();
    if (this.pendingKey == null) goto L388;
    v1 = this.pendingKey;
    this.pendingKey = null;
    this.tourShow(v1, m1);
    L388:
    if (ui.FwmTutorial.pending() ==0) goto L403;
    if (ui.FwmTutorial.active() !=0) goto L403;
    ui.FwmTutorial.start();
    L403:
    return;
}

void tourShow(String categoryKey, int subTab) {
    v3 = 0;
    L2:
    if (v3 >= this.categories.size()) goto L125;
    if (((ui.Category)this.categories.get(v3)).labelKey().equals(categoryKey) !=0) goto L41;
    goto L119;
    L41:
    if (((ui.Category)this.categories.get(v3)).locked() ==0) goto L61;
    return;
    L61:
    if (subTab <0) goto L70;
    this.pendingTab = subTab;
    L70:
    if (v3 == this.selected) goto L90;
    this.select(v3);
    this.applyPendingTab();
    goto L113;
    L90:
    if (this.subs.isEmpty() !=0) goto L109;
    this.closeSub();
    goto L90;
    L109:
    this.applyPendingTab();
    L113:
    this.scrollSidebarTo(v3);
    return;
    L119:
    v3 = v3 + 1;
    goto L2;
    L125:
    return;
}

void tourRelayout() {
    this.layout();
    if (ui.FwmTutorial.active() ==0) goto L18;
    this.scrollSidebarTo(this.selected);
    L18:
    return;
}

private int scrollSidebarTo(int index) {
    v2 = this.frameY;
    v3 = m1;
    v4 = this.sidebarRows().iterator();
    L18:
    if (v4.hasNext() ==0) goto L74;
    v5 = ((ui.FwmScreen$SidebarRow)v4.next());
    if (v5.isHeading() ==0) goto L54;
    v2 = v2 + 10;
    goto L18;
    L54:
    if (v5.categoryIndex() != index) goto L68;
    v3 = v2;
    goto L74;
    L68:
    v2 = v2 + 30;
    goto L18;
    L74:
    if (v3 >=0) goto L79;
    return;
    L79:
    v4 = (v3 - this.frameY);
    v5 = (this.sidebarViewBottom() - this.frameY);
    this.sidebarScroll = Math.max(0, Math.min(this.maxSidebarScroll(), Math.max(((v4 + 24) - v5), Math.min(this.sidebarScroll, v4))));
    return;
}

private float scale() {
    return this.builtForScale;
}

private float configuredScale() {
    return Math.max(0.10000000149011612, (((f)Math.min(config.FwmConfig.INSTANCE.menuScale, ui.FwmScreen.maxScalePercent())) / 100.0));
}

public static int maxScalePercent() {
    v0 = client.Minecraft.getInstance();
    if (v0 == null) goto L15;
    if (v0.getWindow() != null) goto L19;
    L15:
    return 150;
    L19:
    v1 = Math.min(((v0.getWindow().getGuiScaledWidth() * 100) / 300), ((v0.getWindow().getGuiScaledHeight() * 100) / 220));
    return Math.max(70, Math.min(150, v1));
}

private int scaledWidth() {
    return Math.round((((f)this.width) / this.scale()));
}

private int scaledHeight() {
    return Math.round((((f)this.height) / this.scale()));
}

private float toLocal(double value) {
    return (value / ((d)this.scale()));
}

private void layout() {
    this.builtForScale = this.configuredScale();
    v1 = this.scaledWidth();
    v2 = this.scaledHeight();
    v3 = Math.round((((f)ui.FwmTutorial.reservedSide(this.width)) / this.scale()));
    this.panelW = Math.min(((v1 - 8) - v3), 460);
    this.panelH = Math.min((v2 - 8), 236);
    v4 = Math.max(2, (((v1 - this.panelW) - v3) / 2));
    if (v3 <=0) goto L98;
    if (ui.FwmTutorial.onLeft() ==0) goto L98;
    goto L99;
    L98:
    L99:
    v4.panelX = (v3 + 0);
    this.panelY = Math.max(2, ((v2 - this.panelH) / 2));
    ui.FwmTutorial.dock(Math.round((((f)this.panelX) * this.scale())), Math.round((((f)(this.panelX + this.panelW)) * this.scale())), Math.round((((f)this.panelY) * this.scale())), Math.round((((f)(this.panelY + this.panelH)) * this.scale())));
    if (this.panelW >= 330) goto L200;
    goto L202;
    L200:
    L202:
    104.sidebarW = (118 + 0);
    this.headerY = (this.panelY + 4);
    this.frameX = (((this.panelX + 6) + this.sidebarW) + 6);
    this.frameY = ((this.panelY + 26) + 4);
    this.frameW = (((this.panelX + this.panelW) - 6) - this.frameX);
    this.frameH = ((((this.panelY + this.panelH) - 16) - 2) - this.frameY);
    this.applyPanelBounds();
    return;
}

private void applyPanelBounds() {
    if (this.active() == null) goto L21;
    if (this.active().showTitle() !=0) goto L21;
    goto L23;
    L21:
    L23:
    v1 = 14;
    v2 = (this.frameX + 4);
    v3 = ((this.frameY + v1) + 4);
    v4 = Math.max(20, (this.frameW - 8));
    v5 = Math.max(26, ((this.frameH - v1) - 8));
    if (this.base == null) goto L90;
    this.base.setBounds(v2, v3, v4, v5);
    L90:
    v6 = this.subs.iterator();
    L101:
    if (v6.hasNext() ==0) goto L137;
    v7 = ((ui.panel.Panel)v6.next());
    v7.setBounds(v2, v3, v4, v5);
    goto L101;
    L137:
    return;
}

private Object active() {
    if (this.subs.isEmpty() ==0) goto L19;
    goto L31;
    L19:
    L31:
    return ((ui.panel.Panel)this.subs.peek());
}

private boolean tiles() {
    v1 = ?;
    v2 = this.orderedIndices(0).iterator();
    L19:
    if (v2.hasNext() ==0) goto L84;
    v3 = ((Integer)v2.next()).intValue();
    v4 = ((ui.Category)this.categories.get(v3));
    v1.add(?);
    goto L19;
    L84:
    return v1;
}

private void applyPendingTab() {
    if (this.pendingTab >=0) goto L8;
    return;
    L8:
    v2 = this.base;
    if ((v2 instanceof ui.panel.TabbedPanel) ==0) goto L33;
    v1 = ((ui.panel.TabbedPanel)v2);
    v1.setTab(this.pendingTab);
    L33:
    this.pendingTab = m1;
    return;
}

public void openSub(ui.panel.Panel panel) {
    this.subs.push(panel);
    this.applyPanelBounds();
    return;
}

public void closeSub() {
    if (this.subs.isEmpty() ==0) goto L13;
    return;
    L13:
    ((ui.panel.Panel)this.subs.pop()).onHidden();
    this.applyPanelBounds();
    return;
}

public client.gui.components.events.GuiEventListener attach(client.gui.components.AbstractWidget widget) {
    this.addWidget(widget);
    return;
}

public void detach(client.gui.components.AbstractWidget widget) {
    this.removeWidget(widget);
    return;
}

public void extractRenderState(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    ctx.fill(0, 0, this.width, this.height, ui.FwmTheme.OVERLAY);
    v5 = ctx.pose();
    v5.pushMatrix();
    v5.scale(this.scale(), this.scale());
    v6 = ui.widget.Anim.ease(Math.min(1, (((f)(System.currentTimeMillis() - this.openedAt)) / 140.0)));
    if (cmp(v6,1) >=0) goto L169;
    v7 = (0.9599999785423279 + (0.03999999910593033 * v6));
    v5.translate((((f)this.panelX) + (((f)this.panelW) / 2)), (((f)this.panelY) + (((f)this.panelH) / 2)));
    v5.scale(v7, v7);
    v5.translate((-(((f)this.panelX) + (((f)this.panelW) / 2))), ((-(((f)this.panelY) + (((f)this.panelH) / 2))) - (4.0 * (1 - v6))));
    L169:
    v7 = ((i)this.toLocal(((d)mouseX)));
    v8 = ((i)this.toLocal(((d)mouseY)));
    ui.FwmTheme.panel(ctx, this.panelX, this.panelY, this.panelW, this.panelH);
    this.renderHeader(ctx, v7, v8);
    ui.FwmTheme.divider(ctx, (this.panelX + 6), (this.panelY + 26), (this.panelW - 12));
    this.renderSidebar(ctx, v7, v8);
    this.renderContent(ctx, v7, v8, delta);
    this.renderFooter(ctx, v7, v8);
    v5.popMatrix();
    ui.FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
    this.extractRenderState(ctx, mouseX, mouseY, delta);
    return;
}

private void renderHeader(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    v4 = this.sidebarW;
    if (this.selected !=0) goto L29;
    if (this.subs.isEmpty() ==0) goto L29;
    goto L30;
    L29:
    L30:
    v5 = 0;
    if (mouseX < (this.panelX + 6)) goto L80;
    if (mouseX >= ((this.panelX + 6) + v4)) goto L80;
    if (mouseY < this.headerY) goto L80;
    if (mouseY >= (this.headerY + 18)) goto L80;
    goto L81;
    L80:
    L81:
    v6 = 0;
    if (v5 ==0) goto L110;
    goto L113;
    L110:
    L113:
    if (v5 !=0) goto L123;
    if (v6 ==0) goto L129;
    L123:
    goto L132;
    L129:
    L132:
    ui.FwmTheme.box(this.headerY, v4, 18, ui.FwmTheme.ACCENT_DIM, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v7 = ui.Lang.of("fwm.tab.overview", new Object[0]);
    Objects.requireNonNull(this.font);
    if (v5 !=0) goto L207;
    if (v6 ==0) goto L213;
    L207:
    goto L216;
    L213:
    L216:
    this.font.text(v7, ((this.panelX + 6) + ((v4 - this.font.width(v7)) / 2)), ((this.headerY + ((18 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    this.renderBrand(ctx, ((this.panelX + 6) + v4), this.identityX());
    this.renderIdentity(ctx);
    return;
}

private network.chat.MutableComponent identityName() {
    v1 = Math.max(0, (((this.panelW / 3) - 8) - 18));
    return network.chat.Component.literal(this.font.plainSubstrByWidth(ui.FwmGfx.playerName(), v1));
}

private int identityWidth() {
    v1 = this.font.width(this.identityName());
    if (v1 <=0) goto L24;
    goto L25;
    L24:
    L25:
    return ((v1 + 4) + 0);
}

private int identityX() {
    return (((this.panelX + this.panelW) - 6) - this.identityWidth());
}

private int overIdentity(double mx, double a2) {
    if (cmp(mx,((d)this.identityX())) <0) goto L52;
    if (cmp(mx,((d)(this.identityX() + this.identityWidth()))) >=0) goto L52;
    if (cmp(v3,((d)this.headerY)) <0) goto L52;
    if (cmp(v3,((d)(this.headerY + 18))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

private void renderIdentity(client.gui.GuiGraphicsExtractor ctx) {
    v2 = this.identityName();
    v3 = this.identityWidth();
    v4 = this.identityX();
    ui.FwmTheme.box(ctx, v4, this.headerY, v3, 18, ui.FwmTheme.RAISED, ui.FwmTheme.OFF);
    if (this.font.width(v2) <=0) goto L86;
    Objects.requireNonNull(this.font);
    ctx.text(this.font, v2, (v4 + 6), ((this.headerY + ((18 - 9) / 2)) + 1), ui.FwmTheme.TEXT, 0);
    L86:
    ui.FwmGfx.playerHead(ctx, (((v4 + v3) - 8) - 5), (this.headerY + 5), 8);
    return;
}

private void renderBrand(client.gui.GuiGraphicsExtractor ctx, int fromX, int toX) {
    v4 = Math.max(1, Math.round(21.33333396911621));
    v5 = ((toX - fromX) - 12);
    if (v5 >= v4) goto L28;
    return;
    L28:
    v6 = ui.Lang.of("fwm.menu.brand", new Object[0]);
    v7 = this.font.width(v6);
    if (v5 < ((v4 + 4) + v7)) goto L67;
    goto L68;
    L67:
    L68:
    v8 = 0;
    if (v8 ==0) goto L85;
    goto L87;
    L85:
    L87:
    v9 = v4;
    v10 = ((fromX + 6) + ((v5 - v9) / 2));
    if (v8 ==0) goto L146;
    Objects.requireNonNull(this.font);
    ctx.text(this.font, v6, v10, ((this.headerY + ((18 - 9) / 2)) + 1), ui.FwmTheme.TEXT_DIM, 0);
    L146:
    if (v8 ==0) goto L162;
    goto L164;
    L162:
    L164:
    ui.FwmTheme.logo(((v10 + v7) + 4), v10, (this.headerY + 1), v4);
    return;
}

private boolean sidebarRows() {
    v1 = ?;
    v2 = 0;
    v3 = this.orderedIndices().iterator();
    L20:
    if (v3.hasNext() ==0) goto L107;
    v4 = ((Integer)v3.next()).intValue();
    if (((ui.Category)this.categories.get(v4)).locked() ==0) goto L87;
    if (v2 !=0) goto L87;
    v1.add(?);
    v2 = 1;
    L87:
    v1.add(?);
    goto L20;
    L107:
    return v1;
}

private boolean sidebarContentHeight() {
    v1 = 0;
    v2 = this.sidebarRows().iterator();
    L12:
    if (v2.hasNext() ==0) goto L51;
    v3 = ((ui.FwmScreen$SidebarRow)v2.next());
    if (v3.isHeading() ==0) goto L44;
    goto L46;
    L44:
    L46:
    v1 = (10 + 30);
    goto L12;
    L51:
    return v1;
}

private int sidebarViewBottom() {
    return ((this.panelY + this.panelH) - 16);
}

private int maxSidebarScroll() {
    return Math.max(0, (this.sidebarContentHeight() - (this.sidebarViewBottom() - this.frameY)));
}

private void renderSidebar(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    v4 = this.sidebarViewBottom();
    this.sidebarScroll = Math.max(0, Math.min(this.maxSidebarScroll(), this.sidebarScroll));
    ctx.enableScissor((this.panelX + 6), this.frameY, ((this.panelX + 6) + this.sidebarW), v4);
    v5 = (this.frameY - this.sidebarScroll);
    v6 = this.sidebarRows().iterator();
    L76:
    if (v6.hasNext() ==0) goto L521;
    v7 = ((ui.FwmScreen$SidebarRow)v6.next());
    if (v7.isHeading() ==0) goto L124;
    this.renderSidebarHeading(ctx, v7.headingKey(), v5);
    v5 = v5 + 10;
    goto L76;
    L124:
    v8 = v7.categoryIndex();
    v9 = ((ui.Category)this.categories.get(v8));
    if (mouseX < (this.panelX + 6)) goto L193;
    if (mouseX >= ((this.panelX + 6) + this.sidebarW)) goto L193;
    if (mouseY < v5) goto L193;
    if (mouseY >= (v5 + 24)) goto L193;
    goto L194;
    L193:
    L194:
    v10 = 0;
    if (v8 != this.selected) goto L209;
    goto L210;
    L209:
    L210:
    v11 = 0;
    v12 = this.isOn(v8);
    ui.FwmTheme.sidebarButton(ctx, this.sidebarButtonX(), v5, this.sidebarButtonW(), 24, v11, v10, v9.locked(), v12);
    v13 = (((this.sidebarButtonX() + 6) + 16) + 5);
    v14 = network.chat.Component.literal(this.font.plainSubstrByWidth(ui.Lang.str(v9.labelKey(), new Object[0]), (((this.sidebarButtonX() + this.sidebarButtonW()) - 5) - v13)));
    v15 = ui.FwmTheme.sidebarTextColor(v11, v10, v9.locked(), v12);
    ui.FwmIcons.draw(ctx, v9.labelKey(), (this.sidebarButtonX() + 6), (v5 + 4), v15);
    if (v9.locked() ==0) goto L404;
    ctx.text(this.font, v14, v13, (v5 + 4), v15, 0);
    this.smallText(ctx, ui.Lang.str("fwm.tab.comingSoon", new Object[0]), (v13 + (this.font.width(v14) / 4)), (v5 + 15), ui.FwmTheme.OFF);
    goto L439;
    L404:
    Objects.requireNonNull(this.font);
    ctx.text(this.font, v14, v13, ((v5 + ((24 - 9) / 2)) + 1), v15, 0);
    L439:
    if (v9.labelKey().equals(ui.FwmTutorial.highlightKey()) ==0) goto L479;
    ui.FwmTheme.box(ctx, (this.sidebarButtonX() - 1), (v5 - 1), (this.sidebarButtonW() + 2), 26, 0, ui.FwmTheme.ACCENT);
    L479:
    if (v8 != this.dragCategory) goto L515;
    if (this.dragMoved ==0) goto L515;
    ui.FwmTheme.box(ctx, this.sidebarButtonX(), v5, this.sidebarButtonW(), 24, 0, ui.FwmTheme.ACCENT);
    L515:
    v5 = v5 + 30;
    goto L76;
    L521:
    ctx.disableScissor();
    v6 = this.maxSidebarScroll();
    if (v6 <=0) goto L545;
    this.renderSidebarScrollbar(ctx, v4, v6);
    L545:
    return;
}

public boolean sidebarKeys() {
    v1 = ?;
    v2 = 1;
    L10:
    if (v2 >= this.categories.size()) goto L66;
    v3 = ((ui.Category)this.categories.get(v2)).labelKey();
    if (ui.FwmScreen.ALWAYS_IN_SIDEBAR.contains(v3) !=0) goto L60;
    v1.add(v3);
    L60:
    v2 = v2 + 1;
    goto L10;
    L66:
    return v1;
}

private int sidebarButtonX() {
    return ((this.panelX + 6) + 0);
}

private int sidebarButtonW() {
    return (this.sidebarW - 0);
}

private List orderedIndices() {
    return this.orderedIndices(1);
}

private boolean orderedIndices(boolean respectHidden) {
    v2 = config.FwmConfig.INSTANCE;
    v3 = ?;
    v4 = ?;
    v5 = 1;
    L24:
    if (v5 >= this.categories.size()) goto L109;
    v6 = ((ui.Category)this.categories.get(v5));
    if (v6.locked() ==0) goto L78;
    v4.add(Integer.valueOf(v5));
    goto L103;
    L78:
    if (respectHidden ==0) goto L91;
    if (this.hiddenInSidebar(v5) !=0) goto L103;
    L91:
    v3.add(Integer.valueOf(v5));
    L103:
    v5 = v5 + 1;
    goto L24;
    L109:
    v3.sort(/*indy*/compare(this, v2));
    v5 = 0;
    L125:
    if (v5 >= v3.size()) goto L200;
    if (((ui.Category)this.categories.get(((Integer)v3.get(v5)).intValue())).labelKey().equals("fwm.tab.settings") ==0) goto L194;
    v3.add(((Integer)v3.remove(v5)));
    goto L200;
    L194:
    v5 = v5 + 1;
    goto L125;
    L200:
    v3.addAll(v4);
    return v3;
}

private int rank(config.FwmConfig config, int index) {
    v3 = config.sidebarOrder.indexOf(((ui.Category)this.categories.get(index)).labelKey());
    if (v3 >=0) goto L36;
    goto L37;
    L36:
    L37:
    return v3;
}

private boolean hiddenInSidebar(int index) {
    v2 = config.FwmConfig.INSTANCE;
    v3 = ((ui.Category)this.categories.get(index)).labelKey();
    if (v2.sidebarHidden.contains(v3) ==0) goto L36;
    return 1;
    L36:
    if (v2.sidebarHideDisabled ==0) goto L69;
    if (index == this.selected) goto L69;
    if (config.Features.canSwitch(v3) ==0) goto L69;
    if (config.Features.isOn(v3) !=0) goto L69;
    goto L70;
    L69:
    L70:
    return 0;
}

private void moveCategory(int index, int direction) {
    v3 = config.FwmConfig.INSTANCE;
    v4 = ?;
    v5 = this.orderedIndices(0).iterator();
    L25:
    if (v5.hasNext() ==0) goto L86;
    v6 = ((Integer)v5.next()).intValue();
    if (((ui.Category)this.categories.get(v6)).locked() !=0) goto L83;
    v4.add(Integer.valueOf(v6));
    L83:
    goto L25;
    L86:
    v5 = v4.indexOf(Integer.valueOf(index));
    if (v5 >=0) goto L105;
    return;
    L105:
    v6 = m1;
    v7 = (v5 + direction);
    L114:
    if (v7 <0) goto L169;
    if (v7 >= v4.size()) goto L169;
    if (this.hiddenInSidebar(((Integer)v4.get(v7)).intValue()) !=0) goto L160;
    v6 = v7;
    goto L169;
    L160:
    v7 = (v7 + direction);
    goto L114;
    L169:
    if (v6 >=0) goto L175;
    return;
    L175:
    v4.add(v6, ((Integer)v4.remove(v5)));
    config.FwmConfig.INSTANCE.sidebarOrder = ?;
    v7 = v4.iterator();
    L218:
    if (v7.hasNext() ==0) goto L275;
    v8 = ((Integer)v7.next()).intValue();
    config.FwmConfig.INSTANCE.sidebarOrder.add(((ui.Category)this.categories.get(v8)).labelKey());
    goto L218;
    L275:
    config.ConfigManager.save();
    if (this.selected !=0) goto L313;
    this.base = ((ui.panel.Panel)((ui.Category)this.categories.get(0)).panel().get());
    L313:
    this.layout();
    return;
}

private void renderSidebarHeading(client.gui.GuiGraphicsExtractor ctx, String key, int top) {
    this.smallText(ctx, ui.Lang.str(key, new Object[0]), (this.sidebarButtonX() + (this.sidebarButtonW() / 2)), (top + 2), ui.FwmTheme.TEXT_DIM);
    return;
}

private void renderSidebarScrollbar(client.gui.GuiGraphicsExtractor ctx, int viewBottom, int maxScroll) {
    v4 = (((this.panelX + 6) + this.sidebarW) + 2);
    v5 = (viewBottom - this.frameY);
    v6 = Math.max(10, ((v5 * v5) / (v5 + maxScroll)));
    v7 = (this.frameY + (((v5 - v6) * this.sidebarScroll) / maxScroll));
    ctx.fill(v4, this.frameY, (v4 + 2), viewBottom, ui.FwmTheme.OFF);
    ctx.fill(v4, v7, (v4 + 2), (v7 + v6), ui.FwmTheme.ACCENT);
    return;
}

private boolean isOn(int index) {
    v2 = ((ui.Category)this.categories.get(index));
    if (v2.locked() !=0) goto L31;
    if (config.Features.isOn(v2.labelKey()) ==0) goto L35;
    L31:
    goto L36;
    L35:
    L36:
    return 0;
}

private org.joml.Matrix3x2fStack smallText(client.gui.GuiGraphicsExtractor ctx, String text, int cx, int top, int color) {
    v6 = ctx.pose();
    v6.pushMatrix();
    v6.translate((((f)cx) - (((f)this.font.width(text)) / 4.0)), ((f)top));
    v6.scale(0.5, 0.5);
    ctx.text(this.font, network.chat.Component.literal(text), 0, 0, color, 0);
    v6.popMatrix();
    return;
}

private void renderContent(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    ui.FwmTheme.contentFrame(ctx, this.frameX, this.frameY, this.frameW, this.frameH);
    if (this.subs.isEmpty() ==0) goto L36;
    goto L40;
    L36:
    L40:
    v5 = this.backWidth();
    v6 = this.active().settingsCog();
    v7 = this.active().headerSwitch();
    if (this.active().showTitle() ==0) goto L276;
    if (v7 == null) goto L86;
    goto L123;
    L86:
    if (v6 ==0) goto L100;
    goto L123;
    L100:
    if (v5 <=0) goto L114;
    goto L123;
    L114:
    L123:
    v8 = (((this.frameX + this.frameW) - this.frameX) - 9);
    v9 = network.chat.Component.literal(this.font.plainSubstrByWidth(this.active().title().getString(), Math.max(8, v8)));
    Objects.requireNonNull(this.font);
    ctx.text(this.font, v9, (this.frameX + 5), ((this.frameY + ((14 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, 0);
    if (v5 <=0) goto L220;
    this.renderBack(ctx, v5, mouseX, mouseY);
    L220:
    if (v6 ==0) goto L234;
    this.renderCog(ctx, v5, mouseX, mouseY);
    L234:
    if (v7 == null) goto L252;
    this.renderSwitch(ctx, v7, v5, v6, mouseX, mouseY);
    L252:
    ui.FwmTheme.divider(ctx, (this.frameX + 4), (this.frameY + 14), (this.frameW - 8));
    L276:
    if (this.switchedOff() ==0) goto L405;
    v8 = ui.Lang.of("fwm.feature.off", new Object[0]);
    ctx.text(this.font, v8, (this.frameX + ((this.frameW - this.font.width(v8)) / 2)), ((this.frameY + (this.frameH / 2)) - 8), ui.FwmTheme.DISABLED_TEXT, 0);
    v9 = ui.Lang.of("fwm.feature.offHint", new Object[0]);
    ctx.text(this.font, v9, (this.frameX + ((this.frameW - this.font.width(v9)) / 2)), ((this.frameY + (this.frameH / 2)) + 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L405:
    this.active().render(ctx, this.font, mouseX, mouseY, delta);
    return;
}

private boolean switchedOff() {
    if (this.subs.isEmpty() ==0) goto L19;
    if (this.base != null) goto L21;
    L19:
    return 0;
    L21:
    v1 = ((ui.Category)this.categories.get(this.selected));
    if (v1.locked() !=0) goto L59;
    if (config.Features.isOn(v1.labelKey()) !=0) goto L59;
    goto L60;
    L59:
    L60:
    return 0;
}

private int backWidth() {
    return (this.font.width(ui.Lang.of("fwm.items.back", new Object[0])) + 10);
}

private void renderBack(client.gui.GuiGraphicsExtractor ctx, int backW, int mouseX, int mouseY) {
    v5 = this.backX(backW);
    v6 = this.backY();
    if (mouseX < v5) goto L48;
    if (mouseX >= (v5 + backW)) goto L48;
    if (mouseY < v6) goto L48;
    if (mouseY >= (v6 + 11)) goto L48;
    goto L49;
    L48:
    L49:
    v7 = 0;
    if (v7 ==0) goto L73;
    goto L76;
    L73:
    L76:
    ui.FwmTheme.box(v5, v6, backW, 11, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    v8 = ui.Lang.of("fwm.items.back", new Object[0]);
    Objects.requireNonNull(this.font);
    if (v7 ==0) goto L145;
    goto L148;
    L145:
    L148:
    this.font.text(v8, (v5 + ((backW - this.font.width(v8)) / 2)), ((v6 + ((11 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, 0);
    return;
}

private int cogX(int backW) {
    if (backW <=0) goto L17;
    goto L31;
    L17:
    L31:
    return (((this.frameX + this.frameW) - 4) - 11);
}

private int switchX(int backW, boolean cog) {
    if (cog ==0) goto L12;
    goto L35;
    L12:
    if (backW <=0) goto L24;
    goto L35;
    L24:
    L35:
    v3 = ((this.frameX + this.frameW) - 4);
    return ((((v3 - 4) - 16) - 3) - this.font.width(ui.Lang.of("fwm.header.minimap", new Object[0])));
}

private int overSwitch(int backW, boolean cog, double mx, double a4) {
    if (cog ==0) goto L12;
    goto L35;
    L12:
    if (backW <=0) goto L24;
    goto L35;
    L24:
    L35:
    v7 = ((this.frameX + this.frameW) - 4);
    if (cmp(mx,((d)this.switchX(backW, cog))) <0) goto L88;
    if (cmp(mx,((d)(v7 - 4))) >=0) goto L88;
    if (cmp(v5,((d)this.backY())) <0) goto L88;
    if (cmp(v5,((d)(this.backY() + 11))) >=0) goto L88;
    goto L89;
    L88:
    L89:
    return 0;
}

private void renderSwitch(client.gui.GuiGraphicsExtractor ctx, String featureKey, int backW, boolean cog, int mouseX, int mouseY) {
    v7 = config.Features.isOn(featureKey);
    v8 = this.overSwitch(backW, cog, ((d)mouseX), ((d)mouseY));
    v9 = ui.Lang.of("fwm.header.minimap", new Object[0]);
    v10 = this.switchX(backW, cog);
    Objects.requireNonNull(this.font);
    if (v8 ==0) goto L84;
    goto L98;
    L84:
    if (v7 ==0) goto L95;
    goto L98;
    L95:
    L98:
    v9.text(v10, ((this.backY() + ((11 - 9) / 2)) + 1), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    ui.FwmTheme.toggle(ctx, ((v10 + this.font.width(v9)) + 3), (this.backY() + 1), 16, 9, ui.widget.Anim.toward('fwm.header.' + featureKey, v7, 120));
    return;
}

private void renderCog(client.gui.GuiGraphicsExtractor ctx, int backW, int mouseX, int mouseY) {
    v5 = this.cogX(backW);
    v6 = this.backY();
    if (mouseX < v5) goto L49;
    if (mouseX >= (v5 + 11)) goto L49;
    if (mouseY < v6) goto L49;
    if (mouseY >= (v6 + 11)) goto L49;
    goto L50;
    L49:
    L50:
    v7 = 0;
    if (v7 ==0) goto L75;
    goto L78;
    L75:
    L78:
    ui.FwmTheme.box(v5, v6, 11, 11, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (v7 ==0) goto L106;
    goto L109;
    L106:
    L109:
    ui.SharpIcons.draw("icon_cog", (v5 + 1), (v6 + 1), 9, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT);
    return;
}

private int backX(int backW) {
    return (((this.frameX + this.frameW) - 4) - backW);
}

private int backY() {
    return (this.frameY + 1);
}

private void renderFooter(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    v4 = ((this.panelY + this.panelH) - 16);
    ui.FwmTheme.divider(ctx, (this.panelX + 6), v4, (this.panelW - 12));
    new Object[1][0] = FwmClient.MOD_VERSION;
    ctx.text(this.font, ui.Lang.of("fwm.menu.footer", new Object[1]), ((this.panelX + 6) + 2), (v4 + 5), ui.FwmTheme.TEXT_DIM, 0);
    v5 = this.flagY();
    ui.FwmTheme.germanFlag(ctx, this.flagX(0), v5, 12, 8, ui.Lang.isGerman());
    if (ui.Lang.isGerman() !=0) goto L121;
    goto L122;
    L121:
    L122:
    ui.FwmTheme.usFlag(this.flagX(1), v5, 12, 8, 1, 0);
    v6 = this.tourButtonW();
    v7 = this.tourButtonX(v6);
    if (mouseX < v7) goto L177;
    if (mouseX >= (v7 + v6)) goto L177;
    if (mouseY < (v5 - 1)) goto L177;
    if (mouseY >= ((v5 + 8) + 1)) goto L177;
    goto L178;
    L177:
    L178:
    v8 = 0;
    if (v8 ==0) goto L212;
    goto L215;
    L212:
    L215:
    this.font.text(ui.Lang.of("fwm.tutorial.replayShort", new Object[0]), v7, (v4 + 5), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    return;
}

private int tourButtonW() {
    return this.font.width(ui.Lang.of("fwm.tutorial.replayShort", new Object[0]));
}

private int tourButtonX(int w) {
    return (((this.flagX(0) - 5) - 2) - w);
}

private int flagY() {
    return ((((this.panelY + this.panelH) - 16) + 4) + 1);
}

private int flagX(int index) {
    v2 = (((this.panelX + this.panelW) - 6) - 2);
    return ((v2 - ((2 - index) * 12)) - ((1 - index) * 5));
}

private int flagAt(double mx, double a2) {
    if (cmp(v3,((d)(this.flagY() - 1))) <0) goto L27;
    if (cmp(v3,((d)((this.flagY() + 8) + 1))) <0) goto L29;
    L27:
    return m1;
    L29:
    v5 = 0;
    L32:
    if (v5 >= 2) goto L78;
    if (cmp(mx,((d)(this.flagX(v5) - 1))) <0) goto L72;
    if (cmp(mx,((d)((this.flagX(v5) + 12) + 1))) >=0) goto L72;
    return v5;
    L72:
    v5 = v5 + 1;
    goto L32;
    L78:
    return m1;
}

public client.sounds.SoundEngine$PlayResult mouseClicked(client.input.MouseButtonEvent click, boolean doubled) {
    v3 = this.handleClick(click, doubled);
    if (v3 ==0) goto L37;
    if (config.FwmConfig.INSTANCE.menuSounds ==0) goto L37;
    client.Minecraft.getInstance().getSoundManager().play(client.resources.sounds.SimpleSoundInstance.forUI(sounds.SoundEvents.UI_BUTTON_CLICK, 1));
    L37:
    return v3;
}

private boolean handleClick(client.input.MouseButtonEvent click, boolean doubled) {
    if (ui.FwmTutorial.mouseClicked(click.x(), click.y(), util.GLFW.button(click.button())) ==0) goto L23;
    return 1;
    L23:
    if (ui.FwmTutorial.consumes(click.x(), this.width) ==0) goto L39;
    return 1;
    L39:
    v3 = this.toLocal(click.x());
    v5 = this.toLocal(click.y());
    if (util.GLFW.button(click.button()) !=0) goto L588;
    v7 = this.tourButtonW();
    v8 = this.tourButtonX(v7);
    if (cmp(v3,((d)v8)) <0) goto L145;
    if (cmp(v3,((d)(v8 + v7))) >=0) goto L145;
    if (cmp(v5,((d)(this.flagY() - 1))) <0) goto L145;
    if (cmp(v5,((d)((this.flagY() + 8) + 1))) >=0) goto L145;
    config.FwmConfig.INSTANCE.tutorialSeen = 0;
    config.ConfigManager.save();
    ui.FwmTutorial.start();
    return 1;
    L145:
    v9 = this.flagAt(v3, v5);
    if (v9 <0) goto L185;
    if (v9 !=0) goto L170;
    goto L173;
    L170:
    L173:
    ui.Lang.set("en");
    config.ConfigManager.save();
    this.layout();
    return 1;
    L185:
    if (this.active().showTitle() ==0) goto L254;
    if (this.active().headerSwitch() == null) goto L254;
    if (this.subs.isEmpty() ==0) goto L222;
    goto L226;
    L222:
    L226:
    if (0.overSwitch(this.backWidth(), this.active().settingsCog(), v3, v5) ==0) goto L254;
    config.Features.toggle(this.active().headerSwitch());
    return 1;
    L254:
    if (this.active().showTitle() ==0) goto L353;
    if (this.active().settingsCog() ==0) goto L353;
    if (this.subs.isEmpty() ==0) goto L291;
    goto L295;
    L291:
    L295:
    v10 = 0.cogX(this.backWidth());
    if (cmp(v3,((d)v10)) <0) goto L353;
    if (cmp(v3,((d)(v10 + 11))) >=0) goto L353;
    if (cmp(v5,((d)this.backY())) <0) goto L353;
    if (cmp(v5,((d)(this.backY() + 11))) >=0) goto L353;
    this.tourShow("fwm.tab.settings", 4);
    return 1;
    L353:
    if (this.subs.isEmpty() !=0) goto L429;
    v10 = this.backWidth();
    if (cmp(v3,((d)this.backX(v10))) <0) goto L429;
    if (cmp(v3,((d)(this.backX(v10) + v10))) >=0) goto L429;
    if (cmp(v5,((d)this.backY())) <0) goto L429;
    if (cmp(v5,((d)(this.backY() + 11))) >=0) goto L429;
    this.closeSub();
    return 1;
    L429:
    if (cmp(v3,((d)(this.panelX + 6))) <0) goto L511;
    if (cmp(v3,((d)((this.panelX + 6) + this.sidebarW))) >=0) goto L511;
    if (cmp(v5,((d)this.headerY)) <0) goto L511;
    if (cmp(v5,((d)(this.headerY + 18))) >=0) goto L511;
    L485:
    if (this.subs.isEmpty() !=0) goto L504;
    this.closeSub();
    goto L485;
    L504:
    this.select(0);
    return 1;
    L511:
    if (this.subs.isEmpty() ==0) goto L566;
    if ((this.base instanceof ui.panel.SpawnerPanel) ==0) goto L566;
    if (this.overIdentity(v3, v5) ==0) goto L566;
    if (net.Members.isMember(ui.FwmGfx.playerName()) ==0) goto L566;
    this.openSub(?);
    return 1;
    L566:
    v10 = this.categoryAt(v3, v5);
    if (v10 <0) goto L588;
    this.select(v10);
    return 1;
    L588:
    if (util.GLFW.button(click.button()) != 1) goto L646;
    v7 = this.categoryAt(v3, v5);
    if (v7 <0) goto L646;
    if (((ui.Category)this.categories.get(v7)).locked() !=0) goto L646;
    this.dragCategory = v7;
    this.dragMoved = 0;
    return 1;
    L646:
    if (this.active().mouseClicked(v3, v5, util.GLFW.button(click.button())) ==0) goto L668;
    return 1;
    L668:
    return this.mouseClicked(click, doubled);
}

private int categoryAt(double mx, double a2) {
    if (cmp(mx,((d)(this.panelX + 6))) <0) goto L31;
    if (cmp(mx,((d)((this.panelX + 6) + this.sidebarW))) <0) goto L33;
    L31:
    return m1;
    L33:
    if (cmp(v3,((d)this.frameY)) <0) goto L53;
    if (cmp(v3,((d)this.sidebarViewBottom())) <0) goto L55;
    L53:
    return m1;
    L55:
    v5 = (this.frameY - this.sidebarScroll);
    v6 = this.sidebarRows().iterator();
    L77:
    if (v6.hasNext() ==0) goto L144;
    v7 = ((ui.FwmScreen$SidebarRow)v6.next());
    if (v7.isHeading() ==0) goto L113;
    v5 = v5 + 10;
    goto L77;
    L113:
    if (cmp(v3,((d)v5)) <0) goto L138;
    if (cmp(v3,((d)(v5 + 24))) >=0) goto L138;
    return v7.categoryIndex();
    L138:
    v5 = v5 + 30;
    goto L77;
    L144:
    return m1;
}

private int overSidebar(double mx, double a2) {
    if (cmp(mx,((d)(this.panelX + 6))) <0) goto L55;
    if (cmp(mx,((d)((this.panelX + 6) + this.sidebarW))) >=0) goto L55;
    if (cmp(v3,((d)this.frameY)) <0) goto L55;
    if (cmp(v3,((d)this.sidebarViewBottom())) >=0) goto L55;
    goto L56;
    L55:
    L56:
    return 0;
}

private void select(int index) {
    if (((ui.Category)this.categories.get(index)).locked() !=0) goto L27;
    if (index != this.selected) goto L28;
    L27:
    return;
    L28:
    if (this.subs.isEmpty() !=0) goto L47;
    this.closeSub();
    goto L28;
    L47:
    if (this.base == null) goto L61;
    this.base.onHidden();
    L61:
    this.selected = index;
    this.base = ((ui.panel.Panel)((ui.Category)this.categories.get(index)).panel().get());
    this.layout();
    return;
}

public boolean mouseDragged(client.input.MouseButtonEvent click, double deltaX, double a3) {
    if (this.dragCategory <0) goto L29;
    this.dragTo(this.toLocal(click.x()), this.toLocal(click.y()));
    return 1;
    L29:
    if (this.active().mouseDragged(this.toLocal(click.x()), this.toLocal(click.y()), util.GLFW.button(click.button())) ==0) goto L64;
    return 1;
    L64:
    return this.mouseDragged(click, deltaX, v4);
}

private void dragTo(double mx, double a2) {
    v5 = this.categoryAt(mx, v3);
    if (v5 <0) goto L22;
    if (v5 != this.dragCategory) goto L23;
    L22:
    return;
    L23:
    if (((ui.Category)this.categories.get(v5)).locked() ==0) goto L44;
    return;
    L44:
    v6 = this.orderedIndices();
    v7 = v6.indexOf(Integer.valueOf(this.dragCategory));
    v8 = v6.indexOf(Integer.valueOf(v5));
    if (v7 <0) goto L90;
    if (v8 >=0) goto L91;
    L90:
    return;
    L91:
    if (v8 <= v7) goto L107;
    goto L108;
    L107:
    L108:
    this.dragCategory.moveCategory(1, m1);
    this.dragMoved = 1;
    return;
}

public boolean mouseReleased(client.input.MouseButtonEvent click) {
    if (this.dragCategory <0) goto L74;
    if (this.dragMoved !=0) goto L61;
    if (config.Features.canSwitch(((ui.Category)this.categories.get(this.dragCategory)).labelKey()) ==0) goto L61;
    config.Features.toggle(((ui.Category)this.categories.get(this.dragCategory)).labelKey());
    L61:
    this.dragCategory = m1;
    this.dragMoved = 0;
    config.ConfigManager.save();
    L74:
    this.active().mouseReleased();
    if (cmp(this.builtForScale,this.configuredScale()) ==0) goto L97;
    this.layout();
    L97:
    return this.mouseReleased(click);
}

public boolean mouseScrolled(double mouseX, double a2, double mouseY, double a4) {
    v9 = this.toLocal(mouseX);
    v11 = this.toLocal(mouseY);
    if (this.overSidebar(v9, v11) ==0) goto L65;
    v13 = this.maxSidebarScroll();
    if (v13 <=0) goto L65;
    this.sidebarScroll = Math.max(0, Math.min(v13, (this.sidebarScroll - (((i)Math.signum(v7)) * 30))));
    return 1;
    L65:
    if (this.active().mouseScrolled(v9, v11, v7) ==0) goto L83;
    return 1;
    L83:
    return this.mouseScrolled(mouseX, mouseY, v5, v7);
}

public boolean keyPressed(client.input.KeyEvent input) {
    if (this.active().isCapturingKeys() ==0) goto L34;
    if (this.active().keyPressed(input.key(), input.keycode(), input.modifiers()) ==0) goto L34;
    return 1;
    L34:
    if (ui.FwmTutorial.keyPressed(input.key()) ==0) goto L46;
    return 1;
    L46:
    if (this.menuKeyArmed ==0) goto L83;
    if (ui.FwmScreen.isMenuKey(input) ==0) goto L83;
    if (this.active().isCapturingKeys() !=0) goto L83;
    if (this.typingInField() !=0) goto L83;
    this.onClose();
    return 1;
    L83:
    if (input.key() != 41) goto L130;
    if (this.subs.isEmpty() !=0) goto L110;
    this.closeSub();
    return 1;
    L110:
    if (this.selected ==0) goto L124;
    this.select(0);
    return 1;
    L124:
    this.onClose();
    return 1;
    L130:
    if (this.active().keyPressed(input.key(), input.keycode(), input.modifiers()) ==0) goto L154;
    return 1;
    L154:
    return this.keyPressed(input);
}

private static boolean menuKeyHeld() {
    v0 = input.Keybinds.OPEN_MENU;
    if (v0 != null) goto L10;
    return 0;
    L10:
    v1 = blaze3d.platform.InputConstants.getKey(v0.saveString());
    if (v1 == null) goto L32;
    if (v1.getType() == blaze3d.platform.InputConstants$Type.KEYBOARD) goto L34;
    L32:
    return 0;
    L34:
    return blaze3d.platform.InputConstants.isKeyDown(v1.getValue());
}

private static boolean isMenuKey(client.input.KeyEvent input) {
    v1 = input.Keybinds.OPEN_MENU;
    if (v1 == null) goto L20;
    if (v1.matches(input) ==0) goto L20;
    goto L21;
    L20:
    L21:
    return 0;
}

private boolean typingInField() {
    v1 = this.children().iterator();
    L10:
    if (v1.hasNext() ==0) goto L50;
    v2 = v1.next();
    if ((v2 instanceof client.gui.components.EditBox) ==0) goto L47;
    v3 = ((client.gui.components.EditBox)v2);
    if (v3.isFocused() ==0) goto L47;
    return 1;
    L47:
    goto L10;
    L50:
    return 0;
}

public boolean keyReleased(client.input.KeyEvent input) {
    if (ui.FwmScreen.isMenuKey(input) ==0) goto L12;
    this.menuKeyArmed = 1;
    L12:
    return this.keyReleased(input);
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.active().charTyped(input) ==0) goto L13;
    return 1;
    L13:
    return this.charTyped(input);
}

public void onClose() {
    L0:
    if (this.subs.isEmpty() !=0) goto L19;
    this.closeSub();
    goto L0;
    L19:
    if (this.base == null) goto L33;
    this.base.onHidden();
    L33:
    config.FwmConfig.INSTANCE.clamp();
    config.ConfigManager.save();
    antileak.AntiLeak.sync(this.minecraft);
    if (this.minecraft == null) goto L71;
    this.minecraft.gui.setScreen(this.parent);
    L71:
    return;
}

public boolean isPauseScreen() {
    return 0;
}

private int lambda$orderedIndices$0(config.FwmConfig config, Integer a, Integer b) {
    return Integer.compare(this.rank(config, a.intValue()), this.rank(config, b.intValue()));
}

private void lambda$init$13() {
    return ?;
}

private void lambda$init$14() {
    return ?;
}

private void lambda$init$12() {
    return ?;
}

private void lambda$init$11() {
    return ?;
}

private void lambda$init$10() {
    return ?;
}

private void lambda$init$9() {
    return ?;
}

private void lambda$init$8() {
    return ?;
}

private void lambda$init$7() {
    return ?;
}

private void lambda$init$6() {
    return ?;
}

private void lambda$init$5() {
    return ?;
}

private void lambda$init$1() {
    return ?;
}

private void lambda$init$4() {
    return ?;
}

private void lambda$init$3() {
    return ?;
}

private void lambda$init$2() {
    return ?;
}

private void lambda$init$0() {
    return ?;
}

static List <clinit>() {
    ui.FwmScreen.ALWAYS_IN_SIDEBAR = List.of("fwm.tab.settings", "fwm.tab.spawner", "fwm.tab.members");
    return;
}

