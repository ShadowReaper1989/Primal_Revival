package com.primalrev.primalrevival.entity.scutosaurus;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class ScutosaurusRenderer extends GeoEntityRenderer<ScutosaurusEntity, LivingEntityRenderState> {
    public ScutosaurusRenderer(EntityRendererProvider.Context context) {
        super(context, new ScutosaurusModel());
    }
}