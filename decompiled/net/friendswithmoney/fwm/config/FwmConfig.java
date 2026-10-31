// class net/friendswithmoney/fwm/config/FwmConfig  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field MIN_REOPEN_DELAY I;
field DEFAULT_REOPEN_DELAY I;
field MAX_REOPEN_DELAY I;
field MIN_ITEM_DELAY I;
field MAX_ITEM_DELAY I;
field MIN_BURST I;
field MAX_BURST I;
field MIN_PRESS_DELAY I;
field MAX_PRESS_DELAY I;
field MAX_SLOT I;
field HOURS I;
field MAX_TESTS I;
field MIN_REFRESH_MINUTES I;
field MAX_REFRESH_MINUTES I;
field MIN_TOOLGUARD_PERCENT I;
field MAX_TOOLGUARD_PERCENT I;
field MIN_AUCTION_SECONDS I;
field MAX_AUCTION_SECONDS I;
field MIN_SECOND_CHAT_WIDTH I;
field MAX_SECOND_CHAT_WIDTH I;
field ALIGN_LEFT I;
field ALIGN_CENTER I;
field ALIGN_RIGHT I;
field MIN_SECOND_CHAT_LINES I;
field MAX_SECOND_CHAT_LINES I;
field MIN_SCALE I;
field MAX_SCALE I;
field DEFAULT_SELL_PATTERN Ljava/lang/String;;
field LEGACY_SELL_PATTERNS Ljava/util/List;;
field DEFAULT_PRICE_PATTERN Ljava/lang/String;;
field DEFAULT_PAY_PATTERN Ljava/lang/String;;
field DEFAULT_MSG_PATTERN Ljava/lang/String;;
field LEGACY_MSG_PATTERN Ljava/lang/String;;
field DEFAULT_MSG_SELF Ljava/util/List;;
field PRESET_FLOWER_FARM Ljava/util/List;;
field CLAN_TAG Ljava/lang/String;;
field DEFAULT_FAKE_TAGS Ljava/util/List;;
field DEFAULT_FAKE_TAGS_EXACT Ljava/util/List;;
field DEFAULT_PAY_OUT_PATTERNS Ljava/util/List;;
field DEFAULT_AH_BUY_PATTERNS Ljava/util/List;;
field DEFAULT_AH_SELL_PATTERNS Ljava/util/List;;
field DEFAULT_PAY_PATTERNS Ljava/util/List;;
field LEGACY_PAY_PATTERNS Ljava/util/List;;
field SPAWNER_AD_SLOTS I;
field STREAMER_AD_SLOTS I;
field DEFAULT_STREAMER_AD_TEMPLATES Ljava/util/List;;
field LEGACY_STREAMER_AD_TEMPLATES Ljava/util/List;;
field DEFAULT_STREAMER_AD_ROTATION Ljava/util/List;;
field DEFAULT_STREAMER_SERVER Ljava/lang/String;;
field DEFAULT_SPAWNER_AD_TEMPLATES Ljava/util/List;;
field OLD_SPAWNER_AD_TEMPLATES Ljava/util/List;;
field INSTANCE Lnet/friendswithmoney/fwm/config/FwmConfig;;
field fakeTags Ljava/util/List;;
field fakeTagsExact Ljava/util/List;;
field enabled Z;
field sellCommand Ljava/lang/String;;
field reopenDelayTicks I;
field itemMoveDelayTicks I;
field transferBurst I;
field sellPressDelayTicks I;
field sellButtonSlot I;
field sellWhitelist Ljava/util/List;;
field apiBaseUrl Ljava/lang/String;;
field newsShownIds Ljava/util/List;;
field fightKits Ljava/util/List;;
field fightKitSelected I;
field fightKitShowInInventory Z;
field waypointsGroupOff Ljava/util/List;;
field waypointsDone Ljava/util/List;;
field spawnerRefreshMinutes I;
field spawnerApiUrl Ljava/lang/String;;
field discordUrl Ljava/lang/String;;
field websiteUrl Ljava/lang/String;;
field rtpApiUrl Ljava/lang/String;;
field rtpSiteUrl Ljava/lang/String;;
field rtpShowOwnPos Z;
field rtpMiniRadius I;
field membersApiUrl Ljava/lang/String;;
field marketApiUrl Ljava/lang/String;;
field marketHistoryUrl Ljava/lang/String;;
field memberRefreshMinutes I;
field memberTagRange D;
field toolGuardPercent I;
field toolGuardWarning Z;
field toolGuardBlock Z;
field msgCommand Ljava/lang/String;;
field msgPattern Ljava/lang/String;;
field msgSelfNames Ljava/util/List;;
field msgShowEnvelope Z;
field envelopeOff66 Z;
field msgShowToast Z;
field msgIgnoreCommand Ljava/lang/String;;
field msgIgnored Ljava/util/List;;
field msgHidden Ljava/util/List;;
field msgContactLimit I;
field msgHistoryPerContact I;
field msgDebug Z;
field unitPricePattern Ljava/lang/String;;
field secondChatTpa Z;
field secondChatMsg Z;
field secondChatPay Z;
field secondChatMarket Z;
field marketAlertPanel Z;
field marketToast Z;
field pricePingCollapsed Z;
field secondChatClan Z;
field secondChatHugoOther Z;
field secondChatFilters Ljava/util/List;;
field secondChatPayPattern Ljava/lang/String;;
field tpaTimeoutSeconds I;
field tpaDonePattern Ljava/lang/String;;
field tpaSentPattern Ljava/lang/String;;
field tpaRefusedPattern Ljava/lang/String;;
field tpaAlreadyPattern Ljava/lang/String;;
field tpaEndedPattern Ljava/lang/String;;
field serverPrefix Ljava/lang/String;;
field secondChatRemove Z;
field secondChatLines I;
field secondChatWidth I;
field secondChatAlign I;
field language Ljava/lang/String;;
field sellMessagePattern Ljava/lang/String;;
field lifetimeEarnedCents J;
field hourlyEarnedCents [J;
field hourlyBaseHour J;
field tests Ljava/util/List;;
field disabledFeatures Ljava/util/List;;
field enabledFeatures Ljava/util/List;;
field menuScale I;
field sidebarOrder Ljava/util/List;;
field sidebarHidden Ljava/util/List;;
field sidebarHideDisabled Z;
field MIN_HUD_SCALE F;
field MAX_HUD_SCALE F;
field payPatterns Ljava/util/List;;
field auctionSeconds I;
field auctionStartBid J;
field auctionMinStep J;
field auctionStackPayments Z;
field auctionAntiSnipe Z;
field auctionAntiSnipeSeconds I;
field ANTI_SNIPE_WINDOW_MS J;
field MIN_ANTI_SNIPE_SECONDS I;
field MAX_ANTI_SNIPE_SECONDS I;
field giveawayWord Ljava/lang/String;;
field giveawayLimit I;
field giveawaySeconds I;
field hudMacro Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudToast Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudEnvelope Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudTool Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudSecondChat Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudTpa Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudMinimap Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudPricePing Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudMoney Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudInvValue Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field hudAuction Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field auctionHudCard Z;
field spawnerHudCard Z;
field hudSpawnerPrices Lnet/friendswithmoney/fwm/config/FwmConfig$HudSpot;;
field spawnerHudHeader Z;
field spawnerHudIcons Z;
field spawnerHudBg I;
field spawnerHudOpacity I;
field spawnerHudBorder I;
field spawnerHudBuyColor I;
field spawnerHudSellColor I;
field spawnerHudGrid Z;
field spawnerHudHidden Ljava/util/List;;
field spawnerHudPrices Ljava/util/Map;;
field spawnerHudStyle I;
field spawnerHudTitle Ljava/lang/String;;
field spawnerHudLabelName Ljava/lang/String;;
field spawnerHudLabelBuy Ljava/lang/String;;
field spawnerHudLabelSell Ljava/lang/String;;
field spawnerHudShowBuy Z;
field spawnerHudShowSell Z;
field spawnerHudBgColor I;
field spawnerHudTextColor I;
field spawnerHudHeadColor I;
field spawnerHudBorderColor I;
field spawnerHudBorderWidth I;
field spawnerHudRadius I;
field spawnerHudPadding I;
field spawnerHudRowGap I;
field spawnerHudShadow Z;
field spawnerHudTextShadow Z;
field spawnerHudDivider Z;
field spawnerHudHeadBold Z;
field spawnerHudGradientSpeed I;
field autoTpaNames Ljava/util/List;;
field autoTpaHere Z;
field autoTpaDelayTicks I;
field spawnerAutoDrop Z;
field spawnerAutoStop Z;
field spawnerAutoSell Z;
field spawnerClickDelayTicks I;
field spawnerRepeatSeconds I;
field spawnerSidePanel Z;
field spawnerLabels Z;
field spawnerSpots Ljava/util/List;;
field MODE_BONES Ljava/lang/String;;
field MODE_DROP Ljava/lang/String;;
field MODE_SELL Ljava/lang/String;;
field SPAWNER_PAGE I;
field spawnerMode Ljava/lang/String;;
field spawnerSellFill I;
field spawnerDropMin I;
field moneyHud Z;
field moneyGoal J;
field moneyGoalSound Z;
field moneyShowAhSold Z;
field moneyShowAhBought Z;
field moneyShowOrderFilled Z;
field moneyShowOrderDelivered Z;
field moneySidebarSync Z;
field moneyHideAmounts Z;
field moneyLogLines Z;
field moneyPayOutPatterns Ljava/util/List;;
field moneyAhBuyPatterns Ljava/util/List;;
field moneyAhSellPatterns Ljava/util/List;;
field historyEnabled Z;
field historyDays I;
field historyMergeSell Z;
field invValueHud Z;
field invValueInInventory Z;
field invValueSource I;
field invValueShulkers Z;
field ecValue Z;
field textureMix Ljava/util/Map;;
field textureMixDisableSources Z;
field textureMixVersion I;
field textureFavorites Ljava/util/List;;
field textureThemes Ljava/util/List;;
field textureMixBefore Ljava/util/List;;
field pricePings Ljava/util/List;;
field antiLeakBedrock Z;
field antiLeakOres Z;
field antiLeakRotation Z;
field antiLeakStones Z;
field antiLeakBiome Z;
field antiLeakCoords Z;
field antiLeakApplied Ljava/lang/String;;
field clanLogosApplied Ljava/lang/String;;
field spawnerAdPrices Ljava/util/Map;;
field spawnerAdTemplates Ljava/util/List;;
field streamerAdTemplates Ljava/util/List;;
field streamerAdRotation Ljava/util/List;;
field streamerName Ljava/lang/String;;
field streamerServer Ljava/lang/String;;
field menuHue F;
field menuSat F;
field tintHue F;
field tintSat F;
field menuPalette Ljava/lang/String;;
field menuSounds Z;
field presenceShowOthers Z;
field menuKeyBinding Ljava/lang/String;;
field macroKeyBinding Ljava/lang/String;;
field messagesKeyBinding Ljava/lang/String;;
field auctionKeyBinding Ljava/lang/String;;
field giveawayKeyBinding Ljava/lang/String;;
field secondChatBackground Z;
field secondChatBackgroundOpacity I;
field tutorialGreeted Z;
field tutorialSeen Z;
field tutorialVersion Ljava/lang/String;;
field tutorialServerMatch Ljava/lang/String;;
field whitelistLookup Ljava/util/Set;;
static boolean streamerAdSlots(List saved) {
    v1 = ?;
    v2 = 0;
    L10:
    if (v2 >= 3) goto L99;
    if (saved == null) goto L42;
    if (v2 >= saved.size()) goto L42;
    goto L43;
    L42:
    L43:
    v3 = null;
    if (v3 == null) goto L66;
    if (v3.equals(config.FwmConfig.LEGACY_STREAMER_AD_TEMPLATES.get(v2)) ==0) goto L66;
    v3 = null;
    L66:
    if (v3 != null) goto L86;
    goto L87;
    L86:
    L87:
    v2 = v2 + 1;
    goto L10;
    L99:
    return v1;
}

static boolean fiveAdSlots(List saved) {
    if (saved == null) goto L25;
    if (saved.isEmpty() !=0) goto L25;
    if (saved.equals(config.FwmConfig.OLD_SPAWNER_AD_TEMPLATES) ==0) goto L36;
    L25:
    return ?;
    L36:
    v1 = ?;
    v2 = saved.iterator();
    L51:
    if (v2.hasNext() ==0) goto L138;
    v3 = ((String)v2.next());
    if (v1.size() != 5) goto L83;
    goto L138;
    L83:
    if (v3 != null) goto L93;
    goto L129;
    L93:
    L129:
    "".add(v3.replace("{name}", "[Name]").replace("{spawner}", "[Spawner]").replace("{kauf}", "[Ankauf Preis]").replace("{verkauf}", "[Verkauf Preis]").replace("{anzahl}", "[Anzahl]"));
    goto L51;
    L138:
    if (v1.size() >= 5) goto L160;
    v1.add("");
    goto L138;
    L160:
    return v1;
}

private static void biggerSpot() {
    v0 = ?;
    v0.scale = 1.399999976158142;
    return v0;
}

private void <init>() {
    this.fakeTags = ?;
    this.fakeTagsExact = ?;
    this.enabled = 0;
    this.sellCommand = "/sell";
    this.reopenDelayTicks = 100;
    this.itemMoveDelayTicks = 1;
    this.transferBurst = 10;
    this.sellPressDelayTicks = 2;
    this.sellButtonSlot = 35;
    this.sellWhitelist = ?;
    this.apiBaseUrl = "https://269153435.de";
    this.newsShownIds = null;
    this.fightKits = ?;
    this.fightKitSelected = 0;
    this.fightKitShowInInventory = 0;
    this.waypointsGroupOff = ?;
    this.waypointsDone = ?;
    this.spawnerRefreshMinutes = 20;
    this.spawnerApiUrl = "https://friendswithmoney.net/api/spawner-types";
    this.discordUrl = "https://discord.gg/friendswithmoney";
    this.websiteUrl = "https://friendswithmoney.net";
    this.rtpApiUrl = "https://269153435.de/api/mod/rtp";
    this.rtpSiteUrl = "https://friendswithmoney.net/tools/rtp";
    this.rtpShowOwnPos = 1;
    this.rtpMiniRadius = 32;
    this.membersApiUrl = "https://friendswithmoney.net/api/clan-members";
    this.marketApiUrl = "https://friendswithmoney.net/api/marketplace";
    this.marketHistoryUrl = "https://269153435.de/api/mod/market";
    this.memberRefreshMinutes = 30;
    this.memberTagRange = 48.0;
    this.toolGuardPercent = 10;
    this.toolGuardWarning = 1;
    this.toolGuardBlock = 1;
    this.msgCommand = "msg";
    this.msgPattern = "([.*]?[A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$";
    this.msgSelfNames = ?;
    this.msgShowEnvelope = 0;
    this.envelopeOff66 = 0;
    this.msgShowToast = 0;
    this.msgIgnoreCommand = "ignore";
    this.msgIgnored = ?;
    this.msgHidden = ?;
    this.msgContactLimit = 30;
    this.msgHistoryPerContact = 200;
    this.msgDebug = 0;
    this.unitPricePattern = "(?:Preis|Price)\\s*:\\s*(\\S+)";
    this.secondChatTpa = 1;
    this.secondChatMsg = 1;
    this.secondChatPay = 1;
    this.secondChatMarket = 1;
    this.marketAlertPanel = 1;
    this.marketToast = 0;
    this.pricePingCollapsed = 0;
    this.secondChatClan = 0;
    this.secondChatHugoOther = 0;
    this.secondChatFilters = ?;
    this.secondChatPayPattern = "(?i)(?=.*[$\u20ac]\\s?\\d)(?=.*(bezahlt|\u00fcberwiesen|gesendet|erhalten|gezahlt|payment|zahlung|paid|sent you|has sent|received))";
    this.tpaTimeoutSeconds = 60;
    this.tpaDonePattern = "(angenommen|abgelehnt|abgelaufen|abgebrochen|accept|denied|declin|expire|cancel)";
    this.tpaSentPattern = "Du hast (?<name>\\S+) (?:eine Teleportations-Anfrage gesendet|gefragt, ob er sich zu dir teleportieren)";
    this.tpaRefusedPattern = "(Teleportations-Anfragen deaktiviert|keine Teleportations-Anfragen empfangen|nicht online|nicht gefunden|unsicheren Ort|Bitte warte|w\u00e4hrend eines Kampfes|im Kampf nicht)";
    this.tpaAlreadyPattern = "bereits eine Teleportations-Anfrage";
    this.tpaEndedPattern = "(Anfrage ist abgelaufen|ausgehende Teleportations-Anfrage\\(n\\) gel\u00f6scht|keine ausgehenden Teleportations-Anfragen)";
    this.serverPrefix = "[HugoSMP]";
    this.secondChatRemove = 1;
    this.secondChatLines = 8;
    this.secondChatWidth = 340;
    this.secondChatAlign = 0;
    this.language = "de";
    this.sellMessagePattern = "(?:^|\\]\\s?)\\+\\$([\\d.,]+)(?:\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+))?";
    this.lifetimeEarnedCents = 0;
    this.hourlyEarnedCents = new long[24];
    this.hourlyBaseHour = 0;
    this.tests = ?;
    this.disabledFeatures = ?;
    this.enabledFeatures = ?;
    this.menuScale = 100;
    this.sidebarOrder = ?;
    this.sidebarHidden = ?;
    this.sidebarHideDisabled = 0;
    this.payPatterns = ?;
    this.auctionSeconds = 60;
    this.auctionStartBid = 10000;
    this.auctionMinStep = 0;
    this.auctionStackPayments = 0;
    this.auctionAntiSnipe = 1;
    this.auctionAntiSnipeSeconds = 10;
    this.giveawayWord = "GEWINN";
    this.giveawayLimit = 50;
    this.giveawaySeconds = 600;
    this.hudMacro = ?;
    this.hudToast = ?;
    this.hudEnvelope = ?;
    this.hudTool = ?;
    this.hudSecondChat = ?;
    this.hudTpa = ?;
    this.hudMinimap = ?;
    this.hudPricePing = ?;
    this.hudMoney = ?;
    this.hudInvValue = ?;
    this.hudAuction = ?;
    this.auctionHudCard = 1;
    this.spawnerHudCard = 0;
    this.hudSpawnerPrices = config.FwmConfig.biggerSpot();
    this.spawnerHudHeader = 1;
    this.spawnerHudIcons = 1;
    this.spawnerHudBg = 0;
    this.spawnerHudOpacity = 90;
    this.spawnerHudBorder = 1;
    this.spawnerHudBuyColor = 65360;
    this.spawnerHudSellColor = 16711680;
    this.spawnerHudGrid = 0;
    this.spawnerHudHidden = ?;
    this.spawnerHudPrices = ?;
    this.spawnerHudStyle = 0;
    this.spawnerHudTitle = "";
    this.spawnerHudLabelName = "";
    this.spawnerHudLabelBuy = "";
    this.spawnerHudLabelSell = "";
    this.spawnerHudShowBuy = 1;
    this.spawnerHudShowSell = 1;
    this.spawnerHudBgColor = 789516;
    this.spawnerHudTextColor = 16119285;
    this.spawnerHudHeadColor = 16119285;
    this.spawnerHudBorderColor = 16777215;
    this.spawnerHudBorderWidth = 1;
    this.spawnerHudRadius = 6;
    this.spawnerHudPadding = 8;
    this.spawnerHudRowGap = 12;
    this.spawnerHudShadow = 0;
    this.spawnerHudTextShadow = 1;
    this.spawnerHudDivider = 1;
    this.spawnerHudHeadBold = 1;
    this.spawnerHudGradientSpeed = 5;
    this.autoTpaNames = ?;
    this.autoTpaHere = 0;
    this.autoTpaDelayTicks = 10;
    this.spawnerAutoDrop = 0;
    this.spawnerAutoStop = 0;
    this.spawnerAutoSell = 0;
    this.spawnerClickDelayTicks = 4;
    this.spawnerRepeatSeconds = 60;
    this.spawnerSidePanel = 1;
    this.spawnerLabels = 1;
    this.spawnerSpots = ?;
    this.spawnerMode = "bones";
    this.spawnerSellFill = 80;
    this.spawnerDropMin = 2304;
    this.moneyHud = 0;
    this.moneyGoal = 10000000;
    this.moneyGoalSound = 1;
    this.moneyShowAhSold = 1;
    this.moneyShowAhBought = 1;
    this.moneyShowOrderFilled = 1;
    this.moneyShowOrderDelivered = 1;
    this.moneySidebarSync = 1;
    this.moneyHideAmounts = 0;
    this.moneyLogLines = 0;
    this.moneyPayOutPatterns = ?;
    this.moneyAhBuyPatterns = ?;
    this.moneyAhSellPatterns = ?;
    this.historyEnabled = 1;
    this.historyDays = 30;
    this.historyMergeSell = 1;
    this.invValueHud = 0;
    this.invValueInInventory = 0;
    this.invValueSource = 0;
    this.invValueShulkers = 1;
    this.ecValue = 1;
    this.textureMix = ?;
    this.textureMixDisableSources = 1;
    this.textureMixVersion = 0;
    this.textureFavorites = ?;
    this.textureThemes = ?;
    this.textureMixBefore = null;
    this.pricePings = ?;
    this.antiLeakBedrock = 1;
    this.antiLeakOres = 1;
    this.antiLeakRotation = 1;
    this.antiLeakStones = 0;
    this.antiLeakBiome = 0;
    this.antiLeakCoords = 1;
    this.antiLeakApplied = "";
    this.clanLogosApplied = "";
    this.spawnerAdPrices = ?;
    this.spawnerAdTemplates = ?;
    this.streamerAdTemplates = ?;
    this.streamerAdRotation = ?;
    this.streamerName = "";
    this.streamerServer = "";
    this.menuHue = -1.0;
    this.menuSat = 1;
    this.tintHue = -1.0;
    this.tintSat = 0;
    this.menuPalette = "";
    this.menuSounds = 1;
    this.presenceShowOthers = 1;
    this.menuKeyBinding = "";
    this.macroKeyBinding = "";
    this.messagesKeyBinding = "";
    this.auctionKeyBinding = "";
    this.giveawayKeyBinding = "";
    this.secondChatBackground = 1;
    this.secondChatBackgroundOpacity = 50;
    this.tutorialGreeted = 0;
    this.tutorialSeen = 0;
    this.tutorialVersion = "";
    this.tutorialServerMatch = "hugo";
    return;
}

public boolean isWhitelisted(String itemId) {
    return this.lookup().contains(itemId);
}

public boolean toggleWhitelisted(String itemId) {
    if (this.lookup().contains(itemId) ==0) goto L40;
    this.lookup().remove(itemId);
    this.sellWhitelist.remove(itemId);
    v2 = 0;
    goto L64;
    L40:
    this.lookup().add(itemId);
    this.sellWhitelist.add(itemId);
    v2 = 1;
    L64:
    return v2;
}

public boolean setWhitelist(Iterable itemIds) {
    this.sellWhitelist = ?;
    v2 = itemIds.iterator();
    L18:
    if (v2.hasNext() ==0) goto L51;
    v3 = ((String)v2.next());
    this.sellWhitelist.add(v3);
    goto L18;
    L51:
    this.whitelistLookup = null;
    return;
}

public void clearWhitelist() {
    this.sellWhitelist = ?;
    this.whitelistLookup = null;
    return;
}

public int whitelistSize() {
    if (this.sellWhitelist != null) goto L11;
    goto L20;
    L11:
    L20:
    return this.sellWhitelist.size();
}

private void lookup() {
    if (this.whitelistLookup != null) goto L35;
    if (this.sellWhitelist != null) goto L25;
    goto L29;
    L25:
    L29:
    ?.whitelistLookup = ?;
    L35:
    return this.whitelistLookup;
}

public void copyFrom(config.FwmConfig other) {
    if (other != null) goto L5;
    return;
    L5:
    this.enabled = other.enabled;
    this.reopenDelayTicks = other.reopenDelayTicks;
    this.itemMoveDelayTicks = other.itemMoveDelayTicks;
    this.transferBurst = other.transferBurst;
    this.sellPressDelayTicks = other.sellPressDelayTicks;
    this.sellButtonSlot = other.sellButtonSlot;
    this.spawnerRefreshMinutes = other.spawnerRefreshMinutes;
    this.lifetimeEarnedCents = other.lifetimeEarnedCents;
    this.hourlyBaseHour = other.hourlyBaseHour;
    if (other.hourlyEarnedCents == null) goto L102;
    if (other.hourlyEarnedCents.length != 24) goto L102;
    this.hourlyEarnedCents = other.hourlyEarnedCents;
    L102:
    if (other.tests == null) goto L117;
    this.tests = other.tests;
    L117:
    if (other.enabledFeatures == null) goto L132;
    this.enabledFeatures = other.enabledFeatures;
    L132:
    if (other.disabledFeatures == null) goto L162;
    this.disabledFeatures = other.disabledFeatures;
    this.disabledFeatures.removeIf(/*indy*/test());
    L162:
    if (other.menuScale !=0) goto L175;
    goto L179;
    L175:
    L179:
    100.menuScale = other.menuScale;
    this.secondChatBackground = other.secondChatBackground;
    this.secondChatBackgroundOpacity = other.secondChatBackgroundOpacity;
    this.tutorialGreeted = other.tutorialGreeted;
    this.tutorialSeen = other.tutorialSeen;
    if (other.tutorialVersion == null) goto L229;
    this.tutorialVersion = other.tutorialVersion;
    L229:
    if (other.tutorialServerMatch == null) goto L244;
    this.tutorialServerMatch = other.tutorialServerMatch;
    L244:
    if (other.newsShownIds == null) goto L266;
    this.newsShownIds = ?;
    L266:
    if (other.fightKits == null) goto L352;
    this.fightKits = ?;
    v2 = other.fightKits.iterator();
    L294:
    if (v2.hasNext() ==0) goto L352;
    v3 = ((config.FwmConfig$FightKit)v2.next());
    if (v3 != null) goto L320;
    goto L294;
    L320:
    if (v3.slots != null) goto L338;
    v3.slots = ?;
    L338:
    this.fightKits.add(v3);
    goto L294;
    L352:
    this.fightKitSelected = Math.max(0, other.fightKitSelected);
    this.fightKitShowInInventory = other.fightKitShowInInventory;
    if (other.waypointsGroupOff == null) goto L394;
    this.waypointsGroupOff = ?;
    L394:
    if (other.waypointsDone == null) goto L416;
    this.waypointsDone = ?;
    L416:
    if (other.apiBaseUrl == null) goto L441;
    if (other.apiBaseUrl.isBlank() !=0) goto L441;
    this.apiBaseUrl = other.apiBaseUrl;
    L441:
    if (other.spawnerApiUrl == null) goto L466;
    if (other.spawnerApiUrl.isBlank() !=0) goto L466;
    this.spawnerApiUrl = other.spawnerApiUrl;
    L466:
    if (other.discordUrl == null) goto L491;
    if (other.discordUrl.isBlank() !=0) goto L491;
    this.discordUrl = other.discordUrl;
    L491:
    if (other.websiteUrl == null) goto L516;
    if (other.websiteUrl.isBlank() !=0) goto L516;
    this.websiteUrl = other.websiteUrl;
    L516:
    if (other.rtpApiUrl == null) goto L541;
    if (other.rtpApiUrl.isBlank() !=0) goto L541;
    this.rtpApiUrl = other.rtpApiUrl;
    L541:
    if (other.rtpSiteUrl == null) goto L566;
    if (other.rtpSiteUrl.isBlank() !=0) goto L566;
    this.rtpSiteUrl = other.rtpSiteUrl;
    L566:
    if (other.membersApiUrl == null) goto L591;
    if (other.membersApiUrl.isBlank() !=0) goto L591;
    this.membersApiUrl = other.membersApiUrl;
    L591:
    if (other.marketApiUrl == null) goto L616;
    if (other.marketApiUrl.isBlank() !=0) goto L616;
    this.marketApiUrl = other.marketApiUrl;
    L616:
    if (other.marketHistoryUrl == null) goto L641;
    if (other.marketHistoryUrl.isBlank() !=0) goto L641;
    this.marketHistoryUrl = other.marketHistoryUrl;
    L641:
    if (other.memberRefreshMinutes <=0) goto L656;
    this.memberRefreshMinutes = other.memberRefreshMinutes;
    L656:
    if (cmp(other.memberTagRange,0) <=0) goto L673;
    this.memberTagRange = other.memberTagRange;
    L673:
    if (other.toolGuardPercent <=0) goto L688;
    this.toolGuardPercent = other.toolGuardPercent;
    L688:
    this.toolGuardWarning = other.toolGuardWarning;
    this.toolGuardBlock = other.toolGuardBlock;
    if (other.msgCommand == null) goto L729;
    if (other.msgCommand.isBlank() !=0) goto L729;
    this.msgCommand = other.msgCommand;
    L729:
    if (other.msgPattern == null) goto L767;
    if (other.msgPattern.isBlank() !=0) goto L767;
    if (other.msgPattern.equals("([A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$") !=0) goto L767;
    this.msgPattern = other.msgPattern;
    L767:
    if (other.msgSelfNames == null) goto L794;
    if (other.msgSelfNames.isEmpty() !=0) goto L794;
    this.msgSelfNames = other.msgSelfNames;
    L794:
    if (other.msgContactLimit <=0) goto L809;
    this.msgContactLimit = other.msgContactLimit;
    L809:
    if (other.msgHistoryPerContact <=0) goto L824;
    this.msgHistoryPerContact = other.msgHistoryPerContact;
    L824:
    this.msgDebug = other.msgDebug;
    if (other.unitPricePattern == null) goto L857;
    if (other.unitPricePattern.isBlank() !=0) goto L857;
    this.unitPricePattern = other.unitPricePattern;
    L857:
    this.secondChatTpa = other.secondChatTpa;
    this.secondChatMsg = other.secondChatMsg;
    this.secondChatPay = other.secondChatPay;
    this.secondChatMarket = other.secondChatMarket;
    this.marketAlertPanel = other.marketAlertPanel;
    this.marketToast = other.marketToast;
    this.pricePingCollapsed = other.pricePingCollapsed;
    this.secondChatClan = other.secondChatClan;
    this.secondChatHugoOther = other.secondChatHugoOther;
    this.secondChatRemove = other.secondChatRemove;
    if (other.secondChatFilters == null) goto L952;
    this.secondChatFilters = other.secondChatFilters;
    L952:
    if (other.tpaTimeoutSeconds <=0) goto L967;
    this.tpaTimeoutSeconds = other.tpaTimeoutSeconds;
    L967:
    if (other.tpaSentPattern == null) goto L992;
    if (other.tpaSentPattern.isBlank() !=0) goto L992;
    this.tpaSentPattern = other.tpaSentPattern;
    L992:
    if (other.tpaRefusedPattern == null) goto L1017;
    if (other.tpaRefusedPattern.isBlank() !=0) goto L1017;
    this.tpaRefusedPattern = other.tpaRefusedPattern;
    L1017:
    if (other.tpaAlreadyPattern == null) goto L1042;
    if (other.tpaAlreadyPattern.isBlank() !=0) goto L1042;
    this.tpaAlreadyPattern = other.tpaAlreadyPattern;
    L1042:
    if (other.tpaEndedPattern == null) goto L1067;
    if (other.tpaEndedPattern.isBlank() !=0) goto L1067;
    this.tpaEndedPattern = other.tpaEndedPattern;
    L1067:
    if (other.serverPrefix == null) goto L1082;
    this.serverPrefix = other.serverPrefix;
    L1082:
    if (other.tpaDonePattern == null) goto L1107;
    if (other.tpaDonePattern.isBlank() !=0) goto L1107;
    this.tpaDonePattern = other.tpaDonePattern;
    L1107:
    if (other.secondChatPayPattern == null) goto L1132;
    if (other.secondChatPayPattern.isBlank() !=0) goto L1132;
    this.secondChatPayPattern = other.secondChatPayPattern;
    L1132:
    if (other.secondChatLines <=0) goto L1147;
    this.secondChatLines = other.secondChatLines;
    L1147:
    if (other.secondChatWidth <=0) goto L1162;
    this.secondChatWidth = other.secondChatWidth;
    L1162:
    this.secondChatAlign = other.secondChatAlign;
    if (other.sidebarOrder == null) goto L1185;
    this.sidebarOrder = other.sidebarOrder;
    L1185:
    if (other.sidebarHidden == null) goto L1200;
    this.sidebarHidden = other.sidebarHidden;
    L1200:
    this.sidebarHideDisabled = other.sidebarHideDisabled;
    this.rtpShowOwnPos = other.rtpShowOwnPos;
    if (other.rtpMiniRadius <=0) goto L1231;
    this.rtpMiniRadius = other.rtpMiniRadius;
    L1231:
    if (other.hudMacro == null) goto L1246;
    this.hudMacro = other.hudMacro;
    L1246:
    if (other.hudToast == null) goto L1261;
    this.hudToast = other.hudToast;
    L1261:
    if (other.hudEnvelope == null) goto L1276;
    this.hudEnvelope = other.hudEnvelope;
    L1276:
    if (other.hudTool == null) goto L1291;
    this.hudTool = other.hudTool;
    L1291:
    if (other.hudSecondChat == null) goto L1306;
    this.hudSecondChat = other.hudSecondChat;
    L1306:
    if (other.hudTpa == null) goto L1321;
    this.hudTpa = other.hudTpa;
    L1321:
    if (other.hudMinimap == null) goto L1336;
    this.hudMinimap = other.hudMinimap;
    L1336:
    if (other.hudPricePing == null) goto L1351;
    this.hudPricePing = other.hudPricePing;
    L1351:
    this.auctionStackPayments = other.auctionStackPayments;
    this.auctionAntiSnipe = other.auctionAntiSnipe;
    if (other.auctionAntiSnipeSeconds <=0) goto L1382;
    this.auctionAntiSnipeSeconds = other.auctionAntiSnipeSeconds;
    L1382:
    if (other.giveawayWord == null) goto L1407;
    if (other.giveawayWord.isBlank() !=0) goto L1407;
    this.giveawayWord = other.giveawayWord;
    L1407:
    if (other.giveawayLimit <=0) goto L1422;
    this.giveawayLimit = other.giveawayLimit;
    L1422:
    if (other.giveawaySeconds <=0) goto L1437;
    this.giveawaySeconds = other.giveawaySeconds;
    L1437:
    if (other.pricePings == null) goto L1452;
    this.pricePings = other.pricePings;
    L1452:
    this.antiLeakBedrock = other.antiLeakBedrock;
    this.antiLeakOres = other.antiLeakOres;
    this.antiLeakRotation = other.antiLeakRotation;
    this.antiLeakStones = other.antiLeakStones;
    this.antiLeakBiome = other.antiLeakBiome;
    this.antiLeakCoords = other.antiLeakCoords;
    if (other.antiLeakApplied == null) goto L1515;
    this.antiLeakApplied = other.antiLeakApplied;
    L1515:
    if (other.clanLogosApplied == null) goto L1530;
    this.clanLogosApplied = other.clanLogosApplied;
    L1530:
    if (other.spawnerAdPrices == null) goto L1545;
    this.spawnerAdPrices = other.spawnerAdPrices;
    L1545:
    this.spawnerAdTemplates = config.FwmConfig.fiveAdSlots(other.spawnerAdTemplates);
    this.streamerAdTemplates = config.FwmConfig.streamerAdSlots(other.streamerAdTemplates);
    if (other.streamerName == null) goto L1582;
    this.streamerName = other.streamerName;
    L1582:
    if (other.streamerAdRotation == null) goto L1744;
    if (other.streamerAdRotation.size() != config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION.size()) goto L1744;
    this.streamerAdRotation = ?;
    v2 = 0;
    L1622:
    if (v2 >= config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION.size()) goto L1744;
    v3 = ((String)other.streamerAdRotation.get(v2));
    if (v3 == null) goto L1659;
    if (v3.isBlank() ==0) goto L1675;
    L1659:
    v3 = ((String)config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION.get(v2));
    goto L1727;
    L1675:
    if (v3.contains("[Server]") ==0) goto L1727;
    if (other.streamerServer == null) goto L1702;
    if (other.streamerServer.isBlank() ==0) goto L1708;
    L1702:
    goto L1715;
    L1708:
    L1715:
    v4 = other.streamerServer.trim();
    v3 = v3.replace("[Server]", v4);
    L1727:
    this.streamerAdRotation.add(v3);
    v2 = v2 + 1;
    goto L1622;
    L1744:
    if (other.streamerServer == null) goto L1759;
    this.streamerServer = other.streamerServer;
    L1759:
    if (other.auctionSeconds <=0) goto L1774;
    this.auctionSeconds = other.auctionSeconds;
    L1774:
    if (cmp(other.auctionStartBid,0) <=0) goto L1791;
    this.auctionStartBid = other.auctionStartBid;
    L1791:
    if (other.payPatterns == null) goto L1833;
    if (other.payPatterns.isEmpty() !=0) goto L1833;
    if (other.payPatterns.equals(config.FwmConfig.LEGACY_PAY_PATTERNS) !=0) goto L1833;
    this.payPatterns = other.payPatterns;
    L1833:
    if (other.menuPalette == null) goto L1848;
    this.menuPalette = other.menuPalette;
    L1848:
    this.menuHue = other.menuHue;
    this.menuSat = other.menuSat;
    this.tintHue = other.tintHue;
    this.tintSat = other.tintSat;
    this.menuSounds = other.menuSounds;
    this.presenceShowOthers = other.presenceShowOthers;
    if (other.menuKeyBinding == null) goto L1911;
    this.menuKeyBinding = other.menuKeyBinding;
    L1911:
    if (other.macroKeyBinding == null) goto L1926;
    this.macroKeyBinding = other.macroKeyBinding;
    L1926:
    if (other.auctionKeyBinding == null) goto L1941;
    this.auctionKeyBinding = other.auctionKeyBinding;
    L1941:
    if (other.giveawayKeyBinding == null) goto L1956;
    this.giveawayKeyBinding = other.giveawayKeyBinding;
    L1956:
    if (other.messagesKeyBinding == null) goto L1971;
    this.messagesKeyBinding = other.messagesKeyBinding;
    L1971:
    this.msgShowEnvelope = other.msgShowEnvelope;
    this.envelopeOff66 = other.envelopeOff66;
    this.msgShowToast = other.msgShowToast;
    if (other.msgIgnoreCommand == null) goto L2020;
    if (other.msgIgnoreCommand.isBlank() !=0) goto L2020;
    this.msgIgnoreCommand = other.msgIgnoreCommand;
    L2020:
    if (other.msgHidden == null) goto L2042;
    this.msgHidden = ?;
    L2042:
    if (other.msgIgnored == null) goto L2057;
    this.msgIgnored = other.msgIgnored;
    L2057:
    if (other.language == null) goto L2082;
    if (other.language.isBlank() !=0) goto L2082;
    this.language = other.language;
    L2082:
    if (other.sellMessagePattern == null) goto L2122;
    if (other.sellMessagePattern.isBlank() !=0) goto L2122;
    if (config.FwmConfig.LEGACY_SELL_PATTERNS.contains(other.sellMessagePattern) !=0) goto L2122;
    this.sellMessagePattern = other.sellMessagePattern;
    L2122:
    if (other.sellCommand == null) goto L2147;
    if (other.sellCommand.isBlank() !=0) goto L2147;
    this.sellCommand = other.sellCommand;
    L2147:
    if (other.sellWhitelist != null) goto L2165;
    goto L2169;
    L2165:
    L2169:
    ?.sellWhitelist = other.sellWhitelist;
    if (other.fakeTags == null) goto L2199;
    if (other.fakeTags.isEmpty() !=0) goto L2199;
    this.fakeTags = other.fakeTags;
    L2199:
    if (other.fakeTagsExact == null) goto L2214;
    this.fakeTagsExact = other.fakeTagsExact;
    L2214:
    config.FwmConfig.addMissing(this.fakeTags, config.FwmConfig.DEFAULT_FAKE_TAGS);
    config.FwmConfig.addMissing(this.fakeTagsExact, config.FwmConfig.DEFAULT_FAKE_TAGS_EXACT);
    this.whitelistLookup = null;
    if (other.hudMoney == null) goto L2254;
    this.hudMoney = other.hudMoney;
    L2254:
    if (other.hudInvValue == null) goto L2269;
    this.hudInvValue = other.hudInvValue;
    L2269:
    if (other.hudAuction == null) goto L2284;
    this.hudAuction = other.hudAuction;
    L2284:
    this.auctionHudCard = other.auctionHudCard;
    this.spawnerHudCard = other.spawnerHudCard;
    if (other.hudSpawnerPrices == null) goto L2315;
    this.hudSpawnerPrices = other.hudSpawnerPrices;
    L2315:
    this.spawnerHudHeader = other.spawnerHudHeader;
    this.spawnerHudIcons = other.spawnerHudIcons;
    this.spawnerHudBg = other.spawnerHudBg;
    this.spawnerHudOpacity = other.spawnerHudOpacity;
    this.spawnerHudBorder = other.spawnerHudBorder;
    this.spawnerHudBuyColor = other.spawnerHudBuyColor;
    this.spawnerHudSellColor = other.spawnerHudSellColor;
    this.spawnerHudGrid = other.spawnerHudGrid;
    if (other.spawnerHudHidden == null) goto L2401;
    this.spawnerHudHidden = ?;
    L2401:
    if (other.spawnerHudPrices == null) goto L2423;
    this.spawnerHudPrices = ?;
    L2423:
    this.spawnerHudStyle = other.spawnerHudStyle;
    if (other.spawnerHudTitle == null) goto L2446;
    this.spawnerHudTitle = other.spawnerHudTitle;
    L2446:
    if (other.spawnerHudLabelName == null) goto L2461;
    this.spawnerHudLabelName = other.spawnerHudLabelName;
    L2461:
    if (other.spawnerHudLabelBuy == null) goto L2476;
    this.spawnerHudLabelBuy = other.spawnerHudLabelBuy;
    L2476:
    if (other.spawnerHudLabelSell == null) goto L2491;
    this.spawnerHudLabelSell = other.spawnerHudLabelSell;
    L2491:
    this.spawnerHudShowBuy = other.spawnerHudShowBuy;
    this.spawnerHudShowSell = other.spawnerHudShowSell;
    this.spawnerHudBgColor = other.spawnerHudBgColor;
    this.spawnerHudTextColor = other.spawnerHudTextColor;
    this.spawnerHudHeadColor = other.spawnerHudHeadColor;
    this.spawnerHudBorderColor = other.spawnerHudBorderColor;
    this.spawnerHudBorderWidth = other.spawnerHudBorderWidth;
    this.spawnerHudRadius = other.spawnerHudRadius;
    this.spawnerHudPadding = other.spawnerHudPadding;
    this.spawnerHudRowGap = other.spawnerHudRowGap;
    this.spawnerHudShadow = other.spawnerHudShadow;
    this.spawnerHudTextShadow = other.spawnerHudTextShadow;
    this.spawnerHudDivider = other.spawnerHudDivider;
    this.spawnerHudHeadBold = other.spawnerHudHeadBold;
    this.spawnerHudGradientSpeed = other.spawnerHudGradientSpeed;
    if (other.autoTpaNames == null) goto L2633;
    this.autoTpaNames = ?;
    L2633:
    this.autoTpaHere = other.autoTpaHere;
    this.autoTpaDelayTicks = other.autoTpaDelayTicks;
    this.spawnerAutoDrop = other.spawnerAutoDrop;
    this.spawnerAutoStop = other.spawnerAutoStop;
    this.spawnerAutoSell = other.spawnerAutoSell;
    this.spawnerClickDelayTicks = other.spawnerClickDelayTicks;
    this.spawnerRepeatSeconds = other.spawnerRepeatSeconds;
    this.spawnerSidePanel = other.spawnerSidePanel;
    this.spawnerLabels = other.spawnerLabels;
    if (other.spawnerSpots == null) goto L2727;
    this.spawnerSpots = ?;
    L2727:
    if (other.spawnerMode == null) goto L2742;
    this.spawnerMode = other.spawnerMode;
    L2742:
    this.spawnerSellFill = other.spawnerSellFill;
    this.spawnerDropMin = other.spawnerDropMin;
    this.moneyHud = other.moneyHud;
    this.moneyGoal = other.moneyGoal;
    this.moneyGoalSound = other.moneyGoalSound;
    this.moneyShowAhSold = other.moneyShowAhSold;
    this.moneyShowAhBought = other.moneyShowAhBought;
    this.moneyShowOrderFilled = other.moneyShowOrderFilled;
    this.moneyShowOrderDelivered = other.moneyShowOrderDelivered;
    this.moneySidebarSync = other.moneySidebarSync;
    this.moneyHideAmounts = other.moneyHideAmounts;
    this.moneyLogLines = 0;
    if (other.moneyPayOutPatterns == null) goto L2862;
    if (other.moneyPayOutPatterns.isEmpty() !=0) goto L2862;
    this.moneyPayOutPatterns = other.moneyPayOutPatterns;
    L2862:
    if (other.moneyAhBuyPatterns == null) goto L2889;
    if (other.moneyAhBuyPatterns.isEmpty() !=0) goto L2889;
    this.moneyAhBuyPatterns = other.moneyAhBuyPatterns;
    L2889:
    if (other.moneyAhSellPatterns == null) goto L2916;
    if (other.moneyAhSellPatterns.isEmpty() !=0) goto L2916;
    this.moneyAhSellPatterns = other.moneyAhSellPatterns;
    L2916:
    this.historyEnabled = other.historyEnabled;
    this.historyDays = other.historyDays;
    this.historyMergeSell = other.historyMergeSell;
    this.invValueHud = other.invValueHud;
    this.invValueInInventory = other.invValueInInventory;
    this.invValueSource = other.invValueSource;
    this.invValueShulkers = other.invValueShulkers;
    this.ecValue = other.ecValue;
    if (other.textureMix == null) goto L3002;
    this.textureMix = ?;
    L3002:
    this.textureMixDisableSources = other.textureMixDisableSources;
    this.textureMixVersion = other.textureMixVersion;
    if (other.textureFavorites == null) goto L3040;
    this.textureFavorites = ?;
    L3040:
    if (other.textureThemes == null) goto L3062;
    this.textureThemes = ?;
    L3062:
    this.textureMixBefore = other.textureMixBefore;
    this.clamp();
    return;
}

public float clamp() {
    this.secondChatBackgroundOpacity = Math.max(0, Math.min(100, this.secondChatBackgroundOpacity));
    this.reopenDelayTicks = config.FwmConfig.clamp(this.reopenDelayTicks, 0, 200);
    this.itemMoveDelayTicks = config.FwmConfig.clamp(this.itemMoveDelayTicks, 1, 20);
    this.transferBurst = config.FwmConfig.clamp(this.transferBurst, 1, 13);
    this.sellPressDelayTicks = config.FwmConfig.clamp(this.sellPressDelayTicks, 0, 20);
    this.sellButtonSlot = config.FwmConfig.clamp(this.sellButtonSlot, 0, 107);
    this.spawnerRefreshMinutes = config.FwmConfig.clamp(this.spawnerRefreshMinutes, 5, 60);
    this.menuScale = config.FwmConfig.clamp(this.menuScale, 70, 150);
    this.toolGuardPercent = config.FwmConfig.clamp(this.toolGuardPercent, 1, 50);
    this.auctionSeconds = config.FwmConfig.clamp(this.auctionSeconds, 15, 3600);
    this.auctionAntiSnipeSeconds = config.FwmConfig.clamp(this.auctionAntiSnipeSeconds, 1, 60);
    this.auctionStartBid = Math.max(0, this.auctionStartBid);
    this.msgContactLimit = config.FwmConfig.clamp(this.msgContactLimit, 1, 200);
    this.msgHistoryPerContact = config.FwmConfig.clamp(this.msgHistoryPerContact, 10, 2000);
    this.secondChatLines = config.FwmConfig.clamp(this.secondChatLines, 3, 16);
    this.secondChatWidth = config.FwmConfig.clamp(this.secondChatWidth, 100, 420);
    this.secondChatAlign = config.FwmConfig.clamp(this.secondChatAlign, 0, 2);
    this.autoTpaDelayTicks = config.FwmConfig.clamp(this.autoTpaDelayTicks, 0, 100);
    this.spawnerClickDelayTicks = config.FwmConfig.clamp(this.spawnerClickDelayTicks, 2, 20);
    this.spawnerRepeatSeconds = config.FwmConfig.clamp(this.spawnerRepeatSeconds, 10, 600);
    this.spawnerSellFill = config.FwmConfig.fill(this.spawnerSellFill);
    if (this.spawnerSpots == null) goto L357;
    v1 = this.spawnerSpots.iterator();
    L320:
    if (v1.hasNext() ==0) goto L357;
    v2 = ((config.FwmConfig$SpawnerSpot)v1.next());
    if (v2 == null) goto L354;
    v2.sellFill = config.FwmConfig.fill(v2.sellFill);
    L354:
    goto L320;
    L357:
    this.spawnerDropMin = config.FwmConfig.clamp(this.spawnerDropMin, 100, 230400);
    this.moneyGoal = Math.max(0, Math.min(1000000000000, this.moneyGoal));
    this.historyDays = config.FwmConfig.clamp(this.historyDays, 1, 90);
    this.invValueSource = config.FwmConfig.clamp(this.invValueSource, 0, 3);
    if (cmp(this.menuHue,1) >0) goto L437;
    if (Float.isNaN(this.menuHue) ==0) goto L444;
    L437:
    this.menuHue = -1.0;
    L444:
    if (Float.isNaN(this.menuSat) ==0) goto L459;
    this.menuSat = 1;
    L459:
    this.menuSat = Math.max(0, Math.min(1, this.menuSat));
    if (cmp(this.tintHue,1) >0) goto L494;
    if (Float.isNaN(this.tintHue) ==0) goto L501;
    L494:
    this.tintHue = -1.0;
    L501:
    if (Float.isNaN(this.tintSat) ==0) goto L516;
    this.tintSat = 0;
    L516:
    this.tintSat = Math.max(0, Math.min(1, this.tintSat));
    return;
}

private static boolean addMissing(List list, List defaults) {
    if (list != null) goto L5;
    return;
    L5:
    v2 = defaults.iterator();
    L12:
    if (v2.hasNext() ==0) goto L106;
    v3 = ((String)v2.next());
    v4 = 0;
    v5 = list.iterator();
    L42:
    if (v5.hasNext() ==0) goto L90;
    v6 = ((String)v5.next());
    if (v6 == null) goto L87;
    if (v6.trim().equalsIgnoreCase(v3) ==0) goto L87;
    v4 = 1;
    goto L90;
    L87:
    goto L42;
    L90:
    if (v4 !=0) goto L103;
    list.add(v3);
    L103:
    goto L12;
    L106:
    return;
}

private static int fill(int value) {
    if (value >0) goto L6;
    return 0;
    L6:
    return config.FwmConfig.clamp(value, 10, 100);
}

private static int clamp(int value, int min, int max) {
    return Math.max(min, Math.min(max, value));
}

private static boolean lambda$copyFrom$0(String key) {
    if (config.Features.canSwitch(key) !=0) goto L11;
    goto L12;
    L11:
    L12:
    return 0;
}

static void <clinit>() {
    config.FwmConfig.LEGACY_SELL_PATTERNS = List.of("\\+\\$([\\d,]+\\.\\d{2})\\s*\\(Basis:\\s*\\$([\\d,]+\\.\\d{2}),\\s*Bonus:\\s*\\+\\$([\\d,]+\\.\\d{2})", "\\+\\$([\\d,]+(?:\\.\\d{1,2})?)\\s*\\(Basis:\\s*\\$([\\d,]+(?:\\.\\d{1,2})?),\\s*Bonus:\\s*\\+\\$([\\d,]+(?:\\.\\d{1,2})?)", "\\+\\$([\\d.,]+)\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+)");
    config.FwmConfig.DEFAULT_MSG_SELF = List.of("du", "dir", "dich", "mir", "mich", "ich", "you", "me", "self");
    config.FwmConfig.PRESET_FLOWER_FARM = List.of("minecraft:poppy", "minecraft:dandelion", "minecraft:wheat_seeds");
    new String[24][0] = "FMW";
    new String[24][1] = "FWH";
    new String[24][2] = "FHW";
    new String[24][3] = "FHM";
    new String[24][4] = "FMH";
    new String[24][5] = "FVM";
    new String[24][6] = "FVVM";
    new String[24][7] = "FWN";
    new String[24][8] = "FHN";
    new String[24][9] = "FVN";
    new String[24][10] = "FVH";
    new String[24][11] = "FMV";
    new String[24][12] = "FMN";
    new String[24][13] = "FNM";
    new String[24][14] = "FNW";
    new String[24][15] = "FHV";
    new String[24][16] = "FVW";
    new String[24][17] = "FWV";
    new String[24][18] = "FWW";
    new String[24][19] = "FMM";
    new String[24][20] = "FVV";
    new String[24][21] = "FWMM";
    new String[24][22] = "FMX";
    new String[24][23] = "FMS";
    config.FwmConfig.DEFAULT_FAKE_TAGS = List.of(new String[24]);
    config.FwmConfig.DEFAULT_FAKE_TAGS_EXACT = List.of("FWBBC");
    config.FwmConfig.DEFAULT_PAY_OUT_PATTERNS = List.of("Du\\s+hast\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+(?:gesendet|gezahlt|\u00fcberwiesen|gegeben|geschickt)", "Du\\s+hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+an\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+(?:gesendet|gezahlt|\u00fcberwiesen|gegeben|geschickt)", "You\\s+(?:sent|paid)\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+to\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})");
    config.FwmConfig.DEFAULT_AH_BUY_PATTERNS = List.of("Du\\s+hast\\s+(?<item>.+?)\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+gekauft", "(?<item>.+?)\\s+(?:erfolgreich\\s+)?gekauft\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    config.FwmConfig.DEFAULT_AH_SELL_PATTERNS = List.of("(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+hat\\s+(?<item>.+?)\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+gekauft", "Dein(?:e)?\\s+(?<item>.+?)\\s+wurde(?:n)?\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+verkauft");
    config.FwmConfig.DEFAULT_PAY_PATTERNS = List.of("(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+hat\\s+dir\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?", "von\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+erhalten", "hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+von\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})", "received\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+from\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})", "(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+(?:sent|paid)\\s+you\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    config.FwmConfig.LEGACY_PAY_PATTERNS = List.of("(?<name>[A-Za-z0-9_]{3,16})\\s+hat\\s+dir\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?", "von\\s+(?<name>[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+erhalten", "hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+von\\s+(?<name>[A-Za-z0-9_]{3,16})", "received\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+from\\s+(?<name>[A-Za-z0-9_]{3,16})", "(?<name>[A-Za-z0-9_]{3,16})\\s+(?:sent|paid)\\s+you\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    config.FwmConfig.DEFAULT_STREAMER_AD_TEMPLATES = List.of("[Name] versteigert [Item] - Gebote per /pay [Spieler], ab [Gebot] - noch [Zeit]", "Auktion f\u00fcr [Item] beendet - Gewinner: [Gewinner] mit [Preis]", "[Name] verlost [Item] - mitmachen: [Befehl] - noch [Zeit]");
    config.FwmConfig.LEGACY_STREAMER_AD_TEMPLATES = List.of("[Name] versteigert [Item] \u2013 Gebote per /pay [Name], ab [Gebot] \u2013 noch [Zeit]", "Auktion f\u00fcr [Item] beendet \u2013 Gewinner: [Gewinner] mit [Preis]", "[Name] verlost [Item] \u2013 mitmachen: [Befehl] \u2013 noch [Zeit]");
    config.FwmConfig.DEFAULT_STREAMER_AD_ROTATION = List.of("[Name] ist live auf Twitch! Wir machen gerade Auktionen, aktuell dran: [Item]", "[Name] macht gerade Auktionen! Aktuell: [Item] - live auf Twitch");
    config.FwmConfig.DEFAULT_SPAWNER_AD_TEMPLATES = List.of("[Name] | FWM Trusted | Kaufe [Spawner] f\u00fcr [Ankauf Preis]", "[Name] | FWM Trusted | Verkaufe [Spawner] f\u00fcr [Verkauf Preis]", "[Name] | FWM Trusted | Kaufe [Spawner] f\u00fcr [Ankauf Preis] | Verkaufe f\u00fcr [Verkauf Preis]", "[Name] | FWM Trusted | Spawner kaufen & verkaufen \u2013 /msg [Name]", "[Name] | FWM Trusted | Faire Spawner-Preise \u2013 mehr im FWM-Discord");
    config.FwmConfig.OLD_SPAWNER_AD_TEMPLATES = List.of("{name} | FWM Trusted | Kaufe {spawner} f\u00fcr {kauf}", "{name} | FWM Trusted | Verkaufe {spawner} f\u00fcr {verkauf}", "{name} | FWM Trusted | Kaufe {spawner} f\u00fcr {kauf} | Verkaufe f\u00fcr {verkauf}", "{name} | FWM Trusted | Suche {anzahl}x {spawner} f\u00fcr je {kauf}", "{name} | FWM Trusted | Spawner kaufen & verkaufen \u2013 /msg {name}", "{name} | FWM Trusted | Faire Spawner-Preise \u2013 mehr im FWM-Discord");
    config.FwmConfig.INSTANCE = ?;
    return;
}

