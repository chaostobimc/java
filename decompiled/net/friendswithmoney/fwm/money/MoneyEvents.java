// class net/friendswithmoney/fwm/money/MoneyEvents  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field DUPLICATE_MS J;
field SIDEBAR_SETTLE_TICKS I;
field DOLLAR Ljava/util/regex/Pattern;;
field ORDER Ljava/util/regex/Pattern;;
field AH_SOLD Ljava/util/regex/Pattern;;
field ORDER_FILLED Ljava/util/regex/Pattern;;
field ORDER_PICKED Ljava/util/regex/Pattern;;
field ORDER_DELIVERED Ljava/util/regex/Pattern;;
field AH_BOUGHT Ljava/util/regex/Pattern;;
field SELLER Ljava/util/regex/Pattern;;
field PRICE_LINE Ljava/util/regex/Pattern;;
field ANY_DOLLAR Ljava/util/regex/Pattern;;
field OFFER_MS J;
field pendingOffer Lnet/friendswithmoney/fwm/money/MoneyEvents$Offer;;
field FEE_LINE Ljava/util/regex/Pattern;;
field AH_CREATED Ljava/util/regex/Pattern;;
field ORDER_CREATED Ljava/util/regex/Pattern;;
field pendingFee Lnet/friendswithmoney/fwm/money/MoneyEvents$Fee;;
field BALANCE_WORDS Ljava/util/List;;
field PATTERNS Ljava/util/Map;;
field lastKey Ljava/lang/String;;
field lastAt J;
field sidebarBase J;
field chatAtBase J;
field chatTotal J;
field sidebarSeen J;
field sidebarStill I;
field sidebarTimer I;
field connection Lnet/minecraft/client/multiplayer/ClientPacketListener;;
field goalChecked Z;
public static boolean isMarketLine(String plain) {
    if (plain != null) goto L6;
    return 0;
    L6:
    if (money.MoneyEvents.AH_BOUGHT.matcher(plain).find() !=0) goto L71;
    if (money.MoneyEvents.AH_CREATED.matcher(plain).find() !=0) goto L71;
    if (money.MoneyEvents.ORDER_CREATED.matcher(plain).find() !=0) goto L71;
    if (money.MoneyEvents.ORDER_DELIVERED.matcher(plain).find() !=0) goto L71;
    if (money.MoneyEvents.ORDER_PICKED.matcher(plain).find() ==0) goto L75;
    L71:
    goto L76;
    L75:
    L76:
    return 0;
}

public static boolean shown(network.chat.Component message, boolean actionBar) {
    if (message != null) goto L6;
    return 1;
    L6:
    v2 = config.FwmConfig.INSTANCE;
    v3 = chat.ServerLine.plain(message).trim();
    if (actionBar ==0) goto L68;
    if (v2.moneyShowAhSold !=0) goto L44;
    if (money.MoneyEvents.AH_SOLD.matcher(v3).find() ==0) goto L44;
    return 0;
    L44:
    if (v2.moneyShowOrderFilled !=0) goto L66;
    if (money.MoneyEvents.ORDER_FILLED.matcher(v3).find() ==0) goto L66;
    return 0;
    L66:
    return 1;
    L68:
    if (v2.moneyShowAhBought !=0) goto L97;
    if (money.MoneyEvents.AH_BOUGHT.matcher(v3).find() ==0) goto L97;
    if (chat.ServerLine.fromServer(message) ==0) goto L97;
    return 0;
    L97:
    if (v2.moneyShowOrderDelivered !=0) goto L119;
    if (money.MoneyEvents.ORDER_DELIVERED.matcher(v3).find() ==0) goto L119;
    return 0;
    L119:
    return 1;
}

public static void offerClicked(world.item.ItemStack stack) {
    if (stack == null) goto L11;
    if (stack.isEmpty() ==0) goto L12;
    L11:
    return;
    L12:
    v1 = ((world.item.component.ItemLore)stack.get(core.component.DataComponents.LORE));
    if (v1 != null) goto L28;
    return;
    L28:
    v2 = null;
    v3 = -1;
    v5 = -1;
    v7 = v1.lines().iterator();
    L50:
    if (v7.hasNext() ==0) goto L232;
    v8 = ((network.chat.Component)v7.next());
    v9 = chat.ServerLine.strip(v8.getString());
    v10 = money.MoneyEvents.SELLER.matcher(v9);
    if (v10.find() ==0) goto L109;
    v2 = v10.group(1);
    L109:
    v11 = money.MoneyEvents.PRICE_LINE.matcher(v9);
    if (v11.find() ==0) goto L168;
    if (cmp(v3,0) >=0) goto L168;
    if (v11.group(2) != null) goto L153;
    goto L159;
    L153:
    L159:
    v3 = auction.Money.cents("" + v11.group(2));
    L168:
    v12 = money.MoneyEvents.ANY_DOLLAR.matcher(v9);
    if (v12.find() ==0) goto L229;
    if (cmp(v5,0) >=0) goto L229;
    if (v12.group(2) != null) goto L213;
    goto L219;
    L213:
    L219:
    v5 = auction.Money.cents("" + v12.group(2));
    L229:
    goto L50;
    L232:
    if (v2 != null) goto L237;
    return;
    L237:
    if (cmp(v3,0) <=0) goto L247;
    goto L249;
    L247:
    L249:
    v7 = v5;
    if (cmp(v7,0) >0) goto L259;
    return;
    L259:
    if (stack.getCount() <= 1) goto L279;
    goto L281;
    L279:
    L281:
    v9 = "1x " + chat.ServerLine.strip(stack.getHoverName().getString());
    money.MoneyEvents.pendingOffer = ?;
    return;
}

public static void menuClicked(world.inventory.AbstractContainerMenu handler) {
    if (handler != null) goto L5;
    return;
    L5:
    v1 = handler.slots.iterator();
    L13:
    if (v1.hasNext() ==0) goto L220;
    v2 = ((world.inventory.Slot)v1.next());
    if ((v2.container instanceof world.entity.player.Inventory) ==0) goto L45;
    goto L13;
    L45:
    v3 = v2.getItem();
    if (v3.isEmpty() ==0) goto L60;
    goto L13;
    L60:
    v4 = ((world.item.component.ItemLore)v3.get(core.component.DataComponents.LORE));
    if (v4 != null) goto L80;
    goto L13;
    L80:
    v5 = v4.lines().iterator();
    L92:
    if (v5.hasNext() ==0) goto L217;
    v6 = ((network.chat.Component)v5.next());
    v7 = money.MoneyEvents.FEE_LINE.matcher(chat.ServerLine.strip(v6.getString()));
    if (v7.find() !=0) goto L143;
    goto L92;
    L143:
    if (v7.group(2) != null) goto L163;
    goto L169;
    L163:
    L169:
    v8 = auction.Money.cents("" + v7.group(2));
    if (cmp(v8,0) <=0) goto L216;
    money.MoneyEvents.pendingFee = ?;
    L216:
    return;
    L217:
    goto L13;
    L220:
    return;
}

private void <init>() {
    return;
}

public static boolean active(client.Minecraft client) {
    if (config.Features.isOn("fwm.tab.money") ==0) goto L26;
    if (client.player == null) goto L26;
    if (net.Presence.onHugo(client) ==0) goto L26;
    goto L27;
    L26:
    L27:
    return 0;
}

public static boolean accept(network.chat.Component message, boolean overlay) {
    v2 = client.Minecraft.getInstance();
    if (message == null) goto L15;
    if (money.MoneyEvents.active(v2) !=0) goto L16;
    L15:
    return;
    L16:
    v3 = chat.ServerLine.plain(message);
    if (v3.isEmpty() ==0) goto L29;
    return;
    L29:
    if (overlay !=0) goto L106;
    if (money.MoneyEvents.AH_BOUGHT.matcher(v3).find() ==0) goto L106;
    if (chat.ServerLine.fromServer(message) ==0) goto L106;
    if (money.MoneyEvents.pendingOffer == null) goto L101;
    if (cmp((System.currentTimeMillis() - money.MoneyEvents.pendingOffer.at()),60000) >=0) goto L101;
    money.MoneyEvents.book(money.MoneyStore$Kind.AH_BUY, money.MoneyEvents.pendingOffer.cents(), money.MoneyEvents.pendingOffer.seller(), money.MoneyEvents.pendingOffer.item(), v3);
    L101:
    money.MoneyEvents.pendingOffer = null;
    return;
    L106:
    if (overlay !=0) goto L191;
    if (money.MoneyEvents.AH_CREATED.matcher(v3).find() !=0) goto L136;
    if (money.MoneyEvents.ORDER_CREATED.matcher(v3).find() ==0) goto L191;
    L136:
    if (chat.ServerLine.fromServer(message) ==0) goto L191;
    if (money.MoneyEvents.pendingFee == null) goto L186;
    if (cmp((System.currentTimeMillis() - money.MoneyEvents.pendingFee.at()),60000) >=0) goto L186;
    money.MoneyEvents.book(money.MoneyStore$Kind.FEE, money.MoneyEvents.pendingFee.cents(), null, money.MoneyEvents.pendingFee.item(), v3);
    L186:
    money.MoneyEvents.pendingFee = null;
    return;
    L191:
    if (v3.indexOf(36) >=0) goto L201;
    return;
    L201:
    if (config.FwmConfig.INSTANCE.moneyLogLines ==0) goto L215;
    money.MoneyEvents.log(v3, overlay);
    L215:
    if (overlay ==0) goto L415;
    v4 = money.MoneyEvents.AH_SOLD.matcher(v3.trim());
    if (v4.find() ==0) goto L317;
    v5 = v4.group("unit");
    if (v5 != null) goto L270;
    goto L272;
    L270:
    L272:
    money.MoneyEvents.book(v4.group("amount"), auction.Money.cents("" + v5), v4.group("name"), money.MoneyEvents.clean(v4.group("count") + 'x ' + v4.group("item")), v3);
    return;
    L317:
    v5 = money.MoneyEvents.ORDER_FILLED.matcher(v3.trim());
    if (v5.find() ==0) goto L415;
    v6 = v5.group("unit");
    if (v6 != null) goto L368;
    goto L370;
    L368:
    L370:
    money.MoneyEvents.book(v5.group("amount"), auction.Money.cents("" + v6), v5.group("name"), money.MoneyEvents.clean(v5.group("count") + 'x ' + v5.group("item")), v3);
    return;
    L415:
    v4 = money.MoneyEvents.ORDER.matcher(v3.trim());
    if (v4.find() ==0) goto L483;
    v5 = v4.group("unit");
    if (v5 != null) goto L466;
    goto L468;
    L466:
    L468:
    money.MoneyEvents.book(v4.group("amount"), auction.Money.cents("" + v5), null, null, v3);
    return;
    L483:
    if (overlay !=0) goto L495;
    if (chat.ServerLine.fromServer(message) !=0) goto L495;
    return;
    L495:
    v5 = config.FwmConfig.INSTANCE;
    v6 = money.MoneyEvents.pattern(v5.sellMessagePattern).matcher(v3);
    if (v6.find() ==0) goto L541;
    money.MoneyEvents.book(money.MoneyStore$Kind.SELL, auction.Money.cents(v6.group(1)), null, null, v3);
    return;
    L541:
    if (money.MoneyEvents.tryList(v5.moneyPayOutPatterns, money.MoneyStore$Kind.PAY_OUT, v3) ==0) goto L557;
    return;
    L557:
    if (money.MoneyEvents.tryList(v5.moneyAhBuyPatterns, money.MoneyStore$Kind.AH_BUY, v3) ==0) goto L573;
    return;
    L573:
    if (money.MoneyEvents.tryList(v5.moneyAhSellPatterns, money.MoneyStore$Kind.AH_SELL, v3) ==0) goto L589;
    return;
    L589:
    money.MoneyEvents.tryList(v5.payPatterns, money.MoneyStore$Kind.PAY_IN, v3);
    return;
}

private static void tryList(List sources, money.MoneyStore$Kind kind, String line) {
    if (sources != null) goto L6;
    return 0;
    L6:
    v3 = sources.iterator();
    L13:
    if (v3.hasNext() ==0) goto L145;
    v4 = ((String)v3.next());
    v5 = money.MoneyEvents.pattern(v4).matcher(line);
    if (v5.find() !=0) goto L55;
    goto L13;
    L55:
    v6 = money.MoneyEvents.group(v5, "amount");
    if (v6 != null) goto L73;
    goto L13;
    L73:
    v7 = money.MoneyEvents.group(v5, "unit");
    if (v7 != null) goto L95;
    goto L97;
    L95:
    L97:
    v8 = auction.Money.cents("" + v7);
    if (cmp(v8,0) >0) goto L117;
    goto L13;
    L117:
    money.MoneyEvents.book(kind, v8, money.MoneyEvents.group(v5, "name"), money.MoneyEvents.clean(money.MoneyEvents.group(v5, "item")), line);
    return 1;
    L145:
    return 0;
}

private static void toast(money.MoneyStore$Kind kind, long cents, String a2, String player) {
    return;
}

private static void book(money.MoneyStore$Kind kind, long cents, String a2, String player, String item) {
    if (cmp(cents,0) >0) goto L7;
    return;
    L7:
    money.MoneyEvents.toast(kind, cents, player, item);
    v6 = System.currentTimeMillis();
    v8 = String.valueOf(kind) + ':' + cents + ':' + player;
    if (v8.equals(money.MoneyEvents.lastKey) ==0) goto L58;
    if (cmp((v6 - money.MoneyEvents.lastAt),1500) >=0) goto L58;
    return;
    L58:
    money.MoneyEvents.lastKey = v8;
    money.MoneyEvents.lastAt = v6;
    money.MoneyStore.book(kind, cents);
    money.History.add(kind, cents, player, item, v5);
    if (kind.income ==0) goto L97;
    goto L99;
    L97:
    L99:
    money.MoneyEvents.chatTotal = (cents + (-cents));
    money.MoneyEvents.goalChecked = 0;
    return;
}

public static void tick(client.Minecraft client) {
    v1 = client.getConnection();
    if (v1 == money.MoneyEvents.connection) goto L54;
    if (money.MoneyEvents.connection == null) goto L24;
    money.MoneyStore.left();
    money.History.save();
    L24:
    money.MoneyEvents.connection = v1;
    money.MoneyEvents.sidebarBase = -1;
    money.MoneyEvents.sidebarSeen = -1;
    if (v1 == null) goto L54;
    if (money.MoneyEvents.active(client) ==0) goto L54;
    money.MoneyStore.joined();
    L54:
    if (money.MoneyEvents.active(client) !=0) goto L62;
    return;
    L62:
    if (config.FwmConfig.INSTANCE.moneySidebarSync ==0) goto L93;
    money.MoneyEvents.sidebarTimer = (money.MoneyEvents.sidebarTimer + 1);
    if ((money.MoneyEvents.sidebarTimer + 1) < 10) goto L93;
    money.MoneyEvents.sidebarTimer = 0;
    money.MoneyEvents.sidebar(client);
    L93:
    if (money.MoneyEvents.goalChecked !=0) goto L107;
    money.MoneyEvents.goalChecked = 1;
    money.MoneyEvents.checkGoal(client);
    L107:
    money.MoneyStore.tick();
    money.History.tick();
    return;
}

private static void sidebar(client.Minecraft client) {
    v1 = money.MoneyEvents.readBalance(client);
    if (cmp(v1,0) >=0) goto L12;
    return;
    L12:
    if (cmp(v1,money.MoneyEvents.sidebarSeen) ==0) goto L29;
    money.MoneyEvents.sidebarSeen = v1;
    money.MoneyEvents.sidebarStill = 0;
    return;
    L29:
    money.MoneyEvents.sidebarStill = (money.MoneyEvents.sidebarStill + 10);
    if (money.MoneyEvents.sidebarStill >= 60) goto L47;
    return;
    L47:
    if (cmp(money.MoneyEvents.sidebarBase,0) >=0) goto L66;
    money.MoneyEvents.sidebarBase = v1;
    money.MoneyEvents.chatAtBase = money.MoneyEvents.chatTotal;
    return;
    L66:
    if (cmp(v1,money.MoneyEvents.sidebarBase) !=0) goto L85;
    if (cmp(money.MoneyEvents.chatTotal,money.MoneyEvents.chatAtBase) !=0) goto L85;
    return;
    L85:
    v3 = (money.MoneyEvents.sidebarBase + (money.MoneyEvents.chatTotal - money.MoneyEvents.chatAtBase));
    v5 = (v1 - v3);
    v7 = Math.max(100, (Math.abs(v1) / 500));
    if (cmp(Math.abs(v5),v7) <=0) goto L191;
    if (cmp(v5,0) <=0) goto L142;
    goto L145;
    L142:
    L145:
    v9 = money.MoneyStore$Kind.OTHER_OUT;
    money.MoneyStore.book(v9, Math.abs(v5));
    money.History.add(v9, Math.abs(v5), null, null, ui.Lang.t("Kontostand", "Balance") + ': ' + util.Fmt.money(v1));
    money.MoneyEvents.goalChecked = 0;
    L191:
    money.MoneyEvents.sidebarBase = v1;
    money.MoneyEvents.chatAtBase = money.MoneyEvents.chatTotal;
    return;
}

private static boolean readBalance(client.Minecraft client) {
    if (client.level != null) goto L11;
    return -1;
    L11:
    v1 = client.level.getScoreboard();
    v2 = v1.getDisplayObjective(world.scores.DisplaySlot.SIDEBAR);
    if (v2 != null) goto L35;
    return -1;
    L35:
    v3 = -1;
    v5 = 0;
    v6 = v1.listPlayerScores(v2).iterator();
    L54:
    if (v6.hasNext() ==0) goto L240;
    v7 = ((world.scores.PlayerScoreEntry)v6.next());
    if (v7.isHidden() ==0) goto L87;
    goto L54;
    L87:
    v8 = v1.getPlayersTeam(v7.owner());
    v9 = chat.ServerLine.strip(world.scores.PlayerTeam.formatNameForTeam(v8, v7.ownerName()).getString());
    v10 = money.MoneyEvents.DOLLAR.matcher(v9);
    if (v10.find() !=0) goto L137;
    goto L54;
    L137:
    if (v10.group(2) != null) goto L157;
    goto L163;
    L157:
    L163:
    v11 = auction.Money.cents("" + v10.group(2));
    v13 = v9.toLowerCase(Locale.ROOT);
    v14 = money.MoneyEvents.BALANCE_WORDS.iterator();
    L193:
    if (v14.hasNext() ==0) goto L231;
    v15 = ((String)v14.next());
    if (v13.contains(v15) ==0) goto L228;
    return v11;
    L228:
    goto L193;
    L231:
    v5 = v5 + 1;
    v3 = v11;
    goto L54;
    L240:
    if (v5 != 1) goto L250;
    goto L253;
    L250:
    L253:
    return -1;
}

private static void checkGoal(client.Minecraft client) {
    v1 = (config.FwmConfig.INSTANCE.moneyGoal * 100);
    if (cmp(v1,0) <=0) goto L25;
    if (cmp(money.MoneyStore.todayNet(),v1) >=0) goto L26;
    L25:
    return;
    L26:
    if (money.MoneyStore.claimGoal() !=0) goto L33;
    return;
    L33:
    if (client.player == null) goto L114;
    client.player.sendSystemMessage(network.chat.Component.literal("[FWM] ").withStyle(ChatFormatting.GREEN).append(network.chat.Component.literal(ui.Lang.t("Tagesziel geschafft: ", "Daily goal reached: ") + util.Fmt.compactMoney(v1)).withStyle(ChatFormatting.GOLD)));
    if (config.FwmConfig.INSTANCE.moneyGoalSound ==0) goto L114;
    client.player.playSound(sounds.SoundEvents.PLAYER_LEVELUP, 0.3499999940395355, 1.2000000476837158);
    L114:
    return;
}

private static Path log(String line, boolean overlay) {
    v2 = loader.api.FabricLoader.getInstance().getConfigDir().resolve("fwm-money-log.txt");
    v3 = LocalDateTime.now().format(format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    if (overlay ==0) goto L42;
    goto L45;
    L42:
    L45:
    new OpenOption[2][0] = StandardOpenOption.CREATE;
    new OpenOption[2][1] = StandardOpenOption.APPEND;
    Files.writeString(v3, " [Actionbar] " + " " + line + System.lineSeparator(), StandardCharsets.UTF_8, new OpenOption[2]);
    goto L81;
    v2 = /*catch Exception*/ ex;
    L81:
    return;
}

private static String clean(String item) {
    if (item != null) goto L6;
    return null;
    L6:
    v1 = item.replaceAll("^\\s*\\d+\\s*[x\u00d7]\\s*", "").trim();
    if (v1.isEmpty() ==0) goto L30;
    goto L50;
    L30:
    if (v1.length() <= 40) goto L49;
    goto L50;
    L49:
    L50:
    return v1;
}

private static String group(regex.Matcher m, String name) {
    return m.group(name);
    v2 = /*catch IllegalArgumentException*/ ex;
    return null;
}

private static Object pattern(String source) {
    if (source == null) goto L11;
    if (source.isBlank() ==0) goto L18;
    L11:
    return regex.Pattern.compile("(?!)");
    L18:
    return ((regex.Pattern)money.MoneyEvents.PATTERNS.computeIfAbsent(source, /*indy*/apply()));
}

private static regex.Pattern lambda$pattern$0(String s) {
    return regex.Pattern.compile(s, 2);
    v1 = /*catch Exception*/ ex;
    System.err.println("[FWM] Bad money pattern '" + s + "': " + v1.getMessage());
    return regex.Pattern.compile("(?!)");
}

private static void lambda$toast$0(String title, long cents, String a2, String item) {
    if (item != null) goto L20;
    goto L21;
    L20:
    L21:
    if (v4 != null) goto L31;
    goto L50;
    L31:
    if (item != null) goto L40;
    goto L43;
    L40:
    L43:
    L50:
    ui.FwmNotice.show(item, "", "" + " \u00b7 " + v4, 6088852, 10000);
    return;
}

static void <clinit>() {
    money.MoneyEvents.DOLLAR = regex.Pattern.compile("[$]\\s?([0-9][0-9.,]*)\\s*([kmbKMB])?");
    money.MoneyEvents.ORDER = regex.Pattern.compile("^(?:\\[HugoSMP\\]\\s*)?Geliefert:\\s*[0-9][0-9.,]*\\.?\\s*Auszahlung:\\s*[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmbKMB])?");
    money.MoneyEvents.AH_SOLD = regex.Pattern.compile("^Auktion gekauft:\\s*(?<name>[.*]?[A-Za-z0-9_]{2,16})\\s+hat\\s+(?<count>[0-9]+)x\\s+(?<item>.+?)\\s+f(?:\u00fc|ue)r\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmbKMB])?\\s*gekauft");
    money.MoneyEvents.ORDER_FILLED = regex.Pattern.compile("^Order beliefert:\\s*(?<name>[.*]?[A-Za-z0-9_]{2,16})\\s+hat\\s+(?<count>[0-9]+)x\\s+(?<item>.+?)\\s+f(?:\u00fc|ue)r\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmbKMB])?\\s*geliefert");
    money.MoneyEvents.ORDER_PICKED = regex.Pattern.compile("^\\[HugoSMP\\]\\s*Abgeholt:");
    money.MoneyEvents.ORDER_DELIVERED = regex.Pattern.compile("^(?:\\[HugoSMP\\]\\s*)?Geliefert:\\s*[0-9][0-9.,]*\\.?\\s*Auszahlung:");
    money.MoneyEvents.AH_BOUGHT = regex.Pattern.compile("Du hast die Auktion erfolgreich gekauft");
    money.MoneyEvents.SELLER = regex.Pattern.compile("^\\s*(?:Verk\u00e4ufer|Verkaeufer|Seller)\\s*:\\s*([.*]?[A-Za-z0-9_]{2,16})");
    money.MoneyEvents.PRICE_LINE = regex.Pattern.compile("(?:Preis|Price|Kosten|Sofortkauf)[^$]*[$]\\s*([0-9][0-9.,]*)\\s*([kmbKMB])?", 2);
    money.MoneyEvents.ANY_DOLLAR = regex.Pattern.compile("[$]\\s*([0-9][0-9.,]*)\\s*([kmbKMB])?");
    money.MoneyEvents.FEE_LINE = regex.Pattern.compile("^\\s*(?:Einstellgeb\u00fchr|Erstellgeb\u00fchr|Einstellgebuehr|Erstellgebuehr)\\s*:\\s*[$]\\s*([0-9][0-9.,]*)\\s*([kmbKMB])?");
    money.MoneyEvents.AH_CREATED = regex.Pattern.compile("^\\[HugoSMP\\]\\s*Auktion mit .* erstellt");
    money.MoneyEvents.ORDER_CREATED = regex.Pattern.compile("^\\[HugoSMP\\]\\s*Order erstellt");
    money.MoneyEvents.BALANCE_WORDS = List.of("kontostand", "guthaben", "geld", "money", "balance", "bank");
    money.MoneyEvents.PATTERNS = ?;
    money.MoneyEvents.lastKey = "";
    money.MoneyEvents.sidebarBase = -1;
    money.MoneyEvents.sidebarSeen = -1;
    return;
}

