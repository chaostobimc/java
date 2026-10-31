// class net/friendswithmoney/fwm/ui/SpawnerCard  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field STYLE_FWM I;
field STYLE_APPLE I;
field STYLE_NEON I;
field STYLE_MINIMAL I;
private void <init>() {
    return;
}

public static void applyStyle(int style) {
    v1 = config.FwmConfig.INSTANCE;
    v1.spawnerHudStyle = style;
    // switch (style) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=288), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=26), Operand(op_type=<OperandTypes.BRANCH: 40>, value=124), Operand(op_type=<OperandTypes.BRANCH: 40>, value=215)]
    L36:
    v1.spawnerHudBg = 1;
    v1.spawnerHudBgColor = 1842206;
    v1.spawnerHudOpacity = 80;
    v1.spawnerHudBorder = 1;
    v1.spawnerHudBorderColor = 16777215;
    v1.spawnerHudBorderWidth = 1;
    v1.spawnerHudRadius = 10;
    v1.spawnerHudPadding = 10;
    v1.spawnerHudRowGap = 13;
    v1.spawnerHudShadow = 1;
    v1.spawnerHudTextShadow = 0;
    v1.spawnerHudDivider = 1;
    v1.spawnerHudHeadBold = 0;
    v1.spawnerHudTextColor = 16777215;
    v1.spawnerHudHeadColor = 10000541;
    v1.spawnerHudBuyColor = 3199320;
    v1.spawnerHudSellColor = 16729402;
    goto L393;
    L134:
    v1.spawnerHudBg = 0;
    v1.spawnerHudBgColor = 460559;
    v1.spawnerHudOpacity = 92;
    v1.spawnerHudBorder = 2;
    v1.spawnerHudBorderWidth = 2;
    v1.spawnerHudRadius = 4;
    v1.spawnerHudPadding = 9;
    v1.spawnerHudRowGap = 12;
    v1.spawnerHudShadow = 0;
    v1.spawnerHudTextShadow = 1;
    v1.spawnerHudDivider = 0;
    v1.spawnerHudHeadBold = 1;
    v1.spawnerHudTextColor = 15790335;
    v1.spawnerHudHeadColor = 16732120;
    v1.spawnerHudBuyColor = 3800852;
    v1.spawnerHudSellColor = 16719993;
    goto L393;
    L225:
    v1.spawnerHudBg = 2;
    v1.spawnerHudBorder = 0;
    v1.spawnerHudRadius = 0;
    v1.spawnerHudPadding = 4;
    v1.spawnerHudRowGap = 11;
    v1.spawnerHudShadow = 0;
    v1.spawnerHudTextShadow = 1;
    v1.spawnerHudDivider = 0;
    v1.spawnerHudHeadBold = 1;
    v1.spawnerHudTextColor = 16777215;
    v1.spawnerHudHeadColor = 16777215;
    v1.spawnerHudBuyColor = 5635925;
    v1.spawnerHudSellColor = 16733525;
    goto L393;
    L298:
    v1.spawnerHudBg = 0;
    v1.spawnerHudBgColor = 789516;
    v1.spawnerHudOpacity = 90;
    v1.spawnerHudBorder = 1;
    v1.spawnerHudBorderColor = 16777215;
    v1.spawnerHudBorderWidth = 1;
    v1.spawnerHudRadius = 6;
    v1.spawnerHudPadding = 8;
    v1.spawnerHudRowGap = 12;
    v1.spawnerHudShadow = 0;
    v1.spawnerHudTextShadow = 1;
    v1.spawnerHudDivider = 1;
    v1.spawnerHudHeadBold = 1;
    v1.spawnerHudTextColor = 16119285;
    v1.spawnerHudHeadColor = 16119285;
    v1.spawnerHudBuyColor = 65360;
    v1.spawnerHudSellColor = 16711680;
    L393:
    return;
}

private static boolean rows(config.FwmConfig c) {
    v1 = net.Spawners.data();
    v2 = ?;
    if (v1 == null) goto L23;
    if (v1.items != null) goto L25;
    L23:
    return v2;
    L25:
    v3 = ui.panel.SpawnerPanel.inShopOrder(v1.items).iterator();
    L38:
    if (v3.hasNext() ==0) goto L222;
    v4 = ((net.SpawnerData$Item)v3.next());
    if (v4.name == null) goto L38;
    if (c.spawnerHudHidden.contains(v4.name) ==0) goto L86;
    goto L38;
    L86:
    v5 = (([Ljava.lang.String;)c.spawnerHudPrices.get(v4.name));
    if (v5 == null) goto L134;
    if (v5[0] == null) goto L134;
    if (v5[0].isBlank() !=0) goto L134;
    goto L139;
    L134:
    L139:
    v6 = v4.buyPrice;
    if (v5 == null) goto L177;
    if (v5.length <= 1) goto L177;
    if (v5[1] == null) goto L177;
    if (v5[1].isBlank() !=0) goto L177;
    goto L182;
    L177:
    L182:
    v7 = v4.sellPrice;
    new String[3][0] = v4.name;
    new String[3][1] = ui.SpawnerCard.price(v6);
    new String[3][2] = ui.SpawnerCard.price(v7);
    v2.add(new String[3]);
    goto L38;
    L222:
    return v2;
}

private static boolean price(String p) {
    if (p == null) goto L11;
    if (p.isBlank() ==0) goto L14;
    L11:
    return "-";
    L14:
    v1 = p.replaceAll("[^0-9]", "");
    if (v1.isEmpty() !=0) goto L47;
    if (v1.chars().allMatch(/*indy*/test()) ==0) goto L52;
    L47:
    goto L53;
    L52:
    L53:
    return p;
}

private static boolean label(String own, String fallback) {
    if (own == null) goto L11;
    if (own.isBlank() ==0) goto L15;
    L11:
    goto L16;
    L15:
    L16:
    return own;
}

public static ui.SpawnerCard$Layout size(client.gui.Font font) {
    v1 = ui.SpawnerCard.layout(font, config.FwmConfig.INSTANCE);
    if (v1 != null) goto L16;
    goto L33;
    L16:
    new int[2][0] = v1.w;
    new int[2][1] = v1.h;
    L33:
    return new int[2];
}

private static void layout(client.gui.Font font, config.FwmConfig c) {
    v2 = ui.SpawnerCard.rows(c);
    if (v2.isEmpty() ==0) goto L16;
    return null;
    L16:
    v3 = c.spawnerHudShowBuy;
    v4 = c.spawnerHudShowSell;
    if (c.spawnerHudTitle == null) goto L44;
    if (c.spawnerHudTitle.isBlank() ==0) goto L48;
    L44:
    goto L61;
    L48:
    L61:
    v5 = network.chat.Component.literal(c.spawnerHudTitle).withStyle(ChatFormatting.BOLD);
    v6 = ui.SpawnerCard.styled(ui.SpawnerCard.label(c.spawnerHudLabelName, "Spawner"), c.spawnerHudHeadBold);
    v7 = ui.SpawnerCard.styled(ui.SpawnerCard.label(c.spawnerHudLabelBuy, "Kauf"), c.spawnerHudHeadBold);
    v8 = ui.SpawnerCard.styled(ui.SpawnerCard.label(c.spawnerHudLabelSell, "Verkauf"), c.spawnerHudHeadBold);
    if (c.spawnerHudIcons ==0) goto L130;
    goto L131;
    L130:
    L131:
    v9 = 0;
    v10 = 0;
    v11 = 0;
    v12 = 0;
    v13 = v2.iterator();
    L150:
    if (v13.hasNext() ==0) goto L220;
    v14 = (([Ljava.lang.String;)v13.next());
    v10 = Math.max(v10, font.width(v14[0]));
    v11 = Math.max(v11, font.width(v14[1]));
    v12 = Math.max(v12, font.width(v14[2]));
    goto L150;
    L220:
    if (c.spawnerHudHeader ==0) goto L269;
    v10 = Math.max(v10, (font.width(v6) - v9));
    v11 = Math.max(v11, font.width(v7));
    v12 = Math.max(v12, font.width(v8));
    L269:
    if (v3 !=0) goto L276;
    v11 = 0;
    L276:
    if (v4 !=0) goto L284;
    v12 = 0;
    L284:
    v13 = c.spawnerHudPadding;
    v14 = c.spawnerHudRowGap;
    if (v5 != null) goto L305;
    goto L309;
    L305:
    L309:
    v15 = (v14 + 2);
    if (c.spawnerHudHeader ==0) goto L325;
    goto L326;
    L325:
    L326:
    v16 = 0;
    v17 = 14;
    v20 = 0;
    v21 = 1;
    if (c.spawnerHudGrid ==0) goto L477;
    if (v3 ==0) goto L369;
    if (v4 ==0) goto L369;
    goto L370;
    L369:
    L370:
    v20 = (Math.max((v11 + v12), (12 + 0)) + 14);
    v21 = Math.min(v2.size(), 3);
    v22 = (((v2.size() + v21) - 1) / v21);
    v18 = (((2 * v13) + (v21 * v20)) + ((v21 - 1) * 4));
    v19 = (((((2 * v13) + v15) + v16) + (v22 * ((2 * v14) + 6))) - 4);
    if (v5 == null) goto L474;
    v18 = Math.max(v18, ((2 * v13) + font.width(v5)));
    L474:
    goto L565;
    L477:
    if (v3 ==0) goto L499;
    goto L500;
    L499:
    L500:
    if (v4 ==0) goto L514;
    goto L515;
    L514:
    L515:
    v18 = ((v17 + v12) + 0);
    if (v5 == null) goto L541;
    v18 = Math.max(v18, ((2 * v13) + font.width(v5)));
    L541:
    v19 = (((((2 * v13) + v15) + v16) + (v2.size() * v14)) - 2);
    L565:
    return ?;
}

private static network.chat.MutableComponent styled(String s, boolean bold) {
    if (bold ==0) goto L17;
    goto L21;
    L17:
    L21:
    return network.chat.Component.literal(s);
}

public static void draw(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font) {
    v2 = config.FwmConfig.INSTANCE;
    v3 = ui.SpawnerCard.layout(font, v2);
    if (v3 != null) goto L15;
    return;
    L15:
    v4 = v3.w;
    v5 = v3.h;
    v6 = v2.spawnerHudPadding;
    v7 = v2.spawnerHudRowGap;
    v8 = Math.max(0, Math.min(12, v2.spawnerHudRadius));
    v9 = (-16777216 | v2.spawnerHudBuyColor);
    v10 = (-16777216 | v2.spawnerHudSellColor);
    v11 = (-16777216 | v2.spawnerHudTextColor);
    v12 = (-16777216 | v2.spawnerHudHeadColor);
    v13 = Math.max(0, Math.min(255, Math.round((((f)v2.spawnerHudOpacity) * 2.549999952316284))));
    v14 = v2.spawnerHudTextShadow;
    if (v2.spawnerHudShadow ==0) goto L198;
    if (v2.spawnerHudBg == 2) goto L198;
    v15 = 4;
    L142:
    if (v15 < 1) goto L198;
    ui.LauncherGfx.round(ctx, (-v15), ((-v15) + 3), (v4 + (2 * v15)), (v5 + (2 * v15)), (v8 + v15), ((6 + ((4 - v15) * 6)) << 24));
    v15 = v15 + 255;
    goto L142;
    L198:
    v15 = (v2.spawnerHudBgColor & 16777215);
    if (v2.spawnerHudBg !=0) goto L237;
    ui.LauncherGfx.round(ctx, 0, 0, v4, v5, v8, ((v13 << 24) | v15));
    goto L287;
    L237:
    if (v2.spawnerHudBg != 1) goto L287;
    ui.LauncherGfx.round(ctx, 0, 0, v4, v5, v8, ((((v13 * 55) / 100) << 24) | v15));
    ctx.fill(v8, 0, (v4 - v8), 1, 1090519039);
    L287:
    v16 = Math.max(1, Math.min(3, v2.spawnerHudBorderWidth));
    v17 = (-16777216 | v2.spawnerHudBorderColor);
    if (v2.spawnerHudBorder != 1) goto L378;
    v18 = 0;
    L322:
    if (v18 >= v16) goto L375;
    ui.LauncherGfx.ring(ctx, v18, v18, (v4 - (2 * v18)), (v5 - (2 * v18)), Math.max(0, (v8 - v18)), (1073741824 | (v17 & 16777215)));
    v18 = v18 + 1;
    goto L322;
    L375:
    goto L404;
    L378:
    if (v2.spawnerHudBorder != 2) goto L404;
    ui.SpawnerCard.gradientBorder(ctx, v4, v5, v9, v10, v16, v2.spawnerHudGradientSpeed);
    L404:
    v18 = v6;
    if (v3.title == null) goto L441;
    ui.SpawnerCard.text(ctx, font, v3.title, v6, v18, v11, v14);
    v18 = (v18 + v3.titleH);
    L441:
    v19 = v2.spawnerHudShowBuy;
    v20 = v2.spawnerHudShowSell;
    if (v2.spawnerHudGrid ==0) goto L688;
    if (v3.headerH <=0) goto L493;
    ui.SpawnerCard.text(ctx, font, v3.headName, v6, v18, v12, v14);
    v18 = (v18 + v3.headerH);
    L493:
    v21 = 0;
    L496:
    if (v21 >= v3.rows.size()) goto L685;
    v22 = (([Ljava.lang.String;)v3.rows.get(v21));
    v23 = (v6 + ((v21 % v3.cols) * (v3.cellW + 4)));
    v24 = (v18 + ((v21 / v3.cols) * ((2 * v7) + 6)));
    ui.SpawnerCard.icon(ctx, v22[0], v23, v24, v2.spawnerHudIcons);
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v22[0]), (v23 + v3.iconW), v24, v11, v14);
    if (v19 ==0) goto L634;
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v22[1]), v23, (v24 + v7), v9, v14);
    L634:
    if (v20 ==0) goto L679;
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v22[2]), (((v23 + v3.cellW) - 14) - font.width(v22[2])), (v24 + v7), v10, v14);
    L679:
    v21 = v21 + 1;
    goto L496;
    L685:
    goto L1052;
    L688:
    v21 = (((v6 + v3.iconW) + v3.nameW) + 14);
    if (v19 ==0) goto L722;
    goto L723;
    L722:
    L723:
    v22 = ((v3.buyW + 14) + 0);
    if (v3.headerH <=0) goto L888;
    ui.SpawnerCard.text(ctx, font, v3.headName, v6, v18, v12, v14);
    if (v19 ==0) goto L798;
    if (v2.spawnerHudHeadBold ==0) goto L791;
    goto L793;
    L791:
    L793:
    ui.SpawnerCard.text(font, v3.headBuy, ((v21 + v3.buyW) - font.width(v3.headBuy)), v18, v9, v12, v14);
    L798:
    if (v20 ==0) goto L846;
    if (v2.spawnerHudHeadBold ==0) goto L839;
    goto L841;
    L839:
    L841:
    ui.SpawnerCard.text(font, v3.headSell, ((v22 + v3.sellW) - font.width(v3.headSell)), v18, v10, v12, v14);
    L846:
    if (v2.spawnerHudDivider ==0) goto L879;
    ctx.fill(v6, ((v18 + v7) - 1), (v4 - v6), (v18 + v7), 654311423);
    L879:
    v18 = (v18 + v3.headerH);
    L888:
    v23 = v3.rows.iterator();
    L899:
    if (v23.hasNext() ==0) goto L1052;
    v24 = (([Ljava.lang.String;)v23.next());
    ui.SpawnerCard.icon(ctx, v24[0], v6, (v18 - 1), v2.spawnerHudIcons);
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v24[0]), (v6 + v3.iconW), v18, v11, v14);
    if (v19 ==0) goto L1003;
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v24[1]), ((v21 + v3.buyW) - font.width(v24[1])), v18, v9, v14);
    L1003:
    if (v20 ==0) goto L1042;
    ui.SpawnerCard.text(ctx, font, network.chat.Component.literal(v24[2]), ((v22 + v3.sellW) - font.width(v24[2])), v18, v10, v14);
    L1042:
    v18 = (v18 + v7);
    goto L899;
    L1052:
    return;
}

private static void text(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, network.chat.Component t, int x, int y, int color, boolean shadow) {
    ctx.text(font, t, x, y, color, shadow);
    return;
}

private static void icon(client.gui.GuiGraphicsExtractor ctx, String name, int x, int y, boolean on) {
    if (on !=0) goto L6;
    return;
    L6:
    v5 = ui.panel.SpawnerPanel.picture(name);
    if (v5 == null) goto L37;
    ui.FwmGfx.blit(ctx, v5, x, y, 10, 10, 64, 64, 64, 64);
    L37:
    return;
}

private static void gradientBorder(client.gui.GuiGraphicsExtractor ctx, int w, int h, int a, int b, int thick, int speed) {
    v7 = (2 * (w + h));
    v8 = Math.max(600, (6000 - (((l)Math.max(1, Math.min(10, speed))) * 540)));
    v10 = (((f)(System.currentTimeMillis() % v8)) / ((f)v8));
    v11 = 0;
    L51:
    if (v11 >= v7) goto L227;
    v12 = (((((f)v11) / ((f)v7)) + v10) % 1);
    if (cmp(v12,0.5) >=0) goto L88;
    goto L94;
    L88:
    L94:
    v13 = ((1 - v12) * 2);
    v14 = (-16777216 | ui.SpawnerCard.lerp(a, b, v13));
    if (v11 >= w) goto L126;
    v15 = v11;
    v16 = 0;
    goto L193;
    L126:
    if (v11 >= (w + h)) goto L149;
    v15 = (w - thick);
    v16 = (v11 - w);
    goto L193;
    L149:
    if (v11 >= ((2 * w) + h)) goto L178;
    v15 = (w - ((v11 - w) - h));
    v16 = (h - thick);
    goto L193;
    L178:
    v15 = 0;
    v16 = (h - ((v11 - (2 * w)) - h));
    L193:
    ctx.fill(v15, v16, (v15 + Math.max(2, thick)), (v16 + Math.max(2, thick)), v14);
    v11 = v11 + 2;
    goto L51;
    L227:
    return;
}

private static int lerp(int a, int b, float t) {
    v3 = Math.round(((((f)((a >> 16) & 255)) * (1 - t)) + (((f)((b >> 16) & 255)) * t)));
    v4 = Math.round(((((f)((a >> 8) & 255)) * (1 - t)) + (((f)((b >> 8) & 255)) * t)));
    v5 = Math.round(((((f)(a & 255)) * (1 - t)) + (((f)(b & 255)) * t)));
    return (((v3 << 16) | (v4 << 8)) | v5);
}

private static boolean lambda$price$0(int ch) {
    if (ch != 48) goto L10;
    goto L11;
    L10:
    L11:
    return 0;
}

