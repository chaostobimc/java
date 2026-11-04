/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.stream.JsonReader
 *  java.io.IOException
 *  java.lang.Boolean
 *  java.lang.CharSequence
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.FunctionalInterface
 *  java.lang.IllegalStateException
 *  java.lang.Long
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.net.URI
 *  java.net.http.HttpClient
 *  java.net.http.HttpClient$Redirect
 *  java.net.http.HttpRequest
 *  java.net.http.HttpRequest$BodyPublishers
 *  java.net.http.HttpRequest$Builder
 *  java.net.http.HttpResponse$BodyHandlers
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.time.Duration
 *  java.util.Locale
 *  java.util.concurrent.CompletableFuture
 *  java.util.concurrent.Executor
 *  java.util.concurrent.Executors
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import net.friendswithmoney.fwm.FwmClient;
import net.friendswithmoney.fwm.config.FwmConfig;

public final class FwmApi {
    private static final Executor EXECUTOR = Executors.newSingleThreadExecutor(runnable -> {
        Thread thread = new Thread(runnable, "FWM-API");
        thread.setDaemon(true);
        return thread;
    });
    private static final HttpClient CLIENT = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds((long)5L)).followRedirects(HttpClient.Redirect.NORMAL).executor(EXECUTOR).build();
    private static final Gson GSON = new Gson();
    public static final String BOT_API = "https://api.friendswithmoney.net";

    public static Executor executor() {
        return EXECUTOR;
    }

    private FwmApi() {
    }

    public static CompletableFuture<Fetched> request(String method, String url, String ifNoneMatch, String bearer, Object jsonBody, long maxBytes) {
        URI uri;
        try {
            uri = URI.create((String)url);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest.Builder request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)10L)).header("Accept", "application/json, image/png").header("User-Agent", "FWM-Mod/" + FwmClient.MOD_VERSION);
        if (ifNoneMatch != null && !ifNoneMatch.isBlank()) {
            request.header("If-None-Match", ifNoneMatch);
        }
        if (bearer != null && !bearer.isBlank()) {
            request.header("Authorization", "Bearer " + bearer);
        }
        if (jsonBody != null) {
            request.header("Content-Type", "application/json").method(method, HttpRequest.BodyPublishers.ofString((String)GSON.toJson(jsonBody), (Charset)StandardCharsets.UTF_8));
        } else {
            request.method(method, HttpRequest.BodyPublishers.noBody());
        }
        return CLIENT.sendAsync(request.build(), HttpResponse.BodyHandlers.ofByteArray()).thenApply(response -> {
            byte[] body;
            String etag = (String)response.headers().firstValue("etag").orElse(null);
            long retry = 0L;
            try {
                retry = Long.parseLong((String)((String)response.headers().firstValue("retry-after").orElse((Object)"0")).trim());
            }
            catch (NumberFormatException ignored) {
                retry = 60L;
            }
            byte[] byArray = body = response.statusCode() == 304 ? null : (byte[])response.body();
            if (body != null && maxBytes > 0L && (long)body.length > maxBytes) {
                body = null;
            }
            return new Fetched(response.statusCode(), body, etag, retry);
        });
    }

    public static <T> CompletableFuture<T> get(String path, Class<T> type) {
        return FwmApi.getUrl(FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + path, type);
    }

    public static <T> CompletableFuture<T> getUrlStreamed(String url, Parser<T> parser) {
        URI uri;
        try {
            uri = URI.create((String)url);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)60L)).header("Accept", "application/json").header("Accept-Encoding", "gzip").header("User-Agent", "FWM-Mod/" + FwmClient.MOD_VERSION).GET().build();
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofInputStream()).thenApply(response -> {
            /*
             * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
             * 
             * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3308866)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3007678)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:2355268)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:935200)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:420409)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:2100477)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1145698)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1131579)
             *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1117704)
             */
            throw new IllegalStateException("Decompilation failed");
        });
    }

    public static CompletableFuture<byte[]> getBytes(String url) {
        URI uri;
        try {
            uri = URI.create((String)url);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)10L)).header("User-Agent", "FWM-Mod/" + FwmClient.MOD_VERSION).GET().build();
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray()).thenApply(response -> {
            if (response.statusCode() / 100 != 2) {
                throw new IllegalStateException("HTTP " + response.statusCode());
            }
            return (byte[])response.body();
        });
    }

    public static <T> CompletableFuture<T> post(String path, Object body, String token, Class<T> type) {
        URI uri;
        try {
            uri = URI.create((String)(FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + path));
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest.Builder request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)10L)).header("Accept", "application/json").header("Content-Type", "application/json").header("User-Agent", "FWM-Mod").POST(HttpRequest.BodyPublishers.ofString((String)GSON.toJson(body), (Charset)StandardCharsets.UTF_8));
        if (token != null) {
            request.header("Authorization", "Bearer " + token);
        }
        return CLIENT.sendAsync(request.build(), HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            if (response.statusCode() / 100 != 2) {
                throw new HttpStatusException(response.statusCode());
            }
            Object parsed = GSON.fromJson((String)response.body(), type);
            if (parsed == null) {
                throw new IllegalStateException("empty response");
            }
            return parsed;
        });
    }

    public static <T> CompletableFuture<T> postUrl(String url, Object body, Class<T> type) {
        URI uri;
        try {
            uri = URI.create((String)url);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)10L)).header("Accept", "application/json").header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString((String)GSON.toJson(body), (Charset)StandardCharsets.UTF_8)).build();
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            if (response.statusCode() / 100 != 2) {
                throw new HttpStatusException(response.statusCode());
            }
            Object parsed = GSON.fromJson((String)response.body(), type);
            if (parsed == null) {
                throw new IllegalStateException("empty response");
            }
            return parsed;
        });
    }

    public static <T> CompletableFuture<T> getUrl(String url, Class<T> type) {
        URI uri;
        try {
            uri = URI.create((String)url);
        }
        catch (Exception e) {
            return CompletableFuture.failedFuture((Throwable)e);
        }
        HttpRequest request = HttpRequest.newBuilder((URI)uri).timeout(Duration.ofSeconds((long)10L)).header("Accept", "application/json").header("User-Agent", "FWM-Mod/" + FwmClient.MOD_VERSION).GET().build();
        return CLIENT.sendAsync(request, HttpResponse.BodyHandlers.ofString()).thenApply(response -> {
            if (response.statusCode() / 100 != 2) {
                throw new IllegalStateException("HTTP " + response.statusCode());
            }
            Object parsed = GSON.fromJson((String)response.body(), type);
            if (parsed == null) {
                throw new IllegalStateException("empty response");
            }
            return parsed;
        });
    }

    private static /* synthetic */ Boolean lambda$getUrlStreamed$1(String value) {
        return value.toLowerCase(Locale.ROOT).contains((CharSequence)"gzip");
    }

    @FunctionalInterface
    public static interface Parser<T> {
        public T parse(JsonReader var1) throws IOException;
    }

    public static final class HttpStatusException
    extends IllegalStateException {
        public final int status;

        HttpStatusException(int status) {
            super("HTTP " + status);
            this.status = status;
        }
    }

    public record Fetched(int status, byte[] body, String etag, long retryAfterSeconds) {
        public String text() {
            return this.body == null ? null : new String(this.body, StandardCharsets.UTF_8);
        }

        public <T> T json(Class<T> type) {
            String text = this.text();
            return (T)(text == null ? null : GSON.fromJson(text, type));
        }
    }
}
