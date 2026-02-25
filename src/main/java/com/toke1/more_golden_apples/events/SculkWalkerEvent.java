package com.toke1.more_golden_apples.events;

import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.tags.GameEventTags;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.VanillaGameEvent;

@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID)
public class SculkWalkerEvent {
    @SubscribeEvent
    public static void onSculkSignal(VanillaGameEvent event) {
        if (event.getContext().sourceEntity() instanceof LivingEntity entity) {
            if (entity.hasEffect(ModEffects.SCULK_WALKER)) {
                if (event.getVanillaEvent().is(GameEventTags.VIBRATIONS)) {
                    event.setCanceled(true);
                }
            }
        }
    }
}