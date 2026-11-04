/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.attribute.FileAttribute
 *  java.util.ArrayList
 *  java.util.Comparator
 *  java.util.LinkedHashMap
 *  java.util.List
 *  java.util.Map
 *  net.fabricmc.loader.api.FabricLoader
 */
package net.friendswithmoney.fwm.seventv;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteRegistry;
import net.friendswithmoney.fwm.seventv.SevenTv;

public final class EmoteConfig {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final Map<String, Usage> USAGE = new LinkedHashMap();
    private static int pickerW = 310;
    private static int pickerH = 250;

    private EmoteConfig() {
    }

    public static synchronized int pickerWidth() {
        return pickerW;
    }

    public static synchronized int pickerHeight() {
        return pickerH;
    }

    public static synchronized void setPickerSize(int w, int h) {
        pickerW = Math.max((int)190, (int)Math.min((int)2000, (int)w));
        pickerH = Math.max((int)150, (int)Math.min((int)2000, (int)h));
    }

    private static Path file() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-emotes.json");
    }

    public static synchronized void load() {
        USAGE.clear();
        Path f = EmoteConfig.file();
        if (!Files.exists((Path)f, (LinkOption[])new LinkOption[0])) {
            return;
        }
        try {
            JsonArray arr;
            JsonObject root = JsonParser.parseString((String)Files.readString((Path)f, (Charset)StandardCharsets.UTF_8)).getAsJsonObject();
            if (root.has("pickerWidth")) {
                pickerW = root.get("pickerWidth").getAsInt();
            }
            if (root.has("pickerHeight")) {
                pickerH = root.get("pickerHeight").getAsInt();
            }
            if ((arr = root.getAsJsonArray("recent")) == null) {
                return;
            }
            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                Emote.Provider p = Emote.Provider.byKey(o.get("provider").getAsString());
                if (p == null) continue;
                Emote e = new Emote(p, o.get("id").getAsString(), o.get("name").getAsString(), o.has("animated") && o.get("animated").getAsBoolean(), o.has("type") && !o.get("type").isJsonNull() ? o.get("type").getAsString() : null);
                Usage u = new Usage();
                u.emote = e;
                u.count = o.has("count") ? o.get("count").getAsInt() : 1;
                u.last = o.has("last") ? o.get("last").getAsLong() : 0L;
                USAGE.put((Object)e.key(), (Object)u);
                EmoteRegistry.register(e);
            }
        }
        catch (Exception ex) {
            SevenTv.LOG.warn("fwm-emotes.json konnte nicht gelesen werden", (Throwable)ex);
        }
    }

    public static synchronized void save() {
        JsonArray arr = new JsonArray();
        for (Usage u : USAGE.values()) {
            JsonObject o = new JsonObject();
            o.addProperty("provider", u.emote.provider().key);
            o.addProperty("id", u.emote.id());
            o.addProperty("name", u.emote.name());
            o.addProperty("animated", Boolean.valueOf((boolean)u.emote.animated()));
            o.addProperty("type", u.emote.imageType());
            o.addProperty("count", (Number)Integer.valueOf((int)u.count));
            o.addProperty("last", (Number)Long.valueOf((long)u.last));
            arr.add((JsonElement)o);
        }
        JsonObject root = new JsonObject();
        root.addProperty("pickerWidth", (Number)Integer.valueOf((int)pickerW));
        root.addProperty("pickerHeight", (Number)Integer.valueOf((int)pickerH));
        root.add("recent", (JsonElement)arr);
        try {
            Files.createDirectories((Path)EmoteConfig.file().getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)EmoteConfig.file(), (CharSequence)GSON.toJson((JsonElement)root), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        catch (Exception ex) {
            SevenTv.LOG.warn("fwm-emotes.json konnte nicht gespeichert werden", (Throwable)ex);
        }
    }

    public static synchronized void recordUse(Emote e) {
        Usage u = (Usage)USAGE.computeIfAbsent((Object)e.key(), k -> {
            Usage n = new Usage();
            n.emote = e;
            return n;
        });
        u.emote = e;
        ++u.count;
        u.last = System.currentTimeMillis();
        if (USAGE.size() > 200) {
            ArrayList all = new ArrayList(USAGE.values());
            all.sort(Comparator.comparingInt(x -> x.count).thenComparingLong(x -> x.last));
            for (int i = 0; i < all.size() - 200; ++i) {
                USAGE.remove((Object)((Usage)all.get((int)i)).emote.key());
            }
        }
        EmoteConfig.save();
    }

    public static synchronized List<Emote> frequent(int limit) {
        ArrayList all = new ArrayList(USAGE.values());
        long now = System.currentTimeMillis();
        all.sort(Comparator.comparingDouble(u -> -EmoteConfig.score(u, now)));
        ArrayList out = new ArrayList();
        for (Usage u2 : all) {
            if (out.size() >= limit) break;
            out.add((Object)u2.emote);
        }
        return out;
    }

    private static double score(Usage u, long now) {
        double days = Math.max((double)0.0, (double)((double)(now - u.last) / 8.64E7));
        return (double)u.count / (1.0 + days / 7.0);
    }

    public static synchronized int size() {
        return USAGE.size();
    }

    private static final class Usage {
        Emote emote;
        int count;
        long last;

        private Usage() {
        }
    }
}
