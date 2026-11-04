/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  net.minecraft.client.gui.components.AbstractWidget
 */
package net.friendswithmoney.fwm.ui.panel;

import java.util.List;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.components.AbstractWidget;

public interface PanelHost {
    public void openSub(Panel var1);

    public void closeSub();

    public void attach(AbstractWidget var1);

    public void detach(AbstractWidget var1);

    public List<String> sidebarKeys();
}
