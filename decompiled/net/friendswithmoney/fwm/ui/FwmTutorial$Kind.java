// class net/friendswithmoney/fwm/ui/FwmTutorial$Kind  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field TEXT Lnet/friendswithmoney/fwm/ui/FwmTutorial$Kind;;
field PICKER Lnet/friendswithmoney/fwm/ui/FwmTutorial$Kind;;
field OVERLAY Lnet/friendswithmoney/fwm/ui/FwmTutorial$Kind;;
field $VALUES [Lnet/friendswithmoney/fwm/ui/FwmTutorial$Kind;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.ui.FwmTutorial$Kind;)ui.FwmTutorial$Kind.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((ui.FwmTutorial$Kind)Enum.valueOf(ui.FwmTutorial$Kind.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static ui.FwmTutorial$Kind[] $values() {
    new ui.FwmTutorial$Kind[3][0] = ui.FwmTutorial$Kind.TEXT;
    new ui.FwmTutorial$Kind[3][1] = ui.FwmTutorial$Kind.PICKER;
    new ui.FwmTutorial$Kind[3][2] = ui.FwmTutorial$Kind.OVERLAY;
    return new ui.FwmTutorial$Kind[3];
}

static ui.FwmTutorial$Kind[] <clinit>() {
    ui.FwmTutorial$Kind.TEXT = ?;
    ui.FwmTutorial$Kind.PICKER = ?;
    ui.FwmTutorial$Kind.OVERLAY = ?;
    ui.FwmTutorial$Kind.$VALUES = ui.FwmTutorial$Kind.$values();
    return;
}

