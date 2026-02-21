package com.toke1.more_golden_apples.sound;

import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.core.Holder;

public class BadAppleSoundInstance extends AbstractTickableSoundInstance {
    private final LivingEntity entity;
    private final Holder<MobEffect> effect;

    public BadAppleSoundInstance(LivingEntity entity, Holder<MobEffect> effect) {
        super(ModSounds.BAD_APPLE_MUSIC.get(), SoundSource.RECORDS, entity.getRandom());
        this.entity = entity;
        this.effect = effect;
        this.looping = true;
        this.delay = 0;
        this.volume = 1.0F;
        this.relative = false;
    }

    @Override
    public void tick() {
        if (this.entity.isRemoved() || !this.entity.hasEffect(this.effect)) {
            this.stop();
        } else {
            this.x = (float) this.entity.getX();
            this.y = (float) this.entity.getY();
            this.z = (float) this.entity.getZ();
        }
    }
}