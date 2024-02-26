package com.redlimerl.tabfocus.mixins.widget;

import com.redlimerl.tabfocus.FocusableWidget;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.widget.ClickableWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.redlimerl.tabfocus.FocusableWidget.FOCUSED_WIDGET;

@Mixin(ClickableWidget.class)
public abstract class ButtonWidgetMixin extends DrawableHelper {

    @Shadow
    public int field_2069;

    @Shadow
    public int field_2068;

    @Shadow
    public boolean field_2076;

    @Shadow
    public boolean field_2078;

    @Inject(method = "method_1824", at = @At("HEAD"))
    public void onRender(CallbackInfo ci) {
        FocusableWidget.initWidget(this, () -> this.field_2076 && this.field_2078);
    }

    @ModifyVariable(method = "method_1824", at = @At("HEAD"), ordinal = 0, argsOnly = true)
    public int renderMouseX(int mouseX) {
        return FOCUSED_WIDGET != null && FOCUSED_WIDGET.isEquals(this) ? this.field_2069 : mouseX;
    }

    @ModifyVariable(method = "method_1824", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    public int renderMouseY(int mouseY) {
        return FOCUSED_WIDGET != null && FOCUSED_WIDGET.isEquals(this) ? this.field_2068 : mouseY;
    }
}
