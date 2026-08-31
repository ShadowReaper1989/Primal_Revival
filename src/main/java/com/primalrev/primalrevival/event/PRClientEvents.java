package com.primalrev.primalrevival.event;

import com.primalrev.primalrevival.PrimalRevival;
import com.primalrev.primalrevival.entity.apatosaurus.ApatosaurusRenderer;
import com.primalrev.primalrevival.entity.arthropleura.ArthropleuraRenderer;
import com.primalrev.primalrevival.entity.mummy.MummyRenderer;
import com.primalrev.primalrevival.entity.plumalexius.PlumalexiusRenderer;
import com.primalrev.primalrevival.registry.PREntities;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import com.primalrev.primalrevival.entity.meganeura.MeganeuraRenderer;
import com.primalrev.primalrevival.entity.rex.RexRenderer;
import com.primalrev.primalrevival.entity.liopleurodon.LiopleurodonRenderer;
import com.primalrev.primalrevival.entity.scutosaurus.ScutosaurusRenderer;


@EventBusSubscriber(
        modid = PrimalRevival.MODID,
        value = Dist.CLIENT
)
public class PRClientEvents {

    @SubscribeEvent
    public static void registerRenderers(
            EntityRenderersEvent.RegisterRenderers event
    ) {
        event.registerEntityRenderer(
                PREntities.MEGANEURA.get(),
                MeganeuraRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.REX.get(),
                RexRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.LIOPLEURODON.get(),
                LiopleurodonRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.MUMMY.get(),
                MummyRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.ARTHROPLEURA.get(),
                ArthropleuraRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.SCUTOSAURUS.get(),
                ScutosaurusRenderer::new
        );
        event.registerEntityRenderer(
                PREntities.PLUMALEXIUS.get(),
                PlumalexiusRenderer::new
        );

        event.registerEntityRenderer(
                PREntities.APATOSAURUS.get(),
                ApatosaurusRenderer::new
        );
    }
}
