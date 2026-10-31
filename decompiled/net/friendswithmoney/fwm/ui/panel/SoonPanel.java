// class net/friendswithmoney/fwm/ui/panel/SoonPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.panel.soon", new Object[0]);
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    v6 = ui.Lang.of("fwm.panel.soonHint", new Object[0]);
    Objects.requireNonNull(font);
    ctx.text(font, v6, (this.x + ((this.width - font.width(v6)) / 2)), (this.y + ((this.height - 9) / 2)), ui.FwmTheme.TEXT_DIM, 0);
    return;
}

