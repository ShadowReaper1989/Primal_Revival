package com.primalrev.primalrevival.entity.scutosaurus.goal;

import com.primalrev.primalrevival.entity.scutosaurus.ScutosaurusEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ScutosaurusAttackGoal extends MeleeAttackGoal {

    private final ScutosaurusEntity scutosaurus;

    public ScutosaurusAttackGoal(ScutosaurusEntity entity, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(entity, speedModifier, followTargetEvenIfNotSeen);
        this.scutosaurus = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        int before = this.getTicksUntilNextAttack();
        super.checkAndPerformAttack(target);
        if (before <= 0 && this.getTicksUntilNextAttack() > 0) {
            this.scutosaurus.triggerAnim("AttackController", "attack");
        }
    }
}