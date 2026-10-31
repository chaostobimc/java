// class net/friendswithmoney/fwm/net/Market$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field EMPTY Lnet/friendswithmoney/fwm/net/Market$State;;
field LOADING Lnet/friendswithmoney/fwm/net/Market$State;;
field OK Lnet/friendswithmoney/fwm/net/Market$State;;
field ERROR Lnet/friendswithmoney/fwm/net/Market$State;;
field $VALUES [Lnet/friendswithmoney/fwm/net/Market$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.Market$State;)net.Market$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.Market$State)Enum.valueOf(net.Market$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.Market$State[] $values() {
    new net.Market$State[4][0] = net.Market$State.EMPTY;
    new net.Market$State[4][1] = net.Market$State.LOADING;
    new net.Market$State[4][2] = net.Market$State.OK;
    new net.Market$State[4][3] = net.Market$State.ERROR;
    return new net.Market$State[4];
}

static net.Market$State[] <clinit>() {
    net.Market$State.EMPTY = ?;
    net.Market$State.LOADING = ?;
    net.Market$State.OK = ?;
    net.Market$State.ERROR = ?;
    net.Market$State.$VALUES = net.Market$State.$values();
    return;
}

