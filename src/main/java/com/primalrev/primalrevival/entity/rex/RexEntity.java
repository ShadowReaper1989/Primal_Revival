package com.primalrev.primalrevival.entity.rex;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.primalrev.primalrevival.entity.rex.goal.*;
import com.primalrev.primalrevival.registry.PRSounds;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.animal.Animal;
import org.jspecify.annotations.Nullable;

public class RexEntity extends Animal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> IS_CHASING =
            SynchedEntityData.defineId(RexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_OBSERVING =
            SynchedEntityData.defineId(RexEntity.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> IS_ROARING =
            SynchedEntityData.defineId(RexEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache geocache =
            GeckoLibUtil.createInstanceCache(this);
    private LivingEntity lastObservedTarget = null;
    private LivingEntity lastRoaredTarget = null;

    public RexEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(IS_CHASING, false);
        builder.define(IS_OBSERVING, false);
        builder.define(IS_ROARING, false);
    }

    public boolean isChasing() {
        return this.entityData.get(IS_CHASING);
    }

    public void setChasing(boolean chasing) {
        this.entityData.set(IS_CHASING, chasing);
    }

    public boolean isObserving() {
        return this.entityData.get(IS_OBSERVING);
    }

    public void setObserving(boolean observing) {
        this.entityData.set(IS_OBSERVING, observing);
    }

    public boolean hasObservedTarget(LivingEntity target) {
        return this.lastObservedTarget == target;
    }

    public void setLastObservedTarget(LivingEntity target) {
        this.lastObservedTarget = target;
    }

    public boolean isRoaring() {
        return this.entityData.get(IS_ROARING);
    }

    public void setRoaring(boolean roaring) {
        boolean wasRoaring = this.isRoaring();
        this.entityData.set(IS_ROARING, roaring);

        if (roaring && !wasRoaring && !this.level().isClientSide()) {
            this.playSound(PRSounds.REX_ROAR.get(), 1.0F, 1.0F);
        }
    }

    public boolean hasRoaredTarget(LivingEntity target) {
        return this.lastRoaredTarget == target;
    }

    public void setLastRoaredTarget(LivingEntity target) {
        this.lastRoaredTarget = target;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isClientSide()) {
            LivingEntity target = this.getTarget();
            if (target == null || !target.isAlive()) {
                if (this.isObserving()) setObserving(false);
                if (this.isRoaring()) setRoaring(false);
                if (this.isChasing()) setChasing(false);
            } else {
                boolean activelyChasing = !this.isObserving() && !this.isRoaring();
                this.setChasing(activelyChasing);
            }
        }
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 50.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.5D)
                .add(Attributes.ATTACK_DAMAGE, 10.0D)
                .add(Attributes.FOLLOW_RANGE, 16.0D);
    }

    @Override
    public void setTarget(@Nullable LivingEntity target) {
        LivingEntity previousTarget = this.getTarget();
        super.setTarget(target);

        if (target != previousTarget) {
            if (target == null || !target.isAlive()) {
                this.setLastObservedTarget(null);
                this.setLastRoaredTarget(null);
            }
        }
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();

        this.goalSelector.addGoal(
                1,
                new RexObserveGoal(this)
        );

        this.goalSelector.addGoal(
                2,
                new RexRoarGoal(this)
        );

        this.goalSelector.addGoal(
                3,
                new RexAttackGoal(this)
        );

        this.goalSelector.addGoal(
                4,
                new RexChaseGoal(this)
        );

        this.goalSelector.addGoal(
                5,
                new RandomStrollGoal(this, 1.0D)
        );

        this.targetSelector.addGoal(1, new RexTargetGoal(this));
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>(
                "Movement",
                2,
                state -> {
                    if (this.isRoaring()) {
                        return state.setAndContinue(RawAnimation.begin().thenPlay("misc.roar"));
                    }
                    if (this.isObserving()) {
                        return state.setAndContinue(RawAnimation.begin().thenPlay("misc.look"));
                    }

                    double horizontalSpeed = this.getDeltaMovement().horizontalDistanceSqr();

                    if (this.isChasing() && horizontalSpeed > 0.0001D) {
                        return state.setAndContinue(RawAnimation.begin().thenLoop("move.run"));
                    }
                    if (horizontalSpeed > 0.0001D) {
                        return state.setAndContinue(RawAnimation.begin().thenLoop("move.walk"));
                    }

                    return state.setAndContinue(RawAnimation.begin().thenLoop("misc.idle"));
                }
        ));

        AnimationController<RexEntity> attackController =
                new AnimationController<>(
                        "AttackController",
                        2,
                        state -> PlayState.STOP
                );

        attackController.triggerableAnim(
                "attack_bite",
                RawAnimation.begin().thenPlay("attack.bite")
        );

        controllers.add(attackController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return PRSounds.REX_IDLE.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return PRSounds.REX_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return PRSounds.REX_DEATH.get();
    }
}