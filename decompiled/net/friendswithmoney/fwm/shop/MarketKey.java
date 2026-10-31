// class net/friendswithmoney/fwm/shop/MarketKey  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static String of(world.item.ItemStack stack) {
    if (stack == null) goto L11;
    if (stack.isEmpty() ==0) goto L13;
    L11:
    return null;
    L13:
    v1 = net.MarketPrices.keyForName(stack.getHoverName().getString());
    if (v1 == null) goto L32;
    return v1;
    L32:
    v2 = core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem());
    v3 = v2.getPath();
    if (v3.equals("enchanted_book") ==0) goto L64;
    return shop.MarketKey.book(stack);
    L64:
    if (v3.equals("potion") !=0) goto L91;
    if (v3.equals("splash_potion") !=0) goto L91;
    if (v3.equals("tipped_arrow") ==0) goto L115;
    L91:
    v4 = shop.MarketKey.potion(stack);
    if (v4 != null) goto L106;
    goto L114;
    L106:
    L114:
    return v3 + '#' + v4;
    L115:
    return v3;
}

private static String book(world.item.ItemStack stack) {
    v1 = ((world.item.enchantment.ItemEnchantments)stack.get(core.component.DataComponents.STORED_ENCHANTMENTS));
    if (v1 == null) goto L23;
    if (v1.size() == 1) goto L25;
    L23:
    return null;
    L25:
    v2 = v1.keySet().iterator();
    if (v2.hasNext() ==0) goto L97;
    v3 = ((core.Holder)v2.next());
    v4 = ((String)v3.unwrapKey().map(/*indy*/apply()).orElse(null));
    if (v4 != null) goto L84;
    return null;
    L84:
    return 'enchanted_book#' + v4 + '_' + v1.getLevel(v3);
    L97:
    return null;
}

private static String potion(world.item.ItemStack stack) {
    v1 = ((world.item.alchemy.PotionContents)stack.get(core.component.DataComponents.POTION_CONTENTS));
    if (v1 == null) goto L25;
    if (v1.potion().isEmpty() ==0) goto L27;
    L25:
    return null;
    L27:
    v2 = ((core.Holder)v1.potion().get());
    v3 = ((String)v2.unwrapKey().map(/*indy*/apply()).orElse(null));
    if (v3 != null) goto L66;
    return null;
    L66:
    return v3.replaceFirst("^(long|strong)_", "");
}

private static String lambda$potion$0(resources.ResourceKey key) {
    return key.identifier().getPath();
}

private static String lambda$book$0(resources.ResourceKey key) {
    return key.identifier().getPath();
}

