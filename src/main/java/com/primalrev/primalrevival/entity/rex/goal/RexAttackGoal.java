package com.primalrev.primalrevival.entity.rex.goal;

import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RexAttackGoal extends Goal {

    private static final double ATTACK_RANGE = 3.5D;
    private static final int ATTACK_COOLDOWN = 30;

    private final RexEntity rex;
    private int attackCooldown;

    public RexAttackGoal(RexEntity rex) {
        this.rex = rex;

        this.setFlags(EnumSet.of(
                Goal.Flag.MOVE,
                Goal.Flag.LOOK
        ));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.rex.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        if (!this.rex.hasRoaredTarget(target)) {
            return false;
        }

        return this.rex.distanceToSqr(target) <= ATTACK_RANGE * ATTACK_RANGE;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.rex.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        return this.rex.hasRoaredTarget(target)
                && (this.rex.distanceToSqr(target) <= ATTACK_RANGE * ATTACK_RANGE || this.attackCooldown > 0);
    }

    @Override
    public void start() {
        this.attackCooldown = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.rex.getTarget();

        if (target == null) {
            return;
        }

        this.rex.getLookControl().setLookAt(target, 30.0F, 30.0F);

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
            this.rex.getNavigation().moveTo(target, 1.2D);
            return;
        }

        if (this.rex.distanceToSqr(target) <= ATTACK_RANGE * ATTACK_RANGE) {
            if (this.rex.level() instanceof ServerLevel serverLevel) {

                this.rex.triggerAnim("AttackController", "attack_bite");

                this.rex.doHurtTarget(serverLevel, target);

                this.attackCooldown = ATTACK_COOLDOWN;
            }
        }
    }

    @Override
    public void stop() {
        this.attackCooldown = 0;
    }
}
