// class net/friendswithmoney/fwm/chat/Messages  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
field SAVE_INTERVAL_MS J;
field conversations Ljava/util/List;;
field DUPLICATE_MS J;
field pattern Ljava/util/regex/Pattern;;
field patternSource Ljava/lang/String;;
field dirty Z;
field lastSave J;
field lastKey Ljava/lang/String;;
field lastKeyAt J;
field STRICT Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

private static Path file() {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-messages.json");
}

public static void load() {
    v0 = chat.Messages.file();
    if (Files.exists(v0, new LinkOption[0]) !=0) goto L16;
    return;
    L16:
    v1 = Files.readString(v0, StandardCharsets.UTF_8);
    v2 = ((List)chat.Messages.GSON.fromJson(v1, ?.getType()));
    chat.Messages.conversations.clear();
    if (v2 != null) goto L58;
    return;
    L58:
    v3 = v2.iterator();
    L65:
    if (v3.hasNext() ==0) goto L135;
    v4 = ((chat.Conversation)v3.next());
    if (v4 == null) goto L65;
    if (v4.partner != null) goto L101;
    goto L65;
    L101:
    if (v4.messages != null) goto L121;
    v4.messages = ?;
    L121:
    chat.Messages.conversations.add(v4);
    goto L65;
    L135:
    goto L158;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not read ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L158:
    return;
}

public static void tick(client.Minecraft client) {
    if (chat.Messages.dirty !=0) goto L7;
    return;
    L7:
    v1 = System.currentTimeMillis();
    if (cmp((v1 - chat.Messages.lastSave),15000) >=0) goto L24;
    return;
    L24:
    chat.Messages.save();
    return;
}

public static void save() {
    chat.Messages.dirty = 0;
    chat.Messages.lastSave = System.currentTimeMillis();
    v0 = chat.Messages.file();
    Files.createDirectories(v0.getParent(), new attribute.FileAttribute[0]);
    Files.writeString(v0, chat.Messages.GSON.toJson(chat.Messages.conversations), StandardCharsets.UTF_8, new OpenOption[0]);
    goto L72;
    v1 = /*catch Exception*/ ex;
    System.err.println('[FWM] Could not write ' + String.valueOf(v0) + ': ' + v1.getMessage());
    L72:
    return;
}

public static boolean isHidden(String partner) {
    if (partner != null) goto L6;
    return 0;
    L6:
    v1 = config.FwmConfig.INSTANCE.msgHidden.iterator();
    L18:
    if (v1.hasNext() ==0) goto L50;
    v2 = ((String)v1.next());
    if (v2.equalsIgnoreCase(partner) ==0) goto L47;
    return 1;
    L47:
    goto L18;
    L50:
    return 0;
}

public static void hide(String partner) {
    if (partner == null) goto L18;
    if (partner.isBlank() !=0) goto L18;
    if (chat.Messages.isHidden(partner) ==0) goto L19;
    L18:
    return;
    L19:
    config.FwmConfig.INSTANCE.msgHidden.add(partner);
    config.ConfigManager.save();
    return;
}

public static void unhide(String partner) {
    if (config.FwmConfig.INSTANCE.msgHidden.removeIf(/*indy*/test(partner)) ==0) goto L23;
    config.ConfigManager.save();
    L23:
    return;
}

public static void conversations() {
    v0 = ?;
    v0.removeIf(/*indy*/test());
    v0.sort(Comparator.comparingLong(/*indy*/applyAsLong()).reversed());
    return v0;
}

public static boolean find(String partner) {
    v1 = chat.Messages.conversations.iterator();
    L9:
    if (v1.hasNext() ==0) goto L44;
    v2 = ((chat.Conversation)v1.next());
    if (v2.partner.equalsIgnoreCase(partner) ==0) goto L41;
    return v2;
    L41:
    goto L9;
    L44:
    return null;
}

public static boolean unreadTotal() {
    v0 = 0;
    v1 = chat.Messages.conversations.iterator();
    L11:
    if (v1.hasNext() ==0) goto L50;
    v2 = ((chat.Conversation)v1.next());
    if (chat.Messages.isHidden(v2.partner) !=0) goto L47;
    v0 = (v0 + v2.unread);
    L47:
    goto L11;
    L50:
    return v0;
}

public static void markRead(chat.Conversation conversation) {
    if (conversation == null) goto L11;
    if (conversation.unread !=0) goto L12;
    L11:
    return;
    L12:
    conversation.unread = 0;
    chat.Messages.dirty = 1;
    return;
}

public static void forget(String partner) {
    if (partner != null) goto L5;
    return;
    L5:
    v1 = chat.Messages.conversations.removeIf(/*indy*/test(partner));
    if (v1 ==0) goto L31;
    chat.Messages.dirty = 1;
    chat.Messages.save();
    L31:
    return;
}

public static Object markAllRead() {
    v0 = 0;
    v1 = chat.Messages.conversations.iterator();
    L11:
    if (v1.hasNext() ==0) goto L50;
    v2 = ((chat.Conversation)v1.next());
    if (v2.unread !=0) goto L40;
    goto L11;
    L40:
    v2.unread = 0;
    v0 = 1;
    goto L11;
    L50:
    if (v0 ==0) goto L58;
    chat.Messages.dirty = 1;
    L58:
    return;
}

public static boolean isIgnored(String partner) {
    if (partner != null) goto L6;
    return 0;
    L6:
    v1 = config.FwmConfig.INSTANCE.msgIgnored.iterator();
    L18:
    if (v1.hasNext() ==0) goto L50;
    v2 = ((String)v1.next());
    if (partner.equalsIgnoreCase(v2) ==0) goto L47;
    return 1;
    L47:
    goto L18;
    L50:
    return 0;
}

public static void ignore(String partner) {
    if (partner == null) goto L18;
    if (partner.isBlank() !=0) goto L18;
    if (chat.Messages.isIgnored(partner) ==0) goto L19;
    L18:
    return;
    L19:
    config.FwmConfig.INSTANCE.msgIgnored.add(partner);
    config.ConfigManager.save();
    chat.Messages.send(config.FwmConfig.INSTANCE.msgIgnoreCommand, partner, "");
    v1 = chat.Messages.find(partner);
    if (v1 == null) goto L60;
    chat.Messages.markRead(v1);
    L60:
    return;
}

public static void unignore(String partner) {
    if (partner != null) goto L5;
    return;
    L5:
    Objects.requireNonNull(partner);
    v1 = config.FwmConfig.INSTANCE.msgIgnored.removeIf(/*indy*/test(partner));
    if (v1 !=0) goto L33;
    return;
    L33:
    config.ConfigManager.save();
    chat.Messages.send(config.FwmConfig.INSTANCE.msgIgnoreCommand, partner, "");
    return;
}

public static void send(String partner, String text) {
    if (text == null) goto L11;
    if (text.isBlank() ==0) goto L12;
    L11:
    return;
    L12:
    chat.Messages.send(config.FwmConfig.INSTANCE.msgCommand, partner, text);
    return;
}

private static void send(String rawCommand, String partner, String text) {
    v3 = client.Minecraft.getInstance();
    if (v3.player != null) goto L12;
    return;
    L12:
    if (partner == null) goto L27;
    if (partner.isBlank() !=0) goto L27;
    if (rawCommand != null) goto L28;
    L27:
    return;
    L28:
    v4 = rawCommand.trim();
    if (v4.startsWith("/") ==0) goto L53;
    v4 = v4.substring(1);
    L53:
    if (v4.isEmpty() ==0) goto L62;
    return;
    L62:
    v5 = v4 + ' ' + partner;
    if (text.isBlank() !=0) goto L92;
    v5 = v5 + ' ' + text.trim();
    L92:
    v3.player.connection.sendCommand(v5);
    return;
}

public static void accept(network.chat.Component message) {
    if (message == null) goto L13;
    if (config.Features.isOn("fwm.tab.messages") !=0) goto L14;
    L13:
    return;
    L14:
    v1 = chat.Messages.parse(message);
    if (v1 != null) goto L24;
    return;
    L24:
    chat.Messages.record(v1.partner(), v1.text(), v1.sent());
    return;
}

public static void parse(network.chat.Component message) {
    if (message == null) goto L14;
    if (chat.ServerLine.tagged(message, "[Nachricht]") !=0) goto L16;
    L14:
    return null;
    L16:
    v1 = chat.Messages.STRICT.matcher(chat.ServerLine.plain(message));
    if (v1.matches() !=0) goto L36;
    return null;
    L36:
    if (v1.group(1) == null) goto L63;
    return ?;
    L63:
    return ?;
}

public static boolean involvesSelf(String from, String to) {
    if (chat.Messages.isSelf(from) == chat.Messages.isSelf(to)) goto L15;
    goto L16;
    L15:
    L16:
    return 0;
}

public static boolean isSelf(String name) {
    if (name == null) goto L11;
    if (name.isBlank() ==0) goto L13;
    L11:
    return 0;
    L13:
    v1 = config.FwmConfig.INSTANCE.msgSelfNames.iterator();
    L25:
    if (v1.hasNext() ==0) goto L57;
    v2 = ((String)v1.next());
    if (name.equalsIgnoreCase(v2) ==0) goto L54;
    return 1;
    L54:
    goto L25;
    L57:
    return name.equalsIgnoreCase(ui.FwmGfx.playerName());
}

private static void record(String partner, String text, boolean sent) {
    if (partner == null) goto L22;
    if (partner.isBlank() !=0) goto L22;
    if (text == null) goto L22;
    if (text.isBlank() ==0) goto L23;
    L22:
    return;
    L23:
    if (sent ==0) goto L33;
    goto L36;
    L33:
    L36:
    v3 = "<" + partner + ' ' + text;
    v4 = System.currentTimeMillis();
    if (v3.equals(chat.Messages.lastKey) ==0) goto L73;
    if (cmp((v4 - chat.Messages.lastKeyAt),250) >=0) goto L73;
    return;
    L73:
    chat.Messages.lastKey = v3;
    chat.Messages.lastKeyAt = v4;
    v6 = chat.Messages.find(partner);
    if (v6 != null) goto L114;
    v6 = ?;
    chat.Messages.conversations.add(v6);
    L114:
    if (chat.Messages.isIgnored(partner) !=0) goto L128;
    if (chat.Messages.isHidden(partner) ==0) goto L132;
    L128:
    goto L133;
    L132:
    L133:
    v7 = 0;
    if (v7 !=0) goto L164;
    goto L165;
    L164:
    L165:
    ?.add(config.FwmConfig.INSTANCE.msgHistoryPerContact, 1, 0);
    chat.Messages.prune();
    chat.Messages.dirty = 1;
    if (sent !=0) goto L189;
    if (v7 !=0) goto L189;
    ui.FwmHud.showMessage(partner, text);
    L189:
    return;
}

private static boolean prune() {
    v0 = config.FwmConfig.INSTANCE.msgContactLimit;
    L7:
    if (chat.Messages.conversations.size() <= v0) goto L90;
    v1 = ((chat.Conversation)chat.Messages.conversations.get(0));
    v2 = chat.Messages.conversations.iterator();
    L41:
    if (v2.hasNext() ==0) goto L77;
    v3 = ((chat.Conversation)v2.next());
    if (cmp(v3.lastActivity,v1.lastActivity) >=0) goto L74;
    v1 = v3;
    L74:
    goto L41;
    L77:
    chat.Messages.conversations.remove(v1);
    goto L7;
    L90:
    return;
}

public static regex.Pattern pattern() {
    v0 = config.FwmConfig.INSTANCE.msgPattern;
    if (chat.Messages.pattern == null) goto L23;
    if (v0.equals(chat.Messages.patternSource) !=0) goto L47;
    L23:
    chat.Messages.pattern = regex.Pattern.compile(v0);
    goto L43;
    v1 = /*catch Exception*/ ex;
    chat.Messages.pattern = regex.Pattern.compile("([.*]?[A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$");
    L43:
    chat.Messages.patternSource = v0;
    L47:
    return chat.Messages.pattern;
}

private static boolean lambda$forget$0(String partner, chat.Conversation conversation) {
    return partner.equalsIgnoreCase(conversation.partner);
}

private static long lambda$conversations$1(chat.Conversation c) {
    return c.lastActivity;
}

private static boolean lambda$conversations$0(chat.Conversation c) {
    return chat.Messages.isHidden(c.partner);
}

private static boolean lambda$unhide$0(String partner, String n) {
    return n.equalsIgnoreCase(partner);
}

static regex.Pattern <clinit>() {
    chat.Messages.GSON = ?.setPrettyPrinting().create();
    chat.Messages.conversations = ?;
    chat.Messages.STRICT = regex.Pattern.compile("^\\[Nachricht\\] (?:Du -> ([.*]?[A-Za-z0-9_]{2,16})|([.*]?[A-Za-z0-9_]{2,16}) -> Du): (.+)$");
    return;
}

