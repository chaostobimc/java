// class net/friendswithmoney/fwm/mixin/MouseMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private boolean fwm$onButton(long window, client.input.MouseButtonInfo a2, int input, asm.mixin.injection.callback.CallbackInfo action) {
    if (action !=0) goto L16;
    seventv.EmotePicker.get().mouseReleased(input.button());
    L16:
    return;
}

