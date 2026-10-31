// class net/friendswithmoney/fwm/auction/StreamerStore  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GSON Lcom/google/gson/Gson;;
private void <init>() {
    return;
}

private static Path file(String name) {
    return loader.api.FabricLoader.getInstance().getConfigDir().resolve(name);
}

public static void load(String name, Class type) {
    v2 = auction.StreamerStore.file(name);
    if (Files.exists(v2, new LinkOption[0]) !=0) goto L18;
    return null;
    L18:
    v3 = Files.newBufferedReader(v2, StandardCharsets.UTF_8);
    v4 = auction.StreamerStore.GSON.fromJson(v3, type);
    if (v3 == null) goto L44;
    v3.close();
    L44:
    return v4;
    v4 = /*catch Throwable*/ ex;
    if (v3 == null) goto L69;
    v3.close();
    goto L69;
    v5 = /*catch Throwable*/ ex;
    v4.addSuppressed(v5);
    L69:
    throw v4;
    v3 = /*catch Exception*/ ex;
    System.err.println('[FWM] ' + name + ' could not be read: ' + v3.getMessage());
    return null;
}

public static void save(String name, Object state) {
    v2 = auction.StreamerStore.file(name);
    v3 = auction.StreamerStore.file(name + '.tmp');
    if (state != null) goto L25;
    Files.deleteIfExists(v2);
    return;
    L25:
    v4 = Files.newBufferedWriter(v3, StandardCharsets.UTF_8, new OpenOption[0]);
    auction.StreamerStore.GSON.toJson(state, v4);
    if (v4 == null) goto L87;
    v4.close();
    goto L87;
    v5 = /*catch Throwable*/ ex;
    if (v4 == null) goto L84;
    v4.close();
    goto L84;
    v6 = /*catch Throwable*/ ex;
    v5.addSuppressed(v6);
    L84:
    throw v5;
    L87:
    new CopyOption[2][0] = StandardCopyOption.REPLACE_EXISTING;
    new CopyOption[2][1] = StandardCopyOption.ATOMIC_MOVE;
    Files.move(v3, v2, new CopyOption[2]);
    goto L131;
    v4 = /*catch Exception*/ ex;
    System.err.println('[FWM] ' + name + ' could not be saved: ' + v4.getMessage());
    L131:
    return;
}

public static Object encode(world.item.ItemStack stack) {
    v1 = client.Minecraft.getInstance();
    if (stack == null) goto L22;
    if (stack.isEmpty() !=0) goto L22;
    if (v1.level != null) goto L24;
    L22:
    return null;
    L24:
    v2 = v1.level.registryAccess().createSerializationContext(serialization.JsonOps.INSTANCE);
    return ((JsonElement)world.item.ItemStack.CODEC.encodeStart(v2, stack).result().orElse(null));
}

public static Object decode(JsonElement json) {
    v1 = client.Minecraft.getInstance();
    if (json == null) goto L15;
    if (v1.level != null) goto L19;
    L15:
    return world.item.ItemStack.EMPTY;
    L19:
    v2 = v1.level.registryAccess().createSerializationContext(serialization.JsonOps.INSTANCE);
    return ((world.item.ItemStack)world.item.ItemStack.CODEC.parse(v2, json).result().orElse(world.item.ItemStack.EMPTY));
}

public static int carried(world.item.ItemStack wanted) {
    v1 = client.Minecraft.getInstance();
    if (v1.player == null) goto L22;
    if (wanted == null) goto L22;
    if (wanted.isEmpty() ==0) goto L24;
    L22:
    return 0;
    L24:
    v2 = v1.player.getInventory();
    v3 = 0;
    v4 = 0;
    L37:
    if (v4 >= v2.getContainerSize()) goto L85;
    v5 = v2.getItem(v4);
    if (v5.isEmpty() !=0) goto L79;
    if (world.item.ItemStack.isSameItemSameComponents(v5, wanted) ==0) goto L79;
    v3 = (v3 + v5.getCount());
    L79:
    v4 = v4 + 1;
    goto L37;
    L85:
    return v3;
}

public static void copy(String text) {
    client.Minecraft.getInstance().keyboardHandler.setClipboard(text);
    return;
}

public static void say(String line) {
    v1 = auction.StreamerStore.chatSafe(line);
    if (v1.length() <= 256) goto L24;
    v1 = v1.substring(0, 256);
    L24:
    if (v1.isEmpty() ==0) goto L33;
    return 0;
    L33:
    if (v1.startsWith("/") ==0) goto L51;
    return auction.StreamerStore.command(v1.substring(1));
    L51:
    v2 = client.Minecraft.getInstance();
    if (v2.player == null) goto L69;
    if (v2.getConnection() != null) goto L71;
    L69:
    return 0;
    L71:
    v2.getConnection().sendChat(v1);
    return 1;
}

// DECOMPILE ERROR in chatSafe: 'dict' object has no attribute 'op_type'
public static void command(String command) {
    v1 = client.Minecraft.getInstance();
    if (v1.player == null) goto L18;
    if (v1.getConnection() != null) goto L20;
    L18:
    return 0;
    L20:
    v1.getConnection().sendCommand(command);
    return 1;
}

static Gson <clinit>() {
    auction.StreamerStore.GSON = ?.setPrettyPrinting().create();
    return;
}

