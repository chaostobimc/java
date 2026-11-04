/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.function.Supplier
 */
package net.friendswithmoney.fwm.ui;

import java.util.function.Supplier;
import net.friendswithmoney.fwm.ui.panel.Panel;

public record Category(String labelKey, Supplier<Panel> panel, boolean locked) {
    public static Category of(String labelKey, Supplier<Panel> panel) {
        return new Category(labelKey, panel, false);
    }

    public static Category locked(String labelKey, Supplier<Panel> panel) {
        return new Category(labelKey, panel, true);
    }
}
