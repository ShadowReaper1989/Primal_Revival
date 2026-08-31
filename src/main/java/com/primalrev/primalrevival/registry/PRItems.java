package com.primalrev.primalrevival.registry;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BrushItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.component.BlocksAttacks;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;
import java.util.Optional;

import static com.primalrev.primalrevival.PrimalRevival.MODID;

public class PRItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(MODID);
    public static final DeferredItem<Item> IRIDIUM = ITEMS.registerSimpleItem("iridium");
    public static final DeferredItem<Item> STING = ITEMS.registerSimpleItem("sting");
    public static final DeferredItem<ShieldItem> MYCENAEAN_SHIELD = ITEMS.registerItem(
            "mycenaean_shield",
            ShieldItem::new,
            props -> props
                    .durability(336)
                    .component(DataComponents.BLOCKS_ATTACKS, new BlocksAttacks(
                            0.0f,
                            0.5f,
                            List.of(new BlocksAttacks.DamageReduction(
                                    90.0f,
                                    Optional.empty(),
                                    0.0f,
                                    1.0f
                            )),
                            new BlocksAttacks.ItemDamageFunction(1.0f, 0.0f, 1.0f),
                            Optional.empty(),
                            Optional.of(SoundEvents.SHIELD_BLOCK),
                            Optional.of(SoundEvents.SHIELD_BREAK)
                    ))
    );
    public static final DeferredItem<BlockItem> IRIDIUM_ORE = ITEMS.registerSimpleBlockItem("iridium_ore", PRBlocks.IRIDIUM_ORE);
    public static final DeferredItem<BlockItem> GYPSUM_ORE = ITEMS.registerSimpleBlockItem("gypsum_ore", PRBlocks.GYPSUM_ORE);
    public static final DeferredItem<BlockItem> RUBY_ORE = ITEMS.registerSimpleBlockItem("ruby_ore", PRBlocks.RUBY_ORE);
    public static final DeferredItem<BlockItem> GYPSUM_ROCK = ITEMS.registerSimpleBlockItem("gypsum_rock", PRBlocks.GYPSUM_ROCK);
    public static final DeferredItem<BlockItem> PRIMEVAL_STONE = ITEMS.registerSimpleBlockItem("primeval_stone", PRBlocks.PRIMEVAL_STONE);
    public static final DeferredItem<BlockItem> VOLCANIC_TUFF = ITEMS.registerSimpleBlockItem("volcanic_tuff", PRBlocks.VOLCANIC_TUFF);

    public static final DeferredItem<Item> DINOSAUR_MEAT = ITEMS.registerSimpleItem("dinosaur_meat");
    public static final DeferredItem<Item> RUBY_ITEM = ITEMS.registerSimpleItem("ruby_item");
    public static final DeferredItem<Item> TREASURE = ITEMS.registerSimpleItem("treasure");

    public static final DeferredItem<BrushItem> PALEO_BRUSH = ITEMS.registerItem(
            "paleo_brush",
            BrushItem::new,
            props -> props.durability(64));
    public static final DeferredItem<Item> GYPSUM_CRISTAL = ITEMS.registerSimpleItem("gypsum_cristal");
    public static final DeferredItem<Item> RHODIUM = ITEMS.registerSimpleItem("rhodium");
    public static final DeferredItem<Item> PLATINUM = ITEMS.registerSimpleItem("platinum");
    public static final DeferredItem<Item> INSECT_FOSSIL = ITEMS.registerSimpleItem("insect_fossil");
    public static final DeferredItem<Item> REX_FOSSIL = ITEMS.registerSimpleItem("rex_fossil");
    public static final DeferredItem<Item> PARAREPTILE_FOSSIL = ITEMS.registerSimpleItem("parareptile_fossil");
    public static final DeferredItem<Item> INSECT_MEMBRANE = ITEMS.registerSimpleItem("insect_membrane");


    public static final DeferredItem<BlockItem> MONSTERA_DELICIOSA = ITEMS.registerSimpleBlockItem("monstera_deliciosa", PRBlocks.MONSTERA_DELICIOSA);
    public static final DeferredItem<BlockItem> HELICONIA = ITEMS.registerSimpleBlockItem("heliconia", PRBlocks.HELICONIA);
    public static final DeferredItem<BlockItem> PTERIS_UMBROSA = ITEMS.registerSimpleBlockItem("pteris_umbrosa", PRBlocks.PTERIS_UMBROSA);

    public static final DeferredItem<BlockItem> ARAUCARIA_LOG = ITEMS.registerSimpleBlockItem("araucaria_log", PRBlocks.ARAUCARIA_LOG);
    public static final DeferredItem<BlockItem> ARAUCARIA_WOOD = ITEMS.registerSimpleBlockItem("araucaria_wood", PRBlocks.ARAUCARIA_WOOD);
    public static final DeferredItem<BlockItem> ARAUCARIA_LEAVES = ITEMS.registerSimpleBlockItem("araucaria_leaves", PRBlocks.ARAUCARIA_LEAVES);
    public static final DeferredItem<BlockItem> ARAUCARIA_SAPLING = ITEMS.registerSimpleBlockItem("araucaria_sapling", PRBlocks.ARAUCARIA_SAPLING);


    public static final DeferredItem<SpawnEggItem> MEGANEURA_SPAWN_EGG = ITEMS.registerItem(
            "meganeura_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.MEGANEURA.get())));
    public static final DeferredItem<SpawnEggItem> REX_SPAWN_EGG = ITEMS.registerItem(
            "rex_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.REX.get())));
    public static final DeferredItem<SpawnEggItem> LIOPLEURODON_SPAWN_EGG = ITEMS.registerItem(
            "liopleurodon_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.LIOPLEURODON.get())));
    public static final DeferredItem<SpawnEggItem> MUMMY_SPAWN_EGG = ITEMS.registerItem(
            "mummy_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.MUMMY.get())));
    public static final DeferredItem<SpawnEggItem> ARTHROPLEURA_SPAWN_EGG = ITEMS.registerItem(
            "arthropleura_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.ARTHROPLEURA.get())));
    public static final DeferredItem<SpawnEggItem> SCUTOSAURUS_SPAWN_EGG = ITEMS.registerItem(
            "scutosaurus_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.SCUTOSAURUS.get())));
    public static final DeferredItem<SpawnEggItem> PLUMALEXIUS_SPAWN_EGG = ITEMS.registerItem(
            "plumalexius_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.PLUMALEXIUS.get())));
    public static final DeferredItem<SpawnEggItem> APATOSAURUS_SPAWN_EGG = ITEMS.registerItem(
            "apatosaurus_spawn_egg",
            props -> new SpawnEggItem(props.spawnEgg(PREntities.APATOSAURUS.get())));
}