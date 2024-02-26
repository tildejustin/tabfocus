package com.redlimerl.tabfocus.mixins.accessor;

import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.gui.screen.option.ControlsOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ControlsOptionsScreen.class)
public interface ControlsOptionsScreenAccessor {
    @Accessor
    ControlsListWidget getKeyBindingListWidget();
}
