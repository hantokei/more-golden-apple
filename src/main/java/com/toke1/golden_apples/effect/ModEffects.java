package com.toke1.golden_apples.effect;

import com.toke1.golden_apples.MoreGoldenApples;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, MoreGoldenApples.MOD_ID);

    public static final DeferredHolder<MobEffect,OceanicBlissEffect> OCEANIC_BLISS =
            EFFECTS.register("oceanic_bliss",OceanicBlissEffect::new);
    public static final DeferredHolder<MobEffect,BadAppleEffect> BAD_APPLE =
            EFFECTS.register("bad_apple",BadAppleEffect::new);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }

}
