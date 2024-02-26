package com.redlimerl.tabfocus.mixins.accessor;

import net.minecraft.client.gui.screen.option.SoundOptionsScreen;
import net.minecraft.sound.SoundCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SoundOptionsScreen.class_444.class)
public interface SoundButtonWidgetAccessor {
    @Accessor("field_2622")
    SoundCategory getCategory();

    @Accessor("field_2620")
    float getVolume();

    @Accessor("field_2620")
    void setVolume(float volume);

    @Accessor("field_2621")
    String getCategoryName();
}
