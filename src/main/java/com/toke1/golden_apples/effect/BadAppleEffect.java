package com.toke1.golden_apples.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class BadAppleEffect extends MobEffect {
    public BadAppleEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAAAAAA);
    }

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true; 
    }
}
