package com.toke1.more_golden_apples.item;

import com.toke1.more_golden_apples.MoreGoldenApples;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModItmes {
    public static final DeferredRegister.Items ITEMS =
            DeferredRegister.createItems(MoreGoldenApples.MOD_ID);

    public static final DeferredItem<Item> CRIMSON_GOLDEN_APPLE =
            ITEMS.register("crimson_golden_apple", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .food(CrimsonGoldenApple.CRIMSON_GOLD_APPLE)));

    public static final DeferredItem<Item> PURPLE_GOLDEN_APPLE =
            ITEMS.register("purple_golden_apple", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .food(PurpleGoldenApple.PURPLE_GOLD_APPLE)));

    public static final DeferredItem<Item> DARK_GOLDEN_APPLE =
            ITEMS.register("dark_golden_apple", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .food(DarkGoldenApple.DARK_GOLD_APPLE)));

    public static final DeferredItem<Item> ABYSS_GOLDEN_APPLE =
            ITEMS.register("abyss_golden_apple", () -> new Item(new Item.Properties()
                    .rarity(Rarity.RARE)
                    .food(AbyssGoldenApple.ABYSS_GOLD_APPLE)));

    public static final DeferredItem<BadAppleItem> BAD_APPLE =
            ITEMS.register("bad_apple", () -> new BadAppleItem(new Item.Properties()
                    .rarity(Rarity.UNCOMMON)
                    .food(BadApple.BAD_APPLE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
