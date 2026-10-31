// class net/friendswithmoney/fwm/ui/panel/WaypointsPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field ROW_H I;
field open Lnet/friendswithmoney/fwm/net/Waypoints$Group;;
field scroll I;
field boxes Ljava/util/List;;
field actions Ljava/util/List;;
public void <init>() {
    this.boxes = ?;
    this.actions = ?;
    return;
}

public network.chat.MutableComponent title() {
    if (this.open != null) goto L19;
    goto L43;
    L19:
    L43:
    return network.chat.Component.literal(ui.Lang.str("fwm.waypoints.title", new Object[0]) + ' ' + this.open.name);
}

private boolean toggle(client.gui.GuiGraphicsExtractor ctx, int tx, int ty, boolean on, int mouseX, int mouseY, Runnable action) {
    if (mouseX < tx) goto L34;
    if (mouseX >= (tx + 22)) goto L34;
    if (mouseY < ty) goto L34;
    if (mouseY >= (ty + 11)) goto L34;
    goto L35;
    L34:
    L35:
    v8 = 0;
    if (on ==0) goto L55;
    goto L58;
    L55:
    L58:
    if (v8 ==0) goto L69;
    goto L83;
    L69:
    if (on ==0) goto L80;
    goto L83;
    L80:
    L83:
    ui.FwmTheme.box(22, 11, ui.FwmTheme.ON, ui.FwmTheme.INSET, ui.FwmTheme.ACCENT, ui.FwmTheme.ON, ui.FwmTheme.OFF);
    if (on ==0) goto L99;
    goto L102;
    L99:
    L102:
    if (on ==0) goto L117;
    goto L121;
    L117:
    L121:
    if (on ==0) goto L134;
    goto L137;
    L134:
    L137:
    (ty + 2).fill((tx + 20), (tx + 9), (ty + 9), m1, ui.FwmTheme.TEXT_DIM);
    new int[4][0] = tx;
    new int[4][1] = ty;
    new int[4][2] = 22;
    new int[4][3] = 11;
    this.boxes.add(new int[4]);
    this.actions.add(action);
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.boxes.clear();
    this.actions.clear();
    v6 = net.Waypoints.groups();
    if (this.open == null) goto L49;
    if (v6.contains(this.open) !=0) goto L49;
    this.open = null;
    L49:
    if (v6.isEmpty() ==0) goto L90;
    ctx.text(font, ui.Lang.of("fwm.waypoints.none", new Object[0]), (this.x + 2), (this.y + 4), ui.FwmTheme.TEXT_DIM, 0);
    return;
    L90:
    v7 = (this.y - this.scroll);
    ctx.enableScissor(this.x, this.y, (this.x + this.width), (this.y + this.height));
    if (this.open != null) goto L574;
    v8 = v6.iterator();
    L147:
    if (v8.hasNext() ==0) goto L571;
    v9 = ((net.Waypoints$Group)v8.next());
    v10 = net.Waypoints.groupOn(v9);
    if (mouseX < this.x) goto L244;
    if (mouseX >= ((this.x + this.width) - 30)) goto L244;
    if (mouseY < v7) goto L244;
    if (mouseY >= (v7 + 18)) goto L244;
    if (mouseY < this.y) goto L244;
    if (mouseY >= (this.y + this.height)) goto L244;
    goto L245;
    L244:
    L245:
    v11 = 0;
    if (v11 ==0) goto L279;
    ctx.fill(this.x, v7, (this.x + this.width), (v7 + 18), ui.FwmTheme.HOVER);
    L279:
    if (v10 ==0) goto L311;
    goto L314;
    L311:
    L314:
    font.text(network.chat.Component.literal(v9.name), (this.x + 6), (v7 + 5), ui.FwmTheme.TEXT, ui.FwmTheme.TEXT_DIM, 0);
    if (v9.credit == null) goto L386;
    if (v9.credit.isBlank() !=0) goto L386;
    ctx.text(font, ui.Lang.str("fwm.waypoints.credit", new Object[0]) + v9.credit, ((this.x + 12) + font.width(v9.name)), (v7 + 5), ui.FwmTheme.TEXT_DIM, 0);
    L386:
    v12 = net.Waypoints.found(v9) + '/' + v9.points.size() + ' ' + ui.Lang.str("fwm.waypoints.found", new Object[0]);
    ctx.text(font, v12, (((this.x + this.width) - 34) - font.width(v12)), (v7 + 5), ui.FwmTheme.TEXT_DIM, 0);
    v13 = v9;
    this.toggle(ctx, ((this.x + this.width) - 26), (v7 + 3), v10, mouseX, mouseY, /*indy*/run(v13));
    new int[4][0] = this.x;
    new int[4][1] = v7;
    new int[4][2] = (this.width - 30);
    new int[4][3] = 18;
    this.boxes.add(new int[4]);
    this.actions.add(/*indy*/run(this, v13));
    ui.FwmTheme.divider(ctx, this.x, ((v7 + 18) - 1), this.width);
    v7 = v7 + 18;
    goto L147;
    L571:
    goto L1200;
    L574:
    if (mouseX < this.x) goto L614;
    if (mouseX >= (this.x + 60)) goto L614;
    if (mouseY < v7) goto L614;
    if (mouseY >= (v7 + 12)) goto L614;
    goto L615;
    L614:
    L615:
    v8 = 0;
    if (v8 ==0) goto L654;
    goto L657;
    L654:
    L657:
    font.text('< ' + ui.Lang.str("fwm.waypoints.back", new Object[0]), (this.x + 2), (v7 + 2), ui.FwmTheme.ACCENT, ui.FwmTheme.TEXT_DIM, 0);
    if (this.open.credit == null) goto L740;
    if (this.open.credit.isBlank() !=0) goto L740;
    v9 = ui.Lang.str("fwm.waypoints.credit", new Object[0]) + this.open.credit;
    ctx.text(font, v9, (((this.x + this.width) - 4) - font.width(v9)), (v7 + 2), ui.FwmTheme.ACCENT, 0);
    L740:
    new int[4][0] = this.x;
    new int[4][1] = v7;
    new int[4][2] = 60;
    new int[4][3] = 12;
    this.boxes.add(new int[4]);
    this.actions.add(/*indy*/run(this));
    v7 = v7 + 16;
    v9 = client.Minecraft.getInstance();
    v10 = this.open.points.iterator();
    L813:
    if (v10.hasNext() ==0) goto L1200;
    v11 = ((net.Waypoints$Point)v10.next());
    v12 = net.Waypoints.done(this.open, v11);
    v13 = ui.Lang.str("fwm.waypoints.point", new Object[0]) + ' ' + v11.n;
    if (v12 ==0) goto L893;
    goto L896;
    L893:
    L896:
    font.text(v13, (this.x + 6), (v7 + 5), ui.FwmTheme.TEXT_DIM, ui.FwmTheme.TEXT, 0);
    if (v12 ==0) goto L917;
    goto L926;
    L917:
    L926:
    v14 = ui.Lang.str("fwm.waypoints.open", new Object[0]);
    if (v12 !=0) goto L1033;
    if (v9.player == null) goto L1033;
    v15 = Math.sqrt(((Math.pow(((v9.player.getX() - ((d)v11.x)) - 0.5), 2.0) + Math.pow((v9.player.getY() - ((d)v11.y)), 2.0)) + Math.pow(((v9.player.getZ() - ((d)v11.z)) - 0.5), 2.0)));
    v14 = v14 + ' · ' + Math.round(v15) + ' m';
    L1033:
    if (v12 ==0) goto L1059;
    goto L1062;
    L1059:
    L1062:
    font.text(v14, (this.x + 90), (v7 + 5), -10688364, ui.FwmTheme.TEXT_DIM, 0);
    v15 = v11.x + ' ' + v11.y + ' ' + v11.z;
    ctx.text(font, v15, (((this.x + this.width) - 34) - font.width(v15)), (v7 + 5), ui.FwmTheme.TEXT_DIM, 0);
    v16 = this.open;
    v17 = v11;
    if (v12 !=0) goto L1159;
    goto L1160;
    L1159:
    L1160:
    ctx.toggle(((this.x + this.width) - 26), (v7 + 3), 1, 0, mouseX, mouseY, /*indy*/run(v16, v17));
    ui.FwmTheme.divider(ctx, this.x, ((v7 + 18) - 1), this.width);
    v7 = v7 + 18;
    goto L813;
    L1200:
    ctx.disableScissor();
    return;
}

public void mouseClicked(double mouseX, double a2, int mouseY) {
    if (v5 !=0) goto L14;
    if (this.contains(mouseX, mouseY) !=0) goto L16;
    L14:
    return 0;
    L16:
    v6 = 0;
    L19:
    if (v6 >= this.boxes.size()) goto L126;
    v7 = (([I)this.boxes.get(v6));
    if (cmp(mouseX,((d)v7[0])) <0) goto L120;
    if (cmp(mouseX,((d)(v7[0] + v7[2]))) >=0) goto L120;
    if (cmp(mouseY,((d)v7[1])) <0) goto L120;
    if (cmp(mouseY,((d)(v7[1] + v7[3]))) >=0) goto L120;
    ((Runnable)this.actions.get(v6)).run();
    return 1;
    L120:
    v6 = v6 + 1;
    goto L19;
    L126:
    return 1;
}

public int mouseScrolled(double mouseX, double a2, double mouseY) {
    if (this.open != null) goto L18;
    goto L32;
    L18:
    L32:
    v7 = (this.open.points.size() + 1);
    v8 = Math.max(0, ((v7 * 18) - this.height));
    this.scroll = Math.max(0, Math.min(v8, (this.scroll - (((i)Math.signum(v5)) * 18))));
    return 1;
}

private static void lambda$render$3(net.Waypoints$Group g, net.Waypoints$Point p) {
    if (net.Waypoints.done(g, p) !=0) goto L14;
    goto L15;
    L14:
    L15:
    net.Waypoints.setDone(p, 1, 0);
    return;
}

private void lambda$render$2() {
    this.open = null;
    this.scroll = 0;
    return;
}

private void lambda$render$1(net.Waypoints$Group g) {
    this.open = g;
    this.scroll = 0;
    return;
}

private static void lambda$render$0(net.Waypoints$Group g) {
    if (net.Waypoints.groupOn(g) !=0) goto L12;
    goto L13;
    L12:
    L13:
    net.Waypoints.setGroupOn(1, 0);
    return;
}

