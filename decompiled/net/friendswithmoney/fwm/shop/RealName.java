// class net/friendswithmoney/fwm/shop/RealName  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register(/*indy*/getTooltip());
    return;
}

private static void append(world.item.ItemStack stack, List lines) {
    if (config.Features.isOn("fwm.feature.realname") !=0) goto L9;
    return;
    L9:
    if (stack == null) goto L33;
    if (stack.isEmpty() !=0) goto L33;
    if (lines == null) goto L33;
    if (lines.isEmpty() ==0) goto L34;
    L33:
    return;
    L34:
    v2 = shop.RealName.isListing(lines);
    v3 = ((network.chat.Component)stack.get(core.component.DataComponents.CUSTOM_NAME));
    if (v3 == null) goto L65;
    if (v2 !=0) goto L66;
    if (shop.RealName.anvilName(v3) !=0) goto L66;
    L65:
    return;
    L66:
    v4 = network.chat.Component.translatable(stack.getItem().getDescriptionId());
    if (v3.getString().trim().equalsIgnoreCase(v4.getString().trim()) ==0) goto L104;
    return;
    L104:
    v5 = ui.Lang.str("fwm.realname.label", new Object[0]);
    v6 = lines.iterator();
    L123:
    if (v6.hasNext() ==0) goto L164;
    v7 = ((network.chat.Component)v6.next());
    if (v7.getString().startsWith(v5) ==0) goto L161;
    return;
    L161:
    goto L123;
    L164:
    lines.set(0, shop.RealName.italic(((network.chat.Component)lines.get(0))));
    lines.add(1, network.chat.Component.literal(v5).withStyle(ChatFormatting.GRAY).append(v4.copy().withStyle(ChatFormatting.YELLOW)));
    return;
}

private static Optional italic(network.chat.Component line) {
    v1 = network.chat.Component.empty();
    line.visit(/*indy*/accept(v1), network.chat.Style.EMPTY);
    return v1;
}

static boolean isListing(List lines) {
    v1 = lines.iterator();
    L7:
    if (v1.hasNext() ==0) goto L59;
    v2 = ((network.chat.Component)v1.next());
    v3 = v2.getString().trim();
    if (v3.startsWith("Verk\u00e4ufer:") !=0) goto L54;
    if (v3.startsWith("Seller:") ==0) goto L56;
    L54:
    return 1;
    L56:
    goto L7;
    L59:
    return 0;
}

private static boolean anvilName(network.chat.Component name) {
    v1 = network.chat.Style.EMPTY.withItalic(Boolean.valueOf(1));
    v2 = name.visit(/*indy*/accept(v1), network.chat.Style.EMPTY);
    return v2.isEmpty();
}

private static Optional lambda$anvilName$0(network.chat.Style italic, network.chat.Style style, String part) {
    if (part.isBlank() !=0) goto L27;
    if (style.applyTo(italic).isItalic() !=0) goto L27;
    goto L30;
    L27:
    L30:
    return Optional.empty();
}

private static Optional lambda$italic$0(network.chat.MutableComponent out, network.chat.Style style, String text) {
    out.append(network.chat.Component.literal(text).setStyle(style.withItalic(Boolean.valueOf(1))));
    return Optional.empty();
}

private static void lambda$register$0(world.item.ItemStack stack, world.item.Item$TooltipContext context, world.item.TooltipFlag type, List lines) {
    shop.RealName.append(stack, lines);
    return;
}

