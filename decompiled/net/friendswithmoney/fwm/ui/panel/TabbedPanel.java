// class net/friendswithmoney/fwm/ui/panel/TabbedPanel  (classfile vClassVersion(major=69, minor=0))
// extends net/friendswithmoney/fwm/ui/panel/Panel
field titleKey Ljava/lang/String;;
field tabs Ljava/util/List;;
field built Ljava/util/List;;
field strip Lnet/friendswithmoney/fwm/ui/widget/TabStrip;;
public boolean <init>(String titleKey, List tabs) {
    this.titleKey = titleKey;
    this.tabs = tabs;
    this.built = ?;
    this.strip = ?;
    v3 = 0;
    L59:
    if (v3 >= tabs.size()) goto L86;
    this.built.add(null);
    v3 = v3 + 1;
    goto L59;
    L86:
    return;
}

public network.chat.MutableComponent title() {
    return ui.Lang.of(this.titleKey, new Object[0]);
}

public void setTab(int index) {
    if (index <0) goto L28;
    if (index >= this.tabs.size()) goto L28;
    if (index != this.strip.active()) goto L29;
    L28:
    return;
    L29:
    v2 = this.current();
    if (v2 == null) goto L42;
    v2.onHidden();
    L42:
    this.strip.setActive(index);
    this.applyBounds();
    return;
}

public boolean shows(Class kind) {
    return kind.isInstance(this.current());
}

private Object current() {
    v1 = this.strip.active();
    if (v1 >= this.built.size()) goto L37;
    goto L38;
    L37:
    L38:
    return null;
}

private Object active() {
    v1 = this.strip.active();
    v2 = ((ui.panel.Panel)this.built.get(v1));
    if (v2 != null) goto L63;
    v2 = ((ui.panel.Panel)((ui.panel.TabbedPanel$Tab)this.tabs.get(v1)).panel().get());
    this.built.set(v1, v2);
    L63:
    return v2;
}

protected void onResize() {
    this.applyBounds();
    return;
}

private void applyBounds() {
    this.strip.setBounds(this.x, this.y, this.width);
    v1 = ((this.y + 13) + 4);
    this.active().setBounds(this.x, v1, this.width, Math.max(10, ((this.y + this.height) - v1)));
    return;
}

public void render(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, int mouseX, int mouseY, float delta) {
    this.strip.render(ctx, font, mouseX, mouseY);
    this.active().render(ctx, font, mouseX, mouseY, delta);
    return;
}

public boolean mouseClicked(double mouseX, double a2, int mouseY) {
    v6 = this.strip.indexAt(mouseX, mouseY);
    if (v6 <0) goto L24;
    this.setTab(v6);
    return 1;
    L24:
    return this.active().mouseClicked(mouseX, mouseY, v5);
}

public boolean mouseDragged(double mouseX, double a2, int mouseY) {
    return this.active().mouseDragged(mouseX, mouseY, v5);
}

public void mouseReleased() {
    this.active().mouseReleased();
    return;
}

public boolean mouseScrolled(double mouseX, double a2, double mouseY) {
    return this.active().mouseScrolled(mouseX, mouseY, v5);
}

public boolean isCapturingKeys() {
    return this.active().isCapturingKeys();
}

public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
    return this.active().keyPressed(keyCode, scanCode, modifiers);
}

public boolean charTyped(client.input.CharacterEvent input) {
    return this.active().charTyped(input);
}

public void onHidden() {
    v1 = this.built.iterator();
    L10:
    if (v1.hasNext() ==0) goto L40;
    v2 = ((ui.panel.Panel)v1.next());
    if (v2 == null) goto L37;
    v2.onHidden();
    L37:
    goto L10;
    L40:
    return;
}

