package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.component.Consumable;
import net.minecraft.world.item.consume_effects.ApplyStatusEffectsConsumeEffect;

public class BadApple {
    public static final Consumable BAD_APPLE = Consumable.builder()
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NAUSEA,4120,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(MobEffects.NIGHT_VISION,4120,0)))
            .onConsume(new ApplyStatusEffectsConsumeEffect(new MobEffectInstance(ModEffects.BAD_APPLE,4120,0)))
            .build();
}
