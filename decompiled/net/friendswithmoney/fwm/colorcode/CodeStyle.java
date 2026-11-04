/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Locale
 */
package net.friendswithmoney.fwm.colorcode;

import java.util.Locale;

public record CodeStyle(String color, boolean bold, boolean underline, boolean italic, boolean strike, boolean obf) {
    public static final CodeStyle DEFAULT = new CodeStyle(null, false, false, false, false, false);

    public CodeStyle {
        if (color != null) {
            color = color.toUpperCase(Locale.ROOT);
        }
    }

    public boolean isHex() {
        return this.color != null && this.color.length() == 6;
    }

    public CodeStyle colorOnly(String newColor) {
        return new CodeStyle(newColor, false, false, false, false, false);
    }

    public CodeStyle withColor(String newColor) {
        return new CodeStyle(newColor, this.bold, this.underline, this.italic, this.strike, this.obf);
    }

    public boolean has(Format format) {
        return switch (format.ordinal()) {
            default -> throw new MatchException(null, null);
            case 0 -> this.bold;
            case 1 -> this.underline;
            case 2 -> this.italic;
            case 3 -> this.strike;
            case 4 -> this.obf;
        };
    }

    public CodeStyle with(Format format, boolean on) {
        return new CodeStyle(this.color, format == Format.BOLD ? on : this.bold, format == Format.UNDERLINE ? on : this.underline, format == Format.ITALIC ? on : this.italic, format == Format.STRIKE ? on : this.strike, format == Format.OBF ? on : this.obf);
    }

    public static enum Format {
        BOLD('l'),
        UNDERLINE('n'),
        ITALIC('o'),
        STRIKE('m'),
        OBF('k');

        public final char code;

        private Format(char code) {
            this.code = code;
        }
    }
}
