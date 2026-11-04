/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 */
package net.friendswithmoney.fwm.util;

public final class GLFW {
    public static final int GLFW_KEY_UNKNOWN = -1;
    public static final int GLFW_KEY_ESCAPE = 41;
    public static final int GLFW_KEY_ENTER = 40;
    public static final int GLFW_KEY_KP_ENTER = 88;
    public static final int GLFW_KEY_BACKSPACE = 42;
    public static final int GLFW_KEY_LEFT = 80;
    public static final int GLFW_KEY_RIGHT = 79;
    public static final int GLFW_KEY_MINUS = 45;
    public static final int GLFW_KEY_TAB = 43;
    public static final int GLFW_KEY_UP = 82;
    public static final int GLFW_KEY_DOWN = 81;
    public static final int GLFW_KEY_F = 9;
    public static final int GLFW_MOUSE_BUTTON_LEFT = 1;
    public static final int GLFW_KEY_A = 4;
    public static final int GLFW_KEY_B = 5;
    public static final int GLFW_KEY_C = 6;
    public static final int GLFW_KEY_H = 11;
    public static final int GLFW_KEY_J = 13;
    public static final int GLFW_KEY_K = 14;
    public static final int GLFW_KEY_M = 16;
    public static final int GLFW_KEY_0 = 48;
    public static final int GLFW_KEY_9 = 57;
    public static final int GLFW_KEY_KP_0 = 320;
    public static final int GLFW_KEY_KP_9 = 329;
    public static final int GLFW_KEY_KP_DECIMAL = 330;
    public static final int GLFW_KEY_PERIOD = 46;
    public static final int GLFW_KEY_COMMA = 44;
    public static final int GLFW_KEY_R = 21;
    public static final int GLFW_KEY_S = 22;
    public static final int GLFW_KEY_Y = 28;
    public static final int GLFW_KEY_Z = 29;
    public static final int GLFW_MOD_SHIFT = 3;
    private static final int[] DIGITS = new int[]{39, 30, 31, 32, 33, 34, 35, 36, 37, 38};
    private static final int[] NUMPAD = new int[]{98, 89, 90, 91, 92, 93, 94, 95, 96, 97};

    private GLFW() {
    }

    public static int digit(int keyCode) {
        for (int i = 0; i < 10; ++i) {
            if (DIGITS[i] != keyCode && NUMPAD[i] != keyCode) continue;
            return i;
        }
        return -1;
    }

    public static char letter(int keyCode) {
        if (keyCode >= 4 && keyCode <= 29) {
            return (char)(65 + keyCode - 4);
        }
        return '\u0000';
    }

    public static int button(int sdl) {
        return switch (sdl) {
            case 1 -> 0;
            case 2 -> 2;
            case 3 -> 1;
            default -> sdl - 1;
        };
    }

    public static int sdlButton(int button) {
        return switch (button) {
            case 0 -> 1;
            case 1 -> 3;
            case 2 -> 2;
            default -> button + 1;
        };
    }
}
