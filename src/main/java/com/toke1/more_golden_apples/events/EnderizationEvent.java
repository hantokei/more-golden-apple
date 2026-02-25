package com.toke1.more_golden_apples.events;

import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingFallEvent;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;
import net.neoforged.neoforge.event.tick.EntityTickEvent;

// Enderman capability logic - Finalized 2026 2.25
@EventBusSubscriber(modid = MoreGoldenApples.MOD_ID)
public class EnderizationEvent {

    @SubscribeEvent
    public static void onLivingTick(EntityTickEvent.Post event) {
        if (!(event.getEntity() instanceof Player player) || player.level().isClientSide) return;

        if (player.hasEffect(ModEffects.ENDERIZATION)) {
            Level level = player.level();

            if (player.getY() < level.getMinBuildHeight() - 5) {
                if (level instanceof ServerLevel serverLevel) {
                    if (!teleportToSafety(player)) {
                        player.teleportTo(player.getX(), level.getMinBuildHeight() + 64, player.getZ());
                    }
                    player.setDeltaMovement(0, 0, 0);
                    player.resetFallDistance();
                }
                return;
            }

            if (player.isInWaterOrRain() && player.tickCount % 60 == 0) {
                player.hurt(level.damageSources().magic(), 2.0F);
                teleportToSafety(player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.ENDERIZATION)) {

            if (event.getSource().is(DamageTypes.FALL)) {

                if (event.getSource().getDirectEntity() instanceof ThrownEnderpearl ||
                        event.getSource().getEntity() instanceof ThrownEnderpearl) {
                    event.setAmount(0);
                    event.setCanceled(true);
                    return;
                }

                event.setAmount(0);
                event.setCanceled(true);

                player.resetFallDistance();
                player.removeEffect(ModEffects.ENDERIZATION);
                teleportToSafety(player);

                player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 0.5f);
                return;
            }

            if (event.getSource().getDirectEntity() instanceof Projectile projectile) {
                if (!(projectile instanceof ThrownEnderpearl)) {
                    if(player.getRandom().nextDouble() < 0.5D){
                        event.setCanceled(true);
                        teleportToSafety(player);
                    }
                }
            }
        }
    }

    @SubscribeEvent
    public static void onLivingFall(LivingFallEvent event) {
        if (event.getEntity().hasEffect(ModEffects.ENDERIZATION)) {
        }
    }

    private static boolean teleportToSafety(Player player) {
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return false;

        double startX = player.getX();
        double startY = player.getY();
        double startZ = player.getZ();

        for (int i = 0; i < 32; ++i) {
            double targetX = startX + (player.getRandom().nextDouble() - 0.5D) * 32.0D;
            double targetY = Math.max(level.getMinBuildHeight() + 1, startY + (double) (player.getRandom().nextInt(32) - 16));
            double targetZ = startZ + (player.getRandom().nextDouble() - 0.5D) * 32.0D;

            if (isSafeLocation(level, player, targetX, targetY, targetZ)) {
                serverLevel.playSound(null, startX, startY, startZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                player.teleportTo(targetX, targetY, targetZ);
                player.resetFallDistance();
                player.setDeltaMovement(0, 0, 0);
                serverLevel.playSound(null, targetX, targetY, targetZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }

    private static boolean isSafeLocation(Level level, Player player, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState floor = level.getBlockState(pos.below());
        if (!floor.isFaceSturdy(level, pos.below(), Direction.UP)) return false;
        if (!level.getFluidState(pos).isEmpty()) return false;
        boolean fits = level.getBlockState(pos).getCollisionShape(level, pos).isEmpty() &&
                level.getBlockState(pos.above()).getCollisionShape(level, pos.above()).isEmpty();
        return fits && level.noCollision(player, player.getBoundingBox().move(x - player.getX(), y - player.getY(), z - player.getZ()));
    }
}