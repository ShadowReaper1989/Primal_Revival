package com.primalrev.primalrevival.entity.plumalexius.goal;

import com.primalrev.primalrevival.entity.plumalexius.PlumalexiusEntity;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;

public class PlumalexiusAttackGoal extends MeleeAttackGoal {

    private final PlumalexiusEntity plumalexius;

    public PlumalexiusAttackGoal(PlumalexiusEntity entity, double speedModifier, boolean followTargetEvenIfNotSeen) {
        super(entity, speedModifier, followTargetEvenIfNotSeen);
        this.plumalexius = entity;
    }

    @Override
    protected void checkAndPerformAttack(LivingEntity target) {
        int before = this.getTicksUntilNextAttack();
        super.checkAndPerformAttack(target);
        if (before <= 0 && this.getTicksUntilNextAttack() > 0) {
            this.plumalexius.triggerAnim("AttackController", "attack");
        }

        if (this.plumalexius.distanceTo(target) <= 2.5D) {
            target.addEffect(new MobEffectInstance(MobEffects.POISON, 80, 0, false, true));
            target.addEffect(new MobEffectInstance(MobEffects.DARKNESS, 120, 0, false, true));
        }
    }
}