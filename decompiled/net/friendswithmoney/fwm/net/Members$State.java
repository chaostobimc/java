// class net/friendswithmoney/fwm/net/Members$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field EMPTY Lnet/friendswithmoney/fwm/net/Members$State;;
field LOADING Lnet/friendswithmoney/fwm/net/Members$State;;
field OK Lnet/friendswithmoney/fwm/net/Members$State;;
field ERROR Lnet/friendswithmoney/fwm/net/Members$State;;
field $VALUES [Lnet/friendswithmoney/fwm/net/Members$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.Members$State;)net.Members$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.Members$State)Enum.valueOf(net.Members$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.Members$State[] $values() {
    new net.Members$State[4][0] = net.Members$State.EMPTY;
    new net.Members$State[4][1] = net.Members$State.LOADING;
    new net.Members$State[4][2] = net.Members$State.OK;
    new net.Members$State[4][3] = net.Members$State.ERROR;
    return new net.Members$State[4];
}

static net.Members$State[] <clinit>() {
    net.Members$State.EMPTY = ?;
    net.Members$State.LOADING = ?;
    net.Members$State.OK = ?;
    net.Members$State.ERROR = ?;
    net.Members$State.$VALUES = net.Members$State.$values();
    return;
}

