// class net/friendswithmoney/fwm/ui/panel/AuctionItemPanel$Slot  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Record
field stack Lnet/minecraft/world/item/ItemStack;;
field index I;
private void <init>(world.item.ItemStack stack, int index) {
    this.stack = stack;
    this.index = index;
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

public world.item.ItemStack stack() {
    return this.stack;
}

public int index() {
    return this.index;
}

