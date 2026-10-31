// class net/friendswithmoney/fwm/ui/FwmPalette  (classfile vClassVersion(major=69, minor=0))
// extends java/lang/Object
field LEGACY Ljava/util/Map;;
field defaults Ljava/util/Map;;
field TINT_ALPHA I;
private void <init>() {
    return;
}

public static void applyFromConfig() {
    v0 = config.FwmConfig.INSTANCE;
    if (v0.menuPalette == null) goto L64;
    if (v0.menuPalette.isBlank() !=0) goto L64;
    v1 = (([F)ui.FwmPalette.LEGACY.get(v0.menuPalette));
    if (v1 == null) goto L55;
    v0.menuHue = v1[0];
    v0.menuSat = v1[1];
    L55:
    v0.menuPalette = "";
    config.ConfigManager.save();
    L64:
    ui.FwmPalette.apply(v0.menuHue, v0.menuSat);
    return;
}

public static void refresh() {
    v0 = config.FwmConfig.INSTANCE;
    ui.FwmPalette.apply(v0.menuHue, v0.menuSat);
    return;
}

public static int accent() {
    return ui.FwmPalette.accentAt(config.FwmConfig.INSTANCE.menuHue, config.FwmConfig.INSTANCE.menuSat);
}

public static float[] defaultHue() {
    ui.FwmPalette.snapshot();
    v0 = ((Integer)ui.FwmPalette.defaults.getOrDefault("ACCENT", Integer.valueOf(ui.FwmTheme.ACCENT))).intValue();
    return ui.FwmPalette.toHsv(((v0 >> 16) & 255), ((v0 >> 8) & 255), (v0 & 255))[0];
}

public static int accentAt(float hue, float saturation) {
    ui.FwmPalette.snapshot();
    return ui.FwmPalette.recolor(((Integer)ui.FwmPalette.defaults.getOrDefault("ACCENT", Integer.valueOf(ui.FwmTheme.ACCENT))).intValue(), hue, saturation);
}

public static void apply(float hue, float saturation) {
    ui.FwmPalette.snapshot();
    v2 = ui.FwmPalette.colorFields().iterator();
    L12:
    if (v2.hasNext() ==0) goto L83;
    v3 = ((reflect.Field)v2.next());
    v4 = ((Integer)ui.FwmPalette.defaults.get(v3.getName()));
    if (v4 != null) goto L56;
    goto L12;
    L56:
    v3.setInt(null, ui.FwmPalette.valueFor(v3.getName(), v4.intValue(), hue, saturation));
    goto L80;
    v5 = /*catch IllegalAccessException*/ ex;
    L80:
    goto L12;
    L83:
    return;
}

public static int tint() {
    return ui.FwmPalette.tintAt(config.FwmConfig.INSTANCE.tintHue, config.FwmConfig.INSTANCE.tintSat);
}

public static int tintAt(float hue, float saturation) {
    if (cmp(hue,0) >=0) goto L12;
    goto L13;
    L12:
    L13:
    v2 = hue;
    return (-16777216 | ui.FwmPalette.toRgb(v2, saturation, 1));
}

private static int valueFor(String name, int base, float hue, float saturation) {
    if (name.equals("GLASS_TOP") ==0) goto L34;
    v4 = config.FwmConfig.INSTANCE;
    return (1291845632 | (ui.FwmPalette.tintAt(v4.tintHue, v4.tintSat) & 16777215));
    L34:
    if (name.startsWith("DISABLED") !=0) goto L50;
    return ui.FwmPalette.recolor(base, hue, saturation);
    L50:
    if (cmp(saturation,0.3499999940395355) <=0) goto L81;
    if (cmp(hue,0) <0) goto L81;
    if (cmp(hue,0.9399999976158142) >0) goto L77;
    if (cmp(hue,0.05999999865889549) >=0) goto L81;
    L77:
    goto L82;
    L81:
    L82:
    v4 = 0;
    if (v4 ==0) goto L99;
    goto L100;
    L99:
    L100:
    return base;
}

private static Object snapshot() {
    if (ui.FwmPalette.defaults == null) goto L7;
    return;
    L7:
    ui.FwmPalette.defaults = ?;
    v0 = ui.FwmPalette.colorFields().iterator();
    L26:
    if (v0.hasNext() ==0) goto L73;
    v1 = ((reflect.Field)v0.next());
    ui.FwmPalette.defaults.put(v1.getName(), Integer.valueOf(v1.getInt(null)));
    goto L70;
    v2 = /*catch IllegalAccessException*/ ex;
    L70:
    goto L26;
    L73:
    return;
}

private static boolean colorFields() {
    v0 = ?;
    v1 = ui.FwmTheme.class.getDeclaredFields();
    v2 = v1.length;
    v3 = 0;
    L19:
    if (v3 >= v2) goto L87;
    v4 = v1[v3];
    v5 = v4.getModifiers();
    if (reflect.Modifier.isStatic(v5) !=0) goto L47;
    goto L81;
    L47:
    if (reflect.Modifier.isFinal(v5) ==0) goto L58;
    goto L81;
    L58:
    if (v4.getType() == Integer.TYPE) goto L72;
    goto L81;
    L72:
    v0.add(v4);
    L81:
    v3 = v3 + 1;
    goto L19;
    L87:
    return v0;
}

private static int recolor(int argb, float hue, float saturation) {
    if (cmp(hue,0) >=0) goto L14;
    if (cmp(saturation,1) <0) goto L14;
    return argb;
    L14:
    v3 = (argb >>> 24);
    v4 = ui.FwmPalette.toHsv(((argb >> 16) & 255), ((argb >> 8) & 255), (argb & 255));
    if (cmp(v4[1],0) !=0) goto L56;
    return argb;
    L56:
    if (cmp(hue,0) >=0) goto L69;
    goto L70;
    L69:
    L70:
    v5 = hue;
    return ((v3 << 24) | ui.FwmPalette.toRgb(v5, (v4[1] * saturation), v4[2]));
}

private static float toHsv(int r, int g, int b) {
    v3 = (((f)r) / 255.0);
    v4 = (((f)g) / 255.0);
    v5 = (((f)b) / 255.0);
    v6 = Math.max(v3, Math.max(v4, v5));
    v7 = Math.min(v3, Math.min(v4, v5));
    v8 = (v6 - v7);
    v9 = 0;
    if (cmp(v8,0) <=0) goto L140;
    if (cmp(v6,v3) !=0) goto L86;
    v9 = (((v4 - v5) / v8) % 6.0);
    goto L120;
    L86:
    if (cmp(v6,v4) !=0) goto L108;
    v9 = (((v5 - v3) / v8) + 2);
    goto L120;
    L108:
    v9 = (((v3 - v4) / v8) + 4.0);
    L120:
    v9 = (v9 / 6.0);
    if (cmp(v9,0) >=0) goto L140;
    v9 = (v9 + 1);
    L140:
    new float[3][0] = v9;
    if (cmp(v6,0) !=0) goto L161;
    goto L166;
    L161:
    L166:
    1[0] = (v8 / v6);
    new float[3][2] = v6;
    return new float[3];
}

private static int toRgb(float hue, float saturation, float value) {
    v3 = (value * saturation);
    v4 = (v3 * (1 - Math.abs((((hue * 6.0) % 2) - 1))));
    v5 = (value - v3);
    // switch ((((i)(hue * 6.0)) % 6)) -> [Operand(op_type=<OperandTypes.BRANCH: 40>, value=99), Operand(op_type=<OperandTypes.LITERAL: 10>, value=0), Operand(op_type=<OperandTypes.LITERAL: 10>, value=4), Operand(op_type=<OperandTypes.BRANCH: 40>, value=34), Operand(op_type=<OperandTypes.BRANCH: 40>, value=47), Operand(op_type=<OperandTypes.BRANCH: 40>, value=60), Operand(op_type=<OperandTypes.BRANCH: 40>, value=73), Operand(op_type=<OperandTypes.BRANCH: 40>, value=86)]
    L68:
    v6 = v3;
    v7 = v4;
    v8 = 0;
    goto L143;
    L81:
    v6 = v4;
    v7 = v3;
    v8 = 0;
    goto L143;
    L94:
    v6 = 0;
    v7 = v3;
    v8 = v4;
    goto L143;
    L107:
    v6 = 0;
    v7 = v4;
    v8 = v3;
    goto L143;
    L120:
    v6 = v4;
    v7 = 0;
    v8 = v3;
    goto L143;
    L133:
    v6 = v3;
    v7 = 0;
    v8 = v4;
    L143:
    return (((ui.FwmPalette.channel((v6 + v5)) << 16) | (ui.FwmPalette.channel((v7 + v5)) << 8)) | ui.FwmPalette.channel((v8 + v5)));
}

private static int channel(float value) {
    return Math.max(0, Math.min(255, Math.round((value * 255.0))));
}

static Object <clinit>() {
    ui.FwmPalette.LEGACY = ?;
    new float[2][0] = -1.0;
    new float[2][1] = 1;
    ui.FwmPalette.LEGACY.put("green", new float[2]);
    new float[2][0] = 0.5799999833106995;
    new float[2][1] = 1;
    ui.FwmPalette.LEGACY.put("blue", new float[2]);
    new float[2][0] = 0.7599999904632568;
    new float[2][1] = 1;
    ui.FwmPalette.LEGACY.put("purple", new float[2]);
    new float[2][0] = 0.9900000095367432;
    new float[2][1] = 1;
    ui.FwmPalette.LEGACY.put("red", new float[2]);
    new float[2][0] = 0.10999999940395355;
    new float[2][1] = 1;
    ui.FwmPalette.LEGACY.put("gold", new float[2]);
    new float[2][0] = -1.0;
    new float[2][1] = 0;
    ui.FwmPalette.LEGACY.put("gray", new float[2]);
    return;
}

