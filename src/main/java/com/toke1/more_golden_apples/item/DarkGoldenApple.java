package com.toke1.more_golden_apples.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;

public class DarkGoldenApple {
    public static final FoodProperties DARK_GOLD_APPLE = new FoodProperties.Builder().nutrition(4).saturationModifier(1.2f)
            .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION,1200,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.REGENERATION,100,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION,9600,0) ,1.0f)
            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED,9600,1) ,1.0f).
            alwaysEdible().build();
}
