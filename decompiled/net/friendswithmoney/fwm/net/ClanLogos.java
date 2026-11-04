/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.awt.Graphics2D
 *  java.awt.Image
 *  java.awt.RenderingHints
 *  java.awt.image.BufferedImage
 *  java.awt.image.RenderedImage
 *  java.io.ByteArrayInputStream
 *  java.io.ByteArrayOutputStream
 *  java.io.IOException
 *  java.io.InputStream
 *  java.io.OutputStream
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Long
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.CopyOption
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.StandardCopyOption
 *  java.nio.file.attribute.FileAttribute
 *  java.util.ArrayList
 *  java.util.Collection
 *  java.util.List
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.TreeMap
 *  java.util.concurrent.ConcurrentHashMap
 *  javax.imageio.ImageIO
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.packs.repository.PackRepository
 */
package net.friendswithmoney.fwm.net;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import javax.imageio.ImageIO;
import net.fabricmc.loader.api.FabricLoader;
import net.friendswithmoney.fwm.antileak.AntiLeakPack;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.FwmApi;
import net.friendswithmoney.fwm.net.TrustedClans;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.repository.PackRepository;

public final class ClanLogos {
    public static final String FOLDER = "FWM Clan-Logos";
    private static final String PACK_ID = "file/FWM Clan-Logos";
    private static final Map<String, Long> READY = new ConcurrentHashMap();
    private static final Set<String> LOADING = ConcurrentHashMap.newKeySet();
    private static volatile Set<String> active = Set.of();
    private static boolean checkedAtStart;
    private static volatile long nextIcon;

    private ClanLogos() {
    }

    private static Path cacheDir() {
        return FabricLoader.getInstance().getConfigDir().resolve("fwm-clan-logos");
    }

    public static Identifier font(String id) {
        if (id == null) {
            return null;
        }
        if (active.contains((Object)id)) {
            return Identifier.fromNamespaceAndPath((String)"fwm", (String)("clan_" + id));
        }
        if ("mrket".equals((Object)id)) {
            return Identifier.fromNamespaceAndPath((String)"fwm", (String)"market");
        }
        return null;
    }

    private static long versionOf(String etag) {
        if (etag == null || etag.isBlank()) {
            return 0L;
        }
        long h = 1125899906842597L;
        for (int i = 0; i < etag.length(); ++i) {
            h = 31L * h + (long)etag.charAt(i);
        }
        return h & Long.MAX_VALUE | 1L;
    }

    private static byte[] shrink(byte[] png) throws IOException {
        BufferedImage img = ImageIO.read((InputStream)new ByteArrayInputStream(png));
        if (img == null) {
            return null;
        }
        int target = 30;
        BufferedImage cur = img;
        int w = img.getWidth();
        int h = img.getHeight();
        int side = Math.max((int)w, (int)h);
        BufferedImage square = new BufferedImage(side, side, 2);
        Graphics2D g0 = square.createGraphics();
        g0.drawImage((Image)cur, (side - w) / 2, (side - h) / 2, null);
        g0.dispose();
        cur = square;
        while (cur.getWidth() / 2 >= target) {
            int n = cur.getWidth() / 2;
            BufferedImage next = new BufferedImage(n, n, 2);
            Graphics2D g = next.createGraphics();
            g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
            g.drawImage((Image)cur, 0, 0, n, n, null);
            g.dispose();
            cur = next;
        }
        BufferedImage out = new BufferedImage(target, target, 2);
        Graphics2D g = out.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g.drawImage((Image)cur, 0, 0, target, target, null);
        g.dispose();
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ImageIO.write((RenderedImage)out, (String)"png", (OutputStream)bytes);
        return bytes.toByteArray();
    }

    static void tick(Minecraft client, List<TrustedClans.Clan> clanList, List<TrustedClans.Player> playerList) {
        String key;
        ArrayList clans = new ArrayList();
        for (Object c : clanList) {
            if (c.iconUrl != null) {
                clans.add((Object)new Logo(c.id, c.iconUrl, ClanLogos.versionOf(c.iconEtag), c.iconEtag));
                continue;
            }
            clans.add((Object)new Logo(c.id, c.logo, c.logoVersion, null));
        }
        for (TrustedClans.Player p : playerList) {
            if (p.uuid == null) continue;
            clans.add((Object)new Logo("p_" + p.uuid, p.logo, p.logoVersion, null));
        }
        for (Object c : clans) {
            Long have;
            if (((Logo)((Object)c)).logoVersion() <= 0L || ((Logo)((Object)c)).logo() == null || ((Logo)((Object)c)).logo().isBlank() || ((Logo)((Object)c)).id() == null || !((Logo)((Object)c)).id().matches("[a-z0-9_-]{2,24}|p_[0-9a-f]{32}") || (have = (Long)READY.get((Object)((Logo)((Object)c)).id())) != null && have.longValue() == ((Logo)((Object)c)).logoVersion()) continue;
            Path file = ClanLogos.cacheDir().resolve(((Logo)((Object)c)).id() + "-" + ((Logo)((Object)c)).logoVersion() + ".png");
            if (Files.exists((Path)file, (LinkOption[])new LinkOption[0])) {
                READY.put((Object)((Logo)((Object)c)).id(), (Object)((Logo)((Object)c)).logoVersion());
                continue;
            }
            boolean bot = ((Logo)((Object)c)).logo().startsWith("http");
            if (bot && System.currentTimeMillis() < nextIcon || !LOADING.add((Object)((Logo)((Object)c)).id())) continue;
            String logoId = ((Logo)((Object)c)).id();
            long version = ((Logo)((Object)c)).logoVersion();
            if (bot) {
                nextIcon = System.currentTimeMillis() + 1000L;
                Path etagFile = ClanLogos.cacheDir().resolve(logoId + ".etag");
                String had = null;
                try {
                    if (Files.exists((Path)etagFile, (LinkOption[])new LinkOption[0])) {
                        had = Files.readString((Path)etagFile, (Charset)StandardCharsets.UTF_8).trim();
                    }
                }
                catch (Exception exception) {
                    // empty catch block
                }
                String sent = had;
                FwmApi.request("GET", ((Logo)((Object)c)).logo(), sent, null, null, 0x200000L).whenComplete((got, error) -> {
                    try {
                        if (error != null) {
                            return;
                        }
                        if (got.status() == 429) {
                            nextIcon = System.currentTimeMillis() + Math.max((long)60L, (long)got.retryAfterSeconds()) * 1000L;
                            return;
                        }
                        Files.createDirectories((Path)ClanLogos.cacheDir(), (FileAttribute[])new FileAttribute[0]);
                        Path last = ClanLogos.cacheDir().resolve(logoId + "-last.png");
                        if (got.status() == 304 && Files.exists((Path)last, (LinkOption[])new LinkOption[0])) {
                            Files.copy((Path)last, (Path)file, (CopyOption[])new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                            READY.put((Object)logoId, (Object)version);
                            return;
                        }
                        byte[] bytes = got.body();
                        if (got.status() != 200 || bytes == null || bytes.length < 8 || (bytes[0] & 0xFF) != 137 || bytes[1] != 80) {
                            return;
                        }
                        byte[] small = ClanLogos.shrink(bytes);
                        if (small == null) {
                            return;
                        }
                        Files.write((Path)file, (byte[])small, (OpenOption[])new OpenOption[0]);
                        Files.write((Path)last, (byte[])small, (OpenOption[])new OpenOption[0]);
                        if (got.etag() != null) {
                            Files.writeString((Path)etagFile, (CharSequence)got.etag(), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
                        }
                        READY.put((Object)logoId, (Object)version);
                    }
                    catch (Exception exception) {
                    }
                    finally {
                        LOADING.remove((Object)logoId);
                    }
                });
                continue;
            }
            String url = FwmConfig.INSTANCE.apiBaseUrl.replaceAll("/+$", "") + ((Logo)((Object)c)).logo();
            FwmApi.getBytes(url).whenComplete((bytes, error) -> {
                try {
                    if (error == null && bytes != null && ((byte[])bytes).length > 8 && ((byte[])bytes).length < 64000 && (bytes[0] & 0xFF) == 137 && bytes[1] == 80) {
                        Files.createDirectories((Path)ClanLogos.cacheDir(), (FileAttribute[])new FileAttribute[0]);
                        Files.write((Path)file, (byte[])bytes, (OpenOption[])new OpenOption[0]);
                        READY.put((Object)logoId, (Object)version);
                    }
                }
                catch (Exception exception) {
                }
                finally {
                    LOADING.remove((Object)logoId);
                }
            });
        }
        if (client.gui.overlay() != null || client.gui.screen() != null) {
            return;
        }
        TreeMap wanted = new TreeMap();
        for (Logo c : clans) {
            Long have = (Long)READY.get((Object)c.id());
            if (have == null || c.logoVersion() <= 0L || have.longValue() != c.logoVersion()) continue;
            wanted.put((Object)c.id(), (Object)have);
        }
        String string = key = wanted.isEmpty() ? "" : "v1|" + AntiLeakPack.packFormat() + "|" + String.valueOf((Object)wanted);
        if (!checkedAtStart) {
            checkedAtStart = true;
            if (key.equals((Object)FwmConfig.INSTANCE.clanLogosApplied) && Files.isDirectory((Path)ClanLogos.folder(client), (LinkOption[])new LinkOption[0])) {
                active = Set.copyOf((Collection)wanted.keySet());
                return;
            }
        }
        if (key.equals((Object)FwmConfig.INSTANCE.clanLogosApplied)) {
            return;
        }
        if (!wanted.isEmpty() && wanted.size() < ClanLogos.countWithLogo((List<Logo>)clans)) {
            return;
        }
        ClanLogos.apply(client, (Map<String, Long>)wanted, key);
    }

    private static int countWithLogo(List<Logo> clans) {
        int n = 0;
        for (Logo c : clans) {
            if (c.logoVersion() <= 0L) continue;
            ++n;
        }
        return n;
    }

    private static Path folder(Minecraft client) {
        return client.getResourcePackDirectory().resolve(FOLDER);
    }

    private static void apply(Minecraft client, Map<String, Long> wanted, String key) {
        Path dir = ClanLogos.folder(client);
        PackRepository manager = client.getResourcePackRepository();
        boolean wasEnabled = manager.getSelectedIds().contains((Object)PACK_ID);
        boolean on = !wanted.isEmpty();
        try {
            AntiLeakPack.delete(dir);
            if (on) {
                Path fonts = dir.resolve("assets/fwm/font");
                Path pics = dir.resolve("assets/fwm/textures/font");
                Files.createDirectories((Path)fonts, (FileAttribute[])new FileAttribute[0]);
                Files.createDirectories((Path)pics, (FileAttribute[])new FileAttribute[0]);
                Files.writeString((Path)dir.resolve("pack.mcmeta"), (CharSequence)AntiLeakPack.mcmeta(), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
                for (Map.Entry e : wanted.entrySet()) {
                    String id = (String)e.getKey();
                    Files.copy((Path)ClanLogos.cacheDir().resolve(id + "-" + String.valueOf((Object)e.getValue()) + ".png"), (Path)pics.resolve("clan_" + id + ".png"), (CopyOption[])new CopyOption[0]);
                    Files.writeString((Path)fonts.resolve("clan_" + id + ".json"), (CharSequence)("{\"providers\":[{\"type\":\"bitmap\",\"file\":\"fwm:font/clan_" + id + ".png\",\"ascent\":8,\"height\":10,\"chars\":[\"\\ue000\"]}]}"), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
                }
                manager.reload();
                if (!wasEnabled && !manager.addPack(PACK_ID)) {
                    System.err.println("[FWM] Clan logo pack not found after writing it: " + String.valueOf((Object)dir));
                    return;
                }
            } else {
                manager.removePack(PACK_ID);
            }
            client.options.updateResourcePacks(manager);
            if (on && wasEnabled) {
                client.reloadResourcePacks();
            }
            active = Set.copyOf((Collection)wanted.keySet());
            FwmConfig.INSTANCE.clanLogosApplied = key;
            ConfigManager.save();
        }
        catch (Exception e) {
            System.err.println("[FWM] Clan logos could not be applied: " + String.valueOf((Object)((Object)e)));
        }
    }

    private record Logo(String id, String logo, long logoVersion, String etag) {
    }
}
