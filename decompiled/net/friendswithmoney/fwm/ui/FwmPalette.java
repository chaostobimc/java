/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.IllegalAccessException
 *  java.lang.Integer
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.lang.reflect.Field
 *  java.lang.reflect.Modifier
 *  java.util.ArrayList
 *  java.util.HashMap
 *  java.util.List
 *  java.util.Map
 */
package net.friendswithmoney.fwm.ui;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.friendswithmoney.fwm.config.ConfigManager;
import net.friendswithmoney.fwm.config.FwmConfig;
import net.friendswithmoney.fwm.ui.FwmTheme;

public final class FwmPalette {
    private static final Map<String, float[]> LEGACY = new HashMap();
    private static Map<String, Integer> defaults;
    private static final int TINT_ALPHA = 0x4D000000;

    private FwmPalette() {
    }

    public static void applyFromConfig() {
        FwmConfig config = FwmConfig.INSTANCE;
        if (config.menuPalette != null && !config.menuPalette.isBlank()) {
            float[] legacy = (float[])LEGACY.get((Object)config.menuPalette);
            if (legacy != null) {
                config.menuHue = legacy[0];
                config.menuSat = legacy[1];
            }
            config.menuPalette = "";
            ConfigManager.save();
        }
        FwmPalette.apply(config.menuHue, config.menuSat);
    }

    public static void refresh() {
        FwmConfig config = FwmConfig.INSTANCE;
        FwmPalette.apply(config.menuHue, config.menuSat);
    }

    public static int accent() {
        return FwmPalette.accentAt(FwmConfig.INSTANCE.menuHue, FwmConfig.INSTANCE.menuSat);
    }

    public static float defaultHue() {
        FwmPalette.snapshot();
        int base = (Integer)defaults.getOrDefault((Object)"ACCENT", (Object)FwmTheme.ACCENT);
        return FwmPalette.toHsv(base >> 16 & 0xFF, base >> 8 & 0xFF, base & 0xFF)[0];
    }

    public static int accentAt(float hue, float saturation) {
        FwmPalette.snapshot();
        return FwmPalette.recolor((Integer)defaults.getOrDefault((Object)"ACCENT", (Object)FwmTheme.ACCENT), hue, saturation);
    }

    public static void apply(float hue, float saturation) {
        FwmPalette.snapshot();
        for (Field field : FwmPalette.colorFields()) {
            Integer base = (Integer)defaults.get((Object)field.getName());
            if (base == null) continue;
            try {
                field.setInt(null, FwmPalette.valueFor(field.getName(), base, hue, saturation));
            }
            catch (IllegalAccessException illegalAccessException) {}
        }
    }

    public static int tint() {
        return FwmPalette.tintAt(FwmConfig.INSTANCE.tintHue, FwmConfig.INSTANCE.tintSat);
    }

    public static int tintAt(float hue, float saturation) {
        float h = hue < 0.0f ? FwmPalette.defaultHue() : hue;
        return 0xFF000000 | FwmPalette.toRgb(h, saturation, 1.0f);
    }

    private static int valueFor(String name, int base, float hue, float saturation) {
        if (name.equals((Object)"GLASS_TOP")) {
            FwmConfig config = FwmConfig.INSTANCE;
            return 0x4D000000 | FwmPalette.tintAt(config.tintHue, config.tintSat) & 0xFFFFFF;
        }
        if (!name.startsWith("DISABLED")) {
            return FwmPalette.recolor(base, hue, saturation);
        }
        boolean menuIsRed = saturation > 0.35f && hue >= 0.0f && (hue > 0.94f || hue < 0.06f);
        return menuIsRed ? FwmPalette.recolor(base, -1.0f, 0.0f) : base;
    }

    private static void snapshot() {
        if (defaults != null) {
            return;
        }
        defaults = new HashMap();
        for (Field field : FwmPalette.colorFields()) {
            try {
                defaults.put((Object)field.getName(), (Object)field.getInt(null));
            }
            catch (IllegalAccessException illegalAccessException) {}
        }
    }

    private static List<Field> colorFields() {
        ArrayList out = new ArrayList();
        for (Field field : FwmTheme.class.getDeclaredFields()) {
            int modifiers = field.getModifiers();
            if (!Modifier.isStatic((int)modifiers) || Modifier.isFinal((int)modifiers) || field.getType() != Integer.TYPE) continue;
            out.add((Object)field);
        }
        return out;
    }

    private static int recolor(int argb, float hue, float saturation) {
        if (hue < 0.0f && saturation >= 1.0f) {
            return argb;
        }
        int alpha = argb >>> 24;
        float[] hsv = FwmPalette.toHsv(argb >> 16 & 0xFF, argb >> 8 & 0xFF, argb & 0xFF);
        if (hsv[1] == 0.0f) {
            return argb;
        }
        float target = hue < 0.0f ? hsv[0] : hue;
        return alpha << 24 | FwmPalette.toRgb(target, hsv[1] * saturation, hsv[2]);
    }

    private static float[] toHsv(int r, int g, int b) {
        float rf = (float)r / 255.0f;
        float gf = (float)g / 255.0f;
        float bf = (float)b / 255.0f;
        float max = Math.max((float)rf, (float)Math.max((float)gf, (float)bf));
        float min = Math.min((float)rf, (float)Math.min((float)gf, (float)bf));
        float delta = max - min;
        float hue = 0.0f;
        if (delta > 0.0f) {
            hue = max == rf ? (gf - bf) / delta % 6.0f : (max == gf ? (bf - rf) / delta + 2.0f : (rf - gf) / delta + 4.0f);
            if ((hue /= 6.0f) < 0.0f) {
                hue += 1.0f;
            }
        }
        return new float[]{hue, max == 0.0f ? 0.0f : delta / max, max};
    }

    private static int toRgb(float hue, float saturation, float value) {
        float g;
        float r;
        float c = value * saturation;
        float x = c * (1.0f - Math.abs((float)(hue * 6.0f % 2.0f - 1.0f)));
        float m = value - c;
        return FwmPalette.channel(r + m) << 16 | FwmPalette.channel(g + m) << 8 | FwmPalette.channel((switch ((int)(hue * 6.0f) % 6) {
            case 0 -> {
                r = c;
                g = x;
                yield 0.0f;
            }
            case 1 -> {
                r = x;
                g = c;
                yield 0.0f;
            }
            case 2 -> {
                r = 0.0f;
                g = c;
                yield x;
            }
            case 3 -> {
                r = 0.0f;
                g = x;
                yield c;
            }
            case 4 -> {
                r = x;
                g = 0.0f;
                yield c;
            }
            default -> {
                r = c;
                g = 0.0f;
                yield x;
            }
        }) + m);
    }

    private static int channel(float value) {
        return Math.max((int)0, (int)Math.min((int)255, (int)Math.round((float)(value * 255.0f))));
    }

    static {
        LEGACY.put((Object)"green", (Object)new float[]{-1.0f, 1.0f});
        LEGACY.put((Object)"blue", (Object)new float[]{0.58f, 1.0f});
        LEGACY.put((Object)"purple", (Object)new float[]{0.76f, 1.0f});
        LEGACY.put((Object)"red", (Object)new float[]{0.99f, 1.0f});
        LEGACY.put((Object)"gold", (Object)new float[]{0.11f, 1.0f});
        LEGACY.put((Object)"gray", (Object)new float[]{-1.0f, 0.0f});
    }
}
