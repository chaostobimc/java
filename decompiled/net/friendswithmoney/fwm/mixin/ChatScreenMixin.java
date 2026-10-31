// class net/friendswithmoney/fwm/mixin/ChatScreenMixin  (classfile vClassVersion(major=69, minor=0))
// extends net/minecraft/client/gui/screens/Screen
field input Lnet/minecraft/client/gui/components/EditBox;;
field fwm$pressHandled Z;
field BTN I;
protected void <init>(network.chat.Component title) {
    return;
}

private void fwm$picker() {
    v1 = seventv.EmotePicker.get();
    v1.setOnPick(/*indy*/accept(this));
    return v1;
}

private void fwm$insert(seventv.Emote e) {
    if (seventv.EmoteText.roomFor(this.input.getValue()) !=0) goto L14;
    return;
    L14:
    v2 = this.input.getValue();
    v3 = this.input.getCursorPosition();
    v4 = e.chatCode() + ' ';
    if (v3 <=0) goto L74;
    if (v3 > v2.length()) goto L74;
    if (v2.charAt((v3 - 1)) == 32) goto L74;
    v4 = ' ' + v4;
    L74:
    this.input.insertText(v4);
    this.input.setFocused(1);
    return;
}

private int fwm$btnX() {
    return ((this.width - 12) - 3);
}

private int fwm$btnY() {
    return (this.height - 13);
}

private int fwm$overButton(double mx, double a2) {
    v5 = this.fwm$btnX();
    v6 = this.fwm$btnY();
    if (cmp(mx,((d)(v5 - 1))) <0) goto L62;
    if (cmp(mx,((d)((v5 + 12) + 1))) >=0) goto L62;
    if (cmp(v3,((d)(v6 - 1))) <0) goto L62;
    if (cmp(v3,((d)((v6 + 12) + 1))) >=0) goto L62;
    goto L63;
    L62:
    L63:
    return 0;
}

private void fwm$init(asm.mixin.injection.callback.CallbackInfo ci) {
    if (seventv.SevenTv.on() !=0) goto L7;
    return;
    L7:
    if (this.input == null) goto L39;
    this.input.setWidth(Math.max(20, ((this.input.getWidth() - 12) - 6)));
    L39:
    v2 = this.fwm$picker();
    if (v2.isOpen() ==0) goto L61;
    if (seventv.EmotePicker.toggledRecently() !=0) goto L61;
    v2.close();
    L61:
    return;
}

private void fwm$render(client.gui.GuiGraphicsExtractor g, int mouseX, int mouseY, float delta, asm.mixin.injection.callback.CallbackInfo ci) {
    if (seventv.SevenTv.on() !=0) goto L7;
    return;
    L7:
    v6 = this.fwm$picker();
    if (this.fwm$overButton(((d)mouseX), ((d)mouseY)) !=0) goto L32;
    if (v6.isOpen() ==0) goto L36;
    L32:
    goto L37;
    L36:
    L37:
    v7 = 0;
    this.fwm$drawButton(g, this.fwm$btnX(), this.fwm$btnY(), v7);
    if (v6.isOpen() ==0) goto L68;
    seventv.EmoteSuggest.clear();
    goto L85;
    L68:
    seventv.EmoteSuggest.update(this.input);
    seventv.EmoteSuggest.render(g, this.input, mouseX, mouseY);
    L85:
    v6.render(g, mouseX, mouseY, delta, this.width, this.height);
    return;
}

private void fwm$drawButton(client.gui.GuiGraphicsExtractor g, int x, int y, boolean hover) {
    if (hover ==0) goto L11;
    goto L13;
    L11:
    L13:
    v5 = -4867391;
    if (hover ==0) goto L25;
    goto L27;
    L25:
    L27:
    v6 = -13947599;
    g.fill((x + 3), y, (x + 9), (y + 12), v5);
    g.fill((x + 1), (y + 1), (x + 11), (y + 11), v5);
    g.fill(x, (y + 3), (x + 12), (y + 9), v5);
    g.fill((x + 3), (y + 3), (x + 5), (y + 6), v6);
    g.fill((x + 7), (y + 3), (x + 9), (y + 6), v6);
    g.fill((x + 2), (y + 7), (x + 3), (y + 8), v6);
    g.fill((x + 9), (y + 7), (x + 10), (y + 8), v6);
    g.fill((x + 3), (y + 8), (x + 9), (y + 9), v6);
    if (hover ==0) goto L239;
    if (this.fwm$picker().isOpen() !=0) goto L239;
    g.setTooltipForNextFrame(List.of(network.chat.Component.literal("Emotes").getVisualOrderText(), network.chat.Component.literal('§7' + seventv.EmoteConfig.size() + ' zuletzt benutzt').getVisualOrderText()), (x - 20), (y - 4));
    L239:
    return;
}

private void fwm$mouseClicked(client.input.MouseButtonEvent event, boolean doubleClick, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if (seventv.SevenTv.on() !=0) goto L7;
    return;
    L7:
    v4 = this.fwm$picker();
    if (v4.isOpen() !=0) goto L48;
    if (seventv.EmoteSuggest.mouseClicked(this.input, event.x(), event.y()) ==0) goto L48;
    cir.setReturnValue(Boolean.valueOf(1));
    return;
    L48:
    if (this.fwm$overButton(event.x(), event.y()) ==0) goto L82;
    this.fwm$pressHandled = 1;
    v4.toggleDebounced();
    cir.setReturnValue(Boolean.valueOf(1));
    return;
    L82:
    if (v4.isOpen() ==0) goto L126;
    v5 = v4.isInside(event.x(), event.y());
    v4.mouseClicked(event, doubleClick);
    if (v5 ==0) goto L126;
    cir.setReturnValue(Boolean.valueOf(1));
    L126:
    return;
}

public boolean mouseReleased(client.input.MouseButtonEvent event) {
    if (seventv.SevenTv.on() ==0) goto L68;
    if (this.fwm$picker().mouseReleased(event.button()) ==0) goto L22;
    return 1;
    L22:
    v2 = this.fwm$pressHandled;
    this.fwm$pressHandled = 0;
    if (event.button() != 1) goto L68;
    if (this.fwm$overButton(event.x(), event.y()) ==0) goto L68;
    if (v2 !=0) goto L66;
    this.fwm$picker().toggleDebounced();
    L66:
    return 1;
    L68:
    return this.mouseReleased(event);
}

private void fwm$mouseScrolled(double mx, double a2, double my, double a4, asm.mixin.injection.callback.CallbackInfoReturnable sx) {
    if (seventv.SevenTv.on() ==0) goto L29;
    if (this.fwm$picker().mouseScrolled(mx, my, v7) ==0) goto L29;
    v9.setReturnValue(Boolean.valueOf(1));
    L29:
    return;
}

private void fwm$keyPressed(client.input.KeyEvent event, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if (seventv.SevenTv.on() !=0) goto L7;
    return;
    L7:
    if (this.fwm$picker().isOpen() ==0) goto L37;
    if (this.fwm$picker().keyPressed(event) ==0) goto L36;
    cir.setReturnValue(Boolean.valueOf(1));
    L36:
    return;
    L37:
    if (seventv.EmoteSuggest.keyPressed(this.input, event.key()) ==0) goto L59;
    cir.setReturnValue(Boolean.valueOf(1));
    L59:
    return;
}

public boolean charTyped(client.input.CharacterEvent event) {
    if (seventv.SevenTv.on() ==0) goto L29;
    if (this.fwm$picker().isOpen() ==0) goto L29;
    if (this.fwm$picker().charTyped(event) ==0) goto L29;
    return 1;
    L29:
    return this.charTyped(event);
}

