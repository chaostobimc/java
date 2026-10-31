// class net/friendswithmoney/fwm/ui/panel/ItemPanel$Entry  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field id Ljava/lang/String;;
field name Ljava/lang/String;;
field lowerName Ljava/lang/String;;
field stack Lnet/minecraft/world/item/ItemStack;;
private void <init>(String id, String name, String lowerName, world.item.ItemStack stack) {
    this.id = id;
    this.name = name;
    this.lowerName = lowerName;
    this.stack = stack;
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

public String id() {
    return this.id;
}

public String name() {
    return this.name;
}

public String lowerName() {
    return this.lowerName;
}

public world.item.ItemStack stack() {
    return this.stack;
}

