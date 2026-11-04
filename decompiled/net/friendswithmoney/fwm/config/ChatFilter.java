/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.CharSequence
 *  java.lang.Object
 *  java.lang.String
 *  java.util.Locale
 */
package net.friendswithmoney.fwm.config;

import java.util.Locale;

public class ChatFilter {
    public String text;
    public boolean on = true;

    public ChatFilter() {
    }

    public ChatFilter(String text) {
        this.text = text;
    }

    public boolean matches(String line) {
        if (!this.on || this.text == null || this.text.isBlank()) {
            return false;
        }
        return line.toLowerCase(Locale.ROOT).contains((CharSequence)this.text.toLowerCase(Locale.ROOT));
    }
}
