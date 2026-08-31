package com.primalrev.primalrevival.registry;

import com.primalrev.primalrevival.PrimalRevival;
import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusEntity;
import com.primalrev.primalrevival.entity.arthropleura.ArthropleuraEntity;
import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import com.primalrev.primalrevival.entity.mummy.MummyEntity;
import com.primalrev.primalrevival.entity.plumalexius.PlumalexiusEntity;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import com.primalrev.primalrevival.entity.scutosaurus.ScutosaurusEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PREntities {

    public static final DeferredRegister<EntityType<?>> ENTITIES =
            DeferredRegister.create(
                    Registries.ENTITY_TYPE,
                    PrimalRevival.MODID
            );

    public static final DeferredHolder<EntityType<?>, EntityType<MeganeuraEntity>> MEGANEURA =
            ENTITIES.register("meganeura", registryName ->
                    EntityType.Builder.of(MeganeuraEntity::new, MobCategory.CREATURE)
                            .sized(1.5f, 0.8f)
                            .build(
                                    ResourceKey.create(
                                            Registries.ENTITY_TYPE,
                                            registryName
                                    )
                            )
            );
    public static final DeferredHolder<EntityType<?>, EntityType<RexEntity>> REX =
            ENTITIES.register("rex", registryName ->
                    EntityType.Builder.of(RexEntity::new, MobCategory.CREATURE)
                            .sized(2.8F, 5.2F)
                            .build(
                                    ResourceKey.create(
                                            Registries.ENTITY_TYPE,
                                            registryName
                                    )
                            )
            );

    public static final DeferredHolder<EntityType<?>, EntityType<LiopleurodonEntity>> LIOPLEURODON =
            ENTITIES.register("liopleurodon", registryName ->
                    EntityType.Builder.of(LiopleurodonEntity::new, MobCategory.WATER_CREATURE)
                            .sized(4.5F, 2.5F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );


    public static final DeferredHolder<EntityType<?>, EntityType<MummyEntity>> MUMMY =
            ENTITIES.register("mummy", registryName ->
                    EntityType.Builder.of(MummyEntity::new, MobCategory.MONSTER)
                            .sized(0.6F, 1.95F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );

    public static final DeferredHolder<EntityType<?>, EntityType<ArthropleuraEntity>> ARTHROPLEURA =
            ENTITIES.register("arthropleura", registryName ->
                    EntityType.Builder.of(ArthropleuraEntity::new, MobCategory.CREATURE)
                            .sized(2.0F, 1.0F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );

    public static final DeferredHolder<EntityType<?>, EntityType<ScutosaurusEntity>> SCUTOSAURUS =
            ENTITIES.register("scutosaurus", registryName ->
                    EntityType.Builder.of(ScutosaurusEntity::new, MobCategory.CREATURE)
                            .sized(1.8F, 1.1F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );

    public static final DeferredHolder<EntityType<?>, EntityType<PlumalexiusEntity>> PLUMALEXIUS =
            ENTITIES.register("plumalexius", registryName ->
                    EntityType.Builder.of(PlumalexiusEntity::new, MobCategory.MONSTER)
                            .sized(1.0F, 0.8F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );


    public static final DeferredHolder<EntityType<?>, EntityType<ApatosaurusEntity>> APATOSAURUS =
            ENTITIES.register("apatosaurus", registryName ->
                    EntityType.Builder.of(ApatosaurusEntity::new, MobCategory.CREATURE)
                            .sized(6.0F, 5.5F)
                            .build(ResourceKey.create(Registries.ENTITY_TYPE, registryName))
            );
}
