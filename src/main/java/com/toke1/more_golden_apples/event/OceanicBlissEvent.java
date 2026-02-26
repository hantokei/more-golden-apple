package com.toke1.more_golden_apples.event;

import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;
import com.toke1.more_golden_apples.Config;

@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID)
public class OceanicBlissEvent {

    @SubscribeEvent
    public static void onLivingHurt(LivingIncomingDamageEvent event) {
        if (event.getSource().getEntity() instanceof Player player) {
            if (player.hasEffect(ModEffects.OCEANIC_BLISS) && player.isUnderWater()) {
                int amp = player.getEffect(ModEffects.OCEANIC_BLISS).getAmplifier();

                float bonus = (float) (Config.WATER_DAMAGE_BOOST_PER_LEVEL.get() * (amp + 1));
                event.setAmount(event.getAmount() + bonus);
            }
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(EntityTickEvent.Post event) {
        if (event.getEntity() instanceof Player player && !player.level().isClientSide) {
            if (Config.ENABLE_MINING_FATIGUE_IMMUNE.get() &&
                    player.hasEffect(ModEffects.OCEANIC_BLISS) &&
                    player.isUnderWater()) {

                if (player.hasEffect(MobEffects.DIG_SLOWDOWN)) {
                    player.removeEffect(MobEffects.DIG_SLOWDOWN);
                }
            }
        }
    }
}