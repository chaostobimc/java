// class net/friendswithmoney/fwm/game/ToolGuard  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field blockedAt J;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.event.player.UseBlockCallback.EVENT.register(/*indy*/interact());
    fabric.api.event.client.player.ClientPreAttackCallback.EVENT.register(/*indy*/onClientPlayerPreAttack());
    return;
}

public static long sinceBlocked() {
    if (cmp(game.ToolGuard.blockedAt,0) !=0) goto L14;
    goto L21;
    L14:
    L21:
    return (System.currentTimeMillis() - game.ToolGuard.blockedAt);
}

public static boolean warningStack(client.Minecraft client) {
    if (config.Features.isOn("fwm.tab.toolguard") ==0) goto L15;
    if (client.player != null) goto L17;
    L15:
    return null;
    L17:
    v1 = client.player.getMainHandItem();
    if (game.ToolGuard.worn(v1) ==0) goto L36;
    goto L37;
    L36:
    L37:
    return null;
}

public static float remaining(world.item.ItemStack stack) {
    v1 = stack.getMaxDamage();
    if (v1 >0) goto L11;
    return 1;
    L11:
    return Math.max(0, (((f)(v1 - stack.getDamageValue())) / ((f)v1)));
}

public static int usesLeft(world.item.ItemStack stack) {
    return Math.max(0, (stack.getMaxDamage() - stack.getDamageValue()));
}

private static boolean blocksUse(world.entity.player.Player player, world.level.Level world, world.phys.BlockHitResult hit) {
    if (config.Features.isOn("fwm.tab.toolguard") !=0) goto L10;
    return 0;
    L10:
    if (config.FwmConfig.INSTANCE.toolGuardBlock !=0) goto L21;
    return 0;
    L21:
    if (player == null) goto L29;
    if (world != null) goto L31;
    L29:
    return 0;
    L31:
    v3 = client.Minecraft.getInstance();
    if (v3.gameMode != null) goto L44;
    return 0;
    L44:
    if (v3.gameMode.getPlayerMode().isCreative() ==0) goto L59;
    return 0;
    L59:
    v4 = player.getMainHandItem();
    if (game.ToolGuard.worn(v4) !=0) goto L75;
    return 0;
    L75:
    return game.ToolGuard.wears(v4, world.getBlockState(hit.getBlockPos()));
}

private static boolean wears(world.item.ItemStack stack, world.level.block.state.BlockState state) {
    if (stack.is(tags.ItemTags.HOES) ==0) goto L56;
    if (state.is(tags.BlockTags.DIRT) !=0) goto L50;
    if (state.is(tags.BlockTags.CROPS) !=0) goto L50;
    if (state.is(world.level.block.Blocks.GRASS_BLOCK) !=0) goto L50;
    if (state.is(world.level.block.Blocks.DIRT_PATH) ==0) goto L54;
    L50:
    goto L55;
    L54:
    L55:
    return 0;
    L56:
    if (stack.is(tags.ItemTags.SHOVELS) ==0) goto L102;
    if (state.is(tags.BlockTags.DIRT) !=0) goto L96;
    if (state.is(world.level.block.Blocks.GRASS_BLOCK) !=0) goto L96;
    if (state.is(world.level.block.Blocks.CAMPFIRE) ==0) goto L100;
    L96:
    goto L101;
    L100:
    L101:
    return 0;
    L102:
    if (stack.is(tags.ItemTags.AXES) ==0) goto L158;
    if (state.is(tags.BlockTags.LOGS) !=0) goto L152;
    if (state.is(tags.BlockTags.PLANKS) !=0) goto L152;
    if (state.is(tags.BlockTags.WOODEN_SLABS) !=0) goto L152;
    if (state.is(tags.BlockTags.WOODEN_STAIRS) ==0) goto L156;
    L152:
    goto L157;
    L156:
    L157:
    return 0;
    L158:
    return 0;
}

private static boolean blocks(client.Minecraft client, client.player.LocalPlayer player) {
    if (config.Features.isOn("fwm.tab.toolguard") !=0) goto L10;
    return 0;
    L10:
    if (config.FwmConfig.INSTANCE.toolGuardBlock !=0) goto L21;
    return 0;
    L21:
    if (client.level == null) goto L35;
    if (client.gameMode != null) goto L37;
    L35:
    return 0;
    L37:
    if (client.gameMode.getPlayerMode().isCreative() ==0) goto L52;
    return 0;
    L52:
    if (game.ToolGuard.worn(player.getMainHandItem()) !=0) goto L64;
    return 0;
    L64:
    v2 = client.hitResult;
    if ((v2 instanceof world.phys.BlockHitResult) ==0) goto L84;
    v3 = ((world.phys.BlockHitResult)v2);
    goto L86;
    L84:
    return 0;
    L86:
    if (v2.getType() == world.phys.HitResult$Type.BLOCK) goto L98;
    return 0;
    L98:
    return game.ToolGuard.costsDurability(client, v3.getBlockPos());
}

private static float costsDurability(client.Minecraft client, core.BlockPos pos) {
    if (cmp(client.level.getBlockState(pos).getDestroySpeed(client.level, pos),0) ==0) goto L25;
    goto L26;
    L25:
    L26:
    return 0;
    v2 = /*catch Exception*/ ex;
    return 1;
}

private static float worn(world.item.ItemStack stack) {
    if (stack == null) goto L18;
    if (stack.isEmpty() !=0) goto L18;
    if (stack.isDamageableItem() !=0) goto L20;
    L18:
    return 0;
    L20:
    if (cmp((game.ToolGuard.remaining(stack) * 100.0),((f)config.FwmConfig.INSTANCE.toolGuardPercent)) >0) goto L42;
    goto L43;
    L42:
    L43:
    return 0;
}

private static long lambda$register$1(client.Minecraft client, client.player.LocalPlayer player, int clickCount) {
    if (game.ToolGuard.blocks(client, player) !=0) goto L10;
    return 0;
    L10:
    game.ToolGuard.blockedAt = System.currentTimeMillis();
    return 1;
}

private static long lambda$register$0(world.entity.player.Player player, world.level.Level world, world.InteractionHand hand, world.phys.BlockHitResult hit) {
    if (hand == world.InteractionHand.MAIN_HAND) goto L11;
    return world.InteractionResult.PASS;
    L11:
    if (game.ToolGuard.blocksUse(player, world, hit) !=0) goto L24;
    return world.InteractionResult.PASS;
    L24:
    game.ToolGuard.blockedAt = System.currentTimeMillis();
    return world.InteractionResult.FAIL;
}

