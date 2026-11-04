/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Throwable
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.util.concurrent.CompletableFuture
 *  net.fabricmc.loader.api.FabricLoader
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.concurrent.CompletableFuture;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.net.FwmApi;

public final class ModAccount {
    private static final Gson GSON = new Gson();
    private static volatile String key;
    private static volatile State state;
    private static volatile String minecraftName;
    private static volatile String message;

    private ModAccount() {
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-key.json");
    }

    public static void load() {
        try {
            if (!Files.exists((Path)ModAccount.file(), (LinkOption[])new LinkOption[0])) {
                return;
            }
            Stored s = (Stored)GSON.fromJson(Files.readString((Path)ModAccount.file(), (Charset)StandardCharsets.UTF_8), Stored.class);
            if (s != null && ModAccount.valid(s.key)) {
                key = s.key.trim();
                ModAccount.check();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static boolean valid(String k) {
        return k != null && k.trim().matches("fwm_[A-Za-z0-9_-]{16,200}");
    }

    public static boolean hasKey() {
        return key != null;
    }

    public static State state() {
        return state;
    }

    public static String minecraftName() {
        return minecraftName;
    }

    public static String message() {
        return message;
    }

    public static String hint() {
        String k = key;
        return k == null || k.length() < 8 ? "" : "fwm_\u2026" + k.substring(k.length() - 4);
    }

    public static void setKey(String k) {
        if (!ModAccount.valid(k)) {
            state = State.INVALID;
            message = "Das ist kein FWM-Key (beginnt mit fwm_).";
            return;
        }
        key = k.trim();
        ModAccount.save();
        ModAccount.check();
    }

    public static void forget() {
        key = null;
        minecraftName = null;
        state = State.NONE;
        message = "";
        try {
            Files.deleteIfExists((Path)ModAccount.file());
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    private static void save() {
        try {
            Stored s = new Stored();
            s.key = key;
            Files.writeString((Path)ModAccount.file(), (CharSequence)GSON.toJson((Object)s), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public static void check() {
        if (key == null) {
            return;
        }
        state = State.CHECKING;
        message = "";
        ModAccount.call("GET", "/mod/v1/key/check", null).whenComplete((got, error) -> {
            if (error != null || got == null) {
                state = State.ERROR;
                message = "Keine Verbindung zur FWM-API.";
                return;
            }
            if (got.status() == 401) {
                return;
            }
            Check c = ModAccount.parse(got, Check.class);
            if (got.status() == 200 && c != null && c.ok && c.verified) {
                minecraftName = c.minecraftName;
                state = State.OK;
                message = "";
            } else {
                state = State.ERROR;
                message = ModAccount.errorText(got);
            }
        });
    }

    public static CompletableFuture<FwmApi.Fetched> call(String method, String path, Object body) {
        String k = key;
        if (k == null) {
            return CompletableFuture.failedFuture((Throwable)new IllegalStateException("no key"));
        }
        return FwmApi.request(method, "https://api.friendswithmoney.net" + path, null, k, body, 1000000L).thenApply(got -> {
            if (got.status() == 401) {
                ModAccount.forget();
                state = State.INVALID;
                message = "Key ung\u00fcltig oder ersetzt \u2013 im Discord-Kanal \ud83e\udd16\u1d00\u1d18\u026a einen neuen generieren.";
            }
            return got;
        });
    }

    static <T> T parse(FwmApi.Fetched got, Class<T> type) {
        try {
            return got.json(type);
        }
        catch (Exception e) {
            return null;
        }
    }

    public static String errorText(FwmApi.Fetched got) {
        Error e = ModAccount.parse(got, Error.class);
        if (e != null && e.error != null && !e.error.isBlank()) {
            return e.error;
        }
        return switch (got.status()) {
            case 401 -> "Key ung\u00fcltig.";
            case 409 -> "Du hast schon ein offenes Ticket dieser Art.";
            case 429 -> "Zu viele Anfragen \u2013 kurz warten.";
            default -> "Fehler " + got.status() + ".";
        };
    }

    static {
        state = State.NONE;
        message = "";
    }

    static final class Stored {
        String key;

        Stored() {
        }
    }

    public static enum State {
        NONE,
        CHECKING,
        OK,
        INVALID,
        ERROR;

    }

    static final class Error {
        boolean ok;
        String error;

        Error() {
        }
    }

    static final class Check {
        boolean ok;
        boolean verified;
        String minecraftName;
        String error;

        Check() {
        }
    }
}
