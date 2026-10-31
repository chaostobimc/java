// class net/friendswithmoney/fwm/ui/panel/RtpPanel$1  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field $SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim [I;
static int <clinit>() {
    ui.panel.RtpPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim = new int[net.RtpPoints$Dim.values().length];
    ui.panel.RtpPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim[net.RtpPoints$Dim.OVERWORLD.ordinal()] = 1;
    goto L24;
    v0 = /*catch NoSuchFieldError*/ ex;
    L24:
    ui.panel.RtpPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim[net.RtpPoints$Dim.NETHER.ordinal()] = 2;
    goto L39;
    v0 = /*catch NoSuchFieldError*/ ex;
    L39:
    ui.panel.RtpPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$RtpPoints$Dim[net.RtpPoints$Dim.END.ordinal()] = 3;
    goto L54;
    v0 = /*catch NoSuchFieldError*/ ex;
    L54:
    return;
}

