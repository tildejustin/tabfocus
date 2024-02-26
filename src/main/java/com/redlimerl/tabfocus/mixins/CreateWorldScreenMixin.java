package com.redlimerl.tabfocus.mixins;

import com.redlimerl.tabfocus.FocusableWidget;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(CreateWorldScreen.class)
public abstract class CreateWorldScreenMixin {
    @Shadow protected abstract void method_0_2778(ClickableWidget clickableWidget);

    @Redirect(method = "method_0_2773", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/CreateWorldScreen;method_0_2778(Lnet/minecraft/client/gui/widget/ClickableWidget;)V"))
    public void buttonClickedRedirect(CreateWorldScreen instance, ClickableWidget button) {
        if (!(MinecraftClient.getInstance().currentScreen instanceof CreateWorldScreen)) return;
        if (FocusableWidget.FOCUSED_WIDGET != null) {
            if (FocusableWidget.FOCUSED_WIDGET.isEquals(button) || FocusableWidget.FOCUSED_WIDGET.is(TextFieldWidget.class)) {
                this.method_0_2778(button);
            }
        } else {
            this.method_0_2778(button);
        }
    }
}
