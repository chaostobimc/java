// class net/friendswithmoney/fwm/mixin/EntityRendererMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$logo(world.entity.Entity entity, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if ((entity instanceof world.entity.player.Player) ==0) goto L15;
    v3 = ((world.entity.player.Player)entity);
    goto L16;
    L15:
    return;
    L16:
    v4 = ((network.chat.Component)cir.getReturnValue());
    cir.setReturnValue(net.Presence.decorate(v3.getUUID(), v4));
    return;
}

