// class net/friendswithmoney/fwm/net/ModAccount$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field NONE Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field CHECKING Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field OK Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field INVALID Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field ERROR Lnet/friendswithmoney/fwm/net/ModAccount$State;;
field $VALUES [Lnet/friendswithmoney/fwm/net/ModAccount$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.ModAccount$State;)net.ModAccount$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.ModAccount$State)Enum.valueOf(net.ModAccount$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.ModAccount$State[] $values() {
    new net.ModAccount$State[5][0] = net.ModAccount$State.NONE;
    new net.ModAccount$State[5][1] = net.ModAccount$State.CHECKING;
    new net.ModAccount$State[5][2] = net.ModAccount$State.OK;
    new net.ModAccount$State[5][3] = net.ModAccount$State.INVALID;
    new net.ModAccount$State[5][4] = net.ModAccount$State.ERROR;
    return new net.ModAccount$State[5];
}

static net.ModAccount$State[] <clinit>() {
    net.ModAccount$State.NONE = ?;
    net.ModAccount$State.CHECKING = ?;
    net.ModAccount$State.OK = ?;
    net.ModAccount$State.INVALID = ?;
    net.ModAccount$State.ERROR = ?;
    net.ModAccount$State.$VALUES = net.ModAccount$State.$values();
    return;
}

