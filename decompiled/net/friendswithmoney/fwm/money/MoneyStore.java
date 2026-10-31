// class net/friendswithmoney/fwm/money/MoneyStore  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field KEEP_DAYS I;
field SESSION_GAP_MS J;
field GSON Lcom/google/gson/Gson;;
field data Lnet/friendswithmoney/fwm/money/MoneyStore$Data;;
field loadedFor Ljava/lang/String;;
field dirty Z;
field lastSave J;
field sessionCents J;
field sessionLeftAt J;
field inSession Z;
private void <init>() {
    return;
}

public static int book(money.MoneyStore$Kind kind, long cents) {
    if (cmp(cents,0) >0) goto L7;
    return;
    L7:
    money.MoneyStore.ensureLoaded();
    v3 = (([J)money.MoneyStore.data.days.computeIfAbsent(money.MoneyStore.today(), /*indy*/apply()));
    if (v3.length >= money.MoneyStore$Kind.values().length) goto L79;
    v4 = new long[money.MoneyStore$Kind.values().length];
    System.arraycopy(v3, 0, v4, 0, v3.length);
    v3 = v4;
    money.MoneyStore.data.days.put(money.MoneyStore.today(), v3);
    L79:
    v3[kind.ordinal()] = (v3[kind.ordinal()] + cents);
    if (kind.income ==0) goto L103;
    goto L105;
    L103:
    L105:
    money.MoneyStore.sessionCents = (cents + (-cents));
    money.MoneyStore.dirty = 1;
    return;
}

public static long user() {
    return money.MoneyStore.sessionCents;
}

public static void day(LocalDate date) {
    money.MoneyStore.ensureLoaded();
    v1 = (([J)money.MoneyStore.data.days.get(date.toString()));
    v2 = new long[money.MoneyStore$Kind.values().length];
    if (v1 == null) goto L47;
    System.arraycopy(v1, 0, v2, 0, Math.min(v1.length, v2.length));
    L47:
    return v2;
}

public static int net(long[] day) {
    v1 = 0;
    v3 = money.MoneyStore$Kind.values();
    v4 = v3.length;
    v5 = 0;
    L13:
    if (v5 >= v4) goto L61;
    v6 = v3[v5];
    if (v6.income ==0) goto L45;
    goto L53;
    L45:
    L53:
    v1 = (day[v6.ordinal()] + (-day[v6.ordinal()]));
    v5 = v5 + 1;
    goto L13;
    L61:
    return v1;
}

public static int income(long[] day) {
    v1 = 0;
    v3 = money.MoneyStore$Kind.values();
    v4 = v3.length;
    v5 = 0;
    L13:
    if (v5 >= v4) goto L50;
    v6 = v3[v5];
    if (v6.income ==0) goto L44;
    v1 = (v1 + day[v6.ordinal()]);
    L44:
    v5 = v5 + 1;
    goto L13;
    L50:
    return v1;
}

public static int expenses(long[] day) {
    v1 = 0;
    v3 = money.MoneyStore$Kind.values();
    v4 = v3.length;
    v5 = 0;
    L13:
    if (v5 >= v4) goto L50;
    v6 = v3[v5];
    if (v6.income !=0) goto L44;
    v1 = (v1 + day[v6.ordinal()]);
    L44:
    v5 = v5 + 1;
    goto L13;
    L50:
    return v1;
}

public static long todayNet() {
    return money.MoneyStore.net(money.MoneyStore.day(LocalDate.now()));
}

public static String claimGoal() {
    money.MoneyStore.ensureLoaded();
    if (money.MoneyStore.today().equals(money.MoneyStore.data.goalNotified) ==0) goto L20;
    return 0;
    L20:
    money.MoneyStore.data.goalNotified = money.MoneyStore.today();
    money.MoneyStore.dirty = 1;
    return 1;
}

public static long joined() {
    if (money.MoneyStore.inSession !=0) goto L24;
    if (cmp((System.currentTimeMillis() - money.MoneyStore.sessionLeftAt),300000) <=0) goto L24;
    money.MoneyStore.sessionCents = 0;
    L24:
    money.MoneyStore.inSession = 1;
    return;
}

public static void left() {
    if (money.MoneyStore.inSession !=0) goto L7;
    return;
    L7:
    money.MoneyStore.inSession = 0;
    money.MoneyStore.sessionLeftAt = System.currentTimeMillis();
    money.MoneyStore.save();
    return;
}

public static void tick() {
    if (money.MoneyStore.dirty ==0) goto L23;
    if (cmp((System.currentTimeMillis() - money.MoneyStore.lastSave),60000) <=0) goto L23;
    money.MoneyStore.save();
    L23:
    return;
}

public static void save() {
    if (money.MoneyStore.dirty ==0) goto L12;
    if (money.MoneyStore.loadedFor != null) goto L13;
    L12:
    return;
    L13:
    v0 = LocalDate.now().minusDays(60).toString();
    v1 = ?;
    v2 = money.MoneyStore.data.days.keySet().iterator();
    L51:
    if (v2.hasNext() ==0) goto L89;
    v3 = ((String)v2.next());
    if (v3.compareTo(v0) >=0) goto L86;
    v1.add(v3);
    L86:
    goto L51;
    L89:
    Objects.requireNonNull(money.MoneyStore.data.days);
    v1.forEach(/*indy*/accept(money.MoneyStore.data.days));
    v2 = money.MoneyStore.file(money.MoneyStore.loadedFor);
    Files.createDirectories(v2.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v2, money.MoneyStore.GSON.toJson(money.MoneyStore.data), StandardCharsets.UTF_8, new OpenOption[0]);
    money.MoneyStore.dirty = 0;
    money.MoneyStore.lastSave = System.currentTimeMillis();
    goto L182;
    v2 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write money file: ' + v2.getMessage());
    L182:
    return;
}

private static void ensureLoaded() {
    v0 = money.MoneyStore.account();
    if (v0.equals(money.MoneyStore.loadedFor) ==0) goto L15;
    return;
    L15:
    if (money.MoneyStore.loadedFor == null) goto L24;
    money.MoneyStore.save();
    L24:
    money.MoneyStore.loadedFor = v0;
    money.MoneyStore.data = ?;
    v1 = money.MoneyStore.file(v0);
    if (Files.exists(v1, new LinkOption[0]) ==0) goto L113;
    v2 = ((money.MoneyStore$Data)money.MoneyStore.GSON.fromJson(Files.readString(v1, StandardCharsets.UTF_8), money.MoneyStore$Data.class));
    if (v2 == null) goto L113;
    if (v2.days != null) goto L95;
    v2.days = ?;
    L95:
    if (v2.goalNotified != null) goto L109;
    v2.goalNotified = "";
    L109:
    money.MoneyStore.data = v2;
    L113:
    goto L132;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read money file: ' + v1.getMessage());
    L132:
    return;
}

static String account() {
    v0 = client.Minecraft.getInstance().getGameProfile();
    if (v0 == null) goto L18;
    if (v0.id() != null) goto L24;
    L18:
    goto L31;
    L24:
    L31:
    return v0.id().toString();
}

private static Path file(String id) {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve('fwm-money-' + id + '.json');
}

static String today() {
    return LocalDate.now().toString();
}

private static money.MoneyStore$Kind[] lambda$book$0(String k) {
    return new long[money.MoneyStore$Kind.values().length];
}

static void <clinit>() {
    money.MoneyStore.GSON = ?.setPrettyPrinting().create();
    money.MoneyStore.data = ?;
    return;
}

