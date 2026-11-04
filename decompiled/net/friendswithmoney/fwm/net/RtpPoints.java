/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.stream.JsonReader
 *  com.google.gson.stream.JsonToken
 *  java.io.BufferedInputStream
 *  java.io.BufferedOutputStream
 *  java.io.DataInputStream
 *  java.io.DataOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Short
 *  java.lang.String
 *  java.lang.System
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.attribute.FileAttribute
 *  java.util.EnumMap
 *  java.util.Locale
 *  java.util.Map
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.core.Holder
 *  net.minecraft.resources.ResourceKey
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.dimension.BuiltinDimensionTypes
 */
package net.friendswithmoney.fwm.net;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;

public final class RtpPoints {
    private static final int MAGIC = 1180126546;
    private static final int FORMAT = 1;
    private static final long STALE_MS = 86400000L;
    public static final int NO_HEIGHT = Short.MIN_VALUE;
    private static final Map<Dim, Cloud> CLOUDS = new EnumMap(Dim.class);
    private static final int CELL_SHIFT = 12;

    private RtpPoints() {
    }

    private static Cloud cloud(Dim dim) {
        return (Cloud)CLOUDS.computeIfAbsent((Object)dim, key -> new Cloud());
    }

    public static Index index(Dim dim) {
        return RtpPoints.cloud((Dim)dim).index;
    }

    public static int[] points(Dim dim) {
        return RtpPoints.cloud((Dim)dim).points;
    }

    public static int count(Dim dim) {
        return RtpPoints.cloud((Dim)dim).points.length / 3;
    }

    public static State state(Dim dim) {
        return RtpPoints.cloud((Dim)dim).state;
    }

    public static int area(Dim dim) {
        return Math.max((int)1, (int)RtpPoints.cloud((Dim)dim).area);
    }

    public static long ageMinutes(Dim dim) {
        long at = RtpPoints.cloud((Dim)dim).fetchedAt;
        return at == 0L ? -1L : (System.currentTimeMillis() - at) / 60000L;
    }

    public static void open(Dim dim) {
        Cloud cloud = RtpPoints.cloud(dim);
        if (cloud.busy) {
            return;
        }
        if (cloud.points.length == 0) {
            cloud.busy = true;
            cloud.state = State.LOADING;
            FwmApi.executor().execute(() -> {
                Loaded local = RtpPoints.readFile(dim);
                Minecraft client = Minecraft.getInstance();
                client.execute(() -> {
                    cloud.busy = false;
                    if (local != null) {
                        RtpPoints.apply(dim, local);
                        if (System.currentTimeMillis() - local.at > 86400000L) {
                            RtpPoints.fetch(dim);
                        }
                        return;
                    }
                    RtpPoints.fetch(dim);
                });
            });
            return;
        }
        if (System.currentTimeMillis() - cloud.fetchedAt > 86400000L) {
            RtpPoints.fetch(dim);
        }
    }

    private static void fetch(Dim dim) {
        Cloud cloud = RtpPoints.cloud(dim);
        if (cloud.busy) {
            return;
        }
        cloud.busy = true;
        if (cloud.points.length == 0) {
            cloud.state = State.LOADING;
        }
        Minecraft client = Minecraft.getInstance();
        String url = RtpPoints.url(dim);
        FwmApi.getUrlStreamed(url, reader -> RtpPoints.parse(reader)).whenCompleteAsync((loaded, error) -> {
            if (error == null && loaded != null) {
                RtpPoints.writeFile(dim, loaded);
            } else {
                System.err.println("[FWM] RTP fetch failed for " + dim.key() + ": " + (error == null ? "empty response" : error.getMessage()));
            }
            client.execute(() -> {
                cloud.busy = false;
                if (error != null || loaded == null) {
                    if (cloud.points.length == 0) {
                        cloud.state = State.ERROR;
                    }
                    return;
                }
                RtpPoints.apply(dim, loaded);
            });
        }, FwmApi.executor());
    }

    private static void apply(Dim dim, Loaded loaded) {
        Cloud cloud = RtpPoints.cloud(dim);
        cloud.points = loaded.points;
        cloud.index = loaded.index;
        cloud.area = loaded.area;
        cloud.fetchedAt = loaded.at;
        cloud.state = State.OK;
    }

    private static String url(Dim dim) {
        String base = FwmConfig.INSTANCE.rtpApiUrl.replaceAll("/+$", "");
        return base + "?dim=" + dim.key();
    }

    /*
     * Exception decompiling
     */
    private static Loaded parse(JsonReader reader) throws IOException {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.bytecode.analysis.opgraph.op4rewriters.SwitchStringRewriter$TooOptimisticMatchException
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1802596)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4489513)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4491475)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820062)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:3820183)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:1024810)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4466968)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:604767)
         *     at java.lang.Throwable$FakeClass.fakeMethod(Throwable.java:4234497)
         */
        throw new IllegalStateException("Decompilation failed");
    }

    private static int[] readPoints(JsonReader reader, int count) throws IOException {
        int[] out = new int[count > 0 ? Math.min((int)count, (int)4000000) * 3 : 65536];
        int size = 0;
        reader.beginArray();
        while (reader.peek() != JsonToken.END_ARRAY) {
            if (size == out.length) {
                int[] bigger = new int[out.length + (out.length >> 1)];
                System.arraycopy((Object)out, (int)0, (Object)bigger, (int)0, (int)size);
                out = bigger;
            }
            out[size++] = reader.nextInt();
        }
        reader.endArray();
        size -= size % 3;
        if (size == out.length) {
            return out;
        }
        int[] exact = new int[size];
        System.arraycopy((Object)out, (int)0, (Object)exact, (int)0, (int)size);
        return exact;
    }

    private static Index buildIndex(int[] points) {
        if (points.length < 3) {
            return null;
        }
        int minX = Integer.MAX_VALUE;
        int minZ = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxZ = Integer.MIN_VALUE;
        int i = 0;
        while (i + 2 < points.length) {
            minX = Math.min((int)minX, (int)points[i]);
            maxX = Math.max((int)maxX, (int)points[i]);
            minZ = Math.min((int)minZ, (int)points[i + 1]);
            maxZ = Math.max((int)maxZ, (int)points[i + 1]);
            i += 3;
        }
        int minCellX = minX >> 12;
        int cellsW = (maxX >> 12) - minCellX + 1;
        int minCellZ = minZ >> 12;
        int cellsH = (maxZ >> 12) - minCellZ + 1;
        if ((long)cellsW * (long)cellsH > 4000000L) {
            return null;
        }
        int[] start = new int[cellsW * cellsH + 1];
        int i2 = 0;
        while (i2 + 2 < points.length) {
            int n = RtpPoints.cellOf(points[i2], points[i2 + 1], minCellX, minCellZ, cellsW) + 1;
            start[n] = start[n] + 1;
            i2 += 3;
        }
        for (int c = 0; c < cellsW * cellsH; ++c) {
            int n = c + 1;
            start[n] = start[n] + start[c];
        }
        int[] order = new int[points.length / 3];
        int[] cursor = new int[cellsW * cellsH];
        int i3 = 0;
        while (i3 + 2 < points.length) {
            int cell;
            int n = cell = RtpPoints.cellOf(points[i3], points[i3 + 1], minCellX, minCellZ, cellsW);
            int n2 = cursor[n];
            cursor[n] = n2 + 1;
            order[start[cell] + n2] = i3;
            i3 += 3;
        }
        Index index = new Index(12, minCellX, minCellZ, cellsW, cellsH, start, order);
        index.minX = minX;
        index.minZ = minZ;
        index.maxX = maxX;
        index.maxZ = maxZ;
        return index;
    }

    private static int cellOf(int x, int z, int minCellX, int minCellZ, int cellsW) {
        return ((z >> 12) - minCellZ) * cellsW + ((x >> 12) - minCellX);
    }

    private static void writeFile(Dim dim, Loaded loaded) {
        Path path = RtpPoints.cacheFile(dim);
        try {
            Files.createDirectories((Path)path.getParent(), (FileAttribute[])new FileAttribute[0]);
            try (DataOutputStream out = new DataOutputStream((OutputStream)new BufferedOutputStream(Files.newOutputStream((Path)path, (OpenOption[])new OpenOption[0]), 65536));){
                out.writeInt(1180126546);
                out.writeInt(1);
                out.writeLong(loaded.at);
                out.writeInt(loaded.area);
                out.writeInt(loaded.points.length);
                for (int value : loaded.points) {
                    out.writeInt(value);
                }
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not write " + String.valueOf((Object)path) + ": " + e.getMessage());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static Loaded readFile(Dim dim) {
        Path path = RtpPoints.cacheFile(dim);
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            return null;
        }
        try (DataInputStream in = new DataInputStream((InputStream)new BufferedInputStream(Files.newInputStream((Path)path, (OpenOption[])new OpenOption[0]), 65536));){
            if (in.readInt() != 1180126546 || in.readInt() != 1) {
                Loaded loaded = null;
                return loaded;
            }
            Loaded loaded = new Loaded();
            loaded.at = in.readLong();
            loaded.area = in.readInt();
            int length = in.readInt();
            if (length < 0 || length % 3 != 0) {
                Loaded loaded2 = null;
                return loaded2;
            }
            loaded.points = new int[length];
            for (int i = 0; i < length; ++i) {
                loaded.points[i] = in.readInt();
            }
            loaded.index = RtpPoints.buildIndex(loaded.points);
            Loaded loaded3 = loaded;
            return loaded3;
        }
        catch (Exception e) {
            System.err.println("[FWM] Could not read " + String.valueOf((Object)path) + ": " + e.getMessage());
            return null;
        }
    }

    private static Path cacheFile(Dim dim) {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-rtp-" + dim.key() + ".bin");
    }

    private static class Cloud {
        int[] points = new int[0];
        Index index;
        int area = 5000;
        long fetchedAt;
        boolean busy;
        State state = State.EMPTY;

        private Cloud() {
        }
    }

    public static enum Dim {
        OVERWORLD,
        NETHER,
        END;


        public String key() {
            return this.name().toLowerCase(Locale.ROOT);
        }

        public static Dim of(ClientLevel world) {
            if (world == null) {
                return OVERWORLD;
            }
            ResourceKey key = world.dimension();
            if (key == Level.NETHER) {
                return NETHER;
            }
            if (key == Level.END) {
                return END;
            }
            Holder type = world.dimensionTypeRegistration();
            return Dim.of(key.identifier().getPath(), type.is(BuiltinDimensionTypes.NETHER), type.is(BuiltinDimensionTypes.END));
        }

        public static Dim of(String path, boolean netherType, boolean endType) {
            String p;
            if (netherType) {
                return NETHER;
            }
            if (endType) {
                return END;
            }
            String string = p = path == null ? "" : path.toLowerCase(Locale.ROOT);
            if (p.contains((CharSequence)"nether")) {
                return NETHER;
            }
            if (p.equals((Object)"end") || p.endsWith("_end") || p.contains((CharSequence)"the_end")) {
                return END;
            }
            return OVERWORLD;
        }
    }

    public static final class Index {
        public final int shift;
        public final int minCellX;
        public final int minCellZ;
        public final int cellsW;
        public final int cellsH;
        public final int[] start;
        public final int[] order;
        public int minX;
        public int minZ;
        public int maxX;
        public int maxZ;

        Index(int shift, int minCellX, int minCellZ, int cellsW, int cellsH, int[] start, int[] order) {
            this.shift = shift;
            this.minCellX = minCellX;
            this.minCellZ = minCellZ;
            this.cellsW = cellsW;
            this.cellsH = cellsH;
            this.start = start;
            this.order = order;
        }
    }

    public static enum State {
        EMPTY,
        LOADING,
        OK,
        ERROR;

    }

    private static class Loaded {
        int[] points = new int[0];
        Index index;
        int area = 5000;
        long at;

        private Loaded() {
        }
    }
}
