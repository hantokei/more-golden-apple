package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class DarkGoldenApple {
    public static final Consumable DARK_GOLD_APPLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION,1200,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION,100,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,9600,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED,9600,1)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.SCULK_WALKER,9600,0)))
            .build();

    public static final Consumable ENCHANTED_DARK_GOLD_APPLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.ABSORPTION,2400,3)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.REGENERATION,400,1)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.RESISTANCE,6000,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE,5000,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,24000,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.SPEED,24000,2)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.SCULK_WALKER,24000,0)))
            .build();
}
