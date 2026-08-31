package com.primalrev.primalrevival.entity.liopleurodon;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.primalrev.primalrevival.entity.liopleurodon.goal.LiopleurodonAttackGoal;
import com.primalrev.primalrevival.entity.liopleurodon.goal.LiopleurodonRoarGoal;
import com.primalrev.primalrevival.entity.liopleurodon.goal.LiopleurodonTargetGoal;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.SmoothSwimmingLookControl;
import net.minecraft.world.entity.ai.control.SmoothSwimmingMoveControl;
import net.minecraft.world.entity.ai.goal.PanicGoal;
import net.minecraft.world.entity.ai.goal.RandomSwimmingGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.ai.navigation.AmphibiousPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.animal.fish.WaterAnimal;
import net.minecraft.world.level.Level;

public class LiopleurodonEntity extends WaterAnimal implements GeoEntity {

    private static final int MAX_OUT_OF_WATER_TICKS = 1200;

    private final AnimatableInstanceCache geocache = GeckoLibUtil.createInstanceCache(this);

    private int outOfWaterTicks = 0;
    private long lastRoarGameTime = -100000L;

    public LiopleurodonEntity(EntityType<? extends WaterAnimal> type, Level level) {
        super(type, level);

        this.moveControl = new SmoothSwimmingMoveControl(this, 85, 10, 0.02F, 0.1F, true);
        this.lookControl = new SmoothSwimmingLookControl(this, 10);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 60.0D)

                .add(Attributes.MOVEMENT_SPEED, 1.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        return new AmphibiousPathNavigation(this, level);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new LiopleurodonRoarGoal(this));
        this.goalSelector.addGoal(1, new LiopleurodonAttackGoal(this, 1.5D, true));
        this.goalSelector.addGoal(2, new PanicGoal(this, 1.6D));
        this.goalSelector.addGoal(5, new RandomSwimmingGoal(this, 1.0D, 40));

        this.targetSelector.addGoal(1, new LiopleurodonTargetGoal(this));
        this.targetSelector.addGoal(2, new NearestAttackableTargetGoal<>(this, RexEntity.class, true));
    }


    public boolean canRoarNow() {
        return this.level().getGameTime() - this.lastRoarGameTime >= 200L;
    }

    public void markRoared() {
        this.lastRoarGameTime = this.level().getGameTime();
    }

    @Override
    public boolean isPushedByFluid() {
        return false;
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public boolean isInWater() {
        return this.level().getFluidState(this.blockPosition()).is(net.minecraft.tags.FluidTags.WATER)
                || this.level().getFluidState(this.blockPosition().above()).is(net.minecraft.tags.FluidTags.WATER);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.level().isClientSide()) return;

        if (this.isInWater()) {
            this.outOfWaterTicks = 0;
        } else {
            this.outOfWaterTicks++;
            if (this.outOfWaterTicks > MAX_OUT_OF_WATER_TICKS && this.outOfWaterTicks % 20 == 0) {
                this.hurtServer((ServerLevel) this.level(), this.damageSources().dryOut(), 1.0F);
            }
        }
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>("Movement", 5, state -> {
            boolean inWater = this.isInWater();
            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.0004D;

            if (!inWater) {
                return state.setAndContinue(RawAnimation.begin().thenLoop(isMoving ? "stranded_walk" : "stranded"));
            }

            if (!isMoving) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }


            double speedAttr = this.getAttributeValue(Attributes.MOVEMENT_SPEED);
            double fastThresholdSqr = (speedAttr * speedAttr) * 0.02D;
            boolean isFast = this.getDeltaMovement().horizontalDistanceSqr() > fastThresholdSqr;

            return state.setAndContinue(RawAnimation.begin().thenLoop(isFast ? "swim_faster" : "swim"));
        }));

        AnimationController<LiopleurodonEntity> attackController = new AnimationController<>(
                "AttackController", 0, state -> PlayState.STOP);
        attackController.triggerableAnim("attack_bite", RawAnimation.begin().thenPlay("attack"));
        attackController.triggerableAnim("bite_on_land", RawAnimation.begin().thenPlay("bite_on_land"));
        controllers.add(attackController);

        AnimationController<LiopleurodonEntity> roarController = new AnimationController<>(
                "RoarController", 0, state -> PlayState.STOP);
        roarController.triggerableAnim("roar", RawAnimation.begin().thenPlay("roar"));
        controllers.add(roarController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }
}