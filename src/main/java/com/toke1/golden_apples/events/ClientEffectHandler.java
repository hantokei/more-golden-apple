package com.toke1.golden_apples.events;

import com.mojang.logging.LogUtils;
import com.toke1.golden_apples.MoreGoldenApples;
import com.toke1.golden_apples.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import org.slf4j.Logger;

@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID, value = Dist.CLIENT)
public class ClientEffectHandler {
    private static final Logger LOGGER = LogUtils.getLogger();

    private static final ResourceLocation DESATURATE_SHADER = ResourceLocation.fromNamespaceAndPath(
            MoreGoldenApples.MOD_ID, "shaders/post/bad_apple.json");

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        boolean hasEffect = mc.player.hasEffect(ModEffects.BAD_APPLE);

        PostChain currentChain = mc.gameRenderer.currentEffect();

        if (hasEffect) {
            String currentName = (currentChain != null) ? currentChain.getName() : "None";
            if (currentChain == null || !currentName.equals(DESATURATE_SHADER.toString())) {
                LOGGER.warn("检测到药水，正在加载着色器: {}", DESATURATE_SHADER);
                mc.gameRenderer.loadEffect(DESATURATE_SHADER);
            }
        } else {
            if (currentChain != null && currentNameEquals(currentChain, DESATURATE_SHADER)) {
                LOGGER.info("药水结束，卸载着色器");
                mc.gameRenderer.shutdownEffect();
            }
        }
    }

    private static boolean currentNameEquals(PostChain chain, ResourceLocation rl) {
        return chain.getName().equals(rl.toString());
    }
}
