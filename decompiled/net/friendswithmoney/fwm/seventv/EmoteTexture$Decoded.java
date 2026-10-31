// class net/friendswithmoney/fwm/seventv/EmoteTexture$Decoded  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field image Lcom/mojang/blaze3d/platform/NativeImage;;
field frameW I;
field frameH I;
field frames I;
field ends [I;
field hash J;
private void <init>(blaze3d.platform.NativeImage image, int frameW, int frameH, int frames, int[] ends, long hash) {
    this.image = image;
    this.frameW = frameW;
    this.frameH = frameH;
    this.frames = frames;
    this.ends = ends;
    this.hash = hash;
    return;
}

public final String toString() {
    return /*indy*/toString(this);
}

public final int hashCode() {
    return /*indy*/hashCode(this);
}

public final boolean equals(Object o) {
    return /*indy*/equals(this, o);
}

public blaze3d.platform.NativeImage image() {
    return this.image;
}

public int frameW() {
    return this.frameW;
}

public int frameH() {
    return this.frameH;
}

public int frames() {
    return this.frames;
}

public int[] ends() {
    return this.ends;
}

public long hash() {
    return this.hash;
}

