// class net/friendswithmoney/fwm/seventv/SevenTv  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field NAMESPACE Ljava/lang/String;;
field LOG Lorg/slf4j/Logger;;
field chatDirty Z;
field lastLoadedAt J;
field IN_CHAT Ljava/util/Set;;
field lastRefresh J;
field REFRESH_LINES I;
private void <init>() {
    return;
}

public static boolean on() {
    return config.Features.isOn("fwm.tab.emotes");
}

public static void register() {
    seventv.EmoteConfig.load();
    v0 = seventv.EmoteConfig.frequent(2147483647).iterator();
    L14:
    if (v0.hasNext() ==0) goto L40;
    v1 = ((seventv.Emote)v0.next());
    seventv.EmoteRegistry.bindName(v1);
    goto L14;
    L40:
    fabric.api.client.event.lifecycle.v1.ClientTickEvents.END_CLIENT_TICK.register(/*indy*/onEndTick());
    return;
}

public static void transform(network.chat.Component message) {
    if (seventv.SevenTv.on() !=0) goto L8;
    return message;
    L8:
    return seventv.EmoteText.transform(message);
    v1 = /*catch Throwable*/ ex;
    seventv.SevenTv.LOG.warn("Emote-Ersetzung fehlgeschlagen", v1);
    return message;
}

static boolean inChat(String key) {
    if (seventv.SevenTv.IN_CHAT.size() <= 2000) goto L22;
    seventv.SevenTv.IN_CHAT.clear();
    L22:
    seventv.SevenTv.IN_CHAT.add(key);
    return;
}

public static long onEmoteLoaded(String key) {
    if (seventv.SevenTv.IN_CHAT.contains(key) !=0) goto L13;
    return;
    L13:
    seventv.SevenTv.lastLoadedAt = System.currentTimeMillis();
    seventv.SevenTv.chatDirty = 1;
    return;
}

public static void requestChatRefresh() {
    seventv.SevenTv.chatDirty = 1;
    return;
}

private static void tick(client.gui.components.ChatComponent chat) {
    if (seventv.SevenTv.chatDirty !=0) goto L7;
    return;
    L7:
    v1 = System.currentTimeMillis();
    if (cmp((v1 - seventv.SevenTv.lastLoadedAt),150) <0) goto L35;
    if (cmp((v1 - seventv.SevenTv.lastRefresh),1000) >=0) goto L36;
    L35:
    return;
    L36:
    seventv.SevenTv.chatDirty = 0;
    seventv.SevenTv.lastRefresh = v1;
    v3 = ((mixin.ChatHudAccessor)chat);
    v4 = v3.fwm$messages();
    v5 = 0;
    L60:
    if (v5 >= Math.min(v4.size(), 120)) goto L156;
    v6 = ((client.multiplayer.chat.GuiMessage)v4.get(v5));
    v7 = seventv.EmoteText.transform(v6.content());
    if (v7 == v6.content()) goto L150;
    v4.set(v5, ?);
    L150:
    v5 = v5 + 1;
    goto L60;
    L156:
    v3.fwm$refresh();
    goto L177;
    v3 = /*catch Throwable*/ ex;
    seventv.SevenTv.LOG.warn("Chat-Aktualisierung fehlgeschlagen", v3);
    L177:
    return;
}

public static client.Minecraft mc() {
    return client.Minecraft.getInstance();
}

private static void lambda$register$0(client.Minecraft client) {
    if (client.gui == null) goto L20;
    seventv.SevenTv.tick(client.gui.hud.getChat());
    L20:
    return;
}

static concurrent.ConcurrentHashMap$KeySetView <clinit>() {
    seventv.SevenTv.LOG = org.slf4j.LoggerFactory.getLogger("FWM 7TV");
    seventv.SevenTv.IN_CHAT = concurrent.ConcurrentHashMap.newKeySet();
    return;
}

