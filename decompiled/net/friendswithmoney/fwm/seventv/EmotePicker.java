// class net/friendswithmoney/fwm/seventv/EmotePicker  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field C_BG I;
field C_BG_DARK I;
field C_BORDER I;
field C_HOVER I;
field C_TEXT I;
field C_MUTED I;
field C_ACCENT I;
field C_7TV I;
field C_BTTV I;
field CELL I;
field ICON I;
field HEADER_H I;
field KEY_ENTER I;
field KEY_KP_ENTER I;
field KEY_ESCAPE I;
field KEY_TAB I;
field mc Lnet/minecraft/client/Minecraft;;
field onPick Ljava/util/function/Consumer;;
field INSTANCE Lnet/friendswithmoney/fwm/seventv/EmotePicker;;
field preSeven Ljava/util/List;;
field preBttv Ljava/util/List;;
field preAt J;
field preloadingSeven Z;
field preloadingBttv Z;
field PRELOAD_TTL J;
field MIN_W I;
field MIN_H I;
field resizing Z;
field selectingText Z;
field selectButton Lnet/minecraft/client/input/MouseButtonInfo;;
field lastToggle J;
field search Lnet/minecraft/client/gui/components/EditBox;;
field open Z;
field tab Lnet/friendswithmoney/fwm/seventv/EmotePicker$Tab;;
field x I;
field y I;
field w I;
field h I;
field gridX I;
field gridY I;
field gridW I;
field gridH I;
field cols I;
field scroll D;
field jumpTarget Ljava/lang/String;;
field activeQuery Ljava/lang/String;;
field pendingQuery Ljava/lang/String;;
field queryChangedAt J;
field requestId I;
field seven Ljava/util/List;;
field bttv Ljava/util/List;;
field sevenPage I;
field bttvOffset I;
field sevenMore Z;
field bttvMore Z;
field loadingSeven Z;
field loadingBttv Z;
field errorSeven Z;
field errorBttv Z;
field rows Ljava/util/List;;
field sectionOffsets Ljava/util/Map;;
field contentHeight I;
field hovered Lnet/friendswithmoney/fwm/seventv/Emote;;
public void <init>(function.Consumer onPick) {
    this.mc = client.Minecraft.getInstance();
    this.tab = seventv.EmotePicker$Tab.ALL;
    this.activeQuery = "";
    this.pendingQuery = "";
    this.seven = ?;
    this.bttv = ?;
    this.sevenPage = 1;
    this.bttvOffset = 0;
    this.sevenMore = 1;
    this.bttvMore = 1;
    this.rows = ?;
    this.sectionOffsets = ?;
    this.onPick = onPick;
    return;
}

public static void get() {
    if (seventv.EmotePicker.INSTANCE != null) goto L21;
    seventv.EmotePicker.INSTANCE = ?;
    L21:
    return seventv.EmotePicker.INSTANCE;
}

public static concurrent.CompletableFuture preload() {
    v0 = client.Minecraft.getInstance();
    if (cmp((System.currentTimeMillis() - seventv.EmotePicker.preAt),1800000) >=0) goto L22;
    goto L23;
    L22:
    L23:
    v1 = 0;
    v2 = seventv.EmoteConfig.frequent(36).iterator();
    L35:
    if (v2.hasNext() ==0) goto L62;
    v3 = ((seventv.Emote)v2.next());
    seventv.EmoteTexture.get(v3);
    goto L35;
    L62:
    if (seventv.EmotePicker.preloadingSeven !=0) goto L100;
    if (v1 ==0) goto L78;
    if (seventv.EmotePicker.preSeven != null) goto L100;
    L78:
    seventv.EmotePicker.preloadingSeven = 1;
    seventv.EmoteApi.searchSevenTv("", 1, 60).whenComplete(/*indy*/accept(v0));
    L100:
    if (seventv.EmotePicker.preloadingBttv !=0) goto L138;
    if (v1 ==0) goto L116;
    if (seventv.EmotePicker.preBttv != null) goto L138;
    L116:
    seventv.EmotePicker.preloadingBttv = 1;
    seventv.EmoteApi.searchBttv("", 0, 100).whenComplete(/*indy*/accept(v0));
    L138:
    return;
}

public void setOnPick(function.Consumer onPick) {
    this.onPick = onPick;
    return;
}

public void toggleDebounced() {
    v1 = System.currentTimeMillis();
    if (cmp((v1 - seventv.EmotePicker.lastToggle),250) >=0) goto L17;
    return;
    L17:
    seventv.EmotePicker.lastToggle = v1;
    this.toggle();
    return;
}

public static long toggledRecently() {
    if (cmp((System.currentTimeMillis() - seventv.EmotePicker.lastToggle),1000) >=0) goto L18;
    goto L19;
    L18:
    L19:
    return 0;
}

public boolean isOpen() {
    return this.open;
}

public void toggle() {
    if (this.open ==0) goto L14;
    this.close();
    goto L18;
    L14:
    this.open();
    L18:
    return;
}

public void open() {
    seventv.EmotePicker.preload();
    this.open = 1;
    this.scroll = 0;
    this.jumpTarget = null;
    if (this.search != null) goto L92;
    this.search = ?;
    this.search.setMaxLength(40);
    this.search.setBordered(0);
    this.search.setHint(network.chat.Component.literal("Finde das perfekte Emote"));
    this.search.setTextColor(-2367775);
    L92:
    this.search.setValue("");
    this.search.setFocused(1);
    this.activeQuery = "";
    this.pendingQuery = "";
    this.startQuery("");
    return;
}

public void close() {
    this.open = 0;
    if (this.resizing ==0) goto L20;
    this.resizing = 0;
    seventv.EmoteConfig.save();
    L20:
    this.selectingText = 0;
    if (this.search == null) goto L40;
    this.search.setFocused(0);
    L40:
    return;
}

private void startQuery(String q) {
    this.activeQuery = q;
    this.requestId = (this.requestId + 1);
    v2 = (this.requestId + 1);
    this.seven.clear();
    this.bttv.clear();
    this.sevenPage = 1;
    this.bttvOffset = 0;
    this.bttvMore = 1;
    this.sevenMore = 1;
    this.errorBttv = 0;
    this.errorSeven = 0;
    this.scroll = 0;
    this.jumpTarget = null;
    if (q.isBlank() ==0) goto L133;
    v3 = seventv.EmotePicker.preSeven;
    v4 = seventv.EmotePicker.preBttv;
    if (v3 == null) goto L111;
    this.seven.addAll(v3);
    this.sevenPage = 2;
    L111:
    if (v4 == null) goto L133;
    this.bttv.addAll(v4);
    this.bttvMore = 0;
    L133:
    this.loadMore(v2);
    return;
}

private concurrent.CompletableFuture loadMore(int rid) {
    if (rid == this.requestId) goto L9;
    return;
    L9:
    if (this.tab == seventv.EmotePicker$Tab.SEVENTV) goto L37;
    if (this.activeQuery.isBlank() ==0) goto L37;
    if (this.sevenPage != 1) goto L41;
    L37:
    goto L42;
    L41:
    L42:
    v2 = 0;
    if (this.tab == seventv.EmotePicker$Tab.BTTV) goto L116;
    if (this.sevenMore ==0) goto L116;
    if (v2 ==0) goto L116;
    if (this.loadingSeven !=0) goto L116;
    if (this.errorSeven !=0) goto L116;
    this.loadingSeven = 1;
    this.sevenPage = (this.sevenPage + 1);
    v3 = this.sevenPage;
    seventv.EmoteApi.searchSevenTv(this.activeQuery, v3, 60).whenComplete(/*indy*/accept(this, rid));
    L116:
    if (this.tab == seventv.EmotePicker$Tab.SEVENTV) goto L204;
    if (this.bttvMore ==0) goto L204;
    if (this.loadingBttv !=0) goto L204;
    if (this.errorBttv !=0) goto L204;
    this.loadingBttv = 1;
    v3 = this.bttvOffset;
    this.bttvOffset = (this.bttvOffset + 50);
    if (this.activeQuery.isBlank() ==0) goto L188;
    goto L190;
    L188:
    L190:
    seventv.EmoteApi.searchBttv(v3, 100, 50).whenComplete(/*indy*/accept(this, rid));
    L204:
    return;
}

private Object rebuildRows() {
    this.rows.clear();
    this.sectionOffsets.clear();
    v1 = ?;
    v2 = ?;
    if (this.activeQuery.isBlank() ==0) goto L160;
    v3 = this.filterTab(seventv.EmoteConfig.frequent(36));
    if (v3.isEmpty() !=0) goto L93;
    this.addSection("H\u00e4ufig verwendet", -2367775, seventv.EmotePicker.dedupe(v3, ?, ?), "freq");
    L93:
    if (this.tab == seventv.EmotePicker$Tab.BTTV) goto L125;
    this.addSection("7TV \u2013 Beliebt", -14043402, seventv.EmotePicker.dedupe(this.seven, v1, v2), "7tv");
    L125:
    if (this.tab == seventv.EmotePicker$Tab.SEVENTV) goto L157;
    this.addSection("BetterTTV \u2013 Beliebt", -2818028, seventv.EmotePicker.dedupe(this.bttv, v1, v2), "bttv");
    L157:
    goto L732;
    L160:
    v3 = ?;
    if (this.tab == seventv.EmotePicker$Tab.BTTV) goto L189;
    v3.addAll(this.seven);
    L189:
    if (this.tab == seventv.EmotePicker$Tab.SEVENTV) goto L210;
    v3.addAll(this.bttv);
    L210:
    v4 = this.activeQuery.trim().toLowerCase(Locale.ROOT);
    v5 = ?;
    v6 = ?;
    v7 = ?;
    v8 = ?;
    if (this.tab != seventv.EmotePicker$Tab.BTTV) goto L277;
    goto L281;
    L277:
    L281:
    v9 = this.seven;
    if (this.tab != seventv.EmotePicker$Tab.SEVENTV) goto L299;
    goto L303;
    L299:
    L303:
    v10 = this.bttv;
    v11 = 0;
    L308:
    if (v11 >= Math.max(v9.size(), v10.size())) goto L400;
    if (v11 >= v9.size()) goto L362;
    v8.add(((seventv.Emote)v9.get(v11)));
    L362:
    if (v11 >= v10.size()) goto L394;
    v8.add(((seventv.Emote)v10.get(v11)));
    L394:
    v11 = v11 + 1;
    goto L308;
    L400:
    v11 = v8.iterator();
    L409:
    if (v11.hasNext() ==0) goto L503;
    v12 = ((seventv.Emote)v11.next());
    v13 = v12.name().toLowerCase(Locale.ROOT);
    if (v13.equals(v4) ==0) goto L467;
    v5.add(v12);
    goto L500;
    L467:
    if (v13.startsWith(v4) ==0) goto L490;
    v6.add(v12);
    goto L500;
    L490:
    v7.add(v12);
    L500:
    goto L409;
    L503:
    v11 = ?;
    v11.addAll(v5);
    v11.addAll(v6);
    v11.addAll(v7);
    v12 = ?;
    v13 = this.seven.iterator();
    L562:
    if (v13.hasNext() ==0) goto L606;
    v14 = ((seventv.Emote)v13.next());
    v12.add(v14.name().toLowerCase(Locale.ROOT));
    goto L562;
    L606:
    v13 = ?;
    v14 = v11.iterator();
    L624:
    if (v14.hasNext() ==0) goto L704;
    v15 = ((seventv.Emote)v14.next());
    if (v15.provider() != seventv.Emote$Provider.BTTV) goto L691;
    if (this.tab != seventv.EmotePicker$Tab.ALL) goto L691;
    if (v12.contains(v15.name().toLowerCase(Locale.ROOT)) ==0) goto L691;
    goto L624;
    L691:
    v13.add(v15);
    goto L624;
    L704:
    this.addSection('Suchergebnisse für „' + this.activeQuery.trim() + '“', -2367775, seventv.EmotePicker.dedupe(v13, v1, v2), "results");
    L732:
    this.contentHeight = 0;
    v3 = this.rows.iterator();
    L747:
    if (v3.hasNext() ==0) goto L794;
    v4 = ((seventv.EmotePicker$Row)v3.next());
    if ((v4 instanceof seventv.EmotePicker$Header) ==0) goto L785;
    goto L787;
    L785:
    L787:
    this.contentHeight.contentHeight = (14 + 24);
    goto L747;
    L794:
    return;
}

private boolean filterTab(List list) {
    if (this.tab != seventv.EmotePicker$Tab.ALL) goto L12;
    return list;
    L12:
    v2 = ?;
    v3 = list.iterator();
    L27:
    if (v3.hasNext() ==0) goto L110;
    v4 = ((seventv.Emote)v3.next());
    if (this.tab != seventv.EmotePicker$Tab.SEVENTV) goto L77;
    if (v4.provider() != seventv.Emote$Provider.SEVENTV) goto L77;
    v2.add(v4);
    L77:
    if (this.tab != seventv.EmotePicker$Tab.BTTV) goto L107;
    if (v4.provider() != seventv.Emote$Provider.BTTV) goto L107;
    v2.add(v4);
    L107:
    goto L27;
    L110:
    return v2;
}

private static boolean dedupe(List in, Set names, Set hashes) {
    v3 = ?;
    v4 = in.iterator();
    L16:
    if (v4.hasNext() ==0) goto L179;
    v5 = ((seventv.Emote)v4.next());
    if (names.add(v5.name().toLowerCase(Locale.ROOT)) !=0) goto L61;
    goto L16;
    L61:
    v6 = seventv.EmoteTexture.peek(v5);
    if (v6 == null) goto L167;
    if (v6.state != seventv.EmoteTexture$State.READY) goto L167;
    v7 = 0;
    v8 = hashes.iterator();
    L95:
    if (v8.hasNext() ==0) goto L144;
    v9 = ((Long)v8.next()).longValue();
    if (Long.bitCount((v9 ^ v6.hash)) > 2) goto L141;
    v7 = 1;
    goto L144;
    L141:
    goto L95;
    L144:
    if (v7 ==0) goto L152;
    goto L16;
    L152:
    hashes.add(Long.valueOf(v6.hash));
    L167:
    v3.add(v5);
    goto L16;
    L179:
    return v3;
}

private boolean addSection(String title, int color, List list, String id) {
    v5 = 0;
    v6 = this.rows.iterator();
    L14:
    if (v6.hasNext() ==0) goto L59;
    v7 = ((seventv.EmotePicker$Row)v6.next());
    if ((v7 instanceof seventv.EmotePicker$Header) ==0) goto L51;
    goto L53;
    L51:
    L53:
    v5 = (14 + 24);
    goto L14;
    L59:
    this.sectionOffsets.put(id, Integer.valueOf(v5));
    this.rows.add(?);
    if (list.isEmpty() ==0) goto L127;
    this.rows.add(?);
    return;
    L127:
    v6 = 0;
    L130:
    if (v6 >= list.size()) goto L196;
    this.rows.add(?);
    v6 = (v6 + this.cols);
    goto L130;
    L196:
    return;
}

private void layout(int screenW, int screenH) {
    v3 = Math.max(190, (screenW - 8));
    v4 = Math.max(150, (screenH - 40));
    this.w = Math.max(190, Math.min(v3, seventv.EmoteConfig.pickerWidth()));
    this.h = Math.max(150, Math.min(v4, seventv.EmoteConfig.pickerHeight()));
    this.x = ((screenW - this.w) - 2);
    this.y = ((screenH - 16) - this.h);
    this.gridX = (this.x + 26);
    this.gridY = (this.y + 40);
    this.gridW = ((this.w - 26) - 6);
    this.gridH = ((this.h - 40) - 24);
    this.cols = Math.max(1, (this.gridW / 24));
    this.search.setX((this.x + 10));
    this.search.setY((this.y + 24));
    this.search.setWidth((this.w - 30));
    this.search.setHeight(12);
    return;
}

public void render(client.gui.GuiGraphicsExtractor g, int mouseX, int mouseY, float delta, int screenW, int screenH) {
    if (this.open !=0) goto L8;
    return;
    L8:
    this.layout(screenW, screenH);
    v7 = this.search.getValue();
    if (v7.equals(this.pendingQuery) !=0) goto L50;
    this.pendingQuery = v7;
    this.queryChangedAt = System.currentTimeMillis();
    L50:
    if (this.pendingQuery.equals(this.activeQuery) !=0) goto L87;
    if (cmp((System.currentTimeMillis() - this.queryChangedAt),300) <=0) goto L87;
    this.startQuery(this.pendingQuery);
    L87:
    if (this.selectingText ==0) goto L121;
    if (this.selectButton == null) goto L121;
    this.search.mouseDragged(this.searchEvent(((d)mouseX), this.selectButton), 0, 0);
    L121:
    if (this.resizing ==0) goto L160;
    v8 = (screenW - 2);
    v9 = (screenH - 16);
    seventv.EmoteConfig.setPickerSize((v8 - mouseX), (v9 - mouseY));
    this.layout(screenW, screenH);
    L160:
    this.rebuildRows();
    if (this.jumpTarget == null) goto L211;
    if (this.sectionOffsets.containsKey(this.jumpTarget) ==0) goto L211;
    this.scroll = ((d)((Integer)this.sectionOffsets.get(this.jumpTarget)).intValue());
    L211:
    v8 = ((d)Math.max(0, (this.contentHeight - this.gridH)));
    if (cmp(this.scroll,v8) <=0) goto L243;
    this.scroll = v8;
    L243:
    if (cmp(this.scroll,0) >=0) goto L257;
    this.scroll = 0;
    L257:
    if (cmp((v8 - this.scroll),72.0) >=0) goto L279;
    this.loadMore(this.requestId);
    L279:
    v10 = this.mc.font;
    g.nextStratum();
    g.fill((this.x - 1), (this.y - 1), ((this.x + this.w) + 1), ((this.y + this.h) + 1), -14803166);
    g.fill(this.x, this.y, (this.x + this.w), (this.y + this.h), -265605839);
    this.drawGrip(g, mouseX, mouseY);
    v11 = (this.x + 14);
    v12 = seventv.EmotePicker$Tab.values();
    v13 = v12.length;
    v14 = 0;
    L395:
    if (v14 >= v13) goto L600;
    v15 = v12[v14];
    v16 = (v10.width(v15.label) + 10);
    if (v15 != this.tab) goto L437;
    goto L438;
    L437:
    L438:
    v17 = 0;
    if (mouseX < v11) goto L480;
    if (mouseX >= (v11 + v16)) goto L480;
    if (mouseY < (this.y + 4)) goto L480;
    if (mouseY >= (this.y + 17)) goto L480;
    goto L481;
    L480:
    L481:
    v18 = 0;
    if (v17 ==0) goto L518;
    g.fill(v11, (this.y + 4), (v11 + v16), (this.y + 17), -12565943);
    goto L550;
    L518:
    if (v18 ==0) goto L550;
    g.fill(v11, (this.y + 4), (v11 + v16), (this.y + 17), 822083583);
    L550:
    if (v17 ==0) goto L578;
    goto L581;
    L578:
    L581:
    v10.text(v15.label, (v11 + 5), (this.y + 7), m1, -7038044, 0);
    v11 = (v11 + (v16 + 4));
    v14 = v14 + 1;
    goto L395;
    L600:
    g.fill((this.x + 6), (this.y + 21), ((this.x + this.w) - 6), (this.y + 36), -266461406);
    g.outline((this.x + 6), (this.y + 21), (this.w - 12), 15, -10983950);
    this.search.extractWidgetRenderState(g, mouseX, mouseY, delta);
    this.drawMagnifier(g, ((this.x + this.w) - 18), (this.y + 24));
    g.fill(this.x, (this.gridY - 2), (this.x + 22), ((this.y + this.h) - 22), -266461406);
    v12 = (this.gridY + 2);
    v12 = this.sidebarButton(g, v10, "freq", "", -2367775, v12, mouseX, mouseY);
    v12 = this.sidebarButton(g, v10, "7tv", "7", -14043402, v12, mouseX, mouseY);
    this.sidebarButton(g, v10, "bttv", "B", -2818028, v12, mouseX, mouseY);
    this.hovered = null;
    g.enableScissor(this.gridX, this.gridY, (this.gridX + this.gridW), (this.gridY + this.gridH));
    v13 = (this.gridY - ((i)this.scroll));
    v14 = this.rows.iterator();
    L871:
    if (v14.hasNext() ==0) goto L1208;
    v15 = ((seventv.EmotePicker$Row)v14.next());
    if ((v15 instanceof seventv.EmotePicker$Header) ==0) goto L906;
    goto L908;
    L906:
    L908:
    v16 = 24;
    if ((v13 + v16) < this.gridY) goto L1198;
    if (v13 > (this.gridY + this.gridH)) goto L1198;
    if ((v15 instanceof seventv.EmotePicker$Header) ==0) goto L981;
    v17 = ((seventv.EmotePicker$Header)v15);
    g.text(v10, v17.title(), (this.gridX + 2), (v13 + 3), v17.color(), 0);
    goto L1198;
    L981:
    if ((v15 instanceof seventv.EmotePicker$Line) ==0) goto L1198;
    v18 = ((seventv.EmotePicker$Line)v15);
    if (v18.emotes().isEmpty() ==0) goto L1039;
    g.text(v10, this.statusText(v18.section()), (this.gridX + 4), (v13 + 8), -7038044, 0);
    L1039:
    v19 = 0;
    L1042:
    if (v19 >= v18.emotes().size()) goto L1198;
    v20 = ((seventv.Emote)v18.emotes().get(v19));
    v21 = (this.gridX + (v19 * 24));
    if (mouseX < v21) goto L1139;
    if (mouseX >= (v21 + 24)) goto L1139;
    if (mouseY < Math.max(v13, this.gridY)) goto L1139;
    if (mouseY >= Math.min((v13 + 24), (this.gridY + this.gridH))) goto L1139;
    goto L1140;
    L1139:
    L1140:
    v22 = 0;
    if (v22 ==0) goto L1174;
    g.fill(v21, v13, (v21 + 24), (v13 + 24), 1090519039);
    this.hovered = v20;
    L1174:
    seventv.EmotePicker.drawEmote(g, v20, (v21 + 2), (v13 + 2), 20, 20);
    v19 = v19 + 1;
    goto L1042;
    L1198:
    v13 = (v13 + v16);
    goto L871;
    L1208:
    g.disableScissor();
    if (this.contentHeight <= this.gridH) goto L1362;
    v14 = Math.max(12, ((i)(((d)this.gridH) * (((d)this.gridH) / ((d)this.contentHeight)))));
    v15 = (this.gridY + ((i)(((d)(this.gridH - v14)) * (this.scroll / ((d)Math.max(1, (this.contentHeight - this.gridH)))))));
    g.fill(((this.x + this.w) - 5), this.gridY, ((this.x + this.w) - 2), (this.gridY + this.gridH), 1073741824);
    g.fill(((this.x + this.w) - 5), v15, ((this.x + this.w) - 2), (v15 + v14), -15066338);
    L1362:
    v14 = ((this.y + this.h) - 22);
    g.fill(this.x, v14, (this.x + this.w), (this.y + this.h), -266461406);
    if (this.hovered == null) goto L1421;
    goto L1425;
    L1421:
    L1425:
    v15 = this.firstResult();
    if (v15 == null) goto L1528;
    seventv.EmotePicker.drawEmote(g, v15, (this.x + 8), (v14 + 3), 16, 16);
    g.text(v10, v15.chatCode(), (this.x + 30), (v14 + 4), -2367775, 0);
    if (v15.provider() != seventv.Emote$Provider.SEVENTV) goto L1518;
    goto L1521;
    L1518:
    L1521:
    v10.text(v15.provider().label, (this.x + 30), (v14 + 13), -14043402, -2818028, 0);
    goto L1553;
    L1528:
    g.text(v10, "Emote ausw\u00e4hlen", (this.x + 8), (v14 + 8), -7038044, 0);
    L1553:
    return;
}

private void sidebarButton(client.gui.GuiGraphicsExtractor g, client.gui.Font font, String id, String label, int color, int sy, int mx, int my) {
    if (this.sectionOffsets.containsKey(id) !=0) goto L16;
    return sy;
    L16:
    if (mx < (this.x + 2)) goto L60;
    if (mx >= (this.x + 20)) goto L60;
    if (my < sy) goto L60;
    if (my >= (sy + 18)) goto L60;
    goto L61;
    L60:
    L61:
    v9 = 0;
    if (v9 ==0) goto L95;
    goto L98;
    L95:
    L98:
    (this.x + 2).fill(sy, (this.x + 20), (sy + 18), -12565943, -13552840);
    if (label.isEmpty() ==0) goto L130;
    this.drawClock(g, (this.x + 6), (sy + 4), color);
    goto L150;
    L130:
    g.centeredText(font, label, (this.x + 11), (sy + 5), color);
    L150:
    return (sy + 22);
}

private boolean overSearch(double mx, double a2) {
    if (cmp(mx,((d)(this.x + 6))) <0) goto L61;
    if (cmp(mx,((d)((this.x + this.w) - 6))) >=0) goto L61;
    if (cmp(v3,((d)(this.y + 21))) <0) goto L61;
    if (cmp(v3,((d)(this.y + 36))) >=0) goto L61;
    goto L62;
    L61:
    L62:
    return 0;
}

private void searchEvent(double mx, client.input.MouseButtonInfo a2) {
    v4 = Math.max(((d)this.search.getX()), Math.min(((d)((this.search.getX() + this.search.getWidth()) - 1)), mx));
    v6 = (((d)this.search.getY()) + (((d)this.search.getHeight()) / 2.0));
    return ?;
}

private boolean overGrip(double mx, double a2) {
    if (cmp(mx,((d)(this.x - 1))) <0) goto L54;
    if (cmp(mx,((d)(this.x + 12))) >=0) goto L54;
    if (cmp(v3,((d)(this.y - 1))) <0) goto L54;
    if (cmp(v3,((d)(this.y + 12))) >=0) goto L54;
    goto L55;
    L54:
    L55:
    return 0;
}

private void drawGrip(client.gui.GuiGraphicsExtractor g, int mx, int my) {
    if (this.resizing !=0) goto L18;
    if (this.overGrip(((d)mx), ((d)my)) ==0) goto L22;
    L18:
    goto L23;
    L22:
    L23:
    v4 = 0;
    if (v4 ==0) goto L36;
    goto L39;
    L36:
    L39:
    v5 = -7038044;
    v6 = (this.x + 3);
    v7 = (this.y + 3);
    g.fill((v6 + 1), v7, (v6 + 5), (v7 + 6), v5);
    g.fill(v6, (v7 + 1), (v6 + 6), (v7 + 5), v5);
    if (v4 ==0) goto L130;
    if (this.resizing !=0) goto L130;
    g.setTooltipForNextFrame(this.mc.font, network.chat.Component.literal("Ziehen, um die Gr\u00f6\u00dfe zu \u00e4ndern"), mx, my);
    L130:
    return;
}

private void drawClock(client.gui.GuiGraphicsExtractor g, int cx, int cy, int c) {
    g.fill((cx + 3), cy, (cx + 7), (cy + 1), c);
    g.fill((cx + 3), (cy + 9), (cx + 7), (cy + 10), c);
    g.fill(cx, (cy + 3), (cx + 1), (cy + 7), c);
    g.fill((cx + 9), (cy + 3), (cx + 10), (cy + 7), c);
    g.fill((cx + 1), (cy + 1), (cx + 3), (cy + 3), c);
    g.fill((cx + 7), (cy + 1), (cx + 9), (cy + 3), c);
    g.fill((cx + 1), (cy + 7), (cx + 3), (cy + 9), c);
    g.fill((cx + 7), (cy + 7), (cx + 9), (cy + 9), c);
    g.fill((cx + 4), (cy + 2), (cx + 5), (cy + 5), c);
    g.fill((cx + 5), (cy + 5), (cx + 7), (cy + 6), c);
    return;
}

private void drawMagnifier(client.gui.GuiGraphicsExtractor g, int mx, int my) {
    v4 = -7038044;
    g.fill((mx + 1), my, (mx + 5), (my + 1), v4);
    g.fill((mx + 1), (my + 5), (mx + 5), (my + 6), v4);
    g.fill(mx, (my + 1), (mx + 1), (my + 5), v4);
    g.fill((mx + 5), (my + 1), (mx + 6), (my + 5), v4);
    g.fill((mx + 5), (my + 5), (mx + 7), (my + 7), v4);
    g.fill((mx + 7), (my + 7), (mx + 9), (my + 9), v4);
    return;
}

public static void drawEmote(client.gui.GuiGraphicsExtractor g, seventv.Emote e, int bx, int by, int bw, int bh) {
    v6 = seventv.EmoteTexture.get(e);
    if (v6.state == seventv.EmoteTexture$State.READY) goto L64;
    if (v6.state != seventv.EmoteTexture$State.FAILED) goto L34;
    goto L37;
    L34:
    L37:
    v7 = 553648127;
    g.fill((bx + 3), (by + 3), ((bx + bw) - 3), ((by + bh) - 3), v7);
    return;
    L64:
    v7 = v6.aspect();
    v8 = bw;
    v9 = bh;
    if (cmp(v7,1) <0) goto L104;
    v9 = Math.max(1, Math.round((((f)bw) / v7)));
    goto L119;
    L104:
    v8 = Math.max(1, Math.round((((f)bh) * v7)));
    L119:
    v10 = (bx + ((bw - v8) / 2));
    v11 = (by + ((bh - v9) / 2));
    v12 = v6.currentFrame();
    g.blit(client.renderer.RenderPipelines.GUI_TEXTURED, v6.id, v10, v11, 0, ((f)(v12 * v6.frameH)), v8, v9, v6.frameW, v6.frameH, v6.frameW, (v6.frameH * v6.frames));
    return;
}

// DECOMPILE ERROR in statusText: 'dict' object has no attribute 'op_type'
private Object firstResult() {
    v1 = this.rows.iterator();
    L10:
    if (v1.hasNext() ==0) goto L70;
    v2 = ((seventv.EmotePicker$Row)v1.next());
    if ((v2 instanceof seventv.EmotePicker$Line) ==0) goto L67;
    v3 = ((seventv.EmotePicker$Line)v2);
    if (v3.emotes().isEmpty() !=0) goto L67;
    return ((seventv.Emote)v3.emotes().get(0));
    L67:
    goto L10;
    L70:
    return null;
}

public boolean isInside(double mx, double a2) {
    if (this.open ==0) goto L76;
    if (this.resizing !=0) goto L72;
    if (cmp(mx,((d)(this.x - 1))) <0) goto L76;
    if (cmp(mx,((d)((this.x + this.w) + 1))) >=0) goto L76;
    if (cmp(v3,((d)(this.y - 1))) <0) goto L76;
    if (cmp(v3,((d)((this.y + this.h) + 1))) >=0) goto L76;
    L72:
    goto L77;
    L76:
    L77:
    return 0;
}

public void mouseClicked(client.input.MouseButtonEvent event, boolean doubleClick) {
    v3 = event.x();
    v5 = event.y();
    v7 = event.button();
    if ((event.modifiers() & 3) ==0) goto L30;
    goto L31;
    L30:
    L31:
    v8 = 0;
    if (this.open !=0) goto L42;
    return 0;
    L42:
    if (this.isInside(v3, v5) !=0) goto L58;
    this.close();
    return 0;
    L58:
    if (v7 != 1) goto L81;
    if (this.overGrip(v3, v5) ==0) goto L81;
    this.resizing = 1;
    return 1;
    L81:
    if (this.overSearch(v3, v5) ==0) goto L138;
    this.search.setFocused(1);
    this.search.mouseClicked(this.searchEvent(v3, event.buttonInfo()), doubleClick);
    if (v7 != 1) goto L136;
    this.selectingText = 1;
    this.selectButton = event.buttonInfo();
    L136:
    return 1;
    L138:
    v9 = (this.x + 14);
    v10 = seventv.EmotePicker$Tab.values();
    v11 = v10.length;
    v12 = 0;
    L160:
    if (v12 >= v11) goto L290;
    v13 = v10[v12];
    v14 = (this.mc.font.width(v13.label) + 10);
    if (cmp(v3,((d)v9)) <0) goto L275;
    if (cmp(v3,((d)(v9 + v14))) >=0) goto L275;
    if (cmp(v5,((d)(this.y + 4))) <0) goto L275;
    if (cmp(v5,((d)(this.y + 17))) >=0) goto L275;
    if (this.tab == v13) goto L273;
    this.tab = v13;
    this.scroll = 0;
    this.jumpTarget = null;
    this.loadMore(this.requestId);
    L273:
    return 1;
    L275:
    v9 = (v9 + (v14 + 4));
    v12 = v12 + 1;
    goto L160;
    L290:
    v10 = (this.gridY + 2);
    new String[3][0] = "freq";
    new String[3][1] = "7tv";
    new String[3][2] = "bttv";
    v11 = new String[3];
    v12 = v11.length;
    v13 = 0;
    L330:
    if (v13 >= v12) goto L446;
    v14 = v11[v13];
    if (this.sectionOffsets.containsKey(v14) !=0) goto L361;
    goto L440;
    L361:
    if (cmp(v3,((d)(this.x + 2))) <0) goto L437;
    if (cmp(v3,((d)(this.x + 20))) >=0) goto L437;
    if (cmp(v5,((d)v10)) <0) goto L437;
    if (cmp(v5,((d)(v10 + 18))) >=0) goto L437;
    this.scroll = ((d)((Integer)this.sectionOffsets.get(v14)).intValue());
    this.jumpTarget = v14;
    return 1;
    L437:
    v10 = v10 + 22;
    L440:
    v13 = v13 + 1;
    goto L330;
    L446:
    if (cmp(v3,((d)this.gridX)) <0) goto L663;
    if (cmp(v3,((d)(this.gridX + this.gridW))) >=0) goto L663;
    if (cmp(v5,((d)this.gridY)) <0) goto L663;
    if (cmp(v5,((d)(this.gridY + this.gridH))) >=0) goto L663;
    v11 = (this.gridY - ((i)this.scroll));
    v12 = this.rows.iterator();
    L521:
    if (v12.hasNext() ==0) goto L663;
    v13 = ((seventv.EmotePicker$Row)v12.next());
    if ((v13 instanceof seventv.EmotePicker$Header) ==0) goto L556;
    goto L558;
    L556:
    L558:
    v14 = 24;
    if ((v13 instanceof seventv.EmotePicker$Line) ==0) goto L653;
    v15 = ((seventv.EmotePicker$Line)v13);
    if (cmp(v5,((d)v11)) <0) goto L653;
    if (cmp(v5,((d)(v11 + v14))) >=0) goto L653;
    v16 = ((i)((v3 - ((d)this.gridX)) / 24.0));
    if (v16 <0) goto L653;
    if (v16 >= v15.emotes().size()) goto L653;
    this.pick(((seventv.Emote)v15.emotes().get(v16)), v8);
    return 1;
    L653:
    v11 = (v11 + v14);
    goto L521;
    L663:
    this.search.setFocused(1);
    return 1;
}

public void mouseReleased(int button) {
    if (this.selectingText ==0) goto L14;
    this.selectingText = 0;
    return 1;
    L14:
    if (this.resizing !=0) goto L23;
    return 0;
    L23:
    this.resizing = 0;
    seventv.EmoteConfig.save();
    return 1;
}

public boolean isResizing() {
    return this.resizing;
}

public boolean mouseScrolled(double mx, double a2, double my) {
    if (this.open ==0) goto L16;
    if (this.isInside(mx, my) !=0) goto L18;
    L16:
    return 0;
    L18:
    this.jumpTarget = null;
    this.scroll = (this.scroll - ((v5 * 24.0) * 1.5));
    if (cmp(this.scroll,0) >=0) goto L56;
    this.scroll = 0;
    L56:
    return 1;
}

public boolean keyPressed(client.input.KeyEvent e) {
    if (this.open !=0) goto L9;
    return 0;
    L9:
    v2 = e.key();
    if (v2 != 41) goto L26;
    this.close();
    return 1;
    L26:
    if (v2 == 40) goto L38;
    if (v2 != 88) goto L55;
    L38:
    v3 = this.firstResult();
    if (v3 == null) goto L53;
    this.pick(v3, 0);
    L53:
    return 1;
    L55:
    if (v2 != 43) goto L103;
    this.tab = seventv.EmotePicker$Tab.values()[((this.tab.ordinal() + 1) % seventv.EmotePicker$Tab.values().length)];
    this.scroll = 0;
    this.jumpTarget = null;
    this.loadMore(this.requestId);
    return 1;
    L103:
    this.search.keyPressed(e);
    return 1;
}

public boolean charTyped(client.input.CharacterEvent e) {
    if (this.open !=0) goto L9;
    return 0;
    L9:
    this.search.charTyped(e);
    return 1;
}

private void pick(seventv.Emote e, boolean keepOpen) {
    seventv.EmoteRegistry.bindName(e);
    seventv.EmoteConfig.recordUse(e);
    this.onPick.accept(e);
    if (keepOpen !=0) goto L26;
    this.close();
    L26:
    return;
}

private void lambda$loadMore$2(int rid, List list, Throwable err) {
    this.mc.execute(/*indy*/run(this, rid, err, list));
    return;
}

private boolean lambda$loadMore$3(int rid, Throwable err, List list) {
    if (rid == this.requestId) goto L9;
    return;
    L9:
    this.loadingBttv = 0;
    if (err == null) goto L39;
    this.errorBttv = 1;
    seventv.SevenTv.LOG.warn("BetterTTV konnte nicht geladen werden: {}", String.valueOf(err));
    return;
    L39:
    if (list.isEmpty() !=0) goto L58;
    if (this.activeQuery.isBlank() ==0) goto L63;
    L58:
    this.bttvMore = 0;
    L63:
    v4 = list.iterator();
    L71:
    if (v4.hasNext() ==0) goto L101;
    v5 = ((seventv.Emote)v4.next());
    seventv.EmoteRegistry.register(v5);
    goto L71;
    L101:
    this.bttv.addAll(list);
    return;
}

private void lambda$loadMore$0(int rid, List list, Throwable err) {
    this.mc.execute(/*indy*/run(this, rid, err, list));
    return;
}

private boolean lambda$loadMore$1(int rid, Throwable err, List list) {
    if (rid == this.requestId) goto L9;
    return;
    L9:
    this.loadingSeven = 0;
    if (err == null) goto L39;
    this.errorSeven = 1;
    seventv.SevenTv.LOG.warn("7TV konnte nicht geladen werden: {}", String.valueOf(err));
    return;
    L39:
    if (list.isEmpty() ==0) goto L53;
    this.sevenMore = 0;
    L53:
    v4 = list.iterator();
    L61:
    if (v4.hasNext() ==0) goto L91;
    v5 = ((seventv.Emote)v4.next());
    seventv.EmoteRegistry.register(v5);
    goto L61;
    L91:
    this.seven.addAll(list);
    return;
}

private static void lambda$preload$2(client.Minecraft mc, List list, Throwable err) {
    mc.execute(/*indy*/run(err, list));
    return;
}

private static void lambda$preload$3(Throwable err, List list) {
    seventv.EmotePicker.preloadingBttv = 0;
    if (err != null) goto L21;
    if (list == null) goto L21;
    if (list.isEmpty() ==0) goto L22;
    L21:
    return;
    L22:
    v2 = list.iterator();
    L29:
    if (v2.hasNext() ==0) goto L60;
    v3 = ((seventv.Emote)v2.next());
    seventv.EmoteRegistry.register(v3);
    seventv.EmoteTexture.get(v3);
    goto L29;
    L60:
    seventv.EmotePicker.preBttv = List.copyOf(list);
    seventv.EmotePicker.preAt = System.currentTimeMillis();
    seventv.SevenTv.LOG.info("Emote Chat: {} BTTV-Emotes vorgeladen", Integer.valueOf(list.size()));
    return;
}

private static void lambda$preload$0(client.Minecraft mc, List list, Throwable err) {
    mc.execute(/*indy*/run(err, list));
    return;
}

private static void lambda$preload$1(Throwable err, List list) {
    seventv.EmotePicker.preloadingSeven = 0;
    if (err != null) goto L21;
    if (list == null) goto L21;
    if (list.isEmpty() ==0) goto L22;
    L21:
    return;
    L22:
    v2 = list.iterator();
    L29:
    if (v2.hasNext() ==0) goto L60;
    v3 = ((seventv.Emote)v2.next());
    seventv.EmoteRegistry.register(v3);
    seventv.EmoteTexture.get(v3);
    goto L29;
    L60:
    seventv.EmotePicker.preSeven = List.copyOf(list);
    seventv.EmotePicker.preAt = System.currentTimeMillis();
    seventv.SevenTv.LOG.info("Emote Chat: {} 7TV-Emotes vorgeladen", Integer.valueOf(list.size()));
    return;
}

private static void lambda$get$0(seventv.Emote e) {
    return;
}

