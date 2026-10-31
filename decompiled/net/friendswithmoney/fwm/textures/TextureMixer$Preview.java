// class net/friendswithmoney/fwm/textures/TextureMixer$Preview  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field id Lnet/minecraft/resources/Identifier;;
field width I;
field frameHeight I;
field height I;
public void <init>(resources.Identifier id, int width, int frameHeight, int height) {
    this.id = id;
    this.width = width;
    this.frameHeight = frameHeight;
    this.height = height;
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

public resources.Identifier id() {
    return this.id;
}

public int width() {
    return this.width;
}

public int frameHeight() {
    return this.frameHeight;
}

public int height() {
    return this.height;
}

