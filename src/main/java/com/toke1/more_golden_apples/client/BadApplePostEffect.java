package com.toke1.more_golden_apples.client;

import com.mojang.blaze3d.buffers.GpuBuffer;
import com.mojang.blaze3d.systems.RenderSystem;
import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.mixin.client.GameRendererAccessor;
import com.toke1.more_golden_apples.mixin.client.PostChainAccessor;
import com.toke1.more_golden_apples.mixin.client.PostPassAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelTargetBundle;
import net.minecraft.client.renderer.PostChain;
import net.minecraft.client.renderer.PostPass;
import net.minecraft.resources.Identifier;
import org.lwjgl.system.MemoryStack;

/** Uses a separate post chain so the effect does not replace spectator or other vanilla effects. */
public final class BadApplePostEffect {
    public static final Identifier ID = Identifier.fromNamespaceAndPath(MoreGoldenApples.MOD_ID, "bad_apple");

    private BadApplePostEffect() {}

    public static void render(float progress) {
        Minecraft mc = Minecraft.getInstance();
        PostChain chain = mc.getShaderManager().getPostChain(ID, LevelTargetBundle.MAIN_TARGETS);
        if (chain == null) return;

        for (PostPass pass : ((PostChainAccessor) chain).moreGoldenApples$getPasses()) {
            var uniforms = ((PostPassAccessor) pass).moreGoldenApples$getCustomUniforms();
            GpuBuffer buffer = uniforms.get("BadAppleConfig");
            if (buffer == null) continue;

            // JSON uniforms start as immutable GPU buffers. Make only our progress uniform writable.
            if ((buffer.usage() & GpuBuffer.USAGE_COPY_DST) == 0) {
                GpuBuffer writable = RenderSystem.getDevice().createBuffer(
                        () -> "More Golden Apples / BadAppleConfig",
                        GpuBuffer.USAGE_UNIFORM | GpuBuffer.USAGE_COPY_DST, 16);
                uniforms.put("BadAppleConfig", writable);
                buffer.close();
                buffer = writable;
            }
            try (MemoryStack stack = MemoryStack.stackPush()) {
                var data = stack.calloc(16);
                data.putFloat(0, Math.clamp(progress, 0.0F, 1.0F));
                RenderSystem.getDevice().createCommandEncoder().writeToBuffer(buffer.slice(), data);
            }
        }
        chain.process(mc.getMainRenderTarget(), ((GameRendererAccessor) mc.gameRenderer).moreGoldenApples$getResourcePool());
    }
}
