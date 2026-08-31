package com.primalrev.primalrevival.entity.liopleurodon.goal;

import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class LiopleurodonRoarGoal extends Goal {

    private static final int ROAR_DURATION_TICKS = 40;

    private final LiopleurodonEntity liopleurodon;
    private int ticksRemaining;

    public LiopleurodonRoarGoal(LiopleurodonEntity liopleurodon) {
        this.liopleurodon = liopleurodon;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.liopleurodon.getTarget() != null && this.liopleurodon.canRoarNow();
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksRemaining > 0 && this.liopleurodon.getTarget() != null;
    }

    @Override
    public void start() {
        this.ticksRemaining = ROAR_DURATION_TICKS;
        this.liopleurodon.markRoared();
        this.liopleurodon.triggerAnim("RoarController", "roar");
        this.liopleurodon.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.ticksRemaining--;
        LivingEntity target = this.liopleurodon.getTarget();
        if (target != null) {
            this.liopleurodon.getLookControl().setLookAt(target, 30.0F, 30.0F);
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