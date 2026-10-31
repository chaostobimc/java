// class net/friendswithmoney/fwm/mixin/DebugHudMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private List fwm$fakeCoords(List lines) {
    return antileak.FakeCoords.rewrite(lines);
}

