/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.Boolean
 *  java.lang.Math
 *  java.lang.Object
 *  java.lang.String
 *  java.util.List
 *  java.util.function.Consumer
 *  net.minecraft.client.gui.GuiGraphicsExtractor
 *  net.minecraft.client.gui.components.EditBox
 *  net.minecraft.client.gui.screens.ChatScreen
 *  net.minecraft.client.gui.screens.Screen
 *  net.minecraft.client.input.CharacterEvent
 *  net.minecraft.client.input.KeyEvent
 *  net.minecraft.client.input.MouseButtonEvent
 *  net.minecraft.network.chat.Component
 *  org.spongepowered.asm.mixin.Mixin
 *  org.spongepowered.asm.mixin.Shadow
 *  org.spongepowered.asm.mixin.Unique
 *  org.spongepowered.asm.mixin.injection.At
 *  org.spongepowered.asm.mixin.injection.Inject
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfo
 *  org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable
 */
package net.friendswithmoney.fwm.mixin;

import java.util.List;
import java.util.function.Consumer;
import net.friendswithmoney.fwm.seventv.Emote;
import net.friendswithmoney.fwm.seventv.EmoteConfig;
import net.friendswithmoney.fwm.seventv.EmotePicker;
import net.friendswithmoney.fwm.seventv.EmoteSuggest;
import net.friendswithmoney.fwm.seventv.EmoteText;
import net.friendswithmoney.fwm.seventv.SevenTv;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.input.CharacterEvent;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.input.MouseButtonEvent;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value={ChatScreen.class})
public abstract class ChatScreenMixin
extends Screen {
    @Shadow
    protected EditBox input;
    @Unique
    private boolean fwm$pressHandled;
    @Unique
    private static final int BTN = 12;

    protected ChatScreenMixin(Component title) {
        super(title);
    }

    @Unique
    private EmotePicker fwm$picker() {
        EmotePicker p = EmotePicker.get();
        p.setOnPick((Consumer<Emote>)((Consumer)this::fwm$insert));
        return p;
    }

    @Unique
    private void fwm$insert(Emote e) {
        if (!EmoteText.roomFor(this.input.getValue())) {
            return;
        }
        String value = this.input.getValue();
        int cursor = this.input.getCursorPosition();
        String text = e.chatCode() + " ";
        if (cursor > 0 && cursor <= value.length() && value.charAt(cursor - 1) != ' ') {
            text = " " + text;
        }
        this.input.insertText(text);
        this.input.setFocused(true);
    }

    @Unique
    private int fwm$btnX() {
        return this.width - 12 - 3;
    }

    @Unique
    private int fwm$btnY() {
        return this.height - 13;
    }

    @Unique
    private boolean fwm$overButton(double mx, double my) {
        int bx = this.fwm$btnX();
        int by = this.fwm$btnY();
        return mx >= (double)(bx - 1) && mx < (double)(bx + 12 + 1) && my >= (double)(by - 1) && my < (double)(by + 12 + 1);
    }

    @Inject(method={"init"}, at={@At(value="TAIL")})
    private void fwm$init(CallbackInfo ci) {
        EmotePicker picker;
        if (!SevenTv.on()) {
            return;
        }
        if (this.input != null) {
            this.input.setWidth(Math.max((int)20, (int)(this.input.getWidth() - 12 - 6)));
        }
        if ((picker = this.fwm$picker()).isOpen() && !EmotePicker.toggledRecently()) {
            picker.close();
        }
    }

    @Inject(method={"extractRenderState"}, at={@At(value="TAIL")})
    private void fwm$render(GuiGraphicsExtractor g, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if (!SevenTv.on()) {
            return;
        }
        EmotePicker picker = this.fwm$picker();
        boolean hover = this.fwm$overButton(mouseX, mouseY) || picker.isOpen();
        this.fwm$drawButton(g, this.fwm$btnX(), this.fwm$btnY(), hover);
        if (picker.isOpen()) {
            EmoteSuggest.clear();
        } else {
            EmoteSuggest.update(this.input);
            EmoteSuggest.render(g, this.input, mouseX, mouseY);
        }
        picker.render(g, mouseX, mouseY, delta, this.width, this.height);
    }

    @Unique
    private void fwm$drawButton(GuiGraphicsExtractor g, int x, int y, boolean hover) {
        int face = hover ? -13235 : -4867391;
        int dark = hover ? -10074880 : -13947599;
        g.fill(x + 3, y, x + 9, y + 12, face);
        g.fill(x + 1, y + 1, x + 11, y + 11, face);
        g.fill(x, y + 3, x + 12, y + 9, face);
        g.fill(x + 3, y + 3, x + 5, y + 6, dark);
        g.fill(x + 7, y + 3, x + 9, y + 6, dark);
        g.fill(x + 2, y + 7, x + 3, y + 8, dark);
        g.fill(x + 9, y + 7, x + 10, y + 8, dark);
        g.fill(x + 3, y + 8, x + 9, y + 9, dark);
        if (hover && !this.fwm$picker().isOpen()) {
            g.setTooltipForNextFrame(List.of((Object)Component.literal((String)"Emotes").getVisualOrderText(), (Object)Component.literal((String)("\u00a77" + EmoteConfig.size() + " zuletzt benutzt")).getVisualOrderText()), x - 20, y - 4);
        }
    }

    @Inject(method={"mouseClicked"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$mouseClicked(MouseButtonEvent event, boolean doubleClick, CallbackInfoReturnable<Boolean> cir) {
        if (!SevenTv.on()) {
            return;
        }
        EmotePicker picker = this.fwm$picker();
        if (!picker.isOpen() && EmoteSuggest.mouseClicked(this.input, event.x(), event.y())) {
            cir.setReturnValue((Object)true);
            return;
        }
        if (this.fwm$overButton(event.x(), event.y())) {
            this.fwm$pressHandled = true;
            picker.toggleDebounced();
            cir.setReturnValue((Object)true);
            return;
        }
        if (picker.isOpen()) {
            boolean inside = picker.isInside(event.x(), event.y());
            picker.mouseClicked(event, doubleClick);
            if (inside) {
                cir.setReturnValue((Object)true);
            }
        }
    }

    public boolean mouseReleased(MouseButtonEvent event) {
        if (SevenTv.on()) {
            if (this.fwm$picker().mouseReleased(event.button())) {
                return true;
            }
            boolean handled = this.fwm$pressHandled;
            this.fwm$pressHandled = false;
            if (event.button() == 1 && this.fwm$overButton(event.x(), event.y())) {
                if (!handled) {
                    this.fwm$picker().toggleDebounced();
                }
                return true;
            }
        }
        return super.mouseReleased(event);
    }

    @Inject(method={"mouseScrolled"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$mouseScrolled(double mx, double my, double sx, double sy, CallbackInfoReturnable<Boolean> cir) {
        if (SevenTv.on() && this.fwm$picker().mouseScrolled(mx, my, sy)) {
            cir.setReturnValue((Object)true);
        }
    }

    @Inject(method={"keyPressed"}, at={@At(value="HEAD")}, cancellable=true)
    private void fwm$keyPressed(KeyEvent event, CallbackInfoReturnable<Boolean> cir) {
        if (!SevenTv.on()) {
            return;
        }
        if (this.fwm$picker().isOpen()) {
            if (this.fwm$picker().keyPressed(event)) {
                cir.setReturnValue((Object)true);
            }
            return;
        }
        if (EmoteSuggest.keyPressed(this.input, event.key())) {
            cir.setReturnValue((Object)true);
        }
    }

    public boolean charTyped(CharacterEvent event) {
        if (SevenTv.on() && this.fwm$picker().isOpen() && this.fwm$picker().charTyped(event)) {
            return true;
        }
        return super.charTyped(event);
    }
}
