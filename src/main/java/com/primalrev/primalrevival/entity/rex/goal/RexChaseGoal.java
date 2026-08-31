package com.primalrev.primalrevival.entity.rex.goal;

import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class RexChaseGoal extends Goal {

    private final RexEntity rex;

    public RexChaseGoal(RexEntity rex) {
        this.rex = rex;

        this.setFlags(EnumSet.of(
                Goal.Flag.MOVE,
                Goal.Flag.LOOK
        ));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.rex.getTarget();

        return target != null
                && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.rex.getTarget();

        return target != null
                && target.isAlive();
    }

    @Override
    public void tick() {
        LivingEntity target = this.rex.getTarget();

        if (target == null) {
            return;
        }

        this.rex.getLookControl().setLookAt(target);

        this.rex.getNavigation().moveTo(target, 1.0D);
    }

    @Override
    public void stop() {
        this.rex.getNavigation().stop();
    }
}

