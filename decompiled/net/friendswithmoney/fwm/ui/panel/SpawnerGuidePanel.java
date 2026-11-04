/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.Override
 *  java.lang.String
 *  net.minecraft.client.gui.Font
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.network.chat.Component
 *  net.minecraft.resources.Identifier
 */
package net.friendswithmoney.fwm.ui.panel;

import net.friendswithmoney.fwm.ui.FwmGfx;
import net.friendswithmoney.fwm.ui.FwmTheme;
import net.friendswithmoney.fwm.ui.Lang;
import net.friendswithmoney.fwm.ui.panel.Panel;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class SpawnerGuidePanel
extends Panel {
    private static final Identifier IMAGE = Identifier.fromNamespaceAndPath((String)"fwm", (String)"textures/gui/spawner_guide.png");
    private static final int IMG_W = 512;
    private static final int IMG_H = 359;

    @Override
    public Component title() {
        return Component.literal((String)Lang.t("So baust du es am besten", "How to build it best"));
    }

    @Override
    public void render(GuiGraphicsExtractor ctx, Font font, int mouseX, int mouseY, float delta) {
        int textH = 22;
        float scale = Math.min((float)((float)this.width / 512.0f), (float)((float)(this.height - textH) / 359.0f));
        int w = Math.round((float)(512.0f * scale));
        int h = Math.round((float)(359.0f * scale));
        int ix = this.x + (this.width - w) / 2;
        FwmGfx.blit(ctx, IMAGE, ix, this.y, w, h, 512, 359, 512, 359);
        String line1 = Lang.t("Spawner in Bedrock einbauen, daneben ein Loch, in dem du stehst.", "Build the spawner into bedrock, with a hole next to it to stand in.");
        String line2 = Lang.t("Nichts zwischen dir und dem Spawner \u2013 sonst \u00f6ffnet das Makro ihn nicht.", "Nothing between you and the spawner \u2013 or the macro will not open it.");
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(line1, this.width)), this.x + (this.width - Math.min((int)this.width, (int)font.width(line1))) / 2, this.y + h + 3, FwmTheme.TEXT, false);
        ctx.text(font, (Component)Component.literal((String)font.plainSubstrByWidth(line2, this.width)), this.x + (this.width - Math.min((int)this.width, (int)font.width(line2))) / 2, this.y + h + 13, FwmTheme.TEXT_DIM, false);
    }
}
