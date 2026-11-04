/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 */
package net.friendswithmoney.fwm.config;

import java.util.List;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.ModBan;

public final class Features {
    public static final String SELLMACRO = "fwm.tab.sellmacro";
    public static final String SELLSTATS = "fwm.tab.sellmacro";
    public static final String SPAWNER = "fwm.tab.spawner";
    public static final String MEMBERS = "fwm.tab.members";
    public static final String MESSAGES = "fwm.tab.messages";
    public static final String TOOLGUARD = "fwm.tab.toolguard";
    public static final String UNITPRICE = "fwm.feature.unitprice";
    public static final String AHPRICE = "fwm.feature.ahprice";
    public static final String REALNAME = "fwm.feature.realname";
    public static final String SECONDCHAT = "fwm.tab.secondchat";
    public static final String AUCTION = "fwm.tab.auction";
    public static final String COLORCODES = "fwm.tab.colorcodes";
    public static final String TPAWARN = "fwm.feature.tpawarn";
    public static final String MARKET = "fwm.tab.market";
    public static final String RTPMAP = "fwm.tab.rtp";
    public static final String RTPMINIMAP = "fwm.feature.rtpminimap";
    public static final String FIGHTKIT = "fwm.tab.fightkit";
    public static final String WAYPOINTS = "fwm.tab.waypoints";
    public static final String PRICEPING = "fwm.feature.priceping";
    public static final String ANTILEAK = "fwm.feature.antileak";
    public static final String AUTOTPA = "fwm.tab.autotpa";
    public static final String SPAWNERMACRO = "fwm.tab.spawnermacro";
    public static final String MONEY = "fwm.tab.money";
    public static final String INVVALUE = "fwm.feature.invvalue";
    public static final String EMOTES = "fwm.tab.emotes";
    public static final String TEXTURES = "fwm.tab.textures";
    private static final List<String> DEFAULT_OFF = List.of((Object)"fwm.tab.secondchat", (Object)"fwm.feature.rtpminimap", (Object)"fwm.feature.antileak", (Object)"fwm.tab.waypoints", (Object)"fwm.tab.autotpa", (Object)"fwm.tab.spawnermacro", (Object)"fwm.feature.invvalue");
    public static final List<String> SWITCHABLE = List.of((Object[])new String[]{"fwm.tab.sellmacro", "fwm.tab.messages", "fwm.tab.toolguard", "fwm.feature.unitprice", "fwm.feature.ahprice", "fwm.feature.realname", "fwm.feature.tpawarn", "fwm.tab.secondchat", "fwm.tab.auction", "fwm.tab.colorcodes", "fwm.tab.market", "fwm.feature.priceping", "fwm.tab.rtp", "fwm.feature.rtpminimap", "fwm.feature.antileak", "fwm.tab.fightkit", "fwm.tab.waypoints", "fwm.tab.autotpa", "fwm.tab.spawnermacro", "fwm.tab.money", "fwm.feature.invvalue", "fwm.tab.emotes"});

    private Features() {
    }

    public static boolean canSwitch(String key) {
        return SWITCHABLE.contains((Object)key);
    }

    public static boolean isOn(String key) {
        if (ModBan.locked()) {
            return false;
        }
        if (!Features.canSwitch(key)) {
            return true;
        }
        if (DEFAULT_OFF.contains((Object)key)) {
            return FwmConfig.INSTANCE.enabledFeatures.contains((Object)key);
        }
        return !FwmConfig.INSTANCE.disabledFeatures.contains((Object)key);
    }

    public static void set(String key, boolean on) {
        if (!Features.canSwitch(key)) {
            return;
        }
        if (DEFAULT_OFF.contains((Object)key)) {
            if (on) {
                if (!FwmConfig.INSTANCE.enabledFeatures.contains((Object)key)) {
                    FwmConfig.INSTANCE.enabledFeatures.add((Object)key);
                }
            } else {
                FwmConfig.INSTANCE.enabledFeatures.remove((Object)key);
            }
            ConfigManager.save();
            return;
        }
        if (on) {
            FwmConfig.INSTANCE.disabledFeatures.remove((Object)key);
        } else if (!FwmConfig.INSTANCE.disabledFeatures.contains((Object)key)) {
            FwmConfig.INSTANCE.disabledFeatures.add((Object)key);
        }
        ConfigManager.save();
    }

    public static void toggle(String key) {
        Features.set(key, !Features.isOn(key));
    }
}
