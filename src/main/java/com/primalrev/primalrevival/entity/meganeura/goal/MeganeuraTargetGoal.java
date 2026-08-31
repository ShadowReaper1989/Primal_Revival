package com.primalrev.primalrevival.entity.meganeura.goal;

import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.chicken.Chicken;
import net.minecraft.world.entity.animal.frog.Frog;
import net.minecraft.world.entity.player.Player;

import java.util.Comparator;
import java.util.List;

public class MeganeuraTargetGoal extends Goal {

    private static final double TARGET_RANGE = 32.0D;
    private static final int SCAN_INTERVAL = 20;

    protected final MeganeuraEntity meganeura;
    protected final TargetingConditions targetingConditions;

    protected int nextScanTick;

    public MeganeuraTargetGoal(MeganeuraEntity meganeura) {
        this.meganeura = meganeura;

        this.targetingConditions = TargetingConditions
                .forCombat()
                .range(TARGET_RANGE);

        this.nextScanTick = SCAN_INTERVAL;
    }

    @Override
    public boolean canUse() {
        if (this.nextScanTick > 0) {
            --this.nextScanTick;
            return false;
        }

        this.nextScanTick = SCAN_INTERVAL;

        if (!(this.meganeura.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        List<LivingEntity> possibleTargets = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                this.meganeura.getBoundingBox().inflate(TARGET_RANGE),
                target -> this.isValidTarget(serverLevel, target)
        );

        if (possibleTargets.isEmpty()) {
            return false;
        }

        LivingEntity target = possibleTargets.stream()
                .min(Comparator.comparingDouble(this.meganeura::distanceToSqr))
                .orElse(null);

        if (target != null) {
            this.meganeura.setTarget(target);
            return true;
        }

        return false;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.meganeura.getTarget();

        if (!(this.meganeura.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        return target != null
                && target.isAlive()
                && this.isValidTarget(serverLevel, target)
                && this.meganeura.distanceToSqr(target) <= TARGET_RANGE * TARGET_RANGE;
    }

    @Override
    public void stop() {
        this.meganeura.setTarget(null);
    }

    private boolean isValidTarget(ServerLevel level, LivingEntity entity) {

        if (entity == this.meganeura) {
            return false;
        }

        if (!this.targetingConditions.test(
                level,
                this.meganeura,
                entity
        )) {
            return false;
        }

        if (entity instanceof Player player) {
            return player.getHealth() <= 6.0F;
        }

        return isSmallPrey(entity);
    }

    private boolean isSmallPrey(LivingEntity entity) {

        if (entity instanceof AgeableMob ageable && ageable.isBaby()) {
            return true;
        }

        if (entity instanceof Chicken) {
            return true;
        }

        if (entity instanceof Frog) {
            return true;
        }

        return false;
    }
}
