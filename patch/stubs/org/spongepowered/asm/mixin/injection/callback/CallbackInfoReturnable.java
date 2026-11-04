package org.spongepowered.asm.mixin.injection.callback;
public class CallbackInfoReturnable<T> {
    public T value;
    public void setReturnValue(T value) { this.value = value; }
}
