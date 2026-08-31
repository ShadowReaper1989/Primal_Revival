package com.primalrev.primalrevival;

import com.mojang.logging.LogUtils;
import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import com.primalrev.primalrevival.entity.mummy.MummyEntity;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import com.primalrev.primalrevival.registry.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import org.slf4j.Logger;

@Mod(PrimalRevival.MODID)
public class PrimalRevival {
    public static final String MODID = "primalrevival";
    public static final Logger LOGGER = LogUtils.getLogger();

    public PrimalRevival(IEventBus modEventBus, ModContainer modContainer) {
        PRBlocks.register(modEventBus);
        PRItems.ITEMS.register(modEventBus);
        PREntities.ENTITIES.register(modEventBus);
        PRCreativeTabs.TABS.register(modEventBus);
        PRSounds.SOUNDS.register(modEventBus);
        PRParticles.PARTICLES.register(modEventBus);

        modEventBus.addListener(this::commonSetup);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        LOGGER.info("Primal & Revival");
    }
}