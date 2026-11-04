/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.reflect.TypeToken
 *  java.io.InputStream
 *  java.io.InputStreamReader
 *  java.io.Reader
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.nio.charset.StandardCharsets
 *  java.util.HashMap
 *  java.util.Locale
 *  java.util.Map
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.MutableComponent
 */
package net.friendswithmoney.fwm.ui;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public final class Lang {
    public static final String GERMAN = "de";
    public static final String ENGLISH = "en";
    private static final Gson GSON = new Gson();
    private static Map<String, String> de;
    private static Map<String, String> en;

    private Lang() {
    }

    public static void load() {
        de = Lang.read("de_de");
        en = Lang.read("en_us");
    }

    private static Map<String, String> read(String file) {
        Map map;
        block9: {
            String path = "/assets/fwm/lang/" + file + ".json";
            InputStream in = Lang.class.getResourceAsStream(path);
            try {
                if (in == null) {
                    throw new IllegalStateException("missing " + path);
                }
                map = (Map)GSON.fromJson((Reader)new InputStreamReader(in, StandardCharsets.UTF_8), new TypeToken<Map<String, String>>(){}.getType());
                if (in == null) break block9;
            }
            catch (Throwable throwable) {
                try {
                    if (in != null) {
                        try {
                            in.close();
                        }
                        catch (Throwable throwable2) {
                            throwable.addSuppressed(throwable2);
                        }
                    }
                    throw throwable;
                }
                catch (Exception e) {
                    System.err.println("[FWM] Could not read " + path + ": " + e.getMessage());
                    return new HashMap();
                }
            }
            in.close();
        }
        return map;
    }

    public static boolean isGerman() {
        return !ENGLISH.equals((Object)FwmConfig.INSTANCE.language);
    }

    public static void set(String language) {
        FwmConfig.INSTANCE.language = ENGLISH.equals((Object)language) ? ENGLISH : GERMAN;
    }

    public static String t(String de, String en) {
        return Lang.isGerman() ? de : en;
    }

    public static void toggle() {
        Lang.set(Lang.isGerman() ? ENGLISH : GERMAN);
    }

    public static String str(String key, Object ... args) {
        Map<String, String> table;
        String value;
        if (de == null) {
            Lang.load();
        }
        if ((value = (String)(table = Lang.isGerman() ? de : en).get((Object)key)) == null) {
            value = (String)(Lang.isGerman() ? en : de).get((Object)key);
        }
        if (value == null) {
            return key;
        }
        if (args.length == 0) {
            return value;
        }
        try {
            return String.format((Locale)Locale.ROOT, (String)value, (Object[])args);
        }
        catch (Exception e) {
            return value;
        }
    }

    public static MutableComponent of(String key, Object ... args) {
        return Component.literal((String)Lang.str(key, args));
    }
}
