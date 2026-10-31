// class net/friendswithmoney/fwm/mixin/SuggestionWindowMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field rect Lnet/minecraft/client/renderer/Rect2i;;
field suggestionList Ljava/util/List;;
public void <init>() {
    return;
}

private void fwm$widen(asm.mixin.injection.callback.CallbackInfo ci) {
    v2 = client.Minecraft.getInstance().font;
    v3 = this.rect.getWidth();
    v4 = this.suggestionList.iterator();
    L26:
    if (v4.hasNext() ==0) goto L92;
    v5 = ((brigadier.suggestion.Suggestion)v4.next());
    v6 = net.FakeList.realBadge(v5.getText());
    if (v6 != null) goto L66;
    goto L26;
    L66:
    v3 = Math.max(v3, ((v2.width(v5.getText()) + v2.width(v6)) + 2));
    goto L26;
    L92:
    this.rect.setWidth(v3);
    return;
}

private void fwm$badge(client.gui.GuiGraphicsExtractor ctx, client.gui.Font font, String text, int x, int y, int color, com.llamalad7.mixinextras.injector.wrapoperation.Operation original) {
    new Object[6][0] = ctx;
    new Object[6][1] = font;
    new Object[6][2] = text;
    new Object[6][3] = Integer.valueOf(x);
    new Object[6][4] = Integer.valueOf(y);
    new Object[6][5] = Integer.valueOf(color);
    original.call(new Object[6]);
    v8 = net.FakeList.realBadge(text);
    if (v8 == null) goto L79;
    ctx.text(font, v8, (x + font.width(text)), y, -11141291);
    L79:
    return;
}

