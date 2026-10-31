// class net/friendswithmoney/fwm/ui/panel/FeaturesPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/RowsPanel
public void <init>() {
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.tab.features", new Object[0]);
}

protected boolean buildRows() {
    v1 = config.Features.SWITCHABLE.iterator();
    L9:
    if (v1.hasNext() ==0) goto L78;
    v2 = ((String)v1.next());
    if (config.Features.canSwitch(v2) !=0) goto L38;
    goto L9;
    L38:
    this.rows.add(?);
    goto L9;
    L78:
    return;
}

private static void lambda$buildRows$1(String key, Boolean value) {
    config.Features.set(key, value.booleanValue());
    return;
}

private static boolean lambda$buildRows$0(String key) {
    return config.Features.isOn(key);
}

