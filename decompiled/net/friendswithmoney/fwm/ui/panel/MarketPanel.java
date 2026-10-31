// class net/friendswithmoney/fwm/ui/panel/MarketPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field SEARCH_H I;
field HEADER_H I;
field ROW_H I;
field STATUS_H I;
field SCROLLBAR_W I;
field ICON I;
field UP I;
field DOWN I;
field PRICE_SAMPLE Ljava/lang/String;;
field NAME_MIN I;
field TYPE_PAUSE_MS J;
field PREFETCH_ROWS I;
field ICONS Ljava/util/Map;;
field host Lnet/friendswithmoney/fwm/ui/panel/PanelHost;;
field search Lnet/minecraft/client/gui/components/EditBox;;
field pendingQuery Ljava/lang/String;;
field pendingAt J;
field listY I;
field listH I;
field rowsVisible I;
field scroll I;
field draggingBar Z;
field grabOffset I;
field BAR_GRIP I;
field pickForPing Z;
field REFRESH I;
public void <init>(ui.panel.PanelHost host) {
    return;
}

public void <init>(ui.panel.PanelHost host, boolean pickForPing) {
    this.host = host;
    this.pickForPing = pickForPing;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.market", new Object[0]);
}

protected void onResize() {
    this.listY = (((this.y + 15) + 2) + 10);
    this.listH = Math.max(18, ((((this.y + this.height) - 13) - 2) - this.listY));
    this.rowsVisible = Math.max(1, (this.listH / 18));
    if (this.search != null) goto L72;
    goto L79;
    L72:
    L79:
    v1 = this.search.getValue();
    if (this.search == null) goto L100;
    this.host.detach(this.search);
    L100:
    v2 = client.Minecraft.getInstance().font;
    this.search = ?;
    this.search.setBordered(0);
    this.search.setMaxLength(32);
    this.search.setTextColor(ui.FwmTheme.TEXT);
    this.search.setHint(ui.Lang.of("fwm.market.search", new Object[0]));
    this.search.setValue(v1);
    this.search.setResponder(/*indy*/accept(this));
    this.host.attach(this.search);
    net.Market.openPage();
    return;
}

private List items() {
    return net.Market.items();
}

private int maxScroll() {
    return Math.max(0, (this.items().size() - this.rowsVisible));
}

static network.chat.MutableComponent nameFor(net.MarketData$Item item) {
    v1 = "de".equalsIgnoreCase(config.FwmConfig.INSTANCE.language);
    if (item.displayName == null) goto L33;
    if (item.displayName.isBlank() !=0) goto L33;
    goto L34;
    L33:
    L34:
    v2 = 0;
    if (v1 ==0) goto L51;
    if (v2 ==0) goto L51;
    return network.chat.Component.literal(item.displayName);
    L51:
    v3 = ui.panel.MarketPanel.iconFor(item);
    if (v3.isEmpty() !=0) goto L115;
    v4 = v3.getHoverName().getString();
    if (item.variantKey == null) goto L91;
    if (item.variantKey.isBlank() ==0) goto L97;
    L91:
    return network.chat.Component.literal(v4);
    L97:
    return network.chat.Component.literal(v4 + ' - ' + ui.panel.MarketPanel.spell(item.variantKey));
    L115:
    if (v2 ==0) goto L127;
    return network.chat.Component.literal(item.displayName);
    L127:
    if (item.itemKey != null) goto L139;
    goto L143;
    L139:
    L143:
    return network.chat.Component.literal(item.itemKey);
}

private static String spell(String key) {
    v1 = ?;
    v2 = key.split("_");
    v3 = v2.length;
    v4 = 0;
    L21:
    if (v4 >= v3) goto L87;
    v5 = v2[v4];
    if (v5.isEmpty() ==0) goto L44;
    goto L81;
    L44:
    if (v1.length() <=0) goto L58;
    v1.append(32);
    L58:
    v1.append(Character.toUpperCase(v5.charAt(0))).append(v5.substring(1));
    L81:
    v4 = v4 + 1;
    goto L21;
    L87:
    if (v1.length() !=0) goto L98;
    goto L102;
    L98:
    L102:
    return v1.toString();
}

public static void drawIcon(client.gui.GuiGraphicsExtractor ctx, net.MarketData$Item item, int left, int top) {
    v4 = ui.panel.MarketPanel.iconFor(item);
    if (v4.isEmpty() !=0) goto L23;
    ctx.item(v4, left, top);
    return;
    L23:
    v5 = ui.WebIcons.get(item.icon);
    if (v5 == null) goto L55;
    ui.FwmGfx.blit(ctx, v5, left, top, 16, 16, 16, 16, 16);
    L55:
    return;
}

static Object iconFor(net.MarketData$Item item) {
    if (item.baseItemKey != null) goto L14;
    goto L18;
    L14:
    L18:
    v1 = item.baseItemKey;
    if (v1 == null) goto L30;
    if (v1.isBlank() ==0) goto L34;
    L30:
    return world.item.ItemStack.EMPTY;
    L34:
    v2 = ((world.item.ItemStack)ui.panel.MarketPanel.ICONS.get(v1));
    if (v2 == null) goto L53;
    return v2;
    L53:
    v3 = world.item.ItemStack.EMPTY;
    if (v1.contains(":") ==0) goto L71;
    goto L77;
    L71:
    L77:
    v4 = resources.Identifier.tryParse('minecraft:' + v1);
    if (v4 == null) goto L115;
    v3 = ((world.item.ItemStack)core.registries.BuiltInRegistries.ITEM.getOptional(v4).map(/*indy*/apply()).orElse(world.item.ItemStack.EMPTY));
    L115:
    goto L124;
    v4 = /*catch Exception*/ ex;
    v3 = world.item.ItemStack.EMPTY;
    L124:
    ui.panel.MarketPanel.ICONS.put(v1, v3);
    return v3;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.applyPendingSearch();
    if (this.search.isFocused() ==0) goto L38;
    goto L41;
    L38:
    L41:
    ui.FwmTheme.box(this.x, this.y, this.width, 15, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    this.search.extractWidgetRenderState(ctx, mouseX, mouseY, delta);
    this.renderHeader(ctx, font);
    this.renderList(ctx, font, mouseX, mouseY);
    this.renderStatus(ctx, font, mouseX, mouseY);
    return;
}

private int rowWidth() {
    if (this.maxScroll() <=0) goto L16;
    goto L17;
    L16:
    L17:
    return (6 - 0);
}

private void renderHeader(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v3 = this.rowWidth();
    v4 = ((this.y + 15) + 2);
    v5 = ui.FwmTheme.TEXT_DIM;
    v6 = ' ' + ui.Lang.str("fwm.market.colPrice", new Object[0]);
    v7 = ((this.x + v3) - 4);
    v8 = (v7 - font.width(v6));
    ctx.text(font, network.chat.Component.literal(v6), v8, v4, v5, 0);
    ui.panel.MarketPanel.drawAverageSign(ctx, font, (v8 - font.width("O")), v4, v5);
    v9 = ui.Lang.str("fwm.market.colChange", new Object[0]);
    v10 = ((((this.x + v3) - 4) - font.width("$888.8M")) - 6);
    ctx.text(font, network.chat.Component.literal(v9), (v10 - font.width(v9)), v4, v5, 0);
    return;
}

private static void drawAverageSign(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int left, int top, int color) {
    ctx.text(font, network.chat.Component.literal("O"), left, top, color, 0);
    v5 = (font.width("O") - 1);
    v6 = 0;
    L30:
    if (v6 > 8) goto L87;
    v7 = ((left + v5) - Math.round((((f)(v6 * (v5 + 1))) / 8.0)));
    ctx.fill(v7, ((top - 1) + v6), (v7 + 1), (top + v6), color);
    v6 = v6 + 1;
    goto L30;
    L87:
    return;
}

private void applyPendingSearch() {
    if (this.pendingQuery != null) goto L8;
    return;
    L8:
    if (cmp((System.currentTimeMillis() - this.pendingAt),300) >=0) goto L24;
    return;
    L24:
    net.Market.search(this.pendingQuery);
    this.pendingQuery = null;
    this.scroll = 0;
    return;
}

private void renderList(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = this.items();
    if (v5.isEmpty() ==0) goto L72;
    v6 = ui.Lang.of(this.emptyKey(), new Object[0]);
    ctx.text(font, v6, (this.x + ((this.width - font.width(v6)) / 2)), ((this.listY + (this.listH / 2)) - 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L72:
    this.scroll = Math.min(this.scroll, this.maxScroll());
    ctx.enableScissor(this.x, this.listY, (this.x + this.width), (this.listY + this.listH));
    v6 = 0;
    L120:
    if (v6 >= (this.rowsVisible + 1)) goto L194;
    v7 = (this.scroll + v6);
    if (v7 < v5.size()) goto L155;
    goto L194;
    L155:
    this.renderRow(ctx, font, ((net.MarketData$Item)v5.get(v7)), v7, (this.listY + (v6 * 18)), mouseX, mouseY);
    v6 = v6 + 1;
    goto L120;
    L194:
    ctx.disableScissor();
    this.renderScrollbar(ctx);
    if (net.Market.hasMore() ==0) goto L232;
    if (net.Market.busy() !=0) goto L232;
    if (v5.size() > this.rowsVisible) goto L232;
    net.Market.loadMore();
    L232:
    return;
}

private boolean emptyKey() {
    if (net.Market.busy() ==0) goto L10;
    return "fwm.market.loading";
    L10:
    if (net.Market.state() != net.Market$State.ERROR) goto L23;
    return "fwm.market.offline";
    L23:
    if (net.Market.query().isBlank() !=0) goto L36;
    return "fwm.items.noMatch";
    L36:
    return "fwm.market.loading";
}

private void renderRow(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, net.MarketData$Item item, int index, int rowY, int mouseX, int mouseY) {
    v8 = this.rowWidth();
    if (mouseX < this.x) goto L71;
    if (mouseX >= (this.x + v8)) goto L71;
    if (mouseY < rowY) goto L71;
    if (mouseY >= (rowY + 18)) goto L71;
    if (mouseY < this.listY) goto L71;
    if (mouseY >= (this.listY + this.listH)) goto L71;
    goto L72;
    L71:
    L72:
    v9 = 0;
    if (v9 ==0) goto L104;
    ctx.fill(this.x, rowY, (this.x + v8), (rowY + 18), ui.FwmTheme.HOVER);
    L104:
    ui.panel.MarketPanel.drawIcon(ctx, item, (this.x + 2), (rowY + 1));
    Objects.requireNonNull(font);
    v10 = ((rowY + ((18 - 9) / 2)) + 1);
    v11 = (((this.x + 2) + 16) + 4);
    v12 = this.priceText(item);
    if (net.Market.avgPrice(item.itemKey) == null) goto L172;
    goto L173;
    L172:
    L173:
    v13 = 0;
    if (v9 ==0) goto L211;
    goto L225;
    L211:
    if (v13 ==0) goto L222;
    goto L225;
    L222:
    L225:
    network.chat.Component.literal(v12).text((((this.x + v8) - 4) - font.width(v12)), v10, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    v14 = Math.max(font.width(v12), font.width("$888.8M"));
    v15 = ((((this.x + v8) - 4) - v14) - 6);
    v16 = ((v15 - 4) - v11);
    v17 = net.Market.change(item.itemKey);
    if (v17 == null) goto L352;
    v18 = ui.panel.MarketPanel.changeText(v17.doubleValue());
    v19 = (v15 - font.width(v18));
    if (((v19 - 4) - v11) < 40) goto L352;
    ctx.text(font, network.chat.Component.literal(v18), v19, v10, ui.panel.MarketPanel.changeColor(v17.doubleValue()), 0);
    v16 = ((v19 - 4) - v11);
    L352:
    v18 = network.chat.Component.literal(font.plainSubstrByWidth(ui.panel.MarketPanel.nameFor(item).getString(), Math.max(8, v16)));
    ctx.text(font, v18, v11, v10, ui.FwmTheme.TEXT, 0);
    ui.FwmTheme.divider(ctx, this.x, ((rowY + 18) - 1), v8);
    return;
}

static String changeText(double change) {
    if (cmp(Math.abs(change),100.0) <0) goto L21;
    goto L41;
    L21:
    new Object[1][0] = Double.valueOf(change);
    L41:
    v2 = String.format(Locale.ROOT, "%.1f", new Object[1]);
    if (cmp(change,0) <=0) goto L54;
    goto L57;
    L54:
    L57:
    return "" + v2 + '%';
}

static int changeColor(double change) {
    if (cmp(change,0) <=0) goto L10;
    return -12723068;
    L10:
    if (cmp(change,0) >=0) goto L20;
    return -1547164;
    L20:
    return ui.FwmTheme.TEXT_DIM;
}

private String priceText(net.MarketData$Item item) {
    v2 = net.Market.avgPrice(item.itemKey);
    if (v2 != null) goto L18;
    goto L33;
    L18:
    L33:
    return '$' + util.Fmt.compactUp(Math.round(v2.doubleValue()));
}

private void renderScrollbar(client.gui.GuiGraphicsExtractor ctx) {
    if (this.maxScroll() >0) goto L8;
    return;
    L8:
    v2 = ((this.x + this.width) - 4);
    ctx.fill(v2, this.listY, (v2 + 4), (this.listY + this.listH), ui.FwmTheme.OFF);
    v3 = this.thumbH();
    v4 = this.thumbY(v3);
    ctx.fill(v2, v4, (v2 + 4), (v4 + v3), ui.FwmTheme.ACCENT);
    return;
}

private int thumbH() {
    return Math.max(12, ((this.listH * this.rowsVisible) / Math.max(1, this.items().size())));
}

private int thumbY(int thumbH) {
    return (this.listY + (((this.listH - thumbH) * this.scroll) / Math.max(1, this.maxScroll())));
}

private int overBar(double mouseX, double a2) {
    if (this.maxScroll() <=0) goto L70;
    if (cmp(mouseX,((d)(((this.x + this.width) - 4) - 3))) <0) goto L70;
    if (cmp(mouseX,((d)(this.x + this.width))) >=0) goto L70;
    if (cmp(v3,((d)this.listY)) <0) goto L70;
    if (cmp(v3,((d)(this.listY + this.listH))) >=0) goto L70;
    goto L71;
    L70:
    L71:
    return 0;
}

private void dragTo(double mouseY) {
    v3 = this.thumbH();
    v4 = Math.max(1, (this.listH - v3));
    v5 = Math.max(0, Math.min(((d)v4), ((mouseY - ((d)this.grabOffset)) - ((d)this.listY))));
    this.scroll = ((i)Math.round(((v5 / ((d)v4)) * ((d)this.maxScroll()))));
    this.prefetch();
    return;
}

private void prefetch() {
    if (net.Market.hasMore() ==0) goto L32;
    if (((this.scroll + this.rowsVisible) + 4) < this.items().size()) goto L32;
    net.Market.loadMore();
    L32:
    return;
}

private void renderStatus(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY) {
    v5 = ((this.y + this.height) - 13);
    ui.FwmTheme.divider(ctx, this.x, (v5 - 1), this.width);
    Objects.requireNonNull(font);
    v6 = ((v5 + ((13 - 9) / 2)) + 1);
    new Object[2][0] = Integer.valueOf(this.items().size());
    new Object[2][1] = Integer.valueOf(net.Market.totalItems());
    v7 = ui.Lang.of("fwm.market.count", new Object[2]);
    ctx.text(font, v7, (this.x + 4), v6, ui.FwmTheme.TEXT_DIM, 0);
    v8 = this.statusRight();
    if (net.Market.state() != net.Market$State.ERROR) goto L144;
    goto L147;
    L144:
    L147:
    font.text(v8, ((this.refreshX() - 4) - font.width(v8)), v6, -2056032, ui.FwmTheme.TEXT_DIM, 0);
    if (this.overRefresh(((d)mouseX), ((d)mouseY)) ==0) goto L173;
    if (net.Market.busy() !=0) goto L173;
    goto L174;
    L173:
    L174:
    v9 = 0;
    v10 = this.refreshY();
    if (v9 ==0) goto L207;
    goto L210;
    L207:
    L210:
    ui.FwmTheme.box(this.refreshX(), v10, 11, 11, ui.FwmTheme.RAISED, ui.FwmTheme.ACCENT, ui.FwmTheme.OFF);
    if (net.Market.busy() ==0) goto L241;
    goto L255;
    L241:
    if (v9 ==0) goto L252;
    goto L255;
    L252:
    L255:
    ui.SharpIcons.draw((this.refreshX() + 1), (v10 + 1), 9, ui.FwmTheme.TEXT_DIM, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT);
    return;
}

private int refreshX() {
    return (((this.x + this.width) - 11) - 1);
}

private int refreshY() {
    return (((this.y + this.height) - 13) + 1);
}

private int overRefresh(double mouseX, double a2) {
    if (cmp(mouseX,((d)this.refreshX())) <0) goto L50;
    if (cmp(mouseX,((d)(this.refreshX() + 11))) >=0) goto L50;
    if (cmp(v3,((d)this.refreshY())) <0) goto L50;
    if (cmp(v3,((d)(this.refreshY() + 11))) >=0) goto L50;
    goto L51;
    L50:
    L51:
    return 0;
}

private network.chat.MutableComponent statusRight() {
    if (net.Market.busy() ==0) goto L17;
    return ui.Lang.of("fwm.market.loading", new Object[0]);
    L17:
    if (net.Market.state() != net.Market$State.ERROR) goto L37;
    return ui.Lang.of("fwm.market.offline", new Object[0]);
    L37:
    v1 = net.Market.ageMinutes();
    if (cmp(v1,0) >=0) goto L60;
    goto L77;
    L60:
    new Object[1][0] = Long.valueOf(v1);
    L77:
    return ui.Lang.of("fwm.board.updated", new Object[1]);
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    if (cmp(mouseY,((d)(this.y + 15))) >=0) goto L39;
    this.search.setFocused(1);
    return 1;
    L39:
    this.search.setFocused(0);
    if (this.overRefresh(mouseX, mouseY) ==0) goto L61;
    net.Market.refresh();
    return 1;
    L61:
    if (this.overBar(mouseX, mouseY) ==0) goto L132;
    v6 = this.thumbH();
    v7 = this.thumbY(v6);
    if (cmp(mouseY,((d)v7)) <0) goto L113;
    if (cmp(mouseY,((d)(v7 + v6))) >=0) goto L113;
    goto L117;
    L113:
    L117:
    ((i)(mouseY - ((d)v7))).grabOffset = (v6 / 2);
    this.draggingBar = 1;
    this.dragTo(mouseY);
    return 1;
    L132:
    if (cmp(mouseY,((d)this.listY)) <0) goto L297;
    if (cmp(mouseY,((d)(this.listY + this.listH))) >=0) goto L297;
    v6 = (this.scroll + ((i)((mouseY - ((d)this.listY)) / 18.0)));
    v7 = this.items();
    if (v6 <0) goto L297;
    if (v6 >= v7.size()) goto L297;
    v8 = ((net.MarketData$Item)v7.get(v6));
    if (this.pickForPing ==0) goto L275;
    this.host.closeSub();
    if (v8.displayName != null) goto L255;
    goto L260;
    L255:
    L260:
    ?.openSub(?);
    goto L297;
    L275:
    this.host.openSub(?);
    L297:
    return 1;
}

public void mouseScrolled(double mouseX, double a2, double mouseY) {
    this.scroll = Math.max(0, Math.min(this.maxScroll(), (this.scroll - (((i)Math.signum(v5)) * 3))));
    this.prefetch();
    return 1;
}

public void mouseDragged(double mouseX, double a2, int mouseY) {
    if (this.draggingBar !=0) goto L9;
    return 0;
    L9:
    this.dragTo(mouseY);
    return 1;
}

public void mouseReleased() {
    this.draggingBar = 0;
    return;
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    if (this.search.isFocused() ==0) goto L34;
    if (this.search.keyPressed(?) ==0) goto L34;
    goto L35;
    L34:
    L35:
    return 0;
}

public boolean charTyped(client.input.CharacterEvent input) {
    if (this.search.isFocused() ==0) goto L25;
    if (this.search.charTyped(input) ==0) goto L25;
    goto L26;
    L25:
    L26:
    return 0;
}

public void onHidden() {
    if (this.search == null) goto L20;
    this.host.detach(this.search);
    L20:
    return;
}

private long lambda$onResize$0(String text) {
    this.pendingQuery = text;
    this.pendingAt = System.currentTimeMillis();
    return;
}

static void <clinit>() {
    ui.panel.MarketPanel.ICONS = ?;
    return;
}

