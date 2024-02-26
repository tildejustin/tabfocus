package com.redlimerl.tabfocus.mixins;

import com.redlimerl.tabfocus.CoolPeopleListWidget;
import com.redlimerl.tabfocus.FocusableWidget;
import net.minecraft.class_529;
import net.minecraft.client.gui.screen.world.WorldListWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WorldListWidget.class)
public abstract class WorldListWidgetMixin implements CoolPeopleListWidget {
    @Unique
    private int lastIndex = -1;

    @Shadow
    protected abstract int method_1947();

    @Shadow public abstract void method_2751(int i);

    @Shadow public abstract class_529 method_0_2558(int i);

    @Inject(method = "<init>", at = @At("TAIL"))
    public void initWorldWidget(CallbackInfo ci) {
        FocusableWidget.initWidget(this, () -> this.method_1947() > 0,
                () -> this.method_2751(0),
                () -> {
                });
    }

    @Inject(method = "method_2751", at = @At("HEAD"))
    public void onSelect(int index, CallbackInfo ci) {
        lastIndex = index;
    }

    @Override
    public void moveElement(boolean isUp) {
        if (this.method_1947() == 0 && lastIndex == -1) return;
        int idx = lastIndex + (isUp ? -1 : 1);
        if (idx < 0) idx = this.method_1947() - 1;
        if (idx >= this.method_1947()) idx = 0;
        this.method_2751(idx);
    }

    @Override
    public void clickElement() {
        if (this.method_1947() == 0 && lastIndex == -1) return;
        this.method_0_2558(lastIndex).method_2768();
    }
}
