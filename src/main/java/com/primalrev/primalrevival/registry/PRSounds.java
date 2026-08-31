package com.primalrev.primalrevival.registry;

import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PRSounds {
    public static final DeferredRegister<SoundEvent> SOUNDS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.SOUND_EVENT, PrimalRevival.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> REX_IDLE = SOUNDS.register(
            "rex.idle",
            () -> SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "rex.idle")));

    public static final DeferredHolder<SoundEvent, SoundEvent> REX_HURT = SOUNDS.register(
            "rex.hurt",
            () -> SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "rex.hurt")));

    public static final DeferredHolder<SoundEvent, SoundEvent> REX_ROAR = SOUNDS.register(
            "rex.roar",
            () -> SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "rex.roar")));

    public static final DeferredHolder<SoundEvent, SoundEvent> REX_DEATH = SOUNDS.register(
            "rex.death",
            () -> SoundEvent.createVariableRangeEvent(
                    Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "rex.death")));
}