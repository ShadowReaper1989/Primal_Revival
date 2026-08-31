package com.primalrev.primalrevival.entity.mummy;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.primalrev.primalrevival.entity.mummy.goal.MummyAttackGoal;
import com.primalrev.primalrevival.entity.mummy.goal.MummyRoarGoal;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class MummyEntity extends Monster implements GeoEntity {

    private final AnimatableInstanceCache geocache = GeckoLibUtil.createInstanceCache(this);


    private UUID lastRoaredTargetId;

    public MummyEntity(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {

        return Monster.createMonsterAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.23D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D)
                .add(Attributes.FOLLOW_RANGE, 28.0D);
    }

    @Override
    protected void registerGoals() {

        this.goalSelector.addGoal(1, new MummyRoarGoal(this));


        this.goalSelector.addGoal(2, new MummyAttackGoal(this, 1.0D, true));


        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new NearestAttackableTargetGoal<>(this, Player.class, true));
    }


    public boolean hasRoaredAtCurrentTarget() {
        LivingEntity target = this.getTarget();
        return target != null && target.getUUID().equals(this.lastRoaredTargetId);
    }


    public void markRoaredAtCurrentTarget() {
        LivingEntity target = this.getTarget();
        this.lastRoaredTargetId = target != null ? target.getUUID() : null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>("Movement", 5, state -> {
            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.0004D;

            if (!isMoving) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }

            if (this.getTarget() != null) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("run"));
            }

            return state.setAndContinue(RawAnimation.begin().thenLoop("walk"));
        }));

        AnimationController<MummyEntity> attackController = new AnimationController<>(
                "AttackController", 0, state -> PlayState.STOP);
        attackController.triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        controllers.add(attackController);

        AnimationController<MummyEntity> roarController = new AnimationController<>(
                "RoarController", 0, state -> PlayState.STOP);
        roarController.triggerableAnim("roar", RawAnimation.begin().thenPlay("roar"));
        controllers.add(roarController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }
}