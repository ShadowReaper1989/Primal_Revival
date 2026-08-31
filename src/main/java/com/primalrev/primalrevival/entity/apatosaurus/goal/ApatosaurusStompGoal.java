package com.primalrev.primalrevival.entity.apatosaurus.goal;

import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

import java.util.EnumSet;
import java.util.List;

/**
 * Pisotón en área: cuando el objetivo está pegado al Apatosaurus, este se
 * detiene, hace el "windup" de la animación "stomp" y al final del windup
 * golpea con daño y empuje fuerte a todo lo que esté en el radio, no solo
 * al objetivo. Tiene cooldown para que no se spamee.
 *
 * NOTA: `hurtServer(ServerLevel, DamageSource, float)` corresponde a las
 * mappings recientes de Minecraft/NeoForge; si tu versión usa la firma
 * clásica `hurt(DamageSource, float)`, cambiá esa única línea.
 */
public class ApatosaurusStompGoal extends Goal {

    private static final double STOMP_RANGE = 3.5D;
    private static final double STOMP_RADIUS = 4.0D;
    private static final float STOMP_DAMAGE = 18.0F;
    private static final float STOMP_KNOCKBACK = 1.6F;
    private static final int STOMP_WINDUP_TICKS = 12;
    private static final int STOMP_COOLDOWN_TICKS = 100;

    private final ApatosaurusEntity apatosaurus;
    private int windup = -1;

    public ApatosaurusStompGoal(ApatosaurusEntity apatosaurus) {
        this.apatosaurus = apatosaurus;
        this.setFlags(EnumSet.of(Flag.MOVE, Flag.LOOK));
    }

    @Override
    public boolean canUse() {
        LivingEntity target = this.apatosaurus.getTarget();
        if (target == null || !target.isAlive() || this.apatosaurus.getStompCooldown() > 0) {
            return false;
        }
        return this.apatosaurus.distanceToSqr(target) <= STOMP_RANGE * STOMP_RANGE;
    }

    @Override
    public boolean canContinueToUse() {
        LivingEntity target = this.apatosaurus.getTarget();
        return this.windup >= 0 && target != null && target.isAlive();
    }

    @Override
    public void start() {
        this.windup = STOMP_WINDUP_TICKS;
        this.apatosaurus.getNavigation().stop();
        this.apatosaurus.triggerAnim("StompController", "stomp");
    }

    @Override
    public void tick() {
        LivingEntity target = this.apatosaurus.getTarget();
        if (target != null) {
            this.apatosaurus.getLookControl().setLookAt(target, 30.0F, 30.0F);
        }

        if (this.windup > 0) {
            this.windup--;
            return;
        }

        if (this.windup == 0) {
            this.doStompImpact();
            this.windup = -1;
            this.apatosaurus.setStompCooldown(STOMP_COOLDOWN_TICKS);
        }
    }

    private void doStompImpact() {
        if (!(this.apatosaurus.level() instanceof ServerLevel serverLevel)) {
            return;
        }

        AABB area = this.apatosaurus.getBoundingBox().inflate(STOMP_RADIUS);
        List<LivingEntity> victims = serverLevel.getEntitiesOfClass(LivingEntity.class, area,
                entity -> entity != this.apatosaurus
                        && entity.isAlive()
                        && !(entity instanceof Player player && player.isCreative()));

        for (LivingEntity victim : victims) {
            victim.hurtServer(serverLevel, this.apatosaurus.damageSources().mobAttack(this.apatosaurus), STOMP_DAMAGE);

            Vec3 diff = victim.position().subtract(this.apatosaurus.position());
            Vec3 push = (diff.lengthSqr() > 1.0E-4D ? diff.normalize() : Vec3.ZERO).scale(STOMP_KNOCKBACK);
            victim.setDeltaMovement(victim.getDeltaMovement().add(push.x, 0.5D, push.z));
            victim.hurtMarked = true;
        }
    }

    @Override
    public void stop() {
        this.windup = -1;
    }

    @Override
    public boolean isInterruptable() {
        return false;
    }
}
