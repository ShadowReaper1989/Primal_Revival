package com.primalrev.primalrevival.entity.rex.goal;

import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.target.TargetGoal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;

import java.util.List;

public class RexTargetGoal extends TargetGoal {

    private final RexEntity rex;
    private final TargetingConditions targetingConditions;
    private LivingEntity potentialTarget;

    public RexTargetGoal(RexEntity rex) {
        super(rex, true, false);
        this.rex = rex;

        this.targetingConditions = TargetingConditions.forCombat()
                .range(16.0D)
                .selector((entity, level) -> entity != this.rex
                        && (entity instanceof Player
                        || entity instanceof Animal
                        || entity instanceof LiopleurodonEntity));
    }

    @Override
    public boolean canUse() {
        if (this.rex.getTarget() != null && this.rex.getTarget().isAlive()) {
            return false;
        }

        if (!(this.rex.level() instanceof ServerLevel serverLevel)) {
            return false;
        }

        List<LivingEntity> targets = serverLevel.getEntitiesOfClass(
                LivingEntity.class,
                this.rex.getBoundingBox().inflate(16.0D),
                entity -> entity != this.rex && entity.isAlive()
                        && (entity instanceof Player
                        || entity instanceof Animal
                        || entity instanceof LiopleurodonEntity)
        );

        if (targets.isEmpty()) {
            return false;
        }

        this.potentialTarget = serverLevel.getNearestEntity(
                targets,
                this.targetingConditions,
                this.rex,
                this.rex.getX(),
                this.rex.getY(),
                this.rex.getZ()
        );

        return this.potentialTarget != null;
    }

    @Override
    public void start() {
        this.rex.setTarget(this.potentialTarget);
        super.start();
    }
}