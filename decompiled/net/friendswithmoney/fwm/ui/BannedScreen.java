// class net/friendswithmoney/fwm/ui/BannedScreen  (classfile vClassVersion(major=69, minor=0))
// extends net/minecraft/client/gui/screens/Screen
public void <init>() {
    return;
}

public void extractRenderState(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    ctx.fill(0, 0, this.width, this.height, -261095424);
    v5 = ui.Lang.t("Du wurdest gesperrt", "You have been banned");
    v6 = ui.Lang.t("und wirst die FWM Mod nicht mehr nutzen k\u00f6nnen.", "and can no longer use the FWM mod.");
    ctx.centeredText(this.font, network.chat.Component.literal(v5), (this.width / 2), ((this.height / 2) - 12), m1);
    ctx.centeredText(this.font, network.chat.Component.literal(v6), (this.width / 2), ((this.height / 2) + 2), -12080);
    return;
}

