// class net/friendswithmoney/fwm/ui/panel/SidebarPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
field keys Ljava/util/List;;
public void <init>(List keys) {
    this.keys = keys;
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.sidebar.title", new Object[0]);
}

protected boolean buildRows() {
    this.rows.add(?);
    v1 = config.FwmConfig.INSTANCE;
    v2 = this.keys.iterator();
    L41:
    if (v2.hasNext() ==0) goto L102;
    v3 = ((String)v2.next());
    this.rows.add(?);
    goto L41;
    L102:
    return;
}

private static void lambda$buildRows$1(config.FwmConfig config, String key, Boolean value) {
    if (value.booleanValue() ==0) goto L21;
    config.sidebarHidden.remove(key);
    goto L45;
    L21:
    if (config.sidebarHidden.contains(key) !=0) goto L45;
    config.sidebarHidden.add(key);
    L45:
    config.ConfigManager.save();
    return;
}

private static boolean lambda$buildRows$0(config.FwmConfig config, String key) {
    if (config.sidebarHidden.contains(key) !=0) goto L17;
    goto L18;
    L17:
    L18:
    return 0;
}

