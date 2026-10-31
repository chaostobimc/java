// class net/friendswithmoney/fwm/chat/ChatRouter  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.message.v1.ClientReceiveMessageEvents.ALLOW_GAME.register(/*indy*/allowReceiveGameMessage());
    fabric.api.client.message.v1.ClientReceiveMessageEvents.MODIFY_GAME.register(/*indy*/modifyReceivedGameMessage());
    fabric.api.client.message.v1.ClientReceiveMessageEvents.ALLOW_CHAT.register(/*indy*/allowReceiveChatMessage());
    return;
}

public static void actionBar(network.chat.Component message) {
    if (message != null) goto L6;
    return 1;
    L6:
    stats.SellStats.acceptActionBar(message);
    money.MoneyEvents.accept(message, 1);
    if (money.MoneyEvents.shown(message, 1) !=0) goto L25;
    return 0;
    L25:
    chat.SecondChat.acceptActionBar(message);
    return 1;
}

private static boolean accept(network.chat.Component message, boolean signed) {
    if (signed !=0) goto L33;
    stats.SellStats.accept(message);
    auction.Auction.accept(message);
    game.TpaWatch.accept(message);
    money.MoneyEvents.accept(message, 0);
    game.AutoTpa.accept(message);
    auction.Giveaway.accept(message);
    chat.Messages.accept(message);
    L33:
    if (signed ==0) goto L39;
    return 1;
    L39:
    if (money.MoneyEvents.shown(message, 0) !=0) goto L49;
    return 0;
    L49:
    return chat.SecondChat.accept(chat.FakeTags.mark(message));
}

private static void lambda$register$2(network.chat.Component message, network.chat.PlayerChatMessage signedMessage, authlib.GameProfile sender, network.chat.ChatType$Bound params, Instant receptionTimestamp) {
    if (chat.ChatRouter.accept(message, 1) !=0) goto L10;
    return 0;
    L10:
    v5 = params.decorate(message);
    v6 = chat.FakeTags.mark(v5);
    if (v6 != v5) goto L33;
    return 1;
    L33:
    client.Minecraft.getInstance().gui.hud.getChat().addServerSystemMessage(v6);
    return 0;
}

private static network.chat.Component lambda$register$1(network.chat.Component message, boolean overlay) {
    if (overlay ==0) goto L8;
    goto L12;
    L8:
    L12:
    return chat.FakeTags.mark(message);
}

private static boolean lambda$register$0(network.chat.Component message, boolean overlay) {
    if (overlay ==0) goto L6;
    return 1;
    L6:
    return chat.ChatRouter.accept(message, 0);
}

