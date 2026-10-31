// class net/friendswithmoney/fwm/net/Spawners$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field EMPTY Lnet/friendswithmoney/fwm/net/Spawners$State;;
field LOADING Lnet/friendswithmoney/fwm/net/Spawners$State;;
field OK Lnet/friendswithmoney/fwm/net/Spawners$State;;
field ERROR Lnet/friendswithmoney/fwm/net/Spawners$State;;
field $VALUES [Lnet/friendswithmoney/fwm/net/Spawners$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.net.Spawners$State;)net.Spawners$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((net.Spawners$State)Enum.valueOf(net.Spawners$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static net.Spawners$State[] $values() {
    new net.Spawners$State[4][0] = net.Spawners$State.EMPTY;
    new net.Spawners$State[4][1] = net.Spawners$State.LOADING;
    new net.Spawners$State[4][2] = net.Spawners$State.OK;
    new net.Spawners$State[4][3] = net.Spawners$State.ERROR;
    return new net.Spawners$State[4];
}

static net.Spawners$State[] <clinit>() {
    net.Spawners$State.EMPTY = ?;
    net.Spawners$State.LOADING = ?;
    net.Spawners$State.OK = ?;
    net.Spawners$State.ERROR = ?;
    net.Spawners$State.$VALUES = net.Spawners$State.$values();
    return;
}

