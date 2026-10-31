// class net/friendswithmoney/fwm/money/MoneyEvents$1  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field $SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind [I;
static int <clinit>() {
    money.MoneyEvents$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind = new int[money.MoneyStore$Kind.values().length];
    money.MoneyEvents$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind[money.MoneyStore$Kind.AH_SELL.ordinal()] = 1;
    goto L24;
    v0 = /*catch NoSuchFieldError*/ ex;
    L24:
    money.MoneyEvents$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind[money.MoneyStore$Kind.AH_BUY.ordinal()] = 2;
    goto L39;
    v0 = /*catch NoSuchFieldError*/ ex;
    L39:
    money.MoneyEvents$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind[money.MoneyStore$Kind.ORDER_BUY.ordinal()] = 3;
    goto L54;
    v0 = /*catch NoSuchFieldError*/ ex;
    L54:
    money.MoneyEvents$1.$SwitchMap$net$friendswithmoney$fwm$money$MoneyStore$Kind[money.MoneyStore$Kind.ORDER.ordinal()] = 4;
    goto L69;
    v0 = /*catch NoSuchFieldError*/ ex;
    L69:
    return;
}

