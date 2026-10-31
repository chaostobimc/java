// class net/friendswithmoney/fwm/ui/widget/Anim  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field VALUES Ljava/util/Map;;
private void <init>() {
    return;
}

public static Object toward(String key, boolean target, int millis) {
    v3 = System.currentTimeMillis();
    if (target ==0) goto L12;
    goto L13;
    L12:
    L13:
    v5 = 0;
    v6 = ((ui.widget.Anim$State)ui.widget.Anim.VALUES.get(key));
    if (v6 != null) goto L57;
    ui.widget.Anim.VALUES.put(key, ?);
    return v5;
    L57:
    v7 = (((f)(v3 - v6.at())) / ((f)Math.max(1, millis)));
    v8 = v6.value();
    if (cmp(v8,v5) >=0) goto L104;
    v8 = Math.min(v5, (v8 + v7));
    goto L124;
    L104:
    if (cmp(v8,v5) <=0) goto L124;
    v8 = Math.max(v5, (v8 - v7));
    L124:
    ui.widget.Anim.VALUES.put(key, ?);
    return v8;
}

public static float ease(float t) {
    v1 = (1 - Math.max(0, Math.min(1, t)));
    return (1 - ((v1 * v1) * v1));
}

static void <clinit>() {
    ui.widget.Anim.VALUES = ?;
    return;
}

