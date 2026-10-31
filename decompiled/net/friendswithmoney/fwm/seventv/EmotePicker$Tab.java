// class net/friendswithmoney/fwm/seventv/EmotePicker$Tab  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field ALL Lnet/friendswithmoney/fwm/seventv/EmotePicker$Tab;;
field SEVENTV Lnet/friendswithmoney/fwm/seventv/EmotePicker$Tab;;
field BTTV Lnet/friendswithmoney/fwm/seventv/EmotePicker$Tab;;
field label Ljava/lang/String;;
field $VALUES [Lnet/friendswithmoney/fwm/seventv/EmotePicker$Tab;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.seventv.EmotePicker$Tab;)seventv.EmotePicker$Tab.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((seventv.EmotePicker$Tab)Enum.valueOf(seventv.EmotePicker$Tab.class, name));
}

private void <init>(String a1, int a2, String l) {
    this.label = l;
    return;
}

private static seventv.EmotePicker$Tab[] $values() {
    new seventv.EmotePicker$Tab[3][0] = seventv.EmotePicker$Tab.ALL;
    new seventv.EmotePicker$Tab[3][1] = seventv.EmotePicker$Tab.SEVENTV;
    new seventv.EmotePicker$Tab[3][2] = seventv.EmotePicker$Tab.BTTV;
    return new seventv.EmotePicker$Tab[3];
}

static seventv.EmotePicker$Tab[] <clinit>() {
    seventv.EmotePicker$Tab.ALL = ?;
    seventv.EmotePicker$Tab.SEVENTV = ?;
    seventv.EmotePicker$Tab.BTTV = ?;
    seventv.EmotePicker$Tab.$VALUES = seventv.EmotePicker$Tab.$values();
    return;
}

