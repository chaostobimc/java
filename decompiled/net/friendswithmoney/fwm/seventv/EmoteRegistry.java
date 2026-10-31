// class net/friendswithmoney/fwm/seventv/EmoteRegistry  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field BY_KEY Ljava/util/Map;;
field BY_NAME Ljava/util/Map;;
field NOT_FOUND Ljava/util/Set;;
field PENDING Ljava/util/Set;;
private void <init>() {
    return;
}

public static Object register(seventv.Emote e) {
    seventv.EmoteRegistry.BY_KEY.putIfAbsent(e.key(), e);
    return;
}

public static boolean bindName(seventv.Emote e) {
    seventv.EmoteRegistry.register(e);
    seventv.EmoteRegistry.BY_NAME.put(e.name(), e);
    seventv.EmoteRegistry.NOT_FOUND.remove(e.name());
    return;
}

public static Object byKey(String key) {
    return ((seventv.Emote)seventv.EmoteRegistry.BY_KEY.get(key));
}

public static Object byName(String name) {
    return ((seventv.Emote)seventv.EmoteRegistry.BY_NAME.get(name));
}

public static List startingWith(String prefix, int limit) {
    v2 = prefix.toLowerCase(Locale.ROOT);
    v3 = ?;
    v4 = seventv.EmoteRegistry.BY_KEY.values().iterator();
    L31:
    if (v4.hasNext() ==0) goto L134;
    v5 = ((seventv.Emote)v4.next());
    if (v5.name().toLowerCase(Locale.ROOT).startsWith(v2) !=0) goto L74;
    goto L31;
    L74:
    v6 = ((seventv.Emote)v3.get(v5.name()));
    if (v6 == null) goto L117;
    if (v6.provider() == seventv.Emote$Provider.SEVENTV) goto L131;
    if (v5.provider() != seventv.Emote$Provider.SEVENTV) goto L131;
    L117:
    v3.put(v5.name(), v5);
    L131:
    goto L31;
    L134:
    v4 = ?;
    v4.sort(Comparator.comparingInt(/*indy*/applyAsInt()).thenComparing(/*indy*/apply()));
    if (v4.size() <= limit) goto L197;
    goto L199;
    L197:
    L199:
    return v4;
}

public static boolean isKnownMissing(String name) {
    return seventv.EmoteRegistry.NOT_FOUND.contains(name);
}

public static concurrent.CompletableFuture resolveAsync(String name) {
    if (seventv.EmoteRegistry.BY_NAME.containsKey(name) !=0) goto L36;
    if (seventv.EmoteRegistry.NOT_FOUND.contains(name) !=0) goto L36;
    if (seventv.EmoteRegistry.PENDING.add(name) !=0) goto L37;
    L36:
    return;
    L37:
    seventv.EmoteApi.lookupByName(name).whenComplete(/*indy*/accept(name));
    return;
}

private static void lambda$resolveAsync$0(String name, seventv.Emote emote, Throwable err) {
    seventv.EmoteRegistry.PENDING.remove(name);
    if (err != null) goto L18;
    if (emote != null) goto L29;
    L18:
    seventv.EmoteRegistry.NOT_FOUND.add(name);
    return;
    L29:
    seventv.EmoteRegistry.register(emote);
    seventv.EmoteRegistry.BY_NAME.putIfAbsent(name, emote);
    seventv.EmoteTexture.get(emote);
    client.Minecraft.getInstance().execute(/*indy*/run());
    return;
}

private static int lambda$startingWith$0(seventv.Emote e) {
    return e.name().length();
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    seventv.EmoteRegistry.BY_KEY = ?;
    seventv.EmoteRegistry.BY_NAME = ?;
    seventv.EmoteRegistry.NOT_FOUND = concurrent.ConcurrentHashMap.newKeySet();
    seventv.EmoteRegistry.PENDING = concurrent.ConcurrentHashMap.newKeySet();
    return;
}

