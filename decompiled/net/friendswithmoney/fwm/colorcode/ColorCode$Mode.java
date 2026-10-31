// class net/friendswithmoney/fwm/colorcode/ColorCode$Mode  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field RENAME Lnet/friendswithmoney/fwm/colorcode/ColorCode$Mode;;
field SIGN Lnet/friendswithmoney/fwm/colorcode/ColorCode$Mode;;
field command Ljava/lang/String;;
field limit I;
field $VALUES [Lnet/friendswithmoney/fwm/colorcode/ColorCode$Mode;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.colorcode.ColorCode$Mode;)colorcode.ColorCode$Mode.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((colorcode.ColorCode$Mode)Enum.valueOf(colorcode.ColorCode$Mode.class, name));
}

private void <init>(String a1, int a2, String command, int limit) {
    this.command = command;
    this.limit = limit;
    return;
}

private static colorcode.ColorCode$Mode[] $values() {
    new colorcode.ColorCode$Mode[2][0] = colorcode.ColorCode$Mode.RENAME;
    new colorcode.ColorCode$Mode[2][1] = colorcode.ColorCode$Mode.SIGN;
    return new colorcode.ColorCode$Mode[2];
}

static colorcode.ColorCode$Mode[] <clinit>() {
    colorcode.ColorCode$Mode.RENAME = ?;
    colorcode.ColorCode$Mode.SIGN = ?;
    colorcode.ColorCode$Mode.$VALUES = colorcode.ColorCode$Mode.$values();
    return;
}

