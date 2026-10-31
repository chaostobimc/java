// class net/friendswithmoney/fwm/auction/Giveaway$Status  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field NONE Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
field RUNNING Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
field PAUSED Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
field ENDED Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
field $VALUES [Lnet/friendswithmoney/fwm/auction/Giveaway$Status;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.auction.Giveaway$Status;)auction.Giveaway$Status.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((auction.Giveaway$Status)Enum.valueOf(auction.Giveaway$Status.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static auction.Giveaway$Status[] $values() {
    new auction.Giveaway$Status[4][0] = auction.Giveaway$Status.NONE;
    new auction.Giveaway$Status[4][1] = auction.Giveaway$Status.RUNNING;
    new auction.Giveaway$Status[4][2] = auction.Giveaway$Status.PAUSED;
    new auction.Giveaway$Status[4][3] = auction.Giveaway$Status.ENDED;
    return new auction.Giveaway$Status[4];
}

static auction.Giveaway$Status[] <clinit>() {
    auction.Giveaway$Status.NONE = ?;
    auction.Giveaway$Status.RUNNING = ?;
    auction.Giveaway$Status.PAUSED = ?;
    auction.Giveaway$Status.ENDED = ?;
    auction.Giveaway$Status.$VALUES = auction.Giveaway$Status.$values();
    return;
}

