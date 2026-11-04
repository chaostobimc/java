/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParser
 *  java.lang.CharSequence
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Number
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.net.URI
 *  java.net.URLEncoder
 *  java.net.http.HttpClient
 *  java.net.http.HttpClient$Redirect
 *  java.net.http.HttpRequest
 *  java.net.http.HttpRequest$BodyPublishers
 *  java.net.http.HttpResponse$BodyHandlers
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.time.Duration
 *  java.util.ArrayList
 *  java.util.List
 *  java.util.Locale
 *  java.util.concurrent.CompletableFuture
 *  java.util.concurrent.Executor
 *  java.util.concurrent.ExecutorService
 *  java.util.concurrent.Executors
 */
package net.friendswithmoney.fwm.seventv;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.SevenTv;

public final class EmoteApi {
    private static final String UA = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/130.0.0.0 Safari/537.36";
    private static final String SEVENTV_GQL = "https://api.7tv.app/v4/gql";
    private static final String SEVENTV_QUERY = "query($q:String,$page:Int!,$perPage:Int!){emotes{search(query:$q,page:$page,perPage:$perPage,sort:{sortBy:TOP_ALL_TIME,order:DESCENDING}){items{id defaultName flags{animated}}}}}";
    public static final ExecutorService IO = Executors.newFixedThreadPool((int)8, r -> {
        Thread t = new Thread(r, "FWM 7TV");
        t.setDaemon(true);
        return t;
    });
    public static final HttpClient HTTP = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds((long)10L)).followRedirects(HttpClient.Redirect.NORMAL).executor((Executor)IO).build();

    private EmoteApi() {
    }

    public static CompletableFuture<List<Emote>> searchSevenTv(String query, int page, int perPage) {
        JsonObject vars = new JsonObject();
        if (query == null || query.isBlank()) {
            vars.add("q", null);
        } else {
            vars.addProperty("q", query.trim());
        }
        vars.addProperty("page", (Number)Integer.valueOf((int)page));
        vars.addProperty("perPage", (Number)Integer.valueOf((int)perPage));
        JsonObject body = new JsonObject();
        body.addProperty("query", SEVENTV_QUERY);
        body.add("variables", (JsonElement)vars);
        HttpRequest req = HttpRequest.newBuilder((URI)URI.create((String)SEVENTV_GQL)).timeout(Duration.ofSeconds((long)15L)).header("User-Agent", UA).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString((String)body.toString())).build();
        return HTTP.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenApply(resp -> {
            ArrayList out = new ArrayList();
            if (resp.statusCode() != 200) {
                SevenTv.LOG.warn("7TV-Suche fehlgeschlagen: HTTP {}", (Object)resp.statusCode());
                throw new IllegalStateException("7TV HTTP " + resp.statusCode());
            }
            JsonObject root = JsonParser.parseString((String)((String)resp.body())).getAsJsonObject();
            JsonObject data = EmoteApi.obj(root, "data");
            JsonObject emotes = EmoteApi.obj(data, "emotes");
            JsonObject search = EmoteApi.obj(emotes, "search");
            if (search == null || !search.has("items")) {
                return out;
            }
            for (JsonElement el : search.getAsJsonArray("items")) {
                JsonObject o = el.getAsJsonObject();
                String id = EmoteApi.str(o, "id");
                String name = EmoteApi.str(o, "defaultName");
                if (id == null || name == null) continue;
                boolean animated = false;
                JsonObject flags = EmoteApi.obj(o, "flags");
                if (flags != null && flags.has("animated") && !flags.get("animated").isJsonNull()) {
                    animated = flags.get("animated").getAsBoolean();
                }
                out.add((Object)new Emote(Emote.Provider.SEVENTV, id, name, animated, animated ? "gif" : "png"));
            }
            return out;
        });
    }

    public static CompletableFuture<List<Emote>> searchBttv(String query, int offset, int limit) {
        String url;
        boolean top;
        boolean bl = top = query == null || query.isBlank();
        if (top) {
            if (offset > 0) {
                return CompletableFuture.completedFuture((Object)new ArrayList());
            }
            url = "https://api.betterttv.net/3/emotes/shared/top?limit=" + Math.min((int)100, (int)limit);
        } else {
            String q = query.trim();
            if (q.length() < 3) {
                return CompletableFuture.completedFuture((Object)new ArrayList());
            }
            url = "https://api.betterttv.net/3/emotes/shared/search?query=" + URLEncoder.encode((String)q, (Charset)StandardCharsets.UTF_8) + "&offset=" + offset + "&limit=" + Math.min((int)100, (int)limit);
        }
        HttpRequest req = HttpRequest.newBuilder((URI)URI.create((String)url)).timeout(Duration.ofSeconds((long)15L)).header("User-Agent", UA).header("Accept", "application/json, text/plain, */*").header("Accept-Language", "de-DE,de;q=0.9,en;q=0.8").header("Origin", "https://betterttv.com").header("Referer", "https://betterttv.com/").GET().build();
        return HTTP.sendAsync(req, HttpResponse.BodyHandlers.ofString()).thenApply(resp -> {
            ArrayList out = new ArrayList();
            if (resp.statusCode() != 200) {
                String b = resp.body() == null ? "" : (String)resp.body();
                SevenTv.LOG.warn("BTTV-Suche fehlgeschlagen: HTTP {} {}", (Object)resp.statusCode(), (Object)b.substring(0, Math.min((int)200, (int)b.length())).replace('\n', ' '));
                throw new IllegalStateException("BTTV HTTP " + resp.statusCode());
            }
            JsonElement root = JsonParser.parseString((String)((String)resp.body()));
            if (!root.isJsonArray()) {
                return out;
            }
            JsonArray arr = root.getAsJsonArray();
            for (JsonElement el : arr) {
                JsonObject o = el.getAsJsonObject();
                if (o.has("emote") && o.get("emote").isJsonObject()) {
                    o = o.getAsJsonObject("emote");
                }
                String id = EmoteApi.str(o, "id");
                String code = EmoteApi.str(o, "code");
                if (id == null || code == null) continue;
                String type = EmoteApi.str(o, "imageType");
                boolean animated = o.has("animated") && !o.get("animated").isJsonNull() ? o.get("animated").getAsBoolean() : "gif".equalsIgnoreCase(type);
                out.add((Object)new Emote(Emote.Provider.BTTV, id, code, animated, type));
            }
            return out;
        });
    }

    public static CompletableFuture<Emote> lookupByName(String name) {
        return EmoteApi.searchSevenTv(name, 1, 25).exceptionally(t -> new ArrayList()).thenCompose(seven -> {
            Emote hit = EmoteApi.pick((List<Emote>)seven, name);
            if (hit != null) {
                return CompletableFuture.completedFuture((Object)((Object)hit));
            }
            return EmoteApi.searchBttv(name, 0, 25).exceptionally(t -> new ArrayList()).thenApply(b -> EmoteApi.pick((List<Emote>)b, name));
        });
    }

    private static Emote pick(List<Emote> list, String name) {
        for (Emote e : list) {
            if (!e.name().equals((Object)name)) continue;
            return e;
        }
        String lower = name.toLowerCase(Locale.ROOT);
        for (Emote e : list) {
            if (!e.name().toLowerCase(Locale.ROOT).equals((Object)lower)) continue;
            return e;
        }
        return null;
    }

    public static CompletableFuture<byte[]> download(String url) {
        HttpRequest req = HttpRequest.newBuilder((URI)URI.create((String)url)).timeout(Duration.ofSeconds((long)20L)).header("User-Agent", UA).header("Accept", "image/png,image/gif;q=0.9,*/*;q=0.5").header("Referer", url.contains((CharSequence)"betterttv") ? "https://betterttv.com/" : "https://7tv.app/").GET().build();
        return HTTP.sendAsync(req, HttpResponse.BodyHandlers.ofByteArray()).thenApply(resp -> {
            if (resp.statusCode() != 200) {
                throw new IllegalStateException("HTTP " + resp.statusCode() + " f\u00fcr " + url);
            }
            return (byte[])resp.body();
        });
    }

    private static JsonObject obj(JsonObject o, String key) {
        if (o == null || !o.has(key) || !o.get(key).isJsonObject()) {
            return null;
        }
        return o.getAsJsonObject(key);
    }

    private static String str(JsonObject o, String key) {
        if (o == null || !o.has(key) || o.get(key).isJsonNull()) {
            return null;
        }
        return o.get(key).getAsString();
    }

    public static String lower(String s) {
        return s.toLowerCase(Locale.ROOT);
    }
}
