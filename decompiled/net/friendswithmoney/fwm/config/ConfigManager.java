/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.attribute.FileAttribute
 *  net.fabricmc.loader.api.FabricLoader
 */
package net.friendswithmoney.fwm.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.config.FwmConfig;

public final class ConfigManager {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private ConfigManager() {
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm.json");
    }

    public static void load() {
        Path path = ConfigManager.file();
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            ConfigManager.save();
            return;
        }
        try {
            String json = Files.readString((Path)path, (Charset)StandardCharsets.UTF_8);
            FwmConfig loaded = (FwmConfig)GSON.fromJson(json, FwmConfig.class);
            FwmConfig.INSTANCE.copyFrom(loaded);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read " + String.valueOf((Object)path) + ": " + e.getMessage());
            FwmConfig.INSTANCE.clamp();
        }
    }

    public static void save() {
        Path path = ConfigManager.file();
        try {
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)FwmConfig.INSTANCE), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }
}
