package com.toke1.more_golden_apples;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    // --- Oceanic Bliss ---
    public static final ModConfigSpec.BooleanValue ENABLE_MINING_FATIGUE_IMMUNE = BUILDER
            .comment("是否在水下免疫挖掘疲劳效果")
            .translation("more_golden_apples.configuration.oceanic_bliss.enable_mining_fatigueImmune")
            .define("oceanic_bliss.enableMiningFatigueImmune", true);

    public static final ModConfigSpec.DoubleValue WATER_DAMAGE_BOOST_PER_LEVEL = BUILDER
            .comment("水下每级伤害加成")
            .translation("more_golden_apples.configuration.oceanic_bliss.damage_boost_perLevel")
            .defineInRange("oceanic_bliss.damageBoostPerLevel", 2.0, 0.0, 100.0);

    // --- Enderization ---
    public static final ModConfigSpec.BooleanValue ENABLE_VOID_TELEPORT = BUILDER
            .comment("是否启用虚空传送保护")
            .translation("more_golden_apples.configuration.enderization.enable_void_teleport")
            .define("enderization.enableVoidTeleport", true);

    public static final ModConfigSpec.DoubleValue DODGE_CHANCE_PER_LEVEL = BUILDER
            .comment("每级避开弹射物的几率 (0.5 = 50%)")
            .translation("more_golden_apples.configuration.enderization.dodge_chance_perLevel")
            .defineInRange("enderization.dodgeChancePerLevel", 0.5, 0.0, 1.0);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }


}
