package com.primalrev.primalrevival.entity.plumalexius;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class PlumalexiusRenderer extends GeoEntityRenderer<PlumalexiusEntity, LivingEntityRenderState> {
    public PlumalexiusRenderer(EntityRendererProvider.Context context) {
        super(context, new PlumalexiusModel());
    }
}