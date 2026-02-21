package com.toke1.more_golden_apples.events;

import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import com.toke1.more_golden_apples.sound.ModSounds;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;

import java.lang.reflect.Field;
import java.util.List;

@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID, value = Dist.CLIENT)
public class ClientEffectHandler {

    private static final ResourceLocation SHADER_LOC =
            ResourceLocation.fromNamespaceAndPath(MoreGoldenApples.MOD_ID, "shaders/post/bad_apple.json");

    private static int lastDuration = -1;
    private static Field passesField = null;

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var effect = mc.player.getEffect(ModEffects.BAD_APPLE);
        if (effect != null) {
            if (lastDuration == -1 || effect.getDuration() > lastDuration + 5) {
                mc.getSoundManager().stop(ModSounds.BAD_APPLE_MUSIC.get().getLocation(), null);
                mc.getSoundManager().play(
                        SimpleSoundInstance.forUI(ModSounds.BAD_APPLE_MUSIC.get(), 1.0F, 1.0F)
                );
            }
            lastDuration = effect.getDuration();
        } else if (lastDuration != -1) {
            mc.getSoundManager().stop(ModSounds.BAD_APPLE_MUSIC.get().getLocation(), null);
            lastDuration = -1;
        }
    }

    @SubscribeEvent
    public static void onRenderStage(RenderLevelStageEvent event) {
        if (event.getStage() != RenderLevelStageEvent.Stage.AFTER_LEVEL) return;

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        var effect = mc.player.getEffect(ModEffects.BAD_APPLE);
        PostChain chain = mc.gameRenderer.currentEffect();

        if (effect != null) {
            if (chain == null || !SHADER_LOC.toString().equals(chain.getName())) {
                mc.gameRenderer.loadEffect(SHADER_LOC);
                chain = mc.gameRenderer.currentEffect();
            }

            if (chain != null) {
                float prog = effect.getDuration() < 100
                        ? effect.getDuration() / 100.0F
                        : 1.0F;

                try {
                    if (passesField == null) {
                        passesField = PostChain.class.getDeclaredField("passes");
                        passesField.setAccessible(true);
                    }

                    List<?> passes = (List<?>) passesField.get(chain);
                    for (Object passObj : passes) {
                        if (passObj instanceof net.minecraft.client.renderer.PostPass pass) {
                            var u = pass.getEffect().getUniform("Progress");
                            if (u != null) {
                                u.set(Math.max(0f, Math.min(1f, prog)));
                            }
                        }
                    }
                } catch (Exception ignored) {
                }
            }
        } else if (chain != null && SHADER_LOC.toString().equals(chain.getName())) {
            mc.gameRenderer.shutdownEffect();
        }
    }
}