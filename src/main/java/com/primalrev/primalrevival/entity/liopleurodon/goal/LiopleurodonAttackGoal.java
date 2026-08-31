package com.primalrev.primalrevival.entity.liopleurodon.goal;

import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class LiopleurodonAttackGoal extends MeleeAttackGoal {

    private final LiopleurodonEntity liopleurodon;

    public LiopleurodonAttackGoal(LiopleurodonEntity entity, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(entity, speedModifier, followTargetEvenIfNotSeen);
        this.liopleurodon = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {

        int ticksUntilNextAttackBefore = this.getTicksUntilNextAttack();

        super.checkAndPerformAttack(target);

        if (ticksUntilNextAttackBefore <= 0 && this.getTicksUntilNextAttack() > 0) {
            String anim = this.liopleurodon.isInWater() ? "attack_bite" : "bite_on_land";
            this.liopleurodon.triggerAnim("AttackController", anim);
        }
    }
}
