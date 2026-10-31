// class net/friendswithmoney/fwm/controller/SellController$Step  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field OFF Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field SEND Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field AWAIT Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field SELL Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field PAUSE Lnet/friendswithmoney/fwm/controller/SellController$Step;;
field $VALUES [Lnet/friendswithmoney/fwm/controller/SellController$Step;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.controller.SellController$Step;)controller.SellController$Step.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((controller.SellController$Step)Enum.valueOf(controller.SellController$Step.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static controller.SellController$Step[] $values() {
    new controller.SellController$Step[5][0] = controller.SellController$Step.OFF;
    new controller.SellController$Step[5][1] = controller.SellController$Step.SEND;
    new controller.SellController$Step[5][2] = controller.SellController$Step.AWAIT;
    new controller.SellController$Step[5][3] = controller.SellController$Step.SELL;
    new controller.SellController$Step[5][4] = controller.SellController$Step.PAUSE;
    return new controller.SellController$Step[5];
}

static controller.SellController$Step[] <clinit>() {
    controller.SellController$Step.OFF = ?;
    controller.SellController$Step.SEND = ?;
    controller.SellController$Step.AWAIT = ?;
    controller.SellController$Step.SELL = ?;
    controller.SellController$Step.PAUSE = ?;
    controller.SellController$Step.$VALUES = controller.SellController$Step.$values();
    return;
}

