package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class AbyssGoldenApple {
    public static final FoodProperties ABYSS_GOLD_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION,1200,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION,100,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING,6000,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.DOLPHINS_GRACE,6000,0),1.0f)
            .effect(() -> new MobEffectInstance(ModEffects.OCEANIC_BLISS,6000,0),1.0f)
            .alwaysEdible().build();

}
