// class net/friendswithmoney/fwm/ui/panel/GiveawayPanel$1  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field $SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status [I;
static int <clinit>() {
    ui.panel.GiveawayPanel$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status = new int[auction.Giveaway$Status.values().length];
    ui.panel.GiveawayPanel$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[auction.Giveaway$Status.RUNNING.ordinal()] = 1;
    goto L24;
    v0 = /*catch NoSuchFieldError*/ ex;
    L24:
    ui.panel.GiveawayPanel$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[auction.Giveaway$Status.PAUSED.ordinal()] = 2;
    goto L39;
    v0 = /*catch NoSuchFieldError*/ ex;
    L39:
    ui.panel.GiveawayPanel$1.$SwitchMap$net$friendswithmoney$fwm$auction$Giveaway$Status[auction.Giveaway$Status.ENDED.ordinal()] = 3;
    goto L54;
    v0 = /*catch NoSuchFieldError*/ ex;
    L54:
    return;
}

