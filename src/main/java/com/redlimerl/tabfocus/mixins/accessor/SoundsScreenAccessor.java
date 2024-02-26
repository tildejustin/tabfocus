package com.redlimerl.tabfocus.mixins.accessor;

import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(SoundOptionsScreen.class)
public interface SoundsScreenAccessor {
    @Invoker("method_2256")
    String callGetVolume(SoundCategory soundCategory);
}
