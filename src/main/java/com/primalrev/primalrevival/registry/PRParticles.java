package com.primalrev.primalrevival.registry;

import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.core.particles.ParticleType;
import net.neoforged.neoforge.registries.DeferredRegister;
import java.util.function.Supplier;

public class PRParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLES =
            DeferredRegister.create(net.minecraft.core.registries.Registries.PARTICLE_TYPE, PrimalRevival.MODID);
}