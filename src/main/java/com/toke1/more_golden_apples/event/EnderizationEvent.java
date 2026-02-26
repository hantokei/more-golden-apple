package com.toke1.more_golden_apples.event;

import com.toke1.more_golden_apples.Config;
import com.toke1.more_golden_apples.MoreGoldenApples;
import com.toke1.more_golden_apples.effect.ModEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.projectile.ThrownEnderpearl;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
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
            ServerLevel serverLevel = (ServerLevel) level;

            if (player.tickCount % 5 == 0) {
                serverLevel.sendParticles(ParticleTypes.PORTAL, player.getRandomX(0.5D), player.getRandomY(), player.getZ(0.5D), 2, 0.1, 0.1, 0.1, 0.02);
            }

            if (Config.ENABLE_VOID_TELEPORT.get() && player.getY() < level.getMinBuildHeight() - 5) {
                if (!teleportToSafety(player)) {
                    player.teleportTo(player.getX(), level.getMinBuildHeight() + 70, player.getZ());
                }
                player.setDeltaMovement(0, 0, 0);
                player.resetFallDistance();
                return;
            }

            if (player.isInWaterOrRain() && player.tickCount % 20 == 0) {
                player.hurt(level.damageSources().magic(), 2.0F);
                teleportToSafety(player);
            }
        }
    }

    @SubscribeEvent
    public static void onLivingIncomingDamage(LivingIncomingDamageEvent event) {
        if (!(event.getEntity() instanceof Player player)) return;

        var effect = player.getEffect(ModEffects.ENDERIZATION);
        if (effect == null) return;

        if (event.getSource().is(DamageTypes.FALL)) {
            event.setCanceled(true);
            player.resetFallDistance();

            if (!(event.getSource().getDirectEntity() instanceof ThrownEnderpearl)) {
                if (effect.getAmplifier() < 1) {
                    player.removeEffect(ModEffects.ENDERIZATION);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 0.5F);
                }
                teleportToSafety(player);
            }
            return;
        }

        if (event.getSource().getDirectEntity() instanceof Projectile proj && !(proj instanceof ThrownEnderpearl)) {
            double chance = Config.DODGE_CHANCE_PER_LEVEL.get() * (effect.getAmplifier() + 1);
            if (player.getRandom().nextDouble() < chance) {
                event.setCanceled(true);
                teleportToSafety(player);
            }
        }
    }

    private static boolean teleportToSafety(Player player) {
        if (!(player.level() instanceof ServerLevel serverLevel)) return false;

        for (int i = 0; i < 64; ++i) {
            double targetX = player.getX() + (player.getRandom().nextDouble() - 0.5D) * 32.0D;
            double targetY = Math.max(serverLevel.getMinBuildHeight() + 1, player.getY() + (double) (player.getRandom().nextInt(32) - 16));
            double targetZ = player.getZ() + (player.getRandom().nextDouble() - 0.5D) * 32.0D;

            BlockPos targetPos = BlockPos.containing(targetX, targetY, targetZ);

            if (!serverLevel.getBlockState(targetPos.below()).isAir() &&
                    serverLevel.getFluidState(targetPos.below()).isEmpty() &&
                    serverLevel.getBlockState(targetPos).getCollisionShape(serverLevel, targetPos).isEmpty() &&
                    serverLevel.getBlockState(targetPos.above()).getCollisionShape(serverLevel, targetPos.above()).isEmpty()) {

                serverLevel.sendParticles(ParticleTypes.PORTAL, player.getX(), player.getY() + 1.0D, player.getZ(), 32, 0.2, 0.5, 0.2, 0.5);
                serverLevel.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

                player.teleportTo(targetX, targetY, targetZ);
                player.resetFallDistance();
                player.setDeltaMovement(0, 0, 0);

                serverLevel.sendParticles(ParticleTypes.PORTAL, targetX, targetY + 1.0D, targetZ, 32, 0.2, 0.5, 0.2, 0.5);
                serverLevel.playSound(null, targetX, targetY, targetZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                return true;
            }
        }
        return false;
    }
}