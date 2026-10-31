// class net/friendswithmoney/fwm/mixin/FontsMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$emoteGlyphs(network.chat.FontDescription source, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if ((source instanceof network.chat.FontDescription$Resource) ==0) goto L32;
    v3 = ((network.chat.FontDescription$Resource)source);
    v4 = seventv.EmoteGlyphs.sourceFor(v3.id());
    if (v4 == null) goto L32;
    cir.setReturnValue(v4);
    L32:
    return;
}

