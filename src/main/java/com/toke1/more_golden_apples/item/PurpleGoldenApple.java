package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class PurpleGoldenApple {
    public static final Consumable PURPLE_GOLD_APPLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION,1200,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION,100,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.ENDERIZATION,6000,0)))
            .build();

    public static final Consumable ENCHANTED_PURPLE_GOLD_APPLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION,2400,3)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION,400,1)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE,6000,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,5000,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.ENDERIZATION,9600,1)))
            .build();
}
