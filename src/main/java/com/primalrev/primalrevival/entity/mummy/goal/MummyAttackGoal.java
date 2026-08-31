package com.primalrev.primalrevival.entity.mummy.goal;

import com.primalrev.primalrevival.entity.mummy.MummyEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class MummyAttackGoal extends MeleeAttackGoal {

    private final MummyEntity mummy;

    public MummyAttackGoal(MummyEntity entity, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(entity, speedModifier, followTargetEvenIfNotSeen);
        this.mummy = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {

        int ticksUntilNextAttackBefore = this.getTicksUntilNextAttack();

        super.checkAndPerformAttack(target);

        if (ticksUntilNextAttackBefore <= 0 && this.getTicksUntilNextAttack() > 0) {
            this.mummy.triggerAnim("AttackController", "attack");
        }
    }
}
