package com.primalrev.primalrevival.entity.rex.goal;

import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RexObserveGoal extends Goal {

    private final RexEntity rex;
    private int observationTicks;

    private static final int OBSERVATION_TIME = 65;

    public RexObserveGoal(RexEntity rex) {
        this.rex = rex;

        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.rex.getTarget();

        if (target == null || !target.isAlive()) {
            return false;
        }

        if (this.rex.hasObservedTarget(target)) {
            return false;
        }

        return this.rex.distanceToSqr(target) <= 4.0D * 4.0D;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.rex.getTarget();

        return target != null
                && target.isAlive()
                && this.observationTicks < OBSERVATION_TIME;
    }

    @Override
    public void start() {
        this.observationTicks = 0;

        this.rex.getNavigation().stop();

        this.rex.setObserving(true);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rex.getTarget();

        if (target != null) {
            this.rex.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        this.observationTicks++;
    }

    @Override
    public void stop() {
        LivingEntity target = this.rex.getTarget();

        if (target != null) {
            this.rex.setLastObservedTarget(target);
        }

        this.rex.setObserving(false);
    }
}
