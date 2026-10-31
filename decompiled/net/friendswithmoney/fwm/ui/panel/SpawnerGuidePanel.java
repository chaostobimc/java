// class net/friendswithmoney/fwm/ui/panel/SpawnerGuidePanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field IMAGE Lnet/minecraft/resources/Identifier;;
field IMG_W I;
field IMG_H I;
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return network.chat.Component.literal(ui.Lang.t("So baust du es am besten", "How to build it best"));
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = 22;
    v7 = Math.min((((f)this.width) / 512.0), (((f)(this.height - v6)) / 359.0));
    v8 = Math.round((512.0 * v7));
    v9 = Math.round((359.0 * v7));
    v10 = (this.x + ((this.width - v8) / 2));
    ui.FwmGfx.blit(ctx, ui.panel.SpawnerGuidePanel.IMAGE, v10, this.y, v8, v9, 512, 359, 512, 359);
    v11 = ui.Lang.t("Spawner in Bedrock einbauen, daneben ein Loch, in dem du stehst.", "Build the spawner into bedrock, with a hole next to it to stand in.");
    v12 = ui.Lang.t("Nichts zwischen dir und dem Spawner \u2013 sonst \u00f6ffnet das Makro ihn nicht.", "Nothing between you and the spawner \u2013 or the macro will not open it.");
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v11, this.width)), (this.x + ((this.width - Math.min(this.width, font.width(v11))) / 2)), ((this.y + v9) + 3), ui.FwmTheme.TEXT, 0);
    ctx.text(font, network.chat.Component.literal(font.plainSubstrByWidth(v12, this.width)), (this.x + ((this.width - Math.min(this.width, font.width(v12))) / 2)), ((this.y + v9) + 13), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

static resources.Identifier <clinit>() {
    ui.panel.SpawnerGuidePanel.IMAGE = resources.Identifier.fromNamespaceAndPath("fwm", "textures/gui/spawner_guide.png");
    return;
}

