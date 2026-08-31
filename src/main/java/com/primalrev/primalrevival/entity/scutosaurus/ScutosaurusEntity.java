package com.primalrev.primalrevival.entity.scutosaurus;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.animation.object.PlayState;
import com.geckolib.util.GeckoLibUtil;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import com.primalrev.primalrevival.entity.scutosaurus.goal.ScutosaurusAttackGoal;
import com.primalrev.primalrevival.entity.scutosaurus.goal.ScutosaurusRoarGoal;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.AvoidEntityGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.RandomStrollGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jspecify.annotations.Nullable;

import java.util.UUID;

public class ScutosaurusEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache geocache = GeckoLibUtil.createInstanceCache(this);

    private UUID lastRoaredTargetId;

    public ScutosaurusEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 30.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.2D)
                .add(Attributes.ATTACK_DAMAGE, 5.0D)
                .add(Attributes.FOLLOW_RANGE, 8.0D);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new ScutosaurusRoarGoal(this));
        this.goalSelector.addGoal(1, new AvoidEntityGoal<>(this, RexEntity.class, 10.0F, 1.0D, 1.3D));
        this.goalSelector.addGoal(2, new ScutosaurusAttackGoal(this, 1.0D, true));
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
    public boolean isFood(ItemStack stack) {
        return stack.is(net.minecraft.tags.ItemTags.LEAVES);
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return com.primalrev.primalrevival.registry.PREntities.SCUTOSAURUS.get()
                .create(serverLevel, EntitySpawnReason.BREEDING);
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {

        controllers.add(new AnimationController<>("Movement", 5, state -> {
            double speedSqr = this.getDeltaMovement().horizontalDistanceSqr();

            if (speedSqr <= 0.0004D) {
                return state.setAndContinue(RawAnimation.begin().thenLoop("idle"));
            }

            boolean isRunning = this.getTarget() != null || speedSqr > 0.02D;
            return state.setAndContinue(RawAnimation.begin().thenLoop(isRunning ? "run" : "walk"));
        }));

        AnimationController<ScutosaurusEntity> attackController = new AnimationController<>(
                "AttackController", 0, state -> PlayState.STOP);
        attackController.triggerableAnim("attack", RawAnimation.begin().thenPlay("attack"));
        controllers.add(attackController);

        AnimationController<ScutosaurusEntity> roarController = new AnimationController<>(
                "RoarController", 0, state -> PlayState.STOP);
        roarController.triggerableAnim("roar", RawAnimation.begin().thenPlay("roar"));
        controllers.add(roarController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }
}