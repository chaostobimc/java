import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import org.lwjgl.sdl.SDLKeyboard;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

public class KeyboardBoundsTest {
    private static void check(Method method, int key, Boolean expected) throws Exception {
        CallbackInfoReturnable<Boolean> result = new CallbackInfoReturnable<>();
        method.invoke(null, key, result);
        if (!java.util.Objects.equals(result.value, expected)) throw new AssertionError("key " + key + ": " + result.value);
    }
    public static void main(String[] args) throws Exception {
        Method guard = Class.forName("net.friendswithmoney.fwm.mixin.KeyboardBoundsMixin")
                .getDeclaredMethod("fwm$guardKeyboardIndex", int.class, CallbackInfoReturnable.class);
        guard.setAccessible(true);
        SDLKeyboard.state = ByteBuffer.allocate(512);
        check(guard, -1, false); check(guard, 0, null); check(guard, 511, null);
        check(guard, 512, false); check(guard, Integer.MAX_VALUE, false);
        SDLKeyboard.state.limit(4);
        check(guard, 3, null); check(guard, 4, false);
        SDLKeyboard.state = null; check(guard, 1, false);
        System.out.println("Keyboard bounds checks passed");
    }
}
