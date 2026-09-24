package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.MoreGoldenApples;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.Item;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItems {
    private static final FoodProperties APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(1.2f).alwaysEdible().build();
    private static final FoodProperties BAD_APPLE_FOOD = new FoodProperties.Builder()
            .nutrition(4).saturationModifier(2.0f).alwaysEdible().build();
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreGoldenApples.MOD_ID);

    public static final DeferredItem<Item> CRIMSON_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("crimson_golden_apple", properties -> properties
                    .rarity(Rarity.RARE)
                    .food(APPLE_FOOD, CrimsonGoldenApple.CRIMSON_GOLD_APPLE));

    public static final DeferredItem<Item> ENCHANTED_CRIMSON_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("enchanted_crimson_golden_apple", properties -> properties
                    .rarity(Rarity.EPIC)
                    .food(APPLE_FOOD, CrimsonGoldenApple.ENCHANTED_CRIMSON_GOLD_APPLE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final DeferredItem<Item> PURPLE_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("purple_golden_apple", properties -> properties
                    .rarity(Rarity.RARE)
                    .food(APPLE_FOOD, PurpleGoldenApple.PURPLE_GOLD_APPLE));

    public static final DeferredItem<Item> ENCHANTED_PURPLE_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("enchanted_purple_golden_apple", properties -> properties
                    .rarity(Rarity.EPIC)
                    .food(APPLE_FOOD, PurpleGoldenApple.ENCHANTED_PURPLE_GOLD_APPLE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final DeferredItem<Item> DARK_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("dark_golden_apple", properties -> properties
                    .rarity(Rarity.RARE)
                    .food(APPLE_FOOD, DarkGoldenApple.DARK_GOLD_APPLE));

    public static final DeferredItem<Item> ENCHANTED_DARK_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("enchanted_dark_golden_apple", properties -> properties
                    .rarity(Rarity.EPIC)
                    .food(APPLE_FOOD, DarkGoldenApple.ENCHANTED_DARK_GOLD_APPLE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final DeferredItem<Item> ABYSS_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("abyss_golden_apple", properties -> properties
                    .rarity(Rarity.RARE)
                    .food(APPLE_FOOD, AbyssGoldenApple.ABYSS_GOLD_APPLE));

    public static final DeferredItem<Item> ENCHANTED_ABYSS_GOLDEN_APPLE =
            ITEMS.registerSimpleItem("enchanted_abyss_golden_apple", properties -> properties
                    .rarity(Rarity.EPIC)
                    .food(APPLE_FOOD, AbyssGoldenApple.ENCHANTED_ABYSS_GOLD_APPLE).component(DataComponents.ENCHANTMENT_GLINT_OVERRIDE, true));

    public static final DeferredItem<Item> BAD_APPLE =
            ITEMS.registerSimpleItem("bad_apple", properties -> properties
                    .rarity(Rarity.UNCOMMON)
                    .food(BAD_APPLE_FOOD, BadApple.BAD_APPLE));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
