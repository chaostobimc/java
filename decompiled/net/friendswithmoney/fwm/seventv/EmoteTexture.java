/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.platform.NativeImage
 *  java.awt.AlphaComposite
 *  java.awt.Composite
 *  java.awt.Graphics2D
 *  java.awt.Image
 *  java.awt.RenderingHints
 *  java.awt.image.BufferedImage
 *  java.io.ByteArrayInputStream
 *  java.lang.Exception
 *  java.lang.IllegalStateException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.NumberFormatException
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Throwable
 *  java.util.ArrayList
 *  java.util.Iterator
 *  java.util.List
 *  java.util.Map
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.concurrent.Executor
 *  javax.imageio.ImageIO
 *  javax.imageio.ImageReader
 *  javax.imageio.stream.ImageInputStream
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.Identifier
 *  org.w3c.dom.NamedNodeMap
 *  org.w3c.dom.Node
 */
package net.friendswithmoney.fwm.seventv;

import com.mojang.blaze3d.platform.NativeImage;
import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteApi;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class EmoteTexture {
    private static final int FRAME_H = 56;
    private static final int MAX_FRAME_W = 224;
    private static final int MAX_FRAMES = 80;
    private static final Map<String, EmoteTexture> CACHE = new ConcurrentHashMap();
    public final Emote emote;
    public final Identifier id;
    public volatile State state = State.LOADING;
    public volatile int frameW = 56;
    public volatile int frameH = 56;
    public volatile int frames = 1;
    private volatile int[] frameEnds = new int[]{100};
    public volatile long hash;

    private EmoteTexture(Emote emote) {
        this.emote = emote;
        this.id = Identifier.fromNamespaceAndPath((String)"fwm", (String)("emotes/" + emote.key()));
    }

    public static EmoteTexture get(Emote emote) {
        return (EmoteTexture)CACHE.computeIfAbsent((Object)emote.key(), k -> {
            EmoteTexture t = new EmoteTexture(emote);
            t.load();
            return t;
        });
    }

    public static EmoteTexture byKey(String key) {
        return (EmoteTexture)CACHE.get((Object)key);
    }

    public static EmoteTexture peek(Emote emote) {
        return (EmoteTexture)CACHE.get((Object)emote.key());
    }

    public float aspect() {
        return this.frameH == 0 ? 1.0f : (float)this.frameW / (float)this.frameH;
    }

    public int currentFrame() {
        int n = this.frames;
        if (n <= 1) {
            return 0;
        }
        int[] ends = this.frameEnds;
        int total = ends[ends.length - 1];
        if (total <= 0) {
            return 0;
        }
        long t = System.currentTimeMillis() % (long)total;
        for (int i = 0; i < ends.length; ++i) {
            if (t >= (long)ends[i]) continue;
            return i;
        }
        return n - 1;
    }

    public float v0() {
        return (float)this.currentFrame() / (float)this.frames;
    }

    public float v1() {
        return (float)(this.currentFrame() + 1) / (float)this.frames;
    }

    private void load() {
        String[] urls = this.emote.imageUrls();
        this.tryUrl(urls, 0);
    }

    private void tryUrl(String[] urls, int idx) {
        if (idx >= urls.length) {
            this.state = State.FAILED;
            return;
        }
        EmoteApi.download(urls[idx]).thenApplyAsync(this::decode, (Executor)EmoteApi.IO).whenComplete((dec, err) -> {
            if (err != null || dec == null) {
                if (idx + 1 >= urls.length) {
                    SevenTv.LOG.debug("Emote {} konnte nicht geladen werden: {}", (Object)this.emote.name(), (Object)String.valueOf((Object)err));
                }
                this.tryUrl(urls, idx + 1);
                return;
            }
            Minecraft.getInstance().execute(() -> this.upload((Decoded)((Object)dec)));
        });
    }

    private Decoded decode(byte[] data) {
        try {
            ArrayList frameImgs = new ArrayList();
            ArrayList delays = new ArrayList();
            EmoteTexture.readFrames(data, (List<BufferedImage>)frameImgs, (List<Integer>)delays);
            if (frameImgs.isEmpty()) {
                return null;
            }
            if (frameImgs.size() > 80) {
                ArrayList f2 = new ArrayList();
                ArrayList d2 = new ArrayList();
                double step = (double)frameImgs.size() / 80.0;
                for (int i = 0; i < 80; ++i) {
                    int from = (int)Math.floor((double)((double)i * step));
                    int to = (int)Math.floor((double)((double)(i + 1) * step));
                    int sum = 0;
                    for (int j = from; j < Math.max((int)to, (int)(from + 1)) && j < delays.size(); ++j) {
                        sum += ((Integer)delays.get(j)).intValue();
                    }
                    f2.add((Object)((BufferedImage)frameImgs.get(from)));
                    d2.add((Object)sum);
                }
                frameImgs = f2;
                delays = d2;
            }
            BufferedImage first = (BufferedImage)frameImgs.get(0);
            int srcW = first.getWidth();
            int srcH = first.getHeight();
            int fh = 56;
            int fw = Math.max((int)1, (int)Math.min((int)224, (int)Math.round((float)((float)srcW * ((float)fh / (float)srcH)))));
            int n = frameImgs.size();
            BufferedImage strip = new BufferedImage(fw, fh * n, 2);
            Graphics2D g = strip.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.setComposite((Composite)AlphaComposite.Src);
            for (int i = 0; i < n; ++i) {
                g.drawImage((Image)frameImgs.get(i), 0, i * fh, fw, fh, null);
            }
            g.dispose();
            int[] ends = new int[n];
            int acc = 0;
            for (int i = 0; i < n; ++i) {
                int d;
                int n2 = d = i < delays.size() ? (Integer)delays.get(i) : 100;
                if (d < 20) {
                    d = 100;
                }
                ends[i] = acc += d;
            }
            NativeImage img = new NativeImage(strip.getWidth(), strip.getHeight(), true);
            for (int y = 0; y < strip.getHeight(); ++y) {
                for (int x = 0; x < strip.getWidth(); ++x) {
                    img.setPixel(x, y, strip.getRGB(x, y));
                }
            }
            return new Decoded(img, fw, fh, n, ends, EmoteTexture.averageHash(first));
        }
        catch (Throwable t) {
            SevenTv.LOG.debug("Dekodieren von {} fehlgeschlagen: {}", (Object)this.emote.name(), (Object)t.toString());
            return null;
        }
    }

    private void upload(Decoded d) {
        try {
            NativeImage img = d.image();
            DynamicTexture tex = new DynamicTexture(() -> "fwm:emote/" + this.emote.key(), img);
            Minecraft.getInstance().getTextureManager().register(this.id, (AbstractTexture)tex);
            this.frameW = d.frameW;
            this.frameH = d.frameH;
            this.frames = d.frames;
            this.frameEnds = d.ends;
            this.hash = d.hash;
            this.state = State.READY;
            SevenTv.onEmoteLoaded(this.emote.key());
        }
        catch (Throwable t) {
            SevenTv.LOG.warn("Textur-Upload f\u00fcr {} fehlgeschlagen", (Object)this.emote.name(), (Object)t);
            this.state = State.FAILED;
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private static void readFrames(byte[] data, List<BufferedImage> out, List<Integer> delays) throws Exception {
        try (ImageInputStream in = ImageIO.createImageInputStream((Object)new ByteArrayInputStream(data));){
            Iterator readers = ImageIO.getImageReaders((Object)in);
            if (!readers.hasNext()) {
                throw new IllegalStateException("Unbekanntes Bildformat");
            }
            ImageReader reader = (ImageReader)readers.next();
            try {
                reader.setInput((Object)in, false);
                String format = reader.getFormatName().toLowerCase();
                if (!format.equals((Object)"gif")) {
                    BufferedImage img = reader.read(0);
                    out.add((Object)EmoteTexture.toArgb(img));
                    delays.add((Object)100);
                    return;
                }
                EmoteTexture.readGif(reader, out, delays);
            }
            finally {
                reader.dispose();
            }
        }
    }

    /*
     * Exception decompiling
     */
    private static void readGif(ImageReader reader, List<BufferedImage> out, List<Integer> delays) throws Exception {
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

    private static BufferedImage toArgb(BufferedImage img) {
        if (img.getType() == 2) {
            return img;
        }
        BufferedImage c = new BufferedImage(img.getWidth(), img.getHeight(), 2);
        Graphics2D g = c.createGraphics();
        g.drawImage((Image)img, 0, 0, null);
        g.dispose();
        return c;
    }

    private static BufferedImage copy(BufferedImage src) {
        BufferedImage c = new BufferedImage(src.getWidth(), src.getHeight(), 2);
        Graphics2D g = c.createGraphics();
        g.setComposite((Composite)AlphaComposite.Src);
        g.drawImage((Image)src, 0, 0, null);
        g.dispose();
        return c;
    }

    private static Node child(Node parent, String name) {
        if (parent == null) {
            return null;
        }
        for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
            if (!name.equals((Object)n.getNodeName())) continue;
            return n;
        }
        return null;
    }

    private static int intAttr(Node node, String name, int def) {
        NamedNodeMap attrs = node.getAttributes();
        if (attrs == null) {
            return def;
        }
        Node a = attrs.getNamedItem(name);
        if (a == null) {
            return def;
        }
        try {
            return Integer.parseInt((String)a.getNodeValue());
        }
        catch (NumberFormatException e) {
            return def;
        }
    }

    private static long averageHash(BufferedImage img) {
        BufferedImage small = new BufferedImage(8, 8, 2);
        Graphics2D g = small.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage((Image)img, 0, 0, 8, 8, null);
        g.dispose();
        int[] lum = new int[64];
        long sum = 0L;
        for (int i = 0; i < 64; ++i) {
            int l;
            int p = small.getRGB(i % 8, i / 8);
            int a = p >>> 24 & 0xFF;
            int r = p >> 16 & 0xFF;
            int gg = p >> 8 & 0xFF;
            int b = p & 0xFF;
            lum[i] = l = (r * 299 + gg * 587 + b * 114) / 1000 * a / 255;
            sum += (long)l;
        }
        long avg = sum / 64L;
        long h = 0L;
        for (int i = 0; i < 64; ++i) {
            if ((long)lum[i] <= avg) continue;
            h |= 1L << i;
        }
        return h == 0L ? 1L : h;
    }

    public static enum State {
        LOADING,
        READY,
        FAILED;

    }

    private record Decoded(NativeImage image, int frameW, int frameH, int frames, int[] ends, long hash) {
    }
}
