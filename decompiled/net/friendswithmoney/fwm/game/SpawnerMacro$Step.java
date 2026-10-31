// class net/friendswithmoney/fwm/game/SpawnerMacro$Step  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field OFF Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field AIM Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field OPEN Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field AWAIT Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field READ Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field DROP_WAIT Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field CONFIRM Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field REOPEN Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field DONE Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field THROW Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field ROUND_WAIT Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
field $VALUES [Lnet/friendswithmoney/fwm/game/SpawnerMacro$Step;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.game.SpawnerMacro$Step;)game.SpawnerMacro$Step.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((game.SpawnerMacro$Step)Enum.valueOf(game.SpawnerMacro$Step.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static game.SpawnerMacro$Step[] $values() {
    new game.SpawnerMacro$Step[11][0] = game.SpawnerMacro$Step.OFF;
    new game.SpawnerMacro$Step[11][1] = game.SpawnerMacro$Step.AIM;
    new game.SpawnerMacro$Step[11][2] = game.SpawnerMacro$Step.OPEN;
    new game.SpawnerMacro$Step[11][3] = game.SpawnerMacro$Step.AWAIT;
    new game.SpawnerMacro$Step[11][4] = game.SpawnerMacro$Step.READ;
    new game.SpawnerMacro$Step[11][5] = game.SpawnerMacro$Step.DROP_WAIT;
    new game.SpawnerMacro$Step[11][6] = game.SpawnerMacro$Step.CONFIRM;
    new game.SpawnerMacro$Step[11][7] = game.SpawnerMacro$Step.REOPEN;
    new game.SpawnerMacro$Step[11][8] = game.SpawnerMacro$Step.DONE;
    new game.SpawnerMacro$Step[11][9] = game.SpawnerMacro$Step.THROW;
    new game.SpawnerMacro$Step[11][10] = game.SpawnerMacro$Step.ROUND_WAIT;
    return new game.SpawnerMacro$Step[11];
}

static game.SpawnerMacro$Step[] <clinit>() {
    game.SpawnerMacro$Step.OFF = ?;
    game.SpawnerMacro$Step.AIM = ?;
    game.SpawnerMacro$Step.OPEN = ?;
    game.SpawnerMacro$Step.AWAIT = ?;
    game.SpawnerMacro$Step.READ = ?;
    game.SpawnerMacro$Step.DROP_WAIT = ?;
    game.SpawnerMacro$Step.CONFIRM = ?;
    game.SpawnerMacro$Step.REOPEN = ?;
    game.SpawnerMacro$Step.DONE = ?;
    game.SpawnerMacro$Step.THROW = ?;
    game.SpawnerMacro$Step.ROUND_WAIT = ?;
    game.SpawnerMacro$Step.$VALUES = game.SpawnerMacro$Step.$values();
    return;
}

