/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.client.gui.screens.Screen
 */
package net.friendswithmoney.fwm.ui;

import net.friendswithmoney.fwm.net.ModBan;
import net.friendswithmoney.fwm.ui.BannedScreen;
import net.friendswithmoney.fwm.ui.LauncherScreen;
import net.minecraft.client.gui.screens.Screen;

public final class Design {
    public static final int NUMBER = 4;

    private Design() {
    }

    public static Screen open(Screen parent, String page, int tab) {
        if (ModBan.locked()) {
            return new BannedScreen();
        }
        return new LauncherScreen(parent, page, tab);
    }
}
