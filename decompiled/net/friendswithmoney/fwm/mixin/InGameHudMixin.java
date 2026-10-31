// class net/friendswithmoney/fwm/mixin/InGameHudMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$actionBar(network.chat.Component message, boolean tinted, asm.mixin.injection.callback.CallbackInfo ci) {
    if (chat.ChatRouter.actionBar(message) !=0) goto L11;
    ci.cancel();
    L11:
    return;
}

