/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.mojang.authlib.GameProfile
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
 *  java.util.ArrayList
 *  java.util.List
 *  net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import com.mojang.authlib.GameProfile;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.ui.FwmGfx;
import net.minecraft.client.Minecraft;

public final class ModBan {
    private static final Gson GSON = new Gson();
    private static final long CHECK_EVERY_MS = 1800000L;
    private static final long RETRY_MS = 300000L;
    private static Note note;
    private static boolean loaded;
    private static long nextCheck;
    private static boolean busy;

    private ModBan() {
    }

    public static void register() {
        ClientTickEvents.END_CLIENT_TICK.register(ModBan::tick);
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-sperre.json");
    }

    public static boolean locked() {
        if (!loaded) {
            ModBan.load();
        }
        return note != null;
    }

    private static void load() {
        loaded = true;
        try {
            Path path = ModBan.file();
            if (Files.exists((Path)path, (LinkOption[])new LinkOption[0]) && (note = (Note)GSON.fromJson(Files.readString((Path)path, (Charset)StandardCharsets.UTF_8), Note.class)) != null && ModBan.note.reported == null) {
                ModBan.note.reported = new ArrayList();
            }
        }
        catch (Exception e) {
            note = new Note();
            ModBan.note.name = "?";
        }
    }

    private static void save() {
        try {
            if (note == null) {
                Files.deleteIfExists((Path)ModBan.file());
            } else {
                Files.writeString((Path)ModBan.file(), (CharSequence)GSON.toJson((Object)note), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write the lock note: " + e.getMessage());
        }
    }

    private static String selfUuid() {
        GameProfile profile = Minecraft.getInstance().getGameProfile();
        return profile == null || profile.id() == null ? "" : profile.id().toString().replace((CharSequence)"-", (CharSequence)"");
    }

    private static void tick(Minecraft client) {
        if (!loaded) {
            ModBan.load();
        }
        if (busy || System.currentTimeMillis() < nextCheck) {
            return;
        }
        String uuid = ModBan.selfUuid();
        String name = FwmGfx.playerName();
        if (uuid.isEmpty() || name.isEmpty()) {
            return;
        }
        busy = true;
        nextCheck = System.currentTimeMillis() + 300000L;
        if (note != null && ModBan.note.uuid != null && !ModBan.note.uuid.equals((Object)uuid) && !ModBan.note.reported.contains((Object)uuid)) {
            Alt alt = new Alt();
            alt.uuid = uuid;
            alt.name = name;
            alt.of = ModBan.note.name;
            alt.ofUuid = ModBan.note.uuid;
            FwmApi.post("/api/mod/ban/alt", alt, null, Ok.class).thenAccept(ok -> client.execute(() -> {
                if (note != null && !ModBan.note.reported.contains((Object)uuid)) {
                    ModBan.note.reported.add((Object)uuid);
                    ModBan.save();
                }
            }));
        }
        Check check = new Check();
        check.uuid = uuid;
        check.name = name;
        check.lockedBy = note == null ? null : ModBan.note.uuid;
        FwmApi.post("/api/mod/ban/check", check, null, Answer.class).whenComplete((answer, error) -> client.execute(() -> {
            busy = false;
            if (error != null || answer == null) {
                return;
            }
            nextCheck = System.currentTimeMillis() + 1800000L;
            if (answer.banned) {
                if (note == null) {
                    note = new Note();
                    ModBan.note.name = name;
                    ModBan.note.uuid = uuid;
                    ModBan.note.at = System.currentTimeMillis();
                    ModBan.save();
                }
            } else if (note != null) {
                note = null;
                ModBan.save();
            }
        }));
    }

    private static final class Note {
        String name;
        String uuid;
        long at;
        List<String> reported = new ArrayList();

        private Note() {
        }
    }

    private static final class Alt {
        String uuid;
        String name;
        String of;
        String ofUuid;

        private Alt() {
        }
    }

    private static final class Ok {
        boolean ok;

        private Ok() {
        }
    }

    private static final class Check {
        String uuid;
        String name;
        String lockedBy;

        private Check() {
        }
    }

    private static final class Answer {
        boolean banned;
        String name;

        private Answer() {
        }
    }
}
