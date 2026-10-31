// class net/friendswithmoney/fwm/mixin/KeyboardMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$noCopyPosition(client.input.KeyEvent input, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if (input.key() != 6) goto L23;
    if (antileak.FakeCoords.active() ==0) goto L23;
    cir.setReturnValue(Boolean.valueOf(1));
    L23:
    return;
}

private void fwm$noCopyLookAt(boolean hasQueryPermission, boolean queryServer, asm.mixin.injection.callback.CallbackInfo ci) {
    if (antileak.FakeCoords.active() ==0) goto L10;
    ci.cancel();
    L10:
    return;
}

