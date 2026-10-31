// class net/friendswithmoney/fwm/seventv/EmoteSuggest  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field TYPING Ljava/util/regex/Pattern;;
field MAX I;
field ROW_H I;
field SEARCH_DELAY_MS J;
field prefix Ljava/lang/String;;
field dismissed Ljava/lang/String;;
field start I;
field shown Ljava/util/List;;
field selected I;
field searched Ljava/lang/String;;
field pending Ljava/lang/String;;
field pendingSince J;
field SEARCHED Ljava/util/Set;;
field boxX I;
field boxY I;
field boxW I;
private void <init>() {
    return;
}

public static boolean active() {
    if (seventv.EmoteSuggest.shown.isEmpty() !=0) goto L15;
    goto L16;
    L15:
    L16:
    return 0;
}

public static int update(client.gui.components.EditBox field) {
    v1 = field.getValue();
    v2 = Math.min(field.getCursorPosition(), v1.length());
    v3 = seventv.EmoteSuggest.TYPING.matcher(v1.substring(0, v2));
    if (v3.find() !=0) goto L41;
    seventv.EmoteSuggest.clear();
    return;
    L41:
    v4 = v3.group(1);
    if (v4.equals(seventv.EmoteSuggest.dismissed) ==0) goto L66;
    seventv.EmoteSuggest.shown = List.of();
    return;
    L66:
    seventv.EmoteSuggest.dismissed = null;
    if (v4.equals(seventv.EmoteSuggest.prefix) !=0) goto L101;
    seventv.EmoteSuggest.prefix = v4;
    seventv.EmoteSuggest.selected = 0;
    seventv.EmoteSuggest.pending = v4;
    seventv.EmoteSuggest.pendingSince = System.currentTimeMillis();
    L101:
    seventv.EmoteSuggest.start = v3.start();
    v5 = v4.toLowerCase(Locale.ROOT);
    if (v5.equals(seventv.EmoteSuggest.searched) !=0) goto L178;
    if (cmp((System.currentTimeMillis() - seventv.EmoteSuggest.pendingSince),250) <=0) goto L178;
    if (seventv.EmoteSuggest.SEARCHED.add(v5) ==0) goto L178;
    seventv.EmoteSuggest.searched = v5;
    seventv.EmoteApi.searchSevenTv(v4, 1, 25).whenComplete(/*indy*/accept());
    L178:
    v6 = ?;
    v7 = ?;
    v8 = seventv.EmoteConfig.frequent(200).iterator();
    L209:
    if (v8.hasNext() ==0) goto L284;
    v9 = ((seventv.Emote)v8.next());
    if (v9.name().toLowerCase(Locale.ROOT).startsWith(v5) ==0) goto L281;
    if (v6.add(v9.name().toLowerCase(Locale.ROOT)) ==0) goto L281;
    v7.add(v9);
    L281:
    goto L209;
    L284:
    v8 = seventv.EmoteRegistry.startingWith(v4, 16).iterator();
    L298:
    if (v8.hasNext() ==0) goto L369;
    v9 = ((seventv.Emote)v8.next());
    if (v7.size() < 8) goto L335;
    goto L369;
    L335:
    if (v6.add(v9.name().toLowerCase(Locale.ROOT)) ==0) goto L366;
    v7.add(v9);
    L366:
    goto L298;
    L369:
    if (v7.size() <= 8) goto L394;
    goto L396;
    L394:
    L396:
    seventv.EmoteSuggest.shown = v7;
    if (seventv.EmoteSuggest.selected < seventv.EmoteSuggest.shown.size()) goto L417;
    seventv.EmoteSuggest.selected = 0;
    L417:
    return;
}

public static List clear() {
    seventv.EmoteSuggest.prefix = null;
    seventv.EmoteSuggest.shown = List.of();
    seventv.EmoteSuggest.selected = 0;
    return;
}

public static void render(client.gui.GuiGraphicsExtractor g, client.gui.components.EditBox field, int mouseX, int mouseY) {
    if (seventv.EmoteSuggest.shown.isEmpty() ==0) goto L12;
    return;
    L12:
    v4 = client.Minecraft.getInstance().font;
    v5 = 0;
    v6 = seventv.EmoteSuggest.shown.iterator();
    L33:
    if (v6.hasNext() ==0) goto L75;
    v7 = ((seventv.Emote)v6.next());
    v5 = Math.max(v5, v4.width(v7.chatCode()));
    goto L33;
    L75:
    seventv.EmoteSuggest.boxW = (v5 + 22);
    v6 = field.getValue().substring(0, Math.min(seventv.EmoteSuggest.start, field.getValue().length()));
    seventv.EmoteSuggest.boxX = Math.min(((field.getX() + v4.width(v6)) - 1), ((g.guiWidth() - seventv.EmoteSuggest.boxW) - 2));
    seventv.EmoteSuggest.boxY = ((field.getY() - 4) - (seventv.EmoteSuggest.shown.size() * 12));
    g.nextStratum();
    g.fill(seventv.EmoteSuggest.boxX, seventv.EmoteSuggest.boxY, (seventv.EmoteSuggest.boxX + seventv.EmoteSuggest.boxW), (seventv.EmoteSuggest.boxY + (seventv.EmoteSuggest.shown.size() * 12)), -805306368);
    v7 = 0;
    L198:
    if (v7 >= seventv.EmoteSuggest.shown.size()) goto L381;
    v8 = ((seventv.Emote)seventv.EmoteSuggest.shown.get(v7));
    v9 = (seventv.EmoteSuggest.boxY + (v7 * 12));
    if (mouseX < seventv.EmoteSuggest.boxX) goto L274;
    if (mouseX >= (seventv.EmoteSuggest.boxX + seventv.EmoteSuggest.boxW)) goto L274;
    if (mouseY < v9) goto L274;
    if (mouseY >= (v9 + 12)) goto L274;
    goto L275;
    L274:
    L275:
    v10 = 0;
    if (v10 ==0) goto L287;
    seventv.EmoteSuggest.selected = v7;
    L287:
    if (v7 != seventv.EmoteSuggest.selected) goto L318;
    g.fill(seventv.EmoteSuggest.boxX, v9, (seventv.EmoteSuggest.boxX + seventv.EmoteSuggest.boxW), (v9 + 12), 1090519039);
    L318:
    seventv.EmotePicker.drawEmote(g, v8, (seventv.EmoteSuggest.boxX + 2), (v9 + 1), 10, 10);
    if (v7 != seventv.EmoteSuggest.selected) goto L369;
    goto L371;
    L369:
    L371:
    v4.text(v8.chatCode(), (seventv.EmoteSuggest.boxX + 16), (v9 + 2), -171, -5592406, 1);
    v7 = v7 + 1;
    goto L198;
    L381:
    return;
}

// DECOMPILE ERROR in keyPressed: 'dict' object has no attribute 'op_type'
public static void mouseClicked(client.gui.components.EditBox field, double mx, double a2) {
    if (seventv.EmoteSuggest.shown.isEmpty() !=0) goto L63;
    if (cmp(mx,((d)seventv.EmoteSuggest.boxX)) <0) goto L63;
    if (cmp(mx,((d)(seventv.EmoteSuggest.boxX + seventv.EmoteSuggest.boxW))) >=0) goto L63;
    if (cmp(v3,((d)seventv.EmoteSuggest.boxY)) <0) goto L63;
    if (cmp(v3,((d)(seventv.EmoteSuggest.boxY + (seventv.EmoteSuggest.shown.size() * 12)))) <0) goto L65;
    L63:
    return 0;
    L65:
    seventv.EmoteSuggest.accept(field, ((seventv.Emote)seventv.EmoteSuggest.shown.get(((i)((v3 - ((d)seventv.EmoteSuggest.boxY)) / 12.0)))));
    return 1;
}

private static void accept(client.gui.components.EditBox field, seventv.Emote e) {
    if (seventv.EmoteText.roomFor(field.getValue()) !=0) goto L14;
    seventv.EmoteSuggest.clear();
    return;
    L14:
    v2 = field.getValue();
    v3 = Math.min(field.getCursorPosition(), v2.length());
    v4 = e.chatCode() + ' ';
    v5 = v2.substring(0, seventv.EmoteSuggest.start) + v4 + v2.substring(v3);
    if (v5.length() <= 256) goto L76;
    return;
    L76:
    field.setValue(v5);
    field.moveCursorTo((seventv.EmoteSuggest.start + v4.length()), 0);
    seventv.EmoteRegistry.bindName(e);
    seventv.EmoteConfig.recordUse(e);
    seventv.EmoteSuggest.clear();
    return;
}

private static void lambda$update$0(List list, Throwable err) {
    if (list == null) goto L37;
    v2 = list.iterator();
    L11:
    if (v2.hasNext() ==0) goto L37;
    v3 = ((seventv.Emote)v2.next());
    seventv.EmoteRegistry.register(v3);
    goto L11;
    L37:
    return;
}

static void <clinit>() {
    seventv.EmoteSuggest.TYPING = regex.Pattern.compile(":([A-Za-z0-9_\\-!?.'()]{2,40})$");
    seventv.EmoteSuggest.shown = List.of();
    seventv.EmoteSuggest.searched = "";
    seventv.EmoteSuggest.pending = "";
    seventv.EmoteSuggest.SEARCHED = ?;
    return;
}

