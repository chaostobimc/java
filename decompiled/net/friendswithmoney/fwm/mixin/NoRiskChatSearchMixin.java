// class net/friendswithmoney/fwm/mixin/NoRiskChatSearchMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$hideForEmotes(asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if (seventv.SevenTv.on() ==0) goto L14;
    cir.setReturnValue(Boolean.valueOf(0));
    L14:
    return;
}

