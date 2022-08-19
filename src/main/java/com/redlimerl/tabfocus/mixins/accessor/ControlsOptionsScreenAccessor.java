package com.redlimerl.tabfocus.mixins.accessor;

import net.minecraft.client.gui.screen.options.ControlsOptionsScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(ControlsOptionsScreen.class)
public interface ControlsOptionsScreenAccessor {
    @Accessor("field_1086")
    int getSelectedKeyBinding();
}
