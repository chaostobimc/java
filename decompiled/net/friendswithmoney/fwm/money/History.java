// class net/friendswithmoney/fwm/money/History  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MAX I;
field MERGE_MS J;
field GSON Lcom/google/gson/Gson;;
field entries Ljava/util/List;;
field loadedFor Ljava/lang/String;;
field dirty Z;
field lastSave J;
private void <init>() {
    return;
}

public static Object add(money.MoneyStore$Kind kind, long cents, String a2, String player, String item) {
    if (config.FwmConfig.INSTANCE.historyEnabled !=0) goto L10;
    return;
    L10:
    money.History.ensureLoaded();
    v6 = System.currentTimeMillis();
    if (kind.income ==0) goto L29;
    goto L31;
    L29:
    L31:
    v8 = (-cents);
    if (kind != money.MoneyStore$Kind.SELL) goto L144;
    if (config.FwmConfig.INSTANCE.historyMergeSell ==0) goto L144;
    if (money.History.entries.isEmpty() !=0) goto L144;
    v10 = ((money.History$Entry)money.History.entries.get((money.History.entries.size() - 1)));
    if (v10.kind() != money.MoneyStore$Kind.SELL) goto L144;
    if (cmp((v6 - v10.t),60000) >=0) goto L144;
    v10.c = (v10.c + v8);
    v10.n = (v10.n + 1);
    v10.t = v6;
    money.History.dirty = 1;
    return;
    L144:
    v10 = ?;
    v10.t = v6;
    v10.k = kind.ordinal();
    v10.c = v8;
    v10.p = player;
    v10.i = item;
    if (v5 != null) goto L200;
    goto L225;
    L200:
    if (v5.length() <= 200) goto L223;
    goto L225;
    L223:
    L225:
    v5.substring(0, 200).l = v5;
    money.History.entries.add(v10);
    L239:
    if (money.History.entries.size() <= 5000) goto L266;
    money.History.entries.remove(0);
    goto L239;
    L266:
    money.History.dirty = 1;
    return;
}

public static void newestFirst() {
    money.History.ensureLoaded();
    v0 = ?;
    Collections.reverse(v0);
    return v0;
}

public static void tick() {
    if (money.History.dirty ==0) goto L23;
    if (cmp((System.currentTimeMillis() - money.History.lastSave),60000) <=0) goto L23;
    money.History.save();
    L23:
    return;
}

public static void save() {
    if (money.History.dirty ==0) goto L12;
    if (money.History.loadedFor != null) goto L13;
    L12:
    return;
    L13:
    v0 = (System.currentTimeMillis() - (((l)config.FwmConfig.INSTANCE.historyDays) * 86400000));
    money.History.entries.removeIf(/*indy*/test(v0));
    v2 = money.History.file(money.History.loadedFor);
    Files.createDirectories(v2.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v2, money.History.GSON.toJson(money.History.entries), StandardCharsets.UTF_8, new OpenOption[0]);
    money.History.dirty = 0;
    money.History.lastSave = System.currentTimeMillis();
    goto L115;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write history: ' + v2.getMessage());
    L115:
    return;
}

private static void ensureLoaded() {
    v0 = money.MoneyStore.account();
    if (v0.equals(money.History.loadedFor) ==0) goto L15;
    return;
    L15:
    if (money.History.loadedFor == null) goto L24;
    money.History.save();
    L24:
    money.History.loadedFor = v0;
    money.History.entries = ?;
    v1 = money.History.file(v0);
    if (Files.exists(v1, new LinkOption[0]) ==0) goto L96;
    v2 = ((List)money.History.GSON.fromJson(Files.readString(v1, StandardCharsets.UTF_8), ?.getType()));
    if (v2 == null) goto L96;
    money.History.entries = ?;
    L96:
    goto L115;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read history: ' + v1.getMessage());
    L115:
    return;
}

private static Path file(String id) {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve('fwm-history-' + id + '.json');
}

private static boolean lambda$save$0(long cut, money.History$Entry a1) {
    if (cmp(v2.t,cut) >=0) goto L13;
    goto L14;
    L13:
    L14:
    return 0;
}

static void <clinit>() {
    money.History.GSON = ?.create();
    money.History.entries = ?;
    return;
}

