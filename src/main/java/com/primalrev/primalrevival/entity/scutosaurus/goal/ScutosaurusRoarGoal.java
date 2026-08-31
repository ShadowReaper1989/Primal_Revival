package com.primalrev.primalrevival.entity.scutosaurus.goal;

import com.primalrev.primalrevival.entity.scutosaurus.ScutosaurusEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ScutosaurusRoarGoal extends Goal {

    private static final int ROAR_DURATION_TICKS = 50;

    private final ScutosaurusEntity scutosaurus;
    private int ticksRemaining;

    public ScutosaurusRoarGoal(ScutosaurusEntity scutosaurus) {
        this.scutosaurus = scutosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.scutosaurus.getTarget() != null && !this.scutosaurus.hasRoaredAtCurrentTarget();
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksRemaining > 0 && this.scutosaurus.getTarget() != null;
    }

    @Override
    public void start() {
        this.ticksRemaining = ROAR_DURATION_TICKS;
        this.scutosaurus.markRoaredAtCurrentTarget();
        this.scutosaurus.triggerAnim("RoarController", "roar");
        this.scutosaurus.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.ticksRemaining--;
        LivingEntity target = this.scutosaurus.getTarget();
        if (target != null) {
            this.scutosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
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