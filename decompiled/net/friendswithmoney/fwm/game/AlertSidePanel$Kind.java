// class net/friendswithmoney/fwm/game/AlertSidePanel$Kind  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field NONE Lnet/friendswithmoney/fwm/game/AlertSidePanel$Kind;;
field AH Lnet/friendswithmoney/fwm/game/AlertSidePanel$Kind;;
field ORDER Lnet/friendswithmoney/fwm/game/AlertSidePanel$Kind;;
field $VALUES [Lnet/friendswithmoney/fwm/game/AlertSidePanel$Kind;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.game.AlertSidePanel$Kind;)game.AlertSidePanel$Kind.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((game.AlertSidePanel$Kind)Enum.valueOf(game.AlertSidePanel$Kind.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static game.AlertSidePanel$Kind[] $values() {
    new game.AlertSidePanel$Kind[3][0] = game.AlertSidePanel$Kind.NONE;
    new game.AlertSidePanel$Kind[3][1] = game.AlertSidePanel$Kind.AH;
    new game.AlertSidePanel$Kind[3][2] = game.AlertSidePanel$Kind.ORDER;
    return new game.AlertSidePanel$Kind[3];
}

static game.AlertSidePanel$Kind[] <clinit>() {
    game.AlertSidePanel$Kind.NONE = ?;
    game.AlertSidePanel$Kind.AH = ?;
    game.AlertSidePanel$Kind.ORDER = ?;
    game.AlertSidePanel$Kind.$VALUES = game.AlertSidePanel$Kind.$values();
    return;
}

