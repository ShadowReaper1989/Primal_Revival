package com.primalrev.primalrevival.entity.apatosaurus;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.primalrev.primalrevival.entity.apatosaurus.goal.ApatosaurusAttackGoal;
import com.primalrev.primalrevival.entity.apatosaurus.goal.ApatosaurusCallGoal;
import com.primalrev.primalrevival.entity.apatosaurus.goal.ApatosaurusStompGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.UUID;


public class ApatosaurusEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache geocache = GeckoLibUtil.createInstanceCache(this);

    private UUID lastCalledTargetId;
    private int stompCooldown;

    public ApatosaurusEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 150.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.16D)
                .add(Attributes.ATTACK_DAMAGE, 12.0D)
                .add(Attributes.FOLLOW_RANGE, 24.0D)
                .add(Attributes.KNOCKBACK_RESISTANCE, 1.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ApatosaurusCallGoal(this));
        this.goalSelector.addGoal(1, new ApatosaurusStompGoal(this));
        this.goalSelector.addGoal(2, new ApatosaurusAttackGoal(this, 1.0D, true));
        this.goalSelector.addGoal(5, new RandomStrollGoal(this, 0.6D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 10.0F));
        this.goalSelector.addGoal(7, new RandomLookAroundGoal(this));


        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (this.stompCooldown > 0) {
            this.stompCooldown--;
        }
    }

    public int getStompCooldown() {
        return this.stompCooldown;
    }

    public void setStompCooldown(int ticks) {
        this.stompCooldown = ticks;
    }

    public boolean hasCalledAtCurrentTarget() {
        LivingEntity target = this.getTarget();
        return target != null && target.getUUID().equals(this.lastCalledTargetId);
    }

    public void markCalledAtCurrentTarget() {
        LivingEntity target = this.getTarget();
        this.lastCalledTargetId = target != null ? target.getUUID() : null;
    }

    @Override
    public boolean isFood(ItemStack stack) {

        return stack.is(net.minecraft.tags.ItemTags.LEAVES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return com.primalrev.primalrevival.registry.PREntities.APATOSAURUS.get()
                .create(serverLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>("Movement", 5, state -> {
            double speedSqr = this.getDeltaMovement().horizontalDistanceSqr();

            if (speedSqr <= 0.0003D) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }

            boolean isRunning = this.getTarget() != null && speedSqr > 0.01D;
            return state.setAndContinue(RawAnimation.begin().thenLoop(isRunning ? "run" : "walk"));
        }));

        AnimationController<ApatosaurusEntity> attackController = new AnimationController<>(
                "AttackController", 0, state -> PlayState.STOP);
        attackController.triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        controllers.add(attackController);

        AnimationController<ApatosaurusEntity> stompController = new AnimationController<>(
                "StompController", 0, state -> PlayState.STOP);
        stompController.triggerableAnim("stomp", RawAnimation.begin().thenPlay("stomp"));
        controllers.add(stompController);

        AnimationController<ApatosaurusEntity> callController = new AnimationController<>(
                "CallController", 0, state -> PlayState.STOP);
        callController.triggerableAnim("call", RawAnimation.begin().thenPlay("call"));
        controllers.add(callController);


        controllers.add(new AnimationController<>("DeathController", 0, state -> {
            if (this.isDeadOrDying()) {
                return state.setAndContinue(RawAnimation.begin().thenPlay("death"));
            }
            return PlayState.STOP;
        }));
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }
}
