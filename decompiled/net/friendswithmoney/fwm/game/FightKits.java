// class net/friendswithmoney/fwm/game/FightKits  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field OFFHAND I;
field TIERED Ljava/util/regex/Pattern;;
field toggleBox [I;
field kitBox [I;
field STACKS Ljava/util/Map;;
public static boolean kitSlot(int index) {
    if (index <0) goto L10;
    if (index < 36) goto L16;
    L10:
    if (index != 40) goto L20;
    L16:
    goto L21;
    L20:
    L21:
    return 0;
}

private void <init>() {
    return;
}

public static String kind(String id) {
    if (id != null) goto L7;
    return "";
    L7:
    if (id.startsWith("minecraft:") ==0) goto L25;
    goto L26;
    L25:
    L26:
    v1 = id;
    v2 = game.FightKits.TIERED.matcher(v1);
    if (v2.matches() ==0) goto L55;
    goto L56;
    L55:
    L56:
    return v1;
}

public static void missing(Map slots, Map have) {
    v2 = ?;
    have.forEach(/*indy*/accept(v2));
    v3 = ?;
    v4 = ?;
    slots.entrySet().stream().sorted(Map$Entry.comparingByKey()).forEach(/*indy*/accept(v3, v4));
    v5 = ?;
    v3.forEach(/*indy*/accept(v2, v5, v4));
    return v5;
}

public static Object selected() {
    v0 = config.FwmConfig.INSTANCE.fightKits;
    if (v0 == null) goto L20;
    if (v0.isEmpty() ==0) goto L22;
    L20:
    return null;
    L22:
    v1 = Math.max(0, Math.min((v0.size() - 1), config.FwmConfig.INSTANCE.fightKitSelected));
    return ((config.FwmConfig$FightKit)v0.get(v1));
}

private static void have(world.entity.player.Inventory inv) {
    v1 = ?;
    v2 = 0;
    L10:
    if (v2 >= 36) goto L31;
    game.FightKits.count(v1, inv.getItem(v2));
    v2 = v2 + 1;
    goto L10;
    L31:
    game.FightKits.count(v1, inv.getItem(40));
    return v1;
}

private static Object count(Map out, world.item.ItemStack stack) {
    if (stack == null) goto L11;
    if (stack.isEmpty() ==0) goto L12;
    L11:
    return;
    L12:
    out.merge(core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString(), Integer.valueOf(stack.getCount()), /*indy*/apply());
    return;
}

public static void capture(config.FwmConfig$FightKit kit, world.entity.player.Inventory inv) {
    kit.slots.clear();
    v2 = 0;
    L11:
    if (v2 >= 36) goto L33;
    game.FightKits.put(kit, v2, inv.getItem(v2));
    v2 = v2 + 1;
    goto L11;
    L33:
    game.FightKits.put(kit, 40, inv.getItem(40));
    return;
}

private static Object put(config.FwmConfig$FightKit kit, int index, world.item.ItemStack stack) {
    if (stack == null) goto L11;
    if (stack.isEmpty() ==0) goto L12;
    L11:
    return;
    L12:
    v3 = ?;
    v3.item = core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    v3.count = stack.getCount();
    kit.slots.put(Integer.valueOf(index), v3);
    return;
}

public static world.item.ItemStack stack(String id, int count) {
    if (id != null) goto L12;
    goto L13;
    L12:
    L13:
    v2 = ((world.item.ItemStack)"".computeIfAbsent(id, /*indy*/apply()));
    if (v2 != null) goto L54;
    if (id != null) goto L43;
    goto L44;
    L43:
    L44:
    "".remove(id);
    return world.item.ItemStack.EMPTY;
    L54:
    if (v2.isEmpty() ==0) goto L67;
    goto L81;
    L67:
    L81:
    return v2.copyWithCount(Math.max(1, Math.min(99, count)));
}

public static void register() {
    fabric.api.client.screen.v1.ScreenEvents.AFTER_INIT.register(/*indy*/afterInit());
    return;
}

private static config.FwmConfig$FightKit active() {
    if (config.Features.isOn("fwm.tab.fightkit") ==0) goto L18;
    if (game.FightKits.selected() == null) goto L18;
    goto L19;
    L18:
    L19:
    return 0;
}

private static void click(double x, double a1, int y) {
    if (game.FightKits.active() ==0) goto L11;
    if (v4 ==0) goto L13;
    L11:
    return 0;
    L13:
    if (game.FightKits.inside(game.FightKits.toggleBox, x, y) ==0) goto L49;
    if (config.FwmConfig.INSTANCE.fightKitShowInInventory !=0) goto L40;
    goto L41;
    L40:
    L41:
    1.fightKitShowInInventory = 0;
    config.ConfigManager.save();
    return 1;
    L49:
    if (game.FightKits.inside(game.FightKits.kitBox, x, y) ==0) goto L95;
    v5 = config.FwmConfig.INSTANCE.fightKits;
    config.FwmConfig.INSTANCE.fightKitSelected = ((config.FwmConfig.INSTANCE.fightKitSelected + 1) % v5.size());
    config.ConfigManager.save();
    return 1;
    L95:
    return 0;
}

private static boolean inside(int[] box, double x, double a2) {
    if (box == null) goto L52;
    if (cmp(x,((d)box[0])) <0) goto L52;
    if (cmp(x,((d)(box[0] + box[2]))) >=0) goto L52;
    if (cmp(v3,((d)box[1])) <0) goto L52;
    if (cmp(v3,((d)(box[1] + box[3]))) >=0) goto L52;
    goto L53;
    L52:
    L53:
    return 0;
}

private static void render(client.Minecraft client, client.gui.screens.inventory.InventoryScreen screen, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY) {
    game.FightKits.toggleBox = null;
    game.FightKits.kitBox = null;
    if (game.FightKits.active() ==0) goto L21;
    if (client.player != null) goto L22;
    L21:
    return;
    L22:
    v5 = client.font;
    v6 = ((mixin.HandledScreenAccessor)screen);
    v7 = game.FightKits.selected();
    v8 = v6.fwm$x();
    v9 = v6.fwm$y();
    v10 = client.player.getActiveEffects().size();
    if (v10 !=0) goto L80;
    goto L97;
    L80:
    if (v10 <= 5) goto L92;
    goto L97;
    L92:
    L97:
    v11 = (v10 * 33);
    v12 = ((v8 + 176) + 4);
    v13 = Math.max(90, Math.min(150, ((screen.width - v12) - 4)));
    if (v10 <=0) goto L145;
    goto L146;
    L145:
    L146:
    v14 = (4 + 0);
    v15 = v12;
    v16 = (v14 + 34);
    v17 = v13;
    v18 = config.FwmConfig.INSTANCE.fightKitShowInInventory;
    if (v18 ==0) goto L183;
    goto L186;
    L183:
    L186:
    v19 = ui.Lang.str("fwm.fightkit.show", new Object[0]);
    game.FightKits.toggleBox = game.FightKits.button(ctx, v5, v12, v14, v13, v5.plainSubstrByWidth(v19, (v13 - 6)), mouseX, mouseY, 1);
    v20 = v12;
    v21 = (v14 + 16);
    if (v7.name != null) goto L253;
    goto L258;
    L253:
    L258:
    v22 = '< ' + "Kit".plainSubstrByWidth(v7.name, (v13 - 24)) + ' >';
    if (config.FwmConfig.INSTANCE.fightKits.size() <= 1) goto L306;
    goto L307;
    L306:
    L307:
    game.FightKits.kitBox = game.FightKits.button(v5, v20, v21, v13, v22, mouseX, mouseY, 1, 0);
    if (v18 !=0) goto L319;
    return;
    L319:
    v23 = client.player.getInventory();
    v24 = game.FightKits.missing(v7.slots, game.FightKits.have(v23));
    if (v17 <=0) goto L565;
    v25 = v16;
    if (v24.isEmpty() ==0) goto L388;
    ctx.text(v5, ui.Lang.str("fwm.fightkit.complete", new Object[0]), v15, v25, -10688364);
    goto L565;
    L388:
    ctx.text(v5, ui.Lang.str("fwm.fightkit.missing", new Object[0]), v15, v25, -1017230);
    v25 = v25 + 11;
    v26 = v24.iterator();
    L423:
    if (v26.hasNext() ==0) goto L565;
    v27 = ((game.FightKits$Missing)v26.next());
    v28 = game.FightKits.stack(v27.item(), 1);
    if (v28.isEmpty() !=0) goto L476;
    ctx.item(v28, v15, (v25 - 2));
    L476:
    if (v28.isEmpty() ==0) goto L492;
    goto L502;
    L492:
    L502:
    v29 = v28.getHoverName().getString();
    ctx.text(v5, v5.plainSubstrByWidth(v27.count() + 'x ' + v29, (v17 - 18)), (v15 + 18), (v25 + 2), ui.FwmTheme.TEXT);
    v25 = v25 + 17;
    if (v25 <= (screen.height - 12)) goto L562;
    goto L565;
    L562:
    goto L423;
    L565:
    v25 = ((world.inventory.InventoryMenu)screen.getMenu()).slots.iterator();
    L580:
    if (v25.hasNext() ==0) goto L858;
    v26 = ((world.inventory.Slot)v25.next());
    if ((v26.container instanceof world.entity.player.Inventory) !=0) goto L616;
    goto L580;
    L616:
    v27 = v26.getContainerSlot();
    v28 = ((config.FwmConfig$KitSlot)v7.slots.get(Integer.valueOf(v27)));
    if (v28 == null) goto L580;
    if (v28.item == null) goto L580;
    if (game.FightKits.kitSlot(v27) !=0) goto L667;
    goto L580;
    L667:
    v29 = v23.getItem(v27);
    if (v29.isEmpty() !=0) goto L721;
    if (game.FightKits.kind(core.registries.BuiltInRegistries.ITEM.getKey(v29.getItem()).toString()).equals(game.FightKits.kind(v28.item)) ==0) goto L721;
    goto L722;
    L721:
    L722:
    v30 = 0;
    if (v30 ==0) goto L732;
    goto L580;
    L732:
    v31 = (v6.fwm$x() + v26.x);
    v32 = (v6.fwm$y() + v26.y);
    v33 = -1750963;
    ctx.fill((v31 - 1), (v32 - 1), (v31 + 17), v32, v33);
    ctx.fill((v31 - 1), (v32 + 16), (v31 + 17), (v32 + 17), v33);
    ctx.fill((v31 - 1), v32, v31, (v32 + 16), v33);
    ctx.fill((v31 + 16), v32, (v31 + 17), (v32 + 16), v33);
    goto L580;
    L858:
    return;
}

private static void button(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int x, int y, int w, String label, int mouseX, int mouseY, boolean enabled) {
    if (enabled ==0) goto L39;
    if (mouseX < x) goto L39;
    if (mouseX >= (x + w)) goto L39;
    if (mouseY < y) goto L39;
    if (mouseY >= (y + 14)) goto L39;
    goto L40;
    L39:
    L40:
    v9 = 0;
    if (v9 ==0) goto L64;
    goto L67;
    L64:
    L67:
    x.fill(y, (x + w), (y + 14), -534754765, -804250602);
    if (v9 ==0) goto L81;
    goto L84;
    L81:
    L84:
    v10 = -13739454;
    ctx.fill(x, y, (x + w), (y + 1), v10);
    ctx.fill(x, (y + 13), (x + w), (y + 14), v10);
    ctx.fill(x, y, (x + 1), (y + 14), v10);
    ctx.fill(((x + w) - 1), y, (x + w), (y + 14), v10);
    if (enabled ==0) goto L187;
    goto L190;
    L187:
    L190:
    font.text(label, (x + ((w - font.width(label)) / 2)), (y + 3), ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM);
    new int[4][0] = x;
    new int[4][1] = y;
    new int[4][2] = w;
    new int[4][3] = 14;
    return new int[4];
}

private static void lambda$register$0(client.Minecraft client, client.gui.screens.Screen screen, int width, int height) {
    if ((screen instanceof client.gui.screens.inventory.InventoryScreen) ==0) goto L16;
    v4 = ((client.gui.screens.inventory.InventoryScreen)screen);
    goto L17;
    L16:
    return;
    L17:
    fabric.api.client.screen.v1.ScreenEvents.afterExtract(screen).register(/*indy*/afterExtract(client, v4));
    fabric.api.client.screen.v1.ScreenMouseEvents.allowMouseClick(screen).register(/*indy*/allowMouseClick());
    return;
}

private static boolean lambda$register$2(client.gui.screens.Screen s, client.input.MouseButtonEvent click) {
    if (game.FightKits.click(click.x(), click.y(), click.button()) !=0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

private static void lambda$register$1(client.Minecraft client, client.gui.screens.inventory.InventoryScreen inventory, client.gui.screens.Screen s, client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    game.FightKits.render(client, inventory, ctx, mouseX, mouseY);
    return;
}

private static void lambda$stack$0(String key) {
    v1 = resources.Identifier.tryParse(key);
    if (v1 == null) goto L21;
    if (core.registries.BuiltInRegistries.ITEM.containsKey(v1) !=0) goto L25;
    L21:
    return world.item.ItemStack.EMPTY;
    L25:
    v2 = ((world.item.Item)core.registries.BuiltInRegistries.ITEM.getValue(v1));
    return ?;
    v1 = /*catch Exception*/ ex;
    return null;
}

private static boolean lambda$missing$2(Map haveByKind, List out, Map sample, String k, Integer n) {
    v5 = (n.intValue() - ((Integer)haveByKind.getOrDefault(k, Integer.valueOf(0))).intValue());
    if (v5 <=0) goto L56;
    out.add(?);
    L56:
    return;
}

private static Object lambda$missing$1(Map need, Map sample, Map$Entry e) {
    v3 = ((config.FwmConfig$KitSlot)e.getValue());
    if (v3 == null) goto L31;
    if (v3.item == null) goto L31;
    if (v3.item.isBlank() ==0) goto L32;
    L31:
    return;
    L32:
    v4 = game.FightKits.kind(v3.item);
    need.merge(v4, Integer.valueOf(Math.max(1, v3.count)), /*indy*/apply());
    sample.putIfAbsent(v4, v3.item);
    return;
}

private static Object lambda$missing$0(Map haveByKind, String id, Integer n) {
    haveByKind.merge(game.FightKits.kind(id), n, /*indy*/apply());
    return;
}

static void <clinit>() {
    game.FightKits.TIERED = regex.Pattern.compile("^(netherite|diamond|iron|golden|stone|wooden|copper|leather|chainmail)_(sword|axe|pickaxe|shovel|hoe|spear|helmet|chestplate|leggings|boots)$");
    game.FightKits.STACKS = ?;
    return;
}

