package com.toke1.more_golden_apples.mixin.client;

import java.util.Map;
import com.mojang.blaze3d.buffers.GpuBuffer;
import net.minecraft.client.renderer.PostPass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PostPass.class)
public interface PostPassAccessor {
    @Accessor("customUniforms")
    Map<String, GpuBuffer> moreGoldenApples$getCustomUniforms();
}
