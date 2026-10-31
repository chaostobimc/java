// class net/friendswithmoney/fwm/ui/FwmGfx  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field PROFILES Ljava/util/Map;;
field NAME Ljava/util/regex/Pattern;;
private void <init>() {
    return;
}

public static void blit(client.gui.GuiGraphicsExtractor ctx, resources.Identifier texture, int x, int y, int drawWidth, int drawHeight, int regionWidth, int regionHeight, int sheetW, int sheetH) {
    ctx.blit(client.renderer.RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, drawWidth, drawHeight, regionWidth, regionHeight, sheetW, sheetH);
    return;
}

public static void playerHead(client.gui.GuiGraphicsExtractor ctx, int x, int y, int size) {
    client.gui.components.PlayerFaceExtractor.extractRenderState(ctx, ui.FwmGfx.skin(), x, y, size);
    return;
}

public static void playerHead(client.gui.GuiGraphicsExtractor ctx, String name, int x, int y, int size) {
    client.gui.components.PlayerFaceExtractor.extractRenderState(ctx, ui.FwmGfx.skinOf(name), x, y, size);
    return;
}

private static world.entity.player.PlayerSkin skinOf(String name) {
    if (name == null) goto L17;
    if (ui.FwmGfx.NAME.matcher(name).matches() !=0) goto L21;
    L17:
    return client.resources.DefaultPlayerSkin.getDefaultSkin();
    L21:
    v1 = ((world.item.component.ResolvableProfile)ui.FwmGfx.PROFILES.computeIfAbsent(name.toLowerCase(Locale.ROOT), /*indy*/apply(name)));
    return ui.FwmGfx.cached(v1, client.resources.DefaultPlayerSkin.getDefaultSkin());
}

private static world.entity.player.PlayerSkin skin() {
    v0 = ui.FwmGfx.playerUuid();
    if (v0 != null) goto L12;
    return client.resources.DefaultPlayerSkin.getDefaultSkin();
    L12:
    v1 = ((world.item.component.ResolvableProfile)ui.FwmGfx.PROFILES.computeIfAbsent(v0.toString(), /*indy*/apply(v0)));
    return ui.FwmGfx.cached(v1, client.resources.DefaultPlayerSkin.get(v0));
}

private static world.entity.player.PlayerSkin cached(world.item.component.ResolvableProfile profile, world.entity.player.PlayerSkin fallback) {
    v2 = client.Minecraft.getInstance().playerSkinRenderCache().getOrDefault(profile).playerSkin();
    if (v2 != null) goto L22;
    goto L23;
    L22:
    L23:
    return v2;
    v2 = /*catch RuntimeException*/ ex;
    return fallback;
}

private static UUID playerUuid() {
    v0 = client.Minecraft.getInstance().getGameProfile();
    if (v0 != null) goto L15;
    goto L19;
    L15:
    L19:
    return v0.id();
}

public static String playerName() {
    v0 = client.Minecraft.getInstance().getGameProfile();
    if (v0 != null) goto L15;
    goto L19;
    L15:
    L19:
    v1 = v0.name();
    if (v1 != null) goto L29;
    goto L30;
    L29:
    L30:
    return v1;
}

public static void blit(client.gui.GuiGraphicsExtractor ctx, resources.Identifier texture, int x, int y, int drawWidth, int drawHeight, int regionWidth, int regionHeight, int sheetSize) {
    ctx.blit(client.renderer.RenderPipelines.GUI_TEXTURED, texture, x, y, 0, 0, drawWidth, drawHeight, regionWidth, regionHeight, sheetSize, sheetSize);
    return;
}

private static world.item.component.ResolvableProfile lambda$skin$0(UUID uuid, String key) {
    return world.item.component.ResolvableProfile.createUnresolved(uuid);
}

private static world.item.component.ResolvableProfile lambda$skinOf$0(String name, String key) {
    return world.item.component.ResolvableProfile.createUnresolved(name);
}

static regex.Pattern <clinit>() {
    ui.FwmGfx.PROFILES = ?;
    ui.FwmGfx.NAME = regex.Pattern.compile("[A-Za-z0-9_]{1,16}");
    return;
}

