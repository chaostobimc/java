/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Float
 *  java.lang.Integer
 *  java.lang.Iterable
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.HashSet
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  java.util.Set
 */
package net.friendswithmoney.fwm.config;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.friendswithmoney.fwm.config.ChatFilter;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.TestRun;

public class FwmConfig {
    public static final int MIN_REOPEN_DELAY = 0;
    public static final int DEFAULT_REOPEN_DELAY = 100;
    public static final int MAX_REOPEN_DELAY = 200;
    public static final int MIN_ITEM_DELAY = 1;
    public static final int MAX_ITEM_DELAY = 20;
    public static final int MIN_BURST = 1;
    public static final int MAX_BURST = 13;
    public static final int MIN_PRESS_DELAY = 0;
    public static final int MAX_PRESS_DELAY = 20;
    public static final int MAX_SLOT = 107;
    public static final int HOURS = 24;
    public static final int MAX_TESTS = 30;
    public static final int MIN_REFRESH_MINUTES = 5;
    public static final int MAX_REFRESH_MINUTES = 60;
    public static final int MIN_TOOLGUARD_PERCENT = 1;
    public static final int MAX_TOOLGUARD_PERCENT = 50;
    public static final int MIN_AUCTION_SECONDS = 15;
    public static final int MAX_AUCTION_SECONDS = 3600;
    public static final int MIN_SECOND_CHAT_WIDTH = 100;
    public static final int MAX_SECOND_CHAT_WIDTH = 420;
    public static final int ALIGN_LEFT = 0;
    public static final int ALIGN_CENTER = 1;
    public static final int ALIGN_RIGHT = 2;
    public static final int MIN_SECOND_CHAT_LINES = 3;
    public static final int MAX_SECOND_CHAT_LINES = 16;
    public static final int MIN_SCALE = 70;
    public static final int MAX_SCALE = 150;
    public static final String DEFAULT_SELL_PATTERN = "(?:^|\\]\\s?)\\+\\$([\\d.,]+)(?:\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+))?";
    public static final List<String> LEGACY_SELL_PATTERNS = List.of((Object)"\\+\\$([\\d,]+\\.\\d{2})\\s*\\(Basis:\\s*\\$([\\d,]+\\.\\d{2}),\\s*Bonus:\\s*\\+\\$([\\d,]+\\.\\d{2})", (Object)"\\+\\$([\\d,]+(?:\\.\\d{1,2})?)\\s*\\(Basis:\\s*\\$([\\d,]+(?:\\.\\d{1,2})?),\\s*Bonus:\\s*\\+\\$([\\d,]+(?:\\.\\d{1,2})?)", (Object)"\\+\\$([\\d.,]+)\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+)");
    public static final String DEFAULT_PRICE_PATTERN = "(?:Preis|Price)\\s*:\\s*(\\S+)";
    public static final String DEFAULT_PAY_PATTERN = "(?i)(?=.*[$\u20ac]\\s?\\d)(?=.*(bezahlt|\u00fcberwiesen|gesendet|erhalten|gezahlt|payment|zahlung|paid|sent you|has sent|received))";
    public static final String DEFAULT_MSG_PATTERN = "([.*]?[A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$";
    public static final String LEGACY_MSG_PATTERN = "([A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$";
    public static final List<String> DEFAULT_MSG_SELF = List.of((Object)"du", (Object)"dir", (Object)"dich", (Object)"mir", (Object)"mich", (Object)"ich", (Object)"you", (Object)"me", (Object)"self");
    public static final List<String> PRESET_FLOWER_FARM = List.of((Object)"minecraft:poppy", (Object)"minecraft:dandelion", (Object)"minecraft:wheat_seeds");
    public static final String CLAN_TAG = "FWM";
    public static final List<String> DEFAULT_FAKE_TAGS = List.of((Object[])new String[]{"FMW", "FWH", "FHW", "FHM", "FMH", "FVM", "FVVM", "FWN", "FHN", "FVN", "FVH", "FMV", "FMN", "FNM", "FNW", "FHV", "FVW", "FWV", "FWW", "FMM", "FVV", "FWMM", "FMX", "FMS"});
    public static final List<String> DEFAULT_FAKE_TAGS_EXACT = List.of((Object)"FWBBC");
    public static final List<String> DEFAULT_PAY_OUT_PATTERNS = List.of((Object)"Du\\s+hast\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+(?:gesendet|gezahlt|\u00fcberwiesen|gegeben|geschickt)", (Object)"Du\\s+hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+an\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+(?:gesendet|gezahlt|\u00fcberwiesen|gegeben|geschickt)", (Object)"You\\s+(?:sent|paid)\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+to\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})");
    public static final List<String> DEFAULT_AH_BUY_PATTERNS = List.of((Object)"Du\\s+hast\\s+(?<item>.+?)\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+gekauft", (Object)"(?<item>.+?)\\s+(?:erfolgreich\\s+)?gekauft\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    public static final List<String> DEFAULT_AH_SELL_PATTERNS = List.of((Object)"(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+hat\\s+(?<item>.+?)\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+gekauft", (Object)"Dein(?:e)?\\s+(?<item>.+?)\\s+wurde(?:n)?\\s+f\u00fcr\\s+[$](?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+verkauft");
    public static final List<String> DEFAULT_PAY_PATTERNS = List.of((Object)"(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+hat\\s+dir\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?", (Object)"von\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+erhalten", (Object)"hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+von\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})", (Object)"received\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+from\\s+(?<name>[.*]?[A-Za-z0-9_]{3,16})", (Object)"(?<name>[.*]?[A-Za-z0-9_]{3,16})\\s+(?:sent|paid)\\s+you\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    public static final List<String> LEGACY_PAY_PATTERNS = List.of((Object)"(?<name>[A-Za-z0-9_]{3,16})\\s+hat\\s+dir\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?", (Object)"von\\s+(?<name>[A-Za-z0-9_]{3,16})\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+erhalten", (Object)"hast\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+von\\s+(?<name>[A-Za-z0-9_]{3,16})", (Object)"received\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?\\s+from\\s+(?<name>[A-Za-z0-9_]{3,16})", (Object)"(?<name>[A-Za-z0-9_]{3,16})\\s+(?:sent|paid)\\s+you\\s+[$]?(?<amount>[0-9][0-9.,]*)\\s*(?<unit>[kmb])?");
    public static final int SPAWNER_AD_SLOTS = 5;
    public static final int STREAMER_AD_SLOTS = 3;
    public static final List<String> DEFAULT_STREAMER_AD_TEMPLATES = List.of((Object)"[Name] versteigert [Item] - Gebote per /pay [Spieler], ab [Gebot] - noch [Zeit]", (Object)"Auktion f\u00fcr [Item] beendet - Gewinner: [Gewinner] mit [Preis]", (Object)"[Name] verlost [Item] - mitmachen: [Befehl] - noch [Zeit]");
    public static final List<String> LEGACY_STREAMER_AD_TEMPLATES = List.of((Object)"[Name] versteigert [Item] \u2013 Gebote per /pay [Name], ab [Gebot] \u2013 noch [Zeit]", (Object)"Auktion f\u00fcr [Item] beendet \u2013 Gewinner: [Gewinner] mit [Preis]", (Object)"[Name] verlost [Item] \u2013 mitmachen: [Befehl] \u2013 noch [Zeit]");
    public static final List<String> DEFAULT_STREAMER_AD_ROTATION = List.of((Object)"[Name] ist live auf Twitch! Wir machen gerade Auktionen, aktuell dran: [Item]", (Object)"[Name] macht gerade Auktionen! Aktuell: [Item] - live auf Twitch");
    public static final String DEFAULT_STREAMER_SERVER = "Twitch";
    public static final List<String> DEFAULT_SPAWNER_AD_TEMPLATES = List.of((Object)"[Name] | FWM Trusted | Kaufe [Spawner] f\u00fcr [Ankauf Preis]", (Object)"[Name] | FWM Trusted | Verkaufe [Spawner] f\u00fcr [Verkauf Preis]", (Object)"[Name] | FWM Trusted | Kaufe [Spawner] f\u00fcr [Ankauf Preis] | Verkaufe f\u00fcr [Verkauf Preis]", (Object)"[Name] | FWM Trusted | Spawner kaufen & verkaufen \u2013 /msg [Name]", (Object)"[Name] | FWM Trusted | Faire Spawner-Preise \u2013 mehr im FWM-Discord");
    private static final List<String> OLD_SPAWNER_AD_TEMPLATES = List.of((Object)"{name} | FWM Trusted | Kaufe {spawner} f\u00fcr {kauf}", (Object)"{name} | FWM Trusted | Verkaufe {spawner} f\u00fcr {verkauf}", (Object)"{name} | FWM Trusted | Kaufe {spawner} f\u00fcr {kauf} | Verkaufe f\u00fcr {verkauf}", (Object)"{name} | FWM Trusted | Suche {anzahl}x {spawner} f\u00fcr je {kauf}", (Object)"{name} | FWM Trusted | Spawner kaufen & verkaufen \u2013 /msg {name}", (Object)"{name} | FWM Trusted | Faire Spawner-Preise \u2013 mehr im FWM-Discord");
    public static final FwmConfig INSTANCE = new FwmConfig();
    public List<String> fakeTags = new ArrayList(DEFAULT_FAKE_TAGS);
    public List<String> fakeTagsExact = new ArrayList(DEFAULT_FAKE_TAGS_EXACT);
    public boolean enabled = false;
    public String sellCommand = "/sell";
    public int reopenDelayTicks = 100;
    public int itemMoveDelayTicks = 1;
    public int transferBurst = 10;
    public int sellPressDelayTicks = 2;
    public int sellButtonSlot = 35;
    public List<String> sellWhitelist = new ArrayList();
    public String apiBaseUrl = "https://269153435.de";
    public List<String> newsShownIds = null;
    public List<FightKit> fightKits = new ArrayList();
    public int fightKitSelected = 0;
    public boolean fightKitShowInInventory = false;
    public List<String> waypointsGroupOff = new ArrayList();
    public List<String> waypointsDone = new ArrayList();
    public int spawnerRefreshMinutes = 20;
    public String spawnerApiUrl = "https://friendswithmoney.net/api/spawner-types";
    public String discordUrl = "https://discord.gg/friendswithmoney";
    public String websiteUrl = "https://friendswithmoney.net";
    public String rtpApiUrl = "https://269153435.de/api/mod/rtp";
    public String rtpSiteUrl = "https://friendswithmoney.net/tools/rtp";
    public boolean rtpShowOwnPos = true;
    public int rtpMiniRadius = 32;
    public String membersApiUrl = "https://friendswithmoney.net/api/clan-members";
    public String marketApiUrl = "https://friendswithmoney.net/api/marketplace";
    public String marketHistoryUrl = "https://269153435.de/api/mod/market";
    public int memberRefreshMinutes = 30;
    public double memberTagRange = 48.0;
    public int toolGuardPercent = 10;
    public boolean toolGuardWarning = true;
    public boolean toolGuardBlock = true;
    public String msgCommand = "msg";
    public String msgPattern = "([.*]?[A-Za-z0-9_]{2,16})\\s*(?:->|\u2192|\u00bb)\\s*([.*]?[A-Za-z0-9_]{2,16})\\s*:\\s*(.+)$";
    public List<String> msgSelfNames = new ArrayList(DEFAULT_MSG_SELF);
    public boolean msgShowEnvelope = false;
    public boolean envelopeOff66 = false;
    public boolean msgShowToast = false;
    public String msgIgnoreCommand = "ignore";
    public List<String> msgIgnored = new ArrayList();
    public List<String> msgHidden = new ArrayList();
    public int msgContactLimit = 30;
    public int msgHistoryPerContact = 200;
    public boolean msgDebug = false;
    public String unitPricePattern = "(?:Preis|Price)\\s*:\\s*(\\S+)";
    public boolean secondChatTpa = true;
    public boolean secondChatMsg = true;
    public boolean secondChatPay = true;
    public boolean secondChatMarket = true;
    public boolean marketAlertPanel = true;
    public boolean marketToast = false;
    public boolean pricePingCollapsed = false;
    public boolean secondChatClan = false;
    public boolean secondChatHugoOther = false;
    public List<ChatFilter> secondChatFilters = new ArrayList();
    public String secondChatPayPattern = "(?i)(?=.*[$\u20ac]\\s?\\d)(?=.*(bezahlt|\u00fcberwiesen|gesendet|erhalten|gezahlt|payment|zahlung|paid|sent you|has sent|received))";
    public int tpaTimeoutSeconds = 60;
    public String tpaDonePattern = "(angenommen|abgelehnt|abgelaufen|abgebrochen|accept|denied|declin|expire|cancel)";
    public String tpaSentPattern = "Du hast (?<name>\\S+) (?:eine Teleportations-Anfrage gesendet|gefragt, ob er sich zu dir teleportieren)";
    public String tpaRefusedPattern = "(Teleportations-Anfragen deaktiviert|keine Teleportations-Anfragen empfangen|nicht online|nicht gefunden|unsicheren Ort|Bitte warte|w\u00e4hrend eines Kampfes|im Kampf nicht)";
    public String tpaAlreadyPattern = "bereits eine Teleportations-Anfrage";
    public String tpaEndedPattern = "(Anfrage ist abgelaufen|ausgehende Teleportations-Anfrage\\(n\\) gel\u00f6scht|keine ausgehenden Teleportations-Anfragen)";
    public String serverPrefix = "[HugoSMP]";
    public boolean secondChatRemove = true;
    public int secondChatLines = 8;
    public int secondChatWidth = 340;
    public int secondChatAlign = 0;
    public String language = "de";
    public String sellMessagePattern = "(?:^|\\]\\s?)\\+\\$([\\d.,]+)(?:\\s*\\(Basis:\\s*\\$([\\d.,]+),\\s*Bonus:\\s*\\+\\$([\\d.,]+))?";
    public long lifetimeEarnedCents = 0L;
    public long[] hourlyEarnedCents = new long[24];
    public long hourlyBaseHour = 0L;
    public List<TestRun> tests = new ArrayList();
    public List<String> disabledFeatures = new ArrayList();
    public List<String> enabledFeatures = new ArrayList();
    public int menuScale = 100;
    public List<String> sidebarOrder = new ArrayList();
    public List<String> sidebarHidden = new ArrayList();
    public boolean sidebarHideDisabled = false;
    public static final float MIN_HUD_SCALE = 0.6f;
    public static final float MAX_HUD_SCALE = 2.5f;
    public List<String> payPatterns = new ArrayList(DEFAULT_PAY_PATTERNS);
    public int auctionSeconds = 60;
    public long auctionStartBid = 10000L;
    public long auctionMinStep = 0L;
    public boolean auctionStackPayments = false;
    public boolean auctionAntiSnipe = true;
    public int auctionAntiSnipeSeconds = 10;
    public static final long ANTI_SNIPE_WINDOW_MS = 3000L;
    public static final int MIN_ANTI_SNIPE_SECONDS = 1;
    public static final int MAX_ANTI_SNIPE_SECONDS = 60;
    public String giveawayWord = "GEWINN";
    public int giveawayLimit = 50;
    public int giveawaySeconds = 600;
    public HudSpot hudMacro = new HudSpot();
    public HudSpot hudToast = new HudSpot();
    public HudSpot hudEnvelope = new HudSpot();
    public HudSpot hudTool = new HudSpot();
    public HudSpot hudSecondChat = new HudSpot();
    public HudSpot hudTpa = new HudSpot();
    public HudSpot hudMinimap = new HudSpot();
    public HudSpot hudPricePing = new HudSpot();
    public HudSpot hudMoney = new HudSpot();
    public HudSpot hudInvValue = new HudSpot();
    public HudSpot hudAuction = new HudSpot();
    public boolean auctionHudCard = true;
    public boolean spawnerHudCard = false;
    public HudSpot hudSpawnerPrices = FwmConfig.biggerSpot();
    public boolean spawnerHudHeader = true;
    public boolean spawnerHudIcons = true;
    public int spawnerHudBg = 0;
    public int spawnerHudOpacity = 90;
    public int spawnerHudBorder = 1;
    public int spawnerHudBuyColor = 65360;
    public int spawnerHudSellColor = 0xFF0000;
    public boolean spawnerHudGrid = false;
    public List<String> spawnerHudHidden = new ArrayList();
    public Map<String, String[]> spawnerHudPrices = new LinkedHashMap();
    public int spawnerHudStyle = 0;
    public String spawnerHudTitle = "";
    public String spawnerHudLabelName = "";
    public String spawnerHudLabelBuy = "";
    public String spawnerHudLabelSell = "";
    public boolean spawnerHudShowBuy = true;
    public boolean spawnerHudShowSell = true;
    public int spawnerHudBgColor = 789516;
    public int spawnerHudTextColor = 0xF5F5F5;
    public int spawnerHudHeadColor = 0xF5F5F5;
    public int spawnerHudBorderColor = 0xFFFFFF;
    public int spawnerHudBorderWidth = 1;
    public int spawnerHudRadius = 6;
    public int spawnerHudPadding = 8;
    public int spawnerHudRowGap = 12;
    public boolean spawnerHudShadow = false;
    public boolean spawnerHudTextShadow = true;
    public boolean spawnerHudDivider = true;
    public boolean spawnerHudHeadBold = true;
    public int spawnerHudGradientSpeed = 5;
    public List<String> autoTpaNames = new ArrayList();
    public boolean autoTpaHere = false;
    public int autoTpaDelayTicks = 10;
    public boolean spawnerAutoDrop = false;
    public boolean spawnerAutoStop = false;
    public boolean spawnerAutoSell = false;
    public int spawnerClickDelayTicks = 4;
    public int spawnerRepeatSeconds = 60;
    public boolean spawnerSidePanel = true;
    public boolean spawnerLabels = true;
    public List<SpawnerSpot> spawnerSpots = new ArrayList();
    public static final String MODE_BONES = "bones";
    public static final String MODE_DROP = "drop";
    public static final String MODE_SELL = "sell";
    public static final int SPAWNER_PAGE = 2304;
    public String spawnerMode = "bones";
    public int spawnerSellFill = 80;
    public int spawnerDropMin = 2304;
    public boolean moneyHud = false;
    public long moneyGoal = 10000000L;
    public boolean moneyGoalSound = true;
    public boolean moneyShowAhSold = true;
    public boolean moneyShowAhBought = true;
    public boolean moneyShowOrderFilled = true;
    public boolean moneyShowOrderDelivered = true;
    public boolean moneySidebarSync = true;
    public boolean moneyHideAmounts = false;
    public boolean moneyLogLines = false;
    public List<String> moneyPayOutPatterns = new ArrayList(DEFAULT_PAY_OUT_PATTERNS);
    public List<String> moneyAhBuyPatterns = new ArrayList(DEFAULT_AH_BUY_PATTERNS);
    public List<String> moneyAhSellPatterns = new ArrayList(DEFAULT_AH_SELL_PATTERNS);
    public boolean historyEnabled = true;
    public int historyDays = 30;
    public boolean historyMergeSell = true;
    public boolean invValueHud = false;
    public boolean invValueInInventory = false;
    public int invValueSource = 0;
    public boolean invValueShulkers = true;
    public boolean ecValue = true;
    public Map<String, String> textureMix = new LinkedHashMap();
    public boolean textureMixDisableSources = true;
    public int textureMixVersion = 0;
    public List<String> textureFavorites = new ArrayList();
    public List<TextureTheme> textureThemes = new ArrayList();
    public List<String> textureMixBefore = null;
    public List<PricePing> pricePings = new ArrayList();
    public boolean antiLeakBedrock = true;
    public boolean antiLeakOres = true;
    public boolean antiLeakRotation = true;
    public boolean antiLeakStones = false;
    public boolean antiLeakBiome = false;
    public boolean antiLeakCoords = true;
    public String antiLeakApplied = "";
    public String clanLogosApplied = "";
    public Map<String, SpawnerAdPrice> spawnerAdPrices = new HashMap();
    public List<String> spawnerAdTemplates = new ArrayList(DEFAULT_SPAWNER_AD_TEMPLATES);
    public List<String> streamerAdTemplates = new ArrayList(DEFAULT_STREAMER_AD_TEMPLATES);
    public List<String> streamerAdRotation = new ArrayList(DEFAULT_STREAMER_AD_ROTATION);
    public String streamerName = "";
    public String streamerServer = "";
    public float menuHue = -1.0f;
    public float menuSat = 1.0f;
    public float tintHue = -1.0f;
    public float tintSat = 0.0f;
    public String menuPalette = "";
    public boolean menuSounds = true;
    public boolean presenceShowOthers = true;
    public String menuKeyBinding = "";
    public String macroKeyBinding = "";
    public String messagesKeyBinding = "";
    public String auctionKeyBinding = "";
    public String giveawayKeyBinding = "";
    public boolean secondChatBackground = true;
    public int secondChatBackgroundOpacity = 50;
    public boolean tutorialGreeted = false;
    public boolean tutorialSeen = false;
    public String tutorialVersion = "";
    public String tutorialServerMatch = "hugo";
    private transient Set<String> whitelistLookup;

    static List<String> streamerAdSlots(List<String> saved) {
        ArrayList out = new ArrayList();
        for (int i = 0; i < 3; ++i) {
            String text;
            String string = text = saved != null && i < saved.size() ? (String)saved.get(i) : null;
            if (text != null && text.equals(LEGACY_STREAMER_AD_TEMPLATES.get(i))) {
                text = null;
            }
            out.add((Object)(text == null ? (String)DEFAULT_STREAMER_AD_TEMPLATES.get(i) : text));
        }
        return out;
    }

    static List<String> fiveAdSlots(List<String> saved) {
        if (saved == null || saved.isEmpty() || saved.equals(OLD_SPAWNER_AD_TEMPLATES)) {
            return new ArrayList(DEFAULT_SPAWNER_AD_TEMPLATES);
        }
        ArrayList out = new ArrayList();
        for (String text : saved) {
            if (out.size() == 5) break;
            out.add((Object)(text == null ? "" : text.replace((CharSequence)"{name}", (CharSequence)"[Name]").replace((CharSequence)"{spawner}", (CharSequence)"[Spawner]").replace((CharSequence)"{kauf}", (CharSequence)"[Ankauf Preis]").replace((CharSequence)"{verkauf}", (CharSequence)"[Verkauf Preis]").replace((CharSequence)"{anzahl}", (CharSequence)"[Anzahl]")));
        }
        while (out.size() < 5) {
            out.add((Object)"");
        }
        return out;
    }

    private static HudSpot biggerSpot() {
        HudSpot spot = new HudSpot();
        spot.scale = 1.4f;
        return spot;
    }

    private FwmConfig() {
    }

    public boolean isWhitelisted(String itemId) {
        return this.lookup().contains((Object)itemId);
    }

    public boolean toggleWhitelisted(String itemId) {
        boolean added;
        if (this.lookup().contains((Object)itemId)) {
            this.lookup().remove((Object)itemId);
            this.sellWhitelist.remove((Object)itemId);
            added = false;
        } else {
            this.lookup().add((Object)itemId);
            this.sellWhitelist.add((Object)itemId);
            added = true;
        }
        return added;
    }

    public void setWhitelist(Iterable<String> itemIds) {
        this.sellWhitelist = new ArrayList();
        for (String id : itemIds) {
            this.sellWhitelist.add((Object)id);
        }
        this.whitelistLookup = null;
    }

    public void clearWhitelist() {
        this.sellWhitelist = new ArrayList();
        this.whitelistLookup = null;
    }

    public int whitelistSize() {
        return this.sellWhitelist == null ? 0 : this.sellWhitelist.size();
    }

    private Set<String> lookup() {
        if (this.whitelistLookup == null) {
            this.whitelistLookup = new HashSet(this.sellWhitelist == null ? List.of() : this.sellWhitelist);
        }
        return this.whitelistLookup;
    }

    public void copyFrom(FwmConfig other) {
        if (other == null) {
            return;
        }
        this.enabled = other.enabled;
        this.reopenDelayTicks = other.reopenDelayTicks;
        this.itemMoveDelayTicks = other.itemMoveDelayTicks;
        this.transferBurst = other.transferBurst;
        this.sellPressDelayTicks = other.sellPressDelayTicks;
        this.sellButtonSlot = other.sellButtonSlot;
        this.spawnerRefreshMinutes = other.spawnerRefreshMinutes;
        this.lifetimeEarnedCents = other.lifetimeEarnedCents;
        this.hourlyBaseHour = other.hourlyBaseHour;
        if (other.hourlyEarnedCents != null && other.hourlyEarnedCents.length == 24) {
            this.hourlyEarnedCents = other.hourlyEarnedCents;
        }
        if (other.tests != null) {
            this.tests = other.tests;
        }
        if (other.enabledFeatures != null) {
            this.enabledFeatures = other.enabledFeatures;
        }
        if (other.disabledFeatures != null) {
            this.disabledFeatures = other.disabledFeatures;
            this.disabledFeatures.removeIf(key -> !Features.canSwitch(key));
        }
        this.menuScale = other.menuScale == 0 ? 100 : other.menuScale;
        this.secondChatBackground = other.secondChatBackground;
        this.secondChatBackgroundOpacity = other.secondChatBackgroundOpacity;
        this.tutorialGreeted = other.tutorialGreeted;
        this.tutorialSeen = other.tutorialSeen;
        if (other.tutorialVersion != null) {
            this.tutorialVersion = other.tutorialVersion;
        }
        if (other.tutorialServerMatch != null) {
            this.tutorialServerMatch = other.tutorialServerMatch;
        }
        if (other.newsShownIds != null) {
            this.newsShownIds = new ArrayList(other.newsShownIds);
        }
        if (other.fightKits != null) {
            this.fightKits = new ArrayList();
            for (FightKit kit : other.fightKits) {
                if (kit == null) continue;
                if (kit.slots == null) {
                    kit.slots = new HashMap();
                }
                this.fightKits.add((Object)kit);
            }
        }
        this.fightKitSelected = Math.max((int)0, (int)other.fightKitSelected);
        this.fightKitShowInInventory = other.fightKitShowInInventory;
        if (other.waypointsGroupOff != null) {
            this.waypointsGroupOff = new ArrayList(other.waypointsGroupOff);
        }
        if (other.waypointsDone != null) {
            this.waypointsDone = new ArrayList(other.waypointsDone);
        }
        if (other.apiBaseUrl != null && !other.apiBaseUrl.isBlank()) {
            this.apiBaseUrl = other.apiBaseUrl;
        }
        if (other.spawnerApiUrl != null && !other.spawnerApiUrl.isBlank()) {
            this.spawnerApiUrl = other.spawnerApiUrl;
        }
        if (other.discordUrl != null && !other.discordUrl.isBlank()) {
            this.discordUrl = other.discordUrl;
        }
        if (other.websiteUrl != null && !other.websiteUrl.isBlank()) {
            this.websiteUrl = other.websiteUrl;
        }
        if (other.rtpApiUrl != null && !other.rtpApiUrl.isBlank()) {
            this.rtpApiUrl = other.rtpApiUrl;
        }
        if (other.rtpSiteUrl != null && !other.rtpSiteUrl.isBlank()) {
            this.rtpSiteUrl = other.rtpSiteUrl;
        }
        if (other.membersApiUrl != null && !other.membersApiUrl.isBlank()) {
            this.membersApiUrl = other.membersApiUrl;
        }
        if (other.marketApiUrl != null && !other.marketApiUrl.isBlank()) {
            this.marketApiUrl = other.marketApiUrl;
        }
        if (other.marketHistoryUrl != null && !other.marketHistoryUrl.isBlank()) {
            this.marketHistoryUrl = other.marketHistoryUrl;
        }
        if (other.memberRefreshMinutes > 0) {
            this.memberRefreshMinutes = other.memberRefreshMinutes;
        }
        if (other.memberTagRange > 0.0) {
            this.memberTagRange = other.memberTagRange;
        }
        if (other.toolGuardPercent > 0) {
            this.toolGuardPercent = other.toolGuardPercent;
        }
        this.toolGuardWarning = other.toolGuardWarning;
        this.toolGuardBlock = other.toolGuardBlock;
        if (other.msgCommand != null && !other.msgCommand.isBlank()) {
            this.msgCommand = other.msgCommand;
        }
        if (other.msgPattern != null && !other.msgPattern.isBlank() && !other.msgPattern.equals((Object)LEGACY_MSG_PATTERN)) {
            this.msgPattern = other.msgPattern;
        }
        if (other.msgSelfNames != null && !other.msgSelfNames.isEmpty()) {
            this.msgSelfNames = other.msgSelfNames;
        }
        if (other.msgContactLimit > 0) {
            this.msgContactLimit = other.msgContactLimit;
        }
        if (other.msgHistoryPerContact > 0) {
            this.msgHistoryPerContact = other.msgHistoryPerContact;
        }
        this.msgDebug = other.msgDebug;
        if (other.unitPricePattern != null && !other.unitPricePattern.isBlank()) {
            this.unitPricePattern = other.unitPricePattern;
        }
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
        if (other.secondChatFilters != null) {
            this.secondChatFilters = other.secondChatFilters;
        }
        if (other.tpaTimeoutSeconds > 0) {
            this.tpaTimeoutSeconds = other.tpaTimeoutSeconds;
        }
        if (other.tpaSentPattern != null && !other.tpaSentPattern.isBlank()) {
            this.tpaSentPattern = other.tpaSentPattern;
        }
        if (other.tpaRefusedPattern != null && !other.tpaRefusedPattern.isBlank()) {
            this.tpaRefusedPattern = other.tpaRefusedPattern;
        }
        if (other.tpaAlreadyPattern != null && !other.tpaAlreadyPattern.isBlank()) {
            this.tpaAlreadyPattern = other.tpaAlreadyPattern;
        }
        if (other.tpaEndedPattern != null && !other.tpaEndedPattern.isBlank()) {
            this.tpaEndedPattern = other.tpaEndedPattern;
        }
        if (other.serverPrefix != null) {
            this.serverPrefix = other.serverPrefix;
        }
        if (other.tpaDonePattern != null && !other.tpaDonePattern.isBlank()) {
            this.tpaDonePattern = other.tpaDonePattern;
        }
        if (other.secondChatPayPattern != null && !other.secondChatPayPattern.isBlank()) {
            this.secondChatPayPattern = other.secondChatPayPattern;
        }
        if (other.secondChatLines > 0) {
            this.secondChatLines = other.secondChatLines;
        }
        if (other.secondChatWidth > 0) {
            this.secondChatWidth = other.secondChatWidth;
        }
        this.secondChatAlign = other.secondChatAlign;
        if (other.sidebarOrder != null) {
            this.sidebarOrder = other.sidebarOrder;
        }
        if (other.sidebarHidden != null) {
            this.sidebarHidden = other.sidebarHidden;
        }
        this.sidebarHideDisabled = other.sidebarHideDisabled;
        this.rtpShowOwnPos = other.rtpShowOwnPos;
        if (other.rtpMiniRadius > 0) {
            this.rtpMiniRadius = other.rtpMiniRadius;
        }
        if (other.hudMacro != null) {
            this.hudMacro = other.hudMacro;
        }
        if (other.hudToast != null) {
            this.hudToast = other.hudToast;
        }
        if (other.hudEnvelope != null) {
            this.hudEnvelope = other.hudEnvelope;
        }
        if (other.hudTool != null) {
            this.hudTool = other.hudTool;
        }
        if (other.hudSecondChat != null) {
            this.hudSecondChat = other.hudSecondChat;
        }
        if (other.hudTpa != null) {
            this.hudTpa = other.hudTpa;
        }
        if (other.hudMinimap != null) {
            this.hudMinimap = other.hudMinimap;
        }
        if (other.hudPricePing != null) {
            this.hudPricePing = other.hudPricePing;
        }
        this.auctionStackPayments = other.auctionStackPayments;
        this.auctionAntiSnipe = other.auctionAntiSnipe;
        if (other.auctionAntiSnipeSeconds > 0) {
            this.auctionAntiSnipeSeconds = other.auctionAntiSnipeSeconds;
        }
        if (other.giveawayWord != null && !other.giveawayWord.isBlank()) {
            this.giveawayWord = other.giveawayWord;
        }
        if (other.giveawayLimit > 0) {
            this.giveawayLimit = other.giveawayLimit;
        }
        if (other.giveawaySeconds > 0) {
            this.giveawaySeconds = other.giveawaySeconds;
        }
        if (other.pricePings != null) {
            this.pricePings = other.pricePings;
        }
        this.antiLeakBedrock = other.antiLeakBedrock;
        this.antiLeakOres = other.antiLeakOres;
        this.antiLeakRotation = other.antiLeakRotation;
        this.antiLeakStones = other.antiLeakStones;
        this.antiLeakBiome = other.antiLeakBiome;
        this.antiLeakCoords = other.antiLeakCoords;
        if (other.antiLeakApplied != null) {
            this.antiLeakApplied = other.antiLeakApplied;
        }
        if (other.clanLogosApplied != null) {
            this.clanLogosApplied = other.clanLogosApplied;
        }
        if (other.spawnerAdPrices != null) {
            this.spawnerAdPrices = other.spawnerAdPrices;
        }
        this.spawnerAdTemplates = FwmConfig.fiveAdSlots(other.spawnerAdTemplates);
        this.streamerAdTemplates = FwmConfig.streamerAdSlots(other.streamerAdTemplates);
        if (other.streamerName != null) {
            this.streamerName = other.streamerName;
        }
        if (other.streamerAdRotation != null && other.streamerAdRotation.size() == DEFAULT_STREAMER_AD_ROTATION.size()) {
            this.streamerAdRotation = new ArrayList();
            for (int i = 0; i < DEFAULT_STREAMER_AD_ROTATION.size(); ++i) {
                String text = (String)other.streamerAdRotation.get(i);
                if (text == null || text.isBlank()) {
                    text = (String)DEFAULT_STREAMER_AD_ROTATION.get(i);
                } else if (text.contains((CharSequence)"[Server]")) {
                    String server = other.streamerServer == null || other.streamerServer.isBlank() ? DEFAULT_STREAMER_SERVER : other.streamerServer.trim();
                    text = text.replace((CharSequence)"[Server]", (CharSequence)server);
                }
                this.streamerAdRotation.add((Object)text);
            }
        }
        if (other.streamerServer != null) {
            this.streamerServer = other.streamerServer;
        }
        if (other.auctionSeconds > 0) {
            this.auctionSeconds = other.auctionSeconds;
        }
        if (other.auctionStartBid > 0L) {
            this.auctionStartBid = other.auctionStartBid;
        }
        if (other.payPatterns != null && !other.payPatterns.isEmpty() && !other.payPatterns.equals(LEGACY_PAY_PATTERNS)) {
            this.payPatterns = other.payPatterns;
        }
        if (other.menuPalette != null) {
            this.menuPalette = other.menuPalette;
        }
        this.menuHue = other.menuHue;
        this.menuSat = other.menuSat;
        this.tintHue = other.tintHue;
        this.tintSat = other.tintSat;
        this.menuSounds = other.menuSounds;
        this.presenceShowOthers = other.presenceShowOthers;
        if (other.menuKeyBinding != null) {
            this.menuKeyBinding = other.menuKeyBinding;
        }
        if (other.macroKeyBinding != null) {
            this.macroKeyBinding = other.macroKeyBinding;
        }
        if (other.auctionKeyBinding != null) {
            this.auctionKeyBinding = other.auctionKeyBinding;
        }
        if (other.giveawayKeyBinding != null) {
            this.giveawayKeyBinding = other.giveawayKeyBinding;
        }
        if (other.messagesKeyBinding != null) {
            this.messagesKeyBinding = other.messagesKeyBinding;
        }
        this.msgShowEnvelope = other.msgShowEnvelope;
        this.envelopeOff66 = other.envelopeOff66;
        this.msgShowToast = other.msgShowToast;
        if (other.msgIgnoreCommand != null && !other.msgIgnoreCommand.isBlank()) {
            this.msgIgnoreCommand = other.msgIgnoreCommand;
        }
        if (other.msgHidden != null) {
            this.msgHidden = new ArrayList(other.msgHidden);
        }
        if (other.msgIgnored != null) {
            this.msgIgnored = other.msgIgnored;
        }
        if (other.language != null && !other.language.isBlank()) {
            this.language = other.language;
        }
        if (other.sellMessagePattern != null && !other.sellMessagePattern.isBlank() && !LEGACY_SELL_PATTERNS.contains((Object)other.sellMessagePattern)) {
            this.sellMessagePattern = other.sellMessagePattern;
        }
        if (other.sellCommand != null && !other.sellCommand.isBlank()) {
            this.sellCommand = other.sellCommand;
        }
        ArrayList arrayList = this.sellWhitelist = other.sellWhitelist == null ? new ArrayList() : other.sellWhitelist;
        if (other.fakeTags != null && !other.fakeTags.isEmpty()) {
            this.fakeTags = other.fakeTags;
        }
        if (other.fakeTagsExact != null) {
            this.fakeTagsExact = other.fakeTagsExact;
        }
        FwmConfig.addMissing(this.fakeTags, DEFAULT_FAKE_TAGS);
        FwmConfig.addMissing(this.fakeTagsExact, DEFAULT_FAKE_TAGS_EXACT);
        this.whitelistLookup = null;
        if (other.hudMoney != null) {
            this.hudMoney = other.hudMoney;
        }
        if (other.hudInvValue != null) {
            this.hudInvValue = other.hudInvValue;
        }
        if (other.hudAuction != null) {
            this.hudAuction = other.hudAuction;
        }
        this.auctionHudCard = other.auctionHudCard;
        this.spawnerHudCard = other.spawnerHudCard;
        if (other.hudSpawnerPrices != null) {
            this.hudSpawnerPrices = other.hudSpawnerPrices;
        }
        this.spawnerHudHeader = other.spawnerHudHeader;
        this.spawnerHudIcons = other.spawnerHudIcons;
        this.spawnerHudBg = other.spawnerHudBg;
        this.spawnerHudOpacity = other.spawnerHudOpacity;
        this.spawnerHudBorder = other.spawnerHudBorder;
        this.spawnerHudBuyColor = other.spawnerHudBuyColor;
        this.spawnerHudSellColor = other.spawnerHudSellColor;
        this.spawnerHudGrid = other.spawnerHudGrid;
        if (other.spawnerHudHidden != null) {
            this.spawnerHudHidden = new ArrayList(other.spawnerHudHidden);
        }
        if (other.spawnerHudPrices != null) {
            this.spawnerHudPrices = new LinkedHashMap(other.spawnerHudPrices);
        }
        this.spawnerHudStyle = other.spawnerHudStyle;
        if (other.spawnerHudTitle != null) {
            this.spawnerHudTitle = other.spawnerHudTitle;
        }
        if (other.spawnerHudLabelName != null) {
            this.spawnerHudLabelName = other.spawnerHudLabelName;
        }
        if (other.spawnerHudLabelBuy != null) {
            this.spawnerHudLabelBuy = other.spawnerHudLabelBuy;
        }
        if (other.spawnerHudLabelSell != null) {
            this.spawnerHudLabelSell = other.spawnerHudLabelSell;
        }
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
        if (other.autoTpaNames != null) {
            this.autoTpaNames = new ArrayList(other.autoTpaNames);
        }
        this.autoTpaHere = other.autoTpaHere;
        this.autoTpaDelayTicks = other.autoTpaDelayTicks;
        this.spawnerAutoDrop = other.spawnerAutoDrop;
        this.spawnerAutoStop = other.spawnerAutoStop;
        this.spawnerAutoSell = other.spawnerAutoSell;
        this.spawnerClickDelayTicks = other.spawnerClickDelayTicks;
        this.spawnerRepeatSeconds = other.spawnerRepeatSeconds;
        this.spawnerSidePanel = other.spawnerSidePanel;
        this.spawnerLabels = other.spawnerLabels;
        if (other.spawnerSpots != null) {
            this.spawnerSpots = new ArrayList(other.spawnerSpots);
        }
        if (other.spawnerMode != null) {
            this.spawnerMode = other.spawnerMode;
        }
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
        this.moneyLogLines = false;
        if (other.moneyPayOutPatterns != null && !other.moneyPayOutPatterns.isEmpty()) {
            this.moneyPayOutPatterns = other.moneyPayOutPatterns;
        }
        if (other.moneyAhBuyPatterns != null && !other.moneyAhBuyPatterns.isEmpty()) {
            this.moneyAhBuyPatterns = other.moneyAhBuyPatterns;
        }
        if (other.moneyAhSellPatterns != null && !other.moneyAhSellPatterns.isEmpty()) {
            this.moneyAhSellPatterns = other.moneyAhSellPatterns;
        }
        this.historyEnabled = other.historyEnabled;
        this.historyDays = other.historyDays;
        this.historyMergeSell = other.historyMergeSell;
        this.invValueHud = other.invValueHud;
        this.invValueInInventory = other.invValueInInventory;
        this.invValueSource = other.invValueSource;
        this.invValueShulkers = other.invValueShulkers;
        this.ecValue = other.ecValue;
        if (other.textureMix != null) {
            this.textureMix = new LinkedHashMap(other.textureMix);
        }
        this.textureMixDisableSources = other.textureMixDisableSources;
        this.textureMixVersion = other.textureMixVersion;
        if (other.textureFavorites != null) {
            this.textureFavorites = new ArrayList(other.textureFavorites);
        }
        if (other.textureThemes != null) {
            this.textureThemes = new ArrayList(other.textureThemes);
        }
        this.textureMixBefore = other.textureMixBefore;
        this.clamp();
    }

    public void clamp() {
        this.secondChatBackgroundOpacity = Math.max((int)0, (int)Math.min((int)100, (int)this.secondChatBackgroundOpacity));
        this.reopenDelayTicks = FwmConfig.clamp(this.reopenDelayTicks, 0, 200);
        this.itemMoveDelayTicks = FwmConfig.clamp(this.itemMoveDelayTicks, 1, 20);
        this.transferBurst = FwmConfig.clamp(this.transferBurst, 1, 13);
        this.sellPressDelayTicks = FwmConfig.clamp(this.sellPressDelayTicks, 0, 20);
        this.sellButtonSlot = FwmConfig.clamp(this.sellButtonSlot, 0, 107);
        this.spawnerRefreshMinutes = FwmConfig.clamp(this.spawnerRefreshMinutes, 5, 60);
        this.menuScale = FwmConfig.clamp(this.menuScale, 70, 150);
        this.toolGuardPercent = FwmConfig.clamp(this.toolGuardPercent, 1, 50);
        this.auctionSeconds = FwmConfig.clamp(this.auctionSeconds, 15, 3600);
        this.auctionAntiSnipeSeconds = FwmConfig.clamp(this.auctionAntiSnipeSeconds, 1, 60);
        this.auctionStartBid = Math.max((long)0L, (long)this.auctionStartBid);
        this.msgContactLimit = FwmConfig.clamp(this.msgContactLimit, 1, 200);
        this.msgHistoryPerContact = FwmConfig.clamp(this.msgHistoryPerContact, 10, 2000);
        this.secondChatLines = FwmConfig.clamp(this.secondChatLines, 3, 16);
        this.secondChatWidth = FwmConfig.clamp(this.secondChatWidth, 100, 420);
        this.secondChatAlign = FwmConfig.clamp(this.secondChatAlign, 0, 2);
        this.autoTpaDelayTicks = FwmConfig.clamp(this.autoTpaDelayTicks, 0, 100);
        this.spawnerClickDelayTicks = FwmConfig.clamp(this.spawnerClickDelayTicks, 2, 20);
        this.spawnerRepeatSeconds = FwmConfig.clamp(this.spawnerRepeatSeconds, 10, 600);
        this.spawnerSellFill = FwmConfig.fill(this.spawnerSellFill);
        if (this.spawnerSpots != null) {
            for (SpawnerSpot spot : this.spawnerSpots) {
                if (spot == null) continue;
                spot.sellFill = FwmConfig.fill(spot.sellFill);
            }
        }
        this.spawnerDropMin = FwmConfig.clamp(this.spawnerDropMin, 100, 230400);
        this.moneyGoal = Math.max((long)0L, (long)Math.min((long)1000000000000L, (long)this.moneyGoal));
        this.historyDays = FwmConfig.clamp(this.historyDays, 1, 90);
        this.invValueSource = FwmConfig.clamp(this.invValueSource, 0, 3);
        if (this.menuHue > 1.0f || Float.isNaN((float)this.menuHue)) {
            this.menuHue = -1.0f;
        }
        if (Float.isNaN((float)this.menuSat)) {
            this.menuSat = 1.0f;
        }
        this.menuSat = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)this.menuSat));
        if (this.tintHue > 1.0f || Float.isNaN((float)this.tintHue)) {
            this.tintHue = -1.0f;
        }
        if (Float.isNaN((float)this.tintSat)) {
            this.tintSat = 0.0f;
        }
        this.tintSat = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)this.tintSat));
    }

    private static void addMissing(List<String> list, List<String> defaults) {
        if (list == null) {
            return;
        }
        for (String tag : defaults) {
            boolean there = false;
            for (String have : list) {
                if (have == null || !have.trim().equalsIgnoreCase(tag)) continue;
                there = true;
                break;
            }
            if (there) continue;
            list.add((Object)tag);
        }
    }

    private static int fill(int value) {
        if (value <= 0) {
            return 0;
        }
        return FwmConfig.clamp(value, 10, 100);
    }

    private static int clamp(int value, int min, int max) {
        return Math.max((int)min, (int)Math.min((int)max, (int)value));
    }

    public static class HudSpot {
        public float x = -1.0f;
        public float y = -1.0f;
        public float scale = 1.0f;

        public boolean moved() {
            return this.x >= 0.0f && this.y >= 0.0f;
        }

        public float scale() {
            if (Float.isNaN((float)this.scale) || this.scale <= 0.0f) {
                return 1.0f;
            }
            return Math.max((float)0.6f, (float)Math.min((float)2.5f, (float)this.scale));
        }

        public void set(float x, float y) {
            this.x = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)x));
            this.y = Math.max((float)0.0f, (float)Math.min((float)1.0f, (float)y));
        }

        public void resize(float value) {
            this.scale = Math.max((float)0.6f, (float)Math.min((float)2.5f, (float)value));
        }

        public void reset() {
            this.x = -1.0f;
            this.y = -1.0f;
            this.scale = 1.0f;
        }
    }

    public static class FightKit {
        public String name = "Kit";
        public Map<Integer, KitSlot> slots = new HashMap();
    }

    public static class SpawnerSpot {
        public int x;
        public int y;
        public int z;
        public String dim = "";
        public String server = "";
        public String mode = "bones";
        public int sellFill = 80;
        public int dropMin = 2304;
        public String kind;
        public String loot;

        public SpawnerSpot() {
        }

        public SpawnerSpot(int x, int y, int z, String dim, String server) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.dim = dim;
            this.server = server;
        }
    }

    public static class SpawnerAdPrice {
        public String buy = "";
        public String sell = "";
        public String amount = "";
    }

    public static class PricePing {
        public String itemKey;
        public String baseItemKey;
        public String name;
        public String icon;
        public double maxPrice;
        public boolean order;
        public String direction;
        public boolean repeat;
        public Boolean toast;
        public boolean chat;
        public List<String> seen = new ArrayList();
        public boolean fired;

        public boolean below() {
            return this.direction == null ? !this.order : !"above".equals((Object)this.direction);
        }

        public boolean toastOn() {
            return this.toast == null || this.toast != false;
        }
    }

    public static class KitSlot {
        public String item;
        public int count = 1;
    }

    public static class TextureTheme {
        public String name;
        public Map<String, String> mix = new LinkedHashMap();
    }
}
