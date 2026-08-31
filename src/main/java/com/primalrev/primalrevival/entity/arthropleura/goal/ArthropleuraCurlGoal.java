package com.primalrev.primalrevival.entity.arthropleura.goal;

import com.primalrev.primalrevival.entity.arthropleura.ArthropleuraEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class ArthropleuraCurlGoal extends Goal {

    private final ArthropleuraEntity arthropleura;

    public ArthropleuraCurlGoal(ArthropleuraEntity arthropleura) {
        this.arthropleura = arthropleura;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK, Flag.JUMP));
    }

    @Override
    public boolean canUse() {
        return this.arthropleura.isCurled();
    }

    @Override
    public boolean canContinueToUse() {
        return this.arthropleura.isCurled();
    }

    @Override
    public void tick() {
        this.arthropleura.tickCurlState();
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
