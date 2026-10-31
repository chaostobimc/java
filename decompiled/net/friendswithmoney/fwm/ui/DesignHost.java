// class net/friendswithmoney/fwm/ui/DesignHost  (classfile vClassVersion(major=69, minor=0))
// extends net/minecraft/client/gui/screens/Screen
field CARD I;
field CARD_EDGE I;
field CARD_HI I;
field LINE I;
field WHITE I;
field SOFT I;
field DIMTXT I;
field GREEN I;
field BRIGHT I;
field GREEN_DARK I;
field RED I;
field OFF_TRACK I;
field SETTINGS_KEYS I;
field parent Lnet/minecraft/client/gui/screens/Screen;;
field pageKey Ljava/lang/String;;
field base Lnet/friendswithmoney/fwm/ui/panel/Panel;;
field subs Ljava/util/Deque;;
field frameX I;
field frameY I;
field frameW I;
field frameH I;
field backBox [I;
field switchBox [I;
field miniBox [I;
field cogBox [I;
field nameBox [I;
field infoBox [I;
field lastMx I;
field lastMy I;
field flagBoxDe [I;
field flagBoxEn [I;
field menuKeyArmed Z;
field appliedScalePercent I;
field mouseDown Z;
field TITLE_H I;
protected boolean <init>(client.gui.screens.Screen parent) {
    this.subs = ?;
    this.backBox = new int[4];
    this.switchBox = new int[4];
    this.miniBox = new int[4];
    this.cogBox = new int[4];
    this.nameBox = new int[4];
    this.infoBox = new int[4];
    this.lastMx = m1;
    this.lastMy = m1;
    this.flagBoxDe = new int[4];
    this.flagBoxEn = new int[4];
    if (ui.DesignHost.menuKeyHeld() !=0) goto L97;
    goto L98;
    L97:
    L98:
    1.menuKeyArmed = 0;
    this.appliedScalePercent = m1;
    this.parent = parent;
    return;
}

// vw()I  (abstract/native)

// vh()I  (abstract/native)

protected float scale() {
    v1 = (this.width - ui.FwmTutorial.reservedSide(this.width));
    v2 = ((d)client.Minecraft.getInstance().getWindow().getGuiScale());
    v4 = ui.DesignHost.crispScale(v1, this.height, this.vw(), this.vh(), v2);
    v5 = this.appliedPercent();
    if (v5 != 100) goto L59;
    return v4;
    L59:
    v6 = Math.min((((f)v1) / ((f)this.vw())), (((f)this.height) / ((f)this.vh())));
    return Math.min(v6, ((v4 * ((f)v5)) / 100.0));
}

protected static int crispScale(int width, int height, int vw, int vh, double gui) {
    v6 = ((f)Math.max(460, vw));
    v7 = ((f)Math.max(290, vh));
    v8 = Math.min((((f)width) / v6), (((f)height) / v7));
    v9 = Math.max(1, ((i)Math.floor((gui * ((d)Math.min((((f)width) / ((f)vw)), (((f)height) / ((f)vh))))))));
    v10 = Math.max(1, ((i)Math.floor(((gui * ((d)v8)) * 0.86))));
    if (v10 >= 2) goto L97;
    if (v9 < 2) goto L97;
    v10 = 2;
    L97:
    return ((f)(((d)Math.min(v10, v9)) / gui));
}

private int appliedPercent() {
    if (this.appliedScalePercent <0) goto L14;
    if (this.mouseDown !=0) goto L21;
    L14:
    this.appliedScalePercent = ui.DesignHost.configuredPercent();
    L21:
    return this.appliedScalePercent;
}

private static int configuredPercent() {
    if (config.FwmConfig.INSTANCE.menuScale >0) goto L14;
    goto L20;
    L14:
    L20:
    return config.FwmConfig.INSTANCE.menuScale;
}

protected double offX() {
    v1 = ui.FwmTutorial.reservedSide(this.width);
    if (ui.FwmTutorial.onLeft() ==0) goto L18;
    goto L19;
    L18:
    L19:
    return (((f)0) + ((f)Math.floor(((d)((((f)(this.width - v1)) - (((f)this.vw()) * this.scale())) / 2)))));
}

protected double offY() {
    return ((f)Math.floor(((d)((((f)this.height) - (((f)this.vh()) * this.scale())) / 2))));
}

protected float vx(double x) {
    return ((x - ((d)this.offX())) / ((d)this.scale()));
}

protected float vy(double y) {
    return ((y - ((d)this.offY())) / ((d)this.scale()));
}

protected static boolean feature(String key) {
    if (key.equals("fwm.tab.antileak") ==0) goto L14;
    goto L15;
    L14:
    L15:
    return key;
}

protected static boolean switchable(String key) {
    return config.Features.canSwitch(ui.DesignHost.feature(key));
}

protected static boolean on(String key) {
    return config.Features.isOn(ui.DesignHost.feature(key));
}

// DECOMPILE ERROR in create: 'dict' object has no attribute 'op_type'
protected void openPage(String key) {
    this.openPage(key, m1);
    return;
}

protected void openPage(String key, int tab) {
    if (key.equals("fwm.tab.giveaway") ==0) goto L15;
    key = "fwm.tab.auction";
    tab = 1;
    L15:
    if (key.equals(this.pageKey) ==0) goto L50;
    if (tab >=0) goto L50;
    L30:
    if (this.subs.isEmpty() !=0) goto L49;
    this.closeSub();
    goto L30;
    L49:
    return;
    L50:
    this.closePage();
    this.pageKey = key;
    this.base = this.create(key);
    if (tab <0) goto L97;
    v4 = this.base;
    if ((v4 instanceof ui.panel.TabbedPanel) ==0) goto L97;
    v3 = ((ui.panel.TabbedPanel)v4);
    v3.setTab(tab);
    L97:
    this.applyBounds();
    return;
}

protected void closePage() {
    L0:
    if (this.subs.isEmpty() !=0) goto L19;
    this.closeSub();
    goto L0;
    L19:
    if (this.base == null) goto L33;
    this.base.onHidden();
    L33:
    this.base = null;
    this.pageKey = null;
    return;
}

protected Object active() {
    if (this.subs.isEmpty() ==0) goto L19;
    goto L31;
    L19:
    L31:
    return ((ui.panel.Panel)this.subs.peek());
}

protected void setFrame(int x, int y, int w, int h) {
    if (x != this.frameX) goto L34;
    if (y != this.frameY) goto L34;
    if (w != this.frameW) goto L34;
    if (h != this.frameH) goto L34;
    return;
    L34:
    this.frameX = x;
    this.frameY = y;
    this.frameW = w;
    this.frameH = h;
    this.applyBounds();
    return;
}

private void applyBounds() {
    if (this.base == null) goto L14;
    if (this.frameW >0) goto L15;
    L14:
    return;
    L15:
    v1 = this.frameX;
    v2 = ((this.frameY + 22) + 3);
    v3 = this.frameW;
    v4 = ((this.frameH - 22) - 3);
    this.base.setBounds(v1, v2, v3, v4);
    v5 = this.subs.iterator();
    L69:
    if (v5.hasNext() ==0) goto L104;
    v6 = ((ui.panel.Panel)v5.next());
    v6.setBounds(v1, v2, v3, v4);
    goto L69;
    L104:
    return;
}

private boolean pageLive() {
    if (this.subs.isEmpty() ==0) goto L19;
    if (this.pageKey != null) goto L21;
    L19:
    return 1;
    L21:
    if (this.pageKey.equals("fwm.tab.antileak") ==0) goto L35;
    return 1;
    L35:
    if (ui.DesignHost.switchable(this.pageKey) ==0) goto L55;
    if (ui.DesignHost.on(this.pageKey) ==0) goto L59;
    L55:
    goto L60;
    L59:
    L60:
    return 0;
}

protected void init() {
    this.init();
    if ((this instanceof ui.LauncherScreen) ==0) goto L26;
    if (ui.FwmTutorial.pending() ==0) goto L26;
    if (ui.FwmTutorial.active() !=0) goto L26;
    ui.FwmTutorial.start();
    L26:
    return;
}

void tourShow(String key, int tab) {
    if ("fwm.tab.overview".equals(key) ==0) goto L15;
    this.closePage();
    return;
    L15:
    if (key.equals(this.pageKey) ==0) goto L79;
    if (tab <0) goto L79;
    v4 = this.base;
    if ((v4 instanceof ui.panel.TabbedPanel) ==0) goto L79;
    v3 = ((ui.panel.TabbedPanel)v4);
    L50:
    if (this.subs.isEmpty() !=0) goto L69;
    this.closeSub();
    goto L50;
    L69:
    v3.setTab(tab);
    this.applyBounds();
    return;
    L79:
    this.openPage(key, tab);
    return;
}

void tourRelayout() {
    return;
}

public void openSub(ui.panel.Panel panel) {
    this.subs.push(panel);
    this.applyBounds();
    return;
}

public void closeSub() {
    if (this.subs.isEmpty() ==0) goto L13;
    return;
    L13:
    ((ui.panel.Panel)this.subs.pop()).onHidden();
    this.applyBounds();
    return;
}

public client.gui.components.events.GuiEventListener attach(client.gui.components.AbstractWidget widget) {
    this.addWidget(widget);
    return;
}

public void detach(client.gui.components.AbstractWidget widget) {
    this.removeWidget(widget);
    return;
}

public List sidebarKeys() {
    return List.of();
}

public void extractRenderState(client.gui.GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
    ctx.fill(0, 0, this.width, this.height, ui.FwmTheme.OVERLAY);
    v5 = ctx.pose();
    v5.pushMatrix();
    v5.translate(this.offX(), this.offY());
    v5.scale(this.scale(), this.scale());
    ui.LauncherGfx.card(ctx, 0, 0, this.vw(), this.vh(), 8);
    this.lastMx = ((i)Math.floor(this.vx(((d)mouseX))));
    this.lastMy = ((i)Math.floor(this.vy(((d)mouseY))));
    this.drawCanvas(ctx, this.lastMx, this.lastMy, delta);
    v5.popMatrix();
    this.extractRenderState(ctx, mouseX, mouseY, delta);
    if (ui.FwmTutorial.active() ==0) goto L208;
    ui.FwmTutorial.dock(Math.round(this.offX()), Math.round((this.offX() + (((f)this.vw()) * this.scale()))), Math.round(this.offY()), Math.round((this.offY() + (((f)this.vh()) * this.scale()))));
    ui.FwmTutorial.render(ctx, this.font, this.width, this.height, mouseX, mouseY);
    L208:
    return;
}

// drawCanvas(Lnet.minecraft.client.gui.GuiGraphicsExtractor;IIF)V  (abstract/native)

protected void text(client.gui.GuiGraphicsExtractor ctx, String s, int x, int y, int color) {
    ui.LauncherGfx.text(ctx, this.font, s, x, y, color);
    return;
}

protected void right(client.gui.GuiGraphicsExtractor ctx, String s, int rightX, int y, int color) {
    this.text(ctx, s, (rightX - this.font.width(s)), y, color);
    return;
}

protected static boolean inside(int[] box, double x, double a2) {
    if (cmp(x,((d)box[0])) <0) goto L48;
    if (cmp(x,((d)(box[0] + box[2]))) >=0) goto L48;
    if (cmp(v3,((d)box[1])) <0) goto L48;
    if (cmp(v3,((d)(box[1] + box[3]))) >=0) goto L48;
    goto L49;
    L48:
    L49:
    return 0;
}

protected void brand(client.gui.GuiGraphicsExtractor ctx, int x, int y, int rightX) {
    ui.FwmTheme.logo(ctx, x, y, 20);
    this.text(ctx, "Friends With Money", (x + 25), (y + 5), m1);
    v5 = ui.FwmGfx.playerName();
    v6 = (this.font.width(v5) + 22);
    new int[4][0] = (rightX - v6);
    new int[4][1] = (y + 2);
    new int[4][2] = v6;
    new int[4][3] = 14;
    this.nameBox = new int[4];
    ui.LauncherGfx.round(ctx, (rightX - v6), (y + 2), v6, 14, 7, 620756991);
    ui.FwmGfx.playerHead(ctx, ((rightX - v6) + 4), (y + 5), 8);
    this.text(ctx, v5, ((rightX - v6) + 16), (y + 5), m1);
    v7 = ((rightX - v6) - 18);
    new int[4][0] = v7;
    new int[4][1] = (y + 2);
    new int[4][2] = 14;
    new int[4][3] = 14;
    this.infoBox = new int[4];
    v8 = ui.DesignHost.inside(this.infoBox, ((d)this.lastMx), ((d)this.lastMy));
    v9 = "fwm.tab.news".equals(this.pageKey);
    if (v8 ==0) goto L222;
    ui.LauncherGfx.ring(ctx, (v7 - 1), (y + 1), 16, 16, 5, 1432152212);
    L222:
    if (v8 ==0) goto L244;
    goto L258;
    L244:
    if (v9 ==0) goto L255;
    goto L258;
    L255:
    L258:
    ui.LauncherGfx.round((y + 2), 14, 14, 4, 1079830676, 1157627903, 620756991);
    if (v8 ==0) goto L283;
    goto L295;
    L283:
    if (v9 ==0) goto L292;
    goto L295;
    L292:
    L295:
    ui.LauncherGfx.ring((y + 2), 14, 14, 4, -10688364, m1, 1728053247);
    if (v8 !=0) goto L314;
    if (v9 ==0) goto L318;
    L314:
    goto L321;
    L318:
    L321:
    ui.LauncherGfx.infoGlyph(v7, (y + 2), m1, -5846860);
    v10 = net.Giveaways.open();
    if (v10 <=0) goto L401;
    v11 = String.valueOf(Math.min(9, v10));
    ui.LauncherGfx.round(ctx, (v7 + 9), (y - 1), 8, 8, 4, -865972);
    ctx.text(this.font, network.chat.Component.literal(v11), ((v7 + 13) - (this.font.width(v11) / 2)), y, -15066598, 0);
    L401:
    return;
}

protected int infoLeft() {
    return this.infoBox[0];
}

protected int footer(client.gui.GuiGraphicsExtractor ctx, int x, int y, int rightX) {
    this.text(ctx, 'FWM Mod v' + FwmClient.MOD_VERSION, x, y, -9464447);
    return this.flags(ctx, y, rightX);
}

protected void flags(client.gui.GuiGraphicsExtractor ctx, int y, int rightX) {
    v4 = (rightX - 12);
    new int[4][0] = (v4 - 1);
    new int[4][1] = (y - 1);
    new int[4][2] = 14;
    new int[4][3] = 10;
    this.flagBoxEn = new int[4];
    if (ui.Lang.isGerman() !=0) goto L54;
    goto L55;
    L54:
    L55:
    ui.FwmTheme.usFlag(v4, y, 12, 8, 1, 0);
    v4 = v4 + 240;
    new int[4][0] = (v4 - 1);
    new int[4][1] = (y - 1);
    new int[4][2] = 14;
    new int[4][3] = 10;
    this.flagBoxDe = new int[4];
    ui.FwmTheme.germanFlag(ctx, v4, y, 12, 8, ui.Lang.isGerman());
    return v4;
}

protected String title(String key) {
    return ui.Lang.str(key, new Object[0]);
}

protected void drawPage(client.gui.GuiGraphicsExtractor ctx, int mx, int my, float delta, String back) {
    v6 = this.active();
    if (v6 != null) goto L12;
    return;
    L12:
    v7 = this.frameX;
    v8 = this.frameY;
    v9 = (this.frameX + this.frameW);
    v10 = v7;
    this.backBox = new int[4];
    if (this.subs.isEmpty() !=0) goto L71;
    goto L73;
    L71:
    L73:
    v11 = back;
    if (v11 == null) goto L230;
    v12 = (this.font.width(v11) + 16);
    new int[4][0] = v7;
    new int[4][1] = (v8 + 4);
    new int[4][2] = v12;
    new int[4][3] = 14;
    this.backBox = new int[4];
    v13 = ui.DesignHost.inside(this.backBox, ((d)mx), ((d)my));
    if (v13 ==0) goto L160;
    goto L163;
    L160:
    L163:
    ui.LauncherGfx.round(v7, (v8 + 4), v12, 14, 7, 872415231, 452984831);
    if (v13 ==0) goto L185;
    goto L188;
    L185:
    L188:
    ui.DesignHost.chevron((v7 + 5), (v8 + 8), m1, -5846860);
    if (v13 ==0) goto L214;
    goto L217;
    L214:
    L217:
    ctx.text(v11, (v7 + 11), (v8 + 7), m1, -5846860);
    v10 = ((v7 + v12) + 8);
    L230:
    if (this.subs.isEmpty() ==0) goto L268;
    if (this.pageKey == null) goto L268;
    ui.LauncherGfx.icon(ctx, this.pageKey, v10, (v8 + 3), 1, m1);
    v10 = v10 + 20;
    L268:
    v12 = v6.title().getString();
    v13 = v9;
    this.switchBox = new int[4];
    this.miniBox = new int[4];
    this.cogBox = new int[4];
    if (this.subs.isEmpty() ==0) goto L492;
    if (this.pageKey == null) goto L492;
    if (ui.DesignHost.switchable(this.pageKey) ==0) goto L492;
    v14 = ui.DesignHost.on(this.pageKey);
    if (v14 ==0) goto L360;
    goto L369;
    L360:
    L369:
    v15 = ui.Lang.t("Aus", "Off");
    v16 = this.font.width(v15);
    new int[4][0] = (((v13 - 26) - v16) - 4);
    new int[4][1] = (v8 + 4);
    new int[4][2] = ((26 + v16) + 4);
    new int[4][3] = 14;
    this.switchBox = new int[4];
    if (v14 ==0) goto L454;
    goto L457;
    L454:
    L457:
    ctx.text(v15, (((v13 - 26) - v16) - 2), (v8 + 7), -5846860, -1017230);
    ui.LauncherGfx.toggle(ctx, (v13 - 22), (v8 + 5), v14, -12136081, -12957119);
    v13 = (this.switchBox[0] - 8);
    L492:
    if (v6.settingsCog() ==0) goto L612;
    new int[4][0] = (v13 - 14);
    new int[4][1] = (v8 + 4);
    new int[4][2] = 14;
    new int[4][3] = 14;
    this.cogBox = new int[4];
    v14 = ui.DesignHost.inside(this.cogBox, ((d)mx), ((d)my));
    if (v14 ==0) goto L572;
    goto L575;
    L572:
    L575:
    ui.LauncherGfx.round((v13 - 14), (v8 + 4), 14, 14, 7, 872415231, 452984831);
    if (v14 ==0) goto L603;
    goto L606;
    L603:
    L606:
    ui.SharpIcons.draw("icon_cog", (v13 - 12), (v8 + 6), 10, m1, -5846860);
    v13 = v13 + 236;
    L612:
    if (v6.headerSwitch() == null) goto L763;
    v14 = ui.Lang.str("fwm.header.minimap", new Object[0]);
    v15 = config.Features.isOn(v6.headerSwitch());
    v16 = this.font.width(v14);
    new int[4][0] = (((v13 - 22) - v16) - 4);
    new int[4][1] = (v8 + 4);
    new int[4][2] = ((22 + v16) + 4);
    new int[4][3] = 14;
    this.miniBox = new int[4];
    if (v15 ==0) goto L725;
    goto L728;
    L725:
    L728:
    ctx.text(v14, (((v13 - 22) - v16) - 3), (v8 + 7), -5846860, -9464447);
    ui.LauncherGfx.toggle(ctx, (v13 - 22), (v8 + 5), v15, -12136081, -12957119);
    v13 = (this.miniBox[0] - 8);
    L763:
    this.text(ctx, ui.LauncherGfx.trim(this.font, v12, Math.max(10, ((v13 - v10) - 4))), v10, (v8 + 7), m1);
    ctx.fill(v7, (v8 + 22), v9, ((v8 + 22) + 1), 452984831);
    if (this.pageLive() !=0) goto L930;
    v14 = ui.Lang.t("Dieses Feature ist ausgeschaltet.", "This feature is switched off.");
    v15 = ui.Lang.t("Mit dem Schalter oben rechts wieder anschalten", "Switch it back on at the top right");
    v16 = (this.frameY + (this.frameH / 2));
    this.text(ctx, v14, (v7 + ((this.frameW - this.font.width(v14)) / 2)), (v16 - 8), -1017230);
    this.text(ctx, v15, (v7 + ((this.frameW - this.font.width(v15)) / 2)), (v16 + 4), -9464447);
    return;
    L930:
    v6.render(ctx, this.font, mx, my, delta);
    return;
}

protected static void chevron(client.gui.GuiGraphicsExtractor ctx, int x, int y, int color) {
    ctx.fill((x + 2), y, (x + 3), (y + 1), color);
    ctx.fill((x + 1), (y + 1), (x + 2), (y + 2), color);
    ctx.fill(x, (y + 2), (x + 1), (y + 3), color);
    ctx.fill((x + 1), (y + 3), (x + 2), (y + 4), color);
    ctx.fill((x + 2), (y + 4), (x + 3), (y + 5), color);
    return;
}

protected client.sounds.SoundEngine$PlayResult click() {
    if (config.FwmConfig.INSTANCE.menuSounds ==0) goto L26;
    client.Minecraft.getInstance().getSoundManager().play(client.resources.sounds.SimpleSoundInstance.forUI(sounds.SoundEvents.UI_BUTTON_CLICK, 1));
    L26:
    return;
}

// designClicked(DDI)Z  (abstract/native)

// leavePage()V  (abstract/native)

public boolean mouseClicked(client.input.MouseButtonEvent click, boolean doubled) {
    v3 = this.vx(click.x());
    v5 = this.vy(click.y());
    v7 = util.GLFW.button(click.button());
    this.mouseDown = 1;
    if (ui.FwmTutorial.mouseClicked(click.x(), click.y(), v7) ==0) goto L51;
    return 1;
    L51:
    if (ui.FwmTutorial.consumes(click.x(), this.width) ==0) goto L67;
    return 1;
    L67:
    if (v7 !=0) goto L392;
    if (ui.DesignHost.inside(this.flagBoxDe, v3, v5) !=0) goto L98;
    if (ui.DesignHost.inside(this.flagBoxEn, v3, v5) ==0) goto L136;
    L98:
    if (ui.DesignHost.inside(this.flagBoxDe, v3, v5) ==0) goto L117;
    goto L120;
    L117:
    L120:
    ui.Lang.set("en");
    config.ConfigManager.save();
    this.applyBounds();
    this.click();
    return 1;
    L136:
    if (ui.DesignHost.inside(this.infoBox, v3, v5) ==0) goto L161;
    this.openPage("fwm.tab.news");
    this.click();
    return 1;
    L161:
    if (this.subs.isEmpty() ==0) goto L255;
    if ((this.base instanceof ui.panel.SpawnerPanel) !=0) goto L215;
    v9 = this.base;
    if ((v9 instanceof ui.panel.TabbedPanel) ==0) goto L255;
    v8 = ((ui.panel.TabbedPanel)v9);
    if (v8.shows(ui.panel.SpawnerPanel.class) ==0) goto L255;
    L215:
    if (ui.DesignHost.inside(this.nameBox, v3, v5) ==0) goto L255;
    if (net.Members.isMember(ui.FwmGfx.playerName()) ==0) goto L255;
    this.openSub(?);
    this.click();
    return 1;
    L255:
    if (this.active() == null) goto L392;
    if (ui.DesignHost.inside(this.backBox, v3, v5) ==0) goto L304;
    if (this.subs.isEmpty() !=0) goto L294;
    this.closeSub();
    goto L298;
    L294:
    this.leavePage();
    L298:
    this.click();
    return 1;
    L304:
    if (ui.DesignHost.inside(this.switchBox, v3, v5) ==0) goto L336;
    config.Features.toggle(ui.DesignHost.feature(this.pageKey));
    config.ConfigManager.save();
    this.click();
    return 1;
    L336:
    if (ui.DesignHost.inside(this.miniBox, v3, v5) ==0) goto L365;
    config.Features.toggle(this.active().headerSwitch());
    this.click();
    return 1;
    L365:
    if (ui.DesignHost.inside(this.cogBox, v3, v5) ==0) goto L392;
    this.openPage("fwm.tab.settings", 3);
    this.click();
    return 1;
    L392:
    if (this.designClicked(v3, v5, v7) ==0) goto L410;
    this.click();
    return 1;
    L410:
    if (this.active() == null) goto L445;
    if (this.pageLive() ==0) goto L445;
    if (this.active().mouseClicked(v3, v5, v7) ==0) goto L445;
    this.click();
    return 1;
    L445:
    return this.mouseClicked(click, doubled);
}

public boolean mouseDragged(client.input.MouseButtonEvent click, double deltaX, double a3) {
    if (this.active() == null) goto L42;
    if (this.active().mouseDragged(this.vx(click.x()), this.vy(click.y()), util.GLFW.button(click.button())) ==0) goto L42;
    return 1;
    L42:
    return this.mouseDragged(click, deltaX, v4);
}

public boolean mouseReleased(client.input.MouseButtonEvent click) {
    if (this.active() == null) goto L14;
    this.active().mouseReleased();
    L14:
    this.mouseDown = 0;
    return this.mouseReleased(click);
}

protected boolean designScrolled(double mx, double a2, double my) {
    return 0;
}

public boolean mouseScrolled(double mouseX, double a2, double mouseY, double a4) {
    v9 = this.vx(mouseX);
    v11 = this.vy(mouseY);
    if (this.designScrolled(v9, v11, v7) ==0) goto L29;
    return 1;
    L29:
    if (this.active() == null) goto L54;
    if (this.active().mouseScrolled(v9, v11, v7) ==0) goto L54;
    return 1;
    L54:
    return this.mouseScrolled(mouseX, mouseY, v5, v7);
}

protected boolean designKey(client.input.KeyEvent input) {
    return 0;
}

protected boolean designChar(client.input.CharacterEvent input) {
    return 0;
}

protected boolean typingInField() {
    v1 = this.children().iterator();
    L10:
    if (v1.hasNext() ==0) goto L50;
    v2 = v1.next();
    if ((v2 instanceof client.gui.components.EditBox) ==0) goto L47;
    v3 = ((client.gui.components.EditBox)v2);
    if (v3.isFocused() ==0) goto L47;
    return 1;
    L47:
    goto L10;
    L50:
    return 0;
}

public boolean keyPressed(client.input.KeyEvent input) {
    v2 = this.active();
    if (ui.FwmTutorial.keyPressed(input.key()) ==0) goto L17;
    return 1;
    L17:
    if (v2 == null) goto L49;
    if (v2.isCapturingKeys() ==0) goto L49;
    if (v2.keyPressed(input.key(), input.keycode(), input.modifiers()) ==0) goto L49;
    return 1;
    L49:
    if (this.typingInField() !=0) goto L67;
    if (v2 == null) goto L71;
    if (v2.isCapturingKeys() ==0) goto L71;
    L67:
    goto L72;
    L71:
    L72:
    v3 = 0;
    if (this.menuKeyArmed ==0) goto L113;
    if (v3 !=0) goto L113;
    if (input.Keybinds.OPEN_MENU == null) goto L113;
    if (input.Keybinds.OPEN_MENU.matches(input) ==0) goto L113;
    if (this.designTyping() !=0) goto L113;
    this.onClose();
    return 1;
    L113:
    if (input.key() != 41) goto L137;
    if (this.escape() ==0) goto L131;
    return 1;
    L131:
    this.onClose();
    return 1;
    L137:
    if (v2 == null) goto L169;
    if (this.pageLive() ==0) goto L169;
    if (v2.keyPressed(input.key(), input.keycode(), input.modifiers()) ==0) goto L169;
    return 1;
    L169:
    if (v3 !=0) goto L183;
    if (this.designKey(input) ==0) goto L183;
    return 1;
    L183:
    return this.keyPressed(input);
}

protected boolean designTyping() {
    return 0;
}

protected void escape() {
    if (this.subs.isEmpty() !=0) goto L18;
    this.closeSub();
    return 1;
    L18:
    return 0;
}

public boolean keyReleased(client.input.KeyEvent input) {
    if (input.Keybinds.OPEN_MENU == null) goto L21;
    if (input.Keybinds.OPEN_MENU.matches(input) ==0) goto L21;
    this.menuKeyArmed = 1;
    L21:
    return this.keyReleased(input);
}

public boolean charTyped(client.input.CharacterEvent input) {
    v2 = this.active();
    if (v2 == null) goto L26;
    if (this.pageLive() ==0) goto L26;
    if (v2.charTyped(input) ==0) goto L26;
    return 1;
    L26:
    if (this.typingInField() !=0) goto L43;
    if (this.designChar(input) ==0) goto L43;
    return 1;
    L43:
    return this.charTyped(input);
}

private static boolean menuKeyHeld() {
    if (input.Keybinds.OPEN_MENU != null) goto L8;
    return 0;
    L8:
    v0 = blaze3d.platform.InputConstants.getKey(input.Keybinds.OPEN_MENU.saveString());
    if (v0 == null) goto L32;
    if (v0.getType() == blaze3d.platform.InputConstants$Type.KEYBOARD) goto L34;
    L32:
    return 0;
    L34:
    return blaze3d.platform.InputConstants.isKeyDown(v0.getValue());
}

public void onClose() {
    this.closePage();
    config.FwmConfig.INSTANCE.clamp();
    config.ConfigManager.save();
    antileak.AntiLeak.sync(client.Minecraft.getInstance());
    if (this.minecraft == null) goto L41;
    this.minecraft.gui.setScreen(this.parent);
    L41:
    return;
}

public boolean isPauseScreen() {
    return 0;
}

private static void lambda$create$12() {
    return ?;
}

private static void lambda$create$11() {
    return ?;
}

private static void lambda$create$10() {
    return ?;
}

private void lambda$create$9() {
    return ?;
}

private void lambda$create$7() {
    return ?;
}

private static List lambda$create$8() {
    return config.FwmConfig.INSTANCE.autoTpaNames;
}

private void lambda$create$6() {
    return ?;
}

private void lambda$create$5() {
    return ?;
}

private void lambda$create$4() {
    return ?;
}

private void lambda$create$3() {
    return ?;
}

private void lambda$create$2() {
    return ?;
}

private void lambda$create$1() {
    return ?;
}

private void lambda$create$0() {
    return ?;
}

