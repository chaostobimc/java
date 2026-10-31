// class net/friendswithmoney/fwm/mixin/ChatHudMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private network.chat.Component fwm$markPlayerMessage(network.chat.Component message) {
    return seventv.SevenTv.transform(chat.FakeTags.mark(message));
}

