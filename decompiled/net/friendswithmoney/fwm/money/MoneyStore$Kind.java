// class net/friendswithmoney/fwm/money/MoneyStore$Kind  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field SELL Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field PAY_IN Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field PAY_OUT Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field AH_SELL Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field AH_BUY Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field OTHER_IN Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field OTHER_OUT Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field ORDER Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field ORDER_BUY Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field FEE Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
field income Z;
field $VALUES [Lnet/friendswithmoney/fwm/money/MoneyStore$Kind;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.money.MoneyStore$Kind;)money.MoneyStore$Kind.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((money.MoneyStore$Kind)Enum.valueOf(money.MoneyStore$Kind.class, name));
}

private void <init>(String a1, int a2, boolean income) {
    this.income = income;
    return;
}

private static money.MoneyStore$Kind[] $values() {
    new money.MoneyStore$Kind[10][0] = money.MoneyStore$Kind.SELL;
    new money.MoneyStore$Kind[10][1] = money.MoneyStore$Kind.PAY_IN;
    new money.MoneyStore$Kind[10][2] = money.MoneyStore$Kind.PAY_OUT;
    new money.MoneyStore$Kind[10][3] = money.MoneyStore$Kind.AH_SELL;
    new money.MoneyStore$Kind[10][4] = money.MoneyStore$Kind.AH_BUY;
    new money.MoneyStore$Kind[10][5] = money.MoneyStore$Kind.OTHER_IN;
    new money.MoneyStore$Kind[10][6] = money.MoneyStore$Kind.OTHER_OUT;
    new money.MoneyStore$Kind[10][7] = money.MoneyStore$Kind.ORDER;
    new money.MoneyStore$Kind[10][8] = money.MoneyStore$Kind.ORDER_BUY;
    new money.MoneyStore$Kind[10][9] = money.MoneyStore$Kind.FEE;
    return new money.MoneyStore$Kind[10];
}

static money.MoneyStore$Kind[] <clinit>() {
    money.MoneyStore$Kind.SELL = ?;
    money.MoneyStore$Kind.PAY_IN = ?;
    money.MoneyStore$Kind.PAY_OUT = ?;
    money.MoneyStore$Kind.AH_SELL = ?;
    money.MoneyStore$Kind.AH_BUY = ?;
    money.MoneyStore$Kind.OTHER_IN = ?;
    money.MoneyStore$Kind.OTHER_OUT = ?;
    money.MoneyStore$Kind.ORDER = ?;
    money.MoneyStore$Kind.ORDER_BUY = ?;
    money.MoneyStore$Kind.FEE = ?;
    money.MoneyStore$Kind.$VALUES = money.MoneyStore$Kind.$values();
    return;
}

