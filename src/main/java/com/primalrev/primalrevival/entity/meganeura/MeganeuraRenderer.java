package com.primalrev.primalrevival.entity.meganeura;

import com.geckolib.renderer.GeoEntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;

public class MeganeuraRenderer
        extends GeoEntityRenderer<MeganeuraEntity, LivingEntityRenderState> {

    public MeganeuraRenderer(EntityRendererProvider.Context context) {
        super(context, new MeganeuraModel());
    }
}
