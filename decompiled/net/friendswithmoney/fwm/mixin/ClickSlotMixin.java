// class net/friendswithmoney/fwm/mixin/ClickSlotMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$handleContainerInput(int containerId, int slotId, int button, world.inventory.ContainerInput action, world.entity.player.Player player, asm.mixin.injection.callback.CallbackInfo ci) {
    if (player != null) goto L9;
    goto L14;
    L9:
    L14:
    v7 = player.containerMenu;
    if (v7 == null) goto L37;
    if (slotId <0) goto L37;
    if (slotId < v7.slots.size()) goto L38;
    L37:
    return;
    L38:
    money.MoneyEvents.offerClicked(v7.getSlot(slotId).getItem());
    money.MoneyEvents.menuClicked(v7);
    goto L60;
    v7 = /*catch Throwable*/ ex;
    L60:
    return;
}

