// class net/friendswithmoney/fwm/ui/panel/NewsPanel$1  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field $SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind [I;
static int <clinit>() {
    ui.panel.NewsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind = new int[net.ModNews$Kind.values().length];
    ui.panel.NewsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind[net.ModNews$Kind.INFOS.ordinal()] = 1;
    goto L24;
    v0 = /*catch NoSuchFieldError*/ ex;
    L24:
    ui.panel.NewsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind[net.ModNews$Kind.BUGS.ordinal()] = 2;
    goto L39;
    v0 = /*catch NoSuchFieldError*/ ex;
    L39:
    ui.panel.NewsPanel$1.$SwitchMap$net$friendswithmoney$fwm$net$ModNews$Kind[net.ModNews$Kind.FIXES.ordinal()] = 3;
    goto L54;
    v0 = /*catch NoSuchFieldError*/ ex;
    L54:
    return;
}

