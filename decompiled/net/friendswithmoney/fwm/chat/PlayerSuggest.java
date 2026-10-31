// class net/friendswithmoney/fwm/chat/PlayerSuggest  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field NAME_MAX I;
field results Ljava/util/List;;
field waiting Z;
field lastQuery Ljava/lang/String;;
field pending Ljava/util/concurrent/CompletableFuture;;
private void <init>() {
    return;
}

public static boolean forQuery(String query) {
    if (query != null) goto L9;
    goto L13;
    L9:
    L13:
    v1 = query.trim();
    if (v1.equals(chat.PlayerSuggest.lastQuery) !=0) goto L32;
    chat.PlayerSuggest.lastQuery = v1;
    chat.PlayerSuggest.request(v1);
    L32:
    v2 = ?;
    v3 = chat.PlayerSuggest.fromPlayerList(v1).iterator();
    L53:
    if (v3.hasNext() ==0) goto L94;
    v4 = ((String)v3.next());
    if (chat.PlayerSuggest.containsIgnoreCase(v2, v4) !=0) goto L91;
    v2.add(v4);
    L91:
    goto L53;
    L94:
    return v2;
}

public static boolean waiting() {
    if (chat.PlayerSuggest.waiting ==0) goto L21;
    if (chat.PlayerSuggest.results.isEmpty() ==0) goto L21;
    goto L22;
    L21:
    L22:
    return 0;
}

public static List reset() {
    chat.PlayerSuggest.lastQuery = null;
    chat.PlayerSuggest.pending = null;
    chat.PlayerSuggest.waiting = 0;
    chat.PlayerSuggest.results = List.of();
    return;
}

private static List request(String query) {
    chat.PlayerSuggest.results = List.of();
    chat.PlayerSuggest.waiting = 0;
    v1 = client.Minecraft.getInstance();
    v2 = v1.getConnection();
    if (v2 == null) goto L30;
    if (query.isEmpty() ==0) goto L31;
    L30:
    return;
    L31:
    v3 = config.FwmConfig.INSTANCE.msgCommand.trim();
    if (v3.startsWith("/") ==0) goto L56;
    v3 = v3.substring(1);
    L56:
    v4 = '/' + v3 + ' ' + query;
    v5 = v2.getCommands();
    v6 = ?;
    v6.setCursor(1);
    v7 = v5.parse(v6, v2.getSuggestionsProvider());
    v8 = v5.getCompletionSuggestions(v7, v4.length());
    chat.PlayerSuggest.pending = v8;
    chat.PlayerSuggest.waiting = 1;
    if (config.FwmConfig.INSTANCE.msgDebug ==0) goto L182;
    v9 = v2.getCommands().getRoot().getChild(v3);
    if (v9 != null) goto L166;
    goto L171;
    L166:
    L171:
    v4.println('[FWM] asking "' + String.valueOf(v9) + '" | node=' + "-" + ' | children=' + String.valueOf(v9.getChildren()));
    L182:
    v8.thenAccept(/*indy*/accept(v8));
    goto L210;
    v5 = /*catch Exception*/ ex;
    chat.PlayerSuggest.results = List.of();
    chat.PlayerSuggest.waiting = 0;
    L210:
    return;
}

private static boolean containsIgnoreCase(List names, String name) {
    v2 = names.iterator();
    L7:
    if (v2.hasNext() ==0) goto L39;
    v3 = ((String)v2.next());
    if (v3.equalsIgnoreCase(name) ==0) goto L36;
    return 1;
    L36:
    goto L7;
    L39:
    return 0;
}

private static void accept(concurrent.CompletableFuture future, brigadier.suggestion.Suggestions suggestions) {
    if (future == chat.PlayerSuggest.pending) goto L8;
    return;
    L8:
    chat.PlayerSuggest.waiting = 0;
    if (suggestions != null) goto L17;
    return;
    L17:
    v2 = ui.FwmGfx.playerName();
    v3 = ?;
    v4 = suggestions.getList().iterator();
    L40:
    if (v4.hasNext() ==0) goto L115;
    v5 = ((brigadier.suggestion.Suggestion)v4.next());
    v6 = v5.getText();
    if (chat.PlayerSuggest.looksLikeName(v6) !=0) goto L80;
    goto L40;
    L80:
    if (v6.equalsIgnoreCase(v2) ==0) goto L92;
    goto L40;
    L92:
    if (v3.contains(v6) !=0) goto L112;
    v3.add(v6);
    L112:
    goto L40;
    L115:
    chat.PlayerSuggest.results = List.copyOf(v3);
    if (config.FwmConfig.INSTANCE.msgDebug ==0) goto L232;
    v4 = ?;
    v5 = suggestions.getList().iterator();
    L151:
    if (v5.hasNext() ==0) goto L197;
    v6 = ((brigadier.suggestion.Suggestion)v5.next());
    v4.append(" [").append(v6.getText()).append(93);
    goto L151;
    L197:
    if (v4.length() !=0) goto L213;
    goto L215;
    L213:
    L215:
    " (none)".println('[FWM] answer:' + String.valueOf(v4) + ' | kept=' + String.valueOf(chat.PlayerSuggest.results));
    L232:
    return;
}

private static char looksLikeName(String text) {
    if (text == null) goto L20;
    if (text.isEmpty() !=0) goto L20;
    if (text.length() <= 16) goto L22;
    L20:
    return 0;
    L22:
    v1 = 0;
    L24:
    if (v1 >= text.length()) goto L98;
    v2 = text.charAt(v1);
    if (v2 == 95) goto L80;
    if (v2 < 48) goto L56;
    if (v2 <= 57) goto L80;
    L56:
    if (v2 < 97) goto L68;
    if (v2 <= 122) goto L80;
    L68:
    if (v2 < 65) goto L84;
    if (v2 > 90) goto L84;
    L80:
    goto L85;
    L84:
    L85:
    v3 = 0;
    if (v3 !=0) goto L92;
    return 0;
    L92:
    v1 = v1 + 1;
    goto L24;
    L98:
    return 1;
}

private static void fromPlayerList(String query) {
    v1 = client.Minecraft.getInstance();
    v2 = v1.getConnection();
    if (v2 == null) goto L20;
    if (query.isEmpty() ==0) goto L24;
    L20:
    return List.of();
    L24:
    v3 = ui.FwmGfx.playerName();
    v4 = query.toLowerCase(Locale.ROOT);
    v5 = ?;
    v6 = v2.getOnlinePlayers().iterator();
    L57:
    if (v6.hasNext() ==0) goto L160;
    v7 = ((client.multiplayer.PlayerInfo)v6.next());
    if (v7.getProfile() != null) goto L90;
    goto L57;
    L90:
    v8 = v7.getProfile().name();
    if (v8 == null) goto L57;
    if (v8.isBlank() ==0) goto L116;
    goto L57;
    L116:
    if (v8.equalsIgnoreCase(v3) ==0) goto L128;
    goto L57;
    L128:
    if (v8.toLowerCase(Locale.ROOT).contains(v4) !=0) goto L147;
    goto L57;
    L147:
    v5.add(v8);
    goto L57;
    L160:
    v5.sort(String.CASE_INSENSITIVE_ORDER);
    return v5;
}

private static void lambda$request$0(concurrent.CompletableFuture future, brigadier.suggestion.Suggestions suggestions) {
    chat.PlayerSuggest.accept(future, suggestions);
    return;
}

static List <clinit>() {
    chat.PlayerSuggest.results = List.of();
    return;
}

