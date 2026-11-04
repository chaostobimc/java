/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Exception
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.System
 *  net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback
 *  net.fabricmc.fabric.api.event.player.UseBlockCallback
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.player.LocalPlayer
 *  net.minecraft.core.BlockPos
 *  net.minecraft.tags.BlockTags
 *  net.minecraft.tags.ItemTags
 *  net.minecraft.world.InteractionHand
 *  net.minecraft.world.InteractionResult
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.item.ItemStack
 *  net.minecraft.world.level.BlockGetter
 *  net.minecraft.world.level.Level
 *  net.minecraft.world.level.block.Blocks
 *  net.minecraft.world.level.block.state.BlockState
 *  net.minecraft.world.phys.BlockHitResult
 *  net.minecraft.world.phys.HitResult
 *  net.minecraft.world.phys.HitResult$Type
 */
package net.friendswithmoney.fwm.game;

import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.friendswithmoney.fwm.config.Features;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

public final class ToolGuard {
    private static long blockedAt;

    private ToolGuard() {
    }

    public static void register() {
        UseBlockCallback.EVENT.register((player, world, hand, hit) -> {
            if (hand != InteractionHand.MAIN_HAND) {
                return InteractionResult.PASS;
            }
            if (!ToolGuard.blocksUse(player, world, hit)) {
                return InteractionResult.PASS;
            }
            blockedAt = System.currentTimeMillis();
            return InteractionResult.FAIL;
        });
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            if (!ToolGuard.blocks(client, player)) {
                return false;
            }
            blockedAt = System.currentTimeMillis();
            return true;
        });
    }

    public static long sinceBlocked() {
        return blockedAt == 0L ? -1L : System.currentTimeMillis() - blockedAt;
    }

    public static ItemStack warningStack(Minecraft client) {
        if (!Features.isOn("fwm.tab.toolguard") || client.player == null) {
            return null;
        }
        ItemStack stack = client.player.getMainHandItem();
        return ToolGuard.worn(stack) ? stack : null;
    }

    public static float remaining(ItemStack stack) {
        int max = stack.getMaxDamage();
        if (max <= 0) {
            return 1.0f;
        }
        return Math.max((float)0.0f, (float)((float)(max - stack.getDamageValue()) / (float)max));
    }

    public static int usesLeft(ItemStack stack) {
        return Math.max((int)0, (int)(stack.getMaxDamage() - stack.getDamageValue()));
    }

    private static boolean blocksUse(Player player, Level world, BlockHitResult hit) {
        if (!Features.isOn("fwm.tab.toolguard")) {
            return false;
        }
        if (!FwmConfig.INSTANCE.toolGuardBlock) {
            return false;
        }
        if (player == null || world == null) {
            return false;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.gameMode == null) {
            return false;
        }
        if (client.gameMode.getPlayerMode().isCreative()) {
            return false;
        }
        ItemStack stack = player.getMainHandItem();
        if (!ToolGuard.worn(stack)) {
            return false;
        }
        return ToolGuard.wears(stack, world.getBlockState(hit.getBlockPos()));
    }

    private static boolean wears(ItemStack stack, BlockState state) {
        if (stack.is(ItemTags.HOES)) {
            return state.is(BlockTags.DIRT) || state.is(BlockTags.CROPS) || state.is((Object)Blocks.GRASS_BLOCK) || state.is((Object)Blocks.DIRT_PATH);
        }
        if (stack.is(ItemTags.SHOVELS)) {
            return state.is(BlockTags.DIRT) || state.is((Object)Blocks.GRASS_BLOCK) || state.is((Object)Blocks.CAMPFIRE);
        }
        if (stack.is(ItemTags.AXES)) {
            return state.is(BlockTags.LOGS) || state.is(BlockTags.PLANKS) || state.is(BlockTags.WOODEN_SLABS) || state.is(BlockTags.WOODEN_STAIRS);
        }
        return false;
    }

    private static boolean blocks(Minecraft client, LocalPlayer player) {
        if (!Features.isOn("fwm.tab.toolguard")) {
            return false;
        }
        if (!FwmConfig.INSTANCE.toolGuardBlock) {
            return false;
        }
        if (client.level == null || client.gameMode == null) {
            return false;
        }
        if (client.gameMode.getPlayerMode().isCreative()) {
            return false;
        }
        if (!ToolGuard.worn(player.getMainHandItem())) {
            return false;
        }
        HitResult target = client.hitResult;
        if (!(target instanceof BlockHitResult)) {
            return false;
        }
        BlockHitResult hit = (BlockHitResult)target;
        if (target.getType() != HitResult.Type.BLOCK) {
            return false;
        }
        return ToolGuard.costsDurability(client, hit.getBlockPos());
    }

    private static boolean costsDurability(Minecraft client, BlockPos pos) {
        try {
            return client.level.getBlockState(pos).getDestroySpeed((BlockGetter)client.level, pos) != 0.0f;
        }
        catch (Exception e) {
            return true;
        }
    }

    private static boolean worn(ItemStack stack) {
        if (stack == null || stack.isEmpty() || !stack.isDamageableItem()) {
            return false;
        }
        return ToolGuard.remaining(stack) * 100.0f <= (float)FwmConfig.INSTANCE.toolGuardPercent;
    }
}
