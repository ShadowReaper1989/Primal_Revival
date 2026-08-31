package com.primalrev.primalrevival.entity.arthropleura;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.geckolib.renderer.base.GeoRenderState;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class ArthropleuraModel extends DefaultedEntityGeoModel<ArthropleuraEntity> {

    public ArthropleuraModel() {
        super(Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "arthropleura"));
    }

    @Override
    public Identifier getTextureResource(GeoRenderState renderState) {
        return Identifier.fromNamespaceAndPath(PrimalRevival.MODID, "textures/entity/arthropleura.png");
    }
}
