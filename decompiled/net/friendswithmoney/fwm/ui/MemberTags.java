/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.blaze3d.vertex.PoseStack
 *  com.mojang.math.Transformation
 *  java.lang.CharSequence
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.StringBuilder
 *  java.lang.System
 *  java.util.HashMap
 *  java.util.Map
 *  java.util.UUID
 *  java.util.regex.Matcher
 *  java.util.regex.Pattern
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext
 *  net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents
 *  net.minecraft.ChatFormatting
 *  net.minecraft.client.Camera
 *  net.minecraft.client.Minecraft
 *  net.minecraft.client.multiplayer.ClientLevel
 *  net.minecraft.client.multiplayer.ClientPacketListener
 *  net.minecraft.client.multiplayer.PlayerInfo
 *  net.minecraft.client.player.AbstractClientPlayer
 *  net.minecraft.client.renderer.SubmitNodeCollector
 *  net.minecraft.client.renderer.state.level.CameraRenderState
 *  net.minecraft.network.chat.Component
 *  net.minecraft.network.chat.FontDescription
 *  net.minecraft.network.chat.FontDescription$Resource
 *  net.minecraft.network.chat.MutableComponent
 *  net.minecraft.network.chat.Style
 *  net.minecraft.resources.Identifier
 *  net.minecraft.world.entity.Display
 *  net.minecraft.world.entity.Display$RenderState
 *  net.minecraft.world.entity.Display$TextDisplay
 *  net.minecraft.world.entity.Entity
 *  net.minecraft.world.entity.EntityAttachment
 *  net.minecraft.world.entity.decoration.ArmorStand
 *  net.minecraft.world.entity.player.Player
 *  net.minecraft.world.phys.AABB
 *  net.minecraft.world.phys.Vec3
 *  net.minecraft.world.scores.DisplaySlot
 */
package net.friendswithmoney.fwm.ui;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Transformation;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.level.LevelRenderEvents;
import net.friendswithmoney.fwm.chat.FakeTags;
import net.friendswithmoney.fwm.chat.ServerLine;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.net.ClanTags;
import net.friendswithmoney.fwm.net.FakeList;
import net.friendswithmoney.fwm.net.MemberData;
import net.friendswithmoney.fwm.net.Members;
import net.friendswithmoney.fwm.net.TrustedClans;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.NoRisk;
import net.friendswithmoney.fwm.ui.RankColors;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.state.level.CameraRenderState;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FontDescription;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Display;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.scores.DisplaySlot;

public final class MemberTags {
    private static final FontDescription BADGE_FONT = new FontDescription.Resource(Identifier.fromNamespaceAndPath((String)"fwm", (String)"badge"));
    private static final String LOGO_GLYPH = "\ue000";
    private static final Vec3 ANCHOR = new Vec3(0.0, -0.5, 0.0);
    private static final double NAME_LIFT = 0.5;
    private static final double LINE_H = 0.3;
    private static final int FULL_BRIGHT = 0xF000F0;
    private static final double EXTRA_LINE = 0.25;
    private static final double HEIGHT = 0.6;
    private static final float SCALE = 0.75f;
    private static final double BELOW_NAME_LINE = 0.25875;
    private static final double BELOW_NAME_RANGE_SQ = 100.0;
    private static final Map<UUID, Stack> STACKS = new HashMap();
    private static final long RESCAN_MS = 150L;
    private static final Map<UUID, Warn> WARNS = new HashMap();
    private static final long WARN_MS = 1000L;
    private static final Pattern BRACKET_TAG = Pattern.compile((String)"\\[([^\\[\\]\\s]{1,8})\\]");

    private static MutableComponent logo() {
        return Component.literal((String)" ").append((Component)Component.literal((String)LOGO_GLYPH).setStyle(Style.EMPTY.withFont(BADGE_FONT).withColor(ChatFormatting.WHITE)));
    }

    private MemberTags() {
    }

    public static double belowNameLift(ClientLevel world, double squaredDistance) {
        if (world == null || squaredDistance >= 100.0) {
            return 0.0;
        }
        return world.getScoreboard().getDisplayObjective(DisplaySlot.BELOW_NAME) != null ? 0.25875 : 0.0;
    }

    private static double stackTop(ClientLevel world, AbstractClientPlayer player, double nameTop) {
        long now = System.currentTimeMillis();
        boolean sneaking = player.isDiscrete();
        Stack cached = (Stack)((Object)STACKS.get((Object)player.getUUID()));
        if (cached != null && cached.sneaking() == sneaking && now - cached.tick() < 150L) {
            return cached.top();
        }
        double top = nameTop;
        for (Entity passenger : player.getIndirectPassengers()) {
            if (!MemberTags.isLabel(passenger)) continue;
            top = Math.max((double)top, (double)MemberTags.labelTop(passenger, player));
        }
        AABB column = new AABB(player.getX() - 0.5, player.getY() - 0.3, player.getZ() - 0.5, player.getX() + 0.5, player.getY() + nameTop + 3.0, player.getZ() + 0.5);
        for (Entity entity : world.getEntities((Entity)player, column, MemberTags::isLabel)) {
            top = Math.max((double)top, (double)MemberTags.labelTop(entity, player));
        }
        if (STACKS.size() > 200) {
            STACKS.clear();
        }
        STACKS.put((Object)player.getUUID(), (Object)new Stack(top, now, sneaking));
        return top;
    }

    private static double labelTop(Entity entity, AbstractClientPlayer player) {
        double base = entity.getY() - player.getY();
        if (entity instanceof Display) {
            Display display = (Display)entity;
            Display.RenderState state = display.renderState();
            if (state != null) {
                Transformation transformation = (Transformation)state.transformation().get(1.0f);
                base += (double)transformation.translation().y() + (double)transformation.scale().y() * 0.3;
            }
            return base + 0.3;
        }
        Vec3 attachment = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, 0.0f);
        return base + (attachment != null ? attachment.y : (double)entity.getBbHeight()) + 0.5 + 0.3;
    }

    private static boolean isLabel(Entity entity) {
        ArmorStand stand;
        if (entity instanceof Display.TextDisplay) {
            return true;
        }
        return entity instanceof ArmorStand && ((stand = (ArmorStand)entity).isMarker() || stand.isInvisible()) && stand.hasCustomName();
    }

    public static MutableComponent label(MemberData.Member member) {
        String rank;
        String role = member == null || member.role == null ? "" : member.role;
        String clan = member == null || member.clan == null ? "" : member.clan;
        int bullet = role.indexOf(8226);
        String string = rank = bullet > 0 ? role.substring(0, bullet).trim() : role;
        String tail = !clan.isEmpty() ? clan : (bullet > 0 ? role.substring(bullet + 1).trim() : "");
        boolean leader = Members.rankIndex(rank) == Members.LEADER_RANK;
        String inner = leader && !tail.isEmpty() ? rank + " " + tail : rank;
        MutableComponent text = Component.empty().append((Component)Component.literal((String)"FWM").withStyle(new ChatFormatting[]{ChatFormatting.GREEN, ChatFormatting.BOLD})).append((Component)Component.literal((String)" - ").withStyle(ChatFormatting.WHITE));
        MutableComponent rankText = RankColors.gradient(inner, Members.rankIndex(rank));
        if (rankText.getSiblings().isEmpty()) {
            text.append((Component)rankText);
        } else {
            for (Component part : rankText.getSiblings()) {
                text.append(part);
            }
        }
        return text.append((Component)MemberTags.logo());
    }

    public static MutableComponent warning(AbstractClientPlayer player) {
        TrustedClans.Clan clan;
        if (!MemberTags.wearsFake(player)) {
            return null;
        }
        String copied = ClanTags.fakeRawIn(MemberTags.wornName(player));
        TrustedClans.Clan clan2 = clan = copied == null ? null : TrustedClans.fakeOf(ClanTags.bare(copied));
        if (clan != null && !"fwm".equals((Object)clan.id)) {
            return Component.literal((String)TrustedClans.fakeWarning(clan)).withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD});
        }
        return Lang.of("fwm.faketag.badge", new Object[0]).withStyle(new ChatFormatting[]{ChatFormatting.RED, ChatFormatting.BOLD});
    }

    public static boolean wearsFake(AbstractClientPlayer player) {
        return MemberTags.look(player).fake();
    }

    public static boolean wearsForeignTag(AbstractClientPlayer player) {
        return MemberTags.look(player).foreign();
    }

    public static String wearsMarket(AbstractClientPlayer player) {
        return MemberTags.look(player).market();
    }

    public static MutableComponent marketLabel(String tag) {
        TrustedClans.Clan clan = TrustedClans.match(tag);
        return clan == null ? Component.literal((String)tag) : TrustedClans.label(clan, tag);
    }

    private static Warn look(AbstractClientPlayer player) {
        long now = System.currentTimeMillis();
        Warn cached = (Warn)((Object)WARNS.get((Object)player.getUUID()));
        if (cached != null && now - cached.at() < 1000L) {
            return cached;
        }
        String worn = MemberTags.wornName(player);
        boolean fake = player.getGameProfile() != null && FakeList.isFakePlayer(player.getGameProfile().name()) || FakeTags.fakeIn(worn) != null;
        boolean anyTag = false;
        boolean realTag = false;
        String market = null;
        Matcher tag = BRACKET_TAG.matcher((CharSequence)ServerLine.strip(worn));
        while (tag.find()) {
            anyTag = true;
            if (ClanTags.isReal(tag.group(1))) {
                realTag = true;
            }
            if (market != null || TrustedClans.match(tag.group(1)) == null) continue;
            market = tag.group(1);
        }
        Warn warn = new Warn(fake, anyTag && !realTag, market, now);
        if (WARNS.size() > 200) {
            WARNS.clear();
        }
        WARNS.put((Object)player.getUUID(), (Object)warn);
        return warn;
    }

    private static String wornName(AbstractClientPlayer player) {
        Component shown;
        PlayerInfo entry;
        StringBuilder worn = new StringBuilder();
        ClientPacketListener handler = Minecraft.getInstance().getConnection();
        PlayerInfo playerInfo = entry = handler == null ? null : handler.getPlayerInfo(player.getUUID());
        if (entry != null && entry.getTabListDisplayName() != null) {
            worn.append(entry.getTabListDisplayName().getString()).append(' ');
        }
        if ((shown = player.getDisplayName()) != null) {
            worn.append(shown.getString());
        }
        return worn.toString();
    }

    public static void register() {
        LevelRenderEvents.COLLECT_SUBMITS.register(MemberTags::render);
    }

    private static void render(LevelRenderContext ctx) {
        NoRisk.attach();
        Minecraft client = Minecraft.getInstance();
        ClientLevel world = client.level;
        if (world == null || client.player == null || client.gui.hud.isHidden()) {
            return;
        }
        PoseStack matrices = ctx.poseStack();
        SubmitNodeCollector queue = ctx.submitNodeCollector();
        if (matrices == null || queue == null || ctx.levelState() == null) {
            return;
        }
        CameraRenderState cameraState = ctx.levelState().cameraRenderState;
        Camera camera = client.gameRenderer.mainCamera();
        if (cameraState == null || camera == null) {
            return;
        }
        FwmConfig config = FwmConfig.INSTANCE;
        double range = Math.max((double)1.0, (double)config.memberTagRange);
        double maxSquared = range * range;
        float tickDelta = client.getDeltaTracker().getGameTimeDeltaPartialTick(false);
        Vec3 cam = camera.position();
        for (AbstractClientPlayer player : world.players()) {
            String market;
            if (player.getGameProfile() == null) continue;
            MemberData.Member member = Members.member(player.getGameProfile().name());
            MutableComponent label = MemberTags.warning(player);
            if (label == null && member != null && !MemberTags.wearsForeignTag(player)) {
                label = MemberTags.label(member);
            }
            if (label == null && member == null && (market = MemberTags.wearsMarket(player)) != null) {
                label = MemberTags.marketLabel(market);
            }
            MutableComponent extra = TrustedClans.playerLabel(player.getUUID());
            if (label == null) {
                label = extra;
                extra = null;
            }
            if (label == null || (player != client.player ? player.isInvisibleTo((Player)client.player) : client.options.getCameraType().isFirstPerson()) || NoRisk.handled(player.getUUID())) continue;
            Vec3 pos = player.getPosition(tickDelta);
            double dx = pos.x - cam.x;
            double dy = pos.y - cam.y;
            double dz = pos.z - cam.z;
            double squared = dx * dx + dy * dy + dz * dz;
            if (squared > maxSquared) continue;
            Vec3 nameAt = player.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, player.getViewYRot(tickDelta));
            double nameY = nameAt != null ? nameAt.y : (double)player.getBbHeight() + 0.5;
            double nameTop = nameY + 0.5 + MemberTags.belowNameLift(world, squared);
            double badgeY = MemberTags.stackTop(world, player, nameTop) + NoRisk.extraLift() + 0.6;
            matrices.pushPose();
            matrices.translate(dx, dy + badgeY, dz);
            matrices.scale(0.75f, 0.75f, 0.75f);
            queue.submitNameTag(matrices, ANCHOR, 0, (Component)label, !player.isDiscrete(), 0xF000F0, cameraState);
            matrices.popPose();
            if (extra == null) continue;
            matrices.pushPose();
            matrices.translate(dx, dy + badgeY + 0.25, dz);
            matrices.scale(0.75f, 0.75f, 0.75f);
            queue.submitNameTag(matrices, ANCHOR, 0, (Component)extra, !player.isDiscrete(), 0xF000F0, cameraState);
            matrices.popPose();
        }
    }

    private record Stack(double top, long tick, boolean sneaking) {
    }

    private record Warn(boolean fake, boolean foreign, String market, long at) {
    }
}
