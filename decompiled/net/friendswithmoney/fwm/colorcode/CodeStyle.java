// class net/friendswithmoney/fwm/colorcode/CodeStyle  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field color Ljava/lang/String;;
field bold Z;
field underline Z;
field italic Z;
field strike Z;
field obf Z;
field DEFAULT Lnet/friendswithmoney/fwm/colorcode/CodeStyle;;
public String <init>(String color, boolean bold, boolean underline, boolean italic, boolean strike, boolean obf) {
    if (color == null) goto L16;
    color = color.toUpperCase(Locale.ROOT);
    L16:
    this.color = color;
    this.bold = bold;
    this.underline = underline;
    this.italic = italic;
    this.strike = strike;
    this.obf = obf;
    return;
}

public int isHex() {
    if (this.color == null) goto L23;
    if (this.color.length() != 6) goto L23;
    goto L24;
    L23:
    L24:
    return 0;
}

public void colorOnly(String newColor) {
    return ?;
}

public void withColor(String newColor) {
    return ?;
}

public void has(colorcode.CodeStyle$Format format) {
    // switch (format.ordinal()) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=36), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=4), Operand(op_type=<OperandTypes.BRANCH: 40>, value=46), Operand(op_type=<OperandTypes.BRANCH: 40>, value=53), Operand(op_type=<OperandTypes.BRANCH: 40>, value=60), Operand(op_type=<OperandTypes.BRANCH: 40>, value=67), Operand(op_type=<OperandTypes.BRANCH: 40>, value=74)]
    L40:
    throw ?;
    L50:
    goto L82;
    L57:
    goto L82;
    L64:
    goto L82;
    L71:
    goto L82;
    L78:
    L82:
    return this.obf;
}

public void with(colorcode.CodeStyle$Format format, boolean on) {
    if (format != colorcode.CodeStyle$Format.BOLD) goto L19;
    goto L23;
    L19:
    L23:
    if (format != colorcode.CodeStyle$Format.UNDERLINE) goto L34;
    goto L38;
    L34:
    L38:
    if (format != colorcode.CodeStyle$Format.ITALIC) goto L49;
    goto L53;
    L49:
    L53:
    if (format != colorcode.CodeStyle$Format.STRIKE) goto L64;
    goto L68;
    L64:
    L68:
    if (format != colorcode.CodeStyle$Format.OBF) goto L79;
    goto L83;
    L79:
    L83:
    return on;
}

public final String toString() {
    return /*indy*/toString(this);
}

public final int hashCode() {
    return /*indy*/hashCode(this);
}

public final boolean equals(Object o) {
    return /*indy*/equals(this, o);
}

public String color() {
    return this.color;
}

public boolean bold() {
    return this.bold;
}

public boolean underline() {
    return this.underline;
}

public boolean italic() {
    return this.italic;
}

public boolean strike() {
    return this.strike;
}

public boolean obf() {
    return this.obf;
}

static void <clinit>() {
    colorcode.CodeStyle.DEFAULT = ?;
    return;
}

