package com.redlimerl.tabfocus.mixins.widget;

import com.redlimerl.tabfocus.FocusableWidget;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TextFieldWidget.class)
public abstract class TextFieldWidgetMixin extends DrawableHelper {
    @Shadow
    public abstract boolean isVisible();

    @Shadow
    public abstract boolean method_1871();

    @Shadow
    public abstract void setTextFieldFocused(boolean focused);

    @Inject(method = "method_1857", at = @At("HEAD"))
    public void onRender(CallbackInfo ci) {
        FocusableWidget.initWidget(
                this,
                () -> this.isVisible() && !this.method_1871(),
                () -> this.setTextFieldFocused(true),
                () -> this.setTextFieldFocused(false)
        );
    }
}
