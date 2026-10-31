// class net/friendswithmoney/fwm/shop/UnitPrice  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field AMOUNT Ljava/util/regex/Pattern;;
field GERMAN Ljava/util/regex/Pattern;;
field ENGLISH Ljava/util/regex/Pattern;;
field lastGerman Z;
field pattern Ljava/util/regex/Pattern;;
field patternSource Ljava/lang/String;;
private void <init>() {
    return;
}

public static void register() {
    fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register(/*indy*/getTooltip());
    return;
}

private static void append(world.item.ItemStack stack, List lines) {
    if (config.Features.isOn("fwm.feature.unitprice") !=0) goto L9;
    return;
    L9:
    if (stack == null) goto L17;
    if (lines != null) goto L18;
    L17:
    return;
    L18:
    v2 = stack.getCount();
    if (v2 > 1) goto L29;
    return;
    L29:
    v3 = ui.Lang.str("fwm.unitprice.label", new Object[0]);
    v4 = 0;
    L42:
    if (v4 >= lines.size()) goto L156;
    v5 = ((network.chat.Component)lines.get(v4)).getString();
    if (v5 == null) goto L150;
    if (v5.isEmpty() ==0) goto L87;
    goto L150;
    L87:
    if (v5.startsWith(v3) ==0) goto L97;
    return;
    L97:
    v6 = shop.UnitPrice.pattern().matcher(v5);
    if (v6.find() !=0) goto L118;
    goto L150;
    L118:
    v7 = shop.UnitPrice.unitLine(v6.group(1), v2, v3);
    if (v7 != null) goto L137;
    return;
    L137:
    lines.add((v4 + 1), v7);
    return;
    L150:
    v4 = v4 + 1;
    goto L42;
    L156:
    return;
}

private static network.chat.MutableComponent unitLine(String price, int count, String label) {
    if (price != null) goto L6;
    return null;
    L6:
    v3 = shop.UnitPrice.AMOUNT.matcher(price.trim());
    if (v3.matches() !=0) goto L26;
    return null;
    L26:
    v4 = v3.group(2);
    v5 = auction.Money.cents(v4);
    if (cmp(v5,0) >0) goto L49;
    return null;
    L49:
    v7 = v3.group(1);
    v8 = v3.group(3);
    v9 = network.chat.Component.literal(label).withStyle(ChatFormatting.WHITE);
    return v9.append(network.chat.Component.literal(shop.UnitPrice.money(Math.round((((d)v5) / ((d)count))), v7, v8, shop.UnitPrice.germanStyle(v4))).withStyle(ChatFormatting.GOLD));
}

private static boolean germanStyle(String amount) {
    if (shop.UnitPrice.GERMAN.matcher(amount).find() ==0) goto L20;
    shop.UnitPrice.lastGerman = 1;
    goto L37;
    L20:
    if (shop.UnitPrice.ENGLISH.matcher(amount).find() ==0) goto L37;
    shop.UnitPrice.lastGerman = 0;
    L37:
    return shop.UnitPrice.lastGerman;
}

private static String money(long cents, String a1, String prefix, boolean suffix) {
    if (v4 ==0) goto L12;
    goto L16;
    L12:
    L16:
    v5 = util.Fmt.money(cents).substring(1);
    return prefix + v5 + suffix;
}

private static regex.Pattern pattern() {
    v0 = config.FwmConfig.INSTANCE.unitPricePattern;
    if (shop.UnitPrice.pattern == null) goto L23;
    if (v0.equals(shop.UnitPrice.patternSource) !=0) goto L46;
    L23:
    shop.UnitPrice.pattern = regex.Pattern.compile(v0);
    goto L42;
    v1 = /*catch Exception*/ ex;
    shop.UnitPrice.pattern = regex.Pattern.compile("(?:Preis|Price)\\s*:\\s*(\\S+)");
    L42:
    shop.UnitPrice.patternSource = v0;
    L46:
    return shop.UnitPrice.pattern;
}

private static void lambda$register$0(world.item.ItemStack stack, world.item.Item$TooltipContext context, world.item.TooltipFlag type, List lines) {
    shop.UnitPrice.append(stack, lines);
    return;
}

static regex.Pattern <clinit>() {
    shop.UnitPrice.AMOUNT = regex.Pattern.compile("^([^\\d]*)([\\d.,]+)([^\\d]*)$");
    shop.UnitPrice.GERMAN = regex.Pattern.compile("\\.\\d{3}(?!\\d)|,\\d{1,2}$");
    shop.UnitPrice.ENGLISH = regex.Pattern.compile(",\\d{3}(?!\\d)|\\.\\d{1,2}$");
    shop.UnitPrice.lastGerman = 1;
    return;
}

