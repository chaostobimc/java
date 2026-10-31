// class net/friendswithmoney/fwm/seventv/EmoteTexture$State  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field LOADING Lnet/friendswithmoney/fwm/seventv/EmoteTexture$State;;
field READY Lnet/friendswithmoney/fwm/seventv/EmoteTexture$State;;
field FAILED Lnet/friendswithmoney/fwm/seventv/EmoteTexture$State;;
field $VALUES [Lnet/friendswithmoney/fwm/seventv/EmoteTexture$State;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.seventv.EmoteTexture$State;)seventv.EmoteTexture$State.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((seventv.EmoteTexture$State)Enum.valueOf(seventv.EmoteTexture$State.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static seventv.EmoteTexture$State[] $values() {
    new seventv.EmoteTexture$State[3][0] = seventv.EmoteTexture$State.LOADING;
    new seventv.EmoteTexture$State[3][1] = seventv.EmoteTexture$State.READY;
    new seventv.EmoteTexture$State[3][2] = seventv.EmoteTexture$State.FAILED;
    return new seventv.EmoteTexture$State[3];
}

static seventv.EmoteTexture$State[] <clinit>() {
    seventv.EmoteTexture$State.LOADING = ?;
    seventv.EmoteTexture$State.READY = ?;
    seventv.EmoteTexture$State.FAILED = ?;
    seventv.EmoteTexture$State.$VALUES = seventv.EmoteTexture$State.$values();
    return;
}

