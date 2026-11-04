/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.Path
 *  net.minecraft.client.Minecraft
 *  net.minecraft.server.packs.repository.PackRepository
 */
package net.friendswithmoney.fwm.antileak;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import net.friendswithmoney.fwm.antileak.AntiLeakPack;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.server.packs.repository.PackRepository;

public final class AntiLeak {
    public static final String FOLDER = "FWM Anti-Leak";
    private static final String PACK_ID = "file/FWM Anti-Leak";
    private static boolean checkedAtStart;

    private AntiLeak() {
    }

    public static boolean dirty() {
        return !AntiLeak.wanted().equals((Object)FwmConfig.INSTANCE.antiLeakApplied);
    }

    public static boolean sync(Minecraft client) {
        boolean upToDate;
        if (client == null) {
            return false;
        }
        String wanted = AntiLeak.wanted();
        boolean on = !wanted.isEmpty();
        boolean bl = upToDate = wanted.equals((Object)FwmConfig.INSTANCE.antiLeakApplied) && (!on || Files.isDirectory((Path)AntiLeak.folder(client), (LinkOption[])new LinkOption[0])) && on == client.getResourcePackRepository().getSelectedIds().contains((Object)PACK_ID);
        if (upToDate) {
            return false;
        }
        return AntiLeak.apply(client, on, wanted);
    }

    public static void tick(Minecraft client) {
        if (checkedAtStart || client.gui.overlay() != null) {
            return;
        }
        checkedAtStart = true;
        AntiLeak.sync(client);
    }

    private static boolean apply(Minecraft client, boolean on, String wanted) {
        Path dir = AntiLeak.folder(client);
        PackRepository manager = client.getResourcePackRepository();
        boolean wasEnabled = manager.getSelectedIds().contains((Object)PACK_ID);
        try {
            if (on) {
                AntiLeakPack.write(dir, AntiLeak.settings());
                manager.reload();
                if (!wasEnabled && !manager.addPack(PACK_ID)) {
                    System.err.println("[FWM] Anti-Leak pack not found after writing it: " + String.valueOf((Object)dir));
                    return false;
                }
            } else {
                manager.removePack(PACK_ID);
            }
            client.options.updateResourcePacks(manager);
            if (on && wasEnabled) {
                client.reloadResourcePacks();
            }
            if (!on) {
                AntiLeakPack.delete(dir);
            }
            FwmConfig.INSTANCE.antiLeakApplied = wanted;
            ConfigManager.save();
            return true;
        }
        catch (Exception e) {
            System.err.println("[FWM] Anti-Leak could not be applied: " + String.valueOf((Object)((Object)e)));
            return false;
        }
    }

    private static Path folder(Minecraft client) {
        return client.getResourcePackDirectory().resolve(FOLDER);
    }

    private static AntiLeakPack.Settings settings() {
        FwmConfig config = FwmConfig.INSTANCE;
        return new AntiLeakPack.Settings(config.antiLeakBedrock, config.antiLeakOres, config.antiLeakRotation, config.antiLeakStones, config.antiLeakBiome);
    }

    private static String wanted() {
        if (!Features.isOn("fwm.feature.antileak")) {
            return "";
        }
        AntiLeakPack.Settings s = AntiLeak.settings();
        return "v3|" + AntiLeakPack.packFormat() + "|b" + AntiLeak.bit(s.bedrock()) + "o" + AntiLeak.bit(s.ores()) + "r" + AntiLeak.bit(s.rotation()) + "s" + AntiLeak.bit(s.stones()) + "c" + AntiLeak.bit(s.biome());
    }

    private static int bit(boolean value) {
        return value ? 1 : 0;
    }
}
