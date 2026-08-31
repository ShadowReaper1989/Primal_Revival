package com.primalrev.primalrevival.entity.rex;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class RexModel extends DefaultedEntityGeoModel<RexEntity> {

    public RexModel() {
        super(Identifier.fromNamespaceAndPath(
                PrimalRevival.MODID,
                "rexy"
        ));
    }
}
