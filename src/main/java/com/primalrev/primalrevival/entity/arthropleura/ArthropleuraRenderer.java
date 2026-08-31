package com.primalrev.primalrevival.entity.arthropleura;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ArthropleuraRenderer extends GeoEntityRenderer<ArthropleuraEntity, LivingEntityRenderState> {

    public ArthropleuraRenderer(EntityRendererProvider.Context context) {
        super(context, new ArthropleuraModel());
    }
}
