package com.toke1.more_golden_apples.mixin;

import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.piglin.PiglinAi;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PiglinAi.class)
public class PiglinAiMixin {
    @Inject(method = "isWearingGold", at = @At("HEAD"), cancellable = true)
    private static void injectPiglinNeutralEffect(LivingEntity entity, CallbackInfoReturnable<Boolean> cir){
        if(entity.hasEffect(ModEffects.GOLDEN_GLOW)){
            cir.setReturnValue(true);
        }
    }
}
