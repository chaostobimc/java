// class net/friendswithmoney/fwm/ui/panel/OverlayPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.overlay", new Object[0]);
}

protected boolean buildRows() {
    this.rows.add(?);
    this.rows.add(?);
    return;
}

private static void lambda$buildRows$1() {
    v0 = client.Minecraft.getInstance();
    v0.gui.setScreen(?);
    return;
}

private static network.chat.MutableComponent lambda$buildRows$0() {
    return ui.Lang.of("fwm.option.hudEditOpen", new Object[0]);
}

