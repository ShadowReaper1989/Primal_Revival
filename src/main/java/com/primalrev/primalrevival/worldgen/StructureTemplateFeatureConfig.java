package com.primalrev.primalrevival.worldgen;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;

public record StructureTemplateFeatureConfig(
        Identifier structureId,
        int offsetX,
        int offsetY,
        int offsetZ
) implements FeatureConfiguration {
    public static final Codec<StructureTemplateFeatureConfig> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            Identifier.CODEC.fieldOf("structure").forGetter(StructureTemplateFeatureConfig::structureId),
            Codec.INT.optionalFieldOf("offset_x", 0).forGetter(StructureTemplateFeatureConfig::offsetX),
            Codec.INT.optionalFieldOf("offset_y", 0).forGetter(StructureTemplateFeatureConfig::offsetY),
            Codec.INT.optionalFieldOf("offset_z", 0).forGetter(StructureTemplateFeatureConfig::offsetZ)
    ).apply(inst, StructureTemplateFeatureConfig::new));
}