package org.lwjgl.sdl;
import java.nio.ByteBuffer;
public class SDLKeyboard {
    public static ByteBuffer state;
    public static ByteBuffer SDL_GetKeyboardState() { return state; }
}
