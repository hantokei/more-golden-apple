package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BadApple {
    public static final FoodProperties BAD_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(2f)
            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION,4120,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION,4120,0) ,1.0f)
            .effect(() -> new MobEffectInstance(ModEffects.BAD_APPLE,4120,0) ,1.0f)
            .alwaysEdible().build();
}
