/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.util.regex.Pattern
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientPacketListener
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.regex.Pattern;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.FwmClient;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.Presence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public final class ModId {
    private static final Gson GSON = new Gson();
    private static final Pattern FORM = Pattern.compile((String)"fwm_[A-Z2-9]{8}");
    private static volatile String modId;
    private static boolean loaded;
    private static ClientPacketListener sentFor;

    private ModId() {
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-id.json");
    }

    public static String get() {
        if (!loaded) {
            ModId.load();
        }
        return modId;
    }

    private static void load() {
        loaded = true;
        try {
            if (!Files.exists((Path)ModId.file(), (LinkOption[])new LinkOption[0])) {
                return;
            }
            Stored s = (Stored)GSON.fromJson(Files.readString((Path)ModId.file(), (Charset)StandardCharsets.UTF_8), Stored.class);
            if (s != null && s.modId != null && FORM.matcher((CharSequence)s.modId).matches()) {
                modId = s.modId;
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static void save(String id) {
        modId = id;
        try {
            Stored s = new Stored();
            s.modId = id;
            Files.writeString((Path)ModId.file(), (CharSequence)GSON.toJson((Object)s), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void tick(Minecraft client) {
        if (client.player == null || !Presence.onHugo(client)) {
            return;
        }
        ClientPacketListener handler = client.getConnection();
        if (handler == null || handler == sentFor) {
            return;
        }
        sentFor = handler;
        Identify body = new Identify();
        body.uuid = client.player.getUUID().toString().replace((CharSequence)"-", (CharSequence)"");
        body.name = client.player.getGameProfile().name();
        body.modId = ModId.get();
        body.modVersion = FwmClient.MOD_VERSION;
        FwmApi.post("/api/mod/identify", body, null, Answer.class).whenComplete((answer, error) -> {
            if (error != null || answer == null || answer.modId == null) {
                return;
            }
            if (!FORM.matcher((CharSequence)answer.modId).matches()) {
                return;
            }
            if (!answer.modId.equals((Object)modId)) {
                ModId.save(answer.modId);
            }
        });
    }

    static final class Stored {
        String modId;

        Stored() {
        }
    }

    static final class Identify {
        String uuid;
        String name;
        String modId;
        String modVersion;

        Identify() {
        }
    }

    static final class Answer {
        boolean ok;
        String modId;
        boolean merged;

        Answer() {
        }
    }
}
