// class net/friendswithmoney/fwm/seventv/Emote$Provider  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Enum
field SEVENTV Lnet/friendswithmoney/fwm/seventv/Emote$Provider;;
field BTTV Lnet/friendswithmoney/fwm/seventv/Emote$Provider;;
field key Ljava/lang/String;;
field label Ljava/lang/String;;
field $VALUES [Lnet/friendswithmoney/fwm/seventv/Emote$Provider;;
public static Object values() {
    return (([Lnet.friendswithmoney.fwm.seventv.Emote$Provider;)seventv.Emote$Provider.$VALUES.clone());
}

public static Enum valueOf(String name) {
    return ((seventv.Emote$Provider)Enum.valueOf(seventv.Emote$Provider.class, name));
}

private void <init>(String a1, int a2, String key, String label) {
    this.key = key;
    this.label = label;
    return;
}

public static boolean byKey(String key) {
    v1 = seventv.Emote$Provider.values();
    v2 = v1.length;
    v3 = 0;
    L9:
    if (v3 >= v2) goto L40;
    v4 = v1[v3];
    if (v4.key.equals(key) ==0) goto L34;
    return v4;
    L34:
    v3 = v3 + 1;
    goto L9;
    L40:
    return null;
}

private static seventv.Emote$Provider[] $values() {
    new seventv.Emote$Provider[2][0] = seventv.Emote$Provider.SEVENTV;
    new seventv.Emote$Provider[2][1] = seventv.Emote$Provider.BTTV;
    return new seventv.Emote$Provider[2];
}

static seventv.Emote$Provider[] <clinit>() {
    seventv.Emote$Provider.SEVENTV = ?;
    seventv.Emote$Provider.BTTV = ?;
    seventv.Emote$Provider.$VALUES = seventv.Emote$Provider.$values();
    return;
}

