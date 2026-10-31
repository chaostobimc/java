// class net/friendswithmoney/fwm/colorcode/CodeStyle$Format  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field BOLD Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field UNDERLINE Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field ITALIC Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field STRIKE Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field OBF Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
field code C;
field $VALUES [Lnet/friendswithmoney/fwm/colorcode/CodeStyle$Format;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.colorcode.CodeStyle$Format;)colorcode.CodeStyle$Format.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((colorcode.CodeStyle$Format)Enum.valueOf(colorcode.CodeStyle$Format.class, name));
}

private void <init>(String a1, int a2, char code) {
    this.code = code;
    return;
}

private static colorcode.CodeStyle$Format[] $values() {
    new colorcode.CodeStyle$Format[5][0] = colorcode.CodeStyle$Format.BOLD;
    new colorcode.CodeStyle$Format[5][1] = colorcode.CodeStyle$Format.UNDERLINE;
    new colorcode.CodeStyle$Format[5][2] = colorcode.CodeStyle$Format.ITALIC;
    new colorcode.CodeStyle$Format[5][3] = colorcode.CodeStyle$Format.STRIKE;
    new colorcode.CodeStyle$Format[5][4] = colorcode.CodeStyle$Format.OBF;
    return new colorcode.CodeStyle$Format[5];
}

static colorcode.CodeStyle$Format[] <clinit>() {
    colorcode.CodeStyle$Format.BOLD = ?;
    colorcode.CodeStyle$Format.UNDERLINE = ?;
    colorcode.CodeStyle$Format.ITALIC = ?;
    colorcode.CodeStyle$Format.STRIKE = ?;
    colorcode.CodeStyle$Format.OBF = ?;
    colorcode.CodeStyle$Format.$VALUES = colorcode.CodeStyle$Format.$values();
    return;
}

