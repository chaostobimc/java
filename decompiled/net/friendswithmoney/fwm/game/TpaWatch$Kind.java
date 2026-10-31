// class net/friendswithmoney/fwm/game/TpaWatch$Kind  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field TPA Lnet/friendswithmoney/fwm/game/TpaWatch$Kind;;
field TPAHERE Lnet/friendswithmoney/fwm/game/TpaWatch$Kind;;
field $VALUES [Lnet/friendswithmoney/fwm/game/TpaWatch$Kind;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.game.TpaWatch$Kind;)game.TpaWatch$Kind.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((game.TpaWatch$Kind)Enum.valueOf(game.TpaWatch$Kind.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static game.TpaWatch$Kind[] $values() {
    new game.TpaWatch$Kind[2][0] = game.TpaWatch$Kind.TPA;
    new game.TpaWatch$Kind[2][1] = game.TpaWatch$Kind.TPAHERE;
    return new game.TpaWatch$Kind[2];
}

static game.TpaWatch$Kind[] <clinit>() {
    game.TpaWatch$Kind.TPA = ?;
    game.TpaWatch$Kind.TPAHERE = ?;
    game.TpaWatch$Kind.$VALUES = game.TpaWatch$Kind.$values();
    return;
}

