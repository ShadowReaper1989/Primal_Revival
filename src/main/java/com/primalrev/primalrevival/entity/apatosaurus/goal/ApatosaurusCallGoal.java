package com.primalrev.primalrevival.entity.apatosaurus.goal;

import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ApatosaurusCallGoal extends Goal {

    private static final int CALL_DURATION_TICKS = 40;

    private final ApatosaurusEntity apatosaurus;
    private int ticksRemaining;

    public ApatosaurusCallGoal(ApatosaurusEntity apatosaurus) {
        this.apatosaurus = apatosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        return this.apatosaurus.getTarget() != null && !this.apatosaurus.hasCalledAtCurrentTarget();
    }

    @Override
    public boolean canContinueToUse() {
        return this.ticksRemaining > 0 && this.apatosaurus.getTarget() != null;
    }

    @Override
    public void start() {
        this.ticksRemaining = CALL_DURATION_TICKS;
        this.apatosaurus.markCalledAtCurrentTarget();
        this.apatosaurus.triggerAnim("CallController", "call");
        this.apatosaurus.getNavigation().stop();
    }

    @Override
    public void tick() {
        this.ticksRemaining--;
        LivingEntity target = this.apatosaurus.getTarget();
        if (target != null) {
            this.apatosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
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
