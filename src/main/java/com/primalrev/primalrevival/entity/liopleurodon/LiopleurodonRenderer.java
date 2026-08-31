package com.primalrev.primalrevival.entity.liopleurodon;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class LiopleurodonRenderer extends GeoEntityRenderer<LiopleurodonEntity, LivingEntityRenderState> {
    public LiopleurodonRenderer(EntityRendererProvider.Context context) {
        super(context, new LiopleurodonModel());
    }
}
