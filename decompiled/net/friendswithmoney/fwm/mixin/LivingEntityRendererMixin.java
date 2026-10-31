// class net/friendswithmoney/fwm/mixin/LivingEntityRendererMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$ownLabel(world.entity.LivingEntity entity, double squaredDistance, asm.mixin.injection.callback.CallbackInfoReturnable a3) {
    if (v4.getReturnValueZ() ==0) goto L9;
    return;
    L9:
    v5 = client.Minecraft.getInstance();
    if (entity != v5.player) goto L32;
    if (entity == v5.getCameraEntity()) goto L33;
    L32:
    return;
    L33:
    if (v5.options.getCameraType().isFirstPerson() ==0) goto L48;
    return;
    L48:
    if (entity.isInvisible() !=0) goto L68;
    if (entity.isDiscrete() !=0) goto L68;
    if (net.Presence.showOwn() !=0) goto L69;
    L68:
    return;
    L69:
    v4.setReturnValue(Boolean.valueOf(1));
    return;
}

