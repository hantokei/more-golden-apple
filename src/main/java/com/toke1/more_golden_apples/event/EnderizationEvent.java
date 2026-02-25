package com.toke1.more_golden_apples.event;

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
import net.minecraft.world.level.block.state.BlockState;
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
                serverLevel.sendParticles(ParticleTypes.PORTAL,
                        player.getRandomX(0.5D), player.getRandomY(), player.getZ(0.5D),
                        2, 0.1D, 0.1D, 0.1D, 0.02D);
            }

            if (player.getY() < level.getMinBuildHeight() - 5) {
                if (!teleportToSafety(player)) {
                    player.teleportTo(player.getX(), level.getMinBuildHeight() + 64, player.getZ());
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
        if (event.getEntity() instanceof Player player && player.hasEffect(ModEffects.ENDERIZATION)) {

            var effect = player.getEffect(ModEffects.ENDERIZATION);
            if(effect == null) return;

            int amp = effect.getAmplifier();
            boolean isEnchanted = amp >= 1;

            if (event.getSource().is(DamageTypes.FALL)) {
                if (event.getSource().getDirectEntity() instanceof ThrownEnderpearl ||
                        event.getSource().getEntity() instanceof ThrownEnderpearl) {
                    event.setAmount(0);
                    event.setCanceled(true);
                    player.resetFallDistance();
                    return;
                }
                event.setAmount(0);
                event.setCanceled(true);
                player.resetFallDistance();

                if(isEnchanted){
                    teleportToSafety(player);
                }else{
                    player.removeEffect(ModEffects.ENDERIZATION);
                    teleportToSafety(player);
                    player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                            SoundEvents.ENDER_EYE_DEATH, SoundSource.PLAYERS, 1.0F, 0.5f);
                }
                return;
            }

            if (event.getSource().getDirectEntity() instanceof Projectile projectile) {
                if(!(event.getSource().getDirectEntity() instanceof ThrownEnderpearl)){
                    double chance = isEnchanted ? 1.0D:0.5D;
                    if(player.getRandom().nextDouble() < chance){
                        event.setCanceled(true);
                        teleportToSafety(player);
                    }
                }
            }
        }
    }

    private static boolean teleportToSafety(Player player) {
        Level level = player.level();
        if (!(level instanceof ServerLevel serverLevel)) return false;

        double startX = player.getX();
        double startY = player.getY();
        double startZ = player.getZ();

        for (int i = 0; i < 64; ++i) {
            double targetX = startX + (player.getRandom().nextDouble() - 0.5D) * 32.0D;
            double targetY = Math.max(level.getMinBuildHeight() + 1, startY + (double) (player.getRandom().nextInt(32) - 16));
            double targetZ = startZ + (player.getRandom().nextDouble() - 0.5D) * 32.0D;

            if (isSafeLocation(level, player, targetX, targetY, targetZ)) {
                serverLevel.sendParticles(ParticleTypes.PORTAL, startX, startY + 1.0D, startZ, 32, 0.2, 0.5, 0.2, 0.5);
                serverLevel.playSound(null, startX, startY, startZ, SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);

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

    private static boolean isSafeLocation(Level level, Player player, double x, double y, double z) {
        BlockPos pos = BlockPos.containing(x, y, z);
        BlockState floor = level.getBlockState(pos.below());
        if(floor.isAir() || !level.getFluidState(pos.below()).isEmpty()) return false;

        boolean isHeadSafe = level.getBlockState(pos.above()).getCollisionShape(level, pos.above()).isEmpty();
        boolean isFootSafe = level.getBlockState(pos).getCollisionShape(level,pos).isEmpty();
        boolean noInLiquid = level.getFluidState(pos).isEmpty();

        return isHeadSafe && isFootSafe && noInLiquid;
    }
}