package com.primalrev.primalrevival.entity.meganeura.goal;

import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MeganeuraWanderGoal extends Goal {

    private final MeganeuraEntity meganeura;

    private BlockPos targetPosition;

    public MeganeuraWanderGoal(MeganeuraEntity meganeura) {
        this.meganeura = meganeura;

        this.setFlags(EnumSet.of(
                Goal.Flag.MOVE
        ));
    }

    @Override
    public boolean canUse() {

        if (meganeura.getTarget() != null)
            return false;

        targetPosition = findRandomPosition();

        return targetPosition != null;
    }

    @Override
    public boolean canContinueToUse() {

        if (meganeura.getTarget() != null)
            return false;

        return !meganeura.getNavigation().isDone();
    }

    @Override
    public void start() {

        if (targetPosition != null) {
            meganeura.getNavigation().moveTo(
                    targetPosition.getX() + 0.5D,
                    targetPosition.getY() + 0.5D,
                    targetPosition.getZ() + 0.5D,
                    1.0D
            );
        }
    }

    @Override
    public void stop() {
        meganeura.getNavigation().stop();
        targetPosition = null;
    }

    private BlockPos findRandomPosition() {

        int x = meganeura.getRandom().nextInt(17) - 8;
        int y = meganeura.getRandom().nextInt(9) - 4;
        int z = meganeura.getRandom().nextInt(17) - 8;

        BlockPos origin = meganeura.blockPosition();

        BlockPos candidate = origin.offset(x, y, z);

        if (candidate.getY() < meganeura.level().getMinY() + 2)
            return null;

        return candidate;
    }
}
