package com.toke1.more_golden_apples.effect;

import com.toke1.more_golden_apples.MoreGoldenApples;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(Registries.MOB_EFFECT, MoreGoldenApples.MOD_ID);

    public static final DeferredHolder<MobEffect,PiglinNeutralEffect> PIGLIN_NEUTRAL =
            EFFECTS.register("piglin_neutral",PiglinNeutralEffect::new);
    public static final DeferredHolder<MobEffect,EnderizationEffect> ENDERIZATION =
            EFFECTS.register("enderization",EnderizationEffect::new);
    public static final DeferredHolder<MobEffect,SculkWalkerEffect> SCULK_WALKER =
            EFFECTS.register("sculk_walker",SculkWalkerEffect::new);
    public static final DeferredHolder<MobEffect,OceanicBlissEffect> OCEANIC_BLISS =
            EFFECTS.register("oceanic_bliss",OceanicBlissEffect::new);
    public static final DeferredHolder<MobEffect,BadAppleEffect> BAD_APPLE =
            EFFECTS.register("bad_apple",BadAppleEffect::new);

    public static void register(IEventBus eventBus){
        EFFECTS.register(eventBus);
    }

}
