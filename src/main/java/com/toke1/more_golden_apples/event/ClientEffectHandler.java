package com.toke1.more_golden_apples.event;

import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import com.toke1.more_golden_apples.sound.ModSounds;
import net.minecraft.client.Minecraft;
import com.toke1.more_golden_apples.client.BadApplePostEffect;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;



@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID, value = Dist.CLIENT)
public class ClientEffectHandler {

    private static int lastDuration = -1;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) {
            stopMusic(mc);
            return;
        }
        //SCULK_WALKER
        if (mc.player.hasEffect(ModEffects.SCULK_WALKER)) {
            if (mc.player.getDeltaMovement().horizontalDistanceSqr() > 1.0E-7D || mc.player.getRandom().nextFloat() < 0.1f) {
                double x = mc.player.getX() + (mc.player.getRandom().nextDouble() - 0.5) * 0.4;
                double y = mc.player.getY() + 0.3;
                double z = mc.player.getZ() + (mc.player.getRandom().nextDouble() - 0.5) * 0.4;
                mc.level.addParticle(ParticleTypes.SCULK_SOUL, x, y, z, 0.0, 0.05, 0.0);
            }
        }

        if (mc.player.hasEffect(ModEffects.ENDERIZATION)){
            if(mc.player.getRandom().nextFloat() < 0.3f){
                mc.level.addParticle(ParticleTypes.PORTAL,
                        mc.player.getRandomX(0.5), mc.player.getRandomY(), mc.player.getRandomZ(0.5),
                        0,0,0);
            }
        }

        //BAD APPLE
        var effect = mc.player.getEffect(ModEffects.BAD_APPLE);
        if (effect != null) {
            if (lastDuration == -1 || effect.getDuration() > lastDuration + 5) {
                mc.getSoundManager().stop(ModSounds.BAD_APPLE_MUSIC.get().location(), null);
                mc.getSoundManager().play(
                        SimpleSoundInstance.forUI(ModSounds.BAD_APPLE_MUSIC.get(), 1.0F, 1.0F)
                );
            }
            lastDuration = effect.getDuration();
        } else if (lastDuration != -1) {
            stopMusic(mc);
        }
    }

    @SubscribeEvent
    public static void onRenderStage(RenderLevelStageEvent.AfterLevel event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        var effect = mc.player.getEffect(ModEffects.BAD_APPLE);
        if (effect != null) {
            BadApplePostEffect.render(effect.isInfiniteDuration() ? 1.0F : Math.min(1.0F, effect.getDuration() / 100.0F));
        }
    }

    private static void stopMusic(Minecraft mc) {
        if (lastDuration != -1) {
            mc.getSoundManager().stop(ModSounds.BAD_APPLE_MUSIC.get().location(), null);
            lastDuration = -1;
        }
    }
}
