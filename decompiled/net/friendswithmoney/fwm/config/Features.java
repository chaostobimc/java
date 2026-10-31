// class net/friendswithmoney/fwm/config/Features  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field SELLMACRO Ljava/lang/String;;
field SELLSTATS Ljava/lang/String;;
field SPAWNER Ljava/lang/String;;
field MEMBERS Ljava/lang/String;;
field MESSAGES Ljava/lang/String;;
field TOOLGUARD Ljava/lang/String;;
field UNITPRICE Ljava/lang/String;;
field AHPRICE Ljava/lang/String;;
field REALNAME Ljava/lang/String;;
field SECONDCHAT Ljava/lang/String;;
field AUCTION Ljava/lang/String;;
field COLORCODES Ljava/lang/String;;
field TPAWARN Ljava/lang/String;;
field MARKET Ljava/lang/String;;
field RTPMAP Ljava/lang/String;;
field RTPMINIMAP Ljava/lang/String;;
field FIGHTKIT Ljava/lang/String;;
field WAYPOINTS Ljava/lang/String;;
field PRICEPING Ljava/lang/String;;
field ANTILEAK Ljava/lang/String;;
field AUTOTPA Ljava/lang/String;;
field SPAWNERMACRO Ljava/lang/String;;
field MONEY Ljava/lang/String;;
field INVVALUE Ljava/lang/String;;
field EMOTES Ljava/lang/String;;
field TEXTURES Ljava/lang/String;;
field DEFAULT_OFF Ljava/util/List;;
field SWITCHABLE Ljava/util/List;;
private void <init>() {
    return;
}

public static boolean canSwitch(String key) {
    return config.Features.SWITCHABLE.contains(key);
}

public static boolean isOn(String key) {
    if (net.ModBan.locked() ==0) goto L8;
    return 0;
    L8:
    if (config.Features.canSwitch(key) !=0) goto L17;
    return 1;
    L17:
    if (config.Features.DEFAULT_OFF.contains(key) ==0) goto L42;
    return config.FwmConfig.INSTANCE.enabledFeatures.contains(key);
    L42:
    if (config.FwmConfig.INSTANCE.disabledFeatures.contains(key) !=0) goto L61;
    goto L62;
    L61:
    L62:
    return 0;
}

public static void set(String key, boolean on) {
    if (config.Features.canSwitch(key) !=0) goto L8;
    return;
    L8:
    if (config.Features.DEFAULT_OFF.contains(key) ==0) goto L72;
    if (on ==0) goto L55;
    if (config.FwmConfig.INSTANCE.enabledFeatures.contains(key) !=0) goto L68;
    config.FwmConfig.INSTANCE.enabledFeatures.add(key);
    goto L68;
    L55:
    config.FwmConfig.INSTANCE.enabledFeatures.remove(key);
    L68:
    config.ConfigManager.save();
    return;
    L72:
    if (on ==0) goto L92;
    config.FwmConfig.INSTANCE.disabledFeatures.remove(key);
    goto L120;
    L92:
    if (config.FwmConfig.INSTANCE.disabledFeatures.contains(key) !=0) goto L120;
    config.FwmConfig.INSTANCE.disabledFeatures.add(key);
    L120:
    config.ConfigManager.save();
    return;
}

public static void toggle(String key) {
    if (config.Features.isOn(key) !=0) goto L12;
    goto L13;
    L12:
    L13:
    config.Features.set(1, 0);
    return;
}

static List <clinit>() {
    config.Features.DEFAULT_OFF = List.of("fwm.tab.secondchat", "fwm.feature.rtpminimap", "fwm.feature.antileak", "fwm.tab.waypoints", "fwm.tab.autotpa", "fwm.tab.spawnermacro", "fwm.feature.invvalue");
    new String[22][0] = "fwm.tab.sellmacro";
    new String[22][1] = "fwm.tab.messages";
    new String[22][2] = "fwm.tab.toolguard";
    new String[22][3] = "fwm.feature.unitprice";
    new String[22][4] = "fwm.feature.ahprice";
    new String[22][5] = "fwm.feature.realname";
    new String[22][6] = "fwm.feature.tpawarn";
    new String[22][7] = "fwm.tab.secondchat";
    new String[22][8] = "fwm.tab.auction";
    new String[22][9] = "fwm.tab.colorcodes";
    new String[22][10] = "fwm.tab.market";
    new String[22][11] = "fwm.feature.priceping";
    new String[22][12] = "fwm.tab.rtp";
    new String[22][13] = "fwm.feature.rtpminimap";
    new String[22][14] = "fwm.feature.antileak";
    new String[22][15] = "fwm.tab.fightkit";
    new String[22][16] = "fwm.tab.waypoints";
    new String[22][17] = "fwm.tab.autotpa";
    new String[22][18] = "fwm.tab.spawnermacro";
    new String[22][19] = "fwm.tab.money";
    new String[22][20] = "fwm.feature.invvalue";
    new String[22][21] = "fwm.tab.emotes";
    config.Features.SWITCHABLE = List.of(new String[22]);
    return;
}

