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
 *  com.mojang.blaze3d.platform.NativeImage
 *  java.io.IOException
 *  java.io.InputStream
 *  java.lang.AutoCloseable
 *  java.lang.CharSequence
 *  java.lang.Exception
 *  java.lang.Integer
 *  java.lang.Iterable
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.System
 *  java.lang.Thread
 *  java.lang.Throwable
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.CopyOption
 *  java.nio.file.FileSystem
 *  java.nio.file.FileSystems
 *  java.nio.file.FileVisitOption
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.StandardCopyOption
 *  java.nio.file.attribute.FileAttribute
 *  java.util.ArrayList
 *  java.util.Arrays
 *  java.util.Collection
 *  java.util.Comparator
 *  java.util.HashMap
 *  java.util.LinkedHashMap
 *  java.util.LinkedHashSet
 *  java.util.List
 *  java.util.Locale
 *  java.util.Map
 *  java.util.Map$Entry
 *  java.util.Set
 *  java.util.concurrent.ConcurrentHashMap
 *  java.util.regex.Pattern
 *  java.util.stream.Stream
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.renderer.texture.AbstractTexture
 *  net.minecraft.client.renderer.texture.DynamicTexture
 *  net.minecraft.resources.Identifier
 *  net.minecraft.server.packs.PackType
 *  net.minecraft.server.packs.repository.PackRepository
 *  net.minecraft.server.packs.resources.IoSupplier
 */
package net.friendswithmoney.fwm.textures;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.platform.NativeImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import net.friendswithmoney.fwm.antileak.AntiLeakPack;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.textures.IsoRender;
import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.PackRepository;
import net.minecraft.server.packs.resources.IoSupplier;

public final class TextureMixer {
    public static final String FOLDER = "FWM Mix";
    private static final String TMP = ".fwm-mix-tmp";
    public static final String PACK_ID = "file/FWM Mix";
    public static final List<String[]> GROUPS = List.of((Object)new String[]{"pvp", "Kampf / PvP", "Combat / PvP"}, (Object)new String[]{"tools", "Werkzeuge", "Tools"}, (Object)new String[]{"world", "Welt", "World"}, (Object)new String[]{"ui", "Oberfl\u00e4che", "Interface"}, (Object)new String[]{"misc", "Sonstiges", "Other"}, (Object)new String[]{"rest", "Rest", "Everything else"});
    private static final String M = "assets/minecraft/";
    public static final List<Category> CATEGORIES = List.of((Object[])new Category[]{new Category("crystal", "Crystal", "Crystal", "pvp", (List<String>)List.of((Object)"textures/entity/end_crystal/", (Object)"textures/item/end_crystal.png"), "assets/minecraft/textures/entity/end_crystal/end_crystal.png"), new Category("anchor", "Anchor", "Anchor", "pvp", (List<String>)List.of((Object)"textures/block/respawn_anchor*"), "assets/minecraft/textures/block/respawn_anchor_side4.png"), new Category("totem", "Totem", "Totem", "pvp", (List<String>)List.of((Object)"textures/item/totem_of_undying.png", (Object)"optifine/cit/totem*"), "assets/minecraft/textures/item/totem_of_undying.png"), new Category("swords", "Schwerter", "Swords", "pvp", (List<String>)List.of((Object)"textures/item/*_sword.png"), "assets/minecraft/textures/item/diamond_sword.png"), new Category("armor", "R\u00fcstung", "Armour", "pvp", (List<String>)List.of((Object)"textures/entity/equipment/", (Object)"textures/trims/", (Object)"equipment/", (Object)"textures/models/armor/", (Object)"optifine/cit/armor"), "assets/minecraft/textures/item/diamond_chestplate.png"), new Category("bows", "B\u00f6gen / Pfeile", "Bows / arrows", "pvp", (List<String>)List.of((Object)"textures/item/bow*", (Object)"textures/item/crossbow*", (Object)"textures/item/*arrow*"), "assets/minecraft/textures/item/bow.png"), new Category("potions", "Tr\u00e4nke", "Potions", "pvp", (List<String>)List.of((Object)"textures/item/potion*", (Object)"textures/item/splash_potion*", (Object)"textures/item/lingering_potion*"), "assets/minecraft/textures/item/potion.png"), new Category("pearls", "Perlen", "Pearls", "pvp", (List<String>)List.of((Object)"textures/item/ender_pearl.png"), "assets/minecraft/textures/item/ender_pearl.png"), new Category("apples", "\u00c4pfel", "Apples", "pvp", (List<String>)List.of((Object)"textures/item/golden_apple.png", (Object)"textures/item/enchanted_golden_apple.png"), "assets/minecraft/textures/item/golden_apple.png"), new Category("tools", "Spitzhacken / \u00c4xte / Schaufeln", "Pickaxes / axes / shovels", "tools", (List<String>)List.of((Object)"textures/item/*_pickaxe.png", (Object)"textures/item/*_axe.png", (Object)"textures/item/*_shovel.png", (Object)"textures/item/*_hoe.png"), "assets/minecraft/textures/item/diamond_pickaxe.png"), new Category("fire", "Feuer", "Fire", "world", (List<String>)List.of((Object)"textures/block/fire_*", (Object)"textures/block/soul_fire_*"), "assets/minecraft/textures/block/fire_0.png"), new Category("sky", "Himmel", "Sky", "world", (List<String>)List.of((Object)"textures/environment/", (Object)"optifine/sky/", (Object)"mcpatcher/sky/", (Object)"sky/"), "assets/minecraft/textures/environment/sun.png"), new Category("deepslate", "Deepslate", "Deepslate", "world", (List<String>)List.of((Object)"textures/block/deepslate*"), "assets/minecraft/textures/block/deepslate.png"), new Category("stone", "Stein", "Stone", "world", (List<String>)List.of((Object)"textures/block/stone*", (Object)"textures/block/cobblestone*"), "assets/minecraft/textures/block/stone.png"), new Category("snow", "Schnee", "Snow", "world", (List<String>)List.of((Object)"textures/block/snow*", (Object)"textures/block/powder_snow*"), "assets/minecraft/textures/block/snow.png"), new Category("obsidian", "Obsidian", "Obsidian", "world", (List<String>)List.of((Object)"textures/block/obsidian*", (Object)"textures/block/crying_obsidian*"), "assets/minecraft/textures/block/obsidian.png"), new Category("ores", "Erze", "Ores", "world", (List<String>)List.of((Object)"textures/block/*_ore*"), "assets/minecraft/textures/block/diamond_ore.png"), new Category("inventory", "Inventar", "Inventory", "ui", (List<String>)List.of((Object)"textures/gui/container/", (Object)"textures/gui/sprites/container/"), "assets/minecraft/textures/gui/container/inventory.png"), new Category("hud", "Hotbar / HUD", "Hotbar / HUD", "ui", (List<String>)List.of((Object)"textures/gui/sprites/hud/", (Object)"textures/gui/sprites/boss_bar/", (Object)"textures/gui/icons.png", (Object)"textures/gui/widgets.png", (Object)"textures/gui/bars.png"), "assets/minecraft/textures/gui/sprites/hud/heart/full.png"), new Category("font", "Schrift", "Font", "ui", (List<String>)List.of((Object)"font/", (Object)"textures/font/"), "assets/minecraft/textures/font/ascii.png"), new Category("particles", "Partikel", "Particles", "ui", (List<String>)List.of((Object)"textures/particle/", (Object)"particles/"), "assets/minecraft/textures/particle/flame.png"), new Category("effects", "Effekte", "Effects", "ui", (List<String>)List.of((Object)"textures/misc/enchanted_glint*", (Object)"textures/mob_effect/", (Object)"textures/effect/"), "assets/minecraft/textures/mob_effect/speed.png"), new Category("sounds", "Sounds", "Sounds", "misc", (List<String>)List.of((Object)"sounds.json", (Object)"sounds/"), null), new Category("menus", "Men\u00fcs (Rest)", "Menus (rest)", "rest", (List<String>)List.of((Object)"textures/gui/"), "assets/minecraft/textures/gui/sprites/container/slot_highlight_front.png"), new Category("blocks", "Bl\u00f6cke (Rest)", "Blocks (rest)", "rest", (List<String>)List.of((Object)"textures/block/", (Object)"models/block/", (Object)"blockstates/", (Object)"optifine/ctm/", (Object)"textures/blocks/"), "assets/minecraft/textures/block/grass_block_side.png"), new Category("items", "Items (Rest)", "Items (rest)", "rest", (List<String>)List.of((Object)"textures/item/", (Object)"models/item/", (Object)"items/", (Object)"optifine/cit/", (Object)"textures/items/"), "assets/minecraft/textures/item/stick.png"), new Category("mobs", "Mobs (Rest)", "Mobs (rest)", "rest", (List<String>)List.of((Object)"textures/entity/", (Object)"optifine/random/", (Object)"optifine/mob/"), "assets/minecraft/textures/entity/creeper/creeper.png"), new Category("rest", "Rest", "Everything else", "rest", (List<String>)List.of(), null)});
    private static final Map<String, String> OLD_PARENT = Map.ofEntries((Map.Entry[])new Map.Entry[]{Map.entry((Object)"anchor", (Object)"blocks"), Map.entry((Object)"fire", (Object)"blocks"), Map.entry((Object)"deepslate", (Object)"blocks"), Map.entry((Object)"stone", (Object)"blocks"), Map.entry((Object)"snow", (Object)"blocks"), Map.entry((Object)"obsidian", (Object)"blocks"), Map.entry((Object)"ores", (Object)"blocks"), Map.entry((Object)"totem", (Object)"items"), Map.entry((Object)"swords", (Object)"items"), Map.entry((Object)"bows", (Object)"items"), Map.entry((Object)"potions", (Object)"items"), Map.entry((Object)"pearls", (Object)"items"), Map.entry((Object)"apples", (Object)"items"), Map.entry((Object)"tools", (Object)"items"), Map.entry((Object)"crystal", (Object)"mobs"), Map.entry((Object)"inventory", (Object)"menus")});
    private static final Map<String, Pattern> GLOBS = new ConcurrentHashMap();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static volatile boolean building;
    private static volatile float progress;
    private static volatile String message;
    public static final Set<String> OFFERED;
    private static final Map<String, Map<String, String>> CONTENT;
    private static final Set<String> SCANNING;
    private static final Map<String, Preview> PREVIEWS;
    private static final Set<String> MISSING;
    private static int previewCount;
    private static int budget;

    public static void migrate() {
        FwmConfig config = FwmConfig.INSTANCE;
        if (config.textureMixVersion >= 2) {
            return;
        }
        for (Map.Entry e : OLD_PARENT.entrySet()) {
            String pack = (String)config.textureMix.get(e.getValue());
            if (pack == null || pack.isEmpty() || config.textureMix.containsKey(e.getKey())) continue;
            config.textureMix.put((Object)((String)e.getKey()), (Object)pack);
        }
        config.textureMixVersion = 2;
        ConfigManager.save();
    }

    public static Category category(String id) {
        for (Category c : CATEGORIES) {
            if (!c.id().equals((Object)id)) continue;
            return c;
        }
        return (Category)((Object)CATEGORIES.get(0));
    }

    private static boolean fits(String inside, String prefix) {
        if (prefix.indexOf(42) < 0) {
            return inside.startsWith(prefix);
        }
        Pattern glob = (Pattern)GLOBS.computeIfAbsent((Object)prefix, g -> Pattern.compile((String)((String)Arrays.stream((Object[])g.split("\\*", -1)).map(Pattern::quote).reduce((x, y) -> x + "[^/]*" + y).orElse((Object)"") + (g.endsWith("/") ? ".*" : "(/.*)?"))));
        return glob.matcher((CharSequence)inside).matches();
    }

    private TextureMixer() {
    }

    public static void register() {
    }

    public static boolean building() {
        return building;
    }

    public static float progress() {
        return progress;
    }

    public static String message() {
        return message;
    }

    private static Path packDir() {
        return Minecraft.getInstance().getResourcePackDirectory();
    }

    public static List<String> packs() {
        ArrayList out = new ArrayList();
        try (Stream list = Files.list((Path)TextureMixer.packDir());){
            list.forEach(arg_0 -> TextureMixer.lambda$packs$0((List)out, arg_0));
        }
        catch (IOException iOException) {
            // empty catch block
        }
        out.sort(String.CASE_INSENSITIVE_ORDER);
        return out;
    }

    public static boolean offered(String category) {
        return OFFERED.contains((Object)category);
    }

    public static String chosen(String category) {
        if (!TextureMixer.offered(category)) {
            return "";
        }
        String pack = (String)FwmConfig.INSTANCE.textureMix.get((Object)category);
        return pack == null ? "" : pack;
    }

    public static void choose(String category, String pack) {
        if (pack == null || pack.isEmpty()) {
            FwmConfig.INSTANCE.textureMix.remove((Object)category);
        } else {
            FwmConfig.INSTANCE.textureMix.put((Object)category, (Object)pack);
        }
        ConfigManager.save();
    }

    static String classify(String path) {
        if (!path.startsWith("assets/")) {
            return null;
        }
        String rest = path.substring("assets/".length());
        int slash = rest.indexOf(47);
        if (slash < 0) {
            return null;
        }
        String namespace = rest.substring(0, slash);
        String inside = rest.substring(slash + 1);
        if (namespace.equals((Object)"fabricskyboxes") || namespace.equals((Object)"nuit")) {
            return "sky";
        }
        for (Category category : CATEGORIES) {
            for (String prefix : category.prefixes()) {
                String model;
                if (TextureMixer.fits(inside, prefix)) {
                    return category.id();
                }
                if (!prefix.startsWith("textures/item/") || !TextureMixer.fits(inside, "models/item/" + (model = prefix.substring("textures/item/".length()).replace((CharSequence)".png", (CharSequence)".json"))) && !TextureMixer.fits(inside, "items/" + model)) continue;
                return category.id();
            }
        }
        return "rest";
    }

    public static void build(Minecraft client) {
        if (building) {
            return;
        }
        LinkedHashMap choice = new LinkedHashMap(FwmConfig.INSTANCE.textureMix);
        choice.values().removeIf(v -> v == null || v.isEmpty());
        if (choice.isEmpty()) {
            message = Lang.t("W\u00e4hle zuerst f\u00fcr mindestens eine Kategorie ein Pack.", "Choose a pack for at least one category first.");
            return;
        }
        building = true;
        progress = 0.0f;
        message = Lang.t("Baue Mix \u2026", "Building mix \u2026");
        Thread thread = new Thread(() -> TextureMixer.lambda$build$1((Map)choice, client), "FWM texture mixer");
        thread.setDaemon(true);
        thread.start();
    }

    private static void write(Map<String, String> choice) throws IOException {
        Path base = TextureMixer.packDir();
        Path tmp = base.resolve(TMP);
        Path target = base.resolve(FOLDER);
        TextureMixer.deleteTree(tmp);
        Files.createDirectories((Path)tmp, (FileAttribute[])new FileAttribute[0]);
        LinkedHashSet packs = new LinkedHashSet(choice.values());
        LinkedHashMap atlases = new LinkedHashMap();
        HashMap formats = new HashMap();
        int done = 0;
        for (String pack : packs) {
            Source source = new Source(pack);
            try {
                List files;
                Path assets = source.root.resolve("assets");
                if (!Files.isDirectory((Path)assets, (LinkOption[])new LinkOption[0])) continue;
                try (Stream walk = Files.walk((Path)assets, (FileVisitOption[])new FileVisitOption[0]);){
                    files = walk.filter(x$0 -> Files.isRegularFile((Path)x$0, (LinkOption[])new LinkOption[0])).toList();
                }
                for (Path file : files) {
                    String rel = source.root.relativize(file).toString().replace('\\', '/');
                    if (rel.startsWith("/")) {
                        rel = rel.substring(1);
                    }
                    if (rel.matches("assets/[^/]+/atlases/[^/]+\\.json")) {
                        TextureMixer.mergeAtlas((Map<String, JsonArray>)atlases, rel, file);
                        continue;
                    }
                    String category = TextureMixer.classify(rel);
                    if (category == null || !pack.equals(choice.get((Object)category))) continue;
                    Path out = TextureMixer.safe(tmp, rel);
                    Files.createDirectories((Path)out.getParent(), (FileAttribute[])new FileAttribute[0]);
                    Files.copy((Path)file, (Path)out, (CopyOption[])new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
                }
                formats.put((Object)pack, (Object)TextureMixer.packFormat(source.root));
            }
            finally {
                source.close();
                continue;
            }
            progress = (float)(++done) / (float)(packs.size() + 1);
        }
        for (Map.Entry atlas : atlases.entrySet()) {
            JsonObject root = new JsonObject();
            root.add("sources", (JsonElement)atlas.getValue());
            Path out = TextureMixer.safe(tmp, (String)atlas.getKey());
            Files.createDirectories((Path)out.getParent(), (FileAttribute[])new FileAttribute[0]);
            Files.writeString((Path)out, (CharSequence)GSON.toJson((JsonElement)root), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        }
        Files.writeString((Path)tmp.resolve("pack.mcmeta"), (CharSequence)AntiLeakPack.mcmeta(), (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);
        try (InputStream logo = TextureMixer.class.getResourceAsStream("/assets/fwm/textures/gui/logo.png");){
            if (logo != null) {
                Files.copy((InputStream)logo, (Path)tmp.resolve("pack.png"), (CopyOption[])new CopyOption[]{StandardCopyOption.REPLACE_EXISTING});
            }
        }
        TextureMixer.deleteTree(target);
        try {
            Files.move((Path)tmp, (Path)target, (CopyOption[])new CopyOption[]{StandardCopyOption.ATOMIC_MOVE});
        }
        catch (IOException e) {
            Files.move((Path)tmp, (Path)target, (CopyOption[])new CopyOption[0]);
        }
        int current = AntiLeakPack.packFormat();
        ArrayList older = new ArrayList();
        formats.forEach((arg_0, arg_1) -> TextureMixer.lambda$write$1(current, (List)older, arg_0, arg_1));
        progress = 1.0f;
        message = older.isEmpty() ? Lang.t("Mix gebaut.", "Mix built.") : Lang.t("Mix gebaut. Pack f\u00fcr \u00e4ltere Version: ", "Mix built. Pack for an older version: ") + String.join((CharSequence)", ", (Iterable)older);
    }

    private static void mergeAtlas(Map<String, JsonArray> atlases, String rel, Path file) {
        try {
            JsonObject json = JsonParser.parseString((String)Files.readString((Path)file, (Charset)StandardCharsets.UTF_8)).getAsJsonObject();
            JsonArray sources = json.has("sources") ? json.getAsJsonArray("sources") : new JsonArray();
            JsonArray merged = (JsonArray)atlases.computeIfAbsent((Object)rel, k -> new JsonArray());
            LinkedHashSet seen = new LinkedHashSet();
            for (JsonElement have : merged) {
                seen.add((Object)have.toString());
            }
            for (JsonElement add : sources) {
                if (!seen.add((Object)add.toString())) continue;
                merged.add(add);
            }
        }
        catch (Exception e) {
            System.err.println("[FWM] Skipping broken atlas " + rel + ": " + e.getMessage());
        }
    }

    private static int packFormat(Path root) {
        try {
            Path meta = root.resolve("pack.mcmeta");
            if (!Files.exists((Path)meta, (LinkOption[])new LinkOption[0])) {
                return 0;
            }
            JsonObject pack = JsonParser.parseString((String)Files.readString((Path)meta, (Charset)StandardCharsets.UTF_8)).getAsJsonObject().getAsJsonObject("pack");
            if (pack == null) {
                return 0;
            }
            if (pack.has("pack_format")) {
                return pack.get("pack_format").getAsInt();
            }
            if (pack.has("min_format")) {
                JsonElement min = pack.get("min_format");
                return min.isJsonArray() ? min.getAsJsonArray().get(0).getAsInt() : min.getAsInt();
            }
        }
        catch (Exception exception) {
            // empty catch block
        }
        return 0;
    }

    private static Path safe(Path base, String rel) throws IOException {
        Path out = base.resolve(rel).normalize();
        if (!out.startsWith(base.normalize()) || rel.contains((CharSequence)"..")) {
            throw new IOException("Path outside the mix folder: " + rel);
        }
        return out;
    }

    private static void deleteTree(Path dir) throws IOException {
        String name = dir.getFileName().toString();
        if (!name.equals((Object)FOLDER) && !name.equals((Object)TMP)) {
            throw new IOException("Refusing to delete " + String.valueOf((Object)dir));
        }
        if (!Files.exists((Path)dir, (LinkOption[])new LinkOption[0])) {
            return;
        }
        try (Stream walk = Files.walk((Path)dir, (FileVisitOption[])new FileVisitOption[0]);){
            for (Path p : walk.sorted(Comparator.reverseOrder()).toList()) {
                Files.deleteIfExists((Path)p);
            }
        }
    }

    private static void apply(Minecraft client, Set<String> sources) {
        PackRepository manager = client.getResourcePackRepository();
        FwmConfig config = FwmConfig.INSTANCE;
        manager.reload();
        ArrayList enabled = new ArrayList(manager.getSelectedIds());
        boolean wasOn = enabled.contains((Object)PACK_ID);
        if (config.textureMixBefore == null) {
            ArrayList before = new ArrayList((Collection)enabled);
            before.remove((Object)PACK_ID);
            config.textureMixBefore = before;
        }
        enabled.remove((Object)PACK_ID);
        if (config.textureMixDisableSources) {
            for (String pack : sources) {
                enabled.remove((Object)("file/" + pack));
            }
        }
        enabled.add((Object)PACK_ID);
        manager.setSelected((Collection)enabled);
        client.options.updateResourcePacks(manager);
        if (wasOn) {
            client.reloadResourcePacks();
        }
        ConfigManager.save();
        message = message + Lang.t(" Aktiviert.", " Switched on.");
    }

    public static void remove(Minecraft client) {
        if (building) {
            return;
        }
        PackRepository manager = client.getResourcePackRepository();
        FwmConfig config = FwmConfig.INSTANCE;
        manager.reload();
        ArrayList enabled = new ArrayList(manager.getSelectedIds());
        enabled.remove((Object)PACK_ID);
        if (config.textureMixBefore != null) {
            for (String id : config.textureMixBefore) {
                if (enabled.contains((Object)id) || !manager.isAvailable(id)) continue;
                enabled.add((Object)id);
            }
            config.textureMixBefore = null;
        }
        manager.setSelected((Collection)enabled);
        client.options.updateResourcePacks(manager);
        try {
            TextureMixer.deleteTree(TextureMixer.packDir().resolve(FOLDER));
        }
        catch (IOException e) {
            System.err.println("[FWM] Could not delete the mix: " + e.getMessage());
        }
        ConfigManager.save();
        message = Lang.t("Mix entfernt.", "Mix removed.");
    }

    public static boolean active(Minecraft client) {
        return client.getResourcePackRepository().getSelectedIds().contains((Object)PACK_ID);
    }

    public static Map<String, String> content(String pack) {
        Map have = (Map)CONTENT.get((Object)pack);
        if (have != null) {
            return have;
        }
        if (SCANNING.add((Object)pack)) {
            Thread thread = new Thread(() -> {
                HashMap found;
                block13: {
                    found = new HashMap();
                    try (Source source = new Source(pack);){
                        Path assets = source.root.resolve("assets");
                        if (!Files.isDirectory((Path)assets, (LinkOption[])new LinkOption[0])) break block13;
                        try (Stream walk = Files.walk((Path)assets, (FileVisitOption[])new FileVisitOption[0]);){
                            walk.filter(x$0 -> Files.isRegularFile((Path)x$0, (LinkOption[])new LinkOption[0])).forEach(arg_0 -> TextureMixer.lambda$content$2(source, (Map)found, arg_0));
                        }
                    }
                    catch (Exception exception) {
                        // empty catch block
                    }
                }
                CONTENT.put((Object)pack, (Object)found);
                SCANNING.remove((Object)pack);
            }, "FWM texture index");
            thread.setDaemon(true);
            thread.start();
        }
        return null;
    }

    public static int packsWith(List<String> packs, String category) {
        int n = 0;
        for (String pack : packs) {
            Map<String, String> c = TextureMixer.content(pack);
            if (c == null || !c.containsKey((Object)category)) continue;
            ++n;
        }
        return n;
    }

    /*
     * Enabled aggressive exception aggregation
     */
    public static Preview preview(String pack, Category category) {
        String own;
        if (pack == null || pack.isEmpty()) {
            return null;
        }
        String path = category.preview();
        Map content = (Map)CONTENT.get((Object)pack);
        String string = own = content == null ? null : (String)content.get((Object)category.id());
        if (path == null) {
            path = own;
        }
        if (path == null || path.isEmpty()) {
            return null;
        }
        String key = pack + "|" + path;
        Preview have = (Preview)((Object)PREVIEWS.get((Object)key));
        if (have != null || MISSING.contains((Object)key)) {
            return have;
        }
        if (budget <= 0) {
            return null;
        }
        --budget;
        try (Source source = new Source(pack);){
            Preview preview;
            block21: {
                Path file = source.root.resolve(path);
                if (!(Files.exists((Path)file, (LinkOption[])new LinkOption[0]) || own == null || own.isEmpty() || own.equals((Object)path))) {
                    file = source.root.resolve(own);
                }
                if (!Files.exists((Path)file, (LinkOption[])new LinkOption[0])) {
                    MISSING.add((Object)key);
                    Preview preview2 = null;
                    return preview2;
                }
                InputStream in = Files.newInputStream((Path)file, (OpenOption[])new OpenOption[0]);
                try {
                    preview = TextureMixer.register(key, in);
                    if (in == null) break block21;
                }
                catch (Throwable throwable) {
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
                in.close();
            }
            return preview;
        }
        catch (Exception e) {
            MISSING.add((Object)key);
            return null;
        }
    }

    public static void previewBudget(int n) {
        budget = n;
    }

    public static Preview vanillaPreview(Category category) {
        Preview preview;
        block12: {
            String path = category.preview();
            if (path == null || !path.startsWith(M)) {
                return null;
            }
            String key = "|vanilla|" + path;
            Preview have = (Preview)((Object)PREVIEWS.get((Object)key));
            if (have != null || MISSING.contains((Object)key)) {
                return have;
            }
            if (budget <= 0) {
                return null;
            }
            --budget;
            IoSupplier supplier = Minecraft.getInstance().getVanillaPackResources().fullResources().getResource(PackType.CLIENT_RESOURCES, Identifier.withDefaultNamespace((String)path.substring(M.length())));
            if (supplier == null) {
                MISSING.add((Object)key);
                return null;
            }
            InputStream in = (InputStream)supplier.get();
            try {
                preview = TextureMixer.register(key, in);
                if (in == null) break block12;
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
                    MISSING.add((Object)key);
                    return null;
                }
            }
            in.close();
        }
        return preview;
    }

    private static Preview register(String key, InputStream in) throws IOException {
        NativeImage image = NativeImage.read((InputStream)in);
        NativeImage built = IsoRender.render(null, key.substring(key.lastIndexOf(124) + 1), image);
        if (built != null) {
            image.close();
            image = built;
        }
        int w = image.getWidth();
        int h = image.getHeight();
        int frameH = h > w && h % w == 0 ? w : h;
        Identifier id = Identifier.fromNamespaceAndPath((String)"fwm", (String)("mixpreview/" + previewCount++));
        Minecraft.getInstance().getTextureManager().register(id, (AbstractTexture)new DynamicTexture(() -> "fwm mix preview", image));
        Preview preview = new Preview(id, w, frameH, h);
        PREVIEWS.put((Object)key, (Object)preview);
        return preview;
    }

    public static void releasePreviews() {
        for (Preview preview : PREVIEWS.values()) {
            Minecraft.getInstance().getTextureManager().release(preview.id());
        }
        PREVIEWS.clear();
        MISSING.clear();
    }

    public static void clearPreviews() {
        for (Preview preview : PREVIEWS.values()) {
            Minecraft.getInstance().getTextureManager().release(preview.id());
        }
        PREVIEWS.clear();
        MISSING.clear();
        CONTENT.clear();
    }

    public static boolean favourite(String pack) {
        return FwmConfig.INSTANCE.textureFavorites.contains((Object)pack);
    }

    public static void toggleFavourite(String pack) {
        List<String> favs = FwmConfig.INSTANCE.textureFavorites;
        if (!favs.remove((Object)pack)) {
            favs.add((Object)pack);
        }
        ConfigManager.save();
    }

    public static void saveTheme(String name) {
        String n;
        String string = n = name == null ? "" : name.trim();
        if (n.isEmpty()) {
            return;
        }
        FwmConfig.INSTANCE.textureThemes.removeIf(t -> n.equalsIgnoreCase(t.name));
        FwmConfig.TextureTheme theme = new FwmConfig.TextureTheme();
        theme.name = n;
        theme.mix = new LinkedHashMap(FwmConfig.INSTANCE.textureMix);
        FwmConfig.INSTANCE.textureThemes.add((Object)theme);
        ConfigManager.save();
    }

    public static void applyTheme(FwmConfig.TextureTheme theme) {
        FwmConfig.INSTANCE.textureMix.clear();
        if (theme != null && theme.mix != null) {
            FwmConfig.INSTANCE.textureMix.putAll(theme.mix);
        }
        ConfigManager.save();
    }

    private static /* synthetic */ void lambda$content$2(Source source, Map found, Path file) {
        String category;
        String rel = source.root.relativize(file).toString().replace('\\', '/');
        if (rel.startsWith("/")) {
            rel = rel.substring(1);
        }
        if ((category = TextureMixer.classify(rel)) == null) {
            return;
        }
        String before = (String)found.get((Object)category);
        boolean png = rel.endsWith(".png");
        if (before == null || before.isEmpty() && png) {
            found.put((Object)category, (Object)(png ? rel : ""));
        }
    }

    private static /* synthetic */ void lambda$write$1(int current, List older, String pack, Integer format) {
        if (format > 0 && format < current) {
            older.add((Object)pack);
        }
    }

    private static /* synthetic */ void lambda$build$1(Map choice, Minecraft client) {
        try {
            TextureMixer.write((Map<String, String>)choice);
            client.execute(() -> {
                building = false;
                TextureMixer.apply(client, (Set<String>)new LinkedHashSet(choice.values()));
            });
        }
        catch (Exception e) {
            System.err.println("[FWM] Texture mix failed: " + String.valueOf((Object)((Object)e)));
            building = false;
            message = Lang.t("Fehler beim Bauen: ", "Build failed: ") + e.getMessage();
        }
    }

    private static /* synthetic */ void lambda$packs$0(List out, Path path) {
        boolean zip;
        String name = path.getFileName().toString();
        if (name.equals((Object)FOLDER) || name.equals((Object)TMP) || name.equals((Object)"FWM Anti-Leak") || name.equals((Object)"FWM Clan-Logos") || name.startsWith(".")) {
            return;
        }
        boolean folder = Files.isDirectory((Path)path, (LinkOption[])new LinkOption[0]) && Files.exists((Path)path.resolve("pack.mcmeta"), (LinkOption[])new LinkOption[0]);
        boolean bl = zip = !Files.isDirectory((Path)path, (LinkOption[])new LinkOption[0]) && name.toLowerCase(Locale.ROOT).endsWith(".zip");
        if (folder || zip) {
            out.add((Object)name);
        }
    }

    static {
        message = "";
        OFFERED = Set.of((Object[])new String[]{"crystal", "anchor", "totem", "swords", "armor", "bows", "potions", "pearls", "apples", "tools", "obsidian", "inventory", "hud", "particles", "effects"});
        CONTENT = new ConcurrentHashMap();
        SCANNING = ConcurrentHashMap.newKeySet();
        PREVIEWS = new ConcurrentHashMap();
        MISSING = ConcurrentHashMap.newKeySet();
        budget = Integer.MAX_VALUE;
    }

    public record Category(String id, String de, String en, String group, List<String> prefixes, String preview) {
        public String label() {
            return Lang.t(this.de, this.en);
        }

        public boolean tiled() {
            return this.preview != null && this.preview.contains((CharSequence)"/block/");
        }
    }

    private static final class Source
    implements AutoCloseable {
        final Path root;
        final FileSystem zip;

        Source(String name) throws IOException {
            Path path = TextureMixer.packDir().resolve(name);
            if (Files.isDirectory((Path)path, (LinkOption[])new LinkOption[0])) {
                this.root = path;
                this.zip = null;
            } else {
                this.zip = FileSystems.newFileSystem((Path)path);
                this.root = this.zip.getPath("/", new String[0]);
            }
        }

        public void close() throws IOException {
            if (this.zip != null) {
                this.zip.close();
            }
        }
    }

    public record Preview(Identifier id, int width, int frameHeight, int height) {
    }
}
