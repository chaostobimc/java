// class net/friendswithmoney/fwm/ui/panel/ModGiveawayPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field LINE_H I;
field GAP I;
field SCROLL_STEP I;
field GOLD I;
field GREEN I;
field buttons Ljava/util/List;;
field scroll I;
field contentH I;
public void <init>() {
    this.buttons = ?;
    net.Giveaways.refresh();
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of("fwm.news.giveaway", new Object[0]);
}

public boolean showTitle() {
    return 0;
}

private static String left(long ms) {
    if (cmp(ms,0) >0) goto L14;
    return ui.Lang.t("vorbei", "ended");
    L14:
    v2 = (ms / 1000);
    v4 = (v2 / 86400);
    v6 = ((v2 % 86400) / 3600);
    v8 = ((v2 % 3600) / 60);
    if (cmp(v4,0) <=0) goto L80;
    return ui.Lang.t("noch ", "") + v4 + 'd ' + v6 + 'h' + ui.Lang.t("", " left");
    L80:
    if (cmp(v6,0) <=0) goto L111;
    return ui.Lang.t("noch ", "") + v6 + 'h ' + v8 + 'm' + ui.Lang.t("", " left");
    L111:
    return ui.Lang.t("noch ", "") + v8 + 'm ' + (v2 % 60) + 's' + ui.Lang.t("", " left");
}

// DECOMPILE ERROR in render: 'dict' object has no attribute 'op_type'
public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = this.buttons.iterator();
    L27:
    if (v6.hasNext() ==0) goto L118;
    v7 = ((ui.panel.ModGiveawayPanel$Button)v6.next());
    if (cmp(mouseX,((d)v7.x())) <0) goto L115;
    if (cmp(mouseX,((d)(v7.x() + v7.w()))) >=0) goto L115;
    if (cmp(mouseY,((d)v7.y())) <0) goto L115;
    if (cmp(mouseY,((d)(v7.y() + v7.h()))) >=0) goto L115;
    net.Giveaways.join(v7.id());
    return 1;
    L115:
    goto L27;
    L118:
    return 0;
}

public double mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.contains(mouseX, mouseY) !=0) goto L11;
    return 0;
    L11:
    this.scroll = (this.scroll - (((i)Math.signum(v5)) * 20));
    return 1;
}

