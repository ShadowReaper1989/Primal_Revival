package com.primalrev.primalrevival.registry;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.List;
import java.util.Optional;

public class AraucariaSaplingBlock extends Block implements BonemealableBlock {

    private static final VoxelShape SHAPE = Block.box(3.0, 0.0, 3.0, 13.0, 12.0, 13.0);

    private static final List<Identifier> TREE_VARIANTS = List.of(
            Identifier.fromNamespaceAndPath("primalrevival", "araucaria"),
            Identifier.fromNamespaceAndPath("primalrevival", "araucaria_small"),
            Identifier.fromNamespaceAndPath("primalrevival", "araucaria_tall")
    );

    public AraucariaSaplingBlock(Properties properties) {
        super(properties);
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        if (random.nextFloat() < 0.05f) {
            growTree(level, random, pos);
        }
    }

    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        return random.nextFloat() < 0.45f;
    }

    @Override
    public void performBonemeal(ServerLevel level, RandomSource random, BlockPos pos, BlockState state) {
        growTree(level, random, pos);
    }

    private void growTree(ServerLevel level, RandomSource random, BlockPos pos) {
        Identifier chosen = TREE_VARIANTS.get(random.nextInt(TREE_VARIANTS.size()));

        com.primalrev.primalrevival.PrimalRevival.LOGGER.info("Araucaria sapling chose template {} at {}", chosen, pos);

        StructureTemplateManager structureManager = level.getServer().getStructureManager();
        Optional<StructureTemplate> templateOpt = structureManager.get(chosen);

        if (templateOpt.isEmpty()) {
            com.primalrev.primalrevival.PrimalRevival.LOGGER.error(
                    "Araucaria template {} not found (¿existe data/{}/structure(s)/{}.nbt en resources?)",
                    chosen, chosen.getNamespace(), chosen.getPath());
            return;
        }

        StructureTemplate template = templateOpt.get();

        level.setBlock(pos, Blocks.AIR.defaultBlockState(), 3);


        Vec3i baseSize = template.getSize();
        BlockPos pivot = new BlockPos(baseSize.getX() / 2, 0, baseSize.getZ() / 2);

        StructurePlaceSettings settings = new StructurePlaceSettings()
                .setRotation(Rotation.getRandom(random))
                .setRotationPivot(pivot)
                .setMirror(Mirror.NONE)
                .setIgnoreEntities(false);


        BlockPos placePos = pos.offset(-pivot.getX(), -pivot.getY(), -pivot.getZ());

        boolean placed = template.placeInWorld(level, placePos, placePos, settings, random, 3);
        com.primalrev.primalrevival.PrimalRevival.LOGGER.info("Araucaria template {} placed at {}: {}", chosen, placePos, placed);
    }
}