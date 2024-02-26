package com.redlimerl.tabfocus.mixins.accessor;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GameOptions.class_316.class)
public interface GameOptionsOptionAccessor {
    @Accessor("field_1928")
    float getStep();

    @Accessor("field_1947")
    float getMin();
}
