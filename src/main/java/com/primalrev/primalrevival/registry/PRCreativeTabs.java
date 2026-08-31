package com.primalrev.primalrevival.registry;

import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class PRCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(net.minecraft.core.registries.Registries.CREATIVE_MODE_TAB, PrimalRevival.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> PRIMAL_REVIVAL =
            TABS.register("primal_revival", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.primalrevival"))
                    .icon(() -> new ItemStack(PRItems.IRIDIUM.get()))
                    .displayItems((parameters, output) -> {
                        output.accept(PRItems.IRIDIUM.get());
                        output.accept(PRItems.STING.get());
                        output.accept(PRItems.MYCENAEAN_SHIELD.get());
                        output.accept(PRItems.IRIDIUM_ORE.get());
                        output.accept(PRItems.RUBY_ORE.get());
                        output.accept(PRItems.GYPSUM_ORE.get());
                        output.accept(PRItems.GYPSUM_ROCK.get());
                        output.accept(PRItems.VOLCANIC_TUFF.get());
                        output.accept(PRItems.PRIMEVAL_STONE.get());
                        output.accept(PRItems.MEGANEURA_SPAWN_EGG.get());
                        output.accept(PRItems.REX_SPAWN_EGG.get());
                        output.accept(PRItems.LIOPLEURODON_SPAWN_EGG.get());
                        output.accept(PRItems.MUMMY_SPAWN_EGG.get());
                        output.accept(PRItems.ARTHROPLEURA_SPAWN_EGG.get());
                        output.accept(PRItems.SCUTOSAURUS_SPAWN_EGG.get());
                        output.accept(PRItems.PLUMALEXIUS_SPAWN_EGG.get());
                        output.accept(PRItems.APATOSAURUS_SPAWN_EGG.get());
                        output.accept(PRItems.DINOSAUR_MEAT.get());
                        output.accept(PRItems.RUBY_ITEM.get());
                        output.accept(PRItems.TREASURE.get());
                        output.accept(PRItems.PALEO_BRUSH.get());
                        output.accept(PRItems.GYPSUM_CRISTAL.get());
                        output.accept(PRItems.REX_FOSSIL.get());
                        output.accept(PRItems.PARAREPTILE_FOSSIL.get());
                        output.accept(PRItems.INSECT_FOSSIL.get());
                        output.accept(PRItems.INSECT_MEMBRANE.get());
                        output.accept(PRItems.RHODIUM.get());
                        output.accept(PRItems.PLATINUM.get());
                        output.accept(PRItems.MONSTERA_DELICIOSA.get());
                        output.accept(PRItems.HELICONIA.get());
                        output.accept(PRItems.PTERIS_UMBROSA.get());
                        output.accept(PRItems.ARAUCARIA_LOG.get());
                        output.accept(PRItems.ARAUCARIA_WOOD.get());
                        output.accept(PRItems.ARAUCARIA_LEAVES.get());
                        output.accept(PRItems.ARAUCARIA_SAPLING.get());
                    })
                    .build());
}