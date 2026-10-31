// class net/friendswithmoney/fwm/ui/FwmNotice  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field QUEUE Ljava/util/concurrent/ConcurrentLinkedQueue;;
field current Lnet/friendswithmoney/fwm/ui/FwmNotice$Notice;;
field since J;
private void <init>() {
    return;
}

public static boolean show(String head, String title, String line, int color, long ms) {
    if (title != null) goto L17;
    goto L18;
    L17:
    L18:
    if (line != null) goto L27;
    goto L28;
    L27:
    L28:
    ?.add(head);
    return;
}

static Object current() {
    v0 = System.currentTimeMillis();
    if (ui.FwmNotice.current == null) goto L29;
    if (cmp((v0 - ui.FwmNotice.since),ui.FwmNotice.current.ms()) <0) goto L29;
    ui.FwmNotice.current = null;
    L29:
    if (ui.FwmNotice.current != null) goto L51;
    ui.FwmNotice.current = ((ui.FwmNotice$Notice)ui.FwmNotice.QUEUE.poll());
    ui.FwmNotice.since = v0;
    L51:
    return ui.FwmNotice.current;
}

static float progress() {
    if (ui.FwmNotice.current != null) goto L10;
    goto L30;
    L10:
    L30:
    return Math.min(1, (((f)(System.currentTimeMillis() - ui.FwmNotice.since)) / ((f)ui.FwmNotice.current.ms())));
}

static void <clinit>() {
    ui.FwmNotice.QUEUE = ?;
    return;
}

