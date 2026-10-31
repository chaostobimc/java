// class net/friendswithmoney/fwm/net/ModNews$Kind  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field INFOS Lnet/friendswithmoney/fwm/net/ModNews$Kind;;
field BUGS Lnet/friendswithmoney/fwm/net/ModNews$Kind;;
field FIXES Lnet/friendswithmoney/fwm/net/ModNews$Kind;;
field $VALUES [Lnet/friendswithmoney/fwm/net/ModNews$Kind;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.ModNews$Kind;)net.ModNews$Kind.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.ModNews$Kind)Enum.valueOf(net.ModNews$Kind.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.ModNews$Kind[] $values() {
    new net.ModNews$Kind[3][0] = net.ModNews$Kind.INFOS;
    new net.ModNews$Kind[3][1] = net.ModNews$Kind.BUGS;
    new net.ModNews$Kind[3][2] = net.ModNews$Kind.FIXES;
    return new net.ModNews$Kind[3];
}

static net.ModNews$Kind[] <clinit>() {
    net.ModNews$Kind.INFOS = ?;
    net.ModNews$Kind.BUGS = ?;
    net.ModNews$Kind.FIXES = ?;
    net.ModNews$Kind.$VALUES = net.ModNews$Kind.$values();
    return;
}

