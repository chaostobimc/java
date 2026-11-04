/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.MatchException
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Locale
 */
package net.friendswithmoney.fwm.seventv;

import java.util.Locale;

public record Emote(Provider provider, String id, String name, boolean animated, String imageType) {
    public String key() {
        return this.provider.key + "_" + this.id.toLowerCase(Locale.ROOT).replaceAll("[^a-z0-9]", "");
    }

    public String[] imageUrls() {
        String[] stringArray;
        switch (this.provider.ordinal()) {
            default: {
                throw new MatchException(null, null);
            }
            case 0: {
                if (this.animated) {
                    String[] stringArray2 = new String[3];
                    stringArray2[0] = "https://cdn.7tv.app/emote/" + this.id + "/1x.gif";
                    stringArray2[1] = "https://cdn.7tv.app/emote/" + this.id + "/2x.gif";
                    stringArray = stringArray2;
                    stringArray2[2] = "https://cdn.7tv.app/emote/" + this.id + "/2x_static.png";
                    break;
                }
                String[] stringArray3 = new String[3];
                stringArray3[0] = "https://cdn.7tv.app/emote/" + this.id + "/2x_static.png";
                stringArray3[1] = "https://cdn.7tv.app/emote/" + this.id + "/2x.png";
                stringArray = stringArray3;
                stringArray3[2] = "https://cdn.7tv.app/emote/" + this.id + "/2x.gif";
                break;
            }
            case 1: {
                String t;
                String string = t = this.imageType == null ? "" : this.imageType.toLowerCase(Locale.ROOT);
                if (t.equals((Object)"gif") || this.animated) {
                    String[] stringArray4 = new String[3];
                    stringArray4[0] = "https://cdn.betterttv.net/emote/" + this.id + "/1x.gif";
                    stringArray4[1] = "https://cdn.betterttv.net/emote/" + this.id + "/2x.gif";
                    stringArray = stringArray4;
                    stringArray4[2] = "https://cdn.betterttv.net/emote/" + this.id + "/2x.png";
                    break;
                }
                String[] stringArray5 = new String[2];
                stringArray5[0] = "https://cdn.betterttv.net/emote/" + this.id + "/2x.png";
                stringArray = stringArray5;
                stringArray5[1] = "https://cdn.betterttv.net/emote/" + this.id + "/2x";
                break;
            }
        }
        return stringArray;
    }

    public String chatCode() {
        return ":" + this.name + ":";
    }

    public static enum Provider {
        SEVENTV("7tv", "7TV"),
        BTTV("bttv", "BetterTTV");

        public final String key;
        public final String label;

        private Provider(String key, String label) {
            this.key = key;
            this.label = label;
        }

        public static Provider byKey(String key) {
            for (Provider p : Provider.values()) {
                if (!p.key.equals((Object)key)) continue;
                return p;
            }
            return null;
        }
    }
}
