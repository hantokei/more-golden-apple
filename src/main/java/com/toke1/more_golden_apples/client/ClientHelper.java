package com.toke1.more_golden_apples.client;

import com.toke1.more_golden_apples.effect.ModEffects;
import com.toke1.more_golden_apples.sound.BadAppleSoundInstance;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class ClientHelper {
    public static void playBadAppleSound(LivingEntity entity) {
        Minecraft.getInstance().getSoundManager().play(
                new BadAppleSoundInstance(entity, ModEffects.BAD_APPLE)
        );
    }
}
