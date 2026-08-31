package com.primalrev.primalrevival.entity.mummy;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class MummyModel extends DefaultedEntityGeoModel<MummyEntity> {

    public MummyModel() {
        super(Identifier.fromNamespaceAndPath(
                PrimalRevival.MODID,
                "mummy"
        ));
    }
}
