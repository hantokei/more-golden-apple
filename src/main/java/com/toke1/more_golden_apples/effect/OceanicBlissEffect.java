package com.toke1.more_golden_apples.effect;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;


public class OceanicBlissEffect extends MobEffect {

    public OceanicBlissEffect() {
        super(MobEffectCategory.BENEFICIAL, 0x2E8BC0);
    }
    private static final ResourceLocation OCEANIC_DAMAGE_ID =
            ResourceLocation.fromNamespaceAndPath("more_golden_apples", "oceanic_damage");

    @Override
    public boolean applyEffectTick(LivingEntity entity, int amplifier) {
        if (entity.level().isClientSide) {
            return true;
        }
        if (entity instanceof Player player) {

            var attribute = player.getAttribute(Attributes.ATTACK_DAMAGE);
            if(attribute == null) return true;

            double bonus = 2.0 * (amplifier + 1);

            var existing = attribute.getModifier(OCEANIC_DAMAGE_ID);

            if (player.isUnderWater()) {
                if(existing != null){
                    attribute.removeModifier(OCEANIC_DAMAGE_ID);
                }
                attribute.addTransientModifier(
                        new AttributeModifier(
                                OCEANIC_DAMAGE_ID,
                                bonus,
                                AttributeModifier.Operation.ADD_VALUE
                        )
                );
                if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    player.removeEffect(MobEffects.DIG_SLOWDOWN);
                }
            }
        }
        return true;
    }

    @Override
    public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
        return true;
    }
}
