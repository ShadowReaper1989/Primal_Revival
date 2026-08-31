package com.primalrev.primalrevival.entity.rex.goal;

import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RexRoarGoal extends Goal {

    private static final int ROAR_TIME = 70;

    private final RexEntity rex;
    private int roarTicks;

    public RexRoarGoal(RexEntity rex) {
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

        return this.rex.hasObservedTarget(target) && !this.rex.hasRoaredTarget(target);
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.rex.getTarget();

        return target != null
                && target.isAlive()
                && this.roarTicks < ROAR_TIME;
    }

    @Override
    public void start() {
        this.roarTicks = 0;
        this.rex.getNavigation().stop();
        this.rex.setRoaring(true);
    }

    @Override
    public void tick() {
        LivingEntity target = this.rex.getTarget();

        if (target != null) {
            this.rex.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        this.roarTicks++;
    }

    @Override
    public void stop() {
        LivingEntity target = this.rex.getTarget();

        if (target != null) {
            this.rex.setLastRoaredTarget(target);
        }

        this.rex.setRoaring(false);
    }
}
