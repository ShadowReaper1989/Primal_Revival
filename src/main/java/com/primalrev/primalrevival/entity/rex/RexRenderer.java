package com.primalrev.primalrevival.entity.rex;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class RexRenderer extends GeoEntityRenderer<RexEntity, LivingEntityRenderState> {

    public RexRenderer(EntityRendererProvider.Context context) {
        super(context, new RexModel());
    }
}

