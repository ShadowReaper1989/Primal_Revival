package com.primalrev.primalrevival.entity.meganeura;

import com.geckolib.animatable.GeoEntity;
import com.geckolib.animatable.instance.AnimatableInstanceCache;
import com.geckolib.animatable.manager.AnimatableManager;
import com.geckolib.animation.AnimationController;
import com.geckolib.animation.RawAnimation;
import com.geckolib.constant.DefaultAnimations;
import com.primalrev.primalrevival.entity.meganeura.goal.MeganeuraAttackGoal;
import com.primalrev.primalrevival.entity.meganeura.goal.MeganeuraTargetGoal;
import com.primalrev.primalrevival.entity.meganeura.goal.MeganeuraWanderGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.level.Level;
import com.geckolib.util.GeckoLibUtil;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.state.BlockState;
import org.jspecify.annotations.Nullable;

public class MeganeuraEntity extends Animal implements GeoEntity {

    private final AnimatableInstanceCache geocache =
            GeckoLibUtil.createInstanceCache(this);

    public MeganeuraEntity(EntityType<? extends Animal> type, Level level) {
        super(type, level);

        this.moveControl = new FlyingMoveControl(this, 20, true);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Animal.createAnimalAttributes()
                .add(Attributes.MAX_HEALTH, 10.0D)
                .add(Attributes.FLYING_SPEED, 0.6D)
                .add(Attributes.MOVEMENT_SPEED, 0.3D)
                .add(Attributes.ATTACK_DAMAGE, 2.0D);
    }

    @Override
    protected PathNavigation createNavigation(Level level) {
        FlyingPathNavigation navigation = new FlyingPathNavigation(this, level);

        navigation.setCanOpenDoors(false);
        navigation.setCanFloat(false);

        return navigation;
    }


    @Override
    protected boolean omnidirectionalAirMover() {
        return true;
    }

    @Override
    protected void registerGoals() {


        this.goalSelector.addGoal(
                1,
                new MeganeuraAttackGoal(this)
        );


        this.goalSelector.addGoal(
                5,
                new MeganeuraWanderGoal(this)
        );

        this.targetSelector.addGoal(
                1,
                new MeganeuraTargetGoal(this)
        );
    }


    @Override
    public boolean isFood(net.minecraft.world.item.ItemStack stack) {
        return false;
    }

    protected void checkFallDamage(double ya, boolean onGround, BlockState onState, BlockPos pos) {
    }

    @Override
    public @Nullable AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        return null;
    }

    @Override
    public void registerControllers(AnimatableManager.ControllerRegistrar controllers) {
        controllers.add(new AnimationController<>(
                "Fly/Idle",
                0,
                state -> {
                    if (this.onGround()) {
                        return state.setAndContinue(DefaultAnimations.IDLE);
                    }

                    return state.setAndContinue(DefaultAnimations.FLY);
                }
        ));

        AnimationController<MeganeuraEntity> attackController = new AnimationController<>(
                "AttackController",
                0,
                state -> com.geckolib.animation.object.PlayState.STOP
        );

        attackController.triggerableAnim("attack_bite", RawAnimation.begin().thenPlay("attack.bite"));

        controllers.add(attackController);
    }

    @Override
    public AnimatableInstanceCache getAnimatableInstanceCache() {
        return this.geocache;
    }

}