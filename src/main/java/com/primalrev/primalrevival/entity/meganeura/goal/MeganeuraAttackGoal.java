package com.primalrev.primalrevival.entity.meganeura.goal;

import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;

import java.util.EnumSet;

public class MeganeuraAttackGoal extends Goal {

    private final MeganeuraEntity meganeura;

    private int attackCooldown;

    public MeganeuraAttackGoal(MeganeuraEntity meganeura) {
        this.meganeura = meganeura;

        this.setFlags(EnumSet.of(
                Goal.Flag.MOVE,
                Goal.Flag.LOOK
        ));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.meganeura.getTarget();

        return target != null
                && target.isAlive();
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.meganeura.getTarget();

        return target != null
                && target.isAlive();
    }

    @Override
    public void start() {
        this.attackCooldown = 0;
    }

    @Override
    public void tick() {
        LivingEntity target = this.meganeura.getTarget();

        if (target == null) {
            return;
        }

        this.meganeura.getLookControl()
                .setLookAt(target);

        this.meganeura.getNavigation()
                .moveTo(target, 1.2D);

        if (this.attackCooldown > 0) {
            this.attackCooldown--;
        }

        double attackRange = 2.5D;

        if (this.meganeura.distanceToSqr(target) <= attackRange * attackRange) {

            if (this.attackCooldown <= 0) {

                if (this.meganeura.level() instanceof ServerLevel serverLevel) {

                    this.meganeura.triggerAnim("AttackController", "attack_bite");

                    this.meganeura.swing(net.minecraft.world.InteractionHand.MAIN_HAND);

                    this.meganeura.doHurtTarget(serverLevel, target);
                }

                this.attackCooldown = 20;
            }
        }
    }

    @Override
    public void stop() {
        this.meganeura.getNavigation().stop();
    }
}
