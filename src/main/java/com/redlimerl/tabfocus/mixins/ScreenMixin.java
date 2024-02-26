package com.redlimerl.tabfocus.mixins;

import com.redlimerl.tabfocus.CoolGuyOptionSlider;
import com.redlimerl.tabfocus.CoolPeopleListWidget;
import com.redlimerl.tabfocus.FocusableWidget;
import com.redlimerl.tabfocus.mixins.accessor.ControlsOptionsScreenAccessor;
import com.redlimerl.tabfocus.mixins.accessor.SoundButtonWidgetAccessor;
import com.redlimerl.tabfocus.mixins.accessor.SoundsScreenAccessor;
import net.minecraft.class_0_692;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Keyboard;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.redlimerl.tabfocus.FocusableWidget.FOCUSED_WIDGET;
import static com.redlimerl.tabfocus.FocusableWidget.focusableWidgets;
import static com.redlimerl.tabfocus.TabFocus.FOCUSED_BUTTON_ORDER;

@Mixin(Screen.class)
public abstract class ScreenMixin {

    @Shadow
    protected MinecraftClient field_2563;
    @Shadow
    protected ClickableWidget field_0_3200;

    @Shadow
    protected abstract void method_0_2778(ClickableWidget clickableWidget);

    @Inject(method = "method_0_2802", at = @At("HEAD"), cancellable = true)
    public void onKeyPressed(CallbackInfo ci) {
        if (Keyboard.getEventKeyState()) {
            int keyCode = Keyboard.getEventKey();

            // Press Tab
            if (keyCode == 15) {
                FocusableWidget.deselectWidget();
                while (focusableWidgets.stream().anyMatch(FocusableWidget::isSelectableWidget)) {
                    if (Screen.method_2223()) {
                        FOCUSED_BUTTON_ORDER = (FOCUSED_BUTTON_ORDER - 1) % focusableWidgets.size();
                        if (FOCUSED_BUTTON_ORDER < 0) FOCUSED_BUTTON_ORDER = focusableWidgets.size() - 1;
                    } else FOCUSED_BUTTON_ORDER = (FOCUSED_BUTTON_ORDER + 1) % focusableWidgets.size();
                    FocusableWidget<?> widget = FocusableWidget.get(FOCUSED_BUTTON_ORDER);
                    if (widget != null && widget.isSelectableWidget()) {
                        FocusableWidget.selectWidget(widget);
                        break;
                    }
                }
            }

            // Press Enter
            else if ((keyCode == 28 || keyCode == 156)) {
                ClickableWidget button = FocusableWidget.getWidgetOrNull(FOCUSED_BUTTON_ORDER, ClickableWidget.class);
                if (button != null) {
                    if (field_2563.currentScreen instanceof ControlsOptionsScreen && ((ControlsOptionsScreen) field_2563.currentScreen).focusedBinding == null) {
                        ((ControlsOptionsScreenAccessor) field_2563.currentScreen).getKeyBindingListWidget().method_0_2559(button.field_2069, button.field_2068, keyCode);
                        if (((ControlsOptionsScreen) field_2563.currentScreen).focusedBinding != null) {
                            ci.cancel();
                            return;
                        }
                    }
                    button.method_1832(this.field_2563.getSoundManager());
                    this.method_0_2778(button);
                }
                WorldListWidget worldList = FocusableWidget.getWidgetOrNull(FOCUSED_BUTTON_ORDER, WorldListWidget.class);
                if (worldList != null) {
                    CoolPeopleListWidget widget = (CoolPeopleListWidget) worldList;
                    widget.clickElement();
                }
            }

            // Press left/right key (for Slider widget)
            else if (keyCode == 203 || keyCode == 205) {
                DrawableHelper sliderWidgetHelper = this.field_0_3200 != null ? this.field_0_3200 : FocusableWidget.getWidgetOrNull(FOCUSED_BUTTON_ORDER, DrawableHelper.class);
                if (sliderWidgetHelper instanceof class_0_692) {
                    class_0_692 sliderWidget = (class_0_692) sliderWidgetHelper;
                    sliderWidget.method_0_2542(MathHelper.clamp(sliderWidget.method_0_2545() + (keyCode == 203 ? -0.01f : 0.01f), 0f, 1f));
                } else if (sliderWidgetHelper instanceof SliderWidget) {
                    SliderWidget sliderWidget = (SliderWidget) sliderWidgetHelper;
                    ((CoolGuyOptionSlider) sliderWidget).moveValue(keyCode == 203);
                } else if (field_2563.currentScreen instanceof SoundOptionsScreen && sliderWidgetHelper != null && ((ClickableWidget) sliderWidgetHelper).field_2077 != 200) {
                    SoundButtonWidgetAccessor slider = ((SoundButtonWidgetAccessor) sliderWidgetHelper);
                    slider.setVolume(Math.min(1, Math.max(0, slider.getVolume() + (keyCode == 203 ? -0.01f : 0.01f))));
                    field_2563.options.setSoundVolume(slider.getCategory(), slider.getVolume());
                    field_2563.options.write();
                    ((ClickableWidget) sliderWidgetHelper).field_2074 = slider.getCategoryName() + ": " + ((SoundsScreenAccessor) field_2563.currentScreen).callGetVolume(slider.getCategory());
                }
            }

            // Press up/down key (for Slider widget)
            else if ((keyCode == 200 || keyCode == 208) && FocusableWidget.getWidgetOrNull(FOCUSED_BUTTON_ORDER, WorldListWidget.class) != null) {
                WorldListWidget worldListWidget = FocusableWidget.getWidgetOrNull(FOCUSED_BUTTON_ORDER, WorldListWidget.class);
                if (worldListWidget != null) {
                    CoolPeopleListWidget widget = (CoolPeopleListWidget) worldListWidget;
                    widget.moveElement(keyCode == 200);
                }
            }

            // Press ESC
            else if (keyCode == 1) {
                clear();
            }
        }
    }

    @Inject(method = "method_2233", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/Screen;method_2224()V", shift = At.Shift.BEFORE))
    public void onInit(CallbackInfo ci) {
        clear();
    }

    @Unique
    public void clear() {
        FOCUSED_BUTTON_ORDER = -1;
        FOCUSED_WIDGET = null;
        focusableWidgets.clear();
    }

    @Inject(method = "method_0_2775", at = @At("TAIL"))
    public void onMouseClicked(CallbackInfo ci) {
        FOCUSED_BUTTON_ORDER = -1;
        FOCUSED_WIDGET = null;
    }

    @Inject(method = "method_2214", at = @At("TAIL"))
    public void onDebug(CallbackInfo ci) {
        //this.client.textRenderer.draw(Integer.toString(FOCUSED_BUTTON_ORDER), 10, 10, -1);
    }

    @Redirect(method = "method_2214", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/widget/ClickableWidget;method_1824(Lnet/minecraft/client/MinecraftClient;IIF)V"))
    public void buttonRender(ClickableWidget instance, MinecraftClient minecraftClient, int i, int j, float f) {
        FocusableWidget.initWidget(instance, () -> instance.field_2076 && instance.field_2078);
        if (FOCUSED_WIDGET != null && FOCUSED_WIDGET.isEquals(instance)) {
            instance.method_1824(field_2563, instance.field_2069, instance.field_2068, f);
        } else {
            instance.method_1824(field_2563, i, j, f);
        }
    }
}
