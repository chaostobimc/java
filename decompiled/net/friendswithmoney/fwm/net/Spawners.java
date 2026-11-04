/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Math
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
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.net;

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
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.SpawnerData;
import net.minecraft.client.Minecraft;

public final class Spawners {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static SpawnerData data;
    private static State state;
    private static long fetchedAt;
    private static long lastAttempt;
    private static boolean inFlight;

    private Spawners() {
    }

    public static SpawnerData data() {
        return data;
    }

    public static State state() {
        return state;
    }

    public static long ageMinutes() {
        if (fetchedAt == 0L) {
            return -1L;
        }
        return (System.currentTimeMillis() - fetchedAt) / 60000L;
    }

    public static void load() {
        Path path = Spawners.cacheFile();
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            return;
        }
        try {
            Snapshot snapshot = (Snapshot)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Snapshot.class);
            if (snapshot != null && snapshot.data != null) {
                data = snapshot.data;
                fetchedAt = snapshot.fetchedAt;
                state = State.OK;
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    public static void tick(Minecraft client) {
        if (inFlight) {
            return;
        }
        long interval = (long)Math.max((int)1, (int)FwmConfig.INSTANCE.spawnerRefreshMinutes) * 60000L;
        if (System.currentTimeMillis() - Math.max((long)fetchedAt, (long)lastAttempt) < interval) {
            return;
        }
        inFlight = true;
        lastAttempt = System.currentTimeMillis();
        if (data == null) {
            state = State.LOADING;
        }
        FwmApi.getUrl(FwmConfig.INSTANCE.spawnerApiUrl, SpawnerData.class).whenCompleteAsync((result, error) -> {
            if (error != null || result == null || result.items == null) {
                System.err.println("[FWM] Spawner fetch failed: " + (error == null ? "empty response" : error.getMessage()));
                client.execute(() -> {
                    inFlight = false;
                    state = State.ERROR;
                });
                return;
            }
            long now = System.currentTimeMillis();
            Spawners.save(result, now);
            client.execute(() -> {
                inFlight = false;
                data = result;
                fetchedAt = now;
                state = State.OK;
            });
        }, FwmApi.executor());
    }

    private static void save(SpawnerData value, long at) {
        Path path = Spawners.cacheFile();
        try {
            Snapshot snapshot = new Snapshot();
            snapshot.fetchedAt = at;
            snapshot.data = value;
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)path, (CharSequence)GSON.toJson((Object)snapshot), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    private static Path cacheFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-spawners.json");
    }

    static {
        state = State.EMPTY;
    }

    public static enum State {
        EMPTY,
        LOADING,
        OK,
        ERROR;

    }

    private static class Snapshot {
        long fetchedAt;
        SpawnerData data;

        private Snapshot() {
        }
    }
}
