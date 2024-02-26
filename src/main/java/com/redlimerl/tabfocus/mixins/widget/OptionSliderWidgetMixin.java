package com.redlimerl.tabfocus.mixins.widget;

import com.redlimerl.tabfocus.CoolGuyOptionSlider;
import com.redlimerl.tabfocus.mixins.accessor.GameOptionsOptionAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.client.option.GameOptions;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(SliderWidget.class)
public abstract class OptionSliderWidgetMixin extends ClickableWidget implements CoolGuyOptionSlider {

    @Shadow
    private float field_2161;

    @Final
    @Shadow
    private GameOptions.class_316 field_2162;

    public OptionSliderWidgetMixin(int id, int x, int y, String message) {
        super(id, x, y, message);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void moveValue(boolean isLeft) {
        MinecraftClient client = MinecraftClient.getInstance();
        GameOptionsOptionAccessor optionAccessor = (GameOptionsOptionAccessor) ((Object) this.field_2162);

        float f;
        if (optionAccessor.getStep() == 0) {
            this.field_2161 = MathHelper.clamp(this.field_2161 + (isLeft ? -0.01f : 0.01f), 0.0F, 1.0F);
            f = this.field_2162.method_1645(this.field_2161);
        } else {
            f = MathHelper.clamp(this.field_2162.method_1645(this.field_2161) + (optionAccessor.getStep() * (isLeft ? -1 : 1)), optionAccessor.getMin(), this.field_2162.method_1652());
        }
        client.options.method_1625(this.field_2162, f);
        this.field_2161 = this.field_2162.method_1645(f);
        this.field_2074 = client.options.method_1642(this.field_2162);
    }
}
