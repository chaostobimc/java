/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Object
 *  java.lang.String
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.network.chat.Component
 */
package net.friendswithmoney.fwm.ui;

import net.friendswithmoney.fwm.ui.Lang;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class BannedScreen
extends Screen {
    public BannedScreen() {
        super((Component)Component.literal((String)"FWM"));
    }

    public void extractRenderState(GuiGraphicsExtractor ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, this.width, this.height, -261095424);
        String line1 = Lang.t("Du wurdest gesperrt", "You have been banned");
        String line2 = Lang.t("und wirst die FWM Mod nicht mehr nutzen k\u00f6nnen.", "and can no longer use the FWM mod.");
        ctx.centeredText(this.font, (Component)Component.literal((String)line1), this.width / 2, this.height / 2 - 12, -1);
        ctx.centeredText(this.font, (Component)Component.literal((String)line2), this.width / 2, this.height / 2 + 2, -12080);
    }
}
