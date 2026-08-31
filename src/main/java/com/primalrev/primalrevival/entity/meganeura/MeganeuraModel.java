package com.primalrev.primalrevival.entity.meganeura;

import com.geckolib.model.DefaultedEntityGeoModel;
import com.primalrev.primalrevival.PrimalRevival;
import net.minecraft.resources.Identifier;

public class MeganeuraModel
        extends DefaultedEntityGeoModel<MeganeuraEntity> {

    public MeganeuraModel() {
        super(Identifier.fromNamespaceAndPath(
                PrimalRevival.MODID,
                "meganeura"
        ));
    }

}