// class net/friendswithmoney/fwm/chat/SecondChatClicks  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static boolean handle(client.Minecraft client, client.gui.screens.ChatScreen screen, client.input.MouseButtonEvent click) {
    if (util.GLFW.button(click.button()) ==0) goto L12;
    return 0;
    L12:
    if (config.Features.isOn("fwm.tab.secondchat") !=0) goto L22;
    return 0;
    L22:
    if (client.player != null) goto L31;
    return 0;
    L31:
    v3 = ui.FwmHud.chatHits().iterator();
    L40:
    if (v3.hasNext() ==0) goto L193;
    v4 = ((ui.FwmHud$ChatHit)v3.next());
    if (cmp(click.x(),((d)v4.x())) <0) goto L40;
    if (cmp(click.x(),((d)(v4.x() + v4.width()))) <0) goto L97;
    goto L40;
    L97:
    if (cmp(click.y(),((d)v4.y())) <0) goto L40;
    if (cmp(click.y(),((d)(v4.y() + v4.height()))) <0) goto L134;
    goto L40;
    L134:
    v5 = (click.x() - ((d)v4.x()));
    v7 = chat.SecondChatClicks.styleAt(client.font, v4.text(), v5);
    if (chat.SecondChatClicks.run(client, screen, v7) ==0) goto L175;
    return 1;
    L175:
    return chat.SecondChatClicks.offerPlainCommand(client, screen, v4.text().getString(), v5);
    L193:
    return 0;
}

private static void offerPlainCommand(client.Minecraft client, client.gui.screens.ChatScreen screen, String line, double offset) {
    v5 = chat.SecondChatClicks.commandAt(line, 0);
    if (v5 >=0) goto L14;
    return 0;
    L14:
    v6 = v5;
    if (chat.SecondChatClicks.commandAt(line, (v5 + 1)) <0) goto L77;
    v7 = client.font.getSplitter().plainIndexAtWidth(line, ((i)offset), network.chat.Style.EMPTY);
    v6 = line.lastIndexOf(47, Math.max(0, Math.min(v7, (line.length() - 1))));
    if (v6 >=0) goto L77;
    return 0;
    L77:
    v7 = line.substring(v6).trim();
    if (v7.length() >= 2) goto L99;
    return 0;
    L99:
    screen.insertText(v7, 1);
    return 1;
}

private static boolean commandAt(String line, int from) {
    v2 = Math.max(0, from);
    L6:
    if (v2 >= (line.length() - 1)) goto L73;
    if (line.charAt(v2) == 47) goto L29;
    goto L67;
    L29:
    if (v2 <=0) goto L49;
    if (Character.isWhitespace(line.charAt((v2 - 1))) !=0) goto L49;
    goto L67;
    L49:
    if (Character.isLetter(line.charAt((v2 + 1))) !=0) goto L65;
    goto L67;
    L65:
    return v2;
    L67:
    v2 = v2 + 1;
    goto L6;
    L73:
    return m1;
}

private static void run(client.Minecraft client, client.gui.screens.ChatScreen screen, network.chat.Style style) {
    if (style != null) goto L6;
    return 0;
    L6:
    v3 = style.getClickEvent();
    if (v3 != null) goto L17;
    return 0;
    L17:
    if ((v3 instanceof network.chat.ClickEvent$RunCommand) ==0) goto L83;
    v4 = ((network.chat.ClickEvent$RunCommand)v3);
    v5 = v4.command();
    if (v5.startsWith("/") ==0) goto L55;
    v5 = v5.substring(1);
    L55:
    if (v5.isBlank() ==0) goto L65;
    return 0;
    L65:
    client.player.connection.sendCommand(v5);
    screen.onClose();
    return 1;
    L83:
    if ((v3 instanceof network.chat.ClickEvent$SuggestCommand) ==0) goto L108;
    v4 = ((network.chat.ClickEvent$SuggestCommand)v3);
    screen.insertText(v4.command(), 1);
    return 1;
    L108:
    return 0;
}

private static Object styleAt(client.gui.Font font, network.chat.FormattedText line, double offset) {
    new float[1][0] = 0;
    v4 = new float[1];
    v5 = line.visit(/*indy*/accept(v4, font, offset), network.chat.Style.EMPTY);
    return ((network.chat.Style)v5.orElse(null));
}

private static Optional lambda$styleAt$0(float[] cursor, client.gui.Font font, double offset, network.chat.Style a3, String style) {
    cursor[0] = (cursor[0] + ((f)font.width(network.chat.Component.literal(v5).setStyle(style))));
    if (cmp(((d)cursor[0]),offset) <=0) goto L38;
    goto L41;
    L38:
    L41:
    return Optional.empty();
}

private static void lambda$register$0(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.ChatScreen) ==0) goto L16;
    v4 = ((client.gui.screens.ChatScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    fabric.api.client.screen.v1.ScreenMouseEvents.allowMouseClick(screen).register(/*indy*/allowMouseClick(client, v4));
    fabric.api.client.screen.v1.ScreenMouseEvents.allowMouseScroll(screen).register(/*indy*/allowMouseScroll());
    return;
}

private static boolean lambda$register$2(client.gui.screens.Screen ignored, double mouseX, double a2, double mouseY, double a4) {
    if (ui.FwmHud.scrollSecondChat(mouseX, mouseY, v7) !=0) goto L14;
    goto L15;
    L14:
    L15:
    return 0;
}

private static boolean lambda$register$1(client.Minecraft client, client.gui.screens.ChatScreen chat, client.gui.screens.Screen ignored, client.input.MouseButtonEvent click) {
    if (chat.SecondChatClicks.handle(client, chat, click) !=0) goto L13;
    goto L14;
    L13:
    L14:
    return 0;
}

