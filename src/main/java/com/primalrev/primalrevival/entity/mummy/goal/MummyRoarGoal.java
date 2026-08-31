package com.primalrev.primalrevival.entity.mummy.goal;

import com.primalrev.primalrevival.entity.mummy.MummyEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MummyRoarGoal extends Goal {


    private static final int ROAR_DURATION_TICKS = 50;

    private final MummyEntity mummy;
    private int ticksRemaining;

    public MummyRoarGoal(MummyEntity mummy) {
        this.mummy = mummy;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.mummy.getTarget() != null && !this.mummy.hasRoaredAtCurrentTarget();
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksRemaining > 0 && this.mummy.getTarget() != null;
    }

    @Override
    public void start() {
        this.ticksRemaining = ROAR_DURATION_TICKS;
        this.mummy.markRoaredAtCurrentTarget();
        this.mummy.triggerAnim("RoarController", "roar");

        this.mummy.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.ticksRemaining--;
        LivingEntity target = this.mummy.getTarget();
        if (target != null) {
            this.mummy.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }
    }

    @Override
    public void stop() {
        this.ticksRemaining = 0;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
