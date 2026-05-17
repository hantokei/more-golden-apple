package com.toke1.more_golden_apples.item;

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
            com.toke1.more_golden_apples.client.ClientHelper.playBadAppleSound(entity);
        }

        return result;
    }
}