package com.primalrev.primalrevival.entity.apatosaurus;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ApatosaurusRenderer extends GeoEntityRenderer<ApatosaurusEntity, LivingEntityRenderState> {
    public ApatosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new ApatosaurusModel());
    }
}
