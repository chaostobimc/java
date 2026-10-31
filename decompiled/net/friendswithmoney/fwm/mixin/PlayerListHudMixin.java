// class net/friendswithmoney/fwm/mixin/PlayerListHudMixin  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
public void <init>() {
    return;
}

private void fwm$rows(asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    net.Presence.tabRows(((List)cir.getReturnValue()));
    return;
}

private void fwm$logo(client.multiplayer.PlayerInfo entry, asm.mixin.injection.callback.CallbackInfoReturnable cir) {
    if (entry == null) goto L11;
    if (entry.getProfile() != null) goto L12;
    L11:
    return;
    L12:
    v3 = ((network.chat.Component)cir.getReturnValue());
    cir.setReturnValue(net.Presence.decorateTab(entry.getProfile().id(), v3));
    return;
}

