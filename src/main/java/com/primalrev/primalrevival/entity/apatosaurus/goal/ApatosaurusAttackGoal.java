package com.primalrev.primalrevival.entity.apatosaurus.goal;

import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class ApatosaurusAttackGoal extends MeleeAttackGoal {

    private final ApatosaurusEntity apatosaurus;

    public ApatosaurusAttackGoal(ApatosaurusEntity entity, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(entity, speedModifier, followTargetEvenIfNotSeen);
        this.apatosaurus = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        int before = this.getTicksUntilNextAttack();
        super.checkAndPerformAttack(target);
        if (before <= 0 && this.getTicksUntilNextAttack() > 0) {
            this.apatosaurus.triggerAnim("AttackController", "attack");
        }
    }
}
