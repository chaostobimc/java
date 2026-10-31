// class net/friendswithmoney/fwm/ui/panel/TicketsPanel$1  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field $SwitchMap$net$friendswithmoney$fwm$net$ModAccount$State [I;
static int <clinit>() {
    ui.panel.TicketsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModAccount$State = new int[net.ModAccount$State.values().length];
    ui.panel.TicketsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModAccount$State[net.ModAccount$State.OK.ordinal()] = 1;
    goto L24;
    v0 = /*catch NoSuchFieldError*/ ex;
    L24:
    ui.panel.TicketsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModAccount$State[net.ModAccount$State.CHECKING.ordinal()] = 2;
    goto L39;
    v0 = /*catch NoSuchFieldError*/ ex;
    L39:
    return;
}

