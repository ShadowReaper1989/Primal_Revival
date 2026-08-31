package com.primalrev.primalrevival.registry;

import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PRBlocks {

    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(PrimalRevival.MODID);

    public static final DeferredBlock<DropExperienceBlock> IRIDIUM_ORE =
            BLOCKS.registerBlock(
                    "iridium_ore",
                    properties -> new DropExperienceBlock(
                            UniformInt.of(3, 7),
                            properties
                    ),
                    () -> BlockBehaviour.Properties.of()
                            .strength(2.0f, 3.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<DropExperienceBlock> GYPSUM_ORE =
            BLOCKS.registerBlock(
                    "gypsum_ore",
                    properties -> new DropExperienceBlock(
                            UniformInt.of(1, 3),
                            properties
                    ),
                    () -> BlockBehaviour.Properties.of()
                            .strength(1.5f, 2.5f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<DropExperienceBlock> RUBY_ORE =
            BLOCKS.registerBlock(
                    "ruby_ore",
                    properties -> new DropExperienceBlock(
                            UniformInt.of(2, 5),
                            properties
                    ),
                    () -> BlockBehaviour.Properties.of()
                            .strength(2.0f, 3.5f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<DropExperienceBlock> GYPSUM_ROCK =
            BLOCKS.registerBlock(
                    "gypsum_rock",
                    properties -> new DropExperienceBlock(
                            UniformInt.of(1, 3),
                            properties
                    ),
                    () -> BlockBehaviour.Properties.of()
                            .strength(1.5f, 2.5f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<Block> PRIMEVAL_STONE =
            BLOCKS.registerSimpleBlock(
                    "primeval_stone",
                    () -> BlockBehaviour.Properties.of()
                            .strength(1.5f, 6.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<Block> VOLCANIC_TUFF =
            BLOCKS.registerSimpleBlock(
                    "volcanic_tuff",
                    () -> BlockBehaviour.Properties.of()
                            .strength(1.5f, 3.0f)
                            .requiresCorrectToolForDrops()
            );

    public static final DeferredBlock<Block> MONSTERA_DELICIOSA =
            BLOCKS.registerBlock(
                    "monstera_deliciosa",
                    properties -> new com.primalrev.primalrevival.blocks.PlantBlock(properties),
                    () -> BlockBehaviour.Properties.of()
                            .noCollision()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.GRASS)
            );

    public static final DeferredBlock<Block> HELICONIA =
            BLOCKS.registerBlock(
                    "heliconia",
                    properties -> new com.primalrev.primalrevival.blocks.PlantBlock(properties),
                    () -> BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.GRASS)
            );

    public static final DeferredBlock<Block> PTERIS_UMBROSA =
            BLOCKS.registerBlock(
                    "pteris_umbrosa",
                    properties -> new com.primalrev.primalrevival.blocks.PlantBlock(properties),
                    () -> BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.GRASS)
            );


    public static final DeferredBlock<Block> ARAUCARIA_LOG =
            BLOCKS.registerBlock(
                    "araucaria_log",
                    properties -> new RotatedPillarBlock(properties),
                    () -> BlockBehaviour.Properties.of()
                            .strength(2.0f)
                            .sound(SoundType.WOOD)
            );

    public static final DeferredBlock<Block> ARAUCARIA_WOOD =
            BLOCKS.registerSimpleBlock(
                    "araucaria_wood",
                    () -> BlockBehaviour.Properties.of()
                            .strength(2.0f)
                            .sound(SoundType.WOOD)
            );

    public static final DeferredBlock<Block> ARAUCARIA_LEAVES =
            BLOCKS.registerSimpleBlock(
                    "araucaria_leaves",
                    () -> BlockBehaviour.Properties.of()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.GRASS)
            );

    public static final DeferredBlock<Block> ARAUCARIA_SAPLING =
            BLOCKS.registerBlock(
                    "araucaria_sapling",
                    properties -> new AraucariaSaplingBlock(properties),
                    () -> BlockBehaviour.Properties.of()
                            .noCollision()
                            .noOcclusion()
                            .instabreak()
                            .sound(SoundType.GRASS)
                            .randomTicks()
            );

    public static void register(IEventBus modEventBus) {
        BLOCKS.register(modEventBus);
    }
}