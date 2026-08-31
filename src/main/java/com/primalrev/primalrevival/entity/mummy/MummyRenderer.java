package com.primalrev.primalrevival.entity.mummy;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class MummyRenderer extends GeoEntityRenderer<MummyEntity, LivingEntityRenderState> {

    public MummyRenderer(EntityRendererProvider.Context context) {
        super(context, new MummyModel());
    }
}
