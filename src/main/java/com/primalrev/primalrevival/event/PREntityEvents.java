package com.primalrev.primalrevival.event;

import com.primalrev.primalrevival.PrimalRevival;
import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusEntity;
import com.primalrev.primalrevival.entity.arthropleura.ArthropleuraEntity;
import com.primalrev.primalrevival.entity.meganeura.MeganeuraEntity;
import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonEntity;
import com.primalrev.primalrevival.entity.mummy.MummyEntity;
import com.primalrev.primalrevival.entity.plumalexius.PlumalexiusEntity;
import com.primalrev.primalrevival.entity.rex.RexEntity;
import com.primalrev.primalrevival.entity.scutosaurus.ScutosaurusEntity;
import com.primalrev.primalrevival.registry.PREntities;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;

@EventBusSubscriber(modid = PrimalRevival.MODID)
public class PREntityEvents {

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeCreationEvent event) {
        event.put(PREntities.MEGANEURA.get(), MeganeuraEntity.createAttributes().build());
        event.put(PREntities.REX.get(), RexEntity.createAttributes().build());
        event.put(PREntities.LIOPLEURODON.get(), LiopleurodonEntity.createAttributes().build());
        event.put(PREntities.MUMMY.get(), MummyEntity.createAttributes().build());
        event.put(PREntities.ARTHROPLEURA.get(), ArthropleuraEntity.createAttributes().build());
        event.put(PREntities.SCUTOSAURUS.get(), ScutosaurusEntity.createAttributes().build());
        event.put(PREntities.PLUMALEXIUS.get(), PlumalexiusEntity.createAttributes().build());
        event.put(PREntities.APATOSAURUS.get(), ApatosaurusEntity.createAttributes().build());
    }
}