// class net/friendswithmoney/fwm/config/FwmConfig$HudSpot  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field x F;
field y F;
field scale F;
public void <init>() {
    this.x = -1.0;
    this.y = -1.0;
    this.scale = 1;
    return;
}

public boolean moved() {
    if (cmp(this.x,0) <0) goto L22;
    if (cmp(this.y,0) <0) goto L22;
    goto L23;
    L22:
    L23:
    return 0;
}

public float scale() {
    if (Float.isNaN(this.scale) !=0) goto L19;
    if (cmp(this.scale,0) >0) goto L21;
    L19:
    return 1;
    L21:
    return Math.max(0.6000000238418579, Math.min(2.5, this.scale));
}

public float set(float x, float y) {
    this.x = Math.max(0, Math.min(1, x));
    this.y = Math.max(0, Math.min(1, y));
    return;
}

public float resize(float value) {
    this.scale = Math.max(0.6000000238418579, Math.min(2.5, value));
    return;
}

public void reset() {
    this.x = -1.0;
    this.y = -1.0;
    this.scale = 1;
    return;
}

