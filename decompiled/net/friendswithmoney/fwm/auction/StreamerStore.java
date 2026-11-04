/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.gson.Gson
 *  com.google.gson.GsonBuilder
 *  com.google.gson.JsonElement
 *  com.mojang.serialization.DynamicOps
 *  com.mojang.serialization.JsonOps
 *  java.io.BufferedReader
 *  java.io.BufferedWriter
 *  java.io.Reader
 *  java.lang.Appendable
 *  java.lang.Character
 *  java.lang.Class
 *  java.lang.Exception
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.nio.charset.Charset
 *  java.nio.charset.StandardCharsets
 *  java.nio.file.CopyOption
 *  java.nio.file.Files
 *  java.nio.file.LinkOption
 *  java.nio.file.OpenOption
 *  java.nio.file.Path
 *  java.nio.file.StandardCopyOption
 *  net.fabricmc.loader.api.FabricLoader
 *  net.minecraft.client.Minecraft
 *  net.minecraft.resources.RegistryOps
 *  net.minecraft.world.entity.player.Inventory
 *  net.minecraft.world.item.ItemStack
 */
package net.friendswithmoney.fwm.auction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.RegistryOps;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;

public final class StreamerStore {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private StreamerStore() {
    }

    private static Path file(String name) {
        return FabricLoader.getInstance().getConfigDir().resolve(name);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static <T> T load(String name, Class<T> type) {
        Path path = StreamerStore.file(name);
        if (!Files.exists((Path)path, (LinkOption[])new LinkOption[0])) {
            return null;
        }
        try (BufferedReader reader = Files.newBufferedReader((Path)path, (Charset)StandardCharsets.UTF_8);){
            Object object = GSON.fromJson((Reader)reader, type);
            return (T)object;
        }
        catch (Exception e) {
            System.err.println("[FWM] " + name + " could not be read: " + e.getMessage());
            return null;
        }
    }

    public static void save(String name, Object state) {
        Path path = StreamerStore.file(name);
        Path temp = StreamerStore.file(name + ".tmp");
        try {
            if (state == null) {
                Files.deleteIfExists((Path)path);
                return;
            }
            try (BufferedWriter writer = Files.newBufferedWriter((Path)temp, (Charset)StandardCharsets.UTF_8, (OpenOption[])new OpenOption[0]);){
                GSON.toJson(state, (Appendable)writer);
            }
            Files.move((Path)temp, (Path)path, (CopyOption[])new CopyOption[]{StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE});
        }
        catch (Exception e) {
            System.err.println("[FWM] " + name + " could not be saved: " + e.getMessage());
        }
    }

    public static JsonElement encode(ItemStack stack) {
        Minecraft client = Minecraft.getInstance();
        if (stack == null || stack.isEmpty() || client.level == null) {
            return null;
        }
        RegistryOps ops = client.level.registryAccess().createSerializationContext((DynamicOps)JsonOps.INSTANCE);
        return (JsonElement)ItemStack.CODEC.encodeStart((DynamicOps)ops, (Object)stack).result().orElse(null);
    }

    public static ItemStack decode(JsonElement json) {
        Minecraft client = Minecraft.getInstance();
        if (json == null || client.level == null) {
            return ItemStack.EMPTY;
        }
        RegistryOps ops = client.level.registryAccess().createSerializationContext((DynamicOps)JsonOps.INSTANCE);
        return (ItemStack)ItemStack.CODEC.parse((DynamicOps)ops, (Object)json).result().orElse((Object)ItemStack.EMPTY);
    }

    public static int carried(ItemStack wanted) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || wanted == null || wanted.isEmpty()) {
            return 0;
        }
        Inventory inventory = client.player.getInventory();
        int count = 0;
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack stack = inventory.getItem(i);
            if (stack.isEmpty() || !ItemStack.isSameItemSameComponents((ItemStack)stack, (ItemStack)wanted)) continue;
            count += stack.getCount();
        }
        return count;
    }

    public static void copy(String text) {
        Minecraft.getInstance().keyboardHandler.setClipboard(text);
    }

    public static boolean say(String line) {
        String text = StreamerStore.chatSafe(line);
        if (text.length() > 256) {
            text = text.substring(0, 256);
        }
        if (text.isEmpty()) {
            return false;
        }
        if (text.startsWith("/")) {
            return StreamerStore.command(text.substring(1));
        }
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.getConnection() == null) {
            return false;
        }
        client.getConnection().sendChat(text);
        return true;
    }

    public static String chatSafe(String line) {
        if (line == null) {
            return "";
        }
        StringBuilder out = new StringBuilder(line.length());
        block7: for (int i = 0; i < line.length(); ++i) {
            char c = line.charAt(i);
            switch (c) {
                case '\u2013': 
                case '\u2014': 
                case '\u2212': {
                    out.append('-');
                    continue block7;
                }
                case '\u00ab': 
                case '\u00bb': 
                case '\u201c': 
                case '\u201d': 
                case '\u201e': {
                    out.append('\"');
                    continue block7;
                }
                case '\u2018': 
                case '\u2019': 
                case '\u201a': {
                    out.append('\'');
                    continue block7;
                }
                case '\u2026': {
                    out.append("...");
                    continue block7;
                }
                case '\u00a0': {
                    out.append(' ');
                    continue block7;
                }
                default: {
                    boolean latinLetter;
                    boolean ascii = c >= ' ' && c <= '~';
                    boolean bl = latinLetter = c >= '\u00c0' && c <= '\u00ff' && Character.isLetter((char)c);
                    if (!ascii && !latinLetter && c != '\u20ac') continue block7;
                    out.append(c);
                }
            }
        }
        return out.toString().replaceAll(" {2,}", " ").trim();
    }

    public static boolean command(String command) {
        Minecraft client = Minecraft.getInstance();
        if (client.player == null || client.getConnection() == null) {
            return false;
        }
        client.getConnection().sendCommand(command);
        return true;
    }
}
