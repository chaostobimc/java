// class net/friendswithmoney/fwm/shop/MarketTooltip  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field UNITS [Ljava/lang/String;;
private void <init>() {
    return;
}

public static void register() {
    shop.MarketScreens.register();
    fabric.api.client.item.v1.ItemTooltipCallback.EVENT.register(/*indy*/getTooltip());
    return;
}

private static boolean append(world.item.ItemStack stack, List lines) {
    if (config.Features.isOn("fwm.feature.ahprice") !=0) goto L9;
    return;
    L9:
    if (stack == null) goto L24;
    if (stack.isEmpty() !=0) goto L24;
    if (lines != null) goto L25;
    L24:
    return;
    L25:
    if (shop.MarketScreens.allowed() !=0) goto L32;
    return;
    L32:
    net.MarketPrices.ensure();
    v2 = net.MarketPrices.get(shop.MarketKey.of(stack));
    if (v2 == null) goto L57;
    if (v2.length < 2) goto L57;
    goto L58;
    L57:
    L58:
    v3 = 0;
    v4 = shop.MarketTooltip.content(stack);
    if (v3 !=0) goto L80;
    if (v4.isEmpty() ==0) goto L80;
    return;
    L80:
    v5 = ui.Lang.str("fwm.ahprice.ah", new Object[0]);
    v6 = ui.Lang.str("fwm.ahprice.content.ah", new Object[0]);
    v7 = m1;
    v8 = 0;
    L108:
    if (v8 >= lines.size()) goto L186;
    v9 = ((network.chat.Component)lines.get(v8)).getString();
    if (v9.startsWith(v5) !=0) goto L157;
    if (v9.startsWith(v6) ==0) goto L158;
    L157:
    return;
    L158:
    if (v7 >=0) goto L180;
    if (v9.trim().startsWith("Wert:") ==0) goto L180;
    v7 = v8;
    L180:
    v8 = v8 + 1;
    goto L108;
    L186:
    v8 = ?;
    if (v3 ==0) goto L248;
    v9 = stack.getCount();
    v8.add(shop.MarketTooltip.line(v5, v2[0], v9));
    v8.add(shop.MarketTooltip.line(ui.Lang.str("fwm.ahprice.orders", new Object[0]), v2[1], v9));
    L248:
    v8.addAll(v4);
    if (v7 <0) goto L277;
    lines.addAll((v7 + 1), v8);
    return;
    L277:
    v9 = shop.MarketTooltip.debugStart(stack, lines);
    lines.add(v9, network.chat.Component.empty());
    lines.addAll((v9 + 1), v8);
    return;
}

private static List content(world.item.ItemStack stack) {
    v1 = ((world.item.component.ItemContainerContents)stack.get(core.component.DataComponents.CONTAINER));
    if (v1 != null) goto L19;
    return List.of();
    L19:
    v2 = 0;
    v4 = 0;
    v6 = 0;
    v7 = 0;
    Objects.requireNonNull(v1.nonEmptyItemCopyStream());
    v8 = /*indy*/iterator(v1.nonEmptyItemCopyStream()).iterator();
    L51:
    if (v8.hasNext() ==0) goto L220;
    v9 = ((world.item.ItemStack)v8.next());
    v7 = 1;
    v10 = net.MarketPrices.get(shop.MarketKey.of(v9));
    if (v10 == null) goto L115;
    if (v10.length < 2) goto L115;
    if (v10[0] == null) goto L115;
    goto L116;
    L115:
    L116:
    v11 = 0;
    if (v10 == null) goto L147;
    if (v10.length < 2) goto L147;
    if (v10[1] == null) goto L147;
    goto L148;
    L147:
    L148:
    v13 = 0;
    if (cmp(v11,0) >0) goto L177;
    if (cmp(v13,0) >0) goto L177;
    v6 = (v6 + v9.getCount());
    goto L51;
    L177:
    v2 = (v2 + (Math.round((v11 * 100.0)) * ((l)v9.getCount())));
    v4 = (v4 + (Math.round((v13 * 100.0)) * ((l)v9.getCount())));
    goto L51;
    L220:
    if (v7 !=0) goto L229;
    return List.of();
    L229:
    if (cmp(v2,0) <=0) goto L260;
    goto L262;
    L260:
    L262:
    if (cmp(v2,0) <=0) goto L277;
    goto L280;
    L277:
    L280:
    v8 = network.chat.Component.literal("\u2013").append(ChatFormatting.AQUA.withStyle(ChatFormatting.GRAY));
    if (cmp(v4,0) <=0) goto L321;
    goto L323;
    L321:
    L323:
    if (cmp(v4,0) <=0) goto L339;
    goto L342;
    L339:
    L342:
    v9 = network.chat.Component.literal("\u2013").append(ChatFormatting.AQUA.withStyle(ChatFormatting.GRAY));
    if (v6 <=0) goto L394;
    new Object[1][0] = Integer.valueOf(v6);
    v9.append(network.chat.Component.literal(String.format(ui.Lang.str("fwm.ahprice.content.unpriced", new Object[0]), new Object[1])).withStyle(ChatFormatting.GRAY));
    L394:
    return List.of(v8, v9);
}

private static int debugStart(world.item.ItemStack stack, List lines) {
    v2 = core.registries.BuiltInRegistries.ITEM.getKey(stack.getItem()).toString();
    v3 = 0;
    L18:
    if (v3 >= lines.size()) goto L102;
    v4 = ((network.chat.Component)lines.get(v3));
    v6 = v4.getContents();
    if ((v6 instanceof network.chat.contents.TranslatableContents) ==0) goto L80;
    v5 = ((network.chat.contents.TranslatableContents)v6);
    if (v5.getKey().equals("item.durability") ==0) goto L80;
    return v3;
    L80:
    if (v4.getString().equals(v2) ==0) goto L96;
    return v3;
    L96:
    v3 = v3 + 1;
    goto L18;
    L102:
    return lines.size();
}

private static network.chat.MutableComponent line(String label, Double dollars, int count) {
    v3 = network.chat.Component.literal(label).withStyle(ChatFormatting.WHITE);
    if (dollars == null) goto L24;
    if (cmp(dollars.doubleValue(),0) >0) goto L40;
    L24:
    return v3.append(network.chat.Component.literal("\u2013").withStyle(ChatFormatting.GRAY));
    L40:
    v4 = Math.round((dollars.doubleValue() * 100.0));
    v3.append(network.chat.Component.literal(shop.MarketTooltip.money((v4 * ((l)count)))).withStyle(ChatFormatting.AQUA));
    if (count <= 1) goto L105;
    v3.append(network.chat.Component.literal(' (' + count + 'x ' + shop.MarketTooltip.money(v4) + ')').withStyle(ChatFormatting.GRAY));
    L105:
    return v3;
}

static String money(long cents) {
    v2 = (((d)cents) / 100.0);
    if (cmp(v2,10.0) >=0) goto L20;
    return util.Fmt.moneyDe(cents);
    L20:
    if (cmp(v2,1000.0) >=0) goto L38;
    return '$' + Math.round(v2);
    L38:
    v4 = v2;
    v6 = m1;
    L44:
    if (cmp(v4,999.95) <0) goto L78;
    if (v6 >= (shop.MarketTooltip.UNITS.length - 1)) goto L78;
    v4 = (v4 / 1000.0);
    v6 = v6 + 1;
    goto L44;
    L78:
    new Object[1][0] = Double.valueOf(v4);
    return '$' + String.format(Locale.GERMANY, "%.1f", new Object[1]) + shop.MarketTooltip.UNITS[v6];
}

private static void lambda$register$0(world.item.ItemStack stack, world.item.Item$TooltipContext context, world.item.TooltipFlag type, List lines) {
    shop.MarketTooltip.append(stack, lines);
    return;
}

static void <clinit>() {
    new String[4][0] = "K";
    new String[4][1] = "M";
    new String[4][2] = "B";
    new String[4][3] = "T";
    shop.MarketTooltip.UNITS = new String[4];
    return;
}

