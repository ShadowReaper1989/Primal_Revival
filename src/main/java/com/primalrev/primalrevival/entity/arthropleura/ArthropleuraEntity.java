package com.primalrev.primalrevival.entity.arthropleura;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.primalrev.primalrevival.entity.arthropleura.goal.ArthropleuraCurlGoal;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import org.jspecify.annotations.Nullable;
import net.minecraft.world.level.Level;


public class ArthropleuraEntity extends Animal implements GeoEntity {

    private static final EntityDataAccessor<Boolean> DATA_MALE =
            SynchedEntityData.defineId(ArthropleuraEntity.class, EntityDataSerializers.BOOLEAN);

    private final AnimatableInstanceCache geocache = GeckoLibUtil.createInstanceCache(this);

    private int curlState = 0;
    private int curlStateTimer = 0;

    public ArthropleuraEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 24.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 3.0D)
                .add(Attributes.FOLLOW_RANGE, 20.0D);
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
        builder.define(DATA_MALE, false);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor level, DifficultyInstance difficulty,
                                        EntitySpawnReason spawnReason, @Nullable SpawnGroupData spawnGroupData) {
        this.entityData.set(DATA_MALE, this.random.nextBoolean());
        return super.finalizeSpawn(level, difficulty, spawnReason, spawnGroupData);
    }

    public boolean isMale() {
        return this.entityData.get(DATA_MALE);
    }

    @Override
    public void addAdditionalSaveData(ValueOutput output) {
        super.addAdditionalSaveData(output);
        output.putBoolean("Male", this.isMale());
    }

    @Override
    public void readAdditionalSaveData(ValueInput input) {
        super.readAdditionalSaveData(input);
        this.entityData.set(DATA_MALE, input.getBooleanOr("Male", false));
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ArthropleuraCurlGoal(this));
        this.goalSelector.addGoal(1, new MeleeAttackGoal(this, 1.0D, true) {
            @Override
            protected void checkAndPerformAttack(LivingEntity target) {
                int before = this.getTicksUntilNextAttack();
                super.checkAndPerformAttack(target);
                if (before <= 0 && this.getTicksUntilNextAttack() > 0) {
                    ArthropleuraEntity.this.triggerAnim("AttackController", "attack");
                }
            }
        });
        this.goalSelector.addGoal(3, new RandomStrollGoal(this, 0.8D));
        this.goalSelector.addGoal(4, new LookAtPlayerGoal(this, net.minecraft.world.entity.player.Player.class, 8.0F));
        this.goalSelector.addGoal(5, new RandomLookAroundGoal(this));

        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }


    public boolean isCurled() {
        return this.curlState != 0;
    }

    public void startCurling() {
        if (this.curlState == 0) {
            this.curlState = 1;
            this.curlStateTimer = 20;
            this.getNavigation().stop();
        }
    }

    public void tickCurlState() {
        if (this.curlState == 0) return;

        this.curlStateTimer--;
        if (this.curlStateTimer > 0) return;

        switch (this.curlState) {
            case 1 -> {
                this.curlState = 2;
                this.curlStateTimer = 60;
            }
            case 2 -> {
                this.curlState = 3;
                this.curlStateTimer = 20;
            }
            case 3 -> this.curlState = 0;
        }
    }

    @Override
    public boolean hurtServer(ServerLevel level, DamageSource source, float amount) {
        boolean result = super.hurtServer(level, source, amount);
        if (result) {
            this.startCurling();
        }
        return result;
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public boolean isFood(ItemStack itemStack) {
        return false;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>("Movement", 5, state -> {
            if (this.curlState != 0) {
                return switch (this.curlState) {
                    case 1 -> state.setAndContinue(RawAnimation.begin().thenPlay("ball_start"));
                    case 2 -> state.setAndContinue(RawAnimation.begin().thenLoop("ball_loop"));
                    case 3 -> state.setAndContinue(RawAnimation.begin().thenPlay("ball_end"));
                    default -> state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
                };
            }

            boolean isMoving = this.getDeltaMovement().horizontalDistanceSqr() > 0.0004D;
            if (isMoving) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("walk"));
            }
            return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
        }));

        AnimationController<ArthropleuraEntity> attackController = new AnimationController<>(
                "AttackController", 0, state -> PlayState.STOP);
        attackController.triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        controllers.add(attackController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }
}