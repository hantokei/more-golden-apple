package com.toke1.golden_apples.effect;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

public class BadAppleEffect extends MobEffect {

    public BadAppleEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xAAAAAA);
    }
    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
        }
        return true;
    }

    public boolean shouldApplyEffectTickThisTick(int tickCount, int amplifier) {
        return true; 
    }
    @Override
    public void onEffectAdded(LivingEntity entity, int amplifier) {
        super.onEffectAdded(entity, amplifier);
        if (entity instanceof Player player) {
        }
    }

    @Override
    public void onEffectStarted(LivingEntity entity, int amplifier) {
    }
}
