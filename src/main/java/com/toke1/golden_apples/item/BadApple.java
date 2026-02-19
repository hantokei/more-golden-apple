package com.toke1.golden_apples.item;

import com.toke1.golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class BadApple {
    public static final FoodProperties BAD_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(2f)
            .effect(() -> new MobEffectInstance(MobEffects.WITHER,1200,9) ,1.0f)
            .effect(() -> new MobEffectInstance(ModEffects.BAD_APPLE,5600,9) ,1.0f)
            .alwaysEdible().build();
}
