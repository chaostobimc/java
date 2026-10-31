// class net/friendswithmoney/fwm/net/RtpPoints$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field EMPTY Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
field LOADING Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
field OK Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
field ERROR Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
field $VALUES [Lnet/friendswithmoney/fwm/net/RtpPoints$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.RtpPoints$State;)net.RtpPoints$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.RtpPoints$State)Enum.valueOf(net.RtpPoints$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.RtpPoints$State[] $values() {
    new net.RtpPoints$State[4][0] = net.RtpPoints$State.EMPTY;
    new net.RtpPoints$State[4][1] = net.RtpPoints$State.LOADING;
    new net.RtpPoints$State[4][2] = net.RtpPoints$State.OK;
    new net.RtpPoints$State[4][3] = net.RtpPoints$State.ERROR;
    return new net.RtpPoints$State[4];
}

static net.RtpPoints$State[] <clinit>() {
    net.RtpPoints$State.EMPTY = ?;
    net.RtpPoints$State.LOADING = ?;
    net.RtpPoints$State.OK = ?;
    net.RtpPoints$State.ERROR = ?;
    net.RtpPoints$State.$VALUES = net.RtpPoints$State.$values();
    return;
}

