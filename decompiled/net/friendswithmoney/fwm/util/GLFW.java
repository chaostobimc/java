// class net/friendswithmoney/fwm/util/GLFW  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field GLFW_KEY_UNKNOWN I;
field GLFW_KEY_ESCAPE I;
field GLFW_KEY_ENTER I;
field GLFW_KEY_KP_ENTER I;
field GLFW_KEY_BACKSPACE I;
field GLFW_KEY_LEFT I;
field GLFW_KEY_RIGHT I;
field GLFW_KEY_MINUS I;
field GLFW_KEY_TAB I;
field GLFW_KEY_UP I;
field GLFW_KEY_DOWN I;
field GLFW_KEY_F I;
field GLFW_MOUSE_BUTTON_LEFT I;
field GLFW_KEY_A I;
field GLFW_KEY_B I;
field GLFW_KEY_C I;
field GLFW_KEY_H I;
field GLFW_KEY_J I;
field GLFW_KEY_K I;
field GLFW_KEY_M I;
field GLFW_KEY_0 I;
field GLFW_KEY_9 I;
field GLFW_KEY_KP_0 I;
field GLFW_KEY_KP_9 I;
field GLFW_KEY_KP_DECIMAL I;
field GLFW_KEY_PERIOD I;
field GLFW_KEY_COMMA I;
field GLFW_KEY_R I;
field GLFW_KEY_S I;
field GLFW_KEY_Y I;
field GLFW_KEY_Z I;
field GLFW_MOD_SHIFT I;
field DIGITS [I;
field NUMPAD [I;
private void <init>() {
    return;
}

public static int digit(int keyCode) {
    v1 = 0;
    L2:
    if (v1 >= 10) goto L34;
    if (util.GLFW.DIGITS[v1] == keyCode) goto L26;
    if (util.GLFW.NUMPAD[v1] != keyCode) goto L28;
    L26:
    return v1;
    L28:
    v1 = v1 + 1;
    goto L2;
    L34:
    return m1;
}

public static char letter(int keyCode) {
    if (keyCode < 4) goto L19;
    if (keyCode > 29) goto L19;
    return ((c)((65 + keyCode) - 4));
    L19:
    return 0;
}

public static int button(int sdl) {
    // switch (sdl) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=39), Operand(op_type=<OperandTypes.LITERAL: 10>, value=1), Operand(op_type=<OperandTypes.LITERAL: 10>, value=3), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=31), Operand(op_type=<OperandTypes.BRANCH: 40>, value=35)]
    L28:
    goto L43;
    L32:
    goto L43;
    L36:
    goto L43;
    L40:
    L43:
    return (sdl - 1);
}

public static int sdlButton(int button) {
    // switch (button) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=39), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=2), Operand(op_type=<OperandTypes.BRANCH: 40>, value=27), Operand(op_type=<OperandTypes.BRANCH: 40>, value=31), Operand(op_type=<OperandTypes.BRANCH: 40>, value=35)]
    L28:
    goto L43;
    L32:
    goto L43;
    L36:
    goto L43;
    L40:
    L43:
    return (button + 1);
}

static void <clinit>() {
    new int[10][0] = 39;
    new int[10][1] = 30;
    new int[10][2] = 31;
    new int[10][3] = 32;
    new int[10][4] = 33;
    new int[10][5] = 34;
    new int[10][6] = 35;
    new int[10][7] = 36;
    new int[10][8] = 37;
    new int[10][9] = 38;
    util.GLFW.DIGITS = new int[10];
    new int[10][0] = 98;
    new int[10][1] = 89;
    new int[10][2] = 90;
    new int[10][3] = 91;
    new int[10][4] = 92;
    new int[10][5] = 93;
    new int[10][6] = 94;
    new int[10][7] = 95;
    new int[10][8] = 96;
    new int[10][9] = 97;
    util.GLFW.NUMPAD = new int[10];
    return;
}

