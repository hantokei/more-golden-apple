package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.sound.BadAppleSoundInstance;
import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class BadAppleItem extends Item {
    public BadAppleItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (level.isClientSide) {
            Minecraft.getInstance().getSoundManager().play(
                    new BadAppleSoundInstance(entity, ModEffects.BAD_APPLE)
            );
        }

        return result;
    }
}