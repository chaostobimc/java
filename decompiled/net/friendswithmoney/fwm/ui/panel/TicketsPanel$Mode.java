// class net/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field BUY Lnet/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode;;
field SELL Lnet/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode;;
field MIDDLEMAN Lnet/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode;;
field $VALUES [Lnet/friendswithmoney/fwm/ui/panel/TicketsPanel$Mode;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.ui.panel.TicketsPanel$Mode;)ui.panel.TicketsPanel$Mode.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((ui.panel.TicketsPanel$Mode)Enum.valueOf(ui.panel.TicketsPanel$Mode.class, name));
}

private void <init>(String a1, int a2) {
    return;
}

private static ui.panel.TicketsPanel$Mode[] $values() {
    new ui.panel.TicketsPanel$Mode[3][0] = ui.panel.TicketsPanel$Mode.BUY;
    new ui.panel.TicketsPanel$Mode[3][1] = ui.panel.TicketsPanel$Mode.SELL;
    new ui.panel.TicketsPanel$Mode[3][2] = ui.panel.TicketsPanel$Mode.MIDDLEMAN;
    return new ui.panel.TicketsPanel$Mode[3];
}

static ui.panel.TicketsPanel$Mode[] <clinit>() {
    ui.panel.TicketsPanel$Mode.BUY = ?;
    ui.panel.TicketsPanel$Mode.SELL = ?;
    ui.panel.TicketsPanel$Mode.MIDDLEMAN = ?;
    ui.panel.TicketsPanel$Mode.$VALUES = ui.panel.TicketsPanel$Mode.$values();
    return;
}

