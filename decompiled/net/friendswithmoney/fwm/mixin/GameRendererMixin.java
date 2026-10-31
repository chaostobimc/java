// class net/friendswithmoney/fwm/mixin/GameRendererMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$worldStart(asm.mixin.injection.callback.CallbackInfo ci) {
    net.Presence.drawingWorld = 1;
    return;
}

private void fwm$worldEnd(asm.mixin.injection.callback.CallbackInfo ci) {
    net.Presence.drawingWorld = 0;
    return;
}

