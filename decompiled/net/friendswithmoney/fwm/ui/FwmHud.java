// class net/friendswithmoney/fwm/ui/FwmHud  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field ID Lnet/minecraft/resources/Identifier;;
field FAREWELL_MS J;
field FLARE_MS J;
field WARN_ICON I;
field WARN_CARD_H I;
field WARN_SCALE F;
field MARGIN I;
field PADDING I;
field GAP I;
field LOGO_H I;
field CARD_H I;
field LINE_H I;
field STACK_GAP I;
field TOAST_MS J;
field TOAST_TEXT_W I;
field CHAT_RESERVE I;
field TOAST_TOP I;
field ENVELOPE_TOP I;
field TPA_TOP I;
field MINIMAP_TOP I;
field PRICEPING_TOP I;
field PRICEPING_MS J;
field pingAt J;
field pingItem Lnet/friendswithmoney/fwm/net/MarketData$Item;;
field pingName Ljava/lang/String;;
field pingPrice Ljava/lang/String;;
field pingSeller Ljava/lang/String;;
field pingAmount J;
field pingOrder Z;
field TPA_FLARE_S I;
field TPA_SOON I;
field ENVELOPE_W I;
field ENVELOPE_H I;
field SCALE F;
field switchedOffAt J;
field blockedAt J;
field greetAt J;
field GREET_MS J;
field ID_MACRO Ljava/lang/String;;
field ID_TOAST Ljava/lang/String;;
field ID_ENVELOPE Ljava/lang/String;;
field ID_TOOL Ljava/lang/String;;
field ID_SECOND_CHAT Ljava/lang/String;;
field ID_TPA Ljava/lang/String;;
field ID_MINIMAP Ljava/lang/String;;
field ID_PRICEPING Ljava/lang/String;;
field ID_MONEY Ljava/lang/String;;
field ID_INVVALUE Ljava/lang/String;;
field ID_AUCTION Ljava/lang/String;;
field ID_SPAWNERS Ljava/lang/String;;
field MONEY_TOP I;
field bounds Ljava/util/Map;;
field FIGURE Lnet/minecraft/resources/Identifier;;
field FIGURE_W I;
field FIGURE_H I;
field FIGURE_SHEET_W I;
field FIGURE_SHEET_H I;
field FIGURE_RATIO F;
field chatHits Ljava/util/List;;
field chatScroll I;
field chatMaxScroll I;
field chatWrappedCount I;
field SCROLLBAR_W I;
field SCROLLBAR_GAP I;
field AUCTION_TOP I;
field AUCTION_W I;
field AUCTION_SOLD_MS J;
field auctionBids I;
field auctionFlashUntil J;
field auctionEndedAt J;
field auctionWasRunning Z;
field SPAWNERS_TOP I;
field toastFrom Ljava/lang/String;;
field toastText Ljava/lang/String;;
field toastAt J;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.rendering.v1.hud.HudElementRegistry.attachElementAfter(fabric.api.client.rendering.v1.hud.VanillaHudElements.MISC_OVERLAYS, ui.FwmHud.ID, /*indy*/extractRenderState());
    return;
}

public static long showDeactivated() {
    ui.FwmHud.switchedOffAt = System.currentTimeMillis();
    return;
}

public static long showBlocked() {
    ui.FwmHud.blockedAt = System.currentTimeMillis();
    return;
}

public static long showGreeting() {
    ui.FwmHud.greetAt = System.currentTimeMillis();
    return;
}

public static void bounds() {
    return ?;
}

// DECOMPILE ERROR in spot: 'dict' object has no attribute 'op_type'
public static float scaleOf(String id) {
    if ("macro".equals(id) ==0) goto L13;
    goto L20;
    L13:
    L20:
    return ui.FwmHud.spot(id).scale();
}

private static boolean place(client.gui.GuiGraphicsExtractor ctx, String id, int w, int h, int stackX, int stackTop) {
    v6 = ui.FwmHud.spot(id);
    v7 = stackX;
    v8 = stackTop;
    if (v6.moved() ==0) goto L66;
    v7 = Math.round((v6.x * ((f)Math.max(0, (ctx.guiWidth() - w)))));
    v8 = Math.round((v6.y * ((f)Math.max(0, (ctx.guiHeight() - h)))));
    L66:
    new int[4][0] = v7;
    new int[4][1] = v8;
    new int[4][2] = w;
    new int[4][3] = h;
    ui.FwmHud.bounds.put(id, new int[4]);
    new int[3][0] = v7;
    new int[3][1] = v8;
    if (v6.moved() ==0) goto L124;
    goto L127;
    L124:
    L127:
    2[0] = (h + 4);
    return new int[3];
}

private static void render(client.gui.GuiGraphicsExtractor ctx) {
    v1 = client.Minecraft.getInstance();
    if (v1.gui.hud.isHidden() ==0) goto L18;
    return;
    L18:
    ui.FwmHud.drawMacroCard(ctx, v1, 6);
    ui.FwmHud.drawMessage(ctx, v1, 64);
    ui.FwmHud.drawEnvelope(ctx, v1, 96);
    ui.FwmHud.drawToolWarning(ctx, v1);
    ui.FwmHud.drawTpa(ctx, v1);
    ui.FwmHud.drawMinimap(ctx, v1);
    ui.FwmHud.drawPricePing(ctx, v1);
    v2 = ui.FwmHud.drawMoney(ctx, v1);
    ui.FwmHud.drawInvValue(ctx, v1, v2);
    ui.FwmHud.drawAuction(ctx, v1);
    ui.FwmHud.drawSpawnerPrices(ctx, v1);
    ui.FwmHud.drawSecondChat(ctx, v1);
    ui.FwmHud.drawGreeting(ctx, v1);
    v3 = ui.FwmHud.drawNews(ctx, v1);
    ui.FwmHud.drawGiveawayCard(ctx, v1, v3);
    return;
}

private static void drawGiveawayCard(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, int top) {
    v3 = ui.FwmNotice.current();
    if (v3 != null) goto L9;
    return;
    L9:
    v4 = client.font;
    v5 = v3.color();
    v6 = 170;
    v7 = network.chat.Component.literal(v3.head()).withStyle(ChatFormatting.BOLD);
    v8 = v3.title();
    if (v4.width(network.chat.Component.literal(v8).withStyle(ChatFormatting.BOLD)) <= v6) goto L129;
    L68:
    if (v8.isEmpty() !=0) goto L120;
    if (v4.width(network.chat.Component.literal(v8 + '…').withStyle(ChatFormatting.BOLD)) <= v6) goto L120;
    v8 = v8.substring(0, (v8.length() - 1));
    goto L68;
    L120:
    v8 = v8 + '…';
    L129:
    v9 = network.chat.Component.literal(v8).withStyle(ChatFormatting.BOLD);
    v10 = v4.split(network.chat.Component.literal(v3.line()), v6);
    if (v10.size() <= 3) goto L180;
    v10 = v10.subList(0, 3);
    L180:
    v11 = ((24 + v6) + 5);
    if (v8.isEmpty() ==0) goto L204;
    goto L205;
    L204:
    L205:
    v12 = (((11 + (1 * (2 + v10.size()))) + 5) + 2);
    v13 = ((ctx.guiWidth() - v11) - 6);
    v14 = top;
    ui.LauncherGfx.card(ctx, v13, v14, v11, v12, 6);
    v15 = (v13 + 5);
    v16 = (v14 + 5);
    ui.LauncherGfx.round(ctx, v15, v16, 14, 14, 4, (855638016 | (v5 & 16777215)));
    ui.LauncherGfx.ring(ctx, v15, v16, 14, 14, 4, v5);
    ui.LauncherGfx.infoGlyph(ctx, v15, v16, v5);
    v17 = (((v13 + 5) + 14) + 5);
    v18 = (v14 + 5);
    ctx.text(v4, v7, v17, v18, v5);
    if (v8.isEmpty() !=0) goto L367;
    v18 = v18 + 11;
    ctx.text(v4, v9, v17, v18, ui.FwmTheme.TEXT);
    L367:
    v19 = 0;
    L370:
    if (v19 >= v10.size()) goto L421;
    ctx.text(v4, ((util.FormattedCharSequence)v10.get(v19)), v17, (v18 + (11 * (1 + v19))), ui.FwmTheme.TEXT_DIM);
    v19 = v19 + 1;
    goto L370;
    L421:
    v19 = (1 - ui.FwmNotice.progress());
    ctx.fill((v13 + 6), ((v14 + v12) - 3), ((v13 + 6) + Math.round((((f)(v11 - 12)) * v19))), ((v14 + v12) - 2), v5);
    return;
}

private static void drawNews(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    v2 = net.ModNews.card();
    if (v2 != null) goto L11;
    return 6;
    L11:
    v3 = client.font;
    v4 = 170;
    v5 = ui.Lang.of("fwm.news.card", new Object[0]).withStyle(ChatFormatting.BOLD);
    if (v2.title != null) goto L52;
    goto L56;
    L52:
    L56:
    v6 = network.chat.Component.literal(v2.title).withStyle(ChatFormatting.BOLD);
    if (v2.text != null) goto L81;
    goto L85;
    L81:
    L85:
    v7 = "".split(network.chat.Component.literal(v2.text), v4);
    if (v7.size() <= 6) goto L119;
    v7 = v7.subList(0, 6);
    L119:
    v8 = ((24 + v4) + 5);
    v9 = (((5 + (11 * (2 + v7.size()))) + 5) + 2);
    v10 = ((ctx.guiWidth() - v8) - 6);
    v11 = 6;
    ui.LauncherGfx.card(ctx, v10, v11, v8, v9, 6);
    ui.LauncherGfx.round(ctx, (v10 + 5), (v11 + 5), 14, 14, 4, 872415231);
    ui.LauncherGfx.ring(ctx, (v10 + 5), (v11 + 5), 14, 14, 4, ui.FwmTheme.ACCENT);
    ui.LauncherGfx.infoGlyph(ctx, (v10 + 5), (v11 + 5), ui.FwmTheme.ACCENT);
    v12 = (((v10 + 5) + 14) + 5);
    v13 = (v11 + 5);
    ctx.text(v3, v5, v12, v13, ui.FwmTheme.ACCENT);
    ctx.text(v3, v6, v12, (v13 + 11), ui.FwmTheme.TEXT);
    v14 = 0;
    L284:
    if (v14 >= v7.size()) goto L334;
    ctx.text(v3, ((util.FormattedCharSequence)v7.get(v14)), v12, (v13 + (11 * (2 + v14))), ui.FwmTheme.TEXT_DIM);
    v14 = v14 + 1;
    goto L284;
    L334:
    v14 = (1 - net.ModNews.cardProgress());
    ctx.fill((v10 + 6), ((v11 + v9) - 3), ((v10 + 6) + Math.round((((f)(v8 - 12)) * v14))), ((v11 + v9) - 2), ui.FwmTheme.ACCENT);
    return ((v11 + v9) + 4);
}

private static void drawGreeting(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (cmp(ui.FwmHud.greetAt,0) !=0) goto L9;
    return;
    L9:
    v2 = (System.currentTimeMillis() - ui.FwmHud.greetAt);
    if (cmp(v2,30000) <0) goto L26;
    return;
    L26:
    v4 = client.font;
    v5 = ui.Lang.of("fwm.tutorial.greet", new Object[0]).withStyle(ChatFormatting.BOLD);
    new Object[1][0] = input.Keybinds.menuKeyName();
    v6 = ui.Lang.of("fwm.tutorial.greetSub", new Object[1]);
    v7 = 76;
    v8 = Math.max(1, Math.round((((f)v7) * 0.5140562057495117)));
    v9 = Math.max(v4.width(v5), v4.width(v6));
    v10 = ((5 + v9) + 5);
    v11 = 32;
    v12 = Math.min(1, (((f)v2) / 200.0));
    v13 = Math.min(1, Math.max(0, (((f)(30000 - v2)) / 300.0)));
    v14 = ui.widget.Anim.ease(Math.min(v12, v13));
    v15 = (ctx.guiHeight() - 6);
    v16 = Math.round((((f)ctx.guiWidth()) - (v14 * ((f)(v8 + 6)))));
    v17 = (v15 - v7);
    ctx.blit(client.renderer.RenderPipelines.GUI_TEXTURED, ui.FwmHud.FIGURE, v16, v17, 256.0, 0, v8, v7, -256, 498, 256, 512);
    v18 = ((v16 - 5) - v10);
    v19 = ((v15 - v11) - 6);
    ui.FwmTheme.panel(ctx, v18, v19, v10, v11);
    ctx.text(v4, v5, (v18 + 5), (v19 + 5), ui.FwmTheme.ACCENT);
    ctx.text(v4, v6, (v18 + 5), ((v19 + 5) + 11), ui.FwmTheme.TEXT);
    return;
}

private static int backingColour() {
    v0 = config.FwmConfig.INSTANCE;
    if (v0.secondChatBackground !=0) goto L13;
    return 0;
    L13:
    v1 = Math.max(0, Math.min(100, v0.secondChatBackgroundOpacity));
    v2 = Math.round((((f)(255 * v1)) / 100.0));
    if (v2 !=0) goto L49;
    goto L53;
    L49:
    L53:
    return (v2 << 24);
}

public static void chatHits() {
    return ?;
}

public static int scrollSecondChat(double mouseX, double a1, double mouseY) {
    if (config.Features.isOn("fwm.tab.secondchat") ==0) goto L16;
    if (cmp(v4,0) !=0) goto L18;
    L16:
    return 0;
    L18:
    if (ui.FwmHud.chatHits.isEmpty() !=0) goto L35;
    if (ui.FwmHud.chatMaxScroll >0) goto L37;
    L35:
    return 0;
    L37:
    v6 = (([I)ui.FwmHud.bounds.get("secondchat"));
    if (v6 != null) goto L59;
    return 0;
    L59:
    if (cmp(mouseX,((d)v6[0])) <0) goto L88;
    if (cmp(mouseX,((d)(((v6[0] + v6[2]) + 2) + 2))) <0) goto L90;
    L88:
    return 0;
    L90:
    if (cmp(mouseY,((d)v6[1])) <0) goto L115;
    if (cmp(mouseY,((d)(v6[1] + v6[3]))) <0) goto L117;
    L115:
    return 0;
    L117:
    v7 = ((i)Math.round(v4));
    if (v7 !=0) goto L144;
    if (cmp(v4,0) <=0) goto L141;
    goto L142;
    L141:
    L142:
    v7 = m1;
    L144:
    ui.FwmHud.chatScroll = Math.max(0, Math.min(ui.FwmHud.chatMaxScroll, (ui.FwmHud.chatScroll + v7)));
    return 1;
}

private static void drawSecondChat(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    ui.FwmHud.chatHits.clear();
    ui.FwmHud.chatMaxScroll = 0;
    if (config.Features.isOn("fwm.tab.secondchat") !=0) goto L22;
    return;
    L22:
    v2 = chat.SecondChat.lines();
    if (v2.isEmpty() ==0) goto L36;
    return;
    L36:
    v3 = client.font;
    v4 = System.currentTimeMillis();
    v6 = (client.gui.screen() instanceof client.gui.screens.ChatScreen);
    v7 = ?;
    v8 = v2.iterator();
    L75:
    if (v8.hasNext() ==0) goto L151;
    v9 = ((chat.SecondChat$Line)v8.next());
    if (v6 !=0) goto L120;
    if (cmp((v4 - v9.at()),10000) <=0) goto L120;
    goto L75;
    L120:
    v3.getSplitter().splitLines(seventv.SevenTv.transform(v9.text()), ui.FwmHud.chatWidth(), network.chat.Style.EMPTY, /*indy*/accept(v7));
    goto L75;
    L151:
    if (v7.isEmpty() ==0) goto L162;
    return;
    L162:
    v8 = config.FwmConfig.INSTANCE.secondChatLines;
    if (v6 !=0) goto L182;
    ui.FwmHud.chatScroll = 0;
    goto L219;
    L182:
    if (ui.FwmHud.chatScroll <=0) goto L219;
    if (v7.size() <= ui.FwmHud.chatWrappedCount) goto L219;
    ui.FwmHud.chatScroll = (ui.FwmHud.chatScroll + (v7.size() - ui.FwmHud.chatWrappedCount));
    L219:
    ui.FwmHud.chatWrappedCount = v7.size();
    if (v6 ==0) goto L251;
    goto L252;
    L251:
    L252:
    ui.FwmHud.chatMaxScroll = 0;
    ui.FwmHud.chatScroll = Math.max(0, Math.min(ui.FwmHud.chatMaxScroll, ui.FwmHud.chatScroll));
    v9 = (v7.size() - ui.FwmHud.chatScroll);
    v10 = v7.subList(Math.max(0, (v9 - v8)), v9);
    v11 = ui.FwmHud.scaleOf("secondchat");
    v12 = ui.FwmHud.chatWidth();
    v13 = Math.round((((f)v12) * v11));
    v14 = Math.round((((f)(v10.size() * 11)) * v11));
    v15 = Math.max(6, ((ctx.guiHeight() - 54) - v14));
    v16 = Math.max(6, ((ctx.guiWidth() - 6) - v13));
    v17 = ui.FwmHud.place(ctx, "secondchat", v13, v14, v16, v15);
    ui.FwmHud.drawChatLines(ctx, v3, v10, v17[0], v17[1], v11, 1);
    if (ui.FwmHud.chatMaxScroll <=0) goto L523;
    v18 = ((v17[0] + v13) + 2);
    v19 = Math.max(4, ((v14 * v10.size()) / v7.size()));
    v20 = (v17[1] + (((v14 - v19) * (ui.FwmHud.chatMaxScroll - ui.FwmHud.chatScroll)) / ui.FwmHud.chatMaxScroll));
    ctx.fill(v18, v17[1], (v18 + 2), (v17[1] + v14), 1090519039);
    ctx.fill(v18, v20, (v18 + 2), (v20 + v19), ui.FwmTheme.ACCENT);
    L523:
    return;
}

public static int chatWidth() {
    return Math.max(40, config.FwmConfig.INSTANCE.secondChatWidth);
}

// DECOMPILE ERROR in alignX: 'dict' object has no attribute 'op_type'
private static org.joml.Matrix3x2fStack drawChatLines(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, List lines, int left, int top, float scale, boolean record) {
    v7 = ui.FwmHud.chatWidth();
    v8 = ctx.pose();
    v8.pushMatrix();
    v8.translate(((f)left), ((f)top));
    v8.scale(scale, scale);
    v9 = 0;
    v10 = ui.FwmHud.backingColour();
    v11 = lines.iterator();
    L54:
    if (v11.hasNext() ==0) goto L210;
    v12 = ((network.chat.FormattedText)v11.next());
    v13 = locale.Language.getInstance().getVisualOrder(v12);
    v14 = font.width(v12);
    v15 = ui.FwmHud.alignX(v7, v14);
    if (v10 ==0) goto L128;
    ctx.fill(0, (v9 - 1), v7, ((v9 + 11) - 1), v10);
    L128:
    ctx.text(font, v13, v15, v9, m1);
    if (record ==0) goto L204;
    ui.FwmHud.chatHits.add(?);
    L204:
    v9 = v9 + 11;
    goto L54;
    L210:
    v8.popMatrix();
    return;
}

public static void sampleLines(client.gui.Font font) {
    v1 = ?;
    new Object[1][0] = ui.FwmHud.sampleName();
    font.getSplitter().splitLines(ui.Lang.of("fwm.hudedit.sample", new Object[1]), ui.FwmHud.chatWidth(), network.chat.Style.EMPTY, /*indy*/accept(v1));
    return v1;
}

private static boolean sampleName() {
    v0 = ui.FwmGfx.playerName();
    if (v0.isEmpty() ==0) goto L17;
    goto L18;
    L17:
    L18:
    return v0;
}

public static int sampleSize(client.gui.Font font, float scale) {
    v2 = Math.max(1, ui.FwmHud.sampleLines(font).size());
    new int[2][0] = Math.round((((f)ui.FwmHud.chatWidth()) * scale));
    new int[2][1] = Math.round((((f)(v2 * 11)) * scale));
    return new int[2];
}

public static int[] drawSample(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int left, int top, float scale) {
    ui.FwmHud.drawChatLines(ctx, font, ui.FwmHud.sampleLines(font), left, top, scale, 0);
    return ui.FwmHud.sampleSize(font, scale);
}

private static void drawMacroCard(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, int top) {
    v3 = config.FwmConfig.INSTANCE.enabled;
    if (v3 !=0) goto L37;
    if (cmp(ui.FwmHud.switchedOffAt,0) <=0) goto L37;
    if (cmp((System.currentTimeMillis() - ui.FwmHud.switchedOffAt),3000) >=0) goto L37;
    goto L38;
    L37:
    L38:
    v4 = 0;
    if (v3 !=0) goto L70;
    if (cmp(ui.FwmHud.blockedAt,0) <=0) goto L70;
    if (cmp((System.currentTimeMillis() - ui.FwmHud.blockedAt),3000) >=0) goto L70;
    goto L71;
    L70:
    L71:
    v5 = 0;
    if (v3 !=0) goto L88;
    if (v4 !=0) goto L88;
    if (v5 !=0) goto L88;
    return;
    L88:
    ui.FwmHud.draw(ctx, client, v3, v5, top);
    return;
}

private static org.joml.Matrix3x2fStack drawTpa(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (game.TpaWatch.active() !=0) goto L7;
    return;
    L7:
    v2 = client.font;
    v3 = game.TpaWatch.remaining();
    if (v3 > 10) goto L26;
    goto L27;
    L26:
    L27:
    v4 = 0;
    if (game.TpaWatch.kind() != game.TpaWatch$Kind.TPAHERE) goto L44;
    goto L47;
    L44:
    L47:
    v5 = ui.Lang.of("fwm.tpa.cardTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
    new Object[2][0] = game.TpaWatch.target();
    new Object[2][1] = Integer.valueOf(v3);
    v6 = ui.Lang.of("fwm.tpa.cardSub", new Object[2]);
    v7 = ui.Lang.of("fwm.tpa.cardHint", new Object[0]);
    v8 = Math.max(v2.width(v5), Math.max(v2.width(v6), v2.width(v7)));
    v9 = ((5 + v8) + 5);
    v10 = 41;
    v11 = ui.FwmHud.scaleOf("tpa");
    v12 = ui.FwmHud.place(ctx, "tpa", Math.round((((f)v9) * v11)), Math.round((((f)v10) * v11)), 6, 43);
    v13 = ctx.pose();
    v13.pushMatrix();
    v13.translate(((f)v12[0]), ((f)v12[1]));
    v13.scale(v11, v11);
    ui.LauncherGfx.card(ctx, 0, 0, v9, v10, 6);
    v14 = 4;
    if (v4 ==0) goto L245;
    goto L248;
    L245:
    L248:
    v2.text(v5, 5, v14, -2056032, ui.FwmTheme.TEXT);
    if (v4 ==0) goto L272;
    goto L275;
    L272:
    L275:
    v2.text(v6, 5, (v14 + 11), -2056032, ui.FwmTheme.ACCENT);
    ctx.text(v2, v7, 5, (v14 + 22), ui.FwmTheme.TEXT_DIM);
    v13.popMatrix();
    return;
}

public static long showPricePing(config.FwmConfig$PricePing ping, String name, String price, String seller, long amount) {
    v6 = ?;
    v6.itemKey = ping.itemKey;
    v6.baseItemKey = ping.baseItemKey;
    v6.icon = ping.icon;
    ui.FwmHud.pingItem = v6;
    ui.FwmHud.pingName = name;
    ui.FwmHud.pingPrice = price;
    ui.FwmHud.pingSeller = seller;
    ui.FwmHud.pingAmount = amount;
    ui.FwmHud.pingOrder = ping.order;
    ui.FwmHud.pingAt = System.currentTimeMillis();
    return;
}

private static org.joml.Matrix3x2fStack drawPricePing(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (ui.FwmHud.pingItem == null) goto L20;
    if (cmp((System.currentTimeMillis() - ui.FwmHud.pingAt),20000) <0) goto L21;
    L20:
    return;
    L21:
    v2 = client.font;
    if (ui.FwmHud.pingOrder ==0) goto L38;
    goto L41;
    L38:
    L41:
    v3 = ui.Lang.of("fwm.priceping.cardTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
    new Object[2][0] = ui.FwmHud.pingName;
    if (cmp(ui.FwmHud.pingAmount,1) <=0) goto L92;
    goto L95;
    L92:
    L95:
    1[ui.FwmHud.pingPrice] = ' (' + ui.FwmHud.pingAmount + 'x)' + "";
    v4 = ui.Lang.of(new Object[2], new Object[2]);
    if (ui.FwmHud.pingOrder ==0) goto L118;
    goto L121;
    L118:
    L121:
    new Object[1][0] = ui.FwmHud.pingSeller;
    v5 = ui.Lang.of("fwm.priceping.cardFrom", new Object[1]);
    v6 = Math.max(v2.width(v3), Math.max(v2.width(v4), v2.width(v5)));
    v7 = ((26 + v6) + 5);
    v8 = 41;
    v9 = ui.FwmHud.scaleOf("priceping");
    v10 = Math.round((((f)v7) * v9));
    v11 = ui.FwmHud.place(ctx, "priceping", v10, Math.round((((f)v8) * v9)), ((ctx.guiWidth() - v10) / 2), 24);
    v12 = ctx.pose();
    v12.pushMatrix();
    v12.translate(((f)v11[0]), ((f)v11[1]));
    v12.scale(v9, v9);
    ui.LauncherGfx.card(ctx, 0, 0, v7, v8, 6);
    ui.panel.MarketPanel.drawIcon(ctx, ui.FwmHud.pingItem, 5, ((v8 - 16) / 2));
    v13 = 26;
    v14 = 4;
    ctx.text(v2, v3, v13, v14, ui.FwmTheme.ACCENT);
    ctx.text(v2, v4, v13, (v14 + 11), ui.FwmTheme.TEXT);
    ctx.text(v2, v5, v13, (v14 + 22), ui.FwmTheme.TEXT_DIM);
    v12.popMatrix();
    return;
}

private static boolean drawMoney(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    v2 = config.FwmConfig.INSTANCE;
    if (v2.moneyHud ==0) goto L18;
    if (money.MoneyEvents.active(client) !=0) goto L21;
    L18:
    return 120;
    L21:
    v3 = client.font;
    v4 = money.MoneyStore.user();
    v6 = money.MoneyStore.todayNet();
    v8 = (v2.moneyGoal * 100);
    v10 = ui.Lang.t("Sitzung", "Session");
    v11 = ui.Lang.t("Heute", "Today");
    v12 = (Math.max(v3.width(v10), v3.width(v11)) + 6);
    v13 = ui.FwmHud.signedMoney(v4);
    v14 = ui.FwmHud.signedMoney(v6);
    v15 = Math.max(v3.width(v13), v3.width(v14));
    v16 = 50;
    if (cmp(v8,0) <=0) goto L157;
    goto L160;
    L157:
    L160:
    v17 = "";
    if (cmp(v8,0) <=0) goto L186;
    goto L187;
    L186:
    L187:
    v18 = 0;
    v19 = ((((5 + v12) + v15) + v18) + 5);
    v20 = 30;
    v21 = ui.FwmHud.scaleOf("money");
    v22 = Math.round((((f)v19) * v21));
    v23 = Math.round((((f)v20) * v21));
    v24 = ui.FwmHud.place(ctx, "money", v22, v23, ((ctx.guiWidth() - v22) - 6), 120);
    v25 = ctx.pose();
    v25.pushMatrix();
    v25.translate(((f)v24[0]), ((f)v24[1]));
    v25.scale(v21, v21);
    ui.LauncherGfx.card(ctx, 0, 0, v19, v20, 6);
    v26 = 4;
    v27 = (5 + v12);
    ctx.text(v3, v10, 5, v26, ui.FwmTheme.TEXT_DIM);
    if (cmp(v4,0) >=0) goto L353;
    goto L356;
    L353:
    L356:
    v3.text(v13, v27, v26, -2067340, -9707377);
    ctx.text(v3, v11, 5, (v26 + 11), ui.FwmTheme.TEXT_DIM);
    if (cmp(v6,0) >=0) goto L399;
    goto L402;
    L399:
    L402:
    v3.text(v14, v27, (v26 + 11), -2067340, -9707377);
    if (cmp(v8,0) <=0) goto L531;
    v28 = ((v27 + v15) + 6);
    v29 = ((v26 + 11) + 1);
    v30 = ((i)Math.max(0, Math.min(((l)v16), ((v6 * ((l)v16)) / v8))));
    ctx.fill(v28, v29, (v28 + v16), (v29 + 6), 1442840575);
    if (cmp(v6,v8) <0) goto L503;
    goto L506;
    L503:
    L506:
    v28.fill(v29, (v28 + v30), (v29 + 6), -865972, ui.FwmTheme.ACCENT);
    ctx.text(v3, v17, ((v28 + v16) + 4), (v26 + 11), ui.FwmTheme.TEXT_DIM);
    L531:
    v25.popMatrix();
    if (ui.FwmHud.spot("money").moved() ==0) goto L556;
    goto L560;
    L556:
    L560:
    return (0 + (v23 + 4));
}

private static String signedMoney(long cents) {
    if (config.FwmConfig.INSTANCE.moneyHideAmounts ==0) goto L13;
    return "\u2022\u2022\u2022";
    L13:
    if (cmp(cents,0) >=0) goto L25;
    goto L28;
    L25:
    L28:
    return "+" + util.Fmt.compactMoney(Math.abs(cents));
}

private static org.joml.Matrix3x2fStack drawInvValue(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, int stackTop) {
    if (config.FwmConfig.INSTANCE.invValueHud ==0) goto L22;
    if (game.InvValue.active() ==0) goto L22;
    if (client.player != null) goto L23;
    L22:
    return;
    L23:
    v3 = client.font;
    v4 = game.InvValue.line();
    v5 = ((5 + v3.width(v4)) + 5);
    v6 = 19;
    v7 = ui.FwmHud.scaleOf("invvalue");
    v8 = Math.round((((f)v5) * v7));
    v9 = ui.FwmHud.place(ctx, "invvalue", v8, Math.round((((f)v6) * v7)), ((ctx.guiWidth() - v8) - 6), stackTop);
    v10 = ctx.pose();
    v10.pushMatrix();
    v10.translate(((f)v9[0]), ((f)v9[1]));
    v10.scale(v7, v7);
    ui.LauncherGfx.card(ctx, 0, 0, v5, v6, 6);
    ctx.text(v3, v4, 5, 4, ui.FwmTheme.TEXT);
    v10.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack drawAuction(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (config.FwmConfig.INSTANCE.auctionHudCard ==0) goto L18;
    if (config.Features.isOn("fwm.tab.auction") !=0) goto L19;
    L18:
    return;
    L19:
    v2 = auction.Auction.running();
    v3 = System.currentTimeMillis();
    if (ui.FwmHud.auctionWasRunning ==0) goto L41;
    if (v2 !=0) goto L41;
    ui.FwmHud.auctionEndedAt = v3;
    L41:
    ui.FwmHud.auctionWasRunning = v2;
    if (v2 !=0) goto L71;
    if (auction.Auction.hasResult() ==0) goto L71;
    if (cmp((v3 - ui.FwmHud.auctionEndedAt),5000) >=0) goto L71;
    goto L72;
    L71:
    L72:
    v5 = 0;
    if (v2 !=0) goto L88;
    if (v5 !=0) goto L88;
    ui.FwmHud.auctionBids = m1;
    return;
    L88:
    if ((client.gui.screen() instanceof ui.DesignHost) ==0) goto L102;
    return;
    L102:
    v6 = auction.Auction.bidCount();
    if (ui.FwmHud.auctionBids <0) goto L129;
    if (v6 <= ui.FwmHud.auctionBids) goto L129;
    ui.FwmHud.auctionFlashUntil = (v3 + 700);
    L129:
    ui.FwmHud.auctionBids = v6;
    v7 = client.font;
    v8 = auction.Auction.item();
    if (v8.isEmpty() ==0) goto L159;
    goto L169;
    L159:
    L169:
    v9 = v8.getHoverName().getString();
    v10 = auction.Auction.leader();
    v11 = '$' + util.Fmt.shortMoney(auction.Auction.price());
    v12 = auction.Auction.remainingMs();
    v14 = ((v12 + 999) / 1000);
    if (v5 !=0) goto L224;
    if (cmp(v14,10) >0) goto L224;
    goto L225;
    L224:
    L225:
    v16 = 0;
    v17 = -865972;
    if (v5 ==0) goto L249;
    goto L286;
    L249:
    new Object[2][0] = Long.valueOf((v14 / 60));
    new Object[2][1] = Long.valueOf((v14 % 60));
    L286:
    v18 = String.format(Locale.ROOT, "%d:%02d", new Object[2]);
    if (v5 ==0) goto L322;
    if (v10 != null) goto L310;
    goto L348;
    L310:
    goto L348;
    L322:
    if (v10 != null) goto L339;
    goto L348;
    L339:
    L348:
    v19 = ui.Lang.t("H\u00f6chstgebot", "Highest bid");
    if (v10 != null) goto L361;
    goto L363;
    L361:
    L363:
    v20 = v10;
    v21 = Math.max(160, (((30 + v7.width(v9)) + 10) + v7.width(v18)));
    v22 = 57;
    v23 = ui.FwmHud.scaleOf("auction");
    v24 = Math.round((((f)v21) * v23));
    v25 = ui.FwmHud.place(ctx, "auction", v24, Math.round((((f)v22) * v23)), ((ctx.guiWidth() - v24) - 6), 180);
    v26 = ctx.pose();
    v26.pushMatrix();
    v26.translate(((f)v25[0]), ((f)v25[1]));
    v26.scale(v23, v23);
    ui.LauncherGfx.card(ctx, 0, 0, v21, v22, 6);
    if (cmp(v3,ui.FwmHud.auctionFlashUntil) >=0) goto L520;
    ui.LauncherGfx.ring(ctx, 0, 0, v21, v22, 6, v17);
    L520:
    v27 = 5;
    if (v8.isEmpty() !=0) goto L540;
    ctx.item(v8, 5, v27);
    L540:
    v28 = v7.width(v18);
    v29 = v7.plainSubstrByWidth(v9, ((((v21 - 10) - 20) - v28) - 8));
    ctx.text(v7, network.chat.Component.literal(v29).withStyle(ChatFormatting.BOLD), 25, (v27 + 4), ui.FwmTheme.TEXT);
    if (v16 ==0) goto L625;
    goto L639;
    L625:
    if (v5 ==0) goto L636;
    goto L639;
    L636:
    L639:
    v18.text(((v21 - 5) - v28), (v27 + 4), -2067340, ui.FwmTheme.TEXT_DIM, ui.FwmTheme.ACCENT);
    v27 = v27 + 19;
    ctx.text(v7, v19, 5, v27, ui.FwmTheme.TEXT_DIM);
    if (v5 ==0) goto L675;
    if (v10 != null) goto L675;
    goto L677;
    L675:
    L677:
    v30 = v11;
    ctx.text(v7, network.chat.Component.literal(v30).withStyle(ChatFormatting.BOLD), ((v21 - 5) - v7.width(network.chat.Component.literal(v30).withStyle(ChatFormatting.BOLD))), v27, v17);
    v27 = v27 + 11;
    if (v20.isEmpty() !=0) goto L759;
    ctx.text(v7, v7.plainSubstrByWidth(v20, (v21 - 10)), 5, v27, ui.FwmTheme.TEXT);
    goto L785;
    L759:
    if (v5 !=0) goto L785;
    ctx.text(v7, ui.Lang.t("noch kein Gebot", "no bid yet"), 5, v27, ui.FwmTheme.TEXT_DIM);
    L785:
    v31 = ((v22 - 5) - 2);
    v32 = (v21 - 10);
    if (v5 ==0) goto L809;
    goto L812;
    L809:
    L812:
    v33 = auction.Auction.progress();
    ui.LauncherGfx.round(ctx, 5, v31, v32, 2, 1, 872415231);
    if (cmp(v33,0) <=0) goto L871;
    if (v16 ==0) goto L865;
    goto L868;
    L865:
    L868:
    ui.LauncherGfx.round(5, v31, Math.max(2, Math.round((((f)v32) * v33))), 2, 1, -2067340, ui.FwmTheme.ACCENT);
    L871:
    v26.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack drawSpawnerPrices(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (config.FwmConfig.INSTANCE.spawnerHudCard !=0) goto L10;
    return;
    L10:
    v2 = ui.SpawnerCard.size(client.font);
    if (v2 != null) goto L23;
    return;
    L23:
    v3 = ui.FwmHud.scaleOf("spawners");
    v4 = ui.FwmHud.place(ctx, "spawners", Math.round((((f)v2[0]) * v3)), Math.round((((f)v2[1]) * v3)), 6, 40);
    v5 = ctx.pose();
    v5.pushMatrix();
    v5.translate(((f)v4[0]), ((f)v4[1]));
    v5.scale(v3, v3);
    ui.SpawnerCard.draw(ctx, client.font);
    v5.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack drawMinimap(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (config.Features.isOn("fwm.tab.rtp") ==0) goto L25;
    if (config.Features.isOn("fwm.feature.rtpminimap") ==0) goto L25;
    if (client.player != null) goto L26;
    L25:
    return;
    L26:
    v2 = ui.FwmHud.scaleOf("minimap");
    v3 = ui.FwmHud.place(ctx, "minimap", Math.round((128.0 * v2)), Math.round((150.0 * v2)), 6, 90);
    v4 = ctx.pose();
    v4.pushMatrix();
    v4.translate(((f)v3[0]), ((f)v3[1]));
    v4.scale(v2, v2);
    ui.RtpMinimap.draw(ctx, client);
    v4.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack drawToolWarning(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client) {
    if (config.FwmConfig.INSTANCE.toolGuardWarning !=0) goto L10;
    return;
    L10:
    v2 = game.ToolGuard.warningStack(client);
    if (v2 != null) goto L20;
    return;
    L20:
    v3 = client.font;
    v4 = game.ToolGuard.sinceBlocked();
    if (cmp(v4,0) <0) goto L50;
    if (cmp(v4,700) >=0) goto L50;
    goto L51;
    L50:
    L51:
    v6 = 0;
    v7 = ui.Lang.of("fwm.toolguard.warnTitle", new Object[0]).withStyle(ChatFormatting.BOLD);
    if (v6 ==0) goto L89;
    goto L109;
    L89:
    new Object[1][0] = Integer.valueOf(game.ToolGuard.usesLeft(v2));
    L109:
    v8 = ui.Lang.of("fwm.toolguard.warnSub", new Object[1]);
    v9 = Math.max(v3.width(v7), v3.width(v8));
    v10 = ((26 + v9) + 5);
    v11 = 33;
    v12 = (1 * ui.FwmHud.scaleOf("tool"));
    v13 = ui.FwmHud.place(ctx, "tool", Math.round((((f)v10) * v12)), Math.round((((f)v11) * v12)), 6, 6);
    v14 = ctx.pose();
    v14.pushMatrix();
    v14.translate(((f)v13[0]), ((f)v13[1]));
    v14.scale(v12, v12);
    if (v6 ==0) goto L239;
    goto L242;
    L239:
    L242:
    ui.FwmTheme.box(0, 0, v10, v11, ui.FwmTheme.DISABLED_FILL, ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.DISABLED_EDGE);
    ctx.item(v2, 5, ((v11 - 16) / 2));
    v15 = 26;
    if (v6 ==0) goto L278;
    goto L281;
    L278:
    L281:
    v3.text(v7, v15, 4, m1, ui.FwmTheme.DISABLED_TEXT);
    ctx.text(v3, v8, v15, 14, ui.FwmTheme.DISABLED_TEXT);
    v16 = (v11 - 8);
    v17 = Math.max(1, Math.round((((f)v9) * game.ToolGuard.remaining(v2))));
    ctx.fill(v15, v16, (v15 + v9), (v16 + 2), ui.FwmTheme.OFF);
    ctx.fill(v15, v16, (v15 + v17), (v16 + 2), ui.FwmTheme.DISABLED_TEXT);
    v14.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack draw(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, boolean active, boolean blocked, int top) {
    v5 = client.font;
    v6 = network.chat.Component.literal("Sellmacro");
    if (blocked ==0) goto L24;
    goto L37;
    L24:
    if (active ==0) goto L34;
    goto L37;
    L34:
    L37:
    v7 = ui.Lang.of("fwm.hud.inactive", new Object[0]);
    if (stats.SellStats.testSettling() ==0) goto L65;
    goto L117;
    L65:
    if (stats.SellStats.testRunning() ==0) goto L93;
    new Object[1][0] = Integer.valueOf(stats.SellStats.testSecondsLeft());
    goto L117;
    L93:
    if (active ==0) goto L116;
    new Object[1][0] = input.Keybinds.toggleKeyName();
    goto L117;
    L116:
    L117:
    v8 = null;
    v9 = Math.max(1, Math.round(18.666667938232422));
    v10 = Math.max(v5.width(v6), v5.width(v7));
    if (v8 == null) goto L169;
    v10 = Math.max(v10, v5.width(v8));
    L169:
    if (v8 != null) goto L178;
    goto L179;
    L178:
    L179:
    v11 = 3;
    v12 = ((8 + (v11 * 11)) + 2);
    v13 = ((((20 + v9) + 6) + v10) + 10);
    v14 = 1.25;
    v15 = ui.FwmHud.place(ctx, "macro", Math.round((((f)v13) * v14)), Math.round((((f)v12) * v14)), ((ctx.guiWidth() - 6) - Math.round((((f)v13) * v14))), top);
    v16 = ctx.pose();
    v16.pushMatrix();
    v16.translate(((f)v15[0]), ((f)v15[1]));
    v16.scale(v14, v14);
    ui.LauncherGfx.card(ctx, 0, 0, v13, v12, 6);
    if (blocked ==0) goto L319;
    goto L332;
    L319:
    if (active ==0) goto L329;
    goto L332;
    L329:
    L332:
    v17 = ui.FwmTheme.TEXT_DIM;
    if (active ==0) goto L388;
    v18 = (0.550000011920929 + (0.44999998807907104 * ((f)Math.abs(Math.sin((((d)System.currentTimeMillis()) / 400.0))))));
    v19 = Math.round((v18 * 255.0));
    v17 = ((v19 << 24) | (v17 & 16777215));
    L388:
    v18 = (v12 / 2);
    ui.LauncherGfx.round(ctx, 8, (v18 - 3), 6, 6, 3, v17);
    ui.FwmTheme.logo(ctx, 20, (v18 - 7), v9);
    v19 = ((20 + v9) + 6);
    v20 = 5;
    ctx.text(v5, v6, v19, v20, ui.FwmTheme.TEXT);
    if (blocked ==0) goto L474;
    goto L487;
    L474:
    if (active ==0) goto L484;
    goto L487;
    L484:
    L487:
    v7.text(v19, (v20 + 11), ui.FwmTheme.DISABLED_TEXT, ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    if (v8 == null) goto L525;
    if (stats.SellStats.testRunning() ==0) goto L519;
    goto L522;
    L519:
    L522:
    v5.text(v8, v19, (v20 + 22), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM);
    L525:
    v16.popMatrix();
    return;
}

public static long showMessage(String from, String text) {
    ui.FwmHud.toastFrom = from;
    ui.FwmHud.toastText = text;
    ui.FwmHud.toastAt = System.currentTimeMillis();
    return;
}

private static org.joml.Matrix3x2fStack drawMessage(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, int top) {
    if (config.Features.isOn("fwm.tab.messages") ==0) goto L18;
    if (config.FwmConfig.INSTANCE.msgShowToast !=0) goto L19;
    L18:
    return;
    L19:
    if (ui.FwmHud.toastFrom != null) goto L26;
    return;
    L26:
    if (cmp((System.currentTimeMillis() - ui.FwmHud.toastAt),5000) <0) goto L41;
    return;
    L41:
    v3 = client.font;
    v4 = network.chat.Component.literal(ui.FwmHud.toastFrom).withStyle(ChatFormatting.BOLD);
    v5 = v3.plainSubstrByWidth(ui.FwmHud.toastText, 120);
    if (v5.length() >= ui.FwmHud.toastText.length()) goto L110;
    goto L112;
    L110:
    L112:
    v6 = network.chat.Component.literal(v5);
    v7 = Math.max(v3.width(v4), v3.width(v6));
    v8 = ((21 + v7) + 5);
    v9 = 26;
    v10 = ui.FwmHud.scaleOf("toast");
    v11 = Math.round((((f)v8) * v10));
    v12 = ui.FwmHud.place(ctx, "toast", v11, Math.round((((f)v9) * v10)), ((ctx.guiWidth() - 6) - v11), top);
    v13 = ctx.pose();
    v13.pushMatrix();
    v13.translate(((f)v12[0]), ((f)v12[1]));
    v13.scale(v10, v10);
    ui.LauncherGfx.card(ctx, 0, 0, v8, v9, 6);
    ui.FwmHud.envelope(ctx, 5, ((v9 - 8) / 2), ui.FwmTheme.ACCENT);
    v14 = 21;
    ctx.text(v3, v4, v14, 5, ui.FwmTheme.ACCENT);
    ctx.text(v3, v6, v14, 15, ui.FwmTheme.TEXT);
    v13.popMatrix();
    return;
}

private static org.joml.Matrix3x2fStack drawEnvelope(client.gui.GuiGraphicsExtractor ctx, client.Minecraft client, int top) {
    if (config.Features.isOn("fwm.tab.messages") ==0) goto L18;
    if (config.FwmConfig.INSTANCE.msgShowEnvelope !=0) goto L19;
    L18:
    return;
    L19:
    v3 = chat.Messages.unreadTotal();
    if (v3 >0) goto L28;
    return;
    L28:
    v4 = client.font;
    if (v3 <= 99) goto L46;
    goto L50;
    L46:
    L50:
    v5 = String.valueOf(v3);
    v6 = ((20 + v4.width(v5)) + 5);
    v7 = 16;
    v8 = ui.FwmHud.scaleOf("envelope");
    v9 = Math.round((((f)v6) * v8));
    v10 = ui.FwmHud.place(ctx, "envelope", v9, Math.round((((f)v7) * v8)), ((ctx.guiWidth() - 6) - v9), top);
    v11 = ctx.pose();
    v11.pushMatrix();
    v11.translate(((f)v10[0]), ((f)v10[1]));
    v11.scale(v8, v8);
    ui.LauncherGfx.card(ctx, 0, 0, v6, v7, 6);
    ui.FwmHud.envelope(ctx, 5, ((v7 - 8) / 2), ui.FwmTheme.ACCENT);
    Objects.requireNonNull(v4);
    ctx.text(v4, v5, 20, (((v7 - 9) / 2) + 1), ui.FwmTheme.TEXT);
    v11.popMatrix();
    return;
}

private static void envelope(client.gui.GuiGraphicsExtractor ctx, int x, int y, int color) {
    ui.FwmTheme.box(ctx, x, y, 11, 8, ui.FwmTheme.ACCENT_DIM, color);
    v4 = Math.min(5, 6);
    v5 = 0;
    L25:
    if (v5 >= v4) goto L102;
    ctx.fill(((x + 1) + v5), ((y + 1) + v5), ((x + 2) + v5), ((y + 2) + v5), color);
    ctx.fill((((x + 11) - 2) - v5), ((y + 1) + v5), (((x + 11) - 1) - v5), ((y + 2) + v5), color);
    v5 = v5 + 1;
    goto L25;
    L102:
    return;
}

private static boolean lambda$sampleLines$0(List out, network.chat.FormattedText part, Boolean continued) {
    out.add(part);
    return;
}

private static boolean lambda$drawSecondChat$0(List wrapped, network.chat.FormattedText part, Boolean continued) {
    wrapped.add(part);
    return;
}

private static void lambda$register$0(client.gui.GuiGraphicsExtractor ctx, client.DeltaTracker tickCounter) {
    ui.FwmHud.render(ctx);
    return;
}

static void <clinit>() {
    ui.FwmHud.ID = resources.Identifier.fromNamespaceAndPath("fwm", "watermark");
    ui.FwmHud.bounds = ?;
    ui.FwmHud.FIGURE = resources.Identifier.fromNamespaceAndPath("fwm", "textures/gui/hydravb.png");
    ui.FwmHud.chatHits = ?;
    ui.FwmHud.auctionBids = m1;
    return;
}

